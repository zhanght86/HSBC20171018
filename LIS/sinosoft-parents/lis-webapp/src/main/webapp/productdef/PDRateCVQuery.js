//�������ƣ�PDAlgoTempLibQuery.js
//�����ܣ��㷨ģ����ѯ����
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var QuerySQLCache="";
var turnPage = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDRateCVQueryInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
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
}
var Mulline9GridTurnPage = new turnPageClass(); 
function query()
{
	initMulline9Grid();
	
	 var mySql=new SqlClass();
	 var sqlid = "PDRateCVQueryInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id	 
	 mySql.addSubPara(QuerySQLCache);
	 mySql.addSubPara(fm.TableName.value);//ָ������Ĳ���
  var strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
   QuerySQLCache="";
}
function retResult()
{
	  try
	  {
	  	top.opener.focus();
	  	top.close();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"�ر�¼��ҳ�����˳�ϵͳ�������µ�¼"+"");
	  	top.close();
	  }
}


