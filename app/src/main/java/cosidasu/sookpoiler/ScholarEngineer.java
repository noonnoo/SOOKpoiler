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

public class ScholarEngineer extends AppCompatActivity {

    LinearLayout scholarLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_engineer);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);

        scholarLayout = (LinearLayout) findViewById(R.id.engineerLayout);

        animationDrawable = (AnimationDrawable) scholarLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }

    public void sch_engineer(View view){
        try {
            EditText score_txt = (EditText)findViewById(R.id.score);
            float score = Float.parseFloat(score_txt.getText().toString());
            String my_scholar="장학금";

            if(score < 3.3){
                my_scholar = "당신은 공학우수 장학금 대상에 \n해당되지 않습니다.";
            } else if(score >= 3.3 && score <3.5){
                my_scholar = "당신은 등록학기 수업료의 30%를 \n받을 수 있습니다.";
            } else if(score >= 3.5 && score <3.7) {
                my_scholar = "당신은 등록학기 수업료의 50%를 \n받을 수 있습니다.";
            } else if(score >= 3.7 && score <4.3){
                my_scholar = "당신은 등록학기 수업료의 70%를 \n받을 수 있습니다.";
            } else if(score >= 4.3 && score <3.7){
                my_scholar = "당신은 등록학기 수업료의 100%를 \n받을 수 있습니다.";
            } else {
                my_scholar = "잘못된 학점을 입력하셨습니다.";
            }

            Intent engineerIntent = new Intent(ScholarEngineer.this, EngineerResult.class);
            engineerIntent.putExtra("my_scholar",my_scholar);
            startActivity(engineerIntent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "아무것도 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
