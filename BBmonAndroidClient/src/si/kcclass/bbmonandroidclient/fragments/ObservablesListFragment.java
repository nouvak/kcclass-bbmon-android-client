package si.kcclass.bbmonandroidclient.fragments;

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

import si.kcclass.bbmonandroidclient.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ObservablesListFragment extends ListFragment {
	
	private static final String TAG = "MainActivity";
	
	OnObservableSelectedListener mCallback;
	
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnObservableSelectedListener {
        /** Called by ObservablesListFragment when a list item is selected */
        public void onObservableSelected(int position);
    }


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
            // We need to use a different list item layout for devices older than Honeycomb
            int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
            
            List<String> observables = new ArrayList<String>();
            for (int i=0; i<result.length(); i++) {
				try {
					/*JSONObject jsonObj;
					jsonObj = result.getJSONObject(i);
					observables.add(jsonObj.getString("name"));*/					
					observables.add(result.getString(i));

				} catch (JSONException e) {
					Log.e(TAG, "Error adding json object to the observables list.", e);
				}
            }

            // Create an array adapter for the list view, using the Ipsum headlines array
            setListAdapter(new ArrayAdapter<String>(getActivity(), layout, observables));
        }
	}
		
    private void loadObservablesList() {
    	//new DownloadObservablesList().execute(
    	//		"http://bbmonserver.cloudfoundry.com/server-instances");
    	new DownloadObservablesList().execute(
    			"http://kcclass-bbmon-server.cloudfoundry.com/server-instances");
    }
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an array adapter for the list view, using the Ipsum headlines array
        //setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
        loadObservablesList();
    }
    
    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.observable_details_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnObservableSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onObservableSelected(position);
        
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }

}
