package by.kozik.quest.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serge on 06.01.2017.
 */
public enum QuestResultTypeEnum {
    VOTING("voting","message.label.quest-type-voting", "message.text.quest-voting-description"),
    TEST_MARK("test","message.label.quest-type-test","message.text.quest-test-description"),
    QUESTIONNAIRE("questionnaire","message.label.quest-type-questionnaire","message.text.quest-test-questionnaire");

    private static Map<String,QuestResultTypeEnum> map;
    private String nameEn;
    private String nameNativeCode;
    private String descriptionCode;


    static {
        map = new HashMap<>();
        for (QuestResultTypeEnum item:QuestResultTypeEnum.values()) {
            map.put(item.getNameEn(),item);
        }
    }

    QuestResultTypeEnum(String nameEn, String nameNativeCode, String descriptionCode) {
        this.nameEn = nameEn;
        this.nameNativeCode = nameNativeCode;
        this.descriptionCode = descriptionCode;
    }
    public String getNameEn() {
        return nameEn;
    }

    public String getNameNativeCode() {
        return nameNativeCode;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public static QuestResultTypeEnum getTypeByName(String testName) {
        return map.get(testName);
    }

}
