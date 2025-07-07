import '../../../../common/model/dto/base_request_dto.dart';

class EditPostRequest implements BaseRequestDto{
  final String? category;
  final String title;
  final String content;
  EditPostRequest({
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
}