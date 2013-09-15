package si.kcclass.bbmonandroidclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.kcclass.bbmonandroidclient.domain.Metric;
import si.kcclass.bbmonandroidclient.rest.clients.MetricClient;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MetricsForDeviceActivity extends ListActivity {

	private class LoadMetricDataTask extends
			AsyncTask<String, Void, List<Metric>> {

		@Override
		protected List<Metric> doInBackground(String... params) {
			String monSysId = params[0];
			String customerId = params[1];
			String deviceId = params[2]; 
			MetricClient restClient = new MetricClient();
			Map<String, String> requestProperties = new HashMap<String, String>();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("monSysId", monSysId);
			arguments.put("monSysCustomer", customerId);
			arguments.put("devId", deviceId);
			List<Metric> data = new ArrayList<Metric>();
			try {
				data = restClient.invokeService("/devices/getMetrics", arguments,
						requestProperties);
			} catch (Exception e) {
				Log.e(LOG_TAG,
						"The loading of the metric data failed: "
								+ e.getMessage(), e);
				Toast.makeText(
						MetricsForDeviceActivity.this,
						"The loading of the metric data failed: "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return data;
		}

		@Override
		protected void onPostExecute(List<Metric> data) {
			mMetricData = data;
			List<String> listNames = new ArrayList<String>();
			for (Metric device : data) {
				listNames.add(device.getName());
			}
			ListAdapter adapter = new ArrayAdapter<String>(
					MetricsForDeviceActivity.this,
					android.R.layout.simple_list_item_1, listNames);
			setListAdapter(adapter);
		}

	}

	private static final String LOG_TAG = MetricsForDeviceActivity.class.getName();

	long monSysId;
	long customerId;
	long deviceId;
	private List<Metric> mMetricData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);

		Bundle b = getIntent().getExtras();
		monSysId = b.getLong("monSysId");
		customerId = b.getLong("customerId");
		deviceId = b.getLong("deviceId");
		loadMetric();
		/*ListView listview = (ListView) findViewById(android.R.id.list);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemClicked(position);
			}

		});*/
	}

	private void loadMetric() {
		new LoadMetricDataTask().execute(Long.toString(monSysId),
				Long.toString(customerId), Long.toString(deviceId));
	}

}
