package lv.ctco.javaschool.goal.entity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String tagMessage;

    public Tag() {
    }

    public Tag(String tagMsg) {
        this.tagMessage = tagMsg;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

    public String getTagMessage() {
        return tagMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        return tagMessage != null ? tagMessage.equals(tag.tagMessage) : tag.tagMessage == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tagMessage != null ? tagMessage.hashCode() : 0);
        return result;
    }
}
