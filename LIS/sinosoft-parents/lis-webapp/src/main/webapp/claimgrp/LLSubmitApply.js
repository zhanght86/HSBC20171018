var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var mySql = new SqlClass(); 
//���水ť
function saveClick()
{ 
	if(fm.SubDesc.value=="" || fm.SubDesc.value ==null )
	{
		alert("����д�ʱ�����");
		return;
	}
	
	//���� �ʱ������ڻ��� ��ѯ �ֹ�˾���κ���Ա�������������
	var tManageCom=fm.ManageCom.value ;
	fm.MngCom.value = tManageCom.substring(0,4); //��½��Ϣ�еĹ��������ǰ��λ���룬�������ʱ��ڵ��л���    

	fm.FilialeDirector.value ="";    //�ֹ�˾���κ���Ա     
    //������Ҫ�ύ�ֶΣ�ͨ����ҳ���ύ�ֶ�Ĭ���ֶ�ֵ   
    fm.SubCount.value = "0";    //�ʱ�����     
    fm.InitPhase.value = "01";   //����׶�
    fm.SubState.value = "0";   //�ʱ�״̬    ��
    fm.fmtransact.value = "INSERT"; //�ύ
    submitForm();
}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action = './LLSubmitApplySave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    tSaveFlag ="0";
}

//��ʼ��ʱ��ѯ--Ϊ������ִ�����������
function initQuery()
{
   /* var tSql = "select name from ldperson where "
             + " customerno = '" + document.all('CustomerNo').value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLSubmitApplyInputSql");
	mySql.setSqlId("LLSubmitApplySql1");
	mySql.addSubPara(document.all('CustomerNo').value );          
    var tName = easyExecSql(mySql.getString());
    if (tName)
    {
        document.all('CustomerName').value = tName;
    }
}
