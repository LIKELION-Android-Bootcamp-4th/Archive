import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorageService {
  static final _storage = FlutterSecureStorage();

  static const _accessToken = "accessToken";
  static const _refreshToken = "refreshToken";

  static Future<void> setToken({
    required String accessToken,
    required String refreshToken
  }) async {
    await _storage.write(key: _accessToken, value: accessToken);
    await _storage.write(key: _refreshToken, value: refreshToken);
  }

  static Future<String?> getAccessToken() async {
    return await _storage.read(key: _accessToken);
  }

  static Future<String?> getRefreshToken() async {
    return await _storage.read(key: _refreshToken);
  }

  static Future<void> clear() async {
    _storage.delete(key: _accessToken);
    _storage.delete(key: _refreshToken);
  }

}