<%
//�������ƣ�NBWMissionReassingInput.jsp
//�����ܣ�����Լ�������
//�������ڣ�2006-2-20 14:26
//������  ��chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="NBWMissionReassign.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="NBWMissionReassignInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit" action="UWMissionAssignSave.jsp"> 
  
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>�������ѯ������</td>
		</tr>
	</table>
	<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
        <table  class= common>
          	<TR  class= common>
                <TD  class= title> ����Լ״̬ </TD>
				<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name="ActivityID1" id="ActivityID1" CodeData="0|5  ^0000001090| �쳣������ ^0000001091|���˳�� ^0000001001|�µ����� ^0000001094|����Ͷ����¼�� ^0000001020|�������� ^0000001099|����Լɨ��¼�� ^0000001002|����Լ������޸�" ondblclick="return showCodeListEx('ActivityID',[this,ActivityIDName],[0,1]);" onclick="return showCodeListEx('ActivityID',[this,ActivityIDName],[0,1]);" onkeyup="return showCodeListKeyEx('ActivityID',[this,ActivityIDName],[0,1]);"><input class=codename name="ActivityIDName" id="ActivityIDName" readonly=true></TD>      	    
                <TD  class= title>ӡˢ��</TD>
                <TD  class= input><Input class="wid" class= common name=PrtNo id=PrtNo value=""></TD>
                <TD  class= title> ����Ա���� </TD>
          	    <TD  class= input><Input class="wid" class= common name=OperatorQ id=OperatorQ></TD></TR>
                <TR  class= common>
                
                <TD  class= title> ɨ�迪ʼ���� </TD>
          	    <TD  class= input>
                <Input class="coolDatePicker" onClick="laydate({elem: '#ScanStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ScanStartDate id="ScanStartDate"><span class="icon"><a onClick="laydate({elem: '#ScanStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
          	 <TD  class= title> ɨ��������� </TD>
          	    <TD  class= input>
                <Input class="coolDatePicker" onClick="laydate({elem: '#ScanEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ScanEndDate id="ScanEndDate"><span class="icon"><a onClick="laydate({elem: '#ScanEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD></TR>
          	
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQuery();">
    <!--<a href="javascript:void(0);" class="button" onClick="easyQuery();">��    ѯ</a> -->    
      
    <div id=DivLCContInfo STYLE="display:''"> 
        <table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllPolGrid);"></td>
        		<td class=titleImg>��������</td>
        	</tr>  	
        </table>
    </div>
	<div  id= "divAllPolGrid" style= "display: ''" align = center>
    	<table  class= common >
    		<tr  class=common>
    			<td text-align: left colSpan=1 ><span id="spanAllPolGrid" ></span></td>
    		</tr>
    	</table>
	 
	<div  id= "divPage" align=center style= "display: 'none' ">
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">	    
	</div></div> 
    <div class="maxbox1">
    <table class= common>
        <tr class= common style= "display: none ">
            <td  class= title>&nbsp;ԭ����Ա&nbsp;</td>
            <td  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=DefaultOperator id=DefaultOperator><input class="codename" readonly name=DefaultOperatorName id=DefaultOperatorName></td>
            </td> </tr> <tr class= common>
            <td class= title>ָ������Ա</td>
            <td  class= Input> <Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=DesignateOperator id=DesignateOperator  onclick="DuserQueryClick();" onchange="DuserNameQueryClick();"><input class=codename name=DesignateOperatorName id=DesignateOperatorName readonly=true></td>
            <td class= title></td>
            <td  class= Input></td> <td class= title></td>
            <td  class= Input></td>
        </tr>   

    </table>

<!--    <input value="��ѯ����Ա" class= cssButton type=button onclick="userQueryClick();">
    <INPUT class=cssButton id="riskbutton" VALUE=" ȷ  �� " TYPE=button onClick="reassignNBW();">--><br><br>
    <a href="javascript:void(0);" class="button" onClick="userQueryClick();">��ѯ����Ա</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="reassignNBW();">ȷ    ��</a>
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    
    <Input type=hidden name="MissionID" >
    <Input type=hidden name="SubMissionID" >
    <Input type=hidden name="ActivityID" >
    <input type=hidden id="fmtransact" name="fmtransact">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
