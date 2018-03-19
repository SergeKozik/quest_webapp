package by.kozik.quest.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serge on 21.01.2017.
 */
public enum AnswerTypeEnum {
    USER_TEXT,
    CASE_TEXT,
    CASE_MARK;

    private static Map<String,AnswerTypeEnum> map;

    static {
        map = new HashMap<>();
        for (AnswerTypeEnum item:AnswerTypeEnum.values()) {
            map.put(item.name().toLowerCase(),item);
        }
    }

    public static AnswerTypeEnum getByNameLower(String title) {
        return map.get(title);
    }
}
