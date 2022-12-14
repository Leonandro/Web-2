package com.blood.bloodbackend.filters;

import com.blood.bloodbackend.service.MyUserDetailService;
import com.blood.bloodbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader  = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        System.out.println("REQUEST " + request.getMethod() + " " + request.getRequestURI() + " - " + request.getHeader("ClientID"));
        if(request.getRemoteHost().equals("127.0.0.1") && request.getHeader("ClientID") != null && request.getHeader("ClientID").equals("bloodapp_98")) {

            if (authorizationHeader != null && authorizationHeader.startsWith("BLOOD ")) {
                jwt = authorizationHeader.substring(6);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.myUserDetailService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails) && this.urlAcessChecker(request.getMethod(), request.getRequestURI(), userDetails.getAuthorities())) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {

                    if (!this.urlAcessChecker(request.getMethod(), request.getRequestURI(), userDetails.getAuthorities())) {
                        response.setStatus(401);

                        //Header sinalizando que o usu??rio n??o tem a permiss??o m??nima necess??ria para acessar esse endpoint
                        response.setHeader("reason", "User-Dont-Have-Min-Role");
                    }
                }
            }
        } else {
            response.setHeader("reason", "Invalid origin");
        }
        filterChain.doFilter(request, response);

    }

    //Checa se o usu??rio pode acessar esse endpoint
    private boolean urlAcessChecker (String method, String url, Collection<? extends GrantedAuthority> papeis) {

        if(method.equals("GET") && url.startsWith("/usuario/") && url.split("/").length > 2 && isNumeric(url.split("/")[2])) {
                return papeis.stream().filter(x -> x.getAuthority().equals("ADMINISTRADOR")).count() > 0;
        }

        if( method.equals("DELETE") && url.startsWith("/usuario")) {
            return papeis.stream().filter(x -> (x.getAuthority().equals("ADMINISTRADOR") || x.getAuthority().equals("EDITOR"))).count() > 0;
        }


        //POST/DELETE  /unidadedoacao -> ADM
        if((method.equals("POST") || method.equals("DELETE") )&& url.startsWith("/unidadedoacao")) {
            return papeis.stream().filter(x -> x.getAuthority().equals("ADMINISTRADOR")).count() > 0;
        }

        //POST/DELETE /endereco -> ADM ou EDITOR
        if((method.equals("POST") || method.equals("DELETE") )&& url.startsWith("/endereco")) {
            return papeis.stream().filter(x -> (x.getAuthority().equals("ADMINISTRADOR") || x.getAuthority().equals("EDITOR"))).count() > 0;
        }

        return true;
    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

 }
