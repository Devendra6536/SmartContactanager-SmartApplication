package com.smart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TestData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String datatype;
	private String ColumnName;
	private String colNo;
	
	public TestData(int id, String datatype, String columnName, String colNo) {
		super();
		Id = id;
		this.datatype = datatype;
		ColumnName = columnName;
		this.colNo = colNo;
	}
	public TestData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "TestData [Id=" + Id + ", datatype=" + datatype + ", ColumnName=" + ColumnName + ", colNo=" + colNo
				+ "]";
	}
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}
	public String getColNo() {
		return colNo;
	}
	public void setColNo(String colNo) {
		this.colNo = colNo;
	}
	
	
}
