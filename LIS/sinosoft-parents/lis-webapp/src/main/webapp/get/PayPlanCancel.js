//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 

var tResourceName="get.PayPlanCancelSql";
var tResourceSQL1="PayPlanCancelSql1";

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
	
	if(trim(fm.GrpContNo.value)==""){
 		alert("��¼���ŵ���");
 		return;
 	}
  // ��ʼ�����
  initLJSGetGrid();  
  
  divLCGet.style.display ='';
	
  // ��дSQL���
  var strSQL = "";	
 // strSQL = "select ljsget.getnoticeno,ljsget.otherno,LCCont.GrpContNo,LCCont.insuredname,ljsget.sumgetmoney,ljsget.getdate,ljsget.makedate,ljsget.operator "
 //        + "from ljsget,LCCont "
 //        + "where ljsget.otherno=LCCont.ContNo and ljsget.othernotype='2' and ljsget.managecom like '"+manageCom+"%%' and LCCont.GrpContNo!='00000000000000000000'"
 //        + getWherePart( 'LJSGet.GetDate','GetDate' )
 //        + getWherePart( 'LCCont.GrpContNo','GrpContNo' )
 //        + " order by ljsget.getnoticeno";
	strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.manageCom.value,fm.GetDate.value,fm.GrpContNo.value]); 
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
    alert( "����ѡ��һ����¼���ٵ�����볷����ť!" );
  else if(trim(fm.all('CancelReason').value)=="")
    alert( "�����볷��ԭ��!" );
  else
  {
    fm.all('OutGetNoticeNo').value = LJSGetGrid.getRowColData(tSel-1,1);	
    fm.all("fmtransact").value = "DELETE||LFGET";
    // showSubmitFrame(mDebug);
    var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit();
  }
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
    initForm();
  }
}
