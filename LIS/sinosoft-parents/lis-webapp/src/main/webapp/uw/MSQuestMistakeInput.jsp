<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
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
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//String sMissionID = request.getParameter("MissionID");
	//String sSubMissionID = request.getParameter("SubMissionID");                  
	//String sActivityID = request.getParameter("ActivityID");
	String sPrtNo = request.getParameter("PrtNo");
    String tOtherFlag = request.getParameter("otherFlag");
    //String sNoType = request.getParameter("NoType"); 
    //String sUnSaveFlag = request.getParameter("UnSaveFlag"); //���ֻ�ǲ�ѯ�������ر���İ�ť��UnSaveFlagΪ"1"   
  //�����¸���
%>                                                                                
                                                                                  
<script> 
var QueryFlag = <%=request.getParameter("QueryFlag")%>;
var operator = "<%=tGI.Operator%>";   //��¼����Ա
var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
var comcode = "<%=tGI.ComCode%>"; //��¼��½����
var tPrtNo = "<%= sPrtNo %>";
var tOtherFlag = "<%=tOtherFlag %>";
var ErrManageCom;
function initParam()       
{                                                                  
	fm.PrtNo.value = tPrtNo;
}	
</script> 
  
  <SCRIPT src="MSQuestMistakeInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="MSQuestMistakeInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  <title>���������¼</title>
  
</head>


<body  onload="initForm();" >
  <form action="./MSQuestMistakeSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  <div id= "divNotePad" style= "display: '';margin-top:10px" >
  <div class="maxbox1">
	  <table class=common >
	  		<tr class=common>
	  			<td class=title5>
	  				ӡˢ��</td>
	  			<td class=input5>
    	 		<Input class="readonly wid" name="PrtNo" id="PrtNo" readonly >
    	 		</td>
                <td class=title5></td>
	  			<td class=input5></td>
	  		</tr>
	 </table> 
	 </div>
	 <table>  
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotePadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 �ѱ������¼ 
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
			         <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
			         <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
			         <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
			         <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage.lastPage();"> 	      
		    </div>
			<br>
			<div class="maxbox1">
			  <table class= common>
			    <tr>
			      <td class=title5>������λ</td>
        		  <TD  class= input5>
        		  	<Input  class="codeno" name=ErrManageCom id="ErrManageCom" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="������λ|NOTNULL" CodeData = "0|^1|�ֹ�˾�����^2|�ֹ�˾ɨ���^3|���¼���^4|�ܹ�˾����Լ^5|�ܹ�˾�˱���^10|����" onClick="showCodeListEx('ErrManageCom',[this,ErrManageComName],[0,1]);" ondblclick= "showCodeListEx('ErrManageCom',[this,ErrManageComName],[0,1]);" onKeyUp="showCodeListKeyEx('ErrManageCom',[this,ErrManageComName],[0,1]);" ><Input class=codename  name=ErrManageComName id="ErrManageComName" readonly ="readonly" >
        		  </TD>
			      <TD  class= title5>�������</TD>
        		  <TD  class= input5>
        		  	<Input class="codeno" name=ErrorType id="ErrorType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="�������|NOTNULL" onClick="return showCodeList('ErrorType', [this,ErrorTypeName],[0,1],null,'#2# and codealias=#'+fm.ErrManageCom.value+'#','2');"  ondblclick="return showCodeList('ErrorType', [this,ErrorTypeName],[0,1],null,'#2# and codealias=#'+fm.ErrManageCom.value+'#','2');" onKeyUp="return showCodeListKey('ErrorType', [this,ErrorTypeName],[0,1],null,'#2# and codealias=#'+fm.ErrManageCom.value+'#','2');" ><Input class=codename  name=ErrorTypeName id="ErrorTypeName" readonly ="readonly" >
        		  </TD>
			    </tr>
			  </table>
			  </div>
			  <table class= common>
			    <TR  class= title> 
			      <TD width="100%" height="15%"  class= title> �������</TD>
			    </TR>
			    <TR  class= title>
			      <TD height="85%"  class= title><textarea name="Content" verify="��������|len<800" verifyorder="1" cols="130" rows="5" class= common ></textarea></TD>
			    </TR>
			  </table>
			<div id= "divButton" style= "display: ''" >			  
			  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onClick="addNote();">
			  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onClick="UpdateMistake();">
			  <INPUT CLASS=cssButton VALUE="ɾ  ��" TYPE=button onClick="DeleteMistake();">
			  <!--<INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="fm.Content.value=''">-->
			  <!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
			  <INPUT class=cssButton VALUE="��  ��" TYPE=button onClick="top.close();" style= "display: none">	
			</div>
			<div id= "divReturn" style= "display: none" >			  
			  <INPUT class=cssButton VALUE="��  ��" TYPE=button onClick="top.close();">	
			</div>
	  </div>	   
   </div> 	 
   
      	 <Input type=hidden id="MissionID" name="MissionID" >
    	 <Input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <Input type=hidden id="ActivityID" name="ActivityID" >
    	 <Input type=hidden id="NoType" name="NoType" >
    	 <Input type=hidden id="SerialNo" name="SerialNo" >
    	 <Input type=hidden id="maction" name="maction" >
    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
