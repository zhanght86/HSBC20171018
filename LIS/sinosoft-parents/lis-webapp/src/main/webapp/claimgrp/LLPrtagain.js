var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ
function initLoprtQuery()
{
	//��ѯ[��ӡ�����----LOPRTManager]
    /*var strSql="select t.prtseq,t.otherno,t.code,t.reqoperator,t.reqcom,t.managecom,t.exeoperator,t.prttype,t.stateflag from loprtmanager t where 1=1 "
			+" and t.prtseq='"+fm.PrtSeq.value+"' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLPrtagainInputSql");
	mySql.setSqlId("LLPrtagainSql1");
	mySql.addSubPara(fm.PrtSeq.value ); 
	var arrLoprt = easyExecSql(mySql.getString());
	fm.PrtSeq.value=arrLoprt[0][0];
	fm.OtherNo.value=arrLoprt[0][1];
	fm.Code.value=arrLoprt[0][2];
	fm.ReqOperator.value=arrLoprt[0][3];
	fm.ReqCom.value=arrLoprt[0][4];
	showOneCodeName('stati','ReqCom','ReqComName')
	fm.ManageCom.value=arrLoprt[0][5];
	fm.ExeOperator.value=arrLoprt[0][6];
	fm.PrtType.value=arrLoprt[0][7];
	fm.StateFlag.value=arrLoprt[0][8];
	
	fm.PrtCode.value=fm.PrtSeq.value;
	fm.ClmNo.value=fm.OtherNo.value;
}

//[����ԭ�򱣴水ť]
function showPrtagainReasion()
{
	fm.action = './LLPrtagainSave.jsp';  
	submitForm();
}

//[����֤��ť]-----���ݲ�ͬ�ĵ�֤������ò�ͬSaveҳ��
function showPrtAffix()
{
	var tcode=fm.Code.value;
	if(tcode=="PCT001"){fm.action = './LLPRTAppraisalSave.jsp';}
	else if(tcode=="PCT002"){fm.action = './LLPRTCertificatePutOutSave.jsp';}
	else if(tcode=="PCT003"){fm.action = './LLPRTCertificateRenewSave.jsp';}
	else if(tcode=="PCT008"){fm.action = './LLPRTPatchFeeSave.jsp';}
	else if(tcode=="PCT007"){fm.action = './LLPRTProtestNoRegisterSave.jsp';}
	else if(tcode=="PCT013"){fm.action = './LLClaimEndCasePrintContSave.jsp';}
	else {fm.action = './LLClaimEndCaseParaPrint.jsp';}
	fm.target = "../f1print";
	fm.submit();
}

//[����]
function goToBack()
{
	top.close();
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

    fm.target="fraSubmit";
    fm.submit(); //�ύ
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,content )
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

        fm.Prtagain.disabled=false;
    }
    tSaveFlag ="0";
}