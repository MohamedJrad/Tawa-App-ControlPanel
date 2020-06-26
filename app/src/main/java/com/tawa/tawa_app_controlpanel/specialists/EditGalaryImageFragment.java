package com.tawa.tawa_app_controlpanel.specialists;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;


public class EditGalaryImageFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference imageReference;
    private CollectionReference galleryRef;
    private StorageReference storageReference;

    Uri imageUri = null;
    String imageUrl;
    Boolean filechosed=false;

    ImageView galleryImage;
    Button delete;
    Button update;
    Button cancel;
    ProgressBar progressBar;

    private int RESULT_Ok = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_galary_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference("specialists_gallery_images");



        galleryImage = view.findViewById(R.id.galleryImage);
        delete = view.findViewById(R.id.delete_btn);
        update = view.findViewById(R.id.updatebtn);
        cancel = view.findViewById(R.id.cancelbtn);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        galleryRef = db.collection("specialists").document(getArguments().getString("specialist")).collection("gallery");
        imageReference = galleryRef.document(getArguments().getString("id"));
        imageReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = documentSnapshot.getString("url");
                assert url != null;
                if (!url.equals(""))
                    Picasso.get().load(url).into(galleryImage);
                imageUri = Uri.parse(url);
            }
        });


        galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = Uri.parse("R.drawable.avatar");
                ;
                Picasso.get().load(imageUri).placeholder(R.drawable.avatar).into(galleryImage);
                imageUri = null;

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri == null) {
                    imageReference.update("url", "");
                    imageReference.update("state", "empty");
                    getActivity().onBackPressed();

                } else if(filechosed) {
                    uploadFile();
                }
            }
        });
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_Ok && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(galleryImage);
            filechosed=true;

        }

    }


    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "."
                    //  + getFileExtension(imageUri)
            );
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                    imageReference.update("url", imageUrl);
                                    imageReference.update("state", "filled");
                                    getActivity().onBackPressed();

                                }
                            });
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress((int) progress);
                    update.setClickable(false);
                    cancel.setClickable(false);

                }
            });
        } else {

        }
    }

}

