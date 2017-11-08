var showInfo;

var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;

function myonfocus()
{
	if(showInfo!=null)
	{
		try
		{
			showInfo.focus();  
		}
		catch(ex)
		{
			showInfo=null;
		}
	}
}

//审核结论保存
function SaveConclusion()
{
	//将结论存储到 RIProLossResult.StandbyString1
	fm.OperateType.value="conclusion";
	var num = LossUWListGrid.getSelNo() ;
	
	if(num==0)
	{
		myAlert(""+"请先选择待审核数据！"+"");
		return false;
	}
	if(fm.RILossUWReport.value==null || fm.RILossUWReport.value=="")
	{
		myAlert(""+"请选择审核结论！"+"");
		return false;
	}
	try
	{
		var i = 0;
		var showStr=""+"正在统计数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		fm.action = "./LRProfitLossCalSave.jsp"
		fm.submit();
		//showInfo.close();
	}	
	catch(ex) 
	{
	  	showInfo.close();
	  	myAlert(ex);
	}	
}


//待审核查询
function queryAuditTask()
{
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRProfitLossCalInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRProfitLossCalInputSql100");//指定使用的Sql的id
		mySql100.addSubPara("1");
	var strSQL=mySql100.getString();
	
	
	/**
	var strSQL = "select a.SerialNo,a.RIContNo,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),"
		+ " a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode) x,"
		+ " a.CalYear,a.ProLosAmnt,decode(StandbyString1,'01','待审核','02','审核修改','03','审核确认'),StandbyString1 from RIProLossResult a where StandbyString1 ='01' "
		;		
	strSQL = strSQL +" order by a.SerialNo desc ";
	*/
	var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL,LossUWListGrid)

}

//提交，保存按钮对应操作  ,应用佣金计算
function submitForm()
{
	
	//如果审核确认则不可以重算
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRProfitLossCalInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRProfitLossCalInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(fm.CalYear.value);//指定传入的参数
		mySql101.addSubPara(fm.RIcomCode.value);//指定传入的参数
		mySql101.addSubPara(fm.ContNo.value);//指定传入的参数
	var strSql=mySql101.getString();
	/**
	var strSql = " select '1' from RIProLossResult where CalYear='"+fm.CalYear.value
		+"'  and ReComCode ='"+fm.RIcomCode.value+"' and RIContNo='"+fm.ContNo.value+"' and StandbyString1 ='03' " ;
	*/
	var strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
	if(strQueryResult)
	{
		myAlert(""+"已经审核确认，不可以重算！！"+"");
		return false;
	}
	fm.OperateType.value="CALCULATE";
	try 
	{
		if( verifyInput() == true &&IncomeGrid.checkValue("IncomeGrid")) 
		{
			if (veriryInput3()==true)
			{
			  	var i = 0;
			  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
					
				fm.action="LRProfitLossCalSave.jsp";
			  	fm.submit(); //提交
		  	}
		  	else
		  	{
			}
		}
	} catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}
function veriryInput3()
{
	var lineNum=IncomeGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(IncomeGrid.getRowColData(i,3)=="01")
		{
			if(IncomeGrid.getRowColData(i,4)!=IncomeGrid.getRowColData(i,5))
			{
				myAlert(""+"计算参数第"+""+(i+1)+""+"行"+"'"+IncomeGrid.getRowColData(i,2)+"'"+"已被修改"+","+"原值为:"+" "+IncomeGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	lineNum=PayoutGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(PayoutGrid.getRowColData(i,3)=="01")
		{
			if(PayoutGrid.getRowColData(i,4)!=PayoutGrid.getRowColData(i,5))
			{
				myAlert(""+"支出项第"+""+(i+1)+""+"行收入值已被修改"+","+"原值为:"+" "+PayoutGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	
	return true;
}

function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameProfitLossQuery.jsp?Serial=");
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,content,calResult)
{
  showInfo.close();
  if (FlagStr == "Fail") {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  fm.CalResult.value=calResult;
  } else { 
	  //content="保存成功！";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //resetForm();
	  fm.CalResult.value=calResult;
  }
  fm.OperateType.value = "";
  resetForm();
}

function initParameter()
{
	var year = fm.all('CalYear').value ;
	
	if( verifyInput1() == false)
	{
		return false;
	} 
	var recomCode=fm.all('RIcomCode').value;
	
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRProfitLossCalInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRProfitLossCalInputSql102");//指定使用的Sql的id
		mySql102.addSubPara(recomCode);//指定传入的参数
		mySql102.addSubPara(fm.ContNo.value);//指定传入的参数
	var strSQL=mySql102.getString();
	
	/**
	var strSQL="select a.factorcode,factorname,inputtype from RIProfitLossDef a,RIProLossRela b "
	+ " where a.FactorCode=b.FactorCode and b.recomcode='"+recomCode+"' and b.ricontno='"+fm.ContNo.value+"' order by inputtype"
	;
	*/
	turnPage.queryModal(strSQL,IncomeGrid);
	if(veriryInput4()==false)
	{
		return false;
	}
	var startdate = year + "-01-01";
	var enddate = year + "-12-31";
	var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRProfitLossCalInputSql"); //指定使用的properties文件名
		mySql103.setSqlId("LRProfitLossCalInputSql103");//指定使用的Sql的id
		mySql103.addSubPara(year);//指定传入的参数
		mySql103.addSubPara(year);//指定传入的参数
		mySql103.addSubPara(fm.RIcomCode.value);//指定传入的参数
		mySql103.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql103.addSubPara(fm.ContNo.value);//指定传入的参数
	var strSQL=mySql103.getString();
	
	/**
	var strSQL=" select nvl(sum(b.PremChang),0),nvl(sum(b.CommChang),0),nvl(sum(b.ReturnPay),0) from RIPolRecordBake a,rirecordtrace b where a.batchno=b.batchno and a.EventNo=b.EventNo  and a.EventType in('01','02','03') and a.batchno in (select batchno from riwflog where startdate>='"+year+"-01-01' and enddate <='"+year+"-12-31' and nodestate='99') and b.incomecompanyno='"+fm.RIcomCode.value
		+"' and b.RIPreceptNo in (select RIPreceptNo from RIPrecept where RIContNo='"
		+fm.ContNo.value+"') and a.riskcode in (select distinct AssociatedCode from RIAccumulateRDCode c, RIAccumulateDef d where c.AccumulateDefNO = d.AccumulateDefNO and d.DeTailFlag = '01' and c.accumulatedefno in (select accumulatedefno FROM RIPrecept where RIContno = '"+fm.ContNo.value+"')) "
	;
	*/

	
	var strResult1=easyExecSql(strSQL);
	
	var lineNum=IncomeGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(strResult1==null)
		{
			if(IncomeGrid.getRowColData(i,3)=="01")
			{
				IncomeGrid.setRowColData(i,4,"0");
				continue;
			}
		}
		if(IncomeGrid.getRowColData(i,1)=="CessPrem")
		{
			IncomeGrid.setRowColData(i,4,strResult1[0][0]+"");
			IncomeGrid.setRowColData(i,5,strResult1[0][0]+"");
		}
		if(IncomeGrid.getRowColData(i,1)=="ReturnComm")
		{
			IncomeGrid.setRowColData(i,4,strResult1[0][1]+"");
			IncomeGrid.setRowColData(i,5,strResult1[0][1]+"");
		}
		if(IncomeGrid.getRowColData(i,1)=="ReturnPay")
		{
			IncomeGrid.setRowColData(i,4,strResult1[0][2]+"");
			IncomeGrid.setRowColData(i,5,strResult1[0][2]+"");
		}
	}
}

function verifyInput1()
{
	if(fm.CalYear.value==""||fm.CalYear.value==null){
		myAlert(""+"年度不能为空"+"");
		return false;
	}
	if(!isNumeric(fm.CalYear.value)){
		myAlert(""+"年度格式错误"+"");
		return false;
	}
	if(fm.RIcomCode.value==""||fm.RIcomCode.value==null){
		myAlert(""+"分保公司不能为空"+"");
		return false;
	}
	if(fm.ContNo.value==""||fm.ContNo.value==null){
		myAlert(""+"合同名称不能为空"+"");
		return false;
	}
	return true;
}

function isNumeric(strValue)
{
  var NUM="0123456789";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false
  }
  if(strValue.indexOf(".")!=strValue.lastIndexOf(".")) return false;
  return true;
}

function veriryInput4()
{
	
	return true;
}

function afterQuery()
{
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try{
	  initForm();
	  fm.RILossUWReport.value = "";
	  fm.RILossUWReportName.value = "";
  }
  catch(re){
  	myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
} 

//提交前的校验、计算  
function beforeSubmit(){
  //添加操作	
}   

function afterCodeSelect(cCodeName, Field )
{

}

