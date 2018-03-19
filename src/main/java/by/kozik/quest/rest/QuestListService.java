package by.kozik.quest.rest;

import by.kozik.quest.entity.FormActionBean;
import by.kozik.quest.entity.QuestBeanJS;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.LanguageEnum;
import by.kozik.quest.service.QuestService;
import by.kozik.quest.service.RoleEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by Serge on 31.12.2016.
 */
@Path("quests")
public class QuestListService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postQuestList(@Context HttpServletRequest request,
                                  @FormParam("quest_lang") List<String> languages,
                                  @FormParam("quest_type") List<String> types,
                                  @FormParam("quest_categ") List<String> categories) {
        List<QuestBeanJS> result=null;
        HttpSession session = request.getSession();
        Object questsObj = session.getAttribute("quests");
        QuestService questService = QuestService.getInstance();
        int status;
        try{
            List<QuestBeanJS> quests;
            if (questsObj==null) {
                status = 503; //Service Unavailable
            } else {
                quests = (List<QuestBeanJS>)questsObj;
                result = questService.filterQuestsRest(quests,languages,types,categories);
            }
            status = 201; //created
        } catch (ClassCastException e2) {
            status = 500; //Internal Server Error
        }
        return Response.status(status).entity(result).build();
    }

}
