//
//程序名称：BankDataQuery.js
//程序功能：财务报盘数据查询
//创建日期：2004-10-20
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();

//查询报盘数据
function easyQuery()
{
	if(document.all('PolNo').value=="")
	{
		if(document.all('StartDate').value=="")
		{
			alert("请选择开始时间");
			return false;
			}
		
		if(document.all('EndDate').value=="")
		{
			alert("请选择终止时间");
			return false;
			}
	}
	if(document.all('ManageCom').value=="")
	{
		alert("请选择管理机构");
		return false;
		}
	// 书写SQL语句
	var strSQL = "";
	if(_DBT==_DBO){
//		strSQL = "select otherno,riskcode,accname,"
//			 + "(select trim(insuredname) from lcpol where contno = a.otherno and mainpolno=polno and rownum=1),startpaydate,"
//			 + "(select count(1) from lysendtobank where paycode = a.getnoticeno)+(select count(1) from lysendtobankb where paycode = a.getnoticeno),getnoticeno "
//			 + "from ljspay a "
//			 + "where othernotype = '2' and bankaccno is not null "
//			 + getWherePart('otherno','PolNo')
//			 + getWherePart('managecom','ManageCom','like')
//			 + getWherePart('startpaydate','StartDate','>=')
//			 + getWherePart('startpaydate','EndDate','<=')
//			 + " order by 1";
		
		var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value; 
		var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value; 
		var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value; 
		var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value; 
		 
		  var sqlid1="BankDataQuerySql1";
		  var mySql1=new SqlClass();
		  mySql1.setResourceName("bank.BankDataQuerySql");
		  mySql1.setSqlId(sqlid1);//指定使用SQL的id
		  mySql1.addSubPara(PolNo);//指定传入参数
		  mySql1.addSubPara(ManageCom);//指定传入参数
		  mySql1.addSubPara(StartDate);//指定传入参数
		  mySql1.addSubPara(EndDate);//指定传入参数
		  var strSQL = mySql1.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select otherno,riskcode,accname,"
//			 + "(select trim(insuredname) from lcpol where contno = a.otherno and mainpolno=polno limit 0,1),startpaydate,"
//			 + "(select count(1) from lysendtobank where paycode = a.getnoticeno)+(select count(1) from lysendtobankb where paycode = a.getnoticeno),getnoticeno "
//			 + "from ljspay a "
//			 + "where othernotype = '2' and bankaccno is not null "
//			 + getWherePart('otherno','PolNo')
//			 + getWherePart('managecom','ManageCom','like')
//			 + getWherePart('startpaydate','StartDate','>=')
//			 + getWherePart('startpaydate','EndDate','<=')
//			 + " order by 1";
		
		var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value; 
		var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value; 
		var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value; 
		var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value; 
		 
		  var sqlid2="BankDataQuerySql2";
		  var mySql2=new SqlClass();
		  mySql2.setResourceName("bank.BankDataQuerySql");
		  mySql2.setSqlId(sqlid2);//指定使用SQL的id
		  mySql2.addSubPara(PolNo);//指定传入参数
		  mySql2.addSubPara(ManageCom);//指定传入参数
		  mySql2.addSubPara(StartDate);//指定传入参数
		  mySql2.addSubPara(EndDate);//指定传入参数
		  var strSQL = mySql2.getString();
		
	}
	
  
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败，请确认：\n①该保单缴费已成功；\n②该保单为非银行转账件；\n③该保单未到交费期；\n④该核对保单号；");
    return false;
  }
  
  fm.bnteasyQuery.disabled = true;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CodeGrid;    
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           		 = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function easyPrint()
{
	fm.bntPrint.disabled=true;
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function QueryDetail()
{
	var tSel = CodeGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录。" );
		return;
	}
	else
	{
		fm.bntQueryDetail.disabled=true;
		var tGetNoticeNo = CodeGrid.getRowColData(tSel-1,7);
		var tPolNo = CodeGrid.getRowColData(tSel-1,1);
		window.open("./BankDataQueryDetailMain.jsp?GetNoticeNo=" + tGetNoticeNo + "&PolNo=" + tPolNo);
	}	
}

