package si.kcclass.bbmonandroidclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	private class DownloadObservablesList extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... urls) {
			return downloadServersList(urls[0]);
		}
		
		private JSONArray downloadServersList(String url) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient(
						new BasicHttpParams());
				HttpGet httpGetRequest = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGetRequest);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(
			    		httpResponse.getEntity().getContent(), "UTF-8"));
			    String json = reader.readLine();
			    JSONArray serverList = new JSONArray(json);
			    return serverList;
			} catch (Exception e) {
				Log.e(TAG, "Downloading server list failed.", e);
				return null;
			}
		}
		
        @Override
        protected void onPostExecute(JSONArray result) {
            setContentView(R.layout.activity_main);
            // We need to use a different list item layout for devices older than Honeycomb
            int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
            
            List<String> observables = new ArrayList<String>();
            for (int i=0; i<result.length(); i++) {
				try {
					JSONObject jsonObj;
					jsonObj = result.getJSONObject(i);
					observables.add(jsonObj.getString("name"));
				} catch (JSONException e) {
					Log.e(TAG, "Error adding json object to the observables list.", e);
				}
            }
            
            ListView observablesListView = (ListView)findViewById(R.id.observable_list);
            observablesListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, 
            		layout, observables));

        }
	}
		
    private void loadServerList() {
    	new DownloadObservablesList().execute(
    			"http://bbmonserver.cloudfoundry.com/server-instances");
    }

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
    @Override
    public void onStart() {
        super.onStart();
        loadServerList();
    }

}
