import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/common/model/entity/base_response.dart';
import 'package:hansul_store/features/community/repository/community_repository.dart';

import '../../../common/model/network/dio_client.dart';
import '../../../common/model/services/community_service.dart';
import '../model/dto/add_post_request.dart';
import '../model/entity/post.dart';

final postWriteViewModelProvider = AsyncNotifierProvider<PostWriteViewModel , BaseResponse<Post?>>(PostWriteViewModel.new);

class PostWriteViewModel extends AsyncNotifier<BaseResponse<Post?>> {
  late final CommunityRepository _repository;

  @override
  Future<BaseResponse<Post?>> build() async {
    _repository = CommunityRepositoryImpl(CommunityService(dio));
    return BaseResponse(success: false, message: "");
  }

  Future<void> createPost(AddPostRequest request) async {
    state = const AsyncLoading();
    final response = await _repository.addPost(request);
    debugPrint("PostWriteViewModel.createPost.response: ${response.message}");
    state = AsyncData(response);
  }
}

