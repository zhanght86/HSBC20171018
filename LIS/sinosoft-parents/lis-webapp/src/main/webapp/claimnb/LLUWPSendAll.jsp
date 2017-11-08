<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：LLUWPSendAll.jsp
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tMissionID    = "";
	String tSubMissionID = "";
	String tEdorNo       = "";
	String tEdorType     = "";
	String tClmNo        = "";
	
	tMissionID    = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
	tEdorNo       =  request.getParameter("EdorNo");
	tEdorType     =  request.getParameter("EdorType");
	tClmNo        = request.getParameter("ClmNo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLUWPSendAll.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>核保问题件录入</title>
  <%@include file="LLUWPSendAllInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tEdorNo%>','<%=tEdorType%>','<%=tClmNo%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./LLUWPSendAllSave.jsp">
  	<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>问题件信息</td>
		</tr>
	</table>
	<div class=maxbox1>
   <table  class= common align=center>
      <TR  class= common>
    	 <TD  class= title5>通知书类型</TD>
         <TD class=input5><Input class= "codeno" name =NoticeType id=NoticeType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('lluwnoticesend',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('lluwnoticesend',[this,NoticeTypeName],[0,1]);">
		 <Input class = codename name=NoticeTypeName id=NoticeTypeName readonly = true>
         </TD>
		 <TD  class= title5></TD>
         <TD class=input5></td>
      </tr>
   </table>
   
  <div id= "divnoticecontent" style= "display: none" >	
  <table class= common align=center width="121%" height="37%">
    <TR  class= common > 
      <TD width="100%" height="13%"  class= title> 通知书内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
</div>
</div>
    <Div  id= "divUWSpec1" style= "display: none">
    <table>
    	<tr>
        	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec);"></td>
    		<td class= titleImg>问卷类型选择</td>                            
    	</tr>	
    </table>
  </div>
    <Div  id= "divUWSpec" style= "display: none">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1 >
  							<span id="spanQuestionTypeGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
    </Div>		
<p>
   <table class=common>
	 <tr class=common>
	   <td class=common>
         <span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
         <input type= "button" name= "sure" id=sure class= cssButton  value="发送通知书" onClick="submitForm()">
       </td>
     </tr>
   </table>

   <hr class=line>
   <table class=common>
	  <tr class=common>
	    <td class=titleImg><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);"> 已发通知书</td>
	  </tr>
   </table>    
	<div id= "divPolGrid" style= "display: ''" >
        <table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
        </div>
   </div>
   <div id="Button" style="display:">
     <hr class=line>
       <table>
         <tr>
           <td>
              <Input Value ="返    回" class=cssButton Type=button onclick="top.close();"> 
           </td>
        </tr>
   </table>
 </div>
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "ContNo" id=ContNo value= "">
    <input type= "hidden" name= "ClmNo" id=ContNo value= "">
    <input type= "hidden" name= "MissionID" id=MissionID value= "">
    <input type= "hidden" name= "SubMissionID" id=SubMissionID value= "">
    <input type= "hidden" name= "EdorNo" id=EdorNo value= "">
    <input type= "hidden" name= "EdorType" id=EdorType value= "">
  </p>
</form>
  

</body>
</html>
