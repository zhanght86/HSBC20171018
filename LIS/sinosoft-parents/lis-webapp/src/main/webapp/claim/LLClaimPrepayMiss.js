//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
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
	/*strSQL = " select missionprop1, "
	       +" (select codename from ldcode where codetype='llclaimstate' and missionprop2=trim(code)), "
	       +" missionprop3,missionprop4, "
	       + " missionprop5,"
	       + " (case trim(missionprop5) when '0' then '��' when '1' then 'Ů' else '����' end) as SexNA,"
	       + " missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop11,missionprop12,missionprop19,missionprop20,missionid,submissionid,activityid,missionprop18,missionprop2 from lwmission where 1=1 "
         + getWherePart( 'missionprop1','RptNo' )
		     + getWherePart( 'missionprop3','CustomerNo' )
         + getWherePart( 'missionprop4','CustomerName' )
                 + " and activityid = '0000005035'"
                 + " and processid = '0000000005'"
                 + " and missionprop12 = 'A'"  //���Ա���
                 + " and (missionprop11 = '0' or missionprop11 is null)" //  δԤ��       
                 + " and DefaultOperator is null "   //������Ҫ������
                 + " and missionprop20 like '" + fm.ComCode.value + "%%'"  //����
                 + " order by missionprop1";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayMissInputSql");
	mySql.setSqlId("LLClaimPrepayMissSql1");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.CustomerNo.value);
	mySql.addSubPara( fm.CustomerName.value);
	mySql.addSubPara( fm.ComCode.value);
//    alert(strSQL);       
    turnPage.queryModal(mySql.getString()
,LLClaimAuditGrid);
}

// ��ʼ�����LLClaimPrepayGrid��ѯԤ��������У���������Ԥ������
//function LLClaimPrepayGridQuery()
//{
//    //initLLClaimPrepayGrid();
//    var strSQL = "";
//	  /*strSQL = " select missionprop1, "
//	         + " (select codename from ldcode where codetype='llclaimstate' and missionprop2=trim(code)), "
//	         + " missionprop3,missionprop4,"
//	             + " missionprop5,"
//	             + " (case trim(missionprop5) when '0' then '��' when '1' then 'Ů' else '����' end) as SexNA,"
//	             + " missionprop6,missionid,submissionid,activityid,missionprop2,MissionProp13,"
//	             + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
//                 + " and activityid = '0000005025'"
//                 + " and processid = '0000000005'"
//                 + " and defaultoperator = '" + fm.Operator.value +"'"
//                 + " order by AcceptedDate,missionprop1";  */ 
//     mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimPrepayMissInputSql");
//	mySql.setSqlId("LLClaimPrepayMissSql2");
//	mySql.addSubPara( fm.Operator.value);
//
//	  
//	//prompt("Ԥ���������в�ѯ",strSQL);
//    turnPage2.queryModal(mySql.getString(),LLClaimPrepayGrid);
//    HighlightSelfByRow();
//}

//SelfLLClaimEndCaseGrid���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
//function HighlightSelfByRow(){
//    for(var i=0; i<LLClaimPrepayGrid.mulLineCount; i++){
//    	var accepteddate = LLClaimPrepayGrid.getRowColData(i,13); //��������
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
//    	   {
//    		   LLClaimPrepayGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//LLClaimAuditGrid�����Ӧ�ĺ�����
function LLClaimAuditGridClick()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	fm.tRptNo.value = LLClaimAuditGrid.getRowColData(selno, 1);//"�ⰸ��" 
	fm.tRptorState.value = LLClaimAuditGrid.getRowColData(selno, 20);//"����״̬����"
	fm.tCustomerNo.value = LLClaimAuditGrid.getRowColData(selno, 3);//"�����˱���"
	fm.tCustomerName.value = LLClaimAuditGrid.getRowColData(selno, 4);//"���������� 
	fm.tCustomerSex.value = LLClaimAuditGrid.getRowColData(selno, 5);//"�������Ա�"
	fm.tAccDate.value = LLClaimAuditGrid.getRowColData(selno, 7);//"��������" 
	fm.tRgtClass.value = LLClaimAuditGrid.getRowColData(selno, 8);//"��������" 
	fm.tRgtObj.value = LLClaimAuditGrid.getRowColData(selno, 9);//"��������" 
	fm.tRgtObjNo.value = LLClaimAuditGrid.getRowColData(selno, 10);//"��������" 
	fm.tPopedom.value = LLClaimAuditGrid.getRowColData(selno, 11);//"����ʦȨ��" 
	fm.tPrepayFlag.value = LLClaimAuditGrid.getRowColData(selno, 12);//"Ԥ����־" 
	fm.tComeWhere.value = LLClaimAuditGrid.getRowColData(selno, 13);//"����" 
	fm.tAuditer.value = LLClaimAuditGrid.getRowColData(selno, 14);//"��˲�����" 
	fm.tMngCom.value = LLClaimAuditGrid.getRowColData(selno, 15);//"" 	
	fm.tComFlag.value = LLClaimAuditGrid.getRowColData(selno, 19);//"" 	Ȩ�޿缶��־
	fm.tMissionID.value = LLClaimAuditGrid.getRowColData(selno, 16);
	fm.tSubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 17);
	fm.tActivityID.value = LLClaimAuditGrid.getRowColData(selno,18);	
}

//LLClaimPrepayGrid�����Ӧ�ĺ�����
//function LLClaimPrepayGridClick()
//{
//	HighlightSelfByRow();
//	var selno = LLClaimPrepayGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��Ҫ��Ԥ��������ⰸ��");
//	      return;
//	}		
//	var tClmNO = LLClaimPrepayGrid.getRowColData(selno,1);//�ⰸ�ţ������ţ�
//	var tCustomerNo=LLClaimPrepayGrid.getRowColData(selno,3);//�ͻ���
//	var tCustomerName=LLClaimPrepayGrid.getRowColData(selno,4);//�ͻ�����
//	var tCustomerSex=LLClaimPrepayGrid.getRowColData(selno,5);//�Ա�
//	var tMissionID=LLClaimPrepayGrid.getRowColData(selno,8);//����ID
//	var tSubMissionID=LLClaimPrepayGrid.getRowColData(selno,9);//������ID
//	var tActivityID=LLClaimPrepayGrid.getRowColData(selno,10);//��ǰ�ID	
//	var tAuditer=LLClaimPrepayGrid.getRowColData(selno,12);//�����
//	//alert("tAuditer:"+tAuditer);
//	var strUrl="LLClaimPrepayInput.jsp?ClmNo="+tClmNO+"&CustomerNo="+tCustomerNo+"&CustomerName="+tCustomerName+"&CustomerSex="+tCustomerSex+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Auditer="+tAuditer;
////    location.href="LLClaimPrepayInput.jsp?";
//    location.href=strUrl;
//  
//
//}

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
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";    
}

//�ύ�����,����
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
    //LLClaimAuditGridQuery();    // ��ʼ�����LLClaimAuditGrid��ѯ
   // LLClaimPrepayGridQuery();//ˢ�²�ѯԤ��������У���������Ԥ������
    jQuery("#privateSearch").click();//ˢ�¹������� lzf 20130521
}

//modify by lzf 
function LLClaimPrepayGridClick()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��Ԥ��������ⰸ��");
	      return;
	}		
	var tClmNO = PrivateWorkPoolGrid.getRowColData(selno,1);//�ⰸ�ţ������ţ�
	var tCustomerNo=PrivateWorkPoolGrid.getRowColData(selno,3);//�ͻ���
	var tCustomerName=PrivateWorkPoolGrid.getRowColData(selno,4);//�ͻ�����
	var tCustomerSex=PrivateWorkPoolGrid.getRowColData(selno,8);//�Ա�
	var tMissionID=PrivateWorkPoolGrid.getRowColData(selno,19);//����ID
	var tSubMissionID=PrivateWorkPoolGrid.getRowColData(selno,20);//������ID
	var tActivityID=PrivateWorkPoolGrid.getRowColData(selno,22);//��ǰ�ID	
	var tAuditer=PrivateWorkPoolGrid.getRowColData(selno,15);//�����
	//alert("tAuditer:"+tAuditer);
	var strUrl="LLClaimPrepayInput.jsp?ClmNo="+tClmNO+"&CustomerNo="+tCustomerNo+"&CustomerName="+tCustomerName+"&CustomerSex="+tCustomerSex+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Auditer="+tAuditer;
//    location.href="LLClaimPrepayInput.jsp?";
    location.href=strUrl;

}
//end by lzf