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
<%                                                                                
	String sMissionID = request.getParameter("MissionID");
	String sSubMissionID = request.getParameter("SubMissionID");                  
	String sActivityID = request.getParameter("ActivityID");                      
  String sPrtNo = request.getParameter("PrtNo");
  loggerDebug("UWBankSendFlagInput","sPrtNo: "+sPrtNo);
 %>                                                                                
                                                                                  
<script>
	var tMissionID = "<%= sMissionID %>";
	var tSubMissionID = "<%= sSubMissionID %>";
	var tActivityID = "<%= sActivityID %>";
	var tPrtNo = "<%= sPrtNo %>";
	var tOldSendBankFlag = "";
</script> 
  
  <SCRIPT src="UWBankSendFlagInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWBankSendFlagInit.jsp"%>
  <title>修改首期自动发盘标志</title>
  
</head>


<body onLoad="initForm();" >
  <form action="./UWBankSendFlagSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  <div id= "divNotePad" style= "display: ''" >
	  <table>
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotePadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 银行发盘方式 
		    	</td>
	    	</tr>
	    </table>    
     <div class="maxbox1" id="divNotePadInfo" >   
       <table  class= common align=center>
      	<tr  class= common>
          <td  class= title5>投保单号</TD>
          <td  class= input5> <Input class= "readonly wid" name=PrtNo id="PrtNo" > </TD>
          <td  class= title5>首期保费自动发盘标志</TD>
          <td class=input5>
           <Input class="codeno" name=NewAutoSendBankFlag id="NewAutoSendBankFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('UWAutoSendBankFlag',[this,NewAutoSendBankName],[0,1]);" onDblClick="return showCodeList('UWAutoSendBankFlag',[this,NewAutoSendBankName],[0,1]);" onKeyUp="return showCodeListKey('UWAutoSendBankFlag',[this,NewAutoSendBankName],[0,1]);"><Input class="codename" name=NewAutoSendBankName id="NewAutoSendBankName" readonly elementtype=nacessary>
          </td>
         </tr> 
       </table>    
      <div id= "divButton" style= "display: ''" >			  
			  <INPUT CLASS=cssButton VALUE="修  改" TYPE=button onClick="addNote();">
			  <INPUT class=cssButton VALUE="返  回" TYPE=button onClick="top.close();">	
			</div>
       <Input type=hidden id="MissionID" name="MissionID" >
    	 <Input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <Input type=hidden id="ActivityID" name="ActivityID" >
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
