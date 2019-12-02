package com.yanghyeonjin.androidexamples.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.model.Example;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<Example> exampleArrayList; // 예제 정보를 담는 ArrayList
    private Context context; // adapter 사용하려면 context 필요

    public ExampleAdapter(ArrayList<Example> exampleArrayList, Context context) {
        this.exampleArrayList = exampleArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // view holder 만들기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_example, parent, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view); // holder and view 연결

        return  exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleAdapter.ExampleViewHolder holder, final int position) { // 데이터와 view 연결
        Glide.with(holder.itemView)
                .load(exampleArrayList.get(position).getImage())
                .into(holder.exampleImageView);

        holder.helperTextView.setText(exampleArrayList.get(position).getHelper());
        holder.titleTextView.setText(exampleArrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clickedExampleNumber = exampleArrayList.get(position).getTitle();

                /*switch (clickedExampleNumber) {
                    case "#08":
                        Intent intent08 = new Intent(context, SharedPreferencesActivity.class);
                        context.startActivity(intent08);
                        break;
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return (exampleArrayList != null ? exampleArrayList.size() : 0);
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        /* layout 폴더의 item_example 요소들 */
        ImageView exampleImageView;
        TextView helperTextView, titleTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            this.exampleImageView = itemView.findViewById(R.id.iv_image);
            this.helperTextView = itemView.findViewById(R.id.tv_helper);
            this.titleTextView = itemView.findViewById(R.id.tv_title);
        }
    }
}