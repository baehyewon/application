package com.baehw1107.contents_mgt;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by baehw_000 on 2017-05-22.
 */

public class ContentsRegForm extends AppCompatActivity{
    private int PICK_IMAGE_REQUEST = 1;
    public final static int PICK_PHOTO_CODE = 1046;
    ImageView iv_photo;
    TextView tv_photo;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iv_photo = (ImageView) findViewById(R.id.photo);
        tv_photo = (TextView) findViewById(R.id.tv_photo);

    }

    public void selectPhoto(View v) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri photoUri = data.getData();
        path = getRealPathFromURI(this, photoUri);

        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

            int WIDTH = 150;
            int width = WIDTH;
            float ratio = (float) bm.getHeight() / bm.getWidth();
            int height = (int) (WIDTH * ratio);

            Bitmap thumb = Bitmap.createScaledBitmap(bm, width, height, false);
            iv_photo.setImageBitmap(thumb);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj,
                    null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void register(View v) {
        EditText et_title = (EditText) findViewById(R.id.title);
        String str_title = et_title.getText().toString();
        RadioGroup rg_orientation = (RadioGroup) findViewById(
                R.id.orientation);
        RadioButton rb_orientation;
        String str_orientation = "";
        if (rg_orientation.getCheckedRadioButtonId() == R.id.landscape) {
            rb_orientation = (RadioButton) findViewById(R.id.landscape);
            str_orientation = rb_orientation.getText().toString();
        }
        if (rg_orientation.getCheckedRadioButtonId() == R.id.portrait) {
            rb_orientation = (RadioButton) findViewById(R.id.portrait);
            str_orientation = rb_orientation.getText().toString();
        }

        CheckBox chk_background;
        String str_background1 = "";
        String str_background2 = "";
        String str_background3 = "";

        chk_background = (CheckBox) findViewById(R.id.background1);
        if (chk_background.isChecked()) {
            str_background1 = (String) chk_background.getText();
        }
        chk_background = (CheckBox) findViewById(R.id.background2);
        if (chk_background.isChecked()) {
            str_background2 = (String) chk_background.getText();
        }
        chk_background = (CheckBox) findViewById(R.id.background3);
        if (chk_background.isChecked()) {
            str_background3 = (String) chk_background.getText();
        }

        Intent it = new Intent(this, ContentsDetail.class);
        it.putExtra("it_title", str_title);
        it.putExtra("it_orientation", str_orientation);
        it.putExtra("it_background1", str_background1);
        it.putExtra("it_background2", str_background2);
        it.putExtra("it_background3", str_background3);
        it.putExtra("it_path", path);
        startActivity(it);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings2) {
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
