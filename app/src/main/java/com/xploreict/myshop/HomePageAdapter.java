package com.xploreict.myshop;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelsList;

    public HomePageAdapter(List<HomePageModel> homePageModelsList) {
        this.homePageModelsList = homePageModelsList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelsList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View bannersliderview = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_add_layout,parent,false);
                return new BannerSliderviewholder(bannersliderview);
            case HomePageModel.STRIP_AD_BANNER:
                View stripadview = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_add_layout,parent,false);
                return new StripAdBannerviewholder(stripadview);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalproductview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalProductviewholder(horizontalproductview);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridproductview = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new GridProductviewholder(gridproductview);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelsList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelsList.get(position).getSliderModelList();
                ((BannerSliderviewholder) holder).setBannersliderviewpager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                int resource = homePageModelsList.get(position).getResource();
                String color = homePageModelsList.get(position).getBackgroundColor();
                ((StripAdBannerviewholder) holder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
               String horizontallayouttitle = homePageModelsList.get(position).getTitle();
               List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelsList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductviewholder) holder).setHorizontalProductlayout(horizontalProductScrollModelList,horizontallayouttitle);
               break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridlayouttitle = homePageModelsList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelsList.get(position).getHorizontalProductScrollModelList();
                ((GridProductviewholder)holder).setGridProductLayout(gridProductScrollModelList,gridlayouttitle);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelsList.size();
    }

    public class BannerSliderviewholder extends
            RecyclerView.ViewHolder{
        private ViewPager bannersliderviewpager;
        private int currentpage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannerSliderviewholder(@NonNull View itemView) {
            super(itemView);
            bannersliderviewpager = itemView.findViewById(R.id.banner_slider_view_pager);

        }


        private void setBannersliderviewpager(final List<SliderModel> sliderModelList){
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            bannersliderviewpager.setAdapter(sliderAdapter);

            bannersliderviewpager.setClipToPadding(false);
            bannersliderviewpager.setPageMargin(20);

            bannersliderviewpager.setCurrentItem(currentpage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentpage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE){
                        pageLopper(sliderModelList);
                    }
                }
            };

            bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);

            startBannerslideshow(sliderModelList);

            bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLopper(sliderModelList);
                    stopBannerslideshow();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                        startBannerslideshow(sliderModelList);
                    }
                    return false;
                }
            });
        }
        private void pageLopper(List<SliderModel> sliderModelList){
            if (currentpage == sliderModelList.size()-2){
                currentpage = 2;
                bannersliderviewpager.setCurrentItem(currentpage,false);
            }
            if (currentpage == 1){
                currentpage = sliderModelList.size()-3;
                bannersliderviewpager.setCurrentItem(currentpage,false);
            }
        }
        private void startBannerslideshow(final List<SliderModel> sliderModelList){
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentpage >= sliderModelList.size()){
                        currentpage = 1;
                    }
                    bannersliderviewpager.setCurrentItem(currentpage++,true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);
        }
        private void stopBannerslideshow(){
            timer.cancel();
        }
    }

    public class StripAdBannerviewholder extends RecyclerView.ViewHolder{

        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerviewholder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_add_image);
            stripAdContainer = itemView.findViewById(R.id.strip_add_container);
        }
        private void setStripAd(int resource,String color){
            stripAdImage.setImageResource(resource);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalProductviewholder extends RecyclerView.ViewHolder{

        private TextView horizontallayouttitle;
        private Button horizontallayoutviewallbtn;
        private RecyclerView horizontalrecyclerview;

        public HorizontalProductviewholder(@NonNull View itemView) {
            super(itemView);
            horizontallayouttitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontallayoutviewallbtn = itemView.findViewById(R.id.horizontal_scroll_view_all);
            horizontalrecyclerview = itemView.findViewById(R.id.horizontal_scroll_layout_recycler_view);
        }
        private void setHorizontalProductlayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){

            horizontallayouttitle.setText(title);

            if (horizontalProductScrollModelList.size() > 8){
                horizontallayoutviewallbtn.setVisibility(View.VISIBLE);
            }else {
                horizontallayoutviewallbtn.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            horizontalrecyclerview.setLayoutManager(linearLayoutManager);

            horizontalrecyclerview.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductviewholder extends RecyclerView.ViewHolder{

        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllbtn;
        private GridView gridView;

        public GridProductviewholder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_model_layout_title);
            gridLayoutViewAllbtn = itemView.findViewById(R.id.grid_product_layout_viewall_btn);
            gridView = itemView.findViewById(R.id.grid_product_layout_gridview);
        }
        private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
        }
    }
}
