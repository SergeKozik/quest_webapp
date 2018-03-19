package by.kozik.quest.service;

import by.kozik.quest.dao.QuestDao;
import by.kozik.quest.dao.StatisticsDao;
import by.kozik.quest.entity.*;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.comparator.ArbitraryStringComparator;
import by.kozik.quest.service.comparator.QuestByTitleComparator;

import java.util.*;

/**
 * Created by Serge on 14.12.2016.
 */
public class QuestService {
    private static QuestService instance = new QuestService();

    private QuestService() {
    }

    public static QuestService getInstance() {
        return instance;
    }

    public List<QuestBean> showQuestNoOrder() throws PoolConnectionException, GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            List<QuestBean> questList = dao.showAllQuest();
            if ((questList==null)||(questList.isEmpty())) {
                return null;
            } else {
                return questList;
            }
        }
    }

    public List<QuestBean> showQuestNoOrder(UserBean userBean) throws PoolConnectionException, GetEntityException {
        if (userBean==null) {
            return null;
        }
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            List<QuestBean> questList = dao.showAllQuest(userBean);
            if ((questList==null)||(questList.isEmpty())) {
                return null;
            } else {
                return questList;
            }
        }
    }

    public List<QuestBean> showParticipatedQuestNoOrder(UserBean userBean) throws PoolConnectionException, GetEntityException {
        if (userBean==null) {
            return null;
        }
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            List<QuestBean> questList = dao.showAllParticipatedQuest(userBean);
            if ((questList==null)||(questList.isEmpty())) {
                return null;
            } else {
                return questList;
            }
        }
    }

    private QuestBeanJS entityToJS(QuestBean quest, Locale userLocale) {
        QuestBeanJS tmpQuest = new QuestBeanJS(quest);
        tmpQuest.setLanguageName(ResourceReader.readMessageResource("message.label.quest-language",userLocale));
        tmpQuest.setCategoryName(ResourceReader.readMessageResource("message.label.quest-category",userLocale));
        tmpQuest.setTypeResultName(ResourceReader.readMessageResource("message.label.quest-type",userLocale));
        tmpQuest.setTypeResultNative(ResourceReader.readMessageResource(QuestResultTypeEnum.getTypeByName(quest.getTypeResult()).getNameNativeCode(),userLocale));
        tmpQuest.setAuthorName(ResourceReader.readMessageResource("message.label.quest-author",userLocale));
        tmpQuest.setDateName(ResourceReader.readMessageResource("message.label.quest-date",userLocale));
        tmpQuest.setPassedName(ResourceReader.readMessageResource("message.label.quest-passed",userLocale));
        tmpQuest.setStartName(ResourceReader.readMessageResource("message.button.quest-start",userLocale));
        tmpQuest.setActionList(new ArrayList<FormActionBean>());
        return tmpQuest;
    }

    public List<QuestBeanJS> showQuestByTitle(Locale userLocale) throws PoolConnectionException, GetEntityException {
        List<QuestBean> listQuest = this.showQuestNoOrder();
        if (listQuest!=null) {
            Collections.sort(listQuest,new QuestByTitleComparator());
            List<QuestBeanJS> result = new ArrayList<>(listQuest.size());
            for (QuestBean quest:listQuest) {
                result.add(entityToJS(quest,userLocale));
            }
            return result;
        } else {
            return null;
        }
    }

    public List<QuestBeanJS> showQuestByTitle(UserBean userBean, Locale userLocale) throws PoolConnectionException, GetEntityException {
        List<QuestBean> listQuest = this.showQuestNoOrder(userBean);
        if (listQuest!=null) {
            Collections.sort(listQuest,new QuestByTitleComparator());
            List<QuestBeanJS> result = new ArrayList<>(listQuest.size());
            for (QuestBean quest:listQuest) {
                result.add(entityToJS(quest,userLocale));
            }
            return result;
        } else {
            return null;
        }
    }

    public List<QuestBeanJS> showParticipatedQuestByTitle(UserBean userBean, Locale userLocale) throws PoolConnectionException, GetEntityException {
        List<QuestBean> listQuest = this.showParticipatedQuestNoOrder(userBean);
        if (listQuest!=null) {
            Collections.sort(listQuest,new QuestByTitleComparator());
            List<QuestBeanJS> result = new ArrayList<>(listQuest.size());
            for (QuestBean quest:listQuest) {
                result.add(entityToJS(quest,userLocale));
            }
            return result;
        } else {
            return null;
        }
    }

    public List<QuestBean> showQuests(List<String> languages, List<String> types, List<String> categories) throws PoolConnectionException, GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            List<QuestBean> questList = dao.showQuests(languages,types,categories);
            return questList;
        }
    }

    public List<QuestBeanJS> showQuestsRest(List<String> languages, List<String> types, List<String> categories, Locale userLocale) throws PoolConnectionException, GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            List<QuestBean> questList = dao.showQuests(languages,types,categories);
            List<QuestBeanJS> result = new ArrayList<>(questList.size());
            for (QuestBean item:questList) {
                result.add(entityToJS(item,userLocale));
            }
            return result;
        }
    }

    public QuestBean findQuestById(int id) throws PoolConnectionException, GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            return  dao.findQuestById(id);
        }
    }

    public List<QuestBeanJS> filterQuestsRest(List<QuestBeanJS> quests, List<String> languages, List<String> types, List<String> categories) {
        if ((quests==null)||(quests.isEmpty())) {
            return null;
        }
        ArrayList<QuestBeanJS> result = new ArrayList<>(quests.size());
        for (QuestBeanJS item: quests) {
            if (
                    ((categories==null)||(categories.isEmpty())||(categories.contains(item.getCategory())))
                            &&((types==null)||(types.isEmpty())||types.contains(item.getTypeResult()))
                            &&((languages==null)||(languages.isEmpty())||(languages.contains(item.getLanguage())))
                    ) {
                result.add(item);
            }
        }
        result.trimToSize();
        return result;
    }

    public Set<String> showAllCategories(List<QuestBeanJS> quests) {
        TreeSet<String> result = new TreeSet<String>(new ArbitraryStringComparator());
        for (QuestBeanJS item:quests) {
            result.add(item.getCategory());
        }
        return result;
    }

    public Set<String> showAllCategories (String language) throws PoolConnectionException, GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            Set<String> result = dao.showAllCategories(language);
            if ((result==null)||(result.isEmpty())) {
                return null;
            } else {
                return result;
            }
        }
    }

    public Set<String> showAllLangugages(List<QuestBeanJS> quests) {
        TreeSet<String> result = new TreeSet<String>(new ArbitraryStringComparator());
        for (QuestBeanJS item:quests) {
            result.add(item.getLanguage());
        }
        return result;
    }

    public Set<String> showAllTypes(List<QuestBeanJS> quests) {
        TreeSet<String> result = new TreeSet<String>(new ArbitraryStringComparator());
        for (QuestBeanJS item:quests) {
            result.add(item.getTypeResult());
        }
        return result;
    }

    public List<TextContentBean> listQuestTypeBeans(Locale locale) {
        List<TextContentBean> result = new ArrayList<>();
        for (QuestResultTypeEnum item:QuestResultTypeEnum.values()) {
            TextContentBean tmpBean = new TextContentBean();
            tmpBean.setName(item.getNameEn());
            tmpBean.setDescription(ResourceReader.readMessageResource(item.getDescriptionCode(),locale));
            result.add(tmpBean);
        }
        return result;
    }

    public String questTypeI18n(String nameEn,Locale locale) {
        QuestResultTypeEnum type = QuestResultTypeEnum.getTypeByName(nameEn);
        if (type!=null) {
            return ResourceReader.readMessageResource(type.getDescriptionCode(),locale);
        } else {
            return null;
        }
    }

    public double calculateMaxMark(QuestBean questBean) {
        List<QuestionBean> questions = questBean.getQuestions();
        double maxMark = 0;
        if (questions!=null) {
            for (QuestionBean item:questions) {
                double tmpMark=0;
                List<AnswerVariantBasic> answers = item.getVariants();
                if (answers!=null) {
                    for(AnswerVariantBasic answer:answers) {
                        if (answer instanceof AnswerVariantCaseMark) {
                            AnswerVariantCaseMark markAnswer = (AnswerVariantCaseMark)answer;
                            if (markAnswer.getMark()>tmpMark) {
                                tmpMark=markAnswer.getMark();
                            }
                        }
                    }
                }
                maxMark = maxMark +tmpMark;
            }
        }
        return maxMark;
    }

    public double calculateUserMark(UserQuestResult questResult) throws PoolConnectionException, GetEntityException {
        double result = 0;
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            throw new GetEntityException("Null QuestDao.");
        }
        for (UserAnswerResult answerResult:questResult.getAnswers()) {
            result+=dao.returnMarkForAnswer(answerResult.getAnswerVariantId());
        }
        return result;
    }

    public int calculateUserAttempts(QuestBeanJS quest,UserBean user) throws PoolConnectionException,GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if ((quest==null)||(user==null)) {
            return 0;
        }
        if (dao!=null) {
            return dao.calculateUserAttempts(quest,user);
        } else {
            throw new GetEntityException("Null QuestDao.");
        }
    }

    public void saveQuestStatistics(UserQuestResult userQuestResult) throws PoolConnectionException,AlterEntityException {
        StatisticsDao dao = StatisticsDao.getInstance();
        if (dao!=null) {
            dao.saveQuestStatistics(userQuestResult);
        } else {
            throw new AlterEntityException("Quest dao is null.");
        }
    }

    public int usersForAnswer(AnswerVariantBasic answer) throws PoolConnectionException, GetEntityException {
        QuestDao dao = QuestDao.getInstance();
        if (dao!=null) {
            return dao.calculateUsersForAnswer(answer);
        } else {
            return 0;
        }
    }

    public void saveQuest(QuestBean quest) throws AlterEntityException,PoolConnectionException {
        QuestDao dao = QuestDao.getInstance();
        if (dao==null) {
            throw new AlterEntityException("Null DAO.");
        } else {
            dao.saveQuest(quest);
        }
    }
}
