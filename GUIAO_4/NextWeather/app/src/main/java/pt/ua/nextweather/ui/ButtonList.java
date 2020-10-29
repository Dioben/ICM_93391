package pt.ua.nextweather.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Set;

import pt.ua.nextweather.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonList#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ButtonList extends Fragment {
    public LinkedList<String> validcities;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    private RecyclerView mRecyclerView;
    private ButtonListAdapter mAdapter;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ButtonList.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonList newInstance(LinkedList<String> cities) {

        ButtonList fragment = new ButtonList();
        fragment.validcities = cities;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ButtonList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =
                inflater.inflate(R.layout.fragment_button_list, container, false);
        //instance recycleview here
        mRecyclerView = rootView.findViewById(R.id.button_recycler);
        mAdapter = new ButtonListAdapter(getActivity(),validcities);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        return rootView;

    }
}