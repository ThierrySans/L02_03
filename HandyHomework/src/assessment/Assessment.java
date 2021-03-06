package assessment;

import java.util.ArrayList;
import java.util.Calendar;

import question.TextQuestion;


public class Assessment {
	private int aid;
	private String title;
	private String name;
	private boolean isMult;
	private boolean isOpt;
	private Calendar due;
	private float weight;
	private ArrayList<TextQuestion> questions;
	
	/**
	 * 
	 * Use this if isOpt is false
	 */

	public Assessment(int aid, String title, String name, boolean isOpt, Calendar due, float weight) {
		this.aid = aid;
		this.title = title;
		this.name = name;
		this.isOpt = isOpt;
		this.due = due;
		this.weight = weight;
		this.questions = new ArrayList<TextQuestion>();
	}
	
	public int getAid() {
		return aid;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getISMult() {
		return this.isMult;
	}
	
	public boolean getIsOpt() {
		return this.isOpt;
	}
	
	public Calendar getDue() {
		return this.due;
	}
	
	public float getWeight() {
		return this.weight;
	}
	
	public void addQuestions(TextQuestion ts) {
		questions.add(ts);
	}
	
	public ArrayList<TextQuestion> getQuestions() {
		return this.questions;
	}

}
