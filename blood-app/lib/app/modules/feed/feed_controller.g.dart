// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'feed_controller.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$FeedController on _FeedControllerBase, Store {
  final _$filterAtom = Atom(name: '_FeedControllerBase.filter');

  @override
  bool get filter {
    _$filterAtom.reportRead();
    return super.filter;
  }

  @override
  set filter(bool value) {
    _$filterAtom.reportWrite(value, super.filter, () {
      super.filter = value;
    });
  }

  final _$_FeedControllerBaseActionController =
      ActionController(name: '_FeedControllerBase');

  @override
  void changeFilter() {
    final _$actionInfo = _$_FeedControllerBaseActionController.startAction(
        name: '_FeedControllerBase.changeFilter');
    try {
      return super.changeFilter();
    } finally {
      _$_FeedControllerBaseActionController.endAction(_$actionInfo);
    }
  }

  @override
  String toString() {
    return '''
filter: ${filter}
    ''';
  }
}
