<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="./LPPolQuery.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="LPPolQueryInit.jsp"%>
  <%@include file = "ManageComLimit.jsp"%>

  <title>���˱�����ѯ </title>
</head>
<body  onload="initForm();" >

  <form action="./LPPolQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPPol1);">
      </td>
      <td class= titleImg>
        ���������ѯ������
      </td>
    	</tr>
    </table>

    <Div  id= "divLPPol1" style= "display: ''" class=maxbox1>
      <table  class= common>
        <TR class=common>
          <TD  class= title5>
            ���˱�����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PolNo id=PolNo >
          </TD>
          <TD  class= title5>
            ���˿ͻ���
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=CustomerNo id=CustomerNo >
          </TD>
        </TR>
      </table>
    </Div>
          <INPUT VALUE="��ѯ" class=cssButton TYPE=button onclick="submitForm();"> 
          <INPUT VALUE="����" class=cssButton TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPPol2);">
    		</td>
    		<td class= titleImg>
    			 ���˱�����Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLPPol2" style= "display: '';text-align:center; ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  				<span id="spanLPPolGrid" >
  				</span> 
  			</td>
  		</tr>
    	</table>
       <INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> 						
  	</div>
  	  <input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="ContType" name="ContType">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="EdorType" name="EdorType">
  	  	<input type=hidden id="GrpPolNo" name="GrpPolNo">


  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
