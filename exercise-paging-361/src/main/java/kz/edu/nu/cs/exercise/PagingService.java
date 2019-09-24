package kz.edu.nu.cs.exercise;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/items")
public class PagingService {
    private static final String BEFORE_SIZE_LINE = "services/items?size=";
    private static final String AFTER_SIZE_LINE = "&page=";
    private static final String OUT_OF_BOUND_STRING = "outOfBound";
    private List<String> list = new CopyOnWriteArrayList<String>();

    public PagingService() {
        for (int i = 0; i < 100; i++) {
            list.add("Entry " + String.valueOf(i));
        }
    }

    @GET
    public Response getMyList(@QueryParam("size") int size, @QueryParam("page") int page) {
        Gson gson = new Gson();
        String json;

        PagedHelper p = new PagedHelper();
        if (size == 0) {
            p.setList(list);
        } else {
            if (list.size() > page * size) {
                if (list.size() > size * (page + 1)) {
                    p.setList(list.subList(size * page, size * (page + 1)));
                } else {
                    p.setList(list.subList(size * page, list.size()));
                }
            } else {
                p.setList(list);
            }
        }
        int lastPage = list.size() / size - 1;
        if (list.size() % size > 0) {
            lastPage++;
        }
        if (page == 0) {
            p.setPrev(OUT_OF_BOUND_STRING);
        } else {
            p.setPrev(BEFORE_SIZE_LINE + size + AFTER_SIZE_LINE + (page - 1));
        }
        if (page == lastPage) {
            p.setNext(OUT_OF_BOUND_STRING);
        } else {
            p.setNext(BEFORE_SIZE_LINE + size + AFTER_SIZE_LINE + (page + 1));
        }
        json = gson.toJson(p, PagedHelper.class);

        return Response.ok(json).build();
    }
}
