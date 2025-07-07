import 'package:hansul_store/common/model/entity/base_response.dart';

class BaseResponseDto {
  final bool success;
  final String message;
  final Map<String,dynamic>? data;
  final String? error;
  final String? timestamp;

  BaseResponseDto({
    required this.success,
    required this.message,
    this.data,
    this.error,
    this.timestamp
});
  factory BaseResponseDto.fromJson(
      Map<String, dynamic> json
      ) {
    return BaseResponseDto(
      success: json['success'] ?? false,
      message: json['message'] ?? '',
      data: json['data'] ?? null ,
      error: json['error'], // 서버가 에러 시 넘겨주는 메시지
      timestamp: json['timestamp'],
    );
  }
}