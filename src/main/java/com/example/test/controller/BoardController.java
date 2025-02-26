package com.example.test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Board;
import com.example.test.repository.BoardRepository;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/board")  
public class BoardController {
        @Autowired
    private BoardRepository boardRepository;

    // ✅ 모든 게시물 조회 (GET /api/board)
    @GetMapping
    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

    // ✅ 특정 게시물 조회 (GET /api/board/{bIdx})
    @GetMapping("/{bIdx}")
    public ResponseEntity<Board> getBoardBybIdx(@RequestParam("board") int bIdx) {
        Optional<Board> board = boardRepository.findById(bIdx);

        if (board.isPresent()) {
            return ResponseEntity.ok(board.get()); // 게시물이 있으면 해당 bIdx 반환
        } else {
            return ResponseEntity.notFound().build(); // 게시물이 없으면 404 Not Found 반환
        }
    }
    
    // ✅ 게시물 생성 (POST /api/board)
    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardRepository.save(board);  // 새로운 게시물 저장
    }

    // ✅ 게시물 수정 (PUT /api/board/{bIdx})
    @PutMapping("/{bIdx}")
    public ResponseEntity<Board> updateBoard(@PathVariable int bIdx, @RequestBody Board updatedBoard) {
        Optional<Board> existingBoard = boardRepository.findById(bIdx);

        if (existingBoard.isPresent()) {
            Board board = existingBoard.get();
            board.setTitle(updatedBoard.getTitle());
            board.setContent(updatedBoard.getContent());
            // 필요한 다른 필드 업데이트

            boardRepository.save(board);  // 업데이트된 게시물 저장
            return ResponseEntity.ok(board);  // 업데이트된 게시물 반환
        } else {
            return ResponseEntity.notFound().build();  // 게시물 없으면 404 반환
        }
    }

    // ✅ 게시물 삭제 (DELETE /api/board/{bIdx})
    @DeleteMapping("/{bIdx}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int bIdx) {
        Optional<Board> board = boardRepository.findById(bIdx);

        if (board.isPresent()) {
            boardRepository.delete(board.get());  // 해당 게시물 삭제
            return ResponseEntity.noContent().build();  // 204 No Content 반환
        } else {
            return ResponseEntity.notFound().build();  // 게시물 없으면 404 반환
        }
    }
}
