package lv.ctco.javaschool.goal.entity.dto;

public class CommentDto {
    private String username;
    private Long userId;
    private String registeredDate;
    private String commentMessage;

    public CommentDto() {
    }

    public CommentDto(String username, String localDateTime, String msg, Long id) {
        this.username = username;
        this.registeredDate = localDateTime;
        this.commentMessage = msg;
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
