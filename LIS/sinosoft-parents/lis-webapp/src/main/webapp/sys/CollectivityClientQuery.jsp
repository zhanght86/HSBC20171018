<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<title>�ͻ���Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <!--SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT-->
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./CollectivityClientQuery.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CollectivityClientQueryInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm target=fraSubmit method=post>
     <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cav);"></td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
    <Div  id= "cav" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>
          ��λ����
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=GrpNo id=GrpNo >
          </TD>
          <TD  class= title5>
          ��λ����
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=GrpName id=GrpName >
          </TD>
          </TR>
          <TR  class= common>
          <TD  class= title5>
          ��λ����
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name=GrpNature id=GrpNature ondblclick="return showCodeList('GrpNature',[this]);" onclick="return showCodeList('GrpNature',[this]);" onkeyup="return showCodeListKey('GrpNature',[this]);">            
          </TD>
          <TD  class= title5></TD>
          <TD  class= input5>
       </TR>             
      	   
   </Table>  </Div>
      <!--<INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="easyQueryClick();"> -->
       <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCollectivityClient1);">
    		</TD>
    		<TD class= titleImg>
    			 �ͻ���Ϣ
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divCollectivityClient1" style= "display: ''" align=center>
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      
 </Div>					

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>	
<!--<INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="returnParent();"> -->
<a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a> 				
</Form><br><br><br><br>
</body>
</html>
