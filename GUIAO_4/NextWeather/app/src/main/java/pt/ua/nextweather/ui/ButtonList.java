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

import pt.ua.nextweather.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonList#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ButtonList extends Fragment {
    private static final LinkedList<String> validcities = new LinkedList<>();
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    private RecyclerView mRecyclerView;
    private ButtonListAdapter mAdapter;
    static{
        String [] x = new String[]{"Aveiro", "Beja", "Braga", "Bragança", "Castelo Branco", "Coimbra", "Évora", "Faro", "Guarda",
                "Leiria", "Lisboa", "Portalegre", "Porto", "Santarém", "Setúbal", "Viana do Castelo", "Vila Real", "Viseu", "Funchal",
                "Porto Santo", "Vila do Porto", "Ponta Delgada", "Angra do Heroísmo", "Santa Cruz da Graciosa", "Velas", "Madalena", "Horta",
                "Santa Cruz das Flores", "Vila do Corvo"};
        for(String y:x){validcities.add(y);}
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ButtonList.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonList newInstance() {
        ButtonList fragment = new ButtonList();
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