<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
<%                                                                                                      
    String sContNo = request.getParameter("ContNo");
    String sNoType = request.getParameter("NoType");   
    String sQueryFlag = request.getParameter("QueryFlag"); 
    String sOperatePos = request.getParameter("OperatePos"); 
    String sClmNo = request.getParameter("ClmNo");
%>                                                                                
<script> 
 var OperatePos = "<%= sOperatePos %>";
 var EdorNo = "<%= request.getParameter("EdorNo")%>";
 var sQueryFlag = "<%= sQueryFlag %>";
    function initParam()       
	{                                                                  
		fm.ContNo.value = "<%= sContNo %>";
		fm.NoType.value = "<%= sNoType %>";
		fm.OperatePos.value = "<%= sOperatePos %>";
		fm.ClmNo.value = "<%= sClmNo %>";
	}
</script>  
  <SCRIPT src="UWReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWReportInit.jsp"%>
  <title>�˱���������</title>
  
</head>

<body  onload="initForm();" >
  <form action="./UWReportSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  <div id= "divNotePad" style= "display: ''" >
	  <table>
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);">
		    	</td>
		    	<td class= titleImg>
		    	 �˱�����������Ϣ 
		    	</td>
	    	</tr>
	    </table>        
		<div id= "asd" style= "display: ''" >

		    <Div  id= "divReportGrid" style= "display: ''" align = center>
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  				<span id="spanReportGrid">
		  				</span> 
		  		  		</td>
		  			</tr>
		    	</table>
<!--			         <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
			         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
			         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
			         <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 	      
-->		    </div>
			 <br>
			  <table class= common>
			    <TR  class= common> 
			      <TD height="15%"  class= title> �˱������������ݣ�800������,�س���ռһ�����֣�</TD>
			      <TD height="15%"  class= title> <input style="vertical-align:middle" type=checkbox id="BussFlag" name=BussFlag value="1"><span style="vertical-align:middle">��ҵ���ر�׼��б�</span></TD>
			    </TR>
			    <TR  class= common>
			      <TD colspan="6" style="padding-left:16px"><textarea name="Content" id="Content" verify="������������|len<=800" verifyorder="1" cols="160" rows="4" class="common" ></textarea></TD>
			    </TR>
			  </table>  </div>	   
   </div>
			<div id= "divButton" style= "display: ''" >			  
			  <INPUT CLASS=cssButton VALUE="��  ��" id="Add" name="Add" TYPE=button onClick="addReport();">
			  <!--<INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="fm.Content.value=''">-->
			  <!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
			  <INPUT class=cssButton VALUE="��  ��" TYPE=button onClick="top.close();" style= "display: none">	
			</div>
	 
    	 <Input type=hidden id="ContNo" name="ContNo" >
    	 <Input type=hidden id="NoType" name="NoType" >   
    	 <Input type=hidden id="OperatePos" name="OperatePos" > 
    	 <Input type=hidden id="BussFlagSave" name="BussFlagSave" > 	    
    	 <Input type=hidden id="ClmNo" name="ClmNo" > 	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
