/***************************************************************
 * <p>ProName：FinAccItemTraceInput.js</p>
 * <p>Title：科目明细查询</p>
 * <p>Description：分支科目明细查询与导出</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态

var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
	tSQLInfo.setSqlId("FinAccItemTraceSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.OtherNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(fm.TransStartDate.value);
	tSQLInfo.addSubPara(fm.TransEndDate.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(mManageCom);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinAccItemTraceGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}


/**
 * 模糊查询分支科目
 */
function fuzzyQueryAccItemCode(Field, FieldName) {
	
	var objCodeName = FieldName.value;
	if (objCodeName=="") {
		return false;
	}
	if (window.event.keyCode=="13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
		
			alert("请输入分支科目!");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
			tSQLInfo.setSqlId("FinAccItemTraceSql3");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (arr==null) {
				alert("不存在该分支科目！");
				return false;
			} else {
				if (arr.length == 1) {
					Field.value = arr[0][0];
					FieldName.value = arr[0][1];
				}else {
					var queryCondition = "1 and accitemname like #%"+objCodeName+"%#";
					showCodeList('accitem', [Field, FieldName], [0,1], null,queryCondition, '1', 1, '300');
				}
			}
		}
	}
}

/**
 * 模糊查询会计科目
 */
function fuzzyQueryFinCode(Field, FieldName) {
	
	var objCodeName = FieldName.value;
	if (objCodeName=="") {
		return false;
	}
	
	if (window.event.keyCode=="13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
			alert("请输入会计科目名称！");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
			tSQLInfo.setSqlId("FinAccItemTraceSql2");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (arr==null) {
				alert("不存在该会计科目！");
				return false;
			} else {
				if (arr.length == 1) {
					Field.value = arr[0][0];
					FieldName.value = arr[0][1];
				}else {
					var queryCondition = "1 and finname like #%"+objCodeName+"%#";
					showCodeList('finaccount', [Field, FieldName], [0,1], null, queryCondition, '1', 1, '300');
				}
			}
		}
	}
}

/**
 * 导出保单交易信息
 */
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "业务日期^|批次号^|业务号^|投保单号^|保单号^|管理机构代码^|oracle机构代码^|机构名称^|成本中心^|借贷标志^|科目代码^|科目名称^|金额^|明细段^|渠道^|产品码^|oracle产品码^|产品名称^|参考段^|备用段^|产品分类1^|产品分类2^|产品分类3^|保障期限^|代理机构名称";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
	tSQLInfo.setSqlId("FinAccItemTraceSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.OtherNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(fm.TransStartDate.value);
	tSQLInfo.addSubPara(fm.TransEndDate.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
