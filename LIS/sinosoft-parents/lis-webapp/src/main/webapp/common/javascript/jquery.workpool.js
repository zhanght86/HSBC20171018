/**
 * WorkFlow for jquery - v0.1
 * 
 * 
 * 
 * 
 * 
 */
(function($){
	$.addWorkPools = function(t,c){// add WorkPools append to t
		(typeof(publicTip)=="undefined")?publicTip = "":typeof(publicTip);
		(typeof(privateTip)=="undefined")?privateTip = "":typeof(privateTip);
		(typeof(publicSelect)=="undefined")?publicSelect = function(){}:typeof(publicTipe);
		(typeof(privateSelect)=="undefined")?privateSelect = function(){}:typeof(privateSelect);
		var config = {// default config add two pools (four mullines) append
						// to t
			functionId : "",
			operator : "",
			activityId : "",
			public : {// public pool contains ：two mullines' congfig
				id : "PublicWorkPool",// id of mulline
				show : true,
				queryTitle : "查询",
				query : {// querymulline :this config can set value to
							// mulline config and flexconfig(if necessary)
					show : true,
					queryButton : {
						"0":{
							title : "查  询",
							id : "publicSearch",
							func : "search"
						}
					},
					mulLineCount : 1,
					hiddenPlus : 1,
					hiddenSubtraction : 1,
					showTitle : 0,
					arrayInfo : {// the config of mulline array
						hasVisited : false,// after visited ajax it will be
											// true
						cols :{// all col config will set by this config
							style : 2// writable
						},
						col :{// the column config
							col0 : {
								title : "序号",
								maxLength : 10,
								style : 3,
								colNo : 0
							}
						}
					}
				},
				resultTitle : "公共工作池",
				result : {// the same to query mulline
					mulLineCount : 2,
					hiddenPlus : 1,
					hiddenSubtraction : 1,
					canSel : 1,
					showTitle : 0,
					ShowPageMark : false,
					selBoxEventFuncName : "publicSelect",
					flexConfig:{striped:true},
					emergency:{// emergency column , color and value
						colorMapping : {
						"10":"yellow",
						"50":"#33DEDB",
						"100":"#F06147"},// color of defalut
						colNo : "default"// colNo of emergency
					},
					arrayInfo : {// the config of mulline array
						sqlId : "querymission2",// default sql
						hasVisited : false,// after visited ajax it will be
											// true
						cols :{// all col config will set by this config
							style : 0// readonly
						},
						col :{// the column config
							col0 : {
								title : "序号",
								maxLength : 10,
								style : 0,
								colNo : 0
							},
							col1 : {
								title : "工作流任务号",
								maxLength : 100,
								style : 3,
								colName : "missionid"
							},
							col2 : {
								title : "工作流子任务号",
								maxLength : 100,
								style : 3,
								colName : "submissionid"
							},
							col3 : {
								title : "工作流过程号",
								maxLength : 100,
								style : 3,
								colName : "processid"
							},
							col4 : {
								title : "工作流活动号码",
								maxLength : 100,
								style : 3,
								colName : "activityid"
							},
							col5 : {
								title : "规定作业用时",
								maxLength : 100,
								style : 0,
								rename : "worktime"
							},
							col6 : {
								title : "剩余作业时间",
								maxLength : 100,
								style : 0,
								rename : "remaintime"
							},
							col7 : {
								title : "紧急度指数",
								maxLength : 100,
								style : 0,
								rename : "emergency"
							}
						}
					},
					condition : {
						"0" : true,
						"1" : true,
						"2" : true,
						"3" : "operator",
						"4" : false,
						"5" : false
					},
					otherTitle : {// the column which need specical title
						colNo : 1,// the column no
						getTitle : publicTip// the function for title ,param is
											// the row number and the mulline
					}
				}
			},
			private : {// the same to public
				id : "PrivateWorkPool",
				show : true,
				queryTitle : "查询",
				query : {
					show : true,
					queryButton : {
						"0":{
							title : "查  询",
							id :	"privateSearch",
							func : "search"
						}
					},
					mulLineCount : 1,
					hiddenPlus : 1,
					hiddenSubtraction : 1,
					showTitle : 0,
					arrayInfo : {// the config of mulline array
						hasVisited : false,// after visited ajax it will be
											// true
						cols :{// all col config will set by this config
							style : 2// writable
						},
						col :{// the column config
							col0 : {
								title : "序号",
								maxLength : 10,
								style : 3,
								colNo : 0
							}
						}
					}
				},
				resultTitle : "个人工作池",
				result : {
					mulLineCount : 2,
					hiddenPlus : 1,
					hiddenSubtraction : 1,
					canSel : 1,
					showTitle : 0,
					ShowPageMark : false,
					selBoxEventFuncName : "privateSelect",
					flexConfig:{striped:true},
					emergency:{// emergency column , color and value
						colorMapping : {
						"10":"yellow",
						"50":"#33DEDB",
						"100":"#F06147"},// color of defalut
						target : "input",
						colNo : "default"// "default"//colNo of emergency
					},
					arrayInfo : {// the config of mulline array
						sqlId : "querymission2",// default sql
						hasVisited : false,// after visited ajax it will be
											// true
						cols :{// all col config will set by this config
							style : 0// readonly
						},
						col :{// the column config
							col0 : {
								title : "序号",
								maxLength : 10,
								style : 0,
								colNo : 0
							},
							col1 : {
								title : "工作流任务号",
								maxLength : 100,
								style : 3,
								colName : "missionid"
							},
							col2 : {
								title : "工作流子任务号",
								maxLength : 100,
								style : 3,
								colName : "submissionid"
							},
							col3 : {
								title : "工作流过程号",
								maxLength : 100,
								style : 3,
								colName : "processid"
							},
							col4 : {
								title : "工作流活动号码",
								maxLength : 100,
								style : 3,
								colName : "activityid"
							},
							col5 : {
								title : "规定作业用时",
								maxLength : 100,
								style : 0,
								rename : "worktime"
							},
							col6 : {
								title : "剩余作业时间",
								maxLength : 100,
								style : 0,
								rename : "remaintime"
							},
							col7 : {
								title : "紧急度指数",
								maxLength : 100,
								style : 0,
								rename : "emergency"
							}
						}
					},
					condition : {// condition of result
						"0" : true,
						"1" : true,
						"2" : true,
						"3" : "operator",
						"4" : false,
						"5" : false
					},
					otherTitle :{
						colNo : 1,
						getTitle : privateTip
					}
				}
			},
			midContent :{// the content between two pools
				id : "MidContent",
				show : false,
				html : ""
			}
		};
		var $t = $(t);
		$t.empty();
		$.initConfig(c,config, $t);// get default setting and activityid and
									// formname
		t.config = c;// $t.attr("config",c);
		if(c.public.show){// add public workpool
			$.addWorkPool(t,c.public);
		}
		if(c.midContent.show){// add some content between pools
			$t.append(c.midContent.html);
		}
		if(c.private.show){// add private workpool
			$.addWorkPool(t,c.private);
		}
		$t.append("<div style='visibility:hidden'>123</div>");// it is not a
																// bug,for tip
																// function
	};
	$.addWorkPool = function(t,c){// add a WorkPool append to t
		var appendStr = "<div id=\"" + c.id + "\">";
		var QueryGrid,ResultGrid,ResultTurnPage;
		var newConfig = {};
		if(c.query){
			appendStr += c.query.show?"<div>":"<div style='display:none'>";
			if(c.queryTitle){
				appendStr += $.getTitle(c.id + "QDiv",c.queryTitle);
			}
			appendStr += "<div id=\"" + c.id + "QDiv\"><table class='common'><tr class='common'><td colSpan=1><span id=\"span" + c.id + "QueryGrid\"></span></td></tr></table></div>";
			appendStr += "</div>";
			QueryGrid = new MulLineEnter(c.formName, c.id + "QueryGrid");
			$.extend(newConfig,{queryGrid : QueryGrid});
			eval(c.id+"QueryGrid = QueryGrid");// global variable for mulline
												// event
		}
		if(c.resultTitle){
			appendStr += $.getTitle(c.id + "RDiv",c.resultTitle);
		}
		if(c.result){
			appendStr += "<div id=\"" + c.id + "RDiv\"><table class='common'><tr class='common'><td colSpan=1><span id=\"span" + c.id + "Grid\"></span></td></tr></table></div>";
			ResultGrid = new MulLineEnter(c.formName, c.id + "Grid");
			ResultTurnPage = new turnPageClass();
			$.extend(newConfig,{resultGrid : ResultGrid,resultTurnPage:ResultTurnPage});
			eval(c.id+"Grid = ResultGrid");
		}
		appendStr += "</div>";
		$(t).append(appendStr);
		$.extend(c,newConfig);
		if(c.query){
			$.addMulLine(QueryGrid, t, c, c.query);// set config and
													// loadMulline
			$.addQueryButton(t, c);// add query button
		}
		if(c.result){
			$.addMulLine(ResultGrid, t, c, c.result);// set config and
														// loadMulline
			$.addTurnPage(t, c);// add turn page
		}
	};
	$.addMulLine = function(grid, t, c, config){// add mulline to workpool
		if(grid && config){
			var item;
			var $grid = $(grid),$config = $(config);
			for(item in config){// set mullline config
				var attr = $grid.attr(item);
				if(typeof(attr) != "function" && typeof(attr) != "undefined" ){
					if(item != "flexConfig"){
						$grid.attr(item, $config.attr(item));
					}else{
						$grid.attr(item, $.extend($grid.attr(item),$config.attr(item)));
					}
				}
			}
			grid.loadMulLine($.getArray(t, config));
		}
	};
	$.initConfig = function(arg1, arg2, arg3){// arg1 is user config, arg2 is
												// default config ,arg3 is $t
		$.superExtend(arg1,arg2);
		$.getActivityId(arg1);
		if(arg1.formName==null || arg1.formName ==""){
			arg1.formName = arg3.parents().find("form").attr("name");
		}
	}
	$.superExtend = function(arg1,arg2){// arg2 is default config
		if(typeof(arg1) == "function" || typeof(arg2) == "function")return;
		var $arg1 = $(arg1),$arg2 = $(arg2);
		for(var i in arg2){
			var j;
			try{
				j= $arg1.attr(i);
			}catch(e){
				j=null;
			}
			if(j instanceof Object){
				$.superExtend(j,$arg2.attr(i));
			}else if(j == null){
				var k = $arg2.attr(i);
				if(typeof(k)=="function"){
					eval("arg1."+i+"=k");
				}else{
					$arg1.attr(i,$arg2.attr(i));
				}
			}else{
				
			}
		}
	};
	$.getActivityId = function(config){// get activityId by functionId
		var data;
		var functionId = config.functionId;
		if(functionId!=null && functionId!=""){
			var mysql = new SqlClass();
			mysql.setResourceName("workflow.FlowGridSql");
			mysql.setSqlId("activityid");
			mysql.addSubPara(functionId);
			data = easyExecSql(mysql.getString());
			if(data){
				config.activityId = data.join(",");
			}
		}
	};
	$.getTitle = function(divId, title){// the title of mulline (sinosoft style)
		var $aDiv = $(document.createElement("div"));
		$aDiv.append("<div></div>").find("div")
			.append("<TABLE><TBODY><TR><TD class=common><IMG style=\"CURSOR: hand\" onclick='showPage(this," 
			+ divId + ")'; src=\"../common/images/butExpand.gif\"> </TD><TD class=titleImg>"+title+"</TD></TR></TBODY></TABLE>");
		return $aDiv.html();
	};
	$.getArray = function (t, config){// get each column info
		if(config.arrayInfo.hasVisited){// already get column info
			return $.jsonToArray(config.arrayInfo.col,config.arrayInfo);
		}
		var mysql = new SqlClass();
		mysql.setResourceName("workflow.FlowGridSql");
		mysql.setSqlId("fieldquery2");
		mysql.addSubPara(t.config.activityId.split(",")[0]);
		var a = easyExecSql(mysql.getString());
		var result={} ;
		if(a==null){
			throw "Field Map is null! Activity id is "+t.config.activityId.split(",")[0];
		}
		for(var i=0,size = a.length; i<size;i++){// array to json
			var m = "result"+i;
			var k = {activityid:a[i][0],fieldorder:a[i][1],sourtablename:a[i][2],sourfieldname:a[i][3],sourfieldcname:a[i][4]
			,desttablename:a[i][5],destfieldname:a[i][6],destfieldcname:a[i][7],getvalue:a[i][8],getvaluetype:a[i][9],canshow:a[i][10]
			,title:a[i][4],style:a[i][10],colName:a[i][6],rename:a[i][3]};
			k = eval("({" + m + ":k})");
			$.extend(result,k);
		}
		var p = config.arrayInfo.col;
		var $p = $(p),colNo = 0;
		var colSeq = new Array();
		for(var i in p){
			colSeq[$p.attr(i).colNo] = i;
		}
		for(var iResult in result){// 遍历获得的列信息
			for(colNo = 0;;colNo++){// get sequence
				if(colSeq[colNo] == null){
					colSeq[colNo] = iResult;
					break;
				}else if(colSeq[colNo] == iResult){
					break;
				}
			}
			var j = $(result).attr(iResult);
			$.extend(j,{colNo:colNo});
			if($p.attr(iResult)){
				$.extend(j,$p.attr(iResult));// user config cover default one
			}
		}
		$.extend(p,result);
		colNo = 0;
		for(var iResult in p){// set empty colNo
			var j = $p.attr(iResult);
			if(j.colNo || j.colNo==0 || typeof(j)!= "object")continue;
			for(colNo;;colNo++){
				if(colSeq[colNo] == null){
					colSeq[colNo] = iResult;
					j.colNo = colNo;
					break;
				}
			}
		}
		var iArray = new Array();
		iArray = $.jsonToArray(p, config.arrayInfo);
		config.arrayInfo.col = p;
		config.arrayInfo.hasVisited = true;
		return iArray;
	};
	$.jsonToArray = function(json, arrayInfo){// change json(contains
												// mulline's arrayinfo) to array
		var iArray = new Array();
		var col = 0,j;
		for(var i in json){
			j= $(json).attr(i);
			if(typeof(j)!= "object")continue;
			col = j.colNo;
			iArray[col] = new Array();
			var title = j.title;
			iArray[col][0] = title;
			iArray[col][1] = (j.width == null)? title.length*20+"px" : j.width;
			iArray[col][2] = (j.maxLength == null)? title.length*20 : j.maxLength;
			if(j.style==""){
				if(arrayInfo.cols.style)
					iArray[col][3] = arrayInfo.cols.style;
				else
					iArray[col][3] = 0;
			}else{
				iArray[col][3] = j.style;
			}
			if(j.refercode1)iArray[col][4] = j.refercode1;
			if(j.refercols1)iArray[col][5] = j.refercols1;
			if(j.refervalue1)iArray[col][6] = j.refervalue1;
			if(j.referfunc1)iArray[col][7] = j.referfunc1;
			if(j.referpara1)iArray[col][8] = j.referpara1;
			if(j.verify)iArray[col][9] = j.verify;
			if(j.refercode2)iArray[col][10] = j.refercode2;
			if(j.refercodedata2)iArray[col][11] = j.refercodedata2;
			if(j.refercols2)iArray[col][12] = j.refercols2;
			if(j.refercolvalue2)iArray[col][13] = j.refercolvalue2;
			if(j.defaultvalue)iArray[col][14] = j.defaultvalue;
			if(j.referobjnames)iArray[col][15] = j.referobjnames;
			if(j.referobjvalues)iArray[col][16] = j.referobjvalues;
			if(j.referobjcolvalue)iArray[col][17] = j.referobjcolvalue;
			if(j.dropdownwidth)iArray[col][18] = j.dropdownwidth;
			if(j.dataresource)iArray[col][19] = j.dataresource;
			if(j.temp21)iArray[col][20] = j.temp21;
			if(j.temp22)iArray[col][21] = j.temp22;
			if(j.moneytype)iArray[col][22] = j.moneytype;
			if(j.temp24)iArray[col][23] = j.temp24;
			if(j.blurfunc)iArray[col][24] = j.blurfunc;
			if(j.blurpara)iArray[col][25] = j.blurpara;
		}
		return iArray;
	}
	$.addQueryButton = function(t,c){// add query button
		var $aDiv = $(document.createElement("div"));
		$.each(c.query.queryButton,function(index,value){
			var find = "input:last";
			if("0" == index){
				$aDiv.prepend(" <input type=\"button\" class=\"cssButton\" /> ");
				find = "input:first";
			}else{
				$aDiv.append(" <input type=\"button\" class=\"cssButton\" /> ");
			}
			$aDiv.find(find).attr("value",function(){if(value.title)return value.title;}).attr("id",function(){if(value.id)return value.id;}).bind("click",function(){
					$.queryButton(value.func,c,t.config.activityId,t.config.operator)
			});
		});
		$(t).find("#" + c.id + "QDiv").append($aDiv);	
	};
	$.addTurnPage = function(t, c){// add turn page after mulline
		var aDiv = document.createElement("div");
		$(aDiv).css({"text-align":"center","position":"center"}).append("<INPUT VALUE='首  页' class=cssButton90 TYPE=button id='_wfInputFisrtPage"+c.id+"'/>");
		$(aDiv).css({"text-align":"center","position":"center"}).append("<INPUT VALUE='上一页' class=cssButton91 TYPE=button id='_wfInputPreviousPage"+c.id+"'/>");
		$(aDiv).css({"text-align":"center","position":"center"}).append("<INPUT VALUE='下一页' class=cssButton92 TYPE=button id='_wfInputNextPage"+c.id+"'/>");
		$(aDiv).css({"text-align":"center","position":"center"}).append("<INPUT VALUE='尾  页' class=cssButton93 TYPE=button id='_wfInputLastPage"+c.id+"'/>");
		
//		$(aDiv).find("input").attr("onclick",function(){
//			if(this.value == '首  页'){
//				return function(){c.resultTurnPage.firstPage();$.setEmergency(c.result.emergency,c.resultGrid);$.setOtherTitle(c.result,c.resultGrid);};
//			}else if(this.value == '上一页'){
//				return function(){c.resultTurnPage.previousPage();$.setEmergency(c.result.emergency,c.resultGrid);$.setOtherTitle(c.result,c.resultGrid);};
//			}else if(this.value == '下一页'){
//				return function(){c.resultTurnPage.nextPage();$.setEmergency(c.result.emergency,c.resultGrid);$.setOtherTitle(c.result,c.resultGrid);};
//			}else if(this.value == '尾  页'){
//				return function(){c.resultTurnPage.lastPage();$.setEmergency(c.result.emergency,c.resultGrid);$.setOtherTitle(c.result,c.resultGrid);};
//			}
//		});
		
		$(t).find("#" + c.id + "RDiv").append(aDiv);
		
		$("#_wfInputFisrtPage"+c.id).click(function(){
			c.resultTurnPage.firstPage();
			$.setEmergency(c.result.emergency,c.resultGrid);
			$.setOtherTitle(c.result,c.resultGrid);
		});
		$("#_wfInputPreviousPage"+c.id).click(function(){
			c.resultTurnPage.previousPage();
			$.setEmergency(c.result.emergency,c.resultGrid);
			$.setOtherTitle(c.result,c.resultGrid);
		});
		$("#_wfInputNextPage"+c.id).click(function(){
			c.resultTurnPage.nextPage();
			$.setEmergency(c.result.emergency,c.resultGrid);
			$.setOtherTitle(c.result,c.resultGrid);
		});
		$("#_wfInputLastPage"+c.id).click(function(){
			c.resultTurnPage.lastPage();
			$.setEmergency(c.result.emergency,c.resultGrid);
			$.setOtherTitle(c.result,c.resultGrid);
		});
	};
	$.setEmergency = function (emergency, grid){// set mulline with emergency
												// color
		if(emergency == null || grid == null) return;
		var colNo = emergency.colNo;
		if(colNo == "default") colNo = grid.colCount - 1;
		var id = "#span" + grid.instanceName;
		var target;
		(emergency.target=="input")?target="input":target="td";
		for(var rowNo = 0;rowNo < grid.mulLineCount;rowNo++){
			var color = "",initNo = 0;
			var eValue = parseInt(grid.getRowColData(rowNo,colNo));
			for(var j in emergency.colorMapping){
				var k = parseInt(j);
				if(eValue >= k && k >= initNo){
					initNo = k;
					color = $(emergency.colorMapping).attr(j);
				}
			}/*
			if(color != ""){
				$(id + rowNo).find(target).each(function(index,element){
					$(this).css("background-color",color);
				});
			}*//*2015-07-06*/
		}
	};
	$.setOtherTitle =  function(config, grid){// set other title
		if(config.otherTitle){
			if(config.otherTitle.colNo == "default") config.otherTitle.colNo = grid.mulLineCount - 1;
			var id = "#" + grid.instanceName + config.otherTitle.colNo + "r";
			for(var i = 0,size=grid.mulLineCount;i<size;i++){
				$(id+i).attr("onmouseover",function(index,oldValue){// removeAttr("onmouseover")
																	// makes a
																	// bug.
					
					if($.noop == 'function () {}'){
						return "";
					}
					return $.noop;
				}).attr("title",function(index,oldValue){
					if(typeof(config.otherTitle.getTitle)=="function"){
						return config.otherTitle.getTitle(i,grid);
					}else if(config.otherTitle.getTitle)
						return this.value;
				}).css("cursor","pointer");
			}				
		}
	};
	$.queryButton = function(com, config, activityId, operator){
		if (com == "search"){// search result
			var query,result;
			query = config.query;
			result = config.result;
			resultTurnPage = config.resultTurnPage;
			var iArray = config.queryGrid.getRowData(0);// value array
			if(iArray){
				var iArray2 = new Array();// column array
				var iArray3 = new Array();// column array2
				var i;
				var $col = $(result.arrayInfo.col)
				var condition = result.condition;
				var $condition = $(condition);
				for(i in result.arrayInfo.col){// part of sql
					var col = $col.attr(i);
					var rename = col.rename;
					if(rename==null||rename=="undefined")rename =col.colName;
					iArray2[col.colNo-1] = rename + ",";// select part
					if(col.colNo == 0 || col.colName == null) continue;
					if(rename == col.colName)rename="";
					iArray3[col.colNo-1] = col.colName + " " + rename + ",";// select
																			// part
				}
				$col = $(query.arrayInfo.col);
				for(i in query.arrayInfo.col){
					var col = $col.attr(i);// where part
					if(col){
						var v = iArray[col.colNo-1];
						iArray[col.colNo-1] = "";
						if(v != "" && v != "undefined" && v != null){
							if(typeof(col.addCondition)=="function"){
								iArray[col.colNo-1] = col.addCondition(col.colName,v);
							}else if(col.colName){
								iArray[col.colNo-1] = "and " + col.colName + " = '" + v + "' ";
							}
						}
					}
				}
				var strSql = "";
				for(i = 0;i < iArray2.length; i++){
					if(iArray2[i])
						strSql += iArray2[i];
				}
				strSql = strSql.substring(0,strSql.length-1);

				var mysql = new SqlClass();
				mysql.setResourceName("workflow.FlowGridSql"); // properties
																// name
				mysql.setSqlId(result.arrayInfo.sqlId);// Sql id
				mysql.addSubPara(strSql);// select part1
				strSql = ""
				for(i = 0;i < iArray3.length; i++){
					if(iArray3[i])
						strSql += iArray3[i];
				}
				strSql = strSql.substring(0,strSql.length-1);
				mysql.addSubPara(strSql);// select part2
				var activityIdStr =  " and activityid in (";
				var temp = activityId.split(",");
				for(var i=0,size=temp.length-1;i<=size;i++){
					activityIdStr += "\'" + temp[i] + "\',"
				}
				activityIdStr = activityIdStr.substring(0,activityIdStr.length-1);
				activityIdStr += ") ";
				mysql.addSubPara(activityIdStr);
				strSql = "";
				for(i = 0;i < iArray.length; i++){
					strSql += iArray[i];
				}
				mysql.addSubPara(strSql);
				
				var conditionArray = new Array();
				for(i in condition){
					conditionArray[i] = $condition.attr(i);
				}
				for(i = 0; i <conditionArray.length; i++){
					var j = conditionArray[i];
					if(j){
						if(j=="operator"){
							mysql.addSubPara(operator);
						}else{
							mysql.addSubPara(j);
						}
					}else
						mysql.addSubPara("");
				}
				resultTurnPage.queryModal(mysql.getString(),config.resultGrid);
				if(result.emergency){// change color for emergency
					$.setEmergency(result.emergency,config.resultGrid);
				}
				if(result.otherTitle){// set a different title for a column
					$.setOtherTitle(result,config.resultGrid);
				}
			}
		}else{
			com = eval(com);
			if(typeof(com)=="function")
				com(config.queryGrid, activityId, operator);
		}
	};
	var docloaded = false;
	$(document).ready(function () {
		docloaded = true;
	});
	$.fn.workpool = function(congfig){
		return this.each(function () {
			if (!docloaded) {
				$(this).hide();
				var t = this;
				$(document).ready(function () {
					$.addWorkPools(t, congfig);
				});
			} else {
				$.addWorkPools(this, congfig);
			}
		});
	};
})(jQuery);