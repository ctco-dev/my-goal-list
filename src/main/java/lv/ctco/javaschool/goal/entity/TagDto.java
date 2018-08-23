package lv.ctco.javaschool.goal.entity;


public class TagDto {

    public TagDto(){
        this("");
    }

    public TagDto(String newTag){
        this.setTagMessage(newTag);
    }

    private String tagMessage;

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

    public String getTagMessage() {
        return tagMessage;
    }
}
