package course.example.dailyselfie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String LOG_TAG = "myLog";

    SimpleAdapter adapter;
    ListView mListView;
    SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(R.id.list);
        updateList();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hm = (HashMap) mListView.getAdapter().getItem(position);
                String path = hm.get("picturePath");
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                Intent fullSizePicture = new Intent(MainActivity.this, ImageVievActivity.class);
                fullSizePicture.putExtra("picturePath", path);
                startActivity(fullSizePicture);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Take photo clicked", Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            storeImage(imageBitmap);
            updateList();
            Toast.makeText(this,"Picture saved", Toast.LENGTH_SHORT).show();
        }
    }


    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(LOG_TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();

        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String fileName =timeStamp.format(new Date());
        File mediaFile;
        String mImageName="MI_"+ fileName +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    private  void updateList(){
        ArrayList <HashMap<String, String>> aList = getPhotoPathArray();
        String [] from = {"picturePath", "name"};
        int [] to = {R.id.picture,R.id.text};
        adapter = new SimpleAdapter(this,aList,R.layout.item,from,to);
        mListView.setAdapter(adapter);
    }

    private ArrayList <HashMap<String, String>> getPhotoPathArray(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        if(!mediaStorageDir.exists()){
            return  new ArrayList <HashMap<String, String>>();
        }
        File [] file = mediaStorageDir.listFiles();
        ArrayList <HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < file.length;i++){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("picturePath", file[i].getAbsolutePath());
            hm.put("name", file[i].getName());
            aList.add(hm);
        }
        return aList;
    }

}
