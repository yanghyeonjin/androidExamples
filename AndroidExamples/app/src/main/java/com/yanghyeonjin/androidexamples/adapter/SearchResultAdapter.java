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
import com.yanghyeonjin.androidexamples.BoxOfficeRetrofitActivity;
import com.yanghyeonjin.androidexamples.ButtonSelectorActivity;
import com.yanghyeonjin.androidexamples.CalendarActivity;
import com.yanghyeonjin.androidexamples.CameraPreviewActivity;
import com.yanghyeonjin.androidexamples.CheckBoxActivity;
import com.yanghyeonjin.androidexamples.ComebackActivity;
import com.yanghyeonjin.androidexamples.CurrentLocationActivity;
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
import com.yanghyeonjin.androidexamples.RetrofitExample2Activity;
import com.yanghyeonjin.androidexamples.SMSReceiverActivity;
import com.yanghyeonjin.androidexamples.SaveToPDFActivity;
import com.yanghyeonjin.androidexamples.SearchLocalOkhttp3Activity;
import com.yanghyeonjin.androidexamples.ServiceActivity;
import com.yanghyeonjin.androidexamples.SharedPreferencesActivity;
import com.yanghyeonjin.androidexamples.SnackbarAlertDialogActivity;
import com.yanghyeonjin.androidexamples.ThreadHandlerActivity;
import com.yanghyeonjin.androidexamples.ToastActivity;
import com.yanghyeonjin.androidexamples.TouchEventActivity;
import com.yanghyeonjin.androidexamples.VideoViewActivity;
import com.yanghyeonjin.androidexamples.ViewPagerActivity;
import com.yanghyeonjin.androidexamples.WebViewActivity;
import com.yanghyeonjin.androidexamples.model.Example;
import com.yanghyeonjin.androidexamples.model.SearchResultItem;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private ArrayList<SearchResultItem> searchResultItems; // 예제 정보를 담는 ArrayList
    private Context context; // adapter 사용하려면 context 필요

    public SearchResultAdapter(ArrayList<SearchResultItem> searchResultItems, Context context) {
        this.searchResultItems = searchResultItems;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // view holder 만들기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        SearchResultViewHolder searchResultViewHolder = new SearchResultViewHolder(view); // holder and view 연결

        return  searchResultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultViewHolder holder, final int position) {

        // 데이터와 view 연결
        holder.tvPlaceName.setText(searchResultItems.get(position).getPlaceName());
        holder.tvPlaceRoadAddress.setText(searchResultItems.get(position).getPlaceRoadAddress());
        holder.tvPlaceCategory.setText(searchResultItems.get(position).getPlaceCategory());
    }

    @Override
    public int getItemCount() {
        return (searchResultItems != null ? searchResultItems.size() : 0);
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {

        /* layout 폴더의 item_example 요소들 */
        private TextView tvPlaceName, tvPlaceRoadAddress, tvPlaceCategory;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlaceName = itemView.findViewById(R.id.tv_place_name);
            tvPlaceRoadAddress = itemView.findViewById(R.id.tv_place_road_address);
            tvPlaceCategory = itemView.findViewById(R.id.tv_place_category);
        }
    }
}
