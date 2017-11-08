"use strict";
var SVGDocument = null;
var SVGRoot = null;
var svg = null;
var currTarget = null;

var _actionState = null;
var _linkStartNode = null;

var _workflowAction = null;

var _openWin = null;

var _maxHeight = 600;
var _maxWidth = 900;

var missionId = null;


String.prototype.startWith=function(str){     
	  var reg=new RegExp("^"+str);     
	  return reg.test(this);        
}  

String.prototype.endWith=function(str){     
	  var reg=new RegExp(str+"$");     
	  return reg.test(this);        
}

function exp(evt){
	return svg.toString();
}

function Init(evt) {

	if(!SVGDocument) {
		SVGDocument = evt.target.ownerDocument;
		SVGDocument.oncontextmenu = function() {return false;};
	}
	
	if(!SVGRoot) SVGRoot = SVGDocument.documentElement;

	if(!svg) {
		svg = Snap(SVGRoot);
		svg.click(_clearmenu);
	}
	
	try{
		_workflowAction = window.parent.action;
	}catch(e){}
}

function _setLinkState(evt) {
	_actionState = "link";
	_cleanCurrTarget();
	_linkStartNode = null;
}

function _setMoveState(evt) {
	_actionState = "move";
	_cleanCurrTarget();
	_linkStartNode = null;
}

function setProperty(evt) {
	if (currTarget == null)
		return;
	if (currTarget.type == 'polyline') {
		if(!_checkOpenWin()) return;
		_openWin = window
				.open(
						encodeURI('./dialogMain.jsp?t=line&id='+currTarget.attr('id')+"&name="+currTarget.attr("name")+
								"&pid="+svg.attr('id')+"&pver="+svg.attr("version")+"&ftype="+svg.attr("flowType")+
								"&ctype="+currTarget.attr("conditionType")+
								"&condition="+currTarget.attr("condition")+
								"&desc="+currTarget.attr("condDesc")+"&action="+_workflowAction),
						'设置',
						'height=300,width=510,top=150,left=400,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no',
						false);
	} else if (currTarget.type == "ellipse"
		|| currTarget.type == "rect"){
		if (currTarget.attr('id') == 'start' || currTarget.attr('id') == 'end')
			return;
		if(!_checkOpenWin()) return;
		_openWin = window
				.open(
						encodeURI('./dialogMain.jsp?t=node&id='+ currTarget.attr('id')+"&name="+currTarget.attr("name")+
								"&time="+currTarget.attr("time")+"&type="+svg.attr("flowType")+"&action="+_workflowAction),
						'设置',
						'height=300,width=510,top=150,left=400,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no',
						false);
	}
	else if (currTarget.type == "svg"){
		if(!_checkOpenWin()) return;
		_openWin = window
		.open(
				encodeURI('./dialogMain.jsp?t=flow&id='+svg.attr('id')+"&ver="+svg.attr("version")+
						"&name="+svg.attr("flowName")+"&type="+svg.attr("flowType")+"&action="+_workflowAction),
				null,
				'height=300,width=510,top=150,left=400,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no',
				false);
		return;
	}

	// alert(currTarget.attr('id'));
	return;
}

function _setCurrTarget(evt, targetID) {
	_cleanCurrTarget();
	var target = _findTarget(evt, targetID)
	if (target.type == "polyline") {
		target.attr({
			stroke : "red",
			'marker-end' : svg.select("#markerArrowRed")
		});
		_insertLink(target,"change");
		currTarget = target;
	} 
	else if (target.type == "ellipse"
			|| target.type == "rect") {
		target.attr({
			fill: "rgba(141, 130, 244, 1)",
		});
		target.parent().append(target);
		target.parent().append(svg.select("#"+target.attr('ref-id')));
		currTarget = target;
	}
	else if (target.type == "svg"){
		currTarget = svg;
	}
	try {
		window.parent.treeSetTarget(target.attr("id"));
	} catch (e) {
	}
	return;
}

function _findTarget(evt, targetID) {
	if(!targetID.startWith("n") && !targetID.startWith("l") &&  !targetID.startWith("start")  && !targetID.startWith("end"))
		return svg;
	var target = svg.select("#"+targetID);
	if (target.type == "text") {
		return svg.select("#"+target.attr('ref-id'));
	}
	else if (target.type == "tspan") {
		return svg.select("#"+target.parent().attr('ref-id'));
	}
	else{
		return target;
	}
	return null;
}

function _cleanCurrTarget() {
	if (currTarget != null) {
		if (currTarget.type == "ellipse" || currTarget.type == "rect") {
			currTarget.attr({
				fill :  "#eee"
			});
		} else if (currTarget.type == "polyline") {
			currTarget.attr({
				stroke :  "black",
				"marker-end" : svg.select("#markerArrow")
			});
		}
		currTarget = null;
	}
}

function _checkOpenWin(){
	if(_openWin && !_openWin.closed){
		alert("您还有打开的窗口没有关闭");
		return false;
	}
	_openWin = null;
	return true;
}

function createNew(evt) {
	try{
		_workflowAction = window.parent.action;
	}catch(e){}
	//copy状态下自动调用
	if('copy' == _workflowAction){
		svg.attr({
			id:"",
			flowName:"",
			version:"",
		});
		try {
			window.parent.treePName("","<font color=red>复制流程</font>");
		} catch (e) {
		}
	}
	if(!_checkOpenWin()) return false;
	_openWin = window
			.open(
					'./dialogMain.jsp?t=flow&action='+_workflowAction+"&type="+svg.attr("flowType"),
					null,
					'height=300,width=510,top=150,left=400,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no',
					false);
	// alert(currTarget.attr('id'));
	return;
}

function _loadProcess(evt, id, ver, name, type) {

	svg.attr("id",id);
	svg.attr("version",ver);
	svg.attr("flowName",name);
	svg.attr("flowType",type);
	
	try {
		window.parent.treePName(id,name);
	} catch (e) {
	}
	return true;
	
}



function _empty(evt) {
	svg.selectAll("ellipse").remove();
	svg.selectAll("rect").remove();
	svg.selectAll("text").remove();
	svg.selectAll("polyline").remove();
	svg.selectAll("path").remove();
	_cleanCurrTarget();
	
	svg.attr("id","");
	svg.attr("version","");
	svg.attr("flowName","");
	svg.attr("flowType","");
	
	// 插入Line定位使用, 并且作为图片下载的背景图片
	svg.paper.rect("-10%","-10%","110%","110%").attr({
		id: "BackDrop",
	    fill: "white",
	    "pointer-events": "all" 
	});
	var markerPath1 = svg.paper.path("M 0 0 L 20 10 L 0 20 z").attr({
		fill:"black",
		stroke:"black"
	});
	var markerPath2 = svg.paper.path("M 0 0 L 20 10 L 0 20 z").attr({
		fill:"red",
		stroke:"red"
	});
	svg.select("#markerArrow").append(markerPath1);
	svg.select("#markerArrowRed").append(markerPath2);
	
	try {
		window.parent.treeClear();
	} catch (e) {
	}
}

function _createNode(evt, type) {
	var x = 15;
	var y = 15;
	var flowType = svg.attr("flowType");
	if (flowType == null || flowType === ''){
		alert("请先创建一个新流程！");
		return false;
	}
	if (type == 'start' || type == 'end') {
		_loadNode(evt, type, x, y, null, null, null, null,null);
	} else {
		if(!_checkOpenWin()) return false;
		_openWin = window
				.open(
						'./dialogMain.jsp?t=node&type=' + flowType,
						null,
						'height=300,width=510,top=150,left=400,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no',
						false);
	}
}

function _loadNode(evt, type, x, y, pid, name, timeType, time,status){
	if (type == 'start' || type == 'end') {
		if ((type == 'start' && svg.select('#start'))
				|| (type == 'end' && svg.select('#end'))) {
			alert("已经存在“开始”或“结束”节点");
			return false;
		}
		var nodeName = type == 'start' ? "开始" : "结束";
		var cx = x + 25;
		var cy = y + 20;
		var startNode = svg.paper.ellipse(cx, cy, 25, 20).attr({
			id: type,
			name: nodeName,
			style: "fill:#eee;stroke:black;stroke-width:1",
			filter: "url(#shadow)",
			"ref-id": type + "Text"
		});
		if(_workflowAction != 'rebuild')
			startNode.drag(_dragging, _startDrag, _droped);
		else
			startNode.click(function(evt){
						_setCurrTarget(evt,this.attr('id'));
					}).dblclick(function(evt){
						_setCurrTarget(evt, this.attr('id'));
						setProperty(evt) ;
					});
		
		var startNodeText = svg.paper.text(cx - 15, cy + 7, nodeName).attr({
			id: type + "Text",
			fill: "black",
			"font-size": "15",
			"font-family": "SimSun",
			"ref-id": type,
			"cursor" : "Default"
		});
		if(_workflowAction != 'rebuild')
			startNodeText.drag(_dragging, _startDrag, _droped);
		else
			startNodeText.click(function(evt){
						_setCurrTarget(evt,this.attr('id'));
					}).dblclick(function(evt){
						_setCurrTarget(evt, this.attr('id'));
						setProperty(evt) ;
					});
		
		try {
			window.parent.treeAdd("node", type, nodeName,"");
		} catch (e) {
		}
		return true;
	} else {
		if(!pid.startWith("n")) {
			pid = "n" + pid;
		}
		var hasElm = svg.select("#"+pid);
		if(hasElm) {
			if(!evt) {//新增返回null
				alert("节点已经存在！");
				return false;
			}
			if (timeType == null)
				hasElm.attr("timeType", "");
			else
				hasElm.attr("timeType", timeType);
			if (time == null)
				hasElm.attr("time", "");
			else
				hasElm.attr("time", time);
			return true;
		}
		var name1 = name.substring(0, 5);
		var name2 = "";
		if (name.length > 5) {
			name2 = name.substring(5, 10);
		}
		if (name.length > 10) {
			name2 += "..";
		}

		var processNode = svg.paper.rect(x, y , 100, 50, 8, 8).attr({
			id: pid,
			"ref-id": pid + "Text",
			"name": name,
			style: "fill:#eee;stroke:black;stroke-width:1",
			filter: "url(#shadow)",
			"timeType" : (timeType == null ? "": timeType),
			"time" : (time == null ? "": time)
		});
		if(_workflowAction == 'status'){
			if(status == '1')
				processNode.attr("stroke","red");
			else if(status == '2')
				processNode.attr("stroke","blue");
		}
		if(_workflowAction == 'status'){
			processNode.click(function(evt){
				_setCurrTarget(evt,this.attr('id'));
				var his = _getNodeHis(missionId,this.attr('id').substring(1));
				parent.showHis(evt.clientX,evt.clientY,his,_maxWidth,_maxHeight);
			});
			
		}else if(_workflowAction == 'rebuild')
			processNode.click(function(evt){
				_setCurrTarget(evt,this.attr('id'));
			}).dblclick(function(evt){
				_setCurrTarget(evt, this.attr('id'));
				setProperty(evt) ;
			});
			
		else
			processNode.drag(_dragging, _startDrag, _droped);
		
		var processNodeText = svg.paper.text(x + 10,  y + 25,[name1, name2]).attr({
			id : pid + "Text",
			"ref-id": pid,
			fill: "black",
			"font-size": 15,
			"font-family": "SimSun",
			"cursor" : "Default"
		});
		
		if(_workflowAction == 'status')
			processNodeText.click(function(evt){
				_setCurrTarget(evt,this.attr('id'));
				var his = _getNodeHis(missionId,this.attr('ref-id').substring(1));
				parent.showHis(evt.clientX,evt.clientY,his,_maxWidth,_maxHeight);
			});
		else if(_workflowAction == 'rebuild') {
			processNodeText.click(function(evt){
				_setCurrTarget(evt,this.attr('id'));
			}).dblclick(function(evt){
				_setCurrTarget(evt, this.attr('id'));
				setProperty(evt) ;
			});
		}
		else 
			processNodeText.drag(_dragging, _startDrag, _droped);
		
			
		
		var tspan1 =  processNodeText.node.childNodes[1];
		tspan1.setAttribute("dx", -75);
		tspan1.setAttribute("dy", 17);

		try {
			window.parent.treeAdd("node", pid, name, "");
		} catch (e) {
		}
	}
	return true;
}

function _createLink(startNode, endNode) {
	_loadLink(startNode, endNode, null, null, null,null, null);
}

function _loadLink(startNode, endNode, id, name, condition, conditionType, condDesc) {
	if((startNode == null || endNode == null) && id == null){
		alert("联线错误！");
		return false;
	}
	var startNodeID = null;
	if(startNode){
		startNodeID = startNode.attr("id");
		if(startNodeID.startWith("n"))
			startNodeID = startNodeID.substring(1);
	}
	var endNodeID = null;
	if(endNode){
		endNodeID =  endNode.attr("id");
		if(endNodeID.startWith("n"))
			endNodeID = endNodeID.substring(1);
	}
	if(id){
		if(id.indexOf("start") == -1 && !id.startWith("l")) {
			id = "l" + id;
		}
	}
	
	var hasElm  = null;
	if(id) {
		hasElm = svg.select("#"+id);
	}
	else {
		if(startNodeID !="start")
			hasElm = svg.select("#l"+startNodeID + "and" + endNodeID);
		else 
			hasElm = svg.select("#"+startNodeID + "and" + endNodeID);
	}
	if (hasElm) {
		if (conditionType != null)
			hasElm.attr("conditionType", conditionType);
		if (condition != null)
			hasElm.attr("condition", condition);
		if (condDesc != null)
			hasElm.attr("condDesc", condDesc);
		return true;
	}

	if (startNode.attr("id") == "end" || endNode.attr("id") == "start") {
		alert("联线错误！")
		return false;
	}
	
	if( id == null) {
		id = (startNodeID !="start"?"l":"")+startNodeID + "and" + endNodeID;
	}

	var linkpoints = _calLinkPoints(startNode, endNode);

	var line = svg.paper.polyline().attr({
	    "id" : id,
	    "ref-id" : id+"Path",
	    "start-id" : startNode.attr("id"),
	    "end-id" : endNode.attr("id"),
	    style : "fill:none;stroke:black;stroke-width:1",
	    "marker-end" : svg.select("#markerArrow"),
	    "name" : name != null ? name:(startNode.attr("name")+" 到 "+endNode.attr("name")),
		"condition": condition != null ? condition :"",
		"conditionType": conditionType != null ? conditionType :"",//0虽然是默认值，但是考虑兼容历史数据，不能置默认值
		"condDesc": condDesc != null ? condDesc :""
	}).click(function(evt){
			if(_workflowAction == 'status'){
				return;
			}
			_setCurrTarget(evt,this.attr('id'));
		}).dblclick(function(evt){
				if(_workflowAction == 'status'){
					return;
				}
				_setCurrTarget(evt, this.attr('id'));
				setProperty(evt) ;
			});
	
	var ref_path = null;
	// 重新改变polyline
	if (linkpoints.mid.x == 0 || linkpoints.mid.y == 0) {// 没有折线
		line.attr('points', linkpoints.start.x + "," + linkpoints.start.y + " "
				+ linkpoints.end.x + "," + linkpoints.end.y);
		//背景line，为了能点中线
		ref_path = svg.paper.path(
				"M"+linkpoints.start.x + "," + linkpoints.start.y + "L"
				+ linkpoints.end.x + "," + linkpoints.end.y);
	} else {
		line.attr('points', linkpoints.start.x + ","
				+ linkpoints.start.y + " " + linkpoints.mid.x + ","
				+ linkpoints.mid.y + " " + linkpoints.end.x + ","
				+ linkpoints.end.y);
		//背景line，为了能点中线
		ref_path = svg.paper.path("M"+linkpoints.start.x + ","
				+ linkpoints.start.y + "L" + linkpoints.mid.x + ","
				+ linkpoints.mid.y + "L" + linkpoints.end.x + ","
				+ linkpoints.end.y);
	}
	
	ref_path.attr({
		"id": id+"Path",
		"ref-id" : id,
		style : "fill:none;stroke:white;stroke-width:5",
		}).click(function(evt){
			if(_workflowAction == 'status'){
				return;
			}
			_setCurrTarget(evt,this.attr('ref-id'));
		}).dblclick(function(evt){
			if(_workflowAction == 'status'){
				return;
			}
			_setCurrTarget(evt, this.attr('ref-id'));
			setProperty(evt) ;
		});
	
	_insertLink(line,"new");
	
	try {
		window.parent.treeAdd("link", id, startNode.attr("name"),
				endNode.attr("name"));
	} catch (e) {
	}
	return true;
}

function _insertLink(line,type){
	var allLinks = svg.selectAll("polyline");
	
	if (allLinks && allLinks.length > 1) {
		if(type == "new") {
			var lastLink = allLinks[allLinks.length - 2];
			line.insertAfter(lastLink);
		}
		else {
			line.insertAfter(allLinks[allLinks.length - 1]);
		}
		
	} else {
		var backDrop = svg.select("#BackDrop");
		if (backDrop) {
			line.insertAfter(backDrop);
		} else {
			svg.paper.append(line);
		}
	}
	//调整Path位置
	 svg.select("#"+line.attr("ref-id")).insertBefore(line);
}

function _calLinkPoints(startNode, endNode) {
	// 获得当前Start节点的四个角的坐标和四个边中心的坐标
	// NW - 左上角，NE － 右上，SW－左下，SE－右下，NC－上边中心点，EC－右边中心点，SC－下边中心，WC－左边中心
	var start_NW_X = 0, start_NW_Y = 0;
	var start_NE_X = 0, start_NE_Y = 0;
	var start_SW_X = 0, start_SW_Y = 0;
	var start_SE_X = 0, start_SE_Y = 0;
	var start_NC_X = 0, start_NC_Y = 0;
	var start_EC_X = 0, start_EC_Y = 0;
	var start_SC_X = 0, start_SC_Y = 0;
	var start_WC_X = 0, start_WC_Y = 0;
	var start_CC_X = 0, start_CC_Y = 0;// 图形中心点
	if (startNode.type == "ellipse") {
		start_NW_X = Number(startNode.attr('cx'))
				- Number(startNode.attr('rx'));
		start_NW_Y = Number(startNode.attr('cy'))
				- Number(startNode.attr('ry'));

		start_NE_X = Number(startNode.attr('cx'))
				+ Number(startNode.attr('rx'));
		start_NE_Y = Number(startNode.attr('cy'))
				- Number(startNode.attr('ry'));

		start_SW_X = Number(startNode.attr('cx'))
				- Number(startNode.attr('rx'));
		start_SW_Y = Number(startNode.attr('cy'))
				+ Number(startNode.attr('ry'));

		start_SE_X = Number(startNode.attr('cx'))
				+ Number(startNode.attr('rx'));
		start_SE_Y = Number(startNode.attr('cy'))
				+ Number(startNode.attr('ry'));

		start_SC_X = Number(startNode.attr('cx'));
		start_SC_Y = Number(startNode.attr('cy'))
				+ Number(startNode.attr('ry'));

		start_EC_X = Number(startNode.attr('cx'))
				+ Number(startNode.attr('rx'));
		start_EC_Y = Number(startNode.attr('cy'));

		start_WC_X = Number(startNode.attr('cx'))
				- Number(startNode.attr('rx'));
		start_WC_Y = Number(startNode.attr('cy'));

		start_NC_X = Number(startNode.attr('cx'));
		start_NC_Y = Number(startNode.attr('cy'))
				- Number(startNode.attr('ry'));

		start_CC_X = Number(startNode.attr('cx'));
		start_CC_Y = Number(startNode.attr('cy'));

	} else if (startNode.type == "rect") {
		start_NW_X = Number(startNode.attr('x'));
		start_NW_Y = Number(startNode.attr('y'));

		start_NE_X = Number(startNode.attr('x'))
				+ Number(startNode.attr('width'));
		start_NE_Y = Number(startNode.attr('y'));

		start_SW_X = Number(startNode.attr('x'));
		start_SW_Y = Number(startNode.attr('y'))
				+ Number(startNode.attr('height'));

		start_SE_X = Number(startNode.attr('x'))
				+ Number(startNode.attr('width'));
		start_SE_Y = Number(startNode.attr('y'))
				+ Number(startNode.attr('height'));

		start_SC_X = Number(startNode.attr('x'))
				+ Number(startNode.attr('width')) / 2;
		start_SC_Y = Number(startNode.attr('y'))
				+ Number(startNode.attr('height'));

		start_EC_X = Number(startNode.attr('x'))
				+ Number(startNode.attr('width'));
		start_EC_Y = Number(startNode.attr('y'))
				+ Number(startNode.attr('height')) / 2;

		start_NC_X = Number(startNode.attr('x'))
				+ Number(startNode.attr('width')) / 2;
		start_NC_Y = Number(startNode.attr('y'));

		start_WC_X = Number(startNode.attr('x'));
		start_WC_Y = Number(startNode.attr('y'))
				+ Number(startNode.attr('height')) / 2;

		start_CC_X = Number(startNode.attr('x'))
				+ Number(startNode.attr('width')) / 2;
		start_CC_Y = Number(startNode.attr('y'))
				+ Number(startNode.attr('height')) / 2;
	}

	// 获得当前End节点的四个角的坐标和四个边中心的坐标
	var end_NW_X = 0, end_NW_Y = 0;
	var end_NE_X = 0, end_NE_Y = 0;
	var end_SW_X = 0, end_SW_Y = 0;
	var end_SE_X = 0, end_SE_Y = 0;
	var end_NC_X = 0, end_NC_Y = 0;
	var end_EC_X = 0, end_EC_Y = 0;
	var end_SC_X = 0, end_SC_Y = 0;
	var end_WC_X = 0, end_WC_Y = 0;
	var end_CC_X = 0, end_CC_Y = 0;
	if (endNode.type == "ellipse") {
		end_NW_X = Number(endNode.attr('cx'))
				- Number(endNode.attr('rx'));
		end_NW_Y = Number(endNode.attr('cy'))
				- Number(endNode.attr('ry'));

		end_NE_X = Number(endNode.attr('cx'))
				+ Number(endNode.attr('rx'));
		end_NE_Y = Number(endNode.attr('cy'))
				- Number(endNode.attr('ry'));

		end_SW_X = Number(endNode.attr('cx'))
				- Number(endNode.attr('rx'));
		end_SW_Y = Number(endNode.attr('cy'))
				+ Number(endNode.attr('ry'));

		end_SE_X = Number(endNode.attr('cx'))
				+ Number(endNode.attr('rx'));
		end_SE_Y = Number(endNode.attr('cy'))
				+ Number(endNode.attr('ry'));

		end_WC_X = Number(endNode.attr('cx'))
				- Number(endNode.attr('rx'));
		end_WC_Y = Number(endNode.attr('cy'));

		end_SC_X = Number(endNode.attr('cx'));
		end_SC_Y = Number(endNode.attr('cy'))
				+ Number(endNode.attr('ry'));

		end_NC_X = Number(endNode.attr('cx'));
		end_NC_Y = Number(endNode.attr('cy'))
				- Number(endNode.attr('ry'));

		end_EC_X = Number(endNode.attr('cx'))
				+ Number(endNode.attr('rx'));
		end_EC_Y = Number(endNode.attr('cy'));

		end_CC_X = Number(endNode.attr('cx'));
		end_CC_Y = Number(endNode.attr('cy'));

	} else if (endNode.type == "rect") {
		end_NW_X = Number(endNode.attr('x'));
		end_NW_Y = Number(endNode.attr('y'));

		end_NE_X = Number(endNode.attr('x'))
				+ Number(endNode.attr('width'));
		end_NE_Y = Number(endNode.attr('y'));

		end_SW_X = Number(endNode.attr('x'));
		end_SW_Y = Number(endNode.attr('y'))
				+ Number(endNode.attr('height'));

		end_SE_X = Number(endNode.attr('x'))
				+ Number(endNode.attr('width'));
		end_SE_Y = Number(endNode.attr('y'))
				+ Number(endNode.attr('height'));

		end_WC_X = Number(endNode.attr('x'));
		end_WC_Y = Number(endNode.attr('y'))
				+ Number(endNode.attr('height')) / 2;
		;

		end_SC_X = Number(endNode.attr('x'))
				+ Number(endNode.attr('width')) / 2;
		end_SC_Y = Number(endNode.attr('y'))
				+ Number(endNode.attr('height'));

		end_NC_X = Number(endNode.attr('x'))
				+ Number(endNode.attr('width')) / 2;
		end_NC_Y = Number(endNode.attr('y'));

		end_EC_X = Number(endNode.attr('x'))
				+ Number(endNode.attr('width'));
		end_EC_Y = Number(endNode.attr('y'))
				+ Number(endNode.attr('height')) / 2;

		end_CC_X = Number(endNode.attr('x'))
				+ Number(endNode.attr('width')) / 2;
		end_CC_Y = Number(endNode.attr('y'))
				+ Number(endNode.attr('height')) / 2;
	}

	var start_Location_X = 0;
	var start_Location_Y = 0;
	var mid_Location_X = 0;
	var mid_Location_Y = 0;
	var end_Location_X = 0;
	var end_Location_Y = 0;

	// 开始 => North_Center，结束 => East_Center
	if (start_NC_X > end_SE_X && start_NC_Y > end_EC_Y) {
		start_Location_X = start_NC_X;
		start_Location_Y = start_NC_Y;

		mid_Location_X = start_NC_X;
		mid_Location_Y = end_EC_Y;
		if (start_NC_X - end_EC_X > 6) {
			end_Location_X = end_EC_X + 6;
		} else {
			end_Location_X = end_EC_X;
		}
		end_Location_Y = end_EC_Y;
	}

	// 开始 => North_Center，结束 => South_Any
	else if (start_NC_X >= end_SW_X && start_NC_X <= end_SE_X
			&& start_NC_Y > end_SC_Y) {
		start_Location_X = start_NC_X;
		start_Location_Y = start_NC_Y;

		end_Location_X = start_NC_X;
		end_Location_Y = end_SC_Y + 6;
	}

	// 开始 => North_Center，结束 => West_Center
	else if (start_NC_X < end_SW_X && start_NC_Y > end_WC_Y) {

		start_Location_X = start_NC_X;
		start_Location_Y = start_NC_Y;

		mid_Location_X = start_NC_X;
		mid_Location_Y = end_WC_Y;

		if (end_SW_X - start_NC_X > 6) {
			end_Location_X = end_WC_X - 6;
		} else {
			end_Location_X = end_WC_X;
		}
		end_Location_Y = end_WC_Y;
	}

	// 开始 => East_Any, 结束 => West_Center
	else if (start_NE_X < end_WC_X && start_NE_Y <= end_WC_Y
			&& start_SE_Y >= end_WC_Y) {

		start_Location_X = start_NE_X;
		start_Location_Y = end_WC_Y;

		end_Location_X = end_WC_X - 6;
		end_Location_Y = end_WC_Y;
	}
	// 开始 => South_Center, 结束 => West_Center
	else if (start_SC_X < end_WC_X && start_SC_Y < end_WC_Y) {
		start_Location_X = start_SC_X;
		start_Location_Y = start_SC_Y;

		mid_Location_X = start_SC_X;
		mid_Location_Y = end_WC_Y;

		if (end_WC_X - start_SC_X > 6) {
			end_Location_X = end_WC_X - 6;
		} else {
			end_Location_X = end_WC_X;
		}
		end_Location_Y = end_WC_Y;
	}

	// 开始 => South_Center, 结束 => North_Any
	else if (start_SC_X >= end_NW_X && start_SC_X <= end_NE_X
			&& start_SC_Y < end_NC_Y) {

		start_Location_X = start_SC_X;
		start_Location_Y = start_SC_Y;

		end_Location_X = start_SC_X;
		end_Location_Y = end_NC_Y - 6;
	}

	// 开始 => South_Center, 结束 => East_Center
	else if (start_SC_X > end_EC_X && start_SC_Y < end_EC_Y) {
		start_Location_X = start_SC_X;
		start_Location_Y = start_SC_Y;

		mid_Location_X = start_SC_X;
		mid_Location_Y = end_EC_Y;
		if (start_SC_X - end_EC_X > 6) {
			end_Location_X = end_EC_X + 6;
		} else {
			end_Location_X = end_EC_X;
		}
		end_Location_Y = end_EC_Y;
	}

	// 开始 => West_Any, 结束 => East_Center
	else if (start_WC_X > end_EC_X && start_NW_Y <= end_EC_Y
			&& start_SW_Y >= end_EC_Y) {
		start_Location_X = start_WC_X;
		start_Location_Y = end_EC_Y;

		end_Location_X = end_EC_X + 6;
		end_Location_Y = end_EC_Y;
	}
	// 图像出现重合，比较中心点的位置
	else {
		if (start_CC_Y <= end_CC_Y) {
			start_Location_X = start_SC_X;
			start_Location_Y = start_SC_Y;

			end_Location_X = end_NC_X;
			end_Location_Y = end_NC_Y;
		} else {
			start_Location_X = start_NC_X;
			start_Location_Y = start_NC_Y;

			end_Location_X = end_SC_X;
			end_Location_Y = end_SC_Y;
		}
	}

	var start = {};
	start.x = start_Location_X;
	start.y = start_Location_Y;

	var mid = {};
	mid.x = mid_Location_X;
	mid.y = mid_Location_Y;

	var end = {};
	end.x = end_Location_X;
	end.y = end_Location_Y;
	var linkPoints = {};
	linkPoints.start = start;
	linkPoints.end = end;
	linkPoints.mid = mid;
	return linkPoints;
}


function _del(evt) {
	if (currTarget != null) {
		if(_workflowAction == 'query' || _workflowAction == 'rebuild') return;
		if(confirm("您确认删除吗？")){
		if (currTarget.type == "ellipse" || currTarget.type == "rect") {
			var links = _findLinks(currTarget);
			for (var i = 0; i < links.length; i++) {
				try {
					window.parent.treeDel(links[i].attr('id'));
				} catch (e) {
					console.log(e);
				}
				svg.select("#"+links[i].attr("ref-id")).remove();
				links[i].remove();
			}
			var ref_id = currTarget.attr('ref-id');
			var refElement = svg.select("#"+ref_id);
			refElement.remove();
			try {
				window.parent.treeDel(currTarget.attr('id'));
			} catch (e) {
				console.log(e);
			}
			currTarget.remove();

		} else if (currTarget.type == "polyline") {
			try {
				window.parent.treeDel(currTarget.attr('id'));
			} catch (e) {
				console.log(e);
			}
			svg.select("#"+currTarget.attr("ref-id")).remove();
			currTarget.remove();
		}
		currTarget = null;
	}}
	return;
}

function _findLinks(target) {
	var links = new Array();
	if(target) {
		var allLines = svg.selectAll("polyline");
		for (var i = 0; i < allLines.length; i++) {
			// 寻找所有以当前移动图形开始的连接线
			if (target.attr('id') == allLines[i].attr('start-id')) {
				links.push(allLines[i]);
			}
			if (target.attr('id') == allLines[i].attr('end-id')) {
				links.push(allLines[i]);
			}
		}
	}
	return links;
}

var _origX = 0;
var _origY = 0;
var _origRefX = 0;
var _origRefY = 0;
var _currTargetTime = null;//用来记录点击的时间，通过时间差来判断是否双击

// 替代了onclick和ondblclick事件
var _startDrag = function(x,y,evt) {
	if(_workflowAction == 'status'){
		return;
	}
	var thisID = this.attr('id');
	if(evt.button == 2) {
		_setCurrTarget(evt,thisID);
		_contextmenu(evt,x,y);
	}
	else if(evt.button == 0) {
		_clearmenu(evt);
		
		// 双击判断
		if (currTarget){
			var target = _findTarget(evt, thisID)
			if(target.attr("id") == currTarget.attr("id")){
				var targetTime = new Date();
				if(!_currTargetTime) _currTargetTime = targetTime;
				if(targetTime.getTime() - _currTargetTime.getTime() < 500){
					if (target.type == "polyline" || target.type == "ellipse"
						|| target.type == "rect") {
						_currTargetTime = new Date();
						_setCurrTarget(evt, target.attr('id'));
						setProperty(evt) ;
						return;
					}
				}
			}
		}
		
		// 单击
		_currTargetTime = new Date();
		_setCurrTarget(evt,thisID);

		// 联线
		if (_actionState == "link") {
			if (_linkStartNode == null) {
				_linkStartNode = currTarget;
			}
			if (_linkStartNode != currTarget) {
				_createLink(_linkStartNode, currTarget);
				_cleanCurrTarget();
				_linkStartNode == null;
				_actionState = null;
			}
		}
		else {
			//记录原始位置
			if(this.type === 'ellipse' || this.type === 'circle' ) {
				_origX = Number(this.asPX("cx"));
				_origY = Number(this.asPX("cy"));
			}
			else {
				_origX = Number(this.asPX("x"));
				_origY = Number(this.asPX("y"));
			}
			
			var ref = svg.select("#"+this.attr("ref-id"));
			if(ref) {
				if(ref.type === 'ellipse' || ref.type === 'circle' ) {
					_origRefX = Number(ref.asPX("cx"));
					_origRefY = Number(ref.asPX("cy"));
				}
				else {
					_origRefX = Number(ref.asPX("x"));
					_origRefY = Number(ref.asPX("y"));
				}
			}
		}
	}
}

var _dragging = function(dx, dy, x, y,evt) {
	if (_actionState == "link") {
		return;
	}
	if(_workflowAction == 'status'){
		return;
	}
	//IE兼容判断
	if(_origX == 0 || _origY == 0 || _origRefX == 0 || _origRefY == 0 )
		return ;
	
	this.attr({
		cursor : "move"
	});
	svg.select("#"+this.attr('ref-id')).attr({
		cursor : "move"
	});
	

	
	var newX = dx + _origX;
	var newY = dy + _origY;
	
	if(this.type === 'ellipse' || this.type === 'circle' ) {
		this.attr("cx",newX);
		this.attr("cy",newY);
	}
	else {
		this.attr("x",newX);
		this.attr("y",newY);
	}
	//this.transform("t0,0");
	
	var newRefX = dx + _origRefX;
	var newRefY = dy + _origRefY;
	
	var ref = svg.select("#"+this.attr("ref-id"));
	if(ref) {
		if(ref.type === 'ellipse' || ref.type === 'circle' ) {
			ref.attr("cx",newRefX);
			ref.attr("cy",newRefY);
		}
		else {
			ref.attr("x",newRefX);
			ref.attr("y",newRefY);
		}
		//ref.transform("t0,0");
	}
	
	_changePloyLine();
	
}

var _droped  = function(evt){
	if (_actionState == "link") {
		return;
	}
	if(_workflowAction == 'status'){
		return;
	}
	this.attr({
		cursor : "Default"
	});
	svg.select("#"+this.attr('ref-id')).attr({
		cursor : "Default"
	});
	
	_origX = 0;
	_origY = 0;
	_origRefX = 0;
	_origRefY = 0;
}

function _changePloyLine() {

	// 寻找所有以当前移动图形开始的连接线
	var changedLines = _findLinks(currTarget);
	for (var i = 0; i < changedLines.length; i++) {
		var changedLine = changedLines[i];

		var startNode = svg.select("#"+changedLine.attr('start-id'));
		var endNode = svg.select("#"+changedLine.attr('end-id'));

		var linkpoints = _calLinkPoints(startNode, endNode);

		//changedLine.attr("marker-end","");
		
		// 重新改变polyline
		if (linkpoints.mid.x == 0 || linkpoints.mid.y == 0) {// 没有折线
			changedLine.attr('points', linkpoints.start.x + ","
					+ linkpoints.start.y + " " + linkpoints.end.x + ","
					+ linkpoints.end.y);
			svg.select("#"+changedLine.attr("ref-id")).attr("d","M"+linkpoints.start.x + ","
					+ linkpoints.start.y + "L" + linkpoints.end.x + ","
					+ linkpoints.end.y)
			
		} else {
			changedLine.attr('points', linkpoints.start.x + ","
					+ linkpoints.start.y + " " + linkpoints.mid.x + ","
					+ linkpoints.mid.y + " " + linkpoints.end.x + ","
					+ linkpoints.end.y);
			svg.select("#"+changedLine.attr("ref-id")).attr("d","M"+linkpoints.start.x + ","
					+ linkpoints.start.y + "L" + linkpoints.mid.x + ","
					+ linkpoints.mid.y + "L" + linkpoints.end.x + ","
					+ linkpoints.end.y)
		}
		
		changedLine.attr("marker-end",svg.select("#markerArrow"));//IE兼容代码
		
		_insertLink(changedLine,"change");
	}
}
	
function _contextmenu(evt,x,y) {
	
	_clearmenu(evt);
	var menu1 = svg.paper.rect(x,y,80,20).attr({
		id: "menu1",
		"ref-id": "menu1Text",
		"out-id": "menu2",
		"out-ref-id": "menu2Text",
		style:"fill:white;stroke-width:1;stroke:rgb(0,0,0)",
		cursor : "pointer"
	}).mouseover(_menuMouseover).click(_menu1Click);
	var menu1Text = svg.paper.text(x+25,y+15,"属性").attr({
		id: "menu1Text",
		"ref-id": "menu1",
		"out-id": "menu2",
		"out-ref-id": "menu2Text",
		fill: "black",
		"font-size": "12",
		"font-family": "SimSun",
		cursor : "pointer"
	}).mouseover(_menuTextMouseover).click(_menu1Click);
	var menu2 = svg.paper.rect(x,y+20,80,20).attr({
		id: "menu2",
		"ref-id": "menu2Text",
		"out-id": "menu1",
		"out-ref-id": "menu1Text",
		style:"fill:white;stroke-width:1;stroke:rgb(0,0,0)",
		cursor : "pointer"
	}).mouseover(_menuMouseover).click(_menu2Click);
	var menu1Text = svg.paper.text(x+25,y+20+15,"删除").attr({
		id: "menu2Text",
		"ref-id": "menu2",
		"out-id": "menu1",
		"out-ref-id": "menu1Text",
		fill: "black",
		"font-size": "12",
		"font-family": "SimSun",
		cursor : "pointer"
	}).mouseover(_menuTextMouseover).click(_menu2Click);
	return true;
}
function _menu1Click(evt) {
	setProperty(evt);
	_clearmenu(evt);
}

function _menu2Click(evt) {
	_del(evt);
	_clearmenu(evt);
}
function _menuMouseover(evt) {
	this.attr("fill", "#348CCC");
	svg.select("#"+this.attr('ref-id')).attr("fill", "white");
	svg.select("#"+this.attr('out-id')).attr("fill", "white");
	svg.select("#"+this.attr('out-ref-id')).attr("fill", "black");
}

function _menuTextMouseover(evt) {
	this.attr("fill", "white");
	svg.select("#"+this.attr('ref-id')).attr("fill", "#348CCC");
	svg.select("#"+this.attr('out-id')).attr("fill", "white");
	svg.select("#"+this.attr('out-ref-id')).attr("fill", "black");
}

function _clearmenu(evt) {
	var menu1 = svg.select("#menu1");
	if(menu1) menu1.remove();
	var menu2 = svg.select("#menu2");
	if(menu2) menu2.remove();
	var menu1Text = svg.select("#menu1Text");
	if(menu1Text) menu1Text.remove();
	var menu2Text = svg.select("#menu2Text");
	if(menu2Text) menu2Text.remove();
}
