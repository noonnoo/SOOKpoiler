package cosidasu.sookpoiler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Chart extends AppCompatActivity {
    private static final String TAG = "Read_Excel";
    Button picker, chart;
    TextView path;
    String filePath;

    String[] rowVal;

    AnimationDrawable bgAnimation;
    LinearLayout chartActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);

        chartActivity = (LinearLayout)findViewById(R.id.chartActivity);
        bgAnimation = (AnimationDrawable) chartActivity.getBackground();
        bgAnimation.setEnterFadeDuration(2500);
        bgAnimation.setExitFadeDuration(2500);
        bgAnimation.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        picker = (Button)findViewById(R.id.picker);
        path = (TextView)findViewById(R.id.path);
        chart = (Button)findViewById(R.id.createChart2);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(Chart.this)
                        .withRequestCode(1)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();

            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    readXLSXFile(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            path.setText(filePath);
            chart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  1001:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(Chart.this, "Permission Granted!", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(Chart.this, "Permission not Granted!", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void readXLSXFile(String filePath) throws IOException
    {
        //InputStream ExcelFileToRead = new FileInputStream(filePath);
        //System.out.println(ExcelFileToRead);
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();
        StringBuilder sb = new StringBuilder();

        while (rows.hasNext())
        {
            row=(XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            int cnum = 1;
            while (cells.hasNext())
            {
                cell=(XSSFCell) cells.next();

                String temp ="";
                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                {
                    System.out.print(cell.getStringCellValue()+" ");
                    temp =cell.getStringCellValue();
                }
                else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                {
                    System.out.print(cell.getNumericCellValue()+" ");
                    temp = Double.toString(cell.getNumericCellValue());
                }
                else
                {       }

                sb.append(temp + ", ");
                cnum++;
            }
            sb.append(":");
        }
        parseStringBuilder(sb);
    }

    private void parseStringBuilder(StringBuilder mStringBuilder) {
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        rowVal = mStringBuilder.toString().split(":");

        Intent ChartIntent= new Intent(Chart.this, ShowChart.class);
        ChartIntent.putExtra("rows",rowVal);
        Chart.this.startActivity(ChartIntent);
    }
}
