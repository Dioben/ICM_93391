package pt.ua.nextweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.ui.ButtonListAdapter;
import pt.ua.nextweather.ui.MainActivity;
import pt.ua.nextweather.ui.WeatherListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherDetails extends Fragment {
    public String city;
    public LinkedList<Weather> weatherStates;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    private RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;




    public WeatherDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WeatherDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherDetails newInstance(LinkedList<Weather> weather,String city) {
        WeatherDetails fragment = new WeatherDetails();
        fragment.weatherStates=weather;
        fragment.city=city;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        getActivity().setTitle("NextWeather");
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(city);
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_list_layout, container, false);
        //instance recycleview here
        mRecyclerView = rootView.findViewById(R.id.list_recycler);
        mAdapter = new WeatherListAdapter(getActivity(),weatherStates,(MainActivity)getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        return  rootView;
    }
}