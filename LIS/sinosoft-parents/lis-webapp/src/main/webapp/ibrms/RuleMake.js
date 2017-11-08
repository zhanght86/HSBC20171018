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
			

			document.getElementById("fm").submit();
		}
	}
}

function modifyLogic() {
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