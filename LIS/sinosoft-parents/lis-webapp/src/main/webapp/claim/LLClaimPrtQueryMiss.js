//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύǰ��У�顢����  
function beforeSubmit()
{
    //��������У��
    if (fm.RptNo.value == ""
       && fm.ClmState.value == ""
       && fm.AccidentDate.value == ""
       && fm.CustomerNo.value == ""
       && fm.CustomerName.value == ""
       && fm.AccidentDate2.value == ""
       && fm.RgtDate.value == ""
       && fm.EndDate.value == ""
       && fm.ContNo.value == ""
       && fm.CalManageCom.value == ""
       && fm.RgtDateStart.value == ""
       && fm.RgtDateEnd.value == ""
       && fm.GrpClmNo.value == ""
       && fm.GrpContNo.value == ""       
       )
    {
        alert("����������һ������!");
        return false;
    }
    
    //ѡ���ⰸ״̬��������������������
    //���ǲ�ѯЧ����� 2006-1-10 15:11 ����
    if (fm.ClmState.value != "" && fm.RgtDateStart.value == "" && fm.RgtDateEnd.value == "")
    {
        alert("ѡ���ⰸ״̬��������������������!");
        return false;
    }
    
    //�����Ż�,�������������������
    //2006-1-23 14:45 ����
    var tName = fm.CustomerName.value;
    if (tName != "" && tName.length < 2)
    {
        alert("��������������ѯ������Ҫ�����������!");
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
//    strSQL = "(select a.rgtno,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end),b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.AccDate "
//           + " from llregister a,llcase b "
//           + " where a.rgtno = b.caseno "
//           + getWherePart( 'a.rgtno' ,'RptNo') //�ⰸ��
//           + getWherePart( 'a.clmstate' ,'ClmState') //�ⰸ״̬
//           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
//           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���
//           + getWherePart( 'b.AccDate' ,'AccidentDate2') //��������
//           + getWherePart( 'a.RgtDate' ,'RgtDate') //��������
//           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //�᰸����
    
   
    
//           if (fm.CustomerName.value != "")  //����������
//           {
//           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
//           }
//           
//           if (fm.ContNo.value != "")  //��ͬ��
//           {
//           strSQL = strSQL + " and b.rgtno in (select ClmNo from LLClaimPolicy where ContNo ='" + fm.ContNo.value + "') ";
//           }
//           
//           if (fm.CalManageCom.value != "")  //��������
//           {
//           strSQL = strSQL + " and a.MngCom like '" + fm.CalManageCom.value + "%%'";
//           }
//           
//           if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
//           {
//           strSQL = strSQL + " and a.RgtDate >= '" + fm.RgtDateStart.value + "'";
//           }
//           
//           if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
//           {
//           strSQL = strSQL + " and a.RgtDate <= '" + fm.RgtDateEnd.value + "'";
//           }
//           
//           if (fm.GrpContNo.value != "")  //���屣����
//           {
//           strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') " ;
//           }           
//           strSQL = strSQL + " )";
           
        var  RptNo1 = window.document.getElementsByName(trim("RptNo"))[0].value;
       	var  ClmState1 = window.document.getElementsByName(trim("ClmState"))[0].value;
       	var  AccidentDate1 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
       	var  CustomerNo1 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
       	var  AccidentDate21 = window.document.getElementsByName(trim("AccidentDate2"))[0].value;
       	var  RgtDate1 = window.document.getElementsByName(trim("RgtDate"))[0].value;
       	var  EndDate1 = window.document.getElementsByName(trim("EndDate"))[0].value;
       	 var sqlid1="LLClaimPrtQueryMissSql1";
       	 var mySql1=new SqlClass();
       	 mySql1.setResourceName("claim.LLClaimPrtQueryMissSql");
       	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
       	 mySql1.addSubPara(RptNo1);//ָ���������
       	 mySql1.addSubPara(ClmState1);//ָ���������
       	 mySql1.addSubPara(AccidentDate1);//ָ���������
       	 mySql1.addSubPara(CustomerNo1);//ָ���������
       	 mySql1.addSubPara(AccidentDate21);//ָ���������
       	 mySql1.addSubPara(RgtDate1);//ָ���������
       	 mySql1.addSubPara(EndDate1);//ָ���������
       	mySql1.addSubPara(fm.CustomerName.value);//ָ���������
       	mySql1.addSubPara(fm.ContNo.value);//ָ���������
       	mySql1.addSubPara(fm.CalManageCom.value);//ָ���������
       	mySql1.addSubPara(fm.RgtDateStart.value);//ָ���������
       	mySql1.addSubPara(fm.RgtDateEnd.value);//ָ���������
       	mySql1.addSubPara(fm.GrpContNo.value);//ָ���������
       	 
       	 strSQL = mySql1.getString();
           
    //���ϲ�ѯ������Ϣ
    if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "" && fm.CalManageCom.value == "")
    {
//    	   strSQL = strSQL + " union "
//           + "(select a.rptno,'����',b.customerno,c.name,(case c.sex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.AccDate "
//           + " from llreport a,llsubreport b,ldperson c "
//           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
//           + getWherePart( 'a.rptno' ,'RptNo') //�ⰸ��
//           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
//           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���
//           + getWherePart( 'b.AccDate' ,'AccidentDate2'); //��������
    	   
    	   
    	   
//           if (fm.CustomerName.value != "")  //����������
//           {
//           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
//           }
//           
//           if (fm.RgtDateStart.value != "")  //�������ڣ��������ڣ�
//           {
//           strSQL = strSQL + " and a.RptDate >= '" + fm.RgtDateStart.value + "'";
//           }
//           
//           if (fm.RgtDateEnd.value != "")  //�������ڣ�����ֹ�ڣ�
//           {
//           strSQL = strSQL + " and a.RptDate <= '" + fm.RgtDateEnd.value + "'";
//           }
//           
//           if (fm.GrpContNo.value != "")  //���屣����
//           {
//           strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') ";
//           }           
//           strSQL = strSQL + " )";
           
    	 var  RptNo2 = window.document.getElementsByName(trim("RptNo"))[0].value;
        	var  ClmState2 = window.document.getElementsByName(trim("ClmState"))[0].value;
        	var  AccidentDate2 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
        	var  CustomerNo2 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
        	var  AccidentDate22 = window.document.getElementsByName(trim("AccidentDate2"))[0].value;
        	var  RgtDate2 = window.document.getElementsByName(trim("RgtDate"))[0].value;
        	var  EndDate2 = window.document.getElementsByName(trim("EndDate"))[0].value;
//         var  RptNo2 = window.document.getElementsByName(trim("RptNo"))[0].value;
//   		 var  AccidentDate2 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
//   		 var  CustomerNo2 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
//   		 var  AccidentDate22 = window.document.getElementsByName(trim("AccidentDate2"))[0].value;
   		 var sqlid2="LLClaimPrtQueryMissSql2";
   		 var mySql2=new SqlClass();
   		 mySql2.setResourceName("claim.LLClaimPrtQueryMissSql");
   		 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
   		 mySql2.addSubPara(RptNo2);//ָ���������
      	 mySql2.addSubPara(ClmState2);//ָ���������
      	 mySql2.addSubPara(AccidentDate2);//ָ���������
      	 mySql2.addSubPara(CustomerNo2);//ָ���������
      	 mySql2.addSubPara(AccidentDate22);//ָ���������
      	 mySql2.addSubPara(RgtDate2);//ָ���������
      	 mySql2.addSubPara(EndDate2);//ָ���������
      	 mySql2.addSubPara(fm.CustomerName.value);//ָ���������
      	 mySql2.addSubPara(fm.ContNo.value);//ָ���������
      	 mySql2.addSubPara(fm.CalManageCom.value);//ָ���������
      	 mySql2.addSubPara(fm.RgtDateStart.value);//ָ���������
      	 mySql2.addSubPara(fm.RgtDateEnd.value);//ָ���������
      	 mySql2.addSubPara(fm.GrpContNo.value);//ָ���������
   		 mySql2.addSubPara(RptNo2);//ָ���������
   		 mySql2.addSubPara(AccidentDate2);//ָ���������
   		 mySql2.addSubPara(CustomerNo2);//ָ���������
   		 mySql2.addSubPara(AccidentDate22);//ָ���������
   		 mySql2.addSubPara(fm.CustomerName.value);//ָ���������
   		 mySql2.addSubPara(fm.RgtDateStart.value);//ָ���������
   		 mySql2.addSubPara(fm.RgtDateEnd.value);//ָ���������
   		 mySql2.addSubPara(fm.GrpContNo.value);//ָ���������
   		 strSQL = mySql2.getString()
   		
           
    }
    //��������
 //   strSQL = strSQL + " order by 1";

    
    
    
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
}

//LLClaimQueryGrid����¼�
function LLClaimQueryGridClick()
{
//    var i = LLClaimQueryGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
//        var tPhase = LLClaimQueryGrid.getRowColData(i,2);
//        if (tPhase != '����')
//        {
////            location.href="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
//            var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
//        }
//        else
//        {
////            location.href="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
//            var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
//        }
//        window.open(strUrl,"�ⰸ��ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    }
}

//�ⰸ��ӡ
function showPrint()
{
    var i = LLClaimQueryGrid.getSelNo()-1;
	if(i<0)
	{
		alert("��ѡ��һ���ⰸ��¼");
		return;
	}
    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
    var tCustNo = LLClaimQueryGrid.getRowColData(i,3);
    var strUrl="LLClaimEndCaseAffixPrtMain.jsp?claimNo="+tClmNo+"&custNo="+tCustNo;
//        window.open(strUrl,"�ⰸ��ӡ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    
}

//�ⰸ��ϸ��ѯ
function showDetail()
{
    var i = LLClaimQueryGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
        var tPhase = LLClaimQueryGrid.getRowColData(i,2);
        if (tPhase != '����')
        {
//            location.href="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
            var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
        }
        else
        {
//            location.href="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
            var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
        }
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}


    //�û��Զ����ӡ
function showUserDefinedPrt()
{
    var i = LLClaimQueryGrid.getSelNo()-1;
	if(i<0)
	{
		alert("��ѡ��һ���ⰸ��¼");
		return;
	}
    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
    var tCustNo = LLClaimQueryGrid.getRowColData(i,3);
    var strUrl="LLUserDefinedBillPrtMain.jsp?ClmNo="+tClmNo+"&CustNo="+tCustNo;
//    window.open(strUrl,"�û��Զ����ӡ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}


//����ҽ�Ƹ����嵥��ӡ��Ӧ����
function showMedBillGrp()
{
	  if(fm.GrpClmNo.value ==null||fm.GrpClmNo.value == "")
	  {
		    var i = LLClaimQueryGrid.getSelNo()-1;
		  	if(i<0)
		  	{
			  	  alert("��ѡ��һ���ⰸ��¼!");
			  	  return;
			  }
		    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
//		    var tSQLF = " select rgtobjno from llregister where rgtobj='2' "
//		              + " and rgtno='"+tClmNo+"'";
		    
		    var sqlid3="LLClaimPrtQueryMissSql3";
   		    var mySql3=new SqlClass();
   		    mySql3.setResourceName("claim.LLClaimPrtQueryMissSql");
   		    mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
   		    mySql3.addSubPara(tClmNo);//ָ���������
   		    var tSQLF = mySql3.getString()
		    
		    var tResult = easyExecSql(tSQLF);
		    if(tResult == null||tResult == "")
		    {
		      	alert("��ѯ�����ⰸ�ų��ִ���");
		    	  return;
		    }
		    fm.GrpClmNo.value = tResult;		    
     }
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		fm.transact.value = "MedBillGrp";
		fm.target = "../f1print";
		fm.action="./LLPrtGrpSave.jsp";
		document.getElementById("fm").submit();
		showInfo.close(); 
}

//��������������ע��ӡ��Ӧ����
function showPostilGrp()
{
	  if(fm.GrpClmNo.value ==null||fm.GrpClmNo.value == "")
	  {
		    var i = LLClaimQueryGrid.getSelNo()-1;
		  	if(i<0)
		  	{
			  	  alert("��ѡ��һ���ⰸ��¼!");
			  	  return;
			  }
		    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
//		    var tSQLF = " select rgtobjno from llregister where rgtno='"+tClmNo+"' and rgtobj='2'";
		    
		    var sqlid4="LLClaimPrtQueryMissSql4";
   		    var mySql4=new SqlClass();
   		    mySql4.setResourceName("claim.LLClaimPrtQueryMissSql");
   		    mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
   		    mySql4.addSubPara(tClmNo);//ָ���������
   		    var tSQLF = mySql4.getString()
		    
		    var tResult = easyExecSql(tSQLF);
		    if(tResult == null||tResult == "")
		    {
		      	alert("��ѯ�����ⰸ�ų��ִ���");
		    	  return;
		    }
		    fm.GrpClmNo.value = tResult;		    
    }
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		fm.transact.value = "PostilGrp";
		fm.target = "../f1print";
		fm.action="./LLPrtGrpSave.jsp";
		document.getElementById("fm").submit();
		showInfo.close();  	    
}