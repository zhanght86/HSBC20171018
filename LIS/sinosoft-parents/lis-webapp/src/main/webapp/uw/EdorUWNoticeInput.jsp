<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var managecom = "<%=tGI.ManageCom%>"; //��¼�������
var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="EdorUWNoticeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorUWNoticeInit.jsp"%>
  <title>��ӡ�б��˱�֪ͨ�� </title>
</head>
<body  onload="initForm();" >
  <form action="./UWNoticeQuery.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- Ͷ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>������Ͷ������ѯ������</td>
		  </tr>
	  </table>
      <div id="divInvAssBuildInfo">
       <div class="maxbox" >
    <table  class= common align=center>
      	<TR  class= common>
      	
          <TD  class= title5>
          ֪ͨ������ 
          </TD>
          <TD class= input5 > 
          <Input class= "codeno" name = NoticeType   id= NoticeType 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
             onclick="return showCodeList('bquwnotice',[this,NoticeTypeName],[0,1],null,'1 and code in(#25#,#27#,#BQ84#)','1');" 
          onDblClick="return showCodeList('bquwnotice',[this,NoticeTypeName],[0,1],null,'1 and code in(#25#,#27#,#BQ84#)','1');" 
          onKeyUp="return showCodeListKey('NoticeType',[this,NoticeTypeName],[0,1],null,'1 and code in(#25#,#27#,#BQ84#)','1');">
		  <Input class = codename id="NoticeTypeName" name= NoticeTypeName readonly = true>
          
          </TD> 
          
                  
          </TD> 
          <TD  class= title5>  ��������   </TD>
          <TD  class= input5>  <Input class="common wid" id="ContNo" name=ContNo > </TD>
      <!--    <TD  class= title>  Ͷ����ӡˢ��   </TD> -->
          <Input  type = "hidden" class= common id="PrtNo" name=PrtNo >
          </TR>
         <TR  class= common>
          <TD  class= title5>   �������  </TD>
          <TD  class= input5>  <Input class="common wid" id="ManageCom" name=ManageCom 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this]);" 
          onDblClick="return showCodeList('station',[this]);" 
          onKeyUp="return showCodeListKey('station',[this]);">  </TD>
        
         
         
         	  <TD  class= title5> ӪҵԱ���� </TD>
          <TD  class= input5>  <Input class="common wid" id="AgentCode" name=AgentCode >   </TD>
          </TR>
         <TR  class= common>
         <TD  class= title5> Ӫҵ����Ӫҵ�� </TD>
          <TD  class= input5>  <Input class="common wid" id="AgentGroup" name=AgentGroup 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentGroup',[this]);"
          onDblClick="return showCodeList('AgentGroup',[this]);"
           onKeyUp="return showCodeListKey('AgentGroup',[this]);">   </TD>
         
		
          </TD>
        </TR>
    </table>
    </div></div>
         <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQueryClick();">
  </form>
  <form action="./EdorUWF1PSave.jsp" method=post name=fmSave id=fmSave target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();">
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();">
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();">
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();">
  	</div>
    <p>
      <INPUT VALUE="��ӡ��ȫ�˱�֪ͨ��" class= cssButton TYPE=button onClick="printPol();">
  	</p>
     <br><br><br><br>
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="ContNo" name="ContNo">
  	<input type=hidden id="NoticeType" name="NoticeType">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="SubMissionID" name="SubMissionID">
  	<input type=hidden id="PrtNo" name="PrtNo">
  	<input type=hidden id="Address" name="Address">
  	<input type=hidden id="FunctionID" name="FunctionID" value='10020320'>
    <input type=hidden id="ActivityID" name="ActivityID">
  	<input type=hidden id="BranchGroup" name="BranchGroup">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="EdorType" name="EdorType">
  	
    </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body><br/><br/><br/><br/>
</html>
