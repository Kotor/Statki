package com.example.statki;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

public class Gra extends Activity implements OnClickListener {
	public ArrayList<Integer> plansza;
	int cztero = 0, troj = 0, dwu = 0, jedno = 0;
	private Button zatwierdz, right;
	boolean zatwierdzPlansze = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gra);
		Plansza.inst().wyczyscPlansze();
		plansza = Plansza.inst().getPlanszaGry();
		
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
		zatwierdz = (Button) this.findViewById(R.id.zatwierdz);
		zatwierdz.setOnClickListener(this);
		right.setVisibility(View.GONE);

		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View imgView,
					int position, long id) {
				if (!zatwierdzPlansze) {
					ImageView imageView = (ImageView) imgView;

					wyborPola(position, imageView);

					czteroTxt.setText(Integer.toString(cztero));
					trojTxt.setText(Integer.toString(troj));
					dwuTxt.setText(Integer.toString(dwu));
					jednoTxt.setText(Integer.toString(jedno));
				}				
			}
		});
	}
	
	public void strzal(View imgView, ArrayList<Integer> plansza) {
		
		ImageView imageView = (ImageView) imgView;
		Random losuj = new Random();
		int position = losuj.nextInt(100);
		Log.i("position", Integer.toString(position));
		if (plansza.get(position) == 2) {
			imageView.setImageResource(R.drawable.trafiony);
			plansza.set(position, 4);
			strzal(imgView, plansza);
		} else if (plansza.get(position) == 1) {
			imageView.setImageResource(R.drawable.pudlo);
			plansza.set(position, 3);
			Intent intent = new Intent();
			intent.setClassName(getApplicationContext(),"com.example.statki.PlanszaPrzeciwnika");
			startActivity(intent);
			overridePendingTransition(R.anim.from_right, R.anim.to_left);
		}
	}

	private void wyborPola(int position, ImageView imageView) {
		ArrayList<Integer> wartosci = new ArrayList<Integer>();
		if (position % 10 == 0) {
			wartosci.add(1);
		}
		if (position % 10 == 9) {
			wartosci.add(2);
		}
		if (position < 10) {
			wartosci.add(3);
		}
		if (position > 89) {
			wartosci.add(4);
		}
		if (wartosci.isEmpty())
			wartosci.add(0);
		if (sprawdzCzyMozna(wartosci, position)) {
			ustawPole(position, imageView);
		} else {
			Toast.makeText(getApplicationContext(),
					"Statki nie mog¹ siê stykaæ ze sob¹.", Toast.LENGTH_SHORT).show();
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
				if (position != 0)
					wartosciDoSprawdzenia.add(-9);
				if (position != 90)
					wartosciDoSprawdzenia.add(11);
			}
			if (a == 2) {
				if (position != 9)
					wartosciDoSprawdzenia.add(-11);
				if (position != 99)
					wartosciDoSprawdzenia.add(9);
			}
			if (a == 3) {
				if (position != 0)
					wartosciDoSprawdzenia.add(9);
				if (position != 9)
					wartosciDoSprawdzenia.add(11);
			}
			if (a == 4) {
				if (position != 89)
					wartosciDoSprawdzenia.add(-11);
				if (position != 99)
					wartosciDoSprawdzenia.add(-9);
			}
		}
		for (Integer b : wartosciDoSprawdzenia) {
			if (plansza.get(position + b) != 1)
				return false;
		}
		return true;
	}
			
	private void sprawdzPlansze(ArrayList<Integer> plansza) {
		jedno = 0;
		dwu = 0;
		troj = 0;
		cztero = 0;
		ArrayList<Integer> plansza2 = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			plansza2.add(plansza.get(i));
		}
		for (int i = 0; i < 100; i++) {
			if (plansza2.get(i) == 2) {
				sprawdzPole(i, plansza2);
			}
		}
	}

	private void sprawdzPole(int position, ArrayList<Integer> plansza2) {
		ArrayList<Integer> wartosci = new ArrayList<Integer>();
		if (position / 10 == 8) {
			wartosci.add(10);
		}
		if (position / 10 == 7) {
			wartosci.add(20);
		}
		if (position / 10 < 7) {
			wartosci.add(30);
		}
		if (position % 10 == 8) {
			wartosci.add(1);
		}
		if (position % 10 == 7) {
			wartosci.add(2);
		}
		if (position % 10 < 7) {
			wartosci.add(3);
		}
		if (wartosci.isEmpty())
			wartosci.add(0);
		ustawLiczbe(wartosci, position, plansza2);
	}

	public void ustawLiczbe(ArrayList<Integer> wartosci, int position,
			ArrayList<Integer> plansza2) {
		for (Integer a : wartosci) {
			if (a == 10) {
				if (plansza2.get(position + 10) == 2) {
					dwu++;
					zeruj(plansza2, position, 0, 0, 0, 1, 0, 0);
				} else
					jedno++;
			}
			if (a == 20) {
				if (plansza2.get(position + 10) == 2
						&& plansza2.get(position + 20) == 2) {
					troj++;
					zeruj(plansza2, position, 0, 0, 0, 1, 1, 0);
				} else if (plansza2.get(position + 10) == 2) {
					dwu++;
					zeruj(plansza2, position, 0, 0, 0, 1, 0, 0);
				} else
					jedno++;
			}
			if (a == 30) {
				if (plansza2.get(position + 10) == 2
						&& plansza2.get(position + 20) == 2
						&& plansza2.get(position + 30) == 2) {
					cztero++;
					zeruj(plansza2, position, 0, 0, 0, 1, 1, 1);
				} else if (plansza2.get(position + 10) == 2
						&& plansza2.get(position + 20) == 2) {
					troj++;
					zeruj(plansza2, position, 0, 0, 0, 1, 1, 0);
				} else if (plansza2.get(position + 10) == 2) {
					dwu++;
					zeruj(plansza2, position, 0, 0, 0, 1, 0, 0);
				} else
					jedno++;
			}
			if (a == 1) {
				if (plansza2.get(position + 1) == 2) {
					dwu++;
					zeruj(plansza2, position, 1, 0, 0, 0, 0, 0);
				} else if (position > 89)
					jedno++;
			}
			if (a == 2) {
				if (plansza2.get(position + 1) == 2
						&& plansza2.get(position + 2) == 2) {
					troj++;
					zeruj(plansza2, position, 1, 1, 0, 0, 0, 0);
				} else if (plansza2.get(position + 1) == 2) {
					dwu++;
					zeruj(plansza2, position, 1, 0, 0, 0, 0, 0);
				} else if (position > 89)
					jedno++;
			}
			if (a == 3) {
				if (plansza2.get(position + 1) == 2
						&& plansza2.get(position + 2) == 2
						&& plansza2.get(position + 3) == 2) {
					cztero++;
					zeruj(plansza2, position, 1, 1, 1, 0, 0, 0);
				} else if (plansza2.get(position + 1) == 2
						&& plansza2.get(position + 2) == 2) {
					troj++;
					zeruj(plansza2, position, 1, 1, 0, 0, 0, 0);
				} else if (plansza2.get(position + 1) == 2) {
					dwu++;
					zeruj(plansza2, position, 1, 0, 0, 0, 0, 0);
				} else if (position > 89)
					jedno++;
			}
			if (a == 0) {
				jedno++;
			}
		}
	}

	private void zeruj(ArrayList<Integer> plansza2, int position, int a, int b,
			int c, int d, int e, int f) {
		if (a != 0)
			plansza2.set(position + 1, 1);
		if (b != 0)
			plansza2.set(position + 2, 1);
		if (c != 0)
			plansza2.set(position + 3, 1);
		if (d != 0)
			plansza2.set(position + 10, 1);
		if (e != 0)
			plansza2.set(position + 20, 1);
		if (f != 0)
			plansza2.set(position + 30, 1);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.getId() == R.id.right) {
			intent.setClassName(this, "com.example.statki.PlanszaPrzeciwnika");
			startActivity(intent);
			this.overridePendingTransition(R.anim.from_right, R.anim.to_left);
		} else if (v.getId() == R.id.zatwierdz) {
			if (cztero == 1 && troj == 2 && dwu == 3 && jedno == 4) {
				zatwierdzPlansze = true;
				zatwierdz.setVisibility(View.GONE);
				right.setVisibility(View.VISIBLE);
				intent.setClassName(getApplicationContext(),
						"com.example.statki.PlanszaPrzeciwnika");
				startActivity(intent);
				overridePendingTransition(R.anim.from_right, R.anim.to_left);
			} else {
				Toast.makeText(getApplicationContext(),
						"Nie ustawiono wszystkich statków.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}