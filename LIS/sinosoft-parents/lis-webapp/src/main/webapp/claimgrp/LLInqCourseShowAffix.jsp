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
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <!--SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT-->
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="LLInqCourseShowAffix.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLInqCourseShowAffixInit.jsp"%>
  <title>������Ϣ</title>
</head>
<body  onload="initForm();" >
  <form action="LLInqCourseShowAffixSave.jsp" method=post name=fm target="fraSubmit">
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
      
    <table  class= common>
      <TR  class= common> 
        <TD class= title> 
         
          �ⰸ�ţ�<Input name=ClmNo class=common value=<%=request.getParameter("ClmNo")%>></input>
        </TD>        
      </TR>
    </table>
   </div>
    <div id="divlink" style= "display: 'none'">

    </div>   
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>

        
