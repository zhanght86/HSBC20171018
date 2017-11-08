<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
String gToday = PubFun.getCurrentDate();
  GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
    
 %>
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="NALAAgentInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NALAAgentInit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./NALAAgentSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent1);">
          <td class=titleImg>
            代理人信息
          </td>
        </td>
      </tr> 
    </table>
    <div id= "divLAAgent1" class="maxbox" style= "display:  ">      
    <table  class= common>
      <TR  class= common> 
        <TD class= title> 
          代理人编码 
        </TD>
        <TD  class= input> 
          <Input class= 'readonly wid' readonly name=AgentCode >
        </TD>        
        <TD  class= title>
          姓名
        </TD>
        <TD  class= input>
          <Input name=Name id="Name" class="common wid" verify="姓名|NotNull&len<=20"
                 onkeyup="value=value.replace(/[^A-Za-z\u4E00-\u9FA5]/g, )"
                 onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^A-Za-z\u4E00-\u9FA5]/g, ))" 
                 maxlength=20> 
                 <font color=red>*</font>
        </TD>
        <TD  class= title>
          性别 
        </TD>
        <TD  class= input>
          <Input name=Sex id="Sex" class="code" verify="性别||NotNull&code:Sex" 
          ondblclick="return showCodeList('Sex',[this]);" 
          onkeyup="return showCodeListKey('Sex',[this]);" > <font color=red>*</font>
        </TD> 
        <!--TD  class= title> 
          密码 
        </TD> 
        <TD  class= input>
          <Input name=Password class=common > 
        </TD--> 
      </TR> 
       <TR  class= common> 
        <!--TD  class= title> 
          推荐报名编号 
        </TD>
        <TD  class= input> 
          <Input name=EntryNo class= common > 
        </TD-->
        <TD  class= title >
          出生日期 
        </TD>
        <TD  class= input >
          <Input name=Birthday class="common wid"  onClick="laydate({elem: '#Birthday'});" verify="出生日期|NotNull&Date" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> <font color=red>*</font>
        </TD>
      
        <TD  class= title >
          民族
        </TD>
        <TD  class= input>
          <Input name=Nationality id="Nationality" class="code" id="Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);" > 
        </TD>
      </TR>
      <tr class=common>
      <td  class=title>
       证件类型
      </td>
      <td class=common>
      <input name=IdType class=code readonly="readonly" VALUE=""
			MAXLENGTH=1 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"
			ondblclick="return showCodeList('IDType', [this]);"
			onkeyup="return showCodeListKey('IDType', [this]);"
			verify="证件类型|code:IDType">
      </td>
      <TD  class= title>
         证件号码 
        </TD>
        <TD  class= input> 
          <Input name=IDNo id="IDNo" class= "common wid" verify="证件号码|notnull&len<=18"
           onchange="return changeIDNo();"> <font color=red>*</font>
        </TD> 
      
      
      
      </tr>
      <TR  class= common> 
        <TD  class= title> 
          籍贯
        </TD>
        <TD  class= input>
          <Input name=NativePlace id="NativePlace"  class="code" verify="籍贯|code:NativePlaceBak" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id="NativePlaceBak" ondblclick="return showCodeList('NativePlaceBak',[this]);" onkeyup="return showCodeListKey('NativePlaceBak',[this]);">
        </TD>   
        <TD  class= title>
          政治面貌 
        </TD>
        <TD  class= input> 
          <Input name=PolityVisage id="PolityVisage" class="code" verify="政治面貌|code:polityvisage" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id="polityvisage" ondblclick="return showCodeList('polityvisage',[this]);" onkeyup="return showCodeListKey('polityvisage',[this]);" > 
        </TD> 
        <TD  class= title>
          户口所在地
        </TD>
        <TD  class= input> 
          <Input name=RgtAddress id="RgtAddress" class="code" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('NativePlaceBak',[this]);" onkeyup="return showCodeListKey('NativePlaceBak',[this]);"> 
        </TD>
      </TR>
      <!--TR  class= common> 
        <TD  class= title>
          来源地 
        </TD>
        <TD  class= input> 
          <Input name=Source class= common > 
        </TD>        
        <TD  class= title> 
          血型
        </TD>
        <TD  class= input> 
          <Input name=BloodType class="code" verify="血型|code:BloodType" id="BloodType" ondblclick="return showCodeList('BloodType',[this]);" onkeyup="return showCodeListKey('BloodType',[this]);"> 
        </TD>        
        <TD  class= title>
          婚姻状况 
        </TD>
        <TD  class= input>
          <Input name=Marriage class="code" verify="婚姻状况|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);" >
        </TD>   
      </TR>
      <tr class=common>           
        <TD  class= title> 
          结婚日期
        </TD>
        <TD  class= input> 
          <Input name=MarriageDate class="coolDatePicker" dateFormat="short" > 
        </TD>     
      </tr-->
      <TR  class= common> 
        <TD  class= title>
          学历 
        </TD>
        <TD  class= input> 
          <Input name=Degree class="code" verify="学历|code:Degree" id="Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);"> 
        </TD>
       <TD  class= title> 
          毕业院校
        </TD>
        <TD  class= input> 
          <Input name=GraduateSchool id="GraduateSchool" class="common wid" > 
        </TD>
        <TD  class= title>
          专业 
        </TD>
        <TD  class= input> 
          <Input name=Speciality id="Speciality" class="common wid" > 
        </TD>
      </TR>
      <TR  class= common>
        <!--TD  class= title> 
          外语水平 
        </TD>
        <TD  class= input> 
          <Input name=ForeignLevel class="code" verify="外语水平|code:EngLevel" id="EngLevel" ondblclick="return showCodeList('EngLevel',[this]);" onkeyup="return showCodeListKey('EngLevel',[this]);"> 
        </TD-->
        <TD  class= title>
          职称 
        </TD>
        <TD  class= input> 
          <Input name=PostTitle id="PostTitle" class='code' style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="职称|code:posttitle" ondblclick="return showCodeList('posttitle',[this]);" onkeyup="return showCodeListKey('posttitle',[this]);" > 
        </TD>
        <!--TD  class= title>
          家庭地址编码
        </TD>
        <TD  class= input>
          <Input name=HomeAddressCode class= common >
        </TD-->
        <TD  class= title>
          家庭地址 
        </TD>
        <TD  class= input> 
          <Input name=HomeAddress  id="HomeAddress" class="common wid" > <font color=red>*</font>
        </TD>
        <!--TD  class= title> 
          通讯地址 
        </TD>
        <TD  class= input> 
          <Input name=PostalAddress class= common > 
        </TD-->
        <TD  class= title>
          邮政编码 
        </TD>
        <TD  class= input> 
          <Input name=ZipCode id="ZipCode" class="common wid" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          电话 
        </TD>
        <TD  class= input> 
          <Input name=Phone id="Phone" class="common wid">
        </TD>
        <TD  class= title> 
          传呼
        </TD>
        <TD  class= input>
          <Input name=BP id="BP" class="common wid">
        </TD>
        <TD  class= title>
          手机 
        </TD>
        <TD  class= input> 
          <Input name=Mobile id="Mobile" class="common wid">
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          E_mail 
        </TD>
        <TD  class= input> 
          <Input name=EMail id="EMail" class="common wid" > 
        </TD>
        <!--TD  class= title>
          是否吸烟标志 
        </TD>
        <TD  class= input> 
          <Input name=SmokeFlag class='code' verify="是否吸烟标志|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);"> 
        </TD-->
        <TD  class= title>
          银行编码
        </TD>
        <TD  class= input> 
          <Input name=BankCode id="BankCode" class='code' verify="银行编码|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);"> 
        </TD>
        <TD  class= title>
          工资存折帐号 
        </TD>
        <TD  class= input> 
          <Input name=BankAccNo id="BankAccNo" class="common wid" > 
        </TD>
      </TR>
      <TR  class= common>
        <!--TD  class= title>
          从业年限 
        </TD>
        <TD  class= input> 
          <Input name=WorkAge id="WorkAge" class="common wid"  > 
        </TD-->
        <TD  class= title>
          原工作单位 
        </TD>
        <TD  class= input> 
          <Input name=OldCom id="OldCom" class="common wid" > 
        </TD>
        <TD  class= title> 
          原职业 
        </TD>
        <TD  class= input> 
          <Input name=OldOccupation id="OldOccupation" class='code' style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="原职业|code:occupation" ondblclick="return showCodeList('occupation',[this]);" onkeyup="return showCodeListKey('occupation',[this]);"> 
        </TD>
        <TD  class= title>
          工作职务 
        </TD>
        <TD  class= input> 
          <Input name=HeadShip id="HeadShip" class="common wid"  > 
        </TD>
      </TR>
      <!--TR  class= common> 
        <TD  class= title>
          推荐代理人 
        </TD>
        <TD  class= input> 
          <Input name=RecommendAgent class="common wid"  > 
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
          <Input name=SaleQuaf class='code' verify="销售资格|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD-->
        <TD  class= title>
          代理人资格证号码 
        </TD>
        <TD  class= input> 
          <Input name=QuafNo id="QuafNo" class="common wid" 
          verify="代理人资格证号码|NOTNULL"
          onkeyup="value=value.replace(/[^a-zA-Z0-9]/g, )"
          onmouseover="value=value.replace(/[^a-zA-Z0-9]/g, )"   
          onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^a-zA-Z0-9]/g, ))"
          maxlength=20
          >
        </TD>
        <TD  class= title>
          证书结束日期 
        </TD>
        <TD  class= input> 
          <Input name=QuafEndDate id="QuafEndDate"  class="coolDatePicker"  verify="证书结束日期|NOTNULL" dateFormat="short" onClick="laydate({elem: '#QuafEndDate'});" >
          <span class="icon"><a onClick="laydate({elem: '#QuafEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		  <input name=currDate type=hidden value='<%=gToday%>' >
        </TD>
        <TD  class= title>
          展业证号码1 
        </TD>
        <TD  class= input> 
          <Input name=DevNo1 id="DevNo1" class="common wid" maxlength=26> 
        </TD>
      </TR>
      <!--TR  class= common>         
        <TD  class= title> 
          证书开始日期 
        </TD>
        <TD  class= input> 
          <Input name=QuafStartDate class="coolDatePicker" dateFormat="short" > 
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
          <Input name=AgentKind class='code' verify="代理人类别|code:AgentKind" ondblclick="return showCodeList('AgentKind',[this]);" onkeyup="return showCodeListKey('AgentKind',[this]);"  > 
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
          <Input name=InsideFlag class='code' verify="内勤标志|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
        <TD  class= title>
          是否专职标志 
        </TD>
        <TD  class= input> 
          <Input name=FullTimeFlag class='code' verify="是否专职标志|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
      </TR-->
      <TR  class= common> 
        <!--TD  class= title>
          是否有待业证标志 
        </TD>
        <TD  class= input> 
          <Input name=NoWorkFlag class='code' verify="是否有待业证标志|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD-->
        <TD  class= title>
          培训期数 
        </TD>
        <TD  class= input> 
          <Input name=TrainPeriods id="TrainPeriods" class="common wid" verify="培训期数|INT" > 
        </TD>
        <TD  class= title>
          入司时间 
        </TD>
        <TD  class= input> 
          <Input name=EmployDate id="EmployDate" class="readonly wid"readonly> 
        </TD>
        <TD  class= title>
          单证保证金 
        </TD>
        <TD  class= input> 
          <Input name=AssuMoney id="AssuMoney" class="readonly wid" readonly="readonly" verify="保证金|notnull&value>"> <font color=red>*</font>
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          备注
        </TD>
        <TD  class=input colSpan= 3> 
          <Input name=Remark id="Remark" class= common3 > 
        </TD>
        <!--TD  class= title>
          转正日期 
        </TD>
        <TD  class= input> 
          <Input name=InDueFormDate class='coolDatePicker' dateFormat='short' > 
        </TD-->
        <!--TD  class= title>
          代理人状态 
        </TD>
        <TD  class= input> 
          <Input name=AgentState class='code' verify="代理人状态|code:agentstate" ondblclick="return showCodeList('agentstate',[this]);" onkeyup="return showCodeListKey('agentstate',[this]);" > 
        </TD-->
        <TD  class= title>
          操作员代码 
        </TD>
        <TD  class= input> 
          <Input name=Operator id="Operator" class= "readonly wid" readonly > 
        </TD>
      </TR>
      <!--TR  class= common> 
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
      <TR class= common> 
        <TD  class= title>
          标志位
        </TD>
        <TD  class= input> 
          <Input name=QualiPassFlag class= common MAXLENGTH=1> 
        </TD>
      </TR-->
    </table>
       <!--代理人状态--> <Input name=AgentState id="AgentState" type=hidden value ='01' > 
       <!--代理人状态--> <Input name=initAgentState id="initAgentState" type=hidden value ='01' > 
    </div>	
    <!--行政信息-->    
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent3);">
            <td class= titleImg>
                行政信息
            </td>
            </td>
    	</tr>
     </table>
     <Div id= "divLAAgent3" class="maxbox" style= "display:  ">
       <table class=common>
        <tr class=common>     
        <TD class= title>
          代理人职级
        </TD>
        <TD class= input>
          <Input name=AgentGrade1 id="AgentGrade1" class="code" readonly verify="代理人职级|notnull&code:AgentGrade1" 
		  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
          ondblclick="return showCodeList('AgentGrade1',[this],[0,1],null,'<%=tSqlAgentGrade+" and code <> #M00# "%>','1');" 
          onkeyup="return showCodeListKey('AgentGrade1',[this],[0,1],null,'<%=tSqlAgentGrade+" and code <> #M00# "%>','1');" 
          > 
          <font color=red>*</font>
        </TD>
        <TD class= title>
          管理机构
        </TD>
        <TD class= input>
          <!--Input name=ManageCom class='code' verify="管理机构|notnull" ondblclick="return showCodeList('station',[this]);" --> 
          <Input name=ManageCom id="ManageCom" class="readonly wid"readonly >
        </TD>
        <TD class= title>
          推荐人 
        </TD>
        <TD class= input>
          <Input class="common wid" id="IntroAgency" name=IntroAgency onchange="return changeIntroAgency();"> 
        </TD>
        
        </tr>
        <tr class=common>
        <TD class= title>
          销售机构 
        </TD>
        <TD class= input>
          <Input class="common wid" name=BranchCode id="BranchCode" verify="销售机构|notnull" 
          onchange="return changeGroup();"> <font color=red>*</font>
        </TD>
        <TD class= title>
          业务主任
        </TD>
        <TD class= input>
          <Input name=GroupManagerName id="GroupManagerName" class="readonly wid" readonly > 
        </TD>
        <TD class= title>
          部经理
        </TD>
        <TD class= input>
          <Input name=DepManagerName id="DepManagerName" class="readonly wid"readonly > 
        </TD>
        </tr>
        <tr class=common>
        <TD class= title>
          区经理
        </TD>
        <TD class= input>
          <Input name=DirManagerName id="DirManagerName" class="readonly wid"readonly > 
        </TD>
        <TD class= title>
          总监
        </TD>
        <TD class= input>
          <Input name=MajordomoName id="MajordomoName" class="readonly wid"readonly > 
        </TD>
        <TD class= title>
          组育成人
        </TD>
        <TD class= input>
          <Input name=RearAgent id="RearAgent" class="common wid" verify="组育成人|len<=10" onchange= "return getInputAgentName(2);"> 
        </TD>
        </tr>
        <tr class=common>
        <TD class= title>
          部育成人
        </TD>
        <TD class= input>
          <Input name=RearDepartAgent id="RearDepartAgent" class="common wid" verify="部育成人|len<=10" onchange= "return getInputAgentName(3);"> 
        </TD>
        <TD class= title>
          区育成人 
        </TD>
        <TD class= input>
          <Input class="common wid" name=RearSuperintAgent id="RearSuperintAgent" verify="区育成人|len<=10" onchange= "return getInputAgentName(4);"> 
        </TD>
        <TD class= title>
          总监育成人 
        </TD>
        <TD class= input>
          <Input class="common wid" name=RearAreaSuperintAgent id="RearAreaSuperintAgent" verify="总监育成人|len<=10" onchange= "return getInputAgentName(5);"> 
        </TD>
        </tr>
       </table>   
         <!--代理人系列--> <Input name=AgentSeries id="AgentSeries" type=hidden> 
    </Div>
    <!--担保人信息（列表） -->
    <table>
    	<tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent2);">
            <td class= titleImg>
                担保人信息
            </td>
            </td>
    	</tr>
     </table>
    <Div  id= "divLAAgent2"  style= "display:  ">
    <table  class= common>
            <tr  class= common>
                    
        <td style="text-align: left" colSpan=1> 
		  <span id="spanWarrantorGrid" >
		  </span> 
        </td>
                    </tr>
            </table>
    </div>
<input type=hidden name=WFlag value='zy'>
    <input type=hidden id="hideOperate"  name=hideOperate value= >
    <input type=hidden id="AgentGrade"  name=AgentGrade value=  >
    <input type=hidden id="initOperate"  name=initOperate value='INSERT'>
    <input type=hidden id="hideIsManager"  name=hideIsManager value='false'>
    <input type=hidden id="hideManageCom"  name=hideManageCom value='<%=tGI.ManageCom%>'>
    <input type=hidden id="BranchType"  name=BranchType value= >
    <input type=hidden id="hideBranchCode"  name=hideBranchCode value= > <!--所属组的隐式代码-->
    <input type=hidden id="UpAgent"  name=UpAgent value= >
    <input type=hidden id="ManagerCode"  name=ManagerCode value= > <!--机构管理人员代码-->
    <input type=hidden id="upBranchAttr"  name=upBranchAttr value= >
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	<%@include file="../common/jsp/OperateAgentButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
  </form>
</body>
</html>

        
