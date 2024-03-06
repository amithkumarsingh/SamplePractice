package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.ui.interfaces.CountListener;
import com.vam.whitecoats.utils.FileDetails;

import java.util.ArrayList;

public class AllFilesListAdapter extends BaseAdapter {
    private int limit;
    private CountListener countListener;
    private LayoutInflater mInflater;
    private Context mContext;
    ArrayList<FileDetails> filesArrayList;
    private String fileType;
    private ArrayList<Integer> selectedlist = new ArrayList<>();

    public AllFilesListAdapter(Context context, ArrayList<FileDetails> fileList, String mFileType, CountListener listener,int filesLimit){
        mContext=context;
        filesArrayList=fileList;
        fileType=mFileType;
        mInflater                = LayoutInflater.from(context);
        countListener=listener;
        limit=filesLimit;
    }
    @Override
    public int getCount() {
        return filesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        AllFilesListAdapter.Holder holder = null;
        View vi  = view;
        if (vi == null) {
            holder = new AllFilesListAdapter.Holder();
            vi                               = mInflater.inflate(R.layout.fileslist_row, null);
            holder.fileNameTextView                   = (TextView) vi.findViewById(R.id.file_name);
            holder.fileTypeImageView              = (ImageView) vi.findViewById(R.id.file_type_img);
            holder.checkBox                  = (CheckBox)vi.findViewById(R.id.file_select_check);
            holder.file_item_layout         = (LinearLayout)vi.findViewById(R.id.file_item_layout);

            vi.setTag(holder);
        } else {
            holder = (AllFilesListAdapter.Holder) vi.getTag();
        }
        if(selectedlist.contains(position)){
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(true);
            holder.file_item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.list_selection_color));
        }
        else{
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setChecked(false);
            holder.file_item_layout.setBackgroundColor(mContext.getResources().getColor(R.color.no_color));
        }
        FileDetails fileObj=filesArrayList.get(position);
        holder.fileNameTextView.setText(fileObj.getFileName());
        if(fileType.equalsIgnoreCase("pdf")){
            holder.fileTypeImageView.setBackgroundResource(R.drawable.ic_pdf_format);
        }else if(fileType.equalsIgnoreCase("audio")){
            holder.fileTypeImageView.setBackgroundResource(R.drawable.ic_attachment_type_audio);
        }
        holder.file_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!selectedlist.contains(position)) {
                        if (limit >= (selectedlist.size()+1)) {
                            selectedlist.add(position);
                            countListener.inviteCount(selectedlist.size());
                        } else {
                            Toast.makeText(mContext, "You can attach maximum 5 files", Toast.LENGTH_SHORT).show();
                        }
                    } else if (selectedlist.contains(position)) {
                        int idq = selectedlist.indexOf(position);
                        selectedlist.remove(idq);
                        countListener.inviteCount(selectedlist.size());
                    }
                    notifyDataSetChanged();
            }

        });
        return vi;
    }

    public class Holder {
        private TextView fileNameTextView;
        private ImageView fileTypeImageView;
        private CheckBox checkBox;
        private LinearLayout file_item_layout;

    }
    public ArrayList<Integer> getSelectedFileList(){
        return selectedlist;
    }
}
