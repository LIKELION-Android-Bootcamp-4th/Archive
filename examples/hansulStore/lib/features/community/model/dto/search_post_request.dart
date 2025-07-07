import '../../../../common/model/dto/base_request_dto.dart';
class SearchPostRequest extends BaseRequestDto{
  final String? category;
  final String? search;

  SearchPostRequest({
    required this.category,
    required this.search
  });

  @override
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    if (category != null && category!.isNotEmpty) {
    data['category'] = category;
    }

    if (search != null && search!.isNotEmpty) {
    data['search'] = search;
    }
    return data;
  }
}