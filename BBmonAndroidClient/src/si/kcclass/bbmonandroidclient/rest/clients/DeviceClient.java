package si.kcclass.bbmonandroidclient.rest.clients;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import si.kcclass.bbmonandroidclient.domain.Device;
import si.kcclass.bbmonandroidclient.rest.RestClient;

public class DeviceClient extends RestClient<List<Device>> {

	@Override
	protected List<Device> parseJson(String strJson) {
		Gson gson = new Gson();
		List<Device> entity = gson.fromJson(strJson, 
				new TypeToken<List<Device>>(){}.getType());
		return entity;
	}

}
