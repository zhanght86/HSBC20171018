//�������ƣ�LLSecondUWAllAll.js
//�����ܣ������˹��˱���ȡ����
//�������ڣ�2005-1-28 11:10:36
//������  ��zhangxing
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


//��ѯ��ť------��ѯ[�����˹��˱�����]
function queryClick()
{
	initLLCUWBatchGridQuery();
}
//���ð�ť------
function cancleClick()
{
	fm.QCaseNo.value="";
	fm.QInsuredNo.value="";
	fm.QInsuredName.value="";
	fm.QClaimRelFlag.value="";
	fm.QClaimRelFlagName.value="";
	fm.QMngCom.value="";
	
}

//��ѯ[�����˹��˱�����]------�����
function initLLCUWBatchGridQuery()
{
//	var strSQL="";   	
	var strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,case missionprop5 when '0' then '0--���' when '1' then '1--�����'   end,missionprop20,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000005505' "
           + " and processid = '0000000005'"
           + " and DefaultOperator is null"
           + getWherePart( 'missionprop1' ,'QCaseNo')
           + getWherePart( 'missionprop3' ,'QInsuredNo')
           + getWherePart( 'missionprop4' ,'QInsuredName','like')
           + getWherePart( 'missionprop5' ,'QClaimRelFlag')
           + getWherePart( 'missionprop20' ,'QMngCom')  //<����----�Ƿ���Ҫ������>
           + " order by missionprop1";	
    turnPage.pageLineNum=5;
	turnPage.queryModal(strSQL, LLCUWBatchGrid);
}

//��ѯ[�����˹��˱�����]------���˶���
function initSelfLLCUWBatchGridQuery()
{
//	var strSQL="";   	
	var strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop20,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000005505' "
           + " and processid = '0000000005'"
           + " and DefaultOperator ='"+fm.Operator.value+"'"
           + " order by missionprop1";	
    turnPage2.pageLineNum=5;
	turnPage2.queryModal(strSQL, SelfLLCUWBatchGrid);
}

//[��������]��ť
function applyClick()
{
	var tSel = LLCUWBatchGrid.getSelNo()-1;
	if(tSel<0)
	{
		alert("��ѡ��һ�������¼");
		return;	
	}
	fm.MissionID.value = LLCUWBatchGrid.getRowColData(tSel, 7);
	fm.SubMissionID.value=LLCUWBatchGrid.getRowColData(tSel, 8);
	fm.ActivityID.value= LLCUWBatchGrid.getRowColData(tSel, 9);
	fm.action = "./LLSecondUWAllSave.jsp";
	submitForm(); //�ύ
}
/*********************************************************************
 *  ����:����˱�����
 *********************************************************************
 */
function SelfLLCUWBatchGridClick()
{
	var tSel = SelfLLCUWBatchGrid.getSelNo()-1;
	var tCaseNo = SelfLLCUWBatchGrid.getRowColData(tSel,1); 
	var tBatNo = SelfLLCUWBatchGrid.getRowColData(tSel,2); 	
	var tInsuredNo = SelfLLCUWBatchGrid.getRowColData(tSel,3)  
	var tClaimRelFlag = SelfLLCUWBatchGrid.getRowColData(tSel,5)  	
	var tMissionid = SelfLLCUWBatchGrid.getRowColData(tSel,7)  	
	var tSubmissionid = SelfLLCUWBatchGrid.getRowColData(tSel,8)  	
	var tActivityid = SelfLLCUWBatchGrid.getRowColData(tSel,9)  	
	
	var strURL="./LLSecondUWALLInputMain.jsp";
	strURL += "?CaseNo=" + tCaseNo ;	//�ⰸ��
    strURL += "&BatNo=" + tBatNo ; //���κ�			    
	strURL += "&InsuredNo=" + tInsuredNo ;   //�����˿ͻ��� 
	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //�ⰸ��ر�־ 	
	strURL += "&Missionid=" + tMissionid ;   //����ID 
	strURL += "&Submissionid=" + tSubmissionid ;   //������ID 
	strURL += "&Activityid=" + tActivityid ;   //�ڵ�� 	
	window.location=strURL;
//	window.location="./LLSecondUWALLInputMain.jsp?CaseNo="+tCaseNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo; 
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

    fm.submit(); //�ύ
    tSaveFlag ="0";
}

/*********************************************************************
 *  �ύ�����,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	
	if (FlagStr == "Fail" )
	{                 
		alert(content);
	}
	else
	{ 
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		window.location="LLSecondUWAllInput.jsp";
	}
}

