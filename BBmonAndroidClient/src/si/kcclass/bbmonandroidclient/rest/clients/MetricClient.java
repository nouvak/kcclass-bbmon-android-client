package si.kcclass.bbmonandroidclient.rest.clients;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import si.kcclass.bbmonandroidclient.domain.Metric;
import si.kcclass.bbmonandroidclient.rest.RestClient;

public class MetricClient extends RestClient<List<Metric>> {

	@Override
	protected List<Metric> parseJson(String strJson) {
		Gson gson = new Gson();
		List<Metric> entity = gson.fromJson(strJson, 
				new TypeToken<List<Metric>>(){}.getType());
		return entity;
	}

}
