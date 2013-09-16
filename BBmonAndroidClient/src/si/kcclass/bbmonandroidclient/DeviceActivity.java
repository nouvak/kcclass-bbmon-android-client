package si.kcclass.bbmonandroidclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.kcclass.bbmonandroidclient.domain.Device;
import si.kcclass.bbmonandroidclient.rest.clients.DeviceClient;
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

public class DeviceActivity extends ListActivity {

	private class LoadDeviceDataTask extends
			AsyncTask<String, Void, List<Device>> {

		@Override
		protected List<Device> doInBackground(String... params) {
			String monSysId = params[0];
			String customerId = params[1];
			DeviceClient restClient = new DeviceClient();
			Map<String, String> requestProperties = new HashMap<String, String>();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("monSysId", monSysId);
			arguments.put("monSysCustomer", customerId);
			List<Device> data = new ArrayList<Device>();
			try {
				data = restClient.invokeService("/devices/getDev",
						arguments, requestProperties);
			} catch (Exception e) {
				Log.e(LOG_TAG,
						"The loading of the customer data failed: "
								+ e.getMessage(), e);
				Toast.makeText(
						DeviceActivity.this,
						"The loading of the device data failed: "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return data;
		}

		@Override
		protected void onPostExecute(List<Device> data) {
			mDeviceData = data;
			List<String> listNames = new ArrayList<String>();
			for (Device device : data) {
				listNames.add(device.getName());
			}
			ListAdapter adapter = new ArrayAdapter<String>(
					DeviceActivity.this, android.R.layout.simple_list_item_1,
					listNames);
			setListAdapter(adapter);
		}

	}

	private static final String LOG_TAG = DeviceActivity.class.getName();
	
	long monSysId;
	long customerId;
	private List<Device> mDeviceData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);
		
		Bundle b = getIntent().getExtras();
		monSysId = b.getLong("monSysId");
		customerId = b.getLong("customerId");
		loadDevice();
		ListView listview = (ListView) findViewById(android.R.id.list);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemClicked(position);
			}

		});
	}

	private void loadDevice() {
		new LoadDeviceDataTask().execute(Long.toString(monSysId),
				Long.toString(customerId));
	}

	private void itemClicked(int position) {
		Device device = mDeviceData.get(position);
		Intent intent = new Intent(this, MetricsForDeviceActivity.class);
		Bundle b = new Bundle();
		b.putLong("monSysId", monSysId);
		b.putLong("customerId", customerId);
		b.putLong("deviceId", device.getId());
		intent.putExtras(b);
		startActivity(intent);
	}

}
