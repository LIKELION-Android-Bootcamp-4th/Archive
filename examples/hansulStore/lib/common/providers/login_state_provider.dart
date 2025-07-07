import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

final loginStateProvider = StateNotifierProvider<LoginStateNotifier, bool>((ref) {
  return LoginStateNotifier();
});

class LoginStateNotifier extends StateNotifier<bool> {
  final _storage = const FlutterSecureStorage();

  static const _accessToken = "accessToken";
  static const _refreshToken = "refreshToken";

  LoginStateNotifier() : super(false);

  Future<void> login(String accessToken, String refreshToken) async {
    await _storage.write(key: _accessToken, value: accessToken);
    await _storage.write(key: _refreshToken, value: refreshToken);
    state = true;
  }

  Future<void> logout() async {
    await _storage.deleteAll();
    state = false;
  }

  Future<void> loadFromStorage() async {
    final token = await _storage.read(key: _accessToken);
    state = token != null;
  }

  Future<String?> getAccessToken() => _storage.read(key: _accessToken);
  Future<String?> getRefreshToken() => _storage.read(key: _refreshToken);
}
