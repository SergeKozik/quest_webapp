package by.kozik.quest.controller.validation;

import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.LanguageEnum;
import by.kozik.quest.service.QuestResultTypeEnum;
import by.kozik.quest.utility.UtilString;

import java.util.Locale;

/**
 * Created by Serge on 08.01.2017.
 */
public class NewQuestValidator extends AbstractValidator {
    private String language;
    private String category;
    private String title;
    private String description;
    private QuestResultTypeEnum type;
    private QuestBean readyQuest = null;

    public NewQuestValidator(String language, String category, String title, String description, QuestResultTypeEnum type) {
        super();
        this.language = language;
        this.category = category;
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public NewQuestValidator(QuestBean readyQuest) {
        this.readyQuest = readyQuest;
    }

    @Override
    public boolean validate(Locale userLocale) {
        if (readyQuest!=null) { // validates existence of QuestBean before saving new quest when constructor NewQuestValidator(QuestBean readyQuest) was called
            if (UtilString.isNullEmpty(readyQuest.getTitle())) {
                return false;
            } else {
                return true;
            }
        }
       flagSuccess = true;
        Locale nativeLocale = LanguageEnum.localeByTitleNative(language);
        if (nativeLocale==null) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.quest-language",userLocale));
        }
        if (UtilString.isNullEmpty(category)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.quest-category",userLocale));
        }
        if (UtilString.isNullEmpty(title)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.quest-title",userLocale));
        }
        if (type==null) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.quest-type",userLocale));
        }
        return flagSuccess;
    }
}
