<%
//�������ƣ���LLSecondManuUWInput.jsp.
//�����ܣ���������˹��˱�-----��ͬ�򱣵��˱�
//�������ڣ�2005-12-29 11:10:36
//������  ��zhangxing
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
    	String tCaseNo = request.getParameter("CaseNo"); //�ⰸ��		
		String tBatNo = request.getParameter("BatNo");   //���κ�			
		String tInsuredNo = request.getParameter("InsuredNo"); //�����˿ͻ��� 
		String tClaimRelFlag = request.getParameter("ClaimRelFlag"); //�ⰸ��ر�־ 	
		
		String tMissionid = request.getParameter("Missionid");   //����ID 
		String tSubmissionid = request.getParameter("Submissionid"); //������ID 
		String tActivityid = request.getParameter("Activityid");  //�ڵ�� 	
		
	%>
<head >
	
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="./LLSecondManuUW.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet Type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet Type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet Type=text/css>
	<%@include file="./LLSecondManuUWInit.jsp"%>
</head>
<body  onload="initForm();" >
 <form method=post name=fm id="fm" target="fraSubmit" >
 	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></TD>
			<TD class= titleImg>���������б���Ϣ</TD>
		</TR>
	</Table>	
	<Div  id= "DivLLCUWBatchGrid" align= center style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1 ><span id="spanLLCUWBatchGrid"></span> </TD>
			</TR>
		</Table>
	</Div> 
	<Div  id= "divReasonList" style= "display: ''">
    	<table class=common>
                <tr class=common>
                    <TD class=title colspan=6 > ����˱�ԭ��˵����(500������) </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="InFo1" cols="100%" rows="3" witdh=100% readonly class="common"></textarea></TD>
                </tr>
         </table>
    </Div>
	<!--��ͬ��Ϣ-->
	<Div id="DivLCCont3" STYLE="display:none">
	<Table >
		<TR>
			<TD class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></TD>
			<TD class="titleImg">��ͬ��ϸ��Ϣ</TD>
		</TR>
	</Table>
	</Div>
	<Div id="DivLCCont" class="maxbox1" STYLE="display:none">
		<Table class=common >
			<TR class=common>
				<TD class="title">������ </TD>
				<TD><Input name="ContNo" id="ContNo" Value class="readonly wid" readonly ></TD>
				<TD class="title">������� </TD>
				<TD><Input name="MngCom" id="MngCom"  MAXLENGTH="10" class="readonly wid" readonly></TD>
                <TD class="title" >�������� </TD>
				<TD  class= input><Input class="readonly wid" type=hidden name="SaleChnl" id="SaleChnl" onKeyUp="return showCodeList('salechnl',[this,SaleChnlName],[0,1]);"><input class="readonly wid" name=SaleChnlName id="SaleChnlName" readonly=true></TD>  
				
			</TR>
			<TR class=common>
				<TD class="title">�����˱��� </TD>
				<TD><Input name="AgentCode" class="readonly wid" readonly></TD>
				<TD class="title">���������� </TD>
				<TD><Input name="AgentName" class="readonly wid" readonly></TD>
				<TD class="title">�������</TD>
				<TD ><Input name="AgentCom" class="readonly wid" type=hidden><input class="readonly wid" name=AgentComName readonly=true></TD>  
				<TD ><Input name="PrtNo" class="readonly wid" readonly type="hidden"></TD>						    		
			</TR>
		</Table>
		<Table class=common>
			<TR>
				<TD class= title>��ע</TD>
			</TR>
			<TR>
				<TD  class= title> <textarea name="Remark" readonly cols=100% rows="2" witdh=100% class=common></textarea></TD>
			</TR>
	<hr class="line">
		</Table>
	</Div>	
	<!----Ͷ���齡����֪��ѯ�ʺ�,��콡����֪��ѯ�ʺ�,��Ӧδ��֪���,�����˽���״����֪����----->
	<Div id="DivLCCont2" STYLE="display:none">
		<Table class=common>
			<TR><TD class= title>Ͷ���齡����֪��ѯ�ʺţ�</TD></TR>
			<TR>
				<TD  class= title><textarea name="HealthImpartNo1" id="HealthImpartNo1" readonly cols="100%" rows="1" wiTDh=100% class="common wid"></textarea></TD>
			</TR>
			<TR><TD class= title>��콡����֪��ѯ�ʺţ�</TD></TR>
			<TR>
				<TD  class= title> <textarea name="HealthImpartNo2" id="HealthImpartNo2" readonly cols="100%" rows="1" wiTDh=100% class=common></textarea></TD>
			</TR>
				
			<TR><TD class= title>��Ӧδ��֪�����</TD></TR>
			<TR>
				<TD  class= title> <textarea name="NoImpartDesc" id="NoImpartDesc" readonly cols="100%" rows="3" wiTDh=100% class=common></textarea></TD>
			</TR>
			<TR><TD class= title>�����˽���״����֪���ݣ�</TD></TR>
			<TR>
				<TD  class= title> <textarea name="Remark1" id="Remark1" readonly cols="100%" rows="3" wiTDh=100% class=common></textarea></TD>
			</TR>					
		</Table>
	</Div>
	<!-- Ͷ������Ϣ���� (Ϊ֧�����գ�����������) -->
	<hr class="line">
	<div id = DivForAppnt style = "display:">
    	<Table>
    		<TR>
    			<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);"></TD>
    			<TD class= titleImg>Ͷ������Ϣ</TD>
    		</TR>
    	</Table>
    	<DIV id=DivLCAppntInd class="maxbox1" STYLE="display:''">
    		<Table  class= common>
    			<TR  class= common>        
    				<TD  class= title>�ͻ���</TD>
    				<TD  class= input><Input class="readonly wid" readonly name=AppntNo id="AppntNo"></TD>				
    				<TD  class= title>����</TD>
    				<TD  class= input><Input class="readonly wid" readonly name=AppntName id="AppntName"></TD>
    				<TD class= title>�Ա�</TD>
                 <TD class= input>
                    <input class="codeno" name="AppntSex" id="AppntSex" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " type="hidden" onClick="style="background: url(../common/images/select--bg_03.png) no-repeat center right; "" ondblClick="showCodeList('AppntSex',[this,AppntSexName],[0,1]);" onKeyUp="showCodeListKey('AppntSex',[this,AppntSexName],[0,1]);" >
                    <input class="readonly wid" name="AppntSexName" id="AppntSexName" readonly>
                </TD>
    
    			</TR>
    			<TR  class= common>
    				<TD  class= title> ����</TD>
    				<TD  class= input><Input class="readonly wid" readonly name="AppntBirthday"  id="AppntBirthday"></TD>				
    				<TD  class= title>ְҵ���� </TD>
                <TD  class= input>
                    <Input class="readonly wid" name="OccupationCode" id="OccupationCode" readonly >
                </TD>
                <TD  class= title> ְҵ��� </TD>
                <TD  class= input>
                    <input class="codeno" name="OccupationType" id="OccupationType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " type="hidden" onClick="showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" ondblClick="showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" onKeyUp="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);" >
                    <input class="readonly wid" name="OccupationTypeName" id="OccupationTypeName" readonly> 
                </TD>        
    		  		
    			</TR>
    			<TR>
    			    <TD CLASS=title>���� </TD>
                    <TD CLASS=input COLSPAN=1>
                       <input class="codeno" name="NativePlace" id="NativePlace" type="hidden" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" ondblClick="showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onKeyUp="showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);" >
                       <input class="readonly wid" name="NativePlaceName" id="NativePlaceName" readonly> 
                    </TD>
    
    				<TD  class= title>VIP���</TD>
    				<TD  class= input><Input class="readonly wid" readonly name=VIPValue id="VIPValue" ></TD>
    				<TD  class=title>���������</TD>
    		 		<TD  class= input><Input class="readonly wid" readonly name=BlacklistFlag id="BlacklistFlag" ></TD>			 		
    			</TR>
    		</Table>   
    	</DIV>
	</DIV>
	<div id = DivForGrpAppnt style = "display:none">
        <table>
        	<tr>
            	<td class=common>
    			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol2);">
        		</td>
        		<td class= titleImg>
        			 Ͷ����λ����
        		</td>
        	</tr>
        </table>
        <Div  id= "divGroupPol2" class="maxbox1" style= "display: none">
          <table  class= common>
           <TR>
              <TD  class= title5>
                ��λ����
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid" name=GrpName id="GrpName" elementtype=nacessary onchange=checkuseronly(this.value) verify="��λ����|notnull&len<=60">
              </TD>
              <TD  class= title5>
                ��λ��ַ
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid" name=GrpAddress id="GrpAddress" elementtype=nacessary  verify="��λ��ַ|notnull&len<=60">
              </TD>
                       </TR>
            <TR  class= common>
              <TD  class= title5>
                ��������
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid" name=GrpZipCode id="GrpZipCode"  elementtype=nacessary  verify="��������|notnull&zipcode">
              </TD>       
              <TD  class= title5>
                ��λ�绰
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid"  name=Phone id="Phone" elementtype=nacessary verify="��λ�绰|notnull&len<=30">
              </TD>
            </TR>
          </table>
          
        </Div>
	</DIV>
	
	<hr class="line">

<table>
  <tr>
    <td nowrap>
    <!--INPUT VALUE=" Ͷ���˼���Ͷ����Ϣ " class=cssButton TYPE=button onclick="showApp(1);"-->
    <INPUT VALUE=" Ͷ���˽�����֪��ѯ " class=cssButton TYPE=button name="uwButton9" onClick="queryHealthImpart()">
    <INPUT VALUE=" Ͷ���˼���Ͷ�����ϲ�ѯ " class=cssButton TYPE=button name="uwButton30" onClick="showApp(1)">
 	<Input Value="Ͷ���˼��������ѯ" class=cssButton Type=button onClick="AppntQuery();">	
 <!--   <INPUT VALUE=" Ͷ����������ϲ�ѯ " class=cssButton TYPE=button name="uwButton10" onclick="showBeforeHealthQ()">
    <INPUT VALUE=" Ͷ�����������ϲ�ѯ(����) " class=cssButton TYPE=button name="uwButton10" onclick="showRiseQ()">
    <INPUT VALUE=" Ͷ���˱����ۼ���Ϣ " class=cssButton TYPE=button name="uwButton11" onclick="amntAccumulate();">
    <INPUT VALUE=" Ͷ����Ӱ�����ϲ�ѯ " class=cssButton TYPE=bu name="uwButton8"tton onclick=""> -->
    </td>
  </tr>   
  <tr>
    <td nowrap>
    <!-- 
    <INPUT VALUE="Ͷ�����ѳб�������ѯ" class=cssButton TYPE=button name="uwButton12" onclick="queryProposal();">
    <INPUT VALUE="Ͷ����δ�б�������ѯ" class=cssButton TYPE=button name="uwButton13" onclick="queryNotProposal();">
    <INPUT VALUE=" Ͷ���˼�����ȫ��ѯ " class=cssButton TYPE=button name="uwButton14" onclick="queryEdor()">
    <INPUT VALUE=" Ͷ���˼��������ѯ " class=cssButton TYPE=button name="uwButton15" onclick="queryClaim();">
     -->
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
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
  					<span id="spanInsuredGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
    </Div>
    <jsp:include page="../uw/LLManuUWInsuredShow.jsp"/>
	<hr class="line">
	<Table  class=common>
		<TR>
			<Input Value="���֪ͨ��¼��" class=cssButton Type=button onClick="showHealth();"> 
			<input class=cssButton id="Donextbutton5" VALUE="  �����¼��  " TYPE=button onClick="QuestInput();">
			<input VALUE="�˱���������"  class=cssButton name="ReportButton" TYPE=button onClick="showUWReport();">
			<INPUT VALUE="  ���±�  " class=cssButton id="NotePadButton" TYPE=button onClick="showNotePad();">
		</TR>
		
		<TR>
			<Input Value=" �������ѯ " class=cssButton Type=button onClick="showHealthResult();"> 
			<input value="  ��ѯ�����  " class=cssButton type=button onClick="QuestQuery();">
			<Input Value="  �� �� �� ѯ " class=cssButton Type=button onClick="queryClaim();">	
			<Input Value=" ������Ϣ��ѯ " class=cssButton Type=button onClick="showContQuery();"> 
			<Input Value=" Ӱ�����ϲ�ѯ " class=cssButton Type=button onClick="colQueryImage();"> 			
            
			<Input Value="�����˼��������ѯ" class=cssButton Type=button onClick="InsuredQuery();">
			<!-- <input value=" ���˱�֪ͨ�� " class="cssButton" type="button" name="uwButton23" onclick="SendAllNotice()"> -->
			<!--<Input Value=" ��Ҫ�ʾ�¼�� " class=cssButton Type=hidden onclick="showUWNotice();"> -->
			<!-- <Input Value="�˱�֪ͨ��¼��" class=cssButton Type=button onclick="SendAllNotice();">  -->
       		<input value="���ҽԺƷ�ʹ���"   class=cssButton type=button name="uwButtonHospital" onClick="HospitalQuality();" >
		    <input value="ҵ��ԱƷ�ʹ���"   class=cssButton type=button name="uwButton26" onClick="AgentQuality();" >
		  	<input value=" �ͻ�Ʒ�ʹ��� "   class=cssButton type=button name="uwButton25" onClick="CustomerQuality();" >

		</TR>
		
	</Table>	
	<hr class="line">   
	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWSub);"></TD>
			<TD class= titleImg>�鿴ѡ�к�ͬ�ĺ˱��켣��Ϣ</TD>
		</TR>
	</Table>
	<Div id=DivLLCUWSub align= center style="display:''">
		<Table  class= common>
			<TR  class= common>
				<td text-align: left colSpan=1><span id="spanLLCUWSubGrid" ></span></td>
			</TR>
		</Table>
	</Div>
	<Div id = "DivUWResult" style = "display: ''">
		<!-- �˱����� -->   	
		<Table  class= common>
			<TR class= common>
				<TD class= title>��ͬ�˱�����</TD>
				<TD class=input><Input class=codeno readonly name=uwState id="uwState" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('lluwsign',[this,uwStatename],[0,1]);" onDblClick="return showCodeList('lluwsign',[this,uwStatename],[0,1]);" onKeyUp="return showCodeListKey('lluwsign',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename id="uwStatename" readonly ></TD>
				<TD class=title></TD>
				<TD class=input></TD>	
				<TD class=title></TD>
				<TD class=input></TD>	
			</TR>               
		</Table>            
		<Table class=common>
			<TR>
				<TD class= title>�˱����</TD>
			</TR>
			<TR>
				<TD  class= title> <textarea name="UWIdea" cols="100%" rows="5" witdh =100% class=common></textarea></TD>
			</TR>
		</Table>
		<Table class=common>		
			<TR>
				<Input Value="ȷ    ��" class=cssButton Type=button onClick="uwSaveClick();">
	    		<Input Value="��    ��" class=cssButton Type=button onClick="uwCancelClick();">
        	</TR>		
		</Table>
	</Div>
	<hr class="line">
	<Table  class=common>
	<tr>	
		<TD class= title>��������</TD>
		<TD class=input><Input class=codeno readonly name=lluwflag id="lluwflag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('lluwflag',[this,lluwflagName],[0,1],null,'1 and code = #0#','1');" onDblClick="return showCodeList('lluwflag',[this,lluwflagName],[0,1],null,'1 and code = #0#','1');" onKeyUp="return showCodeListKey('lluwflag',[this,lluwflagName],[0,1],null,'1 and code = #0#','1');"><Input class=codename name=lluwflagName id="lluwflagName" readonly ></TD>
				<TD class=title></TD>
				<TD class=input></TD>	
				<TD class=title></TD>
				<TD class=input></TD>	
	</tr>
	</table>
	<Table class=common>
			<TR>
				<TD class= title>����ԭ��</TD>
			</TR>
			<TR>
				<TD  class= title> <textarea name="UWIdea2" cols="100%" rows="5" witdh =100% class=common></textarea></TD>
			</TR>
		</Table>
	<!-- <table class=common>
	<TR>
				<TD class= title>�˱����</TD>
	</TR>
	<TR>
		<TD  class= input> <textarea name="UWIdea2" cols="100%" rows="5" witdh =100% class=common></textarea></TD>
	</TR>
	</Table> -->
	<Table  class=common>
		<TR>
			<Input Value ="�˱����" class = cssButton Type=button onClick="llSecondUWFinish();">
			<Input Value ="��    ��" class=cssButton Type=button onClick="turnBack();"> 
		</TR>
	</Table>

	
	<!--��������-->
	<Input Type=hidden id="Operator" name="Operator" ><!--//-->
	<Input Type=hidden id="ComCode" name="ComCode" ><!--//-->
	<Input Type=hidden id="ManageCom" name="ManageCom" ><!--//-->			
	
	<Input Type=hidden id="tCaseNo" name="tCaseNo" ><!--//�ⰸ��<��ҳ����>-->
	<Input Type=hidden id="tBatNo" name="tBatNo" ><!--//���κ�<��ҳ����>-->
	<Input Type=hidden id="tInsuredNo" name="tInsuredNo" ><!--//�����˿ͻ���<��ҳ����> -->
	<Input Type=hidden id="tClaimRelFlag" name="tClaimRelFlag" ><!--//�ⰸ��ر�־<��ҳ����> -->	
	<Input Type=hidden id="tMissionid" name="tMissionid"><!--  //����ID -->
	<Input Type=hidden id="tSubmissionid" name="tSubmissionid"><!--//������ID -->
	<Input Type=hidden id="tActivityid" name="tActivityid"><!--//�ڵ�� 	-->	
		  
	<Input Type=hidden id="tContNo" name="tContNo" ><!--//��ͬ���� -->	
	
    <Input Type=hidden id="fmtransact" name="fmtransact" ><!--�����Ĳ�������-->
</form>
<br><br><br><br><br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
