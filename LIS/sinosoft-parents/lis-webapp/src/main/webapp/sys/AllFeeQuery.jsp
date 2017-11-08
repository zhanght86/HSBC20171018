<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="AllFeeQuery.js"></SCRIPT>
  <%@include file="AllFeeQueryInit.jsp"%>
  <title>交费查询 </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入交费查询条件：</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5> 交费收据号码 </TD>
          <TD  class= input5> <Input class="common wid" name=PayNo > </TD>
          <TD  class= title5>  应收/实收编号</TD>
          <TD  class= input5>  <Input class="common wid" name=IncomeNo > </TD>
           </TR>
           	<TR  class= common>
          <TD  class= title5> 应收/实收编号类型 </TD>
         <TD  class= input5><Input  class="common wid" name=IncomeType verify="应收/实收编号类型" 
          CodeData="0|^1|集体合同号^2|个人合同号^3|家庭单大合同号^5|理赔赔案号^10|保全批改号"
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="showCodeListEx('FeeIncomeType',[this],[0,1,2,3]);"  
          ondblClick="showCodeListEx('FeeIncomeType',[this],[0,1,2,3]);"
           onKeyUp="showCodeListKeyEx('FeeIncomeType',[this],[0,1,2,3]);">           </TD>          
          <TD  class= title5> 交费日期 </TD>
          <TD  class= input5>	
          <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#PayDate'});"dateFormat="short" name=PayDate id=PayDate><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5> 管理机构 </TD>
          <TD  class= input5><Input  class="common wid" name=MngCom verify="管理机构|code:comcode" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('comcode',[this],null,null,null,null,1);"
          onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);"
           onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" readonly> </TD>
          <TD  class= title5> 代理人编码  </TD> 
          <TD  class= input5> <Input  class="common wid" name=AgentCode verify="代理人编码|notnull" onDblClick="queryAgent()" > </TD>
        </TR>
    </table>
    </div>
    </div>
    <a href="javascript:void(0);" class="button" id="feequery" onClick="easyQueryClick();">查   询</a>
    <a href="javascript:void(0);" class="button" onClick="getQueryDetail();">费用明细</a>

         <!-- <INPUT VALUE="查  询" class= cssButton TYPE=button name="feequery" onClick="easyQueryClick();"> 
          <INPUT VALUE="费用明细" class= cssButton TYPE=button onClick="getQueryDetail();"> 				-->	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 费用信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
     
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91  TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
