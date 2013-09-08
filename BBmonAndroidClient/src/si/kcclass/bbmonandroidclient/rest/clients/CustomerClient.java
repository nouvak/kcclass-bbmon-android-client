package si.kcclass.bbmonandroidclient.rest.clients;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import si.kcclass.bbmonandroidclient.domain.Customer;
import si.kcclass.bbmonandroidclient.rest.RestClient;

public class CustomerClient extends RestClient<List<Customer>> {

	@Override
	protected List<Customer> parseJson(String strJson) {
		Gson gson = new Gson();
		List<Customer> entity = gson.fromJson(strJson, 
				new TypeToken<List<Customer>>(){}.getType());
		return entity;
	}

}
