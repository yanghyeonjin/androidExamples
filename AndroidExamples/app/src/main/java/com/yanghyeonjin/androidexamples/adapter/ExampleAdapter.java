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
import com.yanghyeonjin.androidexamples.BackgroundMusicActivity;
import com.yanghyeonjin.androidexamples.BottomNavigationActivity;
import com.yanghyeonjin.androidexamples.ButtonSelectorActivity;
import com.yanghyeonjin.androidexamples.CameraPreviewActivity;
import com.yanghyeonjin.androidexamples.CheckBoxActivity;
import com.yanghyeonjin.androidexamples.ComebackActivity;
import com.yanghyeonjin.androidexamples.CustomNavigationMenuActivity;
import com.yanghyeonjin.androidexamples.DialogActivity;
import com.yanghyeonjin.androidexamples.FacebookLoginActivity;
import com.yanghyeonjin.androidexamples.FirebaseStorageActivity;
import com.yanghyeonjin.androidexamples.GoogleMapActivity;
import com.yanghyeonjin.androidexamples.KakaoLinkActivity;
import com.yanghyeonjin.androidexamples.KakaoLoginActivity;
import com.yanghyeonjin.androidexamples.LoadingAnimationActivity;
import com.yanghyeonjin.androidexamples.LoginActivity;
import com.yanghyeonjin.androidexamples.MediaPlayerActivity;
import com.yanghyeonjin.androidexamples.NaverLoginActivity;
import com.yanghyeonjin.androidexamples.NavigationViewActivity;
import com.yanghyeonjin.androidexamples.OpenCameraGalleryActivity;
import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.RadioButtonActivity;
import com.yanghyeonjin.androidexamples.ServiceActivity;
import com.yanghyeonjin.androidexamples.SharedPreferencesActivity;
import com.yanghyeonjin.androidexamples.ThreadHandlerActivity;
import com.yanghyeonjin.androidexamples.VideoViewActivity;
import com.yanghyeonjin.androidexamples.ViewPagerActivity;
import com.yanghyeonjin.androidexamples.WebViewActivity;
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

        holder.categoryTextView.setText(exampleArrayList.get(position).getCategory());
        holder.titleTextView.setText(exampleArrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedExamTitle = exampleArrayList.get(position).getTitle();
                String selectedExamCategory = exampleArrayList.get(position).getCategory();

                if (selectedExamCategory.equals("홍드로이드")) {
                    switch (selectedExamTitle) {
                        case "SharedPreferences":
                            Intent intent1 = new Intent(context, SharedPreferencesActivity.class);
                            context.startActivity(intent1);
                            break;
                        case "WebView":
                            Intent intent2 = new Intent(context, WebViewActivity.class);
                            context.startActivity(intent2);
                            break;
                        case "CustomNavigationMenu":
                            Intent intent3 = new Intent(context, CustomNavigationMenuActivity.class);
                            context.startActivity(intent3);
                            break;
                        case "CameraPreview":
                            Intent intent4 = new Intent(context, CameraPreviewActivity.class);
                            context.startActivity(intent4);
                            break;
                        case "ThreadHandler":
                            Intent intent5 = new Intent(context, ThreadHandlerActivity.class);
                            context.startActivity(intent5);
                            break;
                        case "Dialog":
                            Intent intent6 = new Intent(context, DialogActivity.class);
                            context.startActivity(intent6);
                            break;
                        case "BackgroundMusic":
                            Intent intent7 = new Intent(context, BackgroundMusicActivity.class);
                            context.startActivity(intent7);
                            break;
                        case "LoadingAnimation":
                            Intent intent8 = new Intent(context, LoadingAnimationActivity.class);
                            context.startActivity(intent8);
                            break;
                        case "MediaPlayer":
                            Intent intent9 = new Intent(context, MediaPlayerActivity.class);
                            context.startActivity(intent9);
                            break;
                        case "GoogleMap":
                            Intent intent10 = new Intent(context, GoogleMapActivity.class);
                            context.startActivity(intent10);
                            break;
                        case "BottomNavigation":
                            Intent intent11 = new Intent(context, BottomNavigationActivity.class);
                            context.startActivity(intent11);
                            break;
                        case "StartActivityForResult":
                            Intent intent12 = new Intent(context, ComebackActivity.class);
                            context.startActivity(intent12);
                            break;
                        case "ButtonSelector":
                            Intent intent13 = new Intent(context, ButtonSelectorActivity.class);
                            context.startActivity(intent13);
                            break;
                        case "LoginSignUp":
                            Intent intent14 = new Intent(context, LoginActivity.class);
                            context.startActivity(intent14);
                            break;
                        case "FullVideo":
                            Intent intent15 = new Intent(context, VideoViewActivity.class);
                            context.startActivity(intent15);
                            break;
                        case "ViewPager":
                            Intent intent16 = new Intent(context, ViewPagerActivity.class);
                            context.startActivity(intent16);
                            break;
                        case "RadioButton":
                            Intent intent17 = new Intent(context, RadioButtonActivity.class);
                            context.startActivity(intent17);
                            break;
                        case "CheckBox":
                            Intent intent18 = new Intent(context, CheckBoxActivity.class);
                            context.startActivity(intent18);
                            break;
                    }

                } else if (selectedExamCategory.equals("Do it! 개정 5판")) {
                    switch (selectedExamTitle) {
                        case "Service":
                            Intent intent19 = new Intent(context, ServiceActivity.class);
                            context.startActivity(intent19);
                            break;
                    }

                } else if (selectedExamCategory.equals("기타")) {
                    switch (selectedExamTitle) {
                        case "OpenCameraGallery":
                            Intent intent20 = new Intent(context, OpenCameraGalleryActivity.class);
                            context.startActivity(intent20);
                            break;
                        case "KakaoLogin":
                            Intent intent21 = new Intent(context, KakaoLoginActivity.class);
                            context.startActivity(intent21);
                            break;
                        case "NaverLogin":
                            Intent intent22 = new Intent(context, NaverLoginActivity.class);
                            context.startActivity(intent22);
                            break;
                        case "FacebookLogin":
                            Intent intent23 = new Intent(context, FacebookLoginActivity.class);
                            context.startActivity(intent23);
                            break;
                        case "Firebase Storage":
                            Intent intent24 = new Intent(context, FirebaseStorageActivity.class);
                            context.startActivity(intent24);
                            break;
                        case "카카오링크":
                            Intent intent25 = new Intent(context, KakaoLinkActivity.class);
                            context.startActivity(intent25);
                            break;
                        case "NavigationView":
                            Intent intent26 = new Intent(context, NavigationViewActivity.class);
                            context.startActivity(intent26);
                            break;
                    }
                }


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
        TextView categoryTextView, titleTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            this.exampleImageView = itemView.findViewById(R.id.iv_image);
            this.categoryTextView = itemView.findViewById(R.id.tv_category);
            this.titleTextView = itemView.findViewById(R.id.tv_title);
        }
    }
}
