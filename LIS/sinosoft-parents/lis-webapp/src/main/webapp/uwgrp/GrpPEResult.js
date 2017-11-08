//程序名称：RReportQuery.js
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    showInfo.close();
    
  }
  else
  { 
 // showInfo.close();
	var showStr="操作成功";
  alert(showStr);
 // parent.close();
    //执行下一步操作
  }
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
  else 
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
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

// 查询按钮
function easyQueryClick(tContNo)
{
	var strsql = "";
	

		
        if(tContNo != "")
        {
		strsql = "select contno,name,operator,makedate,replyoperator,replydate,replyflag,prtseq,RReportReason from lcrreport where 1=1 "
		        + " and contno = '"+tContNo+"'" ;
		     	
	
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查到该客户的生存调查信息！");
	initQuestGrid();
    return true;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
}

// 查询按钮
function easyQueryChoClick(tContNo)
{	
  
	
	var tContNo = fm.ContNo.value;

	var tSelNo = QuestGrid.getSelNo()-1;

	var tPrtSeq = QuestGrid.getRowColData(tSelNo,8);
	
		if (tContNo != " ")
	{
		strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and prtseq = '"+tPrtSeq+"'";
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
		fm.Contente.value = strSQL;
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
    return "";
    
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);    
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RReportGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}


	//查询SQL，返回结果字符串
	
  	var strsql= "select Contente,ReplyContente from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and PrtSeq = '"+tPrtSeq+"'"; 	
		
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		fm.all('Contente').value = arrReturn[0][0];
		fm.all('ReplyContente').value = arrReturn[0][1];
	}
  
  return true;
}


function initReport()
{
  
	var i,j,m,n;
	var returnstr;
  var tPrtSeq = fm.all('PrtSeq').value ;
	//alert(tPrtSeq);
	//查询SQL，返回结果字符串
 var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
  
   turnPage.queryModal(strSql, QuestGrid);
   
   strSql ="select RReportItemCode,RReportItemName from lcrreportitem where  prtseq = '"+tPrtSeq+"'";

   turnPage.queryModal(strSql, RReportGrid);
   
   strSql = "select Name,CustomerNo from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
    
    arrResult = easyExecSql(strSql,1,0);
   
    fm.all('CustomerName').value = arrResult[0][0];
	  fm.all('CustomerNo').value = arrResult[0][1];
   

}



function QueryCont(tGrpContNo)
{
	
	var strSQL="select ContNo,PrtSeq from lcpenotice where grpcontno ='"+tGrpContNo+"'";
	
	turnPage.queryModal(strSQL, ContGrid); 
	return true;
	
}

function showSelectRecord()
{
		
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<ContGrid.mulLineCount; i++) 
   {
    if (ContGrid.getSelNo(i)) 
      { 
      checkFlag = ContGrid.getSelNo();
      break;
      }
   }
  
  if (checkFlag) 
    { 
    	
    		var ContNo = ContGrid.getRowColData(checkFlag - 1, 1);  
    		
    		var PrtSeq = ContGrid.getRowColData(checkFlag - 1, 2);
    		
   		var strSQL="select PEAddress,PEDoctor,PEDate,REPEDate ,PEResult from lcpenotice where contno ='"+ContNo+"' and PrtSeq='"+PrtSeq+"'";
   		var arrReturn = new Array();
        	arrReturn = easyExecSql(strSQL);
       
       		if(arrReturn!=null)
       		{
        
			fm.all('PEAddress').value = arrReturn[0][0];
			fm.all('PEDoctor').value = arrReturn[0][1];
			fm.all('PEDate').value = arrReturn[0][2];
			fm.all('REPEDate').value = arrReturn[0][3];	
			fm.all('Note').value = arrReturn[0][4];
		}
		var strSQL="select PEItemCode,PEItemName,PEItemResult from LCPENoticeItem where contno ='"+ContNo+"' and PrtSeq='"+PrtSeq+"'";
    		
    		turnPage.queryModal(strSQL, HealthGrid); 
    		var strSQL="select DisDesb,DisResult,ICDCode from LCPENoticeResult where contno ='"+ContNo+"' and PrtSeq='"+PrtSeq+"'";
    		turnPage.queryModal(strSQL, DisDesbGrid);
    		return true;
    }
}

function afterCodeSelect(cCodeName, Field)
{
	if (cCodeName == "CustomerName")
	{
		showSelectRecord(fm.all("ContNo").value);
	}
}

/*********************************************************************
 *  生调信息保存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function saveRReport()
{
	var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  fm.action= "./RReportQuerySave.jsp";
  fm.submit(); //提交
   showInfo.close();
}

function easyQueryClickItem()
{
	for (i=0; i<ContGrid.mulLineCount; i++) 
   	{
   	 if (ContGrid.getSelNo(i)) 
     	 { 
     		var tContNo=ContGrid.getRowColData(ContGrid.getSelNo()-1,1);
     		var tPrtSeq=ContGrid.getRowColData(ContGrid.getSelNo()-1,2);	
      		//checkFlag = GrpSubGrid.getSelNo();
      		break;
     	 }
   	}

	
		if (tContNo != " ")
	{
		strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and PrtSeq='"+tPrtSeq+"'";
	
		
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
		
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
    return "";
    
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);    
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RReportGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}


	//查询SQL，返回结果字符串
	
  	var strsql= "select ReplyContente from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and PrtSeq='"+tPrtSeq+"'"; 	
		
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		fm.all('ReplyContente').value = arrReturn[0][0];	
}

}
function ReportInfoClick()
{
	 
	 showSelectRecord();
	 
}