package functional.util;

import org.sikuli.script.*;

public class SikuliExecuter {

	private Screen screen;
	
	public SikuliExecuter(){
		screen = new Screen();
		screen.setAutoWaitTimeout(5);

	}
	
	public boolean clickImage(String imgFile){
		
		Pattern imgToClick = new Pattern(imgFile);
		
		try {
			screen.click(imgToClick.similar((float)0.70));
			System.out.println("Clicked "+imgFile+" on screen");
			return true;
		} catch (FindFailed e) {
			System.out.println("Couldn't find "+imgFile+" on screen");
			return false;
		}
		
	}
	
	public boolean doubleClickImage(String imgFile){
		
		Pattern imgToClick = new Pattern(imgFile);
		
		try {
			screen.doubleClick(imgToClick.similar((float)0.70));
			System.out.println("Double clicked "+imgFile+" on screen");
			return true;
		} catch (FindFailed e) {
			System.out.println("Couldn't find "+imgFile+" on screen");
			return false;
		}
		
	}
	
	public boolean typeIntoTextField(String imgFile, String textToType, int unitsToDelete){
		
		if(this.clickImage(imgFile)){			
			for(int i=0;i<unitsToDelete;i++){
				screen.type(Key.BACKSPACE);
			}			
			screen.type(textToType);
			screen.type(Key.ESC);
			System.out.println("Typed "+textToType+" to "+imgFile);
			return true;
		}	
		System.out.println("Couldn't type "+textToType+" to "+imgFile);
		return false;
	}
	
	public boolean ensureExists(String imgFile){
		
		 Match m = screen.exists(imgFile);

		 if (m != null) {
		 System.out.println("Image "+imgFile+" found");
		 return true;
		 }
		 System.out.println("Image "+imgFile+" not found");
		 return false;
		}

	
}

   