
//�������ڣ� 
//������   jw
//���¼�¼��  ������    ��������     ����ԭ��/����

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;



function PutCertificate()
{
  var i = 0;
  var flag = 0;
  //�ж��Ƿ���ѡ���ӡ����
  for( i = 0; i < ResultGrid.mulLineCount; i++ )
  {
     if( ResultGrid.getSelNo(i) >0 )
     {
	flag = 1;
	break;
      }
   }
   //���û�д�ӡ���ݣ���ʾ�û�ѡ��
   if( flag == 0 )
   {
	alert("����ѡ��һ����������������");
	return false;
   }	
   var showStr="�������ɸ�����ƾ֤���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   document.getElementById("fm").submit();
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
   initResultGrid();
 }
 catch(ex){}
}



function QueryTask()
{
  if(fm.Bdate.value==''){
      alert("��ʼ���ڲ���Ϊ��");
      return ;
  }
  if(fm.Edate.value==''){
      alert("��ֹ���ڲ���Ϊ��");
      return ;
  }
	// ��ʼ�����
  initResultGrid();
	// ��дSQL���
  var strSQL = "";
  
  /**
  strSQL = "select b.parametervalue,a.makedate,(select min(m.parametervalue) || '��' || max(m.parametervalue) from FIOperationParameter m  where m.eventno = a.eventno and m.parametertype in ('StartDate','EndDate')) as datelenth,a.operator from FIOperationLog  a , FIOperationParameter b where a.eventno = b.eventno and b.EventType = '01'  and b.parametertype = 'BatchNo' and a.PerformState = '0' and a.othernomark= '0' "; 
  strSQL = strSQL + "and a.MakeDate>= to_date('" + fm.Bdate.value + "') and a.MakeDate<= to_date('" + fm.Edate.value + "')";
  strSQL = strSQL + " and not exists (select 1 from FIOperationParameter c , FIOperationLog v where c.eventno = v.eventno and v.performstate = '0' and c.eventtype = '02' and c.parametertype = 'BatchNo' and c.parametervalue = b.parametervalue)"
  strSQL = strSQL + "and (select count(1) from FIAboriginalData t where t.BatchNo =  b.parametervalue) >0 "
 	*/	 
 	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIDistillCertificateInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FIDistillCertificateInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.Bdate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.Edate.value);//ָ������Ĳ���
		strSQL= mySql1.getString();
 
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
    alert("û�в�ѯ������!");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = ResultGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}
