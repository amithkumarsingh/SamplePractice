package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.SubCategoriesInfo;
import com.vam.whitecoats.ui.fragments.ContentFeedsFragment;
import com.vam.whitecoats.ui.interfaces.SubCategoriesItemClickListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SkillingFiltersAdapter extends RecyclerView.Adapter {
    private final RecyclerView skilling_filters_list;
    private Context mContext;
    private int VIEW_ITEM = 1;
    private int VIEW_PROG = 0;
    private SubCategoriesItemClickListener listener;
    public static ArrayList<SubCategoriesInfo> dataList;
    public static int selectedPosition = -1;
    private boolean checkboxclicked = false;
    public static List<Integer> checkBoxSelectedList = new ArrayList<Integer>();
    public static List subCategoryList = new ArrayList();

    public static int checkUncheckCount=0;


    public SkillingFiltersAdapter(Context context, RecyclerView skilling_filters_list, SubCategoriesItemClickListener listener) {
        this.listener = listener;
        this.mContext = context;
        this.skilling_filters_list = skilling_filters_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
       /* View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sub_categories_item, viewGroup, false);
        viewHolder = new DataObjectHolder(view);*/
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.skilling_filters_child_lay, viewGroup, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                DataObjectHolder viewHolder = (DataObjectHolder) holder;
                SubCategoriesInfo dataModel = dataList.get(position);
                viewHolder.skilling_check_box.setChecked(dataModel.getSelected());
                if (dataModel.getSelected()) {
                    ((DataObjectHolder) holder).skilling_check_box.setButtonTintList(mContext.getResources().getColorStateList(R.color.app_green));
                    ((DataObjectHolder) holder).sub_category_title.setTextColor(mContext.getResources().getColor(R.color.app_green));

                } else {
                    ((DataObjectHolder) holder).skilling_check_box.setButtonTintList(mContext.getResources().getColorStateList(R.color.black_radio));
                    ((DataObjectHolder) holder).sub_category_title.setTextColor(mContext.getResources().getColor(R.color.black));
                }

                viewHolder.sub_category_title.setText(dataModel.getSubCategoryName());
                viewHolder.sub_category_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClickedData(dataModel, checkboxclicked);

                    }
                });
//                if (selectedPosition == position) {
//                    ((DataObjectHolder) holder).skilling_check_box.setChecked(true);
//                } else {
//                    ((DataObjectHolder) holder).skilling_check_box.setChecked(false);
//                }
/*
                viewHolder.skilling_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                        if(((DataObjectHolder)holder).skilling_check_box.isChecked()) {

                           // selectedPosition = -1;


                            ((DataObjectHolder)holder).skilling_check_box.setChecked(false);
                            checkboxclicked=false;

                        }else{
                           // selectedPosition = position;
                            checkboxclicked=true;
                        }
                        skilling_filters_list.post(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        });
                        listener.onItemClickedData(dataModel,checkboxclicked);
                    }
                });
*/
                viewHolder.skilling_check_box.setTag(position);
                ((DataObjectHolder) holder).skilling_check_box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Integer pos = (Integer) ((DataObjectHolder) holder).skilling_check_box.getTag();
//                        Toast.makeText(mContext, "Checkbox " + pos + "Clicked!",
//                                Toast.LENGTH_SHORT).show();
                        if (dataList.get(pos).getSelected()) {
                            dataList.get(pos).setSelected(false);


                            selectedPosition = -1;
                            checkboxclicked = false;
                            checkUncheckCount--;
//                            subCategoryList.remove(pos);
                            ((DataObjectHolder) holder).skilling_check_box.setButtonTintList(mContext.getResources().getColorStateList(R.color.black_radio));
                            ((DataObjectHolder) holder).sub_category_title.setTextColor(mContext.getResources().getColor(R.color.black));

                        } else {
                            dataList.get(pos).setSelected(true);

                            selectedPosition = position;
                            checkboxclicked = true;
                            checkUncheckCount++;
//                            try {
//                                subCategoryList.add(pos);
//                            } catch (Exception e) {
//
//                            }
                            ((DataObjectHolder) holder).skilling_check_box.setButtonTintList(mContext.getResources().getColorStateList(R.color.app_green));
                            ((DataObjectHolder) holder).sub_category_title.setTextColor(mContext.getResources().getColor(R.color.app_green));

                        }


//                        if (((DataObjectHolder) holder).skilling_check_box.isChecked()) {
//
//                            selectedPosition = position;
//                            checkboxclicked = true;
////                            checkBoxSelectedList.add(position);
//                            try {
//                                subCategoryList.add(dataModel.getSubCategoryId());
//                            } catch (Exception e) {
//
//                            }
//                            ((DataObjectHolder) holder).skilling_check_box.setButtonTintList(mContext.getResources().getColorStateList(R.color.app_green));
//                            ((DataObjectHolder) holder).sub_category_title.setTextColor(mContext.getResources().getColor(R.color.app_green));
//                        } else {
//                            selectedPosition = -1;
//                            checkboxclicked = false;
////                            checkBoxSelectedList.remove(position);
//                            subCategoryList.remove(dataModel.getSubCategoryId());
//                            ((DataObjectHolder) holder).skilling_check_box.setButtonTintList(mContext.getResources().getColorStateList(R.color.black_radio));
//                            ((DataObjectHolder) holder).sub_category_title.setTextColor(mContext.getResources().getColor(R.color.black));
//                        }
                        skilling_filters_list.post(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        });
                        listener.onItemClickedData(dataModel, checkboxclicked);
                    }
                });
            } else if (holder instanceof ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setPadding(0, 40, 0, 40);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    public void setDataList(ArrayList<SubCategoriesInfo> categoriesList) {
        dataList = categoriesList;
        notifyDataSetChanged();
    }

    public void addDummyItemToList() {
        dataList.add(null);
        notifyItemInserted(this.dataList.size());
    }

    public void removeDummyItemFromList() {
        dataList.remove(null);
        notifyItemRemoved(dataList.size());
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private final CheckBox skilling_check_box;
        private final RelativeLayout sub_category_item;
        //        private final RadioGroup skilling_filters_cb_lay;
        private TextView sub_category_title;

        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            sub_category_title = itemView.findViewById(R.id.sub_category_title);
            skilling_check_box = itemView.findViewById(R.id.skilling_filters_cb);
            sub_category_item = itemView.findViewById(R.id.sub_category_item);
//            skilling_filters_cb_lay=itemView.findViewById(R.id.skilling_filters_cb_lay);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        private final AVLoadingIndicatorView progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (AVLoadingIndicatorView) itemView.findViewById(R.id.avIndicator);

        }
    }
}
