package com.example.dialerhw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity{
    SharedPreferences speedsave;
    TextView number;
    View.OnLongClickListener lcHandler = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            switch(v.getId()){
                case R.id.speed_1: speedDialEdit(1,((Button)v).getText().toString()); break;
                case R.id.speed_2: speedDialEdit(2,((Button)v).getText().toString()); break;
                case R.id.speed_3: speedDialEdit(3,((Button)v).getText().toString()); break;
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //easy access to textview
        number = (TextView) findViewById(R.id.numberholder);
        speedsave = getPreferences(Context.MODE_PRIVATE);
        //assign button text/handler
        Button x = (Button) findViewById(R.id.speed_1);
        x.setOnLongClickListener(lcHandler);
        x.setText(speedsave.getString("speed_1",""));

        x = (Button) findViewById(R.id.speed_2);
        x.setOnLongClickListener(lcHandler);
        x.setText(speedsave.getString("speed_2",""));

        x = (Button) findViewById(R.id.speed_3);
        x.setOnLongClickListener(lcHandler);
        x.setText(speedsave.getString("speed_3",""));

    }




    //NUMPAD CODE
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
    //////////////////////////////////////////////////////////////
    public void call(View view){
        Uri phone = Uri.parse("tel:"+number.getText().toString());
        Intent callintent = new Intent(Intent.ACTION_CALL,phone);
        //Intent callintent = new Intent(Intent.ACTION_DIAL); //dial has no handler on my devices
        //callintent.setData(phone);  call requires permissions, you just can't win
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(callintent, 0);
        boolean isIntentSafe = activities.size() > 0;

// Start an activity if it's safe
        if (isIntentSafe) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PERMISSION_DENIED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){Log.i("shouldReq","i'm supposed to show ui stuff here");}
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE },0); //examples for v23 dont have ActivityCompat. , really messed with me

            }
            else{startActivity(callintent);}


        }
        else{Log.i("callfail","no call handler");}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//not checking who requests or what permissions cuz only 1 of each
    if (grantResults[0]==PERMISSION_GRANTED) {
        Uri phone = Uri.parse("tel:"+number.getText().toString());
        Intent callintent = new Intent(Intent.ACTION_CALL,phone);
        startActivity(callintent);
    }



    }

    //SPEED DIAL CODE
    public void speedDialEdit(int button,String prevText){
        Intent intent = new Intent(this,SpeedDialChange.class);
        intent.putExtra("OGTEXT",prevText);
        intent.putExtra("button_no",button);
        startActivityForResult(intent,0);
    }
    public void speedDialPress(View v){

        number.setText(((Button)v).getText().toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences.Editor edittool = speedsave.edit();
        if (resultCode == Activity.RESULT_OK) {
            String res = data.getStringExtra("NEWTEXT");
            switch(data.getIntExtra("button_no",0)){
                case 1:((Button)findViewById(R.id.speed_1)).setText(res); edittool.putString("speed_1",res);break;
                case 2:((Button)findViewById(R.id.speed_2)).setText(res); edittool.putString("speed_2",res);break;
                case 3:((Button)findViewById(R.id.speed_3)).setText(res); edittool.putString("speed_3",res);break;

            }
            edittool.apply();

        }
    }
    ////////////////////////////////////



}