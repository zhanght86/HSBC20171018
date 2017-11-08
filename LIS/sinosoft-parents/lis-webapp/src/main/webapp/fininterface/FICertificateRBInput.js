 
//创建日期： 
//创建人   jw
//更新记录：  更新人    更新日期     更新原因/内容
var showInfo;
var showDealWindow;

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;



function AppDeal()
{

  var i = 0;
  var flag = 0;
  for( i = 0; i < RBResultGrid.mulLineCount; i++ )
  {
  	if( RBResultGrid.getSelNo(i) >0 )
  	{
  		flag = 1;
  		break;
  	}
  }

  if( flag == 0 )
  {
  	alert("请先选择一条申请记录");
  	return false;
  }
  showDealWindow = window.open("./FICertificateRBDealForOther.jsp?BusinessNo='" + document.all('BusinessNo').value +"'&CertificateId='" + document.all('CertificateId').value + "'&AppNo='" + document.all('AppNo').value + "'"); 

}


function AppDelete()
{
  var i = 0;
  var flag = 0;
  for( i = 0; i < RBResultGrid.mulLineCount; i++ )
  {
  	if( RBResultGrid.getSelNo(i) >0 )
  	{
  		flag = 1;
  		break;
  	}
  }

  if( flag == 0 )
  {
  	alert("请先选择一条申请记录");
  	return false;
  }   
  
    var showStr="正在确认数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FICertificateRBSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交	
  
}



function ReConfirm()
{

    var showStr="正在确认数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FICertificateConfirmSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交	
	
}



function queryResultGrid()
{
    
    initResultGrid() ;
     
    var strSQL = ""; 
    /**
    strSQL = "select AppNo,CertificateID,DetailIndexID,BusinessNo,ReasonType,DetailReMark,AppDate,Applicant,AppState from FIDataFeeBackApp where AppState not in ('04','05')";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FICertificateRBInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("FICertificateRBInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(1);//指定传入的参数
		strSQL= mySql1.getString();
	
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    if (!turnPage.strQueryResult)
    {
         alert("未查询到满足条件的数据！");
	 return false;
    }
    
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    turnPage.pageLineNum = 30 ;
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid = RBResultGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL ;
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
}


function ReturnData()
{
        var arrReturn = new Array();
	var tSel = RBResultGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )

	    alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{

	   document.all('AppNo').value = RBResultGrid.getRowColData(tSel-1,1);		
	   document.all('CertificateId').value = RBResultGrid.getRowColData(tSel-1,2);	
	   document.all('DetailIndexID').value = RBResultGrid.getRowColData(tSel-1,3);
	   document.all('BusinessNo').value = RBResultGrid.getRowColData(tSel-1,4);
           document.all('ReasonType').value = RBResultGrid.getRowColData(tSel-1,5);
	   document.all('DetailReMark').value = RBResultGrid.getRowColData(tSel-1,6);    
	   document.all('AppDate').value = RBResultGrid.getRowColData(tSel-1,7);      
           document.all('Applicant').value = RBResultGrid.getRowColData(tSel-1,8);
           showCodeName();
 
	   		
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
	 
 try
 {
   showInfo.close();
 
  if (FlagStr == "Fail" )
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }

  document.all('AppNo').value = "";		
  document.all('CertificateId').value = "";	
  document.all('CertificateIdName').value = "";	  
  document.all('DetailIndexName').value = "";	
  document.all('DetailIndexID').value = "";
  document.all('BusinessNo').value = "";
  document.all('ReasonType').value = "";
  document.all('ReasonTypeName').value = "";
  document.all('DetailReMark').value = "";    
  document.all('AppDate').value = "";      
  document.all('Applicant').value = "";

  queryResultGrid();

 }
 catch(ex){}

}