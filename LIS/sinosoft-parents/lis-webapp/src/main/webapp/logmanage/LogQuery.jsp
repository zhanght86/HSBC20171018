<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="LogQuery.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

  <%@include file="LogQueryInit.jsp"%>
  
<title>��־��ѯ </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- ��־��Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>��������־��ѯ������</td>
		</tr>
	</table>
    <table  class= common align=center>
    
       <TR  class= common>
          <TD  class= title>�������� </TD>
          <TD  class= input> <Input class= common name=OtherNo > </TD>
          <TD  class= title> ������������</TD>
          <TD  class= input><Input class= 'code' name=OtherNoType CodeData="0|^0|�ŵ���ͬ����ɾ��^1|�ŵ������ֵ�����ɾ��^2|�ŵ��¸��˺�ͬ����ɾ��^3|�ŵ��¸�����������ɾ��^4|�ŵ��¸������ֵ�ɾ��^12|������ͬ����ɾ��^13|��������������ɾ��^14|������ͬ�����ֵ�����ɾ��" ondblclick="showCodeListEx('OtherNoType',[this,''],[0,1]);" onkeyup="showCodeListEx('OtherNoType',[this,''],[0,1]);"></TD>
          <TD  class= title> �Ƿ��Ǳ���</TD>
          <TD  class= input><Input class= 'code' name=IsPolFlag CodeData="0|^0|��^1|��" ondblclick="showCodeListEx('IsPolFlag',[this,''],[0,1]);" onkeyup="showCodeListEx('IsPolFlag',[this,''],[0,1]);"></TD>
        </TR>    

      	<TR  class= common>
          <TD  class= title>ӡˢ���� </TD>
          <TD  class= input> <Input class= common name=PrtNo > </TD>
          <TD  class= title> �������</TD>
          <TD  class= input><Input class="code" name=ManageCom verify="�������|code:comcode&notnull" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></TD>
          <TD  class= title> ����Ա</TD>
          <TD  class= input> <Input class= common name=Operator > </TD>
        </TR>
        
        <TR  class= common>          
          <TD  class= title> �������</TD>
          <TD  class= input> <Input class="coolDatePicker" name=MakeDate verify="�������|date"> </TD>
          <TD  class= title> ���ʱ�� </TD>
          <TD  class= input> <Input class=common name=MakeTime verify="���ʱ��"> </TD>
          <TD  class= title> ���һ���޸����� </TD>
          <TD  class= input> <Input class="coolDatePicker" name=ModifyDate verify="���һ���޸�����|date"> </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title> ���һ���޸�ʱ�� </TD>
          <TD  class= input> <Input class= common name=ModifyTime verify="���һ���޸�ʱ��"> </TD>
        </TR>
       
    </table>
          <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> 
          <!--INPUT VALUE="��־��ϸ" TYPE=button onclick="getQueryDetail();"--> 					
          
          <INPUT VALUE="��  ��" name=Return class = cssButton TYPE=button STYLE="display:none" onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLog1);">
    		</td>
    		<td class= titleImg>
    			 ��־��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLog1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanLogGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<BR>
      <INPUT VALUE="��  ҳ" class = cssButton TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class = cssButton TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  	<p>
  	
  <Div  id= "divLog2" style= "display: 'none'"> 
     <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLog2);">
    		</td>
    		<td class= titleImg>
    			 ��־��ϸ
    		</td>
    	</tr>
    </table>   	
    <table  class= common align=center>
    
       <TR  class= common>
          <TD  class= title>�������� </TD>
          <TD  class= input> <Input class= common name=tOtherNo > </TD>
          <TD  class= title> ������������</TD>
          <TD  class= input><Input class= 'code' name=tOtherNoType CodeData="0|^0|�ŵ���ͬ����ɾ��^1|�ŵ������ֵ�����ɾ��^2|�ŵ��¸��˺�ͬ����ɾ��^3|�ŵ��¸�����������ɾ��^4|�ŵ��¸������ֵ�ɾ��^12|������ͬ����ɾ��^13|��������������ɾ��^14|������ͬ�����ֵ�����ɾ��" ondblclick="showCodeListEx('OtherNoType',[this,''],[0,1]);" onkeyup="showCodeListEx('OtherNoType',[this,''],[0,1]);"></TD>
          <TD  class= title> �Ƿ��Ǳ���</TD>
          <TD  class= input><Input class= 'code' name=tIsPolFlag CodeData="0|^0|��^1|��" ondblclick="showCodeListEx('IsPolFlag',[this,''],[0,1]);" onkeyup="showCodeListEx('IsPolFlag',[this,''],[0,1]);"></TD>
        </TR>    

      	<TR  class= common>
          <TD  class= title>ӡˢ���� </TD>
          <TD  class= input> <Input class= common name=tPrtNo > </TD>
          <TD  class= title> �������</TD>
          <TD  class= input><Input class="code" name=tManageCom verify="�������|code:comcode&notnull" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></TD>
          <TD  class= title> ����Ա</TD>
          <TD  class= input> <Input class= common name=tOperator > </TD>
        </TR>
        
        <TR  class= common>          
          <TD  class= title> �������</TD>
          <TD  class= input> <Input class="coolDatePicker" name=tMakeDate verify="Ͷ���˳�������|notnull&date"> </TD>
          <TD  class= title> ���ʱ�� </TD>
          <TD  class= input> <Input class=common name=tMakeTime verify="Ͷ���˳�������|notnull&date"> </TD>
          <TD  class= title> ���һ���޸����� </TD>
          <TD  class= input> <Input class="coolDatePicker" name=tModifyDate verify="Ͷ���˳�������|notnull&date"> </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title> ���һ���޸�ʱ�� </TD>
          <TD  class= input> <Input class= common name=tModifyTime verify="Ͷ���˳�������|notnull&date"> </TD>
        </TR>       
    </table> 	 
  </div>  	 
  	 </p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
