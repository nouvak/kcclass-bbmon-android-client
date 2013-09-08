package si.kcclass.bbmonandroidclient.domain;

import com.google.gson.annotations.SerializedName;

public class Device {
	@SerializedName("dev_id")
	private Long id;
	@SerializedName("dev_name")
	private String name;
	
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
