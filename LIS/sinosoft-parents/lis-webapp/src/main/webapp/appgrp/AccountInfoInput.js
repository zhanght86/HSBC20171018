//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug  = "0";
var mOperate="";
var showInfo;
var arrDataSet;
var mSwitch  = parent.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult  = "";
var QueryCount   = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch   = 0;
window.onfocus=myonfocus;

//����У��
function beforeSubmit(){
	if (document.all('GrpName').value == ""){
		alert("�����뵥λ���ƣ�");
		document.all('GrpName').focus();
		return false;
	}
	if (document.all('BusinessType').value == ""){
		alert("��������ҵ���");
		document.all('BusinessType').focus();
		return false;
	}
	return true;
   
}


//�����ӹ�˾
function addRecord()
{

  if(verifyInput() == false)
     return false;
	if (!beforeSubmit())
	{
		return false;
	}	
	document.all('mOperate').value = "INSERT||ACCOUNT";
	if (document.all('mOperate').value == "INSERT||ACCOUNT")
	{
		if (!confirm('�ӹ�˾ '+document.all('GrpName').value+' �µ�ȫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����'))
		{
			return false;
		}
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
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.action="../appgrp/AccountInfoSave.jsp";
	fm.submit(); //�ύ
}

//ɾ���ӹ�˾
function deleteRecord()
{  
		document.all('mOperate').value = "DELETE||ACCOUNT";
    AccountInfoGrid.delBlankLine();
    var showStr="����ɾ��������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
		fm.action="../appgrp/AccountInfoSave.jsp";
    fm.submit();  
	
}


function easyQueryClick(){
	//��ѯ�������µ����ּ���Ҫ��
	strSQL = "select b.customerno, b.GrpName, b.BusinessType, b.GrpNature, b.Peoples, a.AddressNo  from LDGrp b , LCGrpAppnt a "
	   			+" where b.SupCustoemrno = a.CustomerNo and a.GrpContNo = '" +document.all('GrpContNo').value+ "'"
	   			+" order by  customerno"
	   			  
	//alert ( "strSQL:"+ strSQL ) ;
	
  var str  = easyQueryVer3(strSQL, 1, 0, 1);

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//���û����Ҳ���쳣
	if (!turnPage.strQueryResult) {
		//return false;
	}
	else{
		//QueryCount = 1;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = AccountInfoGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
}


function afterSubmit(FlagStr,content){
	showInfo.close();
	unlockScreen('lkscreen');
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	else{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initAccountInfoGrid();
	    easyQueryClick();
	    tSearch = 0;
	    QueryCount = 0;
	}
	document.all('mOperate').value = "";
}

function ChooseAccount(parm1,parm2)
{

//alert( document.all(parm1).all('InpAccountInfoGridSel').value )
//alert( document.all(parm1).all('AccountInfoGrid1').value )

	if ( document.all(parm1).all('InpAccountInfoGridSel').value != "" )
	{
	  	fm.CustomerNo.value = document.all(parm1).all('AccountInfoGrid1').value;
	  	fm.AddressNo.value = document.all(parm1).all('AccountInfoGrid6').value
	}
//  alert(fm.AddressNo.value);
  
}

//������һ��
function returnparent()
{
	top.close();
}
