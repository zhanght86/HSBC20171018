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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if( cDebug == "1" )
  {
    parent.fraMain.rows = "0,0,50,82,*";
  }
  else
  {
    parent.fraMain.rows = "0,0,0,72,*";
  }
}

function returnparent()
  {
    var backstr=document.all("ContNo").value;
    mSwitch.deleteVar("ContNo");
    mSwitch.addVar("ContNo", "", backstr);
    mSwitch.updateVar("ContNo", "", backstr);
    location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}


//��ʾ����ҳ��
function newReport()
{
    if(document.all('ManageCom').value.length==2){
    	alert("�ܹ�˾���ܽ��б���������");
    	return false;
    }
    location.href="LLGrpReportInput.jsp?isNew=0";
    
}


// ��ʼ�����2
function querySelfGrid()
{
  
    initSelfLLReportGrid();
    var strSQL = "";
    var tManageCom    = fm.ManageCom.value;
    strSQL = "select missionprop1,missionprop2,missionprop15,missionprop16,missionprop7,makedate,missionid,submissionid,activityid,"
             +" (case missionprop9 when '11' then '��ͨ����' when '03' then '��������' when '02' then '�˻�����' end),"
             + " (select case count(*) when 0 then '��' else '��' end from es_doc_main where doccode=lwmission.missionprop1),"
             +" (select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0')"
             +" from lwmission where 1=1 "
             + " and activityid = '0000009005' "
             + " and processid = '0000000009'"
             + " and DefaultOperator = '" + fm.Operator.value +"'"
             + " and missionprop20 like '"+tManageCom+"%%' ";
	  /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql1");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); */
    if (fm.CaseNo.value != '')  //�ⰸ��
      strSQL = strSQL + " AND MissionProp1 = '" + fm.CaseNo.value + "'";
    /*   mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql2");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); */
    if (fm.CustomerNo.value != '')  //����ͻ���
      strSQL = strSQL + " AND missionprop3 = '" + fm.CustomerNo.value + "'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql3");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); 
		mySql.addSubPara(fm.CustomerNo.value); */
    if (fm.CustomerName.value != '')  //��λ����
      strSQL = strSQL + " AND missionprop4 = '" + fm.CustomerName.value + "'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql4");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); 
		mySql.addSubPara(fm.CustomerNo.value); 
		mySql.addSubPara(fm.CustomerName.value); */
    if (fm.rgtstate.value != '')  //��λ����
      strSQL = strSQL + " AND missionprop15 = '" + fm.rgtstate.value + "'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql5");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); 
		mySql.addSubPara(fm.CustomerNo.value); 
		mySql.addSubPara(fm.CustomerName.value); 
  	    mySql.addSubPara(fm.rgtstate.value); */
    //�������������ѯ
    var blDateStart = !(document.all('RgtDateStart').value == null || 
          document.all('RgtDateStart').value == "");
    var blDateEnd = !(document.all('RgtDateEnd').value == null ||
          document.all('RgtDateEnd').value == "");
    if(blDateStart && blDateEnd) {
     strSQL = strSQL + " AND missionprop6 BETWEEN '" + 
          document.all('RgtDateStart').value + "' AND '" +
          document.all('RgtDateEnd').value + "'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql6");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); 
		mySql.addSubPara(fm.CustomerNo.value); 
		mySql.addSubPara(fm.CustomerName.value); 
  	    mySql.addSubPara(fm.rgtstate.value);
  	     mySql.addSubPara( document.all('RgtDateStart').value); 
  	      mySql.addSubPara(document.all('RgtDateEnd').value);  */
    } else if (blDateStart) {
      strSQL = strSQL + " AND missionprop6 >= '" + document.all('RgtDateStart').value + "'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql7");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); 
		mySql.addSubPara(fm.CustomerNo.value); 
		mySql.addSubPara(fm.CustomerName.value); 
  	    mySql.addSubPara(fm.rgtstate.value);
  	     mySql.addSubPara( document.all('RgtDateStart').value); */
  	     
    } else if (blDateEnd) {
      strSQL = strSQL + " AND missionprop6 <= '" + document.all('RgtDateEnd').value + "'";
     /*   mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql8");
		mySql.addSubPara(fm.Operator.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CaseNo.value); 
		mySql.addSubPara(fm.CustomerNo.value); 
		mySql.addSubPara(fm.CustomerName.value); 
  	    mySql.addSubPara(fm.rgtstate.value);
  	    mySql.addSubPara( document.all('RgtDateEnd').value); */
    }

   strSQL = strSQL + " order by missionprop1";
    
//    prompt("�����������г�ʼ����ѯ:",strSQL);
    turnPage2.queryModal(strSQL,SelfLLReportGrid);
}


//SelfLLReportGrid����¼�
function SelfLLReportGridClick() {
	
    var i = SelfLLReportGrid.getSelNo();
    if(document.all('ManageCom').value.length==2){
    	alert("�ܹ�˾���ܽ��б���������");
    	return false;
    }
    //var tsub = new Array;
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLReportGrid.getRowColData(i,1);
        var tMissionID = SelfLLReportGrid.getRowColData(i,7);
        var tSubMissionID = SelfLLReportGrid.getRowColData(i,8);
        
        //var tsql="select rgtState from llreport where rptno='"+tClmNo+"'";        
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportMissInputSql");
		mySql.setSqlId("LLGrpReportMissSql9");
		mySql.addSubPara(tClmNo); 
		
        var tAvaliReason = easyExecSql(mySql.getString());        
        //alert("tAvaliReason:"+tAvaliReason);
        location.href="LLGrpReportInput.jsp?claimNo="+tClmNo+"&isNew=1&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&rgtstate="+tAvaliReason;
    }
}

