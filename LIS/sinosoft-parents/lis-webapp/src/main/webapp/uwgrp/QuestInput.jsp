<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�QuestInput.jsp
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="QuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
  <%@include file="QuestInputInit.jsp"%>
<script language="javascript">
	var LoadFlag ="<%=tFlag%>"; //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����

</script>  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>');" >
  <form method=post name=fm target="fraSubmit" action= "./QuestInputChk.jsp">
  	
  		<table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>�������Ϣ</td>
		</tr>
	</table>
	<table class = common>
	        <TD  class= title>
            ���Ͷ���  
          </TD>
          <TD>
          <Input class= "codeno" name = BackObj ondblclick="return showCodeList('backobj',[this,BackObjName],[0,1]);" onkeyup="return showCodeListKey('BackObj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName readonly = true>
          </TD> 
        </table>
	
		<div id= "divCustomerqustion" style= "display: 'none'" >
    <table class = common >
    	<TR  class= common>
    		  
          
          <TD class=title> ���ն��� </TD>  
          <TD  ><Input class= "codeno" name = QuestionObj ondblclick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onkeyup="return showCodeListKey('question',[this,QuestionObjName],[0,1]);" ><Input class = codename name=QuestionObjName readonly = true> </TD>
                     
          <TD  class = title> �ͻ����� </TD>
          <TD  ><Input class=common  name=CustomerNo ></TD>
           
          <TD  class = title>	�ͻ����� </TD>
          <TD  ><Input class=common  name=CustomerName ></TD>
          </TR>     
          
     </table>
    </div>
     
   <table class = common>
    
       <TD  class= title>
    	  �����ѡ��
    	</TD>
        <TD >
            <Input class=code name=Quest ondblclick= "showCodeListEx('Quest',[this,Content],[0,1],null,null,null,null,770);" onkeyup="showCodeListKeyEx('Quest',[this,Content],[0,1],null,null,null,null,770);" >
        </TD>
    
    </table>

    
  <table class = common align = center width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ContNo" value= "">
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" name= "sure" class= cssButton  value="ȷ  ��" onClick="submitForm()">
</body>
</html>
