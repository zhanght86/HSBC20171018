/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

/*������ҳ��*/
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
/*ϵͳ��ǰ������Ա*/
var mOperate = "";
/*����mysql��ѯ��*/
var mySql = new SqlClass();
/*������Ϣ����*/
var showInfo;
function goBack()
{
    if (document.all("ReturnFlag").value == "N")
    {
        window.returnValue = "true|1|1";
    }
    else
    {
        opener.fileQuery();
    }
    top.close();
}

//add by liuyuxiao 2011-05-24
function afterCodeSelect( cCodeName, Field ){
	if(cCodeName=="querycodetype"){
		if(fm.CodeType.value == 'functionid'){
			document.getElementById('menuNod').style.display = '';
			fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
			fm.OtherSignCode.style.display = '';
        	fm.OtherSignName.style.display = '';
		}
		else{
			fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
        	document.getElementById('menuNod').style.display = 'none';
			fm.OtherSignCode.style.display = 'none';
        	fm.OtherSignName.style.display = 'none';
		}
	}
}

function showData()
{
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("û��ѡ������!");
        return false;
    }
    try
    {
        fm.CodeType.value = TICollectGrid.getRowColData(selno, 1);
        fm.CodeTypeName.value = TICollectGrid.getRowColData(selno, 2);
        fm.Code.value = TICollectGrid.getRowColData(selno, 3);
        fm.CodeName.value = TICollectGrid.getRowColData(selno, 4);
        fm.CodeAlias.value = TICollectGrid.getRowColData(selno, 5);
        //add by liuyuxiao 2011-05-23 �����radioButton���ҳ����OtherSign��������ֵ����ʾ�Ĳ���
        if(fm.CodeTypeQ.value == 'functionid'){
        	document.getElementById('menuNod').style.display = '';
        	fm.OtherSignCode.style.display = '';
        	fm.OtherSignName.style.display = '';
        	fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
        	fm.OtherSignCode.value = TICollectGrid.getRowColData(selno, 6);
        	showOneCodeName('querymenu','OtherSignCode','OtherSignName');	//���÷���������code��name
        }
        else{
        	document.getElementById('menuNod').style.display = 'none';
        	fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
        	fm.OtherSignCode.style.display = 'none';
        	fm.OtherSignName.style.display = 'none';
        }
        //end add by liuyuxiao
    }
    catch(ex)
    {
    }
    return true;
}
/*�ύ����*/
function submitForm()
{
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();

	
    fm.hideOperate.value = mOperate;
    document.getElementById("fm").submit();
}
function afterSubmit(FlagStr, content)
{
    if (typeof(showInfo) == "object")
    {
        showInfo.close();
        if (typeof(showInfo.parent) == "object" && typeof(showInfo.parent) != "unknown")
        {
            showInfo.parent.focus();
            if (typeof(showInfo.parent.parent) == "object" && typeof(showInfo.parent.parent) != "unknown")
            {
                showInfo.parent.parent.blur();
            }
        }
    }
    if (FlagStr == "Fail")
    {
        content = "����ʧ�ܣ�ԭ���ǣ�" + content;
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
        content = "�����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        queryClick();
        
    }
    
    document.all('CodeType').value = "";
    document.all('CodeTypeName').value = "";
    document.all('Code').value = "";
    document.all('CodeName').value = "";
    document.all('CodeAlias').value = "";
}

function queryClick()
{
    if (document.all('CodeTypeQ').value == "")
    {
        alert("�������������!");
        return false;
    }
 
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql1");
    mySql.addSubPara(document.all('CodeTypeQ').value);    
    
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}
function SaveClick()
{
    if (verifyInput2() == false) return false;
    if(fm.CodeType.value=="busitype"&&fm.Code.value.length>4)
    {
       alert("���Ȳ��ܳ���4");
       return false;	
    }
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql2");
    mySql.addSubPara(document.all('CodeType').value); 
    mySql.addSubPara(document.all('Code').value);      
    
    var arr = easyExecSql(mySql.getString());
    if (arr > 0)
    {
        alert("���ݿ��Ѿ����ڸü�¼,��ѡ���޸Ļ�ɾ������!");
        return false;
    }
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "BaseDataManageSave.jsp?OperFlag=INSERT";
    document.getElementById("fm").submit();
}
function ModifyClick()
{
    if (verifyInput2() == false) return false;
    if(fm.CodeType.value=="busitype"&&fm.Code.value.length>4)
    {
       alert("���Ȳ��ܳ���4");
       return false;	
    }    
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql2");
    mySql.addSubPara(document.all('CodeType').value); 
    mySql.addSubPara(document.all('Code').value); 
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,��ѡ�񱣴����!");
        return false;
    }
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "BaseDataManageSave.jsp?OperFlag=MODIFY";
    document.getElementById("fm").submit();
}
function DeleteClick()
{
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql2");
    mySql.addSubPara(document.all('CodeType').value); 
    mySql.addSubPara(document.all('Code').value); 
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,�޷�����ɾ������!");
        return false;
    }
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "BaseDataManageSave.jsp?OperFlag=DELETE";
    document.getElementById("fm").submit();
}