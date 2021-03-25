package com.bhavika.proapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by bhavika on 06-11-2016.
 */
public class Util {

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info!=null && info.isConnected());
    }

    public static final String INSERT_URL = "http://www.firstform.16mb.com//project/insert_pro.php";
    public static final String LOGIN_URL = "http://www.firstform.16mb.com//project/login_pro.php";
    public static final String PROFILE_URL = "http://www.firstform.16mb.com//project/upload_profile.php";
    public static final String ALBUM_URL = "http://www.firstform.16mb.com/project/album_name.php";
    public static final String IMAGES_URL = "http://www.firstform.16mb.com/project/upload_images.php";
    public static final String ALBUM_NAME_URL = "http://www.firstform.16mb.com/project/retrieve_albName.php";
    public static final String ALBUM_IMAGES_URL = "http://www.firstform.16mb.com/project/retrieve_pictures.php";
    public static final String PROFILE_IMAGES_URL = "http://www.firstform.16mb.com/project/retrieve_images.php";
    public static final String ALL_PROFILES_URL = "http://www.firstform.16mb.com/project/retrieve_profiles.php";
    public static final String OTHER_PROFILE_URL = "http://www.firstform.16mb.com/project/retrieve_other_profile.php";
}
