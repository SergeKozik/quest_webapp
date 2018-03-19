package by.kozik.quest.rest;

import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.service.QuestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Serge on 07.01.2017.
 */
@Path("quest/categories")
public class CategoryByLanguageService {

    @GET
    @Path("{lang}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("lang") String language) {
        int status;
        Set<String> result=new HashSet<>();
        try{
            result = QuestService.getInstance().showAllCategories(language);
            status = 201; //created
        } catch (PoolConnectionException|GetEntityException e1) {
            status = 503; //Service Unavailable
        }
        return Response.status(status).entity(result).build();

    }
}
