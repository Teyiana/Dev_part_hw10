
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        String timezone = req.getParameter("timezone");
        timezone = timezone.replace(' ', '+');
        try {
            ZoneId zd = ZoneId.of(timezone);
            ZonedDateTime dateTime = ZonedDateTime.now(zd);
            response.setStatus(200);
            response.getWriter().write(dateTime.format(dtf));
            response.getWriter().close();
        } catch (DateTimeException e) {
            response.setStatus(400);
            response.setContentType(" ");
            response.getWriter().write("{\"Error\": \"Invalid timezone\"}");
            response.getWriter().close();
        }
    }
}
