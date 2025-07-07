import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../viewmodel/login_viewmodel.dart';

class LoginPage extends ConsumerStatefulWidget {
  const LoginPage({super.key});

  @override
  ConsumerState<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends ConsumerState<LoginPage> {
  final _emailController = TextEditingController(text: 'buyer@mtz.com');
  final _passwordController = TextEditingController(text: 'qwer1234');
  String? _returnTo;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    _returnTo ??= ModalRoute.of(context)?.settings.arguments as String? ?? '/';
  }

  void _handleLogin() {
    final email = _emailController.text.trim();
    final password = _passwordController.text.trim();

    ref.read(loginViewModelProvider.notifier).login(email, password);
  }

  @override
  Widget build(BuildContext context) {
    // ref.listen은 무조건 build 내부에서 호출해야 함
    ref.listen(loginViewModelProvider, (previous, next) async {
      next.whenOrNull(
        data: (user) async {
          debugPrint('로그인 성공: $user');
          if (!mounted) return;
          Navigator.pushReplacementNamed(context, _returnTo!);

          // Navigator.pushReplacementNamed(context, '/community');
        },
        error: (e, _) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('로그인 실패: ${e.toString()}')),
          );
        },
      );
    });

    final state = ref.watch(loginViewModelProvider);
    final isLoading = state is AsyncLoading;

    return Scaffold(
      resizeToAvoidBottomInset: true,
      appBar: AppBar(title: const Text('로그인')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              TextField(
                controller: _emailController,
                decoration: const InputDecoration(labelText: '이메일'),
              ),
              const SizedBox(height: 8),
              TextField(
                controller: _passwordController,
                obscureText: true,
                decoration: const InputDecoration(labelText: '비밀번호'),
                onSubmitted: (_) => _handleLogin(),
              ),
              const SizedBox(height: 20),
              isLoading
                  ? const Center(child: CircularProgressIndicator())
                  : ElevatedButton(
                onPressed: _handleLogin,
                child: const Text('로그인'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
