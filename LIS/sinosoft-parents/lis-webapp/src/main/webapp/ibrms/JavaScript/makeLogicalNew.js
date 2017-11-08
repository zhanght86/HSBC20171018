var tResourceName_Logic = 'ibrms.makeLogicalNewSql';
var rowCount = 0; // 定制逻辑中行的数量
var isMenuUnfolded = false; // "如果"是否在被点击之后展开下拉菜单
var preEventNode = null; // 触发产生下拉框事件的节点
var spanNodeEnabled = true;// 节点能否响应鼠标点击事件的标志

var InputNodeEnabled = false;
// 用于标识相应节点所要携带的数据结构
var BOMIndex = new Array('id', 'EnName', 'ChName','KeyValue','VirtualFlag');
var BOMItemIndex = new Array('id', 'EnName', 'ChName', 'MatchType', 'Source',
		'isHierarchical','KeyValue','VirtualFlag');
		
var FunctionBOMIndex = new Array('id', 'EnName', 'ChName','ParaType','FunctionName','KeyValue');
var FunctionBOMItemIndex = new Array('id', 'EnName', 'ChName', 'MatchType', 'Source',
		'isHierarchical','ParaType','FunctionName','KeyValue');
var CommaIndex = new Array('id', 'EnName', 'ChName','KeyValue');		
//tongmeng 2011-02-07 add
//增加运算符的参数数量
var OperatorIndex = new Array('id', 'EnName', 'ChName', 'MatchType',
		'ResultType', 'ParaType', 'IsNextOperator','KeyValue');
//tongmeng 2011-04-14 add
		
var AddFunctionIndex = new Array('id', 'EnName', 'ChName', 'MatchType',
		'ResultType', 'ParaType', 'IsNextOperator','CommType','CommDetail','FunctionName','KeyValue');
				
var LinkIndex = new Array('id', 'EnName', 'ChName','KeyValue');
var ParenIndex = new Array('id', 'EnName', 'ChName');
var InputIndex = new Array('id', 'EnName', 'ChName','KeyValue');
// //连接型运算符数组
var LinkArray = new Array();
LinkArray[0] = new Array("and", "并且","LINK_AND");
LinkArray[1] = new Array("or", "或者","LINK_OR");
// 表示"如果"后面是否有规则
var hasRule = false;
// 后台查询缓存
var queryCache = new Array();

var DTColumnName='Column';
var reCurr=0;
var SQLStatement;// 存储规则的SQL
var CreateTable;// 存储建表语句的SQL
var ViewPara;// 存储规则反显示所需要的参数
var BOMSArray = new Array();// 存储规则所用的BOM
var SQLParaArray = new Array();// 存储规则反解析的参数
var RuleDesInCh;// 存储规则的中文名
var ColumnHeadArray = new Array();// 存储决策表列头所要显示的中文名
var ColumnMetasChArray = new Array();// 存储单列规则中文的的数组
var ColumnDataTypeArray = new Array();// 存储决策表列的数据类型
var TableColumnNameArray = new Array();// 存储DT表中的列明，在决策表中用于列的Index
var ColumnMultipleArray = new Array();// 存储决策表所的运算符是否为“其中之一”
var BaseColumnArray = new Array();// 存储决策表的列是否为基础词条
var MetaNodeChNameArray = new Array();// 存储扫描到节点的中文名
var spanNodeArray = new Array();// 存储规则拼写中扫描到的节点
var InputNodeArray = new Array();// 存储规则定制逻辑中Input节点
var BaseBOMItemSourceArray = new Array();
var InputNodes = new Array();
var DTColumnRecurArray = new Array();// 存储建表过程中列名重复的次数


//----------------------------------------------------------------------------//
//tongmeng 2010-12-14 add
var spanCalNodeEnabled = true;// 节点能否响应鼠标点击事件的标志
var isCalMenuUnfolded = false; // "计算"是否在被点击之后展开下拉菜单
var hasCalRule = false;
var rowCalCount = 0; // 定制算法中行的数量
var spanCalNodeArray = new Array();// 存储算法拼写中扫描到的节点
var CalColumnMetasChArray = new Array();// 存储单列规则中文的的数组
var MetaCalNodeChNameArray = new Array();// 存储扫描到节点的中文名
var CalSQLStatement="";// 存储算法的SQL
var CalRuleDesInCh;// 存储规则的中文名

//OTHERRULE
var OTHERRULEIndex = new Array('id', 'EnName', 'ChName');


//指定表
var mRuleTableFlag = false;
var mRuleTableName = "";
//----------------------------------------------------------------------------//

function getEvent(){
	return  window.event || arguments.callee.caller.arguments[0];
}

function getSrcElemnt(e){
	return e.srcElement ? e.srcElement : e.target;
}

function leftButton() {
	var bi = BrowserInfo();
	if(bi.name == "IE" && bi.version < 9) {
		return 1;
	}
	else {
		return 0;
	}
}

function hightLight() {
	// 节点在数据输入时不响应操作
	if (!spanNodeEnabled) {
		return;
	}
	// 节点字体变成斜体，鼠标变成手型，字体闪烁。
	else {
		var ob = getSrcElemnt(getEvent());
		ob.style.fontStyle = "italic";
		ob.style.cursor = 'hand';
		ob.style.textDecoration = 'blink';
	}
}

function normalLight() {
	// 节点在数据输入时不响应操作
	if (!spanNodeEnabled) {
		return;
	}
	// 节点字体变成正常体，鼠标变成默认型，不闪烁。
	else {
		var ob = getSrcElemnt(getEvent());
		ob.style.fontStyle = "normal";
		ob.style.cursor = 'default';
		ob.style.textDecoration = '';
	}
}

function showOrHideMenu() {
	if (spanNodeEnabled) {
		if (!isMenuUnfolded)// 菜单未打开
		{
			unFoldMenu(); // 展开菜单
			isMenuUnfolded = true;// 设置记录标识为打开状态
		} else {
			foldMenu(); // 折叠菜单
			isMenuUnfolded = false;// 设置记录标识为折叠状态
		}
	}
}

function unFoldMenu() {
	// 创建“DIV”节点、“新条件”节点、”增加条件“节点

	var nextNode = document.getElementById("conditions").nextSibling;

	if (hasRule) {
		nextNode.style.display = '';
	} else {
		var divNode = document.createElement("div");
		divNode.setAttribute("id", "RuleZone");
		var newLine = createNewAditionLine("0");
		var buttonNode = createAddLineButton("0");
		// 获取当前节点的父节点以及下一个兄弟节点
		var pareNode = getSrcElemnt(getEvent()).parentNode;
		var nextNode = getSrcElemnt(getEvent()).nextSibling;
		// 将“条件”节点以及“增加条件”节点插入到“form”节点之后

		divNode.appendChild(newLine);
		divNode.appendChild(buttonNode);
		// 将form节点插入到当前节点之后
		pareNode.insertBefore(divNode, nextNode);
		hasRule = true;
	}
}

// 折叠菜单函数
function foldMenu() {
	// 获取当前节点的父节点和下一个兄弟节点
	var nextNode = getSrcElemnt(getEvent()).nextSibling;
	nextNode.style.display = 'none';
}

// 创建新条件函数
function createNewAditionLine(tType) {
	var divNode = document.createElement("div");// 创建一个"div"节点(一个条件就在一行中,以"div"标识)
	// 当条件不是第一个条件的时候，添加连接词
	if(tType=='0')
	{
	
		if (rowCount != 0) {
			var linkNode = createOriginSpanNode('Link', false);
			divNode.appendChild(linkNode);
		} else {
			var spanNode = createOriginSpanNode('Spacer');
			divNode.appendChild(spanNode);
			spanNode = createOriginSpanNode('Spacer');
			divNode.appendChild(spanNode);
			spanNode = createOriginSpanNode('Spacer');
			divNode.appendChild(spanNode);
		}
		// 创建非库中节点
		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);
		var spanNode = createOriginSpanNode('BOM', false);
		divNode.appendChild(spanNode);

		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);

		var buttonNode = createDeleteButtonNode(tType); // 创建一个可以删除本条件的按钮节点
		divNode.appendChild(buttonNode);
		rowCount++;
	}
	else
	{
		//if (rowCalCount != 0) {
			//tongmeng 2011-01-21 modify
			//针对算法,不添加连接词 
			//var linkNode = createOriginSpanNode('Link', false);
			//divNode.appendChild(linkNode);
		//} else 
		{
			var spanNode = createOriginSpanNode('Spacer');
			divNode.appendChild(spanNode);
			spanNode = createOriginSpanNode('Spacer');
			divNode.appendChild(spanNode);
			spanNode = createOriginSpanNode('Spacer');
			divNode.appendChild(spanNode);
		}
		// 创建非库中节点
		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);
		var spanNode = createOriginSpanNode('BOM', false);
		divNode.appendChild(spanNode);

		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);

		var buttonNode = createDeleteButtonNode(tType); // 创建一个可以删除本条件的按钮节点
		divNode.appendChild(buttonNode);
		rowCalCount++;
	}
	
	return divNode;
}
// 创建一个删除本条件的按钮
function createDeleteButtonNode(tType) {
	var buttonNode = document.createElement("input");
	// 添加Button节点的鼠标响应事件
	buttonNode.setAttribute("type", "button");
	buttonNode.setAttribute("id", "DelButton");
	buttonNode.onclick = function() {
		deleteLine(tType);
	}
	buttonNode.style.background = 'url(./resources/img/delete.jpg)';
	buttonNode.style.width = '35';
	buttonNode.style.height = '32';
	return buttonNode;
}

function deleteLine(tType) {
	if(tType=='0')
	{
	
		if (!spanNodeEnabled) {
			return;
		}
		var fatherNode = getSrcElemnt(getEvent()).parentNode;

		var uncleNode = fatherNode.nextSibling;
		var grandNode = fatherNode.parentNode;
		destroyMenu();

		grandNode.removeChild(fatherNode);
		rowCount--;
		if (rowCount > 0) {
			var firstLine = grandNode.firstChild;
			var linkNode = firstLine.firstChild;
			if (linkNode.id == 'Link') {
				var spanNode = createOriginSpanNode("Spacer");
				firstLine.replaceChild(spanNode, linkNode);
			}
		}
	}
	else
	{
		if (!spanCalNodeEnabled) {
			return;
		}
		var fatherNode = getSrcElemnt(getEvent()).parentNode;

		var uncleNode = fatherNode.nextSibling;
		var grandNode = fatherNode.parentNode;
		destroyMenu();

		grandNode.removeChild(fatherNode);
		rowCalCount--;
		if (rowCalCount > 0) {
			var firstLine = grandNode.firstChild;
			var linkNode = firstLine.firstChild;
			if (linkNode.id == 'Link') {
				var spanNode = createOriginSpanNode("Spacer");
				firstLine.replaceChild(spanNode, linkNode);
			}
		}
	}

}
// 创建一个能够增加一个新条件的按钮
function createAddLineButton(tType) {
	var buttonNode = document.createElement("input");
	// 添加Button节点的鼠标响应事件
	buttonNode.setAttribute("type", "button");
	buttonNode.setAttribute("id", "AddButton");
	buttonNode.onclick = function() {
		addNewLine(tType);
	}
	buttonNode.style.background = 'url(./resources/img/add.jpg)';
	buttonNode.style.width = '35';
	buttonNode.style.height = '32';
	return buttonNode;
}

// 增加一个新条件
function addNewLine(tType) {
	if(tType=='0')
	{
		if (!spanNodeEnabled) {
			return;
		}
		var divNode = createNewAditionLine(tType);
		var ob = getSrcElemnt(getEvent());
		var fatherNode = ob.parentNode;
		fatherNode.insertBefore(divNode, ob);
	}
	else
	{
		if (!spanCalNodeEnabled) {
			return;
		}
		var divNode = createNewAditionLine(tType);
		var ob = getSrcElemnt(getEvent());
		var fatherNode = ob.parentNode;
		fatherNode.insertBefore(divNode, ob);
	}
}
function disableInputNodes() {
	InputNodeEnabled = false;
}

function disableSpanNodes() {
	spanNodeEnabled = false;// 节点能否响应鼠标点击事件的标志

}

function enableInputNodes() {
	InputNodeEnabled = true;
}

function enableSpanNodes() {
	spanNodeEnabled = true;// 节点能否响应鼠标点击事件的标志

}
/*
 * 创建Span节点的函数是本组件的重点 Span节点的数据结构如下： id:
 * 用于标识节点的类型，例如BOM，BOMItem，Operator，Pathesis，Input，Link EnName：用于记录节点的英文名
 * ChName: 用于记录节点的中文名 MatchType: 用于记录运算符与词条的匹配类型， ParaType: 用于记录运算符后携带的参数类型，
 * Source：用于记录基础词条的取值来源， ResultType：用于记录运算符的运算结果类型
 * IsNextOperator：用于记录运算符后面能否接运算符的标志 isMenu是节点是否为下拉菜单中节点的标志，true表示“是”，false表示“否”
 */
function createSpanNode(paraArray, isMenu) {

	var spanNode = document.createElement("span");
	//spanNode.style.float = "left";

	var nodeType = '';
	var displayText = '';

	// 添加"spanNode"节点的属性,比如鼠标响应事件
	if (!(!!paraArray)) {
		alert('创建节点传递的参数出错！');
		return;
	}
	for ( var i = 0, length = paraArray.length; i < length; i++) {
		spanNode.setAttribute(paraArray[i].attribute, paraArray[i].value);
		if (paraArray[i].attribute == 'id')
			nodeType = paraArray[i].value;
		if (paraArray[i].attribute == 'ChName') {
			displayText = paraArray[i].value;
		}
	}
	
	//alert("displayText:"+displayText);
	if (!isMenu) // 判断
	{
		// 如果是
		if (nodeType != 'Input' && nodeType != 'AddInput'
				&& nodeType != "Delete"  && nodeType !='Constant') {
			spanNode.onmousedown = function() {
				popMenu();
			}
		} else if (nodeType == 'Input') {
			spanNode.onmousedown = function() {
				changeToInput();
			}
			
			//tongmeng 2010-12-27 add
			//alert('nodeType:'+nodeType);
			} else if (nodeType == 'Constant') {
			spanNode.onmousedown = function() {
				changeToInputForConstant();
			}
			
		} else if (nodeType == 'AddInput') {
			spanNode.onclick = function() {
				addInput();
			}
		} else if (nodeType == "Delete") {
			spanNode.onclick = function() {
				deleteInput();
			}
		}
	} else {
		spanNode.onmousedown = function() {
			handleClick();
		}
	}
	// 创建并添加"Text"内容
	if (spanNode.id == "Spacer") {
		spanNode.innerHTML = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp';
	} else {
		spanNode.onmouseout = function() {
			normalLight();
		}
		spanNode.onmouseover = function() {
			hightLight();
		}
		spanNode.innerHTML = displayText;
	}
	return spanNode;
}

function addInput() {
	if (!InputNodeEnabled)
		return;
	var srcNode = getSrcElemnt(getEvent());
	var pareNode = srcNode.parentNode;

	if (pareNode && pareNode.id) {
		if (pareNode.id == "InputNodes") {
			var spanNode = createOriginSpanNode("comma");
			pareNode.insertBefore(spanNode, srcNode);
			spanNode = createOriginSpanNode("Input");
			pareNode.insertBefore(spanNode, srcNode);
		}
	}

}
function deleteInput() {
	var obNode = preEventNode;
	var pareNode = obNode.parentNode;
	if (pareNode && pareNode.id == "InputNodes") {
		var firstInput = pareNode.firstChild;

		while (firstInput.id != "Input") {
			firstInput = firstInput.nextSibling;
		}
		if (firstInput == obNode) {
			alert("不能删除第一个节点！");
			return;
		} else {
			pareNode.removeChild(obNode.previousSibling);
			pareNode.removeChild(obNode);
		}
	}
}
function popMenu() {
	console.log("popMenu");
	// 记录被点击的源节点
	preEventNode = getSrcElemnt(getEvent());
	var spanArray = new Array();
	var clickedNode = getSrcElemnt(getEvent());
	// 判断鼠标被点击的左右键
	var mouseButton = getEvent().button;
	// 判断被点击节点的类型
	var Id = clickedNode.id;
	if (!Id || Id == 'Spacer') {
		return;
	}
	// 处于数据输入状态，不响应鼠标事件
	if (!spanNodeEnabled) {

		if (Id == "Input" && mouseButton == 2) {

			var pareNode = preEventNode.parentNode;
			var firstInputNode = pareNode.firstChild;
			while (firstInputNode.id != "Input") {
				firstInputNode = firstInputNode.nextSibling;
			}
			if (firstInputNode == preEventNode) {
				return;
			}
			var spanNode = createOriginSpanNode("Delete");
			spanArray[spanArray.length] = spanNode;
		} else
			return;
	} else {
		if (mouseButton == leftButton())// 鼠标左击
		{
			//alert("#Id#"+Id);
			if (Id == 'BOM') {
				spanArray = prepareMenu_Left('BOM');
			} else if (Id == 'BOMItem')
				spanArray = prepareMenu_Left('BOMItem');
			else 	if (Id == 'FunctionBOM') {
				spanArray = prepareMenu_Left('FunctionBOM');
			} else if (Id == 'FunctionBOMItem')
				spanArray = prepareMenu_Left('FunctionBOMItem');
			else if (Id == 'Operator')
				spanArray = prepareMenu_Left('Operator');
			else if (Id == 'Link')
				spanArray = prepareMenu_Left('Link');
			else if (Id == 'OTHERBOM')
				spanArray = prepareMenu_Left('OTHERBOM');
			//tongmeng 2011-04-14 add
			//增加函数的处理
			else if (Id == 'AddFunction')
				spanArray = prepareMenu_Left('AddFunction');	
			else if (Id == 'OTHERRULE')
				{
					//alert('prepare');
					spanArray = prepareMenu_Left('OTHERRULE');
				}
			/*else if (Id == 'Constant'')
				{
					//alert('prepare');
					spanArray = prepareMenu_Left('Constant');
				}*/
		} else if (mouseButton == 2)// 鼠标右击
		{
			//alert('Id:'+Id);
			if (Id == 'BOM')
				spanArray = prepareMenu_Right('BOM');
			else if (Id == 'BOMItem')
				spanArray = prepareMenu_Right('BOMItem');
			else if (Id == 'FunctionBOM')
				spanArray = prepareMenu_Right('FunctionBOM');
			else if (Id == 'FunctionBOMItem')
				spanArray = prepareMenu_Right('FunctionBOMItem');
				
			else if (Id == 'OTHERRULE')
				spanArray = prepareMenu_Right('OTHERRULE');	
			else if (Id == 'Operator') {
				//alert('preEventNode.getAttribute("EnName"):'+preEventNode.getAttribute("EnName"));
				if (preEventNode.getAttribute("EnName") == "is null"
						|| preEventNode.getAttribute("EnName") == "is not null") {
					spanArray = prepareMenu_Right('Operator');
				}
				else
				{
					spanArray = prepareMenu_Right('DeleteOperator');
				}
			} else if (Id == 'Input')
				spanArray = prepareMenu_Right('Input');
			//tongmeng 2010-12-27 add	
			else if(Id == 'Constant')
				spanArray = prepareMenu_Right('Constant');
				//tongmeng 2011-04-14 add
				//增加对函数的支持
			else if(Id == 'AddFunction')
				spanArray = prepareMenu_Right('AddFunction');	
			else if (Id == 'Left_Paren')
				spanArray = prepareMenu_Right('Left_Paren');
			else if (Id == 'Right_Paren')
				spanArray = prepareMenu_Right('Right_Paren');
			else if (Id == 'Link')
				spanArray = prepareMenu_Right('Link');
			else if (Id == 'InputRightScope')
				spanArray = prepareMenu_Right('InputRightScope');
		}
	}
	displayMenu(spanArray);
	document.oncontextmenu = function() {
		if (getSrcElemnt(getEvent()) == preEventNode)
			return false;
	}
}

function displayMenu(spanArray) {
	if (spanArray == null || spanArray.length == 0) {
		return;
	}
	var position_x = preEventNode.offsetLeft;
	var position_y = preEventNode.offsetTop + preEventNode.offsetHeight;

	var disNode = document.getElementById("display");

	while (disNode.firstChild) {
		disNode.removeChild(disNode.firstChild);
	}

	for ( var i = 0; i < spanArray.length; i++) {

		disNode.appendChild(spanArray[i]);

		var brNode = document.createElement('br');
		disNode.appendChild(brNode);
	}
	disNode.style.left = position_x;
	disNode.style.top = position_y;
	disNode.style.zIndex = "10";
	disNode.style.display = "";
	
	document.onclick = function() {
		//alert("onclick");
		
		var srcNode = getSrcElemnt(getEvent());
		
		//var Id = srcNode.id;
		//alert("srcNode.id:"+srcNode.id);
	//if(srcNode.id=='AddFunction')
		//{
			
		//}
		if (srcNode != preEventNode && srcNode != disNode) {
			destroyMenu();
		}
	}
	//alert(disNode.outerHTML);
}

//tongmeng 2010-12-28 add
function changeToInputForConstant() {
	var DISPLAYINPUT = this;

// alert('changeToInputForConstant');
	preEventNode = getSrcElemnt(getEvent());
	var clickedNode = getSrcElemnt(getEvent());
	var mouseButton = getEvent().button;
	// 将其它Input节点转化成Span节点
	//convertInputsToSpans();
	var Id = clickedNode.id;
	if (mouseButton == leftButton()) {
		{
			var BOMItemNode = null;
			var OperatorNode = null;
			var currNode = clickedNode;
			//alert('1'+currNode.id);
			if(currNode.id!="Constant")
			{
				if (!!currNode.parentNode.id
						&& currNode.parentNode.id == "Constant") {
					currNode = currNode.parentNode;
				}
				
						while (!!currNode) {
				//alert('currNode.id:'+currNode.id)
				if (currNode.id == "BOMItem"||currNode.id == "FunctionBOMItem") {
					BOMItemNode = currNode;
					break;
				} else if (currNode.id == "Operator"
						&& currNode.getAttribute("ResultType") == "Boolean") {
					OperatorNode = currNode;
				}
				currNode = currNode.previousSibling;
			}

			if (!!BOMItemNode) {
				// 如果输入值是基础词条
				if (!!BOMItemNode.getAttribute("Source")) {
					var InputMenu = new Array();
					var strSQL = BOMItemNode.getAttribute("Source");
					InputMenu = getInputSource(strSQL);
					var spanArray = prepareBaseMenu(InputMenu);
					displayMenu(spanArray);
				}
				// 如果输入值不是基础词条
				else {
					//alert('22');
					//var dataType = OperatorNode.getAttribute("ParaType");
					displayInputForConstant(clickedNode, '');
					/*
					 * preEventNode.innerHTML=""; field =new
					 * Ext.form.DateField({renderTo:preEventNode , format:
					 * 'Y-m-d H:i:s',width:150 });
					 */
				}
			}
			
			}
			else
			{
				displayInputForConstant(clickedNode, '');
			}
	
		}
	} else {
		popMenu();
	}
}


function changeToInput() {
	var DISPLAYINPUT = this;

	preEventNode = getSrcElemnt(getEvent());
	var clickedNode = getSrcElemnt(getEvent());
	var mouseButton = getEvent().button;
	// 将其它Input节点转化成Span节点
	convertInputsToSpans();
	var Id = clickedNode.id;
	if (mouseButton == leftButton()) {
		if (!InputNodeEnabled)// '请输入一个值'节点在规则逻辑定制过程中不响应鼠标左击操作
		{
			return;
		} else // '请输入一个值'节点在数据录入过程中响应鼠标左击事件
		{
			var BOMItemNode = null;
			var OperatorNode = null;
			var currNode = clickedNode;
			if (!!currNode.parentNode.id
					&& currNode.parentNode.id == "InputNodes") {
				currNode = currNode.parentNode;
			}
			while (!!currNode) {
				if (currNode.id == "BOMItem") {
					BOMItemNode = currNode;
					break;
				} else if (currNode.id == "Operator"
						&& currNode.getAttribute("ResultType") == "Boolean") {
					OperatorNode = currNode;
				}
				currNode = currNode.previousSibling;
			}

			if (!!BOMItemNode) {
				// 如果输入值是基础词条
				if (!!BOMItemNode.getAttribute("Source")) {
					var InputMenu = new Array();
					var strSQL = BOMItemNode.getAttribute("Source");
					InputMenu = getInputSource(strSQL);
					var spanArray = prepareBaseMenu(InputMenu);
					displayMenu(spanArray);
				}
				// 如果输入值不是基础词条
				else {
					var dataType = OperatorNode.getAttribute("ParaType");
					displayInput(clickedNode, dataType);
					/*
					 * preEventNode.innerHTML=""; field =new
					 * Ext.form.DateField({renderTo:preEventNode , format:
					 * 'Y-m-d H:i:s',width:150 });
					 */
				}
			}
		}
	} else {
		popMenu();
	}
}
function displayInputForConstant(obNode, dataType) {

	var fatherNode = obNode.parentNode;

	var first = true;
	//alert('Constant id:'+obNode.id);
	var tFunctionName = obNode.getAttribute('FunctionName');
	var spanNode = document.createElement("span");
	spanNode.id = "Input";

	var inputNode = document.createElement('input');
	inputNode.setAttribute('type', 'input');
	inputNode.setAttribute('id', 'Input');
	inputNode.setAttribute('Editable', 'Yes');
	inputNode.setAttribute('size', '18');
	if(tFunctionName!=null&&tFunctionName!='')
	{
		inputNode.setAttribute('FunctionName', tFunctionName);
	}
	if (!!obNode.getAttribute("EnName")) {
		inputNode.value = obNode.getAttribute("EnName");
	}
	inputNode.onkeydown = function() {
		handleKey(this);
	}
	fatherNode.replaceChild(inputNode, obNode);
	inputNode.focus();
	inputNode.select();

	document.onclick = function() {
		convertInputsToSpans(inputNode);
	}
}


function displayInput(obNode, dataType) {

	var fatherNode = obNode.parentNode;

	var first = true;

	var spanNode = document.createElement("span");
	spanNode.id = "Input";

	var inputNode = document.createElement('input');
	inputNode.setAttribute('type', 'input');
	inputNode.setAttribute('id', 'Input');
	inputNode.setAttribute('Editable', 'Yes');
	inputNode.setAttribute('size', '18');

	if (!!obNode.getAttribute("EnName")) {
		inputNode.value = obNode.getAttribute("EnName");
	}
	inputNode.onkeydown = function() {
		handleKey(this);
	}
	fatherNode.replaceChild(inputNode, obNode);
	inputNode.focus();
	inputNode.select();

	document.onclick = function() {
		convertInputsToSpans();
	}
}
function handleKey(inputNode) {
	getInputNodes();
	var e = getEvent();
	var keyCode = e.which ? e.which : e.keyCode;
	if (keyCode == 13) {
		convertInputsToSpans();
	}
}
function getIndexOf(inputNode) {
	for ( var i = 0; i < InputNodes.length; i++) {
		if (InputNodes[i] == inputNode) {
			return i;
		}
	}
}
function convertInputsToSpans(spanNode) {
	var spanNode;
	var type;
	if(spanNode==undefined || spanNode==null)
	{
		getInputNodes();
		for ( var i = 0; i < InputNodes.length; i++) {
			spanNode = InputNodes[i];
			type = ColumnDataTypeArray[i];
			if (spanNode.id == "Input") {
				changInputToSpan(spanNode, type);
			} else {
				var childNode = spanNode.firstChild;
				while (childNode) {
					if (childNode.id == "Input", type) {
						changInputToSpan(childNode);
					}
					childNode = childNode.nextSibling;
			}	
			}
		}
	}
	else
	{
		changConstantoSpan(spanNode, 'String');
	}

}

function changConstantoSpan(inputNode, type) {
	   {
var tFunctionName = inputNode.getAttribute('FunctionName');
				//alert('$$$:'+tFunctionName);
			var numRegExp = /^(([1-9][0-9]*)|(0)|((([1-9][0-9]*)|[0-9])\.[0-9]+))$/;
			var dateRegExp = /^\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|31) (([01][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])$/
			var spanNode = null;
			if (inputNode.value != '') {
				if (type == "Number") {
					if (!numRegExp.test(inputNode.value)) {
						alert("请输入正确的数字格式！");
						return;
					}

				} else if (type == "Date") {
					if (!dateRegExp.test(inputNode.value)) {
						alert("请输入正确的日期格式！");
						return;
					}
				}
				var paraArray = new Array();
				paraArray[0] = {
					attribute :'id',
					value :'Constant'
				};
				paraArray[1] = {
					attribute :'EnName',
					value :inputNode.value
				};
				paraArray[2] = {
					attribute :'ChName',
					value :inputNode.value
				};
				
				paraArray[3] = {
					attribute :'KeyValue',
					value :inputNode.value
				};
				
				spanNode = createSpanNode(paraArray, false);
				if(tFunctionName!=null&&tFunctionName!='')
				{
					spanNode.setAttribute('FunctionName',tFunctionName);
				}
			} else {
				
				spanNode = createOriginSpanNode('Constant');
				if(tFunctionName!=null&&tFunctionName!='')
				{
					spanNode.setAttribute('FunctionName',tFunctionName);
				}
			}

			try {
				var fatherNode = inputNode.parentNode;
				fatherNode.replaceChild(spanNode, inputNode);
			} catch (e) {

			}
		}

}

function changInputToSpan(inputNode, type) {
	if (!!inputNode.Editable) {
		if (inputNode.Editable == "Yes") {

			var numRegExp = /^(([1-9][0-9]*)|(0)|((([1-9][0-9]*)|[0-9])\.[0-9]+))$/;
			var dateRegExp = /^\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|31) (([01][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])$/
			var spanNode = null;
			if (inputNode.value != '') {
				if (type == "Number") {
					if (!numRegExp.test(inputNode.value)) {
						alert("请输入正确的数字格式！");
						return;
					}

				} else if (type == "Date") {
					if (!dateRegExp.test(inputNode.value)) {
						alert("请输入正确的日期格式！");
						return;
					}
				}
				var paraArray = new Array();
				paraArray[0] = {
					attribute :'id',
					value :'Input'
				};
				paraArray[1] = {
					attribute :'EnName',
					value :inputNode.value
				};
				paraArray[2] = {
					attribute :'ChName',
					value :inputNode.value
				};
				paraArray[3] = {
					attribute :'KeyValue',
					value :inputNode.value
				};
				
				spanNode = createSpanNode(paraArray, false);
			} else {
				spanNode = createOriginSpanNode('Input');
			}

			try {
				var fatherNode = inputNode.parentNode;
				fatherNode.replaceChild(spanNode, inputNode);
			} catch (e) {

			}
		}
	}
}

function handleClick()// 用下拉框中的节点取代事件源节点，并在必要时生成一些新节点
{
	var srcNode = getSrcElemnt(getEvent());
	//alert("srcNode.getAttribute("EnName"):preEventNode.getAttribute("EnName")###"+srcNode.getAttribute("EnName")+":"+preEventNode.getAttribute("EnName"));
	//alert('handleClick srcNode.id:'+srcNode.id);
	
	if (srcNode.getAttribute("EnName") != preEventNode.getAttribute("EnName")) {
		// 将新节点信息取代原事件节点信息
		var fatherNode = preEventNode.parentNode;
		if (srcNode.id == "BOM") {
			deleteNodesAfter(preEventNode);
			appendNewNodes('BOM', preEventNode, srcNode);
		} else if (srcNode.id == "BOMItem") {
			deleteNodesAfter(preEventNode);
			appendNewNodes('BOMItem', preEventNode, srcNode);
		} else if (srcNode.id == "Operator") {
			deleteNodesAfter(preEventNode);
			appendNewNodes('Operator', preEventNode, srcNode);
		} else if (srcNode.id == 'Link') {
		}
		else if (srcNode.id == "OTHERRULE") {
			deleteNodesAfter(preEventNode);
			appendNewNodes('OTHERRULE', preEventNode, srcNode);
		}
		//tongmeng 2011-04-14 add
		//增加支持函数
		else if (srcNode.id == "AddFunction") {
			deleteNodesAfter(preEventNode);
			appendNewNodes('AddFunction', preEventNode, srcNode);
		}
			//tongmeng 2011-04-14 Add
		//增加参数BOM和参数BOMItem
		else if (srcNode.id == "FunctionBOM") {
			//deleteNodesAfter(preEventNode);
			appendNewNodes('FunctionBOM', preEventNode, srcNode);
		} else if (srcNode.id == "FunctionBOMItem") {
			//deleteNodesAfter(preEventNode);
			appendNewNodes('FunctionBOMItem', preEventNode, srcNode);
		}
	
		
		
		
		//tongmeng 2010-12-27 add
		if (srcNode.id == 'Constant')
		{
			//alert('deal Constant');
			srcNode.onmousedown = function() {
				changeToInputForConstant();
			}
		}
		if (srcNode.id == 'Input') {
			srcNode.onmousedown = function() {
				changeToInput();
			}
		} else {
			srcNode.onmousedown = function() {
				popMenu();
			}
		}
		fatherNode.replaceChild(srcNode, preEventNode);
		destroyMenu();
	}
}

function destroyMenu() {
	var disNode = document.getElementById("display");
	if (disNode.style.display != 'none') {
		while (disNode.firstChild) {
			disNode.removeChild(disNode.firstChild);
		}

		disNode.style.display = "none";
	}
}
function deleteNodesAfter(ob) {
	var fromNode = ob;
	var fatherNode = fromNode.parentNode;
	var lastNode = fatherNode.lastChild;
	//alert("&fromNode.getAttribute('FunctionName'):"+fromNode.getAttribute('FunctionName'));
	// 需要针对实际情况进行修正
	while (fromNode.nextSibling != lastNode
			&& fromNode.nextSibling.id != 'Link'
			&& fromNode.nextSibling.id != 'Left_Paren'
			&& fromNode.nextSibling.id != 'Right_Paren'
			&&fromNode.getAttribute('FunctionName')==''
			) {
		fatherNode.removeChild(fromNode.nextSibling);
	}
}

//tongmeng 2011-04-15 add
//创建函数节点功能
//function createFunctionSpanNodes()


function appendNewNodes(id, preEventNode, srcNode) {
	var Id = id;
	var obNode = preEventNode;
	var srNode = srcNode;
	if (Id == 'BOM') {
		if (preEventNode.nextSibling.id != 'BOMItem') {
			var fatherNode = preEventNode.parentNode;
			// 创建一个BOMItem节点
			var spanNode = null;
			spanNode = createOriginSpanNode('BOMItem');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);

			spanNode = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		}
	} else if (Id == 'BOMItem') {
		var fatherNode = preEventNode.parentNode;
		var preNode = preEventNode.previousSibling;

		var isFirstBOMItem = true;
		var createNext = true;
	
		
		
		// 创建一个BOMItem节点
		if (createNext) {
			var spanNode = null;
			spanNode = createOriginSpanNode('Operator');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			spanNode = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		}
	}
	else if (Id == 'OTHERRULE') {
				var spanNode = null;
		    var fatherNode = preEventNode.parentNode;
	      spanNode = createOriginSpanNode('BOM');
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				spanNode = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
}


else 	if (Id == 'FunctionBOM') {
		if (preEventNode.nextSibling.id != 'FunctionBOMItem') {
			var tFunctionName = srcNode.getAttribute('FunctionName');
			
			var fatherNode = preEventNode.parentNode;
			// 创建一个BOMItem节点
			var spanNode = null;
			spanNode = createOriginSpanNode('FunctionBOMItem');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);

			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		}
	} else if (Id == 'FunctionBOMItem') {
		var fatherNode = preEventNode.parentNode;
		var preNode = preEventNode.previousSibling;

		var isFirstBOMItem = true;
		var createNext = true;
	
		
		
		// 创建一个BOMItem节点
		if (createNext) {
			var spanNode = null;
			var tFunctionName = srcNode.getAttribute('FunctionName');
			//alert('test function BOMItem:'+tFunctionName);
			spanNode = createOriginSpanNode('Operator');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		}
	}
//tongmeng 2011-04-14 
//增加函数

 else if (Id == 'AddFunction') {
		
			//tongmeng test
			var tAddParam = "";
			//alert("srcNode.id:"+srcNode.id+"getAttribute:"+""+srcNode.getAttribute('CommType')+":value:"+srcNode.getAttribute('EnName'));
			//此处需要按照参数的数量和类型初始化
			//select Implenmation,Display,CommandType,ResultType,ParaType,ParaNum,commtype,CommDetail
			/*
			var AddFunctionIndex = new Array('id', 'EnName', 'ChName', 'MatchType','ResultType', 'ParaType', 
			'IsNextOperator','CommType','CommDetail');
			*/
			if(srcNode.getAttribute('CommType')!=null&&srcNode.getAttribute('CommType')!=''&&srcNode.getAttribute('CommType')=='2')
			{
				tAddParam = "#";
			}
			srcNode.setAttribute("EnName",tAddParam+srcNode.getAttribute('EnName'));
			//alert("value:"+srcNode.getAttribute('EnName'));

			//	spanNode.setAttribute(paraArray[i].attribute, paraArray[i].value);
			var tParamNum = srcNode.getAttribute('IsNextOperator');
			var tCommType = srcNode.getAttribute('CommType');
			var tCommDetail = srcNode.getAttribute('CommDetail');
			
			var tFunctionName = srcNode.getAttribute('FunctionName');
			
			var tParamsArr = tCommDetail.split(",");
			//alert('tParamsArr.length:'+tParamsArr.length);
			/*for(i=0;i<tParamsArr.length;i++)
			{
				alert(tParamsArr[i]);
			}*/
			//alert('tParamNum:'+tParamNum+":tCommType:"+tCommType+":tCommDetail:"+tCommDetail);
			var fatherNode = preEventNode.parentNode;
			
			//创建括号
			//创建参数
			var spanNode = null;
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			
			//增加运算符
			spanNode = createOriginSpanNode('Operator');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
				
			spanNode = createOriginSpanNode('Right_Paren');
			spanNode.setAttribute('FunctionName',tFunctionName);
			//tongmeng 2011-03-22 函数的括号,不允许随便处理
			spanNode.setAttribute('FunctionFlag','1');
			//alert("@@@value:"+spanNode.getAttribute('EnName'));
			spanNode.setAttribute("EnName",spanNode.getAttribute('EnName')+tAddParam);
			//alert("@@#value:"+spanNode.getAttribute('EnName'));
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			for(parnum=tParamNum;parnum>0;parnum--)
			{
				//增加BOM的选择
				spanNode = createOriginSpanNode('FunctionBOM');
				spanNode.setAttribute('FunctionName',tFunctionName);
				spanNode.setAttribute('ParaType',tParamsArr[parnum-1])
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);

				spanNode = createOriginSpanNode('Spacer');
				spanNode.setAttribute('FunctionName',tFunctionName);
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				
				
				if(parnum!=1)
				{
					spanNode = createOriginSpanNode('comma');
					spanNode.setAttribute('FunctionName',tFunctionName);
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);

					spanNode = createOriginSpanNode('Spacer');
					spanNode.setAttribute('FunctionName',tFunctionName);
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				}
			}
			
			//spanNode = createOriginSpanNode('Spacer');
			//fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			spanNode = createOriginSpanNode('Left_Paren');
			//tongmeng 2011-03-22 函数的括号,不允许随便处理
			spanNode.setAttribute('FunctionFlag','1');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			
			
	}

	 else if (Id == 'Operator') {
		var spanNode = null;
		var fatherNode = preEventNode.parentNode;
		if (srNode.getAttribute("IsNextOperator") == 0) {
			return;
		}
	//tongmeng 此处需要增加对指定表的支持
		else if (srNode.getAttribute("IsNextOperator") == 1) {
			spanNode = createOriginSpanNode('Operator');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			spanNode = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		} else if (srNode.getAttribute("IsNextOperator") == 2) {
			if (srNode.getAttribute("ResultType") == 'Boolean') {
				if (srNode.getAttribute("EnName") == "instrExt(@,#,$)>0"
						|| srNode.getAttribute("EnName") == "instrExt(@,#,$)=0") {
					var InputNodes = createInputNodes();
					fatherNode.insertBefore(InputNodes,
							preEventNode.nextSibling);
					spanNode = createOriginSpanNode('Spacer');
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				} else {
					if(!mRuleTableFlag)
					{
						spanNode = createOriginSpanNode('Input');
					}
					else
					{
						spanNode = createOriginSpanNode('BOM');
					}
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
					spanNode = createOriginSpanNode('Spacer');
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				}
			} else {
				//alert("test operator:"+srNode.getAttribute('FunctionName'));
				var tFunctionName = srNode.getAttribute('FunctionName');
				if(tFunctionName==null||tFunctionName=='')
				{
					spanNode = createOriginSpanNode('BOM');
				}
				else 
				{
					spanNode = createOriginSpanNode('FunctionBOM');
					spanNode.setAttribute('FunctionName',tFunctionName);
				}
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				spanNode = createOriginSpanNode('Spacer');
				if(tFunctionName!=null&&tFunctionName!='')
				{
					spanNode.setAttribute('FunctionName',tFunctionName);
				}
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			}
		}

	}
}

function createInputNodes() {

	var InputNodes = createOriginSpanNode('InputNodes');

	var spanNode = createOriginSpanNode('InputLeftScope');
	InputNodes.appendChild(spanNode);
	spanNode = createOriginSpanNode('Spacer');
	InputNodes.appendChild(spanNode);
	spanNode = createOriginSpanNode('Input');
	InputNodes.appendChild(spanNode);
	spanNode = createOriginSpanNode("AddInput");
	InputNodes.appendChild(spanNode);
	spanNode = createOriginSpanNode('Spacer');
	InputNodes.appendChild(spanNode);
	spanNode = createOriginSpanNode('InputRightScope');

	InputNodes.appendChild(spanNode);
	return InputNodes;

}

function createOriginSpanNode(id) {
	var reSpanNode = null;
	var paraArray = new Array();
	if (id == 'BOM' || id == 'FunctionBOM' ) {
		paraArray[0] = {
			attribute :'id',
			//value :'BOM'
			value : id
		};
			var tLINK_CHOOSEBOM = getMapValueByKey('LINK_CHOOSEBOM',tLanguage);
			if(tLINK_CHOOSEBOM==null||tLINK_CHOOSEBOM=='')
			{
				tLINK_CHOOSEBOM = '请选择对象';
			}
			
			
		paraArray[1] = {
			attribute :'ChName',
			value :tLINK_CHOOSEBOM
		};
	} 
	//tongmeng 2010-12-17 Add
	//支持选择其他规则
	else if (id == 'OTHERRULE') {
		paraArray[0] = {
			attribute :'id',
			value :'OTHERRULE'
		};
			var tLINK_CHOOSEBOM = getMapValueByKey('LINK_CHOOSEBOM',tLanguage);
			if(tLINK_CHOOSEBOM==null||tLINK_CHOOSEBOM=='')
			{
				tLINK_CHOOSEBOM = '请选择对象';
			}
			
		paraArray[1] = {
			attribute :'ChName',
			value :'选择其他规则'
		};
			paraArray[2] = {
			attribute :'KeyValue',
			value :'LINK_OTHRERULE'
		};
	} 
	
	else if (id == 'Constant') {
		paraArray[0] = {
			attribute :'id',
			value :'Constant'
		};
		
		
		var tLINK_CONS = getMapValueByKey('LINK_CONS',tLanguage);
		if(tLINK_CONS==null||tLINK_CONS=='')
			{
				tLINK_CONS = '一个常数';
			}
			
		paraArray[1] = {
			attribute :'ChName',
			value :tLINK_CONS
		};
		
		paraArray[2] = {
			attribute :'KeyValue',
			value :'LINK_CONS'
		};
	} 
	
	//tongmeng 2011-04-14 Add
	//增加函数
	else if (id == 'AddFunction') {
		paraArray[0] = {
			attribute :'id',
			value :'AddFunction'
		};
		
		var tLINK_FUNC = getMapValueByKey('LINK_FUNC',tLanguage);
		if(tLINK_FUNC==null||tLINK_FUNC=='')
			{
				tLINK_FUNC = '函数';
			}
			
		paraArray[1] = {
			attribute :'ChName',
			value :tLINK_FUNC
		};
		
		paraArray[2] = {
			attribute :'KeyValue',
			value :'LINK_FUNC'
		};
	} 
	
	else if (id == 'BOMItem' || id == 'FunctionBOMItem') {
		paraArray[0] = {
			attribute :'id',
			//value :'BOMItem'
			value : id
		};
			var tIF = getMapValueByKey('LINK_OF',tLanguage);
			if(tIF==null||tIF=='')
			{
				tIF = '的';
			}
			
		paraArray[1] = {
			attribute :'ChName',
			value :tIF
		};
		paraArray[2] = {
			attribute :'KeyValue',
			value :'LINK_OF'
		};
	} else if (id == 'Operator') {
		paraArray[0] = {
			attribute :'id',
			value :'Operator'
		};
		var topt = getMapValueByKey('LINK_CHOOSE',tLanguage);
			if(topt==null||topt=='')
			{
				topt = '请选择操作符';
			}
			
			
		paraArray[1] = {
			attribute :'ChName',
			value :topt
		};
		
		paraArray[2] = {
			attribute :'KeyValue',
			value :'LINK_CHOOSE'
		};
	} else if (id == 'Input') {
		paraArray[0] = {
			attribute :'id',
			value :'Input'
		};
		var tLINK_INPUT = getMapValueByKey('LINK_INPUT',tLanguage);
			if(tLINK_INPUT==null||tLINK_INPUT=='')
			{
				tLINK_INPUT = '请输入值';
			}
			
			
		//LINK_INPUT
		paraArray[1] = {
			attribute :'ChName',
			value :tLINK_INPUT
		};
		paraArray[2] = {
			attribute :'EnName',
			value :''
		};
		paraArray[3] = {
			attribute :'KeyValue',
			value :'LINK_INPUT'
		};

	} else if (id == 'Link') {
		paraArray[0] = {
			attribute :'id',
			value :'Link'
		};
			var tLINK_AND = getMapValueByKey('LINK_AND',tLanguage);
			if(tLINK_AND==null||tLINK_AND=='')
			{
				tLINK_AND = '并且';
			}
			
		paraArray[1] = {
			attribute :'ChName',
			value :tLINK_AND
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'and'
		};
		paraArray[3] = {
			attribute :'KeyValue',
			value :'LINK_AND'
		};
	} else if (id == 'Left_Paren') {
		paraArray[0] = {
			attribute :'id',
			value :'Left_Paren'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'('
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'('
		};
	} else if (id == 'Right_Paren') {
		paraArray[0] = {
			attribute :'id',
			value :'Right_Paren'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :')'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :')'
		};
	} else if (id == 'Spacer') {
		paraArray[0] = {
			attribute :'id',
			value :'Spacer'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :''
		};
	} else if (id == 'InputRightScope') {
		paraArray[0] = {
			attribute :'id',
			value :'InputRightScope'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'}'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'}'
		};
	} else if (id == 'InputLeftScope') {
		paraArray[0] = {
			attribute :'id',
			value :'InputLeftScope'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'{'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'{'
		};
	} else if (id == 'InputNodes') {
		paraArray[0] = {
			attribute :'id',
			value :'InputNodes'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :''
		};
		paraArray[2] = {
			attribute :'EnName',
			value :''
		};
		paraArray[3] = {
		attribute :'KeyValue',
			value :'LINK_INPUT'
		};
	} else if (id == "AddInput") {
		paraArray[0] = {
			attribute :'id',
			value :'AddInput'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'&nbsp;&nbsp;+&nbsp;&nbsp;'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'+'
		};
	} else if (id == "comma") {
		paraArray[0] = {
			attribute :'id',
			value :'comma'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'，'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :','
		};
	} else if (id == "Delete") {
		paraArray[0] = {
			attribute :'id',
			value :'Delete'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'删除'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'Delete'
		};
	}

	reSpanNode = createSpanNode(paraArray, false);
	return reSpanNode;
}

function prepareMenu_Left(id) {
	var Id = id;
	var reSpanArray = new Array();
	var spanNode = null;
//alert("prepareMenu_Left(id):"+Id);
	if (Id == 'BOM') {
		var BOMArray = getBOMArray();
		//alert(BOMArray);
		var paraArray = new Array();
		if (BOMArray != null) {
			var str = '';
			paraArray[0] = {
				attribute :'id',
				value :'BOM'
			};
			for ( var i = 0, length = BOMArray.length; i < length; i++) {
				for ( var j = 0, len = BOMArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :BOMIndex[j + 1],
						value :BOMArray[i][j]
					};
				}
				spanNode = createSpanNode(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		} else {
			alert("系统出错：BOM获取不成功！");
		}
	} else if (Id == 'BOMItem') {
		var infArray = new Array();
		var infArray = getNodeInformation(preEventNode, 'BOMItem');

		var BOMItemArray = new Array();
		BOMItemArray = getBOMItemArray(infArray);

		var paraArray = new Array();

		if (BOMItemArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'BOMItem'
			};
			for ( var i = 0, length = BOMItemArray.length; i < length; i++) {
				for ( var j = 0, len = BOMItemArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :BOMItemIndex[j + 1],
						value :BOMItemArray[i][j]
					};
				}
				spanNode = createSpanNode(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		}
	}
	else if (Id == 'FunctionBOM') {
		var clickedNode = getSrcElemnt(getEvent());
		//alert("clickedNode.id:"+clickedNode+":clickedNode.getAttribute(ParaType):"+clickedNode.getAttribute('ParaType'));
		var BOMArray = getBOMArray();
		var paraArray = new Array();
		if (BOMArray != null) {
			var str = '';
			paraArray[0] = {
				attribute :'id',
				value :'FunctionBOM'
			};
			for ( var i = 0, length = BOMArray.length; i < length; i++) {
				for ( var j = 0, len = BOMArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :BOMIndex[j + 1],
						value :BOMArray[i][j]
					};
				}
				spanNode = createSpanNode(paraArray, true);
				spanNode.setAttribute('ParaType',clickedNode.getAttribute('ParaType'));
				spanNode.setAttribute('FunctionName',clickedNode.getAttribute('FunctionName'));
				reSpanArray[reSpanArray.length] = spanNode;
			}
		} else {
			alert("系统出错：BOM获取不成功！");
		}
	} else if (Id == 'FunctionBOMItem') {
		var infArray = new Array();
		var infArray = getNodeInformation(preEventNode, 'FunctionBOMItem');

		var BOMItemArray = new Array();
		BOMItemArray = getBOMItemArray(infArray);

		var paraArray = new Array();

		if (BOMItemArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'FunctionBOMItem'
			};
			for ( var i = 0, length = BOMItemArray.length; i < length; i++) {
				for ( var j = 0, len = BOMItemArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :BOMItemIndex[j + 1],
						value :BOMItemArray[i][j]
					};
				}
				spanNode = createSpanNode(paraArray, true);
				spanNode.setAttribute('FunctionName',infArray[2]);
				//alert('spanNode.getAttribute("FunctionName"):'+spanNode.getAttribute('FunctionName'));
				reSpanArray[reSpanArray.length] = spanNode;
			}
		}
	}
	
	///////////////////////////
	 else if (Id == 'Operator') {
	 	var clickedNode = getSrcElemnt(getEvent());
	 	var tFunctionName = clickedNode.getAttribute('FunctionName');
	 	//spanNode.setAttribute('FunctionName',clickedNode.getAttribute('FunctionName'));
		var infArray = new Array();
		var infArray = getNodeInformation(preEventNode, 'Operator');

		var OperatorArray = new Array();
		OperatorArray = getOperatorArray(infArray);

		var paraArray = new Array();

		if (OperatorArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'Operator'
			};
			for ( var i = 0, length = OperatorArray.length; i < length; i++) {
				for ( var j = 0, len = OperatorArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :OperatorIndex[j + 1],
						value :OperatorArray[i][j]
					};
				}
				spanNode = createSpanNode(paraArray, true);
				
				if(tFunctionName!=null&&tFunctionName!='')
				{
					spanNode.setAttribute('FunctionName',tFunctionName);
				}
				reSpanArray[reSpanArray.length] = spanNode;
			}
		}
	} else if (Id == 'Link') {
		var paraArray = new Array();
		if (LinkArray != null) {
			for ( var i = 0, length = LinkArray.length; i < length; i++) {
				paraArray[0] = {
					attribute :'id',
					value :'Link'
				};
				for ( var i = 0, length = LinkArray.length; i < length; i++) {
					for ( var j = 0, len = LinkArray[i].length; j < len; j++) {
						paraArray[j + 1] = {
							attribute :LinkIndex[j + 1],
							value :LinkArray[i][j]
						};
					}
					spanNode = createSpanNode(paraArray, true);
					reSpanArray[reSpanArray.length] = spanNode;
				}
			}
		}

	}
	//tongmeng 2011-04-14 Add
	//增加函数
	else if (Id == 'AddFunction') {
		var infArray = new Array();
		//getNodeInformation 待修改
		var infArray = getNodeInformation(preEventNode, 'AddFunction');

		var OperatorArray = new Array();
		OperatorArray = getOperatorArray(infArray);

		var paraArray = new Array();

		if (OperatorArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'AddFunction'
			};
			for ( var i = 0, length = OperatorArray.length; i < length; i++) {
				for ( var j = 0, len = OperatorArray[i].length; j < len; j++) {
					//alert(OperatorArray[i][j]);
					paraArray[j + 1] = {
						attribute :AddFunctionIndex[j + 1],
						value :OperatorArray[i][j]
					};
				}
				//????
				spanNode = createSpanNode(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		}
	}
	
	
	//tongmeng 2010-12-17 add
	//未修改完毕
	if (Id == 'OTHERRULE') {
		var OtherRuleArray = getOtherRule();
	
		var paraArray = new Array();
		if (OtherRuleArray != null) {
				//alert("OtherRuleArray:"+OtherRuleArray);
			var str = '';
			paraArray[0] = {
				attribute :'id',
				value :'OTHERRULE'
			};
			for ( var i = 0, length = OtherRuleArray.length; i < length; i++) {
				for ( var j = 0, len = OtherRuleArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :OTHERRULEIndex[j + 1],
						value :OtherRuleArray[i][j]
					};
				}
				spanNode = createSpanNode(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		} else {
			alert("系统出错：其他规则获取不成功！");
		}
	}
	return reSpanArray;

}
/*
 * 01代表增加左括号， 02代表增加右括号， 03代表删除节点本身 04代表将BOM转换成Input 05代表将Input转换成BOM 06代表增加运算符
 * 07代表增加条件 08代表增加Input节点 09增加BOM
 */
function prepareMenu_Right(id) {
	var Id = id;
	var reArray = new Array();
	var menuOption = null;

	var obNode = preEventNode;
	//alert('Id:'+Id);
	if (Id == 'BOM') {
		// 添加'('
		menuOption = createMenuOption('01');
		reArray[reArray.length] = menuOption;
		// 删除BOM、将BOM转换成Input
		var preNode = obNode.previousSibling;
		var isFirstBOM = true;
		isFirstBOM = false;
		/*
		while (preNode && preNode.id != 'Link') {
			if (preNode.id == 'BOM') {
				isFirstBOM = false;
				break;
			}
			
			preNode = preNode.previousSibling;
		}
		*/
		if (!isFirstBOM) {
			menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('04');
			reArray[reArray.length] = menuOption;
		  //tongmeng 2010-12-26 modify
			//注释掉此处,子算法修改为使用bom的方式初始化
			//menuOption = createMenuOption('10');
			//reArray[reArray.length] = menuOption;
			
			//tongmeng 2010-12-27 add
			//支持常数的录入
			menuOption = createMenuOption('11');
			reArray[reArray.length] = menuOption;
			
		}
		//tongmeng 2011-04-14
			//支持函数
			menuOption = createMenuOption('12');
		  reArray[reArray.length] = menuOption;
	} else if (Id == 'BOMItem') {
		// 添加‘(’
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		// 增加运算符和增加条件
		var nextNode = obNode.nextSibling;
		if (nextNode == obNode.parentNode.lastNode || nextNode.id != 'Operator') {
			menuOption = createMenuOption('06');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('07');
			reArray[reArray.length] = menuOption;
		}
		
		
	}
	//tongmeng 2011-04-22 add
		else if (Id == 'FunctionBOM') {
		// 添加'('
		//menuOption = createMenuOption('01');
		//reArray[reArray.length] = menuOption;
		// 删除BOM、将BOM转换成Input
		var preNode = obNode.previousSibling;
		var isFirstBOM = true;
		isFirstBOM = false;
		/*
		while (preNode && preNode.id != 'Link') {
			if (preNode.id == 'BOM') {
				isFirstBOM = false;
				break;
			}
			
			preNode = preNode.previousSibling;
		}
		*/
		if (!isFirstBOM) {
			menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('04');
			reArray[reArray.length] = menuOption;
		  //tongmeng 2010-12-26 modify
			//注释掉此处,子算法修改为使用bom的方式初始化
			//menuOption = createMenuOption('10');
			//reArray[reArray.length] = menuOption;
			
			//tongmeng 2010-12-27 add
			//支持常数的录入
			menuOption = createMenuOption('11');
			reArray[reArray.length] = menuOption;
			
			//tongmeng 2011-08-10
			//支持函数
			//menuOption = createMenuOption('12');
		  //reArray[reArray.length] = menuOption;
			
		}
		//tongmeng 2011-04-14
			//支持函数
			menuOption = createMenuOption('12');
		  reArray[reArray.length] = menuOption;
		  
		  //tongmeng 2011-08-16 add
		  //支持扩展参数个数
		  menuOption = createMenuOption('13');
		  reArray[reArray.length] = menuOption;
		  
		  
	} else if (Id == 'FunctionBOMItem') {
		// 添加‘(’
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		// 增加运算符和增加条件
		var nextNode = obNode.nextSibling;
		if (nextNode == obNode.parentNode.lastNode || nextNode.id != 'Operator') {
			menuOption = createMenuOption('06');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('07');
			reArray[reArray.length] = menuOption;
		}
	}
	
	
	
	//tongmeng 2010-12-24 add
	else if (Id == 'OTHERRULE') {
		// 添加‘(’
		menuOption = createMenuOption('10');
		reArray[reArray.length] = menuOption;
		// 增加运算符和增加条件
			menuOption = createMenuOption('09');
		reArray[reArray.length] = menuOption;
		var nextNode = obNode.nextSibling;
		//alert("nextNode.id :"+nextNode.id );
		if (nextNode == obNode.parentNode.lastNode || nextNode.id != 'Operator') {
			menuOption = createMenuOption('06');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('07');
			reArray[reArray.length] = menuOption;
			
		}
	}
	else if (Id == 'Constant') {
			menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;
		// 添加‘(’
		menuOption = createMenuOption('10');
		reArray[reArray.length] = menuOption;
		// 增加运算符和增加条件
			menuOption = createMenuOption('09');
		reArray[reArray.length] = menuOption;
		var nextNode = obNode.nextSibling;
		//alert("nextNode.id :"+nextNode.id );
		//if (nextNode == obNode.parentNode.lastNode || nextNode.id != 'Operator') 
		{
			menuOption = createMenuOption('06');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('07');
			reArray[reArray.length] = menuOption;
			
		}
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
	}
	 else if (Id == 'Operator') {
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('07');
		reArray[reArray.length] = menuOption;
		//tongmeng 2011-01-21 add
		//删除本节点
		menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;
		
		
	}else if (Id == 'DeleteOperator') {
		//tongmeng 2011-01-21 add
		//删除本节点
		menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;
	} else if (Id == 'Link') {
		menuOption = createMenuOption('01');
		reArray[reArray.length] = menuOption;
	} else if (Id == 'Input') {
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;

		menuOption = createMenuOption('05');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('07');
		reArray[reArray.length] = menuOption;
	} else if (Id == 'Left_Paren' || Id == 'Right_Paren') {
		menuOption = createMenuOption('03');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
	}
	 else if (Id == 'InputRightScope') {
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('07');
		reArray[reArray.length] = menuOption;
	}
	
	//tongmeng 2011-04-14 add
	//增加函数的定义 
	else if (Id == 'AddFunction') {
		
		menuOption = createMenuOption('03');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('12');
		reArray[reArray.length] = menuOption;
	}
	
	return reArray;
}

function prepareBaseMenu(inforArray) {
	var spanArray = new Array();

	for ( var i = 0, len = inforArray.length; i < len; i++) {
		var paraArray = new Array();

		paraArray[0] = {
			attribute :'id',
			value :'Input'
		};
		paraArray[1] = {
			attribute :'EnName',
			value :inforArray[i][0] + '-' + inforArray[i][1]
		};
		paraArray[2] = {
			attribute :'ChName',
			value :inforArray[i][0] + '-' + inforArray[i][1]
		};

		spanArray[spanArray.length] = createSpanNode(paraArray, true);
	}

	return spanArray;
}

/*
 * 01代表增加左括号， 02代表增加右括号， 03代表删除节点本身 04代表将BOM转换成Input 05代表将Input转换成BOM 06代表增加运算符
 * 07代表增加条件 08代表增加Input节点 09增加BOM
 */
 
 //tongmeng 2010-12-17 Add
 //增加右键选择子算法
function createMenuOption(menuIndex) {
	var spanNode = document.createElement("span");
	spanNode.style.cursor = 'hand';
	spanNode.style.textDecoration = 'blink';

	if (menuIndex == '01') {
		var tAddLeft = getMapValueByKey('LINK_ADDLEFT',tLanguage);
		if(tAddLeft==null||tAddLeft=='')
		{
				tAddLeft = '增加"("';
		}
							
		spanNode.innerHTML = tAddLeft;
		spanNode.onclick = function() {

			var spanN = createOriginSpanNode('Left_Paren');
			var fatherNode = preEventNode.parentNode;
			if (preEventNode.id == 'Link') {
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);

				spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
			} else {
				fatherNode.insertBefore(spanN, preEventNode);
				spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, preEventNode);
			}
		}
	}

	else if (menuIndex == '02') {
		var tAddRight = getMapValueByKey('LINK_ADDRIGHT',tLanguage);
		if(tAddRight==null||tAddRight=='')
		{
				tAddRight = '增加")"';
		}
		
		spanNode.innerHTML = tAddRight;
		spanNode.onclick = function() {
			var spanN = createOriginSpanNode('Right_Paren');
			var fatherNode = preEventNode.parentNode;
			if (fatherNode.id == "InputNodes") {
				var obNode = fatherNode;
				var parNode = fatherNode.parentNode;
				parNode.insertBefore(spanN, obNode.nextSibling);
				spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, obNode.nextSibling);
			} else {
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
				spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
			}

		}

	}

	else if (menuIndex == '03') {
		var tLINK_DELETE = getMapValueByKey('LINK_DELETE',tLanguage);
		if(tLINK_DELETE==null||tLINK_DELETE=='')
		{
				tLINK_DELETE = '删除本节点';
		}
							
							

		spanNode.innerHTML = tLINK_DELETE;

		spanNode.onclick = function() {
			var fatherNode = preEventNode.parentNode;
			//alert('preEventNode.id:'+preEventNode.id+":fatherNode.id:"+fatherNode.id);
			if (preEventNode.id == 'BOM'||preEventNode.id == 'FunctionBOM') {
				var nextNode = preEventNode.nextSibling;
				if (preEventNode.previousSibling.id == 'Operator'
						&& preEventNode.previousSibling.getAttribute("ResultType") != 'Boolean')
					fatherNode.removeChild(preEventNode.previousSibling);
				fatherNode.removeChild(nextNode);
				fatherNode.removeChild(preEventNode);
			} else if (preEventNode.id == 'BOMItem'||preEventNode.id == 'FunctionBOMItem') {
				var preNode = preEventNode.previousSibling;
				if (preNode.previousSibling.id == 'Operator'
						&& preNode.previousSibling.getAttribute("ResultType") != 'Boolean')
					fatherNode.removeChild(preNode.previousSibling);
				fatherNode.removeChild(preNode);
				fatherNode.removeChild(preEventNode);
			} else if (preEventNode.id == 'Operator') {
				var nextNode = preEventNode.nextSibling;
				
				if(nextNode.nextSibling!=null)
				{
				if (nextNode.nextSibling.id == 'BOMItem'||nextNode.nextSibling.id == 'FunctionBOMItem') {
					fatherNode.removeChild(nextNode.nextSibling);
				}
				fatherNode.removeChild(nextNode);
				fatherNode.removeChild(preEventNode);
			}
			else
				{
					fatherNode.removeChild(preEventNode);
					}
			} else if (preEventNode.id == 'Input') {

			} else if (preEventNode.id == 'Left_Paren'
					|| preEventNode.id == 'Right_Paren') {
				
				//alert('preEventNode.id:'+preEventNode.id);
				var tFunctionName = preEventNode.getAttribute('FunctionName');
				if(tFunctionName!=null&&tFunctionName!='')
				{
					alert('函数参数的括号不能删除!');
					return;
				}		
				fatherNode.removeChild(preEventNode);
			}
			 else if (preEventNode.id == 'Constant') {
				fatherNode.removeChild(preEventNode);
			}
			//tongmeng 2011-04-22 Add
			//增加函数的处理
			else if (preEventNode.id == 'AddFunction') {
				
				var nextNode = preEventNode.nextSibling;
				//var tFatherFunctionName = preEventNode.getAttribute('FunctionName');
				var tFunctionName = nextNode.getAttribute('FunctionName');
				//alert('tFatherFunctionName:'+tFatherFunctionName)
				var tCurrentNode = nextNode;
				while (nextNode && tFunctionName!=null && tFunctionName != '') {
					
					var tFunctionName = nextNode.getAttribute('FunctionName');
			//alert('tFunctionName:'+tFunctionName+':nextNode.id:'+nextNode.id);
			//fatherNode.removeChild(nextNode);
			nextNode = nextNode.nextSibling;
			fatherNode.removeChild(tCurrentNode);
			tCurrentNode = nextNode;
		}
		fatherNode.removeChild(preEventNode);
		}
			
	}	
	} else if (menuIndex == '04') {
		var tLINK_INPUT = getMapValueByKey('LINK_INPUT',tLanguage);
		if(tLINK_INPUT==null||tLINK_INPUT=='')
		{
				tLINK_INPUT = '输入一个值';
		}
		
		spanNode.innerHTML = tLINK_INPUT;

		spanNode.onclick = function() {
			var spanN = createOriginSpanNode('Input');
			var fatherNode = preEventNode.parentNode;
			deleteNodesAfter(preEventNode);
			fatherNode.replaceChild(spanN, preEventNode);
		}
	} else if (menuIndex == '05') {
		var tLINK_OTHERBOM = getMapValueByKey('LINK_OTHERBOM',tLanguage);
		if(tLINK_OTHERBOM==null||tLINK_OTHERBOM=='')
		{
				tLINK_OTHERBOM = '下拉其它BOM';
		}
		
		spanNode.innerHTML = tLINK_OTHERBOM;

		spanNode.onclick = function() {
			var tFunctionName = preEventNode.getAttribute('FunctionName');
			var spanN = null;
			//alert('test Function BOM:'+tFunctionName);
			if(tFunctionName==null||tFunctionName=='')
			{
			  spanN = createOriginSpanNode('BOM');
			}
			else
			{
				spanN = createOriginSpanNode('FunctionBOM');
				spanN.setAttribute('FunctionName',tFunctionName);
			}
			var fatherNode = preEventNode.parentNode;
			fatherNode.replaceChild(spanN, preEventNode);
		}

	} else if (menuIndex == '06') {
		var tLINK_ADDCAL = getMapValueByKey('LINK_ADDCAL',tLanguage);
		if(tLINK_ADDCAL==null||tLINK_ADDCAL=='')
		{
				tLINK_ADDCAL = '增加运算项';
		}
		
		
		spanNode.innerHTML = tLINK_ADDCAL;
		spanNode.onclick = function() {
			var spanNOp = createOriginSpanNode('Operator');
			var spanNBOM = createOriginSpanNode('BOM');
			var fatherNode = preEventNode.parentNode;

			fatherNode.insertBefore(spanNBOM, preEventNode.nextSibling);
			var spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);

			fatherNode.insertBefore(spanNOp, preEventNode.nextSibling);
			spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);
		}
	}

	else if (menuIndex == '07') {
		var tLINK_ANDOR = getMapValueByKey('LINK_ANDOR',tLanguage);
		if(tLINK_ANDOR==null||tLINK_ANDOR=='')
		{
				tLINK_ANDOR = '增加"并且"或者"或者"';
		}
		
		spanNode.innerHTML = tLINK_ANDOR;

		spanNode.onclick = function() {
			var spanNodeLink = createOriginSpanNode('Link');
			var spanNodeBOM = createOriginSpanNode('BOM');
			var fatherNode = preEventNode.parentNode;

			if (fatherNode.id == "InputNodes") {
				var obNode = fatherNode;
				var parNode = fatherNode.parentNode;
				parNode.insertBefore(spanNodeBOM, obNode.nextSibling);

				var spanN = createOriginSpanNode('Spacer');
				parNode.insertBefore(spanN, obNode.nextSibling);

				parNode.insertBefore(spanNodeLink, obNode.nextSibling);

				spanN = createOriginSpanNode('Spacer');
				parNode.insertBefore(spanN, obNode.nextSibling);
			} else {
				fatherNode.insertBefore(spanNodeBOM, preEventNode.nextSibling);

				var spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);

				fatherNode.insertBefore(spanNodeLink, preEventNode.nextSibling);

				spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
			}
		}
	}

	else if (menuIndex == '08') {
			var tLINK_ADDINPUT = getMapValueByKey('LINK_ADDINPUT',tLanguage);
		if(tLINK_ADDINPUT==null||tLINK_ADDINPUT=='')
		{
				tLINK_ADDINPUT = '增加输入框';
		}
		
		spanNode.innerHTML = tLINK_ADDINPUT;
		spanNode.onclick = function() {
			var spanN = createOriginSpanNode('Input');
			var fatherNode = preEventNode.parentNode;
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);
			spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);
		}
	}

	else if (menuIndex == '09') {
		var tLINK_ADDBOM = getMapValueByKey('LINK_ADDBOM',tLanguage);
		if(tLINK_ADDBOM==null||tLINK_ADDBOM=='')
		{
				tLINK_ADDBOM = '增加BOM';
		}
		
		spanNode.innerHTML = tLINK_ADDBOM;
		spanNode.onclick = function() {
			
			var tFunctionName = preEventNode.getAttribute('FunctionName');
			var spanN = null;
			//alert('test Function BOM1:'+tFunctionName);
			var fatherNode = preEventNode.parentNode;
			if(tFunctionName==null||tFunctionName=='')
			{
			  spanN = createOriginSpanNode('BOM');
			  fatherNode.insertBefore(spanN, preEventNode.nextSibling);
				spanN = createOriginSpanNode('Spacer');
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
			}
			else
			{
				spanN = createOriginSpanNode('FunctionBOM');
				spanN.setAttribute('FunctionName',tFunctionName);
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
				spanN = createOriginSpanNode('Spacer');
				spanN.setAttribute('FunctionName',tFunctionName);
				fatherNode.insertBefore(spanN, preEventNode.nextSibling);
			}
		}
	}
	
	else if (menuIndex == '10') {
			var tLINK_OTHERRULE = getMapValueByKey('LINK_OTHERRULE',tLanguage);
		if(tLINK_OTHERRULE==null||tLINK_OTHERRULE=='')
		{
				tLINK_ADDBOM = '选择其他规则结果';
		}
		
		spanNode.innerHTML = tLINK_ADDBOM;
		spanNode.onclick = function() {
			var spanN = createOriginSpanNode('OTHERRULE');
			var fatherNode = preEventNode.parentNode;
			fatherNode.insertBefore(spanN, preEventNode);
			spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode);
		}
	}
	
	else if (menuIndex == '11') {
				var tLINK_CONS = getMapValueByKey('LINK_CONS',tLanguage);
		if(tLINK_CONS==null||tLINK_CONS=='')
		{
				tLINK_CONS = '一个常数';
		}
		
		spanNode.innerHTML = tLINK_CONS;
		spanNode.onclick = function() {
			
			var tFunctionName = preEventNode.getAttribute('FunctionName');
			
			//alert('Constant FunctionName:'+tFunctionName);
			var spanN = createOriginSpanNode('Constant');
			if(tFunctionName!=null&&tFunctionName!='')
			{
				spanN.setAttribute('FunctionName',tFunctionName);
			}
			var fatherNode = preEventNode.parentNode;
			fatherNode.insertBefore(spanN, preEventNode);
			spanN = createOriginSpanNode('Spacer');
			if(tFunctionName!=null&&tFunctionName!='')
			{
				spanN.setAttribute('FunctionName',tFunctionName);
			}
			fatherNode.insertBefore(spanN, preEventNode);
		}
	}
	
	//tongmeng 2011-04-14 Add
	//增加函数
	else if (menuIndex == '12') {
			var tLINK_FUNC = getMapValueByKey('LINK_FUNC',tLanguage);
		if(tLINK_FUNC==null||tLINK_FUNC=='')
		{
				tLINK_FUNC = '函数';
		}
		
		spanNode.innerHTML = tLINK_FUNC;
		spanNode.onclick = function() {
			var spanN = createOriginSpanNode('AddFunction');
			var fatherNode = preEventNode.parentNode;
			fatherNode.insertBefore(spanN, preEventNode);
			spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode);
		}
	}
	
	//tongmeng 2011-04-14 Add
	//增加扩展参数
	else if (menuIndex == '13') {
			var tLINK_FUNC = getMapValueByKey('LINK_PARAMS',tLanguage);
		if(tLINK_FUNC==null||tLINK_FUNC=='')
		{
				tLINK_FUNC = '增加参数';
		}
		
		spanNode.innerHTML = tLINK_FUNC;
		spanNode.onclick = function() {
			var spanNOp = createOriginSpanNode('comma');
			var spanNBOM = createOriginSpanNode('FunctionBOM');
			var fatherNode = preEventNode.parentNode;

			fatherNode.insertBefore(spanNBOM, preEventNode.nextSibling);
			var spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);

			fatherNode.insertBefore(spanNOp, preEventNode.nextSibling);
			spanN = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);
		}
	}
	
	return spanNode;
}

function getNodeInformation(node, id) {
	// 将传入的参数进行函数内部存储，避免在使用过程对参数的修改
	var startNode = node;
	var nodeId = id;

	var fatherNode = node.parentNode;

	var reArray = new Array();
	if (nodeId == 'BOMItem') {
		var BOMFinished = false;
		var OperatorFinished = false;

		var BOMName = '', opParameter = '';

		var preNode = startNode.previousSibling;
		while (preNode && preNode.id != 'Link') {
			if (!BOMFinished && (preNode.id == 'BOM')) {
				BOMName = preNode.getAttribute("EnName");
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {
				opParameter = preNode.getAttribute("ParaType");
				OperatorFinished = true;
			}
			preNode = preNode.previousSibling;
		}
		reArray[0] = BOMName;
		reArray[1] = opParameter;
	} else if (nodeId == 'Operator') {
		var BOMItemFinished = false;
		var OperatorFinished = false;
		var hasBoolean = false;
		var firstOperator = true;

		var CommandType = '', opParaType = '';
		var isHierarchical = 0;

		var preNode = startNode.previousSibling;
		while (preNode && preNode.id != 'Link') {
			
			//alert("preNode.id:"+preNode.id);
			if (preNode.id != 'Spacer') {
				if (firstOperator) {
					if (preNode.id == 'Operator') {
						// CommandType = preNode.getAttribute("ParaType");
						CommandType = preNode.getAttribute("ResultType");
						if(CommandType=="Number"||CommandType=="String"||CommandType=="Date")
						{
							break;
						}
					}
				} else
					firstOperator = false;
			}
			if (!BOMItemFinished && (preNode.id == 'BOMItem')) {
				CommandType = preNode.getAttribute("MatchType");
				isHierarchical = preNode.getAttribute("isHierarchical");
				BOMFinished = true;
			}
			//
				if (!BOMItemFinished && (preNode.id == 'AddFunction')) {
				CommandType = preNode.getAttribute("ResultType");
				isHierarchical = preNode.getAttribute("isHierarchical");
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {

				opParameter = preNode.getAttribute("ParaType");
				OperatorFinished = true;
			}
			if(preNode.id == 'Constant')
			{
				CommandType = "Number";
			}
			if (preNode.id == 'Operator' && preNode.getAttribute("ResultType") == 'Boolean') {
				hasBoolean = true;
			}
			preNode = preNode.previousSibling;
		}
		reArray[0] = CommandType;
		reArray[1] = opParameter;
		reArray[2] = hasBoolean;
		reArray[3] = isHierarchical;
		reArray[4] = '0';//运算符
	}
	
	//tongmeng 2011-04-14 Add
	//增加对函数的 处理
	else if (nodeId == 'AddFunction') {
		var BOMItemFinished = false;
		var OperatorFinished = false;
		var hasBoolean = false;
		var firstOperator = true;

		var CommandType = '', opParaType = '';
		var isHierarchical = 0;

		var preNode = startNode.previousSibling;
		while (preNode && preNode.id != 'Link') {
			if (preNode.id != 'Spacer') {
				if (firstOperator) {
					if (preNode.id == 'Operator') {
						// CommandType = preNode.getAttribute("ParaType");
						CommandType = preNode.getAttribute("ResultType");
						if(CommandType=="Number"||CommandType=="String"||CommandType=="Date")
						{
							break;
						}
					}
				} else
					firstOperator = false;
			}
			if (!BOMItemFinished && preNode.id == 'BOMItem') {
				CommandType = preNode.getAttribute("MatchType");
				isHierarchical = preNode.getAttribute("isHierarchical");
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {

				opParameter = preNode.getAttribute("ParaType");
				OperatorFinished = true;
			}
			if (preNode.id == 'Operator' && preNode.getAttribute("ResultType") == 'Boolean') {
				hasBoolean = true;
			}
			preNode = preNode.previousSibling;
		}
		reArray[0] = CommandType;
		reArray[1] = opParameter;
		reArray[2] = hasBoolean;
		reArray[3] = isHierarchical;
		reArray[4] = '1';//函数
	}
	else if (nodeId == 'FunctionBOMItem') {
		var BOMFinished = false;
		var OperatorFinished = false;
		var FunctionName = '';
		var BOMName = '', opParameter = '';

		var preNode = startNode.previousSibling;
		while (preNode && preNode.id != 'Link') {
			if (!BOMFinished && (preNode.id == 'FunctionBOM')) {
				BOMName = preNode.getAttribute("EnName");
				opParameter = preNode.getAttribute('ParaType');
				FunctionName = preNode.getAttribute('FunctionName');
				BOMFinished = true;
			}
		/*	if (!OperatorFinished && preNode.id == 'Operator') {
				opParameter = preNode.getAttribute("ParaType");
				OperatorFinished = true;
			}
			*/
			preNode = preNode.previousSibling;
		}
		reArray[0] = BOMName;
		reArray[1] = opParameter;
		reArray[2] = FunctionName;
	}
	return reArray;
}
// 获取后台BOM数据
function getBOMArray() {
	
	//alert('mIBRMSDefType:'+mIBRMSDefType);
	var BOMArray = new Array();
	var mySql1=new SqlClass();
	if(mIBRMSDefType=='0')
	{
	var sqlid1="makeLogicalNewSql1";
		
		mySql1.setResourceName(tResourceName_Logic); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tLanguage);//指定传入的参数
	  
	}
	else
	{
		var sqlid1="makeLogicalNewSql6";
		
		mySql1.setResourceName(tResourceName_Logic); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tLanguage);//指定传入的参数
		mySql1.addSubPara(mBusiness);//指定传入的参数
	}
	  var strSq1=mySql1.getString();	
	//var sql = "select Name,getRuleMsg(Name,'"+tLanguage+"','BOM',CNName),Name from LRBOM where valid='1' order by BOMLevel,FBOM";
	// 条件
	var tCatchFlag = false;
		for ( var i = 0; i < queryCache.length; i++) {
			if (queryCache[i][0] == strSq1) {
				tCatchFlag = true;
			}
		}
	BOMArray = getAndPrepareData(strSq1);
	//tongmeng 2012-06-13 add
	//指定表
	if(mRuleTableFlag)
	{
		//BOMArray
		
		if(!tCatchFlag)
		{
			var tempArr = new Array();
			tempArr[0] = mRuleTableName;
			tempArr[1] = mRuleTableName;
			tempArr[2] = mRuleTableName;
			tempArr[3] = '1';
			BOMArray[BOMArray.length] = tempArr;
		}
	}
	return BOMArray;
}

function getOtherRule() {
	//alert("getOtherRule:"+getOtherRule);
	var OtherRuleArray = new Array();
	var sql = "select rulename,rulename from lrtemplate where type='1' ";
	// 条件
	OtherRuleArray = getAndPrepareData(sql);

	return OtherRuleArray;
}

// 包含对查询的缓存
function getAndPrepareData(sql) {
	var reArray = new Array();

	for ( var i = 0; i < queryCache.length; i++) {
		if (queryCache[i][0] == sql) {
			return queryCache[i][1];
		}
	}
	var str = easyQueryVer3(sql,1,1,1,0,1);
	reArray = decodeEasyQueryResult(str);
	
	var len = queryCache.length;
	queryCache[len] = new Array();
	queryCache[len][0] = sql;
	queryCache[len][1] = reArray;

	return reArray;

}
// 根据BOM获取后台BOMItem
function getBOMItemArray(paraArray) {
	var BOMItemArray = new Array();
	var sql = ''
	var str = null;
	if (paraArray[0] == null || paraArray[0] == '') {
		alert("获取后台BOMItem所需要的参数出错：不知道BOMItem所属的BOM");
	} else {
		if (paraArray[1] != null && paraArray[1] != '') {
			if (paraArray[1] == "INT") {
				paraArray[1] = "Number";
			}
			var sqlid2="makeLogicalNewSql2";
			var mySql2=new SqlClass();
			mySql2.setResourceName(tResourceName_Logic); //指定使用的properties文件名
			mySql2.setSqlId(sqlid2);//指定使用的Sql的id
			mySql2.addSubPara(tLanguage);//指定传入的参数
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[1]);
	  	mySql2.addSubPara(tLanguage);//指定传入的参数
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);//指定传入的参数
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);//指定传入的参数
	  	mySql2.addSubPara(paraArray[0]);
	  	
	  	sql=mySql2.getString();	
		//	sql = "select Name,getRuleMsg('LINK_OF','"+tLanguage+"','LINK',connector)||getRuleMsg('"+paraArray[0]+"_'||Name,'"+tLanguage+"','BOMItem',CNName),CommandType,Source,isHierarchical,'"+paraArray[0]+"'||'_'||Name from LRBOMItem where BOMName='"
		//			+ paraArray[0] + "' and CommandType='" + paraArray[1] + "'"
		//			+ " union "
		//			+ "select rulename,getRuleMsg('LINK_OF','"+tLanguage+"','LINK','的')||rulename,'Number','','0','BOMSubCal_'||rulename from lrtemplate where business='99' and '"+paraArray[0]+"'='BOMSubCal'"
			//		;
		} else {
			//sql = "select Name,getRuleMsg('LINK_OF','"+tLanguage+"','LINK',connector)||getRuleMsg('"+paraArray[0]+"_'||Name,'"+tLanguage+"','BOMItem',CNName),CommandType,Source,isHierarchical,'"+paraArray[0]+"'||'_'||Name from LRBOMItem where BOMName='"
			//		+ paraArray[0] + "'"
			//	  + " union "
			//		+ "select rulename,getRuleMsg('LINK_OF','"+tLanguage+"','LINK','的')||rulename,'Number','','0','BOMSubCal_'||rulename from lrtemplate where business='99' and '"+paraArray[0]+"'='BOMSubCal'"
			//		;

			//		;
			var sqlid3="makeLogicalNewSql3";
			var mySql3=new SqlClass();
			mySql3.setResourceName(tResourceName_Logic); //指定使用的properties文件名
			mySql3.setSqlId(sqlid3);//指定使用的Sql的id
			mySql3.addSubPara(tLanguage);//指定传入的参数
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);//指定传入的参数
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);//指定传入的参数
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);//指定传入的参数
	  	mySql3.addSubPara(paraArray[0]);
	  	sql=mySql3.getString();	

		}
		BOMItemArray = getAndPrepareData(sql);
		//if()
	}
	return BOMItemArray;
}

// 根据BOMItem类型获取运算符
// 'id','EnName','ChName','MatchType','ResultType','ParaType','IsNextOperator'
function getOperatorArray(paraArray) {
	var OperatorArray = new Array();
	var commandtype = '';
	if (paraArray[0] == "INT") {
		commandtype = 'Number';
	} else {
		commandtype = paraArray[0];
	}
	
	var tFunctionFlag = paraArray[4];
	//alert('tFunctionFlag:'+tFunctionFlag);
	var sql = "";
	//var OperatorIndex = new Array('id', 'EnName', 'ChName', 'MatchType','ResultType', 'ParaType', 'IsNextOperator','ParaNum');
	
	if(tFunctionFlag=='0')
	{
		var sqlid4or7="makeLogicalNewSql4";
		var mySql4or7=new SqlClass();
		mySql4or7.setResourceName(tResourceName_Logic); //指定使用的properties文件名
		 //sql = "select Implenmation,getRuleMsg('CMD_'||name,'"+tLanguage+"','CMD',Display),CommandType,ResultType,ParaType,ParaNum,'CMD_'||name from LRCommand where commtype='0' and CommandType='"
		//		+ commandtype + "'";
		if (paraArray[0] == null || paraArray[0] == '') {
			alert("获取后台Operatror所需要的参数出错：不知道Operator所属的CommandType");
			return null;
		} else {
			if (paraArray[3] == 1) {
				mySql4or7.setSqlId(sqlid4or7);//指定使用的Sql的id
				mySql4or7.addSubPara(tLanguage);//指定传入的参数
		 	 	mySql4or7.addSubPara(commandtype);
			} else {
				sqlid4or7="makeLogicalNewSql7";
				mySql4or7.setSqlId(sqlid4or7);//指定使用的Sql的id
				mySql4or7.addSubPara(tLanguage);//指定传入的参数
		  		mySql4or7.addSubPara(commandtype);
			}
			if (paraArray[1] != null && paraArray[1] != '') {
			//	sql += " and ParaType='" + paraArray[1] + "'";
				var temp = "and ParaType='"+paraArray[1] + "'";
				mySql4or7.addSubPara(temp);
			}
			if (paraArray[2]) {
			//	sql += " and ResultType!='Boolean'";
				var temp = "and ResultType!='Boolean'";
				mySql4or7.addSubPara(temp);
			}
			else
			{
				mySql4or7.addSubPara('');
			}
		}
		sql=mySql4or7.getString();	

	}
	else
	{
		 //sql = "select Implenmation,getRuleMsg('CMD_'||name,'"+tLanguage+"','CMD',Display),CommandType,ResultType,ParaType,ParaNum,commtype,CommDetail,name,'CMD_'||name from LRCommand where 1=1 "
		     //+ " and CommandType='"+ commandtype + "'" 
		  //   + " and commtype in ('1','2') " ;
		  var sqlid5="makeLogicalNewSql5";
			var mySql5=new SqlClass();
			mySql5.setResourceName(tResourceName_Logic); //指定使用的properties文件名
			mySql5.setSqlId(sqlid5);//指定使用的Sql的id
			mySql5.addSubPara(tLanguage);//指定传入的参数
			sql=mySql5.getString();	
	}
	OperatorArray = getAndPrepareData(sql);
	try {
		for ( var i = 0, len = OperatorArray.length; i < len; i++) {
			for ( var j = 0, len1 = OperatorArray[i].length; j < len1; j++) {
				OperatorArray[i][j] = OperatorArray[i][j].replace(new RegExp(
						'@@SinQuot', 'gm'), "'");
			}
		}
	} catch (e) {
		alert("运算符查找出错！");
	}
	return OperatorArray;
}

function getInputSource(sql) {
	var reArray = new Array();
	sql = sql.replace(new RegExp('@@SinQuot', 'gm'), "'");
	var str = easyQueryVer3(sql);
	reArray = decodeEasyQueryResult(str);
	return reArray;
}

/*
 * 
 * 
 * 
 * 
 */
// 用于扫描节点过程中对数据进行暂存
var stack = new Array();
// 用于生成DT表中的列名中处理重复列名问题
var reCurArray = new Array();
// 用于生成决策表表头信息时对信息进行缓存
var disCol = new Array();
// 用于记录定制逻辑中所有Input节点，以减少决策表数据与定制逻辑中数据的交互问题

var RuleChArray = new Array();

function checkOutRule() {
	var ruleNodes = document.getElementById('RuleZone');
	if (!ruleNodes) {
		//alert("您还没有定制规则，请定制完整后再检测规则定制的完整性");
		return true;
	}
	var ruleNode = ruleNodes.firstChild;
	var endNode = ruleNodes.lastChild;
	if (!ruleNode || ruleNode == endNode) {
		//alert("您还没有定制规则，请定制完整后再检测规则定制的完整性");
		return true;
	}
	var row = 0;
	var errorMessage = '';
	while (ruleNode != endNode) {
		row++;
		var BoolOpNum = 0;
		var paren = 0;
		var oppar = true;
		var spanNode = ruleNode.firstChild;
		if (!spanNode) {
			continue;
		}
		var lastNode = ruleNode.lastChild;
		var i = 0;
		//alert("lastNode.id:"+lastNode.id);
		while (spanNode != lastNode) {
			i++;
				if (spanNode.id == "Link") {
				BoolOpNum = 0;
			}
			if (spanNode.id == "Left_Paren"&&spanNode.getAttribute('FunctionName')!=null&&spanNode.getAttribute('FunctionName')!='') {
				paren++;
			}
			if (spanNode.id == "Right_Paren"&&spanNode.getAttribute('FunctionName')!=null&&spanNode.getAttribute('FunctionName')!='') {
				paren--;
			}
			// 对运算符类型的判断
			if (spanNode.id == "Operator"&&(spanNode.getAttribute('FunctionName')==null||spanNode.getAttribute('FunctionName')=='')) { // 逻辑型运算符的个数
				if (spanNode.getAttribute("ResultType") == 'Boolean') {
					BoolOpNum++;
				}
				//tongmeng 2011-02-09 modify
				var opParaNum = spanNode.getAttribute("IsNextOperator");
				// 看连接运算符左右的参数是否正确
				// 一元运算符只能是：前一个是词条，后一个是“删除本条件”
				// 二元运算符只能是：前后两个都是完整的词条，或者其中一个是词条另一个Input，或者是前一个是必须接运算符的运算符后一个是Input或者是BOM
				// 变态运算符只能是：前一个是完整的词条，后一个是运算符
				if (opParaNum == 0) {
					// 找到往前第一个不是Spacer的节点
					var BOMItemNode = null;
					var startNode = spanNode.previousSibling;
					while ((startNode.id == 'Spacer')||
						(startNode.id!='AddFunction'&&startNode.getAttribute('FunctionName')!=null&&startNode.getAttribute('FunctionName')!='')
						
						) {
						startNode = startNode.previousSibling;
					}
					BOMItemNode = startNode;
					// 往后找，找到第一个id不是Spacer的节点
					startNode = spanNode.nextSibling;
					while ((startNode.id == 'Spacer')) {
						startNode = startNode.nextSibling;
					}
					var tlastNode = startNode;

					if (!(BOMItemNode.id == 'BOMItem' && (tlastNode.id == 'link'
							|| tlastNode.id == undefined || tlastNode.id == 'Parentheses')))
							{
								errorMessage += "算法:行 " + row + " 的运算符连接的参数不正确\n";
							}
				} else if (opParaNum == 1) {

				} else if (opParaNum == 2) {
					//alert("opParaNum:"+opParaNum);
					//tongmeng 2011-02-09 Add
					//二元运算符, 如果是数字型的,前后的必须都为数字
					if (spanNode.getAttribute("ResultType") == 'Number') {
						// 找到往前第一个不是Spacer的节点
						var BOMItemNode = null;
						var startNode = spanNode.previousSibling;
						while ((startNode.id == 'Spacer')||
						(startNode.id!='AddFunction'&&startNode.getAttribute('FunctionName')!=null&&startNode.getAttribute('FunctionName')!='')
						
						) {
							startNode = startNode.previousSibling;
							//alert('@@@startNode.id:'+startNode.id+"##"+startNode.getAttribute('FunctionName'));
						}
						//alert("startNode.id:"+startNode.id);
						BOMItemNode = startNode;
						// 往后找，找到第一个id不是Spacer的节点
						startNode = spanNode.nextSibling;
						while ((startNode.id == 'Spacer' || startNode.id == 'BOM')) {
							startNode = startNode.nextSibling;
						}
						var tlastNode = startNode;
					//alert("BOMItemNode.id:"+BOMItemNode.id+":BOMItemNode.getAttribute("MatchType"):"+BOMItemNode.getAttribute("MatchType"));
						//前一个词条的类型必须是数值型的
						if(!(((BOMItemNode.id == 'BOMItem' ||BOMItemNode.id=='AddFunction')&& BOMItemNode.getAttribute("MatchType")=='Number')
								&&(
										(tlastNode.id == 'BOMItem' && tlastNode.getAttribute("MatchType")=='Number')||
										(tlastNode.id == 'Input')||
										//"或者" 和 "并且"
										(tlastNode.id == 'link')||
										//常数
										(tlastNode.id == 'Constant')	
								)										
						))
						{
							//alert('BOMItemNode.id:'+BOMItemNode.id+":lastNode.id:"+lastNode.id);
							errorMessage += "条件:行 " + row + " 的运算符连接的参数不正确\n";
						}
					}
				}
			}
			spanNode = spanNode.nextSibling;
			//alert('spanNode.id:'+spanNode.id+"FunctionName:"+spanNode.getAttribute('FunctionName'));

		}
		if (BoolOpNum != 1) {
			errorMessage += "条件:行 " + row + " 含有" + BoolOpNum + "个运算结果为逻辑型的运算符!\n";
		}
		if (paren != 0) {
			errorMessage += "条件:行 " + row + " 的括号不匹配！\n";
		}
		ruleNode = ruleNode.nextSibling;
	}
	if (errorMessage != '') {
//		alert(1579);
		alert(errorMessage);
		return false;
	} else
		return true;
}

function checkOutCalRule() {
	var ruleCalNodes = document.getElementById('CalZone');
	if (!ruleCalNodes) {
		//alert("您还没有定制规则，请定制完整后再检测规则定制的完整性");
		return true;
	}
	var ruleNode = ruleCalNodes.firstChild;
	var endNode = ruleCalNodes.lastChild;
	if (!ruleNode || ruleNode == endNode) {
		//alert("您还没有定制规则，请定制完整后再检测规则定制的完整性");
		return true;
	}
	var row = 0;
	var errorMessage = '';
	while (ruleNode != endNode) {
		row++;
		var BoolOpNum = 0;
		var paren = 0;
		var oppar = true;
		var spanNode = ruleNode.firstChild;
		if (!spanNode) {
			continue;
		}
		var lastNode = ruleNode.lastChild;
		var i = 0;
		while (spanNode != lastNode) {
			i++;
			if (spanNode.id == "Link") {
				BoolOpNum = 0;
			}
			if (spanNode.id == "Left_Paren"&&spanNode.getAttribute('FunctionName')!=null&&spanNode.getAttribute('FunctionName')!='') {
				paren++;
			}
			if (spanNode.id == "Right_Paren"&&spanNode.getAttribute('FunctionName')!=null&&spanNode.getAttribute('FunctionName')!='') {
				paren--;
			}
			// 对运算符类型的判断
			if (spanNode.id == "Operator") { // 逻辑型运算符的个数
				if (spanNode.getAttribute("ResultType") == 'Boolean') {
					BoolOpNum++;
				}
				//tongmeng 2011-02-09 modify
				var opParaNum = spanNode.getAttribute("IsNextOperator");
				// 看连接运算符左右的参数是否正确
				// 一元运算符只能是：前一个是词条，后一个是“删除本条件”
				// 二元运算符只能是：前后两个都是完整的词条，或者其中一个是词条另一个Input，或者是前一个是必须接运算符的运算符后一个是Input或者是BOM
				// 变态运算符只能是：前一个是完整的词条，后一个是运算符
				if (opParaNum == 0) {
					// 找到往前第一个不是Spacer的节点
					var BOMItemNode = null;
					var startNode = spanNode.previousSibling;
					while ((startNode.id == 'Spacer')) {
						startNode = startNode.previousSibling;
					}
					BOMItemNode = startNode;
					// 往后找，找到第一个id不是Spacer的节点
					startNode = spanNode.nextSibling;
					while ((startNode.id == 'Spacer')) {
						startNode = startNode.nextSibling;
					}
					var tlastNode = startNode;

					if (!(BOMItemNode.id == 'BOMItem' && (tlastNode.id == 'link'
							|| tlastNode.id == undefined || tlastNode.id == 'Parentheses')))
							{
								errorMessage += "算法:行 " + row + " 的运算符连接的参数不正确\n";
							}
				} else if (opParaNum == 1) {

				} else if (opParaNum == 2) {
					//alert("opParaNum:"+opParaNum);
					//tongmeng 2011-02-09 Add
					//二元运算符, 如果是数字型的,前后的必须都为数字
					if (spanNode.getAttribute("ResultType") == 'Number') {
						// 找到往前第一个不是Spacer的节点
						var BOMItemNode = null;
						var startNode = spanNode.previousSibling;
						while ((startNode.id == 'Spacer')||
						(startNode.id!='AddFunction'&&startNode.getAttribute('FunctionName')!=null&&startNode.getAttribute('FunctionName')!='')
						
						) {
							startNode = startNode.previousSibling;
							//alert('@@@startNode.id:'+startNode.id+"##"+startNode.getAttribute('FunctionName'));
						}
						//alert("startNode.id:"+startNode.id);
						BOMItemNode = startNode;
						// 往后找，找到第一个id不是Spacer的节点
						startNode = spanNode.nextSibling;
						while ((startNode.id == 'Spacer' || startNode.id == 'BOM')) {
							startNode = startNode.nextSibling;
						}
						var tlastNode = startNode;
					//alert("BOMItemNode.id:"+BOMItemNode.id+":BOMItemNode.getAttribute("MatchType"):"+BOMItemNode.getAttribute("MatchType"));
						//前一个词条的类型必须是数值型的
						if(!(((BOMItemNode.id == 'BOMItem' ||BOMItemNode.id=='AddFunction')&& BOMItemNode.getAttribute("MatchType")=='Number')
								&&(
										(tlastNode.id == 'BOMItem' && tlastNode.getAttribute("MatchType")=='Number')||
										(tlastNode.id == 'Input')||
										//"或者" 和 "并且"
										(tlastNode.id == 'link')||
										//常数
										(tlastNode.id == 'Constant')	
								)										
						))
						{
							//alert('BOMItemNode.id:'+BOMItemNode.id+":lastNode.id:"+lastNode.id);
							errorMessage += "算法:行 " + row + " 的运算符连接的参数不正确\n";
						}
					}
				}
			}
			spanNode = spanNode.nextSibling;
		}
	//	if (BoolOpNum != 1) {
	//		errorMessage += "算法:行 " + row + " 含有" + BoolOpNum + "个运算结果为逻辑型的运算符!\n";
//		}
		if (paren != 0) {
			errorMessage += "算法:行 " + row + " 的括号不匹配！\n";
		}
		ruleNode = ruleNode.nextSibling;
	}
	if (errorMessage != '') {
//		alert(1579);
		alert(errorMessage);
		return false;
	} else
		return true;
}
/*
 * 函数传入参数为空 函数返回值：函数的返回值是一个数组（reArray） reArray[0]携带拼写好的SQLStatement
 * reArray[1]携带拼写好的createTable reArray[2]携带拼写好的决策表的表头
 */
function composeSQL() {
	// 获取规则区域的节点
	//alert('1');
	var xmlDoc = new ActiveXObject("Msxml2.DOMDocument.3.0");
	var ruleNodes = document.getElementById('RuleZone');
		var xmlRule = xmlDoc.createElement("Rule");
	xmlDoc.appendChild(xmlRule);
	SQLStatement = "";
	initParaBeforeComposeSQL();
	if (ruleNodes) {
	// 获取规则的第一个条件
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;
	// 对拼SQL过程中需要使用到的变量进行初始化
	

	
	ColumnMetasChArray.length = 0;
	var xmlCondition = xmlDoc.createElement("Condition");
	var MetaNode;
	var count = 0;
	//SQLStatement = SQLStatement + " and ";
	while (ruleNode != endNode) {
		if(count==0)
		{
			SQLStatement = SQLStatement + " and ";
		}
		count ++;	
		// 获取条件的第一个节点和最后一个节点
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;
		// 用于记录规则中一个条件的sql字符串

		xmlCondition = xmlDoc.createElement("Condition");

		MetaNodeChNameArray.length = 0;

		while (spanNode != lastNode) {
			//alert("$$$$$$$$$$$$$$$$:"+spanNodeArray.length);
			if (spanNode.id == 'Spacer') {
				spanNode = spanNode.nextSibling;
				continue;
			}

			MetaNodeChNameArray[MetaNodeChNameArray.length] = spanNode;

			//alert('@@@spanNode.id:'+spanNode);
			// 有优化的余地
			if (spanNode.getAttribute("ChName")!="请选择操作符")
			{
				MetaNode = composeXML(xmlDoc, spanNode);
				xmlCondition.appendChild(MetaNode);
			}
			if (spanNode.id == "AddFunction") {
					//comASQL();
					spanNodeArray.push(spanNode);
					
			}
				if (spanNode.id == "comma") {
						//comASQL();
					spanNodeArray.push(spanNode);
				}
				
			if (spanNode.id == "BOM"||spanNode.id == "FunctionBOM") {
				if (spanNodeArray.length != 0) {
					//comASQL();
				}
				spanNodeArray.push(spanNode);
			}
//--------------------------------------------//
			//tongmeng 2010-12-24 Add
			//增加其他规则的处理
			else if (spanNode.id == "OTHERRULE" || spanNode.id =="Constant") {
				//alert('push');
				if (spanNodeArray.length != 0) {
					//comASQL();
			}
			spanNodeArray.push(spanNode);
			
			}
			//--------------------------------------------//
			else if (spanNode.id == "BOMItem"||spanNode.id == "FunctionBOMItem") {
				spanNodeArray.push(spanNode);
			} else if (spanNode.id == "Operator") {
				if (spanNode.getAttribute("ChName")!="请选择操作符")
				{
					spanNodeArray.push(spanNode);
				}
				
				if(spanNode.getAttribute("EnName") == "is null"||spanNode.getAttribute("EnName") == "is not null"  )
				{
					//comASQL();
				}
			} else if (spanNode.id == "Link") {
				//if (spanNodeArray.length != 0) 
				{
					//comASQL();
						spanNodeArray.push(spanNode);
					completeStack(0);
					spanNodeArray.length=0;
					spanNodeArray = new Array();
				}
				//spanNodeArray.push(spanNode);
				ColumnMetasChArray.length = 0;
			} else if (spanNode.id == "Left_Paren") {
				spanNodeArray.push(spanNode);
			} else if (spanNode.id == "Right_Paren") {
				spanNodeArray.push(spanNode);
			} 
			else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
				spanNodeArray.push(spanNode);
				//comASQL();
			}
			spanNode = spanNode.nextSibling;
		}
		if (spanNodeArray.length != 0) {
			//comASQL();
			completeStack(0);
			spanNodeArray.length=0;
		}
		xmlRule.appendChild(xmlCondition);
		composeRuleDesInCh();

		ruleNode = ruleNode.nextSibling;
	}
	}
	//------------------------------------------------------------------------------//
	//tongmeng 2010-12-14 Add
	//增加计算规则的定义
	// 获取规则区域的节点
	var ruleCalNodes = document.getElementById('CalZone');
	// 获取规则的第一个条件
	var ruleCalNode = ruleCalNodes.firstChild;
	// 获取规则的最后一个条件
	var endCalNode = ruleCalNodes.lastChild;
	// 对拼SQL过程中需要使用到的变量进行初始化
	
	var xmlCalCondition = xmlDoc.createElement("CalCondition");
	var MetaCalNode;
	var testrule = "";
	MetaCalNodeChNameArray.length = 0;
	
	var rightFlag = 0;
	
	while (ruleCalNode != endCalNode) {
		// 获取条件的第一个节点和最后一个节点
		var spanNode = ruleCalNode.firstChild;
		var lastNode = ruleCalNode.lastChild;
		// 用于记录规则中一个条件的sql字符串

		xmlCalCondition = xmlDoc.createElement("CalCondition");
	
		
		//alert("spanNode.id:"+spanNode.id+":lastNode.id:"+lastNode.id);
		while (spanNode != lastNode) {
			if (spanNode.id == 'Spacer') {
				spanNode = spanNode.nextSibling;
				continue;
			}
			
			MetaCalNodeChNameArray[MetaCalNodeChNameArray.length] = spanNode;

			//可以复用
			//tongmeng 2010-12-15 修改
			if (spanNode.getAttribute("ChName")!="请选择操作符")
			{
				MetaCalNode = composeXML(xmlDoc, spanNode);
			
				xmlCalCondition.appendChild(MetaCalNode);
			}
			
			//alert("111spanNode.id:"+spanNode.id+":spanCalNodeArray.length:"+spanCalNodeArray.length);
			if (spanNode.id == "AddFunction") {
					//comACalSQL();
					spanCalNodeArray.push(spanNode);
					
			}
				if (spanNode.id == "comma") {
					//	comACalSQL();
					spanCalNodeArray.push(spanNode);
				
			}
			
			if (spanNode.id == "BOM"||spanNode.id == "FunctionBOM") {
				if (spanCalNodeArray.length != 0&&spanNode.getAttribute("ChName")!="请选择对象") {
					//需要修改为拼写算法的SQL
					//comACalSQL();
				}
				if(spanNode.getAttribute("ChName")!="请选择对象")
				{
					spanCalNodeArray.push(spanNode);
				}
			}
//--------------------------------------------//
			//tongmeng 2010-12-24 Add
			//增加其他规则的处理
			else if (spanNode.id == "OTHERRULE"|| spanNode.id =="Constant") {
				//alert('push');
				if (spanCalNodeArray.length != 0) {
				//	comACalSQL();
			}
			spanCalNodeArray.push(spanNode);
			
			}
			//--------------------------------------------//
			else if (spanNode.id == "BOMItem" || spanNode.id == "FunctionBOMItem") {
				spanCalNodeArray.push(spanNode);
			} else if (spanNode.id == "Operator" &&spanNode.getAttribute("ChName")!="请选择操作符") {
				spanCalNodeArray.push(spanNode);
				//if(spanNode.getAttribute("EnName") == "is null"||spanNode.getAttribute("EnName") == "is not null"  )
				{
					//comACalSQL();
				}
			} else if (spanNode.id == "Link") {
				if (spanCalNodeArray.length != 0) {
				//	comACalSQL();
				completeStack(1);
				spanCalNodeArray = new Array();
				}
				spanCalNodeArray.push(spanNode);
				CalColumnMetasChArray.length = 0;
			} else if (spanNode.id == "Left_Paren") {
				spanCalNodeArray.push(spanNode);
			} else if (spanNode.id == "Right_Paren") {
				
				spanCalNodeArray.push(spanNode);
				rightFlag ++;
			} else if (spanNode.id == "Input" || spanNode.id == "InputNodes" ) {
				spanCalNodeArray.push(spanNode);
				//comACalSQL();
			}
			spanNode = spanNode.nextSibling;
		}
		//tongmeng 2011-02-16 modify
		//计算算法特殊,如果最后一个是bomitem时,也需要压一下
		//alert("lastNode.id:"+lastNode.id);
		if (lastNode.id == "BOMItem") 
		{
			spanCalNodeArray.push(lastNode);
			MetaCalNodeChNameArray[MetaCalNodeChNameArray.length] = lastNode;
		}
		
		//alert("spanCalNodeArray.length:"+spanCalNodeArray.length);
		if (spanCalNodeArray.length != 0) {
			//comACalSQL();
			completeStack(1);
			spanCalNodeArray.length=0;
		}
		xmlRule.appendChild(xmlCalCondition);
		//tongmeng 2010-12-15 modify
		//暂时先不生成计算规则的中文对应,稍后需要修改lrtemplate相关表
		CalRuleDesInCh = "";
		composeCalRuleDesInCh();

		ruleCalNode = ruleCalNode.nextSibling;
	}
	//------------------------------------------------------------------------------//
	ViewPara = xmlRule.xml;
	
	//tongmeng 需要增加对指定表的校验
	if(mRuleTableFlag)
	{
		if(!checkRuleTable())
		{
			return false;
		}
	}
//	alert('CalRuleDesInCh:'+CalRuleDesInCh);
	
//	alert("rightFlag:"+rightFlag);
//	alert('CalSQLStatement:'+CalSQLStatement);
	
//	alert("RuleDesInCh:"+RuleDesInCh);
	//alert("ViewPara:"+ViewPara);
	
	
//	alert('SQLStatement:'+SQLStatement);
	//alert("ViewPara:"+ViewPara);
//	return;
	return completeParaAfterCompose();
}

//如果使用指定表的方式,那么,指定表的主键要求一定要出现在规则的条件中
function checkRuleTable()
{
			var sqlid8="makeLogicalNewSql8";
			var mySql8=new SqlClass();
			mySql8.setResourceName(tResourceName_Logic); //指定使用的properties文件名
			mySql8.setSqlId(sqlid8);//指定使用的Sql的id
			mySql8.addSubPara(mRuleTableName);//指定传入的参数
			var sql=mySql8.getString();	
			
			var str = easyQueryVer3(sql,1,1,1,0,1);
			var tPKArray = new Array();
			tPKArray = decodeEasyQueryResult(str);
			
		//	alert('SQLStatement:'+SQLStatement);
			for(i=0;i<tPKArray.length;i++)
			{
				var tPK = tPKArray[i];
				
				var tCheckStr = mRuleTableName+'.'+tPK;
			//	alert('tPK:'+tPK+':tCheckStr:'+tCheckStr);
				if(SQLStatement.indexOf(tCheckStr)==-1)
				{
					alert('使用指定表方式,算法必须包含指定表的所有主键,目前缺少:'+tPK);
					return false;
				}
			}
			return true;
}


function comASQL() {
	var reStr = '';
	var str = '';
	var spanNode = null;
	var outCol = '';
	var op = '';
	var finished = false;
	// 用于记录扫描到节点的英文名
	var linkStr = '';
	var lparenStr = '';
	var rparenStr = '';
	var BOMStr = '';
	var BOMItemStr = '';
	var opStr = '';
	var opStr1 = '';
	// 用于记录扫描到节点的中文名
	var linkStrCh = '';
	var lparenStrCh = '';
	var rparenStrCh = '';
	var BOMStrCh = '';
	var BOMItemStrCh = '';
	var opStrCh = '';

	var disColCh = '';
	var BOMItemType = "";
	var InputType = "";
	var reCreateTable = '';

	var BOMNode = '';
	var BOMItemNode = '';

	var pareNumber = 0;
	
	//tongmeng 2010-12-24 Add
	var otherRule = '';
	
	// aler("堆栈stack的长度是："+stack.length);
	for ( var i = 0, len = spanNodeArray.length; i < len; i++) {
		var spanNode = spanNodeArray[i];
		if (!(!!spanNode))
			continue;
		if (spanNode.id == "Link") {
			com(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			SQLStatement += ' ' + spanNode.getAttribute('EnName') + ' ';
		} 
		
		if (spanNode.id == "AddFunction") {
				com(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			SQLStatement += ' ' + spanNode.getAttribute('EnName');
			//pareNumber += 1;
		} 
		
		if (spanNode.id == "comma") {
			com(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			SQLStatement += spanNode.getAttribute('EnName');
			//pareNumber += 1;
		} 
		
		if (spanNode.id == "OTHERRULE") {
			//???
			otherRule = spanNode.getAttribute('EnName');
			com(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType,otherRule);
			//CalSQLStatement += '^' + spanNode.getAttribute('EnName') + '^';
		}
		else if (spanNode.id == "Left_Paren") {
			if (lparenStr != '') {
				SQLStatement += spanNode.getAttribute('EnName');
			}
			lparenStr = spanNode.getAttribute('EnName');
			pareNumber += 1;
		} else if (spanNode.id == "Right_Paren") {
			rparenStr = spanNode.getAttribute('EnName');
			pareNumber -= 1;
		} else if (spanNode.id == "BOM"||spanNode.id == "FunctionBOM") {
			outCol += spanNode.getAttribute('EnName');
			BOMNode = spanNode.getAttribute('EnName');
			ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute("ChName");
		} else if (spanNode.id == "BOMItem"||spanNode.id == "FunctionBOMItem") {
			outCol += '_' + spanNode.getAttribute('EnName');
			BOMItemNode = spanNode.getAttribute('EnName');
			BOMItemType = spanNode.getAttribute("MatchType");

			ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute("ChName");
		} else if (spanNode.id == "Operator") {
			if (opStr == '') {
				opStr = spanNode.getAttribute('EnName');
			} else {
				opStr1 = spanNode.getAttribute('EnName');
			}
			InputType = spanNode.getAttribute("ParaType");
			ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute("ChName");
		} 
		else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
			var rArray = com(lparenStr, outCol, rparenStr, opStr, opStr1,
					'isInput', InputType, BOMItemType);
			finished = true;
			for ( var j = 0; j < ColumnMetasChArray.length; j++) {
				disColCh += ColumnMetasChArray[j];
			}
			ColumnMetasChArray.length = 0;
		}
	}
	if (!finished) {
		com(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM', InputType,
				BOMItemType);
	}
	spanNodeArray.length = 0;

	/*
	 * if (reCreateTable != '') { CreateTable+= reCreateTable; }
	 */
	if (disColCh != '') {
		ColumnHeadArray[ColumnHeadArray.length] = disColCh;
		ColumnDataTypeArray[ColumnDataTypeArray.length] = InputType;
	}

}

/*
 * 函数传入参数分别是： lparenStr：左括号的英文名 outCol： 格式为BOM.BOMItem rparenStr：右括号的英文名 opStr：
 * 运算符的英文名 flag： 是否需要生成决策表中的一列的标识，Input表示要，BOM表示不要 dataType：
 * BOM.BOMItem中BOMItem的数据类型，用于falg为Input时需要创建决策条表的情况 函数返回值：函数的返回值是一个数组（reArray）
 * reArray[0]携带拼写好的SQLStatement reArray[1]携带拼写好的createTable
 */

function com(lparenStr, outCol, rparenStr, opStr, opStr1, flag, InputType,
		BOMItemType,otherRule) {
	var reStr = '';
	var reCreateTable = '';
	var temp = '';

	if (outCol != '') {
		getBOMAndBOMItem(outCol);
	}

	if (lparenStr != '' && rparenStr != '') {
		lparenStr = '';
		rparenStr = '';
	}
	if (outCol != '') {
		if (BOMItemType == 'Date') {
			temp = lparenStr + "to_date(?" + outCol.replace('_', '.')
					+ "?,'yyyy-mm-dd hh24:mi:ss')" + rparenStr;
		} else {
			temp = lparenStr + '?' + outCol.replace('_', '.') + '?' + rparenStr;
		}

	} else {
		reStr += lparenStr + rparenStr;
	}

	var indexLeft = opStr.indexOf('#');
	var indexRight = opStr.indexOf('@');

	if (flag == 'isInput') {
		var i = getRecur();

		if (opStr.indexOf('$' >= 0)) {
			if (outCol.indexOf('ManageCom') >= 0) {
				opStr = opStr.replace('$', 1);
			} else {
				opStr = opStr.replace('$', 0);
			}
		}
		var hasTwo = false;
		if (indexLeft >= 0 && temp != '') {
			opStr = opStr.replace('#', temp);
			if (opStr1 != '') {
				hasTwo = true;
			}

			if (indexRight >= 0) {

				opStr = opStr.replace('@', DTColumnName + i);

				reStr += opStr + " ";
			} else {
				reStr += opStr + opStr1 + " " + DTColumnName + i + ' ';
			}
		} else {
			reStr += temp + " " + opStr + " " + DTColumnName + i + ' ';
		}
		if (InputType != '') {

			if (InputType == 'Date') {
				reCreateTable = DTColumnName + i + ' Date';
			} else if (InputType == 'INT' || InputType == 'Number') {
				reCreateTable = DTColumnName + i + ' Number';
			} else if (InputType == 'String') {
				reCreateTable = DTColumnName + i + ' varchar2(1000)';
			}
			TableColumnNameArray[TableColumnNameArray.length] = (DTColumnName + i)
					.toUpperCase();
		}
	} 
	 else if(otherRule!=undefined && otherRule!=null)
	 {
	 	 reStr+= '^'+otherRule+'^';
	 }
	else {
		if (indexLeft < 0 && indexRight < 0 && opStr1 == '') {
			reStr += temp + " " + opStr;
		} else {
			if (indexLeft >= 0 && temp != '') {
				temp = opStr.replace('#', temp);
			}
			reStr += temp + " " + opStr1;
		}

	}
	SQLStatement += reStr;
	if (reCreateTable != "") {
		CreateTable += reCreateTable + ",";
	}
}

//tongmeng 2010-12-14 modify
//此处需要调整支持计算型规整的处理
function initParaBeforeComposeSQL() {
	// 存储规则的SQL
	//tongmeng 2010-12-15 modify
	if(!mRuleTableFlag)
	{
		SQLStatement = "select #CalSQLStatement#,RuleID from #DTTable# where 1=1  ";
	}
	else
	{
		SQLStatement = "select #CalSQLStatement#,RuleID from "+mRuleTableName+" where 1=1  ";
	}
	// 创建DT表的语句
	CreateTable = "create table #DTTable# (";

	// 存储规则反显示所需要的参数
	ViewPara = "";
	// 存储规则所用的BOM
	BOMSArray.length = 0;
	// 存储规则反解析的参数
	SQLParaArray.length = 0;
	// 存储规则的中文名
	//tongmeng 2011-05-30 modify
	//LINK_CON
	//RuleDesInCh = "条件，";
	RuleDesInCh = "?LINK_CON?，";
	// 存储决策表列头所要显示的中文名
	ColumnHeadArray.length = 0;
	// 存储决策表列的数据类型
	ColumnDataTypeArray.length = 0;
	// 存储DT表中列名。
	TableColumnNameArray.length = 0;
	// 存储决策表所的运算符是否为“其中之一”
	ColumnMultipleArray.length = 0;
	// 存储决策表的列是否为基础词条
	BaseColumnArray.length = 0;
	// 存储扫描到节点的中文名
	MetaNodeChNameArray.length = 0;
	// 存储扫描节点的数组
	spanNodeArray.length = 0;
	
	//列名重复次数
	reCurr=0;
}

function completeParaAfterCompose() {
	// 创建DT表的语句
	//CreateTable += 'DTRate Number(10,4),RuleId varchar2(20))';
	CreateTable += 'DTRate varchar2(100),RuleId varchar2(20))';
	
	//alert(CreateTable);
	//tongmeng 2011-05-30 modify
	
	//RuleDesInCh += "设置算法#";
	RuleDesInCh += "?LINK_CAL?#";
	
	//if(RuleDesInCh.indexOf("是空")>0){
		//alert("减少一列!");
	//}
	//alert(RuleDesInCh);

	ColumnHeadArray[ColumnHeadArray.length] = "结果";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	TableColumnNameArray[TableColumnNameArray.length] = "DTRate";

  ColumnHeadArray[ColumnHeadArray.length] = "提示信息";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "Button";
	TableColumnNameArray[TableColumnNameArray.length] = "RuleId";
/*
  ColumnHeadArray[ColumnHeadArray.length] = "RuleId";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	TableColumnNameArray[TableColumnNameArray.length] = "RuleId";
*/
	return checkColumnLength();
}

function checkColumnLength() {
	var message = '';
	if(SQLStatement!=undefined && SQLStatement!=null)
	{
	if (Length(SQLStatement) > 2000 || Length(BOMSArray.toString()) > 500
			|| Length(SQLParaArray.toString()) > 2000
			|| Length(RuleDesInCh) > 2000) {
		message = "定制的规则太长，请对规则进行精简！";
	}
}
	if (message != '') {
//		alert(1917);
		alert(message);
		return false;
	} else {
		return true;
	}

}

function Length(str) {
	var i, sum;
	sum = 0;
	for (i = 0; i < str.length; i++) {
		if ((str.charCodeAt(i) >= 0) && (str.charCodeAt(i) <= 255))
			sum = sum + 1;
		else
			sum = sum + 2;
	}
	return sum;
}

function compactBOMArray(BOMEnName) {
	var i = 0;
	var len = BOMSArray.length;
	for (i = 0; i < len; i++) {
		if (BOMSArray[i] == BOMEnName)
			break;
	}
	if (i >= len) {
		BOMSArray[len] = BOMEnName;
	}
}

function compactSQLParaArray(SQLPara) {
	var i = 0;
	var len = SQLParaArray.length;
	for (i = 0; i < len; i++) {
		if (SQLParaArray[i] == SQLPara)
			break;
	}
	if (i >= len) {
		SQLParaArray[len] = SQLPara;
	}
}
function getBOMAndBOMItem(BOMAndBOMItem) {
	var result = BOMAndBOMItem.split("_");
	var BOMStr = result[0];
	var BOMItemStr = result[1];

	if (BOMStr != '') {
		compactBOMArray(BOMStr);
		if (BOMItemStr != '') {
			compactSQLParaArray(BOMStr + "." + BOMItemStr);
		}
	}
}
function getRecur() {
	//alert(1974);
	//alert("reCurr"+reCurr);
	return reCurr++;
}

function composeXML(xmlDoc, spanNode) {
	var xmlNode = xmlDoc.createElement("MetaNode");
	//alert('composeXML:spanNode.id:'+spanNode.id);
	if (spanNode.id == 'BOM') {
		for ( var i = 0; i < BOMIndex.length; i++) {
			try{
			var att = spanNode.getAttribute(BOMIndex[i]);
			xmlNode.setAttribute(BOMIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	} else if (spanNode.id == 'BOMItem') {
		for ( var i = 0; i < BOMItemIndex.length; i++) {
			try{
			var att = spanNode.getAttribute(BOMItemIndex[i]);
			att = att.replace(new RegExp("'", "gm"), "''");
			xmlNode.setAttribute(BOMItemIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	} 
	else if (spanNode.id == 'AddFunction') {
		for ( var i = 0; i < BOMIndex.length; i++) {
			try{
			var att = spanNode.getAttribute(AddFunctionIndex[i]);
			xmlNode.setAttribute(AddFunctionIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	}
	else if (spanNode.id == 'FunctionBOM') {
		for ( var i = 0; i < BOMIndex.length; i++) {
			try{
				var att = spanNode.getAttribute(FunctionBOMIndex[i]);
				xmlNode.setAttribute(FunctionBOMIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	} else if (spanNode.id == 'FunctionBOMItem') {
		for ( var i = 0; i < BOMItemIndex.length; i++) {
			
			try{
			var att = spanNode.getAttribute(FunctionBOMItemIndex[i]);
			att = att.replace(new RegExp("'", "gm"), "''");
			xmlNode.setAttribute(FunctionBOMItemIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		
		}
	} 
	else if (spanNode.id == 'OTHERRULE') {
		for ( var i = 0; i < LinkIndex.length; i++) {
			try{
				var att = spanNode.getAttribute(OTHERRULEIndex[i]);
				xmlNode.setAttribute(OTHERRULEIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	}
	else if (spanNode.id == 'Operator') {
		//tongmeng 此处需要调整下
		for ( var i = 0; i < OperatorIndex.length; i++) {
			try
			{
			var att = spanNode.getAttribute(OperatorIndex[i]);
			xmlNode.setAttribute(OperatorIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	} else if (spanNode.id == 'Link') {
		for ( var i = 0; i < LinkIndex.length; i++) {
			try{
				var att = spanNode.getAttribute(LinkIndex[i]);
				xmlNode.setAttribute(LinkIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	} 
	else if (spanNode.id == 'comma') {
		for ( var i = 0; i < CommaIndex.length; i++) {
			try{
			var att = spanNode.getAttribute(CommaIndex[i]);
			xmlNode.setAttribute(CommaIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	}
	
	else if (spanNode.id == 'Left_Paren' || spanNode.id == 'Right_Paren') {
		for ( var i = 0; i < ParenIndex.length; i++) {
			try{
			var att = spanNode.getAttribute(ParenIndex[i]);
			xmlNode.setAttribute(ParenIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	} else if (spanNode.id == 'Input' || spanNode.id == 'InputNodes' || spanNode.id == 'Constant') {
		//alert(spanNode.id);
		//alert('spanNode:'+spanNode.getAttribute('EnName'));
		for ( var i = 0; i < InputIndex.length; i++) {
			try{
			var att = spanNode.getAttribute(InputIndex[i]);
			xmlNode.setAttribute(InputIndex[i], att);
			}
			catch(e)
			{
				//continue;
			}
		}
	}
	return xmlNode;
}
function composeRuleDesInCh() {
	var spanNode;
	for ( var i = 0, len = MetaNodeChNameArray.length; i < len; i++) {
		spanNode = MetaNodeChNameArray[i];
		if(spanNode.id == "Operator" &&spanNode.getAttribute("ChName") =="请选择操作符")
		{
			 continue;
		}
		if (spanNode.id != "Input" && spanNode.id != "InputNodes") {
			//tongmeng 2011-05-30 modify
			//多语言处理
			//RuleDesInCh += spanNode.getAttribute("ChName");
			
			//alert('spanNode.getAttribute("KeyValue"):'+spanNode.getAttribute("KeyValue"));
			RuleDesInCh += "?"+spanNode.getAttribute("KeyValue")+"?";
		} else {
			RuleDesInCh += "#，";
		}
	}
	
	//alert('RuleDesInCh:'+RuleDesInCh);
}

//tongmeng 2010-12-14 modify
//增加计算算法的定义
// 审核规则定制的接口
function comfirmLogic() {
	// 审核规则是否定制完整
	//tongmeng 2010-12-29 add
	//fm.all('CreateDTFlag').value = '1';
	
	CalSQLStatement = "";
	//tongmeng 2011-02-09 add
	//校验计算算法定义是否正确
//checkOutCalRule();
//tongmeng 算法逻辑校验处还需要再做调整,暂时注释掉.
//	if (checkOutRule()&&checkOutCalRule()) 
	{
		var fm = document.getElementById('fm');

		if (composeSQL()) {
			initDataArray();
			//tongmeng add
			initRuleID();
			displayDicTable();
			if(mRuleTableFlag)
			{
				document.getElementById("grid-example").style.display="none";
			}
			var submitData = document.getElementById('submitData');
			submitData.disabled = false;
			var logicToTable = document.getElementById('logicToTable');
			logicToTable.disabled = false;
			var modifyLogic = document.getElementById('modifyLogic');
			modifyLogic.disabled = false;
			var displayDisicionTable = document
					.getElementById('displayDisicionTable');
			displayDisicionTable.disabled = true;
		}

		else {

			return;
		}

	}
}

function getInputNodes() {
	InputNodes = new Array();
	var ruleNodes = document.getElementById('RuleZone');
	// 获取规则的第一个条件
	if(ruleNodes){
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;
	InputNodes.length = 0;
	while (ruleNode != endNode) {
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;

		while (spanNode != lastNode) {
			if (spanNode.id == 'Input' || spanNode.id == 'InputNodes') {
				InputNodes[InputNodes.length] = spanNode;
			}
			spanNode = spanNode.nextSibling;
		}
		ruleNode = ruleNode.nextSibling;
	}
}


	//--------------------------------------------------//
	//tongmeng 2010-12-15 Add
	var ruleNodes = document.getElementById('CalZone');
	// 获取规则的第一个条件
	if(ruleNodes)
	{
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;

	while (ruleNode != endNode) {
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;

		while (spanNode != lastNode) {
			if (spanNode.id == 'Input' || spanNode.id == 'InputNodes') {
				InputNodes[InputNodes.length] = spanNode;
			}
			spanNode = spanNode.nextSibling;
		}
		ruleNode = ruleNode.nextSibling;
	}
	}
	//--------------------------------------------------//
	InputNodes[InputNodes.length] = document.getElementById('Result');
}

function getBaseBomItems() {
	getInputNodes();
	BaseColumnArray.length = 0;
	BaseBOMItemSourceArray.length = 0;
	ColumnMultipleArray.length = 0;
	var inputNode;
	var firstBomItem;
	//if(!!InputNodes)
	///alert("InputNodes.length:"+InputNodes.length);
	{
	for ( var i = 0; i < InputNodes.length - 1; i++) {
		inputNode = InputNodes[i];
		
		//alert('inputNode.id:'+inputNode.id);
		while (inputNode.id != 'BOMItem'&&inputNode.id != 'FunctionBOMItem') {
			inputNode = inputNode.previousSibling;
		}
		firstBomItem = inputNode;
		if (!!firstBomItem.getAttribute("Source")) {
			BaseColumnArray[i] = true;

			inputNode = InputNodes[i];
			while (inputNode.id != 'Operator') {
				inputNode = inputNode.previousSibling;
			}
			if (inputNode.getAttribute("EnName") == "instrExt(@,#,$)>0"
					|| inputNode.getAttribute("EnName") == "instrExt(@,#,$)=0") {
				ColumnMultipleArray[i] = true;
			} else {
				ColumnMultipleArray[i] = false;
			}
		} else {
			BaseColumnArray[i] = false;
			ColumnMultipleArray[i] = false;
		}
		BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = firstBomItem.getAttribute("Source");
	}
}
	// 用于记录自核结论那一列
	//tongmeng 2010-12-15 modify
	//记录决策表的比例数据
	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = "";
	
	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = "";
	
	
/*	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = "";
	
	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = "";
*/

/*
	var sql = "select sysvarvalue from ldsysvar  where sysvar='ibrmsUWLevel'";
	var reArray = getAndPrepareData(sql);
	var UWSource = reArray[0][0];
	
	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = UWSource;
*/
}

function dataToTable() {
	getInputNodes();
	var dataD = new Array();
	if (InputNodes.length != 0) {
		for ( var i = 0; i < InputNodes.length - 1; i++) {
			if (InputNodes[i].id != "InputNodes") {
				if (!!InputNodes[i].getAttribute("EnName")) {
					if (ColumnDataTypeArray[i] == 'Date') {
						var dt = new Date(InputNodes[i].getAttribute("EnName"));
						dataD[i] = dt.format('Y-m-d H:i:s');
					} else {
						dataD[i] = InputNodes[i].getAttribute("EnName");
					}
				}
			} else {
				var firstNode = InputNodes[i].firstChild;
				var first = true;
				while (firstNode) {
					if (firstNode.id == "Input") {
						if (first) {
							dataD[i] = firstNode.getAttribute("EnName");
							first = false;
						} else {
							dataD[i] += ";" + firstNode.getAttribute("EnName");
						}
					}
					firstNode = firstNode.nextSibling;
				}
			}
		}
		if (!!InputNodes[InputNodes.length - 1].value) {
			dataD[InputNodes.length - 1] = InputNodes[InputNodes.length - 1].value;
		}

	}
	var dataArray = sm.getSelections();
	if (dataArray.length > 1) {
		alert('您选择的行数大于1，请选择一行！');
	} else if (dataArray.length == 0) {
		alert('您还没有选择行，请选择一行！');

	} else {

		// dataD[dataD.length]=dataArray[0].get(dataId[dataId.length-1].name);
		var dIndex = grid.getStore().indexOf(dataArray[0]);
		data = getData();
		data[dIndex] = dataD;
		ds = new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
		ds.load();
		grid.reconfigure(ds, cm);

	}

}
function troggleData(tData) {
	if (InputNodes.length == tData.length) {
		for ( var i = 0; i < tData.length - 1; i++) {
			if (InputNodes[i].id != "InputNodes") {
				if (!!tData[i]) {
					InputNodes[i].setAttribute("EnName", tData[i]);
					InputNodes[i].innerHTML = tData[i];
				}
			} else {
				if (!!tData[i]) {
					var strArray = tData[i].split(";");
					j = 0;
					var firstNode = InputNodes[i].firstChild;
					while (firstNode) {
						if (firstNode.id == "Input") {
							if (!!strArray[j]) {
								firstNode.setAttribute("EnName", strArray[j]);
								firstNode.innerHTML = strArray[j];
							} else {
								firstNode.setAttribute("EnName", "");
								firstNode.innerHTML = "请输入一个值";
							}
							j++;
						}
						firstNode = firstNode.nextSibling;
					}

				}
			}
		}
		InputNodes[InputNodes.length - 1].value = tData[tData.length - 1];
	} else {
		alert("获取的数据与要显示的数据不能一一匹配，请找管理员");
	}
}
function dataToLogic() {
	getInputNodes();
	var tData = getSelectedDatas();
	troggleData(tData);
}

function RowToLogic(sm, rowIndex, r) {
	if (!InputNodeEnabled) {
		return;
	}
	getInputNodes();
	var len = dataId.length - 1;
	var da = null;
	if (InputNodes.length == len) {
		for ( var i = 0; i < len - 1; i++) {
			da = r.get(dataId[i].name);
			singleDataToLogic(da, i);
		}
		InputNodes[len - 1].value = r.get(dataId[len - 1].name);
	}
}
function CellToLogic(e) {
	if (!InputNodeEnabled) {
		return;
	}

	var nowValue = e.value;
	var orgValue = e.originalValue;
	if (nowValue != orgValue) {
		var col = e.column - 2;
		getInputNodes();
		if (col == InputNodes.length) {
			return;
		}
		if (col != InputNodes.length - 1) {
			singleDataToLogic(nowValue, col);
		} else {
			InputNodes[InputNodes.length - 1].value = nowValue;
		}
	}
}

function InitLogic(e) {
	if (!InputNodeEnabled) {
		return;
	}
	var r = e.record;
	getInputNodes();
	var len = dataId.length - 1;
	var da = null;
	if (InputNodes.length == len) {
		for ( var i = 0; i < len - 1; i++) {
			da = r.get(dataId[i].name);
			singleDataToLogic(da, i);
		}
		InputNodes[len - 1].value = r.get(dataId[len - 1].name);
	}
}

function singleDataToLogic(da, i) {
	if (InputNodes[i].id != "InputNodes") {

		InputNodes[i].setAttribute("EnName", da);
		if (da != "" && da != undefined && da != null) {
			if (ColumnDataTypeArray[i] == "Date") {
				var dt = new Date(da);
				da = dt.format('Y-m-d H:i:s');
			}
			InputNodes[i].innerHTML = da;
			InputNodes[i].setAttribute("ChName", da);
			InputNodes[i].setAttribute("EnName", da);
		} else {
			InputNodes[i].innerHTML = "请输入一个值";
		}
	} else {
		if (da != "" && da != undefined && da != null) {
			var strArray = da.split(";");
			var len = strArray.length;
			j = 0;
			var firstNode = InputNodes[i].firstChild;
			while (firstNode) {
				if (j >= len) {
					while (firstNode.nextSibling.id == "Input"
							|| firstNode.nextSibling.id == "comma") {
						InputNodes[i].removeChild(firstNode.nextSibling);
					}
				} else if (firstNode.id == "Input") {
					j++;
					if (j == len) {
						continue;
					}

				}
				if (firstNode.id == "AddInput") {
					break;
				}
				firstNode = firstNode.nextSibling;
			}
			if (j < len) {
				for (; j < len; j++) {
					var spanNode = createOriginSpanNode("comma");
					InputNodes[i].insertBefore(spanNode, firstNode);
					spanNode = createOriginSpanNode("Input");
					InputNodes[i].insertBefore(spanNode, firstNode);
				}
			}
			j = 0;
			firstNode = InputNodes[i].firstChild;
			while (firstNode) {
				if (firstNode.id == "Input") {
					firstNode.setAttribute("EnName", strArray[j]);
					if (!!strArray[j]) {
						firstNode.innerHTML = strArray[j];
					} else {
						firstNode.innerHTML = "请输入一个值";
					}
					j++;
				}
				firstNode = firstNode.nextSibling;
			}
		} else {
			var firstNode = InputNodes[i].firstChild;

			while (firstNode) {
				if (firstNode.id == "Input") {
					firstNode.setAttribute("EnName", "");
					firstNode.innerHTML = "请输入一个值";
					break;
				} else {
					firstNode = firstNode.nextSibling;
				}
			}
			while (firstNode.nextSibling.id != "AddInput") {
				InputNodes[i].removeChild(firstNode.nextSibling);
			}
		}
	}
}

function showButtons() {
	setButtonsState('Enable');
}
function hideButtons() {
	setButtonsState('Disable');
	
}
function setButtonsState(displayType) {
	// 获取规则区域的节点
	var ruleNodes = document.getElementById('RuleZone');
	// 获取规则的第一个条件
	if(ruleNodes)
	{
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;

	var buttonNode = null;
	if (displayType == 'Enable') {
		state = '';
	} else {
		state = 'none';
	}
	while (ruleNode != endNode) {
		buttonNode = ruleNode.lastChild;
		if (buttonNode.id == "AddButton" || buttonNode.id == "DelButton") {

			buttonNode.style.display = state;
		}
		ruleNode = ruleNode.nextSibling;
	}
	endNode.style.display = state;
}
//tongmeng 2011-02-09 modify
//增加对算法区的按钮的处理
	ruleNodes = document.getElementById('CalZone');
	// 获取规则的第一个条件
	if(ruleNodes)
	{
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;

	var buttonNode = null;
	if (displayType == 'Enable') {
		state = '';
	} else {
		state = 'none';
	}
	while (ruleNode != endNode) {
		buttonNode = ruleNode.lastChild;
		if (buttonNode.id == "AddButton" || buttonNode.id == "DelButton") {

			buttonNode.style.display = state;
		}
		ruleNode = ruleNode.nextSibling;
	}
	endNode.style.display = state;
}
}

function convertXMLToRule(xmlFile) {
	try {
		var xmlDoc;
		try{
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(xmlFile);
		}catch (e) {
			xmlDoc = (new DOMParser()).parseFromString(xmlFile, "text/xml");
		}
	} catch (e) {
		alert('您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!');
		return;
	}
	if (xmlDoc == null) {
		alert('您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!');
		return;
	}
	// 创建条件DIV区域
	var RuleZone = document.createElement("div");
	RuleZone.setAttribute('id', 'RuleZone');
	var IfNode = document.getElementById("conditions");
	IfNode.parentNode.insertBefore(RuleZone, IfNode.nextSibling);
	// 创建一条规则
	var ruleDiv = null;
	var ruleNode = xmlDoc.getElementsByTagName("Condition");
	var ruleMetaNodes = null;
	var ruleMetaNode = null;
	var spanNode = null;
	/* var disCol = new Array(); */
	var DTHeader = new Array();
	ColumnHeadArray.length = 0;
	ColumnDataTypeArray.length = 0;
	rowCount = 0;

	for ( var i = 0, len = ruleNode.length; i < len; i++) {
		ruleDiv = document.createElement("div");
		try {
			ruleMetaNodes = ruleNode[i].childNodes;
			DTHeader.length = 0;
			if(rowCount==0)
			{
					var spacerNode = createOriginSpanNode('Spacer');
					ruleDiv.appendChild(spacerNode);
						
					var spacerNode = createOriginSpanNode('Spacer');
					ruleDiv.appendChild(spacerNode);
						
					var spacerNode = createOriginSpanNode('Spacer');
					ruleDiv.appendChild(spacerNode);
				}
					
			for ( var j = 0; j < ruleMetaNodes.length; j++) {
				ruleMetaNode = ruleMetaNodes[j];
					
			
				if (ruleMetaNode.getAttribute('id') == "Link") {
					DTHeader.length = 0;
				} else {
					DTHeader[DTHeader.length] = ruleMetaNode;

					if (ruleMetaNode.getAttribute('id') == "Input"
							|| ruleMetaNode.getAttribute('id') == "InputNodes") {

						composeDTHeader(DTHeader);
					}
				}
				
				//tongmeng 初始化前先做替换
				//getMapValueByKey(tKeyID,tLan)
				
				var tKeyID = ruleMetaNode.getAttribute('KeyValue');
				if(tKeyID!=null&&tKeyID!='')
				{
					//alert('tKeyID:'+tKeyID+':tLanguage:'+tLanguage);
					var tReplace = getMapValueByKey(tKeyID,tLanguage);
					//alert(tReplace);
					if(tReplace!='')
					{
						
						if(ruleMetaNode.getAttribute('id')=='BOMItem')
						{
							var tLink = getMapValueByKey('LINK_OF',tLanguage);
							ruleMetaNode.setAttribute('ChName',tLink+' '+tReplace);
						}
						else
						{
							ruleMetaNode.setAttribute('ChName',tReplace);
						}
					}
				}
				
				spanNode = createIntegritySpanNode(ruleMetaNode);
				ruleDiv.appendChild(spanNode);

				spanNode = createOriginSpanNode("Spacer");
				ruleDiv.appendChild(spanNode);
			}
			spanNode = createDeleteButtonNode('0');
			ruleDiv.appendChild(spanNode);
		} catch (e) {
			alert("创建节点时出错！"+e.message);
		}
		RuleZone.appendChild(ruleDiv);
		rowCount++;
	}
	
	
	
	spanNode = createAddLineButton("0");
	RuleZone.appendChild(spanNode);
//tongmeng 2010-12-17 modify
	//解析计算规则
	//-----------------------------------------------------------//
		var CalZone = document.createElement("div");
	CalZone.setAttribute('id', 'CalZone');
	var CalIfNode = document.getElementById("calConditions");
	CalIfNode.parentNode.insertBefore(CalZone, CalIfNode.nextSibling);
	// 创建一条规则
	var CalruleDiv = null;
	var CalruleNode = xmlDoc.getElementsByTagName("CalCondition");
	var CalruleMetaNodes = null;
	var CalruleMetaNode = null;
	var CalspanNode = null;
	/* var disCol = new Array(); */
	//var DTHeader = new Array();
	//ColumnHeadArray.length = 0;
	//ColumnDataTypeArray.length = 0;
	rowCalCount = 0;
//alert("CalruleNode.length:"+CalruleNode.length);


	for ( var i = 0, len = CalruleNode.length; i < len; i++) {
		CalruleDiv = document.createElement("div");
		try {
			//tongmeng 2011-02-16 modify
			if(rowCalCount==0)
			{
						var spacerNode = createOriginSpanNode('Spacer');
						CalruleDiv.appendChild(spacerNode);
						
						var spacerNode = createOriginSpanNode('Spacer');
						CalruleDiv.appendChild(spacerNode);
						
						var spacerNode = createOriginSpanNode('Spacer');
						CalruleDiv.appendChild(spacerNode);
			}
			CalruleMetaNodes = CalruleNode[i].childNodes;
			DTHeader.length = 0;
			for ( var j = 0; j < CalruleMetaNodes.length; j++) {
				CalruleMetaNode = CalruleMetaNodes[j];
				//alert("CalruleMetaNode.getAttribute('id'):"+CalruleMetaNode.getAttribute('id')+":CalruleMetaNode.getAttribute('ChName'):"+CalruleMetaNode.getAttribute('ChName'));
				if (CalruleMetaNode.getAttribute('id') == "Link") {
					DTHeader.length = 0;
				} else {
					DTHeader[DTHeader.length] = CalruleMetaNode;

					if (CalruleMetaNode.getAttribute('id') == "Input"
							|| CalruleMetaNode.getAttribute('id') == "InputNodes") {

						composeDTHeader(DTHeader);
					}
				}
				
				//tongmeng 初始化前先做替换
				//getMapValueByKey(tKeyID,tLan)
				
				var tKeyID = CalruleMetaNode.getAttribute('KeyValue');
				if(tKeyID!=null&&tKeyID!='')
				{
					//alert('tKeyID:'+tKeyID+':tLanguage:'+tLanguage);
					var tReplace = getMapValueByKey(tKeyID,tLanguage);
					//alert(tReplace);
					if(tReplace!='')
					{
						
						if(CalruleMetaNode.getAttribute('id')=='BOMItem')
						{
							var tLink = getMapValueByKey('LINK_OF',tLanguage);
							CalruleMetaNode.setAttribute('ChName',tLink+' '+tReplace);
						}
						else
						{
							CalruleMetaNode.setAttribute('ChName',tReplace);
						}
					}
				}
				
				CalspanNode = createIntegritySpanNode(CalruleMetaNode);
				CalruleDiv.appendChild(CalspanNode);

				CalspanNode = createOriginSpanNode("Spacer");
				CalruleDiv.appendChild(CalspanNode);
			}
			CalspanNode = createDeleteButtonNode('1');
			CalruleDiv.appendChild(CalspanNode);
		} catch (e) {
			alert("创建节点时出错！"+e.message);
		}
		CalZone.appendChild(CalruleDiv);
		rowCalCount++;
	}
	
	
	
	CalspanNode = createAddLineButton("1");
	CalZone.appendChild(CalspanNode);
	//-----------------------------------------------------------//
	 var tRES_RESULT = getMapValueByKey('RES_RESULT',tLanguage)
	 if(tRES_RESULT==null||tRES_RESULT=='')
	 {
	 	tRES_RESULT = "结果";
	 }
	ColumnHeadArray[ColumnHeadArray.length] = tRES_RESULT;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	//TableColumnNameArray[TableColumnNameArray.length] = "DTRate";

 var tRES_MULTMSG = getMapValueByKey('RES_MULTMSG',tLanguage)
	 if(tRES_MULTMSG==null||tRES_MULTMSG=='')
	 {
	 	tRES_MULTMSG = "多语言提示信息";
	 }


  ColumnHeadArray[ColumnHeadArray.length] = tRES_MULTMSG;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "Button";
	//TableColumnNameArray[TableColumnNameArray.length] = "RuleId"
	//TableColumnNameArray[TableColumnNameArray.length] = "MultMsg";
/*
  ColumnHeadArray[ColumnHeadArray.length] = "RuleId";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	//TableColumnNameArray[TableColumnNameArray.length] = "RuleId";
	*/
	//TableColumnNameArray[TableColumnNameArray.length] = "DTRate";
	/*
	ColumnHeadArray[ColumnHeadArray.length] = "自核不通过";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";

	ColumnHeadArray[ColumnHeadArray.length] = "转人工核保级别";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	*/
}

function createIntegritySpanNode(xmlNode) {
	var nodeId = xmlNode.getAttribute("id");
	var reSpanNode;
	var paraArray = new Array();

	if (nodeId == "BOM") {
		paraArray = prepareParaArray(xmlNode, BOMIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "BOMItem") {
		paraArray = prepareParaArray(xmlNode, BOMItemIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "Operator") {
		paraArray = prepareParaArray(xmlNode, OperatorIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "Link") {
		paraArray = prepareParaArray(xmlNode, LinkIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "Left_Paren") {
		paraArray = prepareParaArray(xmlNode, ParenIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "Right_Paren") {
		paraArray = prepareParaArray(xmlNode, ParenIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "Input") {
		paraArray = prepareParaArray(xmlNode, InputIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "InputNodes") {
		reSpanNode = createInputNodes();
	}
	else if (nodeId == "Constant") {
		paraArray = prepareParaArray(xmlNode, InputIndex);
		reSpanNode = createSpanNode(paraArray, false);
		//spanNode.setAttribute(paraArray[i].attribute, paraArray[i].value);
		//reSpanNode.setAttribute(')
	}
	
	else if (nodeId == "AddFunction") {
		paraArray = prepareParaArray(xmlNode, AddFunctionIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "FunctionBOM") {
		paraArray = prepareParaArray(xmlNode, FunctionBOMIndex);
		reSpanNode = createSpanNode(paraArray, false);
	} else if (nodeId == "FunctionBOMItem") {
		paraArray = prepareParaArray(xmlNode,FunctionBOMItemIndex);
		reSpanNode = createSpanNode(paraArray, false);
	}
	else if (nodeId == "comma") {
		paraArray = prepareParaArray(xmlNode,CommaIndex);
		reSpanNode = createSpanNode(paraArray, false);
	}
	
	return reSpanNode;
}
function prepareParaArray(xmlNode, IndexArray) {
	var reArray = new Array();

	var i = 0;
	var len = 0;
	var att = '';
	for (i = 0, len = IndexArray.length; i < len; i++) {

		att = xmlNode.getAttribute(IndexArray[i]);
		if (IndexArray[i] == "Source") {
			att = att.replace(new RegExp("''", "gm"), "'");
		}
		reArray[i] = {
			attribute :IndexArray[i],
			value :att
		};
	}
	return reArray;
}

function composeDTHeader(DTHeader) {

	var reArray = new Array();

	var head = '';
	var dataType = '';

	var i = 0;
	for (i = 0; i < DTHeader.length - 1; i++) {
		head += DTHeader[i].getAttribute("ChName");
	}
	ColumnHeadArray[ColumnHeadArray.length] = head;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = DTHeader[i - 1]
			.getAttribute("MatchType");
}

function initDataArray() {
	for ( var i = 0; i < 10; i++) {
		data[i] = [];
	}
}
//-----------------------------------------------------------------------------------------------------//

//tongmeng 2010-12-13 Add
//计算型规整引擎定义修改

function showOrHideMenuForCal() {
	//alert(spanCalNodeEnabled);
	if (spanCalNodeEnabled) {
		if (!isCalMenuUnfolded)// 菜单未打开
		{
			unCalFoldMenu(); // 展开菜单
			isCalMenuUnfolded = true;// 设置记录标识为打开状态
		} else {
			foldCalMenu(); // 折叠菜单
			isCalMenuUnfolded = false;// 设置记录标识为折叠状态
		}
	}
}

function unCalFoldMenu() {
	// 创建“DIV”节点、“新条件”节点、”增加条件“节点

	var nextNode = document.getElementById("calConditions").nextSibling;

	if (hasCalRule) {
		nextNode.style.display = '';
	} else {
		var divNode = document.createElement("div");
		divNode.setAttribute("id", "CalZone");
		var newLine = createNewAditionLine("1");
		var buttonNode = createAddLineButton("1");
		// 获取当前节点的父节点以及下一个兄弟节点
		var pareNode = getSrcElemnt(getEvent()).parentNode;
		var nextNode = getSrcElemnt(getEvent()).nextSibling;
		// 将“条件”节点以及“增加条件”节点插入到“form”节点之后

		divNode.appendChild(newLine);
		divNode.appendChild(buttonNode);
		// 将form节点插入到当前节点之后
		pareNode.insertBefore(divNode, nextNode);
		hasCalRule = true;
	}
}

// 折叠菜单函数
function foldCalMenu() {
	// 获取当前节点的父节点和下一个兄弟节点
	var nextNode = getSrcElemnt(getEvent()).nextSibling;
	nextNode.style.display = 'none';
}

//tongmeng 2011-08-16 Add
//使用新的拼接SQL算法的方法 
//calflag  0-条件 1-算法
function completeStack(calflag)
{
	//循环堆栈
	var tBOMName = "";
	var tBOMVirtualFlag = "";
	var tBOMItemName = "";
	var tInputType="";
	var tCurrentNodeArray = new Array();
	
	var tOperator= "";
	
	var tSQLStrArray = new Array();
	if(calflag==1)
	{
		tCurrentNodeArray = spanCalNodeArray;
	}
	else
	{
		tCurrentNodeArray = spanNodeArray
	}
	//alert("tCurrentNodeArray.length:"+tCurrentNodeArray.length+":spanCalNodeArray:"+spanCalNodeArray.length+":spanNodeArray:"+spanNodeArray.length);
	for ( i = 0 ;i < tCurrentNodeArray.length; i++) {
		var spanNode = tCurrentNodeArray[i];
		
		if (!(!!spanNode))
			continue;
			
		var pushFlag = true;
		
		var tNodeType = spanNode.id;
		var tNodeEnName =  spanNode.getAttribute('EnName');
		
	//	alert("i:"+i+":tNodeEnName："+tNodeEnName+":tNodeType:"+tNodeType);
	//	if(!(spanNode.id == "Input" || spanNode.id == "InputNodes")&&(tNodeEnName==null||tNodeEnName==''))
	//	{
	//		continue;
	//		}
		var tPushStr = tNodeEnName;
		var tVirtualFlag = "0";
		tVirtualFlag = spanNode.getAttribute('VirtualFlag');
		if(tVirtualFlag==null||tVirtualFlag=='')
		{
			tVirtualFlag = "0";
		}
		if(tNodeType=='BOM'||tNodeType=='FunctionBOM')
		{
			tBOMName = tNodeEnName;
			tBOMVirtualFlag = tVirtualFlag;
			//if(tBOMVirtualFlag=='0')
			{
				if(calflag==1){
					CalColumnMetasChArray[CalColumnMetasChArray.length] = spanNode.getAttribute("ChName");
				}
				else{
					ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute("ChName");
				}
			}
		
			pushFlag = false;
		}
		else if(tNodeType=='BOMItem'||tNodeType=='FunctionBOMItem')
		{
			tBOMItemName = tNodeEnName;
			var tBOMItemType = spanNode.getAttribute("MatchType");
			
			var tTempPuhStr = "";
			//alert("tBOMVirtualFlag:"+tBOMVirtualFlag);
			if(tBOMVirtualFlag=='0')
			{
				tTempPuhStr = "?"+tBOMName+"."+tBOMItemName+"?";
			}
			else
			{
				tTempPuhStr =  tBOMName+"."+tBOMItemName;
			}
			//if(tBOMVirtualFlag=='0')
			{
				if(calflag==1){
					CalColumnMetasChArray[CalColumnMetasChArray.length] = spanNode.getAttribute("ChName");
				}
				else{
					ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute("ChName");
				}
			}
			if(tBOMVirtualFlag=='0')
			{
				getBOMAndBOMItem(tBOMName+"_"+tBOMItemName);
			}
			/*
			if (BOMItemType == 'Date') {
			temp = lparenStr + "to_date(?" + outCol.replace('_', '.')
					+ "?,'yyyy-mm-dd hh24:mi:ss')" + rparenStr;
		} else {
			temp = lparenStr + '?' + outCol.replace('_', '.') + '?' + rparenStr;
		}
			*/
			
			if(tBOMItemType== 'Date')
			{
				tTempPuhStr = "to_date"+"("+tTempPuhStr+","+"'yyyy-mm-dd hh24:mi:ss'"+")";
			}
			
			else if(tBOMItemType== 'String')
			{
				tTempPuhStr = "'"+tTempPuhStr+"'";
			}
			
			tPushStr = tTempPuhStr;
			
			//tBOMName = "";
			//tBOMItemName = "";
		}
		 else if (spanNode.id == "Operator") {
			if(calflag==1){
				CalColumnMetasChArray[CalColumnMetasChArray.length] = spanNode.getAttribute("ChName");
			}
			else{
				ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute("ChName");
			}
			tInputType = spanNode.getAttribute("ParaType");
			tOperator =  spanNode.getAttribute('EnName');
			var indexLeft = -1;
			if(tOperator != null) indexLeft = tOperator.indexOf('#');
			
			
			var temp = "?"+tBOMName+"."+tBOMItemName+"?";
			if (indexLeft >= 0 && temp != '') {
			tPushStr = tPushStr.replace('#', temp);
			tSQLStrArray.length = tSQLStrArray.length-1;
			
			}
			
		}
		else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
			var m = getRecur();
			var indexRight = tOperator.indexOf('@');
			if(indexRight>0)
			{
				var temp = tSQLStrArray[tSQLStrArray.length-1];
				tPushStr = temp.replace('@', DTColumnName + m);
				tSQLStrArray.length = tSQLStrArray.length-1;
				
				if (tPushStr.indexOf('ManageCom') >= 0) {
				tPushStr = tPushStr.replace('$', 1);
				} else {
					tPushStr = tPushStr.replace('$', 0);
				}
				
				if (tInputType != '') {

				if (tInputType == 'Date') {
					reCreateTable = DTColumnName + m + ' Date';
				} else if (tInputType == 'INT' || tInputType == 'Number') {
					reCreateTable = DTColumnName + m + ' Number';
				} else if (tInputType == 'String') {
					reCreateTable = DTColumnName + m + ' varchar2(1000)';
				}
					TableColumnNameArray[TableColumnNameArray.length] = (DTColumnName + m)
							.toUpperCase();
					//tInputType = "";
					
					
					if (reCreateTable != "") {
							CreateTable += reCreateTable + ",";
							
						
					}
						//alert('CreateTable:'+CreateTable);
	
				}
				
				
			}
			else
			{	
				
				tPushStr = DTColumnName + m;
				
				if (tInputType != '') {

				if (tInputType == 'Date') {
					reCreateTable = DTColumnName + m + ' Date';
				} else if (tInputType == 'INT' || tInputType == 'Number') {
					reCreateTable = DTColumnName + m + ' Number';
				} else if (tInputType == 'String') {
					reCreateTable = DTColumnName + m + ' varchar2(1000)';
				}
					TableColumnNameArray[TableColumnNameArray.length] = (DTColumnName + m)
							.toUpperCase();
					//tInputType = "";
					
					
					if (reCreateTable != "") {
							CreateTable += reCreateTable + ",";
							
						
					}
						//alert('CreateTable1:'+CreateTable);
	
				}
			}
			
			if(calflag==0)
			{
				var disColCh = "";
				for ( var j = 0; j <ColumnMetasChArray.length; j++) {
					disColCh += ColumnMetasChArray[j];
				}
			
				if (disColCh != '') {
					ColumnHeadArray[ColumnHeadArray.length] = disColCh;
					ColumnDataTypeArray[ColumnDataTypeArray.length] = tInputType;
				}
			}
		}
		else if(spanNode.id=='Constant')
		{
				if(!isNumeric(tPushStr))
				{
					tPushStr = "'" + tPushStr + "'";
				}
		}
		/*
		 else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
			var rArray = comCal(lparenStr, outCol, rparenStr, opStr, opStr1,
					'isInput', InputType, BOMItemType);
			finished = true;
			for ( var j = 0; j < CalColumnMetasChArray.length; j++) {
				disColCh += CalColumnMetasChArray[j];
			}
			CalColumnMetasChArray.length = 0;
		}
		*/
		
		//alert("calflag:tPushStr:"+calflag+":"+tPushStr);
			
			if(pushFlag)
			{
			
			tSQLStrArray[tSQLStrArray.length]=tPushStr;
			/*	if(calflag=='1')
				{
						CalSQLStatement += ' ' + tPushStr;
				}
				else 
				{
					SQLStatement += ' ' + tPushStr;
				}
			*/
			}
		
		}
	
	for(i=0;i<tSQLStrArray.length;i++)
	{
		if(calflag=='1')
				{
						CalSQLStatement += ' ' + tSQLStrArray[i];
				}
				else 
				{
					SQLStatement += ' ' + tSQLStrArray[i];
				}
	}
	
	if(calflag==1)
	{
		spanCalNodeArray.length=0;
		spanCalNodeArray = new Array();
		
	}
	else
	{
		spanNodeArray.length=0;
		spanNodeArray = new Array();
	}
	
	//alert("###:"+SQLStatement);
}


//拼写计算算法SQL
function comACalSQL() {
	
	var reStr = '';
	var str = '';
	var spanNode = null;
	var outCol = '';
	var op = '';
	var finished = false;
	// 用于记录扫描到节点的英文名
	var linkStr = '';
	var lparenStr = '';
	var rparenStr = '';
	var BOMStr = '';
	var BOMItemStr = '';
	var opStr = '';
	var opStr1 = '';
	// 用于记录扫描到节点的中文名
	var linkStrCh = '';
	var lparenStrCh = '';
	var rparenStrCh = '';
	var BOMStrCh = '';
	var BOMItemStrCh = '';
	var opStrCh = '';

	var disColCh = '';
	var BOMItemType = "";
	var InputType = "";
	var reCreateTable = '';

	var BOMNode = '';
	var BOMItemNode = '';

	var pareNumber = 0;
	
	//tongmeng 2010-12-24 Add
	var otherRule = '';
	// aler("堆栈stack的长度是："+stack.length);
	for ( var i = 0, len = spanCalNodeArray.length; i < len; i++) {
		var spanNode = spanCalNodeArray[i];
		if (!(!!spanNode))
			continue;
			
			//alert("spanNode.id:"+spanNode.id+":EnName:"+spanNode.getAttribute('EnName')+"CalSQLStatement:"+CalSQLStatement);
			
		if (spanNode.id == "Link") {
			//???
			comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			CalSQLStatement += ' ' + spanNode.getAttribute('EnName') + ' ';
		}
		if (spanNode.id == "AddFunction") {
				comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			CalSQLStatement += ' ' + spanNode.getAttribute('EnName');
			//pareNumber += 1;
		} 
		
		if (spanNode.id == "comma") {
			comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			CalSQLStatement += spanNode.getAttribute('EnName');
			//pareNumber += 1;
		} 
		if (spanNode.id == "OTHERRULE" ) {
			//???
			otherRule = spanNode.getAttribute('EnName');
			comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType,otherRule);
			//CalSQLStatement += '^' + spanNode.getAttribute('EnName') + '^';
		}
		
		
		 else if (spanNode.id == "Left_Paren") {
			if (lparenStr != '') {
				CalSQLStatement += spanNode.getAttribute('EnName');
			}
			lparenStr = spanNode.getAttribute('EnName');
			pareNumber += 1;
		} else if (spanNode.id == "Right_Paren") {
			rparenStr = spanNode.getAttribute('EnName');
			pareNumber -= 1;
			comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isConstant',
					InputType, BOMItemType,otherRule);
		} else if (spanNode.id == "BOM"||spanNode.id == "FunctionBOM") {
			outCol += spanNode.getAttribute('EnName');
			BOMNode = spanNode.getAttribute('EnName');
			CalColumnMetasChArray[CalColumnMetasChArray.length] = spanNode.getAttribute("ChName");
		} else if (spanNode.id == "BOMItem"||spanNode.id == "FunctionBOMItem") {
			outCol += '_' + spanNode.getAttribute('EnName');
			BOMItemNode = spanNode.getAttribute('EnName');
			BOMItemType = spanNode.getAttribute("MatchType");

			CalColumnMetasChArray[CalColumnMetasChArray.length] = spanNode.getAttribute("ChName");
		} else if (spanNode.id == "Operator") {
			if (opStr == '') {
				opStr = spanNode.getAttribute('EnName');
			} else {
				opStr1 = spanNode.getAttribute('EnName');
			}
			InputType = spanNode.getAttribute("ParaType");
			CalColumnMetasChArray[CalColumnMetasChArray.length] = spanNode.getAttribute("ChName");
		}
		else if (spanNode.id == "Constant" ) {
			//???
			otherRule = spanNode.getAttribute('EnName');
			comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isConstant',
					InputType, BOMItemType,otherRule);
			//CalSQLStatement += '^' + spanNode.getAttribute('EnName') + '^';
		}
		 else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
			var rArray = comCal(lparenStr, outCol, rparenStr, opStr, opStr1,
					'isInput', InputType, BOMItemType);
			finished = true;
			for ( var j = 0; j < CalColumnMetasChArray.length; j++) {
				disColCh += CalColumnMetasChArray[j];
			}
			CalColumnMetasChArray.length = 0;
		}
	}
	if (!finished) {
		comCal(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM', InputType,
				BOMItemType);
	}
	spanCalNodeArray.length = 0;

//alert("com:"+CalSQLStatement);
	/*
	 * if (reCreateTable != '') { CreateTable+= reCreateTable; }
	 */
	 
	 /*
	if (disColCh != '') {
		ColumnHeadArray[ColumnHeadArray.length] = disColCh;
		ColumnDataTypeArray[ColumnDataTypeArray.length] = InputType;
	}
	*/
}


/*
 * 函数传入参数分别是： lparenStr：左括号的英文名 outCol： 格式为BOM.BOMItem rparenStr：右括号的英文名 opStr：
 * 运算符的英文名 flag： 是否需要生成决策表中的一列的标识，Input表示要，BOM表示不要 dataType：
 * BOM.BOMItem中BOMItem的数据类型，用于falg为Input时需要创建决策条表的情况 函数返回值：函数的返回值是一个数组（reArray）
 * reArray[0]携带拼写好的SQLStatement reArray[1]携带拼写好的createTable
 */

function comCal(lparenStr, outCol, rparenStr, opStr, opStr1, flag, InputType,
		BOMItemType,otherRule) {
			
			//alert('InputType:'+InputType+':1BeforeCalSQLStatement'+CalSQLStatement);
	var reStr = '';
	var reCreateTable = '';
	var temp = '';

	if (outCol != '') {
		getBOMAndBOMItem(outCol);
	}

	if (lparenStr != '' && rparenStr != '') {
		lparenStr = '';
		rparenStr = '';
	}
	if (outCol != '') {
		if (BOMItemType == 'Date') {
			temp = lparenStr + "to_date(?" + outCol.replace('_', '.')
					+ "?,'yyyy-mm-dd hh24:mi:ss')" + rparenStr;
		} else {
			temp = lparenStr + '?' + outCol.replace('_', '.') + '?' + rparenStr;
		}

	} else {
		reStr += lparenStr + rparenStr;
	}
	var indexLeft = 0;
	var indexRight = 0;
	
	try{
	 indexLeft = opStr.indexOf('#');
	 indexRight = opStr.indexOf('@');
  }
	catch(e)
	{
		indexLeft = -1;
		indexRight = -1;
	}
	if (flag == 'isInput') {
		var i = getRecur();

		if (opStr.indexOf('$' >= 0)) {
			if (outCol.indexOf('ManageCom') >= 0) {
				opStr = opStr.replace('$', 1);
			} else {
				opStr = opStr.replace('$', 0);
			}
		}
		var hasTwo = false;
		if (indexLeft >= 0 && temp != '') {
			opStr = opStr.replace('#', temp);
			if (opStr1 != '') {
				hasTwo = true;
			}

			if (indexRight >= 0) {

				opStr = opStr.replace('@', DTColumnName + i);

				reStr += opStr + " ";
			} else {
				reStr += opStr + opStr1 + " " + DTColumnName + i + ' ';
			}
		} else {
			reStr += temp + " " + opStr + " " + DTColumnName + i + ' ';
		}
		if (InputType != '') {

			if (InputType == 'Date') {
				reCreateTable = DTColumnName + i + ' Date';
			} else if (InputType == 'INT' || InputType == 'Number') {
				reCreateTable = DTColumnName + i + ' Number';
			} else if (InputType == 'String') {
				reCreateTable = DTColumnName + i + ' varchar2(1000)';
			}
			TableColumnNameArray[TableColumnNameArray.length] = (DTColumnName + i)
					.toUpperCase();
		}
	}
	else if (flag == 'isConstant') {
		//alert('temp:opStr1:'+temp+':'+opStr1);
		reStr+= otherRule;
	}
	 else if(otherRule!=undefined && otherRule!=null &&flag!='isConstant')
	 {
	 	 reStr+= '^'+otherRule+'^';
	 }
	 else {
		if (indexLeft < 0 && indexRight < 0 && opStr1 == '') {
			reStr += temp + " " + opStr;
		} else {
			if (indexLeft >= 0 && temp != '') {
				temp = opStr.replace('#', temp);
			}
			reStr += temp + " " + opStr1;
		}

	}
	CalSQLStatement += reStr;
	if (reCreateTable != "") {
		CreateTable += reCreateTable + ",";
	}
	
	//alert('EndCalSQLStatement:'+CalSQLStatement);
}


function composeCalRuleDesInCh() {
	var spanNode;
	for ( var i = 0, len = MetaCalNodeChNameArray.length; i < len; i++) {
		spanNode = MetaCalNodeChNameArray[i];
		if(spanNode.id == "Operator" &&spanNode.getAttribute("ChName") =="请选择操作符")
		{
			 continue;
		}
		if (spanNode.id != "Input" && spanNode.id != "InputNodes") {
			//CalRuleDesInCh += spanNode.getAttribute("ChName");
			CalRuleDesInCh += "?"+spanNode.getAttribute("KeyValue")+"?";
		} else {
			CalRuleDesInCh += "#，";
		}
	}
}