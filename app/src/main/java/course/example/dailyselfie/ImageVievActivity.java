package course.example.dailyselfie;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by User on 27.08.2015.
 */
public class ImageVievActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        ImageView picture = (ImageView)findViewById(R.id.picture);
        String path = getIntent().getStringExtra("picturePath");

        File imageFile = new File(path);
        if(imageFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            picture.setImageBitmap(myBitmap);
        }
    }
}
