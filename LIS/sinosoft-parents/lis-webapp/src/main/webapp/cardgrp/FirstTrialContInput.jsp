<%@include file="../common/jsp/UsrCheck.jsp"%>
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
	String prtNo = "";
	String ManageCom  ="";
	String MissionID  ="";
	String SubMissionID  ="";
	try
	{
		prtNo = request.getParameter("prtNo");
    ManageCom = request.getParameter("ManageCom");
    MissionID = request.getParameter("MissionID");
    SubMissionID = request.getParameter("SubMissionID");
	}
	catch( Exception e )
	{ 
	}
 
//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
// 1 -- ����Ͷ����ֱ��¼��
// 2 -- �����¸���Ͷ����¼��
// 3 -- ����Ͷ������ϸ��ѯ
// 4 -- �����¸���Ͷ������ϸ��ѯ
// 5 -- ����
// 6 -- ��ѯ
// 7 -- ��ȫ�±�����
// 8 -- ��ȫ����������
// 9 -- ������������
// 10-- ��������
// 99-- �涯����

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//Ĭ�������Ϊ���˱���ֱ��¼��
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
        loggerDebug("FirstTrialContInput","LoadFlag:" + tLoadFlag);
%>
<script>
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>"; 
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";	
	var type = "<%=request.getParameter("type")%>";
	//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	if (ScanFlag == "null") ScanFlag = "0";
	//��ȫ���ûᴫ���ֹ���
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//�������ģ����ô���
	var LoadFlag ="<%=tLoadFlag%>"; //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="FirstTrialContInputInit.jsp"%>
  <SCRIPT src="FirstTrialContInput.js"></SCRIPT>
  <SCRIPT src="ProposalAutoMove.js"></SCRIPT>
    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

  <%if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){%>
  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
  <%}%> 

 
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./FirstTrialContSave.jsp" method=post name=fm target="fraSubmit">


    <!-- ��ͬ��Ϣ���� ContPage.jsp-->
     
<DIV id=DivLCContButton STYLE="display:''">
<table id="table1">
		<tr>
			<td>
			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
			</td>
			<td class="titleImg">��ͬ��Ϣ
			</td>
		</tr>
</table>
</DIV>
<div id="DivLCCont" STYLE="display:''">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title">Ͷ������ 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<Input class= common name=PrtNo elementtype=nacessary readonly MAXLENGTH="11" verify="ӡˢ����|notnull&len=11">
    		</td>
			<td CLASS="title">������� 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="ManageCom" VALUE MAXLENGTH="10" elementtype=nacessary CLASS="code" ondblclick="return showCodeList('comcode',[this],null,null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull">
    		</td>
		</tr>
		<tr CLASS="common">
			<td CLASS="title">�������� 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="SaleChnl" VALUE MAXLENGTH="2" CLASS="code" elementtype=nacessary ondblclick="return showCodeList('AgentType',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this],null,null,null,null,1);">
    		</td>
			<td CLASS="title">�����˱��� 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="AgentCode" VALUE MAXLENGTH="10" CLASS="code" elementtype=nacessary ondblclick="return queryAgent();" onkeyup="return queryAgent2();">
    		</td>
			<td CLASS="title">��������� 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="AgentGroup" VALUE CLASS="common" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="���������|notnull">
    		</td>
		</tr>


	</table>
	<table class=common>
         <TR  class= common> 
           <TD  class= common>  ��    ע </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Remark" cols="110" rows="2" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
</div>
<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- Ͷ������Ϣ���� -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
Ͷ������Ϣ(�ͻ���)��<Input class= common  name=AppntNo >
<INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">(�״�Ͷ���ͻ�������д�ͻ��ţ�
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display:''">
 <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=AppntName elementtype=nacessary verify="Ͷ��������|notnull&len<=20" >
          </TD>
                    <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" verify="Ͷ����֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntIDNo" verify="Ͷ����֤������|len<=20" onblur="checkidtype();getBirthdaySexByIDNo(this.value);" >
          </TD> 
        </TR>
        <TR  class= common>
       <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" elementtype=nacessary name="AppntBirthday" verify="Ͷ���˳�������|notnull&date" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex elementtype=nacessary verify="Ͷ�����Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
      
        </TR>
     


      </table>   
</DIV>
   <Div  id= "divButton" style= "display: ''">
<span id="operateButton" >
	<table  class=common align=center>
		<tr align=left>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="submitForm();">
			</td>
			<!--
			<td class=button width="10%" align=right>
				<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			</td>			
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
			</td>			
			<td class=button width="10%" align=right>
				<INPUT class=cssButton VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
			</td>
			-->
		</tr>
	</table>
</span>
    <%@include file="../common/jsp/InputButton.jsp"%>
  </DIV>
<br>
 <DIV id=DivLCImpart STYLE="display:''">
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
            </td>
            <td class= titleImg>
                ��������Ϣ�б�
            </td>
        </tr>
    </table>
    
	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>					
</div>
<br>
<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- Ͷ������Ϣ���� -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
��������Ϣ(�ͻ���)��<Input class= common  name=InsuredNo >
<INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryInsuredNo();">(�״�Ͷ���ͻ�������д�ͻ��ţ�
<Div  id= "divSamePerson" style="display:''">
<font color=red>
��Ͷ����Ϊ�������˱��ˣ������������ѡ��
<INPUT TYPE="checkbox" NAME="SamePersonFlag" onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>
<DIV id=DivLCInsuredInd STYLE="display:''">
 <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=InsuredName elementtype=nacessary verify="Ͷ��������|notnull&len<=20" >
          </TD>
                    <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="InsuredIDType" verify="Ͷ����֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name="InsuredIDNo" verify="Ͷ����֤������|len<=20" onblur="checkidtype1();getBirthdaySexByIDNo1(this.value);">
          </TD>  

        </TR>
        
        <TR  class= common>
         <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" elementtype=nacessary name="InsuredBirthday" verify="Ͷ���˳�������|notnull&date" >
          </TD>   
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=InsuredSex elementtype=nacessary verify="Ͷ�����Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
  
        </TR>
       

        </TR>        
      </table>   
</DIV>
   <Div  id= "divInputContButton" style= "display: ''" style="float: left">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="��ӱ�����" TYPE=button onClick="AddInsured();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="ɾ��������" TYPE=button onClick="DelInsured();">
    </DIV>
    <br>
    <br>

    
 </DIV>     
    <br>
    <Div  id= "divInputContButton" style= "display: ''" style="float: right">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="���¼��" TYPE=button onClick="showHealth();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="����ӡ" TYPE=button onClick="PrintHealth();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="����¼��" TYPE=button onClick="showReport();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="������ӡ" TYPE=button onClick="PrintReport();">
      <INPUT class=cssButton id="Donextbutton1" VALUE="�������" TYPE=button onclick="inputConfirm();">
    </DIV>

  <div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('11');" class=cssButton>
	<input type="button" name="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class=cssButton>	
      <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="top.close();">
        <input type=hidden id="" name="autoMoveValue">   
        <input type=hidden id="" name="pagename" value="11">                         
      </div>    
    
    <Div  id= "HiddenValue" style= "display:'none'" style="float: right"> 
    	<input type=hidden id="fmAction" name="fmAction">	
    	<input type=hidden id="stype" name="stype">	
			<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
			<INPUT  type= "hidden" class= Common name= ContNo value= "">
			<input type=hidden id="MissionID" name="MissionID">
			<input type=hidden id="SubMissionID" name="SubMissionID">
			<input type=hidden id="PrtSeq" name="PrtSeq">
    </DIV>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
