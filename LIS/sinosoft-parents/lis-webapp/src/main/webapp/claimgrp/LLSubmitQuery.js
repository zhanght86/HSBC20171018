var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�ʱ���ѯ
function queryLLSubmitApplyGrid()
{
//     var strSQL = "select clmno,subno,subcount,customerno,customername,vipflag,initphase,subtype,subrcode,subdesc,subper,subdate,subdept,substate,DispIdea,remark from LLSubmitApply where 1=1 "
     /*var strSQL = "select clmno,subno,subcount,customerno,customername,vipflag,initphase,subrcode,subper,subdate,subdept"
     			 +" , case substate when '0' then '����' when '1' then '���' end "
     			 +" from llsubmitapply where 1=1 "
                 +" and clmno='"+ fm.ClmNo.value+"' and subcount='0' "
                 +" order by clmno";*/
     
		mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLSubmitQueryInputSql");
		mySql.setSqlId("LLSubmitQuerySql1");
		mySql.addSubPara(fm.ClmNo.value ); 
     turnPage.queryModal(mySql.getString(), LLSubmitApplyGrid);
}

//ѡ��LLSubmitApplyGrid��Ӧ�¼�
function LLSubmitApplyGridClick()
{
	clearData();//�������Ϣ
    var i = LLSubmitApplyGrid.getSelNo()-1;
    var tClmNo=LLSubmitApplyGrid.getRowColData(i,1);//�ⰸ��
    var tSubNo=LLSubmitApplyGrid.getRowColData(i,2);//�ʱ����
    //��ѯ�ʱ�������Ϣ,�ʱ�����Ϊ��0��,  ����������ơ������ˡ�����ʱ�䡢�ʱ�����
    /*var tInitSubSql="select subper,subdept,subdate,subdesc from llsubmitapply where 1=1 "
    	+" and clmno='"+tClmNo+"'"
    	+" and subno='"+tSubNo+"'"
		+" and subcount='0'"  
		+" order by clmno,subno";  */
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLSubmitQueryInputSql");
		mySql.setSqlId("LLSubmitQuerySql2");
		mySql.addSubPara(tClmNo ); 
		mySql.addSubPara(tSubNo); 
	var arrInitSub=easyExecSql(mySql.getString());
//	alert(arrInitSub);
	fm.InitSubPer.value=arrInitSub[0][0];
	fm.InitSubDept.value=arrInitSub[0][1];
	fm.InitSubDate.value=arrInitSub[0][2];
	fm.InitSubDesc.value=arrInitSub[0][3];
	showOneCodeName('stati','InitSubDept','InitSubDeptName');
	
	//��ѯ�ֹ�˾�ʱ����������ϸ��Ϣ-------����������ơ������ˡ�����ʱ�䡢�������͡��������
	/*var tFilialeSql="select dispper,dispdept,dispdate,disptype,dispidea from llsubmitapply where 1=1 "
    	+" and clmno='"+tClmNo+"'"
    	+" and subno='"+tSubNo+"'"
		+" and subcount='0'"  
		+" order by clmno,subno";  */
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLSubmitQueryInputSql");
		mySql.setSqlId("LLSubmitQuerySql3");
		mySql.addSubPara(tClmNo ); 
		mySql.addSubPara(tSubNo); 
	var arrFiliale=easyExecSql(mySql.getString());
	fm.FilialeDispPer.value=arrFiliale[0][0];
	fm.FilialeDispDept.value=arrFiliale[0][1];
	fm.FilialeDispDate.value=arrFiliale[0][2];
	fm.FilialeDispType.value=arrFiliale[0][3];
	fm.FilialeDispIdea.value=arrFiliale[0][4];
	showOneCodeName('lldisptype','FilialeDispType','FilialeDispTypeName');
	showOneCodeName('stati','FilialeDispDept','FilialeDispDeptName');
	//�ܹ�˾���������ϢӦ���У�����������ơ������ˡ�����ʱ�䡢�������
	/*var tHeadSql="select dispper,dispdept,dispdate,dispidea from llsubmitapply where 1=1 "
    	+" and clmno='"+tClmNo+"'"
    	+" and subno='"+tSubNo+"'"
		+" and subcount='1'"  
		+" order by clmno,subno";  */
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLSubmitQueryInputSql");
		mySql.setSqlId("LLSubmitQuerySql4");
		mySql.addSubPara(tClmNo ); 
		mySql.addSubPara(tSubNo); 
	var arrHead=easyExecSql(mySql.getString());
    if(arrHead!=null)
    {
    	fm.HeadDispPer.value=arrHead[0][0];
		fm.HeadDispDept.value=arrHead[0][1];
		fm.HeadDispDate.value=arrHead[0][2];
		fm.HeadDispIdea.value=arrHead[0][3];
		showOneCodeName('stati','HeadDispDept','HeadDispDeptName');
    }

}

//�������Ϣ
function clearData()
{
	//�ʱ�������ϸ��Ϣ  
	fm.InitSubPer.value="";
	fm.InitSubDept.value="";
	fm.InitSubDeptName.value="";
	fm.InitSubDate.value="";
	fm.InitSubDesc.value="";
	//�ֹ�˾�ʱ����������ϸ��Ϣ
	fm.FilialeDispPer.value="";
	fm.FilialeDispDeptName.value="";
	fm.FilialeDispDept.value="";
	fm.FilialeDispDate.value="";
	fm.FilialeDispType.value="";
	fm.FilialeDispTypeName.value="";
	fm.FilialeDispIdea.value="";
	//�ܹ�˾�ʱ����������ϸ��Ϣ	
	fm.HeadDispPer.value="";
	fm.HeadDispDept.value="";
	fm.HeadDispDeptName.value="";
	fm.HeadDispDate.value="";
	fm.HeadDispIdea.value="";
}