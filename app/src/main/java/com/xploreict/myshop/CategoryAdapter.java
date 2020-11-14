package com.xploreict.myshop;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String icon = categoryModelList.get(position).getCategoryiconlink();
        String name = categoryModelList.get(position).getCategoryname();
        holder.setCategoryname(name);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryicon;
        private TextView categoryname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryicon = itemView.findViewById(R.id.category_icon);
            categoryname = itemView.findViewById(R.id.category_name);
        }

        private void setCategoryicon(){
            //todo set data from firebase database
        }

        private void setCategoryname(String name){
            categoryname.setText(name);
        }
    }
}
