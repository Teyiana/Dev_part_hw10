import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
    public static final String ZONE = "UTC";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String timezone = req.getParameter("timezone");
        timezone = timezone == null ? ZONE : timezone;
        timezone = timezone.replace(' ', '+');
        resp.setContentType("text/html; charset=utf-8");
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of(timezone));
        resp.getWriter().write(dateTime.format(dtf));
        resp.getWriter().close();
    }
}
