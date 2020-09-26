package cosidasu.sookpoiler;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Scholar extends AppCompatActivity {

    LinearLayout scholarLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);

        scholarLayout = (LinearLayout) findViewById(R.id.scholarLayout);

        animationDrawable = (AnimationDrawable) scholarLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }

    public void for_none_engineering(View view){
        Intent scholar_none_engineer = new Intent(Scholar.this, ScholarNoEngineer.class);
        Scholar.this.startActivity(scholar_none_engineer);
    }

    public void for_engineering(View view){
        Intent scholar_engineer = new Intent(Scholar.this, ScholarEngineer.class);
        Scholar.this.startActivity(scholar_engineer);
    }
}
