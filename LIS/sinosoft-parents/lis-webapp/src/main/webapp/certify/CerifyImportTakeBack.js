var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CerifyImportTakeBackInputSql";
//������Ϣ
function getin(){	
    getImportPath();
    fmload.ImportPath.value = ImportPath;
    
    if (fmload.FileName.value==null||fmload.FileName.value=="")
    {
       alert ("�������ļ���ַ!");	
       return false;
    }
    
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fmload.action = "./CerifyImportTakeBackSave.jsp?ImportPath="+fmload.ImportPath.value;
    fmload.submit(); //�ύ

}

//jinsh------�����ִ���������-------2007-07-02
function afterSubmitDiskInput( FlagStr,content )
{    
    showInfo.close();        
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    { 
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
     
    }  
}

function getImportPath ()
{
	  var strSQL = "";
	  //strSQL = "select SysvarValue from ldsysvar where sysvar ='CertifyImportPath'";
	  var mysql=new SqlClass();
		mysql.setResourceName(tResourceName);
		mysql.setSqlId("querysqldes1");
		mysql.addSubPara("1");
		strSQL = mysql.getString();
	  
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult) 
	  {
		    alert("δ�ҵ��ϴ�·��");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];	  
	  //alert(ImportPath);
	  
	  //ImportPath="D:\\log\\";
}

//��֤״̬��ѯ
function certifyQuery()
{
    if(verifyInput() == false)
    {
	  return;
    }
  CardImportInfoGrid.clearData();//����������������
  var tCom=fmload.dManageCom.value;
  /*var strSQL="select a.certifycode,(select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+" a.startno, a.endno, a.sumcount, a.handler, a.operator, decode(a.state,'Y','�ɹ�','N','ʧ��'),"
		+" a.makedate, a.maketime,a.managecom "
 		+" from LZCardImport a where 1 = 1 and a.operateflag='1'"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.State', 'State')
 		+ getWherePart('a.makedate', 'StartDate','>=')
 		+ getWherePart('a.makedate', 'EndDate','<=');
 		
 	strSQL = strSQL+" and a.managecom like '"+tCom+"%' "
	 			 +" order by a.certifycode, a.startno, a.makedate, a.maketime" ;*/
 	var strSQL = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value,document.all('State').value
 	                                     ,document.all('StartDate').value,document.all('EndDate').value,tCom]);
	
   //	turnPage2.pageLineNum = 10;
   	turnPage2.queryModal(strSQL, CardImportInfoGrid);
   	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(CardImportInfoGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}

//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+CardImportInfoGrid.mulLineCount);
   	if(CardImportInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}	
   	
	easyQueryPrint(2,'CardImportInfoGrid','turnPage2');	
}
