package com.example.test.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Board;
import com.example.test.model.User;
import com.example.test.repository.BoardRepository;
import com.example.test.repository.UserRepository;

import org.springframework.data.domain.Sort;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/board")  
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    // ✅ 게시물 조회수 증가 (PUT /api/board/{bIdx}/views)
    @PutMapping("/{bIdx}/views")
    public void increaseBoardViews(@PathVariable int bIdx) {
        boardRepository.incrementViews(bIdx);
    }

    // ✅ 게시물 인기순 조회 (GET /api/popular)
    @GetMapping("/popular")
    public List<Board> getBoardsSortedByViews() {
        return boardRepository.findAll(Sort.by(Sort.Order.desc("views")));
    }

    // ✅ 모든 게시물 '내림차순' 조회 (GET /api/board) 
    @GetMapping
    public List<Board> getBoard() {
        return boardRepository.findAll(Sort.by(Sort.Order.desc("bDatetime")));
    }

    // ✅ [내가 작성한 게시글] 본인 고유번호로 조회 (GET /board/{userIdx}/boards)
    @GetMapping("/{userIdx}/boards")
    public ResponseEntity<List<Board>> getBoardsByUserId(@PathVariable int userIdx) {
        // 해당 userIdx에 해당하는 User 객체가 존재하는지 확인
        Optional<User> user = userRepository.findById(userIdx);

        if (user.isPresent()) {
            // 해당 사용자가 작성한 모든 게시물을 조회
            List<Board> boards = boardRepository.findByUserUserIdx(userIdx);
            
            if (!boards.isEmpty()) {
                return ResponseEntity.ok(boards); // 게시물이 있으면 해당 게시물 리스트 반환
            } else {
                return ResponseEntity.noContent().build(); // 게시물이 없으면 204 No Content 반환
            }
        } else {
            return ResponseEntity.notFound().build(); // 사용자가 없으면 404 Not Found 반환
        }
    }

    // // ✅ 게시물 생성 (POST /api/board) -> JSON 방식
    // @PostMapping
    // public Board createBoard(@RequestBody Board board) {
    //     board.setbDatetime(LocalDateTime.now());
    //     return boardRepository.save(board);  
    // }

    // ✅ 게시물 생성 (POST /api/board)
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody Map<String, Object> requestData) {
        try {
            Integer userIdx = (int) requestData.get("user_idx");
            String title = (String) requestData.get("title");
            String content = (String) requestData.get("content");
    
            if (userIdx == null || title == null || content == null) {
                return ResponseEntity.badRequest().body("User, title, and content are required.");
            }

            User user = userRepository.findById(userIdx).orElseThrow(() -> new RuntimeException("User not found"));
    
            Board board = new Board();
            board.setUser(user); 
            board.setTitle(title);
            board.setContent(content);
            board.setbDatetime(LocalDateTime.now());
    
            Board savedBoard = boardRepository.save(board);
            return ResponseEntity.ok(savedBoard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
        }
    }

    public Board createBoard(@RequestBody Board board) {
        if (board.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        board.setbDatetime(LocalDateTime.now());
        return boardRepository.save(board);  
    }
    

    // ✅ 특정 게시물 bIdx로 조회 (GET /api/board/bIdx?board={bIdx})
    @GetMapping("/bIdx")
    public ResponseEntity<Board> getBoardBybIdx(@RequestParam("board") int bIdx) {
        Optional<Board> board = boardRepository.findById(bIdx);

        if (board.isPresent()) {
            return ResponseEntity.ok(board.get()); // 게시물이 있으면 해당 bIdx 반환
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    
    // ✅ 게시물 수정 (PUT /api/board/{bIdx})
    @PutMapping("/{bIdx}")
    public ResponseEntity<Board> updateBoard(@PathVariable int bIdx, @RequestBody Board updatedBoard) {
        Optional<Board> existingBoard = boardRepository.findById(bIdx); 

        if (existingBoard.isPresent()) {
            Board board = existingBoard.get();
            board.setTitle(updatedBoard.getTitle());
            board.setContent(updatedBoard.getContent());
            board.setbDatetime(LocalDateTime.now());

            boardRepository.save(board);  
            return ResponseEntity.ok(board);  
        } else {
            return ResponseEntity.notFound().build();  
        }
    } 

    // ✅ 게시물 삭제 (DELETE /api/board/{bIdx})
    @DeleteMapping("/{bIdx}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int bIdx) {
        Optional<Board> board = boardRepository.findById(bIdx);

        if (board.isPresent()) {
            boardRepository.delete(board.get());  
            return ResponseEntity.noContent().build();  
        } else {
            return ResponseEntity.notFound().build();  
        }
    }

}