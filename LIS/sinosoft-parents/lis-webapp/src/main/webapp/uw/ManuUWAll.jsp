<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ManuUWAll.jsp.
//�����ܣ������˹��˱�
//�������ڣ�2005-1-28 11:10:36
//������  ��HYQ
//���¼�¼��ML   2006-03-08     ����ԭ��/����
%>
<%
	String tFlag = "";
	tFlag = request.getParameter("type");
%>
<html>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var operFlag = "<%=tFlag%>";		//�������պ͸��յı�־
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>";     //��¼��½����

</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ManuUWAll.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <SCRIPT src="../common/javascript/WorkFlowCommon.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ManuUWAllInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm();initElementtype();" >
<form method=post name=fm id="fm"  target="fraSubmit" action="./ManuUWAllChk.jsp">
 <!-- modify by lzf 2013-03-27 -->
  <div id = "ManuUWAllPool"></div>
  
  <!-- <INPUT class=cssButton VALUE="�˱���ť" TYPE=button onclick="IniteasyQueryAddClick();">
  <INPUT class=cssButton VALUE="�˻ع�����" TYPE=button onclick="BackPublicPool();"> 
 <!-- end by lzf -->
	<!--  �������ز�ѯ��������-->
	<!--lzf  <Table class= common>
		<TR>
			<TD class= titleImg align= center>�������ѯ������</TD>
		</TR>
	</Table>
	<Div  id= "divSearch" style= "display: ''">
		<Table  class= common>
		  	<TR  class= common>
				<TD  class= title8>ӡˢ��</TD>
				<TD  class= input8><Input class= common name=ContNo ></TD>
				<!--
				<TD  class= title>ӡˢ����</TD>
				<TD  class= input><Input class= common name=PrtNo ></TD>
				<TD  class= title>Ͷ����</TD>
				<TD  class= input><Input class= common name=AppntName ></TD>				
				<TD  class= title>��������</TD>
				<TD  class= input>
					<Input class="coolDatePicker" dateFormat="short"  name=ApproveDate >
				</TD>
				-->
			<!--lzf  <TD  class= title>ɨ������</TD>
				<TD  class= input>
					<Input class="multiDatePicker" dateFormat="short"  name=ScanDate >
				</TD>
			<TD  class=title>��������</TD>
				<TD class=input>
                 <Input class="codeno" name=SaleChnl ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true>
                </TD>							 
		    </TR>
		    <TR  class= common>	
		        <TD  class= title>���ֱ���</TD>
		        <TD  class= input><Input class="codeno" name=RiskCode ondblclick="showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class="codename" name="RiskCodeName" readonly="readonly"> </TD>
		        <TD  class= title>�������</TD>
				<TD  class= input>
					<Input class="codeno" name=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
				</TD>
				<TD  class= title>ҵ��Ա����</TD>
				<TD  class= input><Input class="code" name=AgentCode ondblclick="return queryAgent();"></TD>
		    </TR>
		    <TR  class= common>	
                <TD class="title" >�Ǽ�ҵ��Ա</TD>
				<TD class="input">
					<Input class="codeno" readonly name="StarAgent"  ><input class="codename" name="StarAgentName" readonly="readonly">
				</TD>
				<TD class="title">VIP�ͻ�</TD>
				<TD class="input">
					<Input class="codeno" name="VIPCustomer" ondblClick="showCodeList('VIPValue',[this,VIPCustomerName],[0,1]);" onkeyup="showCodeListKey('VIPValue',[this,VIPCustomerName],[0,1]);" ><input class="codename" name="VIPCustomerName" readonly="readonly">
				</TD>
				<TD  class= title>�˱����� </TD>
				<TD  class= input>
					<Input class="codeno" name=UWAuthority ondblclick="showCodeList('uwpopedom',[this,UWAuthorityName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onkeyup="showCodeListKey('uwpopedom',[this,UWAuthorityName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');"><input class=codename name=UWAuthorityName readonly=true>
				</TD>							
		    </TR>
		 <!--  
		    <TR  class= common>					
				<TD  class= title>  �˱�����״̬ </TD>
				<TD  class= input>
				<Input class="codeno" name=UWState value= "" CodeData= "0|^1|δ�˹��˱�^2|�˱��ѻظ�^3|�˱�δ�ظ�^4|�ٱ�δ�ظ�|^5|�ٱ��ѻظ�" ondblClick="showCodeListEx('State',[this,UWStateName],[0,1]);" onkeyup="showCodeListKeyEx('State',[this,UWStateName],[0,1]);"><input class="codename" name="UWStateName" readonly="readonly">
				</TD>				
				<TD  class= title>�ϱ�����</TD>
				<TD  class= input>
					<Input class="codeno" name=UWUpReportType value= "" CodeData= "0|^0|����Լ�˱�^1|�ϱ�^4|�����¼�" ondblClick="showCodeListEx('UpReportType',[this,UWUpReportTypeName],[0,1]);" onkeyup="showCodeListKeyEx('UpReportType',[this,UWUpReportTypeName],[0,1]);"><input class="codename" name="UWUpReportTypeName" readonly="readonly">
				</TD>						   	
		    </TR>
		 -->
		    <!--lzf <TR class="common">
							
				<TD  class= title>Ͷ����</TD>
				<TD  class= input><Input class= common name=AppntName2 ></TD>
				<TD  class= title>������</TD>
				<TD  class= input><Input class= common name=InsurName2 ></TD>
				<TD  class= title>�˱�����״̬</TD>
			    <TD  class= input>
				     <Input class="codeno" name=UWState1 value= "1"  verify ="�˱�����״̬|notnull" ondblClick="showCodeList('uwstatus',[this,UWStateName1],[0,1]);" onkeyup="showCodeListKey('uwstatus',[this,UWStateName1],[0,1]);"><input class="codename" name="UWStateName1" value ="δ�˹��˱�" readonly="readonly" elementtype=nacessary>
			    </TD>
		    </TR>
		    <TR class="common">	    	
				<TD  class= title>�˱�Ա����</TD>
			    <TD class= input><Input class= common name=UserCode onblur="CheckUserCode()" ></TD>	
			    <TD  class= title>�Ƿ���</TD>
			    <TD class= input><Input class= codeno name=EnteraccState2 CodeData= "0|^0|��^1|��" ondblClick="showCodeListEx('EnteraccState',[this,EnteraccStateName2],[0,1]);" onkeyup="showCodeListKeyEx('EnteraccState',[this,EnteraccStateName2],[0,1]);"><input class="codename" name="EnteraccStateName2" readonly="readonly">
			    </TD>	   	
			    <TD  class= title>�Ƿ�Ա����</TD>
			    <TD class= input><Input class= codeno name=OperatorState2 value= "0" CodeData= "0|^0|��^1|��" ondblClick="showCodeListEx('OperatorState',[this,OperatorStateName2],[0,1]);" onkeyup="showCodeListKeyEx('OperatorState',[this,OperatorStateName2],[0,1]);"><input class="codename" name="OperatorStateName2" value= "��" readonly="readonly">
			    </TD>	
			</TR>
			<TR class="common">	    	
			    <TD  class= title>ҵ��Ա�ȼ�</TD>
			    <TD class= input><Input class= codeno name=UWLevel CodeData= "0|^A|A��^B|B��^C|C��^D|D��" ondblClick="showCodeListEx('UWLevel',[this,UWLevelName],[0,1]);" onkeyup="showCodeListKeyEx('UWLevel',[this,UWLevelName],[0,1]);"><input class="codename" name="UWLevelName" readonly="readonly">
			    </TD>	
			     <TD  class= title>���Ż���Ͷ����</TD>
			    <TD class= input><Input class= codeno name=BaiTuanCom value= "" CodeData= "0|^0|��^1|��" ondblClick="showCodeListEx('BaiTuanCom',[this,BaiTuanComName],[0,1]);" onkeyup="showCodeListKeyEx('BaiTuanCom',[this,BaiTuanComName],[0,1]);"><input class="codename" name="BaiTuanComName" value= "" readonly="readonly">
			    </TD>   	
			</TR>
		</Table>
	</DIV>
    
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClickAll();">
     <!---- <INPUT VALUE="���������ѯ" class=cssButton TYPE=button onclick="withdrawQueryClick();"> --> 
     <!-- lzf <TD  class= title> �ܵ���</TD>
      <TD  class= input> <Input class="readonly" readonly name=PolSum  ></TD>
	<!--���������б���Ϣ����-->
	<!-- lzf <DIV id=DivLCContInfo STYLE="display:''">
		<Table>
			<TR>
				<TD class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllPolGrid);">
				</TD>
				<TD class= titleImg>��������</TD>
			</TR>
		</Table>
	</Div>
	<Div  id= "divAllPolGrid" style= "display: ''" align = center>
		<Table  class= common >
			<TR  class=common>
				<TD text-align: left colSpan=1 >
					<span id="spanAllPolGrid" ></span>
			  	</TD>
			</TR>
		</Table>
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();changeColor(bg);">
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();changeColor(bg);">
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();changeColor(bg);">
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();changeColor(bg);">
	</Div>
	<br>

	<!--����Ϊ���˶��в���-->
	<!-- lzf   <INPUT class=cssButton  type=hidden id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();">

	<Table  class= common>
		<TR  class= common>
			<TD  class= title8>ӡˢ��</TD>
			<TD  class= input8><Input class= common name=ContNo1 ></TD>
			<!--
			<TD  class= title>��������</TD>
			<TD  class= input><Input class="coolDatePicker" dateFormat="short"  name=ApproveDate1 ></TD>
			-->
	<!-- lzf <TD  class= title>���ֱ���</TD>
		    <TD  class= input><Input class="codeno" name=RiskCode1 ondblclick="showCodeList('RiskCode',[this,RiskCodeName1],[0,1],null,null,null,1);" onkeyup="showCodeListKey('RiskCode',[this,RiskCodeName1],[0,1]);"><input class="codename" name="RiskCodeName1" readonly="readonly"> </TD>
		    <TD  class= title>ҵ��Ա����</TD>
			<TD  class= input><Input class="code" name="AgentCode1" ondblclick="return queryAgent1();"></TD>				
			</TR>
		<TR  class= common>                 
			<!--
			<TD  class= title>�˱�����</TD>
			<TD  class= input>
			<Input class="codeno" name=UWAuthority1 ondblclick="showCodeList('uwpopedom',[this,UWAuthorityName],[0,1]);" onkeyup="showCodeListKey('uwpopedom',[this,UWAuthorityName],[0,1]);"><input class=codename name=UWAuthorityName readonly=true>
			</TD>
			-->     
	<!-- lzf 	    <TD  class= title>�˱�����״̬</TD>
			<TD  class= input><Input class="codeno" name=UWState2   ondblClick="showCodeList('uwstatus',[this,UWStateName2],[0,1],null,'1 and #1#=#1# and code <>#0#','1');" onkeyup="showCodeListKey('uwstatus',[this,UWStateName2],[0,1],null,'1 and #1#=#1# and code <>#0#','1');"><input class="codename" name="UWStateName2" vreadonly="readonly"  elementtype=nacessary></TD>			
			<TD  class= title>�ϱ�����</TD>
			<!-- <TD  class= input>
				<Input class="codeno" name=UWUpReport value= "" CodeData= "0|^0|����Լ�˱�^1|�ϱ�^4|�����¼�" ondblClick="showCodeListEx('UpReport',[this,UWUpReportName],[0,1]);" onkeyup="showCodeListKeyEx('UpReport',[this,UWUpReportName],[0,1]);"><input class="codename" name="UWUpReportName" readonly="readonly">
			</TD>
			-->
	<!-- lzf 		<TD  class= input>
				<Input class="codeno" name=UWUpReport value= "" CodeData= "0|^1|�ϱ�^4|�����¼�" ondblClick="showCodeListEx('UpReport',[this,UWUpReportName],[0,1]);" onkeyup="showCodeListKeyEx('UpReport',[this,UWUpReportName],[0,1]);"><input class="codename" name="UWUpReportName" readonly="readonly">
			</TD>
			<TD  class= title>�������</TD>
			<TD  class= input>
				<Input class="codeno" name=ManageCom1 ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 readonly=true>
			</TD>

		</TR>
		<TR class="common">
			<TD  class=title>��������</TD>
				<TD  class=input>
					<input class="codeno" name=SaleChnl1 ondblclick="showCodeList('SaleChnl',[this,SaleChnlName1],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName1],[0,1]);"><input class=codename name=SaleChnlName1 readonly=true>
				</TD>  			
			<TD  class= title>Ͷ����</TD>
			<TD  class= input><Input class= common name=AppntName1 ></TD>
			<TD  class= title>������</TD>
			<TD  class= input><Input class= common name=InsurName1 ></TD>
		</TR>
		<TR class="common">
			<TD  class= title>�Ƿ���</TD>
			    <TD class= input><Input class= codeno name=EnteraccState1 CodeData= "0|^0|��^1|��" ondblClick="showCodeListEx('EnteraccState',[this,EnteraccStateName1],[0,1]);" onkeyup="showCodeListKeyEx('EnteraccState',[this,EnteraccStateName1],[0,1]);"><input class="codename" name="EnteraccStateName1" readonly="readonly">
			    </TD>	   	
			    <TD  class= title>�Ƿ�Ա����</TD>
			    <TD class= input><Input class= codeno name=OperatorState1 CodeData= "0|^0|��^1|��" ondblClick="showCodeListEx('OperatorState',[this,OperatorStateName1],[0,1]);" onkeyup="showCodeListKeyEx('OperatorState',[this,OperatorStateName1],[0,1]);"><input class="codename" name="OperatorStateName1" readonly="readonly">
			    </TD>
				
			</TR>
			<TR class="common">	    	
			    <TD  class= title>ҵ��Ա�ȼ�</TD>
			    <TD class= input><Input class= codeno name=UWLevel1 CodeData= "0|^A|A��^B|B��^C|C��^D|D��" ondblClick="showCodeListEx('UWLevel1',[this,UWLevel1Name],[0,1]);" onkeyup="showCodeListKeyEx('UWLevel1',[this,UWLevel1Name],[0,1]);"><input class="codename" name="UWLevel1Name" readonly="readonly">
			    </TD>	
			     <TD  class= title>���Ż���Ͷ����</TD>
			    <TD class= input><Input class= codeno name=BaiTuanCom1 value= "" CodeData= "0|^0|��^1|��" ondblClick="showCodeListEx('BaiTuanCom1',[this,BaiTuanComName1],[0,1]);" onkeyup="showCodeListKeyEx('BaiTuanCom1',[this,BaiTuanComName1],[0,1]);"><input class="codename" name="BaiTuanComName1" value= "" readonly="readonly">
			    </TD>      	
			</TR>			
	</Table>

 	<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="NeweasyQueryClick();">
	<DIV id=DivLCContInfo STYLE="display:''">
		<Table>
			<TR>
		    	<TD class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
				</TD>
				<TD class= titleImg>���˱�Ͷ����</TD>
			</TR>
		</Table>
	</Div>
	<Div  id= "divPolGrid" style= "display: " align = center>
		<Table  class= common >
			<TR  class=common>
				<TD text-align: left colSpan=1 >
					<span id="spanPolGrid" ></span>
			  	</TD>
			</TR>
		</Table>
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();changeColor(bg1);">
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();changeColor(bg1);">
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();changeColor(bg1);">
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();changeColor(bg1);">
	</Div>

	 <INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"> -->
	<Table>
		 <Input type=hidden name="ApplyType" id="ApplyType">
		 <Input type=hidden name="MissionID" id="MissionID">
		 <Input type=hidden name="SubMissionID" id="SubMissionID">
		 <Input type=hidden name="ActivityID" id="ActivityID">
		 <Input type=hidden name="Com" id="Com">
		 <Input type=hidden name="Result" id="Result">
		 <Input type=hidden name="PrtNo" id="PrtNo">
		 <Input type=hidden name="Type" id="Type">
	     <Input type=hidden name="priva" id="priva"><!--add by yaory-->
	     <Input type=hidden name="UWAuthority" id="UWAuthority"><!-- �˱����� 2013-03-28-->
	     <Input type=hidden name="UwState" id="UwState"><!-- �˱�״̬ 2013-03-28 -->
	     <Input type=hidden name="AgentCode" id="AgentCode">
	     <Input type=hidden name="ManageCom" id="ManageCom">
	     <Input type=hidden name="UserCode" id="UserCode">
	</Table>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>
</body>
</html>
