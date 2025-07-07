import 'package:flutter/widgets.dart';
import 'package:hansul_store/common/dto/base_request_dto.dart';
import 'package:hansul_store/common/model/list_response.dart';
import 'package:hansul_store/core/services/community_service.dart';
import 'package:hansul_store/features/community/model/PostLike.dart';
import 'package:hansul_store/features/community/model/post.dart';
import '../../../common/model/base_response.dart';


abstract class CommunityRepository {
  Future<BaseResponse<ListResponse<Post>?>> fetchPosts(BaseRequestDto request);
  Future<BaseResponse<Post?>> fetchPost(String postId);
  Future<BaseResponse<Post?>> addPost(BaseRequestDto request);
  Future<BaseResponse<Post?>> editPost(String postId,BaseRequestDto request);
  Future<BaseResponse> deletePost(String postId);
  Future<BaseResponse> toggleLike(String postId);
}

class CommunityRepositoryImpl implements CommunityRepository {
  final CommunityService _communityService;

  CommunityRepositoryImpl(this._communityService);

  @override
  Future<BaseResponse<ListResponse<Post>?>> fetchPosts(BaseRequestDto request) async {
    try {
      final responseDto = await _communityService.fetchPosts(request);
      debugPrint("fetchPosts response: $responseDto");

      return BaseResponse<ListResponse<Post>>.fromDto(
        responseDto,
            (json) => ListResponse<Post>.fromJson(
          json,
              (itemJson) => Post.fromJson(itemJson),
        ),
      );

    } catch (e, stack) {
      debugPrint("fetchPosts error: $e");
      debugPrint("StackTrace: $stack");

      return BaseResponse<ListResponse<Post>>(
        success: false,
        message: "게시글 목록 조회 중 오류가 발생했습니다.",
        data: null,
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  @override
  Future<BaseResponse<Post>> fetchPost(String postId) async {
    try {
      final responseDto = await _communityService.fetchPost(postId);
      debugPrint("fetchPost($postId) response: $responseDto");

      return BaseResponse<Post>.fromDto(
        responseDto,
            (json) => Post.fromJson(json),
      );
    } catch (e, stack) {
      debugPrint("fetchPost($postId) error: $e");
      debugPrint("StackTrace: $stack");

      return BaseResponse<Post>(
        success: false,
        message: "게시글 조회 중 오류가 발생했습니다.",
        data: null,
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  @override
  Future<BaseResponse<Post?>> addPost(BaseRequestDto request) async {
    try {
      debugPrint("repository.addPost.request: ${request.toString()}");
      final responseDto = await _communityService.addPost(request);

      return BaseResponse<Post>.fromDto(
        responseDto,
            (json) => Post.fromJson(json),
      );
    } catch (e, stack ) {
      debugPrint("createPost error: $e");
      return BaseResponse<Post>(
        success: false,
        message: "게시글 작성 중 오류가 발생했습니다.",
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  @override
  Future<BaseResponse<Post?>> editPost(String postId,BaseRequestDto request) async {
    try {
      final responseDto = await _communityService.editPost(postId,request);

      return BaseResponse<Post>.fromDto(
        responseDto,
            (json) => Post.fromJson(json),
      );
    } catch (e, stack ) {
      debugPrint("editPost error: $e");
      return BaseResponse<Post>(
        success: false,
        message: "게시글 수정 중 오류가 발생했습니다.",
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  @override
  Future<BaseResponse> deletePost(String postId) async {
    try {
      final responseDto = await _communityService.deletePost(postId);
      return BaseResponse<Post>.fromDto(
        responseDto,
            (json) => Post.fromJson(json),
      );
    } catch (e, stack ) {
      debugPrint("deletePost error: $e");
      return BaseResponse<Post>(
        success: false,
        message: "게시글 삭제 중 오류가 발생했습니다.",
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  @override
  Future<BaseResponse<PostLike>> toggleLike(String postId) async {
    try {
      final responseDto = await _communityService.toggleLike(postId);
      debugPrint("repository.toggleLike.response: ${responseDto.toString()}");
      return BaseResponse<PostLike>.fromDto(
        responseDto,
            (json) => PostLike.fromJson(json),
      );
    } catch (e, stack ) {
      return BaseResponse<PostLike>(
        success: false,
        message: "게시글 좋아요 처리 중 오류가 발생했습니다.",
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }
}
