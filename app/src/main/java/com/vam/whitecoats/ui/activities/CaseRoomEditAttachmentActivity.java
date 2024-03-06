package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.CaseRoomAttachmentsInfo;
import com.vam.whitecoats.core.models.CaseRoomInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.imageEditing.CustomHorizontalScrollView;
import com.vam.whitecoats.imageEditing.CustomImageView;
import com.vam.whitecoats.imageEditing.CustomRelativeLayout;
import com.vam.whitecoats.imageEditing.CustomScrollView;
import com.vam.whitecoats.imageEditing.UpdateImage;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by swathim on 20-10-2015.
 */
public class CaseRoomEditAttachmentActivity extends BaseActionBarActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String TAG = CaseRoomEditAttachmentActivity.class.getSimpleName();
    public static final int SCALE = 0;
    public static final int ROTATE = 1;
    public static final int MINUS = 2;
    public static final int PLUS = 3;
    /**
     * The list of drawable resource ids.
     */
    private static final int[] imageList = {R.drawable.edit_patch, R.drawable.edit_circle,
            R.drawable.edit_arrow};

    /**
     * The Linear Layout that contains all other elements.
     */
    private LinearLayout root_layout;

    /**
     * ViewGroup to which we add moustaches.
     */
    private LinearLayout mMoustacheGroup;

    /**
     * The picture being viewed.
     */
    private ImageView mCurrentPicture;

    /**
     * The Horizontal Scrollbar we use to display the images we can add.
     */
    private FrameLayout mHorizontalScroll;

    /**
     * The RelativeLayout for the Scrollbar.
     */
    private CustomRelativeLayout mRelative;

    /**
     * The remove button.
     */
    private ImageView mRemove;

    private ImageView mRotateLeft, mRotateRight;

    /**
     * The save button.
     */
    private Button mSave;
    /**
     * The mode. Either Scaling or Rotating.
     */
    private ToggleButton mMode;
    /**
     * The minus button or counterclockwise rotate.
     */
    private ImageView mMinus;

    private static final int MINUS_ID = 15;

    /**
     * The plus button or clockwise rotate.
     */
    private ImageView mPlus;

    private static final int PLUS_ID = 20;

    /**
     * The state of the current Viewer -- either rotate or scale.
     */
    private int mState;

    /**
     * Handles holding down either the plus or minus buttons.
     */
    private Handler mHandler;

    /**
     * The Thread to update the image in.
     */
    private UpdateImage _updateImageTask;

    /**
     * The orientation of the image we are viewing.
     */
    private int mOrientation;
    private int originalImageWidth, originalImageHeight;

    // private AdView mAdView;

    public RelativeLayout mControls;
    private static final int SCROLL_VIEW_SIZE = 105;
    /**
     * Depending on the orientation, this is the amount of screen space taken up
     * by the options -- the ScrollView and buttons.
     */
    private static final int OPTIONS_SIZE = 170;
    String imageURI = null;
    private Realm realm;
    private RealmManager realmManager;
    CaseRoomInfo caseroominfo;
    String caseroom_id;
    boolean isFromChat;
    boolean isFromPost;
    boolean isForEDit = false;
    String attachname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_attachements, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText("Edit image");

        next_button.setText("DONE");
        next_button.setOnClickListener(v -> saveImage());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        Toast.makeText(getApplicationContext(),getResources().getString(R.string.drag_the_shape), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void setCurrentActivity() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentPicture = null;
        mMoustacheGroup = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_caseroomeditatt);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        caseroom_id = bundle.getString("caseroomId");
        imageURI = intent.getStringExtra("selectedImagepath");
        isFromChat = intent.getBooleanExtra("isChat", false);
        isFromPost = intent.getBooleanExtra("isPost", false);
        if (bundle.containsKey("caseroomInfo")) {
            caseroominfo = (CaseRoomInfo) bundle.getSerializable("caseroomInfo");
        } else {
            isForEDit = true;
            attachname = bundle.getString("attachname");
        }
        // Inflate all the views.
        int orientation = getResources().getConfiguration().orientation;
        basicInit(orientation);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            initPortrait();
        } else {
            initLandscape();
        }
        mHandler = new Handler();
        addImage(imageURI);
        addDraggableImages();
        _updateImageTask = new UpdateImage(mRelative, mHandler);
    }


    private void basicInit(int orientation) {
        root_layout = (LinearLayout) findViewById(R.id.root);
        root_layout.setBackgroundColor(15921906);

        mRelative = new CustomRelativeLayout(this);
        mRelative.setBackgroundColor(Color.rgb(242, 242, 242));

        RelativeLayout.LayoutParams fill = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams wrap = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mRelative.setLayoutParams(fill);
        root_layout.addView(mRelative);
        mCurrentPicture = new ImageView(this);
        mCurrentPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams picParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        picParams.addRule(RelativeLayout.BELOW, 150);
        Display d = this.getWindowManager().getDefaultDisplay();
        int widthy = d.getWidth();
        int heighty = d.getHeight();
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            picParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mCurrentPicture.setMaxHeight(heighty - OPTIONS_SIZE);
            mCurrentPicture.setMaxWidth(widthy);
        } else {
            mCurrentPicture.setMaxHeight(heighty);
            mCurrentPicture.setMaxWidth(widthy - OPTIONS_SIZE);
        }
        mCurrentPicture.setLayoutParams(picParams);
        mControls = new RelativeLayout(this);
        RelativeLayout.LayoutParams controlParams = null;
        mMoustacheGroup = new LinearLayout(this);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("Max height: ", (OPTIONS_SIZE - SCROLL_VIEW_SIZE) + "");
            controlParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, OPTIONS_SIZE - SCROLL_VIEW_SIZE);
            controlParams.bottomMargin = 10;
            controlParams.addRule(RelativeLayout.ABOVE, R.id.scrollId);
        } else {
            controlParams = new RelativeLayout.LayoutParams(OPTIONS_SIZE - SCROLL_VIEW_SIZE, RelativeLayout.LayoutParams.MATCH_PARENT);
            controlParams.rightMargin = 10;
            controlParams.addRule(RelativeLayout.LEFT_OF, R.id.scrollId);
        }
        mControls.setMinimumHeight(55);
        mControls.setLayoutParams(controlParams);
        mRelative.addView(mControls);
        mRelative.addView(mCurrentPicture);
        mRelative.setEditable(mCurrentPicture);
        mControls.setVisibility(View.GONE);
    }

    /**
     * Populates the LienarLayout VG with the image resources from IMAGELIST.
     */
    private void addDraggableImages() {
        int counter = 0;
        mMoustacheGroup.setId(R.id.layout1);//200);
        for (int i : imageList) {
            CustomImageView temp = new CustomImageView(this);
            temp.setImageResource(i);
            temp.setId(counter);
            counter++;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            temp.setScaleType(ImageView.ScaleType.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((metrics.widthPixels / 3), 100, Gravity.CENTER);
            temp.setLayoutParams(params);
            mMoustacheGroup.addView(temp);
        }
    }

    /**
     * Adds the image specified by the Uri IMAGEURI to the current view.
     */
    private void addImage(String imageURI) {
        mCurrentPicture.setImageBitmap(this.setUpPicture(imageURI));
        mCurrentPicture.invalidate();
    }

    /**
     * Called by the constructor to set up the views. Because we need to keep
     * track of a lot of the View elements, its easier for us to inflate each
     * View in the Class rather than in the XML. This is where that happens.
     */
    private void init(RelativeLayout.LayoutParams removeLP, LinearLayout.LayoutParams minusLP, LinearLayout.LayoutParams modeLP, LinearLayout.LayoutParams plusLP,
                      RelativeLayout.LayoutParams saveLP, RelativeLayout.LayoutParams leftRoate, boolean portrait) {

        mRemove = new ImageView(this);
        mRemove.setLayoutParams(removeLP);
        mRemove.setImageResource(R.drawable.ic_edit_delete);
        mRemove.setId(R.id.removeId);//REMOVE_ID);
        mRelative.setTrashIcon(mRemove);
        mRemove.setMaxHeight(OPTIONS_SIZE - SCROLL_VIEW_SIZE);
        if (portrait) {
            mRemove.setPadding(0, 0, (int) getResources().getDimension(R.dimen.five), 0);
        } else {
            mRemove.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.five));
        }
        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelative.removeView(mRelative.getSelectedImage());
                mRelative.removeFrmList(null, mRelative.getSelectedImage());

            }
        });
        mMinus = new ImageView(this);
        mMinus.setImageResource(R.drawable.ic_edit_resize_reduce);
        mMinus.setLayoutParams(leftRoate);
        //noinspection ResourceType
        mMinus.setId(MINUS_ID);
        mMinus.setPadding(0, 0, (int) getResources().getDimension(R.dimen.five), 0);
        mMinus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mState = SCALE;
                _updateImageTask.setState(mState, MINUS);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(_updateImageTask);
                        mHandler.postDelayed(_updateImageTask, 30);
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - currentTime < 200) {
                            mRelative.handleEvent(mState, -3, -45);
                        }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                }

                return true;
            }

            private long currentTime;
        });

        mPlus = new ImageView(this);
        mPlus.setImageResource(R.drawable.ic_edit_resize_increase);
        mPlus.setLayoutParams(leftRoate);
        //noinspection ResourceType
        mPlus.setId(PLUS_ID);
        mPlus.setMaxHeight(OPTIONS_SIZE - SCROLL_VIEW_SIZE);
        mPlus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mState = SCALE;
                _updateImageTask.setState(mState, PLUS);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(_updateImageTask);
                        mHandler.postDelayed(_updateImageTask, 30);
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - currentTime < 200) {
                            mRelative.handleEvent(mState, 3, 45);
                        }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                }
                return true;
            }

            private long currentTime;
        });
        mMode = new ToggleButton(this);
        if (portrait) {
            mMode.setPadding(0, 0, 0, 0);
        }
        mMode.setMaxHeight(OPTIONS_SIZE - SCROLL_VIEW_SIZE);
        mMode.setLayoutParams(modeLP);
        mMode.setBackgroundResource(R.drawable.rotate_switch);
        mMode.setTextOff("");
        mMode.setTextOn("");
        mMode.setChecked(true);
        mMode.setId(R.id.modeId);//MODE_ID);
        mMode.setOnCheckedChangeListener(this);

        mState = SCALE;

        mSave = new Button(this);
        mSave.setBackgroundResource(R.drawable.save);
        mSave.setLayoutParams(saveLP);
        mSave.setId(R.id.saveID);//SAVE_ID);
        mSave.setMaxHeight(OPTIONS_SIZE - SCROLL_VIEW_SIZE);


        mRotateLeft = new ImageView(this);
        mRotateLeft.setImageResource(R.drawable.ic_edit_rotate_clock);
        mRotateLeft.setLayoutParams(leftRoate);
        mRotateLeft.setId(R.id.clock_Wise);//clock_Wise);

        mRotateLeft.setMaxHeight(OPTIONS_SIZE - SCROLL_VIEW_SIZE);

        mRotateRight = new ImageView(this);
        mRotateRight.setImageResource(R.drawable.ic_edit_rotate_anticlock);
        mRotateRight.setLayoutParams(saveLP);
        mRotateRight.setId(R.id.anti_clock_Wise);//anti_clock_Wise);
        mRotateRight.setMaxHeight(OPTIONS_SIZE - SCROLL_VIEW_SIZE);

        mRotateLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mState = ROTATE;
                _updateImageTask.setState(mState, PLUS);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(_updateImageTask);
                        mHandler.postDelayed(_updateImageTask, 30);
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - currentTime < 200) {
                            mRelative.handleEvent(mState, 3, 45);
                        }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                }
                return true;
            }

            private long currentTime;
        });

        mRotateRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mState = ROTATE;
                _updateImageTask.setState(mState, MINUS);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(_updateImageTask);
                        mHandler.postDelayed(_updateImageTask, 30);
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - currentTime < 200) {
                            mRelative.handleEvent(mState, -3, -45);
                        }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                }

                return true;
            }

            private long currentTime;

        });

        if (portrait) {
            mHorizontalScroll = new CustomHorizontalScrollView(this);
        } else {
            mHorizontalScroll = new CustomScrollView(this);
        }
        mHorizontalScroll.setId(R.id.scrollId);//1);
        RelativeLayout.LayoutParams lp = null;
        if (portrait) {
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else {
            lp = new RelativeLayout.LayoutParams(100, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        mHorizontalScroll.setLayoutParams(lp);
        mRelative.addView(mHorizontalScroll);

        mMoustacheGroup.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        if (!portrait) {
            mMoustacheGroup.setOrientation(LinearLayout.VERTICAL);
        }
        mHorizontalScroll.addView(mMoustacheGroup);
    }

    void saveImage() {
        CustomImageView view = (CustomImageView) mRelative.getSelectedImage();
        mRelative.removeAllSelected();
        mRelative.setDrawingCacheEnabled(true);
        Bitmap editedBitmap = mRelative.getDrawingCache();
        final Bitmap cropped = Bitmap.createBitmap(editedBitmap, mCurrentPicture.getLeft(), mCurrentPicture.getTop(), mCurrentPicture.getWidth(), mCurrentPicture.getHeight());
        //final Bitmap cropped = AppUtil.sampleResize(editedBitmap,1536,1152);
        mRelative.setDragging(view);
        String filePath = saveInternal(cropped);
        if (filePath != null) {
            if (isFromChat) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("filePath", filePath);
                setResult(RESULT_OK, resultIntent);
            }
        }
        mCurrentPicture.setDrawingCacheEnabled(false);
        finish();
    }

    private void initPortrait() {
        Resources res = getResources();
        RelativeLayout.LayoutParams removeLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        removeLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        removeLP.leftMargin = (int) res.getDimension(R.dimen.three);

        LinearLayout.LayoutParams minusLP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        minusLP.rightMargin = (int) res.getDimension(R.dimen.five);

        LinearLayout.LayoutParams plusLP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        plusLP.leftMargin = (int) res.getDimension(R.dimen.five);

        LinearLayout.LayoutParams modeLP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams saveLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        saveLP.rightMargin = (int) res.getDimension(R.dimen.three);
        saveLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        RelativeLayout.LayoutParams roateLeft = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        roateLeft.rightMargin = (int) res.getDimension(R.dimen.three);
        LinearLayout layout = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        init(removeLP, minusLP, modeLP, plusLP, saveLP, roateLeft, true);

        layout.addView(mMinus);
        layout.addView(mPlus);
        mControls.addView(mRemove);
        mControls.addView(layout);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRotateLeft.getLayoutParams();
        layoutParams.addRule(RelativeLayout.LEFT_OF, mRotateRight.getId());
        layoutLP.addRule(RelativeLayout.CENTER_HORIZONTAL, mRotateLeft.getId());
        layout.setLayoutParams(layoutLP);
        mControls.addView(mRotateLeft);
        mControls.addView(mRotateRight);
    }

    private void initLandscape() {
        Resources res = getResources();
        RelativeLayout.LayoutParams removeLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        removeLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        LinearLayout.LayoutParams minusLP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        minusLP.topMargin = (int) res.getDimensionPixelOffset(R.dimen.five);

        LinearLayout.LayoutParams plusLP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        plusLP.bottomMargin = (int) res.getDimensionPixelOffset(R.dimen.five);

        LinearLayout.LayoutParams modeLP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams saveLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        saveLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        LinearLayout layout = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutLP.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.setLayoutParams(layoutLP);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(mPlus);
        layout.addView(mMode);
        layout.addView(mMinus);

        mControls.addView(mRemove);
        mControls.addView(layout);
        mControls.addView(mSave);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!realm.isClosed())
            realm.close();
    }


    /**
     * This function takes a filepath as a parameter and returns the
     * corresponding image, correctly scaled and rotated (if necessary)
     *
     * @param filePath The filepath of the image
     * @return the scaled and rotated bitmap
     */
    private Bitmap setUpPicture(String filePath) {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int w = size.x;
        int h = size.y - 400;
        Bitmap b = BitmapFactory.decodeFile(filePath);
        originalImageWidth = b.getWidth();
        originalImageHeight = b.getHeight();
        boolean rotated = b.getWidth() > b.getHeight();

        Bitmap resultBmp = null;
        int degree;
        if ((mOrientation = degreeRotated(filePath)) == 0) {
            resultBmp = Bitmap.createScaledBitmap(b, w, h, true);
            b = null;
        } else {
            Bitmap scaledBmp = Bitmap.createScaledBitmap(b, w, h, true);
            b.recycle();
            b = null;
            Matrix mat = new Matrix();
            mat.postRotate(mOrientation);
            resultBmp = Bitmap.createBitmap(scaledBmp, 0, 0, w, h, mat, true);
            scaledBmp.recycle();
            scaledBmp = null;
        }
        return resultBmp;
    }

    /**
     * RETURNS the number of degrees the image specified by FILEPATH is rotate
     * (i.e. what degree rotation the phone was at while taking the picture.
     */
    private int degreeRotated(String filePath) {
        try {

            JpegImageMetadata meta = ((JpegImageMetadata) Sanselan.getMetadata(new File(filePath)));
            TiffImageMetadata data = null;
            if (meta != null) {
                data = meta.getExif();
            }
            int orientation = 0;
            if (data != null) {
                orientation = 15;//data.findField(ExifTagConstants.EXIF_TAG_ORIENTATION).getIntValue();
            } else {
                String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};
                Cursor c = getContentResolver().query(Uri.fromFile(new File(filePath)), projection, null, null, null);

                if (c != null && c.moveToFirst()) {
                    orientation = c.getInt(0);
                }
            }
            switch (orientation) {
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;

            }
        } catch (ImageReadException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            /* Scale mode */
            mMinus.setBackgroundResource(R.drawable.ic_edit_resize_reduce);
            mPlus.setBackgroundResource(R.drawable.ic_edit_resize_increase);
            mMode.setBackgroundResource(R.drawable.rotate_switch);
            mState = SCALE;
        } else {
            /* Rotate mode */
            mMinus.setBackgroundResource(R.drawable.ic_edit_rotate_anticlock);
            mPlus.setBackgroundResource(R.drawable.ic_edit_rotate_clock);
            mMode.setBackgroundResource(R.drawable.scale_switch);
            mState = ROTATE;
        }

    }

    private Button createButton(int resource, LinearLayout.LayoutParams minusLP, int id) {
        Button button = new Button(this);
        button.setBackgroundResource(resource);
        button.setLayoutParams(minusLP);
        button.setId(id);
        return button;
    }

    private String saveInternal(Bitmap image) {
        try {
            File folder;
            String fileInitialName = "";
            File f = null;
            if (isFromChat) {
                fileInitialName = imageURI.substring(imageURI.lastIndexOf('/') + 1);
                if (FileHelper.isFilePresent(fileInitialName, "Chat_images",CaseRoomEditAttachmentActivity.this)) {
                    f=AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Chat_images/" + fileInitialName);
                }
            } else if(isFromPost){
                fileInitialName = imageURI.substring(imageURI.lastIndexOf('/') + 1);
                if (FileHelper.isFilePresent(fileInitialName, "Post_images",CaseRoomEditAttachmentActivity.this)) {
                    f=AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Post_images/" + fileInitialName);
                }else {
                    f=new File(imageURI);
                }
            }else {
                fileInitialName = imageURI.substring(imageURI.lastIndexOf('/') + 1);
                if (FileHelper.isFilePresent(fileInitialName, "CaseRoom_Pic",CaseRoomEditAttachmentActivity.this)) {
                    f=AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/CaseRoom_Pic/" + fileInitialName);
                }
            }

            FileOutputStream fos = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] bitmapdata = bos.toByteArray();
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            long c_time = new Date().getTime();
            if (!isFromChat) {
                CaseRoomAttachmentsInfo cainfo = new CaseRoomAttachmentsInfo();
                cainfo.setAttachname(fileInitialName);
                cainfo.setAttachuploadstatus(0);
                cainfo.setCaseroom_summary_id(caseroom_id);
                cainfo.setQb_attach_id("");
                if (!isForEDit) {
                    realmManager.insertCaseRoomAttachmentsInfo(realm, cainfo);
                    if (realmManager.getCaseRoomInfo(realm, caseroom_id) == null) {
                        CaseRoomInfo caseRoomInfo = (CaseRoomInfo) getIntent().getExtras().getSerializable("caseroomInfo");
                        caseRoomInfo.setStatus(1);
                        caseRoomInfo.setCreateddate(c_time);
                        caseRoomInfo.setLast_message("");
                        caseRoomInfo.setLast_message_time(c_time);
                        realmManager.insertCaseRoomInfo(realm, caseRoomInfo);
                    } else {
                        CaseRoomInfo caseRoomInfo = (CaseRoomInfo) getIntent().getExtras().getSerializable("caseroomInfo");
                        int status = realmManager.getCaseRoomInfostatus(realm, caseroom_id);
                        if (status == 2)
                            caseRoomInfo.setStatus(4);
                        else if (status == 3)
                            caseRoomInfo.setStatus(5);
                        else
                            caseRoomInfo.setStatus(status);
                        caseRoomInfo.setModifieddate(c_time);
                        realmManager.updateCaseRoomInfo(realm, caseRoomInfo);
                    }
                } else {
                    CaseRoomAttachmentsInfo cainfo1 = new CaseRoomAttachmentsInfo();
                    cainfo1.setAttachname(fileInitialName);
                    cainfo1.setAttachuploadstatus(0);
                    cainfo1.setCaseroom_summary_id(caseroom_id);
                    cainfo1.setQb_attach_id("");
                    realmManager.updateCaseRoomAttachmentsInfo(realm, cainfo1, attachname);
                    CaseRoomInfo caseRoomInfo = realmManager.getCaseRoomInfo(realm, caseroom_id);
                    int status = caseRoomInfo.getStatus();
                    if (status == 2)
                        caseRoomInfo.setStatus(4);
                    else if (status == 3)
                        caseRoomInfo.setStatus(5);
                    else
                        caseRoomInfo.setStatus(status);
                    caseRoomInfo.setModifieddate(c_time);
                    realmManager.updateCaseRoomInfo(realm, caseRoomInfo);
                }
            }

            if (f != null) {
                return f.getAbsolutePath();
            }
        } catch (Exception ioe) {
            Log.d(TAG, "Exception " + ioe);
        }
        return null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private static class MyScannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
        private MediaScannerConnection _msc;
        private String _file;

        public MyScannerClient(String file) {
            _file = file;
        }

        public void setScanner(MediaScannerConnection msc) {
            _msc = msc;
        }

        @Override
        public void onMediaScannerConnected() {
            _msc.scanFile(_file, "*/*");
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {

        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
