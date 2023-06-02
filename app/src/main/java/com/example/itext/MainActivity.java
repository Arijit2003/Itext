package com.example.itext;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.RidgeBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import java.io.ByteArrayOutputStream;
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

            Text text1= new Text("Bold").setBold();
            Text text2= new Text("Italic").setItalic();
            Text text3= new Text("Underline").setUnderline();
            Paragraph paragraph1 = new Paragraph();
            paragraph1.add(text1).add(text2).add(text3);
            document.add(paragraph1);


            // Adding a list
            List list = new List();
            list.setListSymbol("\u007D ");
            list.add("C++").setBold().setFontSize(24.54f);
            list.add("C").setBold().setFontSize(24.54f);
            list.add("Java").setBold().setFontSize(24.54f);
            list.add("Kotlin").setBold().setFontSize(24.54f);
            list.add("SQL/PLSQL").setBold().setFontSize(24.54f);
            list.add("Python").setBold().setFontSize(24.54f);
            document.add(list);

            //Adding Image
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable drawable = getDrawable(R.drawable.img);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
            byte[] byteArray = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(byteArray);
            Image image = new Image(imageData);
            document.add(image);

            //Adding a table in pdf
            float[] columnWidths = {200f,200f};
            Table table = new Table(columnWidths);
            table.addCell(new Cell().setBackgroundColor(ColorConstants.CYAN).add(new Paragraph("Name")));
            table.addCell(new Cell().setBackgroundColor(ColorConstants.CYAN).add(new Paragraph("Age")));

            table.addCell("Arijit Modak");
            table.addCell("20");

            table.addCell("Braj Kishor Sharma");
            table.addCell("21");

            //table border

            Border border = new RidgeBorder(2);
            border.setColor(ColorConstants.BLACK);
            table.setBorder(border);
            document.add(table);
            document.close();

            Toast.makeText(MainActivity.this, pdfName.getText().toString() + " created successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, pdfName.getText().toString()+" already exists", Toast.LENGTH_SHORT).show();
        }



    }
}