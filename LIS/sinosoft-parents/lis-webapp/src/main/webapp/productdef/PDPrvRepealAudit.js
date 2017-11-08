//程序名称：PDPrvRepealAudit.js
//程序功能：条款废止审核
//创建日期：
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
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
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 


function queryAudit()
{
  var sql = "select missionprop2, b.Riskname, missionprop1, missionprop3, missionid, submissionid, activityid from lwmission a left outer join pd_lmrisk b on a.Missionprop2 = b.Riskcode where activityid = 'pd00000002' and processid = 'pd00000011' " 
	+ getWherePart("missionprop2","RiskCode") + getWherePart("missionprop1","RequDate") ;
	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}
function query()
{
  var selNo = Mulline10Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中某行后，再点击"+"");
		return;
	}
	
  fm.action = "./PDSetSession.jsp?IsReadOnly=1&Operator=query"; 

  fm.submit();
}

function beginAudit()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中某行后，再点击"+"");
		return;
	}
	
	var parasPart =  
	"riskcode=" + Mulline9Grid.getRowColData(selNo-1,1)
	+ "&requdate="+Mulline9Grid.getRowColData(selNo-1,3)
//	+ "&riskname=" + Mulline9Grid.getRowColData(selNo-1,2)	
	+"&missionid=" + Mulline9Grid.getRowColData(selNo-1,5)
	+ "&submissionid=" + Mulline9Grid.getRowColData(selNo-1,6)
	+ "&activityid=" + Mulline9Grid.getRowColData(selNo-1,7);
	showInfo = window.open("PDPrvRepealAuditMain.jsp?" + parasPart);
}

function queryDeployed()
{
  	var sql = "select missionprop2, b.Riskname, missionprop1, missionprop3, missionid, submissionid, activityid from lwmission a left outer join pd_lmrisk b on a.Missionprop2 = b.Riskcode where activityid = 'pd00000004' and processid = 'pd00000011' " 
	+ getWherePart("missionprop2","RiskCode2") ;
	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
