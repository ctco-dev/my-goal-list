package lv.ctco.javaschool.goal.entity.dto;

import static java.lang.Math.toIntExact;

public class TagDto {
    private String tagMessage;
    private int    cnt;

    public TagDto(){
    }

    public TagDto(String tagMsg, Long cnt ){
        this.tagMessage = tagMsg;
        this.cnt = toIntExact(cnt);
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

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
