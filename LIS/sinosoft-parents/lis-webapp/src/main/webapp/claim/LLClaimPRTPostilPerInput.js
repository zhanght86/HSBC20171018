//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
      

// ��ʼ�����1
function queryGrid()
{
	if(fm.prtType.value!="PCT003")
    //��������У��
   {
   	 if (fm.RptNo.value == ""
       && fm.CustomerNo.value == ""
       && fm.RgtDate.value == ""
       && fm.CalManageCom.value == ""      
       )
    {
        alert("����������һ������!");
        return false;
    }
  }
  if(fm.prtType.value=="PCT003")
      //��������У��
   {
   	 if (fm.RptNo.value == ""
       && fm.AuditCom.value == ""
       && fm.RgtStartDate.value == ""
       && fm.RgtEndDate.value == ""      
       )
    {
        alert("����������һ������!");
        return false;
    }
  }
  
    initLLClaimQueryGrid();
    if(fm.prtType.value=="PCT020")
  {
    //��ѯ����ӡ��֪ͨ����Ϣ
//   var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a ,llregister b "
//           //+ " where code='PCT020' and standbyflag6='50' and stateflag<>'1' and reqcom like '"+fm.mComCode.value+"%' "
//           + " where a.otherno=b.rgtno and code='PCT020' and standbyflag6='50' and stateflag<>'1'   "
//           //+ "exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.mComCode.value+"%' )"
//           + " and b.mngcom like'"+fm.mComCode.value+"%'  and b.rgtantmobile is null "
//           + getWherePart( 'otherno' ,'RptNo') //�ⰸ��
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //�����˱���
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //��������

     
//   
//           if (fm.CalManageCom.value != "")  //��������
//           {
//           //strSQL = strSQL + " and managecom like '" + fm.CalManageCom.value + "%%'";
//          // strSQL = strSQL + " and exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.CalManageCom.value+"%' )";
//          	strSQL = strSQL + " and b. mngcom like'"+fm.CalManageCom.value+"%' ";
//           }

           var mComCode1 = fm.mComCode.value;
           var  RptNo1 = window.document.getElementsByName(trim("RptNo"))[0].value;
      	 var  CustomerNo1 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
      	 var  RgtDate1 = window.document.getElementsByName(trim("RgtDate"))[0].value;
      	 var CalManageCom1 = fm.CalManageCom.value;
      	 var sqlid1="LLClaimPRTPostilPerInputSql1";
      	 var mySql1=new SqlClass();
      	 mySql1.setResourceName("claim.LLClaimPRTPostilPerInputSql");
      	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
      	 mySql1.addSubPara(mComCode1);//ָ���������
      	 mySql1.addSubPara(RptNo1);//ָ���������
      	 mySql1.addSubPara(CustomerNo1);//ָ���������
      	 mySql1.addSubPara(RgtDate1);//ָ���������
      	 mySql1.addSubPara(CalManageCom1);//ָ���������
      	 var strSQL = mySql1.getString();       
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  if(fm.prtType.value=="PCT006")
  {
    //��ѯ����ӡ��֪ͨ����Ϣ
//    var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a "
//          // + " where code='PCT006'  and stateflag<>'1' and reqcom like '"+fm.mComCode.value+"%' "
//           + " where code='PCT006'  and stateflag<>'1' and  "
//           + "exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.mComCode.value+"%' ) "
//           + "and  substr(otherno,12,2)='51' "
//           + getWherePart( 'otherno' ,'RptNo') //�ⰸ��
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //�����˱���
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //��������

    
           
//           if (fm.CalManageCom.value != "")  //��������
//           {
//          // strSQL = strSQL + " and managecom like '" + fm.CalManageCom.value + "%%'";
//           strSQL = strSQL + "and  exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.CalManageCom.value+"%' )";
//           }

           var mComCode2 = fm.mComCode.value;
           var  RptNo2 = window.document.getElementsByName(trim("RptNo"))[0].value;
       	 var  CustomerNo2 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
       	 var  RgtDate2 = window.document.getElementsByName(trim("RgtDate"))[0].value; 
       	 var CalManageCom2 = fm.CalManageCom.value;
       	 var sqlid2="LLClaimPRTPostilPerInputSql2";
       	 var mySql2=new SqlClass();
       	 mySql2.setResourceName("claim.LLClaimPRTPostilPerInputSql");
       	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
       	 mySql2.addSubPara(mComCode2);//ָ���������
       	 mySql2.addSubPara(RptNo2);//ָ���������
       	 mySql2.addSubPara(CustomerNo2);//ָ���������
       	 mySql2.addSubPara(RgtDate2);//ָ���������
       	 mySql2.addSubPara(CalManageCom2);//ָ���������
       	 var strSQL = mySql2.getString();    
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  
  if(fm.prtType.value=="PCT010")
  {
    //��ѯ����ӡ��֪ͨ����Ϣ
//   var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a ,llregister b "
//           + " where a.otherno=b.rgtno and code='PCT010' and standbyflag6='50' and stateflag<>'1'   "
//           + " and b.mngcom like'"+fm.mComCode.value+"%'   "
//           + "and  substr(otherno,12,2)='51' "
//           + getWherePart( 'otherno' ,'RptNo') //�ⰸ��
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //�����˱���
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //��������


           
//           if (fm.CalManageCom.value != "")  //��������
//           {
//        	strSQL = strSQL + " and b. mngcom like'"+fm.CalManageCom.value+"%' ";
//           }
	  
	     var mComCode3 = fm.mComCode.value;
	     var  RptNo3 = window.document.getElementsByName(trim("RptNo"))[0].value;
		 var  CustomerNo3 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
		 var  RgtDate3 = window.document.getElementsByName(trim("RgtDate"))[0].value;
		 var sqlid3="LLClaimPRTPostilPerInputSql3";
		 var mySql3=new SqlClass();
		 mySql3.setResourceName("claim.LLClaimPRTPostilPerInputSql");
		 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
		 mySql3.addSubPara(mComCode3);//ָ���������
		 mySql3.addSubPara(RptNo3);//ָ���������
		 mySql3.addSubPara(CustomerNo3);//ָ���������
		 mySql3.addSubPara(RgtDate3);//ָ���������
		 mySql3.addSubPara(fm.CalManageCom.value);//ָ���������
		 var strSQL = mySql3.getString();  

    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
      if(fm.prtType.value=="PCT003")
  {
    //��ѯ����ӡ�Ĳ������֪ͨ��
   var strSQL = "";
   if(_DBT==DBO){
//	   strSQL = "select otherno,prtseq,managecom,(select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1),"
//           + " (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1),"
//           + " (select IssueBacker from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1)"
//           + " from loprtmanager a "
//           + " where code='PCT003' and standbyflag6='20' and stateflag='3'   "
//           + " and exists (select 1 from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null )"
//           + " and exists (select 1 from LLAffix where rgtno=a.otherno and SupplyStage='01' and AffixState='01')"
//           + " and  substr(otherno,12,2)='51' "
//           + " and (select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1) like '" + fm.mComCode.value + "%'"
//           + getWherePart( 'otherno' ,'RptNo'); //�ⰸ��
	   
	    
//
//         if (fm.AuditCom.value != "")  //��������
//           {
//          		strSQL = strSQL + " and (select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1) like'"+fm.AuditCom.value+"%' ";
//           }
//           if(fm.RgtStartDate.value != "")
//           {
//           		strSQL = strSQL + " and (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1)>='"+fm.RgtStartDate.value+"' ";
//           }
//           if(fm.RgtEndDate.value != "")
//           {
//           		strSQL = strSQL + " and (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1)<='"+fm.RgtEndDate.value+"' ";
//           }
           
           var mComCode4 = fm.mComCode.value;
  	     var  RptNo4 = window.document.getElementsByName(trim("RptNo"))[0].value;
  		 var sqlid4="LLClaimPRTPostilPerInputSql4";
  		 var mySql4=new SqlClass();
  		 mySql4.setResourceName("claim.LLClaimPRTPostilPerInputSql");
  		 mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
  		 mySql4.addSubPara(mComCode4);//ָ���������
  		 mySql4.addSubPara(RptNo4);//ָ���������
  		mySql4.addSubPara(fm.AuditCom.value);//ָ���������
  		mySql4.addSubPara(fm.RgtStartDate.value);//ָ���������
  		mySql4.addSubPara(fm.RgtEndDate.value);//ָ���������
  		 strSQL = mySql4.getString();    
  	   
           
   }else if(_DBT==DBM){
//	   strSQL = "select otherno,prtseq,managecom,(select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1),"
//           + " (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1),"
//           + " (select IssueBacker from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1)"
//           + " from loprtmanager a "
//           + " where code='PCT003' and standbyflag6='20' and stateflag='3'   "
//           + " and exists (select 1 from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null )"
//           + " and exists (select 1 from LLAffix where rgtno=a.otherno and SupplyStage='01' and AffixState='01')"
//           + " and  substr(otherno,12,2)='51' "
//           + " and (select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1) like '" + fm.mComCode.value + "%'"
//           + getWherePart( 'otherno' ,'RptNo'); //�ⰸ��

	     
//	   if (fm.AuditCom.value != "")  //��������
//           {
//          		strSQL = strSQL + " and (select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1) like'"+fm.AuditCom.value+"%' ";
//           }
//           if(fm.RgtStartDate.value != "")
//           {
//           		strSQL = strSQL + " and (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1)>='"+fm.RgtStartDate.value+"' ";
//           }
//           if(fm.RgtEndDate.value != "")
//           {
//           		strSQL = strSQL + " and (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1)<='"+fm.RgtEndDate.value+"' ";
//           }
           
           var mComCode5 = fm.mComCode.value;
  	     var  RptNo5 = window.document.getElementsByName(trim("RptNo"))[0].value;
  		 var sqlid5="LLClaimPRTPostilPerInputSql5";
  		 var mySql5=new SqlClass();
  		 mySql5.setResourceName("claim.LLClaimPRTPostilPerInputSql");
  		 mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
  		 mySql5.addSubPara(mComCode5);//ָ���������
  		 mySql5.addSubPara(RptNo5);//ָ���������
  		mySql5.addSubPara(fm.AuditCom.value);//ָ���������
  		mySql5.addSubPara(fm.RgtStartDate.value);//ָ���������
  		mySql5.addSubPara(fm.RgtEndDate.value);//ָ���������
  		 strSQL = mySql5.getString();    
   }
    

       turnPage.queryModal(strSQL,LLClaimQueryGrid);
 
  }

}



//�ⰸ��ӡ
function showPrint()
{
  var i = LLClaimQueryGrid.getSelNo()-1;
	if(i<0)
	{
		alert("��ѡ��һ������ӡ��¼");
		return;
	}
    fm.ClmNo.value = LLClaimQueryGrid.getRowColData(i,1);
    fm.PrtSeq.value = LLClaimQueryGrid.getRowColData(i,2);
    fm.dCustomerNo.value= LLClaimQueryGrid.getRowColData(i,3);
    fm.fmtransact.value="SinglePrt||Print";
    LLClaimQueryGrid.delRadioTrueLine();
    //fm.prtType.value="PCT010";
    fm.target = "../f1print";
	document.getElementById("fm").submit();
}


function getPrint()
{
	//����֪ͨ��
	if(fm.prtType.value=="PCT020")
	{

    //��ѯ����ӡ��֪ͨ����Ϣ
//    var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a  ,llregister b "
//           + " where a.otherno=b.rgtno and code='PCT020' and standbyflag6='50' and stateflag<>'1' "
//           + " and b.mngcom like'"+fm.mComCode.value+"%'  and b.rgtantmobile is null ";
//         //  + "and reqoperator='"+fm.mOperator.value+"'  and reqcom like '"+fm.mComCode.value+"%' "; 
//         //  + "and exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.mComCode.value+"%' )";
//         //  prompt("strSQL",strSQL);
     var mComCode6 = fm.mComCode.value;
	 var sqlid6="LLClaimPRTPostilPerInputSql6";
	 var mySql6=new SqlClass();
	 mySql6.setResourceName("claim.LLClaimPRTPostilPerInputSql");
	 mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
	 mySql6.addSubPara(mComCode6);//ָ���������
	 var strSQL = mySql6.getString();   
    
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  //����֪ͨ��
  if(fm.prtType.value=="PCT006")
  {
//  	var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a   "
//           + " where code='PCT006' and stateflag<>'1' "
//          // + "and reqoperator='"+fm.mOperator.value+"'  and reqcom like '"+fm.mComCode.value+"%' "; 
//          + "and exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.mComCode.value+"%' ) "
//          + "and  substr(otherno,12,2)='51' ";
//         //  prompt("strSQL",strSQL);
  	 var mComCode7 = fm.mComCode.value;
	 var sqlid7="LLClaimPRTPostilPerInputSql7";
	 var mySql7=new SqlClass();
	 mySql7.setResourceName("claim.LLClaimPRTPostilPerInputSql");
	 mySql7.setSqlId(sqlid7);//ָ��ʹ��SQL��id
	 mySql7.addSubPara(mComCode7);//ָ���������
	 var strSQL = mySql7.getString();   
   
  	
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  
  if(fm.prtType.value=="PCT010")
  {
    //��ѯ����ӡ��֪ͨ����Ϣ
//   var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a ,llregister b "
//           + " where a.otherno=b.rgtno and code='PCT010' and standbyflag6='50' and stateflag<>'1'   "
//           + " and b.mngcom like'"+fm.mComCode.value+"%'   "
//           + "and  substr(otherno,12,2)='51' "
//           + getWherePart( 'otherno' ,'RptNo') //�ⰸ��
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //�����˱���
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //��������

     var mComCode8 = fm.mComCode.value;
     var  RptNo8 = window.document.getElementsByName(trim("RptNo"))[0].value;
     var  CustomerNo8 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
     var  RgtDate8 = window.document.getElementsByName(trim("RgtDate"))[0].value;
	 var sqlid8="LLClaimPRTPostilPerInputSql8";
	 var mySql8=new SqlClass();
	 mySql8.setResourceName("claim.LLClaimPRTPostilPerInputSql");
	 mySql8.setSqlId(sqlid8);//ָ��ʹ��SQL��id
	 mySql8.addSubPara(mComCode8);//ָ���������
	 mySql8.addSubPara(RptNo8);//ָ���������
	 mySql8.addSubPara(CustomerNo8);//ָ���������
	 mySql8.addSubPara(RgtDate8);//ָ���������
	 var strSQL = mySql8.getString();    
   
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
    if(fm.prtType.value=="PCT003")
  {
    	var strSQL = "";
  	//��ѯ����ӡ�Ĳ������֪ͨ��
    	if(DBT==_DBO){
//    		strSQL = "select otherno,prtseq,managecom,(select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1),"
//    	           + " (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1),"
//    	           + " (select IssueBacker from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1)"
//    	           + " from loprtmanager a "
//    	           + " where code='PCT003' and standbyflag6='20' and stateflag='3'   "
//    	           + " and exists (select 1 from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null )"
//    	           + " and exists (select 1 from LLAffix where rgtno=a.otherno and SupplyStage='01' and AffixState='01' and (subflag is null or subflag='1'))"
//    	   		   + " and  substr(otherno,12,2)='51' "
//    	   		   + " and (select IssueBacker from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null and rownum=1)='" + fm.mOperator.value +"'";
    		 var mOperator9 = fm.mOperator.value;
    		 var sqlid9="LLClaimPRTPostilPerInputSql9";
    		 var mySql9=new SqlClass();
    		 mySql9.setResourceName("claim.LLClaimPRTPostilPerInputSql");
    		 mySql9.setSqlId(sqlid9);//ָ��ʹ��SQL��id
    		 mySql9.addSubPara(mOperator9);//ָ���������
    		 strSQL = mySql9.getString();    
    		
    	}else if(_DBT==DBM){
//    		strSQL = "select otherno,prtseq,managecom,(select IssueBackCom from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1),"
//    	           + " (select IssueBackDate from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1),"
//    	           + " (select IssueBacker from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1)"
//    	           + " from loprtmanager a "
//    	           + " where code='PCT003' and standbyflag6='20' and stateflag='3'   "
//    	           + " and exists (select 1 from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null )"
//    	           + " and exists (select 1 from LLAffix where rgtno=a.otherno and SupplyStage='01' and AffixState='01' and (subflag is null or subflag='1'))"
//    	   		   + " and  substr(otherno,12,2)='51' "
//    	   		   + " and (select IssueBacker from LLRegisterIssue where rgtno=a.otherno and IssueConclusion='02' and IssueReplyDate is null limit 0,1)='" + fm.mOperator.value +"'";
    		 var mOperator10 = fm.mOperator.value;
    		 var sqlid10="LLClaimPRTPostilPerInputSql10";
    		 var mySql10=new SqlClass();
    		 mySql10.setResourceName("claim.LLClaimPRTPostilPerInputSql");
    		 mySql10.setSqlId(sqlid10);//ָ��ʹ��SQL��id
    		 mySql10.addSubPara(mOperator10);//ָ���������
    		 strSQL = mySql10.getString();   
    	
    	}
    
   turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }

  
}