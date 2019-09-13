package kz.edu.nu.cs.se.hw;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/items")
public class ListItemsService {
    
    private List<String> list = new CopyOnWriteArrayList<String>();
    
    public ListItemsService() {
        for (int i = 0; i < 20; i++) {
            list.add("Entry "+i+" "+ java.util.Calendar.getInstance().getTime().toString());
        }
        
        Collections.reverse(list);
    }
    
    @GET
    public Response getList() {
        Gson gson = new Gson();
        
        return Response.ok(gson.toJson(list)).build();
    }
    
    @GET
    @Path("{id: [0-9]+}")
    public Response getListItem(@PathParam("id") String id) {
        int i = Integer.parseInt(id);
        
        return Response.ok(list.get(i)).build();
    }
    
    @POST
    public Response postListItem(@FormParam("newEntry") String entry) {
        if(Pattern.compile("\\p{Alpha}\\p{Alnum}{2,}").matcher(entry).matches()){
            list.add(0, entry+" "+java.util.Calendar.getInstance().getTime().toString());
            return Response.ok().build();
        }else{
            return Response.status(400).entity("Task must start at alphabetic character, followed by at least 2 alphanumeric characters").build();
        }
    }

    @DELETE
    public Response deleteAll(){
        list.clear();
        return Response.ok().build();
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteAll(@PathParam("id") String id){
        list.remove(Integer.parseInt(id));
        return Response.ok().build();
    }
}
