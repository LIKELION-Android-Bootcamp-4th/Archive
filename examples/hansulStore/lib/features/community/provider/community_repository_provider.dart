import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/repository/community_repository.dart';

import '../../../common/model/network/dio_client.dart';
import '../../../common/model/services/community_service.dart';

final communityRepositoryProvider = Provider<CommunityRepository>((ref) {
  return CommunityRepositoryImpl(CommunityService(dio)); // 의존성 주입 가능
});