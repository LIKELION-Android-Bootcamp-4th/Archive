import 'package:flutter/material.dart';

import '../model/entity/post.dart';

class PostCard extends StatelessWidget {
  final Post post;
  final Future<void> Function() onDelete;
  final Future<void> Function() onTap;
  final Future<void> Function() onLikePressed;

  const PostCard({
    super.key,
    required this.post,
    required this.onDelete,
    required this.onTap,
    required this.onLikePressed,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(12),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // 사용자 정보
              if (post.user != null)
                Row(
                  children: [
                    const CircleAvatar(child: Icon(Icons.person)),
                    const SizedBox(width: 8),
                    Text(
                      post.user!.nickName,
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                  ],
                ),

              const SizedBox(height: 12),

              // 게시글 제목
              Text(
                post.title,
                style: Theme.of(context).textTheme.titleMedium,
              ),

              const SizedBox(height: 8),

              // 게시글 내용
              Text(
                post.content,
                maxLines: 3,
                overflow: TextOverflow.ellipsis,
              ),

              const SizedBox(height: 12),

              // 좋아요 & 댓글 카운트
              Row(
                children: [
                  GestureDetector(
                    onTap: onLikePressed,
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
            ],
          ),
        ),
      ),
    );
  }
}
