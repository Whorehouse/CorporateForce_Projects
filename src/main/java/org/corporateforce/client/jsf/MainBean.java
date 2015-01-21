package org.corporateforce.client.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.corporateforce.client.config.Config;
import org.corporateforce.client.port.WorklogsPort;
import org.corporateforce.server.model.Worklogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@Scope("request")
public class MainBean implements Serializable {

	@Autowired
	private WorklogsPort worklogsPort;

	private Date selectedDate;

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
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

	public Map<String, String> getWorkTimeMap() throws ParseException {
		Map<String, String> result = new HashMap<String, String>();
		System.out.println(selectedDate);
		if (selectedDate == null)
			return result;
		List<Worklogs> worklogsList = worklogsPort.list();

		workingTime = "0.0";
		Double summ = 0.0;

		for (Worklogs w : worklogsList) {
			if (w.getCreated().getDay() == selectedDate.getDay() && w.getCreated().getMonth() == selectedDate.getMonth() && w.getCreated().getYear() == selectedDate.getYear()) {
				result.put(w.getTickets().getProjects().getName(), w.getTime().toString());
				summ += Double.parseDouble(w.getTime().toString());
			}
		}

		workingTime = summ.toString();
		return result;
	}

	public String getWorkingTime() throws ParseException {
		Map<String, String> m = getWorkTimeMap();
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

}
