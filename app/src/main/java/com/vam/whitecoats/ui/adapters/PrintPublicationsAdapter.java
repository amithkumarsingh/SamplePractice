package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.collect.ArrayListMultimap;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.MemPublications;
import com.vam.whitecoats.ui.fragments.PrintPublicationsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swathim on 5/15/2015.
 */
public class PrintPublicationsAdapter extends BaseAdapter{

    private PrintPublicationsFragment mContext;
    private List<MemPublications> add_text=new ArrayList<>();
    private LayoutInflater mInflater;

    public static ArrayListMultimap<String,String> Pdeleted_ids=ArrayListMultimap.create();
    public static ArrayListMultimap<String,String> Pupdated_ids=ArrayListMultimap.create();
    private MemPublications memPublications;
    TextView text_item;
//    EditText edt_item;
//    ImageView editImg,grey_img,green_img;


    public PrintPublicationsAdapter(PrintPublicationsFragment context, List<MemPublications> add_text) {
        mContext      = context;
        this.add_text = add_text;
//        mInflater = LayoutInflater.from(mContext);
        mInflater = (LayoutInflater)mContext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memPublications=new MemPublications();
    }

    @Override
    public int getCount() {
        return add_text.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View vi, ViewGroup parent) {

        View convertView=vi;
        final ViewHolder holder;
        if (convertView == null) {
            holder                    = new ViewHolder();
            convertView               = mInflater.inflate(R.layout.activity_publications_print_list_item, null);
            text_item                 = (TextView)  convertView.findViewById(R.id.print_text);
           /* holder.edt_item           = (EditText)  convertView.findViewById(R.id.item_edit_text);
            holder.editImg            = (ImageView) convertView.findViewById(R.id.edit_item);
            holder.green_img          = (ImageView) convertView.findViewById(R.id.ok_green_item);
            holder.grey_img           = (ImageView) convertView.findViewById(R.id.ok_grey_item);
            holder.item_edit_layout   = (LinearLayout) convertView.findViewById(R.id.edit_layout);
            holder.item_remove_layout = (RelativeLayout)convertView.findViewById(R.id.remove_layout);
            holder.text_remove_pub    = (TextView)convertView.findViewById(R.id.remove_publication_tx);
            holder.text_cancel_pub    = (TextView)convertView.findViewById(R.id.cancel_publication_edit_tx);*/
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        try{

            text_item.setText(add_text.get(position).getName());
           /* text_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        try {
                            View parentRow = (View) v.getParent();
                            ListView listView = (ListView) parentRow.getParent();
                            final int pos = listView.getPositionForView(parentRow);
                            Pupdated_ids.put("" + add_text.get(pos).getId(), ((EditText) v).getText().toString());
                            memPublications.setName(((EditText) v).getText().toString());
                            add_text.remove(position);
                            add_text.add(position, memPublications);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });*/

           /* editImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        View parentRow = (View) v.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int pos = listView.getPositionForView(parentRow);
                        if (add_text.get(pos).getId() != 0) {
                            Pdeleted_ids.put("delete", "" +add_text.get(pos).getId());
                        }
                        add_text.remove(pos);
                        PrintPublicationsAdapter.this.notifyDataSetChanged();
                        Toast.makeText(mContext, "" + pos, Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });*/

            holder.item_edit_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text_item.setVisibility(View.GONE);
                    holder.edt_item.setVisibility(View.VISIBLE);
                    holder.edt_item.setText(add_text.get(position).getName());
                    holder.editImg.setVisibility(View.GONE);
                    holder.green_img.setVisibility(View.VISIBLE);
                    holder.item_remove_layout.setVisibility(View.VISIBLE);
                }
            });



            holder.edt_item.addTextChangedListener(new myTextWatcher(position,holder));

            holder.text_cancel_pub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.item_remove_layout.setVisibility(View.GONE);
                    holder.edt_item.setVisibility(View.GONE);
                    text_item.setVisibility(View.VISIBLE);
                    holder.editImg.setVisibility(View.VISIBLE);
                    holder.green_img.setVisibility(View.GONE);
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    private class myTextWatcher implements TextWatcher {
        int pos;
        ViewHolder holder;
        public myTextWatcher(int position,ViewHolder holder) {
            this.pos = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().trim().length()==0){
                holder.green_img.setVisibility(View.GONE);
                holder.grey_img.setVisibility(View.VISIBLE);
            }else if(s.toString().trim().length()>0){
                holder.green_img.setVisibility(View.VISIBLE);
                holder.grey_img.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    public static class ViewHolder{
        TextView text_remove_pub,text_cancel_pub;
        EditText edt_item;
        ImageView editImg,grey_img,green_img;
        RelativeLayout item_remove_layout;
        LinearLayout item_edit_layout;
    }

}
