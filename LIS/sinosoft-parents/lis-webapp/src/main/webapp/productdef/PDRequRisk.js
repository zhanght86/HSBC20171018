//�������ƣ�PDRequRisk.js
//�����ܣ���Ʒ�������ѯ
//�������ڣ�2009-3-12
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
//����sql�����ļ�
var tResourceName = "productdef.PDRequRiskInputSql";
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
    fm.RequDate.value ="";
    initForm();  
    queryDefining();   
    initRiskState();
  } 
}
function queryDefining( ){
	var mySql1=new SqlClass();
	var sqlid1 = "PDRequRiskInputSql1";
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.RequDate.value);//ָ������Ĳ���
	var sql = mySql1.getString();
	Mulline9GridTurnPage.pageLineNum = 10; 
	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}

function ApplyNewRisk( ){
	if(fm.RiskCode.value=='undefined'||fm.RiskCode.value==null||fm.RiskCode.value=='')
	{ 
		myAlert(""+"���������ֱ���¼�����ִ���"+"");
		return;
	}
	var mySql2=new SqlClass();
	var sqlid2 = "PDRequRiskInputSql2";
	mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	var tSQL = mySql2.getString();
	tnumber = easyExecSql(tSQL);
	if(tnumber=="1")
	{
		myAlert(""+"ϵͳ���Ѵ��ڸ����ֱ��룬�������ظ����룡"+"");
		return;
	}
	var currentDate = getCurrentDate("-");
	
	if(compareDate(fm.all("RequDate").value,currentDate) != 0)
	{
		myAlert(""+"�������ڱ���Ϊ��ǰ���ڣ�"+"");
		var currentDate = sysDate();
		fm.all("RequDate").value = currentDate;	
		return;
	}
	//-------- add by jucy
	//��������ֹ�¼�����ڸ�ʽУ�飬����Ϊyyyy-mm-dd ����2011-1-1 Ϊ�����ʽ
	if(!checkDateF()){
		myAlert(""+"���ڸ�ʽ������ȷ���ڸ�ʽΪ��YYYY"+"-MM-DD");
		var currentDate = sysDate();
		fm.all("RequDate").value = currentDate;	
		return false;
	}
	//-------- end
	submitForm();
}
//-------- add by jucy
//�����ֹ�¼�����ڸ�ʽУ�飬����Ϊyyyy-mm-dd ����2011-1-1 Ϊ�����ʽ
function checkDateF(){
	var checkRequDate = fm.all("RequDate").value;
	if(checkRequDate == null){
		return false;
	}
    var checkResult = checkRequDate.match(/^\d{4}\-\d\d\-\d\d$/);
	if(checkResult == null){
		return false;
	}
	return true;

}
//-------- end 

function submitForm()
{
	if(!verifyInput2()){
		return;
	}
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.action = "./PDRequRiskSave.jsp";
  fm.submit();
}

function BeginDefine(){
	//var tUrl = "http://10.211.55.3/LIS/productdef/PDRiskDefiMain.jsp?pdflag=1&riskcode=Kathy&requdate=2013-11-27&missionid=00000000000000058308&submissionid=1&activityid=pd00000000";
	//window.open(tUrl);
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert( ""+"����ѡ��һ����¼���ٵ����ʼ���尴ť��"+"" );
		return;
	}
	
	fm.action = "PDSetSession.jsp?pdflag=1&IsReadOnly=0&Operator=define";
	fm.submit();
}

function definAfterSetSession(FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    return;
  }
  
  var selNo = Mulline9Grid.getSelNo();
  var url = "PDRiskDefiMain.jsp?pdflag=1&riskcode=" + Mulline9Grid.getRowColData(selNo-1,1) 
	  + "&requdate=" + Mulline9Grid.getRowColData(selNo-1,3)
	  + "&missionid=" + Mulline9Grid.getRowColData(selNo-1,5)
	  + "&submissionid=" + Mulline9Grid.getRowColData(selNo-1,6)
	  + "&activityid=" + Mulline9Grid.getRowColData(selNo-1,7);
		window.open(url);
}

function queryDefined( )
{
	var mySql3=new SqlClass();
	var sqlid3 = "PDRequRiskInputSql3";
	mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.RiskCodeUsing.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.MakeDateB.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.MakeDateC.value);//ָ������Ĳ���
	var sql = mySql3.getString();
	Mulline10GridTurnPage.pageLineNum = 10; 
	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
function BeginReview( )
{
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert( ""+"����ѡ��һ����¼���ٵ���鿴��ť��"+"" );
		return;
	}
	
	fm.action = "PDSetSession.jsp?pdflag=1&IsReadOnly=1&Operator=query";
//	fm.action = "PDSetSession.jsp";//?IsReadOnly=1&Operator=query";
	fm.submit();
}

function queryAfterSetSession(FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    return;
  }
  
  fm.ViewMode.value = 'view';
  var selNo = Mulline10Grid.getSelNo();
  var url = "PDRiskDefiMain.jsp?pdflag=1&riskcode=" + Mulline10Grid.getRowColData(selNo-1,1) 
	  + "&requdate=" + Mulline10Grid.getRowColData(selNo-1,3)
	  + "&missionid=" + Mulline10Grid.getRowColData(selNo-1,5)
	  + "&submissionid=" + Mulline10Grid.getRowColData(selNo-1,6)
	  + "&activityid=" + Mulline10Grid.getRowColData(selNo-1,7);
		window.open(url);
  
}
//-------- add by jucy
//��������ϵͳʱ��ķ��������ϵͳʱ��Ϊ2011-1-1 ��ҳ����ʾΪ��2011-01-01
function sysDate(){
	
	var splitOp='-';
	var SystemDate=new Date();
	var year=SystemDate.getYear();
	var month=SystemDate.getMonth()+1;
	var day=SystemDate.getDate();
	var m = month+"";
	var cm = m.length;
	if(cm==1){
		month = "0"+m;
	}
	var d = day+"";
	var cd = d.length;
	if(cd==1){
		day = "0"+d;
	}
	var CurrentDate=year+splitOp+month+splitOp+day;
	fm.all("RequDate").value = CurrentDate;
	return CurrentDate;
}
//-------- end

