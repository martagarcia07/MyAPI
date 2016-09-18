package com.appcnt.meekooloh.myapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.my_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        Button b = (Button)findViewById(R.id.my_button);
        b.setClickable(false);
        new LongRunningGetIO().execute();
    }
}

