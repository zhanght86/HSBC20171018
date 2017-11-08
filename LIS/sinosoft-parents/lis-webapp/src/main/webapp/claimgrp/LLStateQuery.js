//程序名称：LPStateQuery.js
//程序功能：赔案状态查询
//创建日期：2006-4-7
//创建人  ：wangjm
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
var varFlag=0;  //判断采用LLRegister  表,还是llreport 表查询数据
var sqlFlag=0;  //判断采用LLRegister  表,还是llregister 和llcase 表关联查询数据
// 查询按钮
function easyQueryClick()
{

    initClaimGrid();
    initClaimStateGrid();

    var strSQL = "";
    var tClmNo=document.all("ClmNo").value;
    var tCustomerNo=document.all("CustomerNo").value;
    var tGrpContNo=document.all("GrpContNo").value;
    var tCustomerName=document.all("CustomerName").value;
    var tRiskDate=document.all("RiskDate").value;
    
    var clmstate=document.all("clmstate").value;
    
    if(tClmNo==""&&tCustomerNo==""&&tGrpContNo==""&&tCustomerName==""&&tRiskDate==""){
    	alert("请至少输入一个查询条件！");
    	return false;
    }
    
    var arr=new Array();
  //  if(tCustomerNo==""&&tCustomerName=="")    
    if(tCustomerNo==""&&tCustomerName==""&&tRiskDate=="")
    {

  //    strSQL="select a.RgtNo,a.GrpName,a.GrpContNo,(select distinct CreateOperator from lwmission where 1=1 and MissionProp1='"+tClmNo+"'),a.MngCom,RgtState from LLRegister a "
    /* strSQL="select a.RgtNo,a.GrpName,a.GrpContNo,a.Operator,a.MngCom,a.RgtState,a.ClmState,(select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=a.rgtno and finiflag='0') from LLRegister a "
        + "where 1=1 " 
        + getWherePart( 'a.RgtNo' ,'ClmNo')
        + getWherePart( 'a.GrpContNo' ,'GrpContNo'); */
     	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql1");
		mySql.addSubPara(fm.ClmNo.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		
        if(clmstate==10||clmstate==20||clmstate==30||clmstate==40){
      //strSQL=strSQL + getWherePart( 'a.clmstate' ,'clmstate'); 
      	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql2");
		mySql.addSubPara(fm.ClmNo.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(fm.clmstate.value ); 
      }else if(clmstate  ==11){
      //strSQL=strSQL + " and a.clmstate in('50','60','70','80')";
       	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql3");
		mySql.addSubPara(fm.ClmNo.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		
    }              
        
    }else
    {
//      strSQL="select a.RgtNo,a.GrpName,a.GrpContNo,(select distinct CreateOperator from lwmission where 1=1 and MissionProp1='"+tClmNo+"'), a.MngCom,a.RgtState from LLRegister a "
       /* strSQL="select a.RgtNo,a.GrpName,a.GrpContNo,a.Operator,a.MngCom,a.RgtState,a.ClmState,(select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=a.rgtno and finiflag='0') from LLRegister a "     
        + "where 1=1 " 
        + getWherePart( 'a.RgtNo' ,'ClmNo')
        + getWherePart( 'a.GrpContNo' ,'GrpContNo')
        +" and a.RgtNo in (select b.caseno from llcase b where 1=1 "  
        + getWherePart( 'b.AccDate' ,'RiskDate')
        + getWherePart( 'b.CustomerNo' ,'CustomerNo');*/
		mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql4");
		mySql.addSubPara(fm.ClmNo.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(fm.RiskDate.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
       if(tCustomerName!="")
       {
        //strSQL=strSQL+" and CustomerName like '%%" + tCustomerName + "%%'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql5");
		mySql.addSubPara(fm.ClmNo.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(fm.RiskDate.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(tCustomerName); 
       }
        //strSQL=strSQL+" )";
        //sqlFlag=1;
    }
    
    if(manageCom!="%"&&manageCom!="")
    { 
    	if( manageCom.length < 4)
    	{
    		//strSQL=strSQL+" and a.MngCom like'"+manageCom+"%%'";
    		mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLStateQueryInputSql");
			mySql.setSqlId("LLStateQuerySql6");
			mySql.addSubPara(fm.ClmNo.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			mySql.addSubPara(fm.RiskDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(tCustomerName); 
			mySql.addSubPara(manageCom); 
    	}else
    		{
    		//strSQL=strSQL+" and a.MngCom='"+manageCom.substring(0,2)+"'";
    		mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLStateQueryInputSql");
			mySql.setSqlId("LLStateQuerySql7");
			mySql.addSubPara(fm.ClmNo.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			mySql.addSubPara(fm.RiskDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(tCustomerName); 
			mySql.addSubPara(manageCom.substring(0,2)); 
    		}      
    } 
    //strSQL=strSQL+" order by a.RgtNo ";
    arr = easyExecSql(mySql.getString());
    if(!arr)
   {  

    //   strSQL="select a.rptno,a.grpname,a.grpcontno,(select distinct CreateOperator from lwmission where 1=1 and MissionProp1='"+tClmNo+"'), a.mngcom from llreport a where 1=1 "  
         /*strSQL="select a.rptno,a.grpname,a.grpcontno,a.Operator,a.mngcom,a.AvaliReason,'10',(select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=a.rptno and finiflag='0') from llreport a "
         +" where 1=1 and not exists (select 'X' from LLRegister where rgtno=a.rptno ) "  
         + getWherePart( 'a.rptno' ,'ClmNo') 
         + getWherePart( 'a.AccidentDate' ,'RiskDate')
         + getWherePart( 'a.GrpContNo' ,'GrpContNo') ;*/
      		 mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLStateQueryInputSql");
			mySql.setSqlId("LLStateQuerySql8");
			mySql.addSubPara(fm.ClmNo.value ); 
			mySql.addSubPara(fm.RiskDate.value ); 
			mySql.addSubPara(fm.GrpContNo.value );     
      if(manageCom!="%"&&manageCom!="")
      { 
        if( manageCom.length < 4)
       	{
       		//strSQL=strSQL+" and a.MngCom like'"+manageCom+"%%'";
       		mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLStateQueryInputSql");
			mySql.setSqlId("LLStateQuerySql9");
			mySql.addSubPara(fm.ClmNo.value ); 
			mySql.addSubPara(fm.RiskDate.value ); 
			mySql.addSubPara(fm.GrpContNo.value );  
			mySql.addSubPara(manageCom);  
       	}else
       		{
       		//strSQL=strSQL+" and a.MngCom='"+manageCom+"'";
       		mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLStateQueryInputSql");
			mySql.setSqlId("LLStateQuerySql10");
			mySql.addSubPara(fm.ClmNo.value ); 
			mySql.addSubPara(fm.RiskDate.value ); 
			mySql.addSubPara(fm.GrpContNo.value );  
			mySql.addSubPara(manageCom);  
       		}  
      } 
        //strSQL=strSQL+" order by a.rptno ";
       arr = easyExecSql(mySql.getString());
       if(!arr)   
      {
      
       alert("没有查询到符合此条件的记录！");
       return;
      }
       //varFlag=1;
       turnPage.queryModal(mySql.getString(),ClaimGrid);
   }else
    {
      turnPage.queryModal(mySql.getString(),ClaimGrid);
    }  
}      
  //显示赔案状态明细
function displayClick()
{
var tSel = ClaimGrid.getSelNo();

if( tSel == 0 || tSel == null )
alert( "请先选择一条记录，再点击返回按钮。" );
else
{
 var tClmNo = ClaimGrid.getRowColData(tSel - 1,1);
 var tRgtState=ClaimGrid.getRowColData(tSel - 1,6);//案件类型
 var tClmState=ClaimGrid.getRowColData(tSel - 1,7);//赔案状态
 //alert("tRgtState="+tRgtState);
 //alert("tClmState="+tClmState);
 var sql="";
 if(tClmNo == "")
    return;  
  if(varFlag==0)
 { 
 	
   if(tRgtState=="11"||tRgtState=="01"||tRgtState=="02")
   {
    if((tClmState=="50")||(tClmState=="60")||(tClmState=="70")||(tClmState=="80"))
    {
      /*sql="select decode(ClmState,'10','报案','20','立案','30','审核','40','审批','50','结案','60','结案','70','不予立案','80','拒赔','处理中'),"
      +" decode(RgtState,'01','简易案件','02','帐户案件','11','普通案件','03','非理算案件'),"
      //+" (select Operator from LLClaim where ClmNo='"+tClmNo+"') "
      +" (select ClmUWer from LLClaim where ClmNo='"+tClmNo+"') "
      +" from LLRegister where RgtNo='"+tClmNo+"'";	*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql11");
		mySql.addSubPara(tClmNo); 
		mySql.addSubPara(tClmNo); 
		
    }
    else if(tClmState=="40"||tClmState=="30")
    {
      /* sql="select decode(missionprop2,'10','报案','20','立案','30','审核','40','审批','50','结案','60','结案','70','不予立案','80','拒赔','处理中'),"
       +"(select decode(RgtState,'01','简易案件','02','帐户案件','11','普通案件','03','非理算案件') from llregister where rgtno='"+tClmNo+"'),"
       //+"(select AuditPer from LLClaimUWMain where ClmNo='"+tClmNo+"') "
       //+"(select Operator from LLClaim where ClmNo='"+tClmNo+"') "
       +" defaultoperator "
       +" from lwmission where missionprop1='"+tClmNo+"' and activityid like '00000050%%' ";*/
     	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql12");
		mySql.addSubPara(tClmNo); 
		mySql.addSubPara(tClmNo); 
    }
     else
    {
    	/* sql="select decode(missionprop2,'10','报案','20','立案','25','延迟立案','30','审核','40','审批','50','结案','60','结案','70','不予立案','80','拒赔','处理中'),"
       +" decode(missionprop15,'01','简易案件','02','帐户案件','11','普通案件') ,"
       +" defaultoperator "
       +" from lwmission where missionprop1='"+tClmNo+"' and activityid like '00000050%%' ";*/ 
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql13");
		mySql.addSubPara(tClmNo); 
	
    }
   
   }
   else
   {

    if(tClmState=="50"||tClmState=="60"||tClmState=="70")
    {
    //alert("not 11=60");
     /*sql="select decode(ClmState,'10','报案','20','立案','30','审核','40','审批','50','结案','60','结案','70','结案','80','拒赔','处理中'),"
      +" decode(RgtState,'01','简易案件','02','帐户案件','11','普通案件','03','非理算案件'),"
      //+" (select Operator from LLClaim where  ClmNo='"+tClmNo+"')"
      +" (select ClmUWer from LLClaim where ClmNo='"+tClmNo+"') "
      +" from LLRegister where RgtNo='"+tClmNo+"'";	*/
      mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql14");
		mySql.addSubPara(tClmNo); 
		mySql.addSubPara(tClmNo); 
    }
    else if(tClmState=="40"||tClmState=="30")
    {
    	/*sql="select decode(ClmState,'10','报案','20','立案','30','审核','40','审批','50','结案','60','结案','70','结案','80','拒赔','处理中'),"
      +" decode(RgtState,'01','简易案件','02','帐户案件','11','普通案件','03','非理算案件'),"
      //+" (select AuditPer from LLClaimUWMain where  ClmNo='"+tClmNo+"')"
      +" (select Operator from LLClaim where  ClmNo='"+tClmNo+"')"
      +" from LLRegister where RgtNo='"+tClmNo+"'";	*/
    	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql15");
		mySql.addSubPara(tClmNo); 
		mySql.addSubPara(tClmNo); 
    }
    else
    {
      /*sql="select decode(ClmState,'10','报案','20','立案','30','审核','40','审批','50','结案','60','结案','70','结案','80','拒赔','待处理中'), "
       +" decode(RgtState,'01','简易案件','02','帐户案件','11','普通案件','03','非理算案件'),"
       +"''"
       +" from llregister where rgtno='"+tClmNo+"'";*/
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql16");
		mySql.addSubPara(tClmNo); 
		
    } 
       
   }
    turnPage2.queryModal(mySql.getString(),ClaimStateGrid);
 }      
    
   if(varFlag==1)
   {
     //alert("varFlag==1");
    /* sql="select decode(MissionProp2,'10','已报案','20','立案','30','审核','40','审批','50','结案','60','结案','70','结案','80','拒赔','待处理中'), "
       +" decode(MissionProp4,'0658a021','普通案件',' 普通案件'),"
       +"''"
       +" from lwmission where MissionProp1='"+tClmNo+"' and activityid like '00000050%%' ";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql17");
		mySql.addSubPara(tClmNo); 
    turnPage2.queryModal(mySql.getString(),ClaimStateGrid);
    
   }
  }
}
//显示报案信息页面
function ReportQueryClick()
{
	  var i = ClaimGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = ClaimGrid.getRowColData(i,1);
       // var tsql="select AvaliReason from llreport where rptno='"+tClmNo+"'"; 
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLStateQueryInputSql");
		mySql.setSqlId("LLStateQuerySql18");
		mySql.addSubPara(tClmNo);        
        var tAvaliReason = easyExecSql(mySql.getString());  
        location.href="LLGrpReportInput.jsp?claimNo="+tClmNo+"&isNew=1&isClaimState=1&rgtstate="+tAvaliReason;
    }else
    {
    		alert("请先选择一条记录！");
    }
}
//显示立案信息页面
function RegisterQueryClick()
{
	  var i = ClaimGrid.getSelNo();
    if (i != '0')
    {
    	  i = i - 1;
        var tClmNo = ClaimGrid.getRowColData(i,1);
        var tClmState = ClaimGrid.getRowColData(i,7);
        location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&isClaimState=1&clmState="+tClmState;
    }else
    {
    		alert("请先选择一条记录！");
    }
}
