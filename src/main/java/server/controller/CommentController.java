package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.entity.Comment;
import server.repository.CommentRepository;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/sheetmusic/{sheetId}/comments")
    public List<Comment> index(@PathVariable String sheetId){
        int id =Integer.parseInt(sheetId);
        // find by sheet id
        return null;
    }

    @PostMapping("/comments")
    public Comment create(@RequestBody Map<String,String> body){
        int sheetId = Integer.parseInt(body.get("sheetId"));
        int userId = Integer.parseInt(body.get("userId"));
        String title = body.get("title");
        String description = body.get("description");
        int score = Integer.parseInt(body.get("score"));
        Comment comment = new Comment(sheetId,userId,title,description,score);
        return commentRepository.save(comment);
    }
}
