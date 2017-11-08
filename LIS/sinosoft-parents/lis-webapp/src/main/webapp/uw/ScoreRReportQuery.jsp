<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：ScoreRReportQuery.jsp
//程序功能：生调评分汇总
//创建日期：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ScoreRReportQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>新单核保等待</title>
  <%@include file="ScoreRReportQueryInit.jsp"%>
<script language="javascript">
  	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>  
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <div id="divSearch">
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
		<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
    </table>
   <div id="divInvAssBuildInfo">
       <div class="maxbox1" > 
    <table  class= common align=center>
      	<tr  class= common>
          <td  class= title5>印刷号</TD>
          <td  class= input5> <Input class="common wid" id="PrtNo" name=PrtNo> </TD>
          </TR>
        <tr  class= common>  
          <td  class= title5> 管理机构  </TD>
          <td  class= input5>  <Input class="codeno" name=ManageCom id=ManageCom
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('station',[this,ManageComName],[0,1]);" 
           onDblClick="showCodeList('station',[this,ManageComName],[0,1]);" 
           onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true>  </TD>
          <td  class= title5>  核保师工号  </TD>
          <td  class= input5>   <Input class="common wid" id="AssessOperator" name=AssessOperator >   </TD>
            </TR>
        <tr  class= common>                      
          <td  class= title5>  生调员姓名 </TD>
          <td  class= input5> <Input class="common wid" id="Name" name=Name > </TD>
          <td  class= title5> 生调员工号  </TD>
          <td  class= input5>  <Input class="common wid" id="CustomerNo" name=CustomerNo >  </TD>
        </TR>
        <tr  class= common>
          <TD  class= title5 >起始日期 </TD>
       		<TD  class= input5 >  
            <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" name=StartDate id=StartDate><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
             src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
       		<TD  class= title5 >截止日期  	</TD>
       		<TD  class= input5 >
            <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short" name=EndDate id=EndDate><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 
             src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>         
    </table>
    </div>
    </div>
          <!--<INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQueryClick();">-->
          <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
    </div>
  <table>
      <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 生调评审信息
    		</td>
      </tr>
  </table>
  <Div  id= "divLCPol2" style= "display: ''" >
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanScoreRReportGrid">
  					</span>
  			  	</td>
  			</tr>
      </table>  
    	<div align=center>
    	<input CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
    </div>
    <p></p> 
	
    <!--<a href="javascript:void(0);" class="button"onClick="ScoreQuery();">生调评分表</a>
    <a href="javascript:void(0);" class="button"onClick="showPolDetail();">投保单明细查询</a>
    <a href="javascript:void(0);" class="button"onClick="ScanQuery();">影像资料查询 </a>-->
    <INPUT VALUE=" 生调评分表 " class=cssButton TYPE=button id="Button1" name="Button1" onClick="ScoreQuery();">
    <INPUT VALUE=" 投保单明细查询 " class=cssButton TYPE=button id="Button2" name="Button2" onClick="showPolDetail();">
    <INPUT VALUE="  影像资料查询  " class=cssButton TYPE=button id="Button3" name="Button3" onClick="ScanQuery();">
 
  </Div>	  
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="ContNoH" name= "ContNoH" value= "">
    <input type= "hidden" id="PrtNoH" name= "PrtNoH" value= "">
  </p>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
