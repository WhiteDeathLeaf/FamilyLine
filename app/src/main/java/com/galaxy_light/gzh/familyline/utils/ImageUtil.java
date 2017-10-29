package com.galaxy_light.gzh.familyline.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.support.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * 图片工具类
 * Created by gzh on 2017/10/29.
 */

public class ImageUtil {
    private static final int[] ORIENTATIONS = {
            ExifInterface.ORIENTATION_ROTATE_90,
            ExifInterface.ORIENTATION_ROTATE_180,
            ExifInterface.ORIENTATION_ROTATE_270
    };
    private static final int[] ROTATION = {90, 180, 270};

    @Nullable
    public static Bitmap convertBitmap(@NonNull final String filePath, final int maxX, final int maxY) {
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try {
            final ExifInterface exif = new ExifInterface(filePath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final BitmapFactory.Options sizeOnlyOptions = new BitmapFactory.Options();
        sizeOnlyOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, sizeOnlyOptions);
        final int myMaxXY = Math.max(sizeOnlyOptions.outHeight, sizeOnlyOptions.outWidth);
        final int maxXY = Math.max(maxX, maxY);
        final int sampleSize = myMaxXY / maxXY;
        final BitmapFactory.Options sampleOptions = new BitmapFactory.Options();
        if (sampleSize > 1) {
            sampleOptions.inSampleSize = sampleSize;
        }
        final Bitmap decodedImage = BitmapFactory.decodeFile(filePath, sampleOptions);
        if (decodedImage != null) {
            for (int i = 0; i < ORIENTATIONS.length; i++) {
                if (orientation == ORIENTATIONS[i]) {
                    final Matrix matrix = new Matrix();
                    matrix.postRotate(ROTATION[i]);
                    return Bitmap.createBitmap(decodedImage, 0, 0, decodedImage.getWidth(), decodedImage.getHeight(), matrix, true);
                }
            }
        }
        return decodedImage;
    }
}

