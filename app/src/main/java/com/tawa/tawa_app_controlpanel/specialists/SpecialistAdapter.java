package com.tawa.tawa_app_controlpanel.specialists;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.tawa.tawa_app_controlpanel.R;
import com.tawa.tawa_app_controlpanel.model.Specialist;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

class SpecialistAdapter extends FirestoreRecyclerAdapter<Specialist, SpecialistAdapter.SpecialistHolder> {


    private onItemClickListener listener;

    public SpecialistAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SpecialistHolder holder, int position, @NonNull Specialist model) {
        Picasso.get().load(model.getImageUrl()).into(holder.profileImage);
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
    }



    @NonNull
    @Override
    public SpecialistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist_list_item, parent, false);
        return new SpecialistHolder(v);
    }


    class SpecialistHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImage;
        TextView name;
        TextView address;
        TextView phone;
        TextView email;

        public SpecialistHolder(View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.profile_image);
            name= itemView.findViewById(R.id.textView_name);
            address=itemView.findViewById(R.id.textView_address);
            phone=itemView.findViewById(R.id.textView_phone);
            email=itemView.findViewById(R.id.textView_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListner(onItemClickListener listner) {
        this.listener=listner;
    }
}
