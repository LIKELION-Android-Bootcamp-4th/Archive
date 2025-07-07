import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter/material.dart';
import 'package:hansul_store/common/dto/base_request_dto.dart';
import 'package:hansul_store/core/network/dio_client.dart';
import 'package:hansul_store/core/services/community_service.dart';
import 'package:hansul_store/features/community/repository/community_repository.dart';

final postEditViewModelProvider =
Provider<PostEditViewModel>((ref) => PostEditViewModel());

class PostEditViewModel {
  final CommunityRepository _repository =
  CommunityRepositoryImpl(CommunityService(dio));

  /// 게시글 수정
  Future<bool> editPost(String postId, BaseRequestDto dto) async {
    try {
      debugPrint("[PostEditViewModel] editPost: $dto");
      final response = await _repository.editPost(postId, dto);
      return response.success;
    } catch (e, stack) {
      debugPrint("[PostEditViewModel] editPost error: $e");
      debugPrintStack(stackTrace: stack);
      return false;
    }
  }
}
