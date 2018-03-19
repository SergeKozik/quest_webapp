package by.kozik.quest.service.comparator;

import by.kozik.quest.entity.QuestBean;

import java.util.Comparator;

/**
 * Created by Serge on 14.12.2016.
 */
public class QuestByDateComparator implements Comparator<QuestBean> {

    @Override
    public int compare(QuestBean o1, QuestBean o2) {
        return o2.getDateObject().compareTo(o1.getDateObject());
    }
}
