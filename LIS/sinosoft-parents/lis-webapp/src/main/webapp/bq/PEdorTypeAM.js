var showInfo;
var mDebug = "1";
var mEdorType;
var turnPage = new turnPageClass();
var turnPageCustomerContGrid = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeAMInputSql";

// 查询地址编号
function initInpRole()
{
	var tContNo = document.all('ContNo').value;
	// 判断其是否是投保人
	// var sql = " select 1 from lcappnt where contno = '" + tContNo + "' and
	// appntno ='"+document.all('CustomerNo').value+"'";
	var sql = "";
	var sqlid1 = "PEdorTypeAMInputSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename); // 指定使用的properties文件名
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	mySql1.addSubPara(tContNo);// 指定传入的参数
	mySql1.addSubPara(document.all('CustomerNo').value);
	sql = mySql1.getString();
	var mrr = easyExecSql(sql);
	if(mrr)
	{
		fm.AppntIsInsuredFlag.value = "0";
	}
	var sqlid2 = "PEdorTypeAMInputSql2";
	var mySql2 = new SqlClass();
	mySql2.setResourceName(sqlresourcename); // 指定使用的properties文件名
	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
	mySql2.addSubPara(tContNo);// 指定传入的参数
	mySql2.addSubPara(document.all('CustomerNo').value);
	sql = mySql2.getString();

	mrr = easyExecSql(sql);
	if(mrr)
	{
		fm.AppntIsInsuredFlag.value = "1";
	}
	var tsql = "";
	var sqlid3 = "PEdorTypeAMInputSql3";
	var mySql3 = new SqlClass();
	mySql3.setResourceName(sqlresourcename); // 指定使用的properties文件名
	mySql3.setSqlId(sqlid3);// 指定使用的Sql的id
	mySql3.addSubPara(tContNo);// 指定传入的参数
	mySql3.addSubPara(tContNo);
	tsql = mySql3.getString();

	mrr = easyExecSql(tsql);
	if(mrr)
	{
		fm.AppntIsInsuredFlag.value = "2";
	}
	var sqlid4 = "PEdorTypeAMInputSql4";
	var mySql4 = new SqlClass();
	mySql4.setResourceName(sqlresourcename); // 指定使用的properties文件名
	mySql4.setSqlId(sqlid4);// 指定使用的Sql的id
	mySql4.addSubPara(document.all('CustomerNo').value);// 指定传入的参数
	sql = mySql4.getString();

	mrr = easyExecSql(sql);
	if(mrr)
	{
		fm.AddressNo.value = mrr[0][0];

	}
	else
	{
		return false;
	}

}
function initInpCustomerInfo()
{

	var sStrRole;
	if(fm.AppntIsInsuredFlag.value == '0')
	{
		sStrRole = "lcappnt";
	}
	else
	{
		sStrRole = "lcinsured";
	}
	var tSQL = "";
	var sqlid5 = "PEdorTypeAMInputSql5";
	var mySql5 = new SqlClass();
	mySql5.setResourceName(sqlresourcename); // 指定使用的properties文件名
	mySql5.setSqlId(sqlid5);// 指定使用的Sql的id
	mySql5.addSubPara(sStrRole);// 指定传入的参数
	mySql5.addSubPara(fm.ContNo.value);
	mySql5.addSubPara(document.all('CustomerNo').value);
	tSQL = mySql5.getString();

	CustomerInfo = easyExecSql(tSQL);
	if(CustomerInfo != null)
	{
		DisPlyaCustomerInf(CustomerInfo);
	}

}
function initInpPCustomerInfo()
{

	var CustomerInfo;
	var tSQL;

	var sStrRole, sPStrRole;
	if(fm.AppntIsInsuredFlag.value == '0')
	{
		sStrRole = "lcappnt";
	}
	else
	{
		sStrRole = "lcinsured";
	}

	if(fm.AppntIsInsuredFlag.value == '0')
	{
		sPStrRole = "lpappnt";
	}
	else
	{
		sPStrRole = "lpinsured";
	}
	var sqlid6 = "PEdorTypeAMInputSql6";
	var mySql6 = new SqlClass();
	mySql6.setResourceName(sqlresourcename); // 指定使用的properties文件名
	mySql6.setSqlId(sqlid6);// 指定使用的Sql的id
	mySql6.addSubPara(sPStrRole);// 指定传入的参数
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.EdorNo.value);
	mySql6.addSubPara(document.all('CustomerNo').value);
	mySql6.addSubPara(fm.EdorNo.value);
	tSQL = mySql6.getString();

	CustomerInfo = easyExecSql(tSQL);
	if(CustomerInfo != null)
	{
		DisPlyaPCustomerInf(CustomerInfo);
	}
	else
	{
		var sqlid7 = "PEdorTypeAMInputSql7";
		var mySql7 = new SqlClass();
		mySql7.setResourceName(sqlresourcename); // 指定使用的properties文件名
		mySql7.setSqlId(sqlid7);// 指定使用的Sql的id
		mySql7.addSubPara(sStrRole);// 指定传入的参数
		mySql7.addSubPara(fm.ContNo.value);
		mySql7.addSubPara(document.all('CustomerNo').value);
		tSQL = mySql7.getString();

		CustomerInfo = easyExecSql(tSQL);
		if(CustomerInfo != null)
		{
			DisPlyaPCustomerInf(CustomerInfo);
		}
	}
}

function DisPlyaCustomerInf(CustomerInfo)
{

	CustomerInfo[0][0] == null || CustomerInfo[0][0] == 'null'? '0': fm.HomeAddress.value = CustomerInfo[0][0];
	CustomerInfo[0][1] == null || CustomerInfo[0][1] == 'null'? '0': fm.HomeZipCode.value = CustomerInfo[0][1];
	CustomerInfo[0][2] == null || CustomerInfo[0][2] == 'null'? '0': fm.Mobile.value = CustomerInfo[0][2];
	CustomerInfo[0][3] == null || CustomerInfo[0][3] == 'null'? '0': fm.GrpPhone.value = CustomerInfo[0][3];

	CustomerInfo[0][4] == null || CustomerInfo[0][4] == 'null'? '0': fm.EMail.value = CustomerInfo[0][4];
	CustomerInfo[0][5] == null || CustomerInfo[0][5] == 'null'? '0': fm.GrpName.value = CustomerInfo[0][5];

	CustomerInfo[0][6] == null || CustomerInfo[0][6] == 'null'? '0': fm.PostalAddress.value = CustomerInfo[0][6];
	CustomerInfo[0][7] == null || CustomerInfo[0][7] == 'null'? '0': fm.ZipCode.value = CustomerInfo[0][7];
	CustomerInfo[0][8] == null || CustomerInfo[0][8] == 'null'? '0': fm.AddressNo.value = CustomerInfo[0][8];
}

function DisPlyaPCustomerInf(CustomerInfo)
{
	CustomerInfo[0][0] == null || CustomerInfo[0][0] == 'null'? '0': fm.HomeAddress_New.value = CustomerInfo[0][0];
	CustomerInfo[0][1] == null || CustomerInfo[0][1] == 'null'? '0': fm.HomeZipCode_New.value = CustomerInfo[0][1];
	CustomerInfo[0][2] == null || CustomerInfo[0][2] == 'null'? '0': fm.Mobile_New.value = CustomerInfo[0][2];
	CustomerInfo[0][3] == null || CustomerInfo[0][3] == 'null'? '0': fm.GrpPhone_New.value = CustomerInfo[0][3];

	CustomerInfo[0][4] == null || CustomerInfo[0][4] == 'null'? '0': fm.EMail_New.value = CustomerInfo[0][4];
	CustomerInfo[0][5] == null || CustomerInfo[0][5] == 'null'? '0': fm.GrpName_New.value = CustomerInfo[0][5];

	CustomerInfo[0][6] == null || CustomerInfo[0][6] == 'null'? '0': fm.PostalAddress_New.value = CustomerInfo[0][6];
	CustomerInfo[0][7] == null || CustomerInfo[0][7] == 'null'? '0': fm.ZipCode_New.value = CustomerInfo[0][7];

}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag,MsgContent)
{
	try
	{
		showInfo.close();
	}
	catch (ex)
	{
	}
	DealFlag = DealFlag.toLowerCase();
	var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
	switch(DealFlag)
	{
		case "fail":
			MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
			//showInfo = showModalDialog(MsgPageURL, window,"status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			break;
		case "succ":
		case "success":
			MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
			//showInfo = showModalDialog(MsgPageURL, window,"status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			break;
		default:
			MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
			//showInfo = showModalDialog(MsgPageURL, window,"status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			break;
	}
}

/**
 * 返回主界面
 */
function returnParent()
{
	try
	{
		top.close();
		top.opener.focus();
		top.opener.getEdorItemGrid();
	}
	catch (ex)
	{
	}
}

function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;;
	var ActivityID = '0000000003';
	var OtherNo = top.opener.document.all("OtherNo").value;;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}
	var varSrc = "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID+ "&ActivityID=" + ActivityID + "&PrtNo=" + OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp"+ varSrc, "工作流记事本查看", "left");
}

function edorTypeAMSave()
{

	// 界面校验
	if(!verifyInput2())
	{
		return false;
	}
	if(fm.PostalAddress_New.value == fm.PostalAddress.value
			&& fm.ZipCode_New.value == fm.ZipCode.value
			&& fm.HomeAddress_New.value == fm.HomeAddress.value
			&& fm.HomeZipCode_New.value == fm.HomeZipCode.value
			&& fm.Mobile_New.value == fm.Mobile.value
			&& fm.GrpPhone_New.value == fm.GrpPhone.value
			&& fm.EMail_New.value == fm.EMail.value
			&& fm.GrpName_New.value == fm.GrpName.value)
	{
		alert('客户资料未发生变更！');
		return;
	}
	if(fm.GrpPhone_New.value == "" || fm.GrpPhone_New.value == null)
	{
		if(!confirm("其他联系电话为空，是否继续"))
		{
			return false
		}
	}
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "PEdorTypeAMSubmit.jsp";
	document.getElementById("fm").submit();
}
