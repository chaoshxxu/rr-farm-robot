package rrfarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class MainAction extends ActionSupport implements ServletRequestAware{
	
	private static final long serialVersionUID = 1L;

	static public Map farmerMap;
	static {
		System.out.println("init farmerMap...");
		farmerMap = new HashMap<String, Farmer>();
	}
	
	public List list;
	public String name;
	public String email;
	public String pw;
	public String feedFriends;
	public String stealFriends;
	public HttpServletRequest request;
	public String tip;
	
	public void getUserList() {
		list = new ArrayList<String>();
		Iterator it = farmerMap.entrySet().iterator();    
		while (it.hasNext())    
		{    
			Map.Entry entry = (Map.Entry) it.next();    
			Object key = entry.getKey();
			Object value = entry.getValue();
			list.add(new Object[]{((Farmer)value).name, key, ((Farmer)value).email, ((Farmer)value).nextTime});
		}
		System.out.println(list.size());
	}
	
	public String toIndex(){
		getUserList();
		System.out.println("farmerMap.size() = " + farmerMap.size());
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
		farmer.feedFriends = feedFriends;
		farmer.stealFriends = stealFriends;
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
}
