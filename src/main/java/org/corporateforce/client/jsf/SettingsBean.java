package org.corporateforce.client.jsf;

import java.io.Serializable;

import org.corporateforce.client.config.Config;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class SettingsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String MODULE_FACES = "Faces";
	private static final String MODULE_TRAININGS = "Trainings";

	public String getUriServer() {
		return Config.getUriServer();
	}

	public String getUriFaces() {
		return Config.getUriModule(MODULE_FACES);
	}
	
	public String getUriTrainings() {
		return Config.getUriModule(MODULE_TRAININGS);
	}

}
