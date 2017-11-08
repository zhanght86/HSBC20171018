//该文件中包含客户端需要处理的函数和事件
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //标识当前选中记录的GRID

//该文件中包含客户端需要处理的函数和事件
var sqlresourcename = "bq.PEdorTypeTIInputSql"; 
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage3 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
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
	

var strSql = "";
	var sqlid1="PEdorTypeTIInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.GrpContNo.value);
	strSql=mySql1.getString();
        
	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(strSql, LCGrpInsuAccGrid);
	showInfo.close();	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content)
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initLPInsuAccGrid();
	    	QueryBussiness();
	}
}

//查询按钮
function queryPol()
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


	var sqlid1="PEdorTypeTIInputSql2";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.PolNo.value);//指定传入的参数
	mySql1.addSubPara(fm.InsuredNo.value);
	mySql1.addSubPara(fm.InsuredName.value);
	mySql1.addSubPara(fm.IDType.value);
	mySql1.addSubPara(fm.IDNo.value);
	mySql1.addSubPara(fm.Sex.value);
	mySql1.addSubPara(fm.Birthday.value);
	var strSql=mySql1.getString();
          
	turnPage3.pageDivName = "divPage2";
	turnPage3.queryModal(strSql, LCInsuAccGrid);
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

//进入团体对个人账户转换
function GEdorDetail()
{	
	if (LCInsuAccGrid.getSelNo()==0)
	{
	   alert("请选择需要进行账户转换的保单!");
	   return;
	}
	var tPolNo=LCInsuAccGrid.getRowColData(LCInsuAccGrid.getSelNo()-1,1);
	if(tPolNo==null||tPolNo=='')
	 {
		alert("未得到保单号码!");
		return;	
	 }
	window.open("./GrPEdorTypeTIInputMain.jsp?GrpContNo="+fm.GrpContNo.value+"&PolNo="+tPolNo+"&EdorType="+fm.EdorType.value);
}

//进入个人账户转换
function EdorDetail()
{
  	//进入之前需要选择进行转换的保单
	  if (LCInsuAccGrid.getSelNo()==0)
	  {
	    alert("请选择需要进行账户转换的保单!");
	    return;
	  }
	  var tPolNo=LCInsuAccGrid.getRowColData(LCInsuAccGrid.getSelNo()-1,1);
	  if(tPolNo==null||tPolNo=='')
	  {
		alert("未得到保单号码!");
		return;	
	  }
	window.open("./PEdorTypeTIDetailMain.jsp?ContNo="+fm.ContNo.value+"&PolNo="+tPolNo+"&EdorType="+fm.EdorType.value+"&EdorNo="+fm.EdorNo.value);
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

	var sqlid1="PEdorTypeTIInputSql3";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('GrpContNo').value);
	mySql1.addSubPara(document.all('EdorType').value);//指定传入的参数
	mySql1.addSubPara(document.all('EdorNo').value);
	var strSql=mySql1.getString();
          
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
 if (LPInsuAccGrid.getSelNo()==0)
  {
    alert("请选择需要撤销的关联交易!");
    return;
  }

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.all('Transact').value = "DELETE||PMAIN";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
	
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

/*查询交易信息*/
function QueryBussiness()
{


	var sqlid1="PEdorTypeTIInputSql4";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.ContNo.value);
	var strSql=mySql1.getString();
          
	turnPage3.pageDivName = "divPage3";
	turnPage3.queryModal(strSql, LPInsuAccGrid); 
}

function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
var sqlid1="PEdorTypeTIInputSql5";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdortype);
	var strSQL=mySql1.getString();
          
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
