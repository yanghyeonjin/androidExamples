package com.yanghyeonjin.androidexamples.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.model.DailyBoxOfficeList;

import java.util.List;


public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.BoxOfficeViewHolder> {
    List<DailyBoxOfficeList> dailyBoxOfficeLists;
    Context context;

    public BoxOfficeAdapter(List<DailyBoxOfficeList> dailyBoxOfficeLists, Context context) {
        this.dailyBoxOfficeLists = dailyBoxOfficeLists;
        this.context = context;
    }

    @NonNull
    @Override
    public BoxOfficeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box_office, parent, false);
        BoxOfficeViewHolder boxOfficeViewHolder = new BoxOfficeViewHolder(rootView);
        return boxOfficeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoxOfficeViewHolder holder, int position) {
        DailyBoxOfficeList dailyBoxOfficeList = dailyBoxOfficeLists.get(position);
        holder.tvRank.setText(dailyBoxOfficeList.getRank());
        holder.tvMovieName.setText(dailyBoxOfficeList.getMovieNm());
        holder.tvCount1.setText(dailyBoxOfficeList.getAudiCnt()); //일일 관객수
        holder.tvCount2.setText(dailyBoxOfficeList.getAudiAcc()); //누적관객수

    }

    @Override
    public int getItemCount() {
        return (dailyBoxOfficeLists != null ? dailyBoxOfficeLists.size() : 0);
    }

    public class BoxOfficeViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvMovieName, tvCount1, tvCount2;

        public BoxOfficeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRank = itemView.findViewById(R.id.tv_rank);
            tvMovieName = itemView.findViewById(R.id.tv_movie_name);
            tvCount1 = itemView.findViewById(R.id.tv_count1);
            tvCount2 = itemView.findViewById(R.id.tv_count2);
        }
    }
}
