package com.example.statki;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gra extends Activity {
	private ArrayList<Boolean> clicked;
	int cztero = 1, troj = 2, dwu = 3, jedno = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gra);
		clicked = new ArrayList<Boolean>();
		for (int i = 0; i < 100; i++) {
			clicked.add(false);
		}

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

				if (position % 10 == 9) {
					if (clicked.get(position) == ((position > 10 && clicked.get(position - 11)) 
							|| (position < 91 && clicked.get(position + 9))) 
							|| clicked.get(position)) {
						ustawPole(position, imageView);
					} else {
						Toast.makeText(getApplicationContext(),
								"Statki nie mog¹ siê stykaæ ze sob¹.",Toast.LENGTH_SHORT).show();
					}
				} else if (position % 10 == 0) {
					if (clicked.get(position) == ((position > 8 && clicked.get(position - 9)) 
							|| (position < 89 && clicked.get(position + 11))) 
							|| clicked.get(position)) {
						ustawPole(position, imageView);
					} else {
						Toast.makeText(getApplicationContext(),
								"Statki nie mog¹ siê stykaæ ze sob¹.",Toast.LENGTH_SHORT).show();
					}
				} else {
					if (clicked.get(position) == ((position > 10 && clicked.get(position - 11)) 
							|| (position < 91 && clicked.get(position + 9)))
							|| (position > 8 && clicked.get(position - 9)) 
							|| (position < 89 && clicked.get(position + 11))
							|| clicked.get(position)) {
						ustawPole(position, imageView);
					} else {
						Toast.makeText(getApplicationContext(),
								"Statki nie mog¹ siê stykaæ ze sob¹.",Toast.LENGTH_SHORT).show();
					}
				}
				czteroTxt.setText(Integer.toString(cztero));
				trojTxt.setText(Integer.toString(troj));
				dwuTxt.setText(Integer.toString(dwu));
				jednoTxt.setText(Integer.toString(jedno));
			}

			private void ustawPole(int position, ImageView imageView) {
				if (!clicked.get(position)) {
					imageView.setImageResource(R.drawable.wybrane);
					clicked.set(position, true);
					if ((position > 0 && clicked.get(position - 1)) 
							|| (position < 99 && clicked.get(position + 1))
							|| (position > 9 && clicked.get(position - 10)) 
							|| (position < 90 && clicked.get(position + 10))) {
						dwu--;
						jedno++;
						if ((position > 1 && clicked.get(position - 1) && clicked.get(position - 2))
								|| (position < 98 && clicked.get(position + 1) && clicked.get(position + 2))
								|| (position > 19 && clicked.get(position - 10) && clicked.get(position - 20))
								|| (position < 80 && clicked.get(position + 10) && clicked.get(position + 20))) {
							troj--;
							dwu += 2;
							jedno--;
							if ((position > 2 && clicked.get(position - 3))
									|| (position < 97 && clicked.get(position + 3)) 
									|| (position > 29 && clicked.get(position - 30))
									|| (position < 70 && clicked.get(position + 30))) {
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
					clicked.set(position, false);
					if ((position > 0 && clicked.get(position - 1))
							|| (position < 99 && clicked.get(position + 1))
							|| (position > 9 && clicked.get(position - 10))
							|| (position < 90 && clicked.get(position + 10))) {
						dwu++;
						jedno--;
						if ((position > 1 && clicked.get(position - 1) && clicked
								.get(position - 2))
								|| (position < 98 && clicked.get(position + 1) && clicked.get(position + 2))
								|| (position > 19 && clicked.get(position - 10) && clicked.get(position - 20))
								|| (position < 80 && clicked.get(position + 10) && clicked.get(position + 20))) {
							troj++;
							dwu -= 2;
							jedno++;
							if ((position > 2 && clicked.get(position - 3))
									|| (position < 97 && clicked.get(position + 3))
									|| (position > 29 && clicked.get(position - 30))
									|| (position < 70 && clicked.get(position + 30))) {
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
