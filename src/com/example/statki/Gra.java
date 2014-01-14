package com.example.statki;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gra extends Activity {
	private ArrayList<Integer> plansza;
	int cztero = 1, troj = 2, dwu = 3, jedno = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gra);
		Plansza.inst().wyczyscPlansze();
		plansza = Plansza.inst().getPlanszaGry();

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this));
		final TextView czteroTxt = (TextView) findViewById(R.id.czteroLiczba);
		czteroTxt.setText(Integer.toString(cztero));
		final TextView trojTxt = (TextView) findViewById(R.id.trojLiczba);
		trojTxt.setText(Integer.toString(troj));
		final TextView dwuTxt = (TextView) findViewById(R.id.dwuLiczba);
		dwuTxt.setText(Integer.toString(dwu));
		final TextView jednoTxt = (TextView) findViewById(R.id.jednoLiczba);
		jednoTxt.setText(Integer.toString(jedno));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View imgView,
					int position, long id) {
				ImageView imageView = (ImageView) imgView;

				wyborPola(position, imageView);
				
				czteroTxt.setText(Integer.toString(cztero));
				trojTxt.setText(Integer.toString(troj));
				dwuTxt.setText(Integer.toString(dwu));
				jednoTxt.setText(Integer.toString(jedno));
			}

			private void wyborPola(int position, ImageView imageView) {
				ArrayList<Integer> wartosci = new ArrayList<Integer>();
				if (position%10 == 0) {
					wartosci.add(1);
				}
				if (position%10 == 9) {
					wartosci.add(2);
				}
				if (position < 10) {
					wartosci.add(3);
				}
				if (position > 89) {
					wartosci.add(4);
				}
				if (wartosci.isEmpty()) wartosci.add(0);
				if (sprawdzCzyPuste(wartosci, position)) {
					ustawPole(position, imageView);
				} else {
					Toast.makeText(getApplicationContext(),
							"Statki nie mog¹ siê stykaæ ze sob¹.",Toast.LENGTH_SHORT).show();
				}
			}
			
			private boolean sprawdzCzyPuste(ArrayList<Integer> wartosci, int position) {
				ArrayList<Integer> wartosciDoSprawdzenia = new ArrayList<Integer>();
				for (Integer a : wartosci) {
					if (a == 0) {
						wartosciDoSprawdzenia.add(-11);
						wartosciDoSprawdzenia.add(-9);
						wartosciDoSprawdzenia.add(9);
						wartosciDoSprawdzenia.add(11);
					}
					if (a == 1) {
						if (position != 0) wartosciDoSprawdzenia.add(-9);
						if (position != 90) wartosciDoSprawdzenia.add(11);
					}
					if (a == 2) {
						if (position != 9) wartosciDoSprawdzenia.add(-11);
						if (position != 99) wartosciDoSprawdzenia.add(9);
					}
					if (a == 3) {
						if (position != 0) wartosciDoSprawdzenia.add(9);
						if (position != 9) wartosciDoSprawdzenia.add(11);
					}
					if (a == 4) {
						if (position != 89) wartosciDoSprawdzenia.add(-11);
						if (position != 99) wartosciDoSprawdzenia.add(-9);
					}
				}
				for (Integer b : wartosciDoSprawdzenia) {
					Log.i("position", Integer.toString(position+b));
					if (plansza.get(position + b) != 1) return false;
				}
				return true;
			}
			
			private void ustawPole(int position, ImageView imageView) {
				if (plansza.get(position) == 1) {
					imageView.setImageResource(R.drawable.wybrane);
					plansza.set(position, 2);
					if ((position > 0 && plansza.get(position - 1) == 2) 
							|| (position < 99 && plansza.get(position + 1) == 2)
							|| (position > 9 && plansza.get(position - 10) == 2) 
							|| (position < 90 && plansza.get(position + 10) == 2 )) {
						dwu--;
						jedno++;
						if ((position > 1 && plansza.get(position - 1) == 2 && plansza.get(position - 2) == 2)
								|| (position < 98 && plansza.get(position + 1) == 2 && plansza.get(position + 2) == 2)
								|| (position > 19 && plansza.get(position - 10) == 2 && plansza.get(position - 20) == 2)
								|| (position < 80 && plansza.get(position + 10) == 2 && plansza.get(position + 20) == 2)) {
							troj--;
							dwu += 2;
							jedno--;
							if ((position > 2 && plansza.get(position - 3) == 2)
									|| (position < 97 && plansza.get(position + 3) == 2) 
									|| (position > 29 && plansza.get(position - 30) == 2)
									|| (position < 70 && plansza.get(position + 30) == 2)) {
								cztero--;
								troj += 2;
								dwu--;
							}
						}
					} else {
						jedno--;
					}
				} else {
					imageView.setImageResource(R.drawable.pole);
					plansza.set(position, 1);
					if ((position > 0 && plansza.get(position - 1) == 2)
							|| (position < 99 && plansza.get(position + 1) == 2)
							|| (position > 9 && plansza.get(position - 10) == 2)
							|| (position < 90 && plansza.get(position + 10) == 2)) {
						dwu++;
						jedno--;
						if ((position > 1 && plansza.get(position - 1) == 2 && plansza.get(position - 2) == 2)
								|| (position < 98 && plansza.get(position + 1) == 2 && plansza.get(position + 2) == 2)
								|| (position > 19 && plansza.get(position - 10) == 2 && plansza.get(position - 20) == 2)
								|| (position < 80 && plansza.get(position + 10) == 2 && plansza.get(position + 20) == 2)) {
							troj++;
							dwu -= 2;
							jedno++;
							if ((position > 2 && plansza.get(position - 3) == 2)
									|| (position < 97 && plansza.get(position + 3) == 2)
									|| (position > 29 && plansza.get(position - 30) == 2)
									|| (position < 70 && plansza.get(position + 30) == 2)) {
								cztero++;
								troj -= 2;
								dwu++;
							}
						}
					} else {
						jedno++;
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
