var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//呈报查询
function queryLLSubmitApplyGrid()
{
//     var strSQL = "select clmno,subno,subcount,customerno,customername,vipflag,initphase,subtype,subrcode,subdesc,subper,subdate,subdept,substate,DispIdea,remark from LLSubmitApply where 1=1 "
     /*var strSQL = "select clmno,subno,subcount,customerno,customername,vipflag,initphase,subrcode,subper,subdate,subdept"
     			 +" , case substate when '0' then '申请' when '1' then '完成' end "
     			 +" from llsubmitapply where 1=1 "
                 +" and clmno='"+ fm.ClmNo.value+"' and subcount='0' "
                 +" order by clmno";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitQueryInputSql");
	mySql.setSqlId("LLSubmitQuerySql1");
	mySql.addSubPara(fm.ClmNo.value ); 
     turnPage.queryModal(mySql.getString(), LLSubmitApplyGrid);
}

//选中LLSubmitApplyGrid响应事件
function LLSubmitApplyGridClick()
{
	clearData();//清除表单信息
    var i = LLSubmitApplyGrid.getSelNo()-1;
    var tClmNo=LLSubmitApplyGrid.getRowColData(i,1);//赔案号
    var tSubNo=LLSubmitApplyGrid.getRowColData(i,2);//呈报序号
    //查询呈报发起信息,呈报次数为“0”,  发起机构名称、发起人、发起时间、呈报描述
   /* *var tInitSubSql="select subper,subdept,subdate,subdesc from llsubmitapply where 1=1 "
    	+" and clmno='"+tClmNo+"'"
    	+" and subno='"+tSubNo+"'"
		+" and subcount='0'"  
		+" order by clmno,subno";  */
	     mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitQueryInputSql");
	mySql.setSqlId("LLSubmitQuerySql2");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tSubNo); 
	var arrInitSub=easyExecSql(mySql.getString());
//	alert(arrInitSub);
	fm.InitSubPer.value=arrInitSub[0][0];
	fm.InitSubDept.value=arrInitSub[0][1];
	fm.InitSubDate.value=arrInitSub[0][2];
	fm.InitSubDesc.value=arrInitSub[0][3];
	showOneCodeName('stati','InitSubDept','InitSubDeptName');
	
	//查询分公司呈报处理情况详细信息-------处理机构名称、处理人、处理时间、处理类型、处理意见
	/*var tFilialeSql="select dispper,dispdept,dispdate,disptype,dispidea from llsubmitapply where 1=1 "
    	+" and clmno='"+tClmNo+"'"
    	+" and subno='"+tSubNo+"'"
		+" and subcount='0'"  
		+" order by clmno,subno";  */
		     mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitQueryInputSql");
	mySql.setSqlId("LLSubmitQuerySql3");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tSubNo); 
	var arrFiliale=easyExecSql(mySql.getString());
	fm.FilialeDispPer.value=arrFiliale[0][0];
	fm.FilialeDispDept.value=arrFiliale[0][1];
	fm.FilialeDispDate.value=arrFiliale[0][2];
	fm.FilialeDispType.value=arrFiliale[0][3];
	fm.FilialeDispIdea.value=arrFiliale[0][4];
	showOneCodeName('lldisptype','FilialeDispType','FilialeDispTypeName');
	showOneCodeName('stati','FilialeDispDept','FilialeDispDeptName');
	//总公司处理情况信息应该有：处理机构名称、处理人、处理时间、处理意见
	/*var tHeadSql="select dispper,dispdept,dispdate,dispidea from llsubmitapply where 1=1 "
    	+" and clmno='"+tClmNo+"'"
    	+" and subno='"+tSubNo+"'"
		+" and subcount='1'"  
		+" order by clmno,subno";  */
			     mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitQueryInputSql");
	mySql.setSqlId("LLSubmitQuerySql4");
	mySql.addSubPara(tClmNo); 
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

//清除表单信息
function clearData()
{
	//呈报发起详细信息  
	fm.InitSubPer.value="";
	fm.InitSubDept.value="";
	fm.InitSubDeptName.value="";
	fm.InitSubDate.value="";
	fm.InitSubDesc.value="";
	//分公司呈报处理情况详细信息
	fm.FilialeDispPer.value="";
	fm.FilialeDispDeptName.value="";
	fm.FilialeDispDept.value="";
	fm.FilialeDispDate.value="";
	fm.FilialeDispType.value="";
	fm.FilialeDispTypeName.value="";
	fm.FilialeDispIdea.value="";
	//总公司呈报处理情况详细信息	
	fm.HeadDispPer.value="";
	fm.HeadDispDept.value="";
	fm.HeadDispDeptName.value="";
	fm.HeadDispDate.value="";
	fm.HeadDispIdea.value="";
}