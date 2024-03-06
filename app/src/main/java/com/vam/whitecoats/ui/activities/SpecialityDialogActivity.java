package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.RestUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * /**
 * <h1>SpecialityDialogActivity is a Dialog to display all specialities!</h1>
 * <p>
 * <p>
 * A simple {@link BaseActionBarActivity} subclass.
 * Executes upon tapping on speciality section of Create Post.
 *
 * @author satyasarathim
 * @version 1.0
 * @since 03-08-2017
 */
public class SpecialityDialogActivity extends AppCompatActivity {
    public static final String TAG = SpecialityDialogActivity.class.getSimpleName();
    ArrayList<HashMap<String, String>> itemsList;
    ListView itemsListView;
    TextView labelHeaderTxtVw, labelWarningTxtVw, selectAllTxtVw;
    CheckBox selectAllChkBox;
    Button cancelBtn, okBtn;
    CustomListAdapter customListAdapter;
    String dialogOption = null;
    int selectionCount = 0;

    /**
     * <p>
     * Called when the activity is first created.This method also provides you with a Bundle
     * containing the activity's previously frozen state, if there was one.
     * Always followed by {@link #onStart()}. </p>
     *
     * @param savedInstanceState If the Activity is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_speacility_dialog);
        itemsListView = (ListView) findViewById(R.id.itemListVw);
        labelHeaderTxtVw = (TextView) findViewById(R.id.labelHeaderTxt);
        labelWarningTxtVw = (TextView) findViewById(R.id.labelWarningTxt);
        selectAllTxtVw = (TextView) findViewById(R.id.selectAllTxtVw);
        selectAllChkBox = (CheckBox) findViewById(R.id.selectAllChkBox);
        cancelBtn = (Button) findViewById(R.id.cancelButton);
        okBtn = (Button) findViewById(R.id.okButton);
        // Get the value from past Activity and check/uncheck the "Select All" CheckBox
        boolean isAllSelected = false;
        Intent intent = getIntent();
        if (intent != null) {
            itemsList = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra(RestUtils.KEY_ITEMS_LIST);
            dialogOption = intent.getStringExtra(RestUtils.DIALOG_OPTION);
            isAllSelected = intent.getBooleanExtra(RestUtils.KEY_IS_ALL_SELECTED, true);
            selectionCount = intent.getIntExtra(RestUtils.KEY_SELECTED_ITEM_COUNT, 0);
        }
        if (selectionCount == 0) {
            labelHeaderTxtVw.setText("Select");
        } else {
            labelHeaderTxtVw.setText(selectionCount + " Selected");
        }
        if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS)) {
            selectAllTxtVw.setText(getString(R.string.label_all_department));
            labelWarningTxtVw.setText(R.string.label_no_memeber_group_selected);
        } else {
            selectAllTxtVw.setText(getString(R.string.label_default_specialty));
            labelWarningTxtVw.setText(R.string.label_no_speciality_selected);
        }
        //on first launch "Select All" will be checked.
        if (isAllSelected)
            selectAllChkBox.setChecked(true);
        else
            selectAllChkBox.setChecked(false);
        /*
         * Prepare the List Adapter
         */
        customListAdapter = new CustomListAdapter(this, itemsList, dialogOption);
        itemsListView.setAdapter(customListAdapter);
        /*
         * Prepare All Listener
         */
        final int totalItemsCount = itemsList.size();
        selectAllChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectAllChkBox.isChecked()) {
                    selectionCount = totalItemsCount;
                    updateCount(totalItemsCount, totalItemsCount);
                    checkAllOrNone(true, totalItemsCount);
                } else {
                    selectionCount = 0;
                    updateCount(0, totalItemsCount);
                    checkAllOrNone(false, totalItemsCount);
                }
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectionCount != 0) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(RestUtils.KEY_ITEMS_LIST, itemsList);
                    resultIntent.putExtra(RestUtils.DIALOG_OPTION, dialogOption);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * <p>
     * Called when the activity will start interacting with the user.
     * At this point your activity is at the top of the activity stack, with user input going to it.
     * Always followed by {@link #onPause()} ()}. </p>
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
    }

    /**
     * <p>
     * The final call you receive before your activity is destroyed.
     * This can happen either because the activity is finishing (someone called finish() on it, or
     * because the system is temporarily destroying this instance of the activity to save space. </p>
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        dialogOption = null;
        itemsList.clear();
    }


    /*
     * Adapter Class
     */
    public class CustomListAdapter extends BaseAdapter {
        Activity mContext;
        String mDialogOption;
        ArrayList<HashMap<String, String>> mItemsList;


        public CustomListAdapter(Activity context, ArrayList<HashMap<String, String>> itemsList, String dialogOption) {
            this.mContext = context;
            this.mDialogOption = dialogOption;
            this.mItemsList = itemsList;
        }

        @Override
        public int getCount() {
            return mItemsList.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ItemViewHolder itemViewHolder;
            HashMap<String, String> item = mItemsList.get(i);
            if (view == null) {
                itemViewHolder = new ItemViewHolder();
                final LayoutInflater sInflater = (LayoutInflater) mContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                view = sInflater.inflate(R.layout.preferences_child, null, false);
                itemViewHolder.itemTextView = (TextView) view.findViewById(R.id.speciality_textView);
                itemViewHolder.itemCheckBox = (CheckBox) view.findViewById(R.id.speciality_checkBox);
                view.setTag(itemViewHolder);
            } else {
                itemViewHolder = (ItemViewHolder) view.getTag();
            }

            itemViewHolder.itemTextView.setTypeface(null, Typeface.NORMAL);
            if (mDialogOption != null && mDialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS)) {
                itemViewHolder.itemTextView.setText(item.get(RestUtils.DEPARTMENT_NAME));
                itemViewHolder.itemTextView.setEllipsize(TextUtils.TruncateAt.END);
                itemViewHolder.itemTextView.setMaxLines(2);
            } else {
                itemViewHolder.itemTextView.setText(item.get(RestUtils.SPECIALITY_NAME));
                itemViewHolder.itemTextView.setEllipsize(TextUtils.TruncateAt.END);
                itemViewHolder.itemTextView.setSingleLine(true);
            }
            if (Boolean.parseBoolean(item.get(RestUtils.KEY_ISSELECTED))) {
                itemViewHolder.itemCheckBox.setChecked(true);
            } else {
                itemViewHolder.itemCheckBox.setChecked(false);
            }
            itemViewHolder.itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                     * On click of Check box
                     */
                    if (itemViewHolder.itemCheckBox.isChecked()) {
                        //Update the Check status as true in main List Object
                        itemsList.get(i).put(RestUtils.KEY_ISSELECTED, Boolean.toString(true));
                        // Increase select count Txt view
                        updateCount(selectionCount += 1, getCount());

                    } else {
                        //Update the Check status as false in main List Object
                        itemsList.get(i).put(RestUtils.KEY_ISSELECTED, Boolean.toString(false));
                        // Decrease select count Txt view
                        updateCount(selectionCount -= 1, getCount());
                    }
                }
            });

            return view;
        }


    }

    static class ItemViewHolder {
        TextView itemTextView;
        CheckBox itemCheckBox;
    }

    private void updateCount(int selectedItemCount, int totalItemCount) {
        Log.i(TAG, "updateCount()");
        // If all items selected, then check the "Select All" checkbox
        if (selectedItemCount == totalItemCount) {
            selectAllChkBox.setChecked(true);
        } else {
            selectAllChkBox.setChecked(false);

        }
        // Update UI items count
        if (selectedItemCount == 0) {
            labelHeaderTxtVw.setText("Select");
            labelWarningTxtVw.setVisibility(View.VISIBLE);
        } else {
            labelHeaderTxtVw.setText(selectedItemCount + " Selected");
            labelWarningTxtVw.setVisibility(View.GONE);
        }

    }

    private void checkAllOrNone(boolean isChecked, int count) {
        Log.i(TAG, "checkAllOrNone()");
        for (int index = 0; index < count; index++) {
            itemsList.get(index).put(RestUtils.KEY_ISSELECTED, Boolean.toString(isChecked));
        }
        customListAdapter.notifyDataSetChanged();

    }


}
