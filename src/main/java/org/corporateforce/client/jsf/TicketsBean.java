package org.corporateforce.client.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Tickets;
import org.corporateforce.client.port.TicketsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class TicketsBean {

	private Tickets editTicket;
	private Boolean createMode;
	private List<Tickets> ticketsList;
	private Tickets currentTicket;

	@Autowired
	private MainBean mainBean;
	@Autowired
	private UsersBean usersBean;
	@Autowired
	private ProjectsBean projectsBean;
	@Autowired
	private TicketsPort ticketsPort;

	private Boolean worklogsChanged = false;
	
	public List<Tickets> getTicketsList() {
		if (ticketsList == null || projectsBean.getTicketsChanged())
			refreshTicketsList();
		projectsBean.setTicketsChanged(false);
		return ticketsList;
	}

	public void refreshTicketsList() {
		ticketsList = ticketsPort.listByProject(projectsBean.getCurrentProject().getId());
	}
	public void actionEdit() {
		createMode = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("editTicketId");
		try {
			this.setEditTicket(ticketsPort.get(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionDelete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("deleteTicketId");
		try {
			ticketsPort.delete(Integer.parseInt(id));
			refreshTicketsList();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getStatusesMap() throws Exception {
		Map<String, String> statuses = new HashMap<String, String>();
		statuses.put("open", "Открыт");
		statuses.put("in progress", "В процессе");
		statuses.put("closed", "Закрыт");
		return statuses;
	}

	public Map<String, String> getPriorityMap() throws Exception {
		Map<String, String> statuses = new HashMap<String, String>();
		statuses.put("major", "Высокий");
		statuses.put("minor", "Обычный");
		statuses.put("trivial", "Низкий");
		return statuses;
	}
	
	public Map<String, String> getTypesMap() throws Exception {
		Map<String, String> statuses = new HashMap<String, String>();
		statuses.put("bug", "Баг");
		statuses.put("task", "Задание");
		statuses.put("question", "Вопрос");
		statuses.put("new feature", "Новая фича");
		statuses.put("improvement", "Улучшение");
		return statuses;
	}

	public void saveEdit() throws Exception {
		if (createMode)
			ticketsPort.add(editTicket);
		else
			ticketsPort.update(editTicket);
		createMode = false;
		refreshTicketsList();
	}

	public void actionCreate() {
		createMode = true;
		editTicket = new Tickets();
		editTicket.setProjects(projectsBean.getCurrentProject());
		editTicket.setUsersByCreator(usersBean.getCurrentUser());
	}

	public Tickets getEditTicket() {
		return editTicket;
	}

	public void setEditTicket(Tickets editTicket) {
		this.editTicket = editTicket;
	}
	
	public void actionOpen() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("currentTicketId");
		try {
			setCurrentTicket(ticketsPort.get(Integer.parseInt(id)));		
			mainBean.actionTicketPage();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tickets getCurrentTicket() {
		return currentTicket;
	}

	public void setCurrentTicket(Tickets currentTicket) {
		this.currentTicket = currentTicket;
		worklogsChanged = true;
	}

	public Boolean getWorklogsChanged() {
		return worklogsChanged;
	}

	public void setWorklogsChanged(Boolean worklogsChanged) {
		this.worklogsChanged = worklogsChanged;
	}
	
}
