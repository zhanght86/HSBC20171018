//�������ƣ�PDDutyPayLib.js
//�����ܣ��ɷ����ο�
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	
//����sql�����ļ�
var tResourceName = "productdef.PDDutyPayLibInputSql";
function returnParent()
{
	/*
	if(Mulline9Grid.getRowColData(fm.all("PayPlanCode2RowOfMulLine").value,4) == "")
	{
		alert("��ѡ�С��ɷ����ο��ѯ������е�ĳ��ٵ������");
		return;
	}
	top.opener.afterQueryPayLib(Mulline9Grid.getRowColData(fm.all("PayPlanCode2RowOfMulLine").value,4));	
	*/
	var selNo = Mulline10Grid.getSelNo();
	if(selNo > 0 ){
		top.opener.afterQueryPayLib(Mulline10Grid.getRowColData(selNo-1,1));	
	}
	
	top.opener.focus();		
	top.close();
}
function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function queryRela()
{
		var selNo = Mulline10Grid.getSelNo();
		var mySql=new SqlClass();
		var sqlid = "PDDutyPayLibInputSql1";
		mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(Mulline10Grid.getRowColData(selNo - 1, 1));//ָ������Ĳ���
		var sql = mySql.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline11Grid);
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
    
    initButtonDisplay();    
    query();
    updateDisplayState();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function query()
{
		var mySql=new SqlClass();
		var sqlid = "PDDutyPayLibInputSql2";
		mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.PayPlanCode2.value);//ָ������Ĳ���
		mySql.addSubPara(fm.PayPlanName.value);//ָ������Ĳ���
		mySql.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		mySql.addSubPara(fm.PayPlanCode.value);//ָ������Ĳ���
    var sql = mySql.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
  	
  	var mySql2=new SqlClass();
		var sqlid2 = "PDDutyPayLibInputSql3";
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.PayPlanCode2.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PayPlanName.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PayPlanCode.value);//ָ������Ĳ���
  	sql = mySql2.getString();
  	Mulline11GridTurnPage.queryModal(sql,Mulline11Grid);
}
function button347()
{
  showInfo = window.open("");
}
function save()
{
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyPayLibInputSql4";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("sqlid");//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

