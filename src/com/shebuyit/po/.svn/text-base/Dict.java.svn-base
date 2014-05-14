package com.shebuyit.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dict")
public class Dict implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dict() {
		
	}
	
	private Long id;
	
	private String code ;
	
	private String name;
	
	private String created_time;
	
	private String time_start;
	
	private String time_end;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 30)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(length = 30)
	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	
}

