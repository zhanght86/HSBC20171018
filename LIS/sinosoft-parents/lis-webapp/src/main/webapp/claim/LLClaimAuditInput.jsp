<%
//**************************************************************************************************/
//Name��LLClaimAuditInput.jsp
//Function����˹���
//Author��zl
//Date: 2005-6-20 14:00
//Desc:
//�޸ģ�niuzj,2006-01-13,�������������ȡ��ʽ���ֶ�
//**************************************************************************************************/
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
<%
//=======================BGN========================
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��

	  String tMissionID = request.getParameter("MissionID");  //����������ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
	  String tActivityid = request.getParameter("Activityid");  //�ڵ�ID
	  String tBudgetFlag = request.getParameter("BudgetFlag");
	  String tAuditPopedom = request.getParameter("AuditPopedom");
	  String tGrpRptNo = request.getParameter("GrpRptNo");  //�����ⰸ��
	  String tRgtClass = request.getParameter("RgtClass");  //��������
	  String tRgtObj = request.getParameter("RgtObj");      //��������
//=======================END========================
%>
<head>
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
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <SCRIPT src="LLClaimAudit.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimAuditInit.jsp"%>

</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <!--������Ϣ-->
    
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
        <table  class= common>
       	    <TR  class= common>
			          <TD  class= title> �¼��� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly name=AccNo id=AccNo></TD>
         	      <TD  class= title> ������ </TD>
                <TD  class= input> <Input class="wid" class="readonly" readonly  name=RptNo id=RptNo ></TD>
          	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> �����˵绰 </TD>
              	<TD  class= input> <Input class="wid" class= "readonly" readonly  name=RptorPhone id=RptorPhone ></TD>
              	<TD  class= title> �������ֻ����� </TD>
              	<TD  class= input> <Input class="wid" class= "readonly" readonly name=RptorMoPhone id=RptorMoPhone ></TD>
                <TD  class= title> ��������ϸ��ַ </TD>
                <TD  class= input> <Input class="wid" class= "common3" readonly  name=RptorAddress id=RptorAddress ></TD>
            </TR>
           
            <TR  class= common>
            		<TD  class= title> �������ʱ� </TD>
								<TD  class= input><Input class="wid" class= "readonly" readonly name="AppntZipCode" id="AppntZipCode" ></TD>
            		<TD  class= title> ������������˹�ϵ </TD>
    	          <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=Relation ondblclick="showCodeList('llrgrelation',[this,RelationName],[0,1]);" onMouseDown="showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
       	        <TD  class= title> ���յص� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly name=AccidentSite id=AccidentSite ></TD>
	          </TR>
            <TR  class= common>
              	<TD  class= title> �������� </TD>
          	    <TD  class= input> <!--<input class="readonly" readonly name="RptDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RptDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RptDate id=RptDate id="RptDate"><span class="icon"><a onClick="laydate({elem: '#RptDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
			          <TD  class= title> ��Ͻ���� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly name=MngCom id=MngCom></TD>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="wid" class="readonly" readonly name=Operator id=Operator ></TD>
            </TR>
            <TR  class= common>
             		<TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeType id=AssigneeType ></TD>
        	      <TD  class= title> �����˴��� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeCode id=AssigneeCode ></TD>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeName id=AssigneeName ></TD>
            </TR>
            <TR  class= common>
            	  <TD  class= title> �������Ա� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeSex id=AssigneeSex ></TD>
        	      <TD  class= title> �����˵绰 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneePhone id=AssigneePhone ></TD>
        	      <TD  class= title> �����˵�ַ </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
        	   </TR>  
        	   <TR  class= common>
        	      <TD  class= title> �������ʱ� </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeZip id=AssigneeZip ></TD>
           <!-- <TD  class= title> ��������</TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=RgtClassName ></TD>  -->
                <TD class= title> �������� </TD>
                <TD class= input>  <!--<input class="readonly" readonly  name="AcceptedDate";">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AcceptedDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AcceptedDate id="AcceptedDate"><span class="icon"><a onClick="laydate({elem: '#AcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                <font size=1 color='#ff0000'><b>*</b></font></TD>            
           			<TD class= title> �ͻ���������</TD>
                <TD class= input>  <!--<input class="readonly" readonly  name="ApplyDate";">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#ApplyDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ApplyDate id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                <font size=1 color='#ff0000'><b>*</b></font></TD>           
            </TR>
            <TR  class= common>
            	  <TD  class= title>�������ȡ��ʽ</TD>
          	    <TD  class= input> <Input class="wid" class= readonly readonly name=GetMode ></TD>
				<TD  class= title>���������������</TD>
          	    <TD  class= input> <!--<Input class= readonly readonly name=AddAffixAccDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AddAffixAccDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AddAffixAccDate id="AddAffixAccDate"><span class="icon"><a onClick="laydate({elem: '#AddAffixAccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
          	    <TD  class= title>
          	       <span id="spanRgtObjNo1" style= "display: none">       
          	           �����ⰸ��
          	       </span>
          	    </TD>
          	    
          	    <TD  class= input> 
          	       <span id="spanRgtObjNo2" style= "display: none"> 	
          	           <Input class="wid" class= readonly readonly name=RgtObjNo id=RgtObjNo >
          	       </span>
          	    </TD>   
            </TR>
        </table>
        </Div>
	<br />
    <!--��������Ϣ-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>
    
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> �ͻ���Ϣ </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" style= "display: ''" class="maxbox">
       
        <!--��������Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--�����˱���,��������-->
                    <TD class= title> �ͻ����� </TD>
                    <TD class= input> <Input class="wid" class="readonly" readonly name=customerName id=customerName></TD>
                    <TD class= title> ���� </TD>
                    <TD class= input> <Input class="wid" class="readonly" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> �Ա� </TD>
                    <TD class= input> <Input class="wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onMouseDown="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=readonly readonly name=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <!-- <TD class= title> VIP�ͻ���ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> �¹�����</TD>
                    <TD class= input> <!--<input class="readonly" readonly name="AccidentDate" >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    <font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ҽ�Ƴ������� </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    <font size=1 color='#ff0000'><b>*</b></font></TD>              
                    <TD class= title> ������������ </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#OtherAccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=OtherAccidentDate id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    <font size=1 color='#ff0000'><b>*</b></font></TD>
					</TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onMouseDown="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
					<TD class= title> ����ԭ��</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onMouseDown="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=accidentDetail
                    id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onMouseDown="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onMouseDown="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onMouseDown="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onMouseDown="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʶ</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onMouseDown="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName readonly=true></TD>
                    <TD class= title> ��Ҫ��Ϣ�޸ı�ʶ</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsModify id=IsModify disabled CodeData="0|^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsModifyName],[0,1])" onMouseDown="return showCodeListEx('IsAllReady', [this,IsModifyName],[0,1])" onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName readonly=true></TD>
                    <TD class= title> ������ʶ</TD>
                    <TD class= input> <Input class="wid" class=readonly name=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class="tet" align=left>
                        <input class="ver" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span>���</span> </input>
                        <input class="ver" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span>�߲�</span> </input>
                        <input class="ver" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"> <span>�ش󼲲�</span> </input>
                        <input class="ver" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span>�м������ˡ����ۡ���Ҫ�����г�</span> </input>
                        <input class="ver" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span>����</span> </input>
                        <input class="ver" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span>ҽ��</span> </input>
                        <input class="ver" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"> <span>���ּ���</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                    </tr>
                <TR class= common>
                    <TD> <textarea style="margin-left:16px" name="AccDesc" cols="199" rows="4" witdh=25% class="common" ></textarea></TD>
                   
                </TR>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea style="margin-left:16px" name="Remark" cols="199" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
     <!--<table>
            <tr>-->
            <!--
                <td><input class=cssButton name=QueryPerson value="�����˲�ѯ" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value=" �¼���ѯ " type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
                </td>
             -->
                <input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onclick="showInsuredLCPol()">
                <input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()">
				<input class=cssButton name=QueryCont5 VALUE="��Ҫ��Ϣ�޸�" TYPE=button onclick="editImpInfo()"></td>
                <input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();">
                <input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();">                

    <br />
	<!--=========================================================================
        �޸�״̬����ʼ
        �޸�ԭ������Ϊ������Ϣ��
        �� �� �ˣ�����
        �޸����ڣ�2005.05.13
        =========================================================================
    -->

    <!--����������Ϣ-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
            <td class= titleImg>����������Ϣ</td>
         </tr>
    </table>
    <Div  id= "divBaseUnit6" style= "display:''" class="maxbox1">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title5>��������</TD>
    	        <TD  class= input5>
    	        <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onMouseDown="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
    	        </TD>

 				<TD  class= title5>�Ƿ�Ԥ��</TD>
    	        <TD  class= input5>
    	        <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno  name=whetherPrePay id=whetherPrePay ondblclick="return showCodeList('whetherpreconclusion',[this,whetherPrePayName],[0,1]);" onMouseDown="return showCodeList('whetherpreconclusion',[this,whetherPrePayName],[0,1]);" onkeyup="return showCodeListKey('whetherpreconclusion',[this,whetherPrePayName],[0,1]);"><input class=codename name=whetherPrePayName readonly=true>
    	        </TD>
               
                <!--Modify by zhaorx
                <TD  class= title> ������ʶ</TD>
                <TD  class= input> <Input class=codeno disabled name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
                -->
    	        <td>&nbsp;&nbsp;</td>
    	     </tr>
    	</table>
    </div>
    
        	<INPUT class=cssButton name="dutySet1" VALUE="Ͷ������"  TYPE=button onclick="clearAcc();"></td>
            <INPUT class=cssButton name="dutySet" VALUE=" ƥ�䲢���� "  TYPE=button onclick="showMatchDutyPay();"></td>
            <INPUT class=cssButton name="MedicalFeeInp" VALUE="ҽ�Ƶ�֤����"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
            <INPUT class=cssButton type=hidden name="CertifyFeeInp" VALUE="��֤���õ���"  TYPE=button onclick="showLLMedicalFeeAdj();"></td>
			<INPUT class=cssButton name="medicalFeeCal" VALUE="���ü���鿴"  TYPE=button onclick="showLLMedicalFeeCal();"></td>

    <span id= "spanConclusion1" style= "display: ">
        <!--�����ⰸ������Ϣ-->
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimGrid);"></td>
                <td class= titleImg>�ⰸ������Ϣ</td>
             </tr>
        </table>
        <Div  id= "divClaimGrid" style= "display:''">
             <Table  class= common>
                  <tr><td text-align: left colSpan=1>
                       <span id="spanClaimGrid"></span>
                  </td></tr>
             </Table>
        </div>
        
        
        <!--�����ⰸ�������ͼ�����Ϣ-->
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit1);"></td>
                <td class= titleImg>�������ͼ�����Ϣ</td>
             </tr>
        </table>
        <Div  id= "divBaseUnit1" style= "display:''">
             <Table  class= common>
                  <tr><td text-align: left colSpan=1>
                       <span id="spanDutyKindGrid"></span>
                  </td></tr>
             </Table>
        </div>
        
        <!--���ձ����������ͼ�����Ϣ-->
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit2);"></td>
                <td class= titleImg>����������Ϣ</td>
             </tr>
        </table>
        <Div  id= "divBaseUnit2" style= "display:''">
             <Table  class= common>
                  <tr><td text-align: left colSpan=1>
                       <span id="spanPolDutyKindGrid"></span>
                  </td></tr>
             </Table>
        </div>
        <!--����ƥ����Ϣ-->
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit3);"></td>
                <td class= titleImg>���������Ϣ</td>
             </tr>
        </table>
        <Div  id= "divBaseUnit3" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
                </tr>
            </Table>
        </div>
    </span>
    <div id= "divBaseUnit5" style= "display: none">
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
                <td class= titleImg>�����⸶����</td>
             </tr>
        </table>
        <Div  id= "divBaseUnit4" style= "display:''">
            <table class= common>
                <TR class= common>
                    <TD  class= title>�⸶����</TD>
                    <TD  class= input><Input class=codeno name=GiveType ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName readonly=true></font>
                    <td class=title>����</td>
    					<td class=input><Input class=common name=Currency readonly=true></td>
                    <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr>
            </table>
            <Div  id= "divGiveTypeUnit1" style= "display: none">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>�������</TD>
                        <TD  class= input><Input class=common name=RealPay ></TD>
                        <TD  class= title>����ԭ��</TD>
                        <TD  class= input><Input class=codeno name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1],null,'1 and code <> #14#',1,null,'150');" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1],null,'1 and code <> #14#',1,null,'150');"><input class=codename name=AdjReasonName readonly=true></font>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title> ������ע </TD>
                    </tr>
                    <TR class= common>
                        <TD class= input> <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
                    </tr>
                 </table>
            </div>
            <Div  id= "divGiveTypeUnit2" style= "display: none">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>�ܸ�ԭ��</TD>
                        <TD  class= input><Input class=codeno name=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class=codename name=GiveReasonName readonly=true></td>
                        <TD  class= title></TD>
                        <TD  class= input></TD>                                                
                    </tr>
                </table>
                <table class= common>
                    <TR class= common >
                        <TD class= title> �ܸ����� </TD>
                    </tr>
                    <TR class= common>
                        <TD class= input> <textarea name="GiveReasonDesc" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
                    </tr>
                </table>
            </div>
            <table>
                 <tr>
                     <td><INPUT class=cssButton name="addUpdate" VALUE=" ����޸� "  TYPE=button onclick="AddUpdate()" ></td>
                     <td><INPUT class=cssButton name="saveUpdate" VALUE=" �����޸� "  TYPE=button onclick="SaveUpdate();" ></td>
                 </tr>
            </table>
        </div>
    </div>
    <!--=========================================================================
        �޸�״̬������
        �޸�ԭ������Ϊ������Ϣ��
        �� �� �ˣ�����
        �޸����ڣ�2005.05.13
        =========================================================================
    -->
    <!--=========================================================================
        �޸�״̬����ʼ
        �޸�ԭ������Ϊ�����Ϣ��
        �� �� �ˣ�����
        �޸����ڣ�2005.05.13
        =========================================================================
    -->
    <!--<table>-->
        <tr>
	        <td><INPUT class=cssButton name="DoHangUp" VALUE=" �������� "  TYPE=button onclick="showLcContSuspend()" ></td>
            <!--<td><INPUT class=cssButton name="AccidentDesc" VALUE="   ��ע��Ϣ   "  TYPE=button onclick="showClaimDesc()"></td>-->
            <!--<td><INPUT class=cssButton name="ViewReport" VALUE=" �鿴�ʱ� "  TYPE=button onclick="showQuerySubmit()"></td>-->
            <td><INPUT class=cssButton name="BeginInq" VALUE=" ������� "  TYPE=button onclick="showBeginInq()"></td>
            <!--<td><INPUT class=cssButton name="ViewInvest" VALUE=" �鿴���� "  TYPE=button onclick="showQueryInq()"></td>-->
	    </tr><br>
        <tr>
            <td><INPUT id="demo" name="SecondUW" style = "display:''" class=cssButton name="" VALUE=" ������� "  TYPE=button onclick="showSecondUWInput()"></td>
            <td><INPUT id="demo" name="SecondUWResult" style = "display:''" class=cssButton name="" VALUE=" ���˽��۲�ѯ "  TYPE=button onclick="SecondUWInput();"></td>
            <td><INPUT class=cssButton name="ExemptDeal" VALUE=" ���⴦�� "  TYPE=button onclick="showExempt();"></td>            
            <td><INPUT class=cssButton name="PolnoCheckIn " VALUE=" �������� "  TYPE=button onclick="showPolDeal();"></td>
            <td><INPUT class=cssButton name="ContDeal" VALUE=" ��ͬ���� "  TYPE=button onclick="showContDeal();"></td>
            <td><INPUT class=cssButton name="BeneficiaryAssign" VALUE="�����˷���"  TYPE=button onclick="showBnf()"></td>
            <td><INPUT class=cssButton name="AccountDeal" VALUE=" �ʻ����� "  TYPE=button onclick="showLCInsureAccont()"></td>
        </tr><br><br><hr class="line"><br>
        <!-- wyc add -->
        <!-- 
         -->
        <a href="javascript:void(0);" name="benefit" class="button" onClick="benefit();">��ʷ�˵�</a>
        <a href="javascript:void(0);" name="claimCheck" class="button" onClick="claimCheck();">�����˵��˶�</a>
        
        
    <!--</table>-->
    <!--��˹�����Ϣ-->
    
    <table>
      <TR>
       <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
       <TD class= titleImg>��˹���</TD>
     </TR>
    </table>
    <Div id= "divLLAudit" style= "display: ''" class="maxbox">
        <table class= common>
            <TR class= common>
                <TD class= title>������(�����������700����)</TD>
            </tr>
            <TR class= common>
                <TD class= input> <textarea style="margin-left:16px" name="AuditIdea" cols="199" rows="4" witdh=25% class="common"></textarea></TD>
            </tr>
         </table>
         <table class= common>
            <TR class= common>
                <TD  class= title>��˽���</TD>
                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=AuditConclusion id=AuditConclusion ondblclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onMouseDown="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class=codename name=AuditConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title>���ⱸע</TD>
                <TD  class= input><Input class="wid" class=common name=SpecialRemark1></TD>
                <TD  class= title> ������ʶ</TD><!--Modify by zhaorx 2006-04-17,2006-08-15-->
                <TD  class= input><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ModifyRgtState id=ModifyRgtState CodeData="0|^11|��ͨ����^12|���ϰ���^14|���Ѱ���" ondblclick="return showCodeListEx('rgtType', [this,ModifyRgtStateName],[0,1])" onMouseDown= "return showCodeListEx('rgtType', [this,ModifyRgtStateName],[0,1])" onkeyup="return showCodeListKeyEx('rgtType', [this,ModifyRgtStateName],[0,1])"><Input class=codename name=ModifyRgtStateName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>                
            </tr>        
        </table>
        <Div id= "divLLAudit2" style= "display: none">
             <table class= common>
                <TR class= common>
                    <TD  class= title>�ܸ�ԭ��</TD>
                    <TD  class= input><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ProtestReason id=ProtestReason ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onMouseDown="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class=codename name=ProtestReasonName readonly=true></TD>
                    <TD  class= title>�ܸ�����</TD>
                    <TD  class= input><Input class="wid" class=common name=ProtestReasonDesc></TD>
                     <TD  class= title></TD>    
                     <TD  class= input></TD>
                </tr>        
            </table>
        </div></Div>
        <table>
            <tr>
                <td><INPUT class=cssButton name="" VALUE="���۱���" TYPE=button onclick="ConclusionSaveClick()" ></td>   
            	<td><INPUT class=cssButton name="" VALUE="����������۲�ѯ"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>   
                <td><INPUT class=cssButton type=hidden name="printAuditRpt" VALUE="��ӡ��˱���"  TYPE=button onclick="LLPRTClaimAuditiReport()" ></td>                                 
                <td><INPUT class=cssButton type=hidden name="printPatchFee" VALUE="��ӡ���ɱ���֪ͨ" TYPE=button onclick="LLPRTPatchFeePrint()" ></td>                                              
                <td><INPUT class=cssButton name="printDelayRgt" VALUE="��ӡ���䵥֤֪ͨ��"  TYPE=button onclick="prtDelayRgt()" ></td>
                <td><INPUT class=cssButton name="ReciInfoInp" VALUE="�ռ�����Ϣ"  TYPE=button onclick="showReciInfoInp();"></td>
           </tr>
        </table>
       
    <!--=========================================================================
        �޸�״̬������
        �޸�ԭ������Ϊ�����Ϣ��
        �� �� �ˣ�����
        �޸����ڣ�2005.05.13
        =========================================================================
    -->
    <br><br><hr class="line"><br>
    <table>
        <tr>
            <td><INPUT class=cssButton name="ConclusionSave" VALUE="���ȷ��"  TYPE=button onclick="AuditConfirmClick()"></td>
            <td><INPUT class=cssButton type=hidden name="BatchCreateNote" VALUE="��֤������ӡ����"  TYPE=button onclick="showPrtControl()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <%
    //������,������Ϣ��
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden name=hideOperate value=''>
    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
    <input type=hidden id="clmState" name="clmState"><!--����״̬-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
    <!--input type=hidden id="RgtClass" name="RgtClass"--><!--���ո��մ���-->
    <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--�Ƿ�Ԥ��-->
    <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--���Ȩ��-->

    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	<input type=hidden id="RgtObj" name= "RgtObj">

    <Input type=hidden id="RgtClass" name=RgtClass value="1" >
    <input type=hidden id="RgtClassName" name=RgtClassName value="����">
	
	<!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
