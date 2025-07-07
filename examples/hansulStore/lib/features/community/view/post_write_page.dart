import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/viewmodel/community_viewmodel.dart';
import '../model/dto/add_post_request.dart';
import '../viewmodel/post_write_viewmodel.dart';

class PostWritePage extends ConsumerStatefulWidget {
  const PostWritePage({super.key});

  @override
  ConsumerState<PostWritePage> createState() => _PostWritePageState();
}

class _PostWritePageState extends ConsumerState<PostWritePage> {
  final _titleController = TextEditingController();
  final _contentController = TextEditingController();

  @override
  void dispose() {
    _titleController.dispose();
    _contentController.dispose();
    super.dispose();
  }

  void _submit() async {

    final viewModel = ref.read(postWriteViewModelProvider.notifier);
    await viewModel.createPost(
      AddPostRequest(title: _titleController.text, content: _contentController.text)
    );

    final result = ref.read(postWriteViewModelProvider);

    if (result.hasValue && result.value!.success) {
      if (context.mounted) {

        // 커뮤니티 게시글 목록 갱신
        ref.invalidate(communityListViewModelProvider);

        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('게시글이 등록되었습니다.')),
        );
        Navigator.pop(context);
      }
    } else {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('등록 실패: ${result.error ?? result.value?.message ?? '알 수 없는 오류'}')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final asyncState = ref.watch(postWriteViewModelProvider);
    final isLoading = asyncState.isLoading;

    return Scaffold(
      appBar: AppBar(title: const Text('게시글 작성')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _titleController,
              decoration: const InputDecoration(labelText: '제목'),
            ),
            const SizedBox(height: 12),
            TextField(
              controller: _contentController,
              decoration: const InputDecoration(labelText: '내용'),
              maxLines: 8,
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: isLoading ? null : _submit,
              child: isLoading
                  ? const CircularProgressIndicator()
                  : const Text('등록하기'),
            ),
          ],
        ),
      ),
    );
  }
}
