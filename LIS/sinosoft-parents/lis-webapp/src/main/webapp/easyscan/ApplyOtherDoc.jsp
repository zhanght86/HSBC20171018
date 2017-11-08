<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：ApplyIssueDoc.jsp
//程序功能：
//创建日期：2006-04-07
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
		String mOperator = tG1.Operator;
%>
<script>
	var moperator = "<%=mOperator%>"; //记录管理机构
</script>
<head >
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./ApplyOtherDoc.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ApplyOtherDocInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ApplyOtherDocSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
        </td>
				<td class= titleImg>扫描单证信息</td>
      </tr>
    </table>
    <div class="maxbox1">		 
    <Div  id= "divCode1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>印刷号</TD>
          <TD  class= input5><Input class= "common wid" name=BussNo id="BussNo" ></TD>
          <TD  class= title5>管理机构</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=ManageCom id="ManageCom" value="<%=Branch%>"
              onclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
          		ondblclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            	onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>录单状态</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=InputState id="InputState" CodeData="0|^0|录单未完成^1|录单完成" 
          		ondblClick="showCodeListEx('InputState',[this],[0,1]);"
              onclick="showCodeListEx('InputState',[this],[0,1]);"
          		onkeyup="showCodeListKeyEx('InputState',[this],[0,1]);" verify="录单状态|code:InputState"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>起始日期（扫描）</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期（扫描）|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>结束时间（扫描）</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束时间（扫描）|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TR>
      </table>
    </Div>
  </div>
    <div>
    </div>
   <INPUT VALUE="查  询" TYPE=button class=cssButton onclick="easyQueryClick()"> 
    <br>
  	<Div  id= "divCodeGrid" style= "display: ''" align=center>
    	<table  class= common>
     		<tr  class= common>
  	  		<td text-align: left colSpan=1><span id="spanCodeGrid" ></span></td>
				</tr>
  		</table>
    	<INPUT VALUE="首  页" TYPE=button class="cssButton90" onClick="getFirstPage();"> 
    	<INPUT VALUE="上一页" TYPE=button class="cssButton91" onClick="getPreviousPage();">
    	<INPUT VALUE="下一页" TYPE=button class="cssButton92" onClick="getNextPage();"> 
    	<INPUT VALUE="尾  页" TYPE=button class="cssButton93" onClick="getLastPage();">
  	</div>
  	<br>
  	<Div  id= "divReason" style= "display: none">
	    <Table class= common>
				<TR class= common>
					<TD class= title5>申请类型：</TD>
                    <td class="input5"><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=Reason id="Reason" readonly
					CodeData="0|^UR200|人身保险投保提示^UR202|投保要约更正申请书(打印)^UR203|投保要约更正申请书(不打印)^UR204|生效日回溯^UR205|体检资料^UR206|未成年人身故保险金特别约定(打印)^UR207|问卷^UR208|身份证明^UR209|病例资料^UR210|其他资料^UR211|分红产品说明书^UR212|补充告知问卷"
          onclick="showCodeListEx('Reason',[this,Content],[0,1]);"
          		ondblClick="showCodeListEx('Reason',[this,Content],[0,1]);"
          		onkeyup="showCodeListKeyEx('Reason',[this,Content],[0,1]);" verify="申请类型|NOTNULL"></TD>
                <TD class= title5></TD>
                <td class="input5"></td>
                </TR>
                <tr>
					<TD  class= input><textarea name="Content" cols="170%" rows="4" witdh=25% class="common" verify="申请原因|NOTNULL"></textarea></TD></TR>
	 		</table>
	    <input type=hidden id="fmtransact" name="fmtransact">
	    <INPUT VALUE="申  请" TYPE=button class="cssButton" name="APPLY" id="APPLY" onclick="saveApply()">
<!--
	    <INPUT VALUE="申请撤销" TYPE=button class=common onclick="undoApply()">
-->
  	</div>
    <br>
    <br>
    <br>
    <br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
