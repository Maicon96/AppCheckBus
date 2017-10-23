package com.example.maicon.checkbus;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.maicon.checkbus.database.DataBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {
    private Bitmap imagemGaleria;
    private ImageView image;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        image = (ImageView) findViewById(R.id.image);

        dataBase = new DataBase(this);
        conn = dataBase.getReadableDatabase();
    }

    public void fotoGaleria(View view) {
        Intent takePitctureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(takePitctureIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = data.getData();
            String[] filepath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filepath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filepath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            image.setImageBitmap(imagemGaleria);

        }
    }

    public void pegafoto(View view) {

        byte[] getFoto;
        int teste = 0;
        String sql = "SELECT FOTO FROM CARTEIRA WHERE _id = 7";
        try {
            Cursor cursor = conn.rawQuery(sql, null);
            Log.d("carollo", "cursor tem tantas linhas : " + cursor.getCount());

            cursor.moveToFirst();

            getFoto = cursor.getBlob(cursor.getColumnIndex("FOTO"));

            ByteArrayInputStream imageStream = new ByteArrayInputStream(getFoto);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(imageBitmap);
        } catch (Exception e) {
            Log.d("carollo","erro ao buscar imagem "+ e.getMessage() );
        }
    }

    public void foto(View view) {

        byte[] salva = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagemGaleria.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        salva = stream.toByteArray();

        try {
            ContentValues values = new ContentValues();

            values.put("FOTO", salva);

            conn.insertOrThrow("CARTEIRA", null, values);
            Log.d("carollo", "SALVOU");
        } catch (Exception e) {
            Log.d("carollo", "erro ao inserir imagem," + e.getMessage());
        }
    }
}
