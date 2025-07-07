import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/dto/add_post_request_dto.dart';
import 'package:hansul_store/features/community/dto/community_request_dto.dart';
import '/core/network/dio_client.dart';
import 'package:hansul_store/common/model/base_response.dart';
import 'package:hansul_store/core/services/community_service.dart';
import 'package:hansul_store/features/community/model/post.dart';
import 'package:hansul_store/features/community/repository/community_repository.dart';

final postWriteViewModelProvider = AsyncNotifierProvider<PostWriteViewModel , BaseResponse<Post?>>(PostWriteViewModel.new);

class PostWriteViewModel extends AsyncNotifier<BaseResponse<Post?>> {
  late final CommunityRepository _repository;

  @override
  Future<BaseResponse<Post?>> build() async {
    _repository = CommunityRepositoryImpl(CommunityService(dio));
    return BaseResponse(success: false, message: "");
  }

  Future<void> createPost(AddPostRequestDto request) async {
    state = const AsyncLoading();
    final response = await _repository.addPost(request);
    state = AsyncData(response);
  }
}

