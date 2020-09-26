package cosidasu.sookpoiler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RankResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_result);

        int percentage = getIntent().getIntExtra("percentage",0);
        int ranking = getIntent().getIntExtra("rank",0);
        int all_percent = getIntent().getIntExtra("all_percent",0);

        TextView percent = (TextView) findViewById(R.id.result);
        TextView rank = (TextView) findViewById(R.id.rank);

        percent.setText("전체" + all_percent +"%로,\n"
                +"상위"+Integer.toString(percentage) +"%이고,");
        rank.setText(Integer.toString(ranking) + "등 입니다.");
    }
}
