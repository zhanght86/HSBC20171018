<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：LABranchGroupInput.jsp
//程序功能：
//创建日期：
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="LABranchGroupInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LABranchGroupInit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./LABranchGroupSave.jsp" method=post name=fm id="fm" target="fraSubmit" >
    <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLABranchGroup1);">
    </IMG>
      <td class=titleImg>
      销售单位
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
            <Input class="common wid" name=BranchAttr id=BranchAttr verify = "代码|notnull" >
          </TD>
          <TD  class= title5>
            名称
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=Name id=Name >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
          <Input class="code" name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" verify="管理机构|code:comcode&NOTNULL" onMouseDown="return showCodeList('comcode',[this],null,null,null,null,1);" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);"> 
          </TD> 
          <TD  class= title5>
            类型
          </TD>
          <TD  class= input5>
            <Input class="readonly wid" readonly name=BranchType id=BranchType >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            级别
          </TD>
          <TD  class= input5>
            <Input class= 'code' name=BranchLevel id="BranchLevel" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" readonly verify="展业机构级别|notnull&code:BranchLevel" 
            onMouseDown="return showCodeList('<%=tSql1%>',[this],[0,1],null,'<%=tSqlBranchLevel%>','1');"
            ondblclick="return showCodeList('<%=tSql1%>',[this],[0,1],null,'<%=tSqlBranchLevel%>','1');" 
            onkeyup="return showCodeListKey('<%=tSql1%>',[this],[0,1],null,'<%=tSqlBranchLevel%>','1');" >
          </TD>
          <TD  class= title5>
            管理人员代码
          </TD>
          <TD  class= input5>
            <Input class="readonly wid"readonly name=BranchManager id=BranchManager >
          </TD>
        </TR>
        <TR  class= common>
          <!--TD  class= title>
            展业机构地址编码
          </TD>
          <TD  class= input>
            <Input class= 'code' name=BranchAddressCode ondblclick="return showCodeList('station',[this]);" 
                                                        onkeyup="return showCodeListKey('station',[this]);">
          </TD-->
          <TD  class= title5>
            地址
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=BranchAddress id=BranchAddress >
          </TD>
          <!--TD  class= input colspan=2>
          </TD-->
          <TD  class= title5>
            管理人员名称
          </TD>
          <TD  class= input5>
            <Input class="readonly wid"readonly name=BranchManagerName id=BranchManagerName >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            电话
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=BranchPhone id=BranchPhone >
          </TD>
          <TD  class= title5>
            传真
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=BranchFax id=BranchFax >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            邮编
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=BranchZipcode id=BranchZipcode verify="展业机构邮编|len<=6" >
          </TD>
          <TD  class= title5>
            成立标志日期
          </TD>
          <TD  class= input5>
            <Input class= 'coolDatePicker' name=FoundDate  verify="成立标志日期|notnull&DATE" format='short' id="FoundDate" onClick="laydate({elem:'#FoundDate'});"><span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            停业日期
          </TD>
          <TD  class= input>
            <Input class='coolDatePicker' name=EndDate format='short' id="EndDate" onClick="laydate({elem:'#EndDate'});"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            停业
          </TD>
          <TD  class= input5>
            <Input class='code' name=EndFlag id="EndFlag" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;"
 verify="停业|notnull&code:yesno" 
            onMouseDown="return showCodeList('yesno',[this]);"
            ondblclick="return showCodeList('yesno',[this]);" 
            onkeyup="return showCodeListKey('yesno',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <!--TD  class= title>
            是否单证交接
          </TD>
           <TD  class= input>
            <Input class= 'code' name=CertifyFlag MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" 
                                                             onkeyup="return showCodeListKey('yesno',[this]);">
          </TD-->
          <TD  class= title5>
            是否有独立的营销职场
          </TD>
          <TD  class= input5>
            <Input class= 'code' name=FieldFlag id="FieldFlag"  style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;"
verify="是否有独立的营销职场|code:yesno" 
onMouseDown="return showCodeList('yesno',[this]);"
                                                ondblclick="return showCodeList('yesno',[this]);" 
                                                onkeyup="return showCodeListKey('yesno',[this]);" >
          </TD>
          <TD  class= title5>
            与上级的隶属关系
          </TD>
          <TD  class= input5>
            <Input class='code' name=UpBranchAttr id="UpBranchAttr" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;"
 verify="与上级的隶属关系|notnull|code:upbranchattr"  onMouseDown="return showCodeList('upbranchattr',[this]);"
                                                  ondblclick="return showCodeList('upbranchattr',[this]);" 
                                                  onkeyup="return showCodeListKey('upbranchattr',[this]);">
          </TD>
          </TR>
          <tr class=common>          
          <TD  class= title5>
            操作员代码
          </TD>
          <TD  class= input5>
            <Input class="readonly wid" readonly name=Operator id=Operator >
          </TD>
          </tr>
          <!--TR  class= common>
          <TD  class= title>
            标志
          </TD>
          <TD  class= input>
            <Input class=common name=State MaxLength=10 >
          </TD>          
          </TR-->
      </table>
    </Div>
    <input type=hidden name=hideOperate value=''>
    <input type=hidden name=AgentGroup value='' >  <!--后台操作的隐式机构编码，不随机构的调整而改变 -->
    <input type=hidden name=UpBranch value='' >  <!--上级机构代码，存储隐式机构代码 -->
    <%@include file="../common/jsp/OperateAgentButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    <Br /><Br /><Br /><Br />
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
