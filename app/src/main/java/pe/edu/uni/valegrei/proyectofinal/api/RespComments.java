package pe.edu.uni.valegrei.proyectofinal.api;

import java.util.List;

import pe.edu.uni.valegrei.proyectofinal.Comment;

public class RespComments {
    private String postId;
    private List<Comment> comments;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
