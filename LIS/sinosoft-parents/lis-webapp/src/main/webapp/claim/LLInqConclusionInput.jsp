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
<%@page import = "com.sinosoft.lis.claim.*"%>
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
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLInqConclusion.js"></script>
    <%@include file="LLInqConclusionInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>

    <!--���������Ϣ-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusion);"></TD>
            <TD class= titleImg>�����������¼��</TD>
        </TR>
    </table>
    <Div id= "divLLInqConclusion" style= "display: ''" class="maxbox1">
        <Table class=common>
            <TR class=common>
                <TD class=title> ������ </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ClmNo id=ClmNo></TD>
                <TD class=title> ������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ConNo id=ConNo></TD>
                <TD class=title> �������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name=BatNo id=BatNo></TD>                
            </TR>
            <TR class=common>
		        <TD class=title> ������� </TD>  <!--##ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);"##-->
		        <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InitDept" id="InitDept" onclick="return showCodeList('stati',[this,InitDeptName],[0,1]);"ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="InitDeptName" id="InitDeptName" readonly=TRue></TD>
		        <TD class=title> ������� </TD>  <!--##ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"##-->
		        <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqDept" onclick="return showCodeList('stati',[this,InqDeptName],[0,1]);" ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"  onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" id="InqDeptName" readonly=TRue></TD>
                <TD class=title> ��ɱ�־ </TD>
                <TD class= input><Input class="readonly wid" readonly  name=FiniFlag id=FiniFlag></TD>
            </TR>
        	<TR  class= common>
                <TD class=title> ���ر�־ </TD>
                <TD class= input><Input class="readonly wid" readonly  name=LocFlag id=LocFlag ></TD>
                <TD class=title> ���ܱ�־ </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ColFlag id=ColFlag ></TD>
                <TD class=title> ���Խ��� </TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly value= "0" name=MasFlag id=MasFlag CodeData="0|^0|��^1|��" ondblClick="showCodeListEx('MasFlag',[this,MasFlagName],[0,1]);" onClick="showCodeListEx('MasFlag',[this,MasFlagName],[0,1]);" onkeyup="showCodeListKeyEx('MasFlag',[this,MasFlagName],[0,1]);"><Input class=codename name= "MasFlagName" id= "MasFlagName" value= "��" readonly=true> </TD>
		    </TR>
        </table>
    </Div>  
    <!--<table>
		<TR  class= common>
            <TD><Input class=cssButton name=""  value="����������" type=button onclick="queryInqApply()"></TD>
            <TD><Input class=cssButton name=""  value="��       ��" type=button onclick="hideInqApply()"></TD>
			<TD><Input class=cssButton name=""  value="�鿴������Ϣ" type=button onclick="queryInqInfo()"></TD>
		</TR>    
    </table>-->
    <a name="" href="javascript:void(0);" class="button" onClick="queryInqApply();">����������</a>
    <a name="" href="javascript:void(0);" class="button" onClick="hideInqApply();">��    ��</a>
    <a name="" href="javascript:void(0);" class="button" onClick="queryInqInfo();">�鿴������Ϣ</a><br><br>
	<Div  id= "divQueryInqApply" style= "display: none">
	 	   <!--����������Ϣ-->
			<Table class= common>
			   <TR class= common>
			        <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
			   </TR>
			</Table>  
			<!--<Table align="center"> 
				<TR class=common>  
					<TD><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></TD>
					<TD><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></TD>
					<TD><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></TD>
					<TD><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></TD>
				</TR> 
			</Table>  -->
            <hr class="line"> 
			<Table class=common> 
				<TR class= common>
				   <TD class= title> ����ԭ�� </TD>
				   <!--##ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"####-->
				   <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqReason" id="InqReason" ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"><input class=codename name="InqReasonName" id="InqReasonName" readonly=TRue></TD>
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
				  <TD style="padding-left:16px" class= input> <textarea name="InqItem" cols="199" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>
				<TR class= common>
				  <TD class= title> ����������� </TD>
				</TR> 
				<TR class= common>       
				  <TD style="padding-left:16px" class= input> <textarea name="InqConclusion1" cols="199" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>                     
			</Table>  
	</Div>   
    <Div>    
        <Table class= common>
	    	<TR class= common>
	    	    <TD class= title> ����������������<font size=1 color='#ff0000'><b>*</b></font> </TD>
    		</TR>
		    <TR class= common>
	    	    <TD style="padding-left:16px" class= input> <textarea name="InqConclusion" cols="199%" rows="4" wiTDh=25% class="common"></textarea></TD>
		    </TR>            
	    	<TR class= common>
	    	    <TD class= title> ��ע </TD>
    		</TR>
		    <TR class= common>
	    	    <TD style="padding-left:16px" class= input> <textarea name="Remark" cols="199%" rows="4" wiTDh=25% class="common"></textarea></TD>
		    </TR>
        </Table>
        <!--<table>
            <TR>
                <TD><Input class=cssButton name="saveAdd"  value=" �� �� " type=button onclick="saveClick()"></TD>
                <TD><Input class=cssButton name=""  value=" �� �� " type=button onclick="goBack()"></TD>
            </TR>
        </table>-->
        <br>
        <a href="javascript:void(0);" name="saveAdd" class="button" onClick="saveClick();">��    ��</a>
        <a href="javascript:void(0);" name="" class="button" onClick="goBack();">��    ��</a>        
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
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
