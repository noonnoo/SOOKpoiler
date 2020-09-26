package cosidasu.sookpoiler;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ScholarNoEngineer extends AppCompatActivity {

    LinearLayout noneEngineerLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_no_engineer);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);

        noneEngineerLayout = (LinearLayout) findViewById(R.id.noneEngineerLayout);

        animationDrawable = (AnimationDrawable) noneEngineerLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }

    public void scholarClicked(View view){
        try {
            EditText all = (EditText) findViewById(R.id.peopleNum);
            EditText scholar_rank = (EditText) findViewById(R.id.rankingNo);

            int all_num = Integer.parseInt(all.getText().toString());
            int rank_num = Integer.parseInt(scholar_rank.getText().toString());

            int great1, great2, great3;
            String mysch = "우수";

            if( (all_num*0.05) < 1){
                great1 = 1;
                great2 = 1;
            } else {
                great1 = (int) (all_num*0.05);
                great2 = (int) (all_num*0.05);
            }

            great3 = (int) (all_num*0.15);
            String major_studnet = "당신의 과에서는 학부학년수석: 1등\n학업우수1: 2~"+ (1+great1)+
                    "등\n 학업우수2: "+(2+great1)+"~"+(1+great1+great2)+
                    "등\n 학업우수3: "+(2+great1+great2)+"~"+(1+great1+great2+great3)+
                    "등이\n 학업우수 장학금 대상입니다.";

            if(rank_num ==1){
                mysch ="학년부 수석으로 \n100%의 장학금을 받습니다.";
            } else if(2<=rank_num && rank_num <= (1+great1)){
                mysch = "학업우수1로 \n60%의 장학금을 받습니다.";
            } else if((2+great1) <= rank_num && rank_num <= (1+great1+great2)){
                mysch = "학업우수2로 \n40%의 장학금을 받습니다.";
            } else if((2+great1+great2) <= rank_num && rank_num <= (1+great1+great2+great3)){
                mysch="학업우수3으로 \n20%의 장학금을 받습니다.";
            } else {
                mysch="안타깝게도 장학금을 받지 못합니다.";
            }

            Intent scholarIntent = new Intent(ScholarNoEngineer.this, ScholarResult.class);
            scholarIntent.putExtra("my_scholar",mysch);
            scholarIntent.putExtra("major_student",major_studnet);
            startActivity(scholarIntent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "아무것도 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
