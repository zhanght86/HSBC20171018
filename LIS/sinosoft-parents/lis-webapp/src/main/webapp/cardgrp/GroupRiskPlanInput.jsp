    <%
//�������ƣ�TempFeeInput.jsp
//�����ܣ������շѵ�����
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="GroupRiskPlanInput.js"></SCRIPT>
  <%@include file="GroupRiskPlanInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			 ���屣�ռƻ���Ϣ
    		</td>
    	</tr>
    </table>
    <table  class= common>
        <TR  class= common>
         <TD  class= title8>
            ����Ͷ��������
          </TD>
          <TD  class= input8>
            <Input class="readonly" readonly name=GrpProposalNo >
          </TD>
          <TD  class= title8>
            ����Ͷ��������
          </TD>
          <TD  class= input8>
            <Input class="readonly" readonly name=ContNo  >
          </TD>
         
          <TD  class= title8>
            ӡˢ����
          </TD>
          <TD  class= input8>
            <Input class="readonly" readonly name=PrtNo verify="ӡˢ����|notnull" >
          </TD>
        </TR>
        </table>
       
    <table>
    	<tr>
        	<td class=common>
		   <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol3);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divGroupPol3" style= "display: ''">
      <table  class= common>
      <TR class=common>
      <TD  class= title8>
            *���ֱ���
          </TD>
           <TD  class= input8>
            <Input class="code8" name=RiskCode ondblclick="return showCodeList('RiskGrp',[this]);" onkeyup="return showCodeListKey('RiskGrp',[this]);">
          </TD>
          </TR>
        <TR  class= common>
          <TD  class= title8>
            Ͷ���������� 
          </TD>
          <TD  class= input8>
            <Input class="coolDatePicker" dateFormat="short" name=PolApplyDate >
          </TD> 
          <TD  class= title8>
            *������Ч���� 
          </TD>
          <TD  class= input8>
            <Input class="coolDatePicker" dateFormat="short" name=CValiDate >
          </TD>    
          <TD  class= title8>
            *��������
          </TD>
          <TD  class= input8>
            <Input class="code8" name=PayIntv ondblclick="return showCodeList('PayIntv',[this]);">
          </TD>     
        </TR>
        
        <TR  class= common>
          
          <TD  class= title8>
            �����ڼ�
          </TD>
          <TD  class= input8>
             <Input class=common name=InsuYear value="0">
          </TD>
             <TD  class= title8>
            *��������
          </TD>
          <TD  class= input8>
             <Input class=common name=FloatRate value="0">
          </TD>
             <TD  class= title8>
            *���㷽��
          </TD>
          <TD  class= input8>
             <Input class="code8"  CodeData="0|^P|�����㱣��|^A|�����㱣��|^O|�����㱣��" name=CalDirect ondblClick="showCodeListEx('CalDirect',[this],[0]);" onkeyup="showCodeListKeyEx('CalDirect',[this],[0]);" value="P">
          </TD>
          
          
        </TR>
        <tr class=common>
        <TD  class= title8>
            ��Ա�Ը�����
          </TD>
          <TD  class= input8>
            <Input class= common name=EmployeeRate verify="��Ա�Ը�����|num&len<=5">
          </TD>
          <TD  class= title8>
            �����Ը�����
          </TD>
          <TD  class= input8>
            <Input class= common name=FamilyRate verify="�����Ը�����|num&len<=80">
          </TD>
          </tr>
        </Table>
        </Div>
       
     
  <table class=common>
   <TR  class= common> 
      <TD  class= title8> �ƻ����� </TD>
    </TR>
    <TR  class= common>
      <TD  class= title8>
      <textarea name="GrpSpec" cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table>  
   <table>
    <TR >     
     <TD >
      <input type =button class=cssButton value="��Ӷ��Ʊ��ռƻ�" onclick="addRecord();">      
     </TD>
     <TD >
      <input type =button class=cssButton value="ɾ�����Ʊ��ռƻ�" onclick="deleteRecord();">      
     </TD>
    </TR> 
    </table> 
    </Div>
    <Div  id= "divTempFeeInput" style= "display: ''">
  <!--¼����ݽ��ѱ��� -->
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    </div>
    <Div  id= "divHidden" style= "display: 'none'">
      <TR  class= common>          
          <TD  class= title8>
            �ⶥ��
          </TD>
          <TD  class= input8>
            <Input class= common name=PeakLine >
          </TD>
        </TR>
        <TR  class= common>          
          <TD  class= title8>
            ҽ�Ʒ����޶�
          </TD>
          <TD  class= input8>
            <Input class= common name=MaxMedFee >
          </TD>
        </TR>
    </Div>
    <table>
    <TR class=common> 
    <TD align =right class=common>
      <input type =button class=cssButton value="�� һ ��" onclick="returnparent();">      
     </TD>    
     <TD  align =right class=common>
      <input type =button class=cssButton value="���뱣����������Ϣ" onclick="grpInsuInfo();">      
     </TD>
     
    </TR> 
    </table> 
    </Form> 
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    </body>
</html>
