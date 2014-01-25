package com.example.statki;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class PlanszaGracza extends Activity implements OnClickListener {	
	private ArrayList<Integer> plansza;
	int cztero = 0, troj = 0, dwu = 0, jedno = 0;
	private Button right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gracz);
		Plansza.inst().wyczyscPlansze();
		plansza = Plansza.inst().getPlanszaGry();
		Intent intent = getIntent();
		plansza = intent.getIntegerArrayListExtra("plansza");
		for (int i = 0; i<100; i++) {
			Log.i("asd", Integer.toString(plansza.get(i)));
		}
				    	
    	Display display = getWindowManager().getDefaultDisplay();
		Rect rect = new Rect();
    	display.getRectSize(rect);
    	GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, rect.width()));
		final TextView czteroTxt = (TextView) findViewById(R.id.czteroLiczba);
		czteroTxt.setText(Integer.toString(cztero));
		final TextView trojTxt = (TextView) findViewById(R.id.trojLiczba);
		trojTxt.setText(Integer.toString(troj));
		final TextView dwuTxt = (TextView) findViewById(R.id.dwuLiczba);
		dwuTxt.setText(Integer.toString(dwu));
		final TextView jednoTxt = (TextView) findViewById(R.id.jednoLiczba);
		jednoTxt.setText(Integer.toString(jedno));
		right = (Button) this.findViewById(R.id.right);
		right.setOnClickListener(this);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if(v.getId() == R.id.right) {
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			intent.setClassName(this,"com.example.statki.PlanszaPrzeciwnika");
			startActivity(intent);
			this.overridePendingTransition(R.anim.from_right, R.anim.to_left);
		}
	}
}