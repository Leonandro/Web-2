import 'dart:ffi';

class BackendRoutes {
  BackendRoutes._();

  //!> VERSÃO DO AMBIENTE DO BACKEND
  //> HOMOLOGAÇÃO

  static const String BACKEND_MODE = "HOMOLOGACAO";
  //static const String BACKEND_DOMAIN = "http://10.0.2.2:8444";
  //-----------------------------------------------------------------

  //> PRODUÇÃO
  //static const String BACKEND_MODE = "PRODUCAO";
  static const String BACKEND_DOMAIN = "http://10.0.2.2:8445";

  //static const String BACKEND_DOMAIN = "https://blood-backend2.herokuapp.com";

  // ------------------------------------------------------------
  static const String LOGIN = BACKEND_DOMAIN + "/autenticacao";

  static const String USER = BACKEND_DOMAIN + "/usuario";

  static const String USER_BYUSERNAME = USER + "/byusername?username=";

  static const String DONATION_UNIT = BACKEND_DOMAIN + "/unidadedoacao";

  static const String PUBLICATION = BACKEND_DOMAIN + "/publicacao";
}
