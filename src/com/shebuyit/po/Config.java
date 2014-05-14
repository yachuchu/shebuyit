package com.shebuyit.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="config")
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Config() {
		
	}
	
	private Long id;
	
	private String configName;
	
	private String configValue;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 30)
	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	@Column(length = 30)
	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
