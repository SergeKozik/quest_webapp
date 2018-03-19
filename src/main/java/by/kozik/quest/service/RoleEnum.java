package by.kozik.quest.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roldo on 24.11.16.
 */
public enum RoleEnum {
    ADMIN(4,"message.label.role-type-admin"),
    AUTHOR(3,"message.label.role-type-author"),
    USER(2,"message.label.role-type-user"),
    ANONYMOUS(1,"message.label.role-type-anon");

    private int priority;
    private String messageKey;
    private static Map<Integer,RoleEnum> map;

    static {
        map = new HashMap<Integer, RoleEnum>();
        for (RoleEnum item:RoleEnum.values()) {
            map.put(item.priority,item);
        }
    }

    RoleEnum(int priority, String messageKey) {
        this.priority = priority;
        this.messageKey = messageKey;
    }

    public int getPriority() {
        return priority;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public static RoleEnum findByPriority(int ii) {
        return map.get(ii);
    }
}
