<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%--
   queryFlag���ã��ж�������Լ¼���������Ǵ�Ͷ����¼����õĲ�ѯ�����Ǳ�����¼����õĲ�ѯ��
   Ͷ����¼�룺queryFlag=queryappnt
   ������¼�룺queryFlag=queryInsured
   
--%>
<%
  String queryFlag = request.getParameter("queryFlag");
%>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>   
  <SCRIPT src="../bq/QueryPEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <%@include file="QueryPEdorInit.jsp"%> 
<html>
<head>
<title>��ȫ���Ĳ�ѯ</title>
<script language="javascript">
  var queryFlag = "<%=queryFlag%>";
  //alert("aa="+queryFlag);
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../bq/QueryPEdorInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id=fm target=fraSubmit method=post>
	
 
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
    		</TD>
    		<TD class= titleImg>
    			 ��ȫ������Ϣ
    		</TD>
    	</TR>
    </Table>    	
    <Div  id= "divLDPerson1" style= "display: ''" class=maxbox1>
    	<table class = common>
    		<TR class = common>
    			<TD class = title>������ </TD>
    			<TD  class= input> <Input class= "common wid" name= > </TD>
    			<TD class = title>������Ŀ </TD>
    			<TD  class= input> <Input class= "common wid" name= > </TD>
    			<TD class = title>���˷ѽ�� </TD>
    			<TD  class= input> <Input class= "common wid" name= > </TD>
    		</TR>
    		<TR class = common>
    			<TD class = title>����ʱ��</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title>��Чʱ��</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title>����״̬</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    		</TR>
    		<TR class = common>
    			<TD class = title>������</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title>������</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title></TD>
    			<TD class = input> <Input class = "readonly wid" readonly name=> </TD> 
    		</TR>
    </table>			
    </Div>
    <table>
     	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReportByLoseInfo);">
      </td>
      <td class= titleImg>
        �˱���ϸ��Ϣ
      </td>
   </tr>
   </table>
   
   <Div  id= "divReportByLoseInfo" style= "display: ''">
      <table  class= common>
       <TR  class= common>
        <TD class= title>�˱�����</TD>
        <TD  class= input> <Input class= "common wid" name=ContNo id=ContNo > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
       </TR>
       
      <TR class=common>
				<TD class=title colspan=6 > ��ȫ�˱���� </TD>
			</TR>
			<TR class=common>
					<TD  class = "readonly wid" readonly colspan=6 ><textarea name="UWIdea" id=UWIdea cols="100%" rows="5" witdh=100% class="readonly" readonly ></textarea></TD>
			</TR>
       
       <TR class=common>
       	<TD class=title>������ӡ����</TD>
       	<TD  class= input> <Input class= "common wid" name= > </TD>
       	<TD class=title>�˱�ʱ��</TD>
       	<TD  class= input> <Input class= "common wid" name= > </TD>
       	<TD class=title>�ر�Լ��</TD>
       	<TD  class= input> <Input class= "common wid" name= > </TD>
       </TR>
       

    </Table>					
 </Div>	
    
 
    <table>
     	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReportByLoseInfo1);">
      </td>
      <td class= titleImg>
        ��/������ϸ��Ϣ
      </td>
   </tr>
   </table>
    <Div  id= "divReportByLoseInfo1" style= "display: ''">
      <table  class= common>
       <TR  class= common>
        <TD class= title>Ӧ������</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>ʵ������</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>Ӧ������</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
       </TR>
       <TR  class= common>
        <TD class= title>ʵ������</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>�շѷ�ʽ</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>���ѷ�ʽ</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
       </TR>
       <TR>
		 <TD class= title>
		     ���б���
		 </TD>    
		 <TD class= input>
		    <Input class= "readOnly wid" readonly  name=BankCode id=BankCode >
		 </TD>	
		 <TD class= title>
		     �����ʺ�
		 </TD>     
		 <TD class= input>
		    <Input class= "readOnly wid" readonly  name=BankAccNo  id=BankAccNo>
		 </TD>
		 <TD class= title>
		     �����˻���
		 </TD>    
		 <TD class= input>
		    <Input class= "readOnly wid" readonly  name=AccName id=AccName >
		 </TD>		       
     </TR>
      </table>
    </Div>
		
		<table>
     	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReportByLoseInfo2);">
      </td>
      <td class= titleImg>
        ������ϸ��Ϣ
      </td>
   </tr>
   </table>
    <Div  id= "divReportByLoseInfo2" style= "display: ''">
      <table  class= common>
       <TR  class= common>
        <TD class= title>����ԭ��</TD>
        <TD  class= input> <Input class= "common wid" name=ContNo id=ContNo > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
       </TR>
      </table>
    </Div>	
    <INPUT class=cssButton VALUE="����Ӱ������" TYPE=button onclick="QueryPEdor();">
    <INPUT class=cssButton VALUE=" ���� " TYPE=button onclick="QueryBonus();">
			

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>					
</Form>
</body>
</html>
