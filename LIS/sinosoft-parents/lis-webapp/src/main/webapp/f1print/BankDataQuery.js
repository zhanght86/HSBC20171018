//程序名称：BankDataQuery.js
//程序功能：财务报盘数据查询
//创建日期：2004-10-20
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;

var turnPage = new turnPageClass();

//简单查询
function easyQuery()
{
	if (!verifyInput()==true) return false;
	var sDate = document.all('SendDate').value;
	if(fm.ContType.value == '1')
	{	
	// 书写SQL语句
//	var strSQL = "select serialno,polno,AccName,AccNo,BankCode,paymoney,SendDate,bankdealdate,''"
//							+ ",case notype when '6' then '个人首期' "
//							+ "when '2' then '个人续期' "
//							+ "when '7' then '银代首期' "
//							+ "when '3' then '银代续期' "
//							+ "when '10' then '保全' "
//							+ "end "
//							+ "from lySendToBank "
//							+ " where 1=1 and dealtype='S'"
//							+ getWherePart('serialno','serialno','like','0')
//							+ getWherePart('polno','PolNo','=','0')
//							+ getWherePart('accname','AccName','=','0')
//							+ getWherePart('comcode','ManageCom','like','0')
//							+ getWherePart('bankcode','BankCode','=','0')
//							+ getWherePart('SendDate','SendDate','=','0')
//							+ getWherePart('bankdealdate','BackDate','=','0')
//							+ getWherePart('notype','NoType','=','0')
//							+ getWherePart('accno','AccNo','=','0')
//							//+ " group by polno,accname,comcode,bankcode,senddate "
//							+ "order by 1,2";
	
  	var  serialno0 = window.document.getElementsByName(trim("serialno"))[0].value;
  	var  PolNo0 = window.document.getElementsByName(trim("PolNo"))[0].value;
  	var  AccName0 = window.document.getElementsByName(trim("AccName"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  BankCode0 = window.document.getElementsByName(trim("BankCode"))[0].value;
  	var  SendDate0 = window.document.getElementsByName(trim("SendDate"))[0].value;
  	var  BackDate0 = window.document.getElementsByName(trim("BackDate"))[0].value;
  	var  NoType0 = window.document.getElementsByName(trim("NoType"))[0].value;
  	var  AccNo0 = window.document.getElementsByName(trim("AccNo"))[0].value;
	var sqlid0="BankDataQuerySql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.BankDataQuerySql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(serialno0);//指定传入的参数
	mySql0.addSubPara(PolNo0);//指定传入的参数
	mySql0.addSubPara(AccName0);//指定传入的参数
	mySql0.addSubPara(ManageCom0);//指定传入的参数
	mySql0.addSubPara(BankCode0);//指定传入的参数
	mySql0.addSubPara(SendDate0);//指定传入的参数
	mySql0.addSubPara(BackDate0);//指定传入的参数
	mySql0.addSubPara(NoType0);//指定传入的参数
	mySql0.addSubPara(AccNo0);//指定传入的参数
	var strSQL=mySql0.getString();
	
	
	 }
	else
		{
//			var strSQL = "select serialno,polno,AccName,AccNo,BankCode,paymoney,SendDate,bankdealdate"
//							+ ",case "
//							+ " when instr((select b.agentpaysuccflag from ldbank b where b.bankcode=a.bankcode  ) ,concat(a.banksuccflag,';'))>0  then '成功' else "
//							+ "(select codename from ldcode1 where trim(code)=trim(BankCode) "+
//							"			 and trim(code1)=trim(banksuccflag) and codetype='bankerror')"
//							+ " end"
//							+ ",case notype when '6' then '个人首期' "
//							+ "when '2' then '个人续期' "
//							+ "when '7' then '银代首期' "
//							+ "when '3' then '银代续期' "
//							+ "when '10' then '保全' "
//							+ "end "
//							+ "from lyreturnfromBankb a	 "
//							+ " where 1=1 and dealtype='S'"
//							+ getWherePart('serialno','serialno','like','0')
//							+ getWherePart('polno','PolNo','=','0')
//							+ getWherePart('accname','AccName','=','0')
//							+ getWherePart('comcode','ManageCom','like','0')
//							+ getWherePart('bankcode','BankCode','=','0')
//							+ getWherePart('SendDate','SendDate','=','0')
//							+ getWherePart('bankdealdate','BackDate','=','0')
//							+ getWherePart('notype','NoType','=','0')
//							+ getWherePart('accno','AccNo','=','0')
//							//+ " group by polno,accname,comcode,bankcode,senddate "
//							+ "order by 1";
			
		  	var  serialno1 = window.document.getElementsByName(trim("serialno"))[0].value;
		  	var  PolNo1 = window.document.getElementsByName(trim("PolNo"))[0].value;
		  	var  AccName1 = window.document.getElementsByName(trim("AccName"))[0].value;
		  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
		  	var  BankCode1 = window.document.getElementsByName(trim("BankCode"))[0].value;
		  	var  SendDate1 = window.document.getElementsByName(trim("SendDate"))[0].value;
		  	var  BackDate1 = window.document.getElementsByName(trim("BackDate"))[0].value;
		  	var  NoType1 = window.document.getElementsByName(trim("NoType"))[0].value;
		  	var  AccNo1 = window.document.getElementsByName(trim("AccNo"))[0].value;
			var sqlid1="BankDataQuerySql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("f1print.BankDataQuerySql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(serialno1);//指定传入的参数
			mySql1.addSubPara(PolNo1);//指定传入的参数
			mySql1.addSubPara(AccName1);//指定传入的参数
			mySql1.addSubPara(ManageCom1);//指定传入的参数
			mySql1.addSubPara(BankCode1);//指定传入的参数
			mySql1.addSubPara(SendDate1);//指定传入的参数
			mySql1.addSubPara(BackDate1);//指定传入的参数
			mySql1.addSubPara(NoType1);//指定传入的参数
			mySql1.addSubPara(AccNo1);//指定传入的参数
			var strSQL=mySql1.getString();
			
			}
	//alert(strSQL);
	turnPage.queryModal(strSQL, CodeGrid);
	//arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

	if(CodeGrid.mulLineCount ==0)
	{
		alert("没有符合条件的数据");
	}
}

//确认输入条件
function verifyInput()
{
	
	if(fm.ManageCom.value =="")
	{
				alert("请选择管理机构");
				fm.ManageCom.focus();
				return false;
	}
	if (fm.BankCode.value == "")
	{
				alert("请选择银行代码");
				fm.BankCode.focus();
				return false;
	}
	
	//制盘日期、返盘日期改为可以录入时间段，且录入的时间范围不能超过1个月；

 //   2、如果没有输入以上条件，则批次号、账号、保单号必须录入其中任何一个。
	var booleanDate = "1"

	if(fm.SendDate.value !="" || fm.BackDate.value !="")
	{
		  if(fm.SendDate.value !="" && fm.BackDate.value !="")
		  if(dateDiff(fm.SendDate.value,fm.BackDate.value,"M") >1)
			{
			alert("制盘日期与返盘日期的间隔不能大于一个月，请确认");
		  return false;
		  }
		  booleanDate = "2";
	}
	
	
	if (booleanDate == "1" && fm.serialno.value=="" && fm.AccNo.value=="" && fm.PolNo.value=="")
	{
				alert("由于没有录入时间，请在批次号、账号、保单号必须录入其中任何一个");
				fm.BankCode.focus();
				return false;
	}
	
	
	
	if(fm.ContType.value=="")
	{
				alert("请选择制盘状态！");
				fm.ContType.focus();
				return false;
	}
	return true;
}