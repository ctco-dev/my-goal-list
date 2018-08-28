package lv.ctco.javaschool.goal.entity;

public class CommentDto {
    private String username;

    private String registeredDate;
    private String commentMessage;

    public CommentDto() {
    }

    public CommentDto(String username, String localDateTime, String msg) {
        this.username = username;
        this.registeredDate = localDateTime;
        this.commentMessage = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }
}
