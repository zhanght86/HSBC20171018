//�������ƣ�PDIssueQuery.js
//�����ܣ������¼��
//�������ڣ�2009-4-3
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;
//����sql�����ļ�
var tResourceName = "productdef.PDIssueQueryInputSql";
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
var Mulline10GridTurnPage = new turnPageClass(); 
function ReplyIssue()
{
	 if(fm.all('RiskCode').value!=fm.all('hiddenRiskCode').value){
	 	  myAlert(""+"��ѡ�����ֲ�����"+"");
			return false;
	 }
	 if(fm.all('BackPost').value!=fm.all('hiddenBackPost').value){
	 	  myAlert(""+"���ظ��뵱ǰ��λ������"+"");
			return false;
	 }
	 if(fm.all('FindFlag').value=='1'){
	 	 myAlert(""+"û��Ȩ��ִ�иò�����"+"");
			return false;
	 }
 	 tSel = Mulline10Grid.getSelNo();
	 if(tSel==0||tSel==null)
	 {
			myAlert(""+"����ѡ���������Ϣ��"+"");
			return false;
	 }
	 if(  Mulline10Grid.getRowColData(tSel - 1, 4) == '2' )
	 {
		myAlert(""+"��������ѻظ���"+"");
		return;	
	 }
	 if( fm.ReplyContDesc.value == "" )
	 {
	 	myAlert(""+"�ظ����ݲ���Ϊ�գ�"+"");
	 	return;
	 }
	 var tSerialNo = Mulline10Grid.getRowColData(tSel - 1, 1);
	 fm.all('SerialNo').value = tSerialNo;
	 fm.all('operator').value = 'modify';
	  fm.action="./PDIssueQuerySave.jsp"
	 submitForm();
}
function button55()
{
  top.opener.focus();
	top.close();
}

function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	document.getElementById('Replybutton').disabled=true;
	}

}

function QueryIssue()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDIssueQueryInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	 mySql.addSubPara(fm.BackPost.value);//ָ������Ĳ���
	 mySql.addSubPara(fm.IssueCont.value);//ָ������Ĳ���
	 mySql.addSubPara(fm.IssueState.value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function DownLoadFile(){
	 tSel = Mulline10Grid.getSelNo();
	 if(tSel==0||tSel==null)
	 {
			myAlert(""+"����ѡ���������Ϣ��"+"");
			return false;
	 }
	 
   var FilePath = Mulline10Grid.getRowColData(tSel - 1, 6);  
   if (FilePath==""||FilePath==null){
	   myAlert(""+"û�и���"+","+"���ܽ������ز�����"+"")	
	   return false;
   }   
   //alert(FilePath);
   //var showStr="�����������ݡ���";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
   //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   fm.action = "./DownLoadSave.jsp?FilePath="+FilePath;
   fm.submit();
	}
