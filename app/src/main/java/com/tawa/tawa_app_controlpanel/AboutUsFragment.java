package com.tawa.tawa_app_controlpanel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Objects;


public class AboutUsFragment extends Fragment {
ImageView facebook;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setHasOptionsMenu(true);
        TextView text = getActivity().findViewById(R.id.toolbar_title);
        text.setText("من نحن ؟");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        facebook=view.findViewById(R.id.facebook_icon);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                //  String facebookUrl = getFacebookPageURL(this);
                facebookIntent.setData(Uri.parse(getFacebookPageURL(getContext(), "france24")));
                startActivity(facebookIntent);
                Log.d("test", facebookIntent.toString())
                ;

            }
        });
}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public static String getFacebookPageURL(Context context, String pageid) {
        String result = "";
        final String FACEBOOK_PAGE_ID = pageid;
        final String FACEBOOK_URL = "https://fb.com/" + pageid;

        if (appInstalledOrNot(context, "com.facebook.katana")) {
            try {
                result = "fb://facewebmodal/f?href=https://www.facebook.com/" + FACEBOOK_PAGE_ID;
                // previous version, maybe relevant for old android APIs ?
                // return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } catch (Exception e) {
            }
        } else {
            result = FACEBOOK_URL;
        }
        return result;
    }

}