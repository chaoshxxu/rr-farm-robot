package rrfarm;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;


import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class MainAction extends ActionSupport implements ServletRequestAware{
	
	private static final long serialVersionUID = 1L;

	static public Map farmerMap;
	
	public List list;
	public String name;
	public String email;
	public String pw;
	public String feedFriends;
	public String stealFriends;
	public String reservedFood;
	public HttpServletRequest request;
	public String tip;
	private String info;
	static public ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	static {
		System.setOut(new PrintStream(out)); // 重定向sysout  
		System.setErr(new PrintStream(out)); // 重定向syserr
		System.out.println("init farmerMap...");
		farmerMap = new HashMap<String, Farmer>();
	}
	
	public void getUserList() {
		list = new ArrayList<String>();
		Iterator it = farmerMap.entrySet().iterator();    
		while (it.hasNext())    
		{    
			Map.Entry entry = (Map.Entry) it.next();    
			Object key = entry.getKey();
			Object value = entry.getValue();
			list.add(new Object[]{((Farmer)value).name, key, ((Farmer)value).email, ((Farmer)value).nextTime, ((Farmer)value).feedFriends, ((Farmer)value).stealFriends, ((Farmer)value).reservedFood});
		}
//		System.out.println(list.size());
	}
	
	public String toIndex(){
		getUserList();
//		System.out.println("farmerMap.size() = " + farmerMap.size());
		return SUCCESS;
	}

	public String logs() throws UnsupportedEncodingException{
		info = new String(out.toByteArray(), "GB2312");
		return SUCCESS;
	}
	
	public String add(){
		
		Farmer farmer;
		if (farmerMap.containsKey(email)){
			farmer = (Farmer) farmerMap.get(email);
			if (farmer.pw.equals(pw)){
				synchronized (farmer) {
					farmer.endFlag = 1;
					farmer.notify();
				}
			} else {
				return SUCCESS;
			}
		}
		farmer = new Farmer();
		farmer.email = email;
		farmer.pw = pw;

		for (String st : feedFriends.split("[,，;；、\\.。\\s]")) {
			if (!st.isEmpty()){
				farmer.feedFriends.add(st);
			}
		}

		for (String st : stealFriends.split("[,，;；、\\.。\\s]")) {
			if (!st.isEmpty()){
				farmer.stealFriends.add(st);
			}
		}
		
		for (String st : reservedFood.split("[^\\u4e00-\\u9fa5]")) {
			if (!st.isEmpty()){
				farmer.reservedFood.add(st);
			}
		}

		synchronized (farmerMap) {
			farmerMap.put(email, farmer);
		}
		farmer.start();

		

		return SUCCESS;
	}
	
	public String remove(){
		Farmer farmer = (Farmer) farmerMap.get(email);
		if (farmer != null && farmer.pw.equals(pw)){
			synchronized (farmer) {
				farmer.endFlag = 1;
				farmer.notify();
			}
			synchronized (farmerMap) {
				farmerMap.remove(email);
			}
		}
		return SUCCESS;
	}
	
	public String work(){
		Farmer farmer = (Farmer) farmerMap.get(email);
		synchronized (farmer) {
			farmer.notify();
		}
		return SUCCESS;
	}
	
	

	public List getList() {
		return list;
	}


	public void setList(List list) {
		this.list = list;
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

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getFeedFriends() {
		return feedFriends;
	}

	public void setFeedFriends(String feedFriends) {
		this.feedFriends = feedFriends;
	}

	public String getStealFriends() {
		return stealFriends;
	}

	public void setStealFriends(String stealFriends) {
		this.stealFriends = stealFriends;
	}

	public String getReservedFood() {
		return reservedFood;
	}

	public void setReservedFood(String reservedFood) {
		this.reservedFood = reservedFood;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
	

}
