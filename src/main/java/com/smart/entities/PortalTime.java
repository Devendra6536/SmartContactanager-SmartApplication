package com.smart.entities;

public class PortalTime {

	private String sno;
	private String date;
	private String time_spend;
	private String total_time;
	private String in_time;
	private String out_time;
	
	
	public PortalTime(String out_time) {
		super();
		this.out_time = out_time;
	}
	public String getOut_time() {
		return out_time;
	}
	public void setOut_time(String out_time) {
		this.out_time = out_time;
	}
	public PortalTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime_spend() {
		return time_spend;
	}
	public void setTime_spend(String time_spend) {
		this.time_spend = time_spend;
	}
	public String getTotal_time() {
		return total_time;
	}
	public void setTotal_time(String total_time) {
		this.total_time = total_time;
	}
	public PortalTime(String sno, String date, String time_spend, String total_time, String in_time, String out_time) {
		super();
		this.sno = sno;
		this.date = date;
		this.time_spend = time_spend;
		this.total_time = total_time;
		this.in_time = in_time;
		this.out_time = out_time;
	}
	@Override
	public String toString() {
		return "PortalTime [sno=" + sno + ", date=" + date + ", time_spend=" + time_spend + ", total_time=" + total_time
				+ ", in_time=" + in_time + ", out_time=" + out_time + "]";
	}
	public String getIn_time() {
		return in_time;
	}
	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}
	
}
