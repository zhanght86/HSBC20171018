//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); 


//待审核账单查询
function queryBillPrint()
{
	var strSQL = "select BillNo,BillingCycle,BillTimes,RIContNo,IncomeCompanyNo,StartDate,EndDate,decode(State,'01','"+"账单计算"+"','02','"+"账单审核"+"','03','"+"账单修改"+"','04','"+"账单确认"+"'),State from RIBsnsBillBatch where State='04' " ;
	
	var mySql1=new SqlClass();
		mySql1.setResourceName("reinsure.LRBsnsBillPrintInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("LRBsnsBillPrintInputSql001");//指定使用的Sql的id
	    mySql1.addSubPara('1');//指定传入的参数
	strSQL=mySql1.getString();	
	
	turnPage.queryModal(strSQL,BillPrintListGrid);
}

// 账单打印
function billPrint()
{
	try
	{
		if(verifyInput()==true)
		{	
			if(verifyInput2()==true)
			{
				//var i = 0;
				//var showStr="正在统计数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
				//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.target = "importCessData"; 
				//fm.action = "LRBsnsTabSave_antail.jsp"
				fm.action = "LRBsnsBillPrintSave.jsp"
				fm.submit();
				//showInfo.close();
			}
		}
	}	
	catch(ex) 
	{
	  	//showInfo.close( );
	  	myAlert(ex);
   }
}

function verifyInput1()
{ 
	var mStaTerm=fm.StartDate.value;
	var year = mStaTerm.substr(0,4);
	var season = mStaTerm.substr(5);
	if(!isInteger(year)||mStaTerm.length>6||parseFloat(season)>4)
	{
	    myAlert(""+"统计期间时出现错误!"+"");
	    return  false;
	}
	return true;
}  


//重  置
function ResetForm()
{
		fm.StartDate.value='';
		fm.EndDate.value='';
		fm.ContNo.value='';
		fm.ContName.value='';
		fm.AccountType.value='';
		fm.AccountTypeName.value='';
		fm.RIcomCode.value='';
		fm.RIcomName.value='';
		fm.TempType.value='';
		fm.TempTypeName.value='';
}

function afterCodeSelect( cCodeName, Field )
{

}
