package si.kcclass.bbmonandroidclient.domain;

import com.google.gson.annotations.SerializedName;

public class MonitoringSystem {
	@SerializedName("mon_sys_id")
	private Long id;
	@SerializedName("mon_sys_name")
	private String name;
	
	public MonitoringSystem() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
