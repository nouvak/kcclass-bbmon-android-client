package si.kcclass.bbmonandroidclient.domain;

import com.google.gson.annotations.SerializedName;

public class Customer {
	@SerializedName("mon_sys_customer")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
