//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var DealWithNam ;


//��������
function StatisticData()
{
	try
	{
		if(verifyInput()&& verifyInput2())
		{	
			var i = 0;
			var showStr=""+"����ͳ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			fm.action = "LRBsnsBillSave.jsp"
			fm.submit();
			showInfo.close();
		}
	}	
	catch(ex) 
	{
	  	showInfo.close( );
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
	    myAlert(""+"ͳ���ڼ�ʱ���ִ���!"+"");
	    return  false;
	}
	return true;
}  

//��  ��
function ResetForm()
{
	fm.StartDate.value='';
	fm.EndDate.value='';
	fm.ContNo.value='';
	fm.ContName.value='';
	fm.RIcomCode.value='';
	fm.RIcomName.value='';
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
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		if (fm.OperateType.value=="DELETE")
		{
			ResetForm();
		}
	}
}
function afterCodeSelect( cCodeName, Field )
{

}
