//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
//���ƽ�����mulLine����ʾ����
var mulLineShowCount = 15;
//Ϊ������tomcat�ȵĻ���������õ�where�������ʽ����
var sqlcount = 1;
var selectCurPage = 0;
var unselectCurPage = 0;
var userCurPage = 0;
var menuCurPage = 0;
var selectArrayLen = 0;
var unselectArrayLen = 0;
var userArrayLen = 0;
var menuArrayLen = 0;
var selectArray;
var unselectArray;
var userArray;
var menuArray;
var strResult = "";
var mySql = new SqlClass();

function change()
{
	if(!document.all)
		return;
	if (event.srcElement.name=="foldheader")
	{
		var srcIndex = event.srcElement.sourceIndex;
		var nested = document.all[srcIndex+1];
		if (nested.style.display=="none")
		{
			nested.style.display='';
		} else {
			nested.style.display="none";
		}
	}
}
document.onclick=change;
//��ʼ���û���ѡ�˵�������
function initSelectArray()
{
    selectArray = new Array();
    selectArrayLen = 0;
    selectCurPage = 0;
}

//��ʼ���û�δѡ�˵�������
function initUnselectArray()
{
    unselectArray = new Array();
    unselectArrayLen = 0;
    unselectCurPage = 0;
}

//��ʼ���û���
function initUserArray()
{
    userArray = new Array();
    userArrayLen = 0;
    userCurPage = 0;
}

function initMenuArray()
{
	menuArray = new Array();
	menuArrayLen = 0;
	menuCurPage = 0;
}

//����û����multiLine
function fillUserGrid()
{
	UserGrid.clearData("UserGrid");
	for (var i = 0; i < mulLineShowCount; i++)
	{
		UserGrid.addOne("UserGrid");
		var offset = i  + userCurPage*mulLineShowCount;
		if (offset < userArrayLen)
		{
			UserGrid.setRowColData(i,1,userArray[offset][1]);
			UserGrid.setRowColData(i,2,userArray[offset][2]);
			UserGrid.setRowColData(i,3,userArray[offset][3]);
			UserGrid.setRowColData(i,4,userArray[offset][4]);
			UserGrid.setRowColData(i,5,userArray[offset][5]);
			UserGrid.setRowColData(i,6,userArray[offset][18]);
			if (userArray[offset][0] == 0)
				UnselectMenuGrpGrid.checkBoxSel(i+1);
		}
		else
		{
			UserGrid.setRowColData(i,1,"");
			UserGrid.setRowColData(i,2,"");
			UserGrid.setRowColData(i,3,"");
			UserGrid.setRowColData(i,4,"");
		}
	}
	//����Ĵ�����Ϊ��ʹ��ҳʱ�������ȷ��ʾ
	for (var i = 0; i < mulLineShowCount; i++)
	{
		var offset = i  + userCurPage*mulLineShowCount;
		document.getElementsByName("UserGridNo")[i].value = offset + 1;
	}
}

//�����ʾδѡ�˵����multiline
function fillUnselectGrid()
{
   UnselectMenuGrpGrid.clearData("UnselectMenuGrpGrid");

   for (var i = 0; i < mulLineShowCount; i++) {

       UnselectMenuGrpGrid.addOne("UnselectMenuGrpGrid");
   	   var offset = i  + unselectCurPage*mulLineShowCount;

   	   if (offset < unselectArrayLen) {

   	       UnselectMenuGrpGrid.setRowColData(i,1,unselectArray[offset][1]);
   	       UnselectMenuGrpGrid.setRowColData(i,2,unselectArray[offset][2]);
   	       UnselectMenuGrpGrid.setRowColData(i,3,"˫���鿴");
   	       if (unselectArray[offset][0] == 0)
   	           UnselectMenuGrpGrid.checkBoxSel(i+1);
   	   } else {

   	       UnselectMenuGrpGrid.setRowColData(i,1,"");
   	       UnselectMenuGrpGrid.setRowColData(i,2,"");
   	   }
   }

   //����Ĵ�����Ϊ��ʹ��ҳʱ�������ȷ��ʾ
   for (var i = 0; i < mulLineShowCount; i++) {
		var offset = i  + unselectCurPage*mulLineShowCount;
       document.getElementsByName("UnselectMenuGrpGridNo")[i].value = offset + 1;
   }

}

//�����ʾ��ѡѡ�˵����multiline
function fillSelectGrid()
{
   SelectMenuGrpGrid.clearData("SelectMenuGrpGrid");
   for (var i = 0; i < mulLineShowCount; i++) {
   	  SelectMenuGrpGrid.addOne("SelectMenuGrpGrid");
   	  var offset = i  + selectCurPage*mulLineShowCount;
   	  if (offset < selectArrayLen) {
   	      SelectMenuGrpGrid.setRowColData(i,1,selectArray[offset][1]);
   	      SelectMenuGrpGrid.setRowColData(i,2,selectArray[offset][2]);
   	      SelectMenuGrpGrid.setRowColData(i,3,"˫���鿴");
		  if (selectArray[offset][0] == 0)
   	          SelectMenuGrpGrid.checkBoxSel(i+1);
   	  } else {
   	      SelectMenuGrpGrid.setRowColData(i,1,"");
   	      SelectMenuGrpGrid.setRowColData(i,2,"");
   	  }
   }

   //����Ĵ�����Ϊ��ʹ��ҳʱ�������ȷ��ʾ
   for (var i = 0; i < mulLineShowCount; i++) {
		var offset = i  + selectCurPage*mulLineShowCount;
        document.getElementsByName("SelectMenuGrpGridNo")[i].value = offset + 1;
   }
}

//��δѡ�˵����е�ѡ�в˵�������û���ѡ�˵���
function addMenus()
{
	markSelectChk();
	markUnselectChk();
	unselectToSelectArray();
	fillSelectGrid();
	fillUnselectGrid();
}

//����ѡ�˵����е�ѡ�в˵�������û�δѡ�˵���
function removeMenus()
{
	markSelectChk();
	markUnselectChk();
	selectToUnselectArray();
	fillSelectGrid();
	fillUnselectGrid();
}

function userFirstPage()
{
	if (userArrayLen == 0) return;
	userCurPage = 0;
	fillUserGrid();
}

function userLastPage()
{
	if (userArrayLen == 0) return;
	while ((userCurPage + 1)*mulLineShowCount < userArrayLen) userCurPage++;
	fillUserGrid();
}

function userPageDown()
{
	if (userArrayLen == 0) return;
	if (userArrayLen <= (userCurPage + 1) * mulLineShowCount)
	{
		alert("�Ѵ�βҳ");
	}
	else
	{
		userCurPage++;
		fillUserGrid();
	}
}

function userPageUp()
{
	if (userArrayLen == 0) return;
	if (userCurPage == 0)
	{
		alert("�ѵ���ҳ");
	}
	else
	{
		userCurPage--;
		fillUserGrid();
	}
}

function selectFirstPage()
{
	if (selectArrayLen == 0) return;
	selectCurPage = 0;
	fillSelectGrid();
}

function selectLastPage()
{
	if (selectArrayLen == 0) return;
	while ((selectCurPage + 1)*mulLineShowCount < selectArrayLen) selectCurPage++;
	fillSelectGrid();
}

function selectPageDown()
{
	if (selectArrayLen == 0) return;
	if (selectArrayLen <= (selectCurPage + 1) * mulLineShowCount)
	{
		alert("�Ѵ�βҳ");
		return;
	}
	//����ҳ��ѡ����Ϣ���浽array��ȥ
	for (var i = 0; i< mulLineShowCount; i++) {
		var offset = i + selectCurPage * mulLineShowCount;
		if (offset >= selectArrayLen) continue;
		if (SelectMenuGrpGrid.getChkNo(i))
			selectArray[offset][0] = 0;
		else
			selectArray[offset][0] = 1;
	}
	selectCurPage++;
	fillSelectGrid();
}

function selectPageUp()
{
	if (selectArrayLen == 0) return;
	if (selectCurPage == 0)
	{
		alert("�ѵ���ҳ");
		return;
	}
	//����ҳ��ѡ����Ϣ���浽array��ȥ
	for (var i = 0; i< mulLineShowCount; i++)
	{
		var offset = i + selectCurPage * mulLineShowCount;
		if (offset >= selectArrayLen) continue;
		if (SelectMenuGrpGrid.getChkNo(i))
			selectArray[offset][0] = 0;
		else
			selectArray[offset][0] = 1;
	}
	selectCurPage--;
	fillSelectGrid();
}

function unselectFirstPage()
{
	if (unselectArrayLen == 0) return;
	unselectCurPage = 0;
	fillUnselectGrid();
}

function unselectLastPage()
{
	if (unselectArrayLen == 0) return;
	while ((unselectCurPage + 1)*mulLineShowCount < unselectArrayLen) unselectCurPage++;
	fillUnselectGrid();
}

function unselectPageDown()
{
	if (unselectArrayLen == 0) return;
	if (unselectArrayLen <= (unselectCurPage + 1) * mulLineShowCount) {
		alert("�Ѵ�βҳ");
		return;
	}
	markUnselectChk();
	unselectCurPage++;
	fillUnselectGrid();
}

function unselectPageUp()
{
	if (unselectArrayLen == 0) return;
	if (unselectCurPage == 0) {
		alert("�ѵ���ҳ");
		return;
	}
	markUnselectChk();
	unselectCurPage--;
	fillUnselectGrid();
}

//��ѡ�еĲ˵���selectArray�н��б��
function markSelectChk()
{
    var gridCount = SelectMenuGrpGrid.mulLineCount;
    for (var i = 0; i< gridCount; i++) {
    	var offset = i + selectCurPage * mulLineShowCount;

    	if (offset >= selectArrayLen)
    	    continue;

    	if (SelectMenuGrpGrid.getChkNo(i))
    	    selectArray[offset][0] = 0;
    	 else
    	    selectArray[offset][0] = 1;
    }
}

function markUnselectChk()
{
    var gridCount = UnselectMenuGrpGrid.mulLineCount;
    for (var i = 0; i< gridCount; i++) {
    	var offset = i + unselectCurPage * mulLineShowCount;

    	if (offset >= unselectArrayLen)
    	    continue;

    	if (UnselectMenuGrpGrid.getChkNo(i)) {
    	    unselectArray[offset][0] = 0;
    	} else {
    	    unselectArray[offset][0] = 1;
    	}
    }

}

function unselectToSelectArray()
{
	var index = 0;
	while (index < unselectArrayLen)
	{
		if (unselectArray[index][0] == 1)
		{
			index++;
			continue;
		}
		// ����selectArray��
		selectArray[selectArrayLen] = new Array();
		selectArray[selectArrayLen][0] = 1;
		selectArray[selectArrayLen][1] = unselectArray[index][1];
		selectArray[selectArrayLen][2] = unselectArray[index][2];
		selectArrayLen++;
		//��δѡ�˵�������ȥ���˲˵��ڵ�
		for (var i = index+1; i < unselectArrayLen; i++)
			unselectArray[i-1] = unselectArray[i];
		unselectArrayLen--;
	}
}

function selectToUnselectArray()
{
	var index = 0;
	while (index < selectArrayLen)
	{
		if (selectArray[index][0] == 1)
		{
			index++;
			continue;
		}
		// ����unselectArray��
		unselectArray[unselectArrayLen] = new Array();
		unselectArray[unselectArrayLen][0] = 1;
		unselectArray[unselectArrayLen][1] = selectArray[index][1];
		unselectArray[unselectArrayLen][2] = selectArray[index][2];
		unselectArrayLen++;
		//����ѡ�˵�������ȥ���˲˵��ڵ�
		for (var i = index+1; i < selectArrayLen; i++)
			selectArray[i-1] = selectArray[i];
		selectArrayLen--;
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,Result)
{
	showInfo.close();
	var action = document.all("Action").value;
	if (action == "query")
	{
		if (FlagStr == "true")
		{
			queryDetailProcess(Result);
			strResult = Result;
		}
		else
		{
			alert("��ѯʧ��!");
		}
	}
	if (action == "update")
	{
		if (FlagStr == "true")
			alert("�����û��ɹ�!");
		else
			alert("�����û�ʧ��!");
	}
	if (action == "insert")
	{
		if (FlagStr == "true")
			alert("�����û��ɹ�!");
		else
			alert("�����û�ʧ��!");
	}
	if (action == "delete")
	{
		if (FlagStr == "true")
			alert("ɾ���û��ɹ�!");
		else
			alert("ɾ���û�ʧ��!");
			
		queryClick();

	}
//	queryClick();
}

function queryDetailProcess(Result)
{
	var tempArray = decodeEasyQueryResult(Result);
	if (tempArray == null)
	{
		alert("û���ҵ�������Ҫ����û�");
		return;
	}
	initUserArray();
	userArrayLen = tempArray.length;
	for (var i = 0; i < tempArray.length; i++)
	{
		userArray[i] = new Array();
		userArray[i][0] = 1;
		userArray[i][1] = tempArray[i][1];
		userArray[i][2] = tempArray[i][0];
		userArray[i][3] = tempArray[i][5];
		userArray[i][4] = tempArray[i][4];
		userArray[i][5] = tempArray[i][2];
		userArray[i][6] = tempArray[i][12];
		userArray[i][7] = tempArray[i][13];
		userArray[i][8] = tempArray[i][14];
		userArray[i][9] = tempArray[i][15];
		userArray[i][10] = tempArray[i][10];
		userArray[i][11] = tempArray[i][11];
		userArray[i][12] = tempArray[i][7];
		userArray[i][13] = tempArray[i][8];
		userArray[i][14] = tempArray[i][9];
		userArray[i][15] = tempArray[i][6];
		userArray[i][16] = tempArray[i][3];
		userArray[i][17] = tempArray[i][17];
		userArray[i][18] = tempArray[i][18];
	}
	fillUserGrid();
}

//�ύǰ��У�顢����
function beforeSubmit()
{
	if (document.all("Action").value == "query")
		return true;
	if (document.all("UserCode").value == "")
	{
		alert("��¼���û�����");
		return false;
	}
	if (document.all("Action").value == "delete")
		return true;
	//���Ӻ͸��²��������������ǿ�
	var comcode = document.all("ComCode").value;
	if (comcode == null || comcode == "")
	{
		alert("��ѡ���������!");
		return false;
	}
	var pwd = document.all("Password").value;
	if (pwd != document.all("PasswordConfirm").value)
	{
		alert("ȷ�ϵ�������¼������벻һ�£�������¼��!");
		return false;
	}
	//zy 2009-04-23 16:21 �û�����ʱ�����״̬Ҳ����Ϊ��
//	if (document.all("Action").value == "update")
//		return true;
	if (pwd == "")
	{
		alert("��¼������");
		return false;
	}
	if(document.all('UserState').value=="")
	{
		alert("��ѡ���û�״̬");
		return false;
	}
	//��֤��ϯ�˱���־   update by lzj at 2016/5/19
	if(document.all('UserState').value.length > 1){
		alert("��ϯ�˱���־���Ȳ��ܴ���1!");
		document.all('UserState').focus(); //�õ�����
		return false;
	}
	return true;
}

//Click�¼����������ɾ������ťʱ�����ú���
function deleteClick()
{
	//����������Ӧ��ɾ������
	var selUserNo = UserGrid.getSelNo();
	if (selUserNo == 0)
	{
		alert("����û��ѡ����Ҫɾ�����û�");
		return;
	}
	var offset = mulLineShowCount * userCurPage + selUserNo - 1;
	var curOperator = document.all("OperatorCode").value;
	if (curOperator == "")
	{
		alert("��ǰ�����߱���Ϊ�գ��޷�ɾ����");
		return;
	}
	//��ǰ����Ա����ѡ���û��Ĵ�����
	if (curOperator != userArray[offset][11])
	{
		alert("����Ȩɾ�����û���");
		return;
	}
	if (confirm("��ȷʵ��ɾ���ü�¼��?"))
	{
		document.all("Action").value = "delete"
		submitForm();
	}
	else
	{
		mOperate="";
	}
}

//Click�¼�����������Ӱ�ťʱ�����ú���
function insertClick()
{
	var fontTag = document.getElementsByTagName("font");
	for (var i=0;i<fontTag.length;i++)
	{
		fontTag[i].style.display= "";
	}
	//����������Ӧ�Ĵ���
	divUserGrid.style.display = "none";
    if(window.divCmdButton !== undefined && window.divCmdButton !== null && window.divCmdButton.style !== undefined && window.divCmdButton.style !== null)
    	
	divCmdButton.style.display = "none";
	divSubCmdButton.style.display = "";
	divMenuGrpGrid.style.display= "";
	divHideInput.style.display= "";
	initAddGrid();
	document.all("Action").value = "insert";
}

//�û�������°�ťʱ�����ú���
function updateClick()
{
	var fontTag = document.getElementsByTagName("font");
	for (var i=0;i<fontTag.length;i++)
	{
		fontTag[i].style.display= "";
	}
	
	//����������Ӧ�Ĵ���
	var selUserNo = UserGrid.getSelNo();
	if (selUserNo == 0)
	{
		alert("����û��ѡ����Ҫ���µ��û�");
		return;
	}
	//��ѡ����û���Ϣ������������Ϣ�������
	var offset = userCurPage * mulLineShowCount + selUserNo -1;
	document.all("UserName").value = userArray[offset][1];
	document.all("UserNameReadOnly").value = userArray[offset][1];
	document.all("UserCodeReadOnly").value = userArray[offset][2];
	document.all("UserState").value = userArray[offset][3];
	document.all("UserDescription").value = userArray[offset][4];
	document.all("ComCode").value = userArray[offset][5];
	document.all("MakeDate").value = userArray[offset][6];
	document.all("MakeTime").value = userArray[offset][7];
	document.all("ValidStartDate").value = userArray[offset][8];
	document.all("ValidEndDate").value = userArray[offset][9];
	document.all("SuperPopedomFlag").value = userArray[offset][10];
	document.all("Operator").value = userArray[offset][11];
	document.all("ClaimPopedom").value = userArray[offset][12];
	document.all("OtherPopedom").value = userArray[offset][13];
	document.all("PopUWFlag").value = userArray[offset][14];
	document.all("UWPopedom").value = userArray[offset][15];
	document.all("EdorPopedom").value = userArray[offset][17];
	var tPassword = userArray[offset][16];
	//����8λ��ʹ���е����뿴����һ��
	for (var i = tPassword.length + 1; i <= 8; i++)
	{
		tPassword += " ";
	}
	document.all("Password").value = tPassword;
	document.all("PasswordConfirm").value = tPassword;
	//�жϵ�ǰ����Ա�Ƿ���ѡ���û��Ĵ�����
	var curOperator = document.all("OperatorCode").value;
	var crtOperator = document.all("Operator").value;
	if (curOperator == crtOperator)
	{
		//������Ҫ��Ԫ��
		divUserGrid.style.display = "none";
        if(window.divCmdButton && window.divCmdButton.style)
		    divCmdButton.style.display = "none";
		divSubCmdButton.style.display = "";
		divMenuGrpGrid.style.display= "";
		divHideInput.style.display= "";
        if(window.tdUserCode && window.tdUserCode.style)
		    tdUserCode.style.display = "none";
		tdUserCodeReadOnly.style.display = "";
	}
	else
	{
		divUserGrid.style.display = "none";
		divCmdButton.style.display = "none";
		divSubCmdButton.style.display = "";
		divMenuGrpGrid.style.display= "";
		tdUserCode.style.display = "none";
		tdUserCodeReadOnly.style.display = "";
		tdUserName.style.display = "none";
		tdUserNameReadOnly.style.display = "";
		//validTR.style.display = "none";
		divHideInput.style.display= "";
	}
	//ȡ��ѡ���û���Ӧ�Ĳ˵���
	showMenuGrp();
	document.all("Action").value = "update";
}

//����״̬�µ��ȷ����ť�����ú���
function insert()
{
	if(!beforeSubmit()) return;
	var UserCode = document.all("UserCode").value;
	HideMenuGrpGrid1.clearData("HideMenuGrpGrid1");
	for (var i = 0; i < selectArrayLen; i++)
	{
		HideMenuGrpGrid1.addOne("HideMenuGrpGrid1");
		HideMenuGrpGrid1.setRowColData(i,1,UserCode);
		HideMenuGrpGrid1.setRowColData(i,2,selectArray[i][1]);
	}
	submitForm();
}

//����״̬�µ��ȷ����ť�����ú���
function update()
{
	document.all("UserCode").value = document.all("UserCodeReadOnly").value;
	var curOperator = document.all("OperatorCode").value;
	var crtOperator = document.all("Operator").value;
	if (curOperator == crtOperator)
	if (!beforeSubmit()) return;
	var UserCode = document.all("UserCode").value;
	HideMenuGrpGrid1.clearData("HideMenuGrpGrid1");
	for (var i = 0; i < selectArrayLen; i++)
	{
		HideMenuGrpGrid1.addOne("HideMenuGrpGrid1");
		HideMenuGrpGrid1.setRowColData(i,1,UserCode);
		HideMenuGrpGrid1.setRowColData(i,2,selectArray[i][1]);
	}
	document.all("Action").value = "update";
	submitForm();
}

function saveSubmit()
{
	submitForm ();
}

function queryClick()
{

	initUserGrid();
	document.all("Action").value = "query";

	if (!beforeSubmit())
		return;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	 //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	document.getElementById("fm").submit();
}

function unSelectMenu(){
		initUnselectArray();
		 initUnselectMenuGrpGrid();
   var tMenuName =fm.MenuName.value;
   var tMenuDetail =fm.MenuDetail.value;
   var userCode = fm.UserCode.value;
   var operator = document.all("OperatorCode").value;
  /* sqlStr = "select MenuGrpCode,MenuGrpDescription from LDMenuGrp "
		+ "where Operator = '" + operator + "' and "
		+ "MenuGrpCode not in  ( select MenuGrpCode from LDUserToMenuGrp "
		+ "where UserCode = '" + userCode + "')";*/
	
	mySql = new SqlClass();
	mySql.setResourceName("userMan.UserAddInputSql");
	mySql.setSqlId("UserAddSql1");
	mySql.addSubPara(operator); 	
	mySql.addSubPara(userCode); 	
   if(tMenuName!=null&&tMenuName!=""){
      //sqlStr=sqlStr+" and MenuGrpCode like '%%"+tMenuName+"%%'";
      mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql2");
		mySql.addSubPara(operator); 	
		mySql.addSubPara(userCode); 	
		mySql.addSubPara(tMenuName); 
   }
   if(tMenuDetail!=null&&tMenuDetail!=""){
     // sqlStr=sqlStr+" and MenuGrpDescription like '%%"+tMenuDetail+"%%'";
      mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql3");
		mySql.addSubPara(operator); 	
		mySql.addSubPara(userCode); 	
		mySql.addSubPara(tMenuName); 
		mySql.addSubPara(tMenuDetail); 
   }
   strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
   var tempUnselectArray = decodeEasyQueryResult(strTemp);
   if (tempUnselectArray != null) {
		unselectArrayLen = tempUnselectArray.length;
		for (var i = 0; i < tempUnselectArray.length; i++)
		{
			unselectArray[i] = new Array;
			unselectArray[i][0] = 1;
			unselectArray[i][1] = tempUnselectArray[i][0];
			unselectArray[i][2] = tempUnselectArray[i][1];
		}
	}
	fillUnselectGrid();
}

function showMenuGrp()
{
	var selUserNo = UserGrid.getSelNo();
	if (selUserNo == 0)
	{
		alert("����û��ѡ���û�");
		return;
	}
	var offset = userCurPage * mulLineShowCount + selUserNo - 1;
	var userCode = userArray[offset][2];
	var operator = document.all("OperatorCode").value;
	/*var sqlStr = "select MenuGrpCode,MenuGrpDescription from LDMenuGrp "
		+ " where Operator = '" + operator + "' and "
		+ "MenuGrpCode in  ( select MenuGrpCode from LDUserToMenuGrp "
		+ "where UserCode = '"+ userCode + "')";*/
	 mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql4");
		mySql.addSubPara(operator); 	
		mySql.addSubPara(userCode);  
	var strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
	//��ѯ�ɹ������ַ��������ض�ά����
	var tempSelectArray = decodeEasyQueryResult(strTemp);
	/*sqlStr = "select MenuGrpCode,MenuGrpDescription from LDMenuGrp "
		+ "where Operator = '" + operator + "' and "
		+ "MenuGrpCode not in  ( select MenuGrpCode from LDUserToMenuGrp "
		+ "where UserCode = '" + userCode + "')";*/
	mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql5");
		mySql.addSubPara(operator); 	
		mySql.addSubPara(userCode);  
	strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
	var tempUnselectArray = decodeEasyQueryResult(strTemp);
	initSelectArray();
	initUnselectArray();
	if (tempSelectArray != null)
	{
		selectArrayLen = tempSelectArray.length;
		for (var i = 0; i < tempSelectArray.length; i++)
		{
			selectArray[i] = new Array;
			selectArray[i][0] = 1;
			selectArray[i][1] = tempSelectArray[i][0];
			selectArray[i][2] = tempSelectArray[i][1];
		}
	}
	if (tempUnselectArray != null) {
		unselectArrayLen = tempUnselectArray.length;
		for (var i = 0; i < tempUnselectArray.length; i++)
		{
			unselectArray[i] = new Array;
			unselectArray[i][0] = 1;
			unselectArray[i][1] = tempUnselectArray[i][0];
			unselectArray[i][2] = tempUnselectArray[i][1];
		}
	}
	fillUnselectGrid();
	fillSelectGrid();
}

function addSqlcount()
{
	sqlcount++;
	if (sqlcount >= 100)
		sqlcount = 0;
}

//��ʾ�û����еĲ˵���Ĳ˵�
function showSelectGridMenus(param1)
{
	var row = param1.replace(/spanSelectMenuGrpGrid/g,"");
	var MenuGrpCode= document.getElementById("SelectMenuGrpGrid1"+"r"+row).value;
	if (row  > selectArrayLen)
		return;
	// ��ѯ���˲˵����Ӧ�Ĳ˵�����
	/*var sqlStr = "select ParentNodeCode,ChildFlag,nodename,nodecode from LDMenu "
		+ "where NodeCode in (select NodeCode from LDMenuGrpToMenu "
		+ "where MenuGrpCode = '" + MenuGrpCode + "') order by nodeorder";*/
	mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql6");
		mySql.addSubPara(MenuGrpCode); 	 
	var strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
	//��ѯ�ɹ������ַ��������ض�ά����
	var tempSelectArray = decodeEasyQueryResult(strTemp);
	initMenuArray();
	if (tempSelectArray != null)
	{
		//��ʼ��selectArray
		for (var i = 0; i < tempSelectArray.length ; i++)
		{
			menuArray[i] = new Array();
			menuArray[i][0] = 0;
			menuArray[i][1] = tempSelectArray[i][0];
			menuArray[i][2] = tempSelectArray[i][1];
			menuArray[i][3] = tempSelectArray[i][2];
			menuArray[i][4] = tempSelectArray[i][3];
			menuArray[i][5] = 0;
			menuArray[i][6] = 0;
			menuArray[i][7] = 0;
			menuArray[i][8] = "sel_" + tempSelectArray[i][3];
		}
	}
	paintTree_ReadOnly(menuArray,"spanMenuTree");
}

//��ʾ�û������еĲ˵���Ĳ˵�
function showUnselectGridMenus(param1)
{
	var row = param1.replace(/spanUnselectMenuGrpGrid/g,"");
	if (row > unselectArrayLen)
		return;
	var MenuGrpCode = document.getElementById("UnselectMenuGrpGrid1"+"r"+row).value;
	// ��ѯ���˲˵����Ӧ�Ĳ˵�����
	/*var sqlStr = "select ParentNodeCode,ChildFlag,nodename,nodecode from LDMenu  "
		+ "where NodeCode in (select NodeCode from LDMenuGrpToMenu "
		+ "where MenuGrpCode = '" + MenuGrpCode + "') order by nodeorder";*/
	mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql7");
		mySql.addSubPara(MenuGrpCode); 	 
	var strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
	//��ѯ�ɹ������ַ��������ض�ά����
	var tempUnselectArray = decodeEasyQueryResult(strTemp);
	if (tempUnselectArray == null)
	{
		alert("�޷���ѯ���ò˵���Ĳ˵�����!");
		return;
	}
	initMenuArray();
	if (tempUnselectArray != null) {
		//��ʼ��selectArray
		for (var i = 0; i < tempUnselectArray.length ; i++)
		{
			menuArray[i] = new Array();
			menuArray[i][0] = 0;
			menuArray[i][1] = tempUnselectArray[i][0];
			menuArray[i][2] = tempUnselectArray[i][1];
			menuArray[i][3] = tempUnselectArray[i][2];
			menuArray[i][4] = tempUnselectArray[i][3];
			menuArray[i][5] = 0;
			menuArray[i][6] = 0;
			menuArray[i][7] = 0;
			menuArray[i][8] = "unsel_" + tempUnselectArray[i][3];
		}
	}
	paintTree_ReadOnly(menuArray,"spanMenuTree");
}

function cancelClick()
{
	var fontTag = document.getElementsByTagName("font");
	for (var i=0;i<fontTag.length;i++)
	{
		fontTag[i].style.display= "none";
	}
	
	divSubCmdButton.style.display = "none";
	divCmdButton.style.display = "";
	divUserGrid.style.display = "";
	divMenuGrpGrid.style.display = "none";
	divHideInput.style.display= "none";
	validTR.style.display = "";
	tdUserCode.style.display = "";
	tdUserCodeReadOnly.style.display = "none";
	tdUserName.style.display = "";
	tdUserNameReadOnly.style.display = "none";
	clearTree("spanMenuTree");
	initForm();
}

function okClick()
{
	var action = document.all("Action").value;
	if (action == "insert")
	{
		insert()
	}
	if (action == "update")
	{
		update();
	}
}

function initAddGrid()
{
	initSelectArray();
	initUnselectArray();
	//��ѯ�ɹ������ַ��������ض�ά����
	var Operator = document.all('Operator').value; //���Ӻ͸��´���һ��
	//sqlStr = "select MenuGrpCode,MenuGrpDescription from LDMenuGrp where  Operator = '" + Operator + "'";
	mySql = new SqlClass();
		mySql.setResourceName("userMan.UserAddInputSql");
		mySql.setSqlId("UserAddSql8");
		mySql.addSubPara(Operator); 
	var strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
	var tempUnselectArray = decodeEasyQueryResult(strTemp);
	if (tempUnselectArray != null)
	{
		unselectArrayLen = tempUnselectArray.length;
		for (var i = 0; i < tempUnselectArray.length; i++)
		{
			unselectArray[i] = new Array;
			unselectArray[i][0] = 1;
			unselectArray[i][1] = tempUnselectArray[i][0];
			unselectArray[i][2] = tempUnselectArray[i][1];
		}
	}
	fillUnselectGrid();
	fillSelectGrid();
}