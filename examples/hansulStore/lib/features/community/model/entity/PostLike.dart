
class PostLike {
  final String id;
  final bool isLiked;
  final String action;

  PostLike({
    required this.id,
    required this.isLiked,
    required this.action
  });

  factory PostLike.fromJson(Map<String, dynamic> json) {
    return PostLike(
        id: json['postId'],
        isLiked: json['isLiked'],
        action: json['action']
    );
  }
}