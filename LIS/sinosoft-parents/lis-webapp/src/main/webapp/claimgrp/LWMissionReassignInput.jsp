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
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LWMissionReassign.js"></SCRIPT>
   <%@include file="LWMissionReassignInit.jsp"%>
</head>
<body onload="initForm();" >
<form  method=post name=fm target="fraSubmit">
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchCondition);"></TD>
            <TD class= titleImg>�ⰸ��ѯ</TD>
            <TD> <input value="��   ѯ" class= cssButton type=button onclick="LWMissionQueryClick();"></TD>
        </TR>
    </table>
    <Div  id= "divSearchCondition" style= "display: ''">
    	<table  class= common>
       	    <TR  class= common>
			    <TD  class= title> �ⰸ�׶� </TD>
          	    <!--<TD  class= input> <Input class= common name=ActivityIDQ ></TD>-->
				<TD class= input><Input class=codeno name="ActivityIDQ" ondblclick="return showCodeList('activityid',[this,ActivityIDQName],[0,1]);" onkeyup="return showCodeListKey('activityid',[this,ActivityIDQName],[0,1]);"><input class=codename name="ActivityIDQName" readonly=true></TD>           	    
         	    <TD  class= title> �ⰸ�� </TD>
          	    <TD  class= input> <Input class= common name=ClmNOQ></TD>
          	    <TD  class= title> ����Ա </TD>
          	    <TD  class= input> <Input class= common name=OperatorQ></TD>
            </TR>
        </table>    
	</Div>	
	
	<table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchResult);"></TD>
            <TD class= titleImg>�ⰸ��ѯ�б�</TD>
        </TR>
    </table>
    <Div  id= "divSearchResult" style= "display: ''">
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLWMissionGrid" ></span></td>
	        </tr>
	    </table>  
	    <input class= button value=" ��  ҳ " type=button onclick="getFirstPage();"> 
	    <input class= button value=" ��һҳ " type=button onclick="getPreviousPage();">                   
	    <input class= button value=" ��һҳ " type=button onclick="getNextPage();"> 
	    <input class= button value=" β  ҳ " type=button onclick="getLastPage();">  
	</Div>	
	<hr>
    <Div  id= "" style= "display: ''">
    	<table class= common>
	        <tr class= common>
				<TD  class= title> ԭ����Ա</TD>
				<TD  class= input> <input class="readonly" readonly name=DefaultOperator></TD>
		        <TD  class= title></TD>      
		        <TD  class= input></TD>   		
		        <TD  class= title></TD>      
		        <TD  class= input></TD>   
		    </TR>    
		    <tr class= common>
				<TD  class= title> ָ������Ա</TD>
          	    <TD  class= input> <Input class= common name=DesignateOperator readonly ></TD>
            	<TD> <input value="��ѯ�����û�" class= cssButton type=button onclick="LLClaimUserQueryClick();"></TD>
		    </TR>    
		</Table>	
		<hr>   
		<Table>
			<tr>
				<TD> <input value="ָ��ȷ��" class= cssButton type=button onclick="DesignateConfirmClick();"></TD>
 			</tr>
 		</table>	
    </Div>		
    <!--������,������Ϣ��-->
    <input type=hidden id="Operate" name=Operate>
    <input type=hidden id="ComCode" name=ComCode>    
    
	<input type=hidden id="MissionID"   name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">  
	  
    <input type=hidden id="fmtransact" name="fmtransact">
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>