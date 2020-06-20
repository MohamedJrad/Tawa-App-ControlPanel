package com.tawa.tawa_app_controlpanel.regions;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

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
import com.tawa.tawa_app_controlpanel.ui.login.LoginActivity;

public class RegionFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("regions");
    private RegionAdapter adapter;
    SearchView searchView;

    public static RegionFragment newInstance() {
        return new RegionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_region, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView text = getActivity().findViewById(R.id.toolbar_title);
        text.setText("سوسة");
        FloatingActionButton fb = view.findViewById(R.id.floatingActionButton_region);
        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_regionFragment_to_addRegionFragment);

            }
        });

        Query query = notebookRef.whereEqualTo("governorate", "سوسة");
        //  .orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Region> options = new FirestoreRecyclerOptions.Builder<Region>()
                .setQuery(query, Region.class)
                .build();

        adapter = new RegionAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.region_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListner(new RegionAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Region region = documentSnapshot.toObject(Region.class);
                String id = documentSnapshot.getId();
                Bundle bundle = new Bundle();
                bundle.putString("region", region.getName());
                bundle.putString("id", id);


                Navigation.findNavController(getView()).navigate(R.id.action_regionFragment_to_specialitiesFragment, bundle);

                //   Toast.makeText(getContext(), "position" + position+"id"+ id, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListner(new RegionAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(DocumentSnapshot documentSnapshot, int position) {
                Region region = documentSnapshot.toObject(Region.class);
                String id = documentSnapshot.getId();
                Bundle bundle = new Bundle();
                bundle.putString("region", region.getName());
                bundle.putString("id", id);


                Navigation.findNavController(getView()).navigate(R.id.action_regionFragment_to_editRegionFragment2, bundle);
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
                    Query query = notebookRef.orderBy("name").startAt(s).endAt(s+"\uf8ff");
                    FirestoreRecyclerOptions<Region> options = new FirestoreRecyclerOptions.Builder<Region>()
                            .setQuery(query, Region.class)
                            .build();
                    adapter.updateOptions(options);
                }else {
                    Query query = notebookRef.whereEqualTo("governorate", "سوسة");
                    //  .orderBy("name", Query.Direction.ASCENDING);

                    FirestoreRecyclerOptions<Region> options = new FirestoreRecyclerOptions.Builder<Region>()
                            .setQuery(query, Region.class)
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
                Navigation.findNavController(getView()).navigate(R.id.action_regionFragment_to_aboutUsFragment);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

