import 'package:hansul_store/common/dto/base_request_dto.dart';

class LoginRequestDto extends BaseRequestDto{
  final String email;
  final String password;

  LoginRequestDto({
    required this.email,
    required this.password,
  });

  @override
  Map<String, dynamic> toJson() => {
    'email': email,
    'password': password,
  };
}