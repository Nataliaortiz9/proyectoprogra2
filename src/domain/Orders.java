package domain;

public class Orders {
	private int idOrder;
	private String nameProduct;
	private int quantity;
	private int total;
	private String state;
	private String IDStudent;
	public Orders() {
		super();
	}
	public Orders(int idOrder, String nameProduct, int quantity, int total, String state, String iDStudent) {
		super();
		this.idOrder = idOrder;
		this.nameProduct = nameProduct;
		this.quantity = quantity;
		this.total = total;
		this.state = state;
		IDStudent = iDStudent;
	}
	public Orders(String nameProduct, int quantity, int total, String state, String iDStudent) {
		super();
		this.nameProduct = nameProduct;
		this.quantity = quantity;
		this.total = total;
		this.state = state;
		IDStudent = iDStudent;
	}
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIDStudent() {
		return IDStudent;
	}
	public void setIDStudent(String iDStudent) {
		IDStudent = iDStudent;
	}
	
	

}
