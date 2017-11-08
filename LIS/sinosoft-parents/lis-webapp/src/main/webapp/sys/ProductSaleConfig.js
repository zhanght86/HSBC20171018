var showInfo;
 var turnPage = new turnPageClass();
 var turnPage1 = new turnPageClass();
 var mAllType = '2';
//是否为数字
function isdigit(c){
  return(c>='0'&&c<='9');
}

function submitForm()
{
	document.all('hideaction').value="INSERT";
if(verifyInput()) 
  {	  	
  	var i = 0;
	  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//	  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  var name='提示';   //网页名称，可为空; 
	  var iWidth=550;      //弹出窗口的宽度; 
	  var iHeight=250;     //弹出窗口的高度; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

	  document.getElementById("fm").submit(); //提交
	}
}

function addData()
{
	
	if(!verifyInput())
	{
		return false;
	}
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	document.all('hideaction').value='';
	showInfo.close();
	
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    initForm();   
  }

}




function queryRiskConfig()
{
	var tRiskCode = document.all('RiskCode').value;
	var tSaleChnl = document.all('SaleChnl').value;
	var tComGroup = document.all('ComGroup').value;
	
//	var tSQL = "select riskcode,salechnl,comgroup,startPolApplyDate,EndPolApplyDate,StartScanDate,StartScanTime,EndScanDate,EndScanTime,Operator,ModifyDate,ModifyTime from LDRiskRule "
//	         + " where 1=1 "
  if(tRiskCode!=null&&tRiskCode!='')
  {
  	tSQL = tSQL + " and riskcode ='"+tRiskCode+"'";
  }  
  if(tSaleChnl!=null&&tSaleChnl!='')
  {
  	tSQL = tSQL + " and salechnl ='"+tSaleChnl+"'";
  }          
   if(tComGroup!=null&&tComGroup!='')
  {
  	tSQL = tSQL + " and comgroup ='"+tComGroup+"'";
  }          
                   
              
 //   tSQL = tSQL + " order by riskcode,salechnl,comgroup";
    
        var sqlid1="ProductSaleConfigSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.ProductSaleConfigSql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(tRiskCode);//指定传入参数
	 	mySql1.addSubPara(tSaleChnl);
	 	mySql1.addSubPara(tComGroup);
	 	var tSQL = mySql1.getString();
    
  turnPage1.queryModal(tSQL,ProductSaleConfigGrid);
}

function getConfigDetail(parm1,parm2)
{
	var tSelNo = ProductSaleConfigGrid.getSelNo() - 1;
	var RiskCode = ProductSaleConfigGrid.getRowColData(tSelNo, 1);
	//alert(RiskCode);
	var SaleChnl = ProductSaleConfigGrid.getRowColData(tSelNo, 2);
	var ComGroup = ProductSaleConfigGrid.getRowColData(tSelNo, 3);
	var PolApplyDateStart = ProductSaleConfigGrid.getRowColData(tSelNo, 4);
	var PolApplyDateEnd = ProductSaleConfigGrid.getRowColData(tSelNo, 5);
	var ScanDateStart = ProductSaleConfigGrid.getRowColData(tSelNo, 6);
	var ScanTimeStart = ProductSaleConfigGrid.getRowColData(tSelNo, 7);
	var ScanDateEnd = ProductSaleConfigGrid.getRowColData(tSelNo, 8);
	var ScanTimeEnd = ProductSaleConfigGrid.getRowColData(tSelNo, 9);
	
	document.all('RiskCode').value = RiskCode;
	document.all('SaleChnl').value = SaleChnl;
	document.all('ComGroup').value = ComGroup;
	document.all('PolApplyDateStart').value = PolApplyDateStart;
	document.all('PolApplyDateEnd').value = PolApplyDateEnd;
	document.all('ScanDateStart').value = ScanDateStart;
	document.all('ScanTimeStart').value = ScanTimeStart;
	document.all('ScanDateEnd').value = ScanDateEnd;
	document.all('ScanTimeEnd').value = ScanTimeEnd;
  
//  var tSQL = "select riskname from lmriskapp where riskcode='"+RiskCode+"' ";
  
    var sqlid2="ProductSaleConfigSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("sys.ProductSaleConfigSql");
	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	mySql2.addSubPara(tRiskCode);//指定传入参数
	var tSQL = mySql2.getString();
  
  var arrResult = easyExecSql(tSQL, 1);
  if(arrResult!=null)
  {
  	document.all('RiskCodeName').value = arrResult[0][0];
  }
  
//  tSQL = "select codename from ldcode where codetype='salechnl' and code='"+SaleChnl+"' ";
  
    var sqlid3="ProductSaleConfigSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("sys.ProductSaleConfigSql");
	mySql3.setSqlId(sqlid3); //指定使用SQL的id
	mySql3.addSubPara(SaleChnl);//指定传入参数
	tSQL = mySql3.getString();
  
   arrResult = easyExecSql(tSQL, 1);
  if(arrResult!=null)
  {
  	document.all('SaleChnlName').value = arrResult[0][0];
  }
  
//  tSQL = " select comgroupname from ldcomgroup where comgroup='"+ComGroup+"' ";
  
    var sqlid4="ProductSaleConfigSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("sys.ProductSaleConfigSql");
	mySql4.setSqlId(sqlid4); //指定使用SQL的id
	mySql4.addSubPara(ComGroup);//指定传入参数
	tSQL = mySql4.getString();
  
   arrResult = easyExecSql(tSQL, 1);
  if(arrResult!=null)
  {
  	document.all('ComGroupName').value = arrResult[0][0];
  }
  
}

function queryTrace()
{
	var tRiskCode = document.all('RiskCode').value;
	var tSaleChnl = document.all('SaleChnl').value;
	var tComGroup = document.all('ComGroup').value;
	
	if(tRiskCode==null||trim(tRiskCode)=='')
	{
		alert('请输入险种编码');
		return false;
	}
	
	if(tSaleChnl==null||trim(tSaleChnl)=='')
	{
		alert('请输入销售渠道');
		return false;
	}
		if(tComGroup==null||trim(tComGroup)=='')
	{
		alert('请输入机构组编码');
		return false;
	}
	
//		var tSQL = "select riskcode,salechnl,comgroup,startPolApplyDate,EndPolApplyDate,StartScanDate,StartScanTime,EndScanDate,EndScanTime,Operator,modifydate,modifytime,bakDate,bakTime from LDRiskRuleB "
//              + " where riskcode='"+tRiskCode+"' "
//              + " and salechnl='"+tSaleChnl+"' "
//              + " and ComGroup = '"+tComGroup+"' "
//              + " order by serialno,riskcode,salechnl,comgroup";
		
		var sqlid5="ProductSaleConfigSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("sys.ProductSaleConfigSql");
		mySql5.setSqlId(sqlid5); //指定使用SQL的id
		mySql5.addSubPara(tRiskCode);//指定传入参数
		mySql5.addSubPara(tSaleChnl);
		mySql5.addSubPara(tComGroup);
		var tSQL = mySql5.getString();
		
  turnPage.queryModal(tSQL,ProductSaleTraceGrid);

}