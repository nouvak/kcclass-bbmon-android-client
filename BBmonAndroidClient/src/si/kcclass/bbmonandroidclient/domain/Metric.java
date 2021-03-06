package si.kcclass.bbmonandroidclient.domain;

import com.google.gson.annotations.SerializedName;

public class Metric {
	@SerializedName("met_id")
	private Long id;
	@SerializedName("met_name")
	private String name;
	@SerializedName("color")
	private String color;
	
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
