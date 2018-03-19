package by.kozik.quest.entity;

import java.io.Serializable;

/**
 * Created by Serge on 16.01.2017.
 */
public class FormActionBean implements Serializable {
    private String href;
    private String buttonCaption;

    public FormActionBean() {
    }

    public FormActionBean(String href, String buttonCaption) {
        this.href = href;
        this.buttonCaption = buttonCaption;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getButtonCaption() {
        return buttonCaption;
    }

    public void setButtonCaption(String buttonCaption) {
        this.buttonCaption = buttonCaption;
    }
}
