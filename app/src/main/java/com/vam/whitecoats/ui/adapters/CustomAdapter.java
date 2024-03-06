package com.vam.whitecoats.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.droidninja.imageeditengine.ImageEditor;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.core.models.AttachmentInfo;
import com.vam.whitecoats.core.models.CaseRoomAttachmentsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.ImageViewer;
import com.vam.whitecoats.ui.activities.PdfViewerActivity;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.RestUtils;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by tejaswini on 08-09-2015.
 */
public class CustomAdapter extends BaseAdapter {

    private  int channelId=0;
    private  int feed_id=0;
    private ArrayList<AttachmentInfo> mPostImages;
    Holder holder;
    Context context;
    ArrayList<CaseRoomAttachmentsInfo> caseRoomAttachmentsInfo = new ArrayList<>();
    public int selectedpostion = -1;
    boolean isFromPost = false;
    private ArrayList<String> urlList = new ArrayList<>();
    private DottedLayoutHolder dottedLayoutHolder;
    private File imageFile;
    /*Refactoring the deprecated startActivityForResults*/
    private NavigateScreenListener screenNavigate;


    public CustomAdapter(Context mContext, ArrayList<AttachmentInfo> postImages,int channel_id,int feedId) {
        context = mContext;
        this.mPostImages = postImages;
        isFromPost = true;
        this.channelId=channel_id;
        this.feed_id=feedId;
        this.screenNavigate=(NavigateScreenListener) mContext;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        // Return true for clickable, false for not
        return false;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count;
        if (isFromPost) {
            count = mPostImages.size();
        } else {
            count = caseRoomAttachmentsInfo.size();
        }
        return count;
    }

    public void selectedpostion(int position) {
        selectedpostion = position;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (isFromPost) {
            return mPostImages.get(position);
        }
        return caseRoomAttachmentsInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        ImageView imageView;
        TextView pdfTv;
        Button editImage, deleteImage;
        FrameLayout frameLayout;
        RelativeLayout overlayLayout;
        RelativeLayout deleteImageNew;

    }

    public class DottedLayoutHolder {
        FrameLayout dottedLayout;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null && !extension.equals("")) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        } else if (extension != null && url.lastIndexOf(".") != -1) {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(ext);
        }
        return type;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        holder = null;
        dottedLayoutHolder = null;
        View inflatedView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (getItem(position) == null) {
            inflatedView = inflater.inflate(R.layout.dotted_border_layout, null);
            dottedLayoutHolder = new DottedLayoutHolder();
            dottedLayoutHolder.dottedLayout = (FrameLayout) inflatedView.findViewById(R.id.dotted_layout);
            dottedLayoutHolder.dottedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context instanceof CreatePostActivity) {
                        ((CreatePostActivity) context).selectImage();
                    }
                }
            });
        } else {
            //if (convertView == null) {
            inflatedView = inflater.inflate(R.layout.listadapter_listitem, null);
            holder = new Holder();
            holder.overlayLayout = (RelativeLayout) inflatedView.findViewById(R.id.overlayLayout);
            holder.frameLayout = (FrameLayout) inflatedView.findViewById(R.id.frameLayout);
            holder.imageView = (ImageView) inflatedView.findViewById(R.id.imageView);
            holder.pdfTv = (TextView) inflatedView.findViewById(R.id.pdfFileName);
            holder.editImage = (Button) inflatedView.findViewById(R.id.editImageView);
            holder.deleteImage = (Button) inflatedView.findViewById(R.id.deleteImageView);
            holder.deleteImageNew=(RelativeLayout) inflatedView.findViewById(R.id.deleteImageNew);
            inflatedView.setTag(holder);
            /*} else {
                holder = (Holder) convertView.getTag();
            }*/
            if (isFromPost) {
                holder.editImage.setVisibility(View.VISIBLE);
//                holder.deleteImage.setVisibility(View.VISIBLE);
                holder.deleteImageNew.setVisibility(View.VISIBLE);
                holder.overlayLayout.setVisibility(View.VISIBLE);
                if (mPostImages.get(position).isEditPost()) {
                    String attachmentType = mPostImages.get(position).getAttachmentType();
                    if (attachmentType.equalsIgnoreCase("image")) {
                        imageFile = new File(context.getExternalFilesDir(null).getAbsolutePath(), "/.Whitecoats/Post_images/" + mPostImages.get(position).getFileAttachmentPath());
                        if (imageFile.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            holder.imageView.setImageBitmap(bitmap);
                        } else {
                            RequestOptions options = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true);
                            Glide.with(context)
                                    .load(mPostImages.get(position).getAttachmentUrl())
                                    .apply(options)
                                    .into(holder.imageView);
                        }


                        holder.editImage.setBackgroundColor(Color.parseColor("#99FFFFFF"));
                        holder.editImage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit, 0, 0, 0);
                        holder.editImage.setText(context.getString(R.string.actionbar_edit));
                    } else if (attachmentType.equalsIgnoreCase("video") || attachmentType.equalsIgnoreCase("pdf")) {
                        int resourceId = R.drawable.video_icon;
                        if (attachmentType.equalsIgnoreCase("pdf")) {
                            resourceId = R.drawable.pdf_icon;
                        }
                        holder.editImage.setBackgroundColor(Color.TRANSPARENT);
                        holder.editImage.setCompoundDrawablesWithIntrinsicBounds(resourceId, 0, 0, 0);
                        holder.editImage.setText("");
                        if (mPostImages.get(position).getAttachmentSmallUrl() != null && !mPostImages.get(position).getAttachmentSmallUrl().isEmpty()) {
                            AppUtil.loadImageUsingGlide(context,mPostImages.get(position).getAttachmentSmallUrl(),holder.imageView,R.drawable.default_image_feed);
                        }
                    } else if (attachmentType.equalsIgnoreCase("audio")) {
                        holder.editImage.setVisibility(View.INVISIBLE);
                        holder.editImage.setText("");
                        holder.imageView.setImageResource(R.drawable.audio_background);
                    } else {
                        holder.imageView.setImageResource(R.drawable.default_image_feed);
                    }
                } else {
                    holder.pdfTv.setVisibility(View.GONE);
                    imageFile = new File(context.getExternalFilesDir(null).getAbsolutePath(), "/.Whitecoats/Post_images/" + mPostImages.get(position).getFileAttachmentPath());
                    if (!imageFile.exists()) {
                        imageFile = new File(mPostImages.get(position).getFileAttachmentPath());
                    }
                    String type = AppUtil.getMimeType(imageFile.toURI().toString());
                    if (imageFile.exists()) {
                        Bitmap myBitmap = null;
                        boolean isAudioFile = false;
                        boolean isPdfFile = false;
                        if (type == null && imageFile.getAbsolutePath().endsWith(".PDF")) {
                            holder.editImage.setBackgroundColor(Color.TRANSPARENT);
                            holder.editImage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pdf_icon, 0, 0, 0);
                            holder.editImage.setText("");
                            holder.pdfTv.setText(mPostImages.get(position).getAttachmentName());
                            holder.pdfTv.setVisibility(View.VISIBLE);
                            isPdfFile = true;
                        } else if (type != null && type.contains("image")) {
                            holder.editImage.setBackgroundColor(Color.parseColor("#99FFFFFF"));
                            holder.editImage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit, 0, 0, 0);
                            holder.editImage.setText(context.getString(R.string.actionbar_edit));
                            myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        } else if (type != null && type.contains("video")) {
                            holder.editImage.setBackgroundColor(Color.TRANSPARENT);
                            holder.editImage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.video_icon, 0, 0, 0);
                            holder.editImage.setText("");
                            myBitmap = ThumbnailUtils.createVideoThumbnail(imageFile.getAbsolutePath(), MediaStore.Images.Thumbnails.MINI_KIND);
                        } else if (type != null && type.contains("audio")) {
                            holder.editImage.setVisibility(View.INVISIBLE);
                            holder.editImage.setText("");
                            isAudioFile = true;
                        } else if (type != null && type.contains("application/pdf")) {
                            holder.editImage.setBackgroundColor(Color.TRANSPARENT);
                            holder.editImage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pdf_icon, 0, 0, 0);
                            holder.editImage.setText("");
                            holder.pdfTv.setText(mPostImages.get(position).getAttachmentName());
                            holder.pdfTv.setVisibility(View.VISIBLE);
                            isPdfFile = true;
                        }
                        if (myBitmap != null) {
                            holder.imageView.setImageBitmap(myBitmap);
                        } else {
                            if (isAudioFile) {
                                holder.imageView.setImageResource(R.drawable.audio_background);
                            } else if (isPdfFile) {
                                holder.imageView.setBackgroundColor(Color.GRAY);
                            } else {
                                holder.imageView.setImageResource(R.drawable.default_image_feed);
                            }
                        }
                    } else {
                        holder.imageView.setImageResource(R.drawable.default_image_feed);
                    }
                }
            } else {
                String pic_path = caseRoomAttachmentsInfo.get(position).getAttachname();
                holder.editImage.setVisibility(View.VISIBLE);
//                holder.deleteImage.setVisibility(View.VISIBLE);
                holder.deleteImageNew.setVisibility(View.VISIBLE);
                File f;
                if (pic_path.contains("/"))
                    f = new File(pic_path);
                else
                    f = new File(context.getExternalFilesDir(null).getAbsolutePath(), "/.Whitecoats/CaseRoom_Pic/" + pic_path);

                String type = AppUtil.getMimeType(f.toURI().toString());
                if (f.exists()) {
                    if (type != null && type.contains("image")) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                        if (myBitmap != null) {
                            holder.imageView.setImageBitmap(myBitmap);
                        } else {
                            holder.imageView.setImageResource(R.drawable.default_image_feed);
                        }
                        holder.pdfTv.setVisibility(View.GONE);
                    } else {
                        holder.imageView.setImageResource(R.drawable.ic_pdf_format);
                        holder.pdfTv.setText(f.getName());
                        holder.editImage.setVisibility(View.GONE);
                    }
                } else {
                    holder.imageView.setImageResource(R.drawable.ic_gallery);
                    holder.editImage.setVisibility(View.GONE);
                }


            }
            holder.overlayLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedpostion = -1;
                    if (isFromPost) {
                        urlList.clear();
                        if (selectedpostion != position) {
                            CreatePostActivity.positionForImageFullView = position;
                            if (mPostImages.get(position).isEditPost()) {
                                String attachmentType = mPostImages.get(position).getAttachmentType();
                                if (attachmentType.equalsIgnoreCase("image")) {
                                    File file = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images/" + mPostImages.get(position).getFileAttachmentPath());
                                    if (!file.exists()) {
                                        file = new File(mPostImages.get(position).getFileAttachmentPath());
                                    }
                                    urlList.add(file.getAbsolutePath());
                                    Intent intent = new Intent(context, ImageViewer.class);
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, file.getAbsolutePath());
                                    intent.putExtra("isPost", true);
                                    intent.putExtra(RestUtils.CHANNEL_ID,channelId);
                                    intent.putExtra("attachname", file.getAbsolutePath());
                                    int count;
                                    if (mPostImages.contains(null)) {
                                        count = getCount() - 1;
                                    } else {
                                        count = getCount();
                                    }
                                    intent.putExtra(RestUtils.TAG_IMAGE_POSITION, (position + 1) + " - " + count);
                                    intent.putExtra("selectedImagePostion",position);
                                    intent.putStringArrayListExtra("URLList", urlList);
                                    if (context instanceof CreatePostActivity) {
                                        /*Refactoring the deprecated startActivityForResults*/
                                        screenNavigate.onScreenNavigate(intent);
                                    }
                                } else if (attachmentType.equalsIgnoreCase("video")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPostImages.get(position).getAttachmentUrl()));
                                    intent.setDataAndType(Uri.parse(mPostImages.get(position).getAttachmentUrl()), "video/*");
                                    context.startActivity(intent);
                                } else if (attachmentType.equalsIgnoreCase("audio")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse(mPostImages.get(position).getAttachmentUrl()), "audio/*");
                                    context.startActivity(intent);
                                } else if (attachmentType.equalsIgnoreCase("pdf")) {
                                    Intent intent = new Intent(context, PdfViewerActivity.class);
                                    intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                                    intent.putExtra(RestUtils.CHANNEL_ID,channelId);
                                    intent.putExtra(RestUtils.ATTACHMENT_TYPE, "pdf");
                                    intent.putExtra(RestUtils.ATTACHMENT_NAME, "");
                                    intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, mPostImages.get(position).getAttachmentUrl());
                                    context.startActivity(intent);
                                }


                            } else {
                                File file = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images/" + mPostImages.get(position).getFileAttachmentPath());
                                if (!file.exists()) {
                                    file = new File(mPostImages.get(position).getFileAttachmentPath());
                                }
                                String type = AppUtil.getMimeType(file.toURI().toString());
                                if (type == null && file.getAbsolutePath().endsWith(".PDF")) {
                                    Intent intent = new Intent(context, PdfViewerActivity.class);
                                    intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                                    intent.putExtra(RestUtils.CHANNEL_ID,channelId);
                                    intent.putExtra(RestUtils.ATTACHMENT_TYPE, "pdf");
                                    intent.putExtra(RestUtils.ATTACHMENT_NAME, "");
                                    intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, file.getAbsolutePath());
                                    intent.putExtra("loadFromLocal", true);
                                    context.startActivity(intent);
                                } else if (type != null && type.contains("image")) {
                                    urlList.add(file.getAbsolutePath());
                                    Intent intent = new Intent(context, ImageViewer.class);
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, file.getAbsolutePath());
                                    intent.putExtra("isPost", true);
                                    intent.putExtra(RestUtils.CHANNEL_ID,channelId);
                                    intent.putExtra("attachname", file.getAbsolutePath());
                                    int count;
                                    if (mPostImages.contains(null)) {
                                        count = getCount() - 1;
                                    } else {
                                        count = getCount();
                                    }
                                    intent.putExtra(RestUtils.TAG_IMAGE_POSITION, (position + 1) + " - " + count);
                                    intent.putStringArrayListExtra("URLList", urlList);
                                    if (context instanceof CreatePostActivity) {
                                        /*Refactoring the deprecated startActivityForResults*/
                                        screenNavigate.onScreenNavigate(intent);                                   }
                                } else if (type != null && type.contains("video")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(file.getAbsolutePath()));
                                    intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "video/*");
                                    context.startActivity(intent);
                                } else if (type != null && type.contains("audio")) {
                                    try {
                                        Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
                                        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
                                        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                                        myIntent.setDataAndType(Uri.fromFile(file), mimetype);
                                        context.startActivity(myIntent);
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                        String data = e.getMessage();
                                    }
                                } else if (type != null && type.contains("application/pdf")) {
                                    Intent intent = new Intent(context, PdfViewerActivity.class);
                                    intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                                    intent.putExtra(RestUtils.CHANNEL_ID,channelId);
                                    intent.putExtra(RestUtils.ATTACHMENT_TYPE, "pdf");
                                    intent.putExtra(RestUtils.ATTACHMENT_NAME, "");
                                    intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, file.getAbsolutePath());
                                    intent.putExtra("loadFromLocal", true);
                                    context.startActivity(intent);
                                }
                            }
                        }
                    } else {
                        notifyDataSetChanged();
                        if (selectedpostion != position) {
                            File file;
                            if (FileHelper.isFilePresent(caseRoomAttachmentsInfo.get(position).getAttachname(), "CaseRoom_Pic",context)) {
                                file = new File(context.getExternalFilesDir(null).getAbsolutePath(), "/.Whitecoats/CaseRoom_Pic/" + caseRoomAttachmentsInfo.get(position).getAttachname());
                                urlList.add(file.getAbsolutePath());
                                Intent intent = new Intent(context, ImageViewer.class);
                                intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                intent.putExtra(RestUtils.TAG_FILE_PATH, file.getAbsolutePath());
                                intent.putExtra("attachment", caseRoomAttachmentsInfo.get(position));
                                intent.putExtra("caseroomId", caseRoomAttachmentsInfo.get(position).getCaseroom_summary_id());
                                intent.putExtra("attachname", caseRoomAttachmentsInfo.get(position).getAttachname());
                                intent.putExtra(RestUtils.TAG_IMAGE_POSITION, (position + 1) + " - " + getCount());
                                intent.putStringArrayListExtra("URLList", urlList);
                                context.startActivity(intent);
                            }
                        }
                    }
                }
            });

            holder.editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFromPost) {

                        CreatePostActivity.editedImagePosition=position;
                        new ImageEditor.Builder((CreatePostActivity) context, context.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images/" + mPostImages.get(position).getFileAttachmentPath())
                                .open();
                    } /*else {

                        new ImageEditor.Builder((CaseRoomAttachmentsActivity) context, context.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/CaseRoom_Pic/" + caseRoomAttachmentsInfo.get(position).getAttachname())
                                .open();

                    }*/
                }
            });

            holder.deleteImageNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFromPost) {
                        File file = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images/" + mPostImages.get(position).getFileAttachmentPath());
                        if (file.exists()) {
                           boolean del= file.delete();
                           Log.i("File Deleted Status", ""+ del);
                        }
                        mPostImages.remove(position);
                        if (!mPostImages.contains(null)) {
                            mPostImages.add(null);
                        }
                        notifyDataSetChanged();
                        ConstsCore.POST_ATTACHMENT_COUNT -= 1;
                    } else {
                        Realm mRealm = Realm.getDefaultInstance();
                        RealmManager mRealmManager = new RealmManager(context);
                        mRealmManager.deleteCaseRoomAttachmentsInfo(mRealm, caseRoomAttachmentsInfo.get(position));
                        caseRoomAttachmentsInfo.remove(position);
                        notifyDataSetChanged();
                        selectedpostion = -1;
                       /* ((CaseRoomAttachmentsActivity) context).updateAttCount();*/
                        if (!mRealm.isClosed()) {
                            mRealm.close();
                        }
                    }
                }
            });


        }
        return inflatedView;
    }




}