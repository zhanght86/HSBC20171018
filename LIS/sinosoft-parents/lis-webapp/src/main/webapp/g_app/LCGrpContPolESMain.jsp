<%
/***************************************************************
 * <p>ProName��LCGrpContESMain.jsp</p>
 * <p>Title���µ�����</p>
 * <p>Description���µ�¼�루��Ӱ��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-19
 ****************************************************************/
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tMissionID = request.getParameter("MissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tFlag = request.getParameter("Flag");
	String SubType = request.getParameter("SubType");
	String BussNo = request.getParameter("BussNo");
	String BussType = request.getParameter("BussType");
	String ScanFlag = request.getParameter("ScanFlag");
	String tSrc = "";
	tSrc = "./LCGrpContPolInput.jsp";
	tSrc += "?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag+"&ScanFlag="+ScanFlag;

%>
<html>
<head>
<title>�µ�����</title>
<script language="javascript">
	var pic_name = new Array();
	var pic_place = 0;
	var viewMode = 1;
	//���û��ر�һ��ҳ��ʱ���� onunload �¼���
	window.onunload = afterInput;
	function afterInput() {
		try {
			$("body").remove();//�ͷ��ڴ�
		}catch(e) {}
	}
</script>

</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
		
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--��һ��ҳ������-->
		<frame name="fraNext" src="about:blank" scrolling="auto">
	</frameset>
	
</frameset>

<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>

</html>
