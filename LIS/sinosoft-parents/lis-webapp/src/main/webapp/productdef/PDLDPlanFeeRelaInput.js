//程序名称：PDLDPlanFeeRelaInput.js
//程序功能: 累加器配置
//创建日期：2016-5-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var Mulline1GridturnPage = new turnPageClass();
// 定义sql配置文件
var tResourceName = "productdef.PDLDPlanFeeRelaInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function returnParent() {
	top.opener.focus();
	top.close();
}


/* 查詢累加器配置信息 */
function queryFeeInfo() {
	// 險種編碼
	var getdutycode=document.all("GetDutyCode").value;
	if (getdutycode == null || getdutycode == "") {
		alter("请输入给付责任编码!");
		return;
	} else {
		var mySql_1 = new SqlClass();
		var sqlid = "PDLDPlanFeeRelaInputSql1"; // 指定sqlID
		mySql_1.setResourceName(tResourceName); // 指定使用的properties文件名
		mySql_1.setSqlId(sqlid);// 指定使用的Sql的id
		// mySql.addSubPara(triskcode);//指定传入的参数
		mySql_1.addSubPara(getdutycode);// 指定传入的参数
		var sql1 = mySql_1.getString();
		// 查詢查該險種下所有的累加器信息
		Mulline1GridturnPage.pageLineNum = 10;
		Mulline1GridturnPage.queryModal(sql1, Mulline1Grid);

		if (Mulline1Grid.mulLineCount <= 0) {
			initMulline1Grid();
			alert("没有查询到账单费用信息!");
			return false;
		}
	}
}
/*选中一条账单信息进行显示*/
function showFeeCodeInfo(){
	var selNo = Mulline1Grid.getSelNo();
	if(selNo == 0)
	{
		alert("请选中一条记录。");
		return;
	} else {
	try{
		// 保障计划代码 PlanCode
		var plancode=Mulline1Grid.getRowColData(selNo-1,1);
		document.all("PlanCode").value=plancode;
	}catch(ex){}
	try{
		// 保障计划代码 PlanCodename
		var plancodename=Mulline1Grid.getRowColData(selNo-1,2);
		if(plancodename=="" && Mulline1Grid.getRowColData(selNo-1,1)=="A"){
			document.all("PlanCodeName").value="其他";
		}else{
			document.all("PlanCodeName").value=plancodename;
		}
	}catch(ex){}
	try{
		// riskcode1
		var RiskCode1=Mulline1Grid.getRowColData(selNo-1,3);
		document.all("RiskCode1").value=RiskCode1;
	}catch(ex){}
	try{
		// riskcode1
		var DutyCode=Mulline1Grid.getRowColData(selNo-1,4);
		document.all("DutyCode").value=DutyCode;
	}catch(ex){}
	try{
		// getdutycode1
		var GetDutyCode1=Mulline1Grid.getRowColData(selNo-1,5);
		document.all("GetDutyCode1").value=GetDutyCode1;
	}catch(ex){}
	try{
		// 账单费用项代码 FeeCode
		var FeeCode=Mulline1Grid.getRowColData(selNo-1,6);
		document.all("FeeCode").value=FeeCode;
	}catch(ex){}
	try{
		// 账单费用项代码 FeeCodename
		var FeeCodename=Mulline1Grid.getRowColData(selNo-1,7);
		if(FeeCodename=="" && Mulline1Grid.getRowColData(selNo-1,6)=='0000'){
			document.all("FeeCodeName").value='其他';
		}else{
			document.all("FeeCodeName").value=FeeCodename;
		}
	}catch(ex){}
	try{
		// 费用类型 FeeType
		var FeeType=Mulline1Grid.getRowColData(selNo-1,8);
		document.all("FeeType").value=FeeType;
	}catch(ex){}
	try{
		// 费用类型 FeeTypeName
		var FeeTypeName=Mulline1Grid.getRowColData(selNo-1,9);
		document.all("FeeTypeName").value=FeeTypeName;
	}catch(ex){}
	try{
		// 是否扣除自付比例  standflag1
		var ifselfPayPercent=Mulline1Grid.getRowColData(selNo-1,10);
		document.all("ifselfPayPercent").value=ifselfPayPercent;
	}catch(ex){}
	try{
		// 是否扣除自付比例  standflag1Name
		var ifselfPayPercentName=Mulline1Grid.getRowColData(selNo-1,11);
		document.all("ifselfPayPercentName").value=ifselfPayPercentName;
	}catch(ex){}
	try{
		// 自付比例 standflag2
		var selfPayPercent=Mulline1Grid.getRowColData(selNo-1,12);
		document.all("selfPayPercent").value=selfPayPercent;
	}catch(ex){}
	try{
		// 免赔额 preauthflag
		var IfPayMoney=Mulline1Grid.getRowColData(selNo-1,13);
		document.all("IfPayMoney").value=IfPayMoney;
	}catch(ex){}
	try{
		// 保单年度金额上限
		var tMoneyLimitAnnual=Mulline1Grid.getRowColData(selNo-1,14);
		document.all("MoneyLimitAnnual").value=tMoneyLimitAnnual;
	}catch(ex){}
	try{
		// 保单年度次数上限
		var tCountLimitAnnual=Mulline1Grid.getRowColData(selNo-1,15);
		document.all("CountLimitAnnual").value=tCountLimitAnnual;
	}catch(ex){}
	try{
		// 保单年度天数上限
		var tDaysLimitAnnual=Mulline1Grid.getRowColData(selNo-1,16);
		document.all("DaysLimitAnnual").value=tDaysLimitAnnual;
	}catch(ex){}
	try{
		// 每次赔偿金额上限
		var tMoneyLimitEveryTime=Mulline1Grid.getRowColData(selNo-1,17);
		document.all("MoneyLimitEveryTime").value=tMoneyLimitEveryTime;
	}catch(ex){}
	try{
		// 每次赔偿天数上限
		var tDaysLimitEveryTime=Mulline1Grid.getRowColData(selNo-1,18);
		document.all("DaysLimitEveryTime").value=tDaysLimitEveryTime;
	}catch(ex){}
	try{
		// 每天赔付金额上限
		var tMoneyLimitEveryDay=Mulline1Grid.getRowColData(selNo-1,19);
		document.all("MoneyLimitEveryDay").value=tMoneyLimitEveryDay;
	}catch(ex){}
	try{
		// 每天赔偿固定金额
		var tPayMoneyEveryDay=Mulline1Grid.getRowColData(selNo-1,20);
		document.all("PayMoneyEveryDay").value=tPayMoneyEveryDay;
	}catch(ex){}
	try{
		// 等待期
		var tObsPeriod=Mulline1Grid.getRowColData(selNo-1,21);
		document.all("ObsPeriod").value=tObsPeriod;
	}catch(ex){}
	try{
		// 等待期单位
		var tObsPeriodUn=Mulline1Grid.getRowColData(selNo-1,22);
		document.all("ObsPeriodUn").value=tObsPeriodUn;
	}catch(ex){}
	try{
		// 等待期单位 name
		var tObsPeriodUnName=Mulline1Grid.getRowColData(selNo-1,23);
		document.all("ObsPeriodUnName").value=tObsPeriodUnName;
	}catch(ex){}
 }
}

// 保存
function save() {
	// 校验是否选中一条数据
	 var selNo = Mulline1Grid.getSelNo();
		if(selNo != 0)
		{
			alert("请执行其他操作。");
			return;
		}
	// 提交方式为INSERT；
	document.all("operator").value="INSERT";
	submitForm();
}
// 选中一条数据修改
function update(){
	// 校验是否选中一条数据
	 var selNo = Mulline1Grid.getSelNo();
		if(selNo == 0)
		{
			alert("请选中一条记录再修改");
			return;
		}
		// 提交方式为UPDATE；
		document.all("operator").value="UPDATE";
		submitForm();	
	
}
// 选中一条数据删除
function del(){
	// 校验是否选中一条数据
	 var selNo = Mulline1Grid.getSelNo();
		if(selNo == 0)
		{
			alert("请选中一条记录再删除");
			return;
		}
		// 提交方式为DELETEETE；
		document.all("operator").value="DELETE";
		submitForm1();	
}
/*数据提交*/
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
  
  document.getElementById("fm1").submit();
}

function submitForm1()
{
if(document.all("IsReadOnly").value == "1")
  {
  	alert("您无权执行此操作");
  	return;
  }
  
// if(!checkNullable())
// {
// return false;
// }
  lockPage("正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
  
  document.getElementById("fm1").submit();
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
// 数据提交后动作处理
function afterSubmit( FlagStr, content )
{
unLockPage();
if (FlagStr == "Fail" )
{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
// showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
// [start] add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
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
// showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
// [start] add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
var name='提示';   // 网页名称，可为空;
var iWidth=550;      // 弹出窗口的宽度;
var iHeight=350;     // 弹出窗口的高度;
var iTop = (window.screen.availHeight - iHeight) / 2; // 获得窗口的垂直位置
var iLeft = (window.screen.availWidth - iWidth) / 2;  // 获得窗口的水平位置
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();


initForm1();// 初始化页面

}
}

// 页面初始化
function initBox(){
	document.getElementById('MoneyLimitAnnual').value='';
	document.getElementById('CountLimitAnnual').value='';
	document.getElementById('DaysLimitAnnual').value='';
	document.getElementById('MoneyLimitEveryTime').value='';
	document.getElementById('DaysLimitEveryTime').value='';
	document.getElementById('MoneyLimitEveryTime').value='';
	document.getElementById('DaysLimitEveryTime').value='';
	document.getElementById('MoneyLimitEveryDay').value='';
	document.getElementById('PayMoneyEveryDay').value='';
	document.getElementById('ObsPeriod').value='';
	document.getElementById("selfPayPercent").value='';
	document.getElementById("IfPayMoney").value='';
	
	$("input.codeno").val("");
	$("input.codename").val("");
	$("input.coolDatePicker").val("");
}

//增量导入
function clickUpload(){
    if(document.all("FileName").value == ""){
        alert("文件不能为空");
        return;
    }
    var i = 0;
    var ImportFile = document.all("FileName").value.toLowerCase();
    ImportPath = "/lis/Ldsysvar/";
    //fm1.all('conf').disable = true;
	//fm1.Transact.value = "INSERT";
    lockPage("正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
    fm2.action = "./PDLDPlanFeeRelaInputULSave.jsp?ImportPath=" + ImportPath
            + "&ImportFile=" + ImportFile + "&transact=INSERT";
//	alert(fm1.CampaignCode.value);
    document.getElementById("fm2").submit();
}
