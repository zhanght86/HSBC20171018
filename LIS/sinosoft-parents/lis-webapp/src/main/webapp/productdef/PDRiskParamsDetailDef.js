//�������ƣ�PDRiskParamsDetailDef.js
//�����ܣ��ֶβ�������
//�������ڣ�2009-3-13
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
var tResourceName = "productdef.PDRiskParamsDetailDefInputSql";

function checkNullable()
{
  var GridObject = Mulline9Grid;
  var rowCount = GridObject.mulLineCount;
  
  for(var i = 0; i < rowCount; i++)
  {
    var PropName = GridObject.getRowColData(i,1);
    var PropValue = GridObject.getRowColData(i,4);
    
  	if(PropName.indexOf("[*]") > -1)
  	{
  		if(PropValue == null || PropValue == "")
  		{
  			PropName = PropName.substring(3,PropName.length);
  		
  			myAlert("\"" + PropName + ""+"\"����Ϊ��"+"");
  			
  			return false;
  		}
  	}
  }
  
  return true;
}
function submitForm()
{
  if(!checkNullable()){
  	return ;
  }

if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
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
var Mulline10GridTurnPage = new turnPageClass(); 
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
    Mulline9GridTurnPage.pageLineNum  = 3215;
  
   var mySql=new SqlClass();
	 var sqlid = "PDRiskParamsDetailDefInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("1");//ָ������Ĳ���
   strSQL = mySql.getString();
  
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
} 
function queryMulline10Grid()
{
   var strSQL = "";

  
  var mySql=new SqlClass();
	 var sqlid = "PDRiskParamsDetailDefInputSql2";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all('RiskCode').value);//ָ������Ĳ���
	  mySql.addSubPara(fm.all('DutyCode').value);//ָ������Ĳ���
	   //mySql.addSubPara(fm.all('OtherCode').value);//ָ������Ĳ���
	   // mySql.addSubPara(fm.all('FieldName').value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('savebuttonid').style.display = 'none';
	}else{
		document.getElementById('savebuttonid').style.display = '';
	}
}

