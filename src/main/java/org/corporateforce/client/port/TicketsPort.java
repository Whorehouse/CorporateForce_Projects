package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Tickets;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TicketsPort extends AbstractPort<Tickets> {
	
	public TicketsPort() {
		super(Tickets.class);
	}

	public TicketsPort(Class<Tickets> entityClass) {
		super(entityClass);
	}

	public List<Tickets> listByProject(int id) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByProject/"+id, List.class);
		return convertToList(list,entityClass);
	}
}
