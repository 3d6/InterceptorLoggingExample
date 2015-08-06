import javax.faces.bean.ApplicationScoped;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Managing String Formats for Dates
 */
@ApplicationScoped
public class DateMessageFactory {

    public String getSimpleDateMessage(Date date){

        ZonedDateTime zdt = date.toInstant().atZone(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return formatter.format(zdt);
    }
}
