//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();

//�ύǰ��У�顢����  
function beforeSubmit()
{
    //��������У��
    if (fm.RptNo.value == ""
       && fm.ClmState.value == ""
       && fm.AccidentDate.value == ""
       && fm.CustomerNo.value == ""
       && fm.CustomerName.value == ""
       //&& fm.AccidentDate2.value == ""
       && fm.RgtDate.value == ""
       && fm.EndDate.value == ""
       && fm.ContNo.value == ""
       && fm.CalManageCom.value == ""
       && fm.RgtDateStart.value == ""
       && fm.RgtDateEnd.value == ""
       //&& fm.GrpClmNo.value == ""
       && fm.GrpContNo.value == ""
       )
    {
        alert("����������һ������!");
        return false;
    }
    
    //ѡ���ⰸ״̬��������������������
    //���ǲ�ѯЧ����� 2006-1-10 15:11 ����
    if (fm.ClmState.value != "" &&fm.ClmState.value != "05"  && fm.RptNo.value == "" && fm.RgtDateStart.value == "" && fm.RgtDateEnd.value == "")
    {
        alert("ѡ���ⰸ״̬��������������");
        return false;
    }
    if (fm.ClmState.value == "05"  && fm.RptNo.value == "" && fm.ScanDateStart.value == "" && fm.ScanDateEnd.value == "")
    {
        alert("������ɨ������");
        return false;
    }
    
    //�����Ż�,�������������������
    //2006-1-23 14:45 ����
    var tName = fm.CustomerName.value;
    if (tName != "" && tName.length < 2)
    {
        alert("���ͻ�������ѯ������Ҫ�����������!");
        return false;
    }
    
    return true;
}         

// ��ʼ�����1
function queryGrid()
{
    if (!beforeSubmit())
    {
        return;
    }
    initLLClaimQueryGrid();
    var strSQL = "";
    
    
    //��ѯ�����Ժ���Ϣ
   /* strSQL = "(select a.rgtno,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '35' then 'Ԥ������' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end),b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.AccDate "
           + " from llregister a,llcase b "
           + " where a.rgtno = b.caseno "
           + getWherePart( 'a.rgtno' ,'RptNo') //�ⰸ��
           + getWherePart( 'a.clmstate' ,'ClmState') //�ⰸ״̬
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���
           //+ getWherePart( 'b.AccDate' ,'AccidentDate2') //��������
           + getWherePart( 'a.RgtDate' ,'RgtDate') //��������
           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //�᰸����*/
    		
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql1");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			
           if (fm.CustomerName.value != "")  //����������
           {
           //strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql2");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			
           }
           
           if (fm.ContNo.value != "")  //��ͬ��
           {
        	//   strSQL = strSQL + " and b.rgtno in (select ClmNo from LLClaimPolicy where ContNo ='" + fm.ContNo.value + "') ";
           	 mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql3");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
           }
           
           if (fm.CalManageCom.value != "")  //��������
           {
           //strSQL = strSQL + " and a.MngCom like '" + fm.CalManageCom.value + "%%'";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql4");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
           }
           
           if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
           {
           //strSQL = strSQL + " and a.RgtDate >= '" + fm.RgtDateStart.value + "'";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql5");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
           }
           
           if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
           {
           //strSQL = strSQL + " and a.RgtDate <= '" + fm.RgtDateEnd.value + "'";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql6");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
           }
           
           //if (fm.GrpClmNo.value != "")  //�����ⰸ��
           //{
           //strSQL = strSQL + " and a.rgtobjno = '" + fm.GrpClmNo.value + "'";
           //}
           
           if (fm.GrpContNo.value != "")  //���屣����
           {
        	//   strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') " ;
           	mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql7");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
           }
           
           //if (fm.AccidentDate2.value != ""){//�¹�����
        	   //strSQL = strSQL + " and ( AccDate = '"+fm.AccidentDate2.value+"' or medaccdate = '"+fm.AccidentDate2.value+"')"
           //}
           
          // strSQL = strSQL + " )";
           
    //���ϲ�ѯ������Ϣ
    if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "" && fm.CalManageCom.value == "")
    {
    /*strSQL = strSQL + " union "
           + "(select a.rptno,'����',b.customerno,c.name,(case c.sex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.AccDate "
           + " from llreport a,llsubreport b,ldperson c "
           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
           + getWherePart( 'a.rptno' ,'RptNo') //�ⰸ��
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���*/
//           + getWherePart( 'b.AccDate' ,'AccidentDate2'); //��������
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql8");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			
           if (fm.CustomerName.value != "")  //����������
           {
          // strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql9");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
           }
    
           if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
           {
         //  strSQL = strSQL + " and a.RptDate >= '" + fm.RgtDateStart.value + "'";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql10");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
           }
           
           if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
           {
          // strSQL = strSQL + " and a.RptDate <= '" + fm.RgtDateEnd.value + "'";
            mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql11");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
           }
           
           //if (fm.GrpClmNo.value != "")  //�����ⰸ��
           //{
           //strSQL = strSQL + " and a.rgtobjno = '" + fm.GrpClmNo.value + "'";
           //}
           
           if (fm.GrpContNo.value != "")  //���屣����
           {
          // strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') ";
           mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql12");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.CalManageCom.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.CustomerName.value ); 
			mySql.addSubPara(fm.RgtDateStart.value ); 
			mySql.addSubPara(fm.RgtDateEnd.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
           }
           
           //if (fm.AccidentDate2.value != ""){//�¹�����
        	  // strSQL = strSQL + " and ( AccDate = '"+fm.AccidentDate2.value+"' or medaccdate = '"+fm.AccidentDate2.value+"')"
           //}
         //  strSQL = strSQL + " )";
    }
    //��������
   // strSQL = strSQL + " order by 1";

    turnPage.queryModal(mySql.getString(),LLClaimQueryGrid);
}

//LLClaimQueryGrid����¼�
function LLClaimQueryGridClick()
{
    var i = LLClaimQueryGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
        var tPhase = LLClaimQueryGrid.getRowColData(i,2);
        var tCustomerNo=LLClaimQueryGrid.getRowColData(i,3);
        var clmType=tClmNo.substr(11,1);

        if (tPhase == '����' && clmType=="6")               
        {
             var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=1";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
        else if (tPhase == '����' && clmType=="5")               
        {
            var strUrl="LLClaimReportQueryMain.jsp?claimNo="+tClmNo+"&customerNo="+tCustomerNo+"&Flag=query";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
        else if(tPhase == 'ɨ�����δ����'||tPhase == '����������'||tPhase == '�������δ����')
        {
        		alert("û����Ӧ�İ�����Ϣ");
        }
        else
        {
        	  var strUrl="LLClaimQueryInputMain.jsp?claimNo="+tClmNo+"&phase=0";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

        }
    }
}

function NewqueryGrid()
{
	  if (!beforeSubmit())
    {
        return;
    }
    initLLClaimQueryGrid();
     var strSQL ="";
//    if(fm.ClmState.value=="05")
//    {
//    		     	//�鿴�Ƿ�����ǰɨ�蹤����
//	   /* strSQL = "select missionprop1,decode(missionprop2,'21','ɨ�����δ����','22','����������','�������δ����'),'','','', missionprop11,missionprop13 "
//	     	     + "from lwmission where  activityid='0000005010' "
//	     	     + getWherePart( 'missionprop1' ,'RptNo') //�ⰸ��
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateStart','>=') //ɨ������
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateEnd','<='); //ɨ��ֹ��*/
//	     mySql = new SqlClass();
//			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
//			mySql.setSqlId("LLClaimQueryMissSql13");
//			mySql.addSubPara(fm.RptNo.value ); 
//			mySql.addSubPara(fm.ScanDateStart.value ); 
//			mySql.addSubPara(fm.ScanDateEnd.value );  
//	     	     
   // }
    //else
    //{	
//	   strSQL = "select a.rgtno,(case a.clmstate  when '20' then '����' when '30' then '���' when '35' then 'Ԥ������' when '40' then '����' when '50' then '�᰸' when '70' then '�ر�' end), "
//	    			 + "b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),to_char(b.AccDate,'yyyy-mm-dd'),'','' "
//			       + " from llregister a,llcase b "
//	           + " where a.rgtno = b.caseno "
//	           + getWherePart( 'a.rgtno' ,'RptNo') //�ⰸ��
//	           + getWherePart( 'a.clmstate' ,'ClmState') //�ⰸ״̬
//	           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
//	           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���
//	           + getWherePart( 'a.RgtDate' ,'RgtDate') //��������
//	           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //�᰸����
////	    mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql14");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value );  
//	   
//			
//	     if (fm.CustomerName.value != "")  //����������
//	     {
//	     strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
////	     mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql15");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value ); 
////			mySql.addSubPara(fm.CustomerName.value ); 
//	     }
//	     
//	     if (fm.ContNo.value != "")  //��ͬ��
//	     {
//	  	  strSQL = strSQL + " and b.rgtno in (select ClmNo from LLClaimPolicy where ContNo ='" + fm.ContNo.value + "') ";
////	    	 mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql16");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value ); 
////			mySql.addSubPara(fm.CustomerName.value ); 
////			mySql.addSubPara(fm.ContNo.value ); 
//	     }
//	     
//	     if (fm.CalManageCom.value != "")  //��������
//	     {
//	    strSQL = strSQL + " and a.MngCom like '" + fm.CalManageCom.value + "%%'";
////	     mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql17");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value ); 
////			mySql.addSubPara(fm.CustomerName.value ); 
////			mySql.addSubPara(fm.ContNo.value ); 
////			mySql.addSubPara(fm.CalManageCom.value ); 
//	     }
//	     
//	     if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
//	     {
//	     strSQL = strSQL + " and a.RgtDate >= '" + fm.RgtDateStart.value + "'";
////	     mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql18");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value ); 
////			mySql.addSubPara(fm.CustomerName.value ); 
////			mySql.addSubPara(fm.ContNo.value ); 
////			mySql.addSubPara(fm.CalManageCom.value ); 
////			mySql.addSubPara(fm.RgtDateStart.value ); 
//	     }
//	     
//	     if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
//	     {
//	     strSQL = strSQL + " and a.RgtDate <= '" + fm.RgtDateEnd.value + "'";
////	     mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql19");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value ); 
////			mySql.addSubPara(fm.CustomerName.value ); 
////			mySql.addSubPara(fm.ContNo.value ); 
////			mySql.addSubPara(fm.CalManageCom.value ); 
////			mySql.addSubPara(fm.RgtDateStart.value ); 
////			mySql.addSubPara(fm.RgtDateEnd.value ); 
//	     }
//	     
//	     if (fm.GrpContNo.value != "")  //���屣����
//	     {
//	  	   strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') " ;
////	     mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql20");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ClmState.value ); 
////			mySql.addSubPara(fm.AccidentDate.value );  
////			mySql.addSubPara(fm.CustomerNo.value ); 
////			mySql.addSubPara(fm.RgtDate.value ); 
////			mySql.addSubPara(fm.EndDate.value ); 
////			mySql.addSubPara(fm.CustomerName.value ); 
////			mySql.addSubPara(fm.ContNo.value ); 
////			mySql.addSubPara(fm.CalManageCom.value ); 
////			mySql.addSubPara(fm.RgtDateStart.value ); 
////			mySql.addSubPara(fm.RgtDateEnd.value ); 
////			mySql.addSubPara(fm.GrpContNo.value ); 
//	     }
//	     

		    var  RptNo32 = window.document.getElementsByName(trim("RptNo"))[0].value;
			var  ClmState32 = window.document.getElementsByName(trim("ClmState"))[0].value;
			var  AccidentDate32 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
			var  CustomerNo32 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
			var  RgtDate32 = window.document.getElementsByName(trim("RgtDate"))[0].value;
			var  EndDate32 = window.document.getElementsByName(trim("EndDate"))[0].value;
		    mySql32 = new SqlClass();
			mySql32.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql32.setSqlId("LLClaimQueryMissSql32");
			mySql32.addSubPara(RptNo32 ); //ָ���������
			mySql32.addSubPara(ClmState32); //ָ���������
			mySql32.addSubPara(AccidentDate32);  //ָ���������
			mySql32.addSubPara(CustomerNo32); //ָ���������
			mySql32.addSubPara(RgtDate32); //ָ���������
			mySql32.addSubPara(EndDate32);  //ָ���������
			mySql32.addSubPara(fm.CustomerName.value);  //ָ���������
			mySql32.addSubPara(fm.ContNo.value);  //ָ���������
			mySql32.addSubPara(fm.CalManageCom.value);  //ָ���������
			mySql32.addSubPara(fm.RgtDateStart.value);  //ָ���������
			mySql32.addSubPara(fm.RgtDateEnd.value);  //ָ���������
			mySql32.addSubPara(fm.GrpContNo.value);  //ָ���������
			strSQL = mySql32.getString();
	     
	     var arrResult=easyExecSql(strSQL);
	     //û������
//	     if(arrResult==null)
//	     {
//	     	//�����ţ�״̬���ͻ����룬�ͻ��������Ա�
//	     	//�鿴�Ƿ�����ǰɨ�蹤����
//	     /*strSQL = "select missionprop1,decode(missionprop2,'21','ɨ�����δ����','22','����������','�������δ����'),'','','', missionprop11,missionprop13 "
//	     	     + "from lwmission where  activityid='0000005010' "
//	     	     + getWherePart( 'missionprop1' ,'RptNo') //�ⰸ��
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateStart','>=') //ɨ������
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateEnd','<='); //ɨ��ֹ��*/
//	        mySql = new SqlClass();
//			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
//			mySql.setSqlId("LLClaimQueryMissSql21");
//			mySql.addSubPara(fm.RptNo.value ); 
//			mySql.addSubPara(fm.ScanDateStart.value ); 
//			mySql.addSubPara(fm.ScanDateEnd.value );   	     
//	     }
    //}
		if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "" && fm.CalManageCom.value == "")
	  {
     	//��ѯ������Ϣ
			var bstrSQL="";
	    if(fm.RptNo.value!="")
	    {
//	    	bstrSQL = "select subrptno,'����',a.customerno,a.customername,(case a.sex when '0' then '��' when '1' then 'Ů'else'����' end),'','','' from llsubreport a ,llregister b "
//	     	     	 	 +" where (a.subrptno=b.rgtobjno or a.subrptno=b.rgtno)  "
//	     	       	 + getWherePart( 'b.rgtno' ,'RptNo') //�ⰸ��
//	     	         + getWherePart( 'b.AccidentDate' ,'AccidentDate') //�¹ʷ�������
//	     	         + getWherePart( 'a.CustomerNo' ,'CustomerNo','like'); //�����˱���;
////	     	 mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql22");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );  
////	     	mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  
//			         
//	    	
//	    	
//	     	if (fm.CustomerName.value != "")  //����������
//	      {
//	       bstrSQL = bstrSQL + " and a.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql23");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );  
////	     	
////	     	mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );
////			mySql.addSubPara(fm.CustomerName.value );  
// 
//	      }
//	
//	      if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
//	      {
//	      bstrSQL = bstrSQL + " and b.RptDate >= '" + fm.RgtDateStart.value + "'";
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql24");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );  
////	     	
////	     	mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );
////			mySql.addSubPara(fm.CustomerName.value );  
////			mySql.addSubPara(fm.RgtDateStart.value );  
//	      }
//	       
//	      if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
//	      {
//	     bstrSQL = bstrSQL + " and b.RptDate <= '" + fm.RgtDateEnd.value + "'";
////	       mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql25");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );  
////	     	
////	     	mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );
////			mySql.addSubPara(fm.CustomerName.value );  
////			mySql.addSubPara(fm.RgtDateStart.value );  
////			mySql.addSubPara(fm.RgtDateEnd.value );  
//	      }
//	
//	       
//	      if (fm.GrpContNo.value != "")  //���屣����
//	      {
//	      bstrSQL = bstrSQL + " and b.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') ";
////	       mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql26");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );  
////	     	
////	     	mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );
////			mySql.addSubPara(fm.CustomerName.value );  
////			mySql.addSubPara(fm.RgtDateStart.value );  
////			mySql.addSubPara(fm.RgtDateEnd.value );  
////			mySql.addSubPara(fm.GrpContNo.value );  
//	      }
//	   	
	        var  RptNo33 = window.document.getElementsByName(trim("RptNo"))[0].value;
			var  ClmState33 = window.document.getElementsByName(trim("ClmState"))[0].value;
			var  AccidentDate33 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
			var  CustomerNo33 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
			var  RgtDate33 = window.document.getElementsByName(trim("RgtDate"))[0].value;
			var  EndDate33 = window.document.getElementsByName(trim("EndDate"))[0].value;
//	        var  RptNo33 = window.document.getElementsByName(trim("RptNo"))[0].value;
//	 		var  AccidentDate33 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
//	 		var  CustomerNo33 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
	 	    mySql33 = new SqlClass();
	 		mySql33.setResourceName("claim.LLClaimQueryMissInputSql");
	 		mySql33.setSqlId("LLClaimQueryMissSql33");
	 		mySql33.addSubPara(RptNo33 ); //ָ���������
			mySql33.addSubPara(ClmState33); //ָ���������
			mySql33.addSubPara(AccidentDate33);  //ָ���������
			mySql33.addSubPara(CustomerNo33); //ָ���������
			mySql33.addSubPara(RgtDate33); //ָ���������
			mySql33.addSubPara(EndDate33);  //ָ���������
			mySql33.addSubPara(fm.CustomerName.value);  //ָ���������
			mySql33.addSubPara(fm.ContNo.value);  //ָ���������
			mySql33.addSubPara(fm.CalManageCom.value);  //ָ���������
			mySql33.addSubPara(fm.RgtDateStart.value);  //ָ���������
			mySql33.addSubPara(fm.RgtDateEnd.value);  //ָ���������
			mySql33.addSubPara(fm.GrpContNo.value);  //ָ���������
	 		mySql33.addSubPara(RptNo33 ); //ָ���������
	 		mySql33.addSubPara(AccidentDate33);  //ָ���������
	 		mySql33.addSubPara(CustomerNo33); //ָ���������
	 		mySql33.addSubPara(fm.CustomerName.value ); //ָ���������
	 		mySql33.addSubPara(fm.RgtDateStart.value);  //ָ���������
	 		mySql33.addSubPara(fm.RgtDateEnd.value); //ָ���������
	 		mySql33.addSubPara(fm.GrpContNo.value); //ָ���������
//	 		bstrSQL = mySql33.getString();
	 		strSQL = mySql33.getString();
	    	
	    
	    }
	    else
	    {
//	     bstrSQL = "select subrptno,'����',a.customerno,a.customername,(case a.sex when '0' then '��' when '1' then 'Ů'else'����' end),'','',''  from llsubreport a ,llregister b where 1=1  "
//	     	       + getWherePart( 'b.AccidentDate' ,'AccidentDate') //�¹ʷ�������
//	     	       + getWherePart( 'a.CustomerNo' ,'CustomerNo','like'); //�����˱���;*/
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql27");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );   
////			
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  
	     
	 		
//	     	if (fm.CustomerName.value != "")  //����������
//	      {
//	       bstrSQL = bstrSQL + " and a.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
////	       mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql28");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );   
////			
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  	
////			mySql.addSubPara(fm.CustomerName.value );  
//	      }
//	
//	      if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
//	      {
//	       bstrSQL = bstrSQL + " and b.RptDate >= '" + fm.RgtDateStart.value + "'";
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql29");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );   
////			
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  	
////			mySql.addSubPara(fm.CustomerName.value );  
////			mySql.addSubPara(fm.RgtDateStart.value );  
////			
//	      }
//	       
//	      if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
//	      {
//	      bstrSQL = bstrSQL + " and b.RptDate <= '" + fm.RgtDateEnd.value + "'";
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql30");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );   
////			
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  	
////			mySql.addSubPara(fm.CustomerName.value );  
////			mySql.addSubPara(fm.RgtDateStart.value );  
////			mySql.addSubPara(fm.RgtDateEnd.value );  
//	      }
//	
//	       
//	      if (fm.GrpContNo.value != "")  //���屣����
//	      {
//	       bstrSQL = bstrSQL + " and b.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') ";
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql31");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );   
////			
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  	
////			mySql.addSubPara(fm.CustomerName.value );  
////			mySql.addSubPara(fm.RgtDateStart.value );  
////			mySql.addSubPara(fm.RgtDateEnd.value );  
////			mySql.addSubPara(fm.GrpContNo.value );  
//	      }
//	   
	    	var  RptNo34 = window.document.getElementsByName(trim("RptNo"))[0].value;
		    var  ClmState34 = window.document.getElementsByName(trim("ClmState"))[0].value;
		    var  AccidentDate34 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
		    var  CustomerNo34 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
		    var  RgtDate34 = window.document.getElementsByName(trim("RgtDate"))[0].value;
		    var  EndDate34 = window.document.getElementsByName(trim("EndDate"))[0].value;
//	        var  AccidentDate34 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
//	 		var  CustomerNo34 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
	 	    mySql34 = new SqlClass();
	 		mySql34.setResourceName("claim.LLClaimQueryMissInputSql");
	 		mySql34.setSqlId("LLClaimQueryMissSql34");
	 		mySql34.addSubPara(RptNo34 ); //ָ���������
			mySql34.addSubPara(ClmState34); //ָ���������
			mySql34.addSubPara(AccidentDate34);  //ָ���������
			mySql34.addSubPara(CustomerNo34); //ָ���������
			mySql34.addSubPara(RgtDate34); //ָ���������
			mySql34.addSubPara(EndDate34);  //ָ���������
			mySql34.addSubPara(fm.CustomerName.value);  //ָ���������
			mySql34.addSubPara(fm.ContNo.value);  //ָ���������
			mySql34.addSubPara(fm.CalManageCom.value);  //ָ���������
			mySql34.addSubPara(fm.RgtDateStart.value);  //ָ���������
			mySql34.addSubPara(fm.RgtDateEnd.value);  //ָ���������
			mySql34.addSubPara(fm.GrpContNo.value);  //ָ���������
	 		mySql34.addSubPara(AccidentDate34);  //ָ���������
	 		mySql34.addSubPara(CustomerNo34); //ָ���������
	 		mySql34.addSubPara(fm.CustomerName.value);  //ָ���������
	 		mySql34.addSubPara( fm.RgtDateStart.value); //ָ���������
	 		mySql34.addSubPara(fm.RgtDateEnd.value);  //ָ���������
	 		mySql34.addSubPara(fm.GrpContNo.value); //ָ���������
//	 		bstrSQL = mySql34.getString();
	 		strSQL = mySql34.getString();
	     
	    
	    }
//	    strSQL=strSQL+" union "+ bstrSQL;
  }	
  //prompt("",strSQL);
		 //turnPage.queryModal(strSQL,LLClaimQueryGrid);
		 var arrDataSet=easyExecSql(strSQL);
		 if(arrDataSet==null)
		 {
		 	alert("û�����������Ϣ�������������ѯ����");
		 	return;
		 	}
	  	else
		{
		 displayMultiline(arrDataSet, LLClaimQueryGrid);
		}
	
}