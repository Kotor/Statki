package com.example.statki;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gra extends Activity implements OnClickListener {
	public static ArrayList<Integer> plansza;
	static int cztero = 1;
	static int troj = 2;
	static int dwu = 3;
	static int jedno = 4;
	static int wszystkie = 20;
	private Button zatwierdz, right;
	private TextView czteroTxt, trojTxt, dwuTxt, jednoTxt, dostepne;
	private ImageView czteroMaszt, trojMaszt, dwuMaszt, jednoMaszt;
	boolean zatwierdzPlansze = false;
	static GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gra);
		Plansza.inst().wyczyscPlansze();
		plansza = Plansza.inst().getPlanszaGry();
		
		Display display = getWindowManager().getDefaultDisplay();
		Rect rect = new Rect();
		display.getRectSize(rect);
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, rect.width()));
		dostepne = (TextView) findViewById(R.id.dostepne);
		czteroTxt = (TextView) findViewById(R.id.czteroLiczba);
		czteroTxt.setText(Integer.toString(cztero));
		czteroMaszt = (ImageView) findViewById(R.id.cztero);
		trojTxt = (TextView) findViewById(R.id.trojLiczba);
		trojTxt.setText(Integer.toString(troj));
		trojMaszt = (ImageView) findViewById(R.id.troj);
		dwuTxt = (TextView) findViewById(R.id.dwuLiczba);
		dwuTxt.setText(Integer.toString(dwu));
		dwuMaszt = (ImageView) findViewById(R.id.dwu);
		jednoTxt = (TextView) findViewById(R.id.jednoLiczba);
		jednoTxt.setText(Integer.toString(jedno));
		jednoMaszt = (ImageView) findViewById(R.id.jedno);
		
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
					
					wyborPola(position, imageView, "pole");

					czteroTxt.setText(Integer.toString(cztero));
					trojTxt.setText(Integer.toString(troj));
					dwuTxt.setText(Integer.toString(dwu));
					jednoTxt.setText(Integer.toString(jedno));
				}
			}
		});
	}
	
	public static void strzal() {	
		Random losuj = new Random();
		int position = losuj.nextInt(100);
		View myTopView = gridView.getChildAt(position);
		ArrayList<View> allViewsWithinMyTopView = getAllChildren(myTopView);	    
	    for (View child : allViewsWithinMyTopView) {
	    	if (child instanceof ImageView) {
	    		ImageView imV = (ImageView) child;
	    		Log.i("b", "c");
	        	Log.i("a", Integer.toString(plansza.get(position)));
	        	if (plansza.get(position) == 2) {
	        		Log.i("a", "k1");
					imV.setImageResource(R.drawable.trafiony);
					wszystkie--;
					plansza.set(position, 4);
					wyborPola(position, imV, "niedostepne");
					if (wszystkie == 0) {						
						//Toast.makeText(getApplicationContext(), "Przegra³eœ!", Toast.LENGTH_SHORT).show();
					}
					strzal();
				} else if (plansza.get(position) == 1) {					
					imV.setImageResource(R.drawable.pudlo);
					plansza.set(position, 3);
				} else if (plansza.get(position) == 3) {
					strzal();
				} else if (plansza.get(position) == 4) {
					strzal();
				}
	        }
	    }	    
	}
	
	private static ArrayList<View> getAllChildren(View v) {
		if (!(v instanceof ViewGroup)) {
			ArrayList<View> viewArrayList = new ArrayList<View>();
			viewArrayList.add(v);
			return viewArrayList;
		}
		ArrayList<View> result = new ArrayList<View>();
		ViewGroup vg = (ViewGroup) v;
		for (int i = 0; i < vg.getChildCount(); i++) {
			View child = vg.getChildAt(i);
			ArrayList<View> viewArrayList = new ArrayList<View>();
			viewArrayList.add(v);
			viewArrayList.addAll(getAllChildren(child));
			result.addAll(viewArrayList);
		}
		return result;
	}

	private static void wyborPola(int position, ImageView imageView, String wybor) {
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
		if (wybor.equals("pole")) {
			if (sprawdzCzyMozna(wartosci, position, "pole")) {
				ustawPole(position, imageView);
			} else {
				//Toast.makeText(getApplicationContext(), "Statki nie mog¹ siê stykaæ ze sob¹.", Toast.LENGTH_SHORT).show();
			}
			sprawdzPlansze(plansza);
		} else if (wybor.equals("niedostepne")) sprawdzCzyMozna(wartosci, position, wybor);
	}

	private static boolean sprawdzCzyMozna(ArrayList<Integer> wartosci, int position, String wybor) {
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
		if (wybor.equals("pole")) {
			for (Integer b : wartosciDoSprawdzenia) {
				if (plansza.get(position + b) != 1)
					return false;
			}
			return true;
		} else if (wybor.equals("niedostepne")) {
			for (Integer b : wartosciDoSprawdzenia) {
				plansza.set(position + b, 3);
				View myTopView = gridView.getChildAt(position + b);
				ArrayList<View> allViewsWithinMyTopView = getAllChildren(myTopView);
				for (View child : allViewsWithinMyTopView) {
					if (child instanceof ImageView) {
						ImageView imV = (ImageView) child;
						if (plansza.get(position+b) == 3) {
							imV.setImageResource(R.drawable.pudlo);
						}
					}
				}
			}
		}
		return true;
	}
			
	private static void sprawdzPlansze(ArrayList<Integer> plansza) {
		jedno = 4;
		dwu = 3;
		troj = 2;
		cztero = 1;
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

	private static void sprawdzPole(int position, ArrayList<Integer> plansza2) {
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

	public static void ustawLiczbe(ArrayList<Integer> wartosci, int position,
			ArrayList<Integer> plansza2) {
		for (Integer a : wartosci) {
			if (a == 10) {
				if (plansza2.get(position + 10) == 2) {
					dwu--;
					zeruj(plansza2, position, 0, 0, 0, 1, 0, 0);
				} else
					jedno--;
			}
			if (a == 20) {
				if (plansza2.get(position + 10) == 2
						&& plansza2.get(position + 20) == 2) {
					troj--;
					zeruj(plansza2, position, 0, 0, 0, 1, 1, 0);
				} else if (plansza2.get(position + 10) == 2) {
					dwu--;
					zeruj(plansza2, position, 0, 0, 0, 1, 0, 0);
				} else
					jedno--;
			}
			if (a == 30) {
				if (plansza2.get(position + 10) == 2
						&& plansza2.get(position + 20) == 2
						&& plansza2.get(position + 30) == 2) {
					cztero--;
					zeruj(plansza2, position, 0, 0, 0, 1, 1, 1);
				} else if (plansza2.get(position + 10) == 2
						&& plansza2.get(position + 20) == 2) {
					troj--;
					zeruj(plansza2, position, 0, 0, 0, 1, 1, 0);
				} else if (plansza2.get(position + 10) == 2) {
					dwu--;
					zeruj(plansza2, position, 0, 0, 0, 1, 0, 0);
				} else
					jedno--;
			}
			if (a == 1) {
				if (plansza2.get(position + 1) == 2) {
					dwu--;
					jedno++;
					zeruj(plansza2, position, 1, 0, 0, 0, 0, 0);
				} if (position > 89)
					jedno--;
			}
			if (a == 2) {
				if (plansza2.get(position + 1) == 2
						&& plansza2.get(position + 2) == 2) {
					troj--;
					zeruj(plansza2, position, 1, 1, 0, 0, 0, 0);
				} else if (plansza2.get(position + 1) == 2) {
					dwu--;
					jedno++;
					zeruj(plansza2, position, 1, 0, 0, 0, 0, 0);
				} if (position > 89)
					jedno--;
			}
			if (a == 3) {
				if (plansza2.get(position + 1) == 2
						&& plansza2.get(position + 2) == 2
						&& plansza2.get(position + 3) == 2) {
					cztero--;
					jedno++;
					zeruj(plansza2, position, 1, 1, 1, 0, 0, 0);
				} else if (plansza2.get(position + 1) == 2
						&& plansza2.get(position + 2) == 2) {
					troj--;
					jedno++;
					zeruj(plansza2, position, 1, 1, 0, 0, 0, 0);
				} else if (plansza2.get(position + 1) == 2) {
					dwu--;
					jedno++;
					zeruj(plansza2, position, 1, 0, 0, 0, 0, 0);
				} if (position > 89)
					jedno--;
			}
			if (a == 0) {
				jedno--;
			}
		}
	}

	private static void zeruj(ArrayList<Integer> plansza2, int position, int a, int b,
			int c, int d, int e, int f) {
		if (a != 0) plansza2.set(position + 1, 1);
		if (b != 0) plansza2.set(position + 2, 1);
		if (c != 0) plansza2.set(position + 3, 1);
		if (d != 0) plansza2.set(position + 10, 1);
		if (e != 0) plansza2.set(position + 20, 1);
		if (f != 0) plansza2.set(position + 30, 1);
	}

	private static void ustawPole(int position, ImageView imageView) {
		if (plansza.get(position) == 1) {
			imageView.setImageResource(R.drawable.wybrane);
			plansza.set(position, 2);
		} else if (plansza.get(position) == 2) {
			imageView.setImageResource(R.drawable.pole);
			plansza.set(position, 1);
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.getId() == R.id.right) {
			intent.setClassName(this, "com.example.statki.PlanszaPrzeciwnika");
			startActivity(intent);
			this.overridePendingTransition(R.anim.from_right, R.anim.to_left);
		} else if (v.getId() == R.id.zatwierdz) {
			if (cztero == 0 && troj == 0 && dwu == 0 && jedno == 0) {
				zatwierdzPlansze = true;
				zatwierdz.setVisibility(View.GONE);
				right.setVisibility(View.VISIBLE);
				dostepne.setVisibility(View.GONE);
				czteroTxt.setVisibility(View.GONE);
				czteroMaszt.setVisibility(View.GONE);
				trojTxt.setVisibility(View.GONE);
				trojMaszt.setVisibility(View.GONE);
				dwuTxt.setVisibility(View.GONE);
				dwuMaszt.setVisibility(View.GONE);
				jednoTxt.setVisibility(View.GONE);
				jednoMaszt.setVisibility(View.GONE);
				intent.setClassName(getApplicationContext(),
						"com.example.statki.PlanszaPrzeciwnika");
				startActivity(intent);
				overridePendingTransition(R.anim.from_right, R.anim.to_left);
			} else {
				Toast.makeText(getApplicationContext(),
						"B³êdna liczba statków.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}