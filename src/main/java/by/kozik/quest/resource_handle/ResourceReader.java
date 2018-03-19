package by.kozik.quest.resource_handle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Serge on 24.12.2016.
 */
public class ResourceReader {
    private final static String MSG_BUNDL_URL = "by.kozik.quest.i18n.messages";
    private final static String EXP_BUNDL_URL = "by.kozik.quest.i18n.form_regexp";
    private static Logger logger = LogManager.getLogger();

    public static String readMessageResource(String key, Locale locale) {
        String result= null;
        try {
            result = ResourceBundle.getBundle(MSG_BUNDL_URL,locale).getString(key);
        } catch (MissingResourceException e) {
            logger.error("No resource found for: "+key);
            result="?ERROR?";
        }
        return result;
    }

    public static String readRegularExpressionSource (String key,Locale locale) {
        String result= null;
        try {
            result = ResourceBundle.getBundle(EXP_BUNDL_URL,locale).getString(key);
        } catch (MissingResourceException e) {
            logger.error("No regexp resource found for: "+key);
            result="?ERROR?";
        }
        return result;
    }
}
