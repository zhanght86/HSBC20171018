// ���¼�¼��BOM���µ����̴���
// ������:
// ��������: 2008-8-15
// ����ԭ��/���ݣ�

var showInfo;
// �ύ�����水ť��Ӧ����
function defineMultLanguage()
{
	var tBOMName = document.all('eName').value;
	addMsgResult(tBOMName,1,'BOM');
}
function addMsgResult(tKeyID,tEditFlag,tMsgType)
{
	dialogURL="../ibrms/LDMsgBOMMain.jsp?KeyID="+tKeyID+"&EditFlag="+tEditFlag+"&MsgType="+tMsgType;  
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1000;      //�������ڵĿ��; 
	var iHeight=500;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (dialogURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function updateBom() {
	document.all("Transact").value = "UPDATE";
	if (verifyInput()) {
		document.getElementById("fm").submit();
		var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ showStr;
		//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	// showInfo.close();
}

function afterCodeSelect(coname, Field) {
	try {
		if (coname == 'ibrmsfBOM') {
			var bomtem = document.all('fBOM').value;
//			var fitemsql = "select name,cnname from lrbomitem where bomname='"
//					+ bomtem + "'";
			var fitemsql = "";
		    var sqlid9="bomUpdateSql9";
			var mySql9=new SqlClass();
			mySql9.setResourceName("ibrms.bomUpdateSql"); 
			mySql9.setSqlId(sqlid1);
			mySql9.addSubPara(bomtem);
			fitemsql=mySql9.getString();
			var tem = easyQueryVer3(fitemsql, 1, 1, 1);
			if (tem == false) {
				document.all('fatherItem').CodeData = ''
			} else {
				document.all('fatherItem').CodeData = tem;
			}
		}
	} catch (ex) {

	}
}

// ���ȡ��ʱ���ص�ҳ��
function returnParent() {
	document.location.href = "bomMain.jsp";
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	// alert(FlagStr);
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;
		alert("�޸�BOM��Ϣ�ɹ�");
		returnParent();
	}
}
