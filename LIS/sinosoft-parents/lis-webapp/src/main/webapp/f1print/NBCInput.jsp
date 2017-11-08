<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="NBC.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NBCInit.jsp"%>
  <title>打印新契约退费通知书 </title>
</head>
<body  onload="initForm();" >
  <form action="./NBCQuery.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入保单查询条件：</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>  保单号   </TD>
          <TD  class= input5>  <Input class="common wid" name=ContNo > </TD>
           <TD  class= title5> 代理人编码 </TD>
          <!--TD  class= input>  <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">   </TD--> 
           <TD  class= input5><Input class="common wid" name=AgentCode >   </TD>
            </TR> 
         <TR  class= common>
                     <TD class=title5> 管理机构 </TD>  
          <TD class= input5 ><Input class= "codeno" name = ManageCom  id = ManageCom 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);" 
          onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" 
          onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);" ><Input class = codename name=ManageComName readonly = true> </TD>

        
          <TD  class= title5>  通知书类型 </TD>
          <TD class= input5>
          <!--Input class= "codeno" name = NoticeType ondblclick="return showCodeList('yjtfnoticetype',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('yjtfnoticetype',[this,NoticeTypeName],[0,1]);"><Input class = codename name= NoticeTypeName readonly = true-->         
          <Input class= "codeno" name=NoticeType
           CodeData="0|^01|个单溢交退费通知书^02|团单溢交退费通知书" 
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeListEx('NoticeType', [this,NoticeTypeName],[0,1])"
           onDblClick="return showCodeListEx('NoticeType', [this,NoticeTypeName],[0,1])"
           onkeyup="return showCodeListKeyEx('NoticeType', [this,NoticeTypeName],[0,1])"><input class=codename name=NoticeTypeName readonly=true>
          </TD>
           </TR> 
         <TR  class= common> 
         <TD class=title5> 销售渠道 </TD>  
         <TD class= input5 ><Input class= "codeno" name = SaleChnl  id = SaleChnl
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onDblClick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onKeyUp="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);" ><Input class = codename name=SaleChnlName readonly = true> </TD>  	
        </TR>  
    </table>
    </div></div>
    
  	 <Input type=hidden  name=AgentGroup > 
         <!-- <INPUT VALUE="查  询" class=  "cssButton"  TYPE=button onClick="easyQueryClick();"> -->
         <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
  </form>
  <form action="./NBCF2PSave.jsp" method=post name=fmSave target="f1print">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 通知书信息
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
      <INPUT VALUE="首  页" class=  "cssButton90"  TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class=  "cssButton91"  TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class=  "cssButton92"  TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾  页" class=  "cssButton93"  TYPE=button onClick="getLastPage();"> 	
  	</div>
  	<!--<p>
      <INPUT VALUE="打  印" class= "cssButton" TYPE=button onClick="printPol();">
  	</p>-->
     <a href="javascript:void(0);" class="button"onClick="printPol();">打  印</a>
     <br><br><br><br>
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type = hidden id = "PrtSeq" name = "PrtSeq">
  	<input type=hidden id="ContNo" name="ContNo">
  		<input type=hidden id = "noticetype" name = "noticetype">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
