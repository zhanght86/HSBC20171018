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
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="GroupRiskInput.js"></SCRIPT>
  <%@include file="GroupRiskInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./GroupRiskSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class=titleImg>
    			 ���屣��������Ϣ
    		</td>
    	</tr>
    </table>
	<div class=maxbox1>
    <table  class= common>
        <TR  class= common>
         <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=GrpProposalNo id=GrpProposalNo>
          </TD>
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=GrpContNo id=GrpContNo >
          </TD>
         
          <TD  class= title>
            ӡˢ����
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=PrtNo id=PrtNo verify="ӡˢ����|notnull" >
          </TD>
        </TR>
        </table>
	</div>
	<br /><br />
        <Div  id= "divTempFeeInput" style= "display:  ">
  <!--¼����ݽ��ѱ��� -->
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanRiskGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    </div>
    <table>
    <TR >     
     <TD >
      <input type =button class=cssButton value="�������" onclick="addRecord();">      
     </TD>
     <TD >
      <input type =button class=cssButton value="ɾ������" onclick="deleteRecord();">      
     </TD>
    </TR> 
    </table>
    <table class=common>	
    	<tr>
        	<td class=common>
		   <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol3);">
    		</td>
    		<td class=titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divGroupPol3" class=maxbox style= "display:  ">
      <table  class= common>
      <TR class=common>
           <TD  class=title>
              *���ֱ���
           </TD>
           <TD  class=input>
            <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('RiskGrp',[this]);" onkeyup="return showCodeListKey('RiskGrp',[this]);">
           </TD>
           <TD  class= title>
            *������Ч���� 
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=CValiDate onClick="laydate({elem: '#CValiDate'});" id="CValiDate">
			<span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>    
          <TD  class= title>
            *��������
          </TD>
          <TD  class= input>
            <Input class="code" name=PayIntv id=PayIntv style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PayIntv',[this]);">
          </TD>     
        </TR>
        </Div>
       
     
  <table class=common>
   <TR  class= common> 
      <TD  class= title> ��ע </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
      <textarea name="Remark" id=Remark cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table>  
   
    </Div>
   
    <Div  id= "divHidden" style= "display: none">
      <TR  class= common>          
          <TD  class= title>
            �ⶥ��
          </TD>
          <TD  class= input>
            <Input class= common name=PeakLine id=PeakLine>
          </TD>
        </TR>
        <TR  class= common>          
          <TD  class= title>
            ҽ�Ʒ����޶�
          </TD>
          <TD  class= input>
            <Input class= common name=MaxMedFee id=MaxMedFee>
          </TD>
        </TR>
    </Div>
    <table>
    <TR class=common> 
    <TD align =right class=common>
      <input type =button class=cssButton value="�� һ ��" onclick="returnparent();">      
     </TD>    
     <TD  align =right class=common>
       
      <INPUT class=cssButton VALUE="��������"  TYPE=button onclick="grpFeeInput()">     
     </TD>
     
    </TR> 
    <input type=hidden id="fmAction" name="fmAction">
    </table> 
    </Form> 
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <br /><br /><br /><br />
   </body>
</html>
