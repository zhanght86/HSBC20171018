//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


var tResourceName="get.PersonPayPlanCancelSql";
var tResourceSQL1="PersonPayPlanCancelSql1";



//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else 
  {
    parent.fraMain.rows = "0,0,0,82,*";
  }
  parent.fraMain.rows = "0,0,0,0,*";
}


// ��ѯ��ť
function easyQueryClick()
{
  // ��ʼ�����
  initLJSGetGrid();  
  
  divLCGet.style.display ='';
  
  if((document.all('ContNo').value==null||document.all('ContNo').value=="")&&(document.all('GetNoticeNo').value==null||document.all('GetNoticeNo').value==""))
  {
	  alert("�����ź�֪ͨ��Ų���ͬʱΪ�գ�");
	  return;
  }
  // ��дSQL���
  var strSQL = "";	
  //strSQL = "select getnoticeno,otherno,grpcontno,insuredname,sumgetmoney,getdate,ljsget.makedate,ljsget.operator "
  //       + "from ljsget,lccont "
  //       + "where otherno=contno and othernotype='2' and ljsget.managecom like '"+manageCom+"%%' "
  //       + getWherePart( 'LJSGet.GetNoticeNo','GetNoticeNo' )
  //       + getWherePart( 'LJSGet.OtherNo','ContNo' )
  //       + getWherePart( 'LCCont.InsuredName','InsuredName' )
  //       + getWherePart( 'LJSGet.GetDate','GetDate' )
  //       + " order by otherno,getdate desc,getnoticeno";
  strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.manageCom.value,fm.GetNoticeNo.value,fm.ContNo.value,fm.InsuredName.value,fm.GetDate.value]); 
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ��,û�з�������������!");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = LJSGetGrid;    
          
  //����SQL���
  turnPage.strQuerySql = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//�����߸�
function CancelCommit()
{
  var tSel = LJSGetGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  {
    alert( "����ѡ��һ����¼���ٵ�����볷����ť!" );
    return;
  }
    document.all('OutGetNoticeNo').value = LJSGetGrid.getRowColData(tSel-1,1);	
    document.all("fmtransact").value = "DELETE";
    var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

function afterSubmit(FlagStr, content)
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             	
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
}
