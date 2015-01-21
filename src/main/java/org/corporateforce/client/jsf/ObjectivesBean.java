package org.corporateforce.client.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Objectives;
import org.corporateforce.client.port.ObjectivesPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ObjectivesBean {

	private Objectives editObjective;
	private Boolean createMode;

	@Autowired
	private MainBean mainBean;
	@Autowired
	private UsersBean usersBean;
	@Autowired
	private ProjectsBean projectsBean;
	@Autowired
	private ObjectivesPort objectivesPort;

	public List<Objectives> getObjectivesList() {
		return objectivesPort.listByProject(projectsBean.getCurrentProject().getId());
	}

	public void actionEdit() {
		createMode = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("editObjectiveId");
		try {
			this.setEditObjective(objectivesPort.get(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionDelete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("deleteObjectiveId");
		try {
			objectivesPort.delete(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getStatusesMap() throws Exception {
		Map<String, String> statuses = new HashMap<String, String>();
		statuses.put("new", "Новая");
		statuses.put("pending", "Согласовывается");
		statuses.put("in progress", "В процессе");
		statuses.put("rejected", "Отменена");
		return statuses;
	}

	public void saveEdit() throws Exception {
		if (createMode)
			objectivesPort.add(editObjective);
		else
			objectivesPort.update(editObjective);
		createMode = false;
	}

	public void actionCreate() {
		createMode = true;
		editObjective = new Objectives();
		editObjective.setProjects(projectsBean.getCurrentProject());
		editObjective.setUsers(usersBean.getCurrentUser());
	}

	public Objectives getEditObjective() {
		return editObjective;
	}

	public void setEditObjective(Objectives editObjective) {
		this.editObjective = editObjective;
	}
	
}
