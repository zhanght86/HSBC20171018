/*********************************************************************
 *  程序名称：TempReInAnswerInput.js
 *  程序功能：再保回复
 *  创建日期：2006-11-30 
 *  创建人  ：zhang bin
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
 *********************************************************************
 */

var arrResult1 = new Array();
var arrResult2 = new Array();
var arrResult3 = new Array();
var arrResult4 = new Array();
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var temp = new Array();
var mOperate="";
var ImportPath;
var oldDataSet ; 
var InputFlag="0"; 
var tSearchFlag="0";
var mAppFlag = "";
window.onfocus=myonfocus;

var mContPlanCode = "";
var mRiskCode = "";
var mDutyCode = "";
var mInsuredNo = "";
var mInsuredName = "";

/**
* 显示发送过来的临分申请任务
*/
function showTempGrp(){
	
	var typeRadio="";
	for(i = 0; i <fm.StateRadio.length; i++)
	{
		if(fm.StateRadio[i].checked)
		{
			typeRadio=fm.StateRadio[i].value;
			break;
		}
	}
	if(fm.OperateType.value=='01'){//个险新单
		if(typeRadio=='1'){ //03-临分处理
			divRelaDel.style.display='';
			divTable1.style.display='';
		}else if(typeRadio=='2'){ //04-处理完毕
			divRelaDel.style.display='none';
			divTable1.style.display='none';
		}else{
			myAlert(""+"不知名的处理类型"+"");
			return false;
		}
		var mSQL = "";
		//mSQL = " select x.X1,x.X2,x.X3,(case x.X4 when '01' then '总公司审核' when '02' then '再保审核' when '03' then '临分处理' when '04' then '审核完毕' end),x.X4, (case x.X5 when '1' then '已签单' else '未签单' end),x.X5,x.X6 from ( select (select max(b.PrtNo) from lccont b where trim(b.ProposalContNo)=trim(a.ProposalContNo)) X1,(select max(b.ProposalContNo) from lccont b where trim(b.ProposalContNo)=trim(a.ProposalContNo)) X2, (select max(b.ContNo) from lccont b where trim(b.ProposalContNo)=trim(a.ProposalContNo)) X3, a.State X4, (select appflag from lccont where trim(ProposalContNo)=trim(a.ProposalContNo)) X5,a.serialno X6 From RIGrpState a where 1=1  and ProposalGrpContNo='000000'  and a.exetype='01'  "
		var mySql100=new SqlClass();
			mySql100.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql100.setSqlId("LRRelaTempPolInputSql100");//指定使用的Sql的id
	    	mySql100.addSubPara("1");
	    	mSQL=mySql100.getString();
			var temp='';
		if(typeRadio=='1'){ //03-临分处理
			//mSQL = mSQL + " and a.state='03' ";
			temp='03';
			var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql101.setSqlId("LRRelaTempPolInputSql101");//指定使用的Sql的id
			mySql101.addSubPara(temp);//指定传入的参数
	    	mSQL=mySql101.getString();
		}
		if(typeRadio=='2'){ //04-处理完毕
			//mSQL = mSQL + " and a.state='04' ";
			temp='04';
			var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql102.setSqlId("LRRelaTempPolInputSql102");//指定使用的Sql的id
			mySql102.addSubPara(temp);//指定传入的参数
	    	mSQL=mySql102.getString();
		}
		//mSQL = mSQL + " ) x order by X6 desc " ;
		/**
		var mySql103=new SqlClass();
			mySql103.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql103.setSqlId("LRRelaTempPolInputSql103");//指定使用的Sql的id
			mySql103.addSubPara(mSQL);//指定传入的参数
	    	mSQL=mySql103.getString();
	    	*/
		turnPage1.queryModal(mSQL, GrpTempInsuListGrid); 
	}else{ //团险新单
		if(typeRadio=='1'){ //03-临分处理
			
		}else if(typeRadio=='2'){ //04-处理完毕
			
		}else{
			myAlert(""+"不知名的处理类型"+"");
			return false;
		}
		var mSQL = "";
		var temp='';
		//mSQL = " select x.X1,x.X2,x.X3,(case x.X4 when '01' then '总公司审核' when '02' then '再保审核' when '03' then '临分处理' when '04' then '审核完毕' end),x.X4,(case x.X5 when '1' then '已签单' else '未签单' end),x.X5,x.X6 from (select (select PrtNo from lcgrpcont where trim(ProposalGrpContNo)=trim(a.ProposalGrpContNo)) X1,a.ProposalGrpContNo X2,(select GrpContNo from lcgrpcont where trim(a.proposalgrpcontno)=trim(proposalgrpcontno)) X3,a.State X4,(select appflag from lcgrpcont where trim(ProposalGrpContNo)=trim(a.ProposalGrpContNo)) X5,a.serialno X6 From RIGrpState a where 1=1 and a.ProposalContNo='000000'  and a.exetype='05'  " 
		var mySql104=new SqlClass();
			mySql104.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql104.setSqlId("LRRelaTempPolInputSql104");//指定使用的Sql的id
	    	mySql104.addSubPara("1");
	    	mSQL=mySql104.getString();
		
		
		if(typeRadio=='1'){
			//mSQL = mSQL + " and a.state='03' ";
			temp='03';
			var mySql105=new SqlClass();
				mySql105.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql105.setSqlId("LRRelaTempPolInputSql105");//指定使用的Sql的id
				mySql105.addSubPara(temp);//指定传入的参数
	    		mSQL=mySql105.getString();
		}
		if(typeRadio=='2'){
			//mSQL = mSQL + " and a.state='04' ";
			temp='04';
			var mySql106=new SqlClass();
				mySql106.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql106.setSqlId("LRRelaTempPolInputSql106");//指定使用的Sql的id
				mySql106.addSubPara(temp);//指定传入的参数
	    		mSQL=mySql106.getString();
			
		}
		//mSQL = mSQL + " ) x order by X6 desc " ; 
		/**
		var mySql107=new SqlClass();
				mySql107.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql107.setSqlId("LRRelaTempPolInputSql107");//指定使用的Sql的id
				mySql107.addSubPara(mSQL);//指定传入的参数
	    		mSQL=mySql107.getString();
	    		*/
		turnPage1.queryModal(mSQL, GrpTempInsuListGrid); 
	}
	SearchResultGrid.clearData();
	RelaListGrid.clearData();
	IndTempListGrid.clearData();
	IndRelaListGrid.clearData();
}


//从任务列表中选择查询信息
function QueryPolInfo(){
	var strSQL = "";
	if(fm.OperateType.value=='01'){
		tSel=GrpTempInsuListGrid.getSelNo();
		fm.OperateNo.value = GrpTempInsuListGrid.getRowColData(tSel-1,2);
		fm.ContNo.value = GrpTempInsuListGrid.getRowColData(tSel-1,3);
		fm.SerialNo.value = GrpTempInsuListGrid.getRowColData(tSel-1,8);
		
		if(fm.DeTailFlag.value=='1'){ //到险种
			/**
			var strSQL = " select a.insuredname A1, a.riskcode A2,'000000' A3, a.Mult A4, a.Prem A5, a.Amnt A6, a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9,decode(b.State ,'00','自动分保','01','满足临分条件','02','待临分审核','03','临分','04','临分未实现')A10, b.State A11, b.uwconclusion  A12, a.insuredno A13 from ripolindview a , RIDutyState b  where b.proposalgrpcontno='000000' and b.dutycode = '000000' and b.state = '02' and b.proposalno=a.proposalno and a.ProposalContNo='"+fm.OperateNo.value+"' "
				;
				*/
			var mySql108=new SqlClass();
				mySql108.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql108.setSqlId("LRRelaTempPolInputSql108");//指定使用的Sql的id
				mySql108.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	var strSQL=mySql108.getString();	
				
			turnPage4.queryModal(strSQL, IndTempListGrid);
			//显示已关联的责任保单
			//strSQL = " select a.insuredname A1, a.riskcode A2, '000000' A3,a.Mult A4, a.Prem A5,a.Amnt A6, a.RiskAmnt A7,a.AccAmnt A8, a.ProposalNo A9, decode(b.State ,'00','自动分保','01','满足临分条件','02','待临分审核','03','临分','04','临分未实现')A10, b.State A11, b.uwconclusion A12, a.insuredno A13,(select c.RIPreceptNo from RITempContLink c where c.dutycode = '000000' and c.ProposalNo = a.ProposalNo) A14 from ripolindview a,RIDutyState b where  b.ProposalNo = a.proposalNo and b.DutyCode = '000000' and b.state = '03' and a.ContNo = '"+fm.ContNo.value+"' ";
			var mySql109=new SqlClass();
				mySql109.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql109.setSqlId("LRRelaTempPolInputSql109");//指定使用的Sql的id
				mySql109.addSubPara(fm.ContNo.value);//指定传入的参数
	    	    strSQL=mySql109.getString();
			turnPage5.queryModal(strSQL,IndRelaListGrid);
			
		}
		else{ //到责任
			//strSQL = " select a.contNo from LCCont a where a.proposalcontno='"+fm.OperateNo.value+"'";
			var mySql110=new SqlClass();
				mySql110.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql110.setSqlId("LRRelaTempPolInputSql110");//指定使用的Sql的id
				mySql110.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	    strSQL=mySql110.getString();
			var arrResult = easyExecSql(strSQL);
			fm.ContNo.value = arrResult;
			/**
			var strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,(case when x.A10='00' then '自动分保' when x.A10='01' then '满足临分条件' when x.A10='02' then '待临分审核' when x.A10='03' then '临分' when x.A10='04' then '临分未实现' end),x.A10,x.A11,x.A12 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select trim(b.State) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)=trim(a.dutycode) ) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)=trim(a.dutycode)) A11,a.insuredno A12 from ripoldutyindview a where trim(a.ProposalContNo)='"+fm.OperateNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='02')) x "
				;
			*/
			var mySql111=new SqlClass();
				mySql111.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql111.setSqlId("LRRelaTempPolInputSql111");//指定使用的Sql的id
				mySql111.addSubPara(fm.OperateNo.value);//指定传入的参数
	        var strSQL=mySql111.getString();
			var arrResult = easyExecSql(strSQL);
			turnPage4.queryModal(strSQL, IndTempListGrid);
			
			//显示已关联的责任保单
			//strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,(case when x.A10='00' then '自动分保' when x.A10='01' then '满足临分条件' when x.A10='02' then '待临分审核' when x.A10='03' then '临分' when x.A10='04' then '临分未实现' end),x.A10,x.A11,x.A12,x.A13 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select trim(b.State) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A11,a.insuredno A12,(select b.RIPreceptNo from RITempContLink b where b.dutycode=a.dutycode and b.ProposalNo=a.ProposalNo) A13 from ripoldutyindview a where trim(a.ContNo)='"+fm.ContNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='03')) x ";
			var mySql112=new SqlClass();
				mySql112.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql112.setSqlId("LRRelaTempPolInputSql112");//指定使用的Sql的id
				mySql112.addSubPara(fm.ContNo.value);//指定传入的参数
	            strSQL=mySql112.getString();
			turnPage5.queryModal(strSQL,IndRelaListGrid);
			
		}
	}else if(fm.OperateType.value=='05'){
		//strSQL = " select a.GrpContNo from LCGrpCont a where a.proposalgrpcontno='"+fm.OperateNo.value+"'";
		var mySql113=new SqlClass();
			mySql113.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql113.setSqlId("LRRelaTempPolInputSql113");//指定使用的Sql的id
			mySql113.addSubPara(fm.OperateNo.value);//指定传入的参数
	        strSQL=mySql113.getString();
		var arrResult = easyExecSql(strSQL);
		fm.ContNo.value = arrResult;
		
		for(var i=0;i<GrpTempInsuListGrid.mulLineCount;i++){
			if(trim(GrpTempInsuListGrid.getRowColData(i,2))==trim(fm.ContNo.value)){
				tSel=i+1;
				break;
			}
		}
	}else{
		tSel=GrpTempInsuListGrid.getSelNo();
		if(tSel==0){
			return false;
		}
	}
	if(fm.OperateType.value==null||fm.OperateType.value==""||fm.OperateType.value=="null"){
		var tGrpContNo=GrpTempInsuListGrid.getRowColData(tSel-1,3);
		var tProPosalGrpNo=GrpTempInsuListGrid.getRowColData(tSel-1,2);
		
		fm.GrpContNo.value=tGrpContNo;
		fm.ContNo.value=tProPosalGrpNo;
		//保单状态
		mAppFlag = GrpTempInsuListGrid.getRowColData(tSel-1,7);
	}else if(fm.OperateType.value=="05"){
		//var strSQL = " select a.grpcontno,a.appflag from lcgrpcont a where a.ProPosalGrpContNo='"+fm.ContNo.value+"' ";
		var mySql114=new SqlClass();
			mySql114.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql114.setSqlId("LRRelaTempPolInputSql114");//指定使用的Sql的id
			mySql114.addSubPara(fm.ContNo.value);//指定传入的参数
	    var strSQL=mySql114.getString();
		
		var arrResult = easyExecSql(strSQL);
		fm.GrpContNo.value=arrResult[0][0];
		//保单状态
		mAppFlag = arrResult[0][1];
	}
}

//页面初始化信息
function QueryPagePolInfo(){
	var strSQL = "";
	if(fm.OperateType.value=='01'){
		if(fm.DeTailFlag.value=='1'){ //到险种
			//strSQL = " select a.contNo from LCCont a where a.proposalcontno='"+fm.OperateNo.value+"'";
			var mySql115=new SqlClass();
				mySql115.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql115.setSqlId("LRRelaTempPolInputSql115");//指定使用的Sql的id
				mySql115.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	    strSQL=mySql115.getString();
			var arrResult = easyExecSql(strSQL);
			fm.ContNo.value = arrResult;
			/**
			var strSQL = "select a.insuredname A1, a.riskcode A2,'000000' A3, a.Mult A4, a.Prem A5, a.Amnt A6, a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9,decode(b.State ,'00','自动分保','01','满足临分条件','02','待临分审核','03','临分','04','临分未实现')A10, b.State A11, b.uwconclusion  A12, a.insuredno A13 from ripolindview a , RIDutyState b  where b.proposalgrpcontno='000000' and b.dutycode = '000000' and b.state = '02' and b.proposalno=a.proposalno and a.ProposalContNo='"+fm.OperateNo.value+"'"
			;
			*/
			var mySql116=new SqlClass();
				mySql116.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql116.setSqlId("LRRelaTempPolInputSql116");//指定使用的Sql的id
				mySql116.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	var strSQL=mySql116.getString();
			turnPage4.queryModal(strSQL, IndTempListGrid);
			//strSQL = " select count(*) from RIPolIndView a where a.ContNo='"+fm.ContNo.value+"' and exists (select 'X' from ridutystate b where b.ProposalNo=a.proposalNo and b.DutyCode='000000' and b.state='03' ) ";
			var mySql117=new SqlClass();
				mySql117.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql117.setSqlId("LRRelaTempPolInputSql117");//指定使用的Sql的id
				mySql117.addSubPara(fm.ContNo.value);//指定传入的参数
	    	 	strSQL=mySql117.getString();
			var arrResult = easyExecSql(strSQL);
			if(arrResult!=0){
				//显示已关联的责任保单
				//strSQL = " select a.insuredname A1, a.riskcode A2, '000000' A3,a.Mult A4, a.Prem A5,a.Amnt A6, a.RiskAmnt A7,a.AccAmnt A8, a.ProposalNo A9, decode(b.State ,'00','自动分保','01','满足临分条件','02','待临分审核','03','临分','04','临分未实现')A10, b.State A11, b.uwconclusion A12, a.insuredno A13,(select c.RIPreceptNo from RITempContLink c where c.dutycode = '000000' and c.ProposalNo = a.ProposalNo) A14 from ripolindview a,RIDutyState b where  b.ProposalNo = a.proposalNo and b.DutyCode = '000000' and b.state = '03' and a.ContNo = '"+fm.ContNo.value+"'";
				var mySql118=new SqlClass();
					mySql118.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
					mySql118.setSqlId("LRRelaTempPolInputSql118");//指定使用的Sql的id
					mySql118.addSubPara(fm.ContNo.value);//指定传入的参数
	    	 		strSQL=mySql118.getString();
				turnPage5.queryModal(strSQL,IndRelaListGrid);
				
			}
		}
		else{ //到责任
			//strSQL = " select a.contNo from LCCont a where a.proposalcontno='"+fm.OperateNo.value+"'";
			var mySql119=new SqlClass();
				mySql119.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql119.setSqlId("LRRelaTempPolInputSql119");//指定使用的Sql的id
				mySql119.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	 	strSQL=mySql119.getString();
			var arrResult = easyExecSql(strSQL);
			fm.ContNo.value = arrResult;
			/**
			var strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,decode( x.A10,'00', '自动分保' , '01','满足临分条件' ,'02','待临分审核','03' ,'临分' ,'04','临分未实现'),x.A10,x.A11,x.A12 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select b.State from RIDutyState b where b.proposalno=a.proposalno and b.proposalgrpcontno='000000' and b.dutycode = a.dutycode ) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)=trim(a.dutycode)) A11,a.insuredno A12 from ripoldutyindview a where trim(a.ProposalContNo)='"+fm.OperateNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='02')) x "
				;
			*/
			var mySql120=new SqlClass();
				mySql120.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql120.setSqlId("LRRelaTempPolInputSql120");//指定使用的Sql的id
				mySql120.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	var strSQL=mySql120.getString();
			turnPage4.queryModal(strSQL, IndTempListGrid);
			
			//strSQL = " select count(*) from ripoldutyindview a where a.ContNo='"+fm.ContNo.value+"' and exists (select 'X' from ridutystate b where b.ProposalNo = a.proposalNo and b.DutyCode=a.dutycode and b.state='03' ) ";
			var mySql121=new SqlClass();
				mySql121.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
				mySql121.setSqlId("LRRelaTempPolInputSql121");//指定使用的Sql的id
				mySql121.addSubPara(fm.ContNo.value);//指定传入的参数
	    	 	strSQL=mySql121.getString();
			var arrResult = easyExecSql(strSQL);
			if(arrResult!=0){
				//显示已关联的责任保单
				//strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,(case when x.A10='00' then '自动分保' when x.A10='01' then '满足临分条件' when x.A10='02' then '待临分审核' when x.A10='03' then '临分' when x.A10='04' then '临分未实现' end),x.A10,x.A11,x.A12,x.A13 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select trim(b.State) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A11,a.insuredno A12,(select b.RIPreceptNo from RITempContLink b where b.dutycode=a.DutyCode and b.ProposalNo=a.ProposalNo) A13 from ripoldutyindview a where trim(a.ContNo)='"+fm.ContNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='03')) x ";
				var mySql122=new SqlClass();
					mySql122.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
					mySql122.setSqlId("LRRelaTempPolInputSql122");//指定使用的Sql的id
					mySql122.addSubPara(fm.ContNo.value);//指定传入的参数
	    	 		strSQL=mySql122.getString();
				turnPage5.queryModal(strSQL,IndRelaListGrid);
				
			}
		}
	}else if(fm.OperateType.value=='05'){
		//strSQL = " select a.GrpContNo from LCGrpCont a where a.proposalgrpcontno='"+fm.OperateNo.value+"'";
		var mySql123=new SqlClass();
			mySql123.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql123.setSqlId("LRRelaTempPolInputSql123");//指定使用的Sql的id
			mySql123.addSubPara(fm.OperateNo.value);//指定传入的参数
	    	strSQL=mySql123.getString();
		var arrResult = easyExecSql(strSQL);
		fm.ContNo.value = arrResult;
		
		for(var i=0;i<GrpTempInsuListGrid.mulLineCount;i++){
			if(trim(GrpTempInsuListGrid.getRowColData(i,2))==trim(fm.ContNo.value)){
				tSel=i+1;
				break;
			}
		}
	}else{
		tSel=GrpTempInsuListGrid.getSelNo();
		if(tSel==0){
			return false;
		}
	}
	if(fm.OperateType.value==null||fm.OperateType.value==""||fm.OperateType.value=="null"){
		var tGrpContNo=GrpTempInsuListGrid.getRowColData(tSel-1,3);
		var tProPosalGrpNo=GrpTempInsuListGrid.getRowColData(tSel-1,2);
		
		fm.GrpContNo.value=tGrpContNo;
		fm.ContNo.value=tProPosalGrpNo;
		//保单状态
		mAppFlag = GrpTempInsuListGrid.getRowColData(tSel-1,7);
	}else if(fm.OperateType.value=="05"){
		//var strSQL = " select a.grpcontno,a.appflag from lcgrpcont a where a.ProPosalGrpContNo='"+fm.ContNo.value+"' ";
		var mySql124=new SqlClass();
			mySql124.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
			mySql124.setSqlId("LRRelaTempPolInputSql124");//指定使用的Sql的id
			mySql124.addSubPara(fm.ContNo.value);//指定传入的参数
	    var strSQL=mySql124.getString();
		var arrResult = easyExecSql(strSQL);
		fm.GrpContNo.value=arrResult[0][0];
		//保单状态
		mAppFlag = arrResult[0][1];
	}
}

////显示已关联的责任保单
//function showRelaPol(){
//	var mSQL = " select (select prtno from lcgrpcont where trim(ProposalGrpContNo)=trim(a.ProposalGrpContNo)),a.ProposalGrpContNo,a.ContPlanCode,a.ProposalContNo,a.ProposalNo,(select name from lcinsured where trim(insuredno)=trim(a.insuredno) and trim(contno)=trim(a.ProposalContNo)),a.Insuredno,a.riskcode,a.dutycode,(case a.RelaType when '01' then '个人责任保单关联' when '02' then '保障计划关联' else '团单关联' end ), (select case state when '00' then '自动分保' when '01' then '满足临分条件' when '02' then '待审核' when '03' then '临分' when '04' then '临分未实现' else '不知名状态' end from RIDutyState where trim(ProposalGrpContNo)=trim(a.ProposalGrpContNo) and trim(ProposalNo)=trim(a.ProposalNo) and trim(DutyCode)=trim(a.dutycode)),a.RelaType from RITempContLink a where trim(a.RIContNo)='"+fm.RIContNo.value+"' and trim(a.RIPreceptNo)='"+fm.RIPreceptNo.value+"' and a.ProposalGrpContNo='"+fm.ContNo.value+"' order by a.RelaType desc " ;
//	turnPage3.queryModal(mSQL,RelaListGrid);
//}

function SearchRecord(){
	if(fm.GrpContNo.value==null||fm.GrpContNo.value==""){
		myAlert(" "+"请在团体临分申请列表中选择保单！"+" ");
		return false;
	}
	/**
	var mSQL =" select a.prtno,a.GrpContNo,(select contplancode from lcinsured where insuredno = a.insuredno and contno = a.contno),a.contno,a.proposalno,insuredname,insuredno,riskcode,dutycode,select decode( state , '00' , '自动分保' , '01' , '满足临分条件' , '02' , '待审核' , '03' , '临分' , '04' , '临分未实现' , '不知名状态') from RIDutyState where proposalno = a.proposalno and dutycode = b.dutycode) from lcpol a , lcduty b where a.polno=b.polno "
	+ " and exists (select 'X' from lcgrpcont where proposalgrpcontno='"+ fm.ContNo.value +"' and a.grpcontno=grpcontno) "
	+ getWherePart("a.riskcode","RiskCode")
	+ getWherePart("b.DutyCode","DutyCode")
	+ getWherePart("a.insuredno","InsuredNo")
	+ getWherePart("a.insuredName","InsuredName","like")
  ;
  */
  var mySql125=new SqlClass();
	  mySql125.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
	  mySql125.setSqlId("LRRelaTempPolInputSql125");//指定使用的Sql的id
	  mySql125.addSubPara(fm.ContNo.value);//指定传入的参数
	  /**
 	  mySql125.addSubPara(getWherePart("a.riskcode","RiskCode"));//指定传入的参数
 	  mySql125.addSubPara(getWherePart("b.DutyCode","DutyCode"));//指定传入的参数
 	  mySql125.addSubPara(getWherePart("a.insuredno","InsuredNo"));//指定传入的参数
 	  mySql125.addSubPara(getWherePart("a.insuredName","InsuredName","like"));//指定传入的参数
 	  */
 	  mySql125.addSubPara(fm.RiskCode.value);//指定传入的参数
 	  mySql125.addSubPara(fm.DutyCode.value);//指定传入的参数
 	  mySql125.addSubPara(fm.InsuredNo.value);//指定传入的参数
 	  mySql125.addSubPara(fm.InsuredName.value);//指定传入的参数
  var mSQL=mySql125.getString();
  if(fm.ContPlanCode.value!=""&&fm.ContPlanCode.value!=null){
  	//mSQL = mSQL + " and exists (select contplancode from lcinsured where insuredno=a.insuredno and contno=a.contno and contplancode='"+fm.ContPlanCode.value+"')";
	var mySql126=new SqlClass();
	  	mySql126.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
	  	mySql126.setSqlId("LRRelaTempPolInputSql126");//指定使用的Sql的id
	  	mySql126.addSubPara(fm.RiskCode.value);//指定传入的参数
 	    mySql126.addSubPara(fm.DutyCode.value);//指定传入的参数
 	    mySql126.addSubPara(fm.InsuredNo.value);//指定传入的参数
 	    mySql126.addSubPara(fm.InsuredName.value);//指定传入的参数
	  	mySql126.addSubPara(fm.ContNo.value);//指定传入的参数
 	 	mySql126.addSubPara(fm.ContPlanCode.value);//指定传入的参数
  	 	mSQL=mySql126.getString();
  }
  //mSQL = mSQL + " and exists (select 'X' from RIDutyState where ProposalNo=a.ProposalNo and DutyCode=b.DutyCode and State = '02' )";
  /**
  var mySql127=new SqlClass();
	  mySql127.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
	  mySql127.setSqlId("LRRelaTempPolInputSql127");//指定使用的Sql的id
	  mySql127.addSubPara(mSQL);//指定传入的参数
  	  mSQL=mySql127.getString();
  	  */
  mContPlanCode = fm.ContPlanCode.value ;
	mRiskCode = fm.RiskCode.value ;
	mDutyCode = fm.DutyCode.value ;
	mInsuredNo = fm.InsuredNo.value ;
	mInsuredName = fm.InsuredName.value ;
  tSearchFlag = "1"; //置已查询标记
	turnPage2.queryModal(mSQL,SearchResultGrid);
}

function ResetForm(){
	fm.ContPlanCode.value = "";
	fm.RiskCode.value = "";
	fm.DutyCode.value = "";
	fm.ContPlanCodeName.value = "";
	fm.RiskCodeName.value = "";
	fm.DutyCodeName.value = "";
	fm.InsuredNo.value = "";
	fm.InsuredName.value = "";
	tSearchFlag ="0"; //清除已查询标记
	SearchResultGrid.clearData();
}
		
function afterSubmit(FlagStr, content ){ 
  showInfo.close();
  if (FlagStr == "Fail" ){             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    location.reload(true);
  }
}
   
function clearData(){ 
	if(InputFlag=="0"){
		fm.Remark.value=""; 
	}
	InputFlag="1"; 
}

function TempConclusionAll(){
	if(mAppFlag!="1"){
		myAlert(""+"保单未签单不能下临分结论"+"");
		return false;
	}
	if (!confirm(""+"要将所有查询结果关联到临分合同吗"+"")) 
	{
    return false;
	}
	if(!verify1()){
		return false;
	}
	if(!VerifySearch()){
		myAlert(""+"查询条件已经修改，请重新查询后再下结论！"+"");
		return false;
	}
	var showStr=""+"正在保存数据......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论
	fm.action = "./LRRelaTempPolSave.jsp?CONOPETYPE=01";
  fm.submit();
}

function VerifySearch(){
  if(mContPlanCode != fm.ContPlanCode.value) return false;
	if(mRiskCode != fm.RiskCode.value) return false;
	if(mDutyCode != fm.DutyCode.value) return false;
	if(mInsuredNo != fm.InsuredNo.value) return false;
	if(mInsuredName != fm.InsuredName.value) return false;
	
	return true;
}

function TempConclusionSel(){
	if(fm.OperateType.value=='01'){ //个险
		
		var Count=IndTempListGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(IndTempListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		
		if (chkFlag==false){
			myAlert(""+"请选中保单信息"+"");
			return false;
		}
		var showStr=""+"正在保存数据......"+""; 
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论
		fm.action = "./LRRelaTempPolSave.jsp?CONOPETYPE=02&ContType=1";
		
	}else if(fm.OperateType.value=='05'){ //团险
		var tSel = GrpTempInsuListGrid.getSelNo();
		
		if(fm.ContNo.value==null||fm.ContNo.value==""){
			myAlert( ""+"请先选择团体临分申请列表记录."+"" );
			return false;
		}
		var relaMode=fm.RelaMode.value;
		if(relaMode==''||relaMode==null){
			myAlert(" "+"请选择关联方式"+"");
			return false;
		}
		if(relaMode=='01'){
			var Count=SearchResultGrid.mulLineCount; 
			var chkFlag=false; 
			for (i=0;i<Count;i++){
				if(SearchResultGrid.getChkNo(i)==true){
					chkFlag=true;
				}
			}
			if (chkFlag==false){
				myAlert(""+"请选中关联查询结果"+"");
				return false;
			}
		}
		if(mAppFlag!="1"){
			myAlert(""+"保单未签单不能下临分结论"+"");
			return false;
		}
		if(relaMode=='02'){
			if(!verify2()){
				return false;
			}
		}
		if(relaMode=='03'){
			if(!verify3()){
				return false;
			} 
		}
		var showStr=""+"正在保存数据......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论
		fm.action = "./LRRelaTempPolSave.jsp?CONOPETYPE=02&ContType=2"; 
	}
  fm.submit(); 
}

function verify1(){
	if(tSearchFlag!="1"){
		myAlert(""+"请查询后再关联责任保单"+"");
		return false;
	}
	/**
	mSQL = " select * from RITempContLink a where a.RIContNo='"+fm.RIContNo.value+"' and a.RIPreceptNo='"+fm.RIPreceptNo.value+"' and a.ProposalGrpContNo='"+fm.ContNo.value+"' and a.RelaType='"+fm.RelaMode.value+"' "
	+ getWherePart("a.ContPlanCode","ContPlanCode")
	+ getWherePart("a.riskcode","RiskCode")
	+ getWherePart("b.DutyCode","DutyCode")
	+ getWherePart("a.insuredno","InsuredNo")
	*/
	var mySql128=new SqlClass();
	  mySql128.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
	  mySql128.setSqlId("LRRelaTempPolInputSql128");//指定使用的Sql的id
	  mySql128.addSubPara(fm.RIContNo.value);//指定传入的参数
	  mySql128.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
	  mySql128.addSubPara(fm.ContNo.value);//指定传入的参数
	  mySql128.addSubPara(fm.RelaMode.value);//指定传入的参数
	  /**
	  mySql128.addSubPara(getWherePart("a.ContPlanCode","ContPlanCode"));//指定传入的参数
	  mySql128.addSubPara(getWherePart("a.riskcode","RiskCode"));//指定传入的参数
	  mySql128.addSubPara(getWherePart("b.DutyCode","DutyCode"));//指定传入的参数
	  mySql128.addSubPara(getWherePart("a.insuredno","InsuredNo"));//指定传入的参数
	  */
	  mySql128.addSubPara(fm.ContPlanCode.value);//指定传入的参数
	  mySql128.addSubPara(fm.RiskCode.value);//指定传入的参数
	  mySql128.addSubPara(fm.DutyCode.value);//指定传入的参数
	  mySql128.addSubPara(fm.InsuredNo.value);//指定传入的参数
  	  mSQL=mySql128.getString();
	if(fm.InsuredName.value!=""&&fm.InsuredName.value!=null){
  	//mSQL = mSQL + " and exists (select * from lcinsured where insuredno=a.insuredno and insuredName like '%"+fm.InsuredName.value+"%')";
 	var str1="'%"+fm.InsuredName.value+"%')";
 	var mySql129=new SqlClass();
	 	mySql129.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
	  	mySql129.setSqlId("LRRelaTempPolInputSql129");//指定使用的Sql的id
	  	mySql129.addSubPara(fm.RIContNo.value);//指定传入的参数
	    mySql129.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
	    mySql129.addSubPara(fm.ContNo.value);//指定传入的参数
	    mySql129.addSubPara(fm.RelaMode.value);//指定传入的参数
	  	mySql129.addSubPara(fm.ContPlanCode.value);//指定传入的参数
	    mySql129.addSubPara(fm.RiskCode.value);//指定传入的参数
	    mySql129.addSubPara(fm.DutyCode.value);//指定传入的参数
	    mySql129.addSubPara(fm.InsuredNo.value);//指定传入的参数
	  	mySql129.addSubPara(str1);//指定传入的参数
  	  	mSQL=mySql129.getString();
  }
  var arrResult = easyExecSql(mSQL);
  if(arrResult!=""&&arrResult!=null){
  	myAlert(" "+"部分或全部保单已经关联到临分合同，不能进行再关联"+" ");
  	return false;
  }
  
	return true;
}

function verify2(){
	if(fm.ContNo.value==''||fm.ContNo.value==null){
		myAlert(""+"请先在团体临分申请列表中选择团体保单!"+"");
		return false;
	}
	if(fm.ContPlanMode.value==''||fm.ContPlanMode.value==null){
		myAlert(""+"保障计划不能为空"+"");
		return false;
	}
	if(fm.DutyCode1.value==''||fm.DutyCode1.value==null){
		myAlert(""+"责任编码不能为空"+"");
		return false;
	}
	return true;
}

function verify3(){
	if(fm.ContNo.value==''||fm.ContNo.value==null){
		myAlert(""+"请先在团体临分申请列表中选择团体保单!"+"");
		return false;
	}
	if(fm.DutyCode1.value==''||fm.DutyCode1.value==null){
		myAlert(""+"责任编码不能为空"+"");
		return false;
	}
	return true;
}

/**
*查询团单
*/
function QueryGrpNewAudit(){
	var tSel = GrpTempInsuListGrid.getSelNo();
 	var tGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,2);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'核保发送',(select case State when '00' then '待回复' when '01' then '已回复' else '办结' end from LCReInsurTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and trim(a.AuditType)=trim(AuditType) and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) "
	+ " from LCReInsurUWIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000' and AuditCode='000000' and AuditAffixCode='000000' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'再保回复',(select case State when '00' then '待回复' when '01' then '已回复' else '办结' end from LCReInsurTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and AuditCode=AuditCode and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) " 
	+ " from LCReInsurIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000'  and AuditCode='000000' and AuditAffixCode='000000') order by uwno desc"
	;	
	*/
	var mySql130=new SqlClass();
	 	mySql130.setResourceName("reinsure.LRRelaTempPolInputSql"); //指定使用的properties文件名
	  	mySql130.setSqlId("LRRelaTempPolInputSql130");//指定使用的Sql的id
	  	mySql130.addSubPara(tGrpContNo);//指定传入的参数
	  	mySql130.addSubPara(tGrpContNo);//指定传入的参数
  	  	mSQL=mySql130.getString();
	
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}

function DeleteRelaList()
{
	//个险
	if(fm.OperateType.value=='01'){
		var Count=IndRelaListGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(IndRelaListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"请在关联列表选中要删除的记录"+"");
			return false;
		}
		var showStr=""+"正在删除数据......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论
		fm.action = "./LRRelaTempPolSave.jsp?CONOPETYPE=03&ContType=1"; 
		
	}else if(fm.OperateType.value=='05'){
		var Count=RelaListGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(RelaListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"请在关联列表选中要删除的记录"+"");
			return false;
		}
		var showStr=""+"正在删除数据......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论
		fm.action = "./LRRelaTempPolSave.jsp?CONOPETYPE=03"; 		
	}
  fm.submit(); 
}

function DeleteRelaAll(){
	
}

function afterCodeSelect( cCodeName, Field ) 
{
	if(Field.value=='01'){
		divButton1.style.display='';
		divSearch.style.display='';
		divContPlanCodeName.style.display='none';
		divDutyCodeName.style.display='none';
		fm.DutyCode1.style.display='none';
		fm.ContPlanMode.style.display='none';
		divRelaAll.style.display='';
		
	}else if(Field.value=='02'){
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='';
		fm.ContPlanMode.style.display='';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		divRelaAll.style.display='none';
		
	}else if(Field.value=='03'){
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='none';
		fm.ContPlanMode.style.display='none';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		divRelaAll.style.display='none';
	}
}
