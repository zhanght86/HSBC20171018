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
  <SCRIPT src="ImageQueryGrp.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ImageQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="../common/EasyScanQuery/EasyScanQuery.jsp" method=post name=fm id="fm" target="fraPic">
  <div class="maxbox1">
	<div id="DivLCCont" >
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>Ͷ�������� 
	    	</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id="ContNo" VALUE CLASS="readonly wid" readonly= TABINDEX="-1" MAXLENGTH="14">
	    	</td>
	    	 
         <td class=title>
           Ӱ����� 
         </td>
	    	<td class=input>
         <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=subtype id="subtype" onclick="return showCodeList('imagetype',[this,subtypname],[0,1]);" ondblclick="return showCodeList('imagetype',[this,subtypname],[0,1]);" onkeyup="return showCodeListKey('Imagetype',[this,subtypname],[0,1]);" ><Input class="codename" name=subtypname id="subtypname"readonly elementtype=nacessary>
         </td> 		
         <td class=title>
           ɨ�����κ�
         </td>
	    	<td class=input>
         <Input class="common wid" name="ScanNo" id="ScanNo">
         </td> 		
			</tr>	  
		</table>
</div>
</div> 
	<INPUT class=cssButton VALUE="��   ѯ" TYPE=button onclick="QueryImage();">  
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
      	  		<td text-align: left colSpan=1 >
  					<span id="spanImageGrid">
  					</span> 
  			  	</td>
  			</tr> 
    	</table> 
         	<div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>				
    </Div>
    
    
     <div  id= "HiddenValue" style="display:none;float: right">
			<input type="hidden" name="DocID" id="DocID" value= ""> 
			<input type="hidden" name="QueryType" id="QueryType" value="0">   
    </div>         
 
     <br>      
     <br>      
     <br>      
     <br>      
  </form> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
  