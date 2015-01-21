package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Users;
import org.corporateforce.client.config.Config;
import org.corporateforce.client.port.ContactsPort;
import org.corporateforce.client.port.UsersPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UsersBean {

	private Users currentUser;
	private Users showUser;
	private List<Users> listUsers;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersPort usersPort;
	@Autowired
	private ContactsPort contactsPort;
	
	public String getAvatar() {
    	if (isExistAvatar()) {
    		return Config.getUriServer()+"Avatars/showAvatar/"+currentUser.getContacts().getAvatars().getId();
    	} else {
    		return Config.getUriServer()+"resources/images/img_no_photo.png";
    	}
    }

	public String getAvatar(Users u) {
    	if (isExistAvatar(u)) {
    		return Config.getUriServer()+"Avatars/showAvatar/"+u.getContacts().getAvatars().getId();
    	} else {
    		return Config.getUriServer()+"resources/images/img_no_photo.png";
    	}
    }

	public Users getCurrentUser() {
		return currentUser;
	}
	
	public String getFullname() {
		return getFullname(currentUser);
	}
	
	public String getFullname(Users u) {
		if (u.getContacts()!=null) {
			return u.getContacts().getFirstname()+" "+u.getContacts().getNickname()+" "+u.getContacts().getLastname()+" ("+u.getUsername()+")";
		} else {
			return u.getUsername();
		}
	}

	public Map<String, String> getGenders() {
		Map<String, String> genders= new HashMap<String, String>();
		genders.put("male", "Мужской");
		genders.put("female", "Женский");
		return genders;
	}	

	public List<Users> getList() {
		List<Users> res = null;
		try {
			res = usersPort.list();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return res;
	}
	public List<Users> getListUsers() {
		return listUsers;
	}
	public Users getShowUser() {
		return showUser;
	}

	public Map<String, String> invertMap(Map<String, String> map) {
		Map<String, String> newMap= new HashMap<String, String>();
		for (Entry<String, String> entry : map.entrySet()) {
			newMap.put(entry.getValue(), entry.getKey());
		}
		return newMap;
	}

	public boolean isExistAvatar() {
		return isExistAvatar(currentUser);
	}

	public boolean isExistAvatar(Users u) {
		return (isExistContacts(u)&&u.getContacts().getAvatars()!=null) ? true : false;
	}
	
	public boolean isExistContacts() {
		return isExistContacts(currentUser);
	}
	
	public boolean isExistContacts(Users u) {
		return (u!=null && u.getContacts()!=null) ? true : false;
	}
	
	public boolean isLoginEnabledAccess() {
		return isLoginEnabledAccess(currentUser);
	}
	
	public boolean isLoginEnabledAccess(Users u) {
		return u!=null && u.getProfiles()!=null && u.getProfiles().isLoginEnabled();
	}
	
	public boolean isManageUsersAccess() {
		return isManageUsersAccess(currentUser);
	}
	
	public boolean isManageUsersAccess(Users u) {
		return u!=null && u.getProfiles()!=null && u.getProfiles().isManageUsers();
	}
	
	public boolean isSignedIn() {
		return currentUser!=null;
	}
	
	public boolean isSystemControlAccess() {
		return isSystemControlAccess(currentUser);
	}
	
	public boolean isSystemControlAccess(Users u) {
		return u!=null && u.getProfiles()!=null && u.getProfiles().isSystemControl();
	}
	
	public boolean isManager() {
		return isManager(currentUser);
	}
	
	public boolean isManager(Users u) {
		return usersPort.isManager(u.getId());
	}
	
	public boolean isManager(Users manager, Users user) {
		return usersPort.isManager(manager.getId(), user.getId());
	}
	
	public List<Users> listByManager() {
		return listByManager(currentUser);
	}
	
	public List<Users> listByManager(Users u) {
		List<Users> res = null;
    	try {
        	res = usersPort.listByManager(u.getId());        	
    	} catch (Exception e) {
    		res = new ArrayList<Users>();
    	}
    	return res;
	}
	
	public List<Users> listExludeCurrentUsers() {
    	List<Users> res = null;
    	try {
        	res = usersPort.listExclude(currentUser.getId());        	
    	} catch (Exception e) {
    		res = new ArrayList<Users>();
    	}
    	return res;
    }
	
	//Contacts
    
    public void login() {
		String login = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("login");
		String password = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("password");
		System.out.println("HEEEY Faces Login "+login+" password "+password);
		Users res = usersPort.login(login, password);
		if (res!=null && res.getId()>0) {
			System.out.println(res);
			currentUser = res;			
		} else {
			System.out.println("No:"+res);
		}
	}
    
	public void logout() {
		currentUser = null;
	}
    
	public void setContactsPort(ContactsPort contactsPort) {
		this.contactsPort = contactsPort;
	}
	
	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}
	
	public void setListUsers(List<Users> listUsers) {
		this.listUsers = listUsers;
	}
	
	public void setMainBean(MainBean mainBean) {
		this.mainBean = mainBean;
	}
	
	public void setShowUser(Users showUser) {
		this.showUser = showUser;
	}
    
    public void setUsersPort(UsersPort usersPort) {
		this.usersPort = usersPort;
	}
    
    public void updateUser() {
		if (currentUser!=null) {
			setCurrentUser(usersPort.get(currentUser.getId()));
		}
	}
    
	public Map<String, Integer> getUsersMap() throws Exception {
		List<Users> users = this.usersPort.list();
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Users u : users) {
			result.put(u.getUsername(), u.getId());
		}
		return result;
	}

}
