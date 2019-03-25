package intgo.skillsacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button synfuels, tradeTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        synfuels = (Button) findViewById(R.id.bSynFuels);
        tradeTest = (Button) findViewById(R.id.bTradeTest);

        synfuels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), RegisterSynFuel.class);
                startActivity(i);
            }
        });

        tradeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

    }
}
