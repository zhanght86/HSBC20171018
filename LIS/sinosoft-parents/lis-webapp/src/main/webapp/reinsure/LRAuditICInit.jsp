<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRProfitLossCalInit.jsp
//function : 
//Creator :zhangbin
//date :2007-3-14
%>

<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 		GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 		String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);                	               	
 %>
<script type="text/javascript">
function initInpBox(){
  try{
  	//���ղ���
  	//fm.OperateNo.value="88800001"; 
  	//���ղ���
  	// fm.OperateNo.value="2008092712"; 
  	//�������
  	//fm.OperateNo.value="4100000450"; 
  	//fm.OperateNo.value="88888888"; 
  }
  catch(ex){
    myAlert("���г�ʼ���ǳ��ִ���");
  }
}
;

// ������ĳ�ʼ��
function initSelBox(){
  try{
  }
  catch(ex){
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm(){
  try{
    initInpBox();
    initSelBox();
  }
  catch(re){
    myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
  }
}
</script>


