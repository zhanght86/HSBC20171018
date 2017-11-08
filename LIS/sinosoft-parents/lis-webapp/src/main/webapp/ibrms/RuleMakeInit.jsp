<script type="text/javascript">
	var tResourceName = 'ibrms.RuleMakeSql';
	function initAllRuleMap(){
		lockPage("���ݼ�����...........");
  jQuery.post(
  	"./InitRuleMap.jsp",
  	//params,
  	function(data) {
			RuleMapArray = data;
			
			var tCon = getMapValueByKey('LINK_IF',tLanguage);
			if(tCon!='')
			{
				document.getElementById('conditions').innerHTML=tCon;
			}
			var tThan = getMapValueByKey('LINK_THAN',tLanguage);
			if(tThan!='')
			{
				document.getElementById('divThan').innerHTML=tThan;
			}	
			
			var tResult  = getMapValueByKey('RES_NOPASS',tLanguage);
			if(tResult!='')
			{
				document.getElementById('Result').value=tResult;
			}	
			//Result
			var ibrmsdeftype = getMapValueByKey('ibrmsdeftype','sysvar');
			if(ibrmsdeftype!='')
			{
				mIBRMSDefType = ibrmsdeftype;
			}
			
			//getMapValueByKey('BOMAppnt','en')
			try{
			initRule();
			initButtons();
			unLockPage();
		  }
		  catch(ex)
		  {
		  	alert(ex);
		  }
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
	//if(tKeyID=='ibrmsdeftype')
//	{
	//	alert(tMsg);
	//	}
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
		getBaseBomItems();
		getJsonData();
	}
	function getVewParameter() {
		var ViewParameter = queryForViewParameter(LRTemplateName, LRTemplate_ID,tLanguage);
		return ViewParameter;
	}

	function initLRTemplatePara() {
		var fm = document.getElementById('fm');

		//var sql = "select SQLStatement,BOMS,SQLParameter,TableName,RuleCh from LRTemplateT where id='"
		//		+ LRTemplate_ID + "'";
		var sqlid1="RuleMakeSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(LRTemplate_ID);//ָ������Ĳ���
	
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
			alert("<������Դ��>��ȡ����");
			return false;
		}
		
		var sqlid2="RuleMakeSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(LRTemplateName);//ָ������Ĳ���
		mySql2.addSubPara(LRTemplate_ID);//ָ������Ĳ���
	  
	  var strSq2=mySql2.getString();	
	  
		
		//var sql = "select TableName from " + LRTemplateName + " where id='"
		//		+ LRTemplate_ID + "'";

		var result = easyQueryVer3(strSq2);
		var reArray = decodeEasyQueryResult(result);
		var tableName = reArray[0][0];
		
		
		var sqlid3="RuleMakeSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tableName);//ָ������Ĳ���
	
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
		if(tableName!='RuleData')
		{
		
			var sqlid4="RuleMakeSql4";
			var mySql4=new SqlClass();
			mySql4.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
			mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
			mySql4.addSubPara(tableName);//ָ������Ĳ���
	
	  	var strSq4=mySql4.getString();	
	  
		//sql = " select column_name from user_tab_cols where table_name='"
		//		+ tableName + "' order by column_id";
		result = easyQueryVer3(strSq4);
		reArray = decodeEasyQueryResult(result);
		var rowLen = reArray.length - 1;

		TableColumnNameArray.length = 0;
		for (i = 0; i < rowLen; i++) {
			TableColumnNameArray[i] = reArray[i][0];
		}
	 	}
	 	else
	 	{
	 			var sqlid5="RuleMakeSql5";
				var mySql5=new SqlClass();
				mySql5.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
				mySql5.addSubPara(TemplateID);//ָ������Ĳ���
	
	  	var strSq5=mySql5.getString();	
	  	
	 		//sql = " select columnnames from lrruledata where id='"+TemplateID+"'";
			result = easyQueryVer3(strSq5);
			var tempColumnnames = decodeEasyQueryResult(result);
			//alert(result);
			//alert("result[0][0]:"+tempColumnnames[0][0]);
			var reArray = tempColumnnames[0][0].split(";");
			TableColumnNameArray.length = 0;
				for (i = 0; i < reArray.length; i++) {
					TableColumnNameArray[i] = reArray[i]
				}
		
	 	}
	}

	function getJsonData() {
		if (!(!!LRTemplateName)) {
			alert("<������Դ��>��ȡ����");
			return false;
		}
		
		var sqlid6="RuleMakeSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(LRTemplateName);//ָ������Ĳ���
		mySql6.addSubPara(LRTemplate_ID);//ָ������Ĳ���
	  
	  var strSq6=mySql6.getString();	
	  
		//var sql = "select TableName from " + LRTemplateName + " where id='"
		//		+ LRTemplate_ID + "'";

		var result = easyQueryVer3(strSq6);
		var reArray = decodeEasyQueryResult(result);
		var tableName = reArray[0][0];

		post = "DTTableName=" + tableName+"&LRTemplateID="+LRTemplate_ID;

    if(tableName!='ldsysvar')
    {
    	//��ʼ�� TableColumnNameArray �������. �����·���.������
		initTableColumnNameArray(tableName,LRTemplate_ID);

		post = encodeURI(post);
		post = encodeURI(post); // ���Σ��ܹؼ�
		Ext.Ajax.request( {
			url :'queryForInformation.jsp',
			method :'post',

			success : function(response, options) {
				displayJsonData(Ext.decode(response.responseText));
			},
			failure : function(response, options) {
				alert("���ݻ�ȡ�������ҹ���Ա��");
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
		jsonData = prepareJsonData(jsonData);
		//alert('jsonData:'+jsonData);
		displayDicTable('init', jsonData);
	}
</script>
