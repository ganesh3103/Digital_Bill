package com.biller.bill;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

public class Screenshot {
    public static Bitmap takescreenshots (View V)
    {
        V.setDrawingCacheEnabled( true );
        V.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(V.getDrawingCache());

        V.setDrawingCacheEnabled( false );
        return b;
    }
    public static Bitmap takescreenshotsOfRootView(View v)
    {
        return takescreenshots( v.getRootView() );
    }
}
