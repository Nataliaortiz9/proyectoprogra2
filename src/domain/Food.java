package domain;

public class Food {
    private String schedule;
    private String dayService;
    private String nameFood;
    private double price;
	public Food(String schedule, String dayService, String nameFood, Double double1) {
		super();
		this.schedule = schedule;
		this.dayService = dayService;
		this.nameFood = nameFood;
		this.price = double1;
	}
	public Food() {
		super();
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getDayService() {
		return dayService;
	}
	public void setDayService(String dayService) {
		this.dayService = dayService;
	}
	public String getNameFood() {
		return nameFood;
	}
	public void setNameFood(String nameFood) {
		this.nameFood = nameFood;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    public String getFileNameForSelection() {
        return "menu_" + dayService + "_" + schedule + ".json";
    }

}
