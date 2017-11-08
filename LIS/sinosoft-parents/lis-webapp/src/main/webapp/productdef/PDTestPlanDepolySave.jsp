<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDTestDeploySave.jsp
//程序功能：产品测试与发布
//创建日期：2009-3-18
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 
 try
 {
     String tChk[] = request.getParameterValues("InpMulline11GridChk"); 

      for(int index=0;index<tChk.length;index++)
      {
        if(tChk[index].equals("1")) {         
         //更新状态 添加修改人和时间
         String[] tClewcontentcodes = request.getParameterValues("Mulline11Grid5");
         String[] tTestplankinds = request.getParameterValues("Mulline11Grid6");
         String[] tTestPersons = request.getParameterValues("Mulline11Grid3");
         String[] tTestTimes = request.getParameterValues("Mulline11Grid4");
         
         String tTestPerson = tTestPersons[index];
         String tClewcontentcode = tClewcontentcodes[index];
         String tTestplankind = tTestplankinds[index];
         String tTestTime = tTestTimes[index];
         
         //判断
         if(tTestplankind == null || tTestTime == null || tTestplankind.trim().equals("") || tTestTime.trim().equals("")){
        	 FlagStr = "Fail";
        	 Content = ""+"测试人和测试时间不能为空"+"";
        %>
        <script type="text/javascript">
		 	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
		</script>
        <% 
        return;
         }
         System.out.println("tClewcontentcode" + tClewcontentcode + "  tTestplankind" + tTestplankind);
         String sql = "update pd_testTrack kk set kk.teststate = '1' , kk.testperson = '" + tTestPerson +"' , kk.testtime = '" + tTestTime +"', kk.modifydate = '" + PubFun.getCurrentDate() + "', kk.modifytime = '" + PubFun.getCurrentTime() +"' where kk.testplankind = '" + tTestplankind + "' and kk.clewcontentcode = '" + tClewcontentcode +"'";
         ExeSQL tExeSQL = new ExeSQL();
         tExeSQL.execUpdateSQL(sql);
        }if(tChk[index].equals("0")) { 
         String[] tClewcontentcodes = request.getParameterValues("Mulline11Grid4");
         String[] tTestplankinds = request.getParameterValues("Mulline11Grid5");
         String tClewcontentcode = tClewcontentcodes[index];
         String tTestplankind = tTestplankinds[index];	
         
         System.out.println("tClewcontentcode" + tClewcontentcode + "  tTestplankind" + tTestplankind);
         //更新状态 ，删除修改人和时间
         String sql = "update pd_testTrack kk set kk.teststate = '0' , kk.testperson = '' , kk.testtime = '', kk.modifydate = '" + PubFun.getCurrentDate() + "', kk.modifytime = '" + PubFun.getCurrentTime() +"' where kk.testplankind = '" + tTestplankind + "' and kk.clewcontentcode = '" + tClewcontentcode +"'";
         ExeSQL tExeSQL = new ExeSQL();
         tExeSQL.execUpdateSQL(sql);	        
        }
        
      }         
      Content = ""+"保存成功!"+"";
      FlagStr = "Successs";
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {

  if (!tError.needDealError())
  {                          
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

