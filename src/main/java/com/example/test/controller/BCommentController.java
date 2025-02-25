package com.example.test.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.B_comment;
import com.example.test.model.Board;
import com.example.test.repository.BCommentRepository;
import com.example.test.repository.BoardRepository;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/b_comment")  //  로그인 API를 별도의 컨트롤러로 분리
public class BCommentController {

       @Autowired
    private BCommentRepository bCommentRepository;

    @Autowired
    private BoardRepository boardRepository;

    // ✅ 특정 게시물에 대한 댓글 조회 (GET /api/b_comment?board=1)
    @GetMapping
    public ResponseEntity<List<B_comment>> getCommentsByBoard(@RequestParam("board") int boardIdx) {
        List<B_comment> comments = bCommentRepository.findByBoard_BIdx(boardIdx);
        if (comments.isEmpty()) {
            return ResponseEntity.notFound().build();  // 댓글이 없으면 404 반환
        }
        return ResponseEntity.ok(comments);  // 댓글 목록 반환
    }

    // ✅ 댓글 추가 (POST /api/b_comment)
    @PostMapping
    public ResponseEntity<B_comment> createComment(@RequestBody B_comment bComment) {
        // 댓글이 달릴 게시물이 존재하는지 확인
        Optional<Board> board = boardRepository.findById(bComment.getBoard().getbIdx());
        
        if (!board.isPresent()) {
            return ResponseEntity.notFound().build();  // 해당 게시물이 없으면 404 반환
        }
        
        bComment.setBoard(board.get());  // 댓글에 게시물 설정
        B_comment savedComment = bCommentRepository.save(bComment);  // 댓글 저장
        return ResponseEntity.ok(savedComment);  // 저장된 댓글 반환
    }

    // ✅ 댓글 수정 (PUT /api/b_comment/{b_commentId})
    // @PutMapping("/{commentId}")
    // public ResponseEntity<B_comment> updateComment(@PathVariable int commentId, @RequestBody B_comment updatedComment) {
    //     Optional<B_comment> existingComment = bCommentRepository.findById(commentId);

    //     if (existingComment.isPresent()) {
    //         B_comment comment = existingComment.get();
    //         comment.setContent(updatedComment.getContent());  // 댓글 내용 수정
    //         comment.setAuthor(updatedComment.getAuthor());  // 작성자 수정
    //         bCommentRepository.save(comment);  // 수정된 댓글 저장
    //         return ResponseEntity.ok(comment);  // 수정된 댓글 반환
    //     } else {
    //         return ResponseEntity.notFound().build();  // 댓글이 없으면 404 반환
    //     }
    // }

    // ✅ 댓글 삭제 (DELETE /api/b_comment/{b_commentId})
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        Optional<B_comment> comment = bCommentRepository.findById(commentId);

        if (comment.isPresent()) {
            bCommentRepository.delete(comment.get());  // 해당 댓글 삭제
            return ResponseEntity.noContent().build();  // 204 No Content 반환
        } else {
            return ResponseEntity.notFound().build();  // 댓글이 없으면 404 반환
        }
    }
}
