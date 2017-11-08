//               该文件中包含客户端需要处理的函数和事件
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
  {
        showInfo.close();
		alert( "请先选择一条记录，再点击返回按钮。" );
  }
  else
	{
		PrtSeq = PolGrid.getRowColData(tSel-1,1);
		fmSave.PrtSeq.value = PrtSeq;
		fmSave.all('fmtransact').value = "PRINT";
		fmSave.target = "f1print";		
		fmSave.submit();
		showInfo.close();				
	}
}


//批量打印——提交
function printBatch()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //showSubmitFrame(mDebug);
  
  var Sql = fmSave.all('Sql').value;
  if(Sql == null || Sql.length == 0 )
  {
  	showInfo.close();
  	alert("没有要打印的续保确认通知书！");
  }
  else
  {
  	fmSave.all('fmtransact').value = "PRINTBATCH";
	fmSave.target = "fraSubmit";		
	fmSave.submit();
  }		
}



function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;	
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
   showInfo.close();
   if (FlagStr == "Fail" )
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
}           

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}


// 查询按钮
function easyQueryClick()
{

	initPolGrid();
	// 书写SQL语句
	var strSQL = "";
	
	var strManageComWhere = " ";
	if( fm.BranchGroup.value != '' ) {
		strManageComWhere += " AND LAAgent.AgentGroup IN" 
						  + " ( SELECT AgentGroup FROM LABranchGroup WHERE BranchAttr LIKE '" 
						  +  fm.BranchGroup.value + "%%') ";
	}
	// 书写SQL语句
//	strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, LAAgent.AgentGroup,LABranchGroup.BranchAttr,LOPRTManager.ManageCom FROM LOPRTManager,LAAgent,LABranchGroup WHERE LOPRTManager.Code = '49' "  //LOPRTManager.Code='03' 单证类型为核保
//	        + "and LAAgent.AgentCode = LOPRTManager.AgentCode "
//	        + "and LABranchGroup.AgentGroup = LAAgent.AgentGroup"
//			+ getWherePart('LOPRTManager.OtherNo', 'ContNo') 
//			+ getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
//			+ getWherePart('LOPRTManager.AgentCode','AgentCode')
//			+ getWherePart('LAAgent.AgentGroup','AgentGroup')
//			+ " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'" //登陆机构权限控制
//			+ strManageComWhere
//			+" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";
	  
	var sqlid1="PRnewSurePrintSql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("renewal.PRnewSurePrintSql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);//指定传入参数
 	mySql1.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("AgentGroup"))[0].value);
 	mySql1.addSubPara(comcode);
 	mySql1.addSubPara(fm.BranchGroup.value);
 	strSQL = mySql1.getString();
	
	
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	fmSave.Sql.value ="";
    alert("没有要打印的续保确认通知书！");
    return false;
    }
  
//var strSQL1 = "SELECT LOPRTManager.PrtSeq, LOPRTManager.Oldprtseq,LOPRTManager.Standbyflag2,LOPRTManager.Reqoperator,LOPRTManager.OtherNo,LOPRTManager.AgentCode,LOPRTManager.ManageCom FROM LOPRTManager,LAAgent,LABranchGroup WHERE LOPRTManager.Code = '49' "  //LOPRTManager.Code='03' 单证类型为核保
//	        + "and LAAgent.AgentCode = LOPRTManager.AgentCode "
//	        + "and LABranchGroup.AgentGroup = LAAgent.AgentGroup "
//			+ getWherePart('LOPRTManager.OtherNo', 'ContNo') 
//			+ "and LOPRTManager.ManageCom like '"+fm.ManageCom.value+"%' "
//			+ getWherePart('LOPRTManager.AgentCode','AgentCode')
//			+ getWherePart('LAAgent.AgentGroup','AgentGroup')
//			+ " AND LOPRTManager.ManageCom LIKE '" + comcode + "%'" //登陆机构权限控制
//			+ strManageComWhere
//			+" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";

    var sqlid2="PRnewSurePrintSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("renewal.PRnewSurePrintSql");
	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	mySql2.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);//指定传入参数
	mySql2.addSubPara(fm.ManageCom.value);
	mySql2.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
	mySql2.addSubPara(window.document.getElementsByName(trim("AgentGroup"))[0].value);
	mySql2.addSubPara(comcode);
	mySql2.addSubPara(fm.BranchGroup.value);
	var strSQL1 = mySql2.getString();


  fmSave.Sql.value=strSQL1;
//查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
 arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 //tArr=chooseArray(arrDataSet,[0]) 
  //调用MULTILINE对象显示查询结果
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html");
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{
  
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}
//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //代理人编码	
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
   
	var sqlid3="PRnewSurePrintSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("renewal.PRnewSurePrintSql");
	mySql3.setSqlId(sqlid3); //指定使用SQL的id
	mySql3.addSubPara(cAgentCode);//指定传入参数
	var strSql = mySql3.getString();
	
	var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
	
	var sqlid4="PRnewSurePrintSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("renewal.PRnewSurePrintSql");
	mySql4.setSqlId(sqlid4); //指定使用SQL的id
	mySql4.addSubPara(cAgentCode);//指定传入参数
	var strSql = mySql4.getString();
	
    var arrResult = easyExecSql(strSql);
    // alert(arrResult);
    if (arrResult != null) {
    
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
}