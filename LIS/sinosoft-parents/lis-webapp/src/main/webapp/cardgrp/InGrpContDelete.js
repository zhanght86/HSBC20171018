
var turnPage = new turnPageClass();
var showInfo;

function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

function querygCont()
{
	initGrpGrid();
	var strSQL = "select a.ContNo,a.InsuredNo,a.InsuredName,decode(a.insuredsex,0,'��',1,'Ů',2,'����',''),a.InsuredBirthday,a.InsuredIDType,a.InsuredIDNo from LCCont a where "
    				 + " a.GrpContNo='"+ GrpContNo +"'";
  				 
  if(fm.ContNo.value.length!=0)  strSQL = strSQL + " and a.ContNo='"+ fm.ContNo.value +"'"; 
  if(fm.InsuredNo.value.length!=0)     strSQL = strSQL + " and a.InsuredNo='"+ fm.InsuredNo.value +"'";
  
  
  //alert(strSQL);
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    
  
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}


function deleteCont()
{
  var i = 0;
	var flag = 0;
	for( i = 0; i < GrpGrid.mulLineCount; i++ ) {
		if( GrpGrid.getChkNo(i) == true ) {
			//���������ѡ����ֱ���˳�ѭ��������
			flag = 1;
			break;
		}
	}
	if( flag == 0 ) {
		alert("����ѡ��һ����¼");
		return false;
	}
  
  if (!confirm("ȷ��Ҫɾ���ñ�����"))
	{
		return;
	}
    
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  lockScreen('lkscreen');  
  fm.submit();

}

function afterSubmit( FlagStr, content )
{ 
	unlockScreen('lkscreen');  
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    alert(content);
  }
  else
  { 
    alert("�����ɹ�");
  }
   querygCont();
  
}	