//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
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
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //ֱ��ȡ��������ת����������
        var i = LLClaimAuditGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimAuditGrid.getRowColData(i,1);
            var tClmState = LLClaimAuditGrid.getRowColData(i,2);
            var tMissionID = LLClaimAuditGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimAuditGrid.getRowColData(i,8);
            var tBudgetFlag = LLClaimAuditGrid.getRowColData(i,14);
            var tAuditPopedom = LLClaimAuditGrid.getRowColData(i,13);
            location.href="LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&GrpRptNo=&RgtClass=1&RgtObj=1";
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
function queryGrid()
{
    //---------------------------------------------------------------------------BEG2005-8-8 15:05
    //��ѯ���������Ȩ��
    //---------------------------------------------------------------------------
    /*
    var tSql = "select substr(a.checklevel,2,2) from LLClaimUser a where 1=1 "
             + " and a.checkflag = '1' "
             + " and a.usercode = '" + fm.Operator.value + "'"
*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql1");
	mySql.addSubPara( fm.Operator.value );

    var tCheckLevel = easyExecSql(mySql.getString());
//    var tLevel = parseInt(tCheckLevel);
//    alert(tLevel);
    //---------------------------------------------------------------------------End

    //---------------------------------------------------------------------------BEG2005-8-14 16:31
    //��ѯ�����˻������,��comcode
    //---------------------------------------------------------------------------
    var tRank = "";
    var tComCode = fm.ComCode.value;
    if (tComCode.length == 2)
    {
        tRank = "1";//�ܹ�˾
    }
    else
    {
        if (tComCode.length == 4)
        {
            tRank = "2";//�ֹ�˾
        }
    }
    //---------------------------------------------------------------------------End
    
    initLLClaimAuditGrid();
    var strSQL = "";
    //---------------------1-------------------------------------------2-----------------------------------3---------------------4-----------------------------------------------------5-----------------------------------------6----------7-----------8----------9-------------------------------------------10--------------------------------------------------------------------------11---------------------------------------------12------------------------------------------------------------------------------------13-----------------------------------14
//	strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid,(case MissionProp11 when '0' then '��Ԥ��' when '1' then '��Ԥ��' end),(case MissionProp7 when '1' then '����' when '2' then '����' end),(case MissionProp12 when 'A' then '����' when 'B' then '�������Ϊ��' when 'C' then '�������Ϊ��' when 'D' then '���װ���' end),MissionProp10,MissionProp11 from lwmission where 1=1 "
	/*
	strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),"
		   + "missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
		   + "missionprop6,missionid,submissionid,activityid,"
		   + "(case MissionProp11 when '0' then '��Ԥ��' when '1' then '��Ԥ��' end),"
		   + "(case MissionProp7 when '1' then '����' when '2' then '����' end),"
		   + "(case MissionProp12 when 'A' then '����' when 'B' then '�������Ϊ��' when 'C' then '�������Ϊ��' when 'D' then '���װ���' end),"
		   + "MissionProp10,MissionProp11,MakeDate,"
//		   + " (select UserName from LLClaimUser where UserCode = (select operator from llregister where trim(rgtno) = lwmission.missionprop1)),"
		   + " (select UserName from LLClaimUser where UserCode = (select defaultoperator from lbmission where activityid = '0000005015' and processid = '0000000005' and missionprop1 = lwmission.missionprop1 and rownum = 1)),"
		   + " missionprop20 from lwmission where 1=1 "  
           + " and activityid = '0000005035'"   
           + " and processid = '0000000005'"
           + " and DefaultOperator is null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           + " and MissionProp19 = '0'"     //û��ԭ�����
           + " and substr(missionprop10,2,2) <= '" + tCheckLevel + "'" //���Ȩ���ж�
           + " and (missionprop20 = '" + fm.ManageCom.value + "'"  //����
//           + " or (substr(missionprop20,1," + tComCode.length + ") like '" + fm.ManageCom.value + "'"  //��������
           + " or (missionprop20 like '" + fm.ManageCom.value + "%%'"  //��������
           + " and missionprop18 = '" + tRank + "'))"
           + " and missionprop8 = '1'"   
           + " order by missionprop1";
    */       
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql2");
	mySql.addSubPara( fm.RptNo.value );
	mySql.addSubPara( fm.ClmState.value );
	mySql.addSubPara( fm.CustomerNo.value );
	mySql.addSubPara( fm.CustomerName.value );
	mySql.addSubPara( fm.customerSex.value );
	mySql.addSubPara( fm.AccidentDate2.value );
	mySql.addSubPara( tCheckLevel );
	mySql.addSubPara( fm.ManageCom.value );
	mySql.addSubPara( fm.ManageCom.value );
	mySql.addSubPara( tRank  );
	
	
    turnPage.queryModal(mySql.getString(),LLClaimAuditGrid);
//    alert(strSQL);
}

// ��ʼ�����2
//function querySelfGrid()
//{
//    initSelfLLClaimAuditGrid();
//    var strSQL = "";
////	strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid,(case MissionProp11 when '0' then '��Ԥ��' when '1' then '��Ԥ��' end),(case MissionProp7 when '1' then '����' when '2' then '����' end),(case MissionProp12 when 'A' then '����' when 'B' then '�������Ϊ��' when 'C' then '�������Ϊ��' when 'D' then '���װ���' end),MissionProp10,MissionProp11 from lwmission where 1=1 "
//	/*
//	strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),"
//		   +" missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
//		   +" (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,"
//		   +" (case MissionProp11 when '0' then '��Ԥ��' when '1' then '��Ԥ��' end),"
//		   +" (case MissionProp7 when '1' then '����' when '2' then '����' end),"
//		   +" (case MissionProp12 when 'A' then '����' when 'B' then '���' when 'C' then '����' when 'D' then '���װ���' end),"
//		   +" MissionProp10,MissionProp11,MakeDate,"
////		   +" (select UserName from LLClaimUser where UserCode = (select operator from llregister where trim(rgtno) = lwmission.missionprop1)),"
//		   +" (select UserName from LLClaimUser where UserCode = (select defaultoperator from lbmission where activityid = '0000005015' and processid = '0000000005' and missionprop1 = lwmission.missionprop1 and rownum = 1)),"
//		   +" missionprop20,(select accepteddate from llregister where rgtno=lwmission.missionprop1) Accepteddate,"
//		   +" (Case (select 1 from llaffix where rgtno=lwmission.missionprop1 And Rownum=1)"
//		   +" When 1 Then (Case (select 1 from llaffix where rgtno=lwmission.missionprop1 And (subflag is null or subflag='1') And Rownum=1) When 1 Then 'δȫ������' Else '�ѻ���'  End) Else 'δ����' End ) from lwmission where 1=1 "  
//           + " and activityid = '0000005035'"
//           + " and processid = '0000000005'"
//           + " and (DefaultOperator = '" + fm.Operator.value + "'"
//           + " or MissionProp19 = '" + fm.Operator.value + "')"
//           + " and missionprop8 = '1'"
//           + " order by AcceptedDate,missionprop1  ";
//    */       
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
//	mySql.setSqlId("LLClaimAuditMissSql3");
//	mySql.addSubPara( fm.Operator.value );
//	mySql.addSubPara( fm.Operator.value );
//	
//	
//    //prompt("��˽����ʼ����ѯ",strSQL);
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimAuditGrid);
//    HighlightByRow();
//}

////���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
//function HighlightByRow(){
//    for(var i=0; i<SelfLLClaimAuditGrid.mulLineCount; i++){
//    	var accepteddate = SelfLLClaimAuditGrid.getRowColData(i,18); //��������
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
//    	   {
//    	    	SelfLLClaimAuditGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//LLClaimAuditGrid����¼�
function LLClaimAuditGridClick()
{
}

////SelfLLClaimAuditGrid����¼�
//function SelfLLClaimAuditGridClick()
//{
//    HighlightByRow();
//    var i = SelfLLClaimAuditGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = SelfLLClaimAuditGrid.getRowColData(i,1);
//        var tClmState = SelfLLClaimAuditGrid.getRowColData(i,2);
//        var tCustomerno = SelfLLClaimAuditGrid.getRowColData(i,3);
//        
//        var tMissionID = SelfLLClaimAuditGrid.getRowColData(i,7);
//        var tSubMissionID = SelfLLClaimAuditGrid.getRowColData(i,8);
//        var tActivityid = SelfLLClaimAuditGrid.getRowColData(i,9);
//        
//        var tBudgetFlag = SelfLLClaimAuditGrid.getRowColData(i,14);
//        var tAuditPopedom = SelfLLClaimAuditGrid.getRowColData(i,13);
//
//        if (checkAudit(tClmNo))
//        {
//        	//ת�����
//        	location.href="../claim/LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Activityid="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&GrpRptNo=&RgtClass=1&RgtObj=1";
//            
//        } 
//        else
//        {
//        	//ת��Ԥ��
//            location.href="../claim/LLClaimPrepayAuditMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tCustomerno+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&RgtClass=1&RgtObj=1";      	
//        }
//    }
//}

//[����]��ť
function ApplyClaim()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}
	fm.MissionID.value = LLClaimAuditGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimAuditGrid.getRowColData(selno, 9);
	
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


/**
 * 2009-01-04
 * zhangzheng
 * �ж���ת��Ԥ�����滹����˽���
 * return false ת��Ԥ��
 * return true ת�����
**/
function checkAudit(pClmNo)
{/*
    var strSql ="select * from LLPrepayClaim c"
                + " where c.ClmNo ='" + pClmNo + "'";
  */              
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql4");
	mySql.addSubPara( pClmNo );

	
    //prompt("strSql",strSql);
    var tFlag = easyExecSql(mySql.getString());
  
    if (tFlag)
    {/*
        strSql = "select nvl(sum(d.Pay),0) from ljagetclaim d where othernotype='5'  and feeoperationtype='B'"
                   + " and d.otherno ='" + pClmNo + "'";
        */
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditMissInputSql");
		mySql.setSqlId("LLClaimAuditMissSql5");
		mySql.addSubPara( pClmNo );
	
        //prompt("У���Ƿ�������Ԥ��������sql:",strSql);
        
        tFlag = easyExecSql(mySql.getString());
        
        //alert("tFlag[0][0]:"+tFlag[0][0]);

        //Ϊ�ձ�ʾû�����
        if (tFlag==null||tFlag[0][0]=='0')
        {
            return false;
        }
    }
     
    return true;
}

//modify by lzf
//���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var accepteddate = PrivateWorkPoolGrid.getRowColData(i,20); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   PrivateWorkPoolGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//SelfLLClaimAuditGrid����¼�
function SelfLLClaimAuditGridClick()
{
    HighlightByRow();
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tClmState = PrivateWorkPoolGrid.getRowColData(i,2);
        var tCustomerno = PrivateWorkPoolGrid.getRowColData(i,3);
        
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,22);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,23);
        var tActivityid = PrivateWorkPoolGrid.getRowColData(i,25);
        
        var tBudgetFlag = PrivateWorkPoolGrid.getRowColData(i,17);//MissionProp11
        var tAuditPopedom = PrivateWorkPoolGrid.getRowColData(i,16);//missionprop10

        if (checkAudit(tClmNo))
        {
        	//ת�����
        	location.href="../claim/LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Activityid="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&GrpRptNo=&RgtClass=1&RgtObj=1";
            
        } 
        else
        {
        	//ת��Ԥ��
            location.href="../claim/LLClaimPrepayAuditMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tCustomerno+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&RgtClass=1&RgtObj=1";      	
        }
    }
}

//end