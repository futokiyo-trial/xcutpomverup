package io.yoshizaki2104.xcutpomverup.cli.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8316591114128571571L;

	private String propertyName;
	
	private String versionSrc;
	
	private String versionDest;

	@JsonProperty("propertyName")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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
