package lv.ctco.javaschool.goal.entity;


import lv.ctco.javaschool.auth.entity.domain.User;


import java.time.LocalDateTime;

public class CommentDto {
    private String username;
    private LocalDateTime registeredDate;
    private String commentMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }
}
