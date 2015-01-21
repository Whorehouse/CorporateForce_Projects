package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Articles;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ArticlesPort extends AbstractPort<Articles> {
	
	public ArticlesPort() {
		super(Articles.class);
	}

	public ArticlesPort(Class<Articles> entityClass) {
		super(entityClass);
	}

	public List<Articles> listByProject(int id) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByProject/"+id, List.class);
		return convertToList(list,entityClass);
	}
}
