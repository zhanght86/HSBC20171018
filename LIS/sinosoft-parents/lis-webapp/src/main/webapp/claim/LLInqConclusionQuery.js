var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//��ѯ������۱�
function queryLLInqConclusionGrid()
{
	 var strSQL = "";
	if(fm.tType.value=="2")
	{
     	/*strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion,finiflag,locflag,colflag,masflag,remark from llinqconclusion where 1=1 "
                 + " and clmno='"+fm.tClmNo.value+"'"
                 + " and batno='"+fm.tBatNo.value+"'"
                 + " and inqdept='"+fm.tInqDept.value+"'"
                 + " and colflag='2'"
                 + " order by clmno";*/
       mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqConclusionQueryInputSql");
		mySql.setSqlId("LLInqConclusionQuerySql1");
		mySql.addSubPara(fm.tClmNo.value );   
		mySql.addSubPara(fm.tBatNo.value );  
		mySql.addSubPara(fm.tInqDept.value );          
    }
	else
	{
		/*strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion,finiflag,locflag,colflag,masflag,remark from llinqconclusion where 1=1 "
                 + " and clmno='"+fm.tClmNo.value+"'"
                 + " and colflag='2'"
                 + " order by clmno";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqConclusionQueryInputSql");
		mySql.setSqlId("LLInqConclusionQuerySql2");
		mySql.addSubPara(fm.tClmNo.value );               
	}
     turnPage.queryModal(mySql.getString(), LLInqConclusionGrid);
}

//ѡ�е������LLInqConclusionGrid��Ӧ�¼�
function LLInqConclusionGridClick()
{
	  //�����������ϸ��Ϣ
    var i = LLInqConclusionGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo2.value = LLInqConclusionGrid.getRowColData(i,1);
        fm.ConNo.value = LLInqConclusionGrid.getRowColData(i,2);
        fm.InqDept.value = LLInqConclusionGrid.getRowColData(i,5);
        fm.InqConclusion.value = LLInqConclusionGrid.getRowColData(i,6);
        fm.Remark.value = LLInqConclusionGrid.getRowColData(i,11);
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }
	  //��ѯ����������Ϣ,���mulline
	document.all('divLLInqConclusionForm').style.display="";//���������ϸ��Ϣ   
    document.all('divLLInqApplyGrid').style.display="";//����������Ϣ
	document.all('divLLInqApplyForm').style.display="none";//����������ϸ��Ϣ
	document.all('divLLInqCourseGrid').style.display="none";//���������Ϣ
	document.all('divLLInqCourseForm').style.display="none";//���������ϸ��Ϣ
//    document.all('divLLInqConclusion').style.display="";    
   /* var strSQL = "select clmno,inqno,batno,customerno,customername,vipflag,initphase,inqrcode,inqitem,inqdesc from LLInqApply where "
                + " clmno = '" + LLInqConclusionGrid.getRowColData(i,1) +"'"
                + " and batno = '" + LLInqConclusionGrid.getRowColData(i,3) +"'"
                + " and inqdept = '" + LLInqConclusionGrid.getRowColData(i,5) +"'"
                + " order by clmno";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqConclusionQueryInputSql");
		mySql.setSqlId("LLInqConclusionQuerySql3");
		mySql.addSubPara(LLInqConclusionGrid.getRowColData(i,1) );   
		mySql.addSubPara(LLInqConclusionGrid.getRowColData(i,3) );   
		mySql.addSubPara(LLInqConclusionGrid.getRowColData(i,5) );   
    turnPage.queryModal(mySql.getString(), LLInqApplyGrid);
}

//ѡ��LLInqApplyGrid��Ӧ�¼�
function LLInqApplyGridClick()
{
	  //������������ϸ��Ϣ
    var i = LLInqApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo3.value = LLInqApplyGrid.getRowColData(i,1);
        fm.InqNo.value = LLInqApplyGrid.getRowColData(i,2);
        fm.CustomerNo.value = LLInqApplyGrid.getRowColData(i,4);
        fm.InqItem.value = LLInqApplyGrid.getRowColData(i,9);
        fm.InqDesc.value = LLInqApplyGrid.getRowColData(i,10);
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }
	  //****************************
	  //��ѯ���������Ϣ,���mulline
    //****************************
	document.all('divLLInqApplyForm').style.display="";//����������ϸ��Ϣ
	document.all('divLLInqCourseGrid').style.display="";//���������Ϣ
	document.all('divLLInqCourseForm').style.display="none";//���������ϸ��Ϣ    
//    document.all('divLLInqCourse').style.display="";
    /*var strSQL = "select clmno,inqno,couno,inqdate,inqmode,inqsite,inqbyper,inqcourse,inqdept,inqper1,inqper2 from LLInqCourse where "
                + " clmno = '" + fm.ClmNo3.value
                + "' and inqno = '" + fm.InqNo.value
                + "' order by clmno";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqConclusionQueryInputSql");
		mySql.setSqlId("LLInqConclusionQuerySql4");
		mySql.addSubPara(fm.ClmNo3.value);   
		mySql.addSubPara(fm.InqNo.value);    
    turnPage.queryModal(mySql.getString(), LLInqCourseGrid);
}

//ѡ��LLInqCourseGrid��Ӧ�¼�
function LLInqCourseGridClick()
{
	//�����������ϸ��Ϣ
    initLLInqCourseForm();
  	document.all('divLLInqCourseForm').style.display="";//���������ϸ��Ϣ    
    var i = LLInqCourseGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo4.value = LLInqCourseGrid.getRowColData(i,1);
        fm.InqNo2.value = LLInqCourseGrid.getRowColData(i,2);
        fm.CouNo.value = LLInqCourseGrid.getRowColData(i,3);
        fm.InqSite.value = LLInqCourseGrid.getRowColData(i,6);
        fm.InqCourse.value = LLInqCourseGrid.getRowColData(i,8);
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }
}

