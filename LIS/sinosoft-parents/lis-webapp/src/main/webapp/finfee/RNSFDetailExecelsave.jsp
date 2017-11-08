<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
//在此设置导出Excel的列名，应与sql语句取出的域相对应

       ExportExcel.Format format = new ExportExcel.Format();
       ArrayList listCell = new ArrayList();
       ArrayList listLB = new ArrayList();
       ArrayList listColWidth = new ArrayList();
       format.mListCell=listCell;
       format.mListBL=listLB;
       format.mListColWidth=listColWidth;

       ExportExcel.Cell tCell=null;
       ExportExcel.ListBlock tLB=null;

       //管理机构
       String tPolicyCom = request.getParameter("PolicyCom");
       loggerDebug("RNSFDetailExecelsave","管理机构=============="+tPolicyCom);
       //收费机构
       String tManageCom = request.getParameter("ManageCom");
       loggerDebug("RNSFDetailExecelsave","收费机构=============="+tManageCom);
       //到帐开始日期
       String tEnterAccStartDate = request.getParameter("EnterAccStartDate");
       loggerDebug("RNSFDetailExecelsave","到帐开始日期=============="+tEnterAccStartDate);
       //到帐结束日期
       String tEnterAccEndDate = request.getParameter("EnterAccEndDate");
       loggerDebug("RNSFDetailExecelsave","到帐结束日期=============="+tEnterAccEndDate);
       //交费开始日期
       String tStartPayDate = request.getParameter("StartDate");
       loggerDebug("RNSFDetailExecelsave","交费开始日期=============="+tStartPayDate);
       //交费结束日期
       String tEndPayDate = request.getParameter("EndDate");
       loggerDebug("RNSFDetailExecelsave","交费结束日期==============="+tEndPayDate);
       
       String tOtherType = request.getParameter("PayType");

       listColWidth.add(new String[]{"0","5000"});

       String sql = "select (select name from ldcom where comcode = t.policycom) policycom,"
                  + "(select name from ldcom where comcode = t.managecom) managecom,"
                  + "t.tempfeeno,t.otherno,(select codename from ldcode where codetype = 'tempfeeothernotype' and code = t.othernotype) as othernotype,"
                  + "t.paymoney,t.paydate,t.enteraccdate,t.confdate,"
                  + "(select codename from ldcode where codetype = 'paymodequery' and code = t.paymode)"
                  + " from ljtempfeeclass t where 1=1"
                  + " and t.managecom like '"+tManageCom+"%'";
                  
       loggerDebug("RNSFDetailExecelsave","收费明细sql："+sql);
       if (!tPolicyCom.equals("")&&tPolicyCom!=null)
       {
         sql = sql + " and t.policycom like '"+tPolicyCom+"%'";
       }
       if (!tEnterAccStartDate.equals("")&&tEnterAccStartDate!=null)
       {
         sql = sql + " and t.enteraccdate >= '"+tEnterAccStartDate+"'";
       }
       if (!tEnterAccEndDate.equals("")&&tEnterAccEndDate!=null)
       {
         sql = sql + " and t.enteraccdate <= '"+tEnterAccEndDate+"'";
       }
       if (!tStartPayDate.equals("")&&tStartPayDate!=null)
       {
         sql = sql + " and t.paydate >= '"+tStartPayDate+"'";
       }
       if (!tEndPayDate.equals("")&&tEndPayDate!=null)
       {
         sql = sql + " and t.paydate <= '"+tEndPayDate+"'";
       }
       if (!tOtherType.equals("")&&tOtherType!=null)
       {
         sql = sql + " and exists (select tempfeeno from ljtempfee where othernotype ='" +tOtherType+"' and tempfeeno=t.tempfeeno)";
       }       
       
       tLB = new ExportExcel.ListBlock("001");
       tLB.colName=new String[]{"管理机构","收费机构","暂收据号","其他号","业务类型","金额","收费日期","到帐日期","核销日期","缴费方式"};
       tLB.sql=sql;
       tLB.row1=0;
       tLB.col1=0;
       tLB.InitData();
       listLB.add(tLB);

       try{
         response.reset();
         response.setContentType("application/octet-stream");
         response.setHeader("Content-Disposition","attachment; filename=SFDetail_EnterAccDate"+tEnterAccStartDate+"_"+tEnterAccEndDate+"_PayDate"+tStartPayDate+"_"+tEndPayDate+"_List.xls");
         OutputStream outOS=response.getOutputStream();
         BufferedOutputStream bos=new BufferedOutputStream(outOS);

         ExportExcel excel = new ExportExcel();

         excel.write(format,bos);

         bos.flush();
         bos.close();
       }
       catch(Exception e){
         loggerDebug("RNSFDetailExecelsave","导出Excel失败！");
       };

%>
