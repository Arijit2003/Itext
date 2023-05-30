package com.example.itext;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText pdfContent;
    private TextInputEditText pdfName;
    private Button btnCreate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //writeExternalStorage.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                try{
                    if(!Objects.requireNonNull(pdfName.getText()).toString().equals("")){
                        createPdf();
                    }else{
                        Toast.makeText(MainActivity.this, "Give a file name", Toast.LENGTH_SHORT).show();
                    }

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }

            }
        });


    }

    private void init(){
        pdfContent=findViewById(R.id.pdfContent);
        pdfName=findViewById(R.id.pdfName);
        btnCreate=findViewById(R.id.btnCreate);
    }
    private void createPdf() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file;
        if(Objects.requireNonNull(pdfName.getText()).toString().contains(".pdf")) {
            file = new File(pdfPath, Objects.requireNonNull(pdfName.getText()).toString());
        }else{
            file = new File(pdfPath,pdfName.getText().toString()+".pdf");
        }
        if(!file.exists()) {
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph(Objects.requireNonNull(pdfContent.getText()).toString());
            document.add(paragraph);
            document.close();

            Toast.makeText(MainActivity.this, pdfName.getText().toString() + " created successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, pdfName.getText().toString()+" already exists", Toast.LENGTH_SHORT).show();
        }



    }
}