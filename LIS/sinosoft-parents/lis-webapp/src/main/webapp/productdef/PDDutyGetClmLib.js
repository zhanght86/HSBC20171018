//�������ƣ�PDDutyGetClmLib.js
//�����ܣ������⸶��
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	
//����sql�����ļ�
var tResourceName = "productdef.PDDutyGetClmLibInputSql";
function returnParent()
{
	if(Mulline9Grid.getRowColData(fm.all("GetDutyCode2RowOfMulLine").value,4) == "")
	{
		myAlert(""+"��ѡ��ĳ�����θ����⸶����룬�ٵ������"+"");
		return;
	}
	top.opener.afterQuery(Mulline9Grid.getRowColData(fm.all("GetDutyCode2RowOfMulLine").value,4));	
	
	top.opener.focus();		top.close();
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
  var wherePart = getWherePart('getdutycode2',"GetDutyCode2")
  	+ getWherePart('getdutyname2',"GetDutyName","like")
  	+ getWherePart('getdutykind2',"GetDutyKind2")
  	+ getWherePart('riskcode',"RiskCode")
  	+ getWherePart('getdutycode',"GetDutyCode")
  	+ getWherePart('getdutykind',"GetDutyKind")  	  	  	
  	;

    var mySql=new SqlClass();
    var sqlid = "PDDutyGetClmLibInputSql1";
    mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
    mySql.addSubPara(fm.all("GetDutyCode2").value);//ָ������Ĳ���
    mySql.addSubPara(fm.all("GetDutyName").value);//ָ������Ĳ���
    mySql.addSubPara(fm.all("GetDutyKind2").value);//ָ������Ĳ���
    mySql.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
    mySql.addSubPara(fm.all("GetDutyCode").value);//ָ������Ĳ���
    mySql.addSubPara(fm.all("GetDutyKind").value);//ָ������Ĳ���
    var sql = mySql.getString();
  	Mulline11GridTurnPage.queryModal(sql,Mulline11Grid);
  	
  	var mySql2=new SqlClass();
    var sqlid2 = "PDDutyGetClmLibInputSql2";
    mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
    mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    mySql2.addSubPara(fm.all("GetDutyCode2").value);//ָ������Ĳ���
    mySql2.addSubPara(fm.all("GetDutyName").value);//ָ������Ĳ���
    mySql2.addSubPara(fm.all("GetDutyKind2").value);//ָ������Ĳ���
    mySql2.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
    mySql2.addSubPara(fm.all("GetDutyCode").value);//ָ������Ĳ���
    mySql2.addSubPara(fm.all("GetDutyKind").value);//ָ������Ĳ���
  	sql = mySql2.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}

function queryRela()
{
	var selNo = Mulline11Grid.getSelNo();
	
	var mySql=new SqlClass();
  var sqlid = "PDDutyGetClmLibInputSql3";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(Mulline11Grid.getRowColData(selNo - 1, 1));//ָ������Ĳ���
 	sql = mySql.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
function button365()
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
   var sqlid = "PDDutyGetClmLibInputSql4";
   mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
   mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
   mySql.addSubPara("sqlid");//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = "";Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "";Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
