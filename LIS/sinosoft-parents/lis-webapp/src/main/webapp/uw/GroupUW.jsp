<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GroupUW.jsp
//�����ܣ����屣���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tContNo = "";
	String tPrtNo = "";
	String tGrpContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tNoType = "";
		
	tPrtNo = request.getParameter("PrtNo");
	tGrpContNo = request.getParameter("GrpContNo");
	tMissionID = request.getParameter("MissionID");
	tSubMissionID  = request.getParameter("SubMissionID");	
	tActivityID = request.getParameter("ActivityID");	
	tNoType = request.getParameter("NoType");
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	loggerDebug("GroupUW",tGrpContNo+"asdf"+tPrtNo);
%>
<script>
	var PrtNo = "<%=tPrtNo%>";
	var GrpContNo = "<%=request.getParameter("GrpContNo")%>";
	var MissionID = "<%=tMissionID%>";
	var SubMissionID = "<%=tSubMissionID%>";
	var ActivityID = "<%=tActivityID%>";
	var NoType = "<%=tNoType%>";
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupUW.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupUWInit.jsp"%>
</head>
<body  onload="initForm();checkNotePad(PrtNo);" >
  <form action="./GroupUWCho.jsp" method=post id="fm" name=fmQuery target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpCont);">
    		</td>
    		<td class= titleImg>
    			����Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divGrpCont" style= "display: ''">
    <div class="maxbox">
      <table  class= common>
        <TR  class= common>
          <TD  class= title8>
            ����Ͷ��������
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="GrpContNo" name=GrpContNo >
          </TD>
      <!--    <TD  class= title8>
            ӡˢ����
          </TD>-->
 
            <Input type="hidden"  class="readonly" readonly id="PrtNo" name=PrtNo>
        
          <TD  class= title8>
            �������
          </TD>
          <TD  class= input8>
            <Input class=codeno id="ManageCom" name=ManageCom readonly=true><input class=codename id="ManageComName" name=ManageComName readonly=true>
          </TD>
           <TD  class= title8>
            ��������
          </TD> 
          <TD  class= input8>
          	<input class=codeno id="SaleChnl" name=SaleChnl readonly=true><input class=codename id="SaleChnlName" name=SaleChnlName readonly=true>
          </TD>
        </TR>
        <TR  class= common8>
          
         
          <TD  class= title8>
            ��������
          </TD>
          <TD  class= input8>
            <input class=codeno id="AgentCom" name=AgentCom readonly=true><input class=codename id="AgentComName" name=AgentComName readonly=true>
          </TD>
          <TD  class= title8>
            ҵ��Ա
          </TD>
          <TD  class= input8>
      				<input class=codeno id="AgentCode" name=AgentCode readonly=true><input class=codename id="AgentCodeName" name=AgentCodeName readonly=true>
         </TD>
         <TD  class= title8>
            ���������
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="AgentGroup" name=AgentGroup>
          </TD>   
        </TR>
        <TR  class= common>
          
          
            <TD  class= title8>
            Ͷ����λ�ͻ�����
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="AppntNo" name=AppntNo >
          </TD>       
          <TD  class= title8>
            Ͷ����λ����
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="AppntName" name=AppntName >
          </TD>  
          <TD  class= title8>
            VIP���
          </TD>
          <TD  class= input8>
          	<input class=codeno id="VIPValue" name=VIPValue readonly=true><input class=codename id="VIPValueName" name=VIPValueName readonly=true>
            <INPUT type= "hidden" id="Operator" name= "Operator" value= "">
          </TD>
        </TR>
        <TR  class= common>          
           <TD  class= title8>
            ���������
          </TD>
          <TD  class= input8>
            <input class=codeno id="BlacklistFlag" name=BlacklistFlag readonly=true><input class=codename id="BlacklistFlagName" name=BlacklistFlagName readonly=true>
          </TD>
          </TR>
         
        </table>
       </div>   	
    <div id=DivLCSendTrance STYLE="display:none">
			<table class=common>
				<tr CLASS="common">
			    <td CLASS="title">�ϱ���־ 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class=codeno id="SendFlag" name=SendFlag readonly=true><input class=codename id="SendFlagName" name=SendFlagName readonly=true>
	    		</td>
				<td CLASS="title">�˱����� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input class=codeno id="SendUWFlag" name=SendUWFlag readonly=true><input class=codename id="SendUWFlagName" name=SendUWFlagName readonly=true>
	    		</td>
				<td CLASS="title">�˱����
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input id="SendUWIdea" NAME="SendUWIdea" CLASS="readonly" readonly>
	    		</td>
			</tr>
		</table>		
	</div>


    </Div>
    <!-- �����ѯ���� -->
    <!--TR  class= common>
      <TD  class= titles>
        ����Ͷ��������
      </TD>
      <TD  class= input>
        <Input class= common name=GrpProposalNo >
        <Input type= "hidden" class= common name=GrpMainProposalNo >
        <INPUT type= "hidden" name= "Operator" value= "">
      </TD>
    </TR-->       
       
    <!-- ��ѯδ�����屣�����б� -->
  <Div id ='divLCGrpPol' style="display:none">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			����Ͷ������ѯ���
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolFee);">
    		</td>
    		<td class= titleImg>
    			���ֱ�����Ϣ
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPolFee" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpPolFeeGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </div>
     
 </div>   
   
   
 <!--��Ʒ��϶���-->  
   <div id="divGrpContPlan" style="display:none">
   
    <table>
    	<tr>
         <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPlan);">
    		</td>
    		<td class= titleImg>
    			��Ʒ��ϲ�ѯ���
    		</td>
    	</tr>
    </table>
    
    <Div  id= "divLDPlan" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPlanGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </div>
    <table>
     <tr>
    	<td class=common>
    			<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlnaRiskDetail);">
    		</td>
    		<td class=titleImg>������ϸ��Ϣ</td>
    	</tr>
    </table>
    <div id="divPlnaRiskDetail">
      <Table class=common>
        <tr>
         <td text-align: left colSpan=1>
           <span id="spanPlanRiskGrid"></span>
         </td>
        </tr>
        <tr>
         <TD  class= title>
            ��Ʒ�����Լ��Ϣ
          </TD>
          <tr></tr>
          <TD  class= input> <textarea name="PlanSpecContent" id=PlanSpecContent readOnly = "True" cols="100%" rows="3" witdh=100% class="common"></textarea></TD>
        </tr>
      </Table>
     </div>
    
   </div>
	<br>    
	<hr class="line"/>
	<INPUT VALUE ="  ����ɨ�����ѯ  " Class="cssButton" id="uwButton1" name="uwButton1" TYPE=button onclick="ScanQuery2();"> 
	<INPUT VALUE =" �����Զ��˱���Ϣ " Class="cssButton" id="uwButton2" name="uwButton2" TYPE=button onclick= "showGNewUWSub();">
	<INPUT VALUE ="   ���屣����ϸ   " Class="cssButton" id="uwButton3" name="uwButton3" TYPE=button onclick= "showGrpCont();">
	<!--INPUT VALUE =" �������ѯ����Ϣ " Class="cssButton" TYPE=button onclick= "showAskApp();"-->
	<!--INPUT VALUE =" �������������Ϣ " Class="cssButton" TYPE=button onclick= "showHistoryCont();"-->
	<INPUT VALUE ="   ���˺˱���Ϣ   " Class="cssButton" id="uwButton4" name="uwButton4" TYPE=button onclick="GrpContQuery('<%=tGrpContNo%>');">
	<INPUT VALUE ="�����ѳб�������ѯ" class="cssButton" id="uwButton5" name="uwButton5" TYPE=button onclick="queryProposal();">
  <INPUT VALUE ="����δ�б�������ѯ" class="cssButton" id="uwButton6" name="uwButton6" TYPE=button onclick="queryNotProposal();">
	<!--INPUT VALUE="���屣���б���Լ¼��" Class=Common TYPE=button onclick="showGSpec();"-->
	<INPUT VALUE ="���屣���������ѯ" Class="cssButton" id="uwButton7" name="uwButton7"  TYPE=button onclick="GrpQuestQuery();">
	<INPUT VALUE ="  ���������ȫ��ѯ" Class="cssButton" id="uwButton14" name="uwButton14" TYPE=button onclick="queryEdor();">
	<INPUT VALUE = "  ������������ѯ" Class="cssButton" id="uwButton15" name="uwButton15" TYPE=button onclick="queryClaim();">
	<INPUT VALUE = "    ֪ͨ���ѯ    " Class="cssButton" id="uwButton16" name="uwButton16" TYPE=button onclick="queryNotice();">
<br></br>
	
	<!--<INPUT VALUE = "���������֪ͨ��" Class="cssButton" TYPE=button onclick="checkBody();">
	<INPUT VALUE = "����������֪ͨ��" Class="cssButton" TYPE=button onclick="RReport();">-->
	<INPUT VALUE = "  �˱�����֪ͨ��  " Class="cssButton" id="uwButton8" name="uwButton8" TYPE=button onclick="GrpSendNotice();">
	
	<!--input value="���˱�֪ͨ��" class=cssButton type=button onclick="SendNotice();"--> 
	
	<!--	<input value="  �б��ƻ����  " class=cssButton type=button onclick="showChangePlan();">-->
		<input value="  �˱�Ҫ��֪ͨ��  " class=cssButton id="uwButton9" name="uwButton9" type=button onclick="showChangeResultView();">
		<input value=" �����������۲�ѯ " class=cssButton id="uwButton10" name="uwButton10" type=button onclick="showReport();">
		<input value=" ���������۲�ѯ " class=cssButton id="uwButton11" name="uwButton11" type=button onclick="showPE();">
		<!--<input value = "�ŵ��ӷѳб�¼��" class=cssButton type=button onclick="grpAddFee()">-->
		
		<input value = "   �������ʵ���   " class = cssButton id="uwButton13" name="uwButton13" type = button onclick = "grpFloatRate()">
		<input value = "   ����Ҫ������   " class=cssButton id="uwButton12" name="uwButton12" type=button onclick="grpRiskElement()">
		<input value = "    �ݽ��Ѳ�ѯ    " class=cssButton id="uwButton17" name="uwButton17" type=button onclick="showTempFee()">
	  <input value = " �ŵ��ر�Լ��¼�� " class=cssButton id="uwButton18" name="uwButton18" type=button onclick="grpSpecInput()">
		<!--INPUT VALUE="�����¼��" class=cssButton TYPE=button onclick="QuestInput();"-->
	<!--INPUT VALUE = "���������" Class="cssButton" TYPE=button onclick="SendQuest();"-->
	<!--INPUT VALUE = "���ͼӷѺ˱�֪ͨ��" Class="cssButton" TYPE=button onclick="SendNotice();"-->
  <hr class="line"/>     
    <!-- ���嵥�˱����� -->
<div id="divGrpPolUWResult" style="display:none">    
    <table class= common border=0 width=100%>
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
		<td class= titleImg align= center>�������ֵ��˱����ۣ�</td>
	</tr>
    </table>
    <div class="maxbox1">
    <table  class= common border=0 width=100%>
      	<TR  class= common>
          <TD  height="29" class= title>
            �������ֵ��˱�����  
            <!--Input class=codeno name=GUWState ondblclick="return showCodeList('tdhbconlusion',[this,GUWStateName],[0,1]);" onkeyup="return showCodeListKey('tdhbconlusion',[this,GUWStateName],[0,1]);"><Input class=codename name=GUWStateName readonly elementtype=nacessary--> 
            <Input class="code" id="GUWState" name=GUWState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData="0|^1|�ܱ�^s|�ϸ����ʳб�^x|�¸����ʳб�^r|��Լ�б������ε���^m|��Լ�б��������⸶��������^q|��Լ�б�����������^9|��׼�б�" ondblclick="showCodeListEx('Grpcondition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">                      
          </TD>
        </TR>
          <tr></tr>
          </div>
          <TD  class= title>
            �������ֵ��˱����
          </TD>
          <tr></tr>
          <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea name="GUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    </table>
    
    	  <div id=divUWSave style="display:''">		
    	  	<input value = "ȷ  ��" Class="cssButton" TYPE=button onclick="grpPolCommit();">         
        </div>
</div>    

<!--��Ʒ��Ϻ˱�����-->
<div id="divGrpPlanUWResult" style="display:none">    
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>�����Ʒ��Ϻ˱����ۣ�</td>
	</tr>
    </table>
    <table  class= common border=0 width=100%>
      	<TR  class= common>
          <TD  height="29" class= title>
            �����Ʒ��Ϻ˱����� 
            <!--Input class=codeno name=GUWState ondblclick="return showCodeList('tdhbconlusion',[this,GUWStateName],[0,1]);" onkeyup="return showCodeListKey('tdhbconlusion',[this,GUWStateName],[0,1]);"><Input class=codename name=GUWStateName readonly elementtype=nacessary--> 
            <Input class="code" id="GPlanUWState" name=GPlanUWState readonly='true' CodeData="0|^1|�ܱ�^s|�ϸ����ʳб�^x|�¸����ʳб�^r|��Լ�б������ε���^m|��Լ�б��������⸶��������^q|��Լ�б�����������^9|��׼�б�" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('Grpcondition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">                      
          </TD>
        </TR>
          <tr></tr>
          <TD  class= title>
            �����Ʒ��Ϻ˱����
          </TD>
          <tr></tr>
          <TD  class= input> <textarea name="GPlanUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    </table>
    
    	  <div id=divUWSave style="display:''">		
    	  	<input value = "ȷ  ��" Class="cssButton" TYPE=button onclick="grpPlanCommit();">         
        </div>
</div>  
        
  	<div id = "divUWAgree" style = "display: none">
    		<INPUT VALUE="ͬ  ��" class=cssButton TYPE=button onclick="gmanuchk(1);">
    		<INPUT VALUE="��ͬ��" class=cssButton TYPE=button onclick="gmanuchk(2);">
    		
  	</div>
  
          <input type="hidden" id="WorkFlowFlag" name= "WorkFlowFlag" value= "">
          <input type="hidden" id="MissionID" name= "MissionID" value= "">
          <input type="hidden" id="SubMissionID" name= "SubMissionID" value= "">
          <input type="hidden" id="PrtNoHide" name= "PrtNoHide" value= "">
          <input type="hidden" id="GrpProposalContNo" name= "GrpProposalContNo" value= "">
          <input type="hidden" id="GrpPolNo" name= "GrpPolNo" value="">
          <input type="hidden" id="ContPlanCode" name= "ContPlanCode" value="">
          <input type="hidden" id="PlanType" name="PlanType" value="6">
          <INPUT  type= "hidden" class= Common id="YesOrNo" name= "YesOrNo"  value= "">
           </div>    
    
    <div id = "divUWResult" style = "display: ''">
        <!-- �˱����� -->
        <table class=common >
            <tr>
                
            <td class= titleImg align= center><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,heId);">&nbsp;�ŵ��˱�����</td></tr>
         
			
            <tr class = common>
            
                <td class=title8>
                <div id="heId" class="maxbox1">
                   &nbsp;&nbsp;&nbsp;&nbsp; �˱�����
              
                    <!--Input class="codeno" name=uwUpReport ondblclick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onkeyup="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary-->
                    <Input class="code" id="uwUpReport" name=uwUpReport style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData="0|^0|�������^2|��Ȩ���ϱ�" ondblclick="showCodeListEx('condition1',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition1',[this,''],[0,1]);">                   
                </td>
</div>
            </TR>
            
        </table>
    <table class=common>
		<tr>
		      	<td height="24"  class= title>
            		�˱����
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<td  class= input>&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      		<td>
      			<input id="uwPopedom" name=uwPopedom type=hidden>

      			</td>
      		</tr>
	  </table>
	    <INPUT VALUE="����Ͷ��������ȷ��" Class="cssButton" TYPE=button onclick="gmanuchk(0);">
	     <INPUT class=cssButton VALUE="���±��鿴"  id="NotePadButton" TYPE=button onclick="showNotePad();">  
	     <INPUT VALUE="���ظ���" class=cssButton TYPE=button onclick="returnApprove();"><br/>
	    <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="GoBack();">
	      	
</div>
    
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
