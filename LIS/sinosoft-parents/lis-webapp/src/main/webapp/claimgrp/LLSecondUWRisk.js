//�������ƣ�LLSecondUWRisk.js
//�����ܣ����ֺ˱���Ϣ����-------���ⲿ��
//�������ڣ�2005-01-06 11:10:36
//������  ��HYQ
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����

var turnPage = new turnPageClass();
var temp = new Array();
///***************��ѯ�˱�������������Ϣ*********************/
function queryLLRiskGridInfo()
{
	var tSql = "select lcpol.polno,lcpol.mainpolno,lcpol.riskcode,lmrisk.riskname,lcpol.prem,lcpol.amnt,lcpol.cvalidate,lcpol.enddate,lcpol.payintv,lcpol.payyears from lcpol,lmrisk where 1=1"
			+ " and contno='"+document.all('ContNo').value+"'"							
			+ " and lcpol.riskcode = lmrisk.riskcode";	
//	alert(tSql);					
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("������Ϣ��ѯʧ��!");
		return "";
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = LLRiskGrid ;    
	//����SQL���
	turnPage.strQuerySql = tSql ; 
 	//���ò�ѯ��ʼλ��
  	turnPage.pageIndex = 0;  
 	 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  	var arrDataSet  = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	if(arrDataSet.length!=0)
	{
	  for(i=0;i<arrDataSet.length;i++)
	  {
	  	 temp[i] = arrDataSet[i][2];
	  }
	}
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	return true;  					
}


/*********************************************************************
 *  �ӷѳб�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function showAdd()
{
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.InsuredNo.value;  	
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	window.open("../uw/UWManuAddMain.jsp?ContNo="+tContNo+"&InsuredNo="+tInsuredNo,"window1"); 
}

/*********************************************************************
 *  ��Լ�б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function showSpec()
{
	var tContNo=fm.ContNo.value;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	if (tContNo != "" )
	{ 	
		window.open("../uw/UWManuSpecMain.jsp?ContNo="+tContNo,"window1");  	
		showInfo.close();
	}
	else
	{
		showInfo.close();
		alert("���ݴ������!");
	}
}

///***************[ȡ����ť]*********************/
function cancelClick()
{
	fm.UWState.value="";
	fm.UWStateName.value="";
	fm.UWIdea.value="";
}

///***************[ȷ����ť]*********************/
function uwSaveClick()
{
//	alert(1);
//	return;
	var tSelNo =LLRiskGrid.getSelNo()-1;
//	alert(tSelNo);
	if(tSelNo < 0)
	{
		alert("��ѡ�񱣵����֣�");
		return;
	}
	if(fm.UWState.value=="")
	{
		alert("��ѡ�����ֺ˱�����!");
		return;
	}
	
	document.all('PolNo').value = LLRiskGrid.getRowColData(tSelNo,1);	//��������
	
	fm.action = "./LLSecondUWRiskChk.jsp";
 	submitForm(); //�ύ
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.submit(); //�ύ
}
/******************�ύ�����,���������ݷ��غ�ִ�еĲ���******************************/ 
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}