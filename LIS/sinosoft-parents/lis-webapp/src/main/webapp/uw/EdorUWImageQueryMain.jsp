<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author ����ǿ <XinYQ@sinosoft.com.cn>
 * @version 1.00
 * @date 2005-11-01
 ******************************************************************************/
%>


<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <title>��ȫ�˱�Ӱ�����ϲ�ѯ</title>
  <script src="../common/javascript/Common.js" ></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script language="javascript">
  	var intPageWidth = screen.availWidth;
  	var intPageHeight = screen.availHeight;
  	window.resizeTo(intPageWidth, intPageHeight);
  	window.moveTo(-1, -1);
  	//window.focus();

  	var initWidth = 0;
    //ͼƬ�Ķ�������
    var pic_name = new Array();
    var pic_place = 0;
    var b_img	= 0;  //�Ŵ�ͼƬ�Ĵ���
    var s_img = 0;	//��СͼƬ�Ĵ���

    window.onunload = afterInput;

    function afterInput()
    {
      try { top.opener.afterInput(); }
      catch(ex) {}
    }

    var mainPolNo = "";
    var mainRisk = "";
    //��ѯɨ��ͼƬ������
    function queryScanType()
    {
  //    var strSql = " select code1, codename, codealias from ldcode1 where codetype='scaninput'";
      
     var sqlid3="EdorUWImageQuerySql3";
 	 var mySql3=new SqlClass();
 	 mySql3.setResourceName("uw.EdorUWImageQuerySql");
 	 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
 	 var strSql = mySql3.getString();
 	 
      var arrResult = easyExecSql(strSql);
      //alert(arrResult);
      return arrResult;
    }
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>&QueryType=0&DocID<%=request.getParameter("DocID")%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./EdorUWImageQuery.jsp?ContNo=<%=request.getParameter("ContNo")%>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	  <br><br><br><br><br>
	  <center><font size="2">�Բ��������������֧�ֿ����ҳ��</font></center>
	</body>
</noframes>
</html>
