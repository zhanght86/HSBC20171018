<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ClaimDetailQuery.jsp
//�����ܣ�����������ϸ��ѯ
//�������ڣ�2005-10-08
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html>
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
  //String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

   String tClmNo = request.getParameter("ClmNo");

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
    <SCRIPT src="ClaimDetailQuery.js"></SCRIPT>
    <%@include file="ClaimDetailQueryInit.jsp"%>
</head>
<body  onload="initForm()" >

<form method=post name=fm id="fm" target="fraSubmit">
    <!--������Ϣ-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery1);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>

    <Div id= "divClaimDetailQuery1" class="maxbox" style= "display:">
        <table class= common>
            <TR class= common>
                <TD class= title> �¼��� </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=AccNo1 id="AccNo1"></TD>
                <TD class= title> �ⰸ�� </TD>
                <TD class= input> <Input class="readonly wid" readonly  name=RptNo1  id="RptNo1"></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <input class="readonly wid"  readonly name=RptorName1 id="RptorName1" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �����˵绰 </TD>
                <TD class= input> <input class="readonly wid"  readonly name=RptorPhone1  id="RptorPhone1"></TD>
                <TD class= title> ������ͨѶ��ַ </TD>
                <TD class= input> <input class="readonly wid" readonly name=RptorAddress1 id="RptorAddress1" ></TD>
                <TD class= title> ���������¹��˹�ϵ </TD>
                <TD class= input> <Input type=hidden id="Relation1" name=Relation1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onDblClick="return showCodeList('llrgrelation',[this,Relation1Name],[0,1]);" onKeyUp="return showCodeListKey('llrgrelation',[this,Relation1Name],[0,1]);"><input class="readonly wid" readonly name=Relation1Name id="Relation1Name" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> ������ʽ </TD>
                <TD class= input> <Input type=hidden id="RptMode" name="RptMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" value="01" onDblClick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onKeyUp="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class="readonly wid" readonly name="RptModeName" id="RptModeName" value="�绰����" ></TD>
                <TD class= title> ���յص� </TD>
                <TD class= input> <input class="readonly wid" readonly name=AccidentSite1 id="AccidentSite1" ></TD>
                <TD class= title> ������������ </TD>
            <TD class= input>  <input class="readonly wid" readonly name="RptDate1" id="RptDate1" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> ��Ͻ���� </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=MngCom1 id="MngCom1"></TD>
                <TD class= title> ���������� </TD> 
                <TD class= input> <Input class="readonly wid" readonly name=Operator1 id="Operator1" ></TD>
        	    	<TD class= title> ��������</TD>
          	    <TD class= input> <Input type=hidden value="1" id="RgtClass1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=RgtClass1 onDblClick="return showCodeList('llrgtclass',[this,RgtClass1Name],[0,1]);" onKeyUp="return showCodeListKey('llrgtclass',[this,RgtClass1Name],[0,1]);"><input class="readonly wid" readonly name=RgtClass1Name id="RgtClass1Name"  value="����"></TD>
            </TR>
        </table>
    </Div>
    <!--������Ϣ-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery2);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>
        <Div id= "divClaimDetailQuery2" class="maxbox" style= "display: ">
        <table  class= common>
       	    <TR  class= common>
			          <TD  class= title> �¼��� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccNo2 id="AccNo2"></TD>
         	      <TD  class= title> �ⰸ�� </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo2 id="RptNo2" ></TD>
          	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName2 id="RptorName2" ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> �����˵绰 </TD>
              	<TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone2 id="RptorPhone2" ></TD>
            	  <TD  class= title> ������ͨѶ��ַ </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorAddress2 id="RptorAddress2" ></TD>
    	          <TD  class= title> ���������¹��˹�ϵ </TD>
    	          <TD  class= input> <Input type=hidden id="Relation2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Relation2 onDblClick="return showCodeList('llrgrelation',[this,Relation2Name],[0,1]);" onKeyUp="return showCodeListKey('llrgrelation',[this,Relation2Name],[0,1]);"><input class="readonly wid" readonly name=Relation2Name id="Relation2Name" readonly=true></TD>
            </TR>
            <TR  class= common>
       	        <TD  class= title> ���յص� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccidentSite2 id="AccidentSite2" ></TD>
       		      <TD  class= title> ������������ </TD>
          	    <TD  class= input> <input class="readonly wid" readonly name="RptDate2" id="RptDate2" ></TD>
			          <TD  class= title> ��Ͻ���� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=MngCom2 id="MngCom2"></TD>
	          </TR>
            <TR  class= common>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=Operator2 id="Operator2" ></TD>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeType id="AssigneeType" ></TD>
        	      <TD  class= title> �����˴��� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeCode id="AssigneeCode" ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeName id="AssigneeName" ></TD>
        	      <TD  class= title> �������Ա� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeSex id="AssigneeSex" ></TD>
        	      <TD  class= title> �����˵绰 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone id="AssigneePhone" ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> �����˵�ַ </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr id="AssigneeAddr" ></TD>
        	      <TD  class= title> �������ʱ� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip id="AssigneeZip" ></TD>
        	      <TD  class= title> ��������</TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=RgtClassName id="RgtClassName" ></TD>
            </TR>
        </table>
  	</Div>
	 <!--��������Ϣ-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery3);"></td>
            <td class=titleImg> ��������Ϣ </td>
        </tr>
    </table>
    <Div id= "divClaimDetailQuery3" class="maxbox" style= "display: ">
    <!--��������Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--�����˱���,��������-->
                    <TD class= title> ���������� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerName id="customerName"></TD>
                    <TD class= title> ���������� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id="customerAge"></TD>
                    <TD class= title> �������Ա� </TD>
                    <TD class= input> <Input class="readonly wid" type=hidden readonly id="customerSex" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=customerSex onDblClick="return showCodeList('sex',[this,customerSexName],[0,1]);" onKeyUp="return showCodeListKey('sex',[this,customerSexName],[0,1]);"><input class="readonly wid" readonly name=customerSexName id="customerSexName" readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> VIP�ͻ���ʾ</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=IsVip id="IsVip"></TD>
                    <TD class= title> �¹�����</TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate1" id="AccidentDate1" ></font></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input><Input class="readonly wid" type=hidden name=occurReason id="occurReason" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onDblClick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onKeyUp="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class="readonly wid" readonly name=ReasonName id="ReasonName" readonly=true></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input type=hidden id="hospital" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=hospital onDblClick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onKeyUp="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class="readonly wid" readonly name=TreatAreaName id="TreatAreaName" readonly=true></TD>
                    <TD class= title> �������� </TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate2" id="AccidentDate2" ></TD>
                    <TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input type=hidden id="accidentDetail" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=accidentDetail onDblClick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onKeyUp="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class="readonly wid" readonly name=accidentDetailName  id="accidentDetailName" readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input type=hidden name=cureDesc id="cureDesc" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onDblClick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onKeyUp="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class="readonly wid" readonly name=cureDescName id="cureDescName" readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 id="AccResult1" onDblClick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onKeyUp="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class="readonly wid" readonly name=AccResult1Name id="AccResult1Name" readonly=true></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 id="AccResult2" onDblClick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onKeyUp="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class="readonly wid" readonly name=AccResult2Name id="AccResult2Name" readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʾ</TD>
                    <TD class= input> <Input type=hidden id="IsAllReady" name=IsAllReady disabled CodeData="0|3^0|��^1|��" onDblClick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class="readonly wid" readonly name=IsAllReadyName id="IsAllReadyName" readonly=true></TD>
                    <TD class= title> ��Ҫ��Ϣ�޸ı�ʾ</TD>
                    <TD class= input> <Input type=hidden name=IsModify id="IsModify" disabled CodeData="0|3^0|��^1|��" onDblClick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class="readonly wid" readonly name=IsModifyName id="IsModifyName" readonly=true></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" id="claimType" name="claimType" > ��� </input>
                        <input disabled type="checkbox" value="03" id="claimType" name="claimType" > �߲� </input>
                        <input disabled type="checkbox" value="04" id="claimType" name="claimType"> �ش󼲲� </input>
                        <input disabled type="checkbox" value="01" id="claimType" name="claimType"> �˲� </input>
                        <input disabled type="checkbox" value="09" id="claimType" name="claimType" > ���� </input>
                        <input disabled type="checkbox" value="00" id="claimType" name="claimType" > ҽ�� </input>
                        <input disabled type="checkbox" value="05" id="claimType" name="claimType" > ���ּ��� </input>
                        <input disabled type="checkbox" value="06" id="claimType" name="claimType" > ʧҵʧ�� </input>

                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                </tr>
                <TR class= common>
                    <TD class= title> <textarea name="AccDesc" id=AccDesc cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD class= title> <textarea name="Remark" id=Remark cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <!--����������Ϣ-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery4);"></td>
            <td class= titleImg>����������Ϣ</td>
         </tr>
    </table>
    <Div  id= "divClaimDetailQuery4" class="maxbox1" style= "display:">
    	<table class= common>
    	     <TR class= common>
    	          <TD  class= title> ��������</TD>
    	          <TD  class= input> <Input type=hidden name=RgtConclusion id="RgtConclusion" onDblClick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class="readonly wid" readonly name=RgtConclusionName id="RgtConclusionName" readonly=true></TD>
                <TD  class= title> ������ʶ</TD>
                <TD  class= input> <Input type=hidden name=rgtType id="rgtType" onDblClick="return showCodeListEx('llrgttype', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeName],[0,1])"><Input class="readonly wid" readonly name=rgtTypeName id="rgtTypeName" readonly=true></TD>
                <TD  class= title> �ⰸ״̬</TD>
                <TD  class= input> <Input type=hidden name=ClmState id="ClmState" onDblClick="return showCodeListEx('llclaimstate', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('llclaimstate', [this,ClmStateName],[0,1])"><Input class="readonly wid" readonly name=ClmStateName id="ClmStateName" readonly=true></TD>
    	     </tr>
    	</table>
    </div>
    <!--��˹�����Ϣ-->
      <Span id="spanAudit" style="display:">
        <table>
        <TR>
               <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery5);"></TD>
               <TD class= titleImg>��˹���</TD>
        </TR>
        </table>
        <Div id= "divClaimDetailQuery5" class="maxbox1" style= "display: ">
        <table class= common>
        <TR class= common>
                <TD  class= title5>��˽���</TD>
                <TD  class= input5><Input type=hidden class="wid" name=AuditConclusion id="AuditConclusion" onDblClick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class="readonly wid" readonly name=AuditConclusionName id="AuditConclusionName" readonly=true></TD>
                <TD  class= title5>���ⱸע</TD>
                <TD  class= input5><Input class="readonly wid" readonly name=SpecialRemark1 id="SpecialRemark1"></TD>
                <TD  class= title5></TD>
                <TD  class= input5></TD>
         </tr>
         </table>
         <table class= common>
         <TR class= common>
                <TD  class= title5>�����</TD>
                <TD  class= input5><Input class="readonly wid" readonly name="AuditPer" id="AuditPer" ></TD>
                <TD  class= title5>�������</TD>
                <TD  class= input5><Input class="readonly wid" readonly name="AuditDate" id="AuditDate" ></TD>
                <TD  class= title5></TD>
                <TD  class= input5></TD>
          </tr>
          </table>
         <table class= common>
          <TR class= common>
                <TD class= title>������</TD>
          </tr>
          <TR class= common>
                <TD class= title> <textarea name="AuditIdea" id="AuditIdea" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
          </tr>
          </table>
          </Div>
        </span>
       <!--����������Ϣ-->
        <Span id= "spanConfirm" style="display: ;">
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divClaimDetailQuery6);"></td>
                    <td class= titleImg> ��������</td>
                </tr>
            </table>
            <Div  id= "divClaimDetailQuery6" class="maxbox1" style="display: ;">
                <table class= common>
               	    <TR class= common>
        	              <TD  class= title>��������</TD>
                        <TD  class= input><Input type=hidden id="DecisionSP" name="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onKeyUp="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" ><input class="readonly wid" readonly name=DecisionSPName id="DecisionSPName" readonly></TD>
                        <TD  class= title>������</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ExamPer" id="ExamPer" ></TD>
                        <TD  class= title>��������</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ExamDate" id="ExamDate" ></TD>
        	        </TR>
                </table>               
                <table class= common>
                    <TR class= common>
                        <TD  class= title> �������</TD>
                    </tr>
                    <TR class= common>
                        <TD  class= title> <textarea name="RemarkSP" id="RemarkSP" cols="100%" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                </table>
            </div>
         </span>
        <!--�᰸��Ϣ-->
        <Span id= "spanEndCase" style="display: ;">
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divClaimDetailQuery7);"></td>
                    <td class= titleImg> �᰸��Ϣ</td>
                </tr>
            </table>
            <Div  id= "divClaimDetailQuery7" class="maxbox" style="display: ;">
                <table class= common>
               	    <TR class= common>
                        <TD  class= title>ʵ���⸶���</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndRealPay" id="ClmEndRealPay" ></TD>
                        <TD  class= title>�᰸��</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndPer" id="ClmEndPer" ></TD>
                        <TD  class= title>�᰸����</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndDate" id="ClmEndDate" ></TD>
        	          </TR>
               	    <TR class= common>
                        <TD  class= title>ʵ����־</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="confGetMoney" id="confGetMoney" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
        	        </TR>
                </table>
            </div>
         </span>
    <table>
        <tr>
            <td><INPUT class=cssButton id="goBack" name="goBack" VALUE="��  ��"  TYPE=button onClick="top.close()"></td>
        </tr>
    </table>

	
	<Input class="readonly wid" type=hidden id="ClmNo" name="ClmNo" >
<Input type=hidden id="customerNo" name="customerNo">
<Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
</form>
<br/><br/><br/><br/>
</body>
</html>
