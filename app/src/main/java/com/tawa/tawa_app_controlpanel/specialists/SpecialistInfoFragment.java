package com.tawa.tawa_app_controlpanel.specialists;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.GalleryImage;
import com.tawa.tawa_app_controlpanel.model.Region;
import com.tawa.tawa_app_controlpanel.model.Specialist;
import com.tawa.tawa_app_controlpanel.regions.RegionAdapter;
import com.tawa.tawa_app_controlpanel.ui.login.LoginActivity;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class SpecialistInfoFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference galleryRef;
    private DocumentReference documentReference;
    String facebookUrl = "";
    String instagramUrl = "";

    private SpecialistGalleryAdapter adapter;

    CircleImageView profile_image;
    TextView name;
    TextView jobTitle;
    TextView description;
    ImageView facebookIcon;
    ImageView instagramIcon;
    Button edit;
    RecyclerView recyclerView;


    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        assert getArguments() != null;
        galleryRef = db.collection("specialists")
                .document(getArguments().getString("id"))
                .collection("gallery");
        toolbar = getActivity().findViewById(R.id.toolbar);
        //  toolbar.dismissPopupMenus();


        Query query = galleryRef.whereEqualTo("state", "filled");


        FirestoreRecyclerOptions<GalleryImage> options = new FirestoreRecyclerOptions.Builder<GalleryImage>()
                .setQuery(query, GalleryImage.class)
                .build();

        adapter = new SpecialistGalleryAdapter(options);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_specialist_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryRef = db.collection("specialists").document(getArguments().getString("id")).collection("gallery");
        documentReference = db.collection("specialists").document(getArguments().getString("id"));
        storageReference = FirebaseStorage.getInstance().getReference("specialists_gallery_images");

        profile_image = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.textView_name);
        jobTitle = view.findViewById(R.id.textView_jobTitle);
        description = view.findViewById(R.id.textView_description);
        facebookIcon = view.findViewById(R.id.facebook_icon);
        instagramIcon = view.findViewById(R.id.instagram_icon);
        edit = view.findViewById(R.id.button_edit);
        recyclerView = view.findViewById(R.id.gallery_recycleview);
        name.setText(getArguments().getString("name"));
        jobTitle.setText(getArguments().getString("jobTitle"));
        description.setText(getArguments().getString("description"));
        facebookUrl = getArguments().getString("facebook");
        instagramUrl = getArguments().getString("instagram");
        Picasso.get().load(getArguments().getString("imageUrl")).into(profile_image);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("imageUrl");
                Picasso.get().load(url).into(profile_image);

//                String sname = documentSnapshot.getString("name");
//                name.setText(sname);

//                String sjobtitle = documentSnapshot.getString("jobTitle");
//                jobTitle.setText(sjobtitle);

                String sdescription = documentSnapshot.getString("description");
                description.setText(sdescription);

                String sfacebook = documentSnapshot.getString("facebook");
                facebookUrl = sfacebook;
                if (!facebookUrl.equals("")) {
                    facebookIcon.setVisibility(View.VISIBLE);
                }
                String sinstagram = documentSnapshot.getString("instagram");
                instagramUrl = sinstagram;
                if (!instagramUrl.equals("")) {
                    instagramIcon.setVisibility(View.VISIBLE);
                }
            }
        });


        recyclerview();


        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                //  String facebookUrl = getFacebookPageURL(this);
                facebookIntent.setData(Uri.parse(getFacebookPageURL(getContext(), getArguments().getString("facebook"))));
                startActivity(facebookIntent);
                Log.d("test", facebookIntent.toString())
                ;

            }
        });

        instagramIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert getArguments() != null;
                if (!Objects.equals(getArguments().getString("instagram"), "")) {

                    Uri uri = Uri.parse("http://instagram.com/_u/" + getArguments().getString("instagram"));
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");

                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://instagram.com/" + getArguments().getString("instagram"))));
                    }
                }

            }

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("id", getArguments().getString("id"));
                bundle.putString("facebook", getArguments().getString("facebook"));
                bundle.putString("instagram", getArguments().getString("instagram"));
                bundle.putString("description", getArguments().getString("description"));
                Navigation.findNavController(getView()).navigate(R.id.action_specialistInfoFragment_to_editSpecialistInfoFragment, bundle);
                onDestroy();
            }
        });
    }


    private void recyclerview() {


        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(adapter);


        Log.d("test", "recyclerview");

    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
        Log.d("test", "onstart");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);
        adapter.stopListening();
        Log.d("test", "ondestroy");

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
                Navigation.findNavController(getView()).navigate(R.id.action_specialistInfoFragment_to_aboutUsFragment);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public static String getFacebookPageURL(Context context, String pageid) {
        String result = "";
        final String FACEBOOK_PAGE_ID = pageid;
        final String FACEBOOK_URL = "https://fb.com/" + pageid;

        if (appInstalledOrNot(context, "com.facebook.katana")) {
            try {
                result = "fb://facewebmodal/f?href=https://www.facebook.com/" + FACEBOOK_PAGE_ID;
                // previous version, maybe relevant for old android APIs ?
                // return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } catch (Exception e) {
            }
        } else {
            result = FACEBOOK_URL;
        }
        return result;
    }


}