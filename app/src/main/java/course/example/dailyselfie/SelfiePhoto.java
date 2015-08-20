package course.example.dailyselfie;

import android.graphics.Bitmap;

/**
 * Created by user on 20.08.2015.
 */
public class SelfiePhoto {


    String mName;
    String mImgPathName;


    public SelfiePhoto(String name, String imgPathName){
        this.mName = name;
        this.mImgPathName = imgPathName;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImgPathName() {
        return mImgPathName;
    }

    public void setmImgPathName(String mImgPathName) {
        this.mImgPathName = mImgPathName;
    }
}
