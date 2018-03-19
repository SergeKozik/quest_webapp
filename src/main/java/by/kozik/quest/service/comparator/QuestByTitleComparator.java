package by.kozik.quest.service.comparator;

import by.kozik.quest.entity.QuestBean;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Serge on 01.01.2017.
 */
public class QuestByTitleComparator implements Comparator<QuestBean> {

    @Override
    public int compare(QuestBean o1, QuestBean o2) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        return collator.compare(o1.getTitle(),o2.getTitle());
    }
}
