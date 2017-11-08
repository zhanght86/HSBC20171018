// 该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sqlresourcename = "app.GrpPersonPrint_IDGFSql";
function printGrpPol()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
  // showSubmitFrame(mDebug);
  //document.all("GrpPolButton").disabled=true;
  document.all('fmtransact').value="PRINT||ALLGRPPERSON";
  document.getElementById("fm").submit();
}


//提交，保存按钮对应操作
function printGrpPerson()
{
  var i = 0;
  var flag = 0;
  
  for( i = 0; i < PolGrid.mulLineCount; i++ )
  {
    if( PolGrid.getChkNo(i) == true )
    {
       flag = 1;
       break;
    }
  }

  if( flag == 0 )
  {
    alert("请至少选择一条记录，再打印团单个人凭证!");
    return false;
  }

  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  //document.all("GrpPersonButton").disabled=true;
  document.all('fmtransact').value="PRINT||GRPPERSON";
  document.getElementById("fm").submit();
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //document.all("GrpPersonButton").disabled=false;
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


// 查询团单按钮
function queryGrpPol()
{
  if(document.all("GrpContNo").value == "" && document.all("PrtNo").value == "")
  {
    alert("请输入团单号或印刷号！");
    return false;
  }

  // 只显示团单信息
  divGrpPerson.style.display = "none" ;
  divGrpPol.style.display ='';
  
  // 初始化表格
  initGrpPolGrid();

  // 书写SQL语句
  /*
  var strSQL = "select Grpcontno,prtno,'',grpname,peoples2,cvalidate "
               + "from lcgrpCont a where AppFlag in ('1','4') "
//               + "and riskcode IN (select riskcode from LMRiskApp where SubRiskFlag = 'M') "
               + getWherePart('GrpContNo')
               + getWherePart('PrtNo')
               + " and ManageCom like '" + comcode + "%%'";
   */      
   
var sqlid1="GrpPersonPrint_IDGFSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.GrpContNo.value);
mySql1.addSubPara(fm.PrtNo.value);
mySql1.addSubPara(comcode);
         
               
  turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
    divGrpPol.style.display = "none" ;
    return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 10 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = GrpPolGrid;
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL ;
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


// 查询团单按钮
function queryGrpPerson()
{
  if(document.all("GrpContNo").value == "" && document.all("PrtNo").value == "")
  {
    alert("请输入团单号或印刷号！");
    return false;
  }

  // 只显示团单个人信息
  divGrpPol.style.display = "none" ;
  divGrpPerson.style.display ='';
  
  // 初始化表格
  initPolGrid();

  // 书写SQL语句
  /*
  var strSQL = "select contno,grpcontno,prtno,'',appntname,insuredname,cvalidate "
               + "from LCCont a where AppFlag in ('1','4') "
//               + "and riskcode IN (select riskcode from LMRiskApp where SubRiskFlag = 'M') "
//               + "and mainpolno = polno "
               + getWherePart('GrpContNo')
               + getWherePart('PrtNo')
               + getWherePart('ContNo')
               + " and ManageCom like '" + comcode + "%%'";
   */            
var sqlid2="GrpPersonPrint_IDGFSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(fm.GrpContNo.value);
mySql2.addSubPara(fm.PrtNo.value);
mySql2.addSubPara(fm.ContNo.value);
mySql2.addSubPara(comcode);
          
  turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
    divGrpPerson.style.display = "none" ;
    return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 10 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL ; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}