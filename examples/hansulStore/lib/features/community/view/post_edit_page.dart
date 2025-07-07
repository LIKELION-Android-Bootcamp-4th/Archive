import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hansul_store/features/community/dto/add_post_request_dto.dart';
import 'package:hansul_store/features/community/viewmodel/post_detail_viewmodel.dart';
import '../model/post.dart';
import '../viewmodel/post_edit_viewmodel.dart';
import '../viewmodel/community_viewmodel.dart';
import '../viewmodel/post_edit_viewmodel.dart';

class PostEditPage extends ConsumerStatefulWidget {
  final Post post;

  const PostEditPage({super.key, required this.post});

  @override
  ConsumerState<PostEditPage> createState() => _PostEditPageState();
}

class _PostEditPageState extends ConsumerState<PostEditPage> {
  late final TextEditingController _titleController;
  late final TextEditingController _contentController;

  @override
  void initState() {
    super.initState();
    _titleController = TextEditingController(text: widget.post.title);
    _contentController = TextEditingController(text: widget.post.content);
  }

  @override
  void dispose() {
    _titleController.dispose();
    _contentController.dispose();
    super.dispose();
  }

  Future<void> _onSubmit() async {
    final title = _titleController.text.trim();
    final content = _contentController.text.trim();

    if (title.isEmpty || content.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('제목과 내용을 모두 입력해주세요.')),
      );
      return;
    }

    final viewModel = ref.read(postEditViewModelProvider);
    final success = await viewModel.editPost(widget.post.id,
        AddPostRequestDto(title: title, content: content));

    if (success && mounted) {

      Navigator.pop(context , true);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('게시글이 수정되었습니다.')),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('게시글 수정에 실패했습니다.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: const Text('게시글 수정')),
        body: Padding(
        padding: const EdgeInsets.all(16),
    child: Column(
    children: [
    TextField(
    controller: _titleController,
    decoration: const InputDecoration(labelText: '제목'),
    ),
    const SizedBox(height: 12),
    TextField(
    controller: _contentController,
    maxLines: 8,
    decoration: const InputDecoration(labelText: '내용'),
    ),
      const SizedBox(height: 20),
      ElevatedButton(
        onPressed: _onSubmit,
        child: const Text('수정 완료'),
      ),
    ],
    ),
        ),
    );
  }
}
