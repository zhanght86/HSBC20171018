<%
//**************************************************************************************************
//ҳ������: LWMissionReassignInput.jsp
//ҳ�湦�ܣ��������·���
//������: yuejw    �������ڣ�2005-7-14   
//�޸����ڣ�  �޸�ԭ��/���ݣ�
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//###############���յ�½������Ϣ####################
	String CurrentDate = PubFun.getCurrentDate();
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <SCRIPT src="LWMissionReassign.js"></SCRIPT>
   <%@include file="LWMissionReassignInit.jsp"%>
</head>
<body onload="initForm();" >
<form  method=post name=fm id=fm target="fraSubmit">
    <table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchCondition);"></TD>
            <TD class= titleImg>�ⰸ��ѯ</TD>
        </TR>
    </table>
    <Div  id= "divSearchCondition" style= "display: ''" class="maxbox1">
    	<table  class= common>
       	    <TR  class= common>
			    <TD  class= title5> �ⰸ�׶� </TD>
				<TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name="ActivityIDQ" id="ActivityIDQ" ondblclick="return showCodeList('lpactivityid',[this,ActivityIDQName],[0,1]);" onclick="return showCodeList('lpactivityid',[this,ActivityIDQName],[0,1]);" onkeyup="return showCodeListKey('lpactivityid',[this,ActivityIDQName],[0,1]);"><input class=codename name="ActivityIDQName" id="ActivityIDQName" readonly=true></TD>           	    
         	    <TD  class= title5> ������ </TD>
          	    <TD  class= input5> <Input class="wid" class= common name=ClmNOQ id=ClmNOQ></TD>
			</TR>
			<TR class= common>
				<TD  class= title5> ������ </TD>
          	    <TD  class= input5> <Input class="wid" class= common name=ClmNOQ1 id=ClmNOQ1></TD>
          	    <TD  class= title5> ����Ա���� </TD>
          	    <TD  class= input5> <Input class="wid" class= common name=OperatorQ id=OperatorQ></TD>
            </TR>
        </table>    
	</Div>	
    <!--<input value="��   ѯ" class= cssButton type=button onclick="LWMissionQueryClick();">-->
    <a href="javascript:void(0);" class="button" onClick="LWMissionQueryClick();">��    ѯ</a>
	<table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchResult);"></TD>
            <TD class= titleImg>�ⰸ�б�</TD>
        </TR>
    </table>
    <Div  id= "divSearchResult" style= "display: ''">
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLWMissionGrid" ></span></td>
	        </tr>
	    </table>
       
	</Div>	

    <Div  id= "" style= "display: ''" class="maxbox1">
    	<table class= common>
	        <tr class= common>
				<TD  class= title5> ԭ����Ա</TD>
				<TD  class= input5> <input class="readonly wid" readonly name=DefaultOperator id=DefaultOperator></TD>
		   <TD  class= title5> ָ������Ա</TD>
          	    <TD  class= input5> <Input class="wid" class= common name=DesignateOperator id=DesignateOperator readonly ></TD>  
		    </TR>    
		     
		</Table> </Div>		
		<!--<input value="��ѯ�����û�" class= cssButton type=button onclick="LLClaimUserQueryClick();"> 
        <input value="ָ��ȷ��" class= cssButton type=button onclick="DesignateConfirmClick();">  --><br>
        <a href="javascript:void(0);" class="button" onClick="LLClaimUserQueryClick();">��ѯ�����û�</a>
        <a href="javascript:void(0);" class="button" onClick="DesignateConfirmClick();">ָ��ȷ��</a>
		
   	
    <!--������,������Ϣ��-->
    <input type=hidden id="Operator" name=Operator>
    <input type=hidden id="ComCode" name=ComCode>    
    <input type=hidden id="OComCode" name=OComCode>
    
	<input type=hidden id="MissionID"   name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">  
	  
    <input type=hidden id="fmtransact" name="fmtransact">
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
