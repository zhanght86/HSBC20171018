package filters;
import org.apache.log4j.Logger;

/*
 * <p>ClassName: GlobalPools </p>
 * <p>Description: 多连接池Filter类 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @author zhangzm
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sinosoft.utility.GlobalPools;
import com.sinosoft.utility.JdbcUrl;

public class MultiDBFilter implements Filter {
private static Logger logger = Logger.getLogger(MultiDBFilter.class);

	private static GlobalPools mGlobalPools = GlobalPools.getInstance();

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		JdbcUrl url = new JdbcUrl();
		boolean deal = false;
		Date start = new Date();
		try {
			
			
			// Thread.sleep(2000);

			// 预加载配置文件
			// 为防止加载最初读文件并发,此处加载会迁移到自动运行程序随weblogic启动时加载
			//tongmeng 2009-03-31 modify 自动运行会加载.
			//MultiDBFilter.getRuleProperties();
			// 如果配置文件成功加载,并且又是使用weblogic连接池连接.
			//if(!mGlobalPools.mLoadSuccFlag&& url.getDBType().equals("WEBLOGICPOOL"))
			{
				MultiDBFilter.getRuleProperties();
			}
			
			if (mGlobalPools.mLoadSuccFlag
					&& url.getDBType().equals("WEBLOGICPOOL")) {
				if (request instanceof javax.servlet.http.HttpServletRequest) {
					HttpServletRequest request1 = (HttpServletRequest) request;
					String path = request1.getServletPath();

					Properties prop = MultiDBFilter.getRuleProperties();
					logger.debug("curr path:" + path + ":"
							+ this.ruleproperties.getProperty(path));
					if (prop.containsKey(path)) {
						String target = (String) prop.get(path);
						mGlobalPools.setPoolName(target);
						deal = true;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 如果处理失败,使用默认的连接池.
		//tongmeng 2009-03-31 modify
		//只对weblogic才做连接池的转发.
		
		if (!deal&&url.getDBType().equals("WEBLOGICPOOL"))
			mGlobalPools.setPoolName(url.getDBName());
			
		chain.doFilter(request, response);
		Date end = new Date();
		/*logger.debug("MultiDBFilter transfer db connection pools:\t"
				+ (end.getTime() - start.getTime()) + "\t:" + " used ");*/
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private static boolean hasinited = false;
	private static boolean loadfailed = false;
	private static Properties ruleproperties = mGlobalPools.getRuleProperties();

	private static Properties getRuleProperties() {

		// 默认将地址指向GlobalPools中的ruleproperties
		ruleproperties = mGlobalPools.getRuleProperties();
		// 如果没有预加载成功,重新加载
		if (ruleproperties == null && !loadfailed) {
			logger.debug("ruleproperties is null ");
			Date start = new Date();
			Properties prop = new Properties();
			// 以下两个方法都可以找到dbrule.properties,但是ClassLoader需要配置classpath,否则有可能加载不到.
			// InputStream is = ClassLoader
			// .getSystemResourceAsStream("dbrule.properties");
			InputStream is = MultiDBFilter.class
					.getResourceAsStream("/dbrule.properties");

			hasinited = true;
			ruleproperties = prop;
			try {
				if (is == null) {
					logger.debug("读取配置文件失败");
				}
				if (is != null) {
					logger.debug("begin load dbrule.properties..");
					prop.load(is);
					loadfailed = true;
					logger.debug("end load dbrule.properties..");
				}
				Date end = new Date();
				logger.debug("load properties:\t"
						+ (end.getTime() - start.getTime()) + "\t:" + " used ");
				// loadfailed = true;
			} catch (Exception e) {
				e.printStackTrace();
				loadfailed = false;
				ruleproperties = null;
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			hasinited = true;
			loadfailed = true;
		}
		return ruleproperties;
	}
}
