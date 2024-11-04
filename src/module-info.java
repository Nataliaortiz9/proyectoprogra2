module ProyectoProgramacion3 {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;
	requires javafx.base;
	requires com.fasterxml.jackson.core;
	requires javafx.graphics;
	requires java.sql;
	exports domain;
	
	opens business to javafx.graphics, javafx.fxml;
}
