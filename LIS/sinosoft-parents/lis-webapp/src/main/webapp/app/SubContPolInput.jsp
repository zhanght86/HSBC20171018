<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<script>
var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="SubContPolInit.jsp"%>
  <SCRIPT src="SubContPolInput.js"></SCRIPT>
  <SCRIPT src="ProposalAutoMove.js"></SCRIPT>
  
</head>

<body  onload="initForm('<%=tGrpContNo%>', '<%=tPrtNo%>');initElementtype();" >
  <form action="./SubContPolSave.jsp" method=post name=fm id=fm target="fraSubmit">
   <Div  id= "divButton" style= "display:  " align=left >
<!-- 	<%@include file="../common/jsp/OperateButton.jsp"%><%@include file="../common/jsp/InputButton.jsp"%>  -->
  </DIV>
  <DIV id=DivLCContButton STYLE="display: ">
  <table id="table1">
  		<tr>
  			<td class=common>
  			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
  			</td>
  			<td class="titleImg">�����ͬ��Ϣ
  			</td>
  		</tr>
  </table>
</DIV>
     
    <Div  id= "divGroupPol1" class=maxbox1 style= "display:  ">
      <table  class= common>
        <TR  class= common>
	 
          <TD  class=title5>
            ����Ͷ��������
          </TD>
          <TD  class=input5>
            <Input class="common wid" readonly name=GrpContNo id=GrpContNo>
          </TD>
          <TD  class=title5>
            ӡˢ����
          </TD>
          <TD  class=input5>
            <Input class="common wid" name=PrtNo id=PrtNo readonly >
          </TD>
        </TR>
      </table>
    </Div>
    
 	<Div  id= "divGeneral" style= "display:  ">
      <table class= common>
	   	<tr>
    	 <td style="text-align:left" colSpan=1>
			<span id="spanGeneralGrid" >
		 </span> 
		</td>
       </tr>
      </table>
	  <table class=common>
        <TR  class=common>
          <TD  class=title5>
            �������
          </TD>
          <TD  class=input5>
            <Input class="code" id = execom name=ExecuteCom  verify="�������|code:comcode&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" 
			onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);">
          </TD>
		  <TD class=title5></TD>
          <TD class=input5></TD>
        </TR>
	  </table>
	</Div>

	<DIV id = "divGrpCustomer" style = "display:  ">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol2);">
    		</td>
    		<td class= titleImg>
    			 �ֵ�Ͷ����λ���ϣ��ͻ��� <Input class=common name=CustomerNo id=CustomerNo> <INPUT id="butGrpNoQuery" class=cssButton VALUE="��ѯ" TYPE=button onclick="showAppnt();"> ��
    		</td>
    	</tr>
    </table>
    

	<Div  id= "divGroupPol2" class=maxbox style= "display:  ">
      <table  class= common>
       <TR>
          <TD  class=title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpName id=GrpName elementtype=nacessary  verify="��λ����|notnull&len<=60">
          </TD>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpAddress id=GrpAddress  elementtype=nacessary verify="��λ��ַ|notnull&len<=60">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpZipCode id=GrpZipCode  elementtype=nacessary  verify="��������|notnull&zipcode">
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class=code name=GrpNature id=GrpNature  elementtype=nacessary verify="��λ����|code:GrpNature&notnull&len<=10" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="showCodeList('GrpNature',[this]);" onkeyup="showCodeListKey('GrpNature',[this]);">
          </TD>
      
          <TD  class= title>
            ��ҵ���
          </TD>
          <TD  class= input>
            <Input class="code" name=BusinessType id=BusinessType elementtype=nacessary verify="��ҵ���|code:BusinessType&notnull&len<=20" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="return showCodeList('BusinessType',[this]);" onkeyup="return showCodeListKey('BusinessType',[this]);">
          </TD>
          <TD  class= title>
            Ա������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Peoples id=Peoples  elementtype=nacessary verify="��λ������|notnull&int">
          </TD>       
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��λ�ܻ�
          </TD>
          <TD  class= input>
            <Input class="wid common"  name=Phone id=Phone elementtype=nacessary verify="��λ�ܻ�|NUM&notnull&len<=30">
          </TD>
      
          <TD  class= title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Fax id=Fax>
          </TD>
          <TD  class= title>
            ����ʱ��
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=FoundDate id=FoundDate verify="����ʱ��|date" onClick="laydate({elem: '#FoundDate'});">
			<span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>       
        </TR>
   
        <TR  class= common>
          <TD  class= title5 >
            ������ϵ��һ
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=LinkMan1 id=LinkMan1 elementtype=nacessary verify="������ϵ��һ����|notnull&len<=10">
          </TD>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Phone1 id=Phone1 elementtype=nacessary verify="������ϵ��һ��ϵ�绰|NUM&notnull&len<=30">
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input class="wid common" name=E_Mail1 id=E_Mail1 verify="������ϵ��һE-MAIL|Email&len<=60">
          </TD>           
        </TR>
     
        <TR  class= common>
          <TD  class= title5>
            ������ϵ�˶�
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=LinkMan2 id=LinkMan2 verify="������ϵ�˶�����|len<=10">
          </TD>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Phone2 id=Phone2 verify="������ϵ�˶���ϵ�绰|NUM&len<=30">
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input class="wid common" name=E_Mail2 id=E_Mail2 verify="������ϵ�˶�E-MAIL|Email&len<=60">
          </TD>
        </TR>    
		
        <TR  class= common>
          <TD  class= title>
            ���ʽ
          </TD>
          <TD  class= input>
            <Input class="code" name=GetFlag id=GetFlag verify="���ʽ|code:PayMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('PayMode',[this]);" onkeyup="return showCodeListKey('PayMode',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class=code name=BankCode id=BankCode verify="��������|code:bank&len<=24" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="showCodeList('bank',[this]);" onkeyup="showCodeListKey('bank',[this]);">
          </TD>
          <TD  class= title>
            �ʺ�
          </TD>
          <TD  class= input>
            <Input class="wid common" name=BankAccNo id=BankAccNo verify="�ʺ�|len<=40">
          </TD>       
        </TR>
      </table>
      <table class=common>
         <TR  class= common> 
           <TD  class= title> ��ע </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="Remark" id=Remark cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
    </table>
    </Div>
	</DIV>

	<Div>
      <INPUT VALUE="��  ��" class=cssButton TYPE=button name=addbutton onclick="appendOne();">
      <INPUT VALUE="��  ��" class=cssButton TYPE=button name=modibutton onclick="updateOne();">
      <INPUT VALUE="ɾ  ��" class=cssButton TYPE=button name=delbutton onclick="deleteOne();">
      <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
    </Div> 
           
      <input type=hidden id="fmAction" name="fmAction">      
      <Input type=hidden name=GrpAddressNo id=GrpAddressNo>
      <Input type=hidden name=GrpProposalContNo id=GrpProposalContNo>
          
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>
