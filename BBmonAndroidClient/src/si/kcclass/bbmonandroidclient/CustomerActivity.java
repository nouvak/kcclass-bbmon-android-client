package si.kcclass.bbmonandroidclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.kcclass.bbmonandroidclient.domain.Customer;
import si.kcclass.bbmonandroidclient.rest.clients.CustomerClient;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CustomerActivity extends ListActivity {

    private class LoadCustomerDataTask extends
	    AsyncTask<String, Void, List<Customer>> {

	@Override
	protected List<Customer> doInBackground(String... params) {
	    String monSysId = params[0];
	    CustomerClient restClient = new CustomerClient();
	    Map<String, String> requestProperties = new HashMap<String, String>();
	    Map<String, String> arguments = new HashMap<String, String>();
	    arguments.put("monSysId", monSysId);
	    List<Customer> data = new ArrayList<Customer>();
	    try {
		data = restClient.invokeService("/devices/getMonSysCustomer",
			arguments, requestProperties);
	    } catch (Exception e) {
		Log.e(LOG_TAG,
			"The loading of the customer data failed: "
				+ e.getMessage(), e);
		Toast.makeText(
			CustomerActivity.this,
			"The loading of the customer data failed: "
				+ e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	    return data;
	}

	@Override
	protected void onPostExecute(List<Customer> data) {
	    mCustomerData = data;
	    List<String> listNames = new ArrayList<String>();
	    for (Customer customer : data) {
		listNames.add(customer.getId().toString());
	    }
	    ListAdapter adapter = new ArrayAdapter<String>(
		    CustomerActivity.this, android.R.layout.simple_list_item_1,
		    listNames);
	    setListAdapter(adapter);
	}

    }

    private static final String LOG_TAG = CustomerActivity.class.getName();

    private long monSysId;
    private List<Customer> mCustomerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_customer);

	Bundle b = getIntent().getExtras();
	monSysId = b.getLong("monSysId");
	loadCustomer();
	updateTitle();
	ListView listview = (ListView) findViewById(android.R.id.list);
	listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		itemClicked(position);
	    }

	});
    }

    private void loadCustomer() {
	new LoadCustomerDataTask().execute(Long.toString(monSysId));
    }
    
    private void updateTitle() {
	Bundle b = getIntent().getExtras();
	String monSysName = b.getString("monSysName");
	setTitle(getTitle() + ": " + monSysName);
    }

    private void itemClicked(int position) {
	Customer customer = mCustomerData.get(position);
	Intent intent = new Intent(this, DeviceActivity.class);
	Bundle b = new Bundle();
	b.putLong("monSysId", monSysId);
	b.putLong("customerId", customer.getId());
	intent.putExtras(b);
	startActivity(intent);
    }
}
