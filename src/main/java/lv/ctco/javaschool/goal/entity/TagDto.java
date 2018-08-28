package lv.ctco.javaschool.goal.entity;

public class TagDto {
    private String tagMessage;
    private long    cnt;

    public TagDto(){
    }

    public TagDto(String tagMsg, Long cnt ){
        this.tagMessage = tagMsg;
        this.cnt = cnt;
    }

    public String getTagMessage() {
        return tagMessage;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }
}
