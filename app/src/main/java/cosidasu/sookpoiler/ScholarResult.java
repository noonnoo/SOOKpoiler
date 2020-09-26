package cosidasu.sookpoiler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScholarResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_result);

        String great_result_txt = getIntent().getStringExtra("my_scholar");
        String student_result_txt = getIntent().getStringExtra("major_student");

        TextView scholar_result = (TextView)findViewById(R.id.great_result);
        TextView student = (TextView)findViewById(R.id.student_grade);

        scholar_result.setText(great_result_txt);
        student.setText(student_result_txt);
    }
}
