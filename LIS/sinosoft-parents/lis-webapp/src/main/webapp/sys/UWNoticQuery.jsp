<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�UWNoticQuery.jsp
//�����ܣ����պ˱�֪ͨ���ѯ
//�������ڣ�2005-8-18 15:07
//������  ��guomy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tContNo = "";
	tContNo = request.getParameter("ContNo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWNoticQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
  <%@include file="UWNoticQueryInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./SendAllNoticeChk.jsp">
  

 <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 �ѷ�֪ͨ��
	    	</td>
	    </tr>
 </table>    
	<div id= "divPolGrid" style= "display: ''" >
        <table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "ContNo" id=ContNo value= "">
  </p>
  <div id ="divOperation" style = "display : ''" class=maxbox1>
    <table class=common>
       <tr  class= common>
          <td class=title>������</td><td class=common><Input  class="common wid" name=Operator id=Operator readonly></td>
          <td class=title>����ʱ��</td><td class=common><Input  class="common wid" name=MakeDate id=MakeDate readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>�ظ�ʱ��</td><td class=common><Input  class="common wid" name=ReplyDate id=ReplyDate readonly></td>
       </tr>
    </table>
    </div>
  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContent);">
	    	</td>
	    	<td class= titleImg>
	    	 ֪ͨ������
	    	</td>
	    </tr>
 </table> 
  <div id="divContent" style= "display: ''">
        <table  class= common>
         
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					 <textarea name="Content" id=Content cols="135" rows="10" class="common"></textarea>
  			  	</td>
  			</tr>
    	</table>  
  
  <div>
</form>
  

</body>
</html>
