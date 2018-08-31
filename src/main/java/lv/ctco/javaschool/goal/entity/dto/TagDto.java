package lv.ctco.javaschool.goal.entity.dto;

import static java.lang.Math.toIntExact;

public class TagDto {
    private String tagMessage;
    private int cnt;

    public TagDto(){
    }

    public TagDto(String tagMsg, Long cnt ){
        this.tagMessage = tagMsg;
        this.cnt = toIntExact(cnt);
    }

    public TagDto(String tagMsg) {
        this(tagMsg, 0);
    }

    public TagDto(String tagMsg, int cnt ){
        this.tagMessage = tagMsg;
        this.cnt = cnt;
    }

    public String getTagMessage() {
        return tagMessage;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
