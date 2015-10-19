package com.wixappsite.fakechat.fakechat.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.wixappsite.fakechat.fakechat.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Wix on 2015/9/27.
 */
public class MessageUtils {

    private  MessageUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public  static String getCurrentTime(Activity activity)
    {
        String time="";
        Time t=new Time();
        t.setToNow();
        int hour = t.hour;
        int minute = t.minute;
Log.e(String.valueOf(t.hour) + ":" + String.valueOf(t.minute), "");
   if(!SystemUtils.isEn(activity)) {
       if (hour > 12) {
           if (minute < 10)
               time += "下午 " + String.valueOf(hour - 12) + ":0" + minute;
           else
               time += "下午 " + String.valueOf(hour - 12) + ":" + minute;
       } else {
           if (minute < 10)
               time += "上午 " + String.valueOf(hour) + ":0" + minute;
           else
               time += "上午 " + String.valueOf(hour) + ":" + minute;
       }
   }
        else
   {
       if (hour > 12) {
           if (minute < 10)
               time +=   String.valueOf(hour - 12) + ":0" + minute+" PM";
           else
               time +=  String.valueOf(hour - 12) + ":" + minute+" PM";
       } else {
           if (minute < 10)
               time +=  String.valueOf(hour) + ":0" + minute+" AM";
           else
               time +=  String.valueOf(hour) + ":" + minute+" AM";
       }
   }

//        int second = t.second;
//
      return time;
    }
    public static void takeScreenshot(Activity activity) {


        try {

            File direct = new File(Environment.getExternalStorageDirectory() + "/FakeChat/FakeChat/");

                direct.mkdir();


            String mPath = Environment.getExternalStorageDirectory().toString() + "/FakeChat/FakeChat_"
                    + System.currentTimeMillis() + ".jpg";

            Bitmap bitmap = ScreenUtils.snapShotWithoutStatusBar(activity);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast toast;
            toast = Toast.makeText(activity,activity.getResources().getString(R.string.ss_saved)+
                    Environment.getExternalStorageDirectory() + "/FakeChat_"
                 + System.currentTimeMillis(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }


    public static void shareScreenshot(Activity activity) {


        try {

            File direct = new File(Environment.getExternalStorageDirectory().toString() + "/FakeChat/");
                direct.mkdir();

            String mPath = Environment.getExternalStorageDirectory().toString() + "/FakeChat/FakeChat_"
                    + System.currentTimeMillis() + ".jpg";


            Bitmap bitmap = ScreenUtils.snapShotWithoutStatusBar(activity);

            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Intent  intent=new Intent(Intent.ACTION_SEND);

            intent.setType("image/*");
            Uri u = Uri.fromFile(imageFile);
            intent.putExtra(Intent.EXTRA_STREAM, u);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            activity.startActivity(Intent.createChooser(intent, activity.getTitle()));
            activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R.string.share)));
            //  openScreenshot(imageFile);

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {



        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        //canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    public static Bitmap getRoundCornerImage(Bitmap bitmap_bg,Bitmap bitmap_in)
    {
        Bitmap roundConcerImage = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,500,500);
        Rect rectF = new Rect(0, 0, bitmap_in.getWidth(), bitmap_in.getHeight());
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap_in, rectF, rect, paint);
        return roundConcerImage;
    }
    public static Bitmap getShardImage(Bitmap bitmap_bg,Bitmap bitmap_in)
    {
        Bitmap roundConcerImage = Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,500,500);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(2,2,498,498);
        canvas.drawBitmap(bitmap_in, rect, rect2, paint);
        return roundConcerImage;
    }
    public static Bitmap decodeSampledBitmapFromFile(String path,
                                                     int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }

        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }


        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

}
