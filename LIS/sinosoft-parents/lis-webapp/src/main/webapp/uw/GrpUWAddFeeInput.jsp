<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpec.jsp
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpUWAddFee.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �ӷѳб� </title>
  <%@include file="GrpUWAddFeeInit.jsp"%>
</head>
<body  onload="initForm()" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./GrpUWAddFeeChk.jsp">
   <Div  id= "divLCPol" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<center>
        <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
        <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
        <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
        <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> 	
        </center>
    </Div>
    
    
    <Div  id= "divUWSpec1" style= "display: none">
        <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 �ӷѲ�ѯ���
    		</td>
    	</tr>
       </table>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
   </div>
      <INPUT type= "hidden" id="PolNo2" name= "PolNo2" value= ""> <!--��ȫ�ӷ�����Եı���-->
      <INPUT type= "hidden" id="PolNo" name= "PolNo" value= ""> <!--��ȫ��Ŀ����Եı���,��ǰһҳ�洫��-->
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
      <p>
     <INPUT type= "button" id="sure" name= "sure" value="ȷ  ��" class= cssButton onclick="submitForm()">			
    </p>
	  
 	
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
