//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();

var tResourceName="bank.RenewBankDataUndoSql";
var tResourceSQL1="RenewBankDataUndoSql1";
var tResourceSQL2="RenewBankDataUndoSql2";
var tResourceSQL3="RenewBankDataUndoSql3";
var tResourceSQL4="RenewBankDataUndoSql4"; 

//�򵥲�ѯ
function easyQueryClick()
{
	// ��ʼ�����
	initCodeGrid();

	// ��дSQL���
//	var strSQL = "select prtno,ContNo,appntname,riskcode,paytodate,getnoticeno,serialno "
//						 + "from lyrenewbankinfo a "
//						 + "where state = '0' "
//						 + getWherePart('prtno', 'PrtNo') 
//						 + getWherePart('ContNo', 'ContNo') 
//						 + getWherePart('managecom', 'ManageCom','like','0') 
//						 + getWherePart('appntname', 'AppntName') 
//						 + getWherePart('paytodate','StartDate','>=','0')
//						 + getWherePart('paytodate','EndDate','<=','0') 
//						 + " and not exists(select 1 from ljspay where getnoticeno = a.getnoticeno and otherno = a.ContNo and startpaydate = a.paytodate and bankonthewayflag = '1') "
//						 + "order by managecom,1,2 ";
	//alert(strSQL);
	var strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.PrtNo.value,fm.ContNo.value,fm.ManageCom.value,fm.AppntName.value,fm.StartDate.value,fm.EndDate.value]); 
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�в鵽���ݣ�\n��ȷ��ת��״̬��������ֻ�ܲ�ѯ����ת��ȷ�ϵ�δ���̵���Ч����������");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CodeGrid;    
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var tChk = CodeGrid.getChkCount();	
	if( tChk == null || tChk <= 0 )
	{
		alert( "����ѡ���¼���ٵ������ȷ�ϰ�ť��" );
		return false;
	}else{      
  		var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		document.all('undo').disabled = true;
  		document.getElementById("fm").submit(); //�ύ
  	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	document.all('undo').disabled = false;
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  
  easyQueryClick();
}

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
