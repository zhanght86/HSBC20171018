//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
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
       var showStr=""+"���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
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
	
	//����ͳ���ڼ��Ƿ���ȷ
	var StaTerm = fm.StaTerm.value; 
	var yearStr = StaTerm.substr(0,4);
	var monthStr = StaTerm.substr(5);
	if(!isInteger(StaTerm)||StaTerm.length>6||parseFloat(monthStr)>12){
		myAlert(""+"ͳ���ڼ��������"+"");
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

//�ύ�����,���������ݷ��غ�ִ�еĲ���
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
	  //content="����ɹ���";
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
			mySql1.setResourceName("reinsure.DetailReportInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId("DetailReportInputSql001");//ָ��ʹ�õ�Sql��id
		    mySql1.addSubPara(Field.value);//ָ������Ĳ���
		var tSql=mySql1.getString();		
		
		arrResult=easyExecSql(tSql);
		if(arrResult == null)
		{
			myAlert(" "+"û�и��ٱ���ͬ��û�д��ٱ���ͬ��ر���"+" ");
			return false;
		}else{
			fm.ContNo.value=arrResult[0][0];
			fm.ContName.value=arrResult[0][1];
		}
	}
}
