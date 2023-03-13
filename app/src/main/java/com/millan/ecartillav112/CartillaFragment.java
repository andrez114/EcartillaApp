package com.millan.ecartillav112;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartillaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartillaFragment extends Fragment {

    List<ListElement> elements;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;

    public CartillaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartillaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartillaFragment newInstance(String param1, String param2) {
        CartillaFragment fragment = new CartillaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_cartilla, container, false);
        init();
        return vista;
    }

    public void init(){
        elements= new ArrayList<>();
        elements.add(new ListElement("#773053", "orientacion Alimentaria","desnutrision","24 de noviembre"));
        elements.add(new ListElement("#482929", "vacuna","influenza","2 de julio"));
        elements.add(new ListElement("#490829", "orientacion sexual","parto","15 de mayo"));
        elements.add(new ListElement("#147289", "orientacion dental","hilo dental","6 de enero"));
        elements.add(new ListElement("#482929", "orientación dedicada a la prevención de adicciones",
                "hacemos la mas atenta invitación a los jovenes de 12 a 17 años " +
                        "a la platica acerca de la prevención de adicciones el dia 15 de febrero ","30 de enero del 2022"));
        elements.add(new ListElement("#948274", "defectos visuales","prueba de vista","8 de octubre"));
        elements.add(new ListElement("#948274", "defectos visuales","prueba de vista","8 de octubre"));
        elements.add(new ListElement("#948274", "defectos visuales","prueba de vista","8 de octubre"));
        elements.add(new ListElement("#948274", "defectos visuales","prueba de vista","8 de octubre"));
        elements.add(new ListElement("#948274", "defectos visuales","prueba de vista","8 de octubre"));

        ListAdapter listAdapter = new ListAdapter(elements, getContext(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                moveToDescription(item);
            }
        });
        RecyclerView recyclerView= vista.findViewById(R.id.listRecycle_cartilla);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);

    }

    public void moveToDescription(ListElement item){
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        intent.putExtra("ListElement",item);
        startActivity(intent);
    }
}