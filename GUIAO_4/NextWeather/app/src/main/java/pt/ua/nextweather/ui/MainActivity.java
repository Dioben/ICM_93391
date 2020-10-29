package pt.ua.nextweather.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.WeatherDetails;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity {


    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    private ButtonListAdapter bAdapter;
    private boolean land_tablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fetch data
        getWeatherConditions(); //TODO: MAKE IT SAVE THIS SO I DONT HAVE TO FETCH WHENEVER WE CHANGE ORIENTATION
        getCityList();
        if (findViewById(R.id.data_holder)!=null) land_tablet = true;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getWeatherConditions(){

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                MainActivity.this.weatherDescriptions = descriptorsCollection;
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.i("stage1","failed to get weather types");
            }
        });

    }

    private void getCityList() {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                LinkedList sortTool = new LinkedList(cities.keySet());
                Collections.sort(sortTool);
                //start up menu fragment here
                ButtonList frag = ButtonList.newInstance(sortTool); //was getting NPE so i assume there's some async stuff in here
                getSupportFragmentManager().beginTransaction().replace(R.id.button_frame,frag).commit();
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.i("stage2","city list fetch failed");
                Toast.makeText(getApplicationContext(),"make sure you are connected to the internet and restart",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callWeatherForecastForACity(String city) {
        City cityFound = cities.get(city);
        int localId = cityFound.getGlobalIdLocal();
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                //expand fragments here
             if (land_tablet){//frame data_holder is available
                 WeatherDetails frag = WeatherDetails.newInstance(new LinkedList<Weather>(forecast),city);
                 getSupportFragmentManager().beginTransaction().replace(R.id.data_holder,frag).addToBackStack(null).commit();
             }
                 else{
                 WeatherDetails frag = WeatherDetails.newInstance(new LinkedList<Weather>(forecast),city);
                 getSupportFragmentManager().beginTransaction().replace(R.id.button_frame,frag).addToBackStack(null).commit();
             }
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.i("stage3","forecast fetch failed");
                Toast.makeText(getApplicationContext(),"make sure you are connected to the internet",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
