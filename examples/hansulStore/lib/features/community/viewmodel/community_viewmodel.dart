import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/provider/community_repository_provider.dart';
import '../model/dto/search_post_request.dart';
import '../model/entity/post.dart';
import '../repository/community_repository.dart'; // 데이터 요청 처리 가정

final communityListViewModelProvider =
StateNotifierProvider<CommunityListViewModel, CommunityListState>((ref) {
  final repo = ref.watch(communityRepositoryProvider);
  return CommunityListViewModel(repo);
});

class CommunityListState {
  final bool isLoading;
  final List<Post> posts;
  final String? error;

  CommunityListState({
    this.isLoading = false,
    this.posts = const[],
    this.error,
});
  CommunityListState copyWith({
    bool? isLoading,
    List<Post>? posts,
    String? error,
  }) {
    return CommunityListState(
      isLoading: isLoading ?? this.isLoading,
      posts: posts ?? this.posts,
      error: error ?? this.error,
    );
  }
}

class CommunityListViewModel extends StateNotifier<CommunityListState> {
  final CommunityRepository _repository;

  CommunityListViewModel(this._repository) : super(CommunityListState()) {
    fetchPosts();
  }

  Future<void> fetchPosts() async {
    state = state.copyWith(isLoading: true);
    final res = await _repository.fetchPosts(SearchPostRequest(category: '', search: ''));
    if (res.success && res.data != null) {
      state = state.copyWith(
        isLoading: false,
        posts: res.data!.items,
      );
    } else {
      state = state.copyWith(
        isLoading: false,
        error: res.error ?? res.message,
      );
    }
  }

  Future<void> deletePost(String id) async {
    final res = await _repository.deletePost(id);
    if (res.success) {
      final updated = state.posts.where((p) => p.id != id).toList();
      state = state.copyWith(posts: updated);
    } else {
      state = state.copyWith(
        isLoading: false,
        error: res.error ?? res.message,
      );
    }
  }

  Future<void> toggleLike(String id) async {
    final res = await _repository.toggleLike(id);
    if (res.success) {
      // 갱신된 게시글 정보 다시 불러오기
      final postRes = await _repository.fetchPost(id);

      if (postRes.success && postRes.data != null) {
        final updatedPosts = state.posts.map((post) {
          return post.id == id ? postRes.data! : post;
        }).toList();

        state = state.copyWith(posts: updatedPosts);
      }
    } else {
      state = state.copyWith(
        isLoading: false,
        error: res.error ?? res.message,
      );
    }
  }
}