package filters;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class TimeFilter implements Filter {
private static Logger logger = Logger.getLogger(TimeFilter.class);
	private static long LASTTIME = 1000 * 60 * 10; // 10m

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		Date start = new Date();
		chain.doFilter(arg0, arg1);
		Date end = new Date();
		if (end.getTime() - start.getTime() > LASTTIME) {
			if (arg0 instanceof javax.servlet.http.HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) arg0;
				logger.debug("TimeFilter Large Request:\t"
						+ (end.getTime() - start.getTime()) + "\t:"
						+ request.getServletPath() + " used"
						+ getUsefulInfo(request));
			}
		}

	}

	private String getUsefulInfo(HttpServletRequest request) {
		String s = (String) request.getAttribute("USEFULINFO");
		if (s != null && !s.equals("") && !s.equals("null"))
			return ", info:" + s;
		return "";
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String time = filterConfig.getInitParameter("lasttime");
		if (time != null && !time.equals("")) {
			try {
				LASTTIME = Long.parseLong(time) * 1000;
			} catch (NumberFormatException e) {
				System.err
						.println("TimeFilter init param 'lasttime' is not a long type value");
			}
		}

	}

}
