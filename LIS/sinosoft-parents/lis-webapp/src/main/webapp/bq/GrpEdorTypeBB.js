//该文件中包含客户端需要处理的函数和事件
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //标识当前选中记录的GRID

//该文件中包含客户端需要处理的函数和事件
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
//重定义获取焦点处理事件
//window.onfocus = initFocus;

//查询按钮
function queryClick()
{
	var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LCInsured where GrpContNo='" 
		   + fm.GrpContNo.value + "'" 
		   + "and ContNo not in (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "')"
		   + "and ContNo in (select ContNo from LCcont where appflag='1' and GrpContNo='" + fm.GrpContNo.value + "')"
		   + getWherePart('ContNo', 'ContNo2')
		   + getWherePart('InsuredNo', 'CustomerNo2')
		   + getWherePart('Name', 'Name2', 'like', '0');

	turnPage1.queryModal(strSql, LCInsuredGrid);
	if (selGridFlag == 1)
	{
		//fm.ContNo.value = "";
		fm.CustomerNo.value = "";
		selGridFlag = 0;
	}
	//alert('1111111111'+fm.ContNo.value);
	showInfo.close();	 
}

//查询按钮
function queryClick2()
{
	var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from lcinsured where ContNo in "
			   + " (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value 
			   + "' and edorno='" + fm.EdorNo.value + "')"
			   + getWherePart('ContNo', 'ContNo3')
			   + getWherePart('InsuredNo', 'CustomerNo3')
			   + getWherePart('Name', 'Name3', 'like', '0');

	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid);
	if (selGridFlag == 2)
	{
		//fm.ContNo.value = "";
		fm.CustomerNo.value = "";
		selGridFlag = 0;
	}

	showInfo.close();	 
}

//单击MultiLine的单选按钮时操作
function reportDetailClick(parm1, parm2)
{	
	fm.ContNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 2);
	//queryGEdorList();
	selGridFlag = 1;
}

//进入个人保全
function GEdorDetail()
{
	if (selGridFlag == 0)
	{
		alert("请先选择一个被保人！");
		return;
	}
	openGEdorDetail();
	//var showStr = "正在申请集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	//if (selGridFlag == 1)
	//{
	//	document.all("Transact").value = "INSERT||EDOR";
	//}
	//fm.submit();
	//document.all("Transact").value = "";
}

//打开个人保全的明细界面
function openGEdorDetail() 
{
	if (fm.ContNo.value=="")
	{
		alert("不能是空记录!");
	}
	else
	{
		fm.CustomerNoBak.value = fm.CustomerNo.value;
		fm.ContNoBak.value = fm.ContNo.value;
		if(document.all('EdorType').value=='GA' || document.all('EdorType').value=='VR')
		{
			window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp");
		}
	  else if (document.all('EdorType').value=='AG')
	  {
	  	  window.open("./PEdorTypeAGInput.jsp");
	  }
		else
		{
		    window.open("./GEdorType" + trim(document.all('EdorType').value) + ".jsp");
	  }
		showInfo.close();	 
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, Result)
{
	showInfo.close();

	if (FlagStr == "Fail")
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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

		if (fm.Transact.value=="DELETE||EDOR")
		{
			var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
	//queryClick2();
}

//处理获取焦点事件
function initFocus() 
{
  if (GEdorFlag)
  {   
    GEdorFlag = false;
    queryClick();
    queryGEdorList();
 	selGridFlag = 0;
  }
}

//查询出申请后的个人保全列表 已经作过保全项目的 
function queryGEdorList() 
{
	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LcInsured where GrpContNo= '" + document.all('GrpContNo').value + "' and ContNo in "
			   + " (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "')";
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid); 	 
	if (selGridFlag == 2)
	{
		//fm.ContNo.value = "";
		fm.CustomerNo.value = "";
		selGridFlag = 0;
	}
}

//单击MultiLine的单选按钮时操作
function reportDetail2Click(parm1, parm2)
{	
	fm.ContNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()-1, 2);
	selGridFlag = 2;
	//queryClick();
}

//撤销集体下个人保全
function cancelGEdor()
{
	if (selGridFlag == 0)
	{
		alert("请先选择一个被保人！");
		return;
	}
	document.all("Transact").value = "DELETE||EDOR"

	var showStr = "正在撤销集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//	var strSql = "select MakeDate, MakeTime, EdorState from LPEdorItem where EdorNo='" + fm.EdorNo.value + "' and EdorType = '" + fm.EdorType.value + "'";
//	var arrResult = easyExecSql(strSql);     //执行SQL语句进行查询，返回二维数组

//	if (arrResult != null)
//	{
//		var tMakeDate = arrResult[0][0];
//		var tMakeTime = arrResult[0][1];
//		var tEdorState = arrResult[0][2];
                var tGrpContNo = fm.GrpContNo.value;
 		var tCustomerNo = fm.CustomerNo.value;
 		var tEdorAcceptNo = fm.EdorNo.value;
 		var tEdorType = fm.EdorType.value;
 		var tEdorNo = fm.EdorNo.value;
 		var tContNo = fm.ContNo.value;
 		var tTransact = "DELETE||EDOR";

 		fm.action = "./GEdorTypeDetailSubmit.jsp?GrpContNo="+tGrpContNo+"&EdorNo="+tEdorNo+"&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&CustomerNo="+tCustomerNo+"&EdorType="+tEdorType+"&Transact="+tTransact;
		fm.submit();
//		fm.action = "./GEdorTypeDetailSubmit.jsp";
//	}
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug) {
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function returnParent() {
	top.opener.getEdorItemGrid();
	top.close();
}
function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value
	if(tEdortype!=null || tEdortype !='')
	{
	var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}

function submitLoad()
{
    getImportPath();
    document.all('ImportPath').value = ImportPath;
    var tGrpContNo = fm.GrpContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorValiDate = fm.EdorValiDate.value;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./GrpEdorTypeBBLoadSubmit.jsp?GrpContNo="+tGrpContNo+"&EdorNo="+tEdorNo+"&EdorValiDate="+tEdorValiDate;
    //alert(tGrpContNo); 
    fm.submit(); //提交
    //tSaveFlag ="0";
}

function getImportPath ()
{
	  var strSQL = "";
	  strSQL = "select SysvarValue from ldsysvar where sysvar ='XmlPath'";
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult) 
	  {
		    alert("未找到上传路径");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];
}

function afterSubmitForLoad( FlagStr, content)
{
	  showInfo.close();
	  if (FlagStr == 'Fail' )
	  {             
		    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	      //fm.BatchNo.value = BatchNo;
	      //SelectJSPayPerson();
	      //operateButton2.style.display="";
              //operateButton1.style.display="none";
	  }
}