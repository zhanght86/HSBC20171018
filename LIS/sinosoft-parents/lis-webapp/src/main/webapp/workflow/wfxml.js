"use strict";
function InitXMLHttp() {
	var xmlhttp = null;
	try {

		if (window.XMLHttpRequest) {// for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	} catch (e) {
		console.log(e);
	}
	return xmlhttp;
}

function createXML() {
	var xmlDoc;
	try // Internet Explorer
	{
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	} catch (e) {
		try // Firefox, Mozilla, Opera, etc.
		{
			xmlDoc = document.implementation.createDocument("", "", null);
		} catch (e) {
			console.log(e);
			return null;
		}
	}
	return xmlDoc;
}
// convert string to xml object
function String2XML(xmlString) {
	// for IE
	if (isIE()) {
		var xmlobject = new ActiveXObject("Microsoft.XMLDOM");
		//var xmlobject = new ActiveXObject("MSXML2.DOMDocument");
		xmlobject.async = "false";
		xmlobject.loadXML(xmlString);
		return xmlobject;
	}
	// for other browsers
	else {
		var xmlobject = (new DOMParser()).parseFromString(xmlString, "text/xml");
		return xmlobject;
	}
}

// convert xml object to string
function XML2String(xmlObject) {
	// for IE
	if (isIE()) {
		return xmlObject.xml;
	}
	// for other browsers
	else {
		return (new XMLSerializer()).serializeToString(xmlObject);
	}
}

function File2XML(xmlFile) {
	var xmlDoc;
	try // Internet Explorer
	{
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = false;
		xmlDoc.load(xmlFile);
	} catch (e) {
		try // Firefox, Mozilla, Opera, etc.
		{
			xmlDoc = document.implementation.createDocument("", "", null);
			xmlDoc.async = false;
			xmlDoc.load(xmlFile);
		} catch (e) {
			try // Google Chrome
			{
				var xmlhttp = new window.XMLHttpRequest();
				xmlhttp.open("GET", xmlFile, false);
				xmlhttp.send(null);
				xmlDoc = xmlhttp.responseXML.documentElement;
			} catch (e) {
				console.log(e);
				return null;
			}
		}
	}
	return xmlDoc;
}

//根据指定的XPATH表达式查找满足条件的所有节点
//@param xmldoc 执行查找的节点
//@param sXpath xpath的表达式
function selectNodes(xmldoc, sXpath) {
	if (isIE()) {
		// IE浏览器
		return xmldoc.selectNodes(sXpath);
	} else if (window.XPathEvaluator) {
		// FireFox类浏览器
		var xpathObj = new XPathEvaluator();

		if (xpathObj) {
			var result = xpathObj.evaluate(sXpath, xmldoc, null,
					XPathResult.ORDERED_NODE_ITEARTOR_TYPE, null);
			var nodes = new Array();
			var node;
			while ((node = result.iterateNext()) != null) {
				nodes.push(node);
			}
			return nodes;

		} else {

			return null;
		}

	} else {
		return null;
	}
}

//根据指定的XPATH表达式查找满足条件的第一个节点
//@param xmldoc 执行查找的节点
//@param sXpath xpath的表达式
function selectSingleNode(xmldoc, sXpath) {
	if (isIE()) {
		// IE浏览器
		return xmldoc.selectSingleNode(sXpath);
	} else if (window.XPathEvaluator) {
		// FireFox类浏览器
		var xpathObj = new XPathEvaluator();
		if (xpathObj) {
			var result = xpathObj.evaluate(sXpath, xmldoc, null,
					XPathResult.ORDERED_NODE_ITEARTOR_TYPE, null);
			return result.singleNodeValue;
		} else {
			return null;
		}
	} else {
		return null;
	}
}

function isIE() { //ie?
	if (!!window.ActiveXObject || "ActiveXObject" in window)
        return true;
    else
    	return false;
}

function readXML(evt,xmlStr,activities){
	//console.log(xmlStr);
	var vmlXML = String2XML(xmlStr);
	if (vmlXML == null)
		return;
	_empty(evt);
	_readHead(evt, vmlXML);
	_readNodes(evt, vmlXML, activities);
	_readLines(vmlXML);
	//调整svg大小。
	if(_maxWidth > 900 || _maxHeight > 600) {
		svg.attr({
			width: _maxWidth+"px",
			height: _maxHeight+"px",
			viewBox: "0 0 "+_maxWidth+" "+_maxHeight
		});
	}
}

function _readLines(vmlXML) {
	var nodes = selectNodes(vmlXML, '/WorkFlow/Lines/line');
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		var BaseProperties = selectNodes(node, 'BaseProperties')[0];
		var lineID = BaseProperties.getAttribute('id');
		var lineName = BaseProperties.getAttribute('lineName');
		var lineType = BaseProperties.getAttribute('lineType');
		var from = BaseProperties.getAttribute('from');
		var to = BaseProperties.getAttribute('to');
		var VMLProperties = selectNodes(node, 'VMLProperties')[0];
		var startArrow = VMLProperties.getAttribute('startArrow');
		var endArrow = VMLProperties.getAttribute('endArrow');
		var FlowProperties = selectNodes(node, 'FlowProperties')[0];
		var flowType = FlowProperties.getAttribute('flowType');
		var conditon = FlowProperties.getAttribute('conditon');
		var conditionType = FlowProperties.getAttribute('conditionType');
		var condDesc = FlowProperties.getAttribute('condDesc');

//		 console.log("lineID:" + lineID + ",lineName:" + lineName + ",lineType:"
//				+ lineType + ",from:" + from + ",to:" + to + ",startArrow:"
//				+ startArrow + ",endArrow:" + endArrow + ",flowType:"
//				+ flowType + ",conditon:" + conditon + ",conditionType:"
//				+ conditionType + ",condDesc:" + condDesc);

		if (lineID.indexOf("begin") != -1)
			lineID = lineID.replace("begin", "start")
		if (from === "begin")
			from = "start";
		
		if(from != 'start' && !from.startWith("n")) {
			from = "n" + from;
		}
		if(to != 'end' && !to.startWith("n")) {
			to = "n" + to;
		}
		var startNode = svg.select("#"+from);
		var endNode  = svg.select("#"+to);
		if(startNode == null) continue;
		if(endNode == null) continue;
		
		if(lineID.indexOf("start") == -1 && !lineID.startWith("l")) {
			lineID = "l" + lineID;
		}
		_loadLink(startNode, endNode, lineID, lineName, conditon,conditionType,condDesc);
	}
}

function _readNodes(evt, vmlXML, allStatus) {
	var nodes = selectNodes(vmlXML, '/WorkFlow/Nodes/node');
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		var BaseProperties = selectNodes(node, 'BaseProperties')[0];
		var nodeID = BaseProperties.getAttribute('id');
		var nodeName = BaseProperties.getAttribute('nodeName');
		var nodeType = BaseProperties.getAttribute('nodeType');
		var VMLProperties = selectNodes(node, 'VMLProperties')[0];
		var width = VMLProperties.getAttribute('width');
		var height = VMLProperties.getAttribute('height');
		var x = VMLProperties.getAttribute('x');
		var y = VMLProperties.getAttribute('y');
		var FlowProperties = selectNodes(node, 'FlowProperties')[0];
		var flowType = FlowProperties.getAttribute('flowType');
		var timeType = FlowProperties.getAttribute('timeType');
		var time = FlowProperties.getAttribute('time');

		// console.log("nodeID:" + nodeID + ",nodeName:" + nodeName +
		// ",nodeType:" + nodeType + ",width:" + width + ",height:" + height +
		// ",x:" + x + ",y:" + y + ",flowType:" + flowType + ",timeType:" +
		// timeType + ",time:" + time);

		if (nodeID === "begin")
			nodeID = "start";
		if (flowType === "begin")
			flowType = "start";
		if (x.indexOf("px") > 0)
			x = x.substring(0, x.indexOf("px"));
		if (y.indexOf("px") > 0)
			y = y.substring(0, y.indexOf("px"));
		x = Number(x);
		y = Number(y);
		if(y > _maxHeight - 75) { //75为50节点高度和25的调整量
			_maxHeight = y+ 75;
		}
		if(x > _maxWidth - 150) { //100为100节点宽度和50的调整量
			_maxWidth = x+ 150;
		}
		
		//判断状态
		var status = _highlightNode(nodeID,allStatus);
		
		_loadNode(evt, flowType, x, y, nodeID, nodeName, timeType,time,status);
	}
}

function _highlightNode(nodeID,allStatus) {
	if(allStatus == null) {
	     return "0";
	}
	for(var i = 0; i< allStatus.length ; i++){
		if(allStatus[i][1] == nodeID)
			return allStatus[i][0];
	}
	return "0"; 
}

function _readHead(evt,vmlXML) {
	var BaseProperties = selectNodes(vmlXML,
			'/WorkFlow/FlowConfig/BaseProperties')[0];
	var flowID = BaseProperties.getAttribute('flowId');
	var version = BaseProperties.getAttribute('version');
	var flowName = BaseProperties.getAttribute('flowName');
	var flowType = BaseProperties.getAttribute('flowType');

	// console.log("flowId:" + flowID + ",version:" + version + ",flowName:" +
	// flowName + ",flowType:" + flowType);

	_loadProcess(evt,flowID,version,flowName,flowType)

	// 下面属性暂不处理
	/**
	 * var BaseProperties = selectNodes(vmlXML,
	 * '/WorkFlow/FlowConfig/VMLProperties')[0]; var nodeTextColor =
	 * BaseProperties.getAttribute('nodeTextColor'); var nodeFillColor =
	 * BaseProperties.getAttribute('nodeFillColor'); var nodeStrokeColor =
	 * BaseProperties.getAttribute('nodeStrokeColor'); var nodeShadowColor =
	 * BaseProperties.getAttribute('nodeShadowColor'); var nodeFocusedColor =
	 * BaseProperties.getAttribute('nodeFocusedColor'); var nodeStrokeWeight =
	 * BaseProperties.getAttribute('nodeStrokeWeight'); var isNodeShadow =
	 * BaseProperties.getAttribute('isNodeShadow'); var lineStrokeColor =
	 * BaseProperties.getAttribute('lineStrokeColor'); var lineFocusedColor =
	 * BaseProperties.getAttribute('lineFocusedColor'); var lineStrokeWeight =
	 * BaseProperties.getAttribute('lineStrokeWeight'); var fontColor =
	 * BaseProperties.getAttribute('fontColor'); var fontSize =
	 * BaseProperties.getAttribute('fontSize'); var fontFace =
	 * BaseProperties.getAttribute('fontFace'); var scale =
	 * BaseProperties.getAttribute('scale');
	 * 
	 * console.log("nodeTextColor:" + nodeTextColor + ",nodeFillColor:" +
	 * nodeFillColor + ",nodeStrokeColor:" + nodeStrokeColor +
	 * ",nodeShadowColor:" + nodeShadowColor + ",nodeFocusedColor:" +
	 * nodeFocusedColor + ",nodeStrokeWeight:" + nodeStrokeWeight +
	 * ",isNodeShadow:" + isNodeShadow + ",lineStrokeColor:" + lineStrokeColor +
	 * ",lineFocusedColor:" + lineFocusedColor + ",lineStrokeWeight:" +
	 * lineStrokeWeight + ",fontColor:" + fontColor + ",fontSize:" + fontSize +
	 * ",fontFace:" + fontFace + ",scale:" + scale);
	 */

}

function _saveWF(evt) {
	var vmlXML = createXML();
	if (vmlXML == null)
		return null;
	var WorkFlow = vmlXML.createElement("WorkFlow");
	_createXMLHead(WorkFlow, vmlXML);
	_createXMLNodes(WorkFlow, vmlXML);
	_createXMLLines(WorkFlow, vmlXML);
	vmlXML.appendChild(WorkFlow);
	return XML2String(vmlXML);
}

function _createXMLHead(parent, xmlDoc) {

	var id = SVGRoot.id;
	var ver = SVGRoot.getAttribute("version");
	var name = SVGRoot.getAttribute("flowName");
	var type = SVGRoot.getAttribute("flowType");

	// FlowConfig
	var FlowConfig = xmlDoc.createElement("FlowConfig");
	// BaseProperties
	var BaseProperties = xmlDoc.createElement("BaseProperties");
	// flowId
	var flowId = xmlDoc.createAttribute("flowId");
	flowId.nodeValue = id;
	BaseProperties.setAttributeNode(flowId);
	// version
	var version = xmlDoc.createAttribute("version");
	version.nodeValue = ver;
	BaseProperties.setAttributeNode(version);
	// flowName
	var flowName = xmlDoc.createAttribute("flowName");
	flowName.nodeValue = name;
	BaseProperties.setAttributeNode(flowName);
	// flowType
	var flowType = xmlDoc.createAttribute("flowType");
	flowType.nodeValue = type;
	BaseProperties.setAttributeNode(flowType);

	FlowConfig.appendChild(BaseProperties);
	// VMLProperties
	var VMLProperties = xmlDoc.createElement("VMLProperties");
	// nodeTextColor="black"
	var nodeTextColor = xmlDoc.createAttribute("nodeTextColor");
	nodeTextColor.nodeValue = "black";
	VMLProperties.setAttributeNode(nodeTextColor);
	// nodeFillColor="#EEEEEE"
	var nodeFillColor = xmlDoc.createAttribute("nodeFillColor");
	nodeFillColor.nodeValue = "#EEEEEE";
	VMLProperties.setAttributeNode(nodeFillColor);
	// nodeStrokeColor="#333333"
	var nodeStrokeColor = xmlDoc.createAttribute("nodeStrokeColor");
	nodeStrokeColor.nodeValue = "#333333";
	VMLProperties.setAttributeNode(nodeStrokeColor);
	// nodeShadowColor="black"
	var nodeShadowColor = xmlDoc.createAttribute("nodeShadowColor");
	nodeShadowColor.nodeValue = "black";
	VMLProperties.setAttributeNode(nodeShadowColor);
	// nodeFocusedColor="#8E83F5"
	var nodeFocusedColor = xmlDoc.createAttribute("nodeFocusedColor");
	nodeFocusedColor.nodeValue = "#8E83F5";
	VMLProperties.setAttributeNode(nodeFocusedColor);
	// nodeStrokeWeight="1pt"
	var nodeStrokeWeight = xmlDoc.createAttribute("nodeStrokeWeight");
	nodeStrokeWeight.nodeValue = "1pt";
	VMLProperties.setAttributeNode(nodeStrokeWeight);
	// isNodeShadow="T"
	var isNodeShadow = xmlDoc.createAttribute("isNodeShadow");
	isNodeShadow.nodeValue = "T";
	VMLProperties.setAttributeNode(isNodeShadow);
	// lineStrokeColor="#000000"
	var lineStrokeColor = xmlDoc.createAttribute("lineStrokeColor");
	lineStrokeColor.nodeValue = "#000000";
	VMLProperties.setAttributeNode(lineStrokeColor);
	// lineFocusedColor="#FF0000"
	var lineFocusedColor = xmlDoc.createAttribute("lineFocusedColor");
	lineFocusedColor.nodeValue = "#FF0000";
	VMLProperties.setAttributeNode(lineFocusedColor);
	// lineStrokeWeight="1pt"
	var lineStrokeWeight = xmlDoc.createAttribute("lineStrokeWeight");
	lineStrokeWeight.nodeValue = "1px";
	VMLProperties.setAttributeNode(lineStrokeWeight);
	// fontColor="black"
	var fontColor = xmlDoc.createAttribute("fontColor");
	fontColor.nodeValue = "black";
	VMLProperties.setAttributeNode(fontColor);
	// fontSize="10pt"
	var fontSize = xmlDoc.createAttribute("fontSize");
	fontSize.nodeValue = "10pt";
	VMLProperties.setAttributeNode(fontSize);
	// fontFace="宋体"
	var fontFace = xmlDoc.createAttribute("fontFace");
	fontFace.nodeValue = "宋体";
	VMLProperties.setAttributeNode(fontFace);
	// scale="1"
	var scale = xmlDoc.createAttribute("scale");
	scale.nodeValue = "1";
	VMLProperties.setAttributeNode(scale);

	FlowConfig.appendChild(VMLProperties);

	// FlowProperties
	var FlowProperties = xmlDoc.createElement("FlowProperties");
	// timeId
	var timeId = xmlDoc.createAttribute("timeId");
	timeId.nodeValue = "";
	FlowProperties.setAttributeNode(timeId);

	FlowConfig.appendChild(FlowProperties);

	parent.appendChild(FlowConfig);
}

function _createXMLNode(xmlDoc, id, name, type, width, height, x, y, flowType,
		timeType, time) {
	if(id == null) id = "";
	if(name == null) name = "";
	if(type == null) type = "";
	if(width == null) width = "";
	if(height == null) height = "";
	if(x == null) x = "";
	if(y == null) y = "";
	if(flowType == null) flowType = "";
	if(timeType == null) timeType = "";
	if(time == null) time = "";
	// node
	var node = xmlDoc.createElement("node");
	// BaseProperties
	var BaseProperties = xmlDoc.createElement("BaseProperties");
	// id
	var nodeId = xmlDoc.createAttribute("id");
	nodeId.nodeValue = id;
	BaseProperties.setAttributeNode(nodeId);
	// nodeName
	var nodeName = xmlDoc.createAttribute("nodeName");
	nodeName.nodeValue = name;
	BaseProperties.setAttributeNode(nodeName);
	// nodeType
	var nodeType = xmlDoc.createAttribute("nodeType");
	nodeType.nodeValue = type;
	BaseProperties.setAttributeNode(nodeType);

	node.appendChild(BaseProperties);
	// VMLProperties
	var VMLProperties = xmlDoc.createElement("VMLProperties");
	// width
	var nodeWidth = xmlDoc.createAttribute("width");
	nodeWidth.nodeValue = width;
	VMLProperties.setAttributeNode(nodeWidth);
	// height
	var nodeHeight = xmlDoc.createAttribute("height");
	nodeHeight.nodeValue = height;
	VMLProperties.setAttributeNode(nodeHeight);
	// x
	var nodeX = xmlDoc.createAttribute("x");
	nodeX.nodeValue = x;
	VMLProperties.setAttributeNode(nodeX);
	// y
	var nodeY = xmlDoc.createAttribute("y");
	nodeY.nodeValue = y;
	VMLProperties.setAttributeNode(nodeY);

	node.appendChild(VMLProperties);

	// FlowProperties
	var FlowProperties = xmlDoc.createElement("FlowProperties");
	// flowType
	var nodeFlowType = xmlDoc.createAttribute("flowType");
	nodeFlowType.nodeValue = flowType;
	FlowProperties.setAttributeNode(nodeFlowType);

	// timeType
	if (timeType != null) {
		var nodeTimeType = xmlDoc.createAttribute("timeType");
		nodeTimeType.nodeValue = timeType;
		FlowProperties.setAttributeNode(nodeTimeType);
	}
	// time
	if (time != null) {
		var nodeTime = xmlDoc.createAttribute("time");
		nodeTime.nodeValue = time;
		FlowProperties.setAttributeNode(nodeTime);
	}

	node.appendChild(FlowProperties);
	return node;
}

function _createXMLNodeSp(xmlDoc,Nodes, ellipse) {

	var nodeID = ellipse.id;
	if (nodeID === "start")
		nodeID = "begin";

	var name = ellipse.getAttribute("name");
	var nodeType = "oval";
	var width = "50px", height = "40px";
	var x = ""
			+ (Number(ellipse.getAttribute("cx")) - Number(ellipse
					.getAttribute("rx"))) + "px";
	var y = ""
			+ (Number(ellipse.getAttribute("cy")) - Number(ellipse
					.getAttribute("ry"))) + "px";

	var flowType = nodeID;

	var node = _createXMLNode(xmlDoc, nodeID, name, nodeType, width, height, x, y,
			flowType, null, null);
	Nodes.appendChild(node);
}
function _createXMLNodes(parent, xmlDoc) {
	// FlowConfig
	var Nodes = xmlDoc.createElement("Nodes");

	var startNode = SVGDocument.getElementById("start");
	if (startNode) {
		_createXMLNodeSp(xmlDoc,Nodes, startNode);
	}
	var rects = SVGDocument.getElementsByTagName("rect");
	for (var i = 0; i < rects.length; i++) {
		var rect = rects[i];
		var nodeID = rect.id;
		if (nodeID == "BackDrop" || nodeID == "menu1" || nodeID == "menu2")
			continue;
		var name = rect.getAttribute("name");
		var nodeType = "RoundRect";
		var width = "100px", height = "50px";
		var x = rect.getAttribute("x") + "px";
		var y = rect.getAttribute("y") + "px";
		var flowType = "course";
		var timeType = rect.getAttribute("timeType");
		var time = rect.getAttribute("time") ;
		
		if(nodeID.startWith("n"))
			nodeID = nodeID.substring(1);

		var node = _createXMLNode(xmlDoc, nodeID, name, nodeType, width, height, x,
				y, flowType, timeType, time)
		Nodes.appendChild(node);
	}
	var endNode = SVGDocument.getElementById("end");
	if (endNode) {
		_createXMLNodeSp(xmlDoc,Nodes, endNode);
	}
	parent.appendChild(Nodes);
}

function _createXMLLine(xmlDoc, id, name, from, to, conditon,conditionType,condDesc) {
	if(id == null) id = "";
	if(name == null) name = "";
	if(from == null) from = "";
	if(to == null) to = "";
	if(conditon == null) conditon = "";
	if(conditionType == null) conditionType = "";
	if(condDesc == null) condDesc = "";
	
	// node
	var line = xmlDoc.createElement("line");
	// BaseProperties
	var BaseProperties = xmlDoc.createElement("BaseProperties");
	// id
	var lineId = xmlDoc.createAttribute("id");
	lineId.nodeValue = id;
	BaseProperties.setAttributeNode(lineId);
	// lineName
	var lineName = xmlDoc.createAttribute("lineName");
	lineName.nodeValue = name;
	BaseProperties.setAttributeNode(lineName);
	// lineType
	var lineType = xmlDoc.createAttribute("lineType");
	lineType.nodeValue = "PolyLine";
	BaseProperties.setAttributeNode(lineType);
	// from
	var lineFrom = xmlDoc.createAttribute("from");
	lineFrom.nodeValue = from;
	BaseProperties.setAttributeNode(lineFrom);
	// to
	var lineTo = xmlDoc.createAttribute("to");
	lineTo.nodeValue = to;
	BaseProperties.setAttributeNode(lineTo);

	line.appendChild(BaseProperties);

	// VMLProperties
	var VMLProperties = xmlDoc.createElement("VMLProperties");
	// startArrow
	var startArrow = xmlDoc.createAttribute("startArrow");
	startArrow.nodeValue = "diamond";
	VMLProperties.setAttributeNode(startArrow);
	// endArrow
	var endArrow = xmlDoc.createAttribute("endArrow");
	endArrow.nodeValue = "classic";
	VMLProperties.setAttributeNode(endArrow);

	line.appendChild(VMLProperties);

	// FlowProperties
	var FlowProperties = xmlDoc.createElement("FlowProperties");
	// flowType
	var lineFlowType = xmlDoc.createAttribute("flowType");
	lineFlowType.nodeValue = "connect";
	FlowProperties.setAttributeNode(lineFlowType);
	// conditon
	var lineConditon = xmlDoc.createAttribute("conditon");
	lineConditon.nodeValue = conditon;
	FlowProperties.setAttributeNode(lineConditon);
	// conditon
	var lineConditionType = xmlDoc.createAttribute("conditionType");
	lineConditionType.nodeValue = conditionType;
	FlowProperties.setAttributeNode(lineConditionType);
	// conditon
	var lineCondDesc = xmlDoc.createAttribute("condDesc");
	lineCondDesc.nodeValue = condDesc;
	FlowProperties.setAttributeNode(lineCondDesc);

	line.appendChild(FlowProperties);
	return line;
}
function _createXMLLines(parent, xmlDoc) {
	var Lines = xmlDoc.createElement("Lines");
	var polylines = SVGDocument.getElementsByTagName("polyline");
	for (var i = 0; i < polylines.length; i++) {
		var polyline = polylines[i];
		var lineID = polyline.id;
		var name = polyline.getAttribute("name");
		var lineType = "PolyLine";
		var from = polyline.getAttribute("start-id");
		var to = polyline.getAttribute("end-id");
		var startArrow = "diamond", endArrow = "classic";
		var flowType = "connect";
		var conditon = polyline.getAttribute("condition");
		var conditionType = polyline.getAttribute("conditionType");
		var condDesc = polyline.getAttribute("condDesc");
		if (lineID.indexOf("start") > -1)
			lineID = lineID.replace(/start/, "begin")
		if (from === "start")
			from = "begin";
		if(lineID.startWith("l"))
			lineID = lineID.substring(1);
		if(from.startWith("n"))
			from = from.substring(1);
		if(to.startWith("n"))
			to = to.substring(1);
		var line = _createXMLLine(xmlDoc, lineID, name, from, to, conditon,conditionType,condDesc)
		Lines.appendChild(line);
	}
	parent.appendChild(Lines);
}