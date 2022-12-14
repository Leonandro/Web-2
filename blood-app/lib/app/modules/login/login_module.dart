import 'package:flutter_modular/flutter_modular.dart';
import 'package:blood_app/app/modules/login/login_page.dart';

class LoginModule extends Module {
  @override
  final List<Bind> binds = [];

  @override
  final List<ModularRoute> routes = [
    ChildRoute(Modular.initialRoute, child: (_, args) => LoginPage()),
  ];
}
