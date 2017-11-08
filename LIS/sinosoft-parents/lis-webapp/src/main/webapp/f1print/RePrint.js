//               该文件中包含客户端需要处理的函数和事件
var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();



//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //initPolGrid();
  showSubmitFrame(mDebug);
  fmSave.submit(); //提交
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

		if( null == arrReturn ) {
			alert("无效的数据");
			return;
		}

		fmSave.PrtSeq.value = arrReturn[0][0];
		fmSave.PolNo.value = arrReturn[0][1];
		fmSave.fmtransact.value = "CONFIRM";
		//fmSave.submit();
		submitForm();
	  showInfo.close();

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
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0]=new Array();
	arrSelected[0][0]=PolGrid.getRowColData(tRow-1,1);
	arrSelected[0][1]=PolGrid.getRowColData(tRow-1,3);

	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
  if (FlagStr == "Fail" )
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
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

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

// 查询按钮
function easyQueryClick()
{
	 //初始化表格
	initPolGrid();

		 //书写SQL语句
	var strSQL = "";
	var len = tmanageCom.length;
	if(fm.all('Code').value=="null"||fm.all('Code').value=="")
	{
		alert("请输入通知书类型！");
		return;
	}
	//alert(fm.all('Code').value);
	if(fm.all('Code').value=="15"||fm.all('Code').value=="16"){
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,(select distinct (case j.othernotype when '6' then '1' "+" when '7' then '3' "+" end ) from ljtempfee j where j.otherno = loprtmanager.otherno) FROM LOPRTManager WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and LOPRTManager.managecom like '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//	  + getWherePart('LOPRTManager.AgentCode','AgentCode')
//	  + getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.OtherNo','OtherNo','like')
//	  + getWherePart('LOPRTManager.Code','Code');
	
  	var  StartDay0 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay0 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq0 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo0 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code0 = window.document.getElementsByName(trim("Code"))[0].value;
	var sqlid0="RePrintSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(tmanageCom);//指定传入的参数
	mySql0.addSubPara(StartDay0);//指定传入的参数
	mySql0.addSubPara(EndDay0);//指定传入的参数
	mySql0.addSubPara(AgentCode0);//指定传入的参数
	mySql0.addSubPara(PrtSeq0);//指定传入的参数
	mySql0.addSubPara(OtherNo0);//指定传入的参数
	mySql0.addSubPara(Code0);//指定传入的参数
	strSQL=mySql0.getString();
	
	}
else if (fm.all('Code').value=="90")
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode FROM LOPRTManager WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and LOPRTManager.managecom like '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//	  + getWherePart('LOPRTManager.AgentCode','AgentCode')
//	  + getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.OtherNo','OtherNo','like')
//	  + getWherePart('LOPRTManager.Code','Code');
	
  	var  StartDay1 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay1 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq1 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo1 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code1 = window.document.getElementsByName(trim("Code"))[0].value;
	var sqlid1="RePrintSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tmanageCom);//指定传入的参数
	mySql1.addSubPara(StartDay1);//指定传入的参数
	mySql1.addSubPara(EndDay1);//指定传入的参数
	mySql1.addSubPara(AgentCode1);//指定传入的参数
	mySql1.addSubPara(PrtSeq1);//指定传入的参数
	mySql1.addSubPara(OtherNo1);//指定传入的参数
	mySql1.addSubPara(Code1);//指定传入的参数
	strSQL=mySql1.getString();
	
	}
	else if (fm.all('Code').value=="54"||fm.all('Code').value=="52")//问题件和撤保通知书的otherno是团体保单号---haopan
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,lcgrpcont.SaleChnl FROM LOPRTManager,lcgrpcont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  lcgrpcont.GrpContNo = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.OtherNo','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay2 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay2 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq2 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo2 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code2 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl2 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid2="RePrintSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tmanageCom);//指定传入的参数
	mySql2.addSubPara(StartDay2);//指定传入的参数
	mySql2.addSubPara(EndDay2);//指定传入的参数
	mySql2.addSubPara(AgentCode2);//指定传入的参数
	mySql2.addSubPara(PrtSeq2);//指定传入的参数
	mySql2.addSubPara(OtherNo2);//指定传入的参数
	mySql2.addSubPara(Code2);//指定传入的参数
	mySql2.addSubPara(SaleChnl2);//指定传入的参数
	strSQL=mySql2.getString();
	
	}
	else if (fm.all('Code').value=="G03")//团体体检和生调的otherno是团单下的个单号----haopan
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,LCCont.SaleChnl FROM LOPRTManager,LCCont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  LCCont.ProposalContNo = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.standbyflag3','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay3 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay3 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode3 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq3 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo3 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code3 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl3 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid3="RePrintSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tmanageCom);//指定传入的参数
	mySql3.addSubPara(StartDay3);//指定传入的参数
	mySql3.addSubPara(EndDay3);//指定传入的参数
	mySql3.addSubPara(OtherNo3);//指定传入的参数
	mySql3.addSubPara(AgentCode3);//指定传入的参数
	mySql3.addSubPara(PrtSeq3);//指定传入的参数
	mySql3.addSubPara(Code3);//指定传入的参数
	mySql3.addSubPara(SaleChnl3);//指定传入的参数
	strSQL=mySql3.getString();
	
	}
	else if (fm.all('Code').value=="G04")//--haopan
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,LCCont.SaleChnl FROM LOPRTManager,LCCont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  LCCont.ProposalContNo = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.standbyflag2','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay4 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay4 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode4 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq4 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo4 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code4 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl4 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid4="RePrintSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tmanageCom);//指定传入的参数
	mySql4.addSubPara(StartDay4);//指定传入的参数
	mySql4.addSubPara(EndDay4);//指定传入的参数
	mySql4.addSubPara(OtherNo4);//指定传入的参数
	mySql4.addSubPara(AgentCode4);//指定传入的参数
	mySql4.addSubPara(PrtSeq4);//指定传入的参数
	mySql4.addSubPara(Code4);//指定传入的参数
	mySql4.addSubPara(SaleChnl4);//指定传入的参数
	strSQL=mySql4.getString();
	
	
	}
//	wujf add beg 2007-6-14 8:49 问题号：127221
	else if (fm.all('Code').value=="78")
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,lcgrpcont.SaleChnl FROM LOPRTManager,lcgrpcont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  lcgrpcont.proposalgrpcontno = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.standbyflag2','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('lcgrpcont.SaleChnl','SaleChnl');
	
  	var  StartDay5 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay5 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode5 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq5 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo5 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code5 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl5 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid5="RePrintSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tmanageCom);//指定传入的参数
	mySql5.addSubPara(StartDay5);//指定传入的参数
	mySql5.addSubPara(EndDay5);//指定传入的参数
	mySql5.addSubPara(OtherNo5);//指定传入的参数
	mySql5.addSubPara(AgentCode5);//指定传入的参数
	mySql5.addSubPara(PrtSeq5);//指定传入的参数
	mySql5.addSubPara(Code5);//指定传入的参数
	mySql5.addSubPara(SaleChnl5);//指定传入的参数
	strSQL=mySql5.getString();
	
	}
//#wujf add end 2007-6-14 8:50 问题号：127221

else{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,LCCont.SaleChnl FROM LOPRTManager,LCCont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  LCCont.ProposalContNo = LOPRTManager.otherno"
//	  + " and code in ('16','18','15','54','09','52','08','78','90','BF00','JB00','G03','G04')"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.OtherNo','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay6 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay6 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode6 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq6 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo6 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code6 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl6 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid6="RePrintSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("f1print.RePrintSql"); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(tmanageCom);//指定传入的参数
	mySql6.addSubPara(StartDay6);//指定传入的参数
	mySql6.addSubPara(EndDay6);//指定传入的参数
	mySql6.addSubPara(AgentCode6);//指定传入的参数
	mySql6.addSubPara(PrtSeq6);//指定传入的参数
	mySql6.addSubPara(OtherNo6);//指定传入的参数
	mySql6.addSubPara(Code6);//指定传入的参数
	mySql6.addSubPara(SaleChnl6);//指定传入的参数
	strSQL=mySql6.getString();
	
	}


	turnPage.strQueryResult  = easyQueryVer3(strSQL);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
   alert("没有要打印的信息！");
    return false;
  }

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
  //tArr = chooseArray(arrDataSet,[0,1,3,4])
  //调用MULTILINE对象显示查询结果

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function IntegrateQueryClick()
{
	var newWindow = window.open("../sys/AllProposalQueryMain1.jsp","AllProposalQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  	newWindow.focus();
	}