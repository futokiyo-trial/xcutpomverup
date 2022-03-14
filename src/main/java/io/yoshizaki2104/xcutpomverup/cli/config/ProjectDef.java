package io.yoshizaki2104.xcutpomverup.cli.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5930847718325291625L;
	
	private String projectName;
	
	private String rootDirectory;
	
	private String versionSrc;
	
	private String versionDest;

	@JsonProperty("projectName")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@JsonProperty("rootDirectory")
	public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	@JsonProperty("versionSrc")
	public String getVersionSrc() {
		return versionSrc;
	}

	public void setVersionSrc(String versionSrc) {
		this.versionSrc = versionSrc;
	}

	@JsonProperty("versionDest")
	public String getVersionDest() {
		return versionDest;
	}

	public void setVersionDest(String versionDest) {
		this.versionDest = versionDest;
	}
	
	

}
