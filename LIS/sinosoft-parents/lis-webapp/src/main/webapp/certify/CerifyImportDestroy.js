var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CerifyImportDestroyInputSql";
//导入信息
function getin(){	
	//明确登录机构只能为二位或者四位机构代码
	
	if(!(trim(fmload.dManageCom.value).length==4 || trim(fmload.dManageCom.value).length==2 ))
	{
		alert("请以分公司或者总公司机构登陆");
		return false;
	}
    getImportPath();
    fmload.ImportPath.value = ImportPath;
    
    if (fmload.FileName.value==null||fmload.FileName.value=="")
    {
       alert ("请输入文件地址!");	
       return false;
    }
    
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fmload.action = "./CerifyImportDestroySave.jsp?ImportPath="+fmload.ImportPath.value;
    fmload.submit(); //提交

}

//jinsh------导入的执行完后调这个-------2007-07-02
function afterSubmitDiskInput( FlagStr,content )
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

function getImportPath ()
{
	  var strSQL = "";
	  //strSQL = "select SysvarValue from ldsysvar where sysvar ='CertifyImportPath'";
	  var queryhelp="1";
	  strSQL = wrapSql(tResourceName,"querysqldes2",[queryhelp]);
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult) 
	  {
		    alert("未找到上传路径");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];	  
	  //alert(ImportPath);
	  
	  //ImportPath="D:\\log\\";
}

//单证状态查询
function certifyQuery()
{
    if(verifyInput() == false)
    {
	  return;
    }
  CardImportInfoGrid.clearData();//清除表格中所有数据
  
  var tCom=fmload.dManageCom.value;

  /*var strSQL="select a.certifycode,(select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+" a.startno, a.endno, a.sumcount, a.handler, a.operator, decode(a.state,'Y','成功','N','失败'),"
		+" a.makedate, a.maketime,a.managecom,a.Reason "
 		+" from LZCardImport a where 1 = 1 and a.operateflag='2'"//导入类型:2、销毁导入
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.State', 'State')
		+ getWherePart('a.takebackno', 'SerialNo')
 		+ getWherePart('a.makedate', 'StartDate','>=')
 		+ getWherePart('a.makedate', 'EndDate','<=');
 		
 strSQL = strSQL+" and a.managecom like '"+tCom+"%' "
	 			+" order by a.certifycode, a.startno, a.makedate, a.maketime" ;*/
 var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('State').value,document.all('SerialNo').value
                                      ,document.all('StartDate').value,document.all('EndDate').value,tCom]);
	
   	//turnPage2.pageLineNum = 10;
   	turnPage2.queryModal(strSQL, CardImportInfoGrid);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardImportInfoGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

//销毁批次信息查询
function SerialQuery()
{
    if(verifyInput() == false)
    {
	  return;
    }
  SerialInfoGrid.clearData();//清除表格中所有数据
  initCardImportInfoGrid();
  
  var tCom=fmload.dManageCom.value;

  /*var strSQL="select takebackno,operator,makedate,min(maketime) "
 		+" from LZCardImport a where 1 = 1 and a.operateflag='2'"//导入类型:2、销毁导入
 		+" and a.managecom like '"+tCom+"%' "
 		+ getWherePart('a.makedate', 'StartDate1','>=')
 		+ getWherePart('a.makedate', 'EndDate1','<=');
	 
	   strSQL = strSQL+" group by takebackno, operator, makedate order by a.makedate";*/
	   var strSQL = wrapSql(tResourceName,"querysqldes3",[tCom,document.all('StartDate1').value,document.all('EndDate1').value]);
	
   	//turnPage2.pageLineNum = 10;
   	turnPage2.queryModal(strSQL, SerialInfoGrid);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(SerialInfoGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何批次信息！");
   		return false;
   	}
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+CardImportInfoGrid.mulLineCount);
   	if(CardImportInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}	
   	
	easyQueryPrint(2,'CardImportInfoGrid','turnPage2');	
}

function showStatistics() 
{  
   document.all("SerialNo").value = SerialInfoGrid.getRowColData(SerialInfoGrid.getSelNo() - 1, 1);  
   initCardImportInfoGrid();
}