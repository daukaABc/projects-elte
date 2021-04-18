package model;

public class OnClick {

	static private int CLICKS = 0;
	
	public int getClicks() {
		return CLICKS;
	}
	
	public void onPress() {
		CLICKS++;
	}
	
}
