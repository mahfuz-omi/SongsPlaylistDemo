package com.example.user.songsplaylistdemo;

import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    ShareButton shareButton;


    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);

        this.toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar actionBar = getSupportActionBar();
//
//        if (actionBar != null)
//        {
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        this.shareButton = (ShareButton) this.toolbar.findViewById(R.id.facebookShareButton);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.omi.piniksong"))
                .build();
        shareButton.setShareContent(content);




    }

}
