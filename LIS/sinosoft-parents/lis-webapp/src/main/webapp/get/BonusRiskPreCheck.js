var showInfo;
var turnPage = new turnPageClass();


function queryData()
{
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('������������������');
		document.all('BonusCalYear').focus();
		return false;
	}
   var sqlid830155420="DSHomeContSql830155420";
var mySql830155420=new SqlClass();
mySql830155420.setResourceName("get.BonusRiskPreCheckInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830155420.setSqlId(sqlid830155420);//ָ��ʹ�õ�Sql��id
mySql830155420.addSubPara(fm.BonusCalRisk.value);//ָ������Ĳ���
mySql830155420.addSubPara(fm.BonusCalYear.value);//ָ������Ĳ���
var strSQL=mySql830155420.getString();
   
//   var strSQL = " select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),decode(state,'0','δ����','1','�Ѹ���')  "
//  	                  + " from LoBonusRiskRem a where 1=1 "
//                          +getWherePart('a.riskcode','BonusCalRisk')
//                          +getWherePart('a.fiscalyear','BonusCalYear')
//                          + " order by riskcode ";                
     turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) 
		{
			alert("��ѯ�����ݣ�");
			return false;
		}	
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,0,turnPage);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = NoBonusRiskGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		var tArr = new Array();
    tArr=turnPage.getData(turnPage.arrDataCacheSet,turnPage.pageIndex,MAXSCREENLINES,turnPage);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(tArr, turnPage.pageDisplayGrid,turnPage);
        	
}


function dealData()
{
  var tFlag=false;
  for(var k=0;k<NoBonusRiskGrid.mulLineCount;k++)
  {
  	
  	if(NoBonusRiskGrid.getChkNo(k))
  	{
  		tFlag=true;
  		}
  }
  if(tFlag)
  {
  document.all('transact').value='UPDATE';
	var showStr="����׼���������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
  	}else
  {
  	alert("������ѡ��һ��");
  			
}

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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
    parent.fraInterface.document.all('compute').disabled = false;
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	parent.fraInterface.document.all('compute').disabled = false;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
   initNoBonusRiskGrid();  
   queryData();
}




