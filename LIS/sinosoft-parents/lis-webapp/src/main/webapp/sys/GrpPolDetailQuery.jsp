<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2003-1-22 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPrtNo = "";
	String tGrpContNo="";
	String tFlag="";
	try
	{
		tPrtNo = request.getParameter("PrtNo");
		tGrpContNo = request.getParameter("GrpContNo");
		tFlag=request.getParameter("flag");
		if("1".equals(tFlag)){
			loggerDebug("GrpPolDetailQuery","团险卡单综合查询！");
		}
		//loggerDebug("GrpPolDetailQuery","########tGrpContNo="+tGrpContNo);
	}
	catch( Exception e )
	{
		tPrtNo = "";
		tGrpContNo="";
	}	
%>
<head >
<script> 
	var tPrtNo = "<%=tPrtNo%>";  
	var tGrpContNo = "<%=tGrpContNo%>";  
    var flag ="<%=tFlag%>";

</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<!--<SCRIPT src="PolQuery.js"></SCRIPT>-->
	<SCRIPT src="GrpPolDetailQuery.js"></SCRIPT>
	<!--<SCRIPT src="AllProposalQuery.js"></SCRIPT>-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="GrpPolDetailQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>保单查询 </title>
</head>
 
<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm >  
   <table id="table1">
  		<tr>
  			<td class="common">
  			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
  			</td>
  			<%if("1".equals(tFlag)){ %>
  				<td class="titleImg">单证批次信息
  			<%}else{ %>
  				<td class="titleImg">团体合同信息
  			<%} %>
  			</td>
  		</tr>
  </table>
</DIV>
     
    <Div  id= "divGroupPol1" style= "display:  " class="maxbox">
      <table  class= common>
        <TR  class= common>
	 <%if(!"1".equals(tFlag)){ %>
          <TD  class= title>
            团体合同号码
          </TD>
          <TD  class= input>
            <Input class="wid" class=common8 name=GrpContNo id=GrpContNo >
          </TD>
          <TD  class= title>
            投保单号码
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=PrtNo id=PrtNo elementtype=nacessary verify="印刷号码|notnull&len=14" >
          </TD>
     <%}else{ %>
            <Input type= "hidden" name=GrpContNo >
          <TD  class= title>
           结算批次号
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=PrtNo id=PrtNo elementtype=nacessary verify="印刷号码|notnull&len=14" >
          </TD>
     <%} %>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <!--<Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">-->
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="wid" name=ManageCom id=ManageCom elementtype=nacessary verify="管理机构|code:comcode" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);">
          </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            销售渠道
          </TD> 
          <TD  class= input>
            <!--<Input class="readonly" readonly name=SaleChnl verify="销售渠道|code:SaleChnl&notnull" >-->
          	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="wid"  name=SaleChnl id=SaleChnl elementtype=nacessary verify="销售渠道|code:SaleChnl&notnull" onDblClick="return showCodeList('SaleChnl',[this]);" onClick="return showCodeList('SaleChnl',[this]);" onKeyUp="return showCodeListKey('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            代理机构
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="wid" name=AgentCom id=AgentCom onDblClick="return showCodeList('AgentCom',[this],null,null, ManageCom, 'ManageCom');" onClick="return showCodeList('AgentCom',[this],null,null, ManageCom, 'ManageCom');" onKeyUp="return showCodeListKey('AgentCom',[this],null,null, ManageCom, 'ManageCom');">
          </TD>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
      <Input class="wid" NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 CLASS=code8 ondblclick="return queryAgent();" onclick="return queryAgent();"onkeyup="return queryAgent2();" >
         </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AgentGroup id=AgentGroup verify="代理人组别|notnull&len<=12" >
          </TD>          
          <TD  class= title>
            联合代理人代码
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=AgentCode1 id=AgentCode1 >
          </TD>
        </TR>
      </table>
    </Div>
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol2);">
    		</td>
    		<%if("1".equals(tFlag)){ %>
    		<td class= titleImg>
    			 结算单位资料
    		</td>
    		<%}else{ %>
    		<td class= titleImg>
    			 投保单位资料
    		</td>
    		<%} %>
    	</tr>
    </table>
    
    <Div  id= "divGroupPol2" style= "display:  " class="maxbox">
      <table  class= common>
       <TR>
        <%if("1".equals(tFlag)){ %>
          <TD  class= title>
            结算单位名称
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=GrpName id=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60">
          </TD>
            <Input type= "hidden" name=GrpAddress id="GrpAddress" elementtype=nacessary  >
            <Input type= "hidden" name=GrpZipCode id="GrpZipCode"  elementtype=nacessary  >
            <Input type= "hidden" name=GrpNature id="GrpNature"  elementtype=nacessary  >
            <Input type= "hidden" name=BusinessType id="BusinessType"  elementtype=nacessary  >
            <Input type= "hidden" name=Peoples id="Peoples"  elementtype=nacessary  >
            <Input type= "hidden" name=Phone id="Phone"  elementtype=nacessary  >
            <Input type= "hidden" name=Fax id="Fax"  elementtype=nacessary  >
            <Input type= "hidden" name=FoundDate id="FoundDate"  elementtype=nacessary  >
        <%}else{ %>
          <TD  class= title>
            单位名称
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=GrpName id=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60">
          </TD>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=GrpAddress id=GrpAddress elementtype=nacessary  verify="单位地址|notnull&len<=60">
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=GrpZipCode id=GrpZipCode  elementtype=nacessary  verify="邮政编码|notnull&zipcode">
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            单位性质
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=wid name=GrpNature id=GrpNature elementtype=nacessary verify="单位性质|notnull&len<=10" onDblClick="showCodeList('GrpNature',[this]);" onKeyUp="showCodeListKey('GrpNature',[this]);">
          </TD>
      
          <TD  class= title>
            行业类别
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=BusinessType id=BusinessType elementtype=nacessary verify="行业类别|notnull&len<=20">
          </TD>
          <TD  class= title>
            员工总数
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Peoples id=Peoples  elementtype=nacessary verify="单位总人数|notnull&int">
          </TD>       
        </TR>
        <TR  class= common>
          <TD  class= title>
            单位总机
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8  name=Phone id=Phone elementtype=nacessary verify="单位总机|notnull&len<=30">
          </TD>
      
          <TD  class= title>
            单位传真
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Fax id=Fax >
          </TD>
          <TD  class= title>
            成立时间
          </TD>
          <TD  class= input>
            <!--<Input class="coolDatePicker" dateFormat="short" name=FoundDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#FoundDate'});" verify="有效开始日期|DATE" dateFormat="short" name=FoundDate id="FoundDate"><span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>    
          <%} %>   
        </TR>
   
<!--
        <TR  class= common>
          <TD  class= title8>
            交费方式
          </TD>
          <TD  class= input8>
            <Input class="code8" name=GetFlag verify="付款方式|code:PayMode" ondblclick="return showCodeList('PayMode',[this]);" onkeyup="return showCodeListKey('PayMode',[this]);">
          </TD>
          <TD  class= title8>
            开户银行
          </TD>
          <TD  class= input8>
            <Input class=code8 name=BankCode verify="开户银行|len<=24" ondblclick="showCodeList('bank',[this]);" onkeyup="showCodeListKey('bank',[this]);">
          </TD>
          <TD  class= title8>
            帐号
          </TD>
          <TD  class= input8>
            <Input class= common8 name=BankAccNo verify="帐号|len<=40">
          </TD>       
        </TR>
-->
      
      <TR  class= common>
          <TD  class= title>
            投保申请日期 
          </TD>
          <TD  class= input>
            <!--<Input class="coolDatePicker" dateFormat="short" name=PolApplyDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDate'});" verify="有效开始日期|DATE" dateFormat="short" name=PolApplyDate id="PolApplyDate"><span class="icon"><a onClick="laydate({elem: '#PolApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD> 
          <TD  class= title>
            保单生效日期 
          </TD>
          <TD  class= input>
            <!--<Input class="coolDatePicker" dateFormat="short" name=CValiDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#CValiDate'});" verify="有效开始日期|DATE" dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
          <TD  class= title>
            签单日期 
          </TD>
          <TD  class= input>
            <!--<Input class="coolDatePicker" dateFormat="short" name=SignDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#SignDate'});" verify="有效开始日期|DATE" dateFormat="short" name=SignDate id="SignDate"><span class="icon"><a onClick="laydate({elem: '#SignDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
          </TR>
          <TR  class= common>
          <TD  class= title>
            保单打印日期 
          </TD>
          <TD  class= input>
            <!--<Input class="coolDatePicker" dateFormat="short" name=PrintDate>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#PrintDate'});" verify="有效开始日期|DATE" dateFormat="short" name=PrintDate id="PrintDate"><span class="icon"><a onClick="laydate({elem: '#PrintDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD> 
        
          </TR>
          
      </table>
      
    </Div>

<Div  id= "divhiddeninfo" style= "display: none"> 
<table class= common>
<TR  class= common>
          <TD  class= title>
            注册资本金
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=RgtMoney id=RgtMoney verify="注册资本金|num&len<=17">
          </TD>
          <TD  class= title>
            资产总额
          </TD>
          <TD  class= input>
            <Input  class="wid" class= common8 name=Asset id=Asset verify="资产总额|num&len<=17">
          </TD>
          <TD  class= title>
            净资产收益率
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=NetProfitRate id=NetProfitRate verify="净资产收益率|num&len<=17">
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            主营业务
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=MainBussiness id=MainBussiness verify="主营业务|len<=60">
          </TD>
          <TD  class= title>
            单位法人代表
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Corporation id=Corporation verify="单位法人代表|len<=20">
          </TD>
          <TD  class= title>
            机构分布区域
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=ComAera id=ComAera verify="机构分布区域|len<=30">
          </TD>       
        </TR>
                <TR  class= common>
           <TD  class= title>
            部门
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Department1 id=Department1 verify="保险联系人一部门|len<=30">
          </TD>
          <TD  class= title>
            职务
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=HeadShip1 id=HeadShip1 verify="保险联系人一职务|len<=30">
          </TD>                
          <TD  class= title>
            传真
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Fax1 id=Fax1 verify="保险联系人一传真|len<=30">
          </TD>       
        </TR>
                <TR  class= common>
           <TD  class= title>
            部门
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Department1 id=Department1 verify="保险联系人一部门|len<=30">
          </TD>
          <TD  class= title>
            职务
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=HeadShip1 id=HeadShip1 verify="保险联系人一职务|len<=30">
          </TD>                
          <TD  class= title>
            传真
          </TD>
          <TD  class= input>
            <Input class="wid" class= common8 name=Fax2 id=Fax2 verify="保险联系人一传真|len<=30">
          </TD>       
        </TR>
         <TR  class= common>
          <TD  class= title>
            币种
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=wid name=Currency id=Currency verify="币种|len<=2" onDblClick="showCodeList('currency',[this]);" onClick="showCodeList('currency',[this]);" onKeyUp="showCodeListKey('currency',[this]);">
          </TD>
                 
        </TR>     
        <tr class=common>
        <td class= titleImg>
    			
    		</td>
        </TR> 
      </table>
</DIV>   

    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol1);">
    		</td>
    		<%if("1".equals(tFlag)){ %>
    		<td class= titleImg>
    			 险种信息
    		</td>
    		<%}else{ %>
    		<td class= titleImg>
    			 集体保单信息
    		</td>
    		<%} %>
    	</tr>
    </table>
  	<Div  id= "divPol1" style= "display:  " >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table></Div>
    	<table>
              <tr>		
			            <td><INPUT class=cssButton VALUE="交费查询" TYPE=button onClick="FeeQueryClick();"> </td>
			            <!--<td><INPUT class=common VALUE="给付查询" TYPE=button onclick="GetQueryClick();"> </td>-->
						<!--<td><!--INPUT class=common VALUE="批改补退费查询" TYPE=button onclick="EdorQueryClick();"-->
						<!--<INPUT class=cssButton VALUE="保全查询" TYPE=button onclick="PerEdorQueryClick();"> </td>-->
						<td><INPUT class=cssButton VALUE="保单明细查询" TYPE=button onClick="getQueryDetail();"> </td>
						<!--INPUT class=common VALUE="主险查询" TYPE=button onclick="MainRiskQuery();"--> 
						<!--INPUT class=common VALUE="附加险查询" TYPE=button onclick="OddRiskQuery();"--> 
						<td><INPUT class=cssButton VALUE="暂交费查询" TYPE=button onClick="TempFeeQuery();"> </td>
						<!--<td><INPUT class=common VALUE="保费项查询" TYPE=button onclick="PremQuery();"> </td>-->
						<!--<td><INPUT class=common VALUE="垫交/借款查询" TYPE=button onclick="LoLoanQuery();"> </td>-->
						<!--<td><INPUT class=common VALUE="给付项查询" TYPE=button onclick="GetItemQuery();"> </td>-->
						<td><INPUT class=cssButton VALUE="账户查询" style="display: none" TYPE=button onClick="InsuredAccQuery();"> </td>
						<td><INPUT class=cssButton VALUE="理赔查询" TYPE=button onClick="ClaimQuery();"> </td>
						<td><INPUT class=cssButton VALUE="扫描件查询" TYPE=button onClick="ScanQuery();"> </td>	
						<%if("1".equals(tFlag)){ %>
				    <td><INPUT class=cssButton VALUE="单证批次明细" TYPE=button onClick="GrpPolSingleQueryClick();"> </td>      	    
						<%}else{ %>
				    <td><INPUT class=cssButton VALUE="集体下个人保单查询" TYPE=button onClick="GrpPolSingleQueryClick();"> </td>      	    
						<%} %>
				    <td><INPUT class=cssButton VALUE="返  回" TYPE=button onClick="GoBack();"> </td>
  		</table>		
  	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	
	var strSQL = "";
	
	var sqlid826100419="DSHomeContSql826100419";
var mySql826100419=new SqlClass();
mySql826100419.setResourceName("sys.GrpPolDetailQuerySql"); //指定使用的properties文件名
mySql826100419.setSqlId(sqlid826100419); //指定使用的Sql的id
mySql826100419.addSubPara(tPrtNo); //指定传入的参数
strSQL=mySql826100419.getString();
		
//	strSQL = "select grppolno,PrtNo,CustomerNo,GrpName,RiskCode,(select riskname from lmriskapp where riskcode=a.riskcode),ManageCom||'-'||(select shortname from ldcom where comcode = a.managecom),nvl((select sum(prem) from lcpol where appflag in ('1','4') and grppolno =a.grppolno),0),nvl((select sum(amnt) from lcpol where appflag in ('1','4') and grppolno =a.grppolno),0),decode(nvl (a.appflag, 'z'),'z','状态异常','1','正常签单', '4', '终止','0','未签单'),substr(State,1,4) from LCGrpPol a where prtno='" + tPrtNo + "' order by makedate,maketime";			 
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    //alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  // alert(turnPage.arrDataCacheSet[0][7]);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}

 function returnParent()
 {
 	
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();

	
		try{
		  //Modified By QianLy for QC7016 on 2006-10-10--------
		  
		  var sqlid826100522="DSHomeContSql826100522";
var mySql826100522=new SqlClass();
mySql826100522.setResourceName("sys.GrpPolDetailQuerySql"); //指定使用的properties文件名
mySql826100522.setSqlId(sqlid826100522); //指定使用的Sql的id
mySql826100522.addSubPara(tGrpContNo); //指定传入的参数
var sql=mySql826100522.getString();
		  
//		  var sql = "select c.grpcontno,"
//              + " c.prtno,"
//              + " c.managecom,"
//              + " c.salechnl,"
//              + " c.agentcode,"
//              + " c.agentgroup,"
//              + " c.agentcom,"
//              + " d.peoples,"
//              + " d.grpname,"
//              + " d.businesstype,"
//              + " d.grpnature,"
//              + " d.fax,"
//              + " d.phone,"
//              + " d.getflag,"
//              + " d.bankcode,"
//              + " d.bankaccno,"
//              + " d.founddate,"
//              + " c.polapplydate,"
//              + " c.cvalidate,"
//              + " c.signdate"
//              + " from lcgrpcont c, ldgrp d"
//              + " where c.grpcontno = '" + tGrpContNo + "'"
//              + " and c.appntno = d.customerno"
//              + "";
			arrReturn1 =easyExecSql(sql);

			if (arrReturn1 == null) {
			  alert("未查到团体合同信息");
			} else {
			   displayCont(arrReturn1[0]);
			}
			
			var sqlid826100627="DSHomeContSql826100627";
var mySql826100627=new SqlClass();
mySql826100627.setResourceName("sys.GrpPolDetailQuerySql"); //指定使用的properties文件名
mySql826100627.setSqlId(sqlid826100627); //指定使用的Sql的id
mySql826100627.addSubPara(tGrpContNo); //指定传入的参数
var strSQL=mySql826100627.getString();
			
			arrReturn2 =easyExecSql(strSQL);
			//-------------------			
			if (arrReturn1 == null) {
			  alert("未查到投保单位资料");
			} else {
			  displayAppnt(arrReturn2[0]);
			}
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录。");
			
		}
  }
  
  //Modified By QianLy on 2006-10-10--------------- 
  function displayCont(cArr)
  { 
  	try { document.all('GrpContNo').value = cArr[0]; } catch(ex) { };
  	try { document.all('PrtNo').value = cArr[1]; } catch(ex) { };
  	try { document.all('ManageCom').value = cArr[2]; } catch(ex) { };
  	try { document.all('SaleChnl').value = cArr[3]; } catch(ex) { };
  	try { document.all('AgentCode').value = cArr[4]; } catch(ex) { };
  	try { document.all('AgentGroup').value = cArr[5]; } catch(ex) { };
  	try { document.all('AgentCom').value = cArr[6]; } catch(ex) { };
  	try { document.all('Peoples').value = cArr[7]; } catch(ex) { };
  	try { document.all('GrpName').value = cArr[8]; } catch(ex) { };
  	try { document.all('BusinessType').value = cArr[9]; } catch(ex) { };	
  	try { document.all('GrpNature').value = cArr[10]; } catch(ex) { };
  	try { document.all('Fax').value = cArr[11]; } catch(ex) { };	
  	try { document.all('Phone').value = cArr[12]; } catch(ex) { };
  	try { document.all('GetFlag').value = cArr[13]; } catch(ex) { };
  	try { document.all('BankCode').value = cArr[14]; } catch(ex) { };
  	try { document.all('BankAccNo').value = cArr[15]; } catch(ex) { };
  	try { document.all('FoundDate').value = cArr[16]; } catch(ex) { };
  	try { document.all('PolApplyDate').value = cArr[17]; } catch(ex) { };
  	try { document.all('CValiDate').value = cArr[18]; } catch(ex) { };
  	try { document.all('SignDate').value = cArr[19]; } catch(ex) { };

   }
    function displayAppnt(cArr)
   {   	
  	try { document.all('GrpAddress').value = cArr[0]; } catch(ex) { };
  	try { document.all('GrpZipCode').value = cArr[1]; } catch(ex) { };
    }
   //---------------------
   function displayPrint(cArr)
   {
   	
   	var sqlid826100733="DSHomeContSql826100733";
var mySql826100733=new SqlClass();
mySql826100733.setResourceName("sys.GrpPolDetailQuerySql"); //指定使用的properties文件名
mySql826100733.setSqlId(sqlid826100733); //指定使用的Sql的id
mySql826100733.addSubPara(tGrpContNo); //指定传入的参数
var strSQL=mySql826100733.getString();
   	
   var arrReturn3=easyExecSql(strSQL);
				
   try{document.all('PrintDate').value=arrReturn3;}catch(ex){};
   }
</script>
</html>


