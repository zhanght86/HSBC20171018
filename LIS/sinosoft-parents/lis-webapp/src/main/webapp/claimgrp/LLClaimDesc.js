var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��Ӱ�ť
function saveClick()
{
    //******************************************************
    //������Ҫ�ύ�ֶΣ�ͨ����ҳ���ύ�ֶ�Ĭ���ֶ�ֵ
    //******************************************************
  ��//�ύ
    fm.fmtransact.value = "INSERT";
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


//    showSubmitFrame(mDebug);
    fm.action = './LLClaimDescSave.jsp';
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


        //��ʼ���ɱ༭����
        initForm();
        //��ӵ�mulline
        addMulline();
    }
    tSaveFlag ="0";
}

//����Mulline
function addMulline()
{
    //����ɹ��󷵻�
    /*var strSQL = "select clmno,customerno,descno,desctype,describe,operator,makedate,maketime from LLClaimDesc where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' )
                 + " order by clmno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimDescInputSql");
	mySql.setSqlId("LLClaimDescSql1");
	mySql.addSubPara(fm.ClmNo.value );              
    var arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,LLSubReportDescGrid);
    }
}

//ѡ��LLSubReportDescGrid��Ӧ�¼�
function LLSubReportDescGridClick()
{

	//�ÿ���ر�
//    fm.DescType.value = "";
    fm.Describe.value = "";
//    fm.addClick.disabled = true;
    //ȡ������
    var i = LLSubReportDescGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
//        fm.DescType.value = LLSubReportDescGrid.getRowColData(i,4);
        fm.Describe.value = LLSubReportDescGrid.getRowColData(i,5);
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }

}
