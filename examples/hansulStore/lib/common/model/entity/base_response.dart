

import '../dto/base_response_dto.dart';

class BaseResponse<T> {
  final bool success;
  final String message;
  final T? data;
  final String? error;
  final String? timestamp;

  BaseResponse({
    required this.success,
    required this.message,
    this.data,
    this.error,
    this.timestamp
  });

  factory BaseResponse.fromDto(
      BaseResponseDto dto,
      T Function(Map<String, dynamic>) fromJsonT,
      ) {
    return BaseResponse<T>(
      success: dto.success,
      message: dto.message,
      data: dto.success && dto.data != null && fromJsonT != null
          ? fromJsonT(dto.data!)
          : null,
      error: dto.error,
      timestamp: dto.timestamp,
    );
  }

  BaseResponse<T> toEntity(
      BaseResponseDto dto,
      T Function(Map<String, dynamic>) fromJsonT,
      ) {
    return BaseResponse<T>(
      success: dto.success,
      message: dto.message,
      data: dto.data != null ? fromJsonT(dto.data!) : null,
      error: dto.error,
      timestamp: dto.timestamp,
    );
  }
}

