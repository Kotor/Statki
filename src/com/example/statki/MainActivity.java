package com.example.statki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button przyciskInformacje, przyciskWyjscie, nowaGra;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        nowaGra = (Button)findViewById(R.id.nowaGra);
        nowaGra.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                	Intent intent = new Intent(MainActivity.this, Gra.class);  
                	MainActivity.this.startActivity(intent);
                }
        });
        
        przyciskInformacje = (Button)findViewById(R.id.informacje);
        przyciskInformacje.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                	Intent intent = new Intent(MainActivity.this, Informacje.class);  
                	MainActivity.this.startActivity(intent);
                }
        });
        
        przyciskWyjscie = (Button)findViewById(R.id.wyjscie);
        przyciskWyjscie.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                	finish();
                    System.exit(0);
                }
        });
    }    
}
