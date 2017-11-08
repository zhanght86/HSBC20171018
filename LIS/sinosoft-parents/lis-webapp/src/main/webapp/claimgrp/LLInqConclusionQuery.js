var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//查询调查结论表
function queryLLInqConclusionGrid()
{
	 var strSQL = "";
	if(fm.tType.value=="2")
	{
     	strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion,finiflag,locflag,colflag,masflag,remark from llinqconclusion where 1=1 "
                 + " and clmno='"+fm.tClmNo.value+"'"
                 + " and batno='"+fm.tBatNo.value+"'"
                 + " and inqdept='"+fm.tInqDept.value+"'"
                 + " and colflag='2'"
                 + " order by clmno";
    }
	else
	{
		strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion,finiflag,locflag,colflag,masflag,remark from llinqconclusion where 1=1 "
                 + " and clmno='"+fm.tClmNo.value+"'"
                 + " and colflag='2'"
                 + " order by clmno";
	}
     turnPage.queryModal(strSQL, LLInqConclusionGrid);
}

//选中调查结论LLInqConclusionGrid响应事件
function LLInqConclusionGridClick()
{
	  //填充调查结论详细信息
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
	  //查询调查申请信息,组成mulline
	document.all('divLLInqConclusionForm').style.display="";//调查结论详细信息   
    document.all('divLLInqApplyGrid').style.display="";//调查申请信息
	document.all('divLLInqApplyForm').style.display="none";//调查申请详细信息
	document.all('divLLInqCourseGrid').style.display="none";//调查过程信息
	document.all('divLLInqCourseForm').style.display="none";//调查过程详细信息
//    document.all('divLLInqConclusion').style.display="";    
    var strSQL = "select clmno,inqno,batno,customerno,customername,vipflag,initphase,inqrcode,inqitem,inqdesc from LLInqApply where "
                + " clmno = '" + LLInqConclusionGrid.getRowColData(i,1) +"'"
                + " and batno = '" + LLInqConclusionGrid.getRowColData(i,3) +"'"
                + " and inqdept = '" + LLInqConclusionGrid.getRowColData(i,5) +"'"
                + " order by clmno";
    turnPage.queryModal(strSQL, LLInqApplyGrid);
}

//选中LLInqApplyGrid响应事件
function LLInqApplyGridClick()
{
	  //填充调查申请详细信息
    var i = LLInqApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo3.value = LLInqApplyGrid.getRowColData(i,1);
        fm.InqNo.value = LLInqApplyGrid.getRowColData(i,2);
        fm.CustomerNo.value = LLInqApplyGrid.getRowColData(i,4);
        fm.InqItem.value = LLInqApplyGrid.getRowColData(i,9);
        fm.InqDesc.value = LLInqApplyGrid.getRowColData(i,10);
//        showOneCodeName('sex','WhoSex','SexName');//性别
    }
	  //****************************
	  //查询调查过程信息,组成mulline
    //****************************
	document.all('divLLInqApplyForm').style.display="";//调查申请详细信息
	document.all('divLLInqCourseGrid').style.display="";//调查过程信息
	document.all('divLLInqCourseForm').style.display="none";//调查过程详细信息    
//    document.all('divLLInqCourse').style.display="";
    var strSQL = "select clmno,inqno,couno,inqdate,inqmode,inqsite,inqbyper,inqcourse,inqdept,inqper1,inqper2 from LLInqCourse where "
                + " clmno = '" + fm.ClmNo3.value
                + "' and inqno = '" + fm.InqNo.value
                + "' order by clmno";
    turnPage.queryModal(strSQL, LLInqCourseGrid);
}

//选中LLInqCourseGrid响应事件
function LLInqCourseGridClick()
{
	//填充调查过程详细信息
    initLLInqCourseForm();
  	document.all('divLLInqCourseForm').style.display="";//调查过程详细信息    
    var i = LLInqCourseGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo4.value = LLInqCourseGrid.getRowColData(i,1);
        fm.InqNo2.value = LLInqCourseGrid.getRowColData(i,2);
        fm.CouNo.value = LLInqCourseGrid.getRowColData(i,3);
        fm.InqSite.value = LLInqCourseGrid.getRowColData(i,6);
        fm.InqCourse.value = LLInqCourseGrid.getRowColData(i,8);
//        showOneCodeName('sex','WhoSex','SexName');//性别
    }
}

