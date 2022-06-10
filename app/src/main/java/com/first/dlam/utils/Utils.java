package com.first.dlam.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;

import java.io.OutputStream;

public class Utils {

    public static boolean addPictureToAlbum(Context context, Bitmap bitmap, String fileName) {
        if (DeviceUtils.getSDKVersionCode() >= Build.VERSION_CODES.Q) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, fileName);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            MediaScannerConnection.scanFile(
                    context,
                    new String[]{uri.getPath()},
                    new String[]{"image/jpeg"},
                    (path, uri1) -> {
                    }
            );

            return true;
        } else {
            String path = PathUtils.getExternalPicturesPath();
            Boolean state = ImageUtils.save(bitmap, path + "/" + fileName, Bitmap.CompressFormat.JPEG);
            if (state) {
                MediaScannerConnection.scanFile(
                        context,
                        new String[]{path + "/" + fileName},
                        new String[]{"image/jpeg"},
                        (path1, uri) -> {
                        }
                );

            }
            return state;
        }
    }

}
