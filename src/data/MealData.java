package data;

public class MealData {
	private String name;
	private double price;
	
	public void MealData() {}
	
	public MealData() {
		super();
		this.name = name;
		this.price = price;
	}

	public String getFileNameForSelection(String selectedDay, boolean isBreakfastSelected) {
        if ("Lunes".equals(selectedDay)) {
            return isBreakfastSelected ? "Monday_breakfast.json" : "Monday_lunch.json";
        } else if ("Martes".equals(selectedDay)) {
            return isBreakfastSelected ? "Tuesday_breakfast.json" : "Tuesday_lunch.json";
        } else if ("Miercoles".equals(selectedDay)) {
            return isBreakfastSelected ? "Wednesday_breakfast.json" : "Wednesday_lunch.json";
        } else if ("Jueves".equals(selectedDay)) {
            return isBreakfastSelected ? "Thursday_breakfast.json" : "Thursday_lunch.json";
        } else if ("Viernes".equals(selectedDay)) {
            return isBreakfastSelected ? "Friday_breakfast.json" : "Friday_lunch.json";
        }
        return "";
    }

}
