//�������ƣ�PDAlgoTempLibQuery.js
//�����ܣ��㷨ģ����ѯ����
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDAlgoTempLibQueryInputSql";
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
  var strSQL = "";
  var mySql=new SqlClass();
  var sqlid = "PDAlgoTempLibQueryInputSql1";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(fm.all("Type").value);//ָ������Ĳ���
  mySql.addSubPara(fm.all("AlogTempCode").value);//ָ������Ĳ���
  mySql.addSubPara(fm.all("AlogTempName").value);//ָ������Ĳ���
  mySql.addSubPara(fm.all("Content").value);//ָ������Ĳ���
  mySql.addSubPara(fm.all("Description").value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function retResult()
{
	var arrReturn = new Array();
	var tSel = Mulline9Grid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼���ٵ�����ذ�ť��"+"" );
	else
	{
	  if(Mulline9Grid.getSelNo() == 0)
	  {
	  	myAlert(""+"��ѡ��ĳ��ٵ�����ذ�ť"+"");
	  	return;
	  }
	  
    	var Code = Mulline9Grid.getRowColData(Mulline9Grid.getSelNo()-1, 1);
	  try
	  {
	  	top.opener.focus();
	  	top.opener.afterQueryAlgo(Code);
	  	top.close();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"�ر�¼��ҳ�����˳�ϵͳ�������µ�¼"+"");
	  	window.open("PDCalFactorInput.jsp");
	  	top.close();
	  }
	}
}


