import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/view/post_detail_page.dart';
import 'package:hansul_store/features/community/widget/post_card_widget.dart';
import '../viewmodel/community_viewmodel.dart';
import '../viewmodel/post_detail_viewmodel.dart';

class CommunityPage extends ConsumerWidget {
  const CommunityPage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final state = ref.watch(communityListViewModelProvider);
    final viewModel = ref.read(communityListViewModelProvider.notifier);

    return Scaffold(
      appBar: AppBar(
        title: const Text('커뮤니티'),
      ),
      body: Builder(
        builder: (context) {
          if (state.isLoading) {
            return const Center(child: CircularProgressIndicator());
          } else if (state.error != null) {
            return Center(child: Text('에러 발생: ${state.error}'));
          } else if (state.posts.isEmpty) {
            return const Center(child: Text('게시글이 없습니다.'));
          }

          return ListView.builder(
            itemCount: state.posts.length,
            itemBuilder: (context, index) {
              final post = state.posts[index];

              return PostCard(
                post: post,
                onTap: () async {
                  await Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) => ProviderScope(
                        overrides: [postIdProvider.overrideWithValue(post.id)],
                        child: const PostDetailPage(),
                      ),
                    ),
                  );
                  // 게시글 상세 이후 돌아오면 목록 갱신
                  ref.invalidate(communityListViewModelProvider);
                },
                onDelete: () async {
                  final confirmed = await showDialog<bool>(
                    context: context,
                    builder: (context) => AlertDialog(
                      title: const Text('삭제 확인'),
                      content: const Text('정말로 삭제하시겠습니까?'),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.pop(context, false),
                          child: const Text('취소'),
                        ),
                        TextButton(
                          onPressed: () => Navigator.pop(context, true),
                          child: const Text('삭제'),
                        ),
                      ],
                    ),
                  );

                  if (confirmed == true) {
                    await viewModel.deletePost(post.id);
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('삭제되었습니다.')),
                    );
                  }
                },
                onLikePressed: () async {
                  await viewModel.toggleLike(post.id);

                  if (state.error != null) {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text(state.error!)),
                    );
                  }
                },
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.pushNamed(context, '/community/post-write');
        },
        child: const Icon(Icons.add),
        tooltip: '글 작성',
      ),
    );
  }
}
