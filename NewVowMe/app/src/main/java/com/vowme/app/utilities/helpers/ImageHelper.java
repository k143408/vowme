package com.vowme.app.utilities.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper {
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options.outHeight, options.outWidth, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options.outHeight, options.outWidth, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromInputStream(InputStream in, int reqWidth, int reqHeight) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = in.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            in.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        int currentHeight = options.outHeight;
        int currentWidth = options.outWidth;
        options.inSampleSize = calculateInSampleSize(options.outHeight, options.outWidth, reqWidth, reqHeight);
        is = new ByteArrayInputStream(os.toByteArray());
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        if (currentHeight == currentWidth) {
            return bitmap;
        }
        if ((currentHeight > reqHeight || currentWidth > reqWidth) && options.inSampleSize == 1) {
            return ThumbnailUtils.extractThumbnail(bitmap, reqWidth, reqHeight, 2);
        }
        return bitmap;
    }

    public static int calculateInSampleSize(int height, int width, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static String getAvatarUrl(String avatar, String sizeExtension) {
        int index = avatar.lastIndexOf(".");
        return avatar.substring(0, index) + sizeExtension + avatar.substring(index);
    }

    public static RoundedBitmapDrawable getDefaultAvatar(Context context, int ressourceId) {
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), drawableToBitmap(context, ressourceId));
        drawable.setCircular(true);
        return drawable;
    }

    public static Bitmap drawableToBitmap(Context context, int ressourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, ressourceId);
        int h = vectorDrawable.getIntrinsicHeight();
        int w = vectorDrawable.getIntrinsicWidth();
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bm = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        vectorDrawable.draw(new Canvas(bm));
        return bm;
    }

    public static Bitmap createSquaredBitmap(Bitmap srcBmp) {
        int dim = Math.min(srcBmp.getWidth(), srcBmp.getHeight());
        Bitmap dstBmp = Bitmap.createBitmap(dim, dim, Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBmp);
        canvas.drawColor(-1);
        canvas.drawBitmap(srcBmp, (float) ((dim - srcBmp.getWidth()) / 2), (float) ((dim - srcBmp.getHeight()) / 2), null);
        return dstBmp;
    }

    public static BitmapDescriptor getBitmapDescriptor(Context context, int ressourceId) {
        return BitmapDescriptorFactory.fromBitmap(drawableToBitmap(context, ressourceId));
    }

    public static Bitmap getScaledRotatedImage(String imagePath, int reqsize) {
        ExifInterface exifInterface;
        IOException e;
        Matrix matrix = new Matrix();
        try {
            ExifInterface ei = new ExifInterface(imagePath);
            int orientation = ei.getAttributeInt("Orientation", 0);
            Bitmap bitmap = decodeSampledBitmapFromFile(imagePath, reqsize, reqsize);
            switch (orientation) {
                case 3:
                    matrix.postRotate(BitmapDescriptorFactory.HUE_CYAN);
                    break;
                case 6:
                    matrix.postRotate(90.0f);
                    break;
            }
            exifInterface = ei;
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    public static ByteArrayOutputStream compressedUntilSize(Bitmap bitmap, int size, int quality) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, quality, bos);
        if (bos.size() > size && quality > 1) {
            return compressedUntilSize(bitmap, size, (int) (((double) quality) / 1.5d));
        }
        if (quality != 1) {
            return bos;
        }
        bitmap.compress(CompressFormat.JPEG, 2, bos);
        return bos;
    }
}
