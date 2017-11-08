//该文件中包含客户端需要处理的函数和事件
var showInfo;
var pEdorFlag = true;                        //用于实时刷新处理过的数据的列表

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();         //使用翻页功能，必须建立为全局变量
window.onfocus = initFocus;                  //重定义获取焦点处理事件
var arrList1 = new Array();                  //选择的记录列表

/**
 * 查询按钮
 */
function queryClick() {
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
	
//	var strSql = "select GrpPolNo, RiskCode, Peoples2, Prem, Amnt from LCGrpPol where grpcontno = '"
//	           + fm.GrpContNo.value + "'"
//	           + getWherePart('GrpPolNo')
//	           + getWherePart('RiskCode')
//	           + " and RiskCode not in (select distinct riskcode from lcpol where polno in (select polno from lpedorItem where edorno='" + fm.EdorNo.value + "' and edortype='"+fm.EdorType.value+"'))";
	var strSql = "";
	var sqlid1="GEdorTypeMultiRiskSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeMultiRiskSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("GrpPolNo")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("RiskCode")[0].value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	strSql=mySql1.getString();	
	turnPage.queryModal(strSql, RiskGrid); 	 
	showInfo.close();	 
}

/**
 * 单击MultiLine的复选按钮时操作
 */
function reportDetailClick(parm1, parm2) {	
  if (document.all(parm1).all('RiskGridChk').checked) {
    arrList1[document.all(parm1).all('RiskGridNo').value] = document.all(parm1).all('RiskGrid1').value;
  }
  else {
    arrList1[document.all(parm1).all('RiskGridNo').value] = null;
  }
  
  GrpBQ = false;
}

/**
 * 进入多个人保全
 */
var arrP = 0;
function pEdorMultiDetail() 
{
    //校验是否选择
    var i = 0;
    var chkFlag=false;
    for (i=0;i<RiskGrid.mulLineCount;i++ )
    {
        if (RiskGrid.getChkNo(i))
        {
          chkFlag=true;
          break;
        }
        
    }
    if (chkFlag)
    {
      PEdorDetail();
      
    }
    else
    {
        alert("请选择需要操作的记录!");
    } 
 
}

/**
 * 进入个人保全
 */
function PEdorDetail() 
{    
    var showStr = "正在申请集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all("Transact").value = "INSERT||EDORRISK";
    fm.submit();
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
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
    pEdorFlag = true;
    
    if (fm.Transact.value=="DELETE||EDORRISK") 
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
     // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
      openPEdorDetail();
      //PEdorDetail();
    }
  }
}

/**
 * 处理获取焦点事件
 */
function initFocus() {
  if (pEdorFlag) {   
    pEdorFlag = false;
        
    queryPEdorList();
  }
}

var GrpBQ = false;
var GTArr = new Array();
/**
 * 打开个人保全的明细界面
 */
function openPEdorDetail() 
{

}

/**
 * 查询出申请后的个人保全列表
 */
function queryPEdorList() {
//  var strSql = "select GrpPolNo, RiskCode, Peoples2, Prem, Amnt from LCGrpPol where grppolno in "
//	           + "(select distinct grppolno from lcpol where polno in (select polno from lpedoritem where edorno='" + fm.EdorNo.value + "' and edortype='"+fm.EdorType.value+"'))";
  
  	var strSql = "";
	var sqlid1="GEdorTypeMultiRiskSql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeMultiRiskSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	strSql=mySql1.getString();	
  
  turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, Risk2Grid); 	 
	
	queryClick();
}

/**
 * 单击MultiLine的单选按钮时操作
 */
function reportDetail2Click(parm1, parm2) {	
  if (document.all(parm1).all('Risk2GridChk').value=='on' || document.all(parm1).all('Risk2GridChk').value=='1') {
    fm.ContNo.value = document.all(parm1).all('Risk2Grid1').value;
  }
}

/**
 * 撤销集体下个人保全
 */
function cancelPEdor() {
  //校验是否选择
    var i = 0;
    var chkFlag=false;
    for (i=0;i<Risk2Grid.mulLineCount;i++ )
    {
        if (Risk2Grid.getChkNo(i))
        {
          chkFlag=true;
          break;
        }
        
    }
    if (chkFlag)
    {
        document.all("Transact").value = "DELETE||EDORRISK"
    
        var showStr = "正在撤销保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
        fm.submit();
    }
    else
    {
        alert("请选择需要操作的记录!");
    }   
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
