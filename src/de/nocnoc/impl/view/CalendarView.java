package de.nocnoc.impl.view;

import de.nocnoc.impl.controller.DateMessageUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
public class CalendarView {

    @Inject
    private DateMessageUtils dateMessageUtils;

    private Date date1;


    public void onDateSelect() {
        String message = dateMessageUtils.getSimpleDateMessage(date1);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", message));

        List<String> al = new ArrayList<>();
        al.add("ArrayList");

        dateMessageUtils.noReturn("noReturn");
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

}