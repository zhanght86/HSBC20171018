<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tPNo = "";
	String tBussNoType = "";
	try
	{
		tPNo = request.getParameter("PNo");
		tBussNoType = request.getParameter("BussNoType");
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//LoadFlag   3-- �쳣��
//           4-- ����
 	
 		String tLoadFlag = ""; 
		tLoadFlag = request.getParameter( "LoadFlag" );
		loggerDebug("WbProposalEasyScanInput","tLoadFlag"+tLoadFlag);
	 

%>
<head >
<script>
  var loadFlag = "<%=tLoadFlag%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����.
  var LoadFlag = loadFlag;
  var prtNo = "<%=request.getParameter("prtNo")%>";��
  var	MissionID = "<%=request.getParameter("MissionID")%>";
	var	SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var BussNoType = "<%=request.getParameter("BussNoType")%>";
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<script src="../common/javascript/EasyQuery.js"></script>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	
	<%@include file="WbProposalInit.jsp"%>
	<SCRIPT src="WbProposalInput.js"></SCRIPT>
	<SCRIPT src="ProposalAutoMove3.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>		

  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
</head>

<body  onload="initForm(); " >
  <form action="./WbProposalSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    ��
    <!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      �����ͬ��Ϣ¼��ؼ�         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="WBContPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->      
<hr size=3 noshade>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               ����Ͷ����¼��ؼ�����
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="WbAppntPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<hr size=3 noshade>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               ���뱻����¼��ؼ�
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="WbInsuredPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<hr size=3 noshade>
    <!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                     �ͻ���֪��Ϣ            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <Div id="DivImpart" style="display:">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divImpart);">
            </td>
            <td class= titleImg>
                �ͻ���֪<font color="maroon" >����֪��������/�ָ���</font>
            </td>
        </tr>
    </table>
    <div id="divImpart" style="display:">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanImpartGrid'>
            </span>
          </td>
        </tr>
      </table>
    </div>
  </Div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
--> 
       
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>    
   <!-- 
    ����ѡ������β��֣��ò���ʼ������
	<Div  id= "divDutyGrid" style= "display: 'none'">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		ȷ���Ƿ���Ҫ������Ϣ
	</Div>
	 -->
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
		<input  type=hidden id="fmLoadFlag" name="fmLoadFlag">
		<input type=hidden name="DealType" value="<%=request.getParameter("DealType")%>" >
		<input type=hidden name="aftersave" value="0"   >
		<!-- ������������� -->
		<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
		<input type="hidden" name="MissionID" value= "">
    <input type="hidden" name="SubMissionID" value= "">
    <input type="hidden" name="BussNoType" value= "">
    <input type="hidden" name="ActivityID" value= "">
    <input type="hidden" name="ProposalContNo" value= "">
    <input type="hidden" name="ProposalNo" value= ""><!-- ��ͬ�� -->
    <input type="hidden" name="InsuredNum" value= "0">
    <input type="hidden" name="MainRiskNum1" value= "0">
    <input type="hidden" name="MainRiskNum2" value= "0">
    <input type="hidden" name="MainRiskNum3" value= "0">
  
  <Div  id= "divButton" style= "display: ''">
  <br>
  <%@include file="./ProposalOperateButtonSpec.jsp"%>
  <input class=cssButton id="11" name="deleteCont" disabled VALUE=" ɾ���ѱ�����Ϣ " TYPE=button onClick="resetCont();">
  </Div> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>


