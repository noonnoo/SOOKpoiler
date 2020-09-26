package cosidasu.sookpoiler;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EngineerResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_result);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);

        String engineer_result_txt = getIntent().getStringExtra("my_scholar");
        TextView engineer_result = (TextView) findViewById(R.id.engineer_result);

        engineer_result.setText(engineer_result_txt);
    }
}
