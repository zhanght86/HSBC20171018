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
//查询
function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameProfitLossQuery.jsp?Serial=");
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
//提交，保存按钮对应操作  ,应用佣金计算
function submitForm()
{
	
	//如果审核确认则不可以重算
	var strSql = " select '1' from RIProLossResult where CalYear='"+fm.CalYear.value
		+"'  and ReComCode ='"+fm.RIcomCode.value+"' and RIContNo='"+fm.ContNo.value+"' and StandbyString1 ='03' " ;
	
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
					
				fm.action="RIProfitLossCalSave.jsp";
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
		if(IncomeGrid.getRowColData(i,7)=="01")
		{
			if(IncomeGrid.getRowColData(i,5)!=IncomeGrid.getRowColData(i,6))
			{
				myAlert(""+"计算参数第"+""+(i+1)+""+"行"+"'"+IncomeGrid.getRowColData(i,3)+"'"+"已被修改"+","+"原值为:"+" "+IncomeGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	lineNum=PayoutGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(PayoutGrid.getRowColData(i,7)=="01")
		{
			if(PayoutGrid.getRowColData(i,5)!=PayoutGrid.getRowColData(i,6))
			{
				myAlert(""+"支出项第"+""+(i+1)+""+"行收入值已被修改"+","+"原值为:"+" "+PayoutGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	
	return true;
}

//提交后操作,服务器数据返回后执行的操作 SDFSFDSFSFDdfsfsfsdfsdf
function afterSubmit(FlagStr,content,calResult)
{
	if("param"!=calResult)
	{
		showInfo.close();	
	}

	if (FlagStr == "Fail") 
	{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		fm.CalResult.value=calResult;
	} 
	else 
	{ 
		if("param"==calResult)
		{
			afterInit();
		}
		else
		{
			fm.CalResult.value=calResult;
			//content="保存成功！";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
			
			showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		}
		
	}
	
	if("param"!=calResult)
	{
		fm.OperateType.value = "";
		resetForm();	
	}
	

}

//初始化参数
function initParameter()
{
	var year = fm.all('CalYear').value
	
	if( verifyInput1() == false)
	{
		return false;
	} 
	fm.action="./LRProfitLossParamSave.jsp";
	fm.submit(); //提交	
}

function afterInit()
{
	var recomCode=fm.all('RIcomCode').value;
	var strSQL="select decode (a.InOutType,'01','"+"收入"+"','"+"支出"+"'),a.factorcode,a.factorname,decode(b.inputtype,'01','"+"系统计算"+"','"+"手工录入"+"'),c.Factorvalue,c.Factorvalue,b.inputtype,d.Serialno from RIProfitLossDef a,RIProLossRela b,RIProLossValue c,RIProLossResult d "
				+ " where a.FactorCode = b.FactorCode and c.Serialno = d.Serialno and b.recomcode = c.Recomcode and b.ricontno = c.Ricontno and b.Factorcode = c.Factorcode and d.recomcode='"+recomCode+"' and d.ricontno='"+fm.ContNo.value+"' and d.Calyear = '"+fm.CalYear.value+"' order by a.InOutType,b.inputtype";
	turnPage.queryModal(strSQL,IncomeGrid);	
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
	var strSQL = "select a.SerialNo,a.RIContNo,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),"
		+ " a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode) x,"
		+ " a.CalYear,a.ProLosAmnt,decode(StandbyString1,'01','"+"待审核"+"','02','"+"审核修改"+"','03','"+"审核确认"+"'),StandbyString1 from RIProLossResult a where StandbyString1 ='01' "
		;
	strSQL = strSQL +" order by a.SerialNo desc ";
	var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL,LossUWListGrid)

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
  	myAlert(""+"在LRProfitLossCalInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
} 

//提交前的校验、计算  
function beforeSubmit(){
  //添加操作	
}   

function afterCodeSelect(cCodeName, Field )
{

}

