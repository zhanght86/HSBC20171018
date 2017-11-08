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
	// 书写SQL语句
	var strSQL ="";
	if(_DBT==_DBO){
//		 strSQL = "select polno,(select trim(prtno) from lcpol where contno = a.polno and rownum=1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '扣款成功' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobank a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " union all "
//			 + "select polno,(select trim(prtno) from lcpol where contno = a.polno and rownum=1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '扣款成功' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobankb a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " order by senddate ";
		 
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		 
		  var sqlid1="BankDataQueryDetailSql1";
		  var mySql1=new SqlClass();
		  mySql1.setResourceName("bank.BankDataQueryDetailSql");
		  mySql1.setSqlId(sqlid1);//指定使用SQL的id
		  mySql1.addSubPara(GetNoticeNo);//指定传入参数
		  mySql1.addSubPara(PolNo);//指定传入参数
		  mySql1.addSubPara(GetNoticeNo);//指定传入参数
		  mySql1.addSubPara(PolNo);//指定传入参数
		  var strSQL = mySql1.getString();
		 
	}else if(_DBT==_DBM){
//		 strSQL = "select polno,(select trim(prtno) from lcpol where contno = a.polno limit 1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '扣款成功' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobank a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " union all "
//			 + "select polno,(select trim(prtno) from lcpol where contno = a.polno limit 1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '扣款成功' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobankb a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " order by senddate ";
		 
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		 
		  var sqlid2="BankDataQueryDetailSql2";
		  var mySql2=new SqlClass();
		  mySql2.setResourceName("bank.BankDataQueryDetailSql");
		  mySql2.setSqlId(sqlid2);//指定使用SQL的id
		  mySql2.addSubPara(GetNoticeNo);//指定传入参数
		  mySql2.addSubPara(PolNo);//指定传入参数
		  mySql2.addSubPara(GetNoticeNo);//指定传入参数
		  mySql2.addSubPara(PolNo);//指定传入参数
		  var strSQL = mySql2.getString();
		 
	}
	
	//prompt("",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
}

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyClose()
{
	top.close();
	}