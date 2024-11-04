package domain;

import java.util.List;

public class DailyMenu {

    private String day;
    private List<Meal> meals;

    public DailyMenu() {
    	
    }
    
    public DailyMenu(String day) {
        this.day = day;
    }

    public DailyMenu(String day, List<Meal> meals) {
        this.day = day;
        this.meals = meals;
    }

    public String getFileNameForSelection(boolean isBreakfastSelected) {
        String fileName = "";
        switch (this.day) {
            case "Lunes":
                fileName = isBreakfastSelected ? "Monday_breakfast.json" : "Monday_lunch.json";;
                break;
            case "Martes":
                fileName = isBreakfastSelected ? "Tuesday_breakfast.json" : "Tuesday_lunch.json";
                break;
            case "Miercoles":
                fileName = isBreakfastSelected ? "Wednesday_breakfast.json" : "Wednesday_lunch.json";
                break;
            case "Jueves":
                fileName = isBreakfastSelected ? "Thursday_breakfast.json" : "Thursday_lunch.json";
                break;
            case "Viernes":
                fileName = isBreakfastSelected ? "Friday_breakfast.json" : "Friday_lunch.json";
                break;
            default:
                fileName = "";
                break;
        }
        return fileName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}