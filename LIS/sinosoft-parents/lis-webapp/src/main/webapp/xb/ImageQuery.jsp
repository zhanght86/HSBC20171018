<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ImageQuery.jsp
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2005-07-07 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%
	String tContNo = "";
	tContNo = request.getParameter("ContNo"); 
	session.putValue("ContNo",tContNo);
%>
<html>
<%
  //�����¸���
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";
	var ttContNo = "<%=request.getParameter("ContNo")%>";          
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
	
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="ImageQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ImageQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="../common/EasyScanQuery/EasyScanQuery.jsp" method=post name=fm id="fm" target="fraPic">
  	<br> 
	<div id="DivLCCont" >
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>ӡˢ�� 
	    	    </td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id="ContNo" VALUE CLASS="readonly wid" readonly= TABINDEX="-1" MAXLENGTH="14">
	    	    </td>
	    	 
         <td class=title>
           Ӱ����� 
         </td>
	    	<td class=input>
         <Input class="codeno" name=subtype id="subtype" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('imagetype',[this,subtypname],[0,1]);" onDblClick="return showCodeList('imagetype',[this,subtypname],[0,1]);" onKeyUp="return showCodeListKey('Imagetype',[this,subtypname],[0,1]);" ><Input class="codename" name=subtypname id="subtypname" readonly elementtype=nacessary>
         </td> 		
         <td class=title>
           ɨ�����κ�
         </td>
	    	<td class=input>
         <Input class="common wid" name="ScanNo" id="ScanNo">
         </td> 		
			</tr>	  
		</table>
		
		 </DIV> 
	    <INPUT class=cssButton VALUE="��   ѯ" TYPE=button onClick="QueryImage();"> 
	 
<hr class="line"> 
<!--Ӱ����Ϣ-->   
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 Ӱ��������Ϣ:
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divLCPol2" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1 >
  					<span id="spanImageGrid">
  					</span> 
  			  	</td>
  			</tr> 
    	</table> 
         	<div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
        </div>				
    </Div>
    
    
     <div  id= "HiddenValue" style="display:none;float: right">
			<input type="hidden" name="DocID" value= ""> 
			<input type="hidden" name="QueryType"  value="0">   
    </div>         
 
           
  </form> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br><br>
</body>
</html>
  