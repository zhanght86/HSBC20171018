<script type="text/javascript">
	var tResourceName = 'ibrms.RuleMakeNewSql';
	function initAllRuleMap(){
		lockPage("数据加载中...........");
  jQuery.post(
  	"./InitRuleMap.jsp",
  	//params,
  	function(data) {
			RuleMapArray = data;
			
			//alert(getMapValueByKey('BOMAppnt','en'));
			var tCalCon = getMapValueByKey('LINK_CAL',tLanguage);
			if(tCalCon!='')
			{
				document.getElementById('calConditions').innerHTML=tCalCon;
			}
			
			var tCon = getMapValueByKey('LINK_CON',tLanguage);
			if(tCon!='')
			{
				document.getElementById('conditions').innerHTML=tCon;
			}
			var ibrmsdeftype = getMapValueByKey('ibrmsdeftype','sysvar');
			if(ibrmsdeftype!='')
			{
				mIBRMSDefType = ibrmsdeftype;
			}
			//getMapValueByKey('BOMAppnt','en')
			initRule();
			initButtons();
			unLockPage();
		},"json" 
	);
}
	

function getMapValueByKey(tKeyID,tLan)
{
	// outjson += "{ \"Language\":\""+tLan+"\",\"MsgType\":\""+tMsgType+"\",\"KeyId\":\""+tKeyId+"\",\"Msg\":\""+tMsg+"\"}";
	var tMsg = "";
	for(i=0;i<RuleMapArray.length;i++)
	{
		if(RuleMapArray[i].KeyId==tKeyID&&RuleMapArray[i].Language==tLan)
		{
			tMsg = RuleMapArray[i].Msg;
			break;
			}
	}
	return tMsg;
}
	
	
	
	function initRule() {
		switch (flag) {
		case 0: {
			initRuleForDetails();
			checkNodesState(flag);
			rowDisable = true;
			break;
		}
		case 1: {
			break;
		}
		case 2: {
			initRuleForDuplication();
			break;
		}
		case 4: {
			initRuleForModifition();
			break;
		}
		}
	}
	function initRuleForDetails() {
		initDisplaymentStyle();
		initLogicalAndDT();

	}
	function initRuleForModifition() {
		initLogicalAndDT();
		initLRTemplatePara();
	}
	function initRuleForDuplication() {
		initLogicalAndDT();
	}
	function initButtons() {
		switch (flag) {
		case 1: {
			initButtonForMake();
			break;
		}
		case 2: {
			initButtonForCopy();
			break;
		}
		case 4: {
			initButtonForModify();
			break;
		}
		}

	}

	function initButtonForMake() {
		var submitData = document.getElementById('submitData');
		submitData.disabled = true;
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.disabled = true;
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.disabled = true;
	}

	function initButtonForCopy() {
		var submitData = document.getElementById('submitData');
		submitData.disabled = true;
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.disabled = true;
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.disabled = true;
	}
	function initButtonForModify() {
		var displayDisicionTable = document
				.getElementById('displayDisicionTable');
		displayDisicionTable.disabled = true;
		var submitData = document.getElementById('submitData');
		submitData.disabled = false;
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.disabled = false;
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.disabled = false;
	}

	function initDisplaymentStyle() {

		var RuleDisplay = document.getElementById('RuleDisplay');
		RuleDisplay.style.height = "auto";
		RuleDisplay.style.overflow = "visible";

		var displayDisicionTable = document
				.getElementById('displayDisicionTable');
		displayDisicionTable.style.display = "none";
		var submitData = document.getElementById('submitData');
		submitData.style.display = "none";
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.style.display = "none";
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.style.display = "none";
	}

	function initLogicalAndDT() {
		var ViewParameter = getVewParameter();
		
		var fm = document.getElementById('fm');
		fm.ViewPara.value=ViewParameter;
		
		convertXMLToRule(ViewParameter);
		//alert("@1");
		getBaseBomItems();
		//alert("@2");
		getJsonData();
		//alert("@3");
	}
	function getVewParameter() {
		//alert(tLanguage);
		var ViewParameter = queryForViewParameter(LRTemplateName, LRTemplate_ID,tLanguage);
		return ViewParameter;
	}

	function initLRTemplatePara() {
		var fm = document.getElementById('fm');

		//var sql = "select SQLStatement,BOMS,SQLParameter,TableName,RuleCh from LRTemplateT where id='"
		//		+ LRTemplate_ID + "'";
		var sqlid1="RuleMakeNewSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(LRTemplate_ID);//指定传入的参数
	
	  var strSql=mySql1.getString();	
		var result = easyQueryVer3(strSql);
		var reArray = decodeEasyQueryResult(result);

		fm.SQLStatement.value = reArray[0][0];
		fm.BOMS.value = reArray[0][1];
		fm.SQLPara.value = reArray[0][2];
	    fm.TableName.value = reArray[0][3];
	    fm.RuleCh.value = reArray[0][4];
	    //alert("RuleCh:"+fm.RuleCh.value);
	}

	function getDataFromDTTable() {
		var reData = new Array();
		if (!(!!LRTemplateName)) {
			alert("<规则来源表>获取有误！");
			return false;
		}
		var sqlid2="RuleMakeNewSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(LRTemplateName);//指定传入的参数
		mySql2.addSubPara(LRTemplate_ID);//指定传入的参数
	  
	  var strSq2=mySql2.getString();	
	  
		
		//var sql = "select TableName from " + LRTemplateName + " where id='"
		//		+ LRTemplate_ID + "'";

		var result = easyQueryVer3(strSq2);
		var reArray = decodeEasyQueryResult(result);
		var tableName = reArray[0][0];
		
		
		var sqlid3="RuleMakeNewSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(tableName);//指定传入的参数
	
	  var strSq3=mySql3.getString();	
	  
		
	
		//sql = "select * from " + tableName;
		result = easyQueryVer3(strSq3);
		reArray = decodeEasyQueryResult(result);
		var rowLen = reArray.length;
		var colLen = reArray[0].length;
		var i = 0, j = 0;

		initTableColumnNameArray(tableName,LRTemplate_ID);

		for (i = 0; i < rowLen; i++) {
			reData[i] = new Array();
			for (j = 0; j < colLen - 1; j++) {
				if (ColumnDataTypeArray[j] != 'Date') {
					reData[i][j] = reArray[i][j];
				} else {
					var da = new Date(reArray[i][j]);
					reData[i][j] = da.format('Y-m-d H:i:s');
				}
			}
		}
		for ( var i = reData.length; i < 10; i++) {
			reData[i] = [];
		}

		return reData;
	}
	function initTableColumnNameArray(tableName,TemplateID) {
		
		//var reArray;
		if(tableName!='RuleData')
		{
		
			var sqlid4="RuleMakeNewSql4";
			var mySql4=new SqlClass();
			mySql4.setResourceName(tResourceName); //指定使用的properties文件名
			mySql4.setSqlId(sqlid4);//指定使用的Sql的id
			mySql4.addSubPara(tableName);//指定传入的参数
	
	  	var strSq4=mySql4.getString();	
	  
		//sql = " select column_name from user_tab_cols where table_name='"
		//		+ tableName + "' order by column_id";
		    result = easyQueryVer3(strSq4);
			reArray =decodeEasyQueryResult(result);
			
			var rowLen = reArray.length - 1;

			TableColumnNameArray.length = 0;
			for (i = 0; i <= rowLen; i++) {
				//alert(reArray[i][0]);
				TableColumnNameArray[i] = reArray[i][0];
			}
		
	 	}
	 	else
	 	{
	 			var sqlid5="RuleMakeNewSql5";
				var mySql5=new SqlClass();
				mySql5.setResourceName(tResourceName); //指定使用的properties文件名
				mySql5.setSqlId(sqlid5);//指定使用的Sql的id
				mySql5.addSubPara(TemplateID);//指定传入的参数
	
	  	var strSq5=mySql5.getString();	
	  	
	 		//sql = " select columnnames from lrruledata where id='"+TemplateID+"'";
			result = easyQueryVer3(strSq5);
			var tempColumnnames = decodeEasyQueryResult(result);
			//alert(result);
			//alert("result[0][0]:"+tempColumnnames[0][0]);
			try{
			var reArray = tempColumnnames[0][0].split(";");
			TableColumnNameArray.length = 0;
				for (i = 0; i < reArray.length; i++) {
					TableColumnNameArray[i] = reArray[i]
				}
			}catch(ex){}
		
	 	}
		
	}

	function getJsonData() {
		if (!(!!LRTemplateName)) {
			alert("<规则来源表>获取有误！");
			return false;
		}
		var sqlid6="RuleMakeNewSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(tResourceName); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(LRTemplateName);//指定传入的参数
		mySql6.addSubPara(LRTemplate_ID);//指定传入的参数
	  
	  var strSq6=mySql6.getString();	
	  
		//var sql = "select TableName from " + LRTemplateName + " where id='"
		//		+ LRTemplate_ID + "'";

		var result = easyQueryVer3(strSq6);
		var reArray = decodeEasyQueryResult(result);
		var tableName = reArray[0][0];

		post = "DTTableName=" + tableName+"&LRTemplateID="+LRTemplate_ID;
		if(tableName!='ldsysvar'&&tableName.indexOf('DT')==-1&&tableName.indexOf('RuleData')==-1)
		{
				mRuleTableFlag = true;
				mRuleTableName = tableName;
				document.getElementById('cTableName').value = mRuleTableName;
				//disableInputNodes();
	   		setButtonsState('Disable');
				
		}
    //if(tableName!='ldsysvar')
    if(tableName!='ldsysvar'&&(tableName.indexOf('DT')!=-1||tableName.indexOf('RuleData')!=-1))
    {
    	//初始化 TableColumnNameArray 这个数组. 对于新方案.待处理
		initTableColumnNameArray(tableName,LRTemplate_ID);

		post = encodeURI(post);
		post = encodeURI(post); // 两次，很关键
		Ext.Ajax.request( {
			url :'queryForInformation.jsp',
			method :'post',
			timeout	: 100000,
			success : function(response, options) {
				displayJsonData(Ext.decode(response.responseText));
			},
			failure : function(response, options) {
				alert("数据获取出错，请找管理员！");
			},
			headers : {
				"Content-Type" :"application/x-www-form-urlencoded",
				"cache-control" :"no-cache"
			},
			params :post
		});
	}
	}
	function prepareJsonData(jsonData) {
	//	alert("ColumnDataTypeArray.length:"+ColumnDataTypeArray.length);
		for ( var i = 0; i < ColumnDataTypeArray.length; i++) {
			if (ColumnDataTypeArray[i] == "Date") {
				for ( var j = 0; j < jsonData.rows.length; j++) {
					jsonData.rows[j][TableColumnNameArray[i]] = Date.parseDate(
							jsonData.rows[j][TableColumnNameArray[i]],
							'Y-m-d H:i:s');
				}
			}
			if (BaseBOMItemSourceArray[i] != "") {
				var baseData;
				var source = BaseBOMItemSourceArray[i];
				//alert("source:"+source);
				 if (typeof(source)!="undefined" && typeof(source)=="undefined" )
				 {
						for ( var j = 0; j < jsonData.rows.length; j++) {

						baseData = jsonData.rows[j][TableColumnNameArray[i]];
					
						var result = easyQueryVer3(source);
						var reArray = decodeEasyQueryResult(result);

						codeArray = baseData.split(";");
						baseData = "";
						for ( var k = 0; k < codeArray.length; k++) {
							if (codeArray[k] == "") {
								continue;
							}
							for ( var m = 0; m < reArray.length; m++) {
								if (codeArray[k] == reArray[m][0]) {

									if (k > 1 && k < codeArray.length - 1) {
										baseData += ";";
									}
									baseData += reArray[m][0] + "-" + reArray[m][1];
									break;
								}
							}
						}
						jsonData.rows[j][TableColumnNameArray[i]] = baseData;
					}	
				}
			}
		}
		return jsonData;
	}

	function displayJsonData(jsonData) {
		//alert("$1");
		jsonData = prepareJsonData(jsonData);
		//alert("$2");
		displayDicTable('init', jsonData);
		//alert("$3");
	}
</script>
