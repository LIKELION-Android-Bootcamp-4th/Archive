// lib/core/network/interceptors/auth_interceptor.dart

import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter/material.dart';
import '../../../../main.dart';
import '../../services/secure_storage_service.dart';

class AuthInterceptor extends Interceptor {
  final String returnTo;

  AuthInterceptor({this.returnTo = '/'});

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    final companyCode = dotenv.env['X_COMPANY_CODE'];

    final token = await SecureStorageService.getAccessToken();
    if (token != null) {
      options.headers['Authorization'] = 'Bearer $token';
    }

    if (companyCode != null) {
      options.headers['X-Company-Code'] = companyCode;
    }

    return handler.next(options); // 다음으로 넘김
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    debugPrintStack(stackTrace: err.stackTrace);
    debugPrint(err.message);

    if (err.response?.statusCode == 403) {

      navigatorKey.currentState?.pushNamedAndRemoveUntil(
        '/login',
            (route) => false,
        arguments: returnTo,
      );

      ScaffoldMessenger.of(navigatorKey.currentContext!).showSnackBar(
        const SnackBar(content: Text('로그인 세션이 만료되었습니다. 다시 로그인해주세요.')),
      );
    }

    handler.next(err);
  }
}
