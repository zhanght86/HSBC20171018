var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//显示隐藏span域
function showDiv(spanID,DivID)
{
    if (spanID != null)
    {
        document.all(DivID).style.display="";
    }
    else
    {
        document.all(DivID).style.display="none";
    }
}

//查询调查结论表,赔案结论 by niuzj 20050829
function queryLLInqPayclusionGrid()
{
     /*var strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion, "
                 + "(case finiflag when '0' then'未完成' when '1' then '已完成' end), "
                 + "(case locflag when '0' then '本地' when '1' then '异地' end), "
                 //+ " finiflag,locflag, "
                 + " colflag, "
                 + "(case masflag when '0' then '非阳性' when '1' then '阳性' end), "
                 //+ "masflag, "
                 + "remark from LLInqConclusion where 1=1 "
                 + " and clmno = '" + fm.ClmNo.value+"'"
                 + " and colflag = '0'"
                 + " order by clmno";*/
		 mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql1");
		mySql.addSubPara(fm.ClmNo.value );     
     turnPage.queryModal(mySql.getString(), LLInqPayclusionGrid);

//以下是判断赔案调查结论信息是否隐藏的，是从LLInqQuetyInit.jsp文件中取过来的，觉得写在那里很不规范 2006-01-01 ZHaoRx
	/*var strSQLs="select count(*) from llinqconclusion  where 1=1 "
		+" and clmno='"+fm.ClmNo.value+"'"
		+" and finiflag='1' and colflag='0' " */
			 mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql2");
		mySql.addSubPara(fm.ClmNo.value );  
	var arr = easyExecSql(mySql.getString());
	if (arr == "0")
	{
		document.all('DivLLInqPayclusionGrid').style.display="none";//赔案
		document.all('DivLLInqPayclusionForm').style.display="none";//赔案
	}     
}

function LLInqPayclusionGridClick()
{  
	  var i = LLInqPayclusionGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo1.value = LLInqPayclusionGrid.getRowColData(i,1);
        fm.ConNo1.value = LLInqPayclusionGrid.getRowColData(i,2);
        fm.InqDept1.value = LLInqPayclusionGrid.getRowColData(i,5);
        fm.InqConclusion1.value = LLInqPayclusionGrid.getRowColData(i,6);
        fm.Remark1.value = LLInqPayclusionGrid.getRowColData(i,11);
//        showOneCodeName('sex','WhoSex','SexName');//性别
    }
    document.all('DivLLInqPayclusionForm').style.display="";//调查结论详细信息  
    document.all('DivLLInqConclusionGrid').style.display="";//调查结论信息 
    document.all('DivLLInqConclusionForm').style.display="none";//调查结论详细信息   
    document.all('DivLLInqApplyGrid').style.display="none";//调查申请信息
	document.all('DivLLInqApplyForm').style.display="none";//调查申请详细信息
//	document.all('DivLLInqCourseGrid').style.display="none";//调查过程信息
//	document.all('DivLLInqCourseForm').style.display="none";//调查过程详细信息
    queryLLInqConclusionGrid();
}

//查询调查结论表
function queryLLInqConclusionGrid()
{
     /*var strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion, "
                 //+ "finiflag,locflag, "
                 + "(case finiflag when '0' then'未完成' when '1' then '已完成' end), "
                 + "(case locflag when '0' then '本地' when '1' then '异地' end), "
                 + "colflag, "
                 //+ "masflag, "
                 + "(case masflag  when '1' then '阳性' else '非阳性'end), "
                 + "remark from LLInqConclusion where 1=1 "
                 + " and clmno = '" + fm.ClmNo.value+"'"
                 + " and colflag = '2'"
                 + " order by clmno";*/
     	mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql3");
		mySql.addSubPara(fm.ClmNo.value );  
     turnPage.queryModal(mySql.getString(), LLInqConclusionGrid);
}

//选中调查结论LLInqConclusionGrid响应事件
function LLInqConclusionGridClick()
{
	  //****************************
	  //填充调查结论详细信息
    //****************************
    var i = LLInqConclusionGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo2.value = LLInqConclusionGrid.getRowColData(i,1);
        fm.ConNo.value = LLInqConclusionGrid.getRowColData(i,2);
        fm.InqDept.value = LLInqConclusionGrid.getRowColData(i,5);
        fm.InqConclusion.value = LLInqConclusionGrid.getRowColData(i,6);
        fm.Remark.value = LLInqConclusionGrid.getRowColData(i,11);
//        showOneCodeName('sex','WhoSex','SexName');//性别
    }
	  //****************************
	  //查询调查申请信息,组成mulline
      //****************************
	document.all('DivLLInqConclusionForm').style.display="";//调查结论详细信息   
    document.all('DivLLInqApplyGrid').style.display="";//调查申请信息
	document.all('DivLLInqApplyForm').style.display="none";//调查申请详细信息
//	document.all('DivLLInqCourseGrid').style.display="none";//调查过程信息
//	document.all('DivLLInqCourseForm').style.display="none";//调查过程详细信息
//    document.all('DivLLInqConclusion').style.display="";    
    /*var strSQL = "select clmno,inqno,batno,customerno,customername,vipflag,initphase,inqrcode,inqitem,inqdesc from LLInqApply where 1=1"
                + " and clmno = '" + LLInqConclusionGrid.getRowColData(i,1)+"'"
                + " and batno = '" + LLInqConclusionGrid.getRowColData(i,3)+"'"
                + " and inqdept='" + LLInqConclusionGrid.getRowColData(i,5)+"'"
                + " order by clmno";*/
         	mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql4");
		mySql.addSubPara(LLInqConclusionGrid.getRowColData(i,1)); 
		mySql.addSubPara(LLInqConclusionGrid.getRowColData(i,3));  
		mySql.addSubPara(LLInqConclusionGrid.getRowColData(i,5) );   
    turnPage.queryModal(mySql.getString(), LLInqApplyGrid);
}

//选中LLInqApplyGrid响应事件
function LLInqApplyGridClick()
{
    var i = LLInqApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo3.value = LLInqApplyGrid.getRowColData(i,1);
        fm.InqNo.value = LLInqApplyGrid.getRowColData(i,2);
        fm.customerNo.value = LLInqApplyGrid.getRowColData(i,4);
        fm.InqItem.value = LLInqApplyGrid.getRowColData(i,9);
        fm.InqDesc.value = LLInqApplyGrid.getRowColData(i,10);
//        showOneCodeName('sex','WhoSex','SexName');//性别
    }
	  //****************************
	  //查询调查过程信息,组成mulline
    //****************************
	document.all('DivLLInqApplyForm').style.display="";//调查申请详细信息

//改为弹出式查询
//	  document.all('DivLLInqCourseGrid').style.display="";//调查过程信息
//	  document.all('DivLLInqCourseForm').style.display="none";//调查过程详细信息    
//    var strSQL = "select clmno,inqno,couno,inqdate,inqmode,inqsite,inqbyper,inqcourse,inqdept,inqper1,inqper2 from LLInqCourse where "
//                + " clmno = '" + fm.ClmNo3.value+"'"
//                + " and inqno = '" + fm.InqNo.value+"'"
//                + " order by clmno";
//    turnPage.queryModal(strSQL, LLInqCourseGrid);
}

//选中LLInqCourseGrid响应事件
function LLInqCourseGridClick()
{
  	document.all('DivLLInqCourseForm').style.display="";//调查过程详细信息    
    var i = LLInqCourseGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo4.value = LLInqCourseGrid.getRowColData(i,1);
        fm.InqNo2.value = LLInqCourseGrid.getRowColData(i,2);
        fm.CouNo.value = LLInqCourseGrid.getRowColData(i,3);
        fm.InqSite.value = LLInqCourseGrid.getRowColData(i,6);
        fm.InqCourse.value = LLInqCourseGrid.getRowColData(i,8);
    }
}


//[打印调查任务通知单]按钮
function PRTInteInqTask()
{
//    fm.fmtransact.value="SinglePrt||Print";
    fm.action="LLPRTInteInqTaskSave.jsp";
    fm.target = "../f1print";
	fm.submit();
}


//[打印理赔调查报告]按钮
function LLPRTInteInqReport()
{
//    fm.fmtransact.value="SinglePrt||Print";
    fm.action="LLPRTInteInqReportSave.jsp";
    fm.target = "../f1print";
	fm.submit();
}

//查询调查过程信息
function InqCourseQueryClick()
{
	 /*var strSql="select * from llinqcourse where 1=1 "
	 		+" and clmno='"+fm.ClmNo.value+"' "
	 		+" and inqno='"+fm.InqNo.value+"'  "
	 		+" order by clmno" ;*/
	          	mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql5");
		mySql.addSubPara(fm.ClmNo.value); 
		mySql.addSubPara(fm.InqNo.value); 
	 var arr = easyExecSql(mySql.getString());
	 if (arr == null)
    {
	      alert("该调查申请还没有任何调查过程信息!");
	      return;
    }
    //弹出查看调查过程信息页面
	var strUrl="LLInqCourseQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    //System.out.println(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查询调查费用信息
function QueryInqFeeClick()
{
	 /*var strSql="select * from llinqfee where 1=1 "
 			+" and clmno='"+fm.ClmNo.value+"' "
 			+" and inqno='"+fm.InqNo.value+"'  "
 			+" order by clmno" ;*/
 		          	mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql6");
		mySql.addSubPara(fm.ClmNo.value); 
		mySql.addSubPara(fm.InqNo.value); 
	 var arr = easyExecSql(mySql.getString());
	 if (arr == null)
    {
	      alert("该调查申请还没有任何费用信息!");
	      return;
    }
    //弹出查看调查过程信息页面
	var strUrl="LLInqFeeQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//赔案调查结论未完成时的提示信息
function QueryInqPAConclusionClick()
{ 								
    //以下为函数内容
	/*var tPAConclusionSQL = " select count(*) from llinqconclusion where clmno = '"+fm.ClmNo.value+"'"
	                     + " and batno = '000000' and FiniFlag = '0' "; */
 		mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql7");
		mySql.addSubPara(fm.ClmNo.value); 

    var tResult = easyExecSql(mySql.getString());
    if(tResult != "0")
    {
    	alert("请您注意，该赔案下的赔案调查结论还没有录入完成！");
    }	   	                        
}