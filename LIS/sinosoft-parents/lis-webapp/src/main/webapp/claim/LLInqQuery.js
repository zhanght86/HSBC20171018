var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//��ʾ����span��
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

//��ѯ������۱�,�ⰸ���� by niuzj 20050829
function queryLLInqPayclusionGrid()
{
     /*var strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion, "
                 + "(case finiflag when '0' then'δ���' when '1' then '�����' end), "
                 + "(case locflag when '0' then '����' when '1' then '���' end), "
                 //+ " finiflag,locflag, "
                 + " colflag, "
                 + "(case masflag when '0' then '������' when '1' then '����' end), "
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

//�������ж��ⰸ���������Ϣ�Ƿ����صģ��Ǵ�LLInqQuetyInit.jsp�ļ���ȡ�����ģ�����д������ܲ��淶 2006-01-01 ZHaoRx
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
		document.all('DivLLInqPayclusionGrid').style.display="none";//�ⰸ
		document.all('DivLLInqPayclusionForm').style.display="none";//�ⰸ
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
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }
    document.all('DivLLInqPayclusionForm').style.display="";//���������ϸ��Ϣ  
    document.all('DivLLInqConclusionGrid').style.display="";//���������Ϣ 
    document.all('DivLLInqConclusionForm').style.display="none";//���������ϸ��Ϣ   
    document.all('DivLLInqApplyGrid').style.display="none";//����������Ϣ
	document.all('DivLLInqApplyForm').style.display="none";//����������ϸ��Ϣ
//	document.all('DivLLInqCourseGrid').style.display="none";//���������Ϣ
//	document.all('DivLLInqCourseForm').style.display="none";//���������ϸ��Ϣ
    queryLLInqConclusionGrid();
}

//��ѯ������۱�
function queryLLInqConclusionGrid()
{
     /*var strSQL = "select clmno,conno,batno,initdept,inqdept,inqconclusion, "
                 //+ "finiflag,locflag, "
                 + "(case finiflag when '0' then'δ���' when '1' then '�����' end), "
                 + "(case locflag when '0' then '����' when '1' then '���' end), "
                 + "colflag, "
                 //+ "masflag, "
                 + "(case masflag  when '1' then '����' else '������'end), "
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

//ѡ�е������LLInqConclusionGrid��Ӧ�¼�
function LLInqConclusionGridClick()
{
	  //****************************
	  //�����������ϸ��Ϣ
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
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }
	  //****************************
	  //��ѯ����������Ϣ,���mulline
      //****************************
	document.all('DivLLInqConclusionForm').style.display="";//���������ϸ��Ϣ   
    document.all('DivLLInqApplyGrid').style.display="";//����������Ϣ
	document.all('DivLLInqApplyForm').style.display="none";//����������ϸ��Ϣ
//	document.all('DivLLInqCourseGrid').style.display="none";//���������Ϣ
//	document.all('DivLLInqCourseForm').style.display="none";//���������ϸ��Ϣ
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

//ѡ��LLInqApplyGrid��Ӧ�¼�
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
//        showOneCodeName('sex','WhoSex','SexName');//�Ա�
    }
	  //****************************
	  //��ѯ���������Ϣ,���mulline
    //****************************
	document.all('DivLLInqApplyForm').style.display="";//����������ϸ��Ϣ

//��Ϊ����ʽ��ѯ
//	  document.all('DivLLInqCourseGrid').style.display="";//���������Ϣ
//	  document.all('DivLLInqCourseForm').style.display="none";//���������ϸ��Ϣ    
//    var strSQL = "select clmno,inqno,couno,inqdate,inqmode,inqsite,inqbyper,inqcourse,inqdept,inqper1,inqper2 from LLInqCourse where "
//                + " clmno = '" + fm.ClmNo3.value+"'"
//                + " and inqno = '" + fm.InqNo.value+"'"
//                + " order by clmno";
//    turnPage.queryModal(strSQL, LLInqCourseGrid);
}

//ѡ��LLInqCourseGrid��Ӧ�¼�
function LLInqCourseGridClick()
{
  	document.all('DivLLInqCourseForm').style.display="";//���������ϸ��Ϣ    
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


//[��ӡ��������֪ͨ��]��ť
function PRTInteInqTask()
{
//    fm.fmtransact.value="SinglePrt||Print";
    fm.action="LLPRTInteInqTaskSave.jsp";
    fm.target = "../f1print";
	fm.submit();
}


//[��ӡ������鱨��]��ť
function LLPRTInteInqReport()
{
//    fm.fmtransact.value="SinglePrt||Print";
    fm.action="LLPRTInteInqReportSave.jsp";
    fm.target = "../f1print";
	fm.submit();
}

//��ѯ���������Ϣ
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
	      alert("�õ������뻹û���κε��������Ϣ!");
	      return;
    }
    //�����鿴���������Ϣҳ��
	var strUrl="LLInqCourseQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    //System.out.println(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ѯ���������Ϣ
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
	      alert("�õ������뻹û���κη�����Ϣ!");
	      return;
    }
    //�����鿴���������Ϣҳ��
	var strUrl="LLInqFeeQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�ⰸ�������δ���ʱ����ʾ��Ϣ
function QueryInqPAConclusionClick()
{ 								
    //����Ϊ��������
	/*var tPAConclusionSQL = " select count(*) from llinqconclusion where clmno = '"+fm.ClmNo.value+"'"
	                     + " and batno = '000000' and FiniFlag = '0' "; */
 		mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqQueryInputSql");
		mySql.setSqlId("LLInqQuerySql7");
		mySql.addSubPara(fm.ClmNo.value); 

    var tResult = easyExecSql(mySql.getString());
    if(tResult != "0")
    {
    	alert("����ע�⣬���ⰸ�µ��ⰸ������ۻ�û��¼����ɣ�");
    }	   	                        
}