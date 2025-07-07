import 'package:hansul_store/common/model/base_response.dart';

abstract class BaseRequestDto{
  Map<String, dynamic> toJson() {
    return {};
  }
  @override
  String toString() {
    // TODO: implement toString
    return super.toString();
  }
}