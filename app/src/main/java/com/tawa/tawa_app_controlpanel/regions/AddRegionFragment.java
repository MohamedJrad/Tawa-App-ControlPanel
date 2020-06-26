package com.tawa.tawa_app_controlpanel.regions;


import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tawa.tawa_app_controlpanel.model.Region;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.tawa.tawa_app_controlpanel.R;


public class AddRegionFragment extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("regions");


    EditText editText;
    Button addbtn;
    Button cancelbtn;
    Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_region, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);

        editText = view.findViewById(R.id.editTextText_newspeciality);

        addbtn = view.findViewById(R.id.button_update);
        cancelbtn = view.findViewById(R.id.button_cancel);


        addbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRegion();
                getActivity().onBackPressed();

            }
        }));
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
    }


    public void addRegion() {
        String regionName = editText.getText().toString();
        Region region = new Region(regionName, "سوسة");

        notebookRef.add(region);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);

    }
}