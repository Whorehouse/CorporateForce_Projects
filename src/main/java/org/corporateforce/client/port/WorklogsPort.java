package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Worklogs;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorklogsPort extends AbstractPort<Worklogs> {
	
	public WorklogsPort() {
		super(Worklogs.class);
	}

	public WorklogsPort(Class<Worklogs> entityClass) {
		super(entityClass);
	}

	public List<Worklogs> listByTicket(int id) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByTicket/"+id, List.class);
		return convertToList(list,entityClass);
	}
	
	public List<Worklogs> listByTicketAndUser(int tid, int uid) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByTicketAndUser/"+tid+"/"+uid, List.class);
		return convertToList(list,entityClass);
	}
}
