//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var eventType;

function queryClick()
{
	fm.OperateType.value="QUERY";
	
  BusynessGrid.clearData();
  EdorGrid.clearData();
  ClaimGrid.clearData();
  
	if (!verifyInput()){
    return false;
  }
  if (!verifyInput1()){
    return false;
  }
	var sqlStr=""; 
	eventType = fm.EventType.value;
	if(eventType=='01'){
		polQuery();
	}else if(eventType=='03'){
		edorQuery();
	}else if(eventType=='04'){
		claimQuery();
	}
}

function exportExcel(){
	fm.OperateType.value="DOWNLOAD";
	if(verifyInput()){
		fm.target="fraSubmit";
		//fm.all("downloadType").value="xls";
		fm.action="./LRSearchSave.jsp";
		fm.submit(); //提交
	}
}

function polQuery(){
	var strSQL;
	/**
	strSQL = "select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12),max(x.A13),sum(x.A14),sum(x.A15),sum(x.A16),x.A17,max(x.A18),max(x.A19),max(x.A20),max(x.A21),max(x.A22) getdate from ("
	+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,decode(a.InsuredSex,0 , '男' , '女') A4,a.insuredage A5,a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,a.accamnt A13,b.CessionAmount A14,b.PremChang A15,b.Commchang A16,a.eventno A17,a.standbydouble2 A18,a.standbydouble3 A19,a.peakline A20, a.payyears A21, a.getdate A22 "
	+ " from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType in('01','02') and a.GetDate between to_date('"+fm.StartDate.value+"') and to_date('"+fm.EndDate.value+"') "
	+ " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"+fm.RIcomCode.value+"') " 
	;
	*/
	//var date1=fm.EndDate.value+"')" ;
	var date1=fm.EndDate.value;
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRSearchInputSql100");//指定使用的Sql的id
		mySql100.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql100.addSubPara(date1);//指定传入的参数
		mySql100.addSubPara(fm.RIcomCode.value);//指定传入的参数
	    strSQL=mySql100.getString();
	 /**   
	if(fm.TempType.value=="1"){
		//strSQL = strSQL + " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRSearchInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql101.addSubPara(date1);//指定传入的参数
		mySql101.addSubPara(fm.RIcomCode.value);//指定传入的参数
	    strSQL=mySql101.getString();
	}else if(fm.TempType.value=="2"){
		//strSQL = strSQL + " and b.StandbyString1='03' ";
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRSearchInputSql102");//指定使用的Sql的id
		mySql102.addSubPara(strSQL);//指定传入的参数
	    strSQL=mySql102.getString();
	}
	if(fm.ContType.value=="1"){
		if(fm.ContNo.value!=null&&fm.ContNo.value!=""){
			//strSQL = strSQL + " and a.contno='"+fm.ContNo.value+"'";
			var mySql103=new SqlClass();
				mySql103.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql103.setSqlId("LRSearchInputSql103");//指定使用的Sql的id
				mySql103.addSubPara(strSQL);//指定传入的参数
				mySql103.addSubPara(fm.ContNo.value);//指定传入的参数
	    		strSQL=mySql103.getString();
		}
	}else if(fm.ContType.value=="2"){
		if(fm.GrpContNo.value!=null&&fm.GrpContNo.value!=""){
			//strSQL = strSQL + " and a.grpcontno='"+fm.GrpContNo.value+"'";
			var mySql104=new SqlClass();
				mySql104.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql104.setSqlId("LRSearchInputSql104");//指定使用的Sql的id
				mySql104.addSubPara(strSQL);//指定传入的参数
				mySql104.addSubPara(fm.GrpContNo.value);//指定传入的参数
	    		strSQL=mySql104.getString();
		}
	}else{
		alert("非法团个标志");
		return false;
	}
	if(fm.AccumulateDefNO.value!=null&&fm.AccumulateDefNO.value!=""){
		//strSQL = strSQL + " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+fm.AccumulateDefNO.value+"') " ;	
		var mySql105=new SqlClass();
				mySql105.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql105.setSqlId("LRSearchInputSql105");//指定使用的Sql的id
				mySql105.addSubPara(strSQL);//指定传入的参数
				mySql105.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
	    		strSQL=mySql105.getString();
	}
	if(fm.InsuredNo.value!=null&&fm.InsuredNo.value!=""){
		//strSQL = strSQL + " and a.InsuredNo='"+fm.InsuredNo.value+"'";
		var mySql106=new SqlClass();
			mySql106.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql106.setSqlId("LRSearchInputSql106");//指定使用的Sql的id
			mySql106.addSubPara(strSQL);//指定传入的参数
			mySql106.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    	strSQL=mySql106.getString();
	}
	if(fm.InsuredName.value!=null&&fm.InsuredName.value!=""){
		//strSQL = strSQL +" and a.InsuredName like '%%"+fm.InsuredName.value+"%%'";
		var mySql107=new SqlClass();
			mySql107.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql107.setSqlId("LRSearchInputSql107");//指定使用的Sql的id
			mySql107.addSubPara(strSQL);//指定传入的参数
			mySql107.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql107.getString();
	}
	//strSQL = strSQL + " and rownum<=500 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate" ;
	

	
	
	var mySql108=new SqlClass();
		mySql108.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql108.setSqlId("LRSearchInputSql108");//指定使用的Sql的id
		mySql108.addSubPara(strSQL);//指定传入的参数
	    strSQL=mySql108.getString();
	turnPage.queryModal(strSQL,BusynessGrid);
	alert("uuuuuuuuuuuuuuuuuuu");
	*/
	if(fm.TempType.value=="1"){
		//strSQL = strSQL + " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRSearchInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql101.addSubPara(date1);//指定传入的参数
		mySql101.addSubPara(fm.RIcomCode.value);//指定传入的参数
	    strSQL=mySql101.getString();
	    
	    
	    if(fm.ContType.value=="1"){
		if(fm.ContNo.value!=null&&fm.ContNo.value!=""){
			//strSQL = strSQL + " and a.contno='"+fm.ContNo.value+"'";
			var mySql103=new SqlClass();
				mySql103.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql103.setSqlId("LRSearchInputSql103");//指定使用的Sql的id
				mySql103.addSubPara(fm.StartDate.value);//指定传入的参数
				mySql103.addSubPara(date1);//指定传入的参数
				mySql103.addSubPara(fm.RIcomCode.value);//指定传入的参数
				mySql103.addSubPara(fm.ContNo.value);//指定传入的参数
				mySql103.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
				mySql103.addSubPara(fm.InsuredNo.value);//指定传入的参数
				mySql103.addSubPara(fm.InsuredName.value);//指定传入的参数
	    		strSQL=mySql103.getString();
		}
	}else if(fm.ContType.value=="2"){
		if(fm.GrpContNo.value!=null&&fm.GrpContNo.value!=""){
			//strSQL = strSQL + " and a.grpcontno='"+fm.GrpContNo.value+"'";
			var mySql104=new SqlClass();
				mySql104.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql104.setSqlId("LRSearchInputSql104");//指定使用的Sql的id
				mySql104.addSubPara(fm.StartDate.value);//指定传入的参数
				mySql104.addSubPara(date1);//指定传入的参数
				mySql104.addSubPara(fm.RIcomCode.value);//指定传入的参数
				mySql104.addSubPara(fm.GrpContNo.value);//指定传入的参数
				mySql104.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
				mySql104.addSubPara(fm.InsuredNo.value);//指定传入的参数
				mySql104.addSubPara(fm.InsuredName.value);//指定传入的参数
	    		strSQL=mySql104.getString();
		}
	}else{
		myAlert(""+"非法团个标志"+"");
		return false;
	}
	}else if(fm.TempType.value=="2"){
		//strSQL = strSQL + " and b.StandbyString1='03' ";
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRSearchInputSql102");//指定使用的Sql的id
		mySql102.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql102.addSubPara(date1);//指定传入的参数
		mySql102.addSubPara(fm.RIcomCode.value);//指定传入的参数
	    strSQL=mySql102.getString();
	    if(fm.ContType.value=="1"){
			if(fm.ContNo.value!=null&&fm.ContNo.value!=""){
			//strSQL = strSQL + " and a.contno='"+fm.ContNo.value+"'";
			var mySql105=new SqlClass();
				mySql105.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql105.setSqlId("LRSearchInputSql105");//指定使用的Sql的id
				mySql105.addSubPara(fm.StartDate.value);//指定传入的参数
				mySql105.addSubPara(date1);//指定传入的参数
				mySql105.addSubPara(fm.RIcomCode.value);//指定传入的参数
				mySql105.addSubPara(fm.ContNo.value);//指定传入的参数
				mySql105.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
				mySql105.addSubPara(fm.InsuredNo.value);//指定传入的参数
				mySql105.addSubPara(fm.InsuredName.value);//指定传入的参数
	    		strSQL=mySql105.getString();
			}
		}else if(fm.ContType.value=="2"){
		if(fm.GrpContNo.value!=null&&fm.GrpContNo.value!=""){
			//strSQL = strSQL + " and a.grpcontno='"+fm.GrpContNo.value+"'";
			var mySql106=new SqlClass();
				mySql106.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
				mySql106.setSqlId("LRSearchInputSql106");//指定使用的Sql的id
				mySql106.addSubPara(fm.StartDate.value);//指定传入的参数
				mySql106.addSubPara(date1);//指定传入的参数
				mySql106.addSubPara(fm.RIcomCode.value);//指定传入的参数
				mySql106.addSubPara(fm.GrpContNo.value);//指定传入的参数
				mySql106.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
				mySql106.addSubPara(fm.InsuredNo.value);//指定传入的参数
				mySql106.addSubPara(fm.InsuredName.value);//指定传入的参数
	    		strSQL=mySql106.getString();
		}
	}else{
		myAlert(""+"非法团个标志"+"");
		return false;
	}
	   
	}
	//strSQL = strSQL + " and rownum<=500 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate" ;
	turnPage.queryModal(strSQL,BusynessGrid); 
}

function edorQuery(){
	var strSQL;
	/**
	strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),sum(x.A17),x.A18,max(x.A19),max(x.A20), max(x.A21),max(x.A22),max(x.A23) getdate "
	+ " from ("
	+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,decode( a.InsuredSex,0 , '男' , '女' ) A4,a.insuredage A5,a.standbydate1 A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16, b.Commchang A17,a.eventno A18,a.standbydouble2 A19,a.standbydouble3 A20,a.peakline A21,a.payyears A22,a.getdate A23 "
	+"　from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '03' and a.GetDate between to_date('"+fm.StartDate.value+"') and to_date('"+fm.EndDate.value+"')"
	+ " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"+fm.RIcomCode.value+"') "
	//+ " and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+fm.AccumulateDefNO.value+"') "
	;
	*/
	myAlert("==============");
	var mySql109=new SqlClass();
		mySql109.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql109.setSqlId("LRSearchInputSql109");//指定使用的Sql的id
		mySql109.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql109.addSubPara(fm.EndDate.value);//指定传入的参数
		mySql109.addSubPara(fm.RIcomCode.value);//指定传入的参数
	    strSQL=mySql109.getString();
	if(fm.TempType.value=="1"){
		//strSQL = strSQL + " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
		var mySql110=new SqlClass();
			mySql110.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql110.setSqlId("LRSearchInputSql110");//指定使用的Sql的id
			mySql110.addSubPara(fm.StartDate.value);//指定传入的参数
			mySql110.addSubPara(fm.EndDate.value);//指定传入的参数
			mySql110.addSubPara(fm.RIcomCode.value);//指定传入的参数
			mySql110.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			mySql110.addSubPara(fm.GrpContNo.value);//指定传入的参数
			mySql110.addSubPara(fm.InsuredNo.value);//指定传入的参数
			mySql110.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql110.getString();
	}else if(fm.TempType.value=="2"){
		//strSQL = strSQL + " and b.StandbyString1='03' ";
		var mySql111=new SqlClass();
			mySql111.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql111.setSqlId("LRSearchInputSql111");//指定使用的Sql的id
			mySql111.addSubPara(fm.StartDate.value);//指定传入的参数
			mySql111.addSubPara(fm.EndDate.value);//指定传入的参数
			mySql111.addSubPara(fm.RIcomCode.value);//指定传入的参数
			mySql111.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			mySql111.addSubPara(fm.GrpContNo.value);//指定传入的参数
			mySql111.addSubPara(fm.InsuredNo.value);//指定传入的参数
			mySql111.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql111.getString();
	    	strSQL=mySql111.getString();
	}
	/**
	if(fm.AccumulateDefNO.value!=null&&fm.AccumulateDefNO.value!=""){
		//strSQL = strSQL + " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+fm.AccumulateDefNO.value+"') " ;	
		var mySql112=new SqlClass();
			mySql112.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql112.setSqlId("LRSearchInputSql112");//指定使用的Sql的id
			mySql112.addSubPara(strSQL);//指定传入的参数
			mySql112.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
	    	strSQL=mySql112.getString();

	}
	alert("--------------------------");
	
	if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!=null){
		//strSQL = strSQL + " and a.grpcontno='"+fm.GrpContNo.value+"'";
		var mySql113=new SqlClass();
			mySql113.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql113.setSqlId("LRSearchInputSql113");//指定使用的Sql的id
			mySql113.addSubPara(strSQL);//指定传入的参数
			mySql113.addSubPara(fm.GrpContNo.value);//指定传入的参数
	    	strSQL=mySql113.getString();
	}
	if(fm.InsuredNo.value!=""&&fm.InsuredNo.value!=null){
		//strSQL = strSQL + " and a.InsuredNo='"+fm.InsuredNo.value+"'";
		var mySql114=new SqlClass();
			mySql114.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql114.setSqlId("LRSearchInputSql114");//指定使用的Sql的id
			mySql114.addSubPara(strSQL);//指定传入的参数
			mySql114.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    	strSQL=mySql114.getString();
	}
	if(fm.InsuredName.value!=""&&fm.InsuredName.value!=null){
		//strSQL = strSQL +" and a.InsuredName like '%%"+fm.InsuredName.value+"%%'";
		var mySql115=new SqlClass();
			mySql115.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql115.setSqlId("LRSearchInputSql115");//指定使用的Sql的id
			mySql115.addSubPara(strSQL);//指定传入的参数
			mySql115.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql115.getString();
	}
	//strSQL = strSQL + " and rownum<=500 ) x group by x.A1,x.A2,x.A9,x.A10,x.A18 order by getdate" ;
	alert("22222222222222222222");
	var mySql116=new SqlClass();
			mySql116.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql116.setSqlId("LRSearchInputSql116");//指定使用的Sql的id
			mySql116.addSubPara(strSQL);//指定传入的参数
	    	strSQL=mySql116.getString();
	    	*/
	turnPage.queryModal(strSQL,EdorGrid);
}

function claimQuery(){
	var strSQL;
	/**
	strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),x.A14,max(x.A15),max(x.A16), max(x.A17),max(x.A18),max(x.A19) getdate"
	+ " from ("
	+ " select decode( a.grpcontno,'00000000000000000000',a.contno,a.grpcontno) A1, a.InsuredNo A2,a.InsuredName A3,decode( a.InsuredSex,0 , '男' , '女' ) A4,a.insuredage A5, a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.returnpay A13,a.eventno A14,a.standbydouble2 A15, a.standbydouble3 A16,a.peakline A17,a.payyears A18,a.getdate A19 from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '04' and a.GetDate between to_date('"+fm.StartDate.value+"') and to_date('"+fm.EndDate.value+"')"
	+ " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"+fm.RIcomCode.value+"')"
	//+ " and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+fm.AccumulateDefNO.value+"') "
	;	
	*/
	var mySql117=new SqlClass();
		mySql117.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql117.setSqlId("LRSearchInputSql117");//指定使用的Sql的id
		mySql117.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql117.addSubPara(fm.EndDate.value);//指定传入的参数
		mySql117.addSubPara(fm.RIcomCode.value);//指定传入的参数
	    strSQL=mySql117.getString();
	if(fm.TempType.value=="1"){
		//strSQL = strSQL + " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
		var mySql118=new SqlClass();
			mySql118.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql118.setSqlId("LRSearchInputSql118");//指定使用的Sql的id
			mySql118.addSubPara(fm.StartDate.value);//指定传入的参数
			mySql118.addSubPara(fm.EndDate.value);//指定传入的参数
			mySql118.addSubPara(fm.RIcomCode.value);//指定传入的参数
			mySql118.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			mySql118.addSubPara(fm.GrpContNo.value);//指定传入的参数
			mySql118.addSubPara(fm.InsuredNo.value);//指定传入的参数
			mySql118.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql118.getString();
	}else if(fm.TempType.value=="2"){
		//strSQL = strSQL + " and b.StandbyString1='03' ";
		var mySql119=new SqlClass();
			mySql119.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql119.setSqlId("LRSearchInputSql119");//指定使用的Sql的id
			mySql119.addSubPara(fm.StartDate.value);//指定传入的参数
			mySql119.addSubPara(fm.EndDate.value);//指定传入的参数
			mySql119.addSubPara(fm.RIcomCode.value);//指定传入的参数
			mySql119.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			mySql119.addSubPara(fm.GrpContNo.value);//指定传入的参数
			mySql119.addSubPara(fm.InsuredNo.value);//指定传入的参数
			mySql119.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql119.getString();
	}
	/**
	if(fm.AccumulateDefNO.value!=null&&fm.AccumulateDefNO.value!=""){
		//strSQL = strSQL + " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+fm.AccumulateDefNO.value+"') " ;	
		var mySql120=new SqlClass();
			mySql120.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql120.setSqlId("LRSearchInputSql120");//指定使用的Sql的id
			mySql120.addSubPara(strSQL);//指定传入的参数
			mySql120.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
	    	strSQL=mySql120.getString();
	}
	if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!=null){
		//strSQL = strSQL + " and a.grpcontno='"+fm.GrpContNo.value+"'";
		var mySql121=new SqlClass();
			mySql121.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql121.setSqlId("LRSearchInputSql121");//指定使用的Sql的id
			mySql121.addSubPara(strSQL);//指定传入的参数
			mySql121.addSubPara(fm.GrpContNo.value);//指定传入的参数
	    	strSQL=mySql121.getString();
	}
	if(fm.InsuredNo.value!=""&&fm.InsuredNo.value!=null){
		//strSQL = strSQL + " and a.InsuredNo='"+fm.InsuredNo.value+"'";
		var mySql122=new SqlClass();
			mySql122.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql122.setSqlId("LRSearchInputSql122");//指定使用的Sql的id
			mySql122.addSubPara(strSQL);//指定传入的参数
			mySql122.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    	strSQL=mySql122.getString();
	}
	if(fm.InsuredName.value!=""&&fm.InsuredName.value!=null){
		//strSQL = strSQL +" and a.InsuredName like '%%"+fm.InsuredName.value+"%%'";
		var mySql123=new SqlClass();
			mySql123.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql123.setSqlId("LRSearchInputSql123");//指定使用的Sql的id
			mySql123.addSubPara(strSQL);//指定传入的参数
			mySql123.addSubPara(fm.InsuredName.value);//指定传入的参数
	    	strSQL=mySql123.getString();
	}
	//strSQL = strSQL + " and rownum<=500 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A14 order by getdate" ;
	alert("yyyyyyyyyyyyyyyyyyy");
	var mySql124=new SqlClass();
			mySql124.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
			mySql124.setSqlId("LRSearchInputSql124");//指定使用的Sql的id
			mySql124.addSubPara(strSQL);//指定传入的参数
	    	strSQL=mySql124.getString();
	    	*/
	turnPage.queryModal(strSQL,ClaimGrid);
}

function verifyInput1(){
	//var tSQL = "select count(*) from ( select 'X' from ripolrecord where GetDate between to_date('"+fm.StartDate.value+"') and to_date('"+fm.EndDate.value+"') union all select 'X' from ripolrecordbake where GetDate between to_date('"+fm.StartDate.value+"') and to_date('"+fm.EndDate.value+"') )" ;
	var mySql125=new SqlClass();
		mySql125.setResourceName("reinsure.LRSearchInputSql"); //指定使用的properties文件名
		mySql125.setSqlId("LRSearchInputSql125");//指定使用的Sql的id
		mySql125.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql125.addSubPara(fm.EndDate.value);//指定传入的参数
		mySql125.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql125.addSubPara(fm.EndDate.value);//指定传入的参数
	var tSQL=mySql125.getString();
	var result = easyExecSql(tSQL);
	if(result==0){
		myAlert(""+"没有查询结果。可能还没有对该区间提数计算！"+"");
		return false;
	}
	return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,Result){
	
}

function resetPage(){
	fm.StartDate.value="";
	fm.EndDate.value="";
	fm.EventType.value="";
	fm.InsuredNo.value="";
	fm.InsuredName.value="";
	fm.RIcomCode.value="";
	fm.RIcomName.value="";
	fm.AccumulateDefNO.value="";
	fm.AccumulateDefNOName.value="";
	fm.TempType.value="";
	fm.TempTypeName.value="";
	
	BusynessGrid.clearData();
	EdorGrid.clearData();
	ClaimGrid.clearData();
}

function afterCodeSelect( codeName, Field )
{
	if(codeName=="state")
	{
		BusynessGrid.clearData();
		EdorGrid.clearData();
		ClaimGrid.clearData();
		if(Field.value=="01")
		{
			divBusynessGrid.style.display='';
			divEdorGrid.style.display='none';
			divClaimGrid.style.display='none';
		}else if(Field.value=="03"){
			divBusynessGrid.style.display='none';
			divEdorGrid.style.display='';
			divClaimGrid.style.display='none';
		}else if(Field.value=="04"){
			divBusynessGrid.style.display='none';
			divEdorGrid.style.display='none';
			divClaimGrid.style.display='';
		}
	}
}
