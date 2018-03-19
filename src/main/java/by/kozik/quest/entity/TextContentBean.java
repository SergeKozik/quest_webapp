package by.kozik.quest.entity;

import java.io.Serializable;

/**
 * Created by Serge on 06.01.2017.
 */
public class TextContentBean implements Serializable{
    String name;
    String description;

    public TextContentBean() {
    }

    public TextContentBean(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
