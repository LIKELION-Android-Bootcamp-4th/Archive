import 'package:flutter/widgets.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/repository/community_repository.dart';
import 'package:hansul_store/common/model/entity/base_response.dart';

import '../../../common/model/network/dio_client.dart';
import '../../../common/model/services/community_service.dart';
import '../model/entity/post.dart';


final postIdProvider = Provider<String>((ref) => throw UnimplementedError());

final postDetailViewModelProvider =
AsyncNotifierProvider.autoDispose<PostDetailViewModel, BaseResponse<Post?>>(
  PostDetailViewModel.new,
  dependencies: [postIdProvider],
);

class PostDetailViewModel extends AutoDisposeAsyncNotifier<BaseResponse<Post?>> {
  CommunityRepository? _repository;

  @override
  Future<BaseResponse<Post?>> build() async {
    final postId = ref.watch(postIdProvider);

    _repository ??= CommunityRepositoryImpl(CommunityService(dio));
    debugPrint("[PostDetailViewModel] postId:$postId");

    return await _repository!.fetchPost(postId);
  }

  Future<BaseResponse> deletePost() async {
    final postId = ref.watch(postIdProvider);

    // repository가 null이면 에러 방지용 fallback
    _repository ??= CommunityRepositoryImpl(CommunityService(dio));

    final res = await _repository!.deletePost(postId);

    debugPrint("[PostDetailViewModel] res:$res");

    return res;
  }

  Future<BaseResponse> toggleLike() async {
    final postId = ref.watch(postIdProvider);

    _repository ??= CommunityRepositoryImpl(CommunityService(dio));

    final res = await _repository!.toggleLike(postId);

    debugPrint("[PostDetailViewModel] toggleLike response: $res");

    // 성공 시 상세 데이터 다시 불러오기
    if (res.success) {
      state = AsyncValue.data(await _repository!.fetchPost(postId));
    }

    return res;
  }
}
