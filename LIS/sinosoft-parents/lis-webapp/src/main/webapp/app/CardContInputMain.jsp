<!--
ProposalApproveEasyScan.jsp
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
 
 //RelationConfig aRelationConfig = new RelationConfig();
 //String SubType = aRelationConfig.getBackRelation(request.getParameter("SubType"));   
 
 String SubType ="";
 
 if(request.getParameter("SubType")!=null&&request.getParameter("SubType").equals("UA005")){
   SubType="TB1010";
 }
 if(request.getParameter("SubType")!=null&&request.getParameter("SubType").equals("UA004")){
   SubType="TB1009";
 
 }
 if(request.getParameter("SubType")!=null&&request.getParameter("SubType").equals("UA003")){
   SubType="TB1008";
 
 }
  if(request.getParameter("SubType")!=null&&request.getParameter("SubType").equals("UA002")){
   SubType="TB1007";
 
 }
  if(request.getParameter("SubType")!=null&&request.getParameter("SubType").equals("UA009")){
   SubType="TB1062";
 
 }

%>
<!--Root="../../" -->

<html>
<head>
<title>Ͷ��ɨ�����ʾ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

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
  
  prtNo = "<%=request.getParameter("prtNo")%>";
  var tLoadFlag = "<%=request.getParameter("LoadFlag")%>";
  window.onbeforeunload = beforeAfterInput;
  window.onunload = afterInput;
  
  function beforeAfterInput() {
    try { 
       if(tLoadFlag != '99') 
       {   
        //�ر�¼������ʱ�����涯��ʾ������Ϣ
        top.fraInterface.goToLock = "RiskCode";
       //top.fraInterface.goToAreaProposal();
      
       for (i=0; i<top.fraInterface.arrScanType.length; i++) {
        eval("if (top.fraInterface.RiskType==top.fraInterface.arrScanType[i][0]) top.fraInterface.goToArea" + top.fraInterface.arrScanType[i][0] + "()");
       }
      
      top.fraInterface.goToLock = "RiskCode";
      
      //��ѯ¼��������ָ���������
      var state = "1";
      var urlStr = "./ProposalScanApplyClose.jsp?prtNo=" + prtNo + "&state=" + state;
      var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
      //var strResult = window.showModalDialog(urlStr, "", sFeatures);
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=400;      //�������ڵĿ��; 
		var iHeight=200;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var strResult = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		strResult.focus();
      
      //��ѯ�ɹ������ַ��������ض�ά����
      var tArr = decodeEasyQueryResult(strResult);
      filterArray          = new Array(0, 2);
      var arrResult = chooseArray(tArr, filterArray);
      
      var strDisplay = "���Ѿ�¼���˸�ɨ����� " + arrResult.length + " ��������Ϣ�����ֱ������£�";
      for (var i=0; i<arrResult.length; i++) {
        if (arrResult[i][1] != "undefined" && arrResult[i][1] != null)
          strDisplay = strDisplay + "\n" + arrResult[i][0] + " ���� " + arrResult[i][1];
        else 
          //strDisplay = strDisplay + "\n" + "û��������Ϣ��";
          strDisplay = "��ɨ�����û������κ�������Ϣ��¼�룬��ȷ�ϣ�";
      }         
      event.returnValue = strDisplay;
     } 
    }
    catch(e) {alert("err");}
  }
  
  function afterInput() {
    try { 
      top.opener.afterInput(); 
    } 
    catch(e) {}
  }
  
  var mainPolNo = "";
  
  //��ѯɨ��ͼƬ������
  function queryScanType() {
//     var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    
	var sqlid1="CardContInputMainSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.CardContInputMainSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSql=mySql1.getString();
    
    return easyExecSql(strSql);
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=request.getParameter("SubType")%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./CardContInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&scantype=<%=request.getParameter("scantype")%>">
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

