import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/view/community_page.dart';
import 'package:hansul_store/features/community/view/post_write_page.dart';
import 'common/model/services/secure_storage_service.dart';
import 'features/community/view/post_edit_page.dart';
import 'features/login/view/login_page.dart';

final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await dotenv.load(fileName: '.env'); // 환경변수 로드
  runApp(const ProviderScope(child: MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Hansul Store',
      theme: ThemeData(primarySwatch: Colors.blue),
      initialRoute: '/',
      routes: {
        '/': (context) => const HomePage(),
        '/login': (context) => const LoginPage(),
        '/community': (context) => const CommunityPage(),
        '/community/post-write': (context) => const PostWritePage(),
        // '/community/post-edit': (context) => const PostEditPage(),
      },
    );
  }
}

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('홈')),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            ElevatedButton(
              child: const Text('로그인 하기'),
              onPressed: () {
                Navigator.pushNamed(context, '/login');
              },
            ),
            ElevatedButton(
              child: const Text('로그아웃 하기'),
              onPressed: () async {
                await SecureStorageService.clear();
              },
            ),
            ElevatedButton(
              child: const Text('커뮤니티 바로가기'),
              onPressed: () {
                Navigator.pushNamed(context, '/community');
              },
            ),
          ],
        )
      ),
    );
  }
}