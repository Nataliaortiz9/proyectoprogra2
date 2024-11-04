package domain;

import java.time.LocalDate;
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
	private String carnet;
	private String name;
	private String email;
	private int phone;
	private boolean isActive;
	private LocalDate date;
	private char gender;
	private double moneyAvailable;
	
	public Student() {
		
	}

	public Student(String carnet, String name, String email, int phone, boolean isActive, LocalDate date, char gender,
			double moneyAvailable) {
		this.carnet = carnet;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.isActive = isActive;
		this.date = date;
		this.gender = gender;
		this.moneyAvailable = moneyAvailable;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public double getMoneyAvailable() {
		return moneyAvailable;
	}

	public void setMoneyAvailable(double moneyAvailable) {
		this.moneyAvailable = moneyAvailable;
	}

	@Override
	public String toString() {
		return "Student [carnet=" + carnet + ", name=" + name + ", email=" + email + ", phone=" + phone + ", isActive="
				+isActive + ", date=" + date + ", gender=" + gender + ", moneyAvailable=" + moneyAvailable + "]";
	}

}
