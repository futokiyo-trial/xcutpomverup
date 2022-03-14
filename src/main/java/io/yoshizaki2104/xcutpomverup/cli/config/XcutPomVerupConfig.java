package io.yoshizaki2104.xcutpomverup.cli.config;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcutPomVerupConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137976374448062754L;
	
	private List<PropertyDef> properties;
	
	private List<ProjectDef> projects;

	@JsonProperty("properties")
	public List<PropertyDef> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyDef> properties) {
		this.properties = properties;
	}

	@JsonProperty("projects")
	public List<ProjectDef> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDef> projects) {
		this.projects = projects;
	}
	
	

}
