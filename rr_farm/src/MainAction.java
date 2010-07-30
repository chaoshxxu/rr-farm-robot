import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class MainAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	static public Map farmerMap = new HashMap<String, Farmer>();
	
	public List list;
	public String name;
	public String email;
	public String pw;
	public String friends;
	
	
	
	
	
	public String toIndex(){
		list = new ArrayList<String>();

		Iterator it = farmerMap.entrySet().iterator()   ;    
		while (it.hasNext())    
		{    
			Map.Entry entry = (Map.Entry) it.next()   ;    
			Object key = entry.getKey();    
			Object value = entry.getValue();
			list.add(new Object[]{((Farmer)value).name, key, ((Farmer)value).email});
		}
		System.out.println(list.size());
		return SUCCESS;
	}
	
	public String add(){
		Farmer farmer = new Farmer();
		farmer.email = email;
		farmer.pw = pw;
		farmer.friends = friends;
		farmerMap.put(email, farmer);
		farmer.start();
		return SUCCESS;
	}
	
	public String remove(){
		Farmer farmer = (Farmer) farmerMap.get(email);
		if (!farmer.pw.equals(pw)){
			this.addActionError("密码错误！");
			return INPUT;
		}
		farmer.endFlag = 1;
		farmer.notify();
		farmerMap.remove(email);
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

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	
}
