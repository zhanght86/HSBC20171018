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
<SCRIPT src="LLUWJB.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LLUWPAllInit.jsp"%>
  <title>��ӡ�б��˱�֪ͨ�� </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./UWNoticeQuery.jsp" method=post name=fm  id=fm target="fraSubmit">
    <!-- Ͷ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>������Ͷ������ѯ������</td>
		  </tr>
	  </table>
      <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
      	
                  
          </TD> 
          <TD  class= title5>  ��������   </TD>
          <TD  class= input5>  <Input class= "common wid" name=ContNo > </TD>
          <Input  type = "hidden" class= common name=PrtNo >
          <TD  class= title5>   �������  </TD>
          <TD  class= input5>  <Input class="common wid" name=ManageCom 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this]);" 
          onDblClick="return showCodeList('station',[this]);"
           onKeyUp="return showCodeListKey('station',[this]);">  </TD>
        
         
         </TR>
         <TR  class= common style = "display: none">
         	  <TD  class= title5> ӪҵԱ���� </TD>
          <TD  class= input5>  <Input "common wid" name=AgentCode id=AgentCode
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentCode',[this]);" 
           onKeyUp="return showCodeListKey('AgentCode',[this]);">   </TD>
         <TD  class= title5> Ӫҵ����Ӫҵ�� </TD>
          <TD  class= input5>  <Input "common wid" name=AgentGroup id=AgentGroup
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentGroup',[this]);"  
           onDblClick="return showCodeList('AgentGroup',[this]);" 
           onKeyUp="return showCodeListKey('AgentGroup',[this]);">   </TD>
         
		
          </TD>
        </TR>
    </table>
    </div>
    </div>
<Input class= "codeno" type=hidden name = NoticeType value="LP00" onDblClick="return showCodeList('lluwnoticeprint',[this,NoticeTypeName],[0,1]);" onKeyUp="return showCodeListKey('lluwnoticeprint',[this,NoticeTypeName],[0,1]);"><!-- <Input class = codename name= NoticeTypeName readonly = true > -->
         <!-- <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQueryClick();">-->
          <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>

  </form>
  <form action="./LLUWPAllSave.jsp" method=post name=fmSave target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>Ͷ������Ϣ </td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
    <div class="maxbox1" >
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
    	</div>
  	
  	<p>
      <!--<INPUT VALUE="��ӡ����˱�֪ͨ��" class= cssButton TYPE=button onClick="printPol();">-->
      <a href="javascript:void(0);" class="button"onClick="printPol();">��ӡ����˱�֪ͨ��</a>
  	</p>
    
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="OldPrtSeq" name="OldPrtSeq">
  	<input type=hidden id="ContNo" name="ContNo">
  	<input type=hidden id="NoticeType" name="NoticeType">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="SubMissionID" name="SubMissionID">
  	<input type=hidden id="PrtNo" name="PrtNo">
  	<input type=hidden id="Address" name="Address">
  	<input type=hidden id="BranchGroup" name="BranchGroup">
  	<input type=hidden id="ClmNo" name="ClmNo">
  	<input type=hidden id="BatNo" name="BatNo">
    </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
