//程序名称：PEdorFormPrint.js
//程序功能：保全报表批量打印控制台
//创建日期：2006-09-25 09:10:00
//创建人  ：wangyan
//更新记录：  更新人    更新日期      更新原因/内容
//该文件中包含客户端需要处理的函数和事件


var showInfo;
var turnPage = new turnPageClass();


//报表类型选择处理函数
function initBillType(cObjCode,cObjName)
{
	var ChnlType = 'reporttype';	
	//var ChnlType = fm.ChnlType.value;
	/*
    if(ChnlType == null||ChnlType == "")
    {
     	alert("请先选择渠道，再选择报表！");
     	fm.SaleChnl.focus();
   	    return;
    }
    */
    return showCodeList(ChnlType,[cObjCode,cObjName],[0,1],null,null,null,null,'230');
}
function onKeyUpBillType(cObjCode,cObjName)
{
	
		var ChnlType = 'reporttype';
    /*
    if(ChnlType == null||ChnlType == "")
    {
    	alert("请先选择渠道，再选择报表！");
    	fm.SaleChnl.focus();
    	return;
    }
    */
    return showCodeListKey(ChnlType,[cObjCode,cObjName],[0,1],null,null,null,null,'230');
}

//根据通知书类型返回其特定的sql查询语句
function getSql(tNoticeType)
{
   var strSql = "";
   switch(tNoticeType)
   {
   	  case "":
   	  default:    
//   		  		strSql = "select (select codename from loreportcode where code=a.reportcode),managecom," +
//   	  		"(case when (select paramvalue from loreportparam where prtseq=a.prtseq and paramname='Chanel')='1' then " +
//   	  		"'个人' else '代理' end),(select paramvalue from loreportparam where prtseq=a.prtseq and paramname='StartDate')," +
//   	  		"(select paramvalue from loreportparam where prtseq=a.prtseq and paramname='EndDate')," +
//   	  		"(case when (select paramvalue from loreportparam where prtseq=a.prtseq and paramname='DateType')='1' then '确认日期'" +
//   	  		" else '生效日期' end),prtflag,prtseq from LOREPORTMANAGER a where 1=1"
//   	  						+ getWherePart('ReportCode', 'BillType', '=')
//   	  						+ getWherePart('ManageCom', 'ManageCom', 'like') 
//   	  						+ getWherePart('MakeDate', 'StartDate', '>=')
//   	  						+ getWherePart('MakeDate', 'EndDate', '<=')
//   	  						+" order by prtseq,modifydate,modifytime";
    var sqlid1="PEdorFormPrintSql1";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.PEdorFormPrintSql"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(window.document.getElementsByName("BillType")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("ManageCom")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("StartDate")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("EndDate")[0].value);//指定传入的参数
    strSql=mySql1.getString();   	  
   	              break;
   }
   

   
   return strSql;
}

//查询可以打印的报表
function queryNotice()
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
  var BillType = fm.BillType.value;
  var ManageCom = fm.ManageCom.value;
  //var SaleChnl = fm.SaleChnl.value;  
  var RiskFlag = fm.RiskFlag.value;  
  var PayIntv = fm.PayIntv.value; 
  var Statistic = fm.Statistic.value; 
  var RiskCode = fm.RiskCode.value; 
  var Period = fm.Period.value;
  
  if(BillType == "")
  {
  	   showInfo.close();
  	   alert("请选择报表类型！");
  	   fm.BillType.focus();
  	   return;
  }
  if(StartDate == "" || EndDate == "")
  {
  	  showInfo.close();
    	alert("请输入起始日期和结束日期!");
    	return;
  }
  if(!checkDate(StartDate)||!checkDate(EndDate))
  {
  	showInfo.close();
  	alert("非有效的日期格式！");
    return;
  }
  if(RiskFlag == "Yes"){
  	  if(RiskCode == ""){
  	  	  showInfo.close();
  	      alert("请选择险种代码！");
  	      fm.RiskCode.focus();
  	      return;
  	  }
  		if(PayIntv == ""){
  	  	  showInfo.close();
  	      alert("请选择缴费方式！");
  	      fm.PayIntv.focus();
  	      return;
  	  }
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
  var tFlag = fm.Flag.value;
  if(tFlag=="1")
  {
        var DATEDIFF = 31;
        if(startValue[1] == 11 && endValue[1] == 12)
        {
        	DATEDIFF = 36;
        }
        var day = dateDiff(dateStartDate,dateEndDate,"D");
        if(day>DATEDIFF)
        {   
        	showInfo.close();		 
	 				alert("对于当月报表，统计起期和止期只能相差一个月！");
	 				return ;        	
        }
  }
  if(fm.CTFlag.value == "1")
  {
  	if(fm.DateType.value == "")
  	{
  	   showInfo.close();
  	   alert("请选择统计起止期的类型！");
       fm.DateType.focus();
  	   return ;
  	}
    if(fm.DateType.value != "1"&&fm.DateType.value != "2")
    {
     	showInfo.close();
    	alert("统计起止期的类型有误！");
    	fm.DateType.focus();
    	return ;
    }
  }

	  strSql = getSql(BillType);
    var brr = easyExecSql(strSql);
    showInfo.close();
    if(brr)
    {
    	initNoticeGrid(BillType);
     	turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
     	alert("没有要打印的报表！ ");
      initNoticeGrid(BillType);
     	return ;
    }
}
// 返回时查询通知书任务列表(之所以改为这个函数名，是为了打印完返回时重新查询任务队列；之所以另加函数而不是调用原来的函数，是因为返回后查不到记录时弹出对话框会对用户造成不便)
function easyQueryClick()
{
		var BillType = fm.BillType.value;
		var strSql = "";
		if(BillType == null || BillType == "")
		{
			alert("请选择报表类型！");
			fm.BillType.focus();
			return;
		}
  	if(fm.StartDate.value == "" || fm.EndDate.value == "")
  	{
  	  alert("请输入起始日期和结束日期！");
    	return;
  	}	

		strSql = getSql(BillType);
    var brr = easyExecSql(strSql);
    if(brr)
    {
    	initNoticeGrid(BillType);
     	turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
      initNoticeGrid(BillType);
     	return ;
    }
}

//打印选择的报表记录
function printNotice()
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
  var tSel = NoticeGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  {
  	showInfo.close();
		alert( "请先选择一条记录!" );
  }
  else
  {
		var tFlag = NoticeGrid.getRowColData(tSel-1, 7);
		if( tFlag == 'N' )
		{
  		showInfo.close();
			alert( "报表生成还未结束，请稍后再试!" );
		}
		else
		{	
			var EdorNo = NoticeGrid.getRowColData(tSel-1, 8);
			fm.action = "../bq/PEdorFormF1PJ1.jsp?EdorNo=" + EdorNo;
  		fm.target="f1print";
  		fm.submit();
  		showInfo.close();
  	}
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
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

//初始化管理机构，最长截取六位
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
//	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(1,6)+"%%' order by comcode";
	    var strSQL = "";
	    var sqlid1="PEdorFormPrintSql2";
	    var mySql1=new SqlClass();
	    mySql1.setResourceName("bq.PEdorFormPrintSql"); //指定使用的properties文件名
	    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	    mySql1.addSubPara(comcode.substring(1,6));//指定传入的参数
	    strSQL=mySql1.getString();
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
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
	}
}
function isLeap(tYear)
{
	return (tYear%4)==0 ? ((tYear%100)==0?((tYear%400)==0?true:false):true):false;
}
function getMonthLength(tYear,tMonth)
{
    if(tMonth > 12 || tMonth < 1)
        return 0;
    var MONTH_LENGTH = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    var LEAP_MONTH_LENGTH = new Array(31,29,31,30,31,30,31,31,30,31,30,31);
   
    return isLeap(tYear) ? LEAP_MONTH_LENGTH[tMonth-1] : MONTH_LENGTH[tMonth-1];
}
function checkDate(tDate)
{
	var dateValue;
	var tYear;
	var tMonth;
	var tDay;
	if(isDate(tDate))
	{
		dateValue = tDate.split("-");
		tYear = eval(dateValue[0]);
		tMonth = eval(dateValue[1]);
		tDay = eval(dateValue[2]);
		if(tDay > getMonthLength(tYear,tMonth))
		{
			return false;
		}
		return true;
	}
	else if(isDateN(tDate))
	{
		dateValue = new Array();
        dateValue[0]=tDate.substring(0, 4);
        dateValue[1]=tDate.substring(4, 6);
        dateValue[2]=tDate.substring(6, 8);
		
		tYear = eval(dateValue[0]);
		tMonth = eval(dateValue[1]);
		tDay = eval(dateValue[2]);
		if(tDay > getMonthLength(tYear,tMonth))
		{
			return false;
		}
		return true;
	}
	else if(isDateI(tDate))
	{
		dateValue = tDate.split("/");
		tYear = eval(dateValue[0]);
		tMonth = eval(dateValue[1]);
		tDay = eval(dateValue[2]);
		if(tDay > getMonthLength(tYear,tMonth))
		{
			return false;
		}
		return true;
	}
	else
	{
		return false;
	}
}

/*
//初始化险种列表
function initRiskCode(SaleChnl)
{
	var i,j,m,n;
	var returnstr;
	var strSql = "";
	if(SaleChnl == "1")
	{
		strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' and riskprop in ('I','C','D','A') union select '000000','所有个人险种' from dual";
	}
    else if(SaleChnl == "3")
    {
		strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' and riskprop in ('Y','B','C','D') union select '000000','所有代理险种' from dual";
    }
    else 
    {
		strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' union select '000000','所有险种' from dual";
    }
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  //alert(strSql);
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  

  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
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
  //alert("returnstr:"+returnstr);		
  fm.RiskCode.CodeData = returnstr;
  return returnstr;
}
*/

/*
//根据报表类型显示其需要的检索条件
function afterCodeSelect( cCodeName, Field )
{
	  try	{
		    if( cCodeName == "bqformbank" || cCodeName == "bqformperson")	
		    {
		    	
		    	  var BillType = fm.all("BillType").value; 
		    	  if(BillType == "103" || BillType == "303")
		    	  {
		    		   divform.style.display = "";
		    		   divPayTitle.style.display = "";
		    		   divPayIntv.style.display = "";
		    		   fm.all('RiskFlag').value = "Yes";	
		    		   fm.all('Statistic').value = "0";
		    		   fm.all('Period').value = "0";	    	  	 
		    	  }
		    	  else if(BillType == "304" || BillType == "104")
		    	  {
		    		   divform.style.display = "";
		    		   divPayTitle.style.display = "";
		    		   divPayIntv.style.display = "";
		    		   fm.all('RiskFlag').value = "Yes";	
		    		   fm.all('Statistic').value = "0";
		    		   fm.all('Period').value = "1";	    	  	 
		    	  }
		    	  else if(BillType == "306" || BillType == "106")
		    	  {
		    		   divform.style.display = "";
		    		   divPayTitle.style.display = "none";
		    		   divPayIntv.style.display = "none";
		    		   fm.all('RiskFlag').value = "NO";	
		    		   fm.all('Statistic').value = "1";
		    		   fm.all('Period').value = "1";	    	  	 
		    	  }	    	  
		    	  else 
		          {
		    		 divPayTitle.style.display = "none";
		    		 divPayIntv.style.display = "none";
		    	     divform.style.display = "none";
		    	     fm.all('RiskFlag').value = "NO";
		         }
		         if(fm.BillType.value == "101" || fm.BillType.value == "301")
		         {
		        	fm.Flag.value = "1";//当月报表的统计
		         }
                 checkCT();//退保撤保需选择日期类型
		    }	  
	  }
	  catch( ex ) {
	  }
}
*/

/*
function checkCT()
{
	var tBillType = fm.BillType.value;
	var tCodeType = fm.ChnlType.value;
	if(tBillType!=null && tBillType!="")
	{
		var strSql = "select trim(othersign) from ldcode "
		              + " where codetype = '"+tCodeType+"' "
		              + " and code = '"+tBillType+"'";
		var brr = easyExecSql(strSql);
		if(brr)
		{
			//ldcode表中othersign:
			//为2的需要选择日期类型
			if(brr[0][0]=="2")
		    {
				divDateType.style.display = ''; 
				fm.CTFlag.value = "1";
	            fm.DateType.value = "2";
	            fm.DateTypeName.value = "生效日期";
				return;		    	
		    }
		}
	}
	divDateType.style.display = 'none';
	fm.CTFlag.value = "0";
}
*/

/*
//报表渠道选择处理函数
function showChnlType()
{
	var SaleChnl = fm.SaleChnl.value;
	var ChnlSel = fm.ChnlSel.value;
	if(SaleChnl!=ChnlSel)
	{
		fm.ChnlSel.value = SaleChnl;    //记录每次所选渠道，如果更改渠道则清空报表类型
	  fm.all('BillType').value = '';
	  fm.all('BillTypeName').value = '';		
	}
	switch(SaleChnl)
	{
		case "1":  fm.ChnlType.value = "bqformperson";
					break;
		case "2":  fm.ChnlType.value = "bqformgrp";
			    break;
		case "3":  fm.ChnlType.value = "bqformbank";
			    break;			                    
		default:   fm.ChnlType.value = ""; 
					break;
	}
	//initRiskCode(SaleChnl);
}
*/