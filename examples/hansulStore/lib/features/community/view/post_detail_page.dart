import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/view/post_edit_page.dart';
import '../viewmodel/community_viewmodel.dart';
import '../viewmodel/post_detail_viewmodel.dart';

/// 커뮤니티 상세 페이지 - 외부에서 ProviderScope로 감싸야 함
class PostDetailPage extends ConsumerWidget {
  const PostDetailPage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final postAsync = ref.watch(postDetailViewModelProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('게시글 상세')),
      body: postAsync.when(
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (e, stack) {
          debugPrint("PostDetailPage error: $e");
          debugPrint("PostDetailPage stacktrace: $stack");
          return Center(child: Text('에러: $e'));
        },
        data: (res) {
          final post = res.data;
          if (post == null) return const Center(child: Text('글이 존재하지 않습니다.'));

          return Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [

                /// --------------------- 게시글 영역 ---------------------
                Text(post.title, style: Theme.of(context).textTheme.titleLarge),
                const SizedBox(height: 8),
                Text(post.content),
                const SizedBox(height: 24),

                /// 좋아요 / 댓글 카운트 영역
                Row(
                  children: [
                    GestureDetector(
                      onTap: () async {
                        final result = await ref.read(postDetailViewModelProvider.notifier).toggleLike();
                        if (result.success) {
                          ref.invalidate(postDetailViewModelProvider);
                          ref.invalidate(communityListViewModelProvider);
                        } else if (result.error != null) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text(result.message)),
                          );
                        }
                      },
                      child: Row(
                        children: [
                          Icon(Icons.thumb_up, size: 16, color: Colors.grey[600]),
                          const SizedBox(width: 4),
                          Text('${post.likeCount}'),
                        ],
                      ),
                    ),
                    const SizedBox(width: 16),
                    Icon(Icons.comment, size: 16, color: Colors.grey[600]),
                    const SizedBox(width: 4),
                    Text('${post.commentCount}'),
                  ],
                ),

                const SizedBox(height: 24),

                /// --------------------- 댓글 영역 ---------------------
                const Divider(thickness: 1),
                Text('댓글', style: Theme.of(context).textTheme.titleMedium),
                const SizedBox(height: 12),
                const Text('※ 댓글 기능은 아직 구현되지 않았습니다.'), // placeholder

                const Spacer(),

                /// --------------------- 수정/삭제 버튼 ---------------------
                Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit),
                      onPressed: () async {
                        final updated = await Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) => PostEditPage(post: post),
                          ),
                        );

                        if (updated == true) {
                          ref.invalidate(postDetailViewModelProvider);
                          ref.invalidate(communityListViewModelProvider);
                        }
                      },
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete),
                      onPressed: () async {
                        final confirmed = await showDialog<bool>(
                          context: context,
                          builder: (ctx) => AlertDialog(
                            title: const Text('삭제 확인'),
                            content: const Text('이 게시글을 삭제하시겠습니까?'),
                            actions: [
                              TextButton(
                                onPressed: () => Navigator.pop(ctx, false),
                                child: const Text('취소'),
                              ),
                              TextButton(
                                onPressed: () => Navigator.pop(ctx, true),
                                child: const Text('삭제'),
                              ),
                            ],
                          ),
                        );

                        if (confirmed == true) {
                          final deleted = await ref.read(postDetailViewModelProvider.notifier).deletePost();

                          if (deleted.success && context.mounted) {
                            ref.invalidate(communityListViewModelProvider);
                            Navigator.pop(context);
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(content: Text('삭제되었습니다.')),
                            );
                          } else if (deleted.error != null) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(content: Text('${deleted.message}')),
                            );
                          }
                        }
                      },
                    ),
                  ],
                )
              ],
            ),
          );
        },
      ),
    );
  }
}
