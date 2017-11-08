//该文件中包含客户端需要处理的函数和事件

//程序名称：WorkAchieveStat.js
//程序功能：工作绩效统计清单
//创建日期：2005-11-29 17:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容 


var showInfo;
var turnPage = new turnPageClass();
var tTurnPage = new turnPageClass();


//提交，保存按钮对应操作
function printBill()
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
  var StartDate = fm.StartDate.value;
  var EndDate = fm.EndDate.value;
  var ManageCom = fm.ManageCom.value;
  if(ManageCom == "")
  {
  	    showInfo.close();
    	alert("请选择管理机构!");
    	return;
  }
  if(StartDate == "" || EndDate == "")
  {
  	    showInfo.close();
    	alert("请输入起始日期和结束日期!");
    	return;
  }
  if (!isDate(StartDate)&&!isDateN(StartDate)||!isDate(EndDate)&&!isDateN(EndDate))
  {
  	    showInfo.close();
		alert("非有效的日期格式！应为(YYYY-MM-DD)或者(YYYYMMDD)");
		return ;
  } 
  var startValue=StartDate.split("-");
  var dateStartDate = new Date(startValue[0],startValue[1]-1,startValue[2]);
  var endValue=EndDate.split("-");
  var dateEndDate = new Date(endValue[0],endValue[1]-1,endValue[2]);  
  if(dateStartDate.getTime() > dateEndDate.getTime())  
  {
  	    showInfo.close();
    	alert("统计起期不能晚于统计止期！");
    	return;
  }
  else
  {
  	    //
		fm.fmtransact.value = "PRINT";
		fm.target = "f1print";		
		fm.submit();
		showInfo.close();				
  }
}



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
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
//此函数废弃不用，改为在init文件中调用后台初始化
function initDate()
{
        var today = new Date();
	    var thisDay = 25;
	    var preDay = 26;
    	var tYear = today.getYear();
    	var preYear = tYear;
    	var thisMonth = today.getMonth()+1;
	    var preMonth = thisMonth-1;
	    if(thisMonth == 1)
	    {
	       preMonth = thisMonth;
	       preDay = 1;
	    }
	    if(thisMonth == 12)
	    {
	       thisDay = 31;
	    }
        document.all('StartDate').value = preYear+"-"+preMonth+"-"+preDay;
        document.all('EndDate').value = tYear+"-"+thisMonth+"-"+thisDay;  	 	

}
//进入用户信息查询页面
function queryUsr()
{
	if(document.all('ManageCom').value=="")
	{
		 alert("请先选择管理机构！");
		 return;
	}
	var newWindow = window.open("../sys/UsrCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"UsrCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
//返回用户代码
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
    fm.UsrCode.value = arrResult[0][0];
  }
}
function initEdorType()
{
    	var i,j,m,n;
	    var returnstr;
//	    var strSQL = "select edorcode,edorname from lmedoritem where appobj in('I','B') order by edorcode";
	    
		var sqlid1="WorkAchieveStatSql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.WorkAchieveStatSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		var strSQL=mySql1.getString();
	    
	    
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);  
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
  				        }
     		        }
  		        }
  	            else
  	            {
  		          	alert("查询失败!!");
  			        return "";
  		        }
             }
         }
         else
         {
	         alert("查询失败!");
	         return "";
         }
         fm.EdorType.CodeData = returnstr;
         return returnstr;
	
}