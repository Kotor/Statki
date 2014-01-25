package com.example.statki;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PlanszaPrzeciwnika extends Activity implements OnClickListener {
	private ArrayList<Integer> planszaPrzeciwnika;
	int cztero = 0, troj = 0, dwu = 0, jedno = 0;
	private Button left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.przeciwnik);
		Plansza.inst().wyczyscPlansze();
		planszaPrzeciwnika = Plansza.inst().getPlanszaGry();
				    	
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
		left = (Button) this.findViewById(R.id.left);
		left.setOnClickListener(this);
		
		ustawPlansze(planszaPrzeciwnika);
				
		czteroTxt.setText(Integer.toString(cztero));
		trojTxt.setText(Integer.toString(troj));
		dwuTxt.setText(Integer.toString(dwu));
		jednoTxt.setText(Integer.toString(jedno));
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View imgView,
					int position, long id) {
				ImageView imageView = (ImageView) imgView;

				strzal(position, imageView);
			}		
		});
	}
	
	private void strzal(int position, ImageView imageView) {
		if (planszaPrzeciwnika.get(position) == 2) {
			imageView.setImageResource(R.drawable.trafiony);
			planszaPrzeciwnika.set(position, 4);
		} else if (planszaPrzeciwnika.get(position) == 1) {
			imageView.setImageResource(R.drawable.pudlo);
			planszaPrzeciwnika.set(position, 3);
			// TO DO: STRZA£
			Gra n = new Gra();
			n.strzal(n.plansza);
			Intent intent = new Intent();
			intent.setClassName(getApplicationContext(),"com.example.statki.Gra");
			startActivity(intent);
			overridePendingTransition(R.anim.from_left, R.anim.to_right);
		}
	}

	private void ustawPlansze(ArrayList<Integer> plansza) {
		plansza.set(3, 2);
		plansza.set(18, 2);
		plansza.set(22, 2);
		plansza.set(23, 2);
		plansza.set(24, 2);
		plansza.set(25, 2);
		plansza.set(28, 2);
		plansza.set(41, 2);
		plansza.set(44, 2);
		plansza.set(45, 2);
		plansza.set(49, 2);
		plansza.set(51, 2);
		plansza.set(61, 2);
		plansza.set(66, 2);
		plansza.set(67, 2);
		plansza.set(68, 2);
		plansza.set(83, 2);
		plansza.set(86, 2);
		plansza.set(88, 2);
		plansza.set(93, 2);
	}
	/*
	private void ustawCztero(ArrayList<Integer> plansza) {
		Random losuj = new Random();
		int liczba;
		liczba = losuj.nextInt(100);
		if (!(liczba/10 < 7 || liczba%10 < 7))  liczba = losuj.nextInt(100);
		wyborPola(liczba);
		int lewoDol = losuj.nextInt(2);
		if (lewoDol == 0) {
			wyborPola(liczba+1);
			wyborPola(liczba+2);
			wyborPola(liczba+3);
		} else {
			wyborPola(liczba+10);
			wyborPola(liczba+20);
			wyborPola(liczba+30);
		}
	}
		
	private void wyborPola(int position) {
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
		if (sprawdzCzyMozna(wartosci, position)) {;
			planszaPrzeciwnika.set(position, 2);
		}
		sprawdzPlansze(planszaPrzeciwnika);
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
			if (planszaPrzeciwnika.get(position + b) != 1) return false;
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
					zeruj (plansza2, position, 0, 0, 0, 1, 0, 0);
				} else jedno++;
			}
			if (a == 20) {
				if (plansza2.get(position+10) == 2 && plansza2.get(position+20) == 2) {								
					troj++;
					zeruj (plansza2, position, 0, 0, 0, 1, 1, 0);
				} else if (plansza2.get(position+10) == 2) {							
					dwu++;	
					zeruj (plansza2, position, 0, 0, 0, 1, 0, 0);
				} else jedno++;
			}					
			if (a == 30) {
				if (plansza2.get(position+10) == 2 && plansza2.get(position+20) == 2 && plansza2.get(position+30) == 2) {
					cztero++;
					zeruj (plansza2, position, 0, 0, 0, 1, 1, 1);
				}else if (plansza2.get(position+10) == 2 && plansza2.get(position+20) == 2) {								
					troj++;
					zeruj (plansza2, position, 0, 0, 0, 1, 1, 0);
				}else if (plansza2.get(position+10) == 2) {							
					dwu++;	
					zeruj (plansza2, position, 0, 0, 0, 1, 0, 0);
				} else jedno++;	
			}
			if (a == 1) {
				if (plansza2.get(position+1) == 2) {							
					dwu++;
					zeruj (plansza2, position, 1, 0, 0, 0, 0, 0);
				} else if (position > 89) jedno++;	
			}
			if (a == 2) {
				if (plansza2.get(position+1) == 2 && plansza2.get(position+2) == 2) {								
					troj++;
					zeruj (plansza2, position, 1, 1, 0, 0, 0, 0);
				} else if (plansza2.get(position+1) == 2) {							
					dwu++;	
					zeruj (plansza2, position, 1, 0, 0, 0, 0, 0);
				} else if (position > 89) jedno++;	
			}					
			if (a == 3) {
				if (plansza2.get(position+1) == 2 && plansza2.get(position+2) == 2 && plansza2.get(position+3) == 2) {
					cztero++;
					zeruj (plansza2, position, 1, 1, 1, 0, 0, 0);
				}else if (plansza2.get(position+1) == 2 && plansza2.get(position+2) == 2) {								
					troj++;
					zeruj (plansza2, position, 1, 1, 0, 0, 0, 0);
				}else if (plansza2.get(position+1) == 2) {							
					dwu++;	
					zeruj (plansza2, position, 1, 0, 0, 0, 0, 0);
				} else if (position > 89) jedno++;	
			}	
			if (a == 0) {
				jedno++;
			}
			plansza2.set(position, 1);
		}
	}
	
	private void zeruj(ArrayList<Integer> plansza2, int position, int a, int b, int c, int d, int e, int f) {
		if (a != 0) plansza2.set(position+1, 1);
		if (b != 0) plansza2.set(position+2, 1);
		if (c != 0) plansza2.set(position+3, 1);
		if (d != 0) plansza2.set(position+10, 1);
		if (e != 0) plansza2.set(position+20, 1);
		if (f != 0) plansza2.set(position+30, 1);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.getId() == R.id.left) {
			intent.setClassName(this,"com.example.statki.Gra");
			startActivity(intent);
			this.overridePendingTransition(R.anim.from_left, R.anim.to_right);		
		}
	}
}
