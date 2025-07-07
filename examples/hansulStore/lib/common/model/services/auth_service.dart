import 'package:hansul_store/common/model/dto/base_request_dto.dart';

import '../../../features/login/model/dto/login_request.dart';
import '../dto/base_response_dto.dart';
import '../network/dio_client.dart';

class AuthService {
  AuthService(dio);

  Future<BaseResponseDto> login(BaseRequestDto request) async {
    final response = await dio.post('/auth/login', data: request.toJson());
    return BaseResponseDto.fromJson(response.data);
  }
}