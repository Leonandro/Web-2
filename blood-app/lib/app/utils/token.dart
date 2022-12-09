class Token {
  String jwt;

  Token({required this.jwt});

  factory Token.fromJson(Map<String, dynamic> json) {
    return Token(
      jwt: json['response']['token'],
    );
  }

  @override
  String toString() {
    // TODO: implement toString
    return "Token = " + this.jwt;
  }
}
