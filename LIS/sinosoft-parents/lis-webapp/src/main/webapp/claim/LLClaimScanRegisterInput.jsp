<%
//**************************************************************************************************
//Name��LLClaimRegister.jsp
//Function�������Ǽ�
//Author��zl
//Date: 2005-6-14 20:04
//Desc:
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	String tIsNew = request.getParameter("isNew");	//�Ƿ�����
	String tClmState = request.getParameter("clmState");	//����״̬

	String tMissionID = request.getParameter("MissionID");  //����������ID
	String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
	String tActivityID = request.getParameter("ActivityID");  //������������ID
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
   <SCRIPT src="LLClaimScanRegister.js"></SCRIPT>
   <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
   <%@include file="LLClaimScanRegisterInit.jsp"%>
</head>
<body onload="initForm()" >
<form action="./LLClaimRegisterSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!--����������Ϣ-->
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>����������Ϣ</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
        <table  class= common>
       	    <TR  class= common>
			    			<TD  class= title> �¼��� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
         	    	<TD  class= title> ������ </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
								<TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> �����˵绰 </TD>
              	<TD  class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone ><font size=1 color='#ff0000'><b>*</b></font></TD>
								<TD  class= title> �������ֻ����� </TD>
              	<TD  class= input> <Input class="wid" class= common name=RptorMoPhone id=RptorMoPhone verify="�������ֻ�����|LEN=11" verifyorder="1" onblur="checkRptorMolength(this.value);confirmSecondInput(this,'onblur');"></TD>
                <TD  class= title> ��������ϸ��ַ </TD>
                <TD  class= input colspan="3"> <Input class="wid" class= "common3" name=RptorAddress id=RptorAddress ><font size=1 color='#ff0000'><b>*</b></font></TD>
						</TR>
						
 			 			<TR  class= common>		
 			 					<TD  class= title> �������ʱ� </TD>
								<TD  class= input><Input class="wid" class=common name="AppntZipCode" id="AppntZipCode" elementtype=nacessary  verify="�������ʱ�|NOTNULL&LEN=6" verifyorder="1" onblur="checkziplength(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></TD>
 			 					<TD  class= title> ������������˹�ϵ</TD>
    	       		<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation onfocus= "getRptorInfo()" ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
								<TD  class= title> ���յص� </TD>
          	    <TD  class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>            
						</TR>
						<TR  class= common>
       		    <TD  class= title> �������� </TD>
          	  <TD  class= input> <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
			    		<TD  class= title> ��Ͻ���� </TD>
          	  <TD  class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
	        		<TD  class= title> ���������� </TD>
          	  <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
 						</TR>
            <TR  class= common>
        	    <TD  class= title> ���������� </TD>
          	  <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeType id=AssigneeType CodeData="0|^0|ҵ��Ա^1|�ͻ�^2|����" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])"  onclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName id=AssigneeTypeName readonly=true></TD>
        	    <TD  class= title> �����˴��� </TD>
          	  <TD  class= input> <Input class="wid" class= common name=AssigneeCode id=AssigneeCode onblur="queryAgent();"></TD>
 							<TD  class= title> ���������� </TD>
          	  <TD  class= input> <Input class="wid" class= common name=AssigneeName id=AssigneeName ></TD>            
						</TR>
            <TR  class= common>
        	    <TD  class= title> �������Ա� </TD>
          	  <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeSex id=AssigneeSex ondblclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,AssigneeSexName],[0,1]);"><input class=codename name=AssigneeSexName id=AssigneeSexName readonly=true></TD>
        	    <TD  class= title> �����˵绰 </TD>
          	  <TD  class= input> <Input class="wid" class= common name=AssigneePhone id=AssigneePhone ></TD>
							<TD  class= title> �����˵�ַ </TD>
          	  <TD  class= input> <Input class="wid" class= common name=AssigneeAddr id=AssigneeAddr ></TD>           
 					 </TR>
           <TR  class= common>
        	    <TD  class= title> �������ʱ� </TD>
          	  <TD  class= input> <Input class="wid" class= common name=AssigneeZip id=AssigneeZip ></TD>
           <!-- <TD  class= title> ��������</TD>
          	  <TD  class= input> <Input class= codeno value="1" name=RgtClass readonly ><input class=codename name=RgtClassName readonly=true value="����"></TD>-->
              <TD class= title> ��������</TD>
              <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font>-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#AcceptedDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AcceptedDate id="AcceptedDate"><span class="icon"><a onClick="laydate({elem: '#AcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>            
							<TD class= title> �ͻ���������</TD>
              <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="ApplyDate";"><font size=1 color='#ff0000'><b>*</b></font>-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#ApplyDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ApplyDate id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>           
			</TR>
        </table>
  	</Div><br><br>
    <!--�ͻ���Ϣ-->
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
                    <TD class= input> <Input class="readonly wid" readonly name=customerName id=customerName></TD>
                    <TD class= title> �ͻ����� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> �Ա� </TD>
                    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <!-- <TD class= title> VIP�ͻ���ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> �¹�����</TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE"  onblur="CheckDate(fm.AccidentDate);" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD class= title> ҽ�Ƴ������� </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>              
                    <TD class= title> ������������ </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#OtherAccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=OtherAccidentDate id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
               </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <!--TD class= input> <Input class=codeno name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD-->
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=hospital id=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onclick="showHospital(this.name,'TreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ����ϸ��</TD>
                    <!--TD class= input> <Input class=codeno name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName readonly=true></TD-->
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" ><input class=codename name=AccResult1Name id=AccResult1Name readonly=true><font size=1 color='#ff0000'><b>*</b></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true><font size=1 color='#ff0000'><b>*</b></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʶ</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"  onclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
                    <TD class= title> ������ʶ</TD>
                    <TD class= input> <Input class="wid" class=readonly name=ClaimFlag id=ClaimFlag readonly ></TD>
                </tr>
            </table>

        
     
            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"><span style="vertical-align:middle"> ���</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"><span style="vertical-align:middle"> �߲�</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"><span style="vertical-align:middle"> �ش󼲲�</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span style="vertical-align:middle">�м������ˡ����ۡ���Ҫ�����г�</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"><span style="vertical-align:middle"> ����</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">ҽ��</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"><span style="vertical-align:middle"> ���ּ���</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
					</td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                </tr>
                <TR class= common>
                    <TD style="padding-left:16px" colspan="6">
                        <span id="spanText3" style= "display: ''">
                            <textarea name="AccDesc" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD style="padding-left:16px" colspan="6">
                        <span id="spanText1" style= "display: ''">
                            <textarea name="Remark" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
            </table>
        </span>
    </div>
     <table>
            <tr>
                <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
 				<td><input class=cssButton name=QueryReportClm value="������ѯ�����" type=button onclick="queryLLReport()"></td>
 				<td><input class=cssButton name=QueryIssue value="�������ѯ" type=button onclick="queryLLIssue()"></td>
                <td><input class=cssButton name=QueryReport value="�¼���ѯ" type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
                </td>
                <!--td><input class=cssButton name=QueryCont1 VALUE="Ͷ������ѯ"  TYPE=button onclick=""></td-->
                <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
			          <!--<td><input type=hidden class=cssButton name=MedCertForPrt VALUE="��ӡ�˲м���֪ͨ"  TYPE=button onclick="PrintMedCert()"></td>-->
                <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>                
            </tr>
        </table>
        <!--<a href="javascript:void(0);" name=QueryPerson class="button" onClick="showInsPerQuery();">�ͻ���ѯ</a>
        <a href="javascript:void(0);" name=QueryReportClm class="button" onClick="queryLLReport();">������ѯ�����</a>
        <a href="javascript:void(0);" name=QueryIssue class="button" onClick="queryLLIssue();">�������ѯ</a>
        <a href="javascript:void(0);" name=QueryReport class="button" onClick="queryLLAccident();">�¼���ѯ</a>
        <a href="javascript:void(0);" name=addbutton class="button" onClick="saveClick();">��    ��</a>
        <a href="javascript:void(0);" name=QueryCont2 class="button" onClick="showInsuredLCPol();">������ѯ</a>
        <a href="javascript:void(0);" name=QueryCont3 class="button" onClick="showOldInsuredCase();">�����ⰸ��ѯ</a>
        <a href="javascript:void(0);" class="button" onClick="colBarCodePrint();">��ӡ������</a>
        <a href="javascript:void(0);" class="button" onClick="colQueryImage();">Ӱ���ѯ</a>-->
	   <hr class="line">
    <span id="operateButton3" >
		<table>
			<tr>
				<td><INPUT class=cssButton name="DoHangUp" VALUE="��������"  TYPE=button onclick="showLcContSuspend()" ></td>
				<td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
				<td><INPUT class=cssButton name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
			    <td><INPUT class=cssButton name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
			    <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="ҽ�Ƶ�֤¼��"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
			    <!--<td><INPUT class=cssButton disabled name="OutReciInfoInp" VALUE="�������¼��"  TYPE=button onclick="showWaiBao();"></td>-->
			    <td><INPUT class=cssButton name="ReciInfoInp" VALUE="�ռ�����Ϣ"  TYPE=button onclick="showReciInfoInp();"></td>
			</tr>
		</table>
	</span>
    <!--����������Ϣ*********************************Beg********************************************-->
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLRegisterConclusion);"></td>
            <td class=titleImg> ����������Ϣ </td>
		</tr>
    </table>
    <Div id= "divLLRegisterConclusion" style= "display: ''" class="maxbox1">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title> ��������</TD>
    	        <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onFocus="showDIV();"><input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title> ��������</TD>
            	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=rgtType id=rgtType CodeData="0|^11|��ͨ����^01|���װ���" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeTypeName],[0,1])"  onclick="return showCodeListEx('rgtType', [this,rgtTypeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName id=rgtTypeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>        
    	        <TD  class= title> </TD>
    	        <TD  class= input> </TD>
    	    </tr>
    	</table>
    	<span id= "spanConclusion1" style= "display: none">
        
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
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DutyKind);"></td>
                    <td class= titleImg>�������ͼ�����Ϣ</td>
                 </tr>
            </table>
            
            <Div  id= "DutyKind" style= "display:''">
                 <Table  class= common>
                      <tr><td text-align: left colSpan=1>
                           <span id="spanDutyKindGrid"></span>
                      </td></tr>
                 </Table>
            </div>
            
                    
            <!--���ձ����������ͼ�����Ϣ-->
        	<table>
        	     <tr>
        	        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyKind);"></td>
        	        <td class= titleImg>����������Ϣ</td>
        	     </tr>
        	</table>
        	<Div  id= "PolDutyKind" style= "display:''">
        	     <Table  class= common>
        	          <tr><td text-align: left colSpan=1>
        	               <span id="spanPolDutyKindGrid"></span>
        	          </td></tr>
        	     </Table>
        	</div>
            
            
            <!--����ƥ����Ϣ-->
            <table>
                 <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyCode);"></td>
                    <td class= titleImg>���������Ϣ</td>
                 </tr>
            </table>
            <Div  id= "PolDutyCode" style= "display:''">
                <Table  class= common>
                    <tr>
                        <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
                    </tr>
                </Table>
            </div>
            <br>
            <!--Ԥ����������Ϣ-->
            <table class= common>
                <TR class= common>
                    <!-- <TD  class= title> Ԥ�����</TD> -->
                    <TD  class= input> <Input class=common  type=hidden readonly name=standpay onblur="checkAdjPay();"></TD>
                    <!-- <TD  class= title> ����Ϊ</TD> -->
                    <TD  class= input> <Input class=common type=hidden name=adjpay onblur="checkAdjPay();"></TD>  
			   </tr>
            </table>
        </span>
        <span id= "spanConclusion2" style= "display: none">
            <table class= common>
                <TR class= common>
                	<TD  class= title> ��������ԭ��</TD>
                	<TD  class= input> <Input class=codeno name=NoRgtReason ondblclick="return showCodeList('llnorgtreason',[this,NoRgtReasonName],[0,1]);" onkeyup="return showCodeListKey('llnorgtreason',[this,NoRgtReasonName],[0,1]);"><input class=codename name=NoRgtReasonName readonly=true></TD>
                	<TD  class= title> </TD>
                	<TD  class= input> </TD>
                	<TD  class= title> </TD>
                	<TD  class= input> </TD>
                </tr>
            </table>
        </span>
    </DIV>
    <table>
        <tr>
            <td><INPUT class=cssButton name="conclusionSave" VALUE="���۱���"  TYPE=button onclick="firstSaveConclusionClick()"></td>
            <td><INPUT class=cssButton name="dutySet" VALUE="ƥ�䲢����"  TYPE=hidden onclick="showMatchDutyPay();"></td>
            <td><INPUT class=cssButton name="medicalFeeCal" VALUE="��֤������Ϣ"  TYPE=button onclick="showLLMedicalFeeCal();" ></td>
            <td><INPUT class=cssButton name="printPassRgt" VALUE="��ӡ�������ǩ���嵥"  TYPE=button onclick="prtPassRgt()" ></td>
            <td><INPUT class=cssButton name="printNoRgt" VALUE="��ӡ��������֪ͨ��"  TYPE=button onclick="prtNoRgt()" ></td>
        </tr>
    </table>
    <!--*****************************************end************************************************-->
    <hr class="line">
    <table>
        <tr>
            <td><INPUT class=cssButton name="RgtConfirm" VALUE="����ȷ��"  TYPE=button onclick="RegisterConfirm()"></td>
            <td><INPUT class=cssButton name="BatchCreateNote" VALUE="��֤������ӡ����"  TYPE=button onclick="showPrtControl()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="top.close();"></td>
        </tr>
    </table>
    <%
    //������,������Ϣ��
    %>
    <input type=hidden name=hideOperate value="">
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
    <input type=hidden id="isRegisterExist" name="isRegisterExist"><!--�Ƿ�Ϊ��������-->

    <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
    <Input type=hidden id="BaccDate" name="BaccDate"><!--��̨���¹��������ڣ�ÿ�β�ѯʱ���£�У��ʱʹ��-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
    <input type=hidden id="clmState" name="clmState"><!--����״̬-->
    <Input type=hidden id="RgtClass" name=RgtClass value="1" >
    <input type=hidden id="RgtClassName" name=RgtClassName value="����">

    <input type=hidden id="PrtSeq" name="PrtSeq"><!--��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�-->
     
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
