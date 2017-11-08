//程序名称：PDLCalculatorInput.js
//程序功能: 累加器配置
//创建日期：2016-5-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var Mulline1GridturnPage = new turnPageClass();
var Mulline2GridturnPage=new turnPageClass();
var Mulline3GridturnPage=new turnPageClass();
// 定义sql配置文件
var tResourceName = "productdef.PDLCalculatorInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function returnParent() {
	top.opener.focus();
	top.close();
}
// 查询本给付责任层级的累加器信息
function queryLCalculatorInfo1(){
	
	var riskcode=document.all("RiskCode").value;
	var getdutycode=document.all("GetDutyCode").value;
	
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql2"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara(riskcode);// 指定传入的参数
	mySql_0.addSubPara(getdutycode);// 指定传入的参数
	var sql = mySql_0.getString();
	// 查詢查該險種下所有的累加器信息
	Mulline2GridturnPage.pageLineNum = 5;
	Mulline2GridturnPage.queryModal(sql, Mulline2Grid);
	
	if(Mulline2GridturnPage.mulLineCount<=0){
		return false;
	}
}
// 查询本给付责任层级以上累加器信息
function queryLCalculatorInfo(){
	document.all("FlagStr").value="0";
	var riskcode=document.all("RiskCode").value;
	var getdutycode=document.all("GetDutyCode").value;
	
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql1"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara(riskcode);// 指定传入的参数
	var sql = mySql_0.getString();
	// 查詢查該險種下所有的累加器信息
	Mulline1GridturnPage.pageLineNum = 5;
	Mulline1GridturnPage.queryModal(sql, Mulline1Grid);
	
	if(Mulline1GridturnPage.mulLineCount<=0){
		return false;
	}
}

function showLCalculatorData(){
	/* 为了避免两个Mulline 同时选中 */
	initMulline1Grid();
	queryLCalculatorInfo();
	/* 获取选中额行号 */
	var selNo = Mulline2Grid.getSelNo();
	/* 判断是否选中 */
	if(selNo==0){
		return;
	}
	/* 获取累加器编码 */
	var  calculatorcode="";
	try{
		var  calculatorcode=Mulline2Grid.getRowColData(selNo-1,1);
	}catch(ex){}
	/* 获取明细流水号 */
	var SerialNo=Mulline2Grid.getRowColData(selNo-1,3);
	document.all("SerialNo").value=SerialNo;
	/* 根据累加器编码查询累加器详情信息 */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql3"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara(calculatorcode);// 指定传入的参数
	var sql = mySql_0.getString();
	var result = easyExecSql(sql, 1, 1, 1);
	
	if(result!=null){
		var i=0;
		// 将查询到的结果依次进行赋值
		try{
			// 累加器编码
			var calculatorcode=result[0][i];
			document.all("CalculatorCode").value=calculatorcode;
		}catch(ex){}
		try{
			// 累加器名称
			var calculatorname=result[0][++i];
			document.all("CalculatorName").value=calculatorname;
		}catch(ex){}
		try{
			// 累加器层级
			var ctrlLevel=result[0][++i];
			document.all("CtrlLevel").value=ctrlLevel;
		}catch(ex){}
		try{
			// 累加器层级name
			var ctrlLevelname=result[0][++i];
			document.all("CtrlLevelName").value=ctrlLevelname;
		}catch(ex){}
		try{
			// 累加器类型
			var calculatortype=result[0][++i];
			document.all("CalculatorType").value=calculatortype;
		}catch(ex){}
		try{
			// 累加器类型name
			var calculatortypename=result[0][++i];
			document.all("CalculatorTypeName").value=calculatortypename;
		}catch(ex){}
		try{
			// 累加器要素类型
			var ctrlFactorType=result[0][++i];
			document.all("CtrlFactorType").value=ctrlFactorType;
		}catch(ex){}
		try{
			// 累加器要素类型name
			var ctrlFactorTypename=result[0][++i];
			document.all("CtrlFactorTypeName").value=ctrlFactorTypename;
		}catch(ex){}
		try{
			// 要素值
			var calculatorvalue=result[0][++i];
			document.all("CtrlFactorValue").value=calculatorvalue;
		}catch(ex){}
		try{
			// 要素单位
			var calculatorUnit=result[0][++i];
			document.all("CtrlFactorUnit").value=calculatorUnit;
		}catch(ex){}
		try{
			// 要素单位name
			var calculatorUnitname=result[0][++i];
			document.all("CtrlFactorUnitName").value=calculatorUnitname;
		}catch(ex){}
		try{
			// 要素值计算方式
			var calmode=result[0][++i];
			document.all("CalMode").value=calmode;
		}catch(ex){}
		try{
			// 要素值计算方式name
			var calmodename=result[0][++i];
			document.all("CalModeName").value=calmodename;
		}catch(ex){}
		try{
			// 要素值计算公式
			var calcode=result[0][++i];
			document.all("CalCode").value=calcode;
		}catch(ex){}
		try{
			// 默认值
			var tdefault=result[0][++i];
			document.all("DefaultValue").value=tdefault;
		}catch(ex){}
		try{
			// 生效起期
			var startedate=result[0][++i];
			document.all("StartDate").value=startedate;
		}catch(ex){}
		try{
			// 生效止期
			var enddate=result[0][++i];
			document.all("EndDate").value=enddate;
		}catch(ex){}
		try{
			// 生效时长
			var validperiod=result[0][++i];
			document.all("ValidPeriod").value=validperiod;
		}catch(ex){}
		try{
			// 生效时长单位
			var validperiodunit=result[0][++i];
			document.all("ValidPeriodUnit").value=validperiodunit;
		}catch(ex){}
		try{
			// 生效时长单位name
			var validperiodunitname=result[0][++i];
			document.all("ValidPeriodUnitName").value=validperiodunitname;
		}catch(ex){}
		try{
			// 累加步骤
			var calorder=result[0][++i];
			document.all("CalOrder").value=calorder;
		}catch(ex){}
		try{
			// 生效时长单位
			var calculatorcodeafter=result[0][++i];
			document.all("CalculatorCodeAfter").value=calculatorcodeafter;
		}catch(ex){}
	}
	
	/* 根据累加器编码获取累加器账单信息 */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql4"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara(calculatorcode);// 指定传入的参数
	var sql = mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feecode="";
	var feecodename="";
	if(result!=null){
		 feecode=result[0][0];
		feecodename=result[0][1];
	}
	
	document.all("FeeCode").value=feecode;
	document.all("FeeCodeName").value=feecodename;
	// 根据riskcode,dutycode,getdutycode,plancode,feecode，查询账单信息
	var riskcode=document.all("RiskCode").value;
	var dutycode=Mulline2Grid.getRowColData(selNo-1,5);
	var getdutycode=Mulline2Grid.getRowColData(selNo-1,6);
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql5"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara("A");// 指定传入的参数
	mySql_0.addSubPara(riskcode);// 指定传入的参数
	mySql_0.addSubPara(dutycode);// 指定传入的参数
	mySql_0.addSubPara(getdutycode);// 指定传入的参数
	mySql_0.addSubPara(feecode);// 指定传入的参数
	var sql=mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feetype="";
	var feetypename="";
	var ifselfPayPercent="";
	var ifselfPayPercentname="";
	var selfPayPercent="";
	var IfPayMoney="";
	if(result!=null){
	var feetype=result[0][0];
	var feetypename=result[0][1];
	var ifselfPayPercent=result[0][2];
	var ifselfPayPercentname=result[0][3];
	var selfPayPercent=result[0][4];
	var IfPayMoney=result[0][5];
	}
	
	document.all("ifselfPayPercent").value=ifselfPayPercent;
	document.all("ifselfPayPercentName").value=ifselfPayPercentname;
	document.all("selfPayPercent").value=selfPayPercent;
	document.all("IfPayMoney").value=IfPayMoney;
	document.all("FeeType").value=feetype;
	document.all("FeeTypeName").value=feetypename;
}
function showLCalculatorDataInfo(){
	document.all("FlagStr").value="1";
	/* 为了避免两个Mulline 同时选中 */
	initMulline2Grid();
	queryLCalculatorInfo1();
	/* 获取选中额行号 */
	var selNo = Mulline1Grid.getSelNo();
	/* 判断是否选中 */
	if(selNo==0){
		return;
	}
	/* 获取累加器编码 */
	var  calculatorcode="";
	try{
		var  calculatorcode=Mulline1Grid.getRowColData(selNo-1,1);
	}catch(ex){}
	
	/* 根据累加器编码查询累加器详情信息 */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql3"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara(calculatorcode);// 指定传入的参数
	var sql = mySql_0.getString();
	var result = easyExecSql(sql, 1, 1, 1);
	
	if(result!=null){
		var i=0;
		// 将查询到的结果依次进行赋值
		try{
			// 累加器编码
			var calculatorcode=result[0][i];
			document.all("CalculatorCode").value=calculatorcode;
		}catch(ex){}
		try{
			// 累加器名称
			var calculatorname=result[0][++i];
			document.all("CalculatorName").value=calculatorname;
		}catch(ex){}
		try{
			// 累加器层级
			var ctrlLevel=result[0][++i];
			document.all("CtrlLevel").value=ctrlLevel;
		}catch(ex){}
		try{
			// 累加器层级name
			var ctrlLevelname=result[0][++i];
			document.all("CtrlLevelName").value=ctrlLevelname;
		}catch(ex){}
		try{
			// 累加器类型
			var calculatortype=result[0][++i];
			document.all("CalculatorType").value=calculatortype;
		}catch(ex){}
		try{
			// 累加器类型name
			var calculatortypename=result[0][++i];
			document.all("CalculatorTypeName").value=calculatortypename;
		}catch(ex){}
		try{
			// 累加器要素类型
			var ctrlFactorType=result[0][++i];
			document.all("CtrlFactorType").value=ctrlFactorType;
		}catch(ex){}
		try{
			// 累加器要素类型name
			var ctrlFactorTypename=result[0][++i];
			document.all("CtrlFactorTypeName").value=ctrlFactorTypename;
		}catch(ex){}
		try{
			// 要素值
			var calculatorvalue=result[0][++i];
			document.all("CtrlFactorValue").value=calculatorvalue;
		}catch(ex){}
		try{
			// 要素单位
			var calculatorUnit=result[0][++i];
			document.all("CtrlFactorUnit").value=calculatorUnit;
		}catch(ex){}
		try{
			// 要素单位name
			var calculatorUnitname=result[0][++i];
			document.all("CtrlFactorUnitName").value=calculatorUnitname;
		}catch(ex){}
		try{
			// 要素值计算方式
			var calmode=result[0][++i];
			document.all("CalMode").value=calmode;
		}catch(ex){}
		try{
			// 要素值计算方式name
			var calmodename=result[0][++i];
			document.all("CalModeName").value=calmodename;
		}catch(ex){}
		try{
			// 要素值计算公式
			var calcode=result[0][++i];
			document.all("CalCode").value=calcode;
		}catch(ex){}
		try{
			// 默认值
			var tdefault=result[0][++i];
			document.all("DefaultValue").value=tdefault;
		}catch(ex){}
		try{
			// 生效起期
			var startedate=result[0][++i];
			document.all("StartDate").value=startedate;
		}catch(ex){}
		try{
			// 生效止期
			var enddate=result[0][++i];
			document.all("EndDate").value=enddate;
		}catch(ex){}
		try{
			// 生效时长
			var validperiod=result[0][++i];
			document.all("ValidPeriod").value=validperiod;
		}catch(ex){}
		try{
			// 生效时长单位
			var validperiodunit=result[0][++i];
			document.all("ValidPeriodUnit").value=validperiodunit;
		}catch(ex){}
		try{
			// 生效时长单位name
			var validperiodunitname=result[0][++i];
			document.all("ValidPeriodUnitName").value=validperiodunitname;
		}catch(ex){}
		try{
			// 累加步骤
			var calorder=result[0][++i];
			document.all("CalOrder").value=calorder;
		}catch(ex){}
		try{
			// 生效时长单位
			var calculatorcodeafter=result[0][++i];
			document.all("CalculatorCodeAfter").value=calculatorcodeafter;
		}catch(ex){}
	}
	
	/* 根据累加器编码获取累加器账单信息 */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql4"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara(calculatorcode);// 指定传入的参数
	var sql = mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feecode="";
	var feecodename="";
	if(result!=null){
		 feecode=result[0][0];
		 feecodename=result[0][1];
	}
	document.all("FeeCode").value=feecode;
	document.all("FeeCodeName").value=feecodename;
// // 查詢查該險種下所有的累加器信息
// Mulline3GridturnPage.pageLineNum = 5;
// Mulline3GridturnPage.queryModal(sql, Mulline3Grid);
// if(Mulline3GridturnPage.mulLineCount<=0){
// alert("该累加器并没有账单维度信息。");
// return false;
// }
	// 根据riskcode,dutycode,getdutycode,plancode,feecode，查询账单信息
	var riskcode=document.all("RiskCode").value;
	var dutycode=Mulline1Grid.getRowColData(selNo-1,4);
	var getdutycode=Mulline2Grid.getRowColData(selNo-1,5);
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql5"; // 指定sqlID
	mySql_0.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql_0.setSqlId(sqlid);// 指定使用的Sql的id
	mySql_0.addSubPara("A");// 指定传入的参数
	mySql_0.addSubPara(riskcode);// 指定传入的参数
	mySql_0.addSubPara(dutycode);// 指定传入的参数
	mySql_0.addSubPara(getdutycode);// 指定传入的参数
	mySql_0.addSubPara(feecode);// 指定传入的参数
	var sql = mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feetype="";
	var feetypename="";
	var ifselfPayPercent="";
	var ifselfPayPercentname=""
	var selfPayPercent="";
	var IfPayMoney="";
	if(result!=null){
	 feetype=result[0][0];
	 feetypename=result[0][1];
	 ifselfPayPercent=result[0][2];
	 ifselfPayPercentname=result[0][3];
	 selfPayPercent=result[0][4];
	 IfPayMoney=result[0][5];
	}
	document.all("ifselfPayPercent").value=ifselfPayPercent;
	document.all("ifselfPayPercentName").value=ifselfPayPercentname;
	document.all("selfPayPercent").value=selfPayPercent;
	document.all("IfPayMoney").value=IfPayMoney;
	document.all("FeeType").value=feetype;
	document.all("FeeTypeName").value=feetypename;
	
	
	alert("请勿修改累加器信息,直接点击保存按钮");
}
// 保存
function save() {
	var selNo = Mulline2Grid.getSelNo();
	var selNo1 = Mulline1Grid.getSelNo();
	if(selNo>0){
		alert("请执行修改操作。");
		return;
	}
	// 提交方式为INSERT；
	document.all("operator").value="INSERT";
	if(selNo1>0){
		// 默认为关联信息
		document.getElementById("fm").submit();
	}else{
	submitForm();
	}
}

// 选中一条数据修改
function update(){
	// 校验是否选中一条数据,只能修改本给付责任层级的累加器信息
	 var selNo = Mulline2Grid.getSelNo();
	 var selNo1 = Mulline1Grid.getSelNo();
	/* 当修改的时候，不允许修改层级，如果修改请先删除该层级的累加器 */
	if(!checkctrllevel(Mulline1Grid.getRowColData(selNo1-1,1),document.all("CtrlLevel").value)){
		alert("不允许修改层级。");
		return;
	}
	 if(selNo1>0){
		 alert("只能修改本给付责任层级的累加器信息");
		 return;
	 }
	if(selNo == 0)
	{
		alert("请选中一条本给付责任层级的信息记录再进行修改操作。");
		return;
	}
		
	// 提交方式为UPDATE；
	document.all("operator").value="UPDATE";
	submitForm();	
		
	
}
// 选中一条数据删除
function del(){
	// 校验是否选中一条数据
	 var selNo = Mulline2Grid.getSelNo();
		if(selNo == 0)
		{
			alert("请选中一条本给付责任的记录再删除");
			return;
		}
		// 提交方式为DELETEETE；
		document.all("operator").value="DELETE";
		submitForm();	
}
/* 数据提交 */
function submitForm()
{
if(document.all("IsReadOnly").value == "1")
  {
  	alert("您无权执行此操作");
  	return;
  }
  
	if(!checkNullable())
	{
		return false;
	}
  lockPage("正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
  
  document.getElementById("fm").submit();
}

/* 校验第一次和第二次选中的累加器层级 */
function checkctrllevel(calculatorcode,ctrllevel){
	var sql="select sign(a.ctrllevel-"+ctrllevel+") from Pd_Lcalculatormain a where calculatorcode='"+calculatorcode+"'";
	var sql = sql.toString();
	var result = easyExecSql(sql, 1, 1, 1);
	var result = result[0][0];
	if(result!=0){
		return false;
	}else{
		return true;
	}
}
// 校验是否存在账单信息
function checkFeeCodeInfo(calculatorcode){
	var tcalculatorcode=calculatorcode;
	var mySql = new SqlClass();
	var sqlid = "PDLCalculatorInputSql7"; // 指定sqlID
	mySql.setResourceName(tResourceName); // 指定使用的properties文件名
	mySql.setSqlId(sqlid);// 指定使用的Sql的id
	// mySql.addSubPara(triskcode);//指定传入的参数
	mySql.addSubPara(tcalculatorcode);// 指定传入的参数
	var sql = mySql.getString();
	var result = easyExecSql(sql, 1, 1, 1);
	if(result<=0){
		return false;
	}else{
		return true;
	}
}
/* 算法编码回显 */
function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CalCode').value = tCalCode;
}
// 数据提交后动作处理
function afterSubmit( FlagStr, content )
{
unLockPage();
if (FlagStr == "Fail" )
{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
var name='提示';   // 网页名称，可为空;
var iWidth=550;      // 弹出窗口的宽度;
var iHeight=350;     // 弹出窗口的高度;
var iTop = (window.screen.availHeight - iHeight) / 2; // 获得窗口的垂直位置
var iLeft = (window.screen.availWidth - iWidth) / 2;  // 获得窗口的水平位置
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();
// [end]
}
else
{
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
var name='提示';   // 网页名称，可为空;
var iWidth=550;      // 弹出窗口的宽度;
var iHeight=350;     // 弹出窗口的高度;
var iTop = (window.screen.availHeight - iHeight) / 2; // 获得窗口的垂直位置
var iLeft = (window.screen.availWidth - iWidth) / 2;  // 获得窗口的水平位置
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();
if(document.all("operator").value!="DELETE" && document.all("CalMode").value=="3")
{
	InputCalCodeDefFace('02');
	}
	// else
	{
		initForm(); 
	}
	if(document.all("operator").value=="DELETE" || document.all("CalMode").value!="3")
	{
		// initGetClaimDetail();
	}
}
}
// 数据校验
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }
  
  return true;
}


/* 配置账单信息 */
function button1(){
	var riskcode=document.all("RiskCode").value;
	var tgetdutycode=document.all("GetDutyCode").value;
	 showInfo = window.open("./PDLDPlanFeeRelaMain.jsp?riskcode=" + document.all("RiskCode").value + "&getdutycode="+tgetdutycode);
}

// 增量导入
function clickUpload(){
    if(fm1.all('FileName').value == ""){
        alert("文件不能为空");
        return;
    }
    var i = 0;
    var ImportFile = document.all("FileName").value.toLowerCase();
    ImportPath = "/lis/Ldsysvar/";
    lockPage("正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
    fm1.action = "./PDLCalculatorInputULSave.jsp?ImportPath=" + ImportPath
            + "&ImportFile=" + ImportFile + "&transact=INSERT";
    fm1.submit(); // 提交
}

function checkCalMode(){
	/*如果要素的计算方式为1：表示默认值和累加器的要素值一致*/
	var calmode=document.all("CalMode").value;
	if(""==calmode){
		alert("请选择要素值的计算方式。");
		return;
	}else{
		if("1"==calmode){
			if(""==document.all("CtrlFactorValue").value){
				alert("请输入要素值。");
			}else{
				document.all("DefaultValue").value=document.all("CtrlFactorValue").value;
			}
		}else{
			document.all("DefaultValue").value="";
		}
	}
}
