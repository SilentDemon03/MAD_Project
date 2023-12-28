package com.example.authenticationmodule.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.authenticationmodule.databinding.ItemContainerCounsellorBinding;
import com.example.authenticationmodule.model.Counsellor;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

public class CounsellorsAdapter extends RecyclerView.Adapter<CounsellorsAdapter.CounsellorViewHolder>{

    private final List<Counsellor>counsellors;

    public CounsellorsAdapter(List<Counsellor>counsellors){
        this.counsellors = counsellors;
    }

    @NonNull
    @Override
    public CounsellorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerCounsellorBinding itemContainerCounsellorBinding = ItemContainerCounsellorBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CounsellorViewHolder(itemContainerCounsellorBinding);
    }
//    public CounsellorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        // Inflate the view using the specific layout resource R.layout.my_custom_layout
//        View view = inflater.inflate(R.layout.item_container_counsellor, parent, false);
//
//        // Now, use DataBindingUtil to bind the inflated view to your binding class
//        ItemContainerCounsellorBinding itemContainerCounsellorBinding = ItemContainerCounsellorBinding.bind(view);
//
//        return new CounsellorViewHolder(itemContainerCounsellorBinding);
//    }

    @Override
    public void onBindViewHolder(@NonNull CounsellorViewHolder holder, int position) {
        holder.setUserData(counsellors.get(position));
    }

    @Override
    public int getItemCount() {
        return counsellors.size();
    }

    class CounsellorViewHolder extends RecyclerView.ViewHolder{

        ItemContainerCounsellorBinding binding;

        CounsellorViewHolder(ItemContainerCounsellorBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        void setUserData(Counsellor counsellor){
            binding.textName.setText(counsellor.name);
            binding.textEmail.setText(counsellor.email);
            //binding.imageProfile.setImageBitmap(getUserImage(counsellor.image));
        }
    }

    private Bitmap getUserImage(String encodedImage){
        byte[]bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);
    }
}