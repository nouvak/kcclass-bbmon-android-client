package si.kcclass.bbmonandroidclient.domain;

import java.util.HashMap;
import java.util.Map;

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
	
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id.toString());
		map.put("name", name);
		return map;
	}

}
