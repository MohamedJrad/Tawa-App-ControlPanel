package com.tawa.tawa_app_controlpanel.specialists;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;


public class EditSpecialistInfoFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference galleryRef;

    private CollectionReference notebookRef;
    private DocumentReference imageReference;
    private DocumentReference documentReference;
    private StorageReference storageReference;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_Ok = -1;
    private Uri imageUri1, imageUri2, imageUri3, imageUri4, imageUri5, imageUri6;


    private String imageUrl;
    private ImageView clickedImage;

    private SpecialistGalleryAdapter adapter;


    EditText description;
    EditText facebook;
    EditText instagram;
    Button update;
    Button cancel;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    ProgressBar progressBar;

    Toolbar toolbar;

    public EditSpecialistInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_specialist_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryRef = db.collection("specialists").document(getArguments().getString("id")).collection("gallery");
        documentReference = db.collection("specialists").document(getArguments().getString("id"));
        storageReference = FirebaseStorage.getInstance().getReference("specialists_gallery_images");
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);
        imageView5 = view.findViewById(R.id.imageView5);
        imageView6 = view.findViewById(R.id.imageView6);
        //  description = view.findViewById(R.id.editText_description);
        description = view.findViewById(R.id.editText_description);
        facebook = view.findViewById(R.id.editText_facebook);
        instagram = view.findViewById(R.id.editText_instagram);
        cancel = view.findViewById(R.id.button_cancel);
        update = view.findViewById(R.id.button_update);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        assert getArguments() != null;



                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              String  sdescription=documentSnapshot.getString("description");
                description.setText(sdescription);
                String sfacebbok=documentSnapshot.getString("facebook");
                facebook.setText(sfacebbok);
                String sinstagram=documentSnapshot.getString("instagram");
                instagram.setText(sinstagram);
            }
        });


        facebook.setText(getArguments().getString("facebook"));
        instagram.setText(getArguments().getString("instagram"));


        imageReference = galleryRef.document("1");
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(imageView1);
                imageUri1 = Uri.parse(url);

            }
        });
        imageReference = galleryRef.document("2");
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(imageView2);
                imageUri2 = Uri.parse(url);

            }
        });
        imageReference = galleryRef.document("3");
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(imageView3);
                imageUri3 = Uri.parse(url);
            }
        });
        imageReference = galleryRef.document("4");
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(imageView4);
                imageUri4 = Uri.parse(url);
            }
        });
        imageReference = galleryRef.document("5");
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(imageView5);
                imageUri5 = Uri.parse(url);
            }
        });

        imageReference = galleryRef.document("6");
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(imageView6);
                imageUri6 = Uri.parse(url);
            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("id", "1");
                bundle.putString("specialist", getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_editSpecialistInfoFragment_to_editGalaryImageFragment, bundle);


            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id", "2");
                bundle.putString("specialist", getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_editSpecialistInfoFragment_to_editGalaryImageFragment, bundle);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                bundle.putString("specialist", getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_editSpecialistInfoFragment_to_editGalaryImageFragment, bundle);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id", "4");
                bundle.putString("specialist", getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_editSpecialistInfoFragment_to_editGalaryImageFragment, bundle);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "5");
                bundle.putString("specialist", getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_editSpecialistInfoFragment_to_editGalaryImageFragment, bundle);
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id", "6");
                bundle.putString("specialist", getArguments().getString("id"));


                Navigation.findNavController(getView()).navigate(R.id.action_editSpecialistInfoFragment_to_editGalaryImageFragment, bundle);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateSpecialist();
              getActivity().onBackPressed();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


    }


    public void updateSpecialist() {


        String sdescription = description.getText().toString();
        String sfacebook = facebook.getText().toString();
        String sinstagram = instagram.getText().toString();


        documentReference.update("description", sdescription);
        documentReference.update("facebook", sfacebook);
        documentReference.update("instagram", sinstagram);


    }


}