package pt.ua.nextweather.ui;

import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
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

    public WeatherListAdapter(Context context, LinkedList<Weather> weathers,MainActivity self){
        mInflater = LayoutInflater.from(context);
        weatherStates = weathers;
        toplevel = self;
        if (self.findViewById(R.id.data_holder)!=null)
            lands = true;
    }
    private final LinkedList<Weather> weatherStates;
    private LayoutInflater mInflater;
    private MainActivity toplevel;
    private boolean lands;
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
        String weathertype = toplevel.weatherDescriptions.get(current.getIdWeatherType()).getDescIdWeatherTypePT();
        if (lands){ //i dont want to make another layout file
            holder.textfield.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
            holder.header.setTextSize(TypedValue.COMPLEX_UNIT_DIP,45);
        }
        String fieldtext = "Temp: ";
        double minT = current.getTMin();
        if (minT<10)
        fieldtext+="<font color=\"#33F9FF\">"+minT+"Cª</font> - ";
        else if(minT<25)
        fieldtext+="<font color=\"#F8C13A\">"+minT+"Cª</font> - ";
        else if (minT<32)
        fieldtext+="<font color=\"#FFB533\">"+minT+"Cª</font> - ";
        else fieldtext+="<font color=\"#FF6833\">"+minT+"Cª</font> - ";

        double maxT = current.getTMax();
        if (maxT<10)
            fieldtext+="<font color=\"#33F9FF\">"+maxT+"Cª</font> \n";
        else if(maxT<25)
            fieldtext+="<font color=\"#F8C13A\">"+maxT+"Cª</font> \n";
        else if (maxT<32)
            fieldtext+="<font color=\"#FFB533\">"+maxT+"Cª</font> \n";
        else fieldtext+="<font color=\"#FF6833\">"+maxT+"Cª</font>\n ";
        fieldtext+="<br>Risco de Precipitação: ";
        double prec = current.getPrecipitaProb();
        if (prec<10)
        fieldtext+="<font color=\"#40C528\">"+prec+"</font>\n";
        else if (prec<20)
            fieldtext+="<font color=\"#FFB533\">"+prec+"</font>\n";
        else
            fieldtext+="<font color=\"#FF6833\">"+prec+"</font>\n<br>";
        fieldtext+="Vento de Classe "+current.getClassWindSpeed()+" ,direção "+current.getPredWindDir()+" ";
        switch(current.getPredWindDir()){
            case "N":fieldtext+="\u2191";break;
            case "W":fieldtext+="\u2190";break;
            case "E":fieldtext+="\u2192";break;
            case "S":fieldtext+="\u2193";break;
            case "NE":fieldtext+="\u2197";break;
            case "NW":fieldtext+="\u2196";break;
            case "SE":fieldtext+="\u2198";break;
            case "SW":fieldtext+="\u2199";break;

        }
        holder.textfield.setText(Html.fromHtml(fieldtext));
        holder.header.setText(current.getForecastDate() + " - "+weathertype);
    }

    @Override
    public int getItemCount() {
        return weatherStates.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        public TextView textfield;
        public TextView header;
        public final WeatherListAdapter mAdapter;
        public WeatherViewHolder(@NonNull View itemView,WeatherListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            header = itemView.findViewById(R.id.data_header);
            textfield = itemView.findViewById(R.id.maintext);
        }
    }
}
