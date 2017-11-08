function submitData() {
	if (flag == 1 || flag == 2) {
		var fm = document.getElementById('fm');
		var dataArray = getData();
		if (!dataArray)
			return;
		
		var Json=dataArrayToJson(dataArray);
		
		var realJson={"rows":Json};
				
		fm.DTData.value = Ext.util.JSON.encode(realJson);

		fm.SQLStatement.value =SQLStatement;
		fm.CreateTable.value =CreateTable;
		fm.ViewPara.value =ViewPara;
		fm.BOMS.value = BOMSArray;
		fm.SQLPara.value = SQLParaArray;
		fm.RuleCh.value = RuleDesInCh;
		fm.ColumnDataType.value=ColumnDataTypeArray;
		fm.TableColumnName.value=TableColumnNameArray;
		fm.Types.value = ColumnDataTypeArray;
		
		//tongmeng 2010-12-15 modify
		fm.CalSQLStatement.value =CalSQLStatement;
		//alert(CalSQLStatement);
		//return ;
		fm.CalRuleCh.value = CalRuleDesInCh;
		fm.RuleTableName.value = mRuleTableName;
		//alert('fm.DTData.value:'+fm.DTData.value);
		//return;
		document.getElementById("fm").submit();
	}
	if (flag == 4) {
		var fm = document.getElementById('fm');
		var op = fm.Operation.value;
		if (op != "Logic_Modification") {
			var dataArray = getData();
			if (!dataArray)
				return;
			
			var Json=dataArrayToJson(dataArray);
			
			var realJson={"rows":Json};
					
			fm.DTData.value = Ext.util.JSON.encode(realJson);
			
			fm.ColumnDataType.value = ColumnDataTypeArray;
			fm.TableColumnName.value=TableColumnNameArray;
			fm.RuleTableName.value = mRuleTableName;
			
			
			document.getElementById("fm").submit();
		} else {
			var fm = document.getElementById('fm');
			var dataArray = getData();
			if (!dataArray)
				return;
			
			var Json=dataArrayToJson(dataArray);
			
			var realJson={"rows":Json};
					
			fm.DTData.value = Ext.util.JSON.encode(realJson);

			fm.SQLStatement.value =SQLStatement;
			fm.CreateTable.value =CreateTable;
			fm.ViewPara.value =ViewPara;
			fm.BOMS.value = BOMSArray;
			fm.SQLPara.value = SQLParaArray;
			fm.RuleCh.value = RuleDesInCh;
			fm.ColumnDataType.value=ColumnDataTypeArray;
			fm.TableColumnName.value=TableColumnNameArray;
				//tongmeng 2010-12-15 modify
			fm.CalSQLStatement.value =CalSQLStatement;
			//alert(CalSQLStatement);
		//	return ;
		//	alert('fm.DTData.value:'+fm.DTData.value);
			//	return;
			fm.CalRuleCh.value = CalRuleDesInCh;
			fm.RuleTableName.value = mRuleTableName;
			document.getElementById("fm").submit();
		}
	}
}

function modifyLogic() {
	
	//tongmeng 2010-12-29 add
	//fm.all('CreateDTFlag').value = '0';
	
	showButtons();
	enableSpanNodes();
	disableInputNodes();
	
	var fm = document.getElementById('fm');
	fm.Operation.value = 'Logic_Modification';
	
	var displayDisicionTable=document.getElementById('displayDisicionTable');
	displayDisicionTable.disabled=false;
	var submitData=document.getElementById('submitData');
	submitData.disabled=true;
	var logicToTable=document.getElementById('logicToTable');
	logicToTable.disabled=true;
	
	var modifyLogic=document.getElementById('modifyLogic');
	modifyLogic.disabled=true;

}

function setRuleTable()
{
	var tTableName = document.getElementById('cTableName').value;
	//alert(tTableName);
	if(tTableName!=null&&tTableName!='')
	{
		mRuleTableFlag = true;
		mRuleTableName = tTableName;
		queryCache.length = 0;
	}
	else
	{
		mRuleTableFlag = false;
		mRuleTableName = '';
		queryCache.length = 0;
	}
}