<%
//**************************************************************************************************
//Name��LLGrpReportInput.jsp
//Function�����屨���Ǽ�
//Author��zhangzheng
//Date: 2008-10-25
//Desc: 
//**************************************************************************************************
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
  String CurrentDate = PubFun.getCurrentDate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");

  String tClmNo = request.getParameter("claimNo");  //�ⰸ��
  String tIsNew = request.getParameter("isNew");  //������
  String tIsClaimState =request.getParameter("isClaimState"); //�ж���������״̬��ѯ�Ľڵ�
  String trgtstate =request.getParameter("rgtstate"); //�������� 
  loggerDebug("LLGrpReportInput","LLGrpReportInput.jsp����Rgtstate:"+trgtstate);
  
  String tMissionID = request.getParameter("MissionID");  //����������ID
  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID  
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
  <SCRIPT src="LLGrpReport.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="LLGrpReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form action="./LLGrpReportSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--������Ϣ-->
  <table>
  <TR>
    <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>������Ϣ</TD>
  </TR>
  </table>

  <Div id= "divLLReport1" style= "display: ''" class="maxbox1">
   
    <TR class= common>
      <TD class= title> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD align=left>
      <span id="spanText7" style= "display: ''">
      <input type="radio" value="11" name="rgtstate" onclick="callrgtstate('11');" > ��ͨ�������� </input>
      <input type="radio" value="03" name="rgtstate" onclick="callrgtstate('03');" > ������������ </input>
      <input type="radio" value="02" name="rgtstate" onclick="callrgtstate('02');" > �ʻ��������� </input>      
      </span> 
    </TR>
  
  </Div> 
  
  <table class= common>    
    <TR class= common>
	    <TD class= title> ���屣���� </TD>
	    <TD class= input> <Input class="wid" class=common  name=GrpContNo id=GrpContNo onkeyup="queryCustomerIn()" ><font size=1 color='#ff0000'><b>*</b></font></TD>
	    <TD class= title> ����ͻ��� </TD>
	    <TD class= input> <Input class="wid" class=common  name=GrpCustomerNo id=GrpCustomerNo ><font size=1 color='#ff0000'><b>*</b></font></TD>   
	    <TD class= title> ��λ���� </TD>
	    <TD class= input> <Input class="wid" class=common  name=GrpName id=GrpName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
  </table> 
        
    <table class= common>
    <TR class= common>
	<TD class= title> ������������˹�ϵ </TD>
    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation onfocus= "getRptorInfo()" ondblclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,1);"onclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,1);"  onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> ���������� </TD>
    <TD class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> �����˵绰 </TD>
    <TD class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone ></TD>  
    </TR>     
    </table>
    
   <Div id="divreport1" style="display:''"> 
    <table class= common>    
    <TR class= common>
    <TD class= title> ������ͨѶ��ַ </TD>
    <TD class= input> <Input class="wid" class= common name=RptorAddress id=RptorAddress ></TD>    
    <TD class= title> ������ʽ </TD>
    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="RptMode" id="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" id="RptModeName" value="�绰����" readonly=true></TD>
    <TD class= title> ���յص� </TD>
    <TD class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>  
    </TR>
    </table>
   </Div> 
    
    <table class= common>
     <TR class= common>

	  <TD class= title> �¹�����</TD>
      <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD> 
    
      <TD class= title> ����ԭ��</TD>
      <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
         
      <TD class= title> ���ֱ��� </TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=RiskCode id=RiskCode ondblclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onkeyup="return showCodeListKey('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');"></TD>
    
       </TR>
   </table>

    <table class= common>
     <TR class= common>

       <TD class= title> �¼��� </TD> 
       <TD class= input><Input class="wid" class= "readonly" readonly name=AccNo id=AccNo></TD>

       <TD class= title> ������ </TD>
       <TD class= input> <Input class="wid" class="readonly" readonly  name=RptNo id=RptNo ></TD>

       <TD class= title> ��Ͻ���� </TD>
       <TD class= input> <Input class="wid" class= "readonly" readonly name=MngCom id=MngCom></TD>
      </TR>
   </table>

   <table class= common>
     <TR class= common>
       <TD class= title> ���������� </TD>
       <TD class= input> <Input class="wid" class="readonly" readonly name=Operator id=Operator ></TD>
     
       <TD class= title> �������� </TD>
	    <TD class= input>  <input class="wid" class="readonly" readonly name="RptDate" id="RptDate" ></TD>
	    
	    <TD class= title> ��������</TD>
        <!--<TD class= input> <Input class= codeno value="1" name=RgtClass ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class=codename name=RgtClassName readonly=true value="����"></TD>-->
        <TD class= input>  <input class="wid" class="readonly" readonly name="RgtClassName" id="RgtClassName" value='����'></TD>
     </TR>
   </table>

   <Div id="divreport5" style="display:none">     
    <table class= common>
     <TR class= common>
      <TD class= title> �Ƿ�ʹ�������ʻ����</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccFlag id=AccFlag CodeData="0|3^10|��^20|��" ondblclick="return showCodeListEx('AccFlag', [this,AccFlagName],[0,1])" onclick="return showCodeListEx('AccFlag', [this,AccFlagName],[0,1])"onkeyup="return showCodeListKeyEx('AccFlag', [this,AccFlagName],[0,1])" ><Input class=codename name=AccFlagName id=AccFlagName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD class= title></TD>
      <TD class= input></TD>
      <TD class= title></TD>
      <TD class= input></TD>
     </TR>
    </table>
   </Div>     

   
  
  


  <!--��������Ϣ-->
  <table class= common>
  <tr class= common>
    <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
  </tr>
  </table>
          <table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
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
      <TD class= title> �ͻ��Ա� </TD>
      <TD class= input> <Input type=hidden readonly name=customerSex><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
    </TR>
    <TR class= common>
      <TD class= title> ��������</TD>
      <TD class= input> <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font>-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD> 
      <TD class= title> �������</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
	  <!-- <TD class= title> VIP�ͻ���ʾ</TD>
      <TD class= input> <Input class="readonly" readonly name=IsVip></TD> -->
      
<!--       <TD class= title> ����ԭ��</TD>
      <TD class= input><Input class=codeno name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD> -->
      <TD style= "display: none" class= title> ���ս��1</TD>
      <TD style= "display: none" class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResultName],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResultName],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
    </TR>
   </table> 
   
   <Div id= "divreport2" style= "display: ''">
   <table class= common>
    <TR class= common>
      <span id="spanText8" style= "display: none">
      <table>
      
      
      <!--TD class= title> ���ս��2</TD>
      <TD class= input> <Input class=codeno name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name readonly=true></TD-->
      </table>
      </span>
    </TR>
    </table>
    
   
   
    <!--TR class= common>
      <TD class= title> ������ʶ</TD>
      <TD class= input> <Input class=codeno name=IsDead ondblclick="return showCodeList('llDieFlag',[this,IsDeadName],[0,1]);" onkeyup="return showCodeListKey('llDieFlag',[this,IsDeadName],[0,1]);"><input class=codename name=IsDeadName readonly=true></TD>
    </tr-->
    
    
    <table class= common>
    
    <TR class= common>
      <TD class= title> ����ϸ�ڱ���</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"></TD>  
        <TD class= title> ���ս������</TD>
      <TD class= input> <Input class=code name=AccResult2 ondblclick="return showCodeList('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"></TD>
        <TD class= title> ����ҽԺ����</TD>
      <TD class= input> <Input class=code name=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"></TD>    </TR></table>
        <table class= common>
       <TR class= common>
      <TD class= title> ����ϸ�� </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText5" style= "display: ''">
        <textarea name="accidentDetailName" id="accidentDetailName" cols="196" rows="4" witdh=25% class="common" ></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <!--<span id="spanText6" style= "display: 'none'">
        <textarea disabled name="accidentDetailNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </TR>

    <TR class= common>
     
      <TD class= title> ���ս��</TD>
      
      </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText9" style= "display: ''">
        <textarea name="AccResult2Name" id="AccResult2Name" cols="196" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(4)"></textarea>
      </span>
      </TD>
    </TR>     
    
    <TR class= common>
          
      <TD class= title> ����ҽԺ</TD></tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      
      <span id="spanText5" style= "display: ''">
        <textarea name="TreatAreaName" id="TreatAreaName" cols="196" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(2)"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <!--<span id="spanText6" style= "display: 'none'">
        <textarea disabled name="TreatAreaNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </TR>
  
    </table>
  </Div> 

    <table class= common>
    <TR class= common>
      <TD class= title> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD align=left>
      <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">���</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">�߲�</span> </input> 
      <input style="vertical-align:middle" type="checkbox" value="04" name="claimType"> <span style="vertical-align:middle">�ش󼲲�</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="01" name="claimType"> <span style="vertical-align:middle">�м������ˡ����ۡ���Ҫ�����г�</span> </input>
<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input> -->
      <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"><span style="vertical-align:middle"> ҽ��</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" ><span style="vertical-align:middle">���ּ���</span> </input>
<!--      <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
      </td>
    </TR>
    </table>
    
    <Div id= "divreport3" style= "display: ''">
    <table class= common>
    <TR  class= common>
      <TD  class= title> �¹����� </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px"> 
      <span id="spanText3" style= "display: ''">
        <textarea name="AccDesc" id="AccDesc" cols="196" rows="4" witdh=25% class="common"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <!--<span id="spanText4" style= "display: 'none'">
        <textarea disabled name="AccDescDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </tr>

    <TR class= common>
      <TD class= title> ��ע </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText1" style= "display: ''">
        <textarea name="Remark" id="Remark" cols="196" rows="4" witdh=25% class="common"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <!--<span id="spanText2" style= "display: 'none'">
        <textarea disabled name="RemarkDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </tr>
    </table>
   </Div>    
    
  </span>
  </div>
   <table>
    <tr>
    <td><input class=cssButton  name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
    <td><input class=cssButton  name=QueryReport value="�¼���ѯ" type=button onclick="queryLLAccident()"></td>
    <td>
      <span id="operateButton2" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
      <span id="operateButton1" style= "display: none"><input class=cssButton name=deletebutton  VALUE="ɾ  ��"  TYPE=button onclick="deleteClick()" --></span>
      <span id="operateButton3" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
    </td>
    <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
    <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td> 
    <td><input class=cssButton name=QueryCont5 VALUE="�����ⰸ����" TYPE=button onclick="getLastCaseInfo()"></td>   
    <td><input class=cssButton type=button name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
    <td><input class=cssButton type=button name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
    <td><input class=cssButton type=hidden name=QueryCont4 VALUE="������Ϣ��ѯ" TYPE=button onclick="showCard()"></td>
    <td><input class=cssButton type=hidden name=MedCertForPrt VALUE="��ӡ�˲м���֪ͨ"  TYPE=button onclick="PrintMedCert()"></td>
    </tr>
  </table>
  <span id="operateButton4" >
  <table>
    <tr>
    <td><input class=cssButton name=QueryReport2 value="���Ԥ�����" type=button onclick="operStandPayInfo()"></td>
    <td><INPUT class=cssButton type=hidden name="DoHangUp" VALUE="��������"  TYPE=button onclick="showLcContSuspend()" ></td>
    <td><INPUT class=cssButton name="CreateNote" VALUE="������������"  TYPE=button onclick="showAffix()"></td>
    <td><INPUT class=cssButton name="CreateNote2" VALUE="��ӡ��������"  TYPE=button onclick="showPrtAffix()"></td>
    <td><INPUT class=cssButton name="BeginInq" VALUE="�������"  TYPE=button onclick="showBeginInq()"></td>
    <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
    <td><INPUT class=cssButton name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
    <td><INPUT class=cssButton type=hidden name="Condole" VALUE="����ο��"  TYPE=button onclick="showCondole()"></td>   
    </tr>
  </table>
  <table>
    <tr>
    <td><INPUT class=cssButton name="SubmitReport" VALUE="����ʱ�"  TYPE=button onclick="showBeginSubmit()"></td>
    <td><INPUT class=cssButton name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
    <td><INPUT class=cssButton type=hidden name="strcondoleflag" VALUE="0"  TYPE=button ></td>
    <td><input class=cssButton VALUE="�������ο�����Ϣ" TYPE=button onclick="ctrlclaimduty();"></td>
    </tr>
  </table>

  </span>
  <hr class="line">
  <table>
  <tr>
    <td><INPUT class=cssButton name="RptConfirm" VALUE="����ȷ��"  TYPE=button onclick="reportConfirm()"></td>
    <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
  </tr>
  </table>
  <%
  //������,������Ϣ��
  %>
  <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->
  <Input type=hidden id="IDNo" name="IDNo"><!--�����˴���-->
  <Input type=hidden id="IDType" name="IDType"><!--�����˴���-->
  <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
  <input type=hidden id="Peoples" name="Peoples"><!--Ͷ��������-->
  <input type=hidden id="RgtClass" name="RgtClass" value='2'><!--��������:1:���ˣ�2:����-->
  <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
  <Input type=hidden id="ManageCom" name="ManageCom"><!--��½��վǰ��������-->
  <Input type=hidden id="AllManageCom" name="AllManageCom"><!--��½��վ-->
  <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
  <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
  <input type=hidden id="isClaimState" name= "isClaimState"><!--�ж���������״̬��ѯ�Ľڵ�-->
  
  <!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
  <input type=hidden id="PrtSeq" name="PrtSeq">
  <input type=hidden id="RgtClass" name="RgtClass" value = "2"><!--���ո��մ���-->
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <Input type=hidden id="BaccDate" name="BaccDate"><!--��̨���¹��������ڣ�ÿ�β�ѯʱ���£�У��ʱʹ��-->
  
  <input type=hidden id="tSQL" name= "tSQL">

</form>
<Div id="divreport4" style="display:none">
<form action="./GrpCustomerDiskReportSave.jsp" method=post name=fmload target="fraSubmit" ENCTYPE="multipart/form-data">    
  <table>
  <TR>
    <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,air);"></TD>
    <TD class= titleImg>�������</TD>
  </TR>
  </table>
  <Div  id= "air" style= "display: ''" class="maxbox1">
  <table class=common>
      <TD class=common >�ļ���ַ:</TD>
      <TD class=common >
        <Input type="file" name=FileName size=20>
        <input name=ImportPath type= hidden>
        <input class=common name=BatchNo type=hidden>
        <INPUT class=cssButton VALUE="��  ��" TYPE=button name="PrintIn" onclick="getin();">
        <a href="./template/GrpReportCustomer.xls">����[����������]����ģ��</a>
      </TD>    
     <input type=hidden name=ImportFile>
  </table>
      <br>
      <TR class= common>                            
          <TD class=title><font  color='#ff0000'><b>*ʹ�õ��빦��������д������Ϣ�����������ֻ�����������Ϣ��</b></font> </TD>
		  </TR> 

 <!--<Div id="divDiskErr" style="display: 'none'" align = center>
    <table class=common border=0 width=100%>
      <tr>
        <td class=titleImg align=center>�����˵��������Ϣ��ѯ���������ѯ����:</td>
      </tr>
    </table>
    <table class=common align=center>
      <TR class=common>
        <TD class=title>������</TD>
        <TD class=input>
          <Input class=common name=tRgtNo>
        </TD>
        <TD class=title>�����ļ���</TD>
        <TD class=input>
          <Input class=common name=tBatchNo>
        </TD>  
      </TR> 
      <TR class=common> 
        <TD class=title>�ͻ���</TD>
        <TD class=input>
          <Input class=common name=tCustomerNo>
        </TD>      
        <TD class=title>�ͻ�����</TD>
        <TD class=input>
          <Input class=common name=tCustomerName>
        </TD>    
      </TR>
      <TR class=common> 
        <TD class=input>
    		<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
        </TD>   
      </TR>
    </table> 
      <Table class=common>
        <TR class=common>
          <TD text-align: left colSpan=1>
            <span id="spanDiskErrQueryGrid"></span>
          </TD>
        </TR>
      </Table>
      <Table align = center>
      <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();">
      <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();">
      <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();">
      <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();">
      </Table>
    </Div>	 -->
    
</form>
</Div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>
</html>
