<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：UWNoticQuery.jsp
//程序功能：个险核保通知书查询
//创建日期：2005-8-18 15:07
//创建人  ：guomy
//更新记录：  更新人    更新日期     更新原因/内容
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
  <title>核保问题件录入</title>
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
	    	 已发通知书
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
    <!--读取信息-->
    <input type= "hidden" name= "ContNo" id=ContNo value= "">
  </p>
  <div id ="divOperation" style = "display : ''" class=maxbox1>
    <table class=common>
       <tr  class= common>
          <td class=title>发送人</td><td class=common><Input  class="common wid" name=Operator id=Operator readonly></td>
          <td class=title>发送时间</td><td class=common><Input  class="common wid" name=MakeDate id=MakeDate readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>回复时间</td><td class=common><Input  class="common wid" name=ReplyDate id=ReplyDate readonly></td>
       </tr>
    </table>
    </div>
  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContent);">
	    	</td>
	    	<td class= titleImg>
	    	 通知书内容
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
