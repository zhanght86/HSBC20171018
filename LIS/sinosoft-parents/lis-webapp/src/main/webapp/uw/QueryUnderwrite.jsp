<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QueryUnderwrite.jsp  
//�����ܣ����˺˱���Ϣ��ѯ
//�������ڣ�2006-09-20 11:32
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%
	String tPolNo = "";
	tPolNo = request.getParameter("PolNo");
	session.putValue("PolNo",tPolNo);
	String tRiskType = "";
	tRiskType = request.getParameter("RiskType");
	loggerDebug("QueryUnderwrite","***RiskType: "+tRiskType);
	
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
	
%>
<script>
  var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="QueryUnderwrite.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QueryUnderwriteInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./QueryUnderwriteCho.jsp">
    <!-- ������ѯ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
			</td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
    </table>
    <div class="maxbox">
    <Div  id= "divFCDay" style= "display: ''">
    	<table  class= common >
      		<TR class =common >
         		<TD  class= title5>  ӡˢ��  </TD>
         		<TD  class= input5> <Input class="common wid" id="QPrtNo" name=QPrtNo> </TD>
          		<TD  class= title5>  ������  </TD>
         		<TD  class= input5> <Input class="common wid" id="QContNo" name=QContNo> </TD>
         	</TR>
       		<TR class = common>
         		<TD  class= title5>  ����״̬  </TD>
         		<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" readonly id="QState" name=QState value= "0" verify ="����״̬|notnull" CodeData= "0|^0|�µ�״̬^1|��ǩ������^4|����������" onMouseDown="showCodeListEx('StateQ',[this,''],[0,1]);" ondblClick="showCodeListEx('StateQ',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('StateQ',[this,''],[0,1]);"> </TD>
         		<TD  class= title5>  Ͷ���˿ͻ���  </TD>
         		<TD  class= input5>   <Input class="common wid" id="QAppntNo" name=QAppntNo value= "">   </TD>
         	</TR>
       		<TR class = common>
         		<TD  class= title5>  Ͷ��������  </TD>
         		<TD  class= input5>   <Input class="common wid" id="QAppntName" name=QAppntName value= "">   </TD>
         		<TD  class= title5>  �����˿ͻ���  </TD>
         		<TD  class= input5> <Input class="common wid" id="QInsuredNo" name=QInsuredNo> </TD>
       		</TR>
      		<TR class =common>
		        <TD  class= title5>  ����������  </TD>
		        <TD  class= input5> <Input class="common wid" id="QInsuredName" name=QInsuredName> </TD>
		        <TD class="title5">�����˱��� </td>
				<TD class="input5" COLSPAN="1"><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" NAME="QAgentCode" VALUE="" MAXLENGTH="10" CLASS="codeno" verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" onMouseDown="return queryAgent();" ondblclick="return queryAgent();" ><input id="QAgentName" name=QAgentName class='codename' readonly=true  elementtype=nacessary ></TD>
			</TR>
      		<TR class =common>
				<TD  class= title5>���ֱ���</TD>
				<TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" id="QRiskCode" name=QRiskCode onMouseDown="showCodeList('RiskCode',[this,QRiskCodeName],[0,1],null,null,null,1);" ondblclick="showCodeList('RiskCode',[this,QRiskCodeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('RiskCode',[this,QRiskCodeName],[0,1]);"><input class="codename" id="QRiskCodeName" name="QRiskCodeName" readonly="readonly"> </TD>
		        <TD class=title5>�������</TD>
				<TD class=input5>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno id="QManageCom" name=QManageCom verify="�������|code:station" onMouseDown="return showCodeList('station',[this,QManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,QManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,QManageComName],[0,1]);"><input class=codename id="QManageComName" name=QManageComName readonly=true elementtype=nacessary></TD>
			</TR>
    	</table>
	</Div>
	</div>
    <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">  
    <INPUT type= "hidden" id="Operator" name= "Operator" value= ""> 
    <!-- ������ѯ������֣��б� -->
    <table>
    	<tr>
        	<td class=common>
    			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
			</td>
    		<td class= titleImg>������ѯ���</td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
        <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 		
        <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
        <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">      
    </Div>    
   	<Div  id= "divMain" style= "display: none">
    	<hr class="line">
    	<!-- �˱� -->
    	<table class= common border=0 width=100%>
    	 	<tr>
	        	<td class= titleImg align= center>�˱���Ϣ��</td>
	     	</tr>
    	</table>
	    <!--��ͬ��Ϣ-->
		<DIV id="DivLCContButton" STYLE="display:''">
			<table id="table1">
				<tr>
					<td class=common>
	    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCCont);">
					</td>
					<td class="titleImg">��ͬ��Ϣ</td>
				</tr>
			</table>
		</DIV>
		<div class="maxbox">
		<div id="DivLCCont" STYLE="display:''">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title5" nowrap>ӡˢ��</td>
					<td CLASS="input5" COLSPAN="1"><input id="PrtNo" NAME="PrtNo" VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14"></td>
					<td CLASS="title5" nowrap>�������</td>
					<td CLASS="input5" COLSPAN="1"><input id="ManageCom" NAME="ManageCom"  MAXLENGTH="10" CLASS="readonly wid" readonly></td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title5" nowrap>��������</td>
					<td CLASS="input5" COLSPAN="1"><input id="SaleChnl" NAME="SaleChnl" CLASS="readonly wid" readonly MAXLENGTH="2"></td>
					<td CLASS="title5">ҵ��Ա����</td>
					<td CLASS="input5" COLSPAN="1"><input id="AgentCode" NAME="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly></td>
				</tr>
				<tr CLASS="common">
		    		<td CLASS="title5">Ӧ������</td>
					<td CLASS="input5" COLSPAN="1"><input id="ShouldPayPrem" NAME="ShouldPayPrem" CLASS="readonly wid" readonly MAXLENGTH="255"></td>
		    		<td CLASS="title5">�����</td>
					<td CLASS="input5" COLSPAN="1"><input id="OldPayPrem" NAME="OldPayPrem" CLASS="readonly wid" readonly MAXLENGTH="255"></td>
		    	</tr>
		    	<tr CLASS="common">
		     		<td CLASS="title5">�߶����ʶ</td>
					<td CLASS="input5" COLSPAN="1"><input id="HighAmntFlag" NAME="HighAmntFlag" CLASS="readonly wid" readonly MAXLENGTH="1"></td>
					<td CLASS="title5">��ע��Ϣ</td>
					<td CLASS="input5" COLSPAN="1"><input id="Remark" NAME="Remark" CLASS="readonly wid" readonly MAXLENGTH="255"></td>
		    	</tr>
				<input id="AgentGroup" NAME="AgentGroup" type=hidden CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="12">
				<input id="AgentCode1" NAME="AgentCode1" type=hidden MAXLENGTH="10" CLASS="readonly wid" readonly>
				<input id="AgentCom" NAME="AgentCom" type=hidden CLASS="readonly wid" readonly>
				<input id="AgentType" NAME="AgentType" type=hidden CLASS="readonly wid" readonly>
			</table>
		</div>
		</div>
		<div id ="DivRevise"  STYLE="display:none">
			<table class="common">
			    <tr class="common">
			    	<TD  class= title5 >����ԭ��</TD>
					<TD  class= input5 COLSPAN="1">
						<Input class="readonly wid" id="Revise" name=Revise readonly="readonly"></TD>
					<TD  class= title5><Input type='hidden' ></TD>
					<TD  class= input5><Input type='hidden' ></TD>
			  	</tr>
			</table>
	    </div>
	    <div id ="DivUpper"  STYLE="display:none">
			<table class="common">
			    <tr class="common">
			    	<TD  class= title5 >�ϱ�ԭ��</TD>
					<TD  class= input5 COLSPAN="1"><Input class="readonly wid" id="UpperReason" name=UpperReason readonly="readonly"></TD>
					<td CLASS="title5" >�ϱ��˱�ʦ����</td>
					<td CLASS="input5" COLSPAN="1"><input id="UpperUwUser" NAME="UpperUwUser" CLASS="readonly wid" readonly ></td>
			  	</tr>
			</table>
	    </div>
	    <br>
		<INPUT VALUE="  Ӱ�����ϲ�ѯ  " class=button TYPE=button name="uwButton2" onclick="ScanQuery();">
		<INPUT VALUE="Ͷ������������ѯ" class=button TYPE=button name="uwButton6" onclick="QueryRecord()">
		<INPUT VALUE="�Զ��˱���ʾ��Ϣ" class=button TYPE=button name="Button4" onclick="showNewUWSub();">
		<INPUT VALUE="���񽻷���Ϣ��ѯ" class=button TYPE=button name="uwButton5" onclick="showTempFee();"> 
		<hr class="line">
		<DIV id="DivLCAppntIndButton" STYLE="display:''">
		<!-- Ͷ������Ϣ���� -->
			<table>
				<tr>
					<td class=common>
    					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
					</td>
					<td class= titleImg>Ͷ������Ϣ</td>
				</tr>
			</table>
		</DIV>
		<div class="maxbox">
		<DIV id="DivLCAppntInd" STYLE="display:''">
		 	<table  class= common>
		        <tr  class= common>
		          	<td  class= title5>����</TD>
		          	<td  class= input5><Input CLASS="readonly wid" readonly id="AppntName" name="AppntName" value=""></TD>
		          	<td  class= title5>�Ա�</TD>
		          	<td  class= input5><Input id="AppntSex" name=AppntSex CLASS="readonly wid" type="hidden"><Input id="AppntSexName" name=AppntSexName CLASS="readonly wid" ></TD>
		        </TR>
		        <tr  class= common>
		          	<td  class= title5>����</TD>
		          	<td  class= input5><input CLASS="readonly wid" readonly id="AppntBirthday" name="AppntBirthday"></TD>
		            <Input CLASS="readonly wid" readonly type=hidden id="AppntNo" name="AppntNo" value="">
		            <Input CLASS="readonly wid" readonly  type=hidden id="AddressNo" name="AddressNo">
		          	<td  class= title5>ְҵ</TD>
		          	<td  class= input5>
		          		<input CLASS="readonly wid" readonly id="OccupationCode"��name="OccupationCode" type="hidden">
		          		<input CLASS="readonly wid" readonly��id="OccupationCodeName" name="OccupationCodeName">
		          	</TD>
		        </TR>
		        <tr  class= common>
		          	<td  class= title5>ְҵ���</TD>
		          	<td  class= input5>
		          		<input CLASS="readonly wid" readonly id="OccupationType" name="OccupationType" type="hidden">
		          		<input CLASS="readonly wid" readonly id="OccupationTypeName" name="OccupationTypeName">
		          	</TD>
		         	<td  class= title5>������</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="income" name=income ></TD>
		        </TR>
		        <tr  class= common>
		        	<td  class= title5>���</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="Stature" name=Stature ></TD>
		        	<td  class= title5>����</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="Weight" name=Weight ></TD>
	           	</TR>
		        <tr  class= common>
	          		<td  class= title5>BMIֵ</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="BMI" name=BMI ></TD>
		        	<td  class= title5>����</TD>
		          	<td  class= input5>
		          		<input CLASS="readonly wid" readonly id="NativePlace" name="NativePlace" type="hidden">
		          		<input CLASS="readonly wid" readonly id="NativePlaceName" name="NativePlaceName">
		          	</TD>
		        </TR>
		        <tr  class= common>
		        	<td  class= title5>VIP���</TD>
	          		<td  class= input5>
	            		<Input class="readonly wid" readonly id="VIPValue" name=VIPValue type="hidden">
	            		<Input class="readonly wid" readonly id="VIPValueName" name=VIPValueName >
	          		</TD>
		        	<td  class="title5" nowrap>���������</TD>
	         	 	<td  class= input5>
	            		<Input class="readonly wid" readonly id="BlacklistFlag" name=BlacklistFlag type="hidden">
	            		<Input class="readonly wid" readonly id="BlacklistFlagName" name=BlacklistFlagName >
	          		</TD>
		        </TR>
		        <tr  class= common>
		        	<td  class= title5>�ۼ����ձ���</TD>
		          	<td  class= input5><input CLASS="readonly wid" readonly id="AppntSumLifeAmnt" name="AppntSumLifeAmnt" ></TD>
		        	<td  class= title5>�ۼ��ش󼲲�����</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="AppntSumHealthAmnt" name=AppntSumHealthAmnt ></TD>
	          	</TR>
		        <tr  class= common>
		         	<td  class="title5" nowrap>�ۼ�ҽ���ձ���</TD>
	         	 	<td  class= input5><Input class="readonly wid" readonly id="AppntMedicalAmnt" name=AppntMedicalAmnt ></TD>
		      		<td  class= title5>�ۼ������ձ���</TD>
		          	<td  class= input5>
		          		<input CLASS="readonly wid" readonly id="AppntAccidentAmnt" name="AppntAccidentAmnt" ></TD>
		        </TR>
		        <tr  class= common>
		        	<td  class= title5>�ۼƷ��ձ���</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="AppntSumAmnt" name=AppntSumAmnt ></TD>
	          		<td  class= title5>�ۼƱ���</TD>
	          		<td  class= input5><Input class="readonly wid" readonly id="SumPrem" name=SumPrem ></TD>
		        </tr>
		    </table>
		</DIV>
		</div>	
		<br>
		<INPUT VALUE=" Ͷ���˽�����֪��ѯ " class=cssButton TYPE=button name="uwButton9" onclick="queryHealthImpart()"> 
		<INPUT VALUE=" Ͷ���˼���Ͷ�����ϲ�ѯ " class=cssButton TYPE=button name="uwButton30" onclick="showApp(1)"> 
		<hr class="line">
	  	<table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
	    		</td>
	    		<td class= titleImg>��������Ϣ</td>
	    	</tr>
	    </table>
	    <Div  id= "divLCPol2" style= "display: ''" >
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanPolAddGrid">
	  					</span>
	  			  	</td>
	  			</tr>
	    	</table>	    
		    <hr class="line">
	     <!--��������Ϣ��ϸ-->
	    	<jsp:include page="ManuUWInsuredShow.jsp"/>  
	    </Div>  
		<hr class="line">
	</Div>
	<div id="divUWButton1" style="display:''">
	<hr class="line">
		<div>
         <input value=" ��ѯ����� "  class="cssButton" type="button" name=uwButton21 onclick="queryHealthReportResult();">
          <input value=" ��ѯ������� "  class="cssButton" type="button" name=uwButton22 onclick="queryRReportResult();">
          <input value="  �������ѯ  " class=cssButton type=button name='uwButton4' onclick="QuestQuery();">
          <input value="�˱��ȴ���ѯ" class=cssButton name= ShowQueryInfoUW type=button onclick="queryReason();">
          <input value=" �ٱ��ظ���Ϣ "      class=cssButton type=button name="uwButton20" onclick="showUpReportReply();" width="200">
          <input value="���������ѯ" class=cssButton name= ShowQueryInfoQuest type=button onclick="QuestMistakeQuery();">
        </div> 
        <br>
        <div>
          <input value="   �ӷѲ�ѯ   " class=cssButton name= ShowQueryInfoAddFee type=button onclick="RiskAddFeeQuery();">
          <input value="   ��Լ��ѯ   " class=cssButton name= ShowQueryInfoSpec type=button onclick="SpecQuery();">
          <input value="���ռƻ�������۲�ѯ" class=cssButton name= ShowQueryInfoChange type=button  onclick="showChangePlan();">
          <input value="������Ч�ղ�ѯ" class=cssButton type=button onclick="ChangeCvalidate();">
          <INPUT VALUE="  �˱����۲�ѯ  " class=cssButton TYPE=button name="uwButton7" onclick="UWQuery()">
        </div>
		<br>
		<div>
	        <input VALUE="�˱���������"  class=cssButton name="ReportButton" TYPE=button onclick="showUWReport();">
	        <input value="���±���ѯ" class=cssButton id="NotePadButton" type=button onclick="showNotePad();" width="200">      
	        <input value="�ͻ�Ʒ�ʹ����ѯ" class=cssButton type=button onclick="CustomerQuality();">
	        <input value="���ҽԺƷ�ʹ����ѯ" class=cssButton type=button onclick="HospitalQuality();"> 
	    </div>  
		<br>
		<div>
          <input value="    �����ѯ    " class=cssButton name= ShowQueryInfoClaim type=button onclick="queryClaim()" width="200">
          <input value="    ��ȫ��ѯ    " class=cssButton name= ShowQueryInfoEdor type=button onclick="queryEdor();" width="200">
          <input value=" �����Ŀ��ѯ   " class=cssButton name= ShowQueryInfoPe type=button onclick="QueryPe();" width="200">
          <!--INPUT VALUE="Ͷ������ϸ��Ϣ" class=cssButton TYPE=button onclick="showPolDetail();"--> 
          <!--INPUT VALUE="�����˼���Ͷ����Ϣ" class=cssButton  name= showAppInfo TYPE=button onclick="showApp();"--> 
          <!--INPUT VALUE="�����˱���¼" class=cssButton TYPE=button onclick="showOldUWSub();"-->
          <!--INPUT VALUE="���պ˱���Ϣ" class=cssButton TYPE=button onclick="showNewUWSub();"-->
        </div>
    </div>
    	  <input type="hidden" id="PrtNoHide" name= "PrtNoHide" value= "">
    	  <input type="hidden" id="PolNoHide" name= "PolNoHide" value= "">
    	  <input type="hidden" id="MainPolNoHide" name= "MainPolNoHide" value= "">
    	  <input type="hidden" id="ContNoHide" name= "ContNoHide" value= "">
    	  <input type="hidden" id="InsuredNoHide" name= "InsuredNoHide" value= "">
    	  <input type="hidden" id="ProposalContNo" name= "ProposalContNo" value= "">
    	  <input type="hidden" id="ProposalNo"��name= "ProposalNo" value= "">
    	  <input type="hidden" id="MissionID" name= "MissionID" value= "">
    	  <input type="hidden" id="SubMissionID"��name= "SubMissionID" value= "">
    	  <input type="hidden" id="ActivityID" name= "ActivityID" value= "">
    	  <input type="hidden" id="UWStateHide" name= "UWStateHide" value= "">
  </Div>       
        
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
