//               该文件中包含客户端需要处理的函数和事件
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var manageCom;

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
  initPolGrid();
  document.getElementById("fm").submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();	
  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		arrReturn = getQueryResult();
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,6);
		if( null == arrReturn ) {
			alert("无效的数据");
			return;
		}
		fmSave.target="f1print";
		fmSave.ContNo.value = tContNo ;
		fmSave.PrtSeq.value = tPrtSeq ;
		fmSave.noticetype.value = tNoticeType;
		
		fmSave.fmtransact.value = "CONFIRM";
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

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
if(document.all('NoticeType').value == "")
  {
  	alert("请选择通知书类型！");
  	return;
  	}
	
    /*strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, LCCont.SaleChnl,LOPRTManager.ManageCom ,LOPRTManager.code FROM LOPRTManager,LCCont WHERE 1 = 1 " 
	   + "and LCCont.ProposalContNo = LOPRTManager.OtherNo "	 
	   + "and LOPRTManager.code in('08','78')"
	   + getWherePart('LOPRTManager.otherno', 'ContNo') 
	   + getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
	   + getWherePart('LOPRTManager.AgentCode','AgentCode')	  
	   + getWherePart('LCCont.SaleChnl','SaleChnl')
	   + " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'"//登陆机构权限控制	  
	   +" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";*/
	if(fm.NoticeType.value == "01")
	{
//	   strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, LCCont.SaleChnl,LOPRTManager.ManageCom ,LOPRTManager.code FROM LOPRTManager,LCCont WHERE 1 = 1 " 
//	   + "and LCCont.ContNo = LOPRTManager.OtherNo "	 
//	   + "and LOPRTManager.code ='08'"
//	   + getWherePart('LOPRTManager.otherno', 'ContNo') 
//	   + getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
//	   + getWherePart('LOPRTManager.AgentCode','AgentCode')	  
//	   + getWherePart('LCCont.SaleChnl','SaleChnl')
//	   + " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'"//登陆机构权限控制	  
//	   +" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";
		fmSave.noticetype.value = "01"
		
		var  ContNo0 = window.document.getElementsByName(trim("ContNo"))[0].value;
	  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	  	var  SaleChnl0 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		var sqlid0="NBCSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.NBCSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(ContNo0);//指定传入的参数
		mySql0.addSubPara(ManageCom0);//指定传入的参数
		mySql0.addSubPara(AgentCode0);//指定传入的参数
		mySql0.addSubPara(SaleChnl0);//指定传入的参数
		mySql0.addSubPara(comcode);//指定传入的参数
		strSQL=mySql0.getString();
	}
 else if(fm.NoticeType.value == "02")
 	{
// 		 strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, lcgrpcont.SaleChnl,LOPRTManager.ManageCom ,LOPRTManager.code FROM LOPRTManager,lcgrpcont WHERE 1 = 1 " 
//	   + "and lcgrpcont.proposalgrpcontno = LOPRTManager.OtherNo "	 
//	   + "and LOPRTManager.code ='78'"
//	   //+ getWherePart('LOPRTManager.otherno', 'ContNo') 
//	   + getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
//	   + getWherePart('LOPRTManager.AgentCode','AgentCode')	  
//	   + getWherePart('lcgrpcont.SaleChnl','SaleChnl')
//	   + " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'"//登陆机构权限控制	  
//	   +" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";
 		fmSave.noticetype.value = "02";
 		
// 		if(fm.ContNo.value!=null&&fm.ContNo.value!=""){
// 		   //add by jiaqiangli 2009-11-18
// 		   strSQL =strSQL+" and LOPRTManager.otherno in (select prtno from lcgrppol where grpcontno ='"+fm.ContNo.value+"')";
// 		}
 		
	  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	  	var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	  	var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		var sqlid1="NBCSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.NBCSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(ManageCom1);//指定传入的参数
		mySql1.addSubPara(AgentCode1);//指定传入的参数
		mySql1.addSubPara(SaleChnl1);//指定传入的参数
		mySql1.addSubPara(comcode);//指定传入的参数
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
		strSQL=mySql1.getString();
 	
 	}
		
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有要打印的新契约退费通知书！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //tArr = chooseArray(arrDataSet,[0,1,3,4]) 
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