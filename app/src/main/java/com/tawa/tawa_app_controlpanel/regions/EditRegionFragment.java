package com.tawa.tawa_app_controlpanel.regions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Region;

import java.util.HashMap;
import java.util.Map;


public class EditRegionFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference regionRef;


    EditText editText;
    Button addbtn;
    Button cancelbtn;
    Button deletebtn;
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        regionRef =db.collection("regions").document(getArguments().getString("id"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_region, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.editTextText_newspeciality);

        addbtn = view.findViewById(R.id.button_update);
        cancelbtn = view.findViewById(R.id.button_cancel);
        deletebtn=view.findViewById(R.id.button_delete);
        editText.setText(getArguments().getString("region"));

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

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regionRef.delete();
                getActivity().onBackPressed();
            }
        });
    }


    public void addRegion() {
        Map<String, Object> userMap = new HashMap<>();


        String regionName = editText.getText().toString();
        Region region = new Region(regionName, "سوسة");

        regionRef.update("name",regionName);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);

    }
}