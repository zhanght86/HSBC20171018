<%@include file="/i18n/language.jsp"%>
 <%@ page contentType="text/html; charset=GBK" import="com.sinosoft.lis.xpath.*" import="java.io.*"%>
 <%@page import="javax.xml.parsers.*"%>
  <%@page import="org.w3c.dom.*"%>
  <%@page import="java.net.*"%>
  <%@page import="org.apache.xml.serialize.*"%>
  <%@page import="java.util.*"%>
  <%@page  import="org.xml.sax.InputSource" %>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@page import = "com.sinosoft.utility.*"%>
  <%@page import="java.net.*"%>
  <%

  String FlagStr = "";
  String Content = "";
  String bdate = request.getParameter("Bdate");
  String edate = request.getParameter("Edate");
  String tOpt=request.getParameter("Opt");
  String flag="true";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getAttribute("GI");

  CErrors tError = null;
  if (bdate.equals("") && edate.equals(""))
  {
          flag = "false";
          Content=""+"请录入日期1!"+"";
  }

  if (!bdate.equals("") && edate.equals(""))
  {
          edate = bdate;
  }

  if (bdate.equals("") && !edate.equals(""))
  {
          bdate = edate;
  }

  FDate chgdate = new FDate();
  Date dbdate = chgdate.getDate(bdate);
  Date dedate = chgdate.getDate(edate);

  if(dbdate.compareTo(dedate) > 0)
  {
          flag = "false";
          Content=""+"日期录入错误1!"+"";
          System.out.println(Content);

  }
  System.out.println("开始测试");

  if (flag.equals("true"))
  {
    if (tOpt.equals("Legal"))
    {
      CalPolRetentUI tCalPolRetentUI = new CalPolRetentUI();
      VData vData = new VData();

      vData.addElement(tG);
      vData.addElement(bdate);
      vData.addElement(edate);
      vData.addElement("1001");
      if (!tCalPolRetentUI.submitData(vData, "CalLegal"))
      {
        tError = tCalPolRetentUI.mErrors;
        System.out.println("fail to get print data");
        flag = "false";
        Content = " "+"提数失败，原因是："+""+tError.getFirstError();
        FlagStr = "Fail";
      }
      else
      {
        Content = " "+"提数成功"+"";
        FlagStr = "Succ";
      }
    }
    if (tOpt.equals("Comm"))
    {
      CalPolRetentUI tCalPolRetentUI = new CalPolRetentUI();
      VData vData = new VData();

      vData.addElement(tG);
      vData.addElement(bdate);
      vData.addElement(edate);
      vData.addElement("1001");
      if (!tCalPolRetentUI.submitData(vData, "CalComm"))
      {
        tError = tCalPolRetentUI.mErrors;
        System.out.println("fail to get print data");
        flag = "false";
        Content = " "+"提数失败，原因是："+""+tError.getFirstError();
        FlagStr = "Fail";
      }
      else
      {
        Content = " "+"提数成功"+"";
        FlagStr = "Succ";
      }
    }
     if (tOpt.equals("Edor"))
    {
      CalEndorRetentUI tCalEndorRetentUI = new CalEndorRetentUI();
      VData vData = new VData();

      vData.addElement(tG);
      vData.addElement(bdate);
      vData.addElement(edate);
      vData.addElement("1001");
      if (!tCalEndorRetentUI.submitData(vData, "CalEdor"))
      {
        tError = tCalEndorRetentUI.mErrors;
        System.out.println("fail to get print data");
        flag = "false";
        Content = " "+"提数失败，原因是："+""+tError.getFirstError();
        FlagStr = "Fail";
      }
      else
      {
        Content = " "+"提数成功"+"";
        FlagStr = "Succ";
      }
    }
    
    if (tOpt.equals("Claim"))
    {
      CalClaimRetentUI tCalClaimRetentUI = new CalClaimRetentUI();
      VData vData = new VData();

      vData.addElement(tG);
      vData.addElement(bdate);
      vData.addElement(edate);
      vData.addElement("1001");
      if (!tCalClaimRetentUI.submitData(vData, "CalClaim"))
      {
        tError = tCalClaimRetentUI.mErrors;
        System.out.println("fail to get print data");
        flag = "false";
        Content = " "+"提数失败，原因是："+""+tError.getFirstError();
        FlagStr = "Fail";
      }
      else
      {
        Content = " "+"提数成功"+"";
        FlagStr = "Succ";
      }
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

