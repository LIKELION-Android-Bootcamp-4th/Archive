// lib/core/network/dio_client.dart
import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'interceptors/auth_interceptor.dart';
import 'interceptors/logging_interceptor.dart';
Dio? _dioInstance;

Dio get dio {
  if (_dioInstance != null) return _dioInstance!;

  final baseUrl = dotenv.env['API_BASE_URL'];
  if (baseUrl == null || baseUrl.isEmpty || !baseUrl.startsWith('http')) {
    throw Exception('🚨 API_BASE_URL is missing or invalid.');
  }

  final dioInstance = Dio(BaseOptions(
    baseUrl: baseUrl,
    connectTimeout: const Duration(seconds: 10),
    receiveTimeout: const Duration(seconds: 10),
  ));

  dioInstance.interceptors.addAll([AuthInterceptor()
    ,LoggingInterceptor()
  ]);

  _dioInstance = dioInstance;
  return _dioInstance!;
}