import '../../../common/model/user.dart';

class Post {
  final String id;
  final String title;
  final String content;
  final String? category;
  final int commentCount;
  final int likeCount;
  final String createdAt;
  final User? user;

  Post({
    required this.id,
    required this.title,
    required this.content,
    this.category,
    required this.commentCount,
    required this.likeCount,
    required this.createdAt,
    this.user,
  });

  factory Post.fromJson(Map<String, dynamic> json) {
    return Post(
      id: json['id'],
      title: json['title'],
      content: json['content'],
      category: json['category'],
      commentCount: json['commentCount'],
      likeCount: json['likeCount'],
      createdAt: json['createdAt'],
      // companyId: json['companyId'],
      user: json['user'] != null ? User.fromJson(json['user']) : json['userId'] != null ? User.fromJson(json['userId']) : null,
    );
  }
}