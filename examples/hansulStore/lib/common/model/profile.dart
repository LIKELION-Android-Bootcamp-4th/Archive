import 'dart:ui';

import 'package:hansul_store/common/model/image.dart';


class Profile {
  final String name;
  final String? birthDate;
  final String? profileImage;

  Profile({
    required this.name,
    required this.birthDate,
    required this.profileImage,
  });

  factory Profile.fromJson(Map<String, dynamic> json) {
    final dynamic imageJson = json['profileImage'];

    Image? parsedImage;

    if (imageJson != null) {
      if (imageJson is String) {
        parsedImage = Image(url: imageJson);
      } else if (imageJson is Map<String, dynamic>) {
        parsedImage = Image.fromJson(imageJson);
      } else {
        parsedImage = null; // 예외 케이스
      }
    }

    return Profile(
      name: json['name'] ?? '',
      birthDate: json['birthDate'],
      profileImage: parsedImage?.url,
    );
  }
}
