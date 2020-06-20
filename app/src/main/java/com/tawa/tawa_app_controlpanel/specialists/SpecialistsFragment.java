package com.tawa.tawa_app_controlpanel.specialists;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Region;
import com.tawa.tawa_app_controlpanel.model.Specialist;
import com.tawa.tawa_app_controlpanel.model.Speciality;
import com.tawa.tawa_app_controlpanel.ui.login.LoginActivity;


public class SpecialistsFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("specialists");
    private SpecialistAdapter adapter;
SearchView searchView;
    public static SpecialistsFragment newInstance() {
        return new SpecialistsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specialist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text =getActivity().findViewById(R.id.toolbar_title);
        text.setText(getArguments().getString("speciality")+"/"+getArguments().getString("region"));

        FloatingActionButton fb=view.findViewById(R.id.floatingActionButton_specialist);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle   bundle = new Bundle();
                bundle.putString("region", getArguments().getString("region"));
                bundle.putString("speciality", getArguments().getString("speciality"));

                Navigation.findNavController(getView()).navigate(R.id.action_specialistsFragment_to_addSpecialistFragment2,bundle);

            }
        });


        Query query = notebookRef
                .whereEqualTo("speciality",getArguments().getString("speciality")).whereEqualTo("region",getArguments().getString("region"));
        //.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Specialist> options = new FirestoreRecyclerOptions.Builder<Specialist>()
                .setQuery(query, Specialist.class)
                .build();

        adapter = new SpecialistAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.specialist_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListner(new SpecialistAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Specialist specialist = documentSnapshot.toObject(Specialist.class);
                String id = documentSnapshot.getId();

                //   Navigation.findNavController(getView()).navigate(R.id.action_regionFragment_to_specialitiesFragment,null);

                //     Toast.makeText(getContext(), "position" + position+"id"+ id, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListner(new SpecialistAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(DocumentSnapshot documentSnapshot, int position) {
                Specialist specialist = documentSnapshot.toObject(Specialist.class);
                String id = documentSnapshot.getId();
                Bundle bundle = new Bundle();

                bundle.putString("id",id);
                bundle.putString("name",specialist.getName());
                bundle.putString("address",specialist.getAddress());
                bundle.putString("phone",specialist.getPhone());
                bundle.putString("email",specialist.getEmail());
                bundle.putString("imageUrl",specialist.getImageUrl());
                bundle.putBoolean("visibility",specialist.getVisibility());



                Navigation.findNavController(getView()).navigate(R.id.action_specialistsFragment_to_editSpecialistFragment,bundle);
            }
        });
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    Query query = notebookRef
                            .whereEqualTo("speciality",getArguments().getString("speciality"))
                            .whereEqualTo("region",getArguments().getString("region"))
                            .orderBy("name").startAt(s).endAt(s+"\uf8ff");
                    //.orderBy("name", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Specialist> options = new FirestoreRecyclerOptions.Builder<Specialist>()
                            .setQuery(query, Specialist.class)
                            .build();
                    adapter.updateOptions(options);
                }else {
                    Query query = notebookRef
                            .whereEqualTo("speciality",getArguments().getString("speciality"))
                            .whereEqualTo("region",getArguments().getString("region"));
                    //.orderBy("name", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Specialist> options = new FirestoreRecyclerOptions.Builder<Specialist>()
                            .setQuery(query, Specialist.class)
                            .build();
                    adapter.updateOptions(options);
                }
                return false;
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            case R.id.action_settings:
                Navigation.findNavController(getView()).navigate(R.id.action_specialistsFragment_to_aboutUsFragment);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}