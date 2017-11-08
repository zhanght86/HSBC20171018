<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：PremDiscountDefInput.jsp
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>折扣定义管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/laydate/laydate.js"></script>
  <script src="../menumang/treeMenu.js"></script>
   <SCRIPT src="../userMan/UserAdd.js"></SCRIPT>
  
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="PremDiscountDef.js"></SCRIPT>
  <%@include file="PremDiscountDefInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm  id="fm" target="fraSubmit" action="./PremDiscountDefSave.jsp">
   <table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    <td class=titleImg>已保存折扣列表</td>
    </td>
    </table>   
    <!--<table  class= common>
    	<tr class= common>
        <TD  class= title>折扣类型</TD>
        <TD  class= input>
            <Input style="width:60px" class="codeno" name=DiscountTypeQ verify ="折扣类型|notnull" ondblclick="return showCodeList('discounttype',[this,DiscounTypeQName],[0,1]);" onkeyup="return showCodeListKey('discounttype',[this,DiscounTypeQName],[0,1]);"><input style="width:100px" class=codename name=DiscounTypeQName readonly=true>
        </TD> 
        <TD  class= title>折扣号码</TD>
        <TD  class= input>
          <Input style="100px" class=common  name=DiscountCodeQ  >
        </TD>
        
        <TD  class= title>促销活动编码</TD>
        <TD  class= input>
          <Input class=common  name=CampaignCodeQ  >
        </TD>
        
        <TD  class= title>加费折扣标记</TD>
       <TD  class= input>
          <Input style="width:60px" class="codeno" name=AddFeeDiscFlagQ verify ="加费折扣标记|notnull" ondblclick="return showCodeList('yesno',[this,AddFeeDiscFlagQName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,AddFeeDiscFlagQName],[0,1]);"><input style="width:100px" class=codename name=AddFeeDiscFlagQName readonly=true>
        </TD>
    	</tr>
   
    <tr>
    	 <TD  class= title>险种编码</TD>
        <TD  class= input>
            <Input class="codeno" name=RiskCodeQ  ondblclick="return showCodeList('RiskCode',[this,RiskCodeQName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeQName],[0,1]);"><input class=codename name=RiskCodeQName readonly=true>
        </TD> 
        <TD  class= title>责任编码</TD>
        <TD  class= input>
 						<Input style="width:60px" class=codeno name=DutyCodeQ ondblclick="return showCodeList('griskduty',[this,DutyCodeQName],[0,1],null,fm.RiskCodeQ.value,'a.RiskCode',1);" onkeyup="return showCodeListKey('griskduty',[this,DutyCodeQName],[0,1],null,fm.RiskCodeQ.value,'a.RiskCode',1);"><input style="width:100px" class=codename name="DutyCodeQName" readonly=true>
        </TD> 
        <TD  class= title>
          起始日期
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="StartDateQ" >
        </TD>
        <TD  class= title>
          截止日期
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="EndDateQ" >
        </TD>       
    </tr>
    </table>-->
    
    <!--2015/7/27-->
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
    	<tr class= common>
        <TD  class= title5>折扣类型</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center";*+style="background:url(../common/images/select--bg_03.png) no-repeat right center"; "  class="codeno" name=DiscountTypeQ verify ="折扣类型|notnull" onDblClick="return showCodeList('discounttype',[this,DiscounTypeQName],[0,1]);" onMouseDown="return showCodeList('discounttype',[this,DiscounTypeQName],[0,1]);" onKeyUp="return showCodeListKey('discounttype',[this,DiscounTypeQName],[0,1]);"><input class=codename name=DiscounTypeQName readonly=true>
        </TD> 
        <TD  class= title5>折扣号码</TD>
        <TD  class= input5>
          <Input class="wid" class=common name=DiscountCodeQ  >
        </TD></tr>
        <tr class= common>
        <TD  class= title5>促销活动编码</TD>
        <TD  class= input5>
          <Input class="wid" class=common  name=CampaignCodeQ  >
        </TD>
        <TD  class= title5>加费折扣标记</TD>
       <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center; " class="codeno" name=AddFeeDiscFlagQ verify ="加费折扣标记|notnull" onDblClick="return showCodeList('yesno',[this,AddFeeDiscFlagQName],[0,1]);" onMouseDown="return showCodeList('yesno',[this,AddFeeDiscFlagQName],[0,1]);" onKeyUp="return showCodeListKey('yesno',[this,AddFeeDiscFlagQName],[0,1]);" ><input class=codename name=AddFeeDiscFlagQName readonly=true>
					</TD>
        </tr>
        <tr class= common>
        
    	
   
    
    	 <TD  class= title5>险种编码</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center;" class="codeno" name=RiskCodeQ  ondblclick="return showCodeList('RiskCode',[this,RiskCodeQName],[0,1]);" onMouseDown="return showCodeList('RiskCode',[this,RiskCodeQName],[0,1]);" onKeyUp="return showCodeListKey('RiskCode',[this,RiskCodeQName],[0,1]);" ><input class=codename name=RiskCodeQName readonly=true>
					</TD>
        <TD  class= title5>责任编码</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center;" class=codeno name=DutyCodeQ onDblClick="return showCodeList('griskduty',[this,DutyCodeQName],[0,1],null,document.RiskCodeQ.value,'a.RiskCode',1);" onMouseDown="return showCodeList('griskduty',[this,DutyCodeQName],[0,1],null,document.RiskCodeQ.value,'a.RiskCode',1);" onKeyUp="return showCodeListKey('griskduty',[this,DutyCodeQName],[0,1],null,document.RiskCodeQ.value,'a.RiskCode',1);" ><input class=codename name="DutyCodeQName" readonly=true>
					</TD>
        </tr>
        <tr class= common>
        <TD  class= title5>
          起始日期
        </TD>
        <!--<TD  class= input5>
          <input class="coolDatePicker" dateFormat="short" name="StartDateQ" >
        </TD>-->
        <TD class=input5>
			<!--<Input class="coolDatePicker" id="StartDateQ" onClick="laydate({elem: '#StartDateQ'})" dateFormat="short" style="width:190px;" name="StartDateQ" ><span class="icon"><a onClick="laydate({elem: '#StartDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDateQ'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDateQ id="StartDateQ"><span class="icon"><a onClick="laydate({elem: '#StartDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
        <TD  class= title5>
          截止日期
        </TD>
        <TD class=input5>
					<!--<Input class="coolDatePicker"  id="EndDateQ" onClick="laydate({elem: '#EndDateQ'})" dateFormat="short" style="width:190px;" name="EndDateQ" ><span class="icon"><a onClick="laydate({elem: '#EndDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDateQ'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDateQ id="EndDateQ"><span class="icon"><a onClick="laydate({elem: '#EndDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>      
    </tr>
    </table></Div>
    
    
    	
    <!--<INPUT CLASS=cssButton VALUE="查   询" TYPE=button onClick="easyQueryClick()">-->
    <a style="color:#fff" href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a><br><br>
      
       <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanPremDiscountGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            
    </Div>
    
   <table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPay);"></td>
    <td class=titleImg>折扣明细</td>
    </td>
    </tr>
    </table> 
    
<!--       <table class= common >
    	<tr class= common>
        <TD  class= title>折扣类型</TD>
        <TD  class= input>
            <Input style="width:60px" class="codeno" name=DiscountType verify ="折扣类型|notnull" ondblclick="return showCodeList('discounttype',[this,DiscounTypeName],[0,1]);" onkeyup="return showCodeListKey('discounttype',[this,DiscounTypeName],[0,1]);"><input style="width:100px" class=codename name=DiscounTypeName readonly=true>
        </TD> 
        <TD  class= title>折扣号码</TD>
        <TD  class= input>
          <Input class=readonly  readonly  name=DiscountCode  >
        </TD>
        
        <TD  class= title>促销活动编码</TD>
        <TD  class= input>
          <Input class=common  name=CampaignCode  >
        </TD>
        
        <TD  class= title>加费折扣标记</TD>
       <TD  class= input>
          <Input style="width:60px" class="codeno" name=AddFeeDiscFlag verify ="加费折扣标记|notnull" ondblclick="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,AddFeeDiscFlagName],[0,1]);"><input style="width:100px" class=codename name=AddFeeDiscFlagName readonly=true>
        </TD>
    	</tr>
   
    <tr>
    	 <TD  class= title>险种编码</TD>
        <TD  class= input>
            <Input style="width:60px" class="codeno" name=RiskCode  ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input style="width:100px" class=codename name=RiskCodeName readonly=true>
        </TD> 
        <TD  class= title>责任编码</TD>
        <TD  class= input>
						<Input style="width:60px" class=codeno name=DutyCode ondblclick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);" onkeyup="return showCodeListKey('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);"><input style="width:100px" class=codename name="DutyCodeName" readonly=true>
        </TD> 
        <TD  class= title>
          起始日期
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="StartDate" >
        </TD>
        <TD  class= title>
          截止日期
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="EndDate" >
        </TD>
      </tr>
   
    <tr>  
        <TD  class= title>算法编码</TD>
        <TD  class= input>
          <Input class=readonly readonly  name=CalCode  >
        </TD>
        <TD  class= title>
        <INPUT CLASS=cssButton VALUE="定义算法" TYPE=button onclick="defCalRule()">
      </TD>
    </tr>
    </table>--> <!--//2015/7/27-->
    <Div  id= "divPay" style= "display: ''" class="maxbox">
       <table class= common >
    	<tr class= common>
        <TD  class= title5>折扣类型</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=DiscountType verify ="折扣类型|notnull" onDblClick="return showCodeList('discounttype',[this,DiscounTypeName],[0,1]);" onMouseDown="return showCodeList('discounttype',[this,DiscounTypeName],[0,1]);" onKeyUp="return showCodeListKey('discounttype',[this,DiscounTypeName],[0,1]);" ><input class=codename name=DiscounTypeName readonly=true>
					</TD>
        <TD  class= title5>折扣号码</TD>
        <TD  class= input5>
          <Input class="wid" class=readonly  readonly name=DiscountCode  >
        </TD></tr>
        <tr class= common>
        <TD  class= title5>促销活动编码</TD>
        <TD  class= input5>
          <Input class="wid" class=common name=CampaignCode  >
        </TD>
         <TD  class= title5>加费折扣标记</TD>
       <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AddFeeDiscFlag verify ="加费折扣标记|notnull" onDblClick="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onMouseDown="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onKeyUp="return showCodeListKey('yesno',[this,AddFeeDiscFlagName],[0,1]);"><input class=codename name=AddFeeDiscFlagName readonly=true>
					</TD>
        </tr>
        <tr class= common>
       
    	
   
    
    	 <TD  class= title5>险种编码</TD>
        <TD class="input5">
               <input style="background:url(../common/images/select--bg_03.png) no-repeat right center";*+style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=RiskCode  ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onMouseDown="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onKeyUp="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);" ><input class=codename name=RiskCodeName readonly=true>
					</TD>
        <TD  class= title5>责任编码</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center";*+style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DutyCode onDblClick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,document.RiskCode.value,'a.RiskCode',1);" onMouseDown="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,document.RiskCode.value,'a.RiskCode',1);" onKeyUp="return showCodeListKey('griskduty',[this,DutyCodeName],[0,1],null,document.RiskCode.value,'a.RiskCode',1);" ><input class=codename name="DutyCodeName" readonly=true>
					</TD>
        </tr> 
        <tr class= common>
        <TD  class= title5>
          起始日期
        </TD>
        <TD class=input5>
					<!--<Input class="laydate-icon" id="demo2" onClick="laydate({elem: '#demo2'});" dateFormat="short" style="width:190px;" name="StartDate"><span class="icon"><a onClick="laydate({elem: '#demo2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                     <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
        <TD  class= title5>
          截止日期
        </TD>
        <TD class=input5>
					<!--<Input class="laydate-icon"  id="demo3" onClick="laydate({elem: '#demo3'});" dateFormat="short" style="width:190px;" name="EndDate" ><span class="icon"><a onClick="laydate({elem: '#demo3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>  </tr>
      <tr class= common>
   
     
        <TD  class= title5>算法编码</TD>
        <TD  class= input5>
          <Input class="wid" class=readonly readonly  name=CalCode  id=CalCode  >
        
        
      </TD>
    </tr>
    </table>
    </Div>
<!--    <INPUT CLASS=cssButton VALUE="定义算法" TYPE=button onClick="defCalRule()">

  <INPUT CLASS=cssButton VALUE="重    置" TYPE=button onClick="initAll()">
  <INPUT CLASS=cssButton VALUE="保    存" TYPE=button onClick="InsertClick()">-->
  
  <a style="color:#fff" href="javascript:void(0);" class="button" onClick="defCalRule();">定义算法</a>
  <a style="color:#fff" href="javascript:void(0);" class="button" onClick="initAll();">重    置</a>
  <a style="color:#fff" href="javascript:void(0);" class="button" onClick="InsertClick();">保    存</a>
  <!--INPUT CLASS=cssButton VALUE="定义算法" TYPE=button onclick="defCalCode()"-->
  <!--INPUT CLASS=cssButton VALUE="修  改" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>

</body>
</html>

