package pt.ua.nextweather.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.LinkedList;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class ButtonListAdapter extends RecyclerView.Adapter<ButtonListAdapter.ButtonViewHolder> {

    private final LinkedList<String> cNameList;
    public final MainActivity activity;
    private LayoutInflater mInflater;

    public ButtonListAdapter(Context context, LinkedList<String> cities){
        mInflater = LayoutInflater.from(context);
        this.cNameList = cities;
        activity = (MainActivity) context;
    }
    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.buttonlistelement,
                parent, false);
        return new ButtonViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String mCurrent = cNameList.get(position);
        holder.buttonView.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return cNameList.size();
    }

    public class ButtonViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public Button buttonView;
        final ButtonListAdapter mAdapter;
        public ButtonViewHolder(@NonNull View itemView,ButtonListAdapter adapter) {
            super(itemView);
            buttonView = itemView.findViewById(R.id.button);
            mAdapter = adapter;
            buttonView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAdapter.activity.callWeatherForecastForACity(buttonView.getText().toString());
        }
    }
}
