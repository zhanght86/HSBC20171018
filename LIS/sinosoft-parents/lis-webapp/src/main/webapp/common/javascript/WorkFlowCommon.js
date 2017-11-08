/*******************************************************************************
 * 
 * 程序名称: WorkFlowCommon.js 程序功能: 工作流组件相关功能 author:chenwei date:2011-02-28
 * 
 ******************************************************************************/
function keyItem(key, value) {
	this.key = key;
	this.value = value;
}
function MapClass() {
	this.map = new Array();
	this.put = function(key, value) {
		for (var i = 0; i < this.map.length; i++) {
			if (this.map[i].key.toLowerCase() === key.toLowerCase()) {
				this.map[i].value = value;
				return;
			}
		}
		this.map[this.map.length] = new keyItem(key, value);
	};
	this.get = function(key) {
		if(key==null)
			return null;
		for (var i = 0; i < this.map.length; i++) {
			if (this.map[i].key.toLowerCase() === key.toLowerCase()) {
				return this.map[i].value;
			}
		}
		return null;
	};
	this.remove = function(key) {
		var v;
		for (var i = 0; i < this.map.length; i++) {
			v = this.map.pop();
			if (v.key.toLowerCase() === key.toLowerCase()) {
				continue;
			}
			this.map.unshift(v);

		}
	};
	this.size = function() {
		return this.map.length;
	};
}
function changeColor(g) {
	if (g.emergency != "") {
	//	alert('g:'+g.gridName);
		_changeColor(g.grid, g.gridName, g.colorMapping);
	}
}
function _changeColor(m, n, color) {
	for (var r = 0; r < m.mulLineCount; r++) {
		for (var c = color.length - 1; c >= 0; c--) {
		//	alert('m.getRowColData(r, m.colCount - 1):'+m.getRowColData(r, m.colCount - 1)+"@@color[c][0]:"+color[c][0]);
			if (m.getRowColData(r, m.colCount - 1) >= color[c][0]) {
				for (var i = 1; i < m.colCount; i++) {
					try{
					eval("fm.all('" + n + i + "')[" + r
							+ "].style.backgroundColor='" + color[c][1] + "'");
						}
						
						catch(e)
						{
							eval("fm.all('" + n + i + "').style.backgroundColor='" + color[c][1] + "'");
						}
				}
				break;
			}
		}
	}
}
function CGrid(fm, gn, turnp) {
	this.formName = fm || "fm";
	this.gridName = gn || "";
	this.grid;
	this.tp = turnp;
	this.initFlag = false;
	this.columnParam = [{
				title : '序号',
				width : '30px'
			}]; // 存放显示的列信息
	this.mywhere = new Array();// 存放查询条件

	this.colMap = new MapClass();// 存放字段对应的列名

	this.time = "";
	this.lefttime = "";
	this.emergency = "";

	this.colorMapping = [[60, "yellow"], [100, "red"]]; //默认颜色显示策略：60以上标黄，100以上标红

	this.hideFlag = "1";
	this.locked = "1";

	this.colpart = "";
	this.wherepart = "";

	this.radioFunction = "";
	this.radioFunPrama = "";

	this.checkFunction = "";
	this.checkFunParam = "";

	this.activityId;
	this.processId;
	this.defaultOperator;

	this.setActivityId = function(actid) {
		this.activityId = actid;
		var p = {
			name : "activityid",
			value : this.activityId,
			operator : '='
		};
		this.mywhere.push(p);
		this.colMap.put("activityid", ["activityid", "活动号"]);
	};
	this.setProcessId = function(pid) {
		this.processId = pid;
		var p = {
			name : "processid",
			value : this.processId,
			operator : '='
		};
		this.mywhere.push(p);
		this.colMap.put("processid", ["processid", "过程号"]);
	};
	this.setDefaultOperator = function(op) {
		this.defaultOperator = op;
		var o = "=";
		if (op == null) {
			o = "is";
		}
		var p = {
			name : "defaultoperator",
			value : op,
			operator : o
		};
		this.mywhere.push(p);
		this.colMap.put("defaultoperator", ["defaultoperator", "操作员"]);
	};
	this.initGrid = function() {
		var mysql = new SqlClass();
		mysql.setResourceName("workflow.FlowGridSql"); // 指定使用的properties文件名
		mysql.setSqlId("fieldquery");// 指定使用的Sql的id
		if (this.activityId == null || this.activityId == "") {
			alert("活动编码为空！");
			return false;
		} else {
			mysql.addSubPara(this.activityId);// 指定传入的参数
		}
		var result = easyExecSql(mysql.getString());
		var f = false;
		var ct = 1;
		if (this.time != "") {
			ct++;
		}
		if (this.lefttime != "") {
			ct++;
		}
		if (this.emergency != "") {
			ct++;
		}
		if (this.columnParam.length == ct) {
			f = true;
		}
		
		for (var i = 0; i < result.length; i++) {
			this.colMap.put(result[i][0], [result[i][1], result[i][2]]);
			if (f) {
				var p = {
					name : result[i][0]
				};
				this.columnParam.splice(1, 0, p);
			}
		}
		var iArray = new Array();
		for (i = 0; i < this.columnParam.length; i++) {
			iArray[i] = new Array();
			var t = this.columnParam[i].title;
			//alert(t)
			if (t == null) {
				t = this.colMap.get(this.columnParam[i].name);
				if (t == null) {
					t = this.columnParam[i].name;
				}else{
					t = t[1];
				}
			}
			if (this.columnParam[i].name != null) {
				var n = this.colMap.get(this.columnParam[i].name);
				if (this.colpart == "") {
					if (n != null) {
						this.colpart = n[0];
					} else {
						this.colpart = this.columnParam[i].name;
					}
				} else {
					if (n != null) {
						this.colpart += "," + n[0];
					} else {
						this.colpart += "," + this.columnParam[i].name;
					}
				}

			}
			iArray[i][0] = t;
			iArray[i][1] = this.columnParam[i].width || '60px';
			iArray[i][2] = this.columnParam[i].maxlength || '100';
			iArray[i][3] = this.columnParam[i].readonly || '0';
			iArray[i][14] = this.columnParam[i].defaultvalue;
		};
		this.grid = new MulLineEnter(this.formName, this.gridName);
		this.grid.mulLineCount = 4;
		this.grid.displayTitle = 1;
		if (this.radioFunction != "") {
			this.grid.canSel = 1;
			this.grid.canChk = 0;
			this.grid.selBoxEventFuncName = this.radioFunction;
		}
		if (this.checkFunction != "") {
			this.grid.canSel = 0;
			this.grid.canChk = 1;
			this.grid.chkBoxEventFuncParm = this.checkFunction;
		}
		this.grid.hiddenPlus = this.hideFlag;
		this.grid.hiddenSubtraction = this.hideFlag;
		this.grid.locked = this.locked;
		this.grid.loadMulLine(iArray);
		this.initFlag = true;
		return true;
	};
	this.queryData = function() {
		if (!this.initFlag && !this.initGrid()) {
			return false;
		}
		for (var i = 0; i < this.mywhere.length; i++) {
			var nn = this.colMap.get(this.mywhere[i].name);
			var n = "";
			if (this.mywhere[i].name != null) {
				if (nn == null) {
					alert("无法找到列：" + this.mywhere[i].name);
					return false;
				} else {
					n = nn[0];
				}
			}
			if (this.mywhere[i].operator == "like") {
				this.wherepart += " and " + n + " " + this.mywhere[i].operator
						+ " '" + this.mywhere[i].value + "%'";
			} else if (this.mywhere[i].operator == "in") {
				this.wherepart += " and " + n + " " + this.mywhere[i].operator
						+ " (" + this.mywhere[i].value + ")";
			} else if (this.mywhere[i].operator == "is") {
				this.wherepart += " and " + n + " " + this.mywhere[i].operator
						+ " " + this.mywhere[i].value;
			} else if (this.mywhere[i].name == null) {
				this.wherepart += this.mywhere[i].value;
			} else
				this.wherepart += " and " + this.mywhere[i].name + " "
						+ this.mywhere[i].operator + " '"
						+ this.mywhere[i].value + "'";
		}
		var mysql = new SqlClass();
		mysql.setResourceName("workflow.FlowGridSql"); // 指定使用的properties文件名
		mysql.setSqlId("querymission");// 指定使用的Sql的id
		mysql.addSubPara(this.colpart);// 指定传入的参数
		mysql.addSubPara(this.wherepart);// 指定传入的参数
		mysql.addSubPara(this.time);// 指定传入的参数
		mysql.addSubPara(this.lefttime);// 指定传入的参数
		mysql.addSubPara(this.emergency);// 指定传入的参数
		// this.tp.pageLineNum = 2;
		// this.tp.dataBlockNum = 200;
		this.tp.queryModal(mysql.getString(), this.grid);
	  	_changeColor(this.grid,this.gridName,this.colorMapping);
	};
	this.addColumn = function(col) {
		if (col instanceof Array) {
			//alert("1:"+col.length);
			this.columnParam = this.columnParam.concat(col);
			//alert("2:"+col.length);
		} else if (typeof col == "string") {
			var c = col.split(",");
			for (var n = 0; n < c.length; n++) {
				var v = {
					name : c[n]
				};
				this.columnParam.push(v);
			}
		} else
			this.columnParam.push(col);
	}
	this.addParam = function(name, value, operator) {
		if (value != "") {
			var p = {
				name : name,
				value : value,
				operator : operator || '='
			};
			this.mywhere.push(p);
		}
	};
	this.setRadioFunction = function(fun) {
		this.radioFunction = fun;
	};
	this.setCheckFunction = function(fun) {
		this.checkFunction = fun;
	};
	this.hideOperator = function(flag) {
		this.hideFlag = flag;
	};
	this.showEmergency = function(a, b, c) {
		if (a != 0) {
			this.time = " ";
			var v = {
				title : '规定作业用时'
			};
			this.columnParam.push(v);
		}
		if (b != 0) {
			this.lefttime = " ";
			var v = {
				title : '剩余作业时间'
			};
			this.columnParam.push(v);
			this.emergency = " ";
		}
		if (c != 0) {
			//tongmeng 2011-03-04 modify 
			//暂时屏蔽这点
			this.emergency = " ";
			var v = {
				title : '紧急度指数'
			};
			this.columnParam.push(v);
		}
	};
	this.setEmergencyColor = function(color) {
		if (color instanceof Array) {
			this.colorMapping = color;
		} else {
			alert("您的格式不正确！");
			return false;
		}
	}
}
