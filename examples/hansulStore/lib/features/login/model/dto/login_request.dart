import '../../../../common/model/dto/base_request_dto.dart';

class LoginRequest extends BaseRequestDto{
  final String email;
  final String password;

  LoginRequest({
    required this.email,
    required this.password,
  });

  @override
  Map<String, dynamic> toJson() => {
    'email': email,
    'password': password,
  };
}