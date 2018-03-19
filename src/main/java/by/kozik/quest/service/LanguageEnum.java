package by.kozik.quest.service;

import by.kozik.quest.resource_handle.ResourceReader;

import java.util.*;

/**
 * Created by Serge on 30.12.2016.
 */
public enum LanguageEnum {
    RUSSIAN("russian", ResourceReader.readMessageResource("message.label.lang-title-ru", new Locale("ru","RU")),"ru_RU",new Locale("ru","RU")), // 2 calls for new Locale() are maid for the purpose of simplifying method getTitleNative()
    ENGLISH("english",ResourceReader.readMessageResource("message.label.lang-title-en", new Locale("en","GB")),"en_GB",new Locale("en","GB"));

    private String titleEn;
    private String titleNative;
    private String nameLocale;
    private Locale locale;
    private static Map<String,Locale> mapEn;
    private static Map<String,Locale> mapNative;
    private static Map<String,Locale> mapLocale;

    static {
        mapEn = new HashMap<>();
        mapNative = new HashMap<>();
        mapLocale = new HashMap<>();
        for (LanguageEnum item:LanguageEnum.values()) {
            mapEn.put(item.getTitleEn(),item.getLocale());
            mapNative.put(item.getTitleNative(),item.getLocale());
            mapLocale.put(item.getNameLocale(),item.getLocale());
        }
    }

    LanguageEnum(String titleEn, String titleNative, String nameLocale, Locale locale) {
        this.titleEn = titleEn;
        this.titleNative = titleNative;
        this.nameLocale = nameLocale;
        this.locale = locale;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleNative() {
        return titleNative;
    }

    public String getNameLocale() {
        return nameLocale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static List<String> listTitlesNative() {
        List<String> result = new ArrayList<>();
        for (LanguageEnum item:LanguageEnum.values()) {
            result.add(item.getTitleNative());
        }
        return result;
    }

    public static Locale localeByTitleEn(String name) {
        return mapEn.get(name);
    }

    public static Locale localeByTitleNative(String name) {
        return mapNative.get(name);
    }

    public static Locale localeByStringLocale(String name) {
        return mapLocale.get(name);
    }
}
