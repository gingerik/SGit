package me.sheimi.android.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import me.sheimi.android.activities.SheimiFragmentActivity;
import me.sheimi.sgit.R;

import android.text.TextUtils;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by sheimi on 8/19/13.
 */
public class BasicFunctions {

    private static final String IMAGE_REQUEST_HASH = "http://www.gravatar.com/avatar/%s?s=40";

    public static String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; ++i) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            BasicFunctions.showException(e);
        }
        return "";
    }

    public static String buildGravatarURL(String email) {
        String hash = md5(email);
        String url = String.format(Locale.getDefault(), IMAGE_REQUEST_HASH,
                hash);
        return url;
    }

    private static SheimiFragmentActivity mActiveActivity;

    public static SheimiFragmentActivity getActiveActivity() {
        return mActiveActivity;
    }

    public static void setActiveActivity(SheimiFragmentActivity activity) {
        mActiveActivity = activity;
    }

    public static ImageLoader getImageLoader() {
        return getActiveActivity().getImageLoader();
    }

    public static void showException(Throwable t) {
        SheimiFragmentActivity activity = BasicFunctions.getActiveActivity();
        String msg = t.getMessage();
        if (TextUtils.isEmpty(msg)) {
            msg = activity.getString(R.string.dialog_error_unknown);
        }
        activity.showMessageDialog(R.string.dialog_error_title, msg);
        t.printStackTrace();
    }

    public static void showException(Throwable t, int res) {
        SheimiFragmentActivity activity = BasicFunctions.getActiveActivity();
        activity.showMessageDialog(R.string.dialog_error_title, activity.getString(res));
        t.printStackTrace();
    }

}
