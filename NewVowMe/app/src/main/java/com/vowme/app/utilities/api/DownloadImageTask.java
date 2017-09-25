package com.vowme.app.utilities.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.vowme.app.utilities.helpers.ImageHelper;
import com.vowme.vol.app.R;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Context context;
    private boolean isCircular = false;

    public DownloadImageTask(ImageView bmImage, boolean isCircular, Context context) {
        this.bmImage = bmImage;
        this.isCircular = isCircular;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap mIcon11 = null;
        try {
            InputStream in = new URL(urls[0]).openStream();
            int smallSize = this.context.getResources().getInteger(R.integer.SMALL_SIZE);
            mIcon11 = ImageHelper.decodeSampledBitmapFromInputStream(in, smallSize, smallSize);
        } catch (Exception e) {
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result == null) {
            return;
        }
        if (this.isCircular) {
            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), result);
            drawable.setCircular(true);
            this.bmImage.setImageDrawable(drawable);
            return;
        }
        this.bmImage.setImageBitmap(result);
    }
}
