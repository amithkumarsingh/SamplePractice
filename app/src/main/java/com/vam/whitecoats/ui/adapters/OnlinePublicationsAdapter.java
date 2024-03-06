package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.ArrayListMultimap;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.MemPublications;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swathim on 5/21/2015.
 */
public class OnlinePublicationsAdapter extends BaseAdapter {

    private Context mContext;
    private List<MemPublications> online_add_text=new ArrayList<>();
    private LayoutInflater mInflater;
    TextView online_text;
    ImageView deleteImg;
    public static ArrayListMultimap<String,String> Odeleted_ids=ArrayListMultimap.create();
    public static ArrayListMultimap<String,String> Oupdated_ids=ArrayListMultimap.create();
    private MemPublications memPublications;

    public OnlinePublicationsAdapter(Context context){
        mContext  = context;
    }


    public OnlinePublicationsAdapter(Context context,List<MemPublications> online_add_text) {
        mContext      = context;
        this.online_add_text = online_add_text;
        mInflater = LayoutInflater.from(mContext);
        memPublications=new MemPublications();
    }

    @Override
    public int getCount() {
        return online_add_text.size();
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_publications_print_list_item, null);
            online_text = (TextView) convertView.findViewById(R.id.print_text);
           // deleteImg   = (ImageView) convertView.findViewById(R.id.edit_item);
        }
        try{

            online_text.setText(online_add_text.get(position).getName());
            online_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        try {
                            View parentRow = (View) v.getParent();
                            ListView listView = (ListView) parentRow.getParent();
                            final int pos = listView.getPositionForView(parentRow);
                            Oupdated_ids.put("" + online_add_text.get(pos).getId(), ((EditText) v).getText().toString());
                            memPublications.setName(((EditText) v).getText().toString());
                            online_add_text.remove(position);
                            online_add_text.add(position, memPublications);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        View parentRow = (View) v.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int pos = listView.getPositionForView(parentRow);
                        if (online_add_text.get(pos).getId() != 0) {
                            Odeleted_ids.put("delete", "" +online_add_text.get(pos).getId());
                        }
                        online_add_text.remove(pos);
                        OnlinePublicationsAdapter.this.notifyDataSetChanged();
                        Toast.makeText(mContext, "" + pos, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }


}
