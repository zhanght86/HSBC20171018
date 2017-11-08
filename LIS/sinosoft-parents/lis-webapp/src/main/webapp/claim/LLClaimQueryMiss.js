//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var mySql = new SqlClass();

//提交前的校验、计算  
function beforeSubmit()
{
    //输入条件校验
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
        alert("请至少输入一个条件!");
        return false;
    }
    
    //选择赔案状态必须输入受理日期区间
    //考虑查询效率添加 2006-1-10 15:11 周磊
    if (fm.ClmState.value != "" &&fm.ClmState.value != "05"  && fm.RptNo.value == "" && fm.RgtDateStart.value == "" && fm.RgtDateEnd.value == "")
    {
        alert("选择赔案状态请输入立案日期");
        return false;
    }
    if (fm.ClmState.value == "05"  && fm.RptNo.value == "" && fm.ScanDateStart.value == "" && fm.ScanDateEnd.value == "")
    {
        alert("请输入扫描日期");
        return false;
    }
    
    //性能优化,姓名必须输入二个汉字
    //2006-1-23 14:45 周磊
    var tName = fm.CustomerName.value;
    if (tName != "" && tName.length < 2)
    {
        alert("按客户姓名查询至少需要输入二个汉字!");
        return false;
    }
    
    return true;
}         

// 初始化表格1
function queryGrid()
{
    if (!beforeSubmit())
    {
        return;
    }
    initLLClaimQueryGrid();
    var strSQL = "";
    
    
    //查询立案以后信息
   /* strSQL = "(select a.rgtno,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '35' then '预付审批' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end),b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.AccDate "
           + " from llregister a,llcase b "
           + " where a.rgtno = b.caseno "
           + getWherePart( 'a.rgtno' ,'RptNo') //赔案号
           + getWherePart( 'a.clmstate' ,'ClmState') //赔案状态
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码
           //+ getWherePart( 'b.AccDate' ,'AccidentDate2') //出险日期
           + getWherePart( 'a.RgtDate' ,'RgtDate') //立案日期
           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //结案日期*/
    		
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
			mySql.setSqlId("LLClaimQueryMissSql1");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.ClmState.value ); 
			mySql.addSubPara(fm.AccidentDate.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.RgtDate.value ); 
			mySql.addSubPara(fm.EndDate.value ); 
			
           if (fm.CustomerName.value != "")  //出险人姓名
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
           
           if (fm.ContNo.value != "")  //合同号
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
           
           if (fm.CalManageCom.value != "")  //立案机构
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
           
           if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
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
           
           if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
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
           
           //if (fm.GrpClmNo.value != "")  //团体赔案号
           //{
           //strSQL = strSQL + " and a.rgtobjno = '" + fm.GrpClmNo.value + "'";
           //}
           
           if (fm.GrpContNo.value != "")  //团体保单号
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
           
           //if (fm.AccidentDate2.value != ""){//事故日期
        	   //strSQL = strSQL + " and ( AccDate = '"+fm.AccidentDate2.value+"' or medaccdate = '"+fm.AccidentDate2.value+"')"
           //}
           
          // strSQL = strSQL + " )";
           
    //联合查询报案信息
    if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "" && fm.CalManageCom.value == "")
    {
    /*strSQL = strSQL + " union "
           + "(select a.rptno,'报案',b.customerno,c.name,(case c.sex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.AccDate "
           + " from llreport a,llsubreport b,ldperson c "
           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
           + getWherePart( 'a.rptno' ,'RptNo') //赔案号
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码*/
//           + getWherePart( 'b.AccDate' ,'AccidentDate2'); //出险日期
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
			
           if (fm.CustomerName.value != "")  //出险人姓名
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
    
           if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
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
           
           if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
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
           
           //if (fm.GrpClmNo.value != "")  //团体赔案号
           //{
           //strSQL = strSQL + " and a.rgtobjno = '" + fm.GrpClmNo.value + "'";
           //}
           
           if (fm.GrpContNo.value != "")  //团体保单号
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
           
           //if (fm.AccidentDate2.value != ""){//事故日期
        	  // strSQL = strSQL + " and ( AccDate = '"+fm.AccidentDate2.value+"' or medaccdate = '"+fm.AccidentDate2.value+"')"
           //}
         //  strSQL = strSQL + " )";
    }
    //增加排序
   // strSQL = strSQL + " order by 1";

    turnPage.queryModal(mySql.getString(),LLClaimQueryGrid);
}

//LLClaimQueryGrid点击事件
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

        if (tPhase == '报案' && clmType=="6")               
        {
             var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=1";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
        else if (tPhase == '报案' && clmType=="5")               
        {
            var strUrl="LLClaimReportQueryMain.jsp?claimNo="+tClmNo+"&customerNo="+tCustomerNo+"&Flag=query";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
        else if(tPhase == '扫描完成未初审'||tPhase == '立案初审中'||tPhase == '初审完毕未立案')
        {
        		alert("没有相应的案件信息");
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
//    		     	//查看是否处于事前扫描工作流
//	   /* strSQL = "select missionprop1,decode(missionprop2,'21','扫描完成未初审','22','立案初审中','初审完毕未立案'),'','','', missionprop11,missionprop13 "
//	     	     + "from lwmission where  activityid='0000005010' "
//	     	     + getWherePart( 'missionprop1' ,'RptNo') //赔案号
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateStart','>=') //扫描起期
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateEnd','<='); //扫描止期*/
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
//	   strSQL = "select a.rgtno,(case a.clmstate  when '20' then '立案' when '30' then '审核' when '35' then '预付审批' when '40' then '审批' when '50' then '结案' when '70' then '关闭' end), "
//	    			 + "b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end),to_char(b.AccDate,'yyyy-mm-dd'),'','' "
//			       + " from llregister a,llcase b "
//	           + " where a.rgtno = b.caseno "
//	           + getWherePart( 'a.rgtno' ,'RptNo') //赔案号
//	           + getWherePart( 'a.clmstate' ,'ClmState') //赔案状态
//	           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
//	           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码
//	           + getWherePart( 'a.RgtDate' ,'RgtDate') //立案日期
//	           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //结案日期
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
//	     if (fm.CustomerName.value != "")  //出险人姓名
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
//	     if (fm.ContNo.value != "")  //合同号
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
//	     if (fm.CalManageCom.value != "")  //立案机构
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
//	     if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
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
//	     if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
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
//	     if (fm.GrpContNo.value != "")  //团体保单号
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
			mySql32.addSubPara(RptNo32 ); //指定传入参数
			mySql32.addSubPara(ClmState32); //指定传入参数
			mySql32.addSubPara(AccidentDate32);  //指定传入参数
			mySql32.addSubPara(CustomerNo32); //指定传入参数
			mySql32.addSubPara(RgtDate32); //指定传入参数
			mySql32.addSubPara(EndDate32);  //指定传入参数
			mySql32.addSubPara(fm.CustomerName.value);  //指定传入参数
			mySql32.addSubPara(fm.ContNo.value);  //指定传入参数
			mySql32.addSubPara(fm.CalManageCom.value);  //指定传入参数
			mySql32.addSubPara(fm.RgtDateStart.value);  //指定传入参数
			mySql32.addSubPara(fm.RgtDateEnd.value);  //指定传入参数
			mySql32.addSubPara(fm.GrpContNo.value);  //指定传入参数
			strSQL = mySql32.getString();
	     
	     var arrResult=easyExecSql(strSQL);
	     //没有立案
//	     if(arrResult==null)
//	     {
//	     	//立案号，状态，客户编码，客户姓名，性别
//	     	//查看是否处于事前扫描工作流
//	     /*strSQL = "select missionprop1,decode(missionprop2,'21','扫描完成未初审','22','立案初审中','初审完毕未立案'),'','','', missionprop11,missionprop13 "
//	     	     + "from lwmission where  activityid='0000005010' "
//	     	     + getWherePart( 'missionprop1' ,'RptNo') //赔案号
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateStart','>=') //扫描起期
//	     	     + getWherePart( 'MissionProp9' ,'ScanDateEnd','<='); //扫描止期*/
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
     	//查询报案信息
			var bstrSQL="";
	    if(fm.RptNo.value!="")
	    {
//	    	bstrSQL = "select subrptno,'报案',a.customerno,a.customername,(case a.sex when '0' then '男' when '1' then '女'else'不详' end),'','','' from llsubreport a ,llregister b "
//	     	     	 	 +" where (a.subrptno=b.rgtobjno or a.subrptno=b.rgtno)  "
//	     	       	 + getWherePart( 'b.rgtno' ,'RptNo') //赔案号
//	     	         + getWherePart( 'b.AccidentDate' ,'AccidentDate') //事故发生日期
//	     	         + getWherePart( 'a.CustomerNo' ,'CustomerNo','like'); //出险人编码;
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
//	     	if (fm.CustomerName.value != "")  //出险人姓名
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
//	      if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
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
//	      if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
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
//	      if (fm.GrpContNo.value != "")  //团体保单号
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
	 		mySql33.addSubPara(RptNo33 ); //指定传入参数
			mySql33.addSubPara(ClmState33); //指定传入参数
			mySql33.addSubPara(AccidentDate33);  //指定传入参数
			mySql33.addSubPara(CustomerNo33); //指定传入参数
			mySql33.addSubPara(RgtDate33); //指定传入参数
			mySql33.addSubPara(EndDate33);  //指定传入参数
			mySql33.addSubPara(fm.CustomerName.value);  //指定传入参数
			mySql33.addSubPara(fm.ContNo.value);  //指定传入参数
			mySql33.addSubPara(fm.CalManageCom.value);  //指定传入参数
			mySql33.addSubPara(fm.RgtDateStart.value);  //指定传入参数
			mySql33.addSubPara(fm.RgtDateEnd.value);  //指定传入参数
			mySql33.addSubPara(fm.GrpContNo.value);  //指定传入参数
	 		mySql33.addSubPara(RptNo33 ); //指定传入参数
	 		mySql33.addSubPara(AccidentDate33);  //指定传入参数
	 		mySql33.addSubPara(CustomerNo33); //指定传入参数
	 		mySql33.addSubPara(fm.CustomerName.value ); //指定传入参数
	 		mySql33.addSubPara(fm.RgtDateStart.value);  //指定传入参数
	 		mySql33.addSubPara(fm.RgtDateEnd.value); //指定传入参数
	 		mySql33.addSubPara(fm.GrpContNo.value); //指定传入参数
//	 		bstrSQL = mySql33.getString();
	 		strSQL = mySql33.getString();
	    	
	    
	    }
	    else
	    {
//	     bstrSQL = "select subrptno,'报案',a.customerno,a.customername,(case a.sex when '0' then '男' when '1' then '女'else'不详' end),'','',''  from llsubreport a ,llregister b where 1=1  "
//	     	       + getWherePart( 'b.AccidentDate' ,'AccidentDate') //事故发生日期
//	     	       + getWherePart( 'a.CustomerNo' ,'CustomerNo','like'); //出险人编码;*/
////	      mySql = new SqlClass();
////			mySql.setResourceName("claim.LLClaimQueryMissInputSql");
////			mySql.setSqlId("LLClaimQueryMissSql27");
////			mySql.addSubPara(fm.RptNo.value ); 
////			mySql.addSubPara(fm.ScanDateStart.value ); 
////			mySql.addSubPara(fm.ScanDateEnd.value );   
////			
////			mySql.addSubPara(fm.AccidentDate.value ); 
////			mySql.addSubPara(fm.CustomerNo.value );  
	     
	 		
//	     	if (fm.CustomerName.value != "")  //出险人姓名
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
//	      if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
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
//	      if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
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
//	      if (fm.GrpContNo.value != "")  //团体保单号
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
	 		mySql34.addSubPara(RptNo34 ); //指定传入参数
			mySql34.addSubPara(ClmState34); //指定传入参数
			mySql34.addSubPara(AccidentDate34);  //指定传入参数
			mySql34.addSubPara(CustomerNo34); //指定传入参数
			mySql34.addSubPara(RgtDate34); //指定传入参数
			mySql34.addSubPara(EndDate34);  //指定传入参数
			mySql34.addSubPara(fm.CustomerName.value);  //指定传入参数
			mySql34.addSubPara(fm.ContNo.value);  //指定传入参数
			mySql34.addSubPara(fm.CalManageCom.value);  //指定传入参数
			mySql34.addSubPara(fm.RgtDateStart.value);  //指定传入参数
			mySql34.addSubPara(fm.RgtDateEnd.value);  //指定传入参数
			mySql34.addSubPara(fm.GrpContNo.value);  //指定传入参数
	 		mySql34.addSubPara(AccidentDate34);  //指定传入参数
	 		mySql34.addSubPara(CustomerNo34); //指定传入参数
	 		mySql34.addSubPara(fm.CustomerName.value);  //指定传入参数
	 		mySql34.addSubPara( fm.RgtDateStart.value); //指定传入参数
	 		mySql34.addSubPara(fm.RgtDateEnd.value);  //指定传入参数
	 		mySql34.addSubPara(fm.GrpContNo.value); //指定传入参数
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
		 	alert("没有相关理赔信息，请重新输入查询条件");
		 	return;
		 	}
	  	else
		{
		 displayMultiline(arrDataSet, LLClaimQueryGrid);
		}
	
}