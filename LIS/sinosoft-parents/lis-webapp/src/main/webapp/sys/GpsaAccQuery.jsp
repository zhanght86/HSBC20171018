<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String cPolNo = "";
	try
	{
		cPolNo = request.getParameter( "PolNo" );
		
		loggerDebug("GpsaAccQuery","---PolNo:"+cPolNo);
	}
	
	
	catch( Exception e1 )
	{
		cPolNo = "00000000000000000000";
			loggerDebug("GpsaAccQuery","---PolNo:"+cPolNo);

	}
 %>
<%@include file="GpsaAccQueryinit.jsp" %>
<script>
	var tPolNo = "<%=cPolNo%>";  //��ѯ����.

</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="GpsaAccQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>


  <title>�ʻ���ѯ </title>
</head>
<body onload="initForm();LCInsureAccQuery();">
  <form name=fm id="fm" target=fraSubmit method=post>
  	<table>
    	
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    		�ʻ���Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCPol2" style= "display: ''" align = center>	
     <table  class= common>  	
       	<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsureAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    </table>
    <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
    <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
    <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
    <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="getLastPage();">
  	</Div>  	  
  	<br>
    <div>
      <a href="javascript:void(0)" class=button onclick="LCInsureAccClassQuery();">���ʻ���ѯ</a>
      <a href="javascript:void(0)" class=button onclick="AccValueCal();">�ʻ���ֵ����</a>
      <a href="javascript:void(0)" class=button onclick="GoBack();">��  ��</a>
    </div>
    <br>
  	<!-- <INPUT VALUE="���ʻ���ѯ" class = cssButton TYPE=button onclick="LCInsureAccClassQuery();">
  	<INPUT class=cssButton VALUE="�ʻ���ֵ����" TYPE=button onclick="AccValueCal();">
  	<INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="GoBack();"> -->
    <div class="maxbox1">
  	<table class="common">
            <tr class="common">
                <td class="title5">�ʻ���ֵ</td>
                <td class="input5"><input class = "readonly wid" name="AccValue" id="AccValue" readonly></td>
                <td class="title5">��Ϣ</td>
                <td class="input5"><input class = "readonly wid" name="Interest" id="Interest" readonly></td>
            </tr>
    </table>
    </div>
    <br>        
  	<Div  id= "divLCPol1" style= "display: none" >	
     <table  class= common>  	
       	<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsureAccClassGrid" >
  					</span> 
  			  	</td>
  			</tr>
    </table>
    <div  align = center>
    <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
    <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
    <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
    <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="getLastPage();">   
    </div> 
    <INPUT name="PolNo" id="PolNo" type=hidden value ="">
  	<div style="text-align: left;">
     <a href="javascript:void(0)" class=button onclick="LCInsureAccTrace();">�ʻ��켣��ѯ</a> 
    </div>
  	<!-- <table  class= common>  	
       	<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<INPUT VALUE="�ʻ��켣��ѯ" class = cssButton TYPE=button onclick="LCInsureAccTrace();">
  			  	</td>
  			</tr>
    </table> -->
  	</Div>  	  
  	
  	<input type=hidden id="fmtransact" name="fmtransact">
	</form>
  <br>
  <br>
  <br>
  <br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
