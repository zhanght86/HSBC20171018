//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug = "0";
var DealWithNam;
var turnPage = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var turnPage3 = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var eventType;

function queryClick() {
	fm.OperateType.value = "QUERY";

	BusynessGrid.clearData();
	EdorGrid.clearData();
	ClaimGrid.clearData();

	if (!verifyInput()) {
		return false;
	}
	if (!verifyInput1()) {
		return false;
	}
	eventType = fm.EventType.value;
	if (eventType == '01') {
		polQuery();
	} else if (eventType == '03') {
		edorQuery();
	} else if (eventType == '04') {
		claimQuery();
	} else if (eventType == '05') {
		feeQuery();
	}
}

function exportExcel() {
	fm.OperateType.value = "DOWNLOAD";
	if (verifyInput()) {
		fm.target = "fraSubmit";
		// fm.all("downloadType").value="xls";
		fm.action = "./RISearchSave.jsp";
		fm.submit(); // 提交
	}
}

function riAll() {
	fm.OperateType.value = "RIALL";
		fm.target = "fraSubmit";
		// fm.all("downloadType").value="xls";
		fm.action = "./RISearchSave.jsp";
		fm.submit(); // 提交
}

function polQuery() {
	var strSQL;
	var strSQL;
	var d = new Date();
	var years = d.getYear();
	var month = d.getMonth()+1;
	var days = d.getDate();
	var ndate = years+"-"+month+"-"+days;
	//strSQL = "select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12),max(x.A13),sum(x.A14),sum(x.A14)/max(x.A12),sum(x.A15),sum(x.A16),sum(x.A23),sum(x.A24),x.A17,max(x.A18),max(x.A19),max(x.A20),max(x.A21),max(x.A22) getdate from ("
		//+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5,a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,a.accamnt A13,b.CessionAmount A14,b.PremChang A15,b.Commchang A16,a.eventno A17,a.standbydouble2 A18,a.standbydouble3 A19,a.peakline A20, a.payyears A21, a.getdate A22,nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A23,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0)) A24 "
		//+ " from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType in('01','02')";
	strSQL = "select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12),max(x.A13),sum(x.A14),decode(max(x.A12),0,0,sum(x.A14)/max(x.A12)),sum(x.A15),sum(x.A16),sum(x.A23),sum(x.A24),x.A17,max(x.A18),max(x.A19),max(x.A20),max(x.A21),max(x.A22) getdate from ("
			+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5,a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "'))) A11,a.riskamnt A12,a.accamnt A13,b.CessionAmount A14,b.PremChang A15,b.Commchang A16,a.eventno A17,a.standbydouble2 A18,a.standbydouble3 A19,a.peakline A20, a.payyears A21, a.getdate A22,nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A23,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0)) A24 "
			+ " from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType in('01','02')";
	if (fm.StartDate.value != null && fm.StartDate.value != "") {
		strSQL += " and a.getdate>='" + fm.StartDate.value + "'";
	}
	if (fm.EndDate.value != null && fm.EndDate.value != "") {
		strSQL += " and getdate<='" + fm.EndDate.value + "'";
	}
	if (fm.RIcomCode.value != null && fm.RIcomCode.value != "") {
		strSQL += " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
				+ fm.RIcomCode.value + "') ";
	}
	if (fm.TempType.value == "1") {
		strSQL = strSQL
				+ " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
	} else if (fm.TempType.value == "2") {
		strSQL = strSQL + " and b.StandbyString1='03' ";
	}
	if (fm.ContType.value == "1") {
		if (fm.ContNo.value != null && fm.ContNo.value != "") {
			strSQL = strSQL + " and a.contno='" + fm.ContNo.value + "'";
		}
	} else if (fm.ContType.value == "2") {
		if (fm.GrpContNo.value != null && fm.GrpContNo.value != "") {
			strSQL = strSQL + " and a.grpcontno='" + fm.GrpContNo.value + "'";
		}
	} else {
		myAlert(""+"非法团个标志"+"");
		return false;
	}
	if (fm.AccumulateDefNO.value != null && fm.AccumulateDefNO.value != "") {
		strSQL = strSQL
				+ " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
				+ fm.AccumulateDefNO.value + "') ";
	}
	if (fm.InsuredNo.value != null && fm.InsuredNo.value != "") {
		strSQL = strSQL + " and a.InsuredNo='" + fm.InsuredNo.value + "'";
	}
	if (fm.InsuredName.value != null && fm.InsuredName.value != "") {
		strSQL = strSQL + " and a.InsuredName like '%%" + fm.InsuredName.value
				+ "%%'";
	}
	if (fm.RIContNo.value != "" && fm.RIContNo.value != null) {
		strSQL = strSQL + " and a.RIContNo='" + fm.RIContNo.value + "'";
	}
	strSQL = strSQL
			+ " and rownum<=200 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate";
	turnPage.queryModal(strSQL, BusynessGrid);
}

function edorQuery() {
	var strSQL;
	var d = new Date();
	var years = d.getYear();
	var month = d.getMonth()+1;
	var days = d.getDate();
	var ndate = years+"-"+month+"-"+days;
	//strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),0,sum(x.A16),sum(x.A17),sum(x.A24),sum(x.A25),x.A18,max(x.A19),max(x.A20), max(x.A21),max(x.A22),max(x.A23) getdate "
		//+ " from ("
		//+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5,a.standbydate1 A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16, b.Commchang A17,a.eventno A18,a.standbydouble2 A19,a.standbydouble3 A20,a.peakline A21,a.payyears A22,a.getdate A23, nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A24,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) ) A25"
		//+ " from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo and a.EventType = '03' and (a.Feefinatype is null or a.Feefinatype ='BF')";
	strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),0,sum(x.A16),sum(x.A17),sum(x.A24),sum(x.A25),x.A18,max(x.A19),max(x.A20), max(x.A21),max(x.A22),max(x.A23) getdate "
			+ " from ("
			+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5,a.standbydate1 A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "'))) A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16, b.Commchang A17,a.eventno A18,a.standbydouble2 A19,a.standbydouble3 A20,a.peakline A21,a.payyears A22,a.getdate A23, nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A24,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) ) A25"
			+ " from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo and a.EventType = '03' and (a.Feefinatype is null or a.Feefinatype ='BF')";
	if (fm.StartDate.value != null && fm.StartDate.value != "") {
		strSQL += " and a.getdate>='" + fm.StartDate.value + "'";
	}
	if (fm.EndDate.value != null && fm.EndDate.value != "") {
		strSQL += " and getdate<='" + fm.EndDate.value + "'";
	}
	if (fm.RIcomCode.value != null && fm.RIcomCode.value != "") {
		strSQL += " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
				+ fm.RIcomCode.value + "') ";
	}
	if (fm.TempType.value == "1") {
		strSQL = strSQL
				+ " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
	} else if (fm.TempType.value == "2") {
		strSQL = strSQL + " and b.StandbyString1='03' ";
	}
	if (fm.AccumulateDefNO.value != null && fm.AccumulateDefNO.value != "") {
		strSQL = strSQL
				+ " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
				+ fm.AccumulateDefNO.value + "') ";
	}
	if (fm.GrpContNo.value != "" && fm.GrpContNo.value != null) {
		strSQL = strSQL + " and a.grpcontno='" + fm.GrpContNo.value + "'";
	}
	if (fm.InsuredNo.value != "" && fm.InsuredNo.value != null) {
		strSQL = strSQL + " and a.InsuredNo='" + fm.InsuredNo.value + "'";
	}
	if (fm.InsuredName.value != "" && fm.InsuredName.value != null) {
		strSQL = strSQL + " and a.InsuredName like '%%" + fm.InsuredName.value
				+ "%%'";
	}
	strSQL = strSQL
			+ " and rownum<=200 ) x group by x.A1,x.A2,x.A9,x.A10,x.A18 order by getdate";

	turnPage.queryModal(strSQL, EdorGrid);
}

function claimQuery() {
	var strSQL;
	var d = new Date();
	var years = d.getYear();
	var month = d.getMonth()+1;
	var days = d.getDate();
	var ndate = years+"-"+month+"-"+days;
	//strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12),0, max(x.A13),x.A14,max(x.A15),max(x.A16), max(x.A17),max(x.A18),max(x.A19) getdate"
		//+ " from ("
		//+ " select decode( a.grpcontno,'00000000000000000000',a.contno,a.grpcontno) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5, a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.returnpay A13,a.eventno A14,a.standbydouble2 A15, a.standbydouble3 A16,a.peakline A17,a.payyears A18,a.getdate A19 from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo and a.EventType = '04'";
	strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12),0, max(x.A13),x.A14,max(x.A15),max(x.A16), max(x.A17),max(x.A18),max(x.A19) getdate"
			+ " from ("
			+ " select decode( a.grpcontno,'00000000000000000000',a.contno,a.grpcontno) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5, a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "'))) A11,a.riskamnt A12,b.returnpay A13,a.eventno A14,a.standbydouble2 A15, a.standbydouble3 A16,a.peakline A17,a.payyears A18,a.getdate A19 from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo and a.EventType = '04'";
	// + " and exists (select * from RIAccumulateDef where AccumulateDefNO =
	// a.AccumulateDefNO and AccumulateDefNO ='"+fm.AccumulateDefNO.value+"') ";

	if (fm.StartDate.value != null && fm.StartDate.value != "") {
		strSQL += " and a.getdate>='" + fm.StartDate.value + "'";
	}
	if (fm.EndDate.value != null && fm.EndDate.value != "") {
		strSQL += " and getdate<='" + fm.EndDate.value + "'";
	}
	if (fm.RIcomCode.value != null && fm.RIcomCode.value != "") {
		strSQL += " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
				+ fm.RIcomCode.value + "') ";
	}

	if (fm.TempType.value == "1") {
		strSQL = strSQL
				+ " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
	} else if (fm.TempType.value == "2") {
		strSQL = strSQL + " and b.StandbyString1='03' ";
	}
	if (fm.AccumulateDefNO.value != null && fm.AccumulateDefNO.value != "") {
		strSQL = strSQL
				+ " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
				+ fm.AccumulateDefNO.value + "') ";
	}
	if (fm.GrpContNo.value != "" && fm.GrpContNo.value != null) {
		strSQL = strSQL + " and a.grpcontno='" + fm.GrpContNo.value + "'";
	}
	if (fm.InsuredNo.value != "" && fm.InsuredNo.value != null) {
		strSQL = strSQL + " and a.InsuredNo='" + fm.InsuredNo.value + "'";
	}
	if (fm.InsuredName.value != "" && fm.InsuredName.value != null) {
		strSQL = strSQL + " and a.InsuredName like '%%" + fm.InsuredName.value
				+ "%%'";
	}
	if (fm.RIContNo.value != "" && fm.RIContNo.value != null) {
		strSQL = strSQL + " and a.RIContNo='" + fm.RIContNo.value + "'";
	}

	strSQL = strSQL
			+ " and rownum<=200 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A14 order by getdate";
	turnPage.queryModal(strSQL, ClaimGrid);
}

function feeQuery() {
	var strSQL;
	var d = new Date();
	var years = d.getYear();
	var month = d.getMonth()+1;
	var days = d.getDate();
	var ndate = years+"-"+month+"-"+days;
	//strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),(select codename from ldcode where codetype = 'feefinatype' and code = x.A17 and rownum =1 ),max(x.A17),max(x.A19),max(x.A20) getdate "
		//+ " from ("
		//+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5,a.standbydate1 A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16,a.FeeFinaType A17,a.eventno A19,a.getdate A20 "
		//+ "　from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo and a.Feefinatype is not null and a.Feefinatype !='BF'";
	strSQL = " select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),(select codename from ldcode where codetype = 'feefinatype' and code = x.A17 and rownum =1 ),max(x.A17),max(x.A19),max(x.A20) getdate "
			+ " from ("
			+ " select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5,a.standbydate1 A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"+ndate + "'))) A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16,a.FeeFinaType A17,a.eventno A19,a.getdate A20 "
			+ " from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo and a.Feefinatype is not null and a.Feefinatype !='BF'";
	if (fm.StartDate.value != null && fm.StartDate.value != "") {
		strSQL += " and a.getdate>='" + fm.StartDate.value + "'";
	}
	if (fm.EndDate.value != null && fm.EndDate.value != "") {
		strSQL += " and getdate<='" + fm.EndDate.value + "'";
	}
	if (fm.RIcomCode.value != null && fm.RIcomCode.value != "") {
		strSQL += " and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
				+ fm.RIcomCode.value + "') ";
	}

	if (fm.TempType.value == "1") {
		strSQL = strSQL
				+ " and (b.StandbyString1 <> '03' or b.StandbyString1 is null) ";
	} else if (fm.TempType.value == "2") {
		strSQL = strSQL + " and b.StandbyString1='03' ";
	}
	if (fm.AccumulateDefNO.value != null && fm.AccumulateDefNO.value != "") {
		strSQL = strSQL
				+ " and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
				+ fm.AccumulateDefNO.value + "') ";
	}
	if (fm.GrpContNo.value != "" && fm.GrpContNo.value != null) {
		strSQL = strSQL + " and a.grpcontno='" + fm.GrpContNo.value + "'";
	}
	if (fm.InsuredNo.value != "" && fm.InsuredNo.value != null) {
		strSQL = strSQL + " and a.InsuredNo='" + fm.InsuredNo.value + "'";
	}
	if (fm.InsuredName.value != "" && fm.InsuredName.value != null) {
		strSQL = strSQL + " and a.InsuredName like '%%" + fm.InsuredName.value
				+ "%%'";
	}
	strSQL = strSQL
			+ " and rownum<=200 ) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate";

	turnPage3.queryModal(strSQL, FeeGrid);
}

function verifyInput1() {
	var tSQL = "select count(*) from ( select a.getdate from ripolrecord a union all select b.getdate from ripolrecordbake b ) where 1=1";
	if (fm.StartDate.value != null && fm.StartDate.value != "") {
		tSQL += " and getdate>='" + fm.StartDate.value + "'";
	}
	if (fm.EndDate.value != null && fm.EndDate.value != "") {
		tSQL += " and getdate<='" + fm.EndDate.value + "'";
	}
	var result = easyExecSql(tSQL);
	if (result == 0) {
		myAlert(""+"没有查询结果。可能还没有对该区间提数计算！"+"");
		return false;
	}
	return true;
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, Result) {

}

function resetPage() {
	fm.StartDate.value = "";
	fm.EndDate.value = "";
	fm.EventType.value = "";
	fm.InsuredNo.value = "";
	fm.InsuredName.value = "";
	fm.RIcomCode.value = "";
	fm.RIcomName.value = "";
	fm.AccumulateDefNO.value = "";
	fm.AccumulateDefNOName.value = "";
	fm.TempType.value = "";
	fm.TempTypeName.value = "";

	BusynessGrid.clearData();
	EdorGrid.clearData();
	ClaimGrid.clearData();
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "risearchtype") {
		BusynessGrid.clearData();
		EdorGrid.clearData();
		ClaimGrid.clearData();
		if (Field.value == "01") {
			divBusynessGrid.style.display = '';
			divEdorGrid.style.display = 'none';
			divClaimGrid.style.display = 'none';
		} else if (Field.value == "03") {
			divBusynessGrid.style.display = 'none';
			divEdorGrid.style.display = '';
			divClaimGrid.style.display = 'none';
		} else if (Field.value == "04") {
			divBusynessGrid.style.display = 'none';
			divEdorGrid.style.display = 'none';
			divClaimGrid.style.display = '';
		}
	}
}
