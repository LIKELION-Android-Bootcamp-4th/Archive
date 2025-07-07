import 'profile.dart';

class User {
  final String userId;
  final List<String> loginRoles;
  final bool needEmailVerification;
  final bool emailVerified;
  final bool isActive;
  final bool isEmailVerified;
  final String email;
  final String nickName;
  final Profile profile;
  final String createdAt;
  final String updatedAt;

  User({
    required this.userId,
    required this.loginRoles,
    required this.needEmailVerification,
    required this.emailVerified,
    required this.isActive,
    required this.isEmailVerified,
    required this.email,
    required this.nickName,
    required this.profile,
    required this.createdAt,
    required this.updatedAt,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      userId: json['id'] ?? '',
      loginRoles: List<String>.from(json['loginRoles'] ?? []),
      needEmailVerification: json['needEmailVerification'] ?? false,
      isActive:json['emailVerified'] ?? false,
      emailVerified: json['emailVerified'] ?? false,
      isEmailVerified: json['isEmailVerified'] ?? false,
      email: json['email'] ?? '',
      nickName: json['nickName'] ?? '',
      profile: Profile.fromJson(json['profile'] ?? {}),
      createdAt: json['createdAt'] ?? '',
      updatedAt: json['updatedAt'] ?? '',
    );
  }
}
