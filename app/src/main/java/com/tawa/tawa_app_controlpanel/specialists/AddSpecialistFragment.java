package com.tawa.tawa_app_controlpanel.specialists;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tawa.tawa_app_controlpanel.model.GalleryImage;
import com.tawa.tawa_app_controlpanel.model.Specialist;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddSpecialistFragment extends Fragment {


    //   ViewModel viewModel;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("specialists");
    private CollectionReference galleryRef;
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
    EditText jobTitle;
    EditText description;
    Button addbtn;
    Button cancelbtn;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    viewModel = ViewModelProviders.of(this).get(AddSpecialistViewModel.class);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);

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
        jobTitle = view.findViewById(R.id.editText_jobtitle);
        description = view.findViewById(R.id.editText_description);

        email = view.findViewById(R.id.editText_email);
        addbtn = view.findViewById(R.id.button_update);
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


    public void addSpecialist(String name, String jobTitle, String imageUrl, String speciality, String address, String phone, String email, String region, String governorate, Boolean visibility) {


        Specialist specialist = new Specialist(
                name,
                jobTitle,
                imageUrl,
                getArguments().getString("speciality"),
                address,
                phone,
                email,
                getArguments().getString("region"),
                "سوسة",
                visibility


        );

        notebookRef.add(specialist).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                galleryRef = db.collection("specialists").document(documentReference.getId()).collection("gallery");

                for (int i = 1; i <= 6; i++) {


                    GalleryImage galleryImage = new GalleryImage("", "empty");
                    galleryRef.document(String.valueOf(i)).set(galleryImage);

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
                                    String sjobTitle = jobTitle.getText().toString();
                                    String sdescription = description.getText().toString();
                                    String saddress = address.getText().toString();
                                    String semail = email.getText().toString();
                                    String sphone = phone.getText().toString();
                                    assert getArguments() != null;
                                    String speciality = getArguments().getString("speciality");
                                    String region = getArguments().getString("region");
                                    String governorate = "سوسة";


                                    addSpecialist( sname, sjobTitle, simageUrl, speciality, saddress, sphone, semail, region, governorate, true);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);

    }
}