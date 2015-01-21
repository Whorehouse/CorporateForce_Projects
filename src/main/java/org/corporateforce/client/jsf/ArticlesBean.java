package org.corporateforce.client.jsf;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Articles;
import org.corporateforce.client.port.ArticlesPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ArticlesBean {

	private Articles editArticle;
	private Boolean createMode;
	private List<Articles> articlesList;

	@Autowired
	private UsersBean usersBean;
	@Autowired
	private ProjectsBean projectsBean;
	@Autowired
	private ArticlesPort articlesPort;

	public List<Articles> getArticlesList() {
		if (articlesList == null || projectsBean.getArticlesChanged())
			refreshArticlesList();
		projectsBean.setArticlesChanged(false);
		return articlesList;
	}

	public void refreshArticlesList() {
		articlesList = articlesPort.listByProject(projectsBean.getCurrentProject().getId());
	}

	public void actionEdit() {
		createMode = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("editArticleId");
		try {
			this.setEditArticle(articlesPort.get(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionDelete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id = params.get("deleteArticleId");
		try {
			articlesPort.delete(Integer.parseInt(id));
			refreshArticlesList();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveEdit() throws Exception {
		editArticle.setBody(editArticle.getBody().replaceAll("\\r|\\n", ""));
		if (createMode)
			articlesPort.add(editArticle);
		else
			articlesPort.update(editArticle);
		createMode = false;
		refreshArticlesList();
	}

	public void actionCreate() {
		createMode = true;
		editArticle = new Articles();
		editArticle.setProjects(projectsBean.getCurrentProject());
		editArticle.setUsers(usersBean.getCurrentUser());
	}

	public Articles getEditArticle() {
		return editArticle;
	}

	public void setEditArticle(Articles editArticle) {
		this.editArticle = editArticle;
	}

}
