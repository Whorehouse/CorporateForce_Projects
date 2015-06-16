package org.corporateforce.client.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.corporateforce.client.config.Config;
import org.corporateforce.client.port.UsersPort;
import org.corporateforce.client.port.WorklogsPort;
import org.corporateforce.server.model.Worklogs;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@Scope("request")
public class MainBean implements Serializable {

	@Autowired
	private WorklogsPort worklogsPort;
	@Autowired
	private UsersBean usersBean;

	private String selectedDate;
	
	private List<Worklogs> worklogsList;

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	private String workingTime = "0.0";

	public static final String PAGE_LOGIN = "login";
	public static final String PAGE_LOGOUT = "logout";
	public static final String PAGE_CONSOLE = "console";

	public static final String PAGE_INDEX = "/index";
	public static final String PAGE_WELCOME = "/welcome";
	public static final String PAGE_PROJECT = "/project";
	public static final String PAGE_TIMESHEET = "/timesheet";
	public static final String PAGE_TICKET = "/ticket";

	private static final String MODULE_FACES = "Faces";
	private static final String MODULE_PROJECTS = "Projects";
	private static final String MODULE_TRAININGS = "Trainings";

	private void redirect(String page, boolean external) throws Exception {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if (!external)
			page = context.getRequestContextPath() + page;
		context.redirect(page);
	}

	private void redirect(String page) throws Exception {
		this.redirect(page, false);
	}

	public void actionMainPage() throws Exception {
		this.redirect(PAGE_INDEX);
	}

	public void actionProjectPage() throws Exception {
		this.redirect(PAGE_PROJECT);
	}

	public void actionTimesheetPage() throws Exception {
		this.redirect(PAGE_TIMESHEET);
	}

	public void actionTicketPage() throws Exception {
		this.redirect(PAGE_TICKET);
	}
	
	public void actionSupportPage() throws Exception {
		this.redirect("/support");
	}

	// External resources

	public void actionServer() throws Exception {
		this.redirect(Config.getUriServer(), true);
	}

	public void actionLogin() throws Exception {
		this.redirect(Config.getUriServer() + PAGE_LOGIN, true);
	}

	public void actionLogout() throws Exception {
		this.redirect(Config.getUriServer() + PAGE_LOGOUT, true);
	}

	public void actionConsole() throws Exception {
		this.redirect(Config.getUriServer() + PAGE_CONSOLE, true);
	}

	public void actionOpenFaces() throws Exception {
		this.redirect(Config.getUriModule(MODULE_FACES), true);
	}

	public void actionOpenProjects() throws Exception {
		this.redirect(Config.getUriModule(MODULE_PROJECTS), true);
	}

	public void actionOpenTrainings() throws Exception {
		this.redirect(Config.getUriModule(MODULE_TRAININGS), true);
	}

	public void onDateSelect(SelectEvent selectedDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		this.selectedDate = format.format(selectedDate.getObject());
		System.out.println("Clendar Selected date: " + selectedDate);
		worklogsList = worklogsPort.list();
	}

	public List<Worklogs> getWorkTimeList() throws ParseException {
		List<Worklogs> result = new ArrayList<Worklogs>();
		System.out.println("Selected date: " + selectedDate);
		if (selectedDate == null || worklogsList == null)
			return result;

		workingTime = "0.0";
		Double summ = 0.0;

		for (Worklogs w : worklogsList) {

			SimpleDateFormat simpleFormat = new SimpleDateFormat("dd.MM.yyyy");
			String formatedDate = simpleFormat.format(w.getCreated());
			System.out.println("w date into dd.MM.yyyy format: " + formatedDate);

			if (formatedDate.equals(selectedDate) && (usersBean.isManageUsersAccess() || usersBean.getCurrentUser().getId() == w.getUsers().getId())) {
				result.add(w);
				summ += Double.parseDouble(w.getTime().toString());
			}
		}

		workingTime = summ.toString();
		return result;
	}

	public String getWorkingTime() {

		workingTime = "0.0";
		
		if (selectedDate == null || worklogsList == null)
			return workingTime;	

		Double summ = 0.0;

		for (Worklogs w : worklogsList) {

			SimpleDateFormat simpleFormat = new SimpleDateFormat("dd.MM.yyyy");
			String formatedDate = simpleFormat.format(w.getCreated());
			System.out.println("w date into dd.MM.yyyy format: " + formatedDate);

			if (formatedDate.equals(selectedDate) && (usersBean.isManageUsersAccess() || usersBean.getCurrentUser().getId() == w.getUsers().getId())) {
				summ += Double.parseDouble(w.getTime().toString());
			}
		}

		workingTime = summ.toString();
		
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

}
