<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var prtNo ="<%=request.getParameter("prtNo")%>";
var polNo ="<%=request.getParameter("polNo")%>";
var scantype ="<%=request.getParameter("scantype")%>";
var MissionID ="<%=request.getParameter("MissionID")%>";
var ManageCom ="<%=request.getParameter("ManageCom")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";

var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
var GrpContNo="<%=request.getParameter("GrpContNo")%>";
var tManageCom = "<%=tGI.ManageCom%>";
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="SubContPolInit.jsp"%>
  <SCRIPT src="SubContPolInput.js"></SCRIPT>
  <SCRIPT src="ProposalAutoMove.js"></SCRIPT>
  
</head>

<body  onload="initForm('<%=tGrpContNo%>', '<%=tPrtNo%>');initElementtype();" >
  <form action="./SubContPolSave.jsp" method=post name=fm target="fraSubmit">
   
  <DIV id=DivLCContButton STYLE="display:''">
  <table id="table1">
  		<tr>
  			<td>
  			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
  			</td>
  			<td class="titleImg">�����ͬ��Ϣ
  			</td>
  		</tr>
  </table>
</DIV>
     
    <Div  id= "divGroupPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
	 
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input8>
            <Input class=common8 readonly name=GrpContNo >
          </TD>
          <TD  class= title8>
            ӡˢ����
          </TD>
          <TD  class= input8>
            <Input class= common8 name=PrtNo readonly >
          </TD>
        </TR>
      </table>
    </Div>
    
 	<Div  id= "divGeneral" style= "display: ''">
      <table class= common>
	   	<tr>
    	 <td text-align: left colSpan=1>
			<span id="spanGeneralGrid" >
		 </span> 
		</td>
       </tr>
      </table>
	   <TD>
	   </TD>
        <TR  class= common8>
          <TD  class= title8>
            �������
          </TD>
          <TD  class= input8>
            <Input class="code8" id = execom name=ExecuteCom verify="�������|notnull" ondblclick="return showCodeList('comcode2',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('comcode2',[this],null,null,null,null,1);">
          </TD>
        </TR>
	</Div>

	<DIV id = "divGrpCustomer" style = "display: ''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol2);">
    		</td>
    		<td class= titleImg>
    			 �ֵ�Ͷ����λ���ϣ��ͻ��� <Input class= common8 maxlength=10 name=CustomerNo > <INPUT id="butGrpNoQuery" class=cssButton VALUE="��ѯ" TYPE=button onclick="showAppnt();"> ��
    		</td>
    	</tr>
    </table>
    

	<Div  id= "divGroupPol2" style= "display: ''">
      <table  class= common>
       <TR>
          <TD  class= title8>
            ��λ����
          </TD>
          <TD  class= input8>
            <Input class= common8 name=GrpName elementtype=nacessary  verify="��λ����|notnull&len<=60">
          </TD>
          <TD  class= title8>
            ��λ��ַ
          </TD>
          <TD  class= input8>
            <Input class= common8 name=GrpAddress  elementtype=nacessary verify="��λ��ַ|notnull&len<=60">
          </TD>
          <TD  class= title8>
            ��������
          </TD>
          <TD  class= input8>
            <Input class= common8 name=GrpZipCode  elementtype=nacessary  verify="��������|notnull&zipcode">
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title8>
            ��λ����
          </TD>
          <TD  class= input8>
            <Input class=code8 name=GrpNature  elementtype=nacessary verify="��λ����|code:GrpNature&notnull&len<=10" ondblclick="showCodeList('GrpNature',[this]);" onkeyup="showCodeListKey('GrpNature',[this]);">
          </TD>
      
          <TD  class= title8>
            ��ҵ���
          </TD>
          <TD  class= input8>
            <Input class="code" name=BusinessType elementtype=nacessary verify="��ҵ���|code:BusinessType&notnull&len<=20" ondblclick="return showCodeList('BusinessType',[this]);" onkeyup="return showCodeListKey('BusinessType',[this]);">
          </TD>
          <TD  class= title8>
            Ա������
          </TD>
          <TD  class= input8>
            <Input class= common8 name=Peoples  elementtype=nacessary verify="��λ������|notnull&int">
          </TD>       
        </TR>
        
   
        <TR  class= common>
          <TD  class= title8 >
            ������ϵ��
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title8>
            ����
          </TD>
          <TD  class= input8>
            <Input class= common8 name=LinkMan1 elementtype=nacessary verify="������ϵ��һ����|notnull&len<=10">
          </TD>
          <TD  class= title8>
            ��ϵ�绰
          </TD>
          <TD  class= input8>
            <Input class= common8 name=Phone1 elementtype=nacessary verify="������ϵ��һ��ϵ�绰|NUM&notnull&len<=30">
          </TD>
          <TD  class= title8>
            E-MAIL
          </TD>
          <TD  class= input8>
            <Input class= common8 name=E_Mail1 verify="������ϵ��һE-MAIL|Email&len<=60">
          </TD>           
        </TR>
     
          
		
        
      </table>
      
    </Div>
    <div id= "tt" style= "display: 'none'">
    	<table class=common>
         <TR  class= common> 
           <TD  class= title8> ��ע </TD>
         </TR>
         <TR  class= common>
           <TD  class= title8>
             <textarea name="Remark" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
         <TR  class= common>
          <TD  class= title8>
            ���ʽ
          </TD>
          <TD  class= input8>
            <Input class="code8" name=GetFlag verify="���ʽ|code:PayMode" ondblclick="return showCodeList('PayMode',[this]);" onkeyup="return showCodeListKey('PayMode',[this]);">
          </TD>
          <TD  class= title8>
            ��������
          </TD>
          <TD  class= input8>
            <Input class=code8 name=BankCode verify="��������|code:bank&len<=24" ondblclick="showCodeList('bank',[this]);" onkeyup="showCodeListKey('bank',[this]);">
          </TD>
          <TD  class= title8>
            �ʺ�
          </TD>
          <TD  class= input8>
            <Input class= common8 name=BankAccNo verify="�ʺ�|len<=40">
          </TD>       
        </TR>
        <TR  class= common>
          <TD  class= title8>
            ������ϵ�˶�
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title8>
            ����
          </TD>
          <TD  class= input8>
            <Input class= common8 name=LinkMan2 verify="������ϵ�˶�����|len<=10">
          </TD>
          <TD  class= title8>
            ��ϵ�绰
          </TD>
          <TD  class= input8>
            <Input class= common8 name=Phone2 verify="������ϵ�˶���ϵ�绰|NUM&len<=30">
          </TD>
          <TD  class= title8>
            E-MAIL
          </TD>
          <TD  class= input8>
            <Input class= common8 name=E_Mail2 verify="������ϵ�˶�E-MAIL|Email&len<=60">
          </TD>
        </TR>  
        <TR  class= common>
          <TD  class= title8>
            ��λ�ܻ�
          </TD>
          <TD  class= input8>
            <Input class= common8  name=Phone elementtype=nacessary >
          </TD>
      
          <TD  class= title8>
            ��λ����
          </TD>
          <TD  class= input8>
            <Input class= common8 name=Fax >
          </TD>
          <TD  class= title8>
            ����ʱ��
          </TD>
          <TD  class= input8>
            <!-- Input class="coolDatePicker" dateFormat="short" name=FoundDate verify="����ʱ��|date" -->
            <Input class="coolDatePicker" dateFormat="short" name=FoundDate>
          </TD>       
        </TR>
    </table>
    </div>
	</DIV>
<br><br>
	<Div id= "divSeperateSave" style= "display: ''" align= right>
      <INPUT VALUE="��  ��" class=cssButton TYPE=button name=addbutton onclick="appendOne();">
      <!--INPUT VALUE="��  ��" class=cssButton TYPE=button name=modibutton onclick="updateOne();"-->
      <INPUT VALUE="ɾ  ��" class=cssButton TYPE=button name=delbutton onclick="deleteOne();">
      <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
    </Div> 
           
      <input type=hidden id="fmAction" name="fmAction">      
      <Input type=hidden name=GrpAddressNo>
      <Input type=hidden name=GrpProposalContNo>
          
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
