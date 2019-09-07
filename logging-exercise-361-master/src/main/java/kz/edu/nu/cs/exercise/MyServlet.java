package kz.edu.nu.cs.exercise;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/myservlet"})
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final ArrayList<String> ARRAY_LIST;
    private static final StringBuilder STRING_BUILDER;
    private static final SimpleDateFormat DATE_FORMAT;
    private static final String STRING_HOST;
    private static final String STRING_COLON;
    private static final String STRING_PATH;
    private static final String STRING_TIME;
    private static final String STRING_NEWLINE;

    static {
        ARRAY_LIST = new ArrayList<>();
        STRING_BUILDER = new StringBuilder();
        DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        STRING_HOST = "Host: ";
        STRING_COLON = ":";
        STRING_PATH = "  Path: ";
        STRING_TIME = "  Time: ";
        STRING_NEWLINE = "\n";
    }

    public MyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        STRING_BUILDER.append(STRING_HOST);
        STRING_BUILDER.append(request.getServerName());
        STRING_BUILDER.append(STRING_COLON);
        STRING_BUILDER.append(request.getServerPort());
        STRING_BUILDER.append(STRING_PATH);
        STRING_BUILDER.append(request.getContextPath());
        STRING_BUILDER.append(STRING_TIME);
        STRING_BUILDER.append(DATE_FORMAT.format(new Date()));
        STRING_BUILDER.append(STRING_NEWLINE);
        ARRAY_LIST.add(STRING_BUILDER.toString());
        STRING_BUILDER.setLength(0);
        for (String element : ARRAY_LIST) {
            response.getWriter().append(element);
        }
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
