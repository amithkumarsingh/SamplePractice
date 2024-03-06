package com.vam.whitecoats.ui.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.TextView;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.adapters.AllFilesListAdapter;
import com.vam.whitecoats.ui.interfaces.CountListener;
import com.vam.whitecoats.utils.FileDetails;
import com.vam.whitecoats.utils.RestUtils;
import java.util.ArrayList;
import java.util.Arrays;

public class ListAllFilesActivity extends BaseActionBarActivity implements CountListener {

    private ListView filesList;
    private ArrayList<FileDetails> filesListArray=new ArrayList<>();
    private AllFilesListAdapter filesListAdapter;
    private TextView mTitleTextView;
    private TextView tv_count;
    private String fileType="pdf";
    private int fileLimit=5;
    private ArrayList<FileDetails> selectedFilesList=new ArrayList<>();
    private TextView noFilesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listallfiles);
        filesList=(ListView)findViewById(R.id.filesList);
        noFilesTextView=(TextView)findViewById(R.id.no_files_text);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            fileType=bundle.getString(RestUtils.TAG_FILE_TYPE,"pdf");
            fileLimit= bundle.getInt(RestUtils.TAG_LIMIT,5);
        }
        if(fileType.equalsIgnoreCase("pdf")){
            filesListArray= getAllPdfFiles();
        }else if(fileType.equalsIgnoreCase("audio")){
            filesListArray= getAllAudioFilesList();
        }
        filesListAdapter=new AllFilesListAdapter(this,filesListArray,fileType,this,fileLimit);
        filesList.setAdapter(filesListAdapter);
        filesListAdapter.notifyDataSetChanged();
        if(filesListArray.size()==0){
            noFilesTextView.setVisibility(View.VISIBLE);
            filesList.setVisibility(View.GONE);
        }else{
            noFilesTextView.setVisibility(View.GONE);
            filesList.setVisibility(View.VISIBLE);
        }
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_caseroominvite, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        tv_count = (TextView) mCustomView.findViewById(R.id.invited_contacts);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        next_button.setText(getString(R.string.actionbar_done));
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText("Select");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> selectedPositions = filesListAdapter.getSelectedFileList();
                if(selectedPositions.size()==0){
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }else {
                    for(int i=0;i<selectedPositions.size();i++){
                        selectedFilesList.add(filesListArray.get(selectedPositions.get(i)));
                    }
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("SELECTED_FILES_LIST", selectedFilesList);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    public int inviteCount(int count) {
        if (count == 0) {
            tv_count.setVisibility(View.GONE);
            mTitleTextView.setVisibility(View.VISIBLE);
        } else {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + count + " " + "Selected");
            mTitleTextView.setVisibility(View.GONE);

        }
        return count;
    }

    public ArrayList<FileDetails> getAllPdfFiles() {
        ArrayList<FileDetails> filesList=new ArrayList<>();
        String selection = "_data LIKE '%.pdf'";
        try (Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"), null, selection, null, "_id DESC")) {
            if (cursor== null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
                // this means error, or simply no results found
                return new ArrayList<FileDetails>();
            }
            do {
                String name=cursor.getString(cursor.getColumnIndex("_data"));
                Uri fileUri = Uri.parse(name);
                FileDetails obj=new FileDetails();
                obj.setFileName(fileUri.getLastPathSegment());
                obj.setFilePath(name);
                filesList.add(obj);
            } while (cursor.moveToNext());
             cursor.close();
        }
        return filesList;
    }

    public ArrayList<FileDetails> getAllPdfFilesInStorage(){
        ArrayList<FileDetails> fileDetails=new ArrayList<>();
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
        };

        String mimeType = "application/pdf";

        String whereClause = MediaStore.Files.FileColumns.MIME_TYPE + " IN ('" + mimeType + "')";
        String orderBy = MediaStore.Files.FileColumns.SIZE + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"),
                projection,
                whereClause,
                null,
                orderBy);

        int idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
        int mimeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);
        int addedCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
        int modifiedCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED);
        int nameCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
        int titleCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE);
        int sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

        if (cursor.moveToFirst()) {
            do {
                Uri fileUri = Uri.withAppendedPath(MediaStore.Files.getContentUri("external"), cursor.getString(idCol));
                String mimeType1 = cursor.getString(mimeCol);
                long dateAdded = cursor.getLong(addedCol);
                long dateModified = cursor.getLong(modifiedCol);
                String name=cursor.getString(nameCol);
                FileDetails obj=new FileDetails();
                obj.setFileName(fileUri.getLastPathSegment());
                obj.setFilePath(name);
                fileDetails.add(obj);
                // ...
            } while (cursor.moveToNext());
        }

        return fileDetails;
    }

    private void getExternalPDFFiles() {

        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");

        // every column, although that is huge waste, you probably need
        // BaseColumns.DATA (the path) only.
        String[] projection = null;

        // exclude media files, they would be here also.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE;
        String[] selectionArgs = null; // there is no ? in selection so null here

        String sortOrder = null; // unordered
        //        Cursor allNonMediaFiles = cr.query(uri, projection, selection, selectionArgs, sortOrder);


        // only pdf
        String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
        String[] selectionArgsPdf = new String[]{mimeType};
        Cursor cursor = cr.query(uri, projection, selectionMimeType, selectionArgsPdf, sortOrder);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //your code to implement
                /*Android : Fix code review bugs analyzed by SonarQube -- changed the casting type to Array.toString*/
                Log.d("TAG", Arrays.toString(cursor.getColumnNames()));
                cursor.moveToNext();
            }
        }
        Log.d("TAG", cursor.getCount() + "");
        Log.d("TAG", cursor.getColumnCount() + "");
        cursor.close();

    }

    public ArrayList<FileDetails> getAllAudioFilesList() {
        ArrayList<FileDetails> audioFilesList=new ArrayList<>();
        String[] proj = { MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.DISPLAY_NAME ,MediaStore.Audio.AudioColumns.TITLE};// Can include more data for more details and check it.
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";
        Cursor audioCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, sortOrder);
        if(audioCursor != null){
            if(audioCursor.moveToFirst()){
                do{
                    //if(audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)).contains(".mp3")) {
                        FileDetails obj = new FileDetails();
                        obj.setFilePath(audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                        obj.setFileName(audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                        audioFilesList.add(obj);
                    //}
                }while(audioCursor.moveToNext());
            }
        }
        /*Android : Fix code review bugs analyzed by SonarQube -- Added the null check condition*/
        if (audioCursor != null) {
            audioCursor.close();
        }
        return audioFilesList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
