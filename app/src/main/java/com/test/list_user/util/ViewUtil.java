package com.test.list_user.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.test.list_user.R;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;


/**
 * Created by Rafael Quiles.
 */
public class ViewUtil {

    public static final String TAG = "ViewUtil";
    public static final int MAX_FILE_SIZE = 3700000;
    public static final int MAX_ATTACHMENT_SIZE = 10485760;


    public static String readableFileSize(final long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    public static void showMessage(final Context context, final int resource, final int duration) {
        if (context != null) {
            Toast.makeText(context, resource, duration).show();
        }
    }

    public static void showMessage(Context context, String text, int duration) {
        if (context != null) {
            Toast.makeText(context, text, duration).show();
        }
    }

    /**
     * Show a custom SnackBar notification without action
     */
    public static void showSnackBar(final Context context, final int resourceId, final int duration, final View view, int color) {
        if (view != null) {
            final Snackbar snackbar = Snackbar.make(view, resourceId, duration);
            //Background
            final View snackBarView = snackbar.getView();
            snackBarView.setBackgroundResource(color);
            //Message
            TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(ContextCompat.getColor(context, android.R.color.white));
            snackbar.show();
        }
    }

    /**
     * Show a custom SnackBar notification without action
     */
    public static void showSnackBar(final Context context, final String text, final int duration, final View view, int color) {
        if (view != null) {
            final Snackbar snackbar = Snackbar.make(view, text, duration);
            //Background
            final View snackBarView = snackbar.getView();
            snackBarView.setBackgroundResource(color);
            //Message
            TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(ContextCompat.getColor(context, android.R.color.white));
            snackbar.show();
        }
    }

    public static void showPopupIcons(final PopupMenu popup) {
        try {
            final Class<?> classPopupMenu = Class.forName(popup.getClass().getName());
            Field mPopup = classPopupMenu.getDeclaredField("mPopup");
            mPopup.setAccessible(true);
            final Object menuPopupHelper = mPopup.get(popup);
            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
            final Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
            setForceIcons.invoke(menuPopupHelper, true);
        } catch (Exception e) {
            Log.d(TAG, "can not show popup icons");
        }
    }


    private static String getExtension(final File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }


    private static String getMimeType(final File file) {
        final MimeTypeMap mime = MimeTypeMap.getSingleton();
        final int index = file.getName().lastIndexOf('.') + 1;
        final String ext = file.getName().substring(index).toLowerCase();
        return mime.getMimeTypeFromExtension(ext);
    }

    public static int findFolderColor(final Context context, final int color) {

        if (color == 0) {
            return ContextCompat.getColor(context, R.color.folder_white);
        }
        if (color == 2) {
            return ContextCompat.getColor(context, R.color.folder_yellow);
        }
        if (color == 3) {
            return ContextCompat.getColor(context, R.color.folder_blue);
        }
        if (color == 4) {
            return ContextCompat.getColor(context, R.color.folder_green);
        }
        if (color == 5) {
            return ContextCompat.getColor(context, R.color.folder_red);
        }
        return ContextCompat.getColor(context, R.color.folder_back);
    }

}
