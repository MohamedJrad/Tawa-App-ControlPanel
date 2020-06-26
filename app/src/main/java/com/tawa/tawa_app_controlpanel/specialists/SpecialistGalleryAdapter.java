package com.tawa.tawa_app_controlpanel.specialists;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.GalleryImage;

class SpecialistGalleryAdapter extends FirestoreRecyclerAdapter<GalleryImage, SpecialistGalleryAdapter.SpecialistGalaryHolder> {


    public SpecialistGalleryAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SpecialistGalaryHolder holder, int position, @NonNull GalleryImage model) {
        Picasso.get().load(model.getUrl()).placeholder(R.drawable.gallery).into(holder.image);

        Log.d("test","onBindViewHolder");
    }


    @NonNull
    @Override
    public SpecialistGalaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist_galary_item, parent, false);
        return new SpecialistGalaryHolder(v);
    }


    class SpecialistGalaryHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public SpecialistGalaryHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.galary_image);


        }
    }

}

