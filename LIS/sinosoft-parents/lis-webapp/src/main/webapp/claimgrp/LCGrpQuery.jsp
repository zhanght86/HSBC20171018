<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<title>����ͻ���ͬ��Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./LCGrpQuery.js"></script> 

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LCGrpQueryInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm target=fraSubmit method=post>
    <Table>
     <TR>
         <TD class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpContGrid);">
      </TD>
      <TD class= titleImg>
        �����ͬ��Ϣ
      </TD>
     </TR>
    </Table>    
     
 <Div  id= "divGrpContGrid" style= "display: ''" align = center>
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanGrpContGrid" ></span> 
   </TD>
      </TR>
    </Table>    
      <INPUT VALUE="��  ҳ" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getPreviousPage();">      
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton TYPE=button onclick="getLastPage();"> 
       
 </Div> 
 
 <hr>
 <INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="returnParent();"> 
       
<input type="hidden" id="CustomerNo" name="CustomerNo">
<input type="hidden" id="GrpName" name="GrpName">
<input type="hidden" id="GrpContNo" name="GrpContNo">
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>     
</Form>
</body>

</html>
