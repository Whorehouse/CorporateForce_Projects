package org.corporateforce.client.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Articles;
import org.corporateforce.server.model.Projects;
import org.corporateforce.server.model.Users;
import org.corporateforce.client.port.ArticlesPort;
import org.corporateforce.client.port.ProjectsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ProjectsBean {

	private Projects currentProject;
	private Projects editProject;
	private List<Projects> projectsList;
	private Boolean createMode;

	@Autowired
	private MainBean mainBean;
	@Autowired
	private UsersBean usersBean;
	@Autowired
	private ProjectsPort projectsPort;
	@Autowired
	private ArticlesPort articlesPort;

	public List<Projects> getProjectsList() {
		if (projectsList == null)
			projectsList = projectsPort.list();
		return projectsList;
	}

	public void refreshProjectsList() {
		projectsList = projectsPort.list();
	}

	public Projects getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Projects currentProject) {
		this.currentProject = currentProject;
		if (currentProject.getUsersByLead() == null)
			currentProject.setUsersByLead(new Users());
	}

	public Projects getEditProject() {
		return editProject;
	}

	public void setEditProject(Projects editProject) {
		this.editProject = editProject;
	}

	public void actionEdit() {
		createMode = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("editProjectId");
		try {
			this.setEditProject(projectsPort.get(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionDelete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("deleteProjectId");
		try {
			projectsPort.delete(Integer.parseInt(id));
			refreshProjectsList();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getStatusesMap() throws Exception {
		Map<String, String> statuses = new HashMap<String, String>();
		statuses.put("active", "Активный");
		statuses.put("suspended", "Приостановлен");
		statuses.put("closed", "Закрыт");
		return statuses;
	}

	public void saveEdit() throws Exception {
		if (createMode)
			projectsPort.add(editProject);
		else
			projectsPort.update(editProject);
		createMode = false;
		refreshProjectsList();
	}

	public void actionOpen() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("currentProjectId");
		try {
			currentProject = projectsPort.get(Integer.parseInt(id));
			mainBean.actionProjectPage();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionCreate() {
		createMode = true;
		editProject = new Projects();
		editProject.setUsersByCreator(usersBean.getCurrentUser());
		editProject.setUsersByLead(new Users());	
	}
	
	

}
