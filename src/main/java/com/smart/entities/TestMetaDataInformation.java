package com.smart.entities;

import java.util.List;

public class TestMetaDataInformation {
	
	private List<TestData> testData;
	private TestDataTypeFormatMetaInfo dataTypeFormatMetaInfo;
	
	public List<TestData> getTestData() {
		return testData;
	}
	public void setTestData(List<TestData> testData) {
		this.testData = testData;
	}
	public TestDataTypeFormatMetaInfo getDataTypeFormatMetaInfo() {
		return dataTypeFormatMetaInfo;
	}
	public void setDataTypeFormatMetaInfo(TestDataTypeFormatMetaInfo dataTypeFormatMetaInfo) {
		this.dataTypeFormatMetaInfo = dataTypeFormatMetaInfo;
	}
	public TestMetaDataInformation(List<TestData> testData, TestDataTypeFormatMetaInfo dataTypeFormatMetaInfo) {
		super();
		this.testData = testData;
		this.dataTypeFormatMetaInfo = dataTypeFormatMetaInfo;
	}
	public TestMetaDataInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TestMetaDataInformation [testData=" + testData + ", dataTypeFormatMetaInfo=" + dataTypeFormatMetaInfo
				+ "]";
	}
	
	
	
	
	
	
	
	
}
