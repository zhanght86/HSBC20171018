 
//�������ڣ� 
//������   jw
//���¼�¼��  ������    ��������     ����ԭ��/����
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;





function queryRBResultGrid()
{
	initResultGrid();
	var strSQL = ""; 
	/**
	strSQL = "select a.businessno,a.certificateid,sum(case when b.finitemtype = 'C' then b.summoney else 0 end),a.accountdate,a.batchno,b.managecom, (select max(c.voucherno) from fidatatransgather c where c.batchno = a.batchno and c.accountdate =a.accountdate and c.certificateid in (select d.codealias from ficodetrans d where d.codetype = 'BigCertificateID' and d.code = a.certificateid ) and c.managecom = b.managecom ) from fiaboriginaldatatemp a,fidatatransresult b where a.aserialno = b.aserialno and a.certificateid = '" + 
	document.all('CertificateId').value + "' and a.businessno = '" + document.all('BusinessNo').value + "' group by a.businessno,a.certificateid,a.accountdate,a.batchno,b.managecom";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FICertificateRBDealInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FICertificateRBDealInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('CertificateId').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('BusinessNo').value);//ָ������Ĳ���
		strSQL= mySql1.getString();
	
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	if (!turnPage.strQueryResult)
	{
	   return false;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = RBDealResultGrid;
	//����SQL���
		
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	

	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
}

function ImportData1()
{
	
    fm.action="./FinReAppSinggleDataExcel.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ	
	
}


function ImportData2()
{
	
    fm.action="./FinReAppSinggleResultExcel.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ	
	
}

function ReDistill()
{
    var showStr="������ȡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FIDistillDataSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ	
	
}

function ReCertificate()
{

    var showStr="��������ƾ֤�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./FICertificateDataSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ		
	
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
  queryRBResultGrid();

 }
 catch(ex){}

}