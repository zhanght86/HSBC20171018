<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<title>ҽԺ��Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./LLHospitalQuery.js"></script> 

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLHospitalQueryInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm target=fraSubmit method=post>
    <Table>
       <TR>
           <TD class=common>
              <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
          </TD>
          <TD class= titleImg>
              ҽԺ��Ϣ
          </TD>
       </TR>
    </Table>       

 <Div  id= "divLDPerson1" style= "display: ''" align = center>
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanHospitalGrid" ></span> 
     </TD>
      </TR>
    </Table>
   <INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="returnParent();">         
      <INPUT VALUE="��  ҳ" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getPreviousPage();">                
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton TYPE=button onclick="getLastPage();"> 
         
 </Div>                        
<input type="hidden" id="HospitalName" name="HospitalName">

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>               
</Form>
</body>

</html>
