package cosidasu.sookpoiler;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowChart extends AppCompatActivity {
    int num[] = {0,0,0,0,0,0,0,0,0,0,0,0};
    String gr[] = {"A+", "A0", "A-","B+", "B0", "B-","C+", "C0", "C-","D+", "D0", "D-"};
    int semester[] = {0,0,0,0,0,0,0,0,0,0};
    double total[] = {0,0,0,0,0,0,0,0,0,0};
    double[] grade;
    String[] rows, cols;

    String[][] subjects, part,  alphabet;
    Double[][] grades, scores;
    int ttn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chart);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);
        rows = getIntent().getStringArrayExtra("rows");

        subjects = new String[10][15];       //과목명
        part = new String[10][15];            //구분
        grades = new Double[10][15];         //학점
        scores = new Double[10][15];         //평점
        alphabet = new String[10][15];       //알파벳

        grade = new double[rows.length];
        splitCols();
        countAlpha();
        calculate();
        setupPieChart();
        setupLineChart();
        TextView total_num = (TextView)findViewById(R.id.total_scores);
        total_num.setText("총 이수 학점은"+ttn+"입니다.");
    }

    private void calculate() {

        for(int i = 0 ; i< semester.length ; i++){
            if(semester[i] != 0){
                double n = 0;
                double k = 0;
                for(int j = 0; j< semester[i]; j++){
                    n += (grades[i][j] * scores[i][j]);
                    ttn += grades[i][j];
                    if(scores[i][j] != 0) {
                        k += grades[i][j];
                    }
                }
                double tt = n / k;
                total[i]= (tt * 100)/100;
                System.out.println("토탈: "+total[i]);
            } else{         }
        }

    }

    private void countAlpha() {
        for(int i = 0 ; i < grade.length; i++){
            System.out.println(grade[i]);
            if(grade[i] == 4.3){
                num[0]++;
            } else if(grade[i] == 4.0){
                num[1]++;
            } else if(grade[i] == 3.7){
                num[2]++;
            } else if(grade[i] == 3.3){
                num[3]++;
            } else if(grade[i] == 3.0){
                num[4]++;
            } else if(grade[i] == 2.7){
                num[5]++;
            } else if(grade[i] == 2.3){
                num[6]++;
            } else if(grade[i] == 2.0){
                num[7]++;
            } else if(grade[i] == 1.7){
                num[8]++;
            } else if(grade[i] == 1.3) {
                num[9]++;
            } else if(grade[i] == 1.0) {
                num[10]++;
            } else if(grade[i] == 0.7) {
                num[11]++;
            }
        }
    }

    private void splitCols() {

        String para ="1학기";                 //학기 나누기
        int sem = -1;                 //학기 카운트
        int j = 0, k=0;
        String trick;

        for(int i = 1; i < rows.length; i++){
            cols = rows[i].split(",");
            trick = cols[1];
            grade[k] = Double.parseDouble(cols[9]);

            if(!para.equals(trick)){      //학기가 바뀌면
                sem++;
                j = 0;
            }

            subjects[sem][j] = cols[3];
            part[sem][j] = cols[5];
            grades[sem][j] = Double.parseDouble(cols[6]);
            scores[sem][j] = Double.parseDouble(cols[9]);
            alphabet[sem][j] = cols[8];

            j++;
            k++;
            para = trick;
            semester[sem]++;
        }
    }
    private void setupLineChart() {
        List<Entry> lineEntries = new ArrayList<>();
        for(int i = 1 ; i < semester.length; i++){
            if(semester[i] != 0 ){
                lineEntries.add(new Entry((float)i,(float)total[i]));
            }
        }

        LineDataSet linedataSet = new LineDataSet(lineEntries, "Transition");
        LineData lineData = new LineData(linedataSet);

        linedataSet.setValueTextColor(Color.WHITE);
        linedataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        linedataSet.setCircleRadius(5f);
        linedataSet.setLineWidth(2.0f);
        linedataSet.setValueTextSize(10f);

        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.animateY(2000);
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0 ; i < num.length; i++){
            pieEntries.add(new PieEntry(num[i], gr[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "score");
        PieData data = new PieData(dataSet);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setValueTextColor(0x2222);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.setCenterText("학점 파이차트");
        chart.setNoDataTextColor(0x0000);
        chart.invalidate();
        chart.animateX(3000);
    }
}