package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.entity.Comment;
import server.entity.SheetMusic;
import server.logic.CommentLogic;
import server.logic.JwtService;
import server.repository.CommentRepository;
import server.repository.SheetMusicRepository;

import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CommentLogic commentLogic;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    SheetMusicRepository sheetMusicRepository;
//
//    @GetMapping("/sheetmusic/{sheetId}/comments")
//    public List<Comment> index(@PathVariable String sheetId){
//        int id =Integer.parseInt(sheetId);
//        // find by sheet id
//        List<Comment> comments = commentRepository.findBySheetMusic_sheetMusicId(id);
//        return comments;
//    }

    @PostMapping("/comments")
    public Comment create(@RequestHeader (name="Authorization") String token ,@RequestBody Map<String,String> body){
        // id krijgen van de user uit de token
        jwtService.decodeJwt(token);
        int userId = jwtService.getIdFromToken();

        int sheetId = Integer.parseInt(body.get("sheetId"));
        String description = body.get("description");
        double score = Double.parseDouble(body.get("score"));

        // Kleine bug hier
        // sheetmusic heeft een lijst met comments, deze comments hebben weer een sheetmusic, en die sheetmusic heeft weer een lijst met comments.
        SheetMusic sheetMusic = sheetMusicRepository.findById(sheetId).orElse(null);
        Comment comment = commentLogic.AddComment(sheetMusic,userId,description,score);

        return comment;
    }
}
