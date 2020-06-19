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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Region;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditSpecialistFragment extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    private DocumentReference documentReference;
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
    Button deletebtn;
    ProgressBar progressBar;
    Switch aswitch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notebookRef = db.collection("specialists");
        documentReference = db.collection("specialists").document(getArguments().getString("id"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_specialist, container, false);
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
        deletebtn = view.findViewById(R.id.button_delete);
        aswitch = view.findViewById(R.id.aswitch);

        aswitch.setChecked(getArguments().getBoolean("visibility"));
        name.setText(getArguments().getString("name"));
        address.setText(getArguments().getString("address"));
        phone.setText(getArguments().getString("phone"));
        email.setText(getArguments().getString("email"));

        Picasso.get().load(getArguments().getString("imageUrl")).into(profile_image);


        addbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    uploadFile();
                    updateSpecialist();
                } else {
                    updateSpecialist();
                    getActivity().onBackPressed();
                }


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
                documentReference.delete();
                getActivity().onBackPressed();
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }


    public void updateSpecialist() {
//            Map<String, Object> userMap = new HashMap<>();
//
//
//            //  String regionName = editText.getText().toString();
//            //        Region region = new Region(regionName, "سوسة");


        String name = this.name.getText().toString();
        String address = this.address.getText().toString();
        String phone = this.phone.getText().toString();
        String email = this.email.getText().toString();
        Boolean visibility=this.aswitch.isChecked();


        documentReference.update("name", name);
        documentReference.update("address", address);
        documentReference.update("phone", phone);
        documentReference.update("email", email);
        documentReference.update("visibility",visibility);

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


                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();

                                    documentReference.update("imageUrl", imageUrl);
                                    updateSpecialist();
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
}
