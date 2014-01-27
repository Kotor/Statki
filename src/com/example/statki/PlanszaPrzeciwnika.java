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
import android.widget.Toast;

public class PlanszaPrzeciwnika extends Activity implements OnClickListener {
	private ArrayList<Integer> planszaPrzeciwnika;
	int cztero = 1, troj = 2, dwu = 3, jedno = 4, wszystkie = 20;
	private Button left;
	GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.przeciwnik);
		Plansza.inst().wyczyscPlansze();
		planszaPrzeciwnika = Plansza.inst().getPlanszaGry();
				    	
    	Display display = getWindowManager().getDefaultDisplay();
		Rect rect = new Rect();
    	display.getRectSize(rect);
    	gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, rect.width()));		
		left = (Button) this.findViewById(R.id.left);
		left.setOnClickListener(this);
		
		losujPlansze(planszaPrzeciwnika);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View imgView,
					int position, long id) {
				ImageView imageView = (ImageView) imgView;
				
				int a = 0;
				do {
					a = strzal(position, imageView);
				} while (a == 0);
			}		
		});
	}
	
	private int strzal(int position, ImageView imageView) {
		if (planszaPrzeciwnika.get(position) == 2) {
			imageView.setImageResource(R.drawable.trafiony);
			wszystkie--;
			planszaPrzeciwnika.set(position, 4);
			niedostepnePola(position);
			if (wszystkie == 0) {
				Toast.makeText(getApplicationContext(),
						"Wygra³eœ!", Toast.LENGTH_SHORT).show();
			}
			return 1;
		} else if (planszaPrzeciwnika.get(position) == 1) {
			imageView.setImageResource(R.drawable.pudlo);
			planszaPrzeciwnika.set(position, 3);
			Intent intent = new Intent();
			Gra.strzal();
			intent.setClassName(getApplicationContext(),"com.example.statki.Gra");
			startActivity(intent);
			overridePendingTransition(R.anim.from_left, R.anim.to_right);			
		} else if (planszaPrzeciwnika.get(position) == 3) {
			return 1;
		} else if (planszaPrzeciwnika.get(position) == 4) {
			return 1;
		}
		return 0;
	}
	
	private void losujPlansze(ArrayList<Integer> plansza) {
		Random losuj = new Random();
		int liczba = losuj.nextInt(5);
		Log.i("liczba", Integer.toString(liczba));
		if (liczba == 0)ustawPlansze(plansza, 3, 18, 22, 23, 24, 25, 28, 41, 44, 45, 49,
					51, 61, 66, 67, 68, 83, 86, 88, 93);
		if (liczba == 1)ustawPlansze(plansza, 11, 13, 14, 21, 29, 31, 36, 39, 41, 53, 56,
					61, 63, 69, 73, 79, 93, 96, 97, 98);
		if (liczba == 2)ustawPlansze(plansza, 2, 6, 7, 8, 14, 21, 24, 28, 31, 34, 50, 53,
					54, 55, 56, 71, 78, 83, 84, 88);
		if (liczba == 3)ustawPlansze(plansza, 8, 11, 12, 34, 35, 36, 37, 51, 52, 54, 57,
					59, 64, 69, 71, 77, 79, 91, 92, 93);
		if (liczba == 4)ustawPlansze(plansza, 3, 4, 5, 6, 21, 22, 37, 38, 39, 41, 44, 
					54, 61, 64, 67, 68, 71, 86, 89, 90);
	}

	private void ustawPlansze(ArrayList<Integer> plansza, int a, int b, int c, int d, int e, int f, 
			int g, int h, int i, int j, int k, int l, int m, int n, int o, int p, int q, int r, int s, int t) {
		plansza.set(a, 2);
		plansza.set(b, 2);
		plansza.set(c, 2);
		plansza.set(d, 2);
		plansza.set(e, 2);
		plansza.set(f, 2);
		plansza.set(g, 2);
		plansza.set(h, 2);
		plansza.set(i, 2);
		plansza.set(j, 2);
		plansza.set(k, 2);
		plansza.set(l, 2);
		plansza.set(m, 2);
		plansza.set(n, 2);
		plansza.set(o, 2);
		plansza.set(p, 2);
		plansza.set(q, 2);
		plansza.set(r, 2);
		plansza.set(s, 2);
		plansza.set(t, 2);
	}
	
	private void niedostepnePola(int position) {
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
		ustawNiedostepne(wartosci, position);
	}
	
	private void ustawNiedostepne(ArrayList<Integer> wartosci, int position) {
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
			planszaPrzeciwnika.set(position + b, 3);
			View myTopView = gridView.getChildAt(position + b);
			ArrayList<View> allViewsWithinMyTopView = getAllChildren(myTopView);
			for (View child : allViewsWithinMyTopView) {
				if (child instanceof ImageView) {
					ImageView imV = (ImageView) child;
					if (planszaPrzeciwnika.get(position+b) == 3) {
						imV.setImageResource(R.drawable.pudlo);
					}
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
