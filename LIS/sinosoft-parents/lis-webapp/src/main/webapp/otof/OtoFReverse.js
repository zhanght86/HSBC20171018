var showInfo;
var turnPage = new turnPageClass(); 

function easyQuery()
{
	initCodeGrid();
	document.all('AccountingDate').value='';
	var strSQL="";
/*	var sqlPart=" ";
	if(document.all('ManageCom').value=='')
	{
		sqlPart=" and ManageCom like '"+document.all('ComCode').value+"%' ";
	}

   strSQL="select BatchNo,MatchID,AccountCode,AccountSubCode,VoucherID,RiskCode,EnteredDR,EnteredCR,ManageCom,TransDate,Polno,Bussno,AccountingDate,VoucherType from ofinterface where 1=1 and (ReversedStatus='0') and VoucherFlag<>'NA' and VoucherID<>'-1' and VoucherDate is not null"
   		+ getWherePart('VoucherType','VoucherType')
   		+ getWherePart('ManageCom','ManageCom','like')
   		+ getWherePart('TransDate','TransDate')
   		+ getWherePart('VoucherID','VoucherID')
   		+ getWherePart('PolNo','PolNo')
   		+ getWherePart('BussNo','BussNo')
   		+ sqlPart
   		+" order by BatchNo,MatchID,polno,bussno,RecordID ";*/
   var mySql1 = new SqlClass();
	mySql1.setResourceName("otof.OtoFReverseSql");
	mySql1.setSqlId("OtoFReverseSql1");
	mySql1.addSubPara(window.document.getElementsByName(trim('VoucherType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('TransDate'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('VoucherID'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('PolNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('BussNo'))[0].value);
	mySql1.addSubPara(document.all('ComCode').value);
	strSQL = mySql1.getString();
　　turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有相关财务凭证信息！");
    return "";
  }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CodeGrid;    
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;		
}

function OtoFReverse()
{
	if(fm.AccountingDate.value=="")
	  {
	  	    alert("请您录入冲销日期！");
		    return false;	
	  }
	  if(fm.Reason.value == "" || fm.Reason.value == "null")
	  {
		    alert("请您录入凭证冲销原因！");
		    return false;
	  }
	  if (confirm("您确实想冲销该财务凭证吗?"))
	  {
			  var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  document.all('fmAction').value = "Reverse";
			  document.getElementById("fm").submit(); 
	  }
}

function OtoFReverseAll()
{
	 if(fm.VoucherType.value==""&&fm.ManageCom.value==""&&fm.TransDate.value==""&&fm.VoucherID.value==""&&fm.PolNo.value==""&&fm.BussNo.value=="")
	  {
	  	alert("请选择冲销条件！");
	  	return false;
	  }
	if(fm.AccountingDate.value=="")
	  {
	  	    alert("请您录入冲销日期！");
		    return false;	
	  }
	  if(fm.Reason.value == "" || fm.Reason.value == "null")
	  {
		    alert("请您录入凭证冲销原因！");
		    return false;
	  }
	  if (confirm("您确实想按照所选条件冲销财务凭证吗?"))
	  {
			  var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  document.all('fmAction').value = "ReverseAll";
			  document.getElementById("fm").submit(); 
	  }
}

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
