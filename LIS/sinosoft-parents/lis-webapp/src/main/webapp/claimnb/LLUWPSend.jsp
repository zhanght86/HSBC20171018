<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�QuestInput.jsp
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tMissionID = "";
	String tSubMissionID = "";
	String tEdorNo = "";
	String tEdorType = "";
	
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
	tEdorNo =  request.getParameter("EdorNo");
	tEdorType =  request.getParameter("EdorType");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLUWPSend.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
  <%@include file="LLUWPSendInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tEdorNo%>','<%=tEdorType%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./LLUWPSendSave.jsp">
  	
  		<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>�������Ϣ</td>
		</tr>
	</table>
	<div class=maxbox>
    <table  class= common align=center >
    	<TR  class= common style= "display: none">
    	  <TD  class= title5>֪ͨ������</TD>
          <TD class=input5>
          <Input class= "codeno" name= NoticeType id=NoticeType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('uwnoticetype',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('noticetype',[this,NoticeTypeName],[0,1]);">
		  <Input class = codename name=NoticeTypeName readonly = true>
          </TD>
		  <TD class=title5></TD>
          <TD class=input5></TD>		  
        </tr>
    </table>
    
    <div id= "divnoticecontent" style= "display: none" >	
        <table class= common align=center width="121%" height="37%">
            <TR  class= common > 
                 <TD width="100%" height="13%"  class= title> ֪ͨ������ </TD>
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
    		<td class= titleImg>�ʾ�����ѡ��</td>                            
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
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" name= "sure" id=sure class= cssButton  value="����֪ͨ��" onClick="submitForm()">
</P>

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
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
  
  <p> 
  	
  <table>
		<tr>
			<td class=common>
				<INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="top.close();">
			</td>
		</tr>  	
 </table>
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "ContNo" id=ContNo value= "">
    <input type= "hidden" name= "MissionID" id=MissionID value= "">
    <input type= "hidden" name= "SubMissionID" id=SubMissionID value= "">
    <input type= "hidden" name= "EdorNo" id=EdorNo value= "">
    <input type= "hidden" name= "EdorType" id=EdorType value= "">
  </p>
</form>
<br /> <br /><br /><br />

</body>
</html>
