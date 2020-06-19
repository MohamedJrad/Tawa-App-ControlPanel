package com.tawa.tawa_app_controlpanel.regions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Region;
import com.tawa.tawa_app_controlpanel.model.Specialist;

import java.util.HashMap;
import java.util.Map;


public class EditRegionFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference notebookRef ;


    EditText editText;
    Button addbtn;
    Button cancelbtn;
    Button deletebtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notebookRef=db.collection("regions").document(getArguments().getString("id"));
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

        addbtn = view.findViewById(R.id.button_add);
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
                notebookRef.delete();
                getActivity().onBackPressed();
            }
        });
    }


    public void addRegion() {
        Map<String, Object> userMap = new HashMap<>();


        String regionName = editText.getText().toString();
        Region region = new Region(regionName, "سوسة");

        notebookRef.update("name",regionName);

    }
}