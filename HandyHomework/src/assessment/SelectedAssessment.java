package assessment;

public class SelectedAssessment {
	 public static Assessment selectedAs = null;
	 
	 public static void setAssess(Assessment selected) {
		 selectedAs = selected;	 
	 }
	 
	 public static boolean isSelected() {
		 return selectedAs != null;
	 }
	 
	 public static Assessment getAssess() {
		return selectedAs;
	 }
}
