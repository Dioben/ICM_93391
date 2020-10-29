package pt.ua.nextweather.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.LinkedList;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.Weather;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {

    public WeatherListAdapter(Context context, LinkedList<Weather> weathers){
        mInflater = LayoutInflater.from(context);
        weatherStates = weathers;
    }
    private final LinkedList<Weather> weatherStates;
    private LayoutInflater mInflater;
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.weatherlistitem,
                parent, false);
        return new WeatherListAdapter.WeatherViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather current = weatherStates.get(position);
        holder.textfield.setText(current.toString());
    }

    @Override
    public int getItemCount() {
        return weatherStates.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        public TextView textfield;
        public final WeatherListAdapter mAdapter;
        public WeatherViewHolder(@NonNull View itemView,WeatherListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            textfield = itemView.findViewById(R.id.tempdataholder);
        }
    }
}
