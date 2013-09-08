package si.kcclass.bbmonandroidclient.rest.clients;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import si.kcclass.bbmonandroidclient.domain.MonitoringSystem;
import si.kcclass.bbmonandroidclient.rest.RestClient;

public class MonitoringSystemClient extends RestClient<List<MonitoringSystem>> {

	@Override
	protected List<MonitoringSystem> parseJson(String strJson) {
		Gson gson = new Gson();
		List<MonitoringSystem> entity = gson.fromJson(strJson, 
				new TypeToken<List<MonitoringSystem>>(){}.getType());
		return entity;
	}

}
