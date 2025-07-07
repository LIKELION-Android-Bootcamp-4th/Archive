import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/core/network/dio_client.dart';
import 'package:hansul_store/core/services/auth_service.dart';
import 'package:hansul_store/core/services/secure_storage_service.dart';
import 'package:hansul_store/features/login/dto/login_request_dto.dart';
import 'package:hansul_store/common/providers/login_state_provider.dart';

import '../../../common/model/base_response.dart';
import '../../../common/model/user.dart';
import '../model/login_response.dart';


final loginViewModelProvider =
  StateNotifierProvider<LoginViewModel, AsyncValue<User?>>((ref) {

  final authService = AuthService(dio);
  return LoginViewModel(authService, ref);
});

class LoginViewModel extends StateNotifier<AsyncValue<User?>> {
  final AuthService authService;
  final Ref ref;

  LoginViewModel(this.authService, this.ref)
      : super(const AsyncValue.data(null));

  Future<void> login(String email, String password) async {
    state = const AsyncValue.loading();
    try {
      // 1. 로그인 요청 → BaseResponseDto2
      final responseDto = await authService.login(
        LoginRequestDto(email: email, password: password),
      );

      // 2. 응답 처리
      if (responseDto.success && responseDto.data != null) {
        final parsedResponse = BaseResponse<LoginResponse>.fromDto(
          responseDto,
          LoginResponse.fromJson,
        );

        final loginResponse = parsedResponse.data!;

        // 3. 사용자 상태 저장
        state = AsyncValue.data(loginResponse.user);

        // 4. 토큰 저장
        await SecureStorageService.setToken(
          accessToken: loginResponse.accessToken,
          refreshToken: loginResponse.refreshToken,
        );

        // 5. 로그인 상태 업데이트
        await ref.read(loginStateProvider.notifier).login(
          loginResponse.accessToken,
          loginResponse.refreshToken,
        );
      } else {
        throw Exception(responseDto.error ?? responseDto.message);
      }
    } catch (e, stackTrace) {

      debugPrint(e.toString());
      debugPrintStack(stackTrace: stackTrace);
      state = AsyncValue.error(e, stackTrace);
    }
  }
}