//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;
var sqlresourcename = "reinsure.RIDetailReportInputSql";
function StatisticData()
{
  try 
	{
		if( verifyInput()) 
		{
			if (verifyInput2())

			{
			 fm.target = "importCessData"; 
	     fm.action = "LPrtPrintReportSave.jsp";
	     fm.submit();
	  	}
	  }
  } 
  catch(ex) 
  {
  	myAlert(ex);
  }
}
  
function veriryInput1(){
	
	//检验统计期间是否正确
	var StaTerm = fm.StaTerm.value; 
	var yearStr = StaTerm.substr(0,4);
	var monthStr = StaTerm.substr(5);
	if(!isInteger(StaTerm)||StaTerm.length>6||parseFloat(monthStr)>12){
		myAlert(""+"统计期间输入错误！"+"");
		return false;
	}
	return true;
}	

function ResetForm(){
	 	//fm.StaTerm.value='';
		//fm.StaTermName.value='';
		//fm.ContNo.value='';
		//fm.ContName.value='';
		fm.ReportType.value='';
		fm.ReportTypeName.value='';
		//fm.ReRiskCode.value='';
		//fm.ReRiskName.value='';
		fm.RValidate.value='';
		fm.RInvalidate.value='';
		fm.RIComCode.value='';
		fm.RIComName.value='';
		//fm.TempType.value='';
	//	fm.TempTypeName.value='';
		//divTitle1.style.display="";
}



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
{
  showInfo.close();
  if (FlagStr == "Fail" )
   {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
   } 
  else 
  { 
	  //content="保存成功！";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
}

function afterCodeSelect(codeName,Field)
{ 
	if (codeName == "rireporttype") {
		if (Field.value == "01") {
			RICoCode.style.display = "none";
			RICoName.style.display = "none";
			fm.RIComCode.verify="";
		} else {
			RICoCode.style.display = "";
			RICoName.style.display = "";
			fm.RIComCode.verify=""+"分保公司"+"|NOTNULL";
		}
		fm.RValidate.value='';
		fm.RInvalidate.value='';
		fm.RIComCode.value='';
		fm.RIComName.value='';
	}
//	if(codeName=="rireporttype"){
//
//	var mySql = new SqlClass();
//	mySql.setResourceName(sqlresourcename);
//	mySql.setSqlId("RIDetailReportInputSql1");//指定使用的Sql的id
//	mySql.addSubPara(Field.value);// 指定传入的参数，多个参数顺序添加
//	var tSql = mySql.getString();	
//		
//		arrResult=easyExecSql(tSql);
//		
//		if(arrResult == null)
//		{
//			alert(" 没有该再保合同或没有此再保合同相关报表！ ");
//			return false;
//		}else{
//			fm.ContNo.value=arrResult[0][0];
//			fm.ContName.value=arrResult[0][1];
//		}
//	}
}
