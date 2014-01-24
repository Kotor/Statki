package com.example.statki;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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
	int cztero = 0, troj = 0, dwu = 0, jedno = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gra);
		Plansza.inst().wyczyscPlansze();
		plansza = Plansza.inst().getPlanszaGry();

    	
    	Display display = getWindowManager().getDefaultDisplay();
		Rect rect = new Rect();
    	display.getRectSize(rect);
    	Log.i("width", Integer.toString(rect.width()));
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
				if (sprawdzCzyMozna(wartosci, position)) {
					ustawPole(position, imageView);
				} else {
					Toast.makeText(getApplicationContext(),
							"Statki nie mog¹ siê stykaæ ze sob¹.",Toast.LENGTH_SHORT).show();
				}
				sprawdzPlansze(plansza);
			}
			
			private boolean sprawdzCzyMozna(ArrayList<Integer> wartosci, int position) {
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
					if (plansza.get(position + b) != 1) return false;
				}
				return true;
			}
						
			private void sprawdzPlansze(ArrayList<Integer> plansza) {
				jedno = 0;
				dwu = 0;
				troj = 0;
				cztero = 0;
				ArrayList<Integer> plansza2 = new ArrayList<Integer>();
				for (int i=0; i<100; i++) {
					plansza2.add(plansza.get(i));
				}
				for (int i=0; i<100; i++) {
					if (plansza2.get(i) == 2) {
						sprawdzPole(i, plansza2);
					}
				}				
			}

			private void sprawdzPole(int position, ArrayList<Integer> plansza2) {
				ArrayList<Integer> wartosci = new ArrayList<Integer>();
				if (position/10 == 8) {
					wartosci.add(10);
				}
				if (position/10 == 7) {
					wartosci.add(20);
				}
				if (position/10 < 7) {
					wartosci.add(30);
				}
				if (position%10 == 8) {
					wartosci.add(1);
				}
				if (position%10 == 7) {
					wartosci.add(2);
				}
				if (position%10 < 7) {
					wartosci.add(3);
				}
				if (wartosci.isEmpty()) wartosci.add(0);				
				ustawLiczbe(wartosci, position, plansza2);				
			}
			
			public void ustawLiczbe(ArrayList<Integer> wartosci, int position, ArrayList<Integer> plansza2) {
				for (Integer a : wartosci) {
					if (a == 10) {
						if (plansza2.get(position+10) == 2) {							
							dwu++;
							plansza2.set(position+10, 1);
						} else jedno++;
					}
					if (a == 20) {
						if (plansza2.get(position+10) == 2 && plansza2.get(position+20) == 2) {								
							troj++;
							plansza2.set(position+10, 1);
							plansza2.set(position+20, 1);
						} else if (plansza2.get(position+10) == 2) {							
							dwu++;	
							plansza2.set(position+10, 1);
						} else jedno++;
					}					
					if (a == 30) {
						if (plansza2.get(position+10) == 2 && plansza2.get(position+20) == 2 && plansza2.get(position+30) == 2) {
							cztero++;
							plansza2.set(position+10, 1);
							plansza2.set(position+20, 1);
							plansza2.set(position+30, 1);
						}else if (plansza2.get(position+10) == 2 && plansza2.get(position+20) == 2) {								
							troj++;
							plansza2.set(position+10, 1);
							plansza2.set(position+20, 1);
						}else if (plansza2.get(position+10) == 2) {							
							dwu++;	
							plansza2.set(position+10, 1);
						} else jedno++;	
					}
					if (a == 1) {
						if (plansza2.get(position+1) == 2) {							
							dwu++;
							plansza2.set(position+1, 1);
						} else if (position > 89) jedno++;	
					}
					if (a == 2) {
						if (plansza2.get(position+1) == 2 && plansza2.get(position+2) == 2) {								
							troj++;
							plansza2.set(position+1, 1);
							plansza2.set(position+2, 1);
						} else if (plansza2.get(position+1) == 2) {							
							dwu++;	
							plansza2.set(position+1, 1);
						} else if (position > 89) jedno++;	
					}					
					if (a == 3) {
						if (plansza2.get(position+1) == 2 && plansza2.get(position+2) == 2 && plansza2.get(position+3) == 2) {
							cztero++;
							plansza2.set(position+1, 1);
							plansza2.set(position+2, 1);
							plansza2.set(position+3, 1);
						}else if (plansza2.get(position+1) == 2 && plansza2.get(position+2) == 2) {								
							troj++;
							plansza2.set(position+1, 1);
							plansza2.set(position+2, 1);
						}else if (plansza2.get(position+1) == 2) {							
							dwu++;	
							plansza2.set(position+1, 1);
						} else if (position > 89) jedno++;	
					}	
					if (a == 0) {
						jedno++;
					}
					plansza2.set(position, 1);
				}
			}
			
			private void ustawPole(int position, ImageView imageView) {
				if (plansza.get(position) == 1) {
					imageView.setImageResource(R.drawable.wybrane);
					plansza.set(position, 2);
				} else if (plansza.get(position) == 2) {
					imageView.setImageResource(R.drawable.pole);
					plansza.set(position, 1);					
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
