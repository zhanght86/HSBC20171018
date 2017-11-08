//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
      

// 初始化表格1
function queryGrid()
{
	if(fm.prtType.value!="PCT003")
    //输入条件校验
   {
   	 if (fm.RptNo.value == ""
       && fm.CustomerNo.value == ""
       && fm.RgtDate.value == ""
       && fm.CalManageCom.value == ""      
       )
    {
        alert("请至少输入一个条件!");
        return false;
    }
  }
  if(fm.prtType.value=="PCT003")
      //输入条件校验
   {
   	 if (fm.RptNo.value == ""
       && fm.AuditCom.value == ""
       && fm.RgtStartDate.value == ""
       && fm.RgtEndDate.value == ""      
       )
    {
        alert("请至少输入一个条件!");
        return false;
    }
  }
  
    initLLClaimQueryGrid();
    if(fm.prtType.value=="PCT020")
  {
    //查询待打印的通知书信息
//   var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a ,llregister b "
//           //+ " where code='PCT020' and standbyflag6='50' and stateflag<>'1' and reqcom like '"+fm.mComCode.value+"%' "
//           + " where a.otherno=b.rgtno and code='PCT020' and standbyflag6='50' and stateflag<>'1'   "
//           //+ "exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.mComCode.value+"%' )"
//           + " and b.mngcom like'"+fm.mComCode.value+"%'  and b.rgtantmobile is null "
//           + getWherePart( 'otherno' ,'RptNo') //赔案号
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //出险人编码
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //立案日期

     
//   
//           if (fm.CalManageCom.value != "")  //立案机构
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
      	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
      	 mySql1.addSubPara(mComCode1);//指定传入参数
      	 mySql1.addSubPara(RptNo1);//指定传入参数
      	 mySql1.addSubPara(CustomerNo1);//指定传入参数
      	 mySql1.addSubPara(RgtDate1);//指定传入参数
      	 mySql1.addSubPara(CalManageCom1);//指定传入参数
      	 var strSQL = mySql1.getString();       
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  if(fm.prtType.value=="PCT006")
  {
    //查询待打印的通知书信息
//    var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a "
//          // + " where code='PCT006'  and stateflag<>'1' and reqcom like '"+fm.mComCode.value+"%' "
//           + " where code='PCT006'  and stateflag<>'1' and  "
//           + "exists(select 1 from llregister where rgtno=a.otherno and mngcom like'"+fm.mComCode.value+"%' ) "
//           + "and  substr(otherno,12,2)='51' "
//           + getWherePart( 'otherno' ,'RptNo') //赔案号
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //出险人编码
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //立案日期

    
           
//           if (fm.CalManageCom.value != "")  //立案机构
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
       	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
       	 mySql2.addSubPara(mComCode2);//指定传入参数
       	 mySql2.addSubPara(RptNo2);//指定传入参数
       	 mySql2.addSubPara(CustomerNo2);//指定传入参数
       	 mySql2.addSubPara(RgtDate2);//指定传入参数
       	 mySql2.addSubPara(CalManageCom2);//指定传入参数
       	 var strSQL = mySql2.getString();    
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  
  if(fm.prtType.value=="PCT010")
  {
    //查询待打印的通知书信息
//   var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a ,llregister b "
//           + " where a.otherno=b.rgtno and code='PCT010' and standbyflag6='50' and stateflag<>'1'   "
//           + " and b.mngcom like'"+fm.mComCode.value+"%'   "
//           + "and  substr(otherno,12,2)='51' "
//           + getWherePart( 'otherno' ,'RptNo') //赔案号
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //出险人编码
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //立案日期


           
//           if (fm.CalManageCom.value != "")  //立案机构
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
		 mySql3.setSqlId(sqlid3);//指定使用SQL的id
		 mySql3.addSubPara(mComCode3);//指定传入参数
		 mySql3.addSubPara(RptNo3);//指定传入参数
		 mySql3.addSubPara(CustomerNo3);//指定传入参数
		 mySql3.addSubPara(RgtDate3);//指定传入参数
		 mySql3.addSubPara(fm.CalManageCom.value);//指定传入参数
		 var strSQL = mySql3.getString();  

    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
      if(fm.prtType.value=="PCT003")
  {
    //查询待打印的补充材料通知书
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
//           + getWherePart( 'otherno' ,'RptNo'); //赔案号
	   
	    
//
//         if (fm.AuditCom.value != "")  //立案机构
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
  		 mySql4.setSqlId(sqlid4);//指定使用SQL的id
  		 mySql4.addSubPara(mComCode4);//指定传入参数
  		 mySql4.addSubPara(RptNo4);//指定传入参数
  		mySql4.addSubPara(fm.AuditCom.value);//指定传入参数
  		mySql4.addSubPara(fm.RgtStartDate.value);//指定传入参数
  		mySql4.addSubPara(fm.RgtEndDate.value);//指定传入参数
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
//           + getWherePart( 'otherno' ,'RptNo'); //赔案号

	     
//	   if (fm.AuditCom.value != "")  //立案机构
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
  		 mySql5.setSqlId(sqlid5);//指定使用SQL的id
  		 mySql5.addSubPara(mComCode5);//指定传入参数
  		 mySql5.addSubPara(RptNo5);//指定传入参数
  		mySql5.addSubPara(fm.AuditCom.value);//指定传入参数
  		mySql5.addSubPara(fm.RgtStartDate.value);//指定传入参数
  		mySql5.addSubPara(fm.RgtEndDate.value);//指定传入参数
  		 strSQL = mySql5.getString();    
   }
    

       turnPage.queryModal(strSQL,LLClaimQueryGrid);
 
  }

}



//赔案打印
function showPrint()
{
  var i = LLClaimQueryGrid.getSelNo()-1;
	if(i<0)
	{
		alert("请选择一条待打印记录");
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
	//给付通知书
	if(fm.prtType.value=="PCT020")
	{

    //查询待打印的通知书信息
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
	 mySql6.setSqlId(sqlid6);//指定使用SQL的id
	 mySql6.addSubPara(mComCode6);//指定传入参数
	 var strSQL = mySql6.getString();   
    
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  //决定通知书
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
	 mySql7.setSqlId(sqlid7);//指定使用SQL的id
	 mySql7.addSubPara(mComCode7);//指定传入参数
	 var strSQL = mySql7.getString();   
   
  	
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
  
  if(fm.prtType.value=="PCT010")
  {
    //查询待打印的通知书信息
//   var strSQL = "select otherno,prtseq,managecom,standbyflag4,standbyflag5 from loprtmanager a ,llregister b "
//           + " where a.otherno=b.rgtno and code='PCT010' and standbyflag6='50' and stateflag<>'1'   "
//           + " and b.mngcom like'"+fm.mComCode.value+"%'   "
//           + "and  substr(otherno,12,2)='51' "
//           + getWherePart( 'otherno' ,'RptNo') //赔案号
//           + getWherePart( 'standbyflag4' ,'CustomerNo') //出险人编码
//           + getWherePart( 'standbyflag5' ,'RgtDate'); //立案日期

     var mComCode8 = fm.mComCode.value;
     var  RptNo8 = window.document.getElementsByName(trim("RptNo"))[0].value;
     var  CustomerNo8 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
     var  RgtDate8 = window.document.getElementsByName(trim("RgtDate"))[0].value;
	 var sqlid8="LLClaimPRTPostilPerInputSql8";
	 var mySql8=new SqlClass();
	 mySql8.setResourceName("claim.LLClaimPRTPostilPerInputSql");
	 mySql8.setSqlId(sqlid8);//指定使用SQL的id
	 mySql8.addSubPara(mComCode8);//指定传入参数
	 mySql8.addSubPara(RptNo8);//指定传入参数
	 mySql8.addSubPara(CustomerNo8);//指定传入参数
	 mySql8.addSubPara(RgtDate8);//指定传入参数
	 var strSQL = mySql8.getString();    
   
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }
    if(fm.prtType.value=="PCT003")
  {
    	var strSQL = "";
  	//查询待打印的补充材料通知书
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
    		 mySql9.setSqlId(sqlid9);//指定使用SQL的id
    		 mySql9.addSubPara(mOperator9);//指定传入参数
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
    		 mySql10.setSqlId(sqlid10);//指定使用SQL的id
    		 mySql10.addSubPara(mOperator10);//指定传入参数
    		 strSQL = mySql10.getString();   
    	
    	}
    
   turnPage.queryModal(strSQL,LLClaimQueryGrid);
  }

  
}