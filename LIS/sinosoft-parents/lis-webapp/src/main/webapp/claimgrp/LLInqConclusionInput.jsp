<%
//�������ƣ�LLInqConclusionInput.jsp
//�����ܣ����������Ϣ
//�������ڣ�2005-05-10
//������  ��zhoulei
//���¼�¼��yuejw
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
		<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //�ⰸ��
	      String tConNo=request.getParameter("ConNo"); //�������      
	      String tActivityid=request.getParameter("Activityid"); //�ID
		  String tMissionID =request.getParameter("MissionID");  //����������ID
		  String tSubMissionID =request.getParameter("SubMissionID");  //������������ID	
 		  
	%> 	
    <title>���������Ϣ</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLInqConclusion.js"></script>
    <%@include file="LLInqConclusionInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>

    <!--���������Ϣ-->
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusion);"></TD>
            <TD class= titleImg>���������Ϣ</TD>
        </TR>
    </table>
    <Div id= "divLLInqConclusion" style= "display: ''">
        <Table class=common>
            <TR class=common>
                <TD class=title> �ⰸ�� </TD>
                <TD class= input><Input class="readonly" readonly  name=ClmNo></TD>
                <TD class=title> ������� </TD>
                <TD class= input><Input class="readonly" readonly  name=ConNo></TD>
                <TD class=title> �������� </TD>
                <TD class= input><Input class="readonly" readonly  name=BatNo></TD>                
            </TR>
            <TR class=common>
		        <TD class=title> ������� </TD>  <!--##ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);"##-->
		        <TD class= input><Input class=codeno readonly  name="InitDept"  onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="InitDeptName" readonly=TRue></TD>
		        <TD class=title> ������� </TD>  <!--##ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"##-->
		        <TD class= input><Input class=codeno readonly name="InqDept"  onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" readonly=TRue></TD>
                <TD class=title> ��ɱ�־ </TD>
                <TD class= input><Input class="readonly" readonly  name=FiniFlag></TD>
            </TR>
        	<TR  class= common>
                <TD class=title> ���ر�־ </TD>
                <TD class= input><Input class="readonly" readonly  name=LocFlag ></TD>
                <TD class=title> ���ܱ�־ </TD>
                <TD class= input><Input class="readonly" readonly  name=ColFlag ></TD>
                <TD class=title> ���Խ��� </TD>
                <TD class= input><Input class="readonly" readonly  name=MasFlag ></TD>
		    </TR>
        </table>
    </Div>  
    <table>
		<TR  class= common>
            <TD><Input class=cssButton name=""  value="�����������" type=button onclick="queryInqApply()"></TD>
            <TD><Input class=cssButton name=""  value="��       ��" type=button onclick="hideInqApply()"></TD>
			      <TD><Input class=cssButton name=""  value="�鿴������Ϣ" type=button onclick="queryInqInfo()"></TD>
			      <TD><Input class=cssButton name="LoadAffix" value="� ��  �� ��" type=button onclick="LoadAffixClick();"></TD>
		</TR>    
    </table>
    <hr> 
	<Div  id= "divQueryInqApply" style= "display: 'none'">
	 	   <!--����������Ϣ-->
			<Table class= common>
			   <TR class= common>
			        <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
			   </TR>
			</Table>  
			<Table class=common> 
				<TR class= common>
				   <TD class= title> ����ԭ�� </TD>
				   <!--##ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"####-->
				   <TD class= input><Input class=codeno readonly name="InqReason" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"><input class=codename name="InqReasonName" readonly=TRue></TD>
				   <TD class= title></TD>	
				   <TD class= input></TD>		
			       <TD class= title></TD>	
				   <TD class= input></TD>		
				</TR> 	
			</Table>  
			<Table class=common> 		
				<TR class= common>
				   <TD class= title> ����������Ŀ </TD>
				</TR> 
				<TR class= common>       
				  <TD class= input> <textarea name="InqItem" cols="100" rows="1" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>
				<TR class= common>
				  <TD class= title> ����������� </TD>
				</TR> 
				<TR class= common>       
				  <TD class= input> <textarea name="InqConclusion1" cols="100" rows="2" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>                     
			</Table>
   		<hr>  
	</Div>   
    <Div>    
        <Table class= common>
	    	<TR class= common>
	    	    <TD class= title> ����������������<font size=1 color='#ff0000'><b>*</b></font> </TD>
    		</TR>
		    <TR class= common>
	    	    <TD class= input> <textarea name="InqConclusion" cols="100%" rows="3" wiTDh=25% class="common"></textarea></TD>
		    </TR>            
	    	<TR class= common>
	    	    <TD class= title> ��ע </TD>
    		</TR>
		    <TR class= common>
	    	    <TD class= input> <textarea name="Remark" cols="100%" rows="3" wiTDh=25% class="common"></textarea></TD>
		    </TR>
        </Table>
        <table>
            <TR>
                <TD><Input class=cssButton name="saveAdd"  value=" �� �� " type=button onclick="saveClick()"></TD>
                <TD><Input class=cssButton name=""  value=" �� �� " type=button onclick="goBack()"></TD>
            </TR>
        </table>        
    </Div>

    <%
    //******************
    //�������ݵ����ر�
    //******************
    %>
    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
	<input type=hidden id="MissionID" name="MissionID"><!--����ID-->	
	<input type=hidden id="SubMissionID" name="SubMissionID"><!--������ID-->	  
    <input type=hidden id="Activityid" name="Activityid"><!--������ID-->	  
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag"><!---->	 

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
