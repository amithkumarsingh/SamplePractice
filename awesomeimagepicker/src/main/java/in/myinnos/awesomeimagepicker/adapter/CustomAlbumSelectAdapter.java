package in.myinnos.awesomeimagepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import in.myinnos.awesomeimagepicker.R;
import in.myinnos.awesomeimagepicker.models.Album;

/**
 * Created by MyInnos on 03-11-2016.
 */
public class CustomAlbumSelectAdapter extends CustomGenericAdapter<Album> {

    public CustomAlbumSelectAdapter(Activity activity, Context context, ArrayList<Album> albums) {
        super(activity, context, albums);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_item_album_select, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view_album_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view_album_name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.getLayoutParams().width = size;
        viewHolder.imageView.getLayoutParams().height = size;

        viewHolder.textView.setText(arrayList.get(position).name);
        RequestOptions options=new RequestOptions()
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.colorAccent)))
                .override(200, 200)
                .centerCrop();
        if (arrayList.get(position).name.equals("Take Photo")) {

            Glide.with(context).load(arrayList.get(position).cover)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(options)
                    .into(viewHolder.imageView);
        } else {
            final Uri uri = Uri.fromFile(new File(arrayList.get(position).cover));
            Glide.with(context).load(uri)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(options)
                    .into(viewHolder.imageView);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
