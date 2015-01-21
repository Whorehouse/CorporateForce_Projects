package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Objectives;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ObjectivesPort extends AbstractPort<Objectives> {
	
	public ObjectivesPort() {
		super(Objectives.class);
	}

	public ObjectivesPort(Class<Objectives> entityClass) {
		super(entityClass);
	}

	public List<Objectives> listByProject(int id) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByProject/"+id, List.class);
		return convertToList(list,entityClass);
	}
}
