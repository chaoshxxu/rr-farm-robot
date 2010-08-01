package rrfarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.protocol.HTTP;


/**
 * 农民类，也就是自动工作的线程，一个账号一个
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class Farmer extends Thread{

	public String email;
	public String pw;
	public String name;
	public String feedFriends;
	public String stealFriends;
	public String index;	//农场首页
	public int endFlag;
	private String pageContent;
	private HttpClient hc;
	
	public Farmer() {
		System.out.println("Here !!!!!!!");
		this.endFlag = 0;
		this.hc = new HttpClient();
		hc.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");//设置信息 
		hc.getParams().setContentCharset(HTTP.UTF_8);
	}
	
	/**
	 * 将"X天X小时X分"转换为毫秒
	 * @param time
	 * @return 毫秒数
	 */
	private long trans(String time) {
		String tmp[] = time.split("\\D+");
		long unit[] = {60000L, 3600000L, 86400000L};
		long ret = 0;
		for (int i = tmp.length - 1; i >= 0; --i){
			ret += unit[tmp.length - i - 1] * Long.parseLong(tmp[i]); 
		}
		return ret;
	}
	
	/**
	 * 获取文字上的链接
	 * @param 文字
	 * @return
	 */
	private String getHref(String name) {
		Matcher m = Pattern.compile("href=\"([^\"]*)\"[^>]*?>" + name + "\\s*<").matcher(pageContent);
		String href = m.find() ? m.group(1) : null;
		System.out.println(name + " - " + href);
		return href;
	}
	
	/**
	 * 点击链接
	 * @param href
	 * @throws HttpException
	 * @throws IOException
	 */
	private void clickHref(String href) throws HttpException, IOException {
		if (!href.startsWith("http")){
			href = "http://mapps.renren.com" + href;
		}
		GetMethod getMethod = new GetMethod(href);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        this.hc.executeMethod(getMethod);
		byte[] responseBody = getMethod.getResponseBody();
        pageContent = new String(responseBody, "UTF-8");
	}
	
	/**
	 * 登陆
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private boolean login() throws HttpException, IOException {
		clickHref("http://3g.renren.com/home.do");
		
		PostMethod postMethod = new PostMethod("http://3g.renren.com/login.do");
		
		System.out.println("email = " + email);
//		System.out.println("password = " + pw);
		postMethod.addParameter("email", email);
		postMethod.addParameter("login", "登录");
		postMethod.addParameter("origURL", "/home.do");
		postMethod.addParameter("password", pw);
		
		
		Object tmp[] = postMethod.getParameters();
		System.out.println("----------para--------------");
		for (Object object : tmp) {
			System.out.println(object);
		}
		tmp = hc.getState().getCookies();
		System.out.println("----------cookie--------------");
		for (Object object : tmp) {
			System.out.println(object);
		}
		
		this.hc.getParams().setContentCharset(HTTP.UTF_8);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			System.out.println("login...");
			int statusCode = this.hc.executeMethod(postMethod);
			System.out.println("statusCode = " + statusCode);
			
            byte[] responseBody = postMethod.getResponseBody();
            String tLine = new String(responseBody, "UTF-8");
            System.out.println("tLine = " + tLine);

            if (statusCode != HttpStatus.SC_MOVED_TEMPORARILY){
				return false;
			}

			clickHref(postMethod.getResponseHeader("Location").getValue());

    		Matcher m = Pattern.compile("<div class=\"sec stat\"><b>([\\s\\S]+?)</b>").matcher(pageContent);
    		if (m.find()){
    			name = m.group(1).replaceAll(":", "");
    			return true;
    		}
        } catch(Exception e) {
        	e.printStackTrace();
            postMethod.releaseConnection();
        }
       	return false;
	}
	
	/**
	 * 进入农场
	 * @throws HttpException
	 * @throws IOException
	 */
	private void enterRRFarm() throws HttpException, IOException {
		String href = getHref("应用");
		clickHref(href);
		href = getHref("人人农场");
		index = href;
		clickHref(href);
	}
	
	/**
	 * 收菜
	 * @throws HttpException
	 * @throws IOException
	 */
	private void fetch() throws HttpException, IOException {
		clickHref(index);
		if (pageContent.contains("【农田】★")){
			clickHref(getHref( "【农田】★"));
			if (pageContent.contains("收获全部农产品")){
				clickHref(getHref( "收获全部农产品"));
				clickHref(getHref( "人人农场首页"));
			} else {
				clickHref(getHref( "返回人人农场首页"));
			}
		}
		
		if (pageContent.contains("【果树】★")){
			clickHref(getHref("【果树】★"));
			if (pageContent.contains("收获全部果树产品")){
				clickHref(getHref( "收获全部果树产品"));
				clickHref(getHref( "人人农场首页"));
			} else {
				clickHref(getHref( "返回人人农场首页"));
			}
		}

		if (pageContent.contains("【畜牧】★")){
			clickHref(getHref("【畜牧】★"));
			if (pageContent.contains("收获全部畜牧产品")){
				clickHref(getHref( "收获全部畜牧产品"));
				clickHref(getHref( "人人农场首页"));
			} else {
				clickHref(getHref( "返回人人农场首页"));
			}
		}

		if (pageContent.contains("【机械】★")){
			clickHref(getHref("【机械】★"));
			if (pageContent.contains("收获全部加工产品")){
				clickHref(getHref( "收获全部加工产品"));
				clickHref(getHref( "人人农场首页"));
			} else {
				clickHref(getHref( "返回人人农场首页"));
			}
		}
		
	}
	
	
	/**
	 * 喂畜生、喂机器
	 * @throws HttpException
	 * @throws IOException
	 */
	private void feed() throws HttpException, IOException {
		List<String> hrefList = new ArrayList();
		
		if (pageContent.contains("【畜牧】★")){
			clickHref(getHref("【畜牧】★"));
			while (true){
				Matcher m = Pattern.compile("href=\"([^\"]*)\"[^>]*?>喂食<").matcher(pageContent);
				while (m.find()){
					hrefList.add(m.group(1));
				}
				if (pageContent.contains("下一页")){
					clickHref(getHref("下一页"));
				} else {
					break;
				}
			}
			clickHref(getHref("返回人人农场首页"));
		}
		if (pageContent.contains("【机械】★")){
			clickHref(getHref("【机械】★"));
			while (true){
				Matcher m = Pattern.compile("href=\"([^\"]*)\"[^>]*?>添加<").matcher(pageContent);
				while (m.find()){
					hrefList.add(m.group(1));
				}
				if (pageContent.contains("下一页")){
					clickHref(getHref("下一页"));
				} else {
					break;
				}
			}
			clickHref(getHref("返回人人农场首页"));
		}
		
		for (String href : hrefList) {
			clickHref(href);
			Map<String, String> map = new HashMap<String, String>();
			Matcher m = Pattern.compile("name=\"([\\s\\S]+?)\" value=\"([\\s\\S]+?)\"").matcher(pageContent);
			PostMethod postMethod = new PostMethod("http://mapps.renren.com/rr_farm/farm/action/wap,feedAction.php?sid=" + map.get("sid"));
			while (m.find()){
				String key = m.group(1);
				String value = m.group(2);
				if (!map.containsKey(key)){
					map.put(key, value);
					postMethod.addParameter(key, value);
				}
			}
			hc.getParams().setContentCharset(HTTP.UTF_8); 
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			hc.executeMethod(postMethod);
		}
	}
	
	/**
	 * 获取下次开始工作的时间
	 * @return 下次工作还要等多少毫秒
	 * @throws HttpException
	 * @throws IOException
	 */
	private long getNextTime() throws HttpException, IOException {
		long rem = 0x7ffffffffffffL;
		clickHref(index);
		String list[] = {
				"【农田】",
				"【农田】★",
				"【果树】",
				"【果树】★",
				"【畜牧】",
				"【畜牧】★",
				"【机械】",
				"【机械】★"
		};
		for (String area : list) {
			String href = getHref(area);
			if (href == null)
				continue;
			clickHref(href);
			while (true){
				Matcher m = Pattern.compile("还有([\\s\\S]*?分)").matcher(pageContent);
				while (m.find()){
					rem = Math.min(rem, trans(m.group(1)));
				}
				if (pageContent.contains("下一页")){
					clickHref(getHref("下一页"));
				} else {
					break;
				}
			}
			clickHref(getHref("返回人人农场首页"));
		}
		return rem;
		
	}

	
	/**
	 * 主工作方法
	 */
	public void run(){
			
		try {

			while (endFlag == 0){
				if (!login()){
					MainAction.farmerMap.remove(email);
					return;
				}
				enterRRFarm();
				
				fetch();
				feed();
				//steal();

				long remain = getNextTime() + 60000L;
				System.out.println(name + ": 距下次工作还剩: " + (remain/3600000L) + "小时" + (remain%3600000L/60000L) + "分");
				synchronized (this) {
					wait(remain);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
