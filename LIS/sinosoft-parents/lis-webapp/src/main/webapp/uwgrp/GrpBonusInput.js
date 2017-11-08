//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;

var turnPage = new turnPageClass();
window.onfocus=myonfocus;


function save()
{
	//校验数据是否录入完整。
	if(fm.GrpContNo.value=="")
	{
		alert("团单合同号未传入!");
		return false;
	}
		if(fm.AppntNo.value=="")
	{
		alert("客户代码未传入!");
		return false;
	}
//		if(fm.Degree.value=="")
//	{
//		alert("请输入客户标识!");
//		return false;
//	}
		if(fm.RiskCode.value=="")
	{
		alert("请选择险种代码!");
		return false;
	}
		if(fm.InsuYear.value=="")
	{
		alert("请输入保险期间!");
		return false;
	}
     //    alert (fm.RewardValue.value);
//return false;
	if(fm.RewardValue.value>=0.035)
	{
		alert("此处录入的分红利率不包含基础保底2.5％利率,请确认您的输入!");
		//return false;       应张鹏要求,取消这个限制(只提示,不终止)
	}
	
	//add  by sujd
	if(fm.RewardValue.value>=0.10)
	{
		alert("分红利率超过10%,数据过大!");
		return false;      
	}
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="INSERT";
	fm.submit(); //提交
	initForm();
	easyQueryClick();
}
//修改
function Mod()
{
	//校验数据是否录入完整。
	if(fm.GrpContNo.value=="")
	{
		alert("团单合同号未传入!");
		return false;
	}
		if(fm.AppntNo.value=="")
	{
		alert("客户代码未传入!");
		return false;
	}
//		if(fm.Degree.value=="")
//	{
//		alert("请输入客户标识!");
//		return false;
//	}
		if(fm.RiskCode.value=="")
	{
		alert("请选择险种代码!");
		return false;
	}
		if(fm.InsuYear.value=="")
	{
		alert("请输入保险期间!");
		return false;
	}
				if(fm.RewardValue.value>=0.035)
	{
		alert("此处录入的分红比例不包含基础保底3.5％利率,请检查您的输入!");
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="UPDATE";
	fm.submit(); //提交
	initForm();
	easyQueryClick();
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
   
	try {
		if(showInfo!=null)
			showInfo.close();
		}
	catch(e)
  {
	}
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}else{
	  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
  	  
}
}
function showMul()
{
	var tSelNo = BonusGrid.getSelNo()-1;
	fm.GrpContNo.value=BonusGrid.getRowColData(tSelNo,1);	
	fm.AppntNo.value=BonusGrid.getRowColData(tSelNo,2);
//	fm.Degree.value=BonusGrid.getRowColData(tSelNo,3);
	fm.GrpPolNo.value=BonusGrid.getRowColData(tSelNo,4);
	fm.RiskCode.value=BonusGrid.getRowColData(tSelNo,5);
	fm.InsuYear.value=BonusGrid.getRowColData(tSelNo,6);
	fm.InsuYearFlag.value=BonusGrid.getRowColData(tSelNo,7);
	fm.RewardType.value=BonusGrid.getRowColData(tSelNo,8);
	fm.RewardValue.value=BonusGrid.getRowColData(tSelNo,9);
	fm.StartDate.value=BonusGrid.getRowColData(tSelNo,10);
	fm.EndDate.value=BonusGrid.getRowColData(tSelNo,11);
	fm.PayMoney.value=BonusGrid.getRowColData(tSelNo,12);
	fm.Note.value=BonusGrid.getRowColData(tSelNo,13);
	
	fm.GrpPolNo1.value=BonusGrid.getRowColData(tSelNo,4);

}
function easyQueryClick()
{

	// 初始化表格
	initBonusGrid();
	// 书写SQL语句
	
	var strSQL = "";
	var GrpContNo=fm.all('GrpContNo').value;
  var RiskCode=fm.all('QueryRiskCode').value;
	strSQL="select grpcontno,appntno,degree,grppolno,riskcode,insuyear,insuyearflag,rewardtype,rewardvalue,startdate,enddate,paymoney,note from lcgrpbonus where 1=1 and grpcontno='"+GrpContNo+"'"
	+ getWherePart('riskcode','QueryRiskCode');
	//查询SQL，返回结果字符串
   turnPage.queryModal(strSQL,BonusGrid);
  
  //判断是否查询成功
  if (!turnPage.queryModal) {

  	BonusGrid.clearData();
    return false;
  }

}
//个别分红退保
function easyQueryClick2()
{

	// 初始化表格
	initCanclePolGrid();
	// 书写SQL语句
	
	var strSQL = "";
	var GrpContNo=fm.all('GrpContNo').value;
//  var RiskCode2=fm.all('QueryRiskCode2').value;
	strSQL="select grpcontno,appntno,grppolno,riskcode,AccountPassYears,AccountValueRate from lcgrpbonussub where 1=1 and grpcontno='"+GrpContNo+"'"
	+ getWherePart('riskcode','QueryRiskCode2');
	//查询SQL，返回结果字符串
   turnPage.queryModal(strSQL,CanclePolGrid);
  
  //判断是否查询成功
  if (!turnPage.queryModal) 
  {  	
  	BonusGrid.clearData();
    return false;
  }

}

//个别分红退保操作
function canclePol()
{
	//校验数据是否录入完整。
	if(fm.GrpContNo2.value=="")
	{
		alert("团单合同号未传入!");
		return false;
	}
		if(fm.AppntNo2.value=="")
	{
		alert("客户代码未传入!");
		return false;
	}
		if(fm.RiskCode2.value=="")
	{
		alert("请选择险种代码!");
		return false;
	}
		if(fm.AccountPassYear.value=="")
	{
		alert("请输入帐户经过年数!");
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="ADD";
	fm.submit(); //提交
	initForm();
	easyQueryClick2();
}
//个别分红退保修改操作
function canclePolMod()
{
	//校验数据是否录入完整。
	if(fm.GrpContNo2.value=="")
	{
		alert("团单合同号未传入!");
		return false;
	}
		if(fm.AppntNo2.value=="")
	{
		alert("客户代码未传入!");
		return false;
	}
		if(fm.RiskCode2.value=="")
	{
		alert("请选择险种代码!");
		return false;
	}
		if(fm.AccountPassYear.value=="")
	{
		alert("请输入帐户经过年数!");
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="CancleMod";
	fm.submit(); //提交
	initForm();
	easyQueryClick2();
}
//个别分红退保删除 
function canclePolDel()
{
	var tSel = CanclePolGrid.getSelNo();
	if(tSel==0)
	{
		alert("请先选择一条分红退保记录！");
		return;
	}
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="CancleDEL";
	fm.submit(); //提交
  initForm();
  easyQueryClick2();
}

function showMul2()
{
	var tSelNo = CanclePolGrid.getSelNo();
	if(tSelNo!=0)
	{
	fm.GrpContNo2.value=CanclePolGrid.getRowColData(tSelNo-1,1);	
	fm.AppntNo2.value=CanclePolGrid.getRowColData(tSelNo-1,2);
	fm.GrpPolNo2.value=CanclePolGrid.getRowColData(tSelNo-1,3);
	fm.RiskCode2.value=CanclePolGrid.getRowColData(tSelNo-1,4);
	fm.AccountPassYear.value=CanclePolGrid.getRowColData(tSelNo-1,5);
	fm.AccountValueRate.value=CanclePolGrid.getRowColData(tSelNo-1,6);
	
  }
	
}
