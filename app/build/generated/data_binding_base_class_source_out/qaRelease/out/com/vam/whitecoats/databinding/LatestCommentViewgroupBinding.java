// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LatestCommentViewgroupBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView commentedDocName;

  @NonNull
  public final RoundedImageView commentedDocPic;

  @NonNull
  public final TextView latestComment;

  @NonNull
  public final RelativeLayout latestCommentedLayout;

  @NonNull
  public final View seperatorLine;

  private LatestCommentViewgroupBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView commentedDocName, @NonNull RoundedImageView commentedDocPic,
      @NonNull TextView latestComment, @NonNull RelativeLayout latestCommentedLayout,
      @NonNull View seperatorLine) {
    this.rootView = rootView;
    this.commentedDocName = commentedDocName;
    this.commentedDocPic = commentedDocPic;
    this.latestComment = latestComment;
    this.latestCommentedLayout = latestCommentedLayout;
    this.seperatorLine = seperatorLine;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LatestCommentViewgroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LatestCommentViewgroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.latest_comment_viewgroup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LatestCommentViewgroupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.commented_doc_name;
      TextView commentedDocName = ViewBindings.findChildViewById(rootView, id);
      if (commentedDocName == null) {
        break missingId;
      }

      id = R.id.commented_doc_pic;
      RoundedImageView commentedDocPic = ViewBindings.findChildViewById(rootView, id);
      if (commentedDocPic == null) {
        break missingId;
      }

      id = R.id.latest_comment;
      TextView latestComment = ViewBindings.findChildViewById(rootView, id);
      if (latestComment == null) {
        break missingId;
      }

      RelativeLayout latestCommentedLayout = (RelativeLayout) rootView;

      id = R.id.seperator_line;
      View seperatorLine = ViewBindings.findChildViewById(rootView, id);
      if (seperatorLine == null) {
        break missingId;
      }

      return new LatestCommentViewgroupBinding((RelativeLayout) rootView, commentedDocName,
          commentedDocPic, latestComment, latestCommentedLayout, seperatorLine);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
