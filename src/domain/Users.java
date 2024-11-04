package domain;


public class Users {
	private int id;
	private String identificationUser;
	private String password;
	private int type;
	private String photoRoute;
	private String nameUser;
	
	
	public Users(int id, String identificationUser, String password, int type, String photoRoute, String nameUser) {
		super();
		this.id = id;
		this.identificationUser = identificationUser;
		this.password = password;
		this.type = type;
		this.photoRoute = photoRoute;
		this.nameUser = nameUser;
	}

	public Users(String identificationUser, String password, int type, String photoRoute, String nameUser) {
		super();
		this.identificationUser = identificationUser;
		this.password = password;
		this.type = type;
		this.photoRoute = photoRoute;
		this.nameUser = nameUser;
	}


	public Users() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdentificationUser() {
		return identificationUser;
	}
	public void setIdentificationUser(String identificationUser) {
		this.identificationUser = identificationUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPhotoRoute() {
		return photoRoute;
	}
	public void setPhotoRoute(String photoRoute) {
		this.photoRoute = photoRoute;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	
	

}
