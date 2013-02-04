package si.kcclass.bbmonandroidclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	private class DownloadObservablesList extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... urls) {
			return downloadServersList(urls[0]);
		}
		
		private JSONObject downloadServersList(String url) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient(
						new BasicHttpParams());
				HttpGet httpGetRequest = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGetRequest);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(
			    		httpResponse.getEntity().getContent(), "UTF-8"));
			    String json = reader.readLine();
			    JSONObject jsonObject = new JSONObject(json);
			    return jsonObject;
			} catch (Exception e) {
				Log.e(TAG, "Downloading server list failed.", e);
				return null;
			}
		}
		
        @Override
        protected void onPostExecute(JSONObject result) {
            setContentView(R.layout.activity_main);
            // Displays the HTML string in the UI via a WebView
            //WebView myWebView = (WebView) findViewById(R.id.webview);
            //myWebView.loadData(result, "text/html", null);
        }
	}
	
    private void loadServerList() {
    	new DownloadObservablesList().execute(
    			"http://www.hpgloe.com/json/getrec/?lat=37.234&lon=-122.234");
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
