package org.corporateforce.client.port;

import org.corporateforce.server.model.Projects;
import org.springframework.stereotype.Service;

@Service
public class ProjectsPort extends AbstractPort<Projects> {
	
	public ProjectsPort() {
		super(Projects.class);
	}

	public ProjectsPort(Class<Projects> entityClass) {
		super(entityClass);
	}

}
