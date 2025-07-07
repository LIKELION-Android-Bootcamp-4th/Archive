import 'package:hansul_store/common/dto/base_request_dto.dart';

class AddPostRequestDto implements BaseRequestDto{
  final String? category;
  final String title;
  final String content;
  AddPostRequestDto({
    this.category,
    required this.title,
    required this.content
});
  @override
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    if (category != null && category!.isNotEmpty) {
      data['category'] = category;
    }

    data['title'] = title;
    data['content'] = content;
    return data;
  }

  @override
  String toString() {
    return 'AddPostRequestDto(title: $title, content: $content)';
  }
}