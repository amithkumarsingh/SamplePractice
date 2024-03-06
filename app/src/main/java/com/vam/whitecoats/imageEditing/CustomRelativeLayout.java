package com.vam.whitecoats.imageEditing;

import android.content.Context;
import android.graphics.Point;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.CaseRoomEditAttachmentActivity;

import java.util.ArrayList;

public class CustomRelativeLayout extends RelativeLayout {
    private int mDragging;
    private ImageView mEditable;
    private ImageView trashImage;


    private static final int FORGIVENESS = 70;
    private ArrayList<CustomImageView> mImages;

    private boolean update = false;

    public CustomRelativeLayout(Context context) {
        super(context);
        mImages = new ArrayList<CustomImageView>();
        mDragging = -1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < mImages.size(); i++) {
            mImages.get(i).reLayout();
        }
    }

    /**
     * Sets the image that the user is currently dragging/scaling/etc to CIV.
     */
    public void setDragging(CustomImageView civ) {
        if (civ == null) {
            mDragging = -1;
        } else {

            removeAllSelected();
            if (!mImages.contains(civ) ) {
                mImages.add(civ);
                if (mImages.size() > 0) {
                    if (getContext() instanceof CaseRoomEditAttachmentActivity) {
                        ((CaseRoomEditAttachmentActivity) getContext()).mControls.setVisibility(View.VISIBLE);
                    }
                }
            }
            mDragging = mImages.indexOf(civ);
        }
    }

    /** Sets the image being edited and viewed (the main one) to CIV. */
    public void setEditable(ImageView civ) {
        mEditable = civ;

    }

    public void setTrashIcon(ImageView r) {
        this.trashImage = r;
    }

    /** RETURNS the image currently being edited. */
    public ImageView getEditableImage() {
        return mEditable;
    }

    /** RETURNS the image currently being dragging and placed. */
    public View getSelectedImage() {
        if (mDragging == -1) {
            return null;
        }
        return mImages.get(mDragging);
    }

    private void changeImageSize(int size) {
        if (mImages.size() <= mDragging || mDragging == -1) {
            return;
        }
        mImages.get(mDragging).scaleX(size);
    }

    private void rotateImageClockwise(int degree) {
        Log.e("Rotate", "rotateImageClockwise");

        if (mImages.size() <= mDragging || mDragging == -1) {
            return;
        }
        mImages.get(mDragging).rotateX(degree);
    }

    public void handleEvent(int state, int amount,int rotate) {
        Log.e("Rotate", "handleEvent");

        if (amount == 0)
            return;
        switch (state) {
            case CaseRoomEditAttachmentActivity.ROTATE:
                rotateImageClockwise(rotate);
                break;
            case CaseRoomEditAttachmentActivity.SCALE:
                changeImageSize(amount);
                break;
        }
    }

    public void removeAllSelected() {
        for (CustomImageView v : mImages) {
            v.removeSelected();
            v.invalidate();
        }
    }

    public boolean outOfBounds(Point coords) {
        return mEditable.getBottom() < coords.y - 70 || mEditable.getTop() > coords.y - 20;// ||
                                                                                           // mEditable.getRight()
                                                                                           // <
                                                                                           // coords.x
                                                                                           // ||
                                                                                           // mEditable.getLeft()
                                                                                           // >
                                                                                           // coords.x;
    }

    public void removeFrmList(Point coords,View view) {
         int index =  mImages.indexOf(view);
        if ( index > -1 ) {
            mImages.remove(index);
            mDragging = (mImages.size() - 1);
            if (mImages.size() == 0) {

                if (getContext() instanceof CaseRoomEditAttachmentActivity) {
                    ((CaseRoomEditAttachmentActivity) getContext()).mControls.setVisibility(View.GONE);
                }

            }else {
                long downTime = SystemClock.uptimeMillis();
                long eventTime = SystemClock.uptimeMillis() + 100;
                float x = 0.0f;
                float y = 0.0f;
// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                int metaState = 0;
                MotionEvent motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_UP,
                        x,
                        y,
                        metaState
                );
                setDragging(((CustomImageView)mImages.get(mDragging)));
                ((CustomImageView)mImages.get(mDragging))._selected = true;
                ((CustomImageView)mImages.get(mDragging))._delayedSelected = true;
            }
            if (coords != null){
                isOverTrash(coords);
            }
        }
    }

   public void isOverTrash(Point coords) {
        int[] loc1 = new int[2];
        int[] loc2 = new int[2];
        trashImage.getLocationOnScreen(loc1);
        trashImage.getLocationInWindow(loc2);
        Log.e("LOCS:", loc1[0] + " " + loc1[1] + " vs " + loc2[0] + " " + loc2[1]);
        int left = loc1[0], bottom = loc1[1];
        int right = left + trashImage.getWidth(), top = bottom - trashImage.getHeight();
        boolean hit = left - FORGIVENESS < coords.x && right + FORGIVENESS > coords.x && bottom + FORGIVENESS > coords.y && top - FORGIVENESS < coords.y;
        if (coords != null && !update && hit) {
            Log.e("Trash", "Is over it");
            this.trashImage.setImageResource(R.drawable.ic_edit_delete_active);
            this.invalidate();
            update = true;
        } else if (update && !hit) {
            this.trashImage.setImageResource(R.drawable.ic_edit_delete);
            this.invalidate();
            update = false;
        }

        Log.e("Points:", left + " " + right + " " + coords.x);
        Log.e("Points:", bottom + " " + top + " " + coords.y);
        Log.e("Points:", update + "");
    }
}
