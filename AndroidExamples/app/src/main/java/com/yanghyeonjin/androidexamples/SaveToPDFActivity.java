package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle;
import com.tom_roush.pdfbox.pdmodel.font.PDFont;
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font;
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SaveToPDFActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SaveToPDF";

    private File root;
    private AssetManager assetManager;
    private ImageView ivPDF;
    private TextView tvPDF;
    private Button btnSaveToPDF;
    private PDFont fontPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_pdf);

        /* 아이디 연결 */
        ivPDF = findViewById(R.id.iv_pdf);
        tvPDF = findViewById(R.id.tv_pdf);
        btnSaveToPDF = findViewById(R.id.btn_save_to_pdf);

        /* drawable에 있는 이미지를 지정합니다. */
        ivPDF.setImageResource(R.drawable.full_moon_415501_1920);

        /* 100줄의 텍스트를 생성합니다. */
        StringBuilder strPDF = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            strPDF.append(i).append("번입니다").append("\n");
            tvPDF.setText(strPDF);
        }


        btnSaveToPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SaveTask saveTask = new SaveTask();
                saveTask.execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        PDFBoxResourceLoader.init(getApplicationContext());
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        assetManager = getAssets();

        if (ContextCompat.checkSelfPermission(SaveToPDFActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            /* 퍼미션 허용되지 않은 경우, 퍼미션 요청 */
            ActivityCompat.requestPermissions(SaveToPDFActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public String createPdf() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            fontPDF = PDType0Font.load(document, assetManager.open("nanum_gothic_bold.ttf"));
        } catch (IOException e) {
            Log.e(LOG_TAG, "폰트를 읽을 수 없습니다.", e);
        }

        PDPageContentStream contentStream;

        try {
            contentStream = new PDPageContentStream(document, page, true, true);

            Drawable drawable = ivPDF.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            int imageWidth = bitmap.getWidth();
            int imageHeight = bitmap.getHeight();
            int A4Wdith = (int) PDRectangle.A4.getWidth();
            int A4Height = (int) PDRectangle.A4.getHeight();

            float scale = (float) (A4Wdith / (float) imageWidth * 0.8);

            int newImagewidth = (int) (imageWidth * scale);
            int newImageHeight = (int) (imageHeight * scale);

            Bitmap resized = Bitmap.createScaledBitmap(bitmap, newImagewidth, newImageHeight, true);
            PDImageXObject pdImageXObject = LosslessFactory.createFromImage(document, resized);

            float posX = page.getCropBox().getWidth();
            float posY = page.getCropBox().getHeight();

            float adjustedX = (float) ((posX - newImagewidth ) * 0.5 + page.getCropBox().getLowerLeftX());
            float adjustedY = (float) ((posY - newImageHeight) * 0.9 + page.getCropBox().getLowerLeftY());

            contentStream.drawImage(pdImageXObject, adjustedX, adjustedY, newImagewidth, newImageHeight);



            int textW = 470;
            int textLeft = 70;

            String textN = tvPDF.getText().toString();
            int fontSize = 17;
            float leading = 1.5f * fontSize;

            List<String> lines = new ArrayList<>();
            int lastSpace = -1;


            for (String text : textN.split("\n"))
            {
                while (text.length() > 0) {
                    int spaceIndex = text.indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0)
                        spaceIndex = text.length();
                    String subString = text.substring(0, spaceIndex);
                    float size = fontSize * fontPDF.getStringWidth(subString) / 1000;
                    if (size > textW) {
                        if (lastSpace < 0)
                            lastSpace = spaceIndex;
                        subString = text.substring(0, lastSpace);
                        lines.add(subString);
                        text = text.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else if (spaceIndex == text.length()) {
                        lines.add(text);
                        text = "";
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }

            contentStream.beginText();
            contentStream.setFont(fontPDF, fontSize);
            contentStream.newLineAtOffset(textLeft, adjustedY - 20);

            for (String line: lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leading);
            }
            contentStream.endText();
            contentStream.close();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);
            Date date = new Date();
            String strNow = simpleDateFormat.format(date);

            String path = root.getAbsolutePath() + "/pdf_example_" + strNow + ".pdf";

            document.save(path);
            document.close();

            return path;


        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception thrown while creating PDF", e);
        }

        return "error";
    }

    class SaveTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            String path = createPdf();
            return path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(SaveToPDFActivity.this, "잠시 기다리세요.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String path) {
            super.onPostExecute(path);

            Toast.makeText(SaveToPDFActivity.this, path + "에 PDF 파일로 저장했습니다.", Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, path);
        }
    }
}
