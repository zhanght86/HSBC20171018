var mDebug="1";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var mySql = new SqlClass();
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
    document.all('ImportPath').value = ImportPath;
    var tFlag = fm.Flag.value;
//    alert(tFlag);return false;
    if (document.all('FileName').value=="")
    {
       alert ("请输入文件地址!");	
       return false;
    }    
//    alert(tFlag);
//    return false;
    //add by wood
    var tRgtNo;
    
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    if(tFlag == "TOACC")
    {
        tRgtNo = fm.RgtNo.value;
        fm.action = "./GrpCustomerDiskForAccSave.jsp?Flag="+tFlag+"&RgtNo="+tRgtNo;//帐户理赔导入
    }
    else if(tFlag == 'TOSIMALL')
    {
        fm.action = "./GrpCustomerDiskForSimpleAllSave.jsp";//非理算导入
    }
    else if(tFlag == 'TOCLASS')
    {
        fm.action = "./GrpCustomerForReceiptClassSave.jsp";//社保账单导入
    }
    else if(tFlag == 'TOSIM')
    {
        
        tRgtNo = fm.RgtNo.value;
        //alert(tRgtNo);
        fm.action = "./GrpCustomerDiskSave.jsp?Flag="+tFlag+"&RgtNo="+tRgtNo;//批量理赔导入
    }
    else
    {
        fm.action = "./GrpCustomerDiskSave.jsp";//简易理赔导入
    }
    fm.submit(); //提交
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ,Result )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();


    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();
 

    }
//  top.opener.afterQuery(Result);
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
    alert("在ProposalCopy.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

function getImportPath ()
{
  // 书写SQL语句
 /* var strSQL = "";
  strSQL = "select SysvarValue from ldsysvar where sysvar ='XmlPath'";*/
  mySql = new SqlClass();
mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
mySql.setSqlId("GrpCustomerDiskSql1");
mySql.addSubPara("");

  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);
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


function easyQueryClick()
{
  
  if (fmquery.tRgtNo.value==""&&fmquery.tCustomerNo.value==""&&fmquery.tCustomerName.value=="")
  {
  	alert("请至少输入一个查询条件！");
  	return;
  }  
  
  initDiskErrQueryGrid();
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


  
  // 书写SQL语句
  //var strSql = "select rgtno,customerno,customername,ErrorInfo,BatchNo,ID,operator,makedate,maketime from lcGrpCustomerImportLog where 1=1 ";
    //+ "and ErrorState='0' "; 暂不加这个条件
	  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql2"); 
	mySql.addSubPara(document.all('Operator').value);  
  if(fmquery.all('tRgtNo').value!=null&&fmquery.all('tRgtNo').value!="")
  {
    //strSql=strSql+ "and RgtNo='"+fmquery.all('tRgtNo').value+"'";
      mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql3");  
	mySql.addSubPara(fmquery.all('tRgtNo').value); 
	mySql.addSubPara(document.all('Operator').value); 
  }
  if(fmquery.all('tCustomerNo').value!=null&&fmquery.all('tCustomerNo').value!="")
  {
    //strSql=strSql+ "and CustomerNo='"+fmquery.all('tCustomerNo').value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql4");  
	mySql.addSubPara(fmquery.all('tRgtNo').value); 
	mySql.addSubPara(fmquery.all('tCustomerNo').value);
	mySql.addSubPara(document.all('Operator').value);  
  }
  if(fmquery.all('tCustomerName').value!=null&&fmquery.all('tCustomerName').value!="")
  {
    //strSql=strSql+ "and customername='"+fmquery.all('tCustomerName').value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql5");  
	mySql.addSubPara(fmquery.all('tRgtNo').value); 
	mySql.addSubPara(fmquery.all('tCustomerNo').value); 
	mySql.addSubPara(fmquery.all('tCustomerName').value); 
	mySql.addSubPara(document.all('Operator').value); 
  }
    /*strSql=strSql+ "and operator='"+document.all('Operator').value+"'";
  strSql=strSql+ " Order by rgtno,customerno";*/

  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    showInfo.close();
    alert("未查询到满足条件的数据！");
    return false;
  }
  //设置查询起始位置
  //turnPage.pageIndex = 0;
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 20 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = DiskErrQueryGrid;
  //保存SQL语句
  turnPage.strQuerySql = strSql
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  showInfo.close();
}


/*********************************************************************
 *  按批次或集体保单号删除磁盘倒入的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteInsured()
{
    var i = DiskErrQueryGrid.getSelNo();
    var tRptNo = "";
    var BatchNo = "";
    var ID = "";
    if (i != '0')
    {
      i = i - 1;
      tRptNo = DiskErrQueryGrid.getRowColData(i,1);
      tBatchNo = DiskErrQueryGrid.getRowColData(i,5);
      tID = DiskErrQueryGrid.getRowColData(i,6);
      
    }else{
      alert("请选择一条数据！");
      return false;
    }

/*
  var numFlag=false;
  var row = DiskErrQueryGrid.mulLineCount-1;
  for(var m=0;m<row;m++ )
  {
    if(DiskErrQueryGrid.getChkNo(m))
    {
      var i = DiskErrQueryGrid.getChkNo(m);//得到焦点行
      if(i==true){
         numFlag = true;
      }
    }
  }
  if(numFlag==false)
  {
    alert("你没有选择要删除的数据！");
    return false;
  }
*/
  var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  
  fm.action = "./DiskDeleteInsured.jsp?tRptNo="+tRptNo+"&tBatchNo="+tBatchNo+"&tID="+tID;
  fm.submit(); //提交
}
/*********************************************************************
 *  按登陆用户删除磁盘倒入的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteAll()
{
  var tOperator = fm.Operator.value ;
  var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  
  fm.action = "./DiskDeleteInsured.jsp?Operator="+tOperator;
  fm.submit(); //提交
}