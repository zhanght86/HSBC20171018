<%
//**************************************************************************************************
//Name��LLClaimPrepayInput.jsp
//Function����Ԥ������������
//Author��yuejw
//Date: 2005-7-5 16:00
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
	String tClmNo=request.getParameter("ClmNo"); //�ⰸ��
	loggerDebug("LLClaimPrepayAuditInput","tClmNo:"+tClmNo);
	
	String tCustomerNo=request.getParameter("CustomerNo"); //�������      
	String tActivityID=request.getParameter("ActivityID"); //�ID
	
	String tMissionID =request.getParameter("MissionID");  //����������ID
	String tSubMissionID =request.getParameter("SubMissionID");  //������������ID	
	
	String mAuditPopedom =request.getParameter("AuditPopedom");  //������˼���
	String mRgtClass =request.getParameter("RgtClass");  //��������
	String mRgtObj =request.getParameter("RgtObj"); //��������
	
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrepayAudit.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimPrepayAuditInit.jsp"%>
    <title> Ԥ�����</title>
</head>
<body  onload="initForm();" >
<form  method=post name=fm id=fm target="fraSubmit">
        <!--����������Ϣ-->
    <table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>����������Ϣ</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" class=maxbox style= "display: ''">
        <table  class= common>
       	    <TR  class= common>
			          <TD  class= title> �¼��� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=AccNo id=AccNo></TD>
         	      <TD  class= title> ������ </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
          	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> �����˵绰 </TD>
              	<TD  class= input> <Input class="readonly wid" readonly  name=RptorPhone id=RptorPhone ></TD>
            	  <TD  class= title> �����˵�ַ </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptorAddress id=RptorAddress ></TD>
    	          <TD  class= title> ������������˹�ϵ </TD>
    	          <TD  class= input> <Input class=codeno disabled name=Relation id=Relation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
            </TR>
            <TR  class= common>
       	        <TD  class= title> ���յص� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=AccidentSite id=AccidentSite ></TD>
       		      <TD  class= title> �������� </TD>
          	    <TD  class= input> <input class="readonly wid" readonly name="RptDate" id=RptDate ></TD>
			          <TD  class= title> ��Ͻ���� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=MngCom id=MngCom></TD>
	          </TR>
            <TR  class= common>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeType id=AssigneeType ></TD>
        	      <TD  class= title> �����˴��� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeCode id=AssigneeCode ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeName id=AssigneeName ></TD>
        	      <TD  class= title> �������Ա� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeSex id=AssigneeSex ></TD>
        	      <TD  class= title> �����˵绰 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneePhone id=AssigneePhone ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> �����˵�ַ </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
        	      <TD  class= title> �������ʱ� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeZip id=AssigneeZip ></TD>
        	      <TD  class= title> ��������</TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=RgtClassName id=RgtClassName ></TD>
            </TR>
            <TR  class= common>
            	  <TD  class= title>�������ȡ��ʽ</TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=GetMode id=GetMode ></TD>
          	    
          	    <TD  class= title>
          	       <span id="spanRgtObjNo1" style= "display: none">       
          	           �����ⰸ��
          	       </span>
          	    </TD>
          	    
          	    <TD  class= input> 
          	       <span id="spanRgtObjNo2" style= "display: none"> 	
          	           <Input class="readonly wid" readonly name=RgtObjNo id=RgtObjNo >
          	       </span>
          	    </TD>   
            </TR>
        </table>
  	</Div>
	<hr class=line>
    <!--�ͻ���Ϣ-->
    <table class= common>
        <tr class= common>
            <td style="text-align: left" colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> �ͻ���Ϣ </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" class=maxbox style= "display: ''">
        <table>
            <tr>
            <!--
                <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value=" �¼���ѯ " type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
                </td>
             -->
                <td><input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
				<!-- <td><input class=cssButton name=QueryCont5 VALUE="��Ҫ��Ϣ�޸�" TYPE=button onclick="editImpInfo()"></td> -->
                <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>                
            </tr>
        </table>
        <!--�ͻ���Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--�����˱���,��������-->
                    <TD class= title> �ͻ����� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerName id=customerName></TD>
                    <TD class= title> �ͻ����� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> �Ա� </TD>
                    <TD class= input> <Input type=hidden name=customerSex id=customerSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
					<!-- <TD class= title> VIP�ͻ���ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> �¹�����</TD>
                    <TD class= input>  <input class="multiDatePicker" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);" onClick="laydate({elem: '#AccidentDate'});" id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>
					<TD class= title> ҽ�Ƴ������� </TD>
                    <TD class= input>  <input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" onClick="laydate({elem: '#MedicalAccidentDate'});" id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>              
                    <TD class= title> ������������ </TD>
                    <TD class= input>  <input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" onClick="laydate({elem: '#OtherAccidentDate'});" id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>
               
                </TR>
                <TR class= common>
                      <TD class= title> ����ҽԺ</TD>
                      <TD class= input> <Input class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                      <TD class= title> ����ԭ��</TD>
                      <TD class= input><Input class=codeno disabled name=occurReason id=occurReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true></font></TD>
                      <TD class= title> ����ϸ��</TD>
                      <TD class= input> <Input class=codeno disabled name=accidentDetail id=accidentDetail style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input class=codeno disabled name=cureDesc id=cureDesc style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult1 id=AccResult1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult2 id=AccResult2 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʶ</TD>
                    <TD class= input> <Input class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|^0|��^1|��" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
                    <TD class= title> ��Ҫ��Ϣ�޸ı�ʶ</TD>
                    <TD class= input> <Input class=codeno name=IsModify id=IsModify disabled CodeData="0|3^0|��^1|��" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName id=IsModifyName readonly=true></TD>
                    <TD class= title> ������ʶ</TD>
                    <TD class= input> <Input class="readonly wid" name=ClaimFlag id=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input type="checkbox" value="02" name="claimType" > ��� </input>
                        <input type="checkbox" value="03" name="claimType" > �߲� </input>
                        <input type="checkbox" value="04" name="claimType" > �ش󼲲� </input>
                        <input type="checkbox" value="01" name="claimType" > �м������ˡ����ۡ���Ҫ�����г� </input>
                        <input type="checkbox" value="09" name="claimType" > ���� </input>
                        <input type="checkbox" value="00" name="claimType" > ҽ�� </input>
                        <input type="checkbox" value="05" name="claimType" > ���ּ��� </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="AccDesc" id=AccDesc cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="Remark" id=Remark cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <hr class=line>
    <!--����������Ϣ-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
            <td class= titleImg>����������Ϣ</td>
         </tr>
    </table>
    <Div  id= "divBaseUnit6" class=maxbox1 style= "display:''">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title> ��������</TD>
    	        <TD  class= input> <Input class=codeno disabled name=RgtConclusion id=RgtConclusion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);">
				<input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true></TD>
                <!-- Modify by zhaorx 2006-04-17
                <TD  class= title> ������ʶ</TD>
                <TD  class= input> <Input class=codeno disabled name=rgtType CodeData="0|3^11|��ͨ����^12|���ϰ���^14|���Ѱ���" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeName],[0,1])"><Input class=codename name=rgtTypeName readonly=true></TD>-->
                <TD  class= title></TD>
                <TD  class= input></TD>                
                <TD  class= title></TD>
                <TD  class= input></TD>
    	     </tr>
    	</table>
    </div>
    <hr class=line>
    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimDetailGrid);"></td>
            <td class= titleImg> ����������Ϣ�б� </td>
        </tr>
    </table>
    <Div id= "divLLClaimDetailGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td style="text-align: left" colSpan=1 ><span id="spanLLClaimDetailGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>   
   
    </div>    

	<hr class=line>
	    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLPrepayDetailInfo);"></td>
            <td class= titleImg> �����Ԥ�����Ĵ��� </td>
        </tr>
    </table>
   <Div id= "divLLPrepayDetailInfo" style= "display: ''" align = center>

        <Table  class= common>
            <TR  class= common>
                <TD style="text-align: left" colSpan=1><span id="spanLLPrepayDetailGrid" ></span> </TD>
            </TR>
        </Table>
        <Table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick=" turnPage2.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick=" turnPage2.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick=" turnPage2.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick=" turnPage2.lastPage();"></td>
            </tr>
        </Table>
    </Div>    
    <table >
        <TR class= common>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayAdd" id=LLPrepayAdd disabled VALUE="��  ��" onclick="LLPrepayDetailAdd();"></TD>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayCancel" id=LLPrepayCancel disabled VALUE="ȡ  ��" onclick="LLPrepayDetailCancel();"></TD>
        </TR>
    </Table>      
   <Div id= "divLLPrepayDetail" style= "display: none" >
		<table class= common>
			<tr class= common>
    					<td class=title>����</td>
    					<td class=input><Input class="common wid" name=Currency id=Currency readonly=true></td>
    					<td></td>
    			<td></td>
    			<td></td>
    			<td></td>
    			</tr>
		    <TR class= common>
	            <TD class= title> ����Ԥ����� </TD>
	            <TD class= input><Input class="common wid" name="PrepaySum" id=PrepaySum ></TD>		        
	            <TD class= title>֧����ʽ</TD>
	            <td class= input><Input class=codeno readonly=true name="CasePayMode" id=CasePayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);">
				<input class=codename name="CasePayModeName" id=CasePayModeName readonly=true></TD>
	            <TD class= title></TD>
	            <TD class= input></TD>
		    </TR>
		</table>
        <table>
	     <TR class=common>
            	<TD> <Input TYPE=button class=cssButton name="PrsSave" id=PrsSave VALUE="��  ��" onclick="LLPrepayDetailSave();"></TD>    
         </TR>
        </table>
   </Div>      
    <!--Ԥ����˹�����Ϣ-->
    <hr class=line>
    <table>
      <TR>
       <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
       <TD class= titleImg>��˹���</TD>
     </TR>
    </table>
    <Div id= "divLLAudit" class=maxbox1 style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title>������(�����������700����)</TD>
            </tr>
            <TR class= common>
                <TD class= input> <textarea name="AuditIdea" id=AuditIdea cols="100" rows="6" witdh=25% class="common"></textarea></TD>
            </tr>
         </table>
         <table class= common>
            <TR class= common>
                <TD  class= title>��˽���</TD>
                <TD  class= input><Input class=codeno readonly name=AuditConclusion id=AuditConclusion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llclaimpreconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimpreconclusion',[this,AuditConclusionName],[0,1]);" >
				<input class=codename name=AuditConclusionName id=AuditConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
	            <TD class= title></TD>
	            <TD class= input></TD>
	            <TD class= title></TD>
	            <TD class= input></TD>
           </tr>        
        </table>
        <table>
            <tr>
				<TD> <Input TYPE=button class=cssButton name="Bnf" VALUE="�����˷���" onclick="showBnf();"></TD>      
                <td><INPUT class=cssButton name="" VALUE="���۱���" TYPE=button onclick="ConclusionSaveClick()" ></td>   
            	<td><INPUT class=cssButton name="" VALUE="����������۲�ѯ"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>   
                <td><INPUT class=cssButton name="printAuditRpt" id=printAuditRpt VALUE="��ӡ��˱���"  TYPE=button onclick="LLPRTClaimAuditiReport()" ></td>                                 
            </tr>
        </table>
    </Div> 
	<hr class=line>
	<Table>
        <TR>	
            <TD> <Input TYPE=button class=cssButton name="PrepayCofirm" id=PrepayCofirm VALUE="���ȷ��" onclick="LLClaimPrepayCofirm();"></TD>
            <!--<TD> <Input TYPE=button class=cssButton VALUE="��   ��" onclick="Return();"></TD>  -->                
        </TR>
    </Table>
	
    <!--//�������������ر���-->
    <Input type=hidden name="Operator" >    <!--//��ǰ������-->
    <Input type=hidden name="ComCode" id=ComCode>     <!--//��ǰ����-->
    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
    <Input type=hidden name="PrepayNo" id=PrepayNo >     <!--//Ԥ�����κ�,ÿ�ν���Ԥ��ҳ���ʼ��ʱ���ɣ��ڴ��������һֱ����-->
    <!--//������Ӧ�� ����������Ϣ�б������ѡť�ĺ���ʱ���������-->
	<input type=hidden id="ClmNo"   name= "ClmNo">
	<input type=hidden id="CaseNo" name= "CaseNo">    
	<input type=hidden id="PolNo"   name= "PolNo">
	<input type=hidden id="GetDutyKind" name= "GetDutyKind">
	<input type=hidden id="GetDutyCode" name= "GetDutyCode">
	<input type=hidden id="CaseRelaNo" name= "CaseRelaNo">
	<input type=hidden id="GrpContNo"   name= "GrpContNo">
	<input type=hidden id="GrpPolNo" name= "GrpPolNo">    
	<input type=hidden id="ContNo"   name= "ContNo">
	<input type=hidden id="KindCode" name= "KindCode">
	<input type=hidden id="RiskCode" name= "RiskCode">
	<input type=hidden id="RiskVer" name= "RiskVer">    
	<input type=hidden id="DutyCode" name= "DutyCode">    
	
    <!--//������ҳ���������,���ڡ�Ԥ��ȷ�ϡ�ʱ��ѯ�ڵ�����,Ϊ�����½ڵ�׼������-->  

	<input type=hidden id="tRptorState"   name= "tRptorState">
	<input type=hidden id="CustomerNo"   name= "CustomerNo">
	<input type=hidden id="CustomerName"   name= "CustomerName">
	<input type=hidden id="CustomerSex"   name= "CustomerSex">
	<input type=hidden id="tAccDate"   name= "tAccDate">
	<input type=hidden id="tRgtClass"   name= "tRgtClass">
	<input type=hidden id="tRgtObj"   name= "tRgtObj">
	<input type=hidden id="tRgtObjNo"   name= "tRgtObjNo">
	<input type=hidden id="AuditPopedom"   name= "AuditPopedom">
	<input type=hidden id="tPrepayFlag"   name= "tPrepayFlag">
	<input type=hidden id="tAuditer"   name= "tAuditer">	
	<input type=hidden id="tComeWhere"   name= "tComeWhere">
	<input type=hidden id="tMngCom"   name= "tMngCom">
	<Input type=hidden id="tComFlag" name= "tComFlag">     <!--//Ȩ�޿缶��־-->
	<input type=hidden id="MissionID"   name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
    <input type=hidden id="tAuditConclusion" name= "tAuditConclusion">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>	
