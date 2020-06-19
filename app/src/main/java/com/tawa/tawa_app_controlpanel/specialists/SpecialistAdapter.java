package com.tawa.tawa_app_controlpanel.specialists;

import android.content.Context;
import android.graphics.Color;
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
    private onItemLongClickListener longClickListener;

    public SpecialistAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SpecialistHolder holder, int position, @NonNull Specialist model) {
        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.avatar).into(holder.profileImage);
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
        if(model.getVisibility()){
            holder.visibility.setText("Visible");
            holder.visibility.setTextColor(Color.GREEN);
        }else
        {
            holder.visibility.setText("Invisible");
            holder.visibility.setTextColor(Color.RED);
        }

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
        TextView visibility;

        public SpecialistHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.textView_name);
            address = itemView.findViewById(R.id.textView_address);
            phone = itemView.findViewById(R.id.textView_phone);
            email = itemView.findViewById(R.id.textView_email);
            visibility=itemView.findViewById(R.id.textView_visibility);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && longClickListener != null) {

                        longClickListener.onItemLongClick(getSnapshots().getSnapshot(position), position);
                    }
                    return false;
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListner(onItemClickListener listner) {
        this.listener = listner;
    }

    public interface onItemLongClickListener {
        void onItemLongClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemLongClickListner(SpecialistAdapter.onItemLongClickListener listner) {
        this.longClickListener = listner;
    }
}
