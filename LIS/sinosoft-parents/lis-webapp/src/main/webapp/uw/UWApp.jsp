<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//�������ƣ�UWApp.jsp
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  �� WHN
//���¼�¼��  ������  ln  ��������  2009-01-04   ����ԭ��/����
%>
<%
  String tContNo = "";
  String tCustomerNo = "";
  String tType = "";
  tContNo = request.getParameter("ContNo");
  tCustomerNo = request.getParameter("CustomerNo");  
  tType = request.getParameter("type");
  loggerDebug("UWApp","ContNo:"+tContNo);
  loggerDebug("UWApp","CustomerNo:"+tCustomerNo);
  loggerDebug("UWApp","type:"+tType);
%> 
<script>
	var ContNo = "<%=tContNo%>";
	
	var tCustomerNo = "<%=tCustomerNo%>";
	var tType = <%=tType%>;	
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAppInit.jsp"%>
  <title>����Ͷ�����ϲ�ѯ </title>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tCustomerNo%>','<%=tType%>');" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
     <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
	    	</td>
	    	<td class= titleImg>
	    	 ��������Ӧ��Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "maxbox" class="maxbox1" >
     <table class="common">
      <tr class="common">
        <td class="title5">
          �ͻ���
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerNo" id="CustomerNo" readonly>
        </td>
        <td class="title5">
          �ͻ�����
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerName" id="CustomerName" readonly>
        </td>
      </tr>
    </table>
    <!-- ����Ͷ����Ϣ���֣��б� -->
    <!-- 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCont);">
    		</td>
    		<td class= titleImg>
    			 ������ͬ��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCCont" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanContGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="��  ҳ" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton TYPE=button onclick="getLastPage();">		
	</div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 �������ֱ�����Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPol1" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanPolGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="��  ҳ" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton TYPE=button onclick="getLastPage();">
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "ProposalNoHide2" value= "">      
      <hr>					
      <INPUT VALUE="������ϸ��Ϣ" class= cssButton TYPE=button onclick="showPolDetail();"> 
      <INPUT VALUE="�����˱���¼" class= cssButton TYPE=button onclick="showOldUWSub();">
      <INPUT VALUE="���պ˱���Ϣ" class= cssButton TYPE=button onclick="showNewUWSub();">
      <INPUT VALUE="���������Ϣ" class= cssButton TYPE=button onclick="showHealthQ();">
      <INPUT VALUE="�˱���������" class= cssButton TYPE=button onclick="showReport();">					
	</div>
	 -->
	 <!--�ѳб�������Ϣ-->
	 <!-- 
	 <table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
				</td>
				<td class= titleImg>������Ϣ</td>
			</tr>
	</table> 
	--> 
	<div id= "divCont" style= "display: " >
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
				</td>
				<td class= titleImg>�ѳб�����������Ϣ</td>
			</tr>
		</table>    
		<div id= "divPol" style= "display: " >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display: none ">
				<input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage1.firstPage();"> 
				<input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage1.previousPage();"> 					
				<input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage1.nextPage();"> 
				<input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage1.lastPage();">
			</div>
		</div>	
		<!--�ѳб�������Ϣ-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotPol);">
				</td>
				<td class= titleImg>δ�б�����������Ϣ</td>
			</tr>
		</table>    
		<div id= "divNotPol" style= "display: ''" >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanNotPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display: ">
				<input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage2.firstPage();"> 
				<input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage2.previousPage();"> 					
				<input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage2.nextPage();"> 
				<input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage2.lastPage();">
			</div>
		</div>	
	</div>
	<!-- --------------------------------------------------- -->
		<!--������Ϣ-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpPol);">
				</td>
				<td class= titleImg>������Ϣ</td>
			</tr>
		</table>    
		<div id= "divGrpPol" style= "display: " >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanGrpPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display:  ">
				<input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage3.firstPage();"> 
				<input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage3.previousPage();"> 					
				<input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage3.nextPage();"> 
				<input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage3.lastPage();">
			</div>
		</div>	
		<hr class="line">
		<div id="Button" style="display:">
		<table  class= common>
				<tr  class= common>
					<td>
					<input class="cssButton" id="button1" name="button1" value="  ������ϸ��Ϣ��ѯ  " type="button" onClick="getContDetailInfo();">
					<input class="cssButton" id="button2" name="button2" value="  Ӱ�����ϲ�ѯ  " type="button" onClick="showImage();">    
					<input class="cssButton" id="button3" name="button3" value="  �˱����������ѯ  " type="button" onClick="showUWReport();">
					<input class="cssButton" id="button4" name="button4" value="  ����������ѯ  " type="button"  onclick="QueryRecord();">
					<input class="cssButton" id="button5" name="button5" value="  �˱��ȴ���ѯ  " type="button"  onclick="queryReason();">
					</td>
				</tr>
				<tr  class= common>
					<td>
					<input class="cssButton" id="button6" name="button6" value="  ����������ϲ�ѯ  " type="button" onClick="queryHealthReportResult();"> 
					<input class="cssButton" id="button7" name="button7" value="  �����������ϲ�ѯ  " type="button" onClick="queryRReportResult();"> 
					<input class="cssButton" id="button8" name="button8" value="  �����������ѯ  " type="button" onClick="QuestQuery();"> 
					<input class="cssButton" id="button9" name="button9" value="  �ٱ���ѯ  " type="button" onClick="showUpReportReply();">
					<input class="cssButton" id="button13" name="button13" value="  �������Ѳ�ѯ  " type="button" onClick="showTempFee();">
					<input class="cssButton" id="button10" name="button10" value="  �б����۱����ѯ  " disabled=true type="hidden" onClick="UWQuery();"> 	
					</td>
				</tr>
				<tr  class= common>
					<td>
				  	<input class="cssButton" id="button11" name="button11" value="  �����������ϲ�ѯ  " type="button" onClick="queryClaim();"> 
					<input class="cssButton" id="button12" name="button12" value="  ������ȫ���ϲ�ѯ  " type="button" onClick="queryEdor();"> 
					<input class="cssButton" id="button14" name="button14" value="  �ͻ������������ϲ�ѯ  " type="button" onClick="queryClaimCus();"> 
					<input class="cssButton" id="button15" name="button15" value="  �ͻ�������ȫ���ϲ�ѯ  " type="button" onClick="queryEdorCus();">
					</td>
				</tr>
			</table>      			              	                      
	    </div>
	<p>
	    <!-- modified by liuyuxiao  2011-05-27-->
        <INPUT VALUE="��  ��" class= cssButton TYPE=button onClick="parent.close();" style="display: none"> 	
        <INPUT type= "hidden" id= "InsureNoHide" value= "">
	    <INPUT type= "hidden" id= "AppntNoHide" name= "AppntNoHide" value= "">
	    <INPUT type= "hidden" id= "ContNoHide" name= "ContNoHide" value= "">	
	    <INPUT type= "hidden" id= "PrtNoHide" name= "PrtNoHide" value= "">	
	    <INPUT type= "hidden" id= "GrpContNoHide" name= "GrpContNoHide" value= "">	
	    <INPUT type= "hidden" id= "GridNoHide" name= "GridNoHide" value= "">	
	    <INPUT type= "hidden" id= "OldContNo" name= "OldContNo" value= "">	
	    <INPUT type= "hidden" id= "OldCustomerNo" name= "OldCustomerNo" value= "">	
	    <INPUT type= "hidden" id= "type" name= "type" value= "">	
    </P>
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
