package by.kozik.quest.service.comparator;

import by.kozik.quest.entity.UserQuestResultView;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Serge on 26.01.2017.
 */
public class UserQuestResultViewByDate implements Comparator<UserQuestResultView> {
    @Override
    public int compare(UserQuestResultView o1, UserQuestResultView o2) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        return collator.compare(o2.getDate(),o1.getDate());
    }
}
