 
//�������ڣ� 
//������   jw
//���¼�¼��  ������    ��������     ����ԭ��/����
var showInfo;
var showDealWindow;

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;



function AppDeal()
{

  var i = 0;
  var flag = 0;
  for( i = 0; i < RBResultGrid.mulLineCount; i++ )
  {
  	if( RBResultGrid.getSelNo(i) >0 )
  	{
  		flag = 1;
  		break;
  	}
  }

  if( flag == 0 )
  {
  	alert("����ѡ��һ�������¼");
  	return false;
  }
  showDealWindow = window.open("./FICertificateRBDealForOther.jsp?BusinessNo='" + document.all('BusinessNo').value +"'&CertificateId='" + document.all('CertificateId').value + "'&AppNo='" + document.all('AppNo').value + "'"); 

}


function AppDelete()
{
  var i = 0;
  var flag = 0;
  for( i = 0; i < RBResultGrid.mulLineCount; i++ )
  {
  	if( RBResultGrid.getSelNo(i) >0 )
  	{
  		flag = 1;
  		break;
  	}
  }

  if( flag == 0 )
  {
  	alert("����ѡ��һ�������¼");
  	return false;
  }   
  
    var showStr="����ȷ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FICertificateRBSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ	
  
}



function ReConfirm()
{

    var showStr="����ȷ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FICertificateConfirmSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ	
	
}



function queryResultGrid()
{
    
    initResultGrid() ;
     
    var strSQL = ""; 
    /**
    strSQL = "select AppNo,CertificateID,DetailIndexID,BusinessNo,ReasonType,DetailReMark,AppDate,Applicant,AppState from FIDataFeeBackApp where AppState not in ('04','05')";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FICertificateRBInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FICertificateRBInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(1);//ָ������Ĳ���
		strSQL= mySql1.getString();
	
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    if (!turnPage.strQueryResult)
    {
         alert("δ��ѯ���������������ݣ�");
	 return false;
    }
    
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    turnPage.pageLineNum = 30 ;
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = RBResultGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL ;
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
}


function ReturnData()
{
        var arrReturn = new Array();
	var tSel = RBResultGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )

	    alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{

	   document.all('AppNo').value = RBResultGrid.getRowColData(tSel-1,1);		
	   document.all('CertificateId').value = RBResultGrid.getRowColData(tSel-1,2);	
	   document.all('DetailIndexID').value = RBResultGrid.getRowColData(tSel-1,3);
	   document.all('BusinessNo').value = RBResultGrid.getRowColData(tSel-1,4);
           document.all('ReasonType').value = RBResultGrid.getRowColData(tSel-1,5);
	   document.all('DetailReMark').value = RBResultGrid.getRowColData(tSel-1,6);    
	   document.all('AppDate').value = RBResultGrid.getRowColData(tSel-1,7);      
           document.all('Applicant').value = RBResultGrid.getRowColData(tSel-1,8);
           showCodeName();
 
	   		
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{ 
	 
 try
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

  document.all('AppNo').value = "";		
  document.all('CertificateId').value = "";	
  document.all('CertificateIdName').value = "";	  
  document.all('DetailIndexName').value = "";	
  document.all('DetailIndexID').value = "";
  document.all('BusinessNo').value = "";
  document.all('ReasonType').value = "";
  document.all('ReasonTypeName').value = "";
  document.all('DetailReMark').value = "";    
  document.all('AppDate').value = "";      
  document.all('Applicant').value = "";

  queryResultGrid();

 }
 catch(ex){}

}