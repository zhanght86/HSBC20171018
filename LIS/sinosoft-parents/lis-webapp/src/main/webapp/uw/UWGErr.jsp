<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGErr.jsp
//�����ܣ�����˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<%
  String tGrpContNo = "";
  tGrpContNo = request.getParameter("GrpContNo");
  String tBqFlag = "";
  tBqFlag = request.getParameter("BqFlag");
  String tEdorNo = request.getParameter("EdorNo");
//  loggerDebug("UWGErr","!!!tGrpContNo:" + tGrpContNo);
%>
<script>	
	var EdorNo = "<%=tEdorNo%>"; //��¼��½����
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>  
  <SCRIPT src="UWGErr.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWGErrInit.jsp"%>
  <title>�����˱���¼ </title>
</head>
<body  onload="initForm('<%=tGrpContNo%>','<%=tBqFlag%>');" >
  <form method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- �����˱���¼���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGUWSub1);">
    		</td>
    		<td class= titleImg>
    			 �ŵ��Զ��˱���Ϣ 
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCGUWSub1" style= "display: ''">
    	<table  class= common >
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWGErrGrid" >
					</span> 
				</td>
			</tr>
		</table>
        <center>
        <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
	    <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
	    <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
	    <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
	    <!--<p align=left>
	        <INPUT VALUE="��ӡ�ŵ��˱���Ϣ" class="cssButton"  TYPE=button onclick="grpUWPrint();">         
	    </p>-->
	</div>	        
    <a href="javascript:void(0);" class="button" onClick="grpUWPrint();">��ӡ�ŵ��˱���Ϣ</a>
	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCUWSub1);">
    		</td>
    		<td class= titleImg>
    			 �����Զ��˱���Ϣ 
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCUWSub1" style= "display: ''">
    	<table  class= common >
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWErrGrid" >
					</span> 
				</td>
			</tr>
		</table>
        <center>
        <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
	    <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
	    <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
	    <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();"></center>
	</div>
	<!--<p>
        <INPUT VALUE="��ӡ�����˱���Ϣ" class="cssButton"  TYPE=button onclick="uwPrint();"> 
        <INPUT VALUE="��  ��" class="cssButton"  TYPE=button onclick="parent.close();"> 					
    </P>-->
    <a href="javascript:void(0);" class="button" onClick="uwPrint();">��ӡ�����˱���Ϣ</a>
    <a href="javascript:void(0);" class="button" onClick="parent.close();">��    ��</a>
    <INPUT type= "hidden" id="GrpContNo" name= GrpContNo value= "">
    <INPUT type= "hidden" id="BqFlag" name= BqFlag value= "">				
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
