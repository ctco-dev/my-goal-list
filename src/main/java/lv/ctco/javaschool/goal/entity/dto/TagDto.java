package lv.ctco.javaschool.goal.entity.dto;

public class TagDto {
    private String tagMessage;

    public TagDto(){
    }

    public TagDto(String tagMsg) {
        this.tagMessage = tagMsg;
    }

    public String getTagMessage() {
        return tagMessage;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

}
