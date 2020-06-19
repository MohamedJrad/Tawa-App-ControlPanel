//package com.tawa.tawa_app_controlpanel.specialists;
//
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Handler;
//import android.webkit.MimeTypeMap;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.ViewModel;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//import com.tawa.tawa_app_controlpanel.model.Specialist;
//
//class AddSpecialistViewModel extends ViewModel {
//
//
//
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference notebookRef = db.collection("specialists");
//
//
//    private Uri imageUri;
//    private String imageUrl;
//    private StorageReference storageReference;
//
//
//    public void addRegion(String imageUrl,String name,String address,String phone,String email,String speciality,String region) {
//        String specialistName = name.getText().toString();
//        String specialistAdress = address.getText().toString();
//        String specialistEmail = email.getText().toString();
//        String specialistPhone = phone.getText().toString();
//
//
//        Specialist specialist = new Specialist(specialistName,
//                imageUrl,
//              speciality,
//              //  getArguments().getString("speciality"),
//                dress,
//                specialistPhone,
//                specialistEmail,
//             //   getArguments().getString("region"),
//                "سوسة"
//        );
//
//        notebookRef.add(specialist);
//
//    }
//
//
//
//
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, 1);
//    }
//
//
//
//
//
//
//
//    private void uploadFile() {
//        if (imageUri != null) {
//            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//            fileRef.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setProgress(0);
//                                }
//                            }, 5000);
//                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    imageUrl = uri.toString();
//
//
//
//                                }
//                            });
//                        }
//
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                    progressBar.setProgress((int) progress);
//                }
//            });
//        } else {
//
//        }
//    }
//}
