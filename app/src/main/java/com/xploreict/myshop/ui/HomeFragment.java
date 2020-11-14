package com.xploreict.myshop.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xploreict.myshop.CategoryAdapter;
import com.xploreict.myshop.CategoryModel;
import com.xploreict.myshop.GridProductLayoutAdapter;
import com.xploreict.myshop.HomePageAdapter;
import com.xploreict.myshop.HomePageModel;
import com.xploreict.myshop.HorizontalProductScrollAdapter;
import com.xploreict.myshop.HorizontalProductScrollModel;
import com.xploreict.myshop.R;
import com.xploreict.myshop.SliderAdapter;
import com.xploreict.myshop.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerview;
    private CategoryAdapter categoryAdapter;

    ////////////////////////////// banner slider code
    private ViewPager bannersliderviewpager;
    private List<SliderModel> sliderModelList;
    private int currentpage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    /////////////////////////////// banner slider code

    //////////////////// Strip Ad

    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;

    //////////////////// Strip Ad


    /////////////////////// horizontal product layout
    private TextView horizontallayouttitle;
    private Button horizontallayoutviewallbtn;
    private RecyclerView horizontalrecyclerview;

    /////////////////////// horizontal product layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerview = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerview.setLayoutManager(manager);

        List<CategoryModel> list = new ArrayList<CategoryModel>();
        list.add(new CategoryModel("link","Home"));
        list.add(new CategoryModel("link","Electronic"));
        list.add(new CategoryModel("link","Applinces"));
        list.add(new CategoryModel("link","Fernicutre"));
        list.add(new CategoryModel("link","Fassion"));
        list.add(new CategoryModel("link","Toys"));
        list.add(new CategoryModel("link","Sports"));
        list.add(new CategoryModel("link","Wall Arts"));
        list.add(new CategoryModel("link","Books"));
        list.add(new CategoryModel("link","Shoes"));

        categoryAdapter = new CategoryAdapter(list);
        categoryRecyclerview.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        /////////////////////////// banner slider

        bannersliderviewpager = view.findViewById(R.id.banner_slider_view_pager);

        sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.ic_shopping_basket,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_profile,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_green,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.ic_email,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.sample_banner,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_error,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_shopping_basket,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.ic_profile,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_green,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email,"#077AE4"));


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
                    pageLopper();
                }
            }
        };

        bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);

        startBannerslideshow();

        bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLopper();
                stopBannerslideshow();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    startBannerslideshow();
                }
                return false;
            }
        });

        ///////////////////////// banner slider

        //////////////////// Strip Ad
        stripAdImage = view.findViewById(R.id.strip_add_image);
        stripAdContainer = view.findViewById(R.id.strip_add_container);

        stripAdImage.setImageResource(R.drawable.strip_ads);
        stripAdContainer.setBackgroundColor(Color.parseColor("#f7f7f7"));


        //////////////////// Strip Ad


        /////////////////////// horizontal product layout
        horizontallayouttitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontallayoutviewallbtn = view.findViewById(R.id.horizontal_scroll_view_all);
        horizontalrecyclerview = view.findViewById(R.id.horizontal_scroll_layout_recycler_view);

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();

        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_phone,"Redmi 5A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_menu_slideshow,"Redmi 6A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_menu_camera,"Redmi 7A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_email,"Redmi 8A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_email_green,"Redmi 9A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_home,"Redmi 10A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_home_red,"Redmi 11A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_menu_camera,"Redmi 12A","SD 425 Processor","BD. 7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_menu_gallery,"Redmi 13A","SD 425 Processor","BD. 7999/-"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horizontalrecyclerview.setLayoutManager(linearLayoutManager);

        horizontalrecyclerview.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        /////////////////////// horizontal product layout

        /////////////////////// Grid product layout

        TextView gridLayoutTitle = view.findViewById(R.id.grid_model_layout_title);
        Button gridLayoutViewAllbtn = view.findViewById(R.id.grid_product_layout_viewall_btn);
        GridView gridView = view.findViewById(R.id.grid_product_layout_gridview);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));

        /////////////////////// Grid product layout


        /////////////////////// ///////////////////////////

        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(RecyclerView.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();

        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.strip_ads,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.strip_ads,"#ffff00"));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.strip_ads,"#ffff00"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /////////////////////// ///////////////////////////


        return view;
    }

    ///////////////////////// banner slider
    private void pageLopper(){
       if (currentpage == sliderModelList.size()-2){
           currentpage = 2;
           bannersliderviewpager.setCurrentItem(currentpage,false);
       }
        if (currentpage == 1){
            currentpage = sliderModelList.size()-3;
            bannersliderviewpager.setCurrentItem(currentpage,false);
        }
    }

    private void startBannerslideshow(){
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
    ///////////////////////// banner slider
}