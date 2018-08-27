package lv.ctco.javaschool.goal.entity;


public class TagDto {
    private String tagMessage;
    private Long   cnt;

    public TagDto(){
        this("", 0L);
    }

    public TagDto(String tagMsg, Long cnt ){
        this.tagMessage = tagMsg;
        this.cnt = cnt;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

    public String getTagMessage() {
        return tagMessage;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }
}
