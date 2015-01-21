package org.corporateforce.client.jsf;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Worklogs;
import org.corporateforce.client.port.WorklogsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class WorklogsBean {

	private Worklogs editWorklog;
	private Boolean createMode;
	private List<Worklogs> worklogsList;
	private Worklogs currentWorklog;

	@Autowired
	private MainBean mainBean;
	@Autowired
	private UsersBean usersBean;
	@Autowired
	private TicketsBean ticketsBean;
	@Autowired
	private WorklogsPort worklogsPort;

	public List<Worklogs> getWorklogsList() {
		if (worklogsList == null || ticketsBean.getWorklogsChanged())
			refreshWorklogsList();
		ticketsBean.setWorklogsChanged(false);
		return worklogsList;
	}

	public void refreshWorklogsList() {
		worklogsList = usersBean.isManageUsersAccess() ? worklogsPort.listByTicket(ticketsBean.getCurrentTicket().getId()) : worklogsPort.listByTicketAndUser(ticketsBean.getCurrentTicket().getId(), usersBean.getCurrentUser().getId());
	}

	public void actionEdit() {
		createMode = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("editWorklogId");
		try {
			this.setEditWorklog(worklogsPort.get(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionDelete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("deleteWorklogId");
		try {
			worklogsPort.delete(Integer.parseInt(id));
			refreshWorklogsList();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveEdit() throws Exception {
		if (createMode)
			worklogsPort.add(editWorklog);
		else
			worklogsPort.update(editWorklog);
		createMode = false;
		refreshWorklogsList();
	}

	public void actionCreate() {
		createMode = true;
		editWorklog = new Worklogs();
		editWorklog.setTickets(ticketsBean.getCurrentTicket());
		editWorklog.setUsers(usersBean.getCurrentUser());
	}

	public Worklogs getEditWorklog() {
		return editWorklog;
	}

	public void setEditWorklog(Worklogs editWorklog) {
		this.editWorklog = editWorklog;
	}

	public Worklogs getCurrentWorklog() {
		return currentWorklog;
	}

	public void setCurrentWorklog(Worklogs currentWorklog) {
		this.currentWorklog = currentWorklog;
	}
	
}
