import 'package:flutter/foundation.dart';
import 'package:hansul_store/common/dto/base_request_dto.dart';
import '../../common/dto/base_response_dto.dart';
import '/core/network/dio_client.dart';

class CommunityService {

  CommunityService(dio);

  Future<BaseResponseDto> fetchPosts(BaseRequestDto request) async {
    try {
      final response = await dio.get('/posts', queryParameters: request.toJson());
      debugPrint("fetchPosts 응답: ${response.data}");
      return BaseResponseDto.fromJson(response.data);
    } catch (e, stack) {
      debugPrint("fetchPosts 에러: $e");
      debugPrint("StackTrace: $stack");
      return BaseResponseDto(
        success: false,
        message: '게시글 목록 조회 실패',
        data: null,
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  Future<BaseResponseDto> fetchPost(String id) async {
    try {
      final response = await dio.get('/posts/$id');
      debugPrint("fetchPost 응답: ${response.data}");
      return BaseResponseDto.fromJson(response.data);
    } catch (e, stack) {
      debugPrint("fetchPost 에러: $e");
      debugPrint("StackTrace: $stack");
      return BaseResponseDto(
        success: false,
        message: '게시글 조회 실패',
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

// TODO: 게시글 추가
Future<BaseResponseDto> addPost(BaseRequestDto dto) async {
    try {
      debugPrint("service.addPost.request: ${dto.toJson()}");
      final response = await dio.post('/posts',data: dto.toJson());
      debugPrint("addPost response: ${response}");
      return BaseResponseDto.fromJson(response.data);
    } catch (e, stack) {
      debugPrint("addPost 에러: $e");
      debugPrint("StackTrace: $stack");
      return BaseResponseDto(
        success: false,
        message: '게시글 생성 실패',
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
}

// TODO: 게시글 수정
  Future<BaseResponseDto> editPost(String id,BaseRequestDto dto) async {
    try {
      final response = await dio.patch('/posts/$id',data: dto.toJson());
      debugPrint("editPost 응답: ${response.data}");
      return BaseResponseDto.fromJson(response.data);
    } catch (e, stack) {
      debugPrint("deletePost 에러: $e");
      debugPrint("StackTrace: $stack");
      return BaseResponseDto(
        success: false,
        message: '게시글 수정 실패',
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  // TODO: 게시글 수정
  Future<BaseResponseDto> deletePost(String id) async {
    try {
      final response = await dio.delete('/posts/$id');
      debugPrint("deletePost 응답: ${response.data}");
      return BaseResponseDto.fromJson(response.data);
    } catch (e, stack) {
      debugPrint("deletePost 에러: $e");
      debugPrint("StackTrace: $stack");
      return BaseResponseDto(
        success: false,
        message: '게시글 삭제 실패',
        error: e.toString(),
        timestamp: DateTime.now().toIso8601String(),
      );
    }
  }

  // 게시글 좋아요
  Future<BaseResponseDto> toggleLike(String postId) async {
    final response = await dio.post('/posts/$postId/like-toggle');
    debugPrint("toggleLike 응답: ${response.data}");
    return BaseResponseDto.fromJson(response.data);
  }

// TODO: 댓글 추가
// Future<BaseResponseDto<CommentDto?>> addComment(CommentCreateDto dto) async {}

// TODO: 대댓글 추가
// Future<BaseResponseDto<ReplyDto?>> addReply(ReplyCreateDto dto) async {}
}
