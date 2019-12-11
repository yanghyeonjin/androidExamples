package com.yanghyeonjin.androidexamples.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yanghyeonjin.androidexamples.BigImageActivity;
import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.model.Image;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Image> imageArrayList; // 이미지 정보를 담는 ArrayList
    private Context context; // adapter 사용하려면 context 필요

    private static final String LOG_TAG = "ImageAdapter";

    public ImageAdapter(ArrayList<Image> imageArrayList, Context context) {
        this.imageArrayList = imageArrayList;
        this.context = context;
    }

    /* view holder 만들기 */
    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);

        /* holder와 view 연결 */
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    /* 데이터와 view 연결 */
    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, final int position) {
        String imageLocation = imageArrayList.get(position).getLocation();
        StorageReference imageReference = FirebaseStorage.getInstance().getReferenceFromUrl(context.getString(R.string.firebase_storage_location)).child(imageLocation);

        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.ivUploadedImage)
                        .load(uri)
                        .into(holder.ivUploadedImage);

                /* 클릭 시 이미지 크게보기 */
                holder.ivUploadedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, BigImageActivity.class);
                        intent.putExtra("bigImage", String.valueOf(uri));
                        context.startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(LOG_TAG, "이미지 다운로드 실패");
            }
        });
    }

    @Override
    public int getItemCount() {

        /* ArrayList가 null이 아니면 ArrayList 크기를 return, null이면 0 return */
        return (imageArrayList != null ? imageArrayList.size() : 0);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        /* layout 폴더의 item_image 요소들 */
        ImageView ivUploadedImage;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            /* 아이디 연결 */
            this.ivUploadedImage = itemView.findViewById(R.id.iv_uploaded_image);

            /* 이미지 크기 똑같이 설정 */
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int imageWidthHeightPx = displayMetrics.widthPixels / 4;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidthHeightPx, imageWidthHeightPx);
            this.ivUploadedImage.setLayoutParams(layoutParams);

        }
    }
}
