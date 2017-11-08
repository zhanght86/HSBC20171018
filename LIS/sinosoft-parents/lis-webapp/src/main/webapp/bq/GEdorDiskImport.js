/*******************************************************************************
* <p>Title: 保全-团单磁盘导入</p>
* <p>Description: 团单磁盘导入js文件</p>
* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: 中科软科技股份有限公司</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : 保全-团单磁盘导入
* @author   : zhangtao
* @version  : 1.00 
* @date     : 2006-11-24
* @modify   : 2006-11-25
******************************************************************************/

var mDebug="1";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass();	//使用翻页功能，必须建立为全局变量
window.onfocus=myonfocus;
var arrDataSet;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm()
{
	var i = 0;
	getImportPath();
	 
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.all('ImportPath').value = ImportPath;
	fm.action = "../bq/GEdorDiskImportSave.jsp?EdorNo=" + fm.EdorNo.value + "&EdorAcceptNo=" + fm.EdorAcceptNo.value + "&EdorType=" + fm.EdorType.value + "&EdorValiDate=" + fm.EdorValiDate.value + "&BQFlag=Y";
	fm.submit(); //提交
}

/*********************************************************************
 *  后台执行完毕反馈信息，显示导入错误日志
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	     
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    getErrorLogAfterSubmit();
}

function getImportPath ()
{
	// 书写SQL语句
	var strSQL = "";
//	strSQL = "select SysvarValue from ldsysvar where sysvar ='ImportPath'";
	var sqlid1="GEdorDiskImportSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorDiskImportSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	strSQL=mySql1.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("未找到上传路径");
		return;
	}
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	ImportPath = turnPage.arrDataCacheSet[0][0];
	
}

/**********************************
 *导入后查看错误日志
 **********************************/
function getErrorLogAfterSubmit(){
	var str=fm.FileName.value;//文件全路径
	//提取保全受理号和批次号
	//var edoracceptno=str.substring(str.indexOf("_",1)+1,str.lastIndexOf("_"));//index本身不算
	var batchno=str.substring(str.lastIndexOf("_")+1,str.indexOf("."));

	try{
//		var querySQL="select GrpContNo,OtherNo,StandbyFlag1,BatchNo,ID,ErrorInfo,Operator,MakeDate,MakeTime "
//		+"from LCGrpImportLog where othernotype='10' "
//		+" and grpcontno = '" + fm.GrpContNo.value + "' "
//		+" and standbyflag1 = '" + fm.EdorType.value + "' "
//		+" and otherno = '" + fm.EdorAcceptNo.value + "' ";
		//+" and batchno = '" + batchno + "'";
		var querySQL="";
		var sqlid1="GEdorDiskImportSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.GEdorDiskImportSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
		querySQL=mySql1.getString();		
		try
		{
			turnPage.queryModal(querySQL,DataGrid);
		}
		catch (ex)
		{
			alert("查询错误日志异常!");
		}
	}
	catch(ex){
		alert("GEdorDiskImportInit.js-->fillDataGrid函数中发生异常:未能获得正确数据!");
	} 
}
/**********************************
 *按条件查询错误日志
 **********************************/
function queryErrorLog(){
    
	var grpcontno=fm.qGrpContNo.value;
	var edoracceptno=fm.qEdorAcceptNo.value;
	var edortype=fm.qEdorType.value;
	var operator=fm.qOperator.value;
	var makedate=fm.errorDate.value;
	var batchno=fm.qBatchNo.value;
	 
	if(grpcontno==""&&edoracceptno==""&&edortype==""&&operator==""&&makedate==""&&batchno==""){
		alert("请输入查询条件！");
		return;
	}
	try{
//		var querySQL="select GrpContNo,OtherNo,StandbyFlag1,BatchNo,ID,ErrorInfo,Operator,MakeDate,MakeTime "
//		+"from LCGrpImportLog where 1=1 and othernotype='10'"
//		+ getWherePart('GrpContNo','qGrpContNo')
//		+ getWherePart('OtherNo','qEdorAcceptNo')
//		+ getWherePart('standbyflag1','qEdorType')		
//		+ getWherePart('batchno','qBatchNo')
//		+ getWherePart('Operator','qOperator')
//		+ getWherePart('MakeDate','errorDate');		 
		//"grpcontno='880000005763' and standbyflag1='IR' and otherno='6120061122000011' and othernotype='10'";
		var querySQL="";
		var sqlid1="GEdorDiskImportSql3";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.GEdorDiskImportSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(grpcontno);//指定传入的参数
		mySql1.addSubPara(edoracceptno);//指定传入的参数
		mySql1.addSubPara(edortype);//指定传入的参数
		mySql1.addSubPara(batchno);//指定传入的参数
		mySql1.addSubPara(operator);//指定传入的参数
		mySql1.addSubPara(makedate);//指定传入的参数
		querySQL=mySql1.getString();		
		try
		{
			turnPage.queryModal(querySQL,DataGrid);
		}
		catch (ex)
		{
			alert("查询错误日志异常!");
		}
	}
	catch(ex){
		alert("GEdorDiskImportInit.js-->queryErrorLog函数中发生异常:未能获得正确数据!");
	} 
}

/**************************************
 *返回
 *************************************/
function returnParent()
{
    try
    {
    	  top.opener.initForm();
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}