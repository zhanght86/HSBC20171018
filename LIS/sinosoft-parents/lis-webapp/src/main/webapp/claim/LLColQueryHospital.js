var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

// 查询按钮
function InitQueryClick()
{
	/*var strSQL = "select ConsultNo,HospitalCode,HospitalName,"
	           + " (select d.codename from ldcode d where d.code=HosAtti and d.codetype='llhosgrade'),"
	           + " (case ConFlag when '0' then '定点' when '1' then '非定点' else '' end), "
	           + " (case AppFlag when '0' then '有资质' when '1' then '无资质' else '' end), "
	           + " (case HosState when '0' then '有效' when '1' then '暂停' when '2' then '终止' else '' end), "
	           + " MngCom from LLCommendHospital where 1=1 "
	           + getWherePart( 'HosAtti','HospitalGradeQ')
			   + getWherePart( 'HospitalCode','HospitalCodeQ');*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryHospitalInputSql");
	mySql.setSqlId("LLColQueryHospitalSql1");
	mySql.addSubPara(fm.HospitalGradeQ.value ); 	
	mySql.addSubPara(fm.HospitalCodeQ.value ); 	
	mySql.addSubPara(fm.HospitalMngcomQ.value ); 	
   if (fm.HospitalNameQ.value != "" && fm.HospitalNameQ.value != null)
   {
   	
		//strSQL = strSQL + " and HospitalName like '%%" + fm.HospitalNameQ.value + "%%'"
			mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryHospitalInputSql");
	mySql.setSqlId("LLColQueryHospitalSql2");
	mySql.addSubPara(fm.HospitalGradeQ.value ); 	
	mySql.addSubPara(fm.HospitalCodeQ.value ); 	
	mySql.addSubPara(fm.HospitalNameQ.value ); 	  
    }
   //if (fm.HospitalMngcomQ.value != "" && fm.HospitalMngcomQ.value != null)
   //{
		//strSQL = strSQL + " and Mngcom like '" + fm.HospitalMngcomQ.value + "%%'"
    //}    
		/*strSQL = strSQL + " order by HospitalCode desc";*/
    //prompt("查询治疗医院的sql",strSQL);
    turnPage.pageLineNum = 10;
	turnPage.queryModal(mySql.getString(), LLColQueryHospitalGrid);
}
//初始化医院管理机构 Modify by zhaorx 2006-05-23
function InitHospitalMngcomQ()
{
	if(fm.tComCode.value.length <= 4)
	{
		fm.HospitalMngcomQ.value = fm.tComCode.value
	}
	else
	{
		fm.HospitalMngcomQ.value = fm.tComCode.value.substring(0,4)
	}
}
//点击（LLColQueryHospitalGrid）的触发函数
function LLColQueryHospitalGridClick()
{
}

//对应RadioBox的单记录返回
function returnParent()
{
    var i = LLColQueryHospitalGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        var arr = new Array();
        arr[0] = LLColQueryHospitalGrid.getRowColData(i,2); //医院代码
        arr[1] = LLColQueryHospitalGrid.getRowColData(i,3); //医院名称
//        arr[2] = LLColQueryHospitalGrid.getRowColData(i,4); //医院等级
    }
    if (arr)
    {
//       top.opener.afterQueryLL(arr);
        top.opener.document.all(fm.HosCode.value).value = arr[0];
        top.opener.document.all(fm.HosName.value).value = arr[1];
    }
    top.close();
}