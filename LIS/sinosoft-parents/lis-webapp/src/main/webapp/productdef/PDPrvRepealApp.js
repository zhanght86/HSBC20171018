//�������ƣ�PDPrvRepealApp.js
//�����ܣ������ֹ����
//�������ڣ�
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var showInfo;	function returnParent(){top.opener.focus();		top.close();}

//��ѯ
function queryDefing()
{
  	var sql = "select missionprop2, b.Riskname, missionprop1, missionprop3, missionid, submissionid, activityid from lwmission a left outer join pd_lmrisk b on a.Missionprop2 = b.Riskcode where activityid = 'pd00000001' and processid = 'pd00000011' " ;
  	
  	var type = fm.all("Type").value;

  	if(type != null)
  	{
  		if (type == "Cont") {
			sql = sql + " and (missionprop5 != '1' or missionprop5 is null) ";
		} else if (type == "Claim") {
			sql = sql + " and (missionprop6 != '1' or missionprop6 is null) ";
		} else if (type == "Edor") {
			sql = sql + " and (missionprop7 != '1' or missionprop7 is null) ";
		} else if (type == "LFRisk") {
			sql = sql + " and (missionprop8 != '1' or missionprop8 is null) ";
		}	
  	}
  	
	sql = sql + getWherePart("missionprop2","RiskCode") + getWherePart("missionprop1","RequDate"); 
	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}
//�����ֹ����
function beginDefine()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"��ѡ��ĳ�к��ٵ��"+"");
		return;
	}
	
  //fm.action = "./PDSetSession.jsp?IsReadOnly=0&Operator=define"; 
	showInfo = window.open("PDPrvRepealAppMain.jsp?riskcode=" + Mulline9Grid.getRowColData(selNo-1,1)+"&requdate="+Mulline9Grid.getRowColData(selNo-1,3));
}

function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
  
  fm.action = "./PDContDefiSave.jsp";
}



function query()
{
  showInfo = window.open("");
}
