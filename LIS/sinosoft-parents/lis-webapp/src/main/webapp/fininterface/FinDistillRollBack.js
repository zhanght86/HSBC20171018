var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;



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
  easyQueryClick();
}catch(ex){}

}

function rollBack()
{
	var flag = 0;
	var j = 0;
	//�ж��Ƿ���ѡ���ӡ����
	for( j = 0; j < RBResultGrid.mulLineCount; j++ )
	{
		if( RBResultGrid.getSelNo(j) >0)
		{
			flag = 1;
			break;
		}
	}
	//���û�д�ӡ���ݣ���ʾ�û�ѡ��
	if( flag == 0 )
	{
		alert("����ѡ��һ�����ν��л���");
		return false;
	}
        var showStr="������ȡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
 
function easyQueryClick()
{
  if(fm.Bdate.value=='')
  {
		
     alert("��ʼ���ڲ���Ϊ��!");
     return;
  }
  if(fm.Edate.value=='')
  {
     alert("��ֹ���ڲ���Ϊ��!");
     return;
  }
	// ��ʼ�����
  initRBResultGrid();
  var oper="like" ;
  
  // ��дSQL���
  var strSQL = "";
  /**
  strSQL = "select a.parametervalue,a.eventno,b.makedate from FIOperationParameter a ,FIOperationLog b where a.eventno = b.eventno and a.parametertype = 'BatchNo'  and a.eventtype in ('01') " ;
  
  if(fm.Bdate.value!='')
  {
  	strSQL = strSQL + " and b.makedate >='"+fm.Bdate.value+"' ";
  }
  if(fm.Edate.value!=''){
  	strSQL = strSQL + " and b.makedate <='"+fm.Edate.value+"' ";
  }
  strSQL = strSQL + " and (exists (select 1 from FIDataTransGather  where FIDataTransGather.Batchno = a.parametervalue and FIDataTransGather.VoucherNo is null) or not exists( select 1 from FIDataTransGather  where FIDataTransGather.Batchno = a.parametervalue)) and not exists (select * from FIOperationParameter c ,FIOperationLog d where c.eventno = d.eventno and d.performstate = '0' and d.eventtype = '03' and c.parametertype = 'BatchNo' and c.parametervalue = a.parametervalue)";
 */
 	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinDistillRollBackSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FinDistillRollBackSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.Bdate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.Edate.value);//ָ������Ĳ���
		strSQL= mySql1.getString();
 	 
				   		 
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�в�ѯ�����ݣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = RBResultGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet  = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

