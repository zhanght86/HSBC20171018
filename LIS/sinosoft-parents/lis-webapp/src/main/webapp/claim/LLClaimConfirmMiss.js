//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var tFirstClmNo ="";
var mySql = new SqlClass();

//�ύ��ɺ���������
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
        //ֱ��ȡ��������ת����������
        var i = LLClaimConfirmGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimConfirmGrid.getRowColData(i,1);
            var tClmState = LLClaimConfirmGrid.getRowColData(i,2);
            var tMissionID = LLClaimConfirmGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimConfirmGrid.getRowColData(i,8);
            var tActivityID = LLClaimConfirmGrid.getRowColData(i,9);
            
            var tBudgetFlag = LLClaimConfirmGrid.getRowColData(i,10);
            var tAuditPopedom = LLClaimConfirmGrid.getRowColData(i,11);
            var tAuditer = LLClaimConfirmGrid.getRowColData(i,12);
            location.href="LLClaimConfirmInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&Auditer="+tAuditer+"&GrpRptNo=&RgtClass=1&RgtObj=1";
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
  	var backstr=document.all("ContNo").value;
  	//alert(backstr+"backstr");
  	mSwitch.deleteVar("ContNo");
	mSwitch.addVar("ContNo", "", backstr);
	mSwitch.updateVar("ContNo", "", backstr);
  	location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("������")
    return;
	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}

	var MissionID = SelfPolGrid.getRowColData(selno, 6);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}
	var NoType = "1";

	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

// ��ʼ�����1
//function queryGrid()
//{
//    //---------------------------------------------------------------------------BEG2005-8-8 15:05
//    //��ѯ����������Ȩ��
//    //---------------------------------------------------------------------------
//    /*var tSql = "select substr(a.UWLevel,2,2) from LLClaimUser a where 1=1 "
//             + " and a.UWFlag = '1' "
//             + " and a.usercode = '" + fm.Operator.value + "'"*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimConfirmMissInputSql");
//	mySql.setSqlId("LLClaimConfirmMissSql1");
//	mySql.addSubPara( fm.Operator.value );
//    var tCheckLevel = easyExecSql(mySql.getString());
//    if (tCheckLevel == null)
//    {
//        tCheckLevel = ""; //��������ֵת��,��Ϊ���������в˵�����Ȩ���û�
//    }
////    var tLevel = parseInt(tCheckLevel);
////    alert(tLevel);
//    //---------------------------------------------------------------------------End
//    
//    //---------------------------------------------------------------------------BEG2005-8-14 16:31
//    //��ѯ�����˻������,��comcode
//    //---------------------------------------------------------------------------
//    var tRank = "";
//    var tComCode = fm.ComCode.value;
//    if (tComCode.length == 2)
//    {
//        tRank = "1";//�ܹ�˾
//    }
//    else
//    {
//        if (tComCode.length == 4)
//        {
//            tRank = "2";//�ֹ�˾
//        }
//    }
//    //---------------------------------------------------------------------------End
//    
//    initLLClaimConfirmGrid();
//    var strSQL = "";
//    //--------------------1------------------------------------------------------------------2----------------------------------------------------------3------------4----------------------------------------5-------------------------------------------------------6-----------7--------8------------9----------10------------11------------12--------
////	strSQL = "select missionprop1,(case missionprop2 when '10' then '����' when '20' then '����'  when '30' then '���' when '40' then '����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid,MissionProp11,MissionProp12,MissionProp13,missionprop10 from lwmission where 1=1 "
////-------------------���������޸�--ע��-----------------------
//	/*strSQL = "select missionprop1,(case missionprop2 when '10' then '����' when '20' then '����'  when '30' then '���' when '40' then '����' end),"
//		   +" missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
//		   +" missionprop6,missionid,submissionid,activityid,MissionProp11,MissionProp12,"
//		   +" MissionProp13,missionprop10 ,MakeDate,(select UserName from LLClaimUser where trim(UserCode) = lwmission.MissionProp13),missionprop20 from lwmission where 1=1 "
//           + " and activityid = '0000005055'"
//           + " and processid = '0000000005'"
//           + " and DefaultOperator is null "
//           + " and MissionProp13 != '" + fm.Operator.value + "'"
//           + getWherePart( 'missionprop1' ,'RptNo')
//           + getWherePart( 'missionprop2' ,'ClmState')
//           + getWherePart( 'missionprop3' ,'CustomerNo')
//           + getWherePart( 'missionprop4' ,'CustomerName','like')
//           + getWherePart( 'missionprop5' ,'customerSex')
//           + getWherePart( 'missionprop6' ,'AccidentDate2')
//           + " and substr(missionprop10,2,2) <= '" + tCheckLevel + "'"
//           + " and (missionprop20 = '" + fm.ManageCom.value + "'"  //����
// //          + " or (substr(missionprop20,1," + tComCode.length + ") like '" + fm.ManageCom.value + "%%')"  //��������
//           + " or (missionprop20 like '" + fm.ManageCom.value + "%%'"  //��������
//           + " and missionprop18 = '" + tRank + "'))"  
//           + " and missionprop8 = '1'"  //�Ǹ���
//           + " order by missionprop1  ";*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimConfirmMissInputSql");
//	mySql.setSqlId("LLClaimConfirmMissSql2");
//	mySql.addSubPara( fm.Operator.value );
//	mySql.addSubPara( fm.RptNo.value );
//	mySql.addSubPara( fm.ClmState.value );
//	mySql.addSubPara( fm.CustomerNo.value );
//	mySql.addSubPara( fm.CustomerName.value );
//	mySql.addSubPara( fm.customerSex.value );
//	mySql.addSubPara( fm.AccidentDate2.value );
//	mySql.addSubPara( tCheckLevel );
//	mySql.addSubPara( fm.ManageCom.value );
//	mySql.addSubPara( fm.ManageCom.value );
//	mySql.addSubPara( tRank );
////--------------------���������޸�--ע��------------------------
//
//    turnPage.queryModal(mySql.getString(),LLClaimConfirmGrid);
//    var rowNum = LLClaimConfirmGrid.mulLineCount;
//	  if (rowNum > 0)
//	  {
//	      tFirstClmNo = LLClaimConfirmGrid.getRowColData(0,1);
//	   }
//    
//}
//
//// ��ʼ�����2
//function querySelfGrid()
//{
//    initSelfLLClaimConfirmGrid();
//    var strSQL = "";
////	strSQL = "select missionprop1,(case missionprop2 when '10' then '����' when '20' then '����'  when '30' then '���' when '40' then '����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid,MissionProp11,MissionProp12,MissionProp13,missionprop10 from lwmission where 1=1 "
//	/*strSQL = "select missionprop1,(case missionprop2 when '10' then '����' when '20' then '����'  when '30' then '���' when '40' then '����' end),"
//		   +" missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
//		   +" (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,MissionProp11,MissionProp12,"
//		   +" MissionProp13,missionprop10,MakeDate,(select UserName from LLClaimUser where trim(UserCode) = lwmission.MissionProp13),missionprop20,"
//		   +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 
//           + " and activityid = '0000005055'"
//           + " and processid = '0000000005'"
//           + " and DefaultOperator = '" + fm.Operator.value + "'"
//           + " and missionprop8 = '1'"  //�Ǹ���
//           + " order by AcceptedDate,missionprop1 ";*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimConfirmMissInputSql");
//	mySql.setSqlId("LLClaimConfirmMissSql3");
//	mySql.addSubPara( fm.Operator.value );
//	
//    //prompt("������ʼ����ѯ�ɵ�ǰ�����������İ���",strSQL);
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimConfirmGrid);
//    HighlightByRow();
//}

////���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
//function HighlightByRow(){
//    for(var i=0; i<SelfLLClaimConfirmGrid.mulLineCount; i++){
//    	var accepteddate = SelfLLClaimConfirmGrid.getRowColData(i,17); //��������
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
//    	   {
//    		   SelfLLClaimConfirmGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//LLClaimConfirmGrid����¼�
function LLClaimConfirmGridClick()
{
}

////SelfLLClaimConfirmGrid����¼�
//function SelfLLClaimConfirmGridClick()
//{
//    HighlightByRow();
//    var i = SelfLLClaimConfirmGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = SelfLLClaimConfirmGrid.getRowColData(i,1);
//        var tClmState = SelfLLClaimConfirmGrid.getRowColData(i,2);
//        var tMissionID = SelfLLClaimConfirmGrid.getRowColData(i,7);
//        var tSubMissionID = SelfLLClaimConfirmGrid.getRowColData(i,8);
//        var tBudgetFlag = SelfLLClaimConfirmGrid.getRowColData(i,10);
//        var tAuditPopedom = SelfLLClaimConfirmGrid.getRowColData(i,11);
//        var tAuditer = SelfLLClaimConfirmGrid.getRowColData(i,12);
//        var tActivityID = SelfLLClaimConfirmGrid.getRowColData(i,9);
//        location.href="LLClaimConfirmInputMain.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&Auditer="+tAuditer+"&ActivityID="+tActivityID+"&GrpRptNo=&RgtClass=1&RgtObj=1";
//    }
//}

//[����]��ť
function ApplyClaim()
{
	var selno = LLClaimConfirmGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}
	if (fm.ComCode.value.length<6&&selno!=0)
	{
	      alert("�밴˳ѡȡ��һ���������İ�����");
	      return;
	}	
		if (LLClaimConfirmGrid.getRowColData(selno, 1)!=tFirstClmNo)
	{
	      alert("�밴˳ѡȡ��һ���������İ�����");
	      return;
	}
	fm.MissionID.value = LLClaimConfirmGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimConfirmGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimConfirmGrid.getRowColData(selno, 9);
	
	fm.action = "./LLReportMissSave.jsp";
	submitForm(); //�ύ
}

//�ύ����
function submitForm()
{
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

//modify by lzf 
//���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var accepteddate = PrivateWorkPoolGrid.getRowColData(i,17); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   PrivateWorkPoolGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//SelfLLClaimConfirmGrid����¼�
function SelfLLClaimConfirmGridClick()
{
    HighlightByRow();
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tClmState = PrivateWorkPoolGrid.getRowColData(i,2);
        
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,18);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,19);
        var tActivityID = PrivateWorkPoolGrid.getRowColData(i,21);
        
        var tBudgetFlag = PrivateWorkPoolGrid.getRowColData(i,13);
        var tAuditPopedom = PrivateWorkPoolGrid.getRowColData(i,14);
        var tAuditer = PrivateWorkPoolGrid.getRowColData(i,15);
       
        location.href="LLClaimConfirmInputMain.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&Auditer="+tAuditer+"&ActivityID="+tActivityID+"&GrpRptNo=&RgtClass=1&RgtObj=1";
    }
}
//end by lzf