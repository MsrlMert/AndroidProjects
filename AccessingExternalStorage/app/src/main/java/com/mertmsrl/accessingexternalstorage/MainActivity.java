package com.mertmsrl.accessingexternalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        try {
            File file = Environment.getExternalStorageDirectory();
            File subdir = new File(file.getAbsolutePath()+"/deneme");

            subdir.mkdirs();
            File dosya = new File(subdir, "Dosya.txt");
            FileOutputStream outputStream = new FileOutputStream(dosya);
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            streamWriter.write("This is the first try");
            streamWriter.close();
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}