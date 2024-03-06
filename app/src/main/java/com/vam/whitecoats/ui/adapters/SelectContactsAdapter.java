package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import com.vam.whitecoats.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokesh on 2/24/2015.
 */
public class SelectContactsAdapter extends BaseAdapter{
    Context context;
    private SparseBooleanArray mCheckStates;
    LayoutInflater mInflater;
    private NameFilter filter;
    ArrayList<String> contacts_list;
    ArrayList<String> filter_contacts_list;
    ArrayList<String> numbers_list;
    private List<String> selected = new ArrayList<String>();
    private List<Integer> selecteditems = new ArrayList<Integer>();

    CheckBox checkBox;
    TextView tv_contact_name,tv_contact_no;


    public SelectContactsAdapter(Context mcontext, ArrayList<String> data, ArrayList<String> numbers_array) {
        context = mcontext;
        contacts_list = data;
        filter_contacts_list=data;
        numbers_list  = numbers_array;
        mCheckStates = new SparseBooleanArray(data.size());
        mInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public List<String> getSelected() {
        return selected;
    }


    public List<Integer> getSelectedlistitem() {
        return selecteditems;
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new NameFilter();
        }
        return filter;
    }

    @Override
    public int getCount() {
        return contacts_list.size();
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
        // TODO Auto-generated method stub
        View vi = convertView;
        if (convertView == null)
            vi = mInflater.inflate(R.layout.activity_selectcontacts_listitem, null);
        tv_contact_name = (TextView) vi.findViewById(R.id.contact_name);
        tv_contact_no = (TextView) vi.findViewById(R.id.phone_number);
        checkBox = (CheckBox) vi.findViewById(R.id.checkBox_id);

        tv_contact_name.setText(contacts_list.get(position));
        tv_contact_no.setText(numbers_list.get(position));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((CheckBox) v).isChecked())) {
                    selected.add(numbers_list.get(position));
                    selecteditems.add(position);
                } else {

                    selecteditems.remove(selected.indexOf(numbers_list.get(position)));
                    selected.remove(numbers_list.get(position));
                }
            }
        });
        checkBox.setChecked(selected.contains(numbers_list.get(position)));
        return vi;
    }
    //Filter implementation
    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            final ArrayList<String> list =filter_contacts_list;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }


            results.values = nlist;
            results.count = nlist.size();

            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            try {
                contacts_list = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }

        }

    }
}