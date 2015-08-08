import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Date;

@ManagedBean
public class CalendarView {

    @Inject
    private DateMessageFactory dateMessageFactory;

    private Date date1;


    public void onDateSelect() {
        String message = dateMessageFactory.getSimpleDateMessage(date1);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", message));
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

}