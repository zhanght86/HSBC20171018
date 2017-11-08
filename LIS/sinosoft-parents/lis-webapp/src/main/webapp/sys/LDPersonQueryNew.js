//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  initPersonGrid();
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	//initPersonGrid();

    //<!-- XinYQ added on 2005-12-09 : 至少输入一个条件才进行查询 : BGN -->
    var sCustomerNo = document.getElementsByName("CustomerNo")[0].value;
    var sName = document.getElementsByName("Name")[0].value;
    var sBirthday = document.getElementsByName("Birthday")[0].value;
    var sIDNo = document.getElementsByName("IDNo")[0].value;
    var sContNo = document.getElementsByName("ContNo")[0].value;
    if (trim(sCustomerNo) == "" && trim(sName) == "" && trim(sBirthday) == "" && trim(sIDNo) == "" && trim(sContNo) == "")
    {
        alert("请您至少输入以下查询条件中的一个：客户号码、姓名、出生日期、证件号码、保单号  ");
        return;
    }
    
//    if(trim(sCustomerNo) == null|| trim(sCustomerNo) == "")
//    {
//    	   alert("请输入客户号码");
//          return;
//    }
    //<!-- XinYQ added on 2005-12-09 : 至少输入一个条件才进行查询 : END -->

	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	
	var sqlid1="LDPersonQueryNewSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("sys.LDPersonQueryNewSql"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(fm.CustomerNo.value);//指定传入的参数
    mySql1.addSubPara(fm.Name.value);//指定传入的参数
    mySql1.addSubPara(fm.Sex.value);//指定传入的参数
    mySql1.addSubPara(fm.Birthday.value);//指定传入的参数
	mySql1.addSubPara(fm.IDType.value);//指定传入的参数
	mySql1.addSubPara(fm.IDNo.value);//指定传入的参数
	var addStr1 = " and 1=1 ";
//	strSQL=mySql1.getString();
	
//	strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//				 + getWherePart( 'a.CustomerNo','CustomerNo',strOperate )
//				 + getWherePart( 'a.Name','Name',strOperate )
//				 + getWherePart( 'a.Sex','Sex',strOperate )
//				 + getWherePart( 'a.Birthday','Birthday','=' )
//				 + getWherePart( 'a.IDType','IDType',strOperate )
//				 + getWherePart( 'a.IDNo','IDNo',strOperate );

    if(sContNo != null && trim(sContNo) != "")
    {
    	addStr1 = " and a.customerno in ("
    	        + " select appntno from lcappnt where contno = '"+sContNo+"' "
    	        + " union "
    	        + " select insuredno from lcinsured where contno = '"+sContNo+"' "
    	        + " )";
    }
//	strSQL	+= "order by a.CustomerNo";
//alert(strSQL);

    mySql1.addSubPara(addStr1);//指定传入的参数
    strSQL=mySql1.getString();
	turnPage.queryModal(strSQL, PersonGrid);        
}

// 数据返回父窗口
function returnParent()
{
	//alert("aaa="+top.opener.location);
	var arrReturn = new Array();
	var tSel = PersonGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			if(queryFlag=="queryAppnt"){
			  top.opener.afterQuery( arrReturn );
		  }
		  else if(queryFlag=="queryInsured"){
			  top.opener.afterQuery21( arrReturn );
		  }
		  else{
			  top.opener.afterQuery( arrReturn );
		  }
			top.close();
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录，再点击返回按钮。");
			//alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResult()
{
	//获取正确的行号
	tRow = PersonGrid.getSelNo() - 1;

	arrSelected = new Array();
	//设置需要返回的数组
	
		var sqlid2="LDPersonQueryNewSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("sys.LDPersonQueryNewSql"); //指定使用的properties文件名
    mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    mySql2.addSubPara(PersonGrid.getRowColData(tRow, 1));//指定传入的参数

	strSql=mySql2.getString();
	
//	strSql = "select * from LDPerson where 1=1"
//	       + " and CustomerNo = '" + PersonGrid.getRowColData(tRow, 1) + "'"
//	       + " and Name = '" + PersonGrid.getRowColData(tRow, 2) + "'"
//	       + " and Sex = '" + PersonGrid.getRowColData(tRow, 3) + "'"
//	       + " and Birthday = '" + PersonGrid.getRowColData(tRow, 4) + "'"
//	       + " and IDType = '" + PersonGrid.getRowColData(tRow, 5) + "'"
//	       + " and IDNo = '" + PersonGrid.getRowColData(tRow, 6) + "'"
	       ;
	
	//alert(strSql);
	var arrResult = easyExecSql(strSql);
	
	return arrResult;
}

