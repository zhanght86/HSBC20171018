package log4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.sinosoft.utility.SysConst;

public class Log4JInit extends HttpServlet {
	private Log log = LogFactory.getLog(this.getClass().getName());

	public void init() throws ServletException {

		// 支持properties文件和xml文件配置的初始化
		System.out.println("begin init log4j");
		String tLog4jType = getInitParameter("log4jtype");
		System.out.println("log4jtype:" + tLog4jType);
		String filePath = "";
		String prefix = "";
		String file = "";
		if (tLog4jType != null && tLog4jType.toLowerCase().equals("properties")) {
			prefix = getServletContext().getRealPath("/");
			file = getInitParameter("log4j");
			if (file != null) {
				PropertyConfigurator.configure(prefix + file);
				System.out.println(prefix + file);
			}
		} else {

			prefix = this.getServletConfig().getServletContext().getRealPath("/");// 读取项目的路径
			file = this.getServletConfig().getInitParameter("log4j");
			if(!prefix.endsWith("/"))prefix+="/";
			// 读取log4j相对路径
			filePath = prefix + file;
			DOMConfigurator.configure(filePath);// 加载.xml文件
		}
		log.debug("-----------------------------------init log4j----------------------------------- ");
		log.debug("log4j filePath:" + filePath);
		log.debug("-----------------------------------end init log4j-----------------------------------");
		System.out.println("end init log4j");
		
		log.debug("-----------------------------------init set Javascript DBType----------------------------------- ");
		setJSDBType();
		log.debug("-----------------------------------end init Javascript DBType-----------------------------------");
	}

	public static String setJSDBType() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("Engine.xml");
		if (url == null) {
			System.out.println(Thread.currentThread().getContextClassLoader());
			throw new RuntimeException("未找到Engine.xml");
		}
		try {
			String path = URLDecoder.decode(url.getPath(), "GBK");
			if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
				if (path.startsWith("/")) {
					path = path.substring(1);
				} else if (path.startsWith("file:/")) {
					path = path.substring(6);
				}
			} else {
				if (path.startsWith("file:/")) {
					path = path.substring(5);
				}
			}
			path = path.substring(0, path.lastIndexOf("/") + 1);
			
			
			String jsPath = path.replaceAll("WEB-INF/classes/", "common/javascript/dbtype.js");
			File jsFile = new File(jsPath);
			if(jsFile.isFile() && jsFile.exists()){
				jsFile.delete();
			}
			try {
				if(!jsFile.exists() && jsFile.createNewFile()){
					FileWriter fileWritter = new FileWriter(jsFile,true);
				    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				    bufferWritter.write("var _DBT = \""+SysConst.DBTYPE+"\";");
				    bufferWritter.close();
				    fileWritter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}

}
