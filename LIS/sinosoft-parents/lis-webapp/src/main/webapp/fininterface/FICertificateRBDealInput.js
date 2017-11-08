 
//创建日期： 
//创建人   jw
//更新记录：  更新人    更新日期     更新原因/内容
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;





function queryRBResultGrid()
{
	initResultGrid();
	var strSQL = ""; 
	/**
	strSQL = "select a.businessno,a.certificateid,sum(case when b.finitemtype = 'C' then b.summoney else 0 end),a.accountdate,a.batchno,b.managecom, (select max(c.voucherno) from fidatatransgather c where c.batchno = a.batchno and c.accountdate =a.accountdate and c.certificateid in (select d.codealias from ficodetrans d where d.codetype = 'BigCertificateID' and d.code = a.certificateid ) and c.managecom = b.managecom ) from fiaboriginaldatatemp a,fidatatransresult b where a.aserialno = b.aserialno and a.certificateid = '" + 
	document.all('CertificateId').value + "' and a.businessno = '" + document.all('BusinessNo').value + "' group by a.businessno,a.certificateid,a.accountdate,a.batchno,b.managecom";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FICertificateRBDealInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("FICertificateRBDealInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(document.all('CertificateId').value);//指定传入的参数
		mySql1.addSubPara(document.all('BusinessNo').value);//指定传入的参数
		strSQL= mySql1.getString();
	
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	if (!turnPage.strQueryResult)
	{
	   return false;
	}

	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 30 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = RBDealResultGrid;
	//保存SQL语句
		
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	

	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
}

function ImportData1()
{
	
    fm.action="./FinReAppSinggleDataExcel.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交	
	
}


function ImportData2()
{
	
    fm.action="./FinReAppSinggleResultExcel.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交	
	
}

function ReDistill()
{
    var showStr="正在提取数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FIDistillDataSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交	
	
}

function ReCertificate()
{

    var showStr="正在生成凭证，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FICertificateDataSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交		
	
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
  queryRBResultGrid();

 }
 catch(ex){}

}