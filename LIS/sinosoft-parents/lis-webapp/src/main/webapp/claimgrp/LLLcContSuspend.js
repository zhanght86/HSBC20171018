var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ
function initQuery()
{
	var strSQL="";
//########################################################�˴�Ϊ�����ڶ������˵�################	
//	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
//	strSQL = "(select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
//		   + " from LcCont a,LCInsured b where 1=1 and a.contno = b.contno "
//		   + " and b.insuredno = '"+ document.all('InsuredNo').value +"')" 
//		   + " UNION "
//		   + "(select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
//		   + " from LcCont a,lcappnt b where 1=1 and a.contno = b.contno "
//		   + " and b.AppntNo = '"+ document.all('InsuredNo').value +"')";//Ͷ����
////#############################################################################################

	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
	/*strSQL = "select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
		   + " from LcCont a where 1=1 "
		   + " and a.insuredno = '"+ document.all('InsuredNo').value +"'"
		   + " or a.AppntNo = '"+ document.all('InsuredNo').value +"'";  //Ͷ����   */  

	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLLcContSuspendInputSql");
mySql.setSqlId("LLLcContSuspendSql1");
mySql.addSubPara(document.all('InsuredNo').value ); 	   
mySql.addSubPara(document.all('InsuredNo').value ); 	  
	//turnPage.pageLineNum=10;    //ÿҳ10��
    //turnPage.queryModal(strSQL, LLLcContSuspendGrid);
    
	arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLLcContSuspendGrid);
    }
}

//LLLcContSuspendGrid����Ӧ����
function LLLcContSuspendGridClick()
{
	var i = LLLcContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLcContSuspendGrid.getRowColData(i,2);//��ͬ��
}

//�����桱��ť��Ӧ����
function saveClick()
{
    fm.isReportExist.value="false";
	fm.fmtransact.value="update";
	submitForm();
}

//����ѯ����ť
function queryClick()
{
//	//���ȼ����Ƿ�ѡ���¼  
	var row = LLLcContSuspendGrid.getSelNo();
	var tContNo="";
    if (row < 1)
    {
    	alert("��ѡ��һ����¼��");
        return;
    } 
        
    row = row - 1;
    //fm.ContNo.value=LLLcContSuspendGrid.getRowColData(row,2);//��ͬ��
    tContNo=LLLcContSuspendGrid.getRowColData(row,2);//��ͬ��   
    //location.href="../sys/PolDetailQueryMain.jsp?ContNo="+tContNo+"&IsCancelPolFlag=0";
    strUrl="../sys/PolDetailQueryMain.jsp?ContNo="+tContNo+"&IsCancelPolFlag=0";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����
//function goToBack()
//{
//    location.href="LLReportInput.jsp";
//}

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

    fm.action = './LLLcContSuspendSave.jsp';
    fm.submit(); //�ύ
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
    //tSaveFlag ="0";
    initQuery();//ˢ��
}

