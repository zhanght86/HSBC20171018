//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();

//"��ѯ"��ť
function LLClaimAuditGridQueryClick()
{
	LLClaimAuditGridQuery();
}
// ��ʼ�����LLClaimAuditGrid��ѯ
function LLClaimAuditGridQuery()
{
    initLLClaimAuditGrid();
    var strSQL = "";
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop11,missionprop12,missionprop19,missionprop20,missionid,submissionid,activityid,missionprop18 from lwmission where 1=1 "
           + getWherePart( 'missionprop1','RptNo' )
		   + getWherePart( 'missionprop3','CustomerNo' )
           + getWherePart( 'missionprop4','CustomerName' )
           + " and activityid = '0000005035'"
           + " and processid = '0000000005'"
           + " and missionprop12 = 'A'"  //���Ա���
           + " and (missionprop11 = '0' or missionprop11 is null)" //  δԤ��       
           + " and DefaultOperator is null "   //������Ҫ������
           + " order by missionprop1";
//    alert(strSQL);       
    turnPage.queryModal(strSQL,LLClaimAuditGrid);
}

// ��ʼ�����LLClaimPrepayGrid��ѯԤ��������У���������Ԥ������
function LLClaimPrepayGridQuery()
{
    //initLLClaimPrepayGrid();
    var strSQL = "";
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000005025'"
           + " and processid = '0000000005'"
           + " and missionprop11 = '" + fm.Operator.value +"'"
           + " order by missionprop1";     
    turnPage2.queryModal(strSQL,LLClaimPrepayGrid);
}


//LLClaimAuditGrid�����Ӧ�ĺ�����
function LLClaimAuditGridClick()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	fm.tRptNo.value = LLClaimAuditGrid.getRowColData(selno, 1);//"�ⰸ��" 
	fm.tRptorState.value = LLClaimAuditGrid.getRowColData(selno, 2);//"����״̬"
	fm.tCustomerNo.value = LLClaimAuditGrid.getRowColData(selno, 3);//"�����˱���"
	fm.tCustomerName.value = LLClaimAuditGrid.getRowColData(selno, 4);//"���������� 
	fm.tCustomerSex.value = LLClaimAuditGrid.getRowColData(selno, 5);//"�������Ա�"
	fm.tAccDate.value = LLClaimAuditGrid.getRowColData(selno, 6);//"��������" 
	fm.tRgtClass.value = LLClaimAuditGrid.getRowColData(selno, 7);//"��������" 
	fm.tRgtObj.value = LLClaimAuditGrid.getRowColData(selno, 8);//"��������" 
	fm.tRgtObjNo.value = LLClaimAuditGrid.getRowColData(selno, 9);//"��������" 
	fm.tPopedom.value = LLClaimAuditGrid.getRowColData(selno, 10);//"����ʦȨ��" 
	fm.tPrepayFlag.value = LLClaimAuditGrid.getRowColData(selno, 11);//"Ԥ����־" 
	fm.tComeWhere.value = LLClaimAuditGrid.getRowColData(selno, 12);//"����" 
	fm.tAuditer.value = LLClaimAuditGrid.getRowColData(selno, 13);//"��˲�����" 
	fm.tMngCom.value = LLClaimAuditGrid.getRowColData(selno, 14);//"" 	
	fm.tComFlag.value = LLClaimAuditGrid.getRowColData(selno, 18);//"" 	Ȩ�޿缶��־
	fm.tMissionID.value = LLClaimAuditGrid.getRowColData(selno, 15);
	fm.tSubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 16);
	fm.tActivityID.value = LLClaimAuditGrid.getRowColData(selno,17);	
}

//LLClaimPrepayGrid�����Ӧ�ĺ�����
function LLClaimPrepayGridClick()
{

	var selno = LLClaimPrepayGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��Ԥ��������ⰸ��");
	      return;
	}		
	var tClmNO = LLClaimPrepayGrid.getRowColData(selno,1);//�ⰸ�ţ������ţ�
	var tCustomerNo=LLClaimPrepayGrid.getRowColData(selno,3);//�ͻ���
	var tMissionID=LLClaimPrepayGrid.getRowColData(selno,7);//����ID
	var tSubMissionID=LLClaimPrepayGrid.getRowColData(selno,8);//������ID
	var tActivityID=LLClaimPrepayGrid.getRowColData(selno,9);//��ǰ�ID	
	var strUrl="LLClaimPrepayInput.jsp?ClmNo="+tClmNO+"&CustomerNo="+tCustomerNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
//    location.href="LLClaimPrepayInput.jsp?";
    location.href=strUrl;

}

//"����"��ť���Ӵ���˶���������������򽫴���˽ڵ�����������Ԥ���ڵ㣬׼������Ԥ������
function ApplyLLClaimAudit()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}
	fm.action = "./LLClaimPrepayMissSave.jsp";
	submitForm(); //�ύ
}

//�����ύ����
function submitForm()
{
    //�ύ����
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

//�ύ�����,����
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
    LLClaimAuditGridQuery();    // ��ʼ�����LLClaimAuditGrid��ѯ
    LLClaimPrepayGridQuery();//ˢ�²�ѯԤ��������У���������Ԥ������
}
