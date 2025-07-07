import 'package:hansul_store/features/login/dto/login_request_dto.dart';
import '../../common/dto/base_response_dto.dart';
import '/core/network/dio_client.dart';

class AuthService {
  AuthService(dio);

  Future<BaseResponseDto> login(LoginRequestDto request) async {
    final response = await dio.post('/auth/login', data: request.toJson());
    return BaseResponseDto.fromJson(response.data);
  }
}