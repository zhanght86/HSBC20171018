//�������ƣ�LLInqOtherQuery.js
//�����ܣ��鿴����������Ϣ�����̡�����������ۡ����̡��������ۣ�
//���ڣ�2005-10-6
//���¼�¼��  ������:     ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tturnPage = new turnPageClass();
var inqturnPage = new turnPageClass();
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
function queryInqConclusion()

{
	//���ҳ�����ݣ����LLInqConclusionGrid��񣬼���divInstituInfo�������������ֵ��
	LLInqConclusionGrid.clearData();
	fm.InstituConclusion.value="";//�������(��������)
	fm.InstituRemark.value="";//���鱸ע
	
	//�������ػ���ʾ����Div����
	document.all('divLLInqApplyInfo').style.display="none";//����������Ϣ
	document.all('divLLInqCourseInfo').style.display="none";//���������Ϣ
	
//	var strSQL = " select clmno,conno,batno,initdept,inqdept"
//			 + " ,(case finiflag when '0' then 'δ���' when '1' then '���' end )"
//			 + " ,(case locflag when '0' then '����' when '1' then '���' end )" 
//			 + " ,(case colflag when '0' then '�ⰸ����' when '2' then '��������' end )" 
//             + " ,masflag,inqconclusion,remark from llinqconclusion where 1=1 and colflag='2'"
//             + " and clmno='"+fm.tClmNo.value+"'"
//             + " order by batno,conno";
	sql_id = "LLInqOtherQuerySql5";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLInqOtherQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(fm.tClmNo.value);//ָ������Ĳ���
	str_sql = my_sql.getString();
	
    turnPage.pageLineNum = 5;            
    turnPage.queryModal(str_sql, LLInqConclusionGrid);         
}

//���ⰸ�µ��Ѿ����������Ϣ���������棩�б���Ӧ����-----��ѯ ����������Ϣ
function LLInqConclusionGridClick()
{
	//��� ����
	document.all('divLLInqApplyInfo').style.display="none";//����������Ϣ
	document.all('divLLInqCourseInfo').style.display="none";//���������Ϣ
	
	fm.InstituConclusion.value="";//�������(��������)
	fm.InstituRemark.value="";//���鱸ע
	
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
	var tClmNo   = LLInqConclusionGrid.getRowColData(i,1);//�ⰸ��
	var tBatNo   = LLInqConclusionGrid.getRowColData(i,3);//���κ�
	var tConNo   = LLInqConclusionGrid.getRowColData(i,2);//�������
	var tInqDept = LLInqConclusionGrid.getRowColData(i,5);//�������
	fm.InstituConclusion.value=LLInqConclusionGrid.getRowColData(i,10);//�������
	fm.InstituRemark.value=LLInqConclusionGrid.getRowColData(i,11);//���鱸ע
	
	//���²�ѯ  ����������Ϣ
//    var sSQL = "select clmno,inqno,batno,customerno,customername,vipflag,initphase,applyper,applydate"
//        + " ,(case locflag when '0' then '����' when '1' then '���' end )" 
//        + " ,(case inqstate when '0' then 'δ���' when '1' then '���' end )"
//    	+ " from llinqapply where 1=1 " //�Ƿ���Ҫ��and inqno!='"+fm.tInqNo.value+"'��
//        + " and clmno = '"+tClmNo +"' and batno = '"+tBatNo+"' and inqdept = '" + tInqDept +"'"
//        + " order by inqno,batno";

    sql_id = "LLInqOtherQuerySql6";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLInqOtherQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tClmNo);//ָ������Ĳ���
	my_sql.addSubPara(tBatNo);//ָ������Ĳ���
	my_sql.addSubPara(tInqDept);//ָ������Ĳ���
	str_sql = my_sql.getString();
    inqturnPage.queryModal(str_sql, LLInqApplyGrid);
//    var strQueryResult=inqturnPage.strQueryResult;
//    if(strQueryResult==""||strQueryResult=="false")
//    {
//		document.all('divLLInqApplyInfo').style.display="none";//����������Ϣ
//		alert("�û�����������û�������������룡");
//		return;
//    }
//	else
//	{	
//    	document.all('divLLInqApplyInfo').style.display="";//��ʾ����������Ϣ
//	}
	document.all('divLLInqApplyInfo').style.display="";//��ʾ����������Ϣ
}



function LLInqApplyGridClick()
{
	
	//��� ����
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
	document.all('divLLInqCourseInfo').style.display="none";//���������Ϣ
	
	var i = LLInqApplyGrid.getSelNo()-1;
	var tClmNo = LLInqApplyGrid.getRowColData(i,1);//�ⰸ��
	var tInqNo = LLInqApplyGrid.getRowColData(i,2);//�������
	var tBatNo = LLInqApplyGrid.getRowColData(i,3);//���κ�
	//��ѯ������ԭ��--�������--�������--���������--�����������--�����������--������Ŀ--��������--�������
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
	      alert("��ѯ��������!");
	      return;
    }    
	else
	{			
		fm.InqPer.value=arr[0][0];	 //���������
		fm.InqStartDate.value=arr[0][1];//	 �����������
		fm.InqEndDate.value=arr[0][2];// �����������
		fm.InqRCode.value=arr[0][3];  //����ԭ��
		fm.InitDept.value=arr[0][4];  //�������
		fm.InqDept.value=arr[0][5];  //�������
		fm.InqItem.value=arr[0][6];  //������Ŀ
		fm.InqDesc.value=arr[0][7];  //��������
		fm.InqConclusion.value=arr[0][8]; //�������
		showOneCodeName('llinqreason','InqRCode','InqRCodeName');
		showOneCodeName('stati','InqDept','InqDeptName');   
		showOneCodeName('stati','InitDept','InitDeptName'); 

	}

	//��ѯ�������
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
		document.all('divLLInqCourseInfo').style.display="none";//���������Ϣ
		alert("�õ������뻹û���κε��������Ϣ��");
		return;
    }
	else
	{	
    	document.all('divLLInqCourseInfo').style.display="";//��ʾ����������Ϣ
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


