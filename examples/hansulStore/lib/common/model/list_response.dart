import 'package:hansul_store/common/model/pagenation.dart';

class ListResponse<T> {
  final List<T> items;
  final Pagination pagination;

  ListResponse({
    required this.items,
    required this.pagination
  });

  factory ListResponse.fromJson(
      Map<String, dynamic> json,
      T Function(Map<String, dynamic>) fromJsonT,
      ) {
    return ListResponse<T>(
      items: (json['items'] as List)
          .map((item) => fromJsonT(item))
          .toList(),
      pagination: Pagination.fromJson(json['pagination']),
    );
  }

  ListResponse toEntity() {
    return ListResponse(
      items: items,
      pagination: pagination
    );
  }
}