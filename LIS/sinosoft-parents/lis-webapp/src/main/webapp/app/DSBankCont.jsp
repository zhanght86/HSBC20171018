  	<!------############# FamilyType:0-������1-��ͥ�� #######################--------->

<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
BeginOfCont tBeginOfCont = new BeginOfCont();
String tPrtNo=request.getParameter("prtNo");
String tInputTime=request.getParameter("InputTime");
String tInputNo="";
if(tInputTime.equals("0")){
	tInputNo="1";
}
if(tInputTime.equals("1")){
	tInputNo="2";
}
if(tInputTime.equals("2")){
	tInputNo="3";
}
String tSql="Select * from lbpocont where contno='"+tPrtNo+"' and inputNo='"+tInputNo+"'";
ExeSQL tAgentExeSQL = new ExeSQL();
SSRS tAgentSSRS = new SSRS();
tAgentSSRS = tAgentExeSQL.execSQL(tSql);
if (tAgentSSRS == null || tAgentSSRS.getMaxRow() == 0) {
	if(!tBeginOfCont.submitData(tPrtNo,tGI,tInputTime)){
%>
<script language="javascript">
alert("��Ϣ��ʼ��ʧ�ܣ�");
parent.window.close=null;
//window.close();
</script>
<%
	}
}
%>


<script language="javascript">
	//alert(14);
	//�ӷ�������ȡ������:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	
	var prtNo = "<%=request.getParameter("prtNo")%>";
	//alert("1tMissionID:"+tMissionID);
	//alert("2tSubMissionID:"+tSubMissionID);
	//alert("3prtNo:"+prtNo);
	
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	//alert("4ActivityID:"+ActivityID);
	var NoType = "<%=request.getParameter("NoType")%>";
	//alert("5NoType:"+NoType);
	
	var ManageCom = "<%=tGI.ManageCom%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	
	//alert("6ManageCom:"+ManageCom);
	//alert("7ContNo:"+ContNo);
	var ContType = 1;
	//alert("8ContType:"+ContType);
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";
	var InputTime = "";
		InputTime = <%=request.getParameter("InputTime")%>;
	var InputNo = InputTime+1;
	var ScanType = "<%=request.getParameter("scantype")%>";
	//alert("9AppntNo :"+AppntNo);
	//alert("10AppntName :"+AppntName);
	//alert("11EdorType :"+EdorType);
	//alert("12ScanType :"+ScanType);
	//alert("13InputTime: "+InputTime);
</script>


<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
    
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <SCRIPT src="../common/javascript/VerifyWorkFlow.js"></SCRIPT>
  <script src="ProposalAutoMove3.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <%
//if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
//}
%>

  
<%@include file="DSBankContInit.jsp"%>

<script src="DSBankCont.js"></script>
</head>
  
<body  onload="initForm();" >
<form action="./DSContSave.jsp" method=post name=fm id="fm" target="fraSubmit">
<!-- ��ͬ��Ϣ -->
<div id="ContMessage" style="display:''" >
  <div class="maxbox1">
	<table class=common >   
		<tr >
			<td class=titleImg >ӡˢ��: <input class="common"  readonly name=PrtNo id="PrtNo">
				<input readonly type=hidden name=ProposalContNo id="ProposalContNo">
			</td>
		</tr>
	</table>
  </div>
</div>
<!-- Ͷ������Ϣ -->
<div id="DivComplexAppnt" style="display:''">
	<jsp:include page="DSBankAppnt.jsp"/>
</div>

<div  id= "divTempFeeInput" style= "display:none">
	<Table class=common>
		<tr>
			<td text-align: left colSpan=1>
				<span id="spanInsuredGrid" ></span>
			</td>
		</tr>
	</Table>
</div>
<hr class="line">
<!-- ��������Ϣ -->
<jsp:include page="DSBankInsured.jsp"/>

<table  class=common align=left>
	<tr align=left>
    	<td class=button align=left>
    		<!-- <input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return saveInsured(1);"> -->
        <a href="javascript:void(0)" class=button name="addbutton" onClick="return saveInsured(1);">��  ��</a>
    	</td>
    	<!-- <td class=button width="10%" align=right>
    		<input class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateInsured(1);">
    	</td> -->
	</tr>
</table>
<div  id= "divBnfInput" style= "display:''">
	<Table class=common>
		<tr><td class= titleImg>����������</td></tr>
		<tr>
			<td text-align: left colSpan=1>
				<span id="spanBnfGrid" ></span>
			</td>
		</tr>
	</Table>
</div>

    
<!-- ������������Ϣ���� -->
<div id="DivLCPol" STYLE="display:''">
	<table class="common">
		<tr>
			<td class= titleImg>
				��������������Ϣ:
			</td>
		</tr>
    	<!-- <tr>
    		<td class=title>�����������ͣ�<Input class="code" name="SequenceNo2"  value="1" CodeData="0|^1|���������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo2', [this],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo2', [this],[0,1]);">
    	</tr> -->
        <tr  class= common>
           	<td style="text-align:left" colSpan=1>
           		<span id="spanPolGrid" ></span>
           	</td>
        </tr>
    </table>
    <table  class=common align=left>
		<tr align=left>
	    	<td class=button align=left>
	    		<!-- <input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return saveRisk(2);"> -->
          <a href="javascript:void(0)" class=button name="addbutton" onClick="return saveRisk(2);">��  ��</a>
	    	</td>
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateRisk(2);">
	    	</td> -->
		</tr>
	</table>
  <br>
  <br>
  <br>
  <div class="maxbox1">
    <table class=common>
      	<tr>
      		<td class=title>���շѺϼ�(����Ҵ�д)</td>
      		<td class=input><input verifyorder="3" class="common wid"  name="Password" id="Password"></td>
      		<td class=title>(Сд)��</td>
      		<td class=input><input verifyorder="3" class="common wid" name="SumPrem" id="SumPrem">Ԫ</td>
      	</tr>
      	<!-- 
      	<tr>
      		<td class=title>׷�ӱ��շ�(����Ҵ�д)</td>
      		<td class=input><input verifyorder="3" class=common name="ApproveCode"></td>
      		<td class=title>��</td>
      		<td class=input><input verifyorder="3" class=common name="ApproveTime">Ԫ</td>
      	</tr> 
      	
      	<TR class="common">
	        <TD class=title>���ڽ���</TD>
		    <TD class=input><input verifyorder="3" class="code" name="PayMode" CodeData="0|^0|����ת��^1|�˹���ȡ^2|֧Ʊ^3|����"
            								    ondblClick="showCodeListEx('ForPayMode',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForPayMode',[this],[0,1]);">
		    </TD>
		    <td class=title>���ڽɷ�</td>
		    <TD class=input><input verifyorder="3" class="code" name="PayLocation" CodeData="0|^0|����ת��^1|�˹���ȡ^2|�ͻ��Խ�^3|����"
            								    ondblClick="showCodeListEx('ForPayLocation',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForPayLocation',[this],[0,1]);">
		    </TD>
	    </TR>
	    <tr>
	    </tr>
	    <tr>
	    	<td class=title>���շ��Զ��潻</td>
	    	<td class=input><input verifyorder="3" class="code" name="AutoPayFlag" CodeData="0|^0|��^1|��"
            								    ondblClick="showCodeListEx('ForAutoPayFlag',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForAutoPayFlag',[this],[0,1]);">
            </td>
	    </tr>
      -->
      <tr>
      		<td class=title>���ѷ�ʽ</td>
      		<td class=input><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="3" class="code" name=PayIntv id="PayIntv"
            								    onClick="return showCodeList('dspayintv',[this],[0,1]);"
                                ondblClick="return showCodeList('dspayintv',[this],[0,1]);"
            								    onkeyup="return showCodeListKey('dspayintv',[this],[0,1]);">
            <!-- <td class=title>�罻���Ѵ���ʽ</td>
            <td class=input><input verifyorder="3" class="code" name=OutPayFlag CodeData="0|^1|�˷�^2|�ֽ����ڱ���"
            								    ondblClick="showCodeListEx('ForOutPayFlag',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForOutPayFlag',[this],[0,1]);"> -->
     		<td class=title>��ȡ��ʽ</td>
    		<td class=input>
      			<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="3" NAME=StandbyFlag3 id="StandbyFlag3" VALUE="" CLASS=code  onClick="return showCodeList('dsgetdutykind',[this],[0]);" ondblClick="return showCodeList('dsgetdutykind',[this],[0]);" onKeyUp="return showCodeListKey('dsgetdutykind',[this],[0]);" >
    		</td>
    		<td class=title>���ʼ��ȡ����</td>
    		<td class=input><input verifyorder="3" class="common wid" name=GetYear id="GetYear"><input type=hidden name=GetYearFlag id="GetYearFlag" value="A">����</td>
     </tr>
      </table>
      </div>
</div>
<div id="DivLCPol2" STYLE="display:'none'">	
	<hr class="line">
	<table class=common>
		<tr class=common>
      <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
      <td class= titleImg>���汣�ս������ȡ</td>
    </tr>
  </table>
  <div class="maxbox1">
  <div  id= "divFCDay" style= "display: ''"> 
  <table class=common>
    	<tr class=common>
    		<td class=title>���汣�ս�/�����ʽ</td>
    		<td class=input><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="3" class="code" name="LiveGetMode" id="LiveGetMode" CodeData="0|^1|�ۻ���Ϣ^2|�ֽ���ȡ^3|�ֽ����շ�^4|����"
            								    onClick="showCodeListEx('ForLiveGetMode',[this],[0,1]);"
                                ondblClick="showCodeListEx('ForLiveGetMode',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForLiveGetMode',[this],[0,1]);">
          	</td>
    		<td class=title>������ȡ��ʽ</td>
            <td class=input><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="3" class="code" name="BonusGetMode" id="BonusGetMode" CodeData="0|^1|�ۻ���Ϣ^2|�ֽ���ȡ^3|�ֽ����շ�^4|���������^5|����"
            								    onClick="showCodeListEx('ForBonusGetMode',[this],[0,1]);"
                                ondblClick="showCodeListEx('ForBonusGetMode',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForBonusGetMode',[this],[0,1]);">
            </td>
        <td class=title></td>
        <td class=input></td>
    	</tr>
    	<tr class=common>	
    		
    		<td class=title>��ȡ����</td>
    		<td class=input><input verifyorder="3" class="common wid"  name=GetLimit id="GetLimit"></td>
    		
    	</tr>
    </table>
  </div>
  </div>
</div>
    <table  class=common align=left>
		<tr align=left>
	    	<td class=button align=left>
	    		<!-- <input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return saveGet(3);"> -->
          <a href="javascript:void(0)" name="addbutton" class=button onClick="return saveGet(3);">��  ��</a>
	    	</td>
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateGet(3);">
	    	</td> -->
		</tr>
	</table>
    <!-- ��֪��Ϣ -->
    <div id="divLCImpart1" style="display:''">
      <table class="common">
      	<tr>
      		<td class= titleImg> ������֪</td>
     	  </tr>
        <tr class="common">
            <td style="text-align:left" colSpan="1">
                <span id="spanImpartGrid">
                </span>
            </td>
        </tr>
      </table>
    	<table class=common>
    		<tr class=common>
    			<td>��֪˵������ע��</td>
    		</tr>
    		<tr class=common>
    		  <TD  class= input>
        		<input verifyorder="4" name="ImpartRemark" id="ImpartRemark" class="common wid">
        	</TD>
    		</tr>
    	</table>
    </div>
    <hr class="line">
    <table class=common>
    	<tr class=common>
    		<td class=titleImg>Ͷ���ˡ�����������������Ȩ</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <table class=common>
    	<tr class=common>
    		<td class=title>��������(Ͷ����)</td>
	    	<td class=input><input verifyorder="4" class="common wid"  name=AccName id="AccName"></td>
	    	<td class=title>������(��)</td>
	    	<td><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="4" NAME=BankCode id="BankCode" VALUE="" CLASS="code" MAXLENGTH=20   onclick="return showCodeList('bank',[this,null],[0,1]);" onDblClick="return showCodeList('bank',[this,null],[0,1]);" onKeyUp="return showCodeListKey('bank',[this,null],[0,1]);" ></td>
    		<td class=title>�����ʺ�</td>
	    	<td class=input><input verifyorder="4" class="common wid" name=BankAccNo id="BankAccNo"></td>
    	</tr>
    	<tr class=common>
    		<td class=title>Ͷ����ǩ��</td>
    		<td class=input><input verifyorder="4" class="common wid" name=AppSignName id="AppSignName"></td>
    		<td class=title>��������/�����໤��ǩ��</td>
    		<td class=input><input verifyorder="4" class="common wid" name=InsSignName2 id="InsSignName2"></td>
    	</tr>
		  <tr class=common>
			  <td class=title>Ͷ����������</td>
			  <td class=input><input verifyorder="4" class="common wid" name=PolApplyDate id="PolApplyDate"></td>
		  </tr>
    </table>
    </div>
    <hr class="line">
    <table class=common>
    	<tr class=common>
    		<td class=titleImg>����������д��</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <table class=common>
    	<tr>
    		<td class=title>������������</td>
        <td class=input><input verifyorder="4" class="common wid" name=AgentComName id="AgentComName"></td>
        <td class=title>�����������</td>
        <td class=input><input verifyorder="4" class="common wid" name=AgentCom id="AgentCom"></td>
        <td class=title></td>
        <td class=input></td>
    	</tr>
    </table>
    </div>
    <table class=common>
    	<tr>
    		<td  class= titleImg>���չ�˾��д��</td>
    	</tr>
    	<tr>
    		<td class=input><input verifyorder="4" class="common wid" name=ManageCom id="ManageCom" type="hidden" readonly></td>
    		<td class=input><input verifyorder="4" class="common wid" name=ScanCom id="ScanCom" type="hidden"  readonly></td>
    	</tr>
    </table>
    <div class="maxbox1">
    <table class=common>
    	<tr>
    		<td class=title>��������</td>
    		<td class=input><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="4" class="code" name="SaleChnl" id="SaleChnl"
            								    onClick="return showCodeList('SaleChnl',[this],[0,1]);"
                                ondblClick="return showCodeList('SaleChnl',[this],[0,1]);"
            								    onkeyup="return showCodeListKey('SaleChnl',[this],[0,1]);">
            </td>
    		<td class=title>�ͻ�����</td>
    		<td class=input><input verifyorder="4" class="common wid"  name=Handler id="Handler"></td>
    		<td class=title>ҵ���</td>
    		<td class=input><input verifyorder="4" class="common wid" name=AgentCode id="AgentCode"></td>
    	</tr>
    	<tr>
    		<td class=title>����Աǩ��</td>
    		<td class=input><input verifyorder="4" class="common wid" name=FirstTrialOperator id="FirstTrialOperator"></td>
    		<td class=title>����</td>
    		<td class=input><input verifyorder="4" class="common wid" name=FirstTrialDate id="FirstTrialDate"></td>
    	</tr>
    	<tr>
    		<td class=title>ɨ��Աǩ��</td>
    		<td class=input><input verifyorder="4" class="common wid" name=UWOperator id="UWOperator"></td>
    		<td class=title>����</td>
    		<td class=input><input verifyorder="4" class="common wid" name=UWDate id="UWDate"></td>
    	</tr>
    </table>
    </div>
    <div id="divLCImpart2" style="display:'none'">
    <table>
    	<tr>
    		<td style="text-align:left" colSpan="1">
                      <span id="spanAgentImpartGrid"></span>
            </td>
    	</tr>
    </table>
    </div>
    <hr class="line">
    <!-- 
    <table class=common>
    	<tr>
    		<td class=title>ҵ��Աǩ��</td>
    		<td class=input><input verifyorder="4" class=common name=SignAgentName></td>
    		<td class=title>����</td>
    		<td class=input><input verifyorder="4" class=common name=AgentSignDate></td>
    	</tr>
    </table> -->
    <table class=common>
    	
    </table>
    <table  class=common align=left>
		<tr align=left>
	    	<td class=button align=left>
	    		<!-- <input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return saveImpart(4);"> -->
          <a href="javascript:void(0)" class=button name="addbutton" onClick="return saveImpart(4);">��  ��</a>
          <a href="javascript:void(0)" class=button name="Confirm" onClick="inputConfirm(0);">¼�����</a>
	    	</td>
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateImpart(4);">
	    	</td> -->
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="Confirm" VALUE="¼�����"  TYPE=button onclick="inputConfirm(0);">
	    	</td> -->
		</tr>
	</table>    
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               ���ؿؼ�λ��                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <div  id= "HiddenValue" style="display:none;float: right">
      <input type="hidden" id="fmAction" name="fmAction">
      <input type="hidden" id="contFillNo" name="contFillNo">
    	<!-- ������������� -->
	  <input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
	  <input type="hidden" name="MissionID" id="MissionID" value= "">
      <input type="hidden" name="SubMissionID" id="SubMissionID" value= "">
      <input type="hidden" name="ActivityID" id="ActivityID" value= "">
      <input type="hidden" name="NoType" id="NoType" value= "">
      <input type="hidden" name="PrtNo2" id="PrtNo2" value= "">
      <input type="hidden" name="FamilyType" id="FamilyType" value= "0">
      <input type="hidden" name="FamilyID" id="FamilyID" value= "">
      <input type="hidden" name="PolType"  id="PolType" value= "0">
      <input type="hidden" name="CardFlag"  id="CardFlag" value= "0">
      <input type="hidden" name="SequenceNo" id="SequenceNo" value= "1">
      <input type="hidden" name="ContNo" id="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" id="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" id="SelPolNo" value="">
      <input type="hidden" name="BQFlag" id="BQFlag">  
      <input type='hidden' name="MakeDate" id="MakeDate">
      <input type='hidden' name="MakeTime" id="MakeTime">
      <input type='hidden' name="InputNo" id="InputNo" value="">
      <input type='hidden' name="InputTime" id="InputTime" value="">
      <input type='hidden' id="ContType" name="ContType" value="4">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
	  <input type='hidden' id='DSFlag' name="DSFlag" value='1'>
    </div>          
</form>
<br>
<br>
<br>
<br>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
