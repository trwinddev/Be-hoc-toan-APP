package com.example.behoctoan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.behoctoan.Models.SetModel;
import com.example.behoctoan.R;
import com.example.behoctoan.activities.QuestionActivity;
import com.example.behoctoan.activities.QuestionActivityFive;
import com.example.behoctoan.activities.QuestionActivityThree;
import com.example.behoctoan.activities.QuestionTwoActivity;
import com.example.behoctoan.databinding.ItemSetsBinding;

import java.util.ArrayList;

public class SetAdapterFive extends RecyclerView.Adapter<SetAdapterFive.viewHolder>{

    Context context;
    ArrayList<SetModel> list;

    public SetAdapterFive(Context context, ArrayList<SetModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sets, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SetModel model = list.get(position);

        holder.binding.setName.setText(model.getSetName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionActivityFive.class);
                intent.putExtra("set", model.getSetName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemSetsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemSetsBinding.bind(itemView);
        }
    }
}
