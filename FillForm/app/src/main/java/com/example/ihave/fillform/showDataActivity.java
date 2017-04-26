package com.example.ihave.fillform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class showDataActivity extends AppCompatActivity {
    TextView showDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        setWidget();
    }

    private void setWidget() {
        Bundle bundle = getIntent().getExtras();
        String showData = bundle.getString("toSendData");
        showDataText = (TextView)findViewById(R.id.textViewData);

        showDataText.setText(showData);
    }

}
