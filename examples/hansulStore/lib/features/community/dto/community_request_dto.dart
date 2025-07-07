import 'package:hansul_store/common/dto/base_request_dto.dart';

class CommunityRequestDto extends BaseRequestDto{
  final String? category;
  final String? search;

  CommunityRequestDto({
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