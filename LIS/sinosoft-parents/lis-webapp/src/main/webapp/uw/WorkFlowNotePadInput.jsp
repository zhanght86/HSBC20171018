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
	String sMissionID = request.getParameter("MissionID");
	String sSubMissionID = request.getParameter("SubMissionID");
	String sActivityID = request.getParameter("ActivityID");                      
    String sPrtNo = request.getParameter("PrtNo");
    String sNoType = request.getParameter("NoType"); 
    String sUnSaveFlag = request.getParameter("UnSaveFlag"); //���ֻ�ǲ�ѯ�������ر���İ�ť��UnSaveFlagΪ"1"    
%>                                                                                
                                                                                  
<script> 
var QueryFlag = <%=request.getParameter("QueryFlag")%>;
function initParam()       
{                                                                  
	fm.MissionID.value = "<%= sMissionID %>";
	fm.SubMissionID.value = "<%= sSubMissionID %>";
	fm.ActivityID.value = "<%= sActivityID %>";
	fm.PrtNo.value = "<%= sPrtNo %>";
	fm.NoType.value = "<%= sNoType %>";
	fm.UnSaveFlag.value = "<%= sUnSaveFlag %>";
}	
</script> 
  
  <SCRIPT src="WorkFlowNotePad.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WorkFlowNotePadInit.jsp"%>
  <title>���±��鿴</title>
  
</head>


<body  onload="initForm();" >
  <form action="./WorkFlowNotePadSave.jsp" method=post id="fm" name=fm target="fraSubmit">
  <div id= "divNotePad" style= "display: ''" >
	  <table>
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotePadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 ���±���Ϣ 
		    	</td>
	    	</tr>
	    </table>        
		<div id= "divNotePadInfo" style= "display: ''" >

		    <Div  id= "divQuestGrid" style= "display: ''" align = center>
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  				<span id="spanQuestGrid">
		  				</span> 
		  		  		</td>
		  			</tr>
		    	</table>
			         <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
			         <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
			         <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
			         <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> 	      
		    </div>
			
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> �������ݣ�400������,�س���ռһ�����֣�</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title style="padding-left:16px"><textarea name="Content" verify="��������|len<800" verifyorder="1" cols="170" rows="4" class="common" ></textarea></TD>
			    </TR>
			  </table>
			<div id= "divButton" style= "display: ''" >			  
			  <!--<INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="addNote();">-->
              
			  <!--<INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="fm.Content.value=''">-->
			  <!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
			  <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="top.close();" style= "display: none">	
			</div>
			<div id= "divReturn" style= "display: none" >			  
			  <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="top.close();">	
			</div>
	  </div>	   
   </div> <br>	 
   <a href="javascript:void(0);" class="button" onClick="addNote();">��    ��</a>
      	 <Input type=hidden name="MissionID" >
    	 <Input type=hidden name="SubMissionID" >
    	 <Input type=hidden name="ActivityID" >
    	 <Input type=hidden name="PrtNo" >
    	 <Input type=hidden name="NoType" >
    	 <Input type=hidden name="UnSaveFlag" >
    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
