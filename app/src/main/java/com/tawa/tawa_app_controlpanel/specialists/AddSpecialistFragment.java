package com.tawa.tawa_app_controlpanel.specialists;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tawa.tawa_app_controlpanel.model.Specialist;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddSpecialistFragment extends Fragment {


    //   ViewModel viewModel;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("specialists");
    private StorageReference storageReference;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_Ok = -1;
    private Uri imageUri;
    private String imageUrl;


    CircleImageView profile_image;
    EditText name;
    EditText address;
    EditText phone;
    EditText email;
    Button addbtn;
    Button cancelbtn;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    viewModel = ViewModelProviders.of(this).get(AddSpecialistViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_specialitist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        storageReference = FirebaseStorage.getInstance().getReference("specialists_profile_images");
        progressBar = view.findViewById(R.id.progressBar);
        profile_image = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.editText_name);
        address = view.findViewById(R.id.editText_address);
        phone = view.findViewById(R.id.editText_phone);
        email = view.findViewById(R.id.editText_email);
        addbtn = view.findViewById(R.id.button_add);
        cancelbtn = view.findViewById(R.id.button_cancel);


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        addbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFile();


            }
        }));
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    public void addSpecialist(String imageUrl, String name, String address, String phone, String email, String speciality, String region, String governorate) {


        Specialist specialist = new Specialist(
                imageUrl,
                name,
                address,
                phone,
                email,
                getArguments().getString("speciality"),
                getArguments().getString("region"),
                "سوسة"
        );

        notebookRef.add(specialist);

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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_Ok && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(profile_image);

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



//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // progressBar.setProgress(0);
//                                }
//                            }, 5000);

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();

                                    String simageUrl = imageUrl;
                                    String sname = name.getText().toString();
                                    String saddress = address.getText().toString();
                                    String semail = email.getText().toString();
                                    String sphone = phone.getText().toString();
                                    assert getArguments() != null;
                                    String speciality = getArguments().getString("speciality");
                                    String region = getArguments().getString("region");
                                    String governorate = "سوسة";


                                    addSpecialist(simageUrl, sname, saddress, sphone, semail, speciality, region, governorate);
                                    requireActivity().onBackPressed();
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
                    addbtn.setClickable(false);
                    cancelbtn.setClickable(false);
                    profile_image.setClickable(false);
                }
            });
        } else {

        }
    }

//    private String getFileExtension(Uri uri) {
//        ContentResolver contentResolver = requireContext().getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//
//    }
}