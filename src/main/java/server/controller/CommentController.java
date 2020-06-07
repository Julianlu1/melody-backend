package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.entity.Comment;
import server.entity.SheetMusic;
import server.entity.User;
import server.logic.CommentLogic;
import server.logic.JwtService;
import server.logic.SheetMusicLogic;
import server.logic.UserLogic;
import server.repository.CommentRepository;
import server.repository.SheetMusicRepository;
import server.repository.UserRepository;

import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CommentLogic commentLogic;

    @Autowired
    private SheetMusicLogic sheetMusicLogic;

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SheetMusicRepository sheetMusicRepository;


//    @GetMapping("/sheetmusic/{sheetId}/comments")
//    public List<Comment> index(@PathVariable String sheetId){
//        int id =Integer.parseInt(sheetId);
//        // find by sheet id
//        List<Comment> comments = commentRepository.findBySheetMusic_sheetMusicId(id);
//        return comments;
//    }

    @PostMapping("/comments")
    public ResponseEntity create(@RequestHeader (name="Authorization") String token , @RequestBody Map<String,String> body){
        // id krijgen van de user uit de token
        try {
            jwtService.decodeJwt(token);
            int userId = jwtService.getIdFromToken();

            int sheetId = Integer.parseInt(body.get("sheetId"));
            String description = body.get("description");
            double score = Double.parseDouble(body.get("score"));

            // Kleine bug hier
            // sheetmusic heeft een lijst met comments, deze comments hebben weer een sheetmusic, en die sheetmusic heeft weer een lijst met comments.
            SheetMusic sheetMusic = sheetMusicLogic.findById(sheetId);
            User user = userLogic.findById(userId);

            Comment comment = commentLogic.AddComment(sheetMusic, user, description, score);

            return ResponseEntity.ok(comment);
        }catch(Exception e){
            return new ResponseEntity("Er ging iets fout", HttpStatus.BAD_REQUEST);
        }
    }
}
