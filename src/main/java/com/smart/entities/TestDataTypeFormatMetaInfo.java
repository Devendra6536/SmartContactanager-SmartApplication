package com.smart.entities;

public class TestDataTypeFormatMetaInfo {
	
	private String num_of_rows;
	private String format;
	private String fileName;
	private String datafileDirectory;
	public String getNum_of_rows() {
		return num_of_rows;
	}
	public void setNum_of_rows(String num_of_rows) {
		this.num_of_rows = num_of_rows;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDatafileDirectory() {
		return datafileDirectory;
	}
	public void setDatafileDirectory(String datafileDirectory) {
		this.datafileDirectory = datafileDirectory;
	}
	@Override
	public String toString() {
		return "TestDataTypeFormatMetaInfo [num_of_rows=" + num_of_rows + ", format=" + format + ", fileName="
				+ fileName + ", datafileDirectory=" + datafileDirectory + "]";
	}
	public TestDataTypeFormatMetaInfo(String num_of_rows, String format, String fileName, String datafileDirectory) {
		super();
		this.num_of_rows = num_of_rows;
		this.format = format;
		this.fileName = fileName;
		this.datafileDirectory = datafileDirectory;
	}
	public TestDataTypeFormatMetaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
