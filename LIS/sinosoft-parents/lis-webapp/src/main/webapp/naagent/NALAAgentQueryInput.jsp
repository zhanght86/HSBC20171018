<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tG2 = new GlobalInput();
    tG2 = (GlobalInput)session.getValue("GI");
%>
<script>
	var commonManageCom = "<%=tG2.ManageCom%>";
</script>
<%
//程序名称：LAAgentQueryInput.jsp
//程序功能：
//创建日期：2002-08-16 15:31:08
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
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
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./NALAAgentQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./NALAAgentQueryInit.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

  <title>代理人查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./NALAAgentQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--代理人查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            <td class= titleImg>
                代理人查询条件
            </td>
            </td>
    	</tr>
     </table>
  <Div  id= "divLAAgent" class=maxbox1 style= "display: ''">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> 
          代理人编码 
        </TD>
        <TD  class= input> 
          <Input class="wid common" id=AgentCode  name=AgentCode >
        </TD>
        <TD class= title>
          销售机构 
        </TD>
        <TD class= input>
          <Input class="wid common" name=AgentGroup id=AgentGroup > 
        </TD>
        <TD class= title>
          管理机构
        </TD>
        <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);"> 
        </TD> 
      </TR>
      <TR  class= common> 
        <TD  class= title>
          姓名
        </TD>
        <TD  class= input>
          <Input name=Name id=Name class="wid common" >
        </TD>        
        <TD  class= title>
          性别 
        </TD>
        <TD  class= input>
          <Input name=Sex id=Sex class="code" MAXLENGTH=1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);" >
        </TD>
        <TD  class= title>
          出生日期 
        </TD>
        <TD  class= input>
          <Input name=Birthday id=Birthday class="wid common" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          证件号码 
        </TD>
        <TD  class= input> 
          <Input name=IDNo id=IDNo class="wid common" > 
        </TD>
        <TD  class= title>
          民族
        </TD>
        <TD  class= input>
          <Input name=Nationality id=Nationality class="code" id="Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);" > 
        </TD>
        <TD  class= title> 
          籍贯
        </TD>
        <TD  class= input>
          <Input name=NativePlace id=NativePlace class="code" MAXLENGTH=2 id="NativePlaceBak" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlaceBak',[this]);" onkeyup="return showCodeListKey('NativePlaceBak',[this]);">
        </TD>
      </TR>      
      <TR  class= common>        
        <TD  class= title>
          邮政编码 
        </TD>
        <TD  class= input> 
          <Input name=ZipCode id=ZipCode class="wid common" > 
        </TD>
        <TD  class= title>
          电话 
        </TD>
        <TD  class= input> 
          <Input name=Phone id=Phone class="wid common" > 
        </TD>
        <TD  class= title> 
          传呼
        </TD>
        <TD  class= input>
          <Input name=BP id=BP class="wid common" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          手机 
        </TD>
        <TD  class= input> 
          <Input name=Mobile id=Mobile class="wid common" > 
        </TD>
        <TD  class= title>
          e_mail 
        </TD>
        <TD  class= input> 
          <Input name=EMail id=EMail class="wid common" > 
        </TD>
        <TD  class= title>
          工作职务 
        </TD>
        <TD  class= input> 
          <Input name=HeadShip id=HeadShip class="wid common"  > 
        </TD>
      </TR>
      <!--TR  class= common>
        <TD  class= title>
          是否吸烟标志 
        </TD>
        <TD  class= input> 
          <Input name=SmokeFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);"> 
        </TD>
        <TD  class= title>
          银行编码
        </TD>
        <TD  class= input> 
          <Input name=BankCode class=common > 
        </TD>
        <TD  class= title>
          银行账号 
        </TD>
        <TD  class= input> 
          <Input name=BankAccNo class= common > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          从业年限 
        </TD>
        <TD  class= input> 
          <Input name=WorkAge class= common  > 
        </TD>
        <TD  class= title>
          原工作单位 
        </TD>
        <TD  class= input> 
          <Input name=OldCom class= common > 
        </TD>
        <TD  class= title> 
          原职业 
        </TD>
        <TD  class= input> 
          <Input name=OldOccupation class= common > 
        </TD>
      </TR-->
      <!--TR  class= common> 
        <TD  class= title>
          推荐代理人 
        </TD>
        <TD  class= input> 
          <Input name=RecommendAgent class= common  > 
        </TD>
        <TD  class= title> 
          工种/行业
        </TD>
        <TD  class= input> 
          <Input name=Business class=common > 
        </TD>
      </TR-->
      <TR  class= common> 
        <!--TD  class= title>
          信用等级 
        </TD>
        <TD  class= input> 
          <Input name=CreditGrade class=common > 
        </TD>
        <TD  class= title>
          销售资格 
        </TD>
        <TD  class= input> 
          <Input name=SaleQuaf class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD-->
        <TD  class= title>
          代理人资格证号码 
        </TD>
        <TD  class= input> 
          <Input name=QuafNo id=QuafNo class="wid common" > 
        </TD>
        <TD  class= title>
          展业证号码1 
        </TD>
        <TD  class= input> 
          <Input name=DevNo1 id=DevNo1 class="wid common" > 
        </TD>
        <TD  class= title>
          入司时间 
        </TD>
        <TD  class= input> 
          <Input name=EmployDate id=EmployDate class='coolDatePicker' dateFormat='short' > 
        </TD>
      </TR>
      <!--TR  class= common> 
        <TD  class= title> 
          证书开始日期 
        </TD>
        <TD  class= input> 
          <Input name=QuafStartDate class="coolDatePicker" dateFormat="short" > 
        </TD>
        <TD  class= title>
          证书结束日期 
        </TD>
        <TD  class= input> 
          <Input name=QuafEndDate class="coolDatePicker" dateFormat="short" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title> 
          展业证号码2
        </TD>
        <TD  class= input> 
          <Input name=DevNo2 class= common > 
        </TD>
        <TD  class= title>
          聘用合同号码 
        </TD>
        <TD  class= input> 
          <Input name=RetainContNo class= common > 
        </TD>
        <TD  class= title>
          代理人类别 
        </TD>
        <TD  class= input> 
          <Input name=AgentKind class='code' ondblclick="return showCodeList('AgentKind',[this]);" onkeyup="return showCodeListKey('AgentKind',[this]);"  > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          业务拓展级别
        </TD>
        <TD  class= input> 
          <Input name=DevGrade class=common > 
        </TD>
        <TD  class= title>
          内勤标志 
        </TD>
        <TD  class= input> 
          <Input name=InsideFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
        <TD  class= title>
          是否专职标志 
        </TD>
        <TD  class= input> 
          <Input name=FullTimeFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          是否有待业证标志 
        </TD>
        <TD  class= input> 
          <Input name=NoWorkFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
        <TD  class= title>
          培训日期 
        </TD>
        <TD  class= input> 
          <Input name=TrainDate class='coolDatePicker' dateFormat='short' > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          转正日期 
        </TD>
        <TD  class= input> 
          <Input name=InDueFormDate class='coolDatePicker' dateFormat='short' > 
        </TD>
        <TD  class= title>
          代理人状态 
        </TD>
        <TD  class= input> 
          <Input name=AgentState class='code'  maxlength=2 ondblclick="return showCodeList('AgentState',[this]);" onkeyup="return showCodeListKey('AgentState',[this]);" > 
        </TD>
        <TD  class= title>
          离司日期 
        </TD>
        <TD  class= input> 
          <Input name=OutWorkDate class='coolDatePicker' dateFormat='short' > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          保证金 
        </TD>
        <TD  class= input> 
          <Input name=AssuMoney class= common > 
        </TD>
        <TD  class= title>
          复核员
        </TD>
        <TD  class= input> 
          <Input name=Approver class= common > 
        </TD>
        <TD  class= title>
          复核日期
        </TD>
        <TD  class= input> 
          <Input name=ApproveDate class= common > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          标志位
        </TD>
        <TD  class= input> 
          <Input name=QualiPassFlag class= common MAXLENGTH=1 > 
        </TD>
        <TD  class= title>
          备注
        </TD>
        <TD class=input> 
          <Input name=Remark class= common > 
        </TD>
        <TD  class= title>
          操作员代码 
        </TD>
        <TD  class= input> 
          <Input name=Operator class= common > 
        </TD>
      </TR-->
    </table>
          <input type=hidden name=BranchType value=''>
          <INPUT VALUE="查询" class=cssButton TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="返回" class=cssButton TYPE=button onclick="returnParent();"> 	
    </Div>      
          				
    <table>
    	<tr>
        	<td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 代理人结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" TYPE=button class=cssButton93 onclick="turnPage.lastPage();"> 						
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	  <br /> <br /> <br /> <br /> <br />
  </form>
</body>
</html>
