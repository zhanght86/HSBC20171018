var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 
function LCInsureAccQuery()
{

  initLcInsureAccGrid();
  
  var sqlid826091902="DSHomeContSql826091902";
var mySql826091902=new SqlClass();
mySql826091902.setResourceName("sys.GpsaAccQuerySql");//指定使用的properties文件名
mySql826091902.setSqlId(sqlid826091902);//指定使用的Sql的id
mySql826091902.addSubPara(tPolNo);//指定传入的参数
var strSQL=mySql826091902.getString();

//	var strSQL = "select a.InsuAccNo,"
//		+" (select d.InsuAccName from LMRiskToAcc d where d.InsuAccNo = a.InsuAccNo and d.riskcode=a.riskcode),"
//		+" a.AccFoundDate,a.BalaDate,a.InsuAccBala "
//		+" from LCInsureAcc a where PolNo='"+  tPolNo +"'";
  				 	
 
  
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    
  
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = LCInsureAccGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
	
}

function LCInsureAccClassQueryb()
{
 
	initLCInsureAccClassGrid();
	
	var sqlid826092003="DSHomeContSql826092003";
var mySql826092003=new SqlClass();
mySql826092003.setResourceName("sys.GpsaAccQuerySql");//指定使用的properties文件名
mySql826092003.setSqlId(sqlid826092003);//指定使用的Sql的id
mySql826092003.addSubPara(tPolNo);//指定传入的参数
mySql826092003.addSubPara(cInsuAccNo);//指定传入的参数
var strSQL=mySql826092003.getString();
  
//	var strSQL = "select a.InsuAccNo,a.PayPlanCode,"
//		+"(select d.PayPlanName from LMRiskAccPay d where d.InsuAccNo = a.InsuAccNo and d.PayPlanCode = a.PayPlanCode),"
//		+"a.OtherNo,a.OtherType,a.AccFoundDate,a.BalaDate,a.AccAscription,a.InsuAccBala "
//		+" from LCInsureAccClass a"
//		+" where PolNo='"+tPolNo +"'" + " and InsuAccNo='" + cInsuAccNo +"'";
  				 	
 

 turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage1.strQueryResult) {
    
 
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage1.pageDisplayGrid = LCInsureAccClassGrid;    
          
  //保存SQL语句
  turnPage1.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage1.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  
	
}


function LCInsureAccClassQuery(){
	
	var tSel = LCInsureAccGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先在帐户查询信息中选择一条记录." );
	else
	{ divLCPol1.style.display=""
	  cInsuAccNo = LCInsureAccGrid.getRowColData( tSel - 1, 1 );
		try
		{ 
		  LCInsureAccClassQueryb();
		  
		}
		catch(ex)
		{
			alert( ex );
		}
	}
	
}

function GoBack(){
	
	top.opener.window.focus();
	
	top.window.close();
	
	
	
}

function LCInsureAccTrace(){
	/*var cInsuAccNo="1"
	var cPayPlanCode="2"
	window.open("./GpsLCInsureAccTraceQuery.jsp?PolNo="+ tPolNo + "&" +  "InsuAccNo=" + cInsuAccNo + "&" + "PayPlanCode=" + cPayPlanCode);*/
	var arrReturn = new Array();
	var tSel = LCInsureAccClassGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先在子帐户查询信息中选择一条记录." );
	else
	{
		var cInsuAccNo = LCInsureAccClassGrid.getRowColData( tSel - 1, 1 );
		var cPayPlanCode =  LCInsureAccClassGrid.getRowColData( tSel - 1, 2 );
		if(cInsuAccNo!="")
		{   try
		    {
			     window.open("./GpsLCInsureAccTraceQuery.jsp?PolNo="+ tPolNo + "&" +  "InsuAccNo=" + cInsuAccNo + "&" + "PayPlanCode=" + cPayPlanCode,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		  
		    }
		    catch(ex)
		    {
			    alert( "没有发现父窗口的afterQuery接口。" + ex );
		    }

    }
   else{ alert( "请先查询子帐户查询信息." ); return;}

	}  
}



function AccValueCal()
{
	
	var showStr = "正在进行帐户价值计算，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 	  
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  
	document.all("PolNo").value=tPolNo;
    //alert(document.all("PolNo").value);
        	
	fm.action = "./GpsaAccQuerySave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit();


}



function afterSubmit( FlagStr, content,dAcc,dInterest )
{
    showInfo.close();
	//alert(document.all("PolNo").value);
    if (FlagStr == "Fail" )
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
              
      	document.all("AccValue").value=dAcc;
		document.all("Interest").value=dInterest;	

    }
}