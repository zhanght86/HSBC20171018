<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RecordQuery.jsp
//�����ܣ�������ѯ
//�������ڣ�2005-06-22 11:10:36
//������  ��CCVIP
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //Ͷ������
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tContType=request.getParameter("ContType"); //�������ı������ͣ�1--������2--�ŵ�---hp
  //GlobalInput tGI = new GlobalInput();
	//tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("RecordQuery","tContType:"+tContType);
%>
<script language="JavaScript">
  var tContNo = "<%=tContNo%>"; //Ͷ������
  var tPrtNo = "<%=tPrtNo%>"; //Ͷ������
  var tContType="<%=tContType%>"; //��������;
 // fm.all.('ContNo').value = tContNo;
</script>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>������ѯ</title>


  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <script src="RecordQuery.js"></SCRIPT>
  
  
  <%@include file="RecordQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRecord);">
	    	</td>
	    	<td class= titleImg>
	    	 ����������Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  
	  <div id= "divRecord" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td>
  				  <span id="spanRecordGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
       <!-- <div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
          <input class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 					
          <input class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"> 
          <input class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();">
        </div>-->
    </div>

    <div id="QueryButton" style="display:''">
    
    <input type="hidden" name="ContNo" value="">
    
 <!-- <table class= common align=center>
  	  
     <tr class= common>
     <td class= input align=lift> -->
   <INPUT class=cssButton VALUE="�������ѯ"   TYPE=button name="Button1" onclick="HealthQuery();">
   <INPUT class=cssButton VALUE="���������ѯ"   TYPE=button name="Button2" onclick="MeetQuery();">
   <INPUT class=cssButton VALUE="   �˱���ѯ   " TYPE=button name="Button3" onclick="UWQuery();"> 	
   <INPUT class=cssButton VALUE=" �ٱ��ظ���ѯ " TYPE=button name="Button4" onclick="UpReportQuery();">
   <!--INPUT class=cssButton VALUE="  ��Լ��ѯ  " TYPE=button onclick="ShowSpecialQuery();"-->
   <!--INPUT class=cssButton VALUE="�ͻ��ϲ�֪ͨ���ѯ" TYPE=button name="Button5" onclick="KHHBNoticQuery();"-->
   <!--INPUT class=cssButton VALUE="  �����ۼ�  " TYPE=button onclick="ShowAddAmntAccumulate();">   
   <INPUT class=cssButton VALUE="�˱��ӷѲ�ѯ" TYPE=button onclick="ShowUWAddAmntQuery();"-->
   <!--INPUT class=cssButton VALUE="������֪��ѯ" TYPE=button onclick="ImpartQuery();"-->
   <!--INPUT class=cssButton VALUE="������������ϲ�ѯ" TYPE=button onclick="InsuredHealthQuery();"-->
   <!--INPUT class=cssButton VALUE="�����˽�����֪��ѯ" TYPE=button onclick="InsuredImpartQuery();"-->
   <!--INPUT class=cssButton VALUE="Ͷ���˱����ۼƲ�ѯ" TYPE=button onclick="AmntAccumulateQuery();"-->
   <!--INPUT class=cssButton VALUE="�����˱����ۼƲ�ѯ" TYPE=button onclick="InsuredAmntAccumulateQuery();"--> 
     
      <!-- </td>
  	  </tr> 
     <tr class= common>
     <td class= input align=lift> -->
   <!--INPUT class=cssButton VALUE="Ӱ�����ϲ�ѯ" TYPE=button onclick="ImageQuery();"-->
   <INPUT class=cssButton VALUE="�Ժ���ʾ��ѯ"   TYPE=button name="Button6" onclick="UWErrQuery();"> 
	 <INPUT class=cssButton VALUE=" �������ѯ "   TYPE=button name="Button7" onclick="ShowQuest();">
   <!--INPUT class=cssButton VALUE="�˱�֪ͨ���ѯ" TYPE=button name="Button8" onclick="UWNoticQuery();"-->
   <INPUT class=cssButton VALUE="  ���±���ѯ  " TYPE=button name="Button9" onclick="ShowNotepadQuery();">
   <!-- </td>
    </tr>
	   <td class= input>-->
	 <!--<INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="GoBack();"> -->
    <!--   </td>
  	  </tr>   
  	</table>	-->
    </div>
    <br>
<a href="javascript:void(0);" class="button" onClick="GoBack();">��    ��</a>
		<!--������-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>

</html>
