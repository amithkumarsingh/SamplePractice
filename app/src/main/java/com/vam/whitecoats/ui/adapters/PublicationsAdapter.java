package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.ValidationUtils;

import java.util.List;

/**
 * Created by lokeshl on 7/1/2015.
 */
public class PublicationsAdapter extends BaseAdapter {
    public Context context;

    private List<PublicationsInfo> publicationsInfo;
    private ValidationUtils validationUtils;

    public PublicationsAdapter(Context context, List<PublicationsInfo> publicationsInfo) {
        this.context = context;
        this.publicationsInfo = publicationsInfo;
        validationUtils = new ValidationUtils(context);
    }


    @Override
    public int getCount() {
        return publicationsInfo.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_publicationsadapter, null);
            holder = new ViewHolder();
            holder.title_text = (TextView) convertView.findViewById(R.id.title_text);
            holder.printTxtVw = (TextView) convertView.findViewById(R.id.textPrint);
            holder.authors_text = (TextView) convertView.findViewById(R.id.authors_text);
            holder.journal_text = (TextView) convertView.findViewById(R.id.journal_text);
            holder.webpage_link_text = (TextView) convertView.findViewById(R.id.webpage_link_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (publicationsInfo != null && publicationsInfo.size() != 0) {
            PublicationsInfo publicationInfo = publicationsInfo.get(position);
            holder.title_text.setText(publicationInfo.getTitle());
            holder.authors_text.setText(publicationInfo.getAuthors());
            holder.journal_text.setText(publicationInfo.getJournal());
            holder.webpage_link_text.setText(publicationInfo.getWeb_page());
            if(publicationInfo.isFirstItem()){
                holder.printTxtVw.setVisibility(View.VISIBLE);
                holder.printTxtVw.setText(publicationInfo.getType().equalsIgnoreCase("print")?"Printed":"Online");
            }else{
                holder.printTxtVw.setVisibility(View.GONE);

            }
            if (holder.title_text.getText().toString().trim().isEmpty()) {
                holder.title_text.setVisibility(View.GONE);
            } else {
                holder.title_text.setVisibility(View.VISIBLE);

            }
            if (holder.authors_text.getText().toString().trim().isEmpty()) {
                holder.authors_text.setVisibility(View.GONE);
            } else {
                holder.authors_text.setVisibility(View.VISIBLE);

            }
            if (holder.journal_text.getText().toString().trim().isEmpty()) {
                holder.journal_text.setVisibility(View.GONE);
            } else {
                holder.journal_text.setVisibility(View.VISIBLE);

            }
            if (holder.webpage_link_text.getText().toString().trim().isEmpty()) {
                holder.webpage_link_text.setVisibility(View.GONE);
            } else {
                holder.webpage_link_text.setVisibility(View.VISIBLE);
                holder.webpage_link_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = holder.webpage_link_text.getText().toString();
                        AppUtil.openLinkInBrowser(url, context);
                    }
                });
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView title_text;
        TextView printTxtVw;
        TextView authors_text;
        TextView journal_text;
        TextView webpage_link_text;
    }

}
