package com.tawa.tawa_app_controlpanel.specialities;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Speciality;

public class AddSpecialityFragment extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
   private CollectionReference    notebookRef ;

    EditText editText;
    Button addbtn;
    Button cancelbtn;
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notebookRef = db.collection("regions").document(requireArguments().getString("id")).collection("specialities");
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_speciality, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.editTextText_newspeciality);

        addbtn = view.findViewById(R.id.button_update);
        cancelbtn = view.findViewById(R.id.button_cancel);


        addbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpeciality();
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


    public void addSpeciality() {
        String specialityName = editText.getText().toString();
        Speciality speciality = new Speciality(specialityName);

        notebookRef.add(speciality);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);

    }

}