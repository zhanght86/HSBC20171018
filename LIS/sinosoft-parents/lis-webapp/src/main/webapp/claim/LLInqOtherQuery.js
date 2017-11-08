//程序名称：LLInqOtherQuery.js
//程序功能：查看其他调查信息（过程、调查申请结论、过程、机构结论）
//日期：2005-10-6
//更新记录：  更新人:     更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tturnPage = new turnPageClass();
var inqturnPage = new turnPageClass();
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
function queryInqConclusion()

{
	//清空页面数据（清空LLInqConclusionGrid表格，及该divInstituInfo区域的其他表单的值）
	LLInqConclusionGrid.clearData();
	fm.InstituConclusion.value="";//调查结论(机构层面)
	fm.InstituRemark.value="";//调查备注
	
	//控制隐藏或显示其他Div区域
	document.all('divLLInqApplyInfo').style.display="none";//调查申请信息
	document.all('divLLInqCourseInfo').style.display="none";//调查过程信息
	
//	var strSQL = " select clmno,conno,batno,initdept,inqdept"
//			 + " ,(case finiflag when '0' then '未完成' when '1' then '完成' end )"
//			 + " ,(case locflag when '0' then '本地' when '1' then '异地' end )" 
//			 + " ,(case colflag when '0' then '赔案结论' when '2' then '机构结论' end )" 
//             + " ,masflag,inqconclusion,remark from llinqconclusion where 1=1 and colflag='2'"
//             + " and clmno='"+fm.tClmNo.value+"'"
//             + " order by batno,conno";
	sql_id = "LLInqOtherQuerySql5";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLInqOtherQueryInputSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(fm.tClmNo.value);//指定传入的参数
	str_sql = my_sql.getString();
	
    turnPage.pageLineNum = 5;            
    turnPage.queryModal(str_sql, LLInqConclusionGrid);         
}

//该赔案下的已经发起调查信息（机构层面）列表相应函数-----查询 调查申请信息
function LLInqConclusionGridClick()
{
	//清除 数据
	document.all('divLLInqApplyInfo').style.display="none";//调查申请信息
	document.all('divLLInqCourseInfo').style.display="none";//调查过程信息
	
	fm.InstituConclusion.value="";//调查结论(机构层面)
	fm.InstituRemark.value="";//调查备注
	
	LLInqApplyGrid.clearData();
	fm.InqPer.value="";
	fm.InqStartDate.value="";
	fm.InqEndDate.value="";
	fm.InqRCode.value="";
	fm.InqRCodeName.value="";
	fm.InitDept.value="";
	fm.InitDeptName.value="";
	fm.InqDept.value="";
	fm.InqDeptName.value="";
	fm.InqItem.value="";
	fm.InqDesc.value="";
	fm.InqConclusion.value="";
		
	var i = LLInqConclusionGrid.getSelNo()-1;
	var tClmNo   = LLInqConclusionGrid.getRowColData(i,1);//赔案号
	var tBatNo   = LLInqConclusionGrid.getRowColData(i,3);//批次号
	var tConNo   = LLInqConclusionGrid.getRowColData(i,2);//结论序号
	var tInqDept = LLInqConclusionGrid.getRowColData(i,5);//调查机构
	fm.InstituConclusion.value=LLInqConclusionGrid.getRowColData(i,10);//调查结论
	fm.InstituRemark.value=LLInqConclusionGrid.getRowColData(i,11);//调查备注
	
	//以下查询  调查申请信息
//    var sSQL = "select clmno,inqno,batno,customerno,customername,vipflag,initphase,applyper,applydate"
//        + " ,(case locflag when '0' then '本地' when '1' then '异地' end )" 
//        + " ,(case inqstate when '0' then '未完成' when '1' then '完成' end )"
//    	+ " from llinqapply where 1=1 " //是否需要《and inqno!='"+fm.tInqNo.value+"'》
//        + " and clmno = '"+tClmNo +"' and batno = '"+tBatNo+"' and inqdept = '" + tInqDept +"'"
//        + " order by inqno,batno";

    sql_id = "LLInqOtherQuerySql6";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLInqOtherQueryInputSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tClmNo);//指定传入的参数
	my_sql.addSubPara(tBatNo);//指定传入的参数
	my_sql.addSubPara(tInqDept);//指定传入的参数
	str_sql = my_sql.getString();
    inqturnPage.queryModal(str_sql, LLInqApplyGrid);
//    var strQueryResult=inqturnPage.strQueryResult;
//    if(strQueryResult==""||strQueryResult=="false")
//    {
//		document.all('divLLInqApplyInfo').style.display="none";//调查申请信息
//		alert("该机构该批次下没有其他调查申请！");
//		return;
//    }
//	else
//	{	
//    	document.all('divLLInqApplyInfo').style.display="";//显示调查申请信息
//	}
	document.all('divLLInqApplyInfo').style.display="";//显示调查申请信息
}



function LLInqApplyGridClick()
{
	
	//清除 数据
	fm.InqPer.value="";
	fm.InqStartDate.value="";
	fm.InqEndDate.value="";
	fm.InqRCode.value="";
	fm.InqRCodeName.value="";
	fm.InitDept.value="";
	fm.InitDeptName.value="";
	fm.InqDept.value="";
	fm.InqDeptName.value="";
	fm.InqItem.value="";
	fm.InqDesc.value="";
	fm.InqConclusion.value="";
	
	LLInqCourseGrid.clearData();
	document.all('divLLInqCourseInfo').style.display="none";//调查过程信息
	
	var i = LLInqApplyGrid.getSelNo()-1;
	var tClmNo = LLInqApplyGrid.getRowColData(i,1);//赔案号
	var tInqNo = LLInqApplyGrid.getRowColData(i,2);//调查序号
	var tBatNo = LLInqApplyGrid.getRowColData(i,3);//批次号
	//查询：调查原因--发起机构--调查机构--调查分配人--调查分配日期--调查结束日期--调查项目--调查描述--调查结论
	/*var sSQl="select inqper,inqstartdate,inqenddate,inqrcode,initdept,inqdept,inqitem,inqdesc,inqconclusion "
		+ " from llinqapply where 1=1"
        + " and clmno = '"+tClmNo +"' and batno = '"+tBatNo+"' and InqNo = '" + tInqNo +"'"
        + " order by inqno,batno";	*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqOtherQueryInputSql");
	mySql.setSqlId("LLInqOtherQuerySql3");
	mySql.addSubPara(tClmNo);
	mySql.addSubPara(tBatNo);
	mySql.addSubPara(tInqNo);
    var arr = easyExecSql(mySql.getString());
    if (arr == null)
    {
	      alert("查询产生错误!");
	      return;
    }    
	else
	{			
		fm.InqPer.value=arr[0][0];	 //调查分配人
		fm.InqStartDate.value=arr[0][1];//	 调查分配日期
		fm.InqEndDate.value=arr[0][2];// 调查结束日期
		fm.InqRCode.value=arr[0][3];  //调查原因
		fm.InitDept.value=arr[0][4];  //发起机构
		fm.InqDept.value=arr[0][5];  //调查机构
		fm.InqItem.value=arr[0][6];  //调查项目
		fm.InqDesc.value=arr[0][7];  //调查描述
		fm.InqConclusion.value=arr[0][8]; //调查结论
		showOneCodeName('llinqreason','InqRCode','InqRCodeName');
		showOneCodeName('stati','InqDept','InqDeptName');   
		showOneCodeName('stati','InitDept','InitDeptName'); 

	}

	//查询调查过程
	/*var strSQL="select clmno,inqno,couno,inqsite,( select codename from ldcode t where t.codetype='llinqmode' and code=inqmode),inqbyper,inqper1,inqper2,inqdept,inqdate,inqcourse,remark"
			 + " from llinqcourse where 1=1 and clmno = '"+tClmNo +"' and InqNo = '" + tInqNo +"'"
       		 + " order by inqno,couno";	*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqOtherQueryInputSql");
	mySql.setSqlId("LLInqOtherQuerySql4");
	mySql.addSubPara(tClmNo);
	mySql.addSubPara(tInqNo);
    tturnPage.queryModal(mySql.getString(), LLInqCourseGrid);
//    alert(tturnPage.strQueryResult);
    var strQueryResult=tturnPage.strQueryResult;
//    var rownum=tturnPage.queryAllRecordCount;
    if(strQueryResult==""||strQueryResult=="false")
    {
		document.all('divLLInqCourseInfo').style.display="none";//调查过程信息
		alert("该调查申请还没有任何调查过程信息！");
		return;
    }
	else
	{	
    	document.all('divLLInqCourseInfo').style.display="";//显示调查申请信息
	}

}

function LLInqCourseGridClick()
{
	fm.InqCourse.value="";
	fm.InqCourseRemark.value="";
	
	var i = LLInqCourseGrid.getSelNo()-1;
	fm.InqCourse.value = LLInqCourseGrid.getRowColData(i,11);// 
	fm.InqCourseRemark.value = LLInqCourseGrid.getRowColData(i,12);// 
}


