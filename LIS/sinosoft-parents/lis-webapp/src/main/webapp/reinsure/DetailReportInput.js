//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;

function StatisticData()
{
  try 
	{
		if( verifyInput()) 
		{
			if (verifyInput2())
			{
		   var i = 0;
       var showStr=""+"正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
       showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	     fm.target = "importCessData"; 
	     fm.action = "DetailReportSava_antai.jsp"
	     fm.submit();
	     showInfo.close();
	  	}
	  }
  } 
  catch(ex) 
  {
  	showInfo.close( );
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
		//fm.ReprotType.value='';
		//fm.ReprotTypeName.value='';
		//fm.ReRiskCode.value='';
		//fm.ReRiskName.value='';
		fm.RValidate.value='';
		fm.RInvalidate.value='';
		fm.RIcomCode.value='';
		fm.RIcomName.value='';
		fm.TempType.value='';
		fm.TempTypeName.value='';
		divTitle1.style.display="";
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
	if(codeName=="lrcontno"){
			fm.ReprotType.value="";
			fm.ReprotTypeName.value="";
			fm.RIcomCode.value = "";
			fm.RIcomName.value = "";
			fm.TempType.style.display="";	
			fm.TempTypeName.style.display="";
			fm.TempType.value = "";
			fm.TempTypeName.value = "";
			fm.ReRiskCode.value="";
			fm.ReRiskName.value="";
	}
	if(codeName=="lreport")
	{
		//var tSql = "select RIContNo ,RIContName from RIBarGainInfo where RIContNo=(select othersign from ldcode where codetype ='rireporttype' and code = '"+Field.value+"')";
		
		var mySql1=new SqlClass();
			mySql1.setResourceName("reinsure.DetailReportInputSql"); //指定使用的properties文件名
			mySql1.setSqlId("DetailReportInputSql001");//指定使用的Sql的id
		    mySql1.addSubPara(Field.value);//指定传入的参数
		var tSql=mySql1.getString();		
		
		arrResult=easyExecSql(tSql);
		if(arrResult == null)
		{
			myAlert(" "+"没有该再保合同或没有此再保合同相关报表！"+" ");
			return false;
		}else{
			fm.ContNo.value=arrResult[0][0];
			fm.ContName.value=arrResult[0][1];
		}
	}
}
