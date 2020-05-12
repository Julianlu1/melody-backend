package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.entity.Comment;
import server.entity.SheetMusic;
import server.entity.User;
import server.repository.CommentRepository;

@Component
public class CommentLogic {

    @Autowired
    CommentRepository commentRepository;

    public Comment AddComment(SheetMusic sheetMusic, int userId, String description, double score){
        // Comment maken
        Comment comment = new Comment(sheetMusic,userId,description,score);

        // Comment opslaan in database
        commentRepository.save(comment);

        // Return comment
        return comment;
    }
}
