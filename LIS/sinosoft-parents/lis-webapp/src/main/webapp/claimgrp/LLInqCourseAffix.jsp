<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-4-8 
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script> 
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/Calendar/Calendar.js"></script>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>  
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="LLInqCourseAffix.js"></SCRIPT>
  <title>������Ϣ</title>
</head>
<body  onload="initForm();" >
  <form action="LLInqCourseAffixSave.jsp" method=post name=fm target="fraSubmit" ENCTYPE="multipart/form-data" >
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDetail1);">
          <td class=titleImg>
            ������Ϣ
          </td>
        </td>
      </tr> 
    </table>
    <Div  id= "divDetail1" style= "display: ''">
      
    <table  class= common >
      <tr>  
        <td  class= title>�ⰸ��     
		    <input  name=ClmNo class=common value=<%=request.getParameter("ClmNo")%>> 
		    </td>
      </tr> 
      <TR>
   			<td  class= title>��  ��
   			<input class= input type="file" name="filePath" accept="*" class="common">
   		  </td>
   		</TR> 			 
    </table>
   </div>             
	    <Div id= "DivSave"  align=center>          
	    	<TABLE class= common> 	        	
	            <TR>
	                <TD class=common>
	                	<Input class=cssButton  name="Replyconfirm" value=" �� �� " type=button onclick="saveClick();"> 
   	                <Input class=cssButton  value=" �� �� " type=button onclick="TurnBack()">
   	              </TD> 
	            </TR>
  	    </TABLE> 
	    </Div>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>

        
