package by.kozik.quest.service.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Serge on 30.12.2016.
 */
public class ArbitraryStringComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        return collator.compare(o1,o2);
    }
}
