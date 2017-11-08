var mDebug="1";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
window.onfocus=myonfocus;
var ImportPath;
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


function submitForm()
{
	    if(document.all('FeeSum').value=="")
    {
    	alert("请输入交费总金额");
	  	return ;
    	}
    	if(document.all('BankCode').value=="")
    	{
    		alert("请选择收款银行");
    		return ;
    		}
		if(document.all('FileName').value=="")
	  {
	  	alert("请选择需要上传的文件");
	  	return ;
	  }
	  var i = 0;
		var ImportFile = document.all('FileName').value.toLowerCase();
		//应客户要求增加对模板格式的限制，zy
		var j = ImportFile.indexOf(".xls");
		if(j==-1)
		{
			alert("请采用excle模板");
		  return ;
		}
		
		
		getImportPath();
   	   document.all('conf').disable=true;
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();   	
		fm.action = "./YBTFinfeeSureSave.jsp?ImportPath="+ImportPath+"&ImportFile="+ImportFile;
	  document.getElementById("fm").submit(); //提交


}


function getImportPath ()
{
		// 书写SQL语句
	var strSQL = "";

	//strSQL = "select SysvarValue from ldsysvar where sysvar ='YBTSavePath'";
	//alert("getImportPath");
	
	var mysql=new SqlClass();
	mysql.setResourceName("finfee.YBTFinfeeSureInputSql");
	mysql.setSqlId("YBTSavePath");
			 
	turnPage.strQueryResult  = easyQueryVer3(mysql.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	alert("未找到上传路径，请到ldsysvar表查看sysvar ='YBTSavePath'的数据记录信息");
    return;
	}
	
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  ImportPath = turnPage.arrDataCacheSet[0][0];
  
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
}