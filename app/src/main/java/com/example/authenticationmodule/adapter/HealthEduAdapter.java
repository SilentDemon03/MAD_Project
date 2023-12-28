package com.example.authenticationmodule.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.authenticationmodule.EduData;
import com.example.authenticationmodule.R;

public class HealthEduAdapter extends RecyclerView.Adapter<HealthEduAdapter.ViewHolder> {

    EduData[]eduData;
    Context context;

    public HealthEduAdapter(EduData[] eduData, Context context){
        this.eduData = eduData;
        this.context = context;
    }
    @NonNull
    @Override
    public HealthEduAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.health_edu_list, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull HealthEduAdapter.ViewHolder holder, int position) {
        EduData eduDataList = eduData[position];
        holder.name.setText(eduDataList.getName());
        holder.course.setText(eduDataList.getTitle());
        holder.desc.setText(eduDataList.getDesc());
        holder.cardImage.setImageResource(eduDataList.getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, eduDataList.getName()+" Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eduData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, course, desc;
        private ImageView cardImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            course = itemView.findViewById(R.id.course);
            desc = itemView.findViewById(R.id.description);
            cardImage = itemView.findViewById(R.id.imageView);
        }
    }

}
