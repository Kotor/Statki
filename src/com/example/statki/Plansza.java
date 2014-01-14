package com.example.statki;

import java.util.ArrayList;

public class Plansza {
	private static Plansza inst;	
	private ArrayList<Integer> planszaGry;
	private Plansza() {
		wyczyscPlansze();
	}
	
	public static synchronized Plansza inst() {
		if (inst == null) {
			inst = new Plansza();
		}
		return inst;
	}

	public ArrayList<Integer> getPlanszaGry() {
		return planszaGry;
	}

	public void setPlanszaGry(ArrayList<Integer> planszaGry) {
		this.planszaGry = planszaGry;
	}
	
	public void wyczyscPlansze() {
		planszaGry = new ArrayList<Integer>();
		for (int i = 0; i<100; i++) {
			planszaGry.add(1);
		}
	}
}
