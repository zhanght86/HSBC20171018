<!--
ProposalApproveEasyScan.jsp
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //String SubType = "TB10" + request.getParameter("SubType");
  String SubType = request.getParameter("SubType");
%>
<!--Root="../../" -->

<html>
<head>
<title>Ͷ��ɨ�����ʾ </title>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	//window.focus();
	var initWidth = 0;
	//ͼƬ�Ķ�������
	var pic_name = new Array();
	var pic_place = 0;
	var b_img	= 0;  //�Ŵ�ͼƬ�Ĵ���
	var s_img = 0;	//��СͼƬ�Ĵ���
  	window.onunload = afterClose;
	function afterInput() 
	{
		try 
		{
		  top.opener.afterInput();
		}
		catch(e) {}
	}
  
  var mainPolNo = "";
  var mainRisk = "";
  function queryScanType() 
  {
	    var sqlid1="ProposalApproveEasyScanSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalApproveEasyScanSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1); //ָ��ʹ�õ�Sql��id
		//mySql1.addSubPara(tContNo); //ָ������Ĳ���
		var strSql=mySql1.getString();	
	  
    //var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    var arrResult=easyExecSql(strSql);
    //alert(arrResult);
    return arrResult;
  }  
  function afterClose() 
  {
    try 
    {
      //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=<%=request.getParameter("prtNo")%>&CreatePos=�б�����&PolState=1003&Action=DELETE";
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	  //top.opener.easyQueryClick();
    }
    catch(e) {}
  }
  
  var type = "<%=request.getParameter("Type")%>";

</script>

</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,*,0">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
<%
if(request.getParameter("BankFlag")!=null&&(request.getParameter("BankFlag").equals("1")||request.getParameter("BankFlag").equals("01")))
{
%>		
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=SubType%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./ContCheckInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&scantype=scan">
<%
}
else if(request.getParameter("BankFlag")!=null&&request.getParameter("BankFlag").equals("3"))
{
%>   	
    <frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=SubType%>">
	<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./BankContCheckInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&scantype=scan">
<%
}
else if(request.getParameter("BankFlag")!=null&&request.getParameter("BankFlag").equals("5"))
{
%>   	
	<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=SubType%>">
	<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DirectContCheckInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&scantype=scan">
<%
}
else 
{
%>
    <frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=TB1005">
    <frame id="fraInterface" name="fraInterface" scrolling="auto" src="../app/CardContInput.jsp?BankFlag=<%=request.getParameter("BankFlag")%>&prtNo=<%=request.getParameter("prtNo")%>">  
<% 
}
%>
    
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

