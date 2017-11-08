// 该文件中包含客户端需要处理的函数和事件
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0; // 标识当前选中记录的GRID

// 该文件中包含客户端需要处理的函数和事件
var turnPage0 = new turnPageClass();
var turnPage = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); // 使用翻页功能，必须建立为全局变量

// 重定义获取焦点处理事件
window.onfocus = initFocus;

// 上面的查询按钮
function queryClick()
{
	// alert("This is GEdorTypeRiskDetail.js->queryClick!");
	if(checkGrpPol() == false)
	{
		return;
	}
	var tEdorNo = document.all('EdorNo').value;
	if(tEdorNo == "")
	{
		alert("保全号为空！");
		return;
	}
	var tEdorType = document.all('EdorType').value;
	if(tEdorType == "")
	{
		alert("保全类型为空！");
		return;
	}
	var tGrpContNo = document.all('GrpContNo').value;
	if(tGrpContNo == "")
	{
		alert("团体保单号为空！");
		return;
	}
	var tGrpPolNo = document.all('GrpPolNo').value;
	if(tGrpPolNo == "")
	{
		alert("团体险种保单号为空！");
		return;
	}
	tRiskCode = document.all('RiskCode').value;
	if(tRiskCode == "")
	{
		return;
	}
//	var tStrSql = "select ContNo, InsuredNo, InsuredName, InsuredSex, InsuredBirthday, "
//			+ "(select IDType from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), "
//			+ "(select IDNo from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), PolNo "
//			+ "from LCPol a where " 
//				+" not exists (select * from LPEdorItem where EdorNo = '"+ tEdorNo+ "' and EdorType = '"+ tEdorType+ "' and PolNo = a.PolNo) "
//				+ " and not exists (select * from LPPol where EdorNo = '"+ tEdorNo+ "' and EdorType = '"+ tEdorType+ "' and GrpPolNo = '"+ tGrpPolNo+ "' and PolNo = a.PolNo)"
//				+" and not exists (select * from LPPol where EdorType = '"+ tEdorType+ "' and EdorNo = '"+ tEdorNo+ "' and GrpPolNo = '"+ tGrpPolNo+ "' and ContNo = a.ContNo) "
//				+ " and not exists (select 1 from LPEdorItem where edortype in ('GT','XT','ZT','AT','AX','WT','AZ') and ContNo = a.ContNo and edorstate = '0')"
//		   + " and GrpContNo = '"+ tGrpContNo+ "' and RiskCode = '"+ tRiskCode+ "' and GrpPolNo = '"+ tGrpPolNo+ "' "
//		   + getWherePart('ContNo', 'ContNo2')
//		   + getWherePart('InsuredNo', 'CustomerNo2')
//		   + getWherePart('InsuredName', 'Name2', 'like', '0');
	var tStrSql = "";
	var sqlid1="GEdorTypeRiskDetailSql4";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdorNo);//指定传入的参数
	mySql1.addSubPara(tEdorType);//指定传入的参数
	mySql1.addSubPara(tEdorNo);//指定传入的参数
	mySql1.addSubPara(tEdorType);//指定传入的参数
	mySql1.addSubPara(tGrpPolNo);//指定传入的参数
	mySql1.addSubPara(tEdorType);//指定传入的参数
	mySql1.addSubPara(tEdorNo);//指定传入的参数
	mySql1.addSubPara(tGrpPolNo);//指定传入的参数
	mySql1.addSubPara(tGrpContNo);//指定传入的参数
	mySql1.addSubPara(tRiskCode);//指定传入的参数
	mySql1.addSubPara(tGrpPolNo);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("ContNo2")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("CustomerNo2")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("Name2")[0].value);//指定传入的参数
	tStrSql=mySql1.getString();	
	turnPage.queryModal(tStrSql, LCInsuredGrid);
//	mySql = new SqlClass();
//	mySql.setResourceName("bq.GEdorTypeRiskDetail");
//	mySql.setSqlId("GEdorTypeRiskDetailSql3");
//	mySql.addSubPara(tEdorNo);
//	mySql.addSubPara(tEdorType);
//	mySql.addSubPara(tGrpPolNo);
//	mySql.addSubPara(tGrpContNo);
//	mySql.addSubPara(tRiskCode);
//	mySql.addSubPara(fm.ContNo2.value);
//	mySql.addSubPara(fm.CustomerNo2.value);
//	mySql.addSubPara(fm.Name2.value);
//	turnPage.queryModal(mySql.getString(), LCInsuredGrid);
	if(selGridFlag == 1)
	{
		selGridFlag = 0;
	}
}

// ==================
function queryClick0()
{

	var tGrpContNo = document.all('GrpContNo').value;
	if(tGrpContNo == "")
	{
		alert("传入团体保单号为空！");
		return false;
	}
	mySql = new SqlClass();
	mySql.setResourceName("bq.GEdorTypeRiskDetail");
	mySql.setSqlId("GEdorTypeRiskDetailSql1");
	mySql.addSubPara(tGrpContNo);
	mySql.addSubPara(fm.EdorType.value);
	turnPage0.queryModal(mySql.getString(), LCInsured0Grid);
	if(selGridFlag == 1)
	{
		selGridFlag = 0;
	}
}

function QueryEdorInfo()
{
	mySql = new SqlClass();

	var tEdortype = document.all('EdorType').value
	if(tEdortype != null || tEdortype != '')
	{
		mySql.setResourceName("bq.GEdorTypeRiskDetail");
		mySql.setSqlId("GEdorTypeRiskDetailSql2");
		mySql.addSubPara(tEdortype);

		// var strSQL="select distinct EdorCode, EdorName from LMEdorItem where
		// EdorCode = '" + tEdortype + "'";
	}
	else
	{
		alert('未查询到保全批改项目信息！');
		return;
	}

	var arrSelected = new Array();
	turnPage.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	// try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { };
	try
	{
		document.all('EdorTypeName').value = arrSelected[0][1];
	}
	catch (ex)
	{
	};
}

// 下面的查询按钮
function queryClick2()
{
	// alert("This is GEdorTypeRiskDetail.js->queryClick2");
	if(checkGrpPol() == false)
	{
		return;
	}
	var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//	var strSql = "select ContNo, InsuredNo, InsuredName, InsuredSex, InsuredBirthday, "
//			+ "(select IDType from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), "
//			+ "(select IDNo from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), PolNo"
//			+ " from LCPol a where exists (select * from LPEdorItem where EdorType = '"
//			+ fm.EdorType.value
//			+ "' and EdorNo = '"
//			+ fm.EdorNo.value
//			+ "' and ContNo = a.ContNo and PolNo = a.PolNo) and GrpContNo = '"
//			+ fm.GrpContNo.value
//			+ "' and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "'"
//			+ getWherePart('ContNo', 'ContNo3')
//			+ getWherePart('InsuredNo', 'CustomerNo3')
//			+ getWherePart('InsuredName', 'Name3', 'like', '0');
	
	var strSql = "";
	var sqlid1="GEdorTypeRiskDetailSql5";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
	mySql1.addSubPara(fm.GrpPolNo.value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("ContNo3")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("CustomerNo3")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("Name3")[0].value);//指定传入的参数
	strSql=mySql1.getString();	
	// document.write(strSql);
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid);
	if(selGridFlag == 2)
	{
		selGridFlag = 0;
	}
	showInfo.close();
}

// ========================
function checkGrpPol()
{

	// alert("This is checkGrpPol!");
	var tSelNo = LCInsured0Grid.getSelNo() - 1;
	var tRowNo = LCInsured0Grid.mulLineCount;
	if(tRowNo <= 0)
	{
		alert("查询团单险种信息列表失败！");
		return false;
	}
	if(tSelNo < 0)
	{
		alert("请选择需要保全的团单险种保单!");
		return false;
	}
}

// 单击MultiLine的单选按钮时操作
function reportDetail0Click(parm1,parm2)
{
	// alert("This is reportDetail0Click!");
	fm.GrpPolNo.value = LCInsured0Grid.getRowColData(LCInsured0Grid.getSelNo()
					- 1, 1);
	fm.RiskCode.value = LCInsured0Grid.getRowColData(LCInsured0Grid.getSelNo()
					- 1, 2);
	// selGridFlag = 0;

	// queryClick0();
	// queryClick();//查询与险种对应的被保人
	// queryGEdorList();
	// queryClick2();//查询保全过的被保人
}

// 单击MultiLine的单选按钮时操作
function reportDetailClick(parm1,parm2)
{
	// alert("This is reportDetailClick!");
	fm.ContNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo() - 1,
			1);
	fm.PolNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo() - 1,
			8);
	fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()
					- 1, 2);
	queryGEdorList();
	selGridFlag = 1;
}

// 进入个人保全
function GEdorDetail()
{
	// alert("This is GEdorDetail!");
	if(selGridFlag == 0)
	{
		alert("请先选择一个被保人！");
		return;
	}
	var showStr = "正在申请集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(selGridFlag == 1)
	{
		document.all("Transact").value = "INSERT||EDOR";
	}
	fm.submit();
	document.all("Transact").value = "";
}

// 打开个人保全的明细界面
function openGEdorDetail()
{
	// alert("This is openGEdorDetail!");
	fm.CustomerNoBak.value = fm.CustomerNo.value;
	fm.ContNoBak.value = fm.ContNo.value;
	window.open("./GEdorType" + document.all('EdorType').value + ".jsp");
	showInfo.close();

}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,content,Result)
{
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		GEdorFlag = true;
		if(fm.Transact.value == "I&EDORITEM")
		{
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+ content;
			//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
		else
		{
			openGEdorDetail();
		}
	}
	initLCInsuredGrid();
	initLCInsured2Grid();
}

// 处理获取焦点事件
function initFocus()
{
	// alert("This is GEdorTypeRiskDetail.js->initFocus");
	// if (GEdorFlag)
	// {
	// GEdorFlag = false;
	// var tReturn = queryClick0();
	// if (tReturn == false){
	// return;
	// }
	// //queryClick();
	// //queryGEdorList();
	// selGridFlag = 0;
	// }
}

// 查询出申请后的个人保全列表 已经作过保全项目的
function queryGEdorList()
{
//	var strSql = "select ContNo, InsuredNo, InsuredName, InsuredSex, InsuredBirthday, "
//			+ "(select IDType from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo),"
//			+ "(select IDNo from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), PolNo"
//			+ " from LCPol a where GrpContNo = '"
//			+ fm.GrpContNo.value
//			+ "'"
//			+ " and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "'"
//			+ " and exists (select PolNo from LPPol where EdorNo = '"
//			+ fm.EdorNo.value
//			+ "' and EdorType = '"
//			+ fm.EdorType.value
//			+ "' and PolNo = a.PolNo and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "') and RiskCode = '"
//			+ fm.RiskCode.value
//			+ "'"
//			+ " and exists (select * from LPPol where EdorType = '"
//			+ fm.EdorType.value
//			+ "' and EdorNo = '"
//			+ fm.EdorNo.value
//			+ "' and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "' and ContNo = a.ContNo)";
	// alert(strSql);
	var strSql = "";
	var sqlid1="GEdorTypeRiskDetailSql6";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
	mySql1.addSubPara(fm.GrpPolNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	mySql1.addSubPara(fm.GrpPolNo.value);//指定传入的参数
	mySql1.addSubPara(fm.RiskCode.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.GrpPolNo.value);//指定传入的参数
	strSql=mySql1.getString();	
	
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid);
	if(selGridFlag == 2)
	{
		selGridFlag = 0;
	}
}

// 单击MultiLine的单选按钮时操作
function reportDetail2Click(parm1,parm2)
{
	fm.ContNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()
					- 1, 1);
	fm.PolNo.value = LCInsured2Grid.getRowColData(
			LCInsured2Grid.getSelNo() - 1, 8);
	fm.CustomerNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid
					.getSelNo()
					- 1, 2);
	fm.InsuredNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()
					- 1, 2);
	selGridFlag = 2;
	// queryClick();
}

// 撤销集体下个人保全
function cancelGEdor()
{
	// alert("This is cancelGEdor!");
	if(LCInsured2Grid.getSelNo() < 1)
	{
		alert("请先选择一个被保人！");
		return;
	}
	document.all("Transact").value = "I&EDORITEM"
	var showStr = "正在撤销集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//	var strSql = "select MakeDate, MakeTime, EdorState from LPEdorItem where EdorNo='"
//			+ fm.EdorNo.value + "' and EdorType = '" + fm.EdorType.value + "'";
	var strSql = "";
	var sqlid1="GEdorTypeRiskDetailSql7";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	strSql=mySql1.getString();	
	
	var arrResult = easyExecSql(strSql); // 执行SQL语句进行查询，返回二维数组
	if(arrResult != null)
	{
		var tMakeDate = arrResult[0][0];
		var tMakeTime = arrResult[0][1];
		var tEdorState = arrResult[0][2];
		var tInusredNo = fm.CustomerNo.value;
		var tContNo = fm.ContNo.value;
		var tPolNo = fm.PolNo.value;
		var tEdorType = fm.EdorType.value;
		var tEdorAcceptNo = fm.EdorAcceptNo.value;
		var tEdorNo = fm.EdorNo.value;
		// alert(tEdorNo);
		// return;
		fm.action = "./PEdorAppCancelSubmit.jsp?EdorAcceptNo=" + tEdorAcceptNo
				+ "&ContNo=" + tContNo + "&EdorType=" + tEdorType
				+ "&DelFlag=EdorItem&InsuredNo=" + tInusredNo + "&MakeDate="
				+ tMakeDate + "&MakeTime=" + tMakeTime + "&EdorItemState="
				+ tEdorState + "&PolNo=" + tPolNo + "&EdorNo=" + tEdorNo;
		fm.submit();
		fm.action = "./GEdorTypeRiskDetailSubmit.jsp";
	}
}

/*******************************************************************************
 * 显示frmSubmit框架，用来调试 参数 ： 无 返回值： 无
 * ********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if(cDebug == "1")
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}

// ======================
function returnParent()
{
	top.opener.getEdorItemGrid();
	top.close();
}

// =====================
function saveGEdor()
{

	if(LCInsured2Grid.mulLineCount == 0)
	{
		alert("必须修改后才能保存申请！");
		return;
	}
	top.opener.updateClick();
	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=保存成功";
	//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}
