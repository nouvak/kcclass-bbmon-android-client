package si.kcclass.bbmonandroidclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.kcclass.bbmonandroidclient.domain.MonitoringSystem;
import si.kcclass.bbmonandroidclient.rest.clients.MonitoringSystemClient;
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

public class MainActivity extends ListActivity {

    private class LoadMonitoringSystemDataTask extends
	    AsyncTask<Void, Void, List<MonitoringSystem>> {

	@Override
	protected List<MonitoringSystem> doInBackground(Void... params) {
	    MonitoringSystemClient restClient = new MonitoringSystemClient();
	    Map<String, String> requestProperties = new HashMap<String, String>();
	    List<MonitoringSystem> data = new ArrayList<MonitoringSystem>();
	    try {
		data = restClient.invokeService("/devices/getMonSys",
			requestProperties);
	    } catch (Exception e) {
		Log.e(LOG_TAG,
			"The loading of the monitoring system data failed: "
				+ e.getMessage(), e);
		Toast.makeText(
			MainActivity.this,
			"The loading of the monitoring system data failed: "
				+ e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	    return data;
	}

	@Override
	protected void onPostExecute(List<MonitoringSystem> data) {
	    mMonSystemData = data;
	    List<String> listNames = new ArrayList<String>();
	    for (MonitoringSystem monSys : data) {
		listNames.add(monSys.getName());
	    }
	    ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this,
		    android.R.layout.simple_list_item_1, listNames);
	    setListAdapter(adapter);
	}

    }

    private static final String LOG_TAG = MainActivity.class.getName();

    private List<MonitoringSystem> mMonSystemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	loadMonitoringSystem();
	ListView listview = (ListView) findViewById(android.R.id.list);
	listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		itemClicked(position);
	    }

	});
    }

    private void loadMonitoringSystem() {
	new LoadMonitoringSystemDataTask().execute();
    }

    private void itemClicked(int position) {
	MonitoringSystem monSys = mMonSystemData.get(position);
	Intent intent = new Intent(this, CustomerActivity.class);
	Bundle b = new Bundle();
	b.putLong("monSysId", monSys.getId());
	b.putString("monSysName", monSys.getName());
	intent.putExtras(b);
	startActivity(intent);
    }

}
