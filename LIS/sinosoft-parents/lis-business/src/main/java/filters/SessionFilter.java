package filters;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.SQLIDefense;
import com.sinosoft.service.BusinessDelegate;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class SessionFilter implements Filter

{
private static Logger logger = Logger.getLogger(SessionFilter.class);



    public SessionFilter()

    {}

    public String indexpagename = "/indexlis.jsp";


    private String[] excludePages = null;
    
    private static final String SQLI_JSP = "CExec.jsp";
    private static final String LOGON_JSP = "/LogonSubmit.jsp";
    private static final String MENU2_JSP = "/menu2.jsp";
    private static final String MENU2_JSP1 = "/menu2New.jsp";
    private static final String HEAD_JSP = "/head.jsp";
    private static final String TITLE_JSP = "/Title.jsp";
    private static final String LOGOUT_JSP = "/logout.jsp";
    private static final String MENUTOP_JSP = "/menutop.jsp";
    private static final String HEAD_JSP1 = "/headNew.jsp";
    
    public void init(FilterConfig filterConfig) throws ServletException

    {

        String strExcludePageNum = filterConfig.getInitParameter(
                "excludePageNum");

        if (strExcludePageNum != null)

        {

            try

            {

                Integer excludePageNum = Integer.valueOf(strExcludePageNum);

                ArrayList<String> list = new ArrayList<String>();

                for (int i = 0; i < excludePageNum.intValue(); i++)

                {

                    String strPage = filterConfig.getInitParameter("page" + i);

                    if (strPage != null)

                    {

                        list.add(strPage);

                    }

                    else

                    {

                        break;

                    }

                }

                if (list.size() > 0)

                {

                    excludePages = new String[list.size()];

                    excludePages = (String[]) list.toArray(excludePages);

                }

            }

            catch (NumberFormatException ex)

            {

                ex.printStackTrace();

            }

        }

    }

    private boolean checkParam(String paramStr,String type){
    	SQLIDefense sqliDefense = SQLIDefense.getInstance();
    	return sqliDefense.checkSQLParam(paramStr);

    }
    
    
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)

            throws java.io.IOException, ServletException

    {

        HttpSession session = ((HttpServletRequest) request).getSession();

        GlobalInput tGI = (GlobalInput) session.getAttribute("GI");

        HttpServletRequest req = (HttpServletRequest) request;

        String servletPath = req.getServletPath();
        String dirPath = "";
        if (servletPath.length() > 9) {
            dirPath = servletPath.substring(1, 9);
//            logger.debug("dirPath:" + dirPath);
        }
        String qs = req.getQueryString();
        String qStr = null;
        if(qs != null){
            byte[] qsBytes = qs.getBytes("ISO-8859-1");
            qStr = new String(qsBytes, "GBK");
        }
        logger.debug("dirPa@@@th****:" + qStr);
        //logger.debug(req.getContextPath());
        logger.debug("dirPath****:" + servletPath);
        String tQueryString = qStr;
       
        logger.debug("tQueryString****:" + tQueryString);
        if(tQueryString!=null){
        	 SQLIDefense sqliDefense = SQLIDefense.getInstance();
        	if(!sqliDefense.checkCSRFParam(tQueryString)){
        		logger.debug("发现违规数据"+((HttpServletRequest) request).getContextPath()+indexpagename);
        		 HttpServletResponse hres = (HttpServletResponse) response;
                 hres.sendRedirect("../indexlis.jsp");
        		return;
        		 //request.getRequestDispatcher(((HttpServletRequest) request).getContextPath()+indexpagename).forward(request,response);
                 //return ;  
        	}
        }
        
        if(servletPath.indexOf("zh-cn")!=-1 || servletPath.indexOf("en")!=-1){
            servletPath = servletPath.substring(servletPath.indexOf("/") + 1);
            if (servletPath.indexOf("/") != -1) {
                servletPath = servletPath.substring(servletPath.indexOf("/"));
            }
        }
        
        
       
        if(servletPath.indexOf("EasyQueryVer3Window.jsp")!=-1){
        	logger.debug("###发现违规路径:"+servletPath);
    		//request.setAttribute(paramName, ""); 
    		HttpServletResponse hres = (HttpServletResponse) response;
             hres.sendRedirect("../.."+indexpagename);
             return;
        }
        
        //SQL注入校验
        if((servletPath.indexOf(SQLI_JSP)!=-1)
        	||servletPath.indexOf(LOGON_JSP)!=-1
        	||servletPath.indexOf(MENU2_JSP)!=-1
        	||servletPath.indexOf(MENU2_JSP1)!=-1
        	||servletPath.indexOf(HEAD_JSP1)!=-1
        	||servletPath.indexOf(HEAD_JSP)!=-1
        	||servletPath.indexOf(TITLE_JSP)!=-1
        	||servletPath.indexOf(LOGOUT_JSP)!=-1
        	||servletPath.toLowerCase().indexOf(MENUTOP_JSP)!=-1){
        	
        	//txtVarData=&txtCodeName=comcode&txtOther=&txtFrameName=fraInterface&txtSQL=&startIndex=&txtQueryResult=&mOperate=&txtCodeCondition=&txtConditionField=&txtShowWidth=undefined&changeEven=undefined&searchFlag=1
        	Enumeration<?> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) 
            {
            	
            	String paramName = (String) paramNames.nextElement();
            	String[] paramValues = request.getParameterValues(paramName);
            	String value = "";
            	for(int i=0;i<paramValues.length;i++){
            		value += paramValues[i];
            	}
            	//logger.debug("name:"+paramName + "    value:"+value);
            	boolean checkResult = true;
            	if(servletPath.indexOf(SQLI_JSP)!=-1){
            		
            		if(tGI==null){
            			checkResult = checkParam(value,"0");
            		}else{
            			checkResult = checkParam(value,"1");
            		}
            	}else{
            		checkResult = checkParam(value,"0");
            	}
            	if(!checkResult){
            		logger.debug("###发现违规数据:"+URLDecoder.decode(value,   "utf-8"));
            		//request.setAttribute(paramName, ""); 
            		HttpServletResponse hres = (HttpServletResponse) response;
                     hres.sendRedirect("../.."+indexpagename);
                     return;
                                 	
            	}
             }
            
            
        }
        
        
        
        

        if (!servletPath.equals("/indexlis.jsp")&&!servletPath.equals("/logon/menu.jsp")&&
              !servletPath.equals("/logon/menutop.jsp")&&!dirPath.equals("easyscan")&&
              !servletPath.equals("/logon/quick.jsp")&&!servletPath.equals("/logon/main.jsp")
              &&!servletPath.equals("/common/cvar/CVarData.jsp")
              &&!servletPath.equals("/common/cvar/CExec.jsp")
              &&!servletPath.equals("/logon/Title.jsp")
               &&!servletPath.equals("/logon/LogonSubmit.jsp")
               &&!servletPath.equals("/logon/head.jsp")
               &&!servletPath.equals("/logon/headNew.jsp")
               &&!servletPath.equals("/logon/mainNew.jsp")
               &&!servletPath.equals("/logon/menu2New.jsp")
               &&!servletPath.equals("/logon/StationNew.jsp"))
             // &&
            //(servletPath.indexOf("zh-cn")!=-1 || servletPath.indexOf("en")!=-1) 
            //  )
        {

            if (tGI == null && !isExcludePage(servletPath) )

            {

                logger.debug("session is null");
                logger.debug("innnnnnnn "+servletPath);

                if(servletPath.indexOf("common/")!=-1){
                	HttpServletResponse hres = (HttpServletResponse) response;

                    hres.sendRedirect("../.."+indexpagename);
                }else{
                
                HttpServletResponse hres = (HttpServletResponse) response;

                hres.sendRedirect(indexpagename);
                }

            }

            else

            {

            	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
            	
                String searchSql =
                    "select count(1) from ldmenu where NodeSign = '2' and runscript like concat(concat('%','?servletPath?'),'%')";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(searchSql);
                sqlbv.put("servletPath", servletPath);
                TransferData tTransferData=new TransferData();
                tTransferData.setNameAndValue("SQL", sqlbv);
                VData tVData = new VData();
                tVData.add(tTransferData);
                SSRS tSSRS=null;
                if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI"))
                {
                    tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
                }

                if (tGI != null && tSSRS != null)

                {

                    String tt[] = tSSRS.getRowData(1);

                    if (!tt[0].equals("0"))

                    {

                        if (!PubFun.canIDo(tGI, ".." + servletPath, "page"))

                        {

                            HttpServletResponse hres = (HttpServletResponse)
                                    response;

                            hres.sendRedirect(indexpagename);
                            return;

                        }

                    }

                    else

                    {

                        String search2Sql =
                            "select count(1) from ldmenu where NodeSign = '2' and runscript like concat(concat('%','?servletPath?'),'%')";
                        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                        sqlbv1.sql(search2Sql);
                        sqlbv1.put("servletPath", servletPath);
                        tTransferData=new TransferData();
                        tTransferData.setNameAndValue("SQL", sqlbv1);
                        tVData = new VData();
                        tVData.add(tTransferData);
                        SSRS t2SSRS=null;
                        if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI"))
                        {
                        	t2SSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
                        }                 
                        
                        if (t2SSRS != null)

                        {

                            String tt2[] = t2SSRS.getRowData(1);

                            if (!tt2[0].equals("0"))

                            {

                                if (!PubFun.canIDo(tGI, ".." + servletPath,
                                        "menu"))

                                {

                                    HttpServletResponse hres = (
                                            HttpServletResponse) response;

                                    hres.sendRedirect(indexpagename);

                                }

                            }

                        }

                    }

                }

                chain.doFilter(request, response);

            }

        }

        else {
            chain.doFilter(request, response);
        }

    }


    private boolean isExcludePage(String servletPath)

    {

        boolean excluded = false;

        if (excludePages != null)

        {

            for (int i = 0; i < excludePages.length; i++)

            {

                if (servletPath.equals(excludePages[i]))

                {

                    excluded = true;

                    break;

                }

            }

        }

        return excluded;

    }


    public void destroy()

    {

    }

}
