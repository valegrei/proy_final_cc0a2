package pe.edu.uni.valegrei.proyectofinal;

import java.util.Date;

public class Comment implements Comparable<Comment> {
    private String postId;
    private String commentEmail;
    private Date commentDate;
    private String commentBody;

    public Comment(String postId, String commentEmail, String commentBody) {
        this.postId = postId;
        this.commentEmail = commentEmail;
        this.commentBody = commentBody;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    @Override
    public int compareTo(Comment o) {
        if (getCommentDate() == null || o.getCommentDate() == null)
            return 0;
        return -getCommentDate().compareTo(o.getCommentDate());
    }
}
