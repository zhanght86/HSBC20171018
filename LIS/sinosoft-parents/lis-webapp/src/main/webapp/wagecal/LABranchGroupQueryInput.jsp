<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LABranchGroupQueryInput.jsp
//程序功能：
//创建日期：
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    GlobalInput tG2 = new GlobalInput();
    tG2 = (GlobalInput)session.getValue("GI");
%>
<script>
	var commonManageCom = "<%=tG2.ManageCom%>";
</script>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./LABranchGroupQuery.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LABranchGroupQueryInit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

  <title>展业机构 </title>
</head>
<body  onload="initForm();" >
  <form action="./LABranchGroupQuerySubmit.jsp" method=post name=fm id="fm" target="fraSubmit">
  
<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLABranchGroup1);">
    </IMG>
      <td class=titleImg>
      查询条件
      </td>
    </td>
    </tr>
    </table>
    <Div  id= "divLABranchGroup1" class="maxbox" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            代码
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=BranchAttr id="BranchAttr" >
          </TD>
          <TD  class= title5>
            名称
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=Name id="Name" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
            <Input class="code" name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" verify="管理机构|code:comcode&NOTNULL" onClick="return showCodeList('comcode',[this],null,null,null,null,1);" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);"> 
          </TD>
          <!--TD  class= title>
            类型
          </TD>
          <TD  class= input>
            <Input class='code' name=BranchType maxlength=2 ondblclick="return showCodeList('BranchType',[this]);" 
                                                            onkeyup="return showCodeListKey('BranchType',[this]);">
          </TD-->
          <TD  class= title5>
            级别
          </TD>
         <TD  class= input5>
            <Input class= 'code' name=BranchLevel id="BranchLevel" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"
 verify="展业机构级别|code:BranchLevel" onClick="return showCodeList('<%=tSql1%>',[this],[0,1],null,'<%=tSqlBranchLevel%>','1');" onDblClick="return showCodeList('<%=tSql1%>',[this],[0,1],null,'<%=tSqlBranchLevel%>','1');"  onkeyup="return showCodeListKey('<%=tSql1%>',[this],[0,1],null,'<%=tSqlBranchLevel%>','1');" >
          </TD>
          </TR>
        <!--TR  class= common>
          <TD  class= title>
            地址编码
          </TD>
          <TD  class= input>
            <Input class= 'code' name=BranchAddressCode ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= title>
            地址
          </TD>
          <TD  class= input>
            <Input class= common name=BranchAddress >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            电话
          </TD>
          <TD  class= input>
            <Input class= common name=BranchPhone >
          </TD>
          <TD  class= title>
            传真
          </TD>
          <TD  class= input>
            <Input class= common name=BranchFax >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            邮编
          </TD>
          <TD  class= input>
            <Input class= common name=BranchZipcode maxlength=6 >
          </TD>           
        </TR-->
        <TR class=common>
          <TD  class= title5>
            管理人员代码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=BranchManager id="BranchManager" >
          </TD>
        <TD  class= title5>
            管理人员名称
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=BranchManagerName id="BranchManagerName" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            成立标志日期
          </TD>
          <TD  class= input5>
            <Input class= 'coolDatePicker' name=FoundDate format='short' id="FoundDate" onClick="laydate({elem:'#FoundDate'});"><span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            停业日期
          </TD>
          <TD  class= input5>
            <Input class='coolDatePicker' name=EndDate format='short' id="EndDate" onClick="laydate({elem:'#EndDate'});"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
         </TR>
          <!--TD  class= title>
            停业
          </TD>
          <TD  class= input>
            <Input class='code' name=EndFlag MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" 
                                                         onkeyup="return showCodeListKey('yesno',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            是否单证交接
         </TD>
          <TD  class= input>
            <Input class= 'code' name=CertifyFlag MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" 
                                                              onkeyup="return showCodeListKey('yesno',[this]);">
          </TD>
          <TD  class= title>
            是否有独立的营销职场
          </TD>
          <TD  class= input>
            <Input class= 'code' name=FieldFlag MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" 
                                                            onkeyup="return showCodeListKey('yesno',[this]);" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            标志
          </TD>
          <TD  class= input>
            <Input class=common name=State MaxLength=10 >
          </TD>
          <TD  class= title>
            操作员代码
          </TD>
          <TD  class= input>
            <Input class=common name=Operator >
          </TD>
        </TR-->
      </table>
          <INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQueryClick();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGroupGrid);">
    		</td>
    		<td class= titleImg>
    			 展业机构结果
    		</td>
    	</tr>
    </table>    
    <Input type=hidden name=BranchType >
    <input type=hidden name=AgentGroup value=''>  <!--后台操作的隐式机构编码，不随机构的调整而改变 -->
    <input type=hidden name=UpBranch value=''>  <!--上级机构代码，存储隐式机构代码 -->
  	<Div  id= "divAGroupGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanBranchGroupGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <center>
    	 <INPUT class=cssButton90 VALUE=" 首页 "  TYPE="button" onClick="turnPage.firstPage();">
         <INPUT  class=cssButton91 VALUE="上一页"  TYPE="button" onClick="turnPage.previousPage();">
         <INPUT class=cssButton92 VALUE="下一页"  TYPE="button" onClick="turnPage.nextPage();">
         <INPUT  class=cssButton93 VALUE=" 尾页 "  TYPE="button" onClick="turnPage.lastPage();">
       </center>  	
     </div>
          <INPUT VALUE="返  回" class=cssButton TYPE=button onClick="returnParent();"> 
          <br><br><br><br><br>			
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
