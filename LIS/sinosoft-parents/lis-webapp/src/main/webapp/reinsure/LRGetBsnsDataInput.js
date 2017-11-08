//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量

function download()
{
	if (verifyInput() == false)
    return false;
  var i = 0;
  var showStr=""+"正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
	fm.target = "f1print";
	fm.all('op').value = 'download';
	fm.submit();
	showInfo.close();
}

function queryClick()
{
	// 书写SQL语句
	
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRGetBsnsDataInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRGetBsnsDataInputSql100");//指定使用的Sql的id
		mySql100.addSubPara(fm.all("StartDate").value);//指定传入的参数
	    mySql100.addSubPara(fm.all("EndDate").value);//指定传入的参数
	var sqlStr=mySql100.getString();
	
	/**
	var sqlStr="select (case when a.conttype='1' then a.contno else a.grpcontno end),"
	+ " b.dutycode,a.insuredno,a.cvalidate,(select max(IDNo) from lcinsured where insuredno=a.insuredno),InsuredAppAge,"
	+ " insuredsex,a.occupationtype,0,a.InsuredBirthday,a.riskcode,b.amnt,b.prem ,"
	+ " (case when conttype='1' then (select last_year_reser01 from ReportActu_temp  where i_info_cntr_no=a.contno and pol_code=a.riskcode) "
	+ " Else (select last_year_reser01 from ReportActu_temp  where ipsn_no=a.contno and pol_code=a.riskcode) end),"
	+ " (case when appflag='1' then '有效' else '无效' end),a.signdate from lcpol a,lcduty b "
	+ " where a.polno=b.polno and appflag in ('1','4') and a.signdate between '"+fm.all("StartDate").value+"' and '"+fm.all("EndDate").value+"' "
	//+ " and riskcode in ()" 
	+ " order by signdate "
	;
	//alert(sqlStr);
	*/
	turnPage.queryModal(sqlStr,BusynessGrid);
}

/**********************************/
/*日结确认清单批的数据导出
/**********************************/
function exportExcel()
{
	fm.target= "fraSubmit";
	fm.action="./LRBsnzDataExportSave.jsp";
	fm.submit(); //提交
  
}

function afterCodeSelect( cCodeName, Field )
{
}
