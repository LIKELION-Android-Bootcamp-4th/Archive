// import '../../../common/dto/image_dto.dart';
// import '../../../common/dto/user_dto.dart';
//
// class ProductDto {
//   final String name;
//   final String description;
//   final int price;
//   final String stockType;
//   final int stock;
//   final ProductAttributes? attributes;
//   final Discount? discount;
//   final ReviewStats reviewStats;
//   final bool isFavorite;
//
//   ProductDto({
//     required this.id,
//     required this.companyId,
//     required this.category,
//     required this.title,
//     required this.description,
//     this.images,
//     required this.commentCount,
//     required this.likeCount,
//     required this.isDeleted,
//     required this.createdBy,
//     required this.updatedBy,
//     required this.createdAt,
//     required this.updatedAt,
//     required this.items,
//     this.user,
//   });
//
//   factory ProductDto.fromJson(Map<String, dynamic> json) {
//     return ProductDto(
//       id: json['id'],
//       companyId: json['companyId'],
//       category: json['category'],
//       title: json['title'],
//       description: json['description'],
//       images: json['images'] != null ? ImageDto.fromJson(json['images']) : null,
//       commentCount: json['commentCount'] ?? 0,
//       likeCount: json['likeCount'] ?? 0,
//       isDeleted: json['isDeleted'] ?? false,
//       createdBy: json['createdBy'],
//       updatedBy: json['updatedBy'],
//       createdAt: json['createdAt'],
//       updatedAt: json['updatedAt'],
//       items: (json['items'] as List<dynamic>?)
//           ?.map((e) => MediaItemDto.fromJson(e))
//           .toList() ??
//           [],
//       user: json['user'] != null ? UserDto.fromJson(json['user']) : null,
//     );
//   }
//
//   Product toEntity() => Product(
//     id: id,
//     companyId: companyId,
//     category: category,
//     title: title,
//     content: content,
//     images: images?.toEntity(),
//     commentCount: commentCount,
//     likeCount: likeCount,
//     isDeleted: isDeleted,
//     createdBy: createdBy,
//     updatedBy: updatedBy,
//     createdAt: DateTime.parse(createdAt),
//     updatedAt: DateTime.parse(updatedAt),
//     items: items.map((e) => e.toEntity()).toList(),
//     user: user?.toEntity(),
//   );
// }
