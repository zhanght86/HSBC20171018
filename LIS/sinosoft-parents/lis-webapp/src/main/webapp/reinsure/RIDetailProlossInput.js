//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var DealWithNam ;
var sqlresourcename = "reinsure.RIDetailReportInputSql";
function StatisticData()
{
  try 
	{
	  if (verify()) {
		  if (verifyInput2() == true) {
	     fm.target = "importCessData"; 
         fm.action = "LPrtPrintProfitLossSave.jsp";
	     fm.submit();
	}
	  }
  } 
  catch(ex) 
  {
  	myAlert(ex);
  }
}

function ResetForm(){
	 	fm.reset();
}
function verify() {
	if (fm.CalYear.value == "" || fm.CalYear.value == null) {
		myAlert(""+"��Ȳ���Ϊ��"+"");
		return false;
	}
	if (!isNumeric(fm.CalYear.value)) {
		myAlert(""+"��ȸ�ʽ����"+"");
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
	
	
	if(codeName=="riprolosstype"){
		
		fm.ContName.value ='';
		fm.ContNo.value ='';
		fm.CalYear.value ='';
		
	  if(fm.ProlossType.value=="001"){
		  fm.RIcomCode.value="R0002";
		  fm.RIComName.value="China Life Re";
	  }
	  if(fm.ProlossType.value=="002"){
		  fm.RIcomCode.value="R0004";
		  fm.RIComName.value="China International Re";
	  }
	  if(fm.ProlossType.value=="003"){
		  fm.RIcomCode.value="R0005";
		  fm.RIComName.value="Hannover Re";
	  }
	  if(fm.ProlossType.value=="004"){
		  fm.RIcomCode.value="R0006";
		  fm.RIComName.value="RGA";
	  }
	  if(fm.ProlossType.value=="005"){
		  fm.RIcomCode.value="R0003";
		  fm.RIComName.value="Swiss Re";
		  
	  }
	  if(fm.ProlossType.value=="006"){
		  fm.RIComCode.value="R0007";
		  fm.RIComName.value="Transamerica Re";
	  }
	}
}
