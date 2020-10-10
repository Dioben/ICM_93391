package com.example.dialerhw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeedDialChange extends AppCompatActivity {
    int button;
    TextView number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_dial_change);
        number = (TextView) findViewById(R.id.numberholder);
        Intent intent = getIntent();
        number.setText(intent.getStringExtra("OGTEXT"));
        button = intent.getIntExtra("button_no",0);
    }
    //numpad code
    public void addNumber(View view){
        Button b = (Button) view;
        //number.append(text); //only updates view the on first press, i dont get it
        //docs say "upgrading it to TextView.BufferType.EDITABLE if it was not already editable." so i guess that's the problem
        String text = number.getText().toString()+b.getText().toString();
        number.setText(text);

    }
    public void deleteChar(View view){
        if (number.getText().toString().length()>0)
        number.setText(number.getText().subSequence(0,number.getText().length()-1));
    }
    ////////////////////////////////////////
    //Lower buttons
    public void clear(View view){
        number.setText("");
    }
    public void save(View view){
        Intent retval = new Intent();
        retval.putExtra("button_no",button);
        retval.putExtra("NEWTEXT",number.getText().toString());
        setResult(Activity.RESULT_OK,retval);
        finish();
    }
    /////////////////////
}