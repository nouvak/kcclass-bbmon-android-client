package si.kcclass.bbmonandroidclient;

import java.util.List;

import si.kcclass.bbmonandroidclient.domain.Metric;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MetricAdapter extends ArrayAdapter<Metric> {

	private List<Metric> items;

	public MetricAdapter(Context context, int textViewResourceId,
			List<Metric> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.row_metric, null);
		}
		Metric metric = items.get(position);
		if (metric != null) {
			TextView textLabel = (TextView) v.findViewById(R.id.label);
			if (textLabel != null) {
				textLabel.setText(metric.getName());
			}
			ImageView imageLed = (ImageView) v.findViewById(R.id.led);
			if (imageLed != null) {
				Drawable imageDrawable = getDrawableFromStatus(metric.getColor());
				imageLed.setImageDrawable(imageDrawable);
			}
		}
		return v;
	}
	
	private Drawable getDrawableFromStatus(String status) {
		if (status.equals("green")) {
			return getContext().getResources().getDrawable(R.drawable.led_green);
		} else if (status.equals("red")) {
			return getContext().getResources().getDrawable(R.drawable.led_red);
		} else if (status.equals("yellow")) {
			return getContext().getResources().getDrawable(R.drawable.led_yellow);
		} else {
			return getContext().getResources().getDrawable(R.drawable.led_grey);
		}
	}
}
