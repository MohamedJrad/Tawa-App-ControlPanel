package com.tawa.tawa_app_controlpanel.specialities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Region;
import com.tawa.tawa_app_controlpanel.model.Speciality;
import com.tawa.tawa_app_controlpanel.ui.login.LoginActivity;


public class SpecialitiesFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef ;
    private SpecialityAdapter adapter;
SearchView searchView;

    public static SpecialitiesFragment newInstance() {
        return new SpecialitiesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

     notebookRef   = db.collection("regions").document(getArguments().getString("id")).collection("specialities");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specialities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = getActivity().findViewById(R.id.toolbar_title);
searchView=view.findViewById(R.id.searchView);
        assert getArguments() != null;
        text.setText(getArguments().getString("region"));


        FloatingActionButton fb = view.findViewById(R.id.floatingActionButton_specialities);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                String id = getArguments().getString("id");
                Bundle bundle = new Bundle();
                bundle.putString("region", getArguments().getString("region"));
                bundle.putString("id", id);


                Navigation.findNavController(getView()).navigate(R.id.action_specialitiesFragment_to_addSpecialityFragment, bundle);

            }
        });
        Query query = notebookRef.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Speciality> options = new FirestoreRecyclerOptions.Builder<Speciality>()
                .setQuery(query, Speciality.class)
                .build();

        adapter = new SpecialityAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.speciality_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListner(new SpecialityAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Speciality speciality = documentSnapshot.toObject(Speciality.class);
                String id = documentSnapshot.getId();
                Bundle bundle = new Bundle();
                bundle.putString("region", getArguments().getString("region"));
                bundle.putString("speciality", speciality.getName());

                Navigation.findNavController(getView()).navigate(R.id.action_specialitiesFragment_to_specialistsFragment, bundle);

                //   Toast.makeText(getContext(), "position" + position+"id"+ id, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListner(new SpecialityAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(DocumentSnapshot documentSnapshot, int position) {
               Speciality speciality = documentSnapshot.toObject(Speciality.class);
                String id = documentSnapshot.getId();
                Bundle bundle = new Bundle();
                bundle.putString("name", speciality.getName());
                bundle.putString("id",id);
                bundle.putString("region",getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_specialitiesFragment_to_editSpecialityFragment,bundle);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    Query query = notebookRef.orderBy("name", Query.Direction.ASCENDING).startAt(s).endAt(s+"\uf8ff");;
                    FirestoreRecyclerOptions<Speciality> options = new FirestoreRecyclerOptions.Builder<Speciality>()
                            .setQuery(query, Speciality.class)
                            .build();
                    adapter.updateOptions(options);
                }else {

                    Query query1 = notebookRef.orderBy("name", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Speciality> options = new FirestoreRecyclerOptions.Builder<Speciality>()
                            .setQuery(query1, Speciality.class)
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
                Navigation.findNavController(getView()).navigate(R.id.action_specialitiesFragment_to_aboutUsFragment);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
