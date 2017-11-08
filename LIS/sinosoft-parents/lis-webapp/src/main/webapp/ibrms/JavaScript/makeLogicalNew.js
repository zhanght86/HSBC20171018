var tResourceName_Logic = 'ibrms.makeLogicalNewSql';
var rowCount = 0; // �����߼����е�����
var isMenuUnfolded = false; // "���"�Ƿ��ڱ����֮��չ�������˵�
var preEventNode = null; // ���������������¼��Ľڵ�
var spanNodeEnabled = true;// �ڵ��ܷ���Ӧ������¼��ı�־

var InputNodeEnabled = false;
// ���ڱ�ʶ��Ӧ�ڵ���ҪЯ�������ݽṹ
var BOMIndex = new Array('id', 'EnName', 'ChName','KeyValue','VirtualFlag');
var BOMItemIndex = new Array('id', 'EnName', 'ChName', 'MatchType', 'Source',
		'isHierarchical','KeyValue','VirtualFlag');
		
var FunctionBOMIndex = new Array('id', 'EnName', 'ChName','ParaType','FunctionName','KeyValue');
var FunctionBOMItemIndex = new Array('id', 'EnName', 'ChName', 'MatchType', 'Source',
		'isHierarchical','ParaType','FunctionName','KeyValue');
var CommaIndex = new Array('id', 'EnName', 'ChName','KeyValue');		
//tongmeng 2011-02-07 add
//����������Ĳ�������
var OperatorIndex = new Array('id', 'EnName', 'ChName', 'MatchType',
		'ResultType', 'ParaType', 'IsNextOperator','KeyValue');
//tongmeng 2011-04-14 add
		
var AddFunctionIndex = new Array('id', 'EnName', 'ChName', 'MatchType',
		'ResultType', 'ParaType', 'IsNextOperator','CommType','CommDetail','FunctionName','KeyValue');
				
var LinkIndex = new Array('id', 'EnName', 'ChName','KeyValue');
var ParenIndex = new Array('id', 'EnName', 'ChName');
var InputIndex = new Array('id', 'EnName', 'ChName','KeyValue');
// //���������������
var LinkArray = new Array();
LinkArray[0] = new Array("and", "����","LINK_AND");
LinkArray[1] = new Array("or", "����","LINK_OR");
// ��ʾ"���"�����Ƿ��й���
var hasRule = false;
// ��̨��ѯ����
var queryCache = new Array();

var DTColumnName='Column';
var reCurr=0;
var SQLStatement;// �洢�����SQL
var CreateTable;// �洢��������SQL
var ViewPara;// �洢������ʾ����Ҫ�Ĳ���
var BOMSArray = new Array();// �洢�������õ�BOM
var SQLParaArray = new Array();// �洢���򷴽����Ĳ���
var RuleDesInCh;// �洢�����������
var ColumnHeadArray = new Array();// �洢���߱���ͷ��Ҫ��ʾ��������
var ColumnMetasChArray = new Array();// �洢���й������ĵĵ�����
var ColumnDataTypeArray = new Array();// �洢���߱��е���������
var TableColumnNameArray = new Array();// �洢DT���е��������ھ��߱��������е�Index
var ColumnMultipleArray = new Array();// �洢���߱�����������Ƿ�Ϊ������֮һ��
var BaseColumnArray = new Array();// �洢���߱�����Ƿ�Ϊ��������
var MetaNodeChNameArray = new Array();// �洢ɨ�赽�ڵ��������
var spanNodeArray = new Array();// �洢����ƴд��ɨ�赽�Ľڵ�
var InputNodeArray = new Array();// �洢�������߼���Input�ڵ�
var BaseBOMItemSourceArray = new Array();
var InputNodes = new Array();
var DTColumnRecurArray = new Array();// �洢��������������ظ��Ĵ���


//----------------------------------------------------------------------------//
//tongmeng 2010-12-14 add
var spanCalNodeEnabled = true;// �ڵ��ܷ���Ӧ������¼��ı�־
var isCalMenuUnfolded = false; // "����"�Ƿ��ڱ����֮��չ�������˵�
var hasCalRule = false;
var rowCalCount = 0; // �����㷨���е�����
var spanCalNodeArray = new Array();// �洢�㷨ƴд��ɨ�赽�Ľڵ�
var CalColumnMetasChArray = new Array();// �洢���й������ĵĵ�����
var MetaCalNodeChNameArray = new Array();// �洢ɨ�赽�ڵ��������
var CalSQLStatement="";// �洢�㷨��SQL
var CalRuleDesInCh;// �洢�����������

//OTHERRULE
var OTHERRULEIndex = new Array('id', 'EnName', 'ChName');


//ָ����
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
	// �ڵ�����������ʱ����Ӧ����
	if (!spanNodeEnabled) {
		return;
	}
	// �ڵ�������б�壬��������ͣ�������˸��
	else {
		var ob = getSrcElemnt(getEvent());
		ob.style.fontStyle = "italic";
		ob.style.cursor = 'hand';
		ob.style.textDecoration = 'blink';
	}
}

function normalLight() {
	// �ڵ�����������ʱ����Ӧ����
	if (!spanNodeEnabled) {
		return;
	}
	// �ڵ������������壬�����Ĭ���ͣ�����˸��
	else {
		var ob = getSrcElemnt(getEvent());
		ob.style.fontStyle = "normal";
		ob.style.cursor = 'default';
		ob.style.textDecoration = '';
	}
}

function showOrHideMenu() {
	if (spanNodeEnabled) {
		if (!isMenuUnfolded)// �˵�δ��
		{
			unFoldMenu(); // չ���˵�
			isMenuUnfolded = true;// ���ü�¼��ʶΪ��״̬
		} else {
			foldMenu(); // �۵��˵�
			isMenuUnfolded = false;// ���ü�¼��ʶΪ�۵�״̬
		}
	}
}

function unFoldMenu() {
	// ������DIV���ڵ㡢�����������ڵ㡢�������������ڵ�

	var nextNode = document.getElementById("conditions").nextSibling;

	if (hasRule) {
		nextNode.style.display = '';
	} else {
		var divNode = document.createElement("div");
		divNode.setAttribute("id", "RuleZone");
		var newLine = createNewAditionLine("0");
		var buttonNode = createAddLineButton("0");
		// ��ȡ��ǰ�ڵ�ĸ��ڵ��Լ���һ���ֵܽڵ�
		var pareNode = getSrcElemnt(getEvent()).parentNode;
		var nextNode = getSrcElemnt(getEvent()).nextSibling;
		// �����������ڵ��Լ��������������ڵ���뵽��form���ڵ�֮��

		divNode.appendChild(newLine);
		divNode.appendChild(buttonNode);
		// ��form�ڵ���뵽��ǰ�ڵ�֮��
		pareNode.insertBefore(divNode, nextNode);
		hasRule = true;
	}
}

// �۵��˵�����
function foldMenu() {
	// ��ȡ��ǰ�ڵ�ĸ��ڵ����һ���ֵܽڵ�
	var nextNode = getSrcElemnt(getEvent()).nextSibling;
	nextNode.style.display = 'none';
}

// ��������������
function createNewAditionLine(tType) {
	var divNode = document.createElement("div");// ����һ��"div"�ڵ�(һ����������һ����,��"div"��ʶ)
	// ���������ǵ�һ��������ʱ��������Ӵ�
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
		// �����ǿ��нڵ�
		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);
		var spanNode = createOriginSpanNode('BOM', false);
		divNode.appendChild(spanNode);

		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);

		var buttonNode = createDeleteButtonNode(tType); // ����һ������ɾ���������İ�ť�ڵ�
		divNode.appendChild(buttonNode);
		rowCount++;
	}
	else
	{
		//if (rowCalCount != 0) {
			//tongmeng 2011-01-21 modify
			//����㷨,��������Ӵ� 
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
		// �����ǿ��нڵ�
		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);
		var spanNode = createOriginSpanNode('BOM', false);
		divNode.appendChild(spanNode);

		var spacerNode = createOriginSpanNode('Spacer');
		divNode.appendChild(spacerNode);

		var buttonNode = createDeleteButtonNode(tType); // ����һ������ɾ���������İ�ť�ڵ�
		divNode.appendChild(buttonNode);
		rowCalCount++;
	}
	
	return divNode;
}
// ����һ��ɾ���������İ�ť
function createDeleteButtonNode(tType) {
	var buttonNode = document.createElement("input");
	// ���Button�ڵ�������Ӧ�¼�
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
// ����һ���ܹ�����һ���������İ�ť
function createAddLineButton(tType) {
	var buttonNode = document.createElement("input");
	// ���Button�ڵ�������Ӧ�¼�
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

// ����һ��������
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
	spanNodeEnabled = false;// �ڵ��ܷ���Ӧ������¼��ı�־

}

function enableInputNodes() {
	InputNodeEnabled = true;
}

function enableSpanNodes() {
	spanNodeEnabled = true;// �ڵ��ܷ���Ӧ������¼��ı�־

}
/*
 * ����Span�ڵ�ĺ����Ǳ�������ص� Span�ڵ�����ݽṹ���£� id:
 * ���ڱ�ʶ�ڵ�����ͣ�����BOM��BOMItem��Operator��Pathesis��Input��Link EnName�����ڼ�¼�ڵ��Ӣ����
 * ChName: ���ڼ�¼�ڵ�������� MatchType: ���ڼ�¼������������ƥ�����ͣ� ParaType: ���ڼ�¼�������Я���Ĳ������ͣ�
 * Source�����ڼ�¼����������ȡֵ��Դ�� ResultType�����ڼ�¼�����������������
 * IsNextOperator�����ڼ�¼����������ܷ��������ı�־ isMenu�ǽڵ��Ƿ�Ϊ�����˵��нڵ�ı�־��true��ʾ���ǡ���false��ʾ����
 */
function createSpanNode(paraArray, isMenu) {

	var spanNode = document.createElement("span");
	//spanNode.style.float = "left";

	var nodeType = '';
	var displayText = '';

	// ���"spanNode"�ڵ������,���������Ӧ�¼�
	if (!(!!paraArray)) {
		alert('�����ڵ㴫�ݵĲ�������');
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
	if (!isMenu) // �ж�
	{
		// �����
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
	// ���������"Text"����
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
			alert("����ɾ����һ���ڵ㣡");
			return;
		} else {
			pareNode.removeChild(obNode.previousSibling);
			pareNode.removeChild(obNode);
		}
	}
}
function popMenu() {
	console.log("popMenu");
	// ��¼�������Դ�ڵ�
	preEventNode = getSrcElemnt(getEvent());
	var spanArray = new Array();
	var clickedNode = getSrcElemnt(getEvent());
	// �ж���걻��������Ҽ�
	var mouseButton = getEvent().button;
	// �жϱ�����ڵ������
	var Id = clickedNode.id;
	if (!Id || Id == 'Spacer') {
		return;
	}
	// ������������״̬������Ӧ����¼�
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
		if (mouseButton == leftButton())// ������
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
			//���Ӻ����Ĵ���
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
		} else if (mouseButton == 2)// ����һ�
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
				//���ӶԺ�����֧��
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
	// ������Input�ڵ�ת����Span�ڵ�
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
				// �������ֵ�ǻ�������
				if (!!BOMItemNode.getAttribute("Source")) {
					var InputMenu = new Array();
					var strSQL = BOMItemNode.getAttribute("Source");
					InputMenu = getInputSource(strSQL);
					var spanArray = prepareBaseMenu(InputMenu);
					displayMenu(spanArray);
				}
				// �������ֵ���ǻ�������
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
	// ������Input�ڵ�ת����Span�ڵ�
	convertInputsToSpans();
	var Id = clickedNode.id;
	if (mouseButton == leftButton()) {
		if (!InputNodeEnabled)// '������һ��ֵ'�ڵ��ڹ����߼����ƹ����в���Ӧ����������
		{
			return;
		} else // '������һ��ֵ'�ڵ�������¼���������Ӧ�������¼�
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
				// �������ֵ�ǻ�������
				if (!!BOMItemNode.getAttribute("Source")) {
					var InputMenu = new Array();
					var strSQL = BOMItemNode.getAttribute("Source");
					InputMenu = getInputSource(strSQL);
					var spanArray = prepareBaseMenu(InputMenu);
					displayMenu(spanArray);
				}
				// �������ֵ���ǻ�������
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
						alert("��������ȷ�����ָ�ʽ��");
						return;
					}

				} else if (type == "Date") {
					if (!dateRegExp.test(inputNode.value)) {
						alert("��������ȷ�����ڸ�ʽ��");
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
						alert("��������ȷ�����ָ�ʽ��");
						return;
					}

				} else if (type == "Date") {
					if (!dateRegExp.test(inputNode.value)) {
						alert("��������ȷ�����ڸ�ʽ��");
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

function handleClick()// ���������еĽڵ�ȡ���¼�Դ�ڵ㣬���ڱ�Ҫʱ����һЩ�½ڵ�
{
	var srcNode = getSrcElemnt(getEvent());
	//alert("srcNode.getAttribute("EnName"):preEventNode.getAttribute("EnName")###"+srcNode.getAttribute("EnName")+":"+preEventNode.getAttribute("EnName"));
	//alert('handleClick srcNode.id:'+srcNode.id);
	
	if (srcNode.getAttribute("EnName") != preEventNode.getAttribute("EnName")) {
		// ���½ڵ���Ϣȡ��ԭ�¼��ڵ���Ϣ
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
		//����֧�ֺ���
		else if (srcNode.id == "AddFunction") {
			deleteNodesAfter(preEventNode);
			appendNewNodes('AddFunction', preEventNode, srcNode);
		}
			//tongmeng 2011-04-14 Add
		//���Ӳ���BOM�Ͳ���BOMItem
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
	// ��Ҫ���ʵ�������������
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
//���������ڵ㹦��
//function createFunctionSpanNodes()


function appendNewNodes(id, preEventNode, srcNode) {
	var Id = id;
	var obNode = preEventNode;
	var srNode = srcNode;
	if (Id == 'BOM') {
		if (preEventNode.nextSibling.id != 'BOMItem') {
			var fatherNode = preEventNode.parentNode;
			// ����һ��BOMItem�ڵ�
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
	
		
		
		// ����һ��BOMItem�ڵ�
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
			// ����һ��BOMItem�ڵ�
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
	
		
		
		// ����һ��BOMItem�ڵ�
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
//���Ӻ���

 else if (Id == 'AddFunction') {
		
			//tongmeng test
			var tAddParam = "";
			//alert("srcNode.id:"+srcNode.id+"getAttribute:"+""+srcNode.getAttribute('CommType')+":value:"+srcNode.getAttribute('EnName'));
			//�˴���Ҫ���ղ��������������ͳ�ʼ��
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
			
			//��������
			//��������
			var spanNode = null;
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			
			//���������
			spanNode = createOriginSpanNode('Operator');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
			spanNode = createOriginSpanNode('Spacer');
			spanNode.setAttribute('FunctionName',tFunctionName);
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			
				
			spanNode = createOriginSpanNode('Right_Paren');
			spanNode.setAttribute('FunctionName',tFunctionName);
			//tongmeng 2011-03-22 ����������,��������㴦��
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
				//����BOM��ѡ��
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
			//tongmeng 2011-03-22 ����������,��������㴦��
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
	//tongmeng �˴���Ҫ���Ӷ�ָ�����֧��
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
				tLINK_CHOOSEBOM = '��ѡ�����';
			}
			
			
		paraArray[1] = {
			attribute :'ChName',
			value :tLINK_CHOOSEBOM
		};
	} 
	//tongmeng 2010-12-17 Add
	//֧��ѡ����������
	else if (id == 'OTHERRULE') {
		paraArray[0] = {
			attribute :'id',
			value :'OTHERRULE'
		};
			var tLINK_CHOOSEBOM = getMapValueByKey('LINK_CHOOSEBOM',tLanguage);
			if(tLINK_CHOOSEBOM==null||tLINK_CHOOSEBOM=='')
			{
				tLINK_CHOOSEBOM = '��ѡ�����';
			}
			
		paraArray[1] = {
			attribute :'ChName',
			value :'ѡ����������'
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
				tLINK_CONS = 'һ������';
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
	//���Ӻ���
	else if (id == 'AddFunction') {
		paraArray[0] = {
			attribute :'id',
			value :'AddFunction'
		};
		
		var tLINK_FUNC = getMapValueByKey('LINK_FUNC',tLanguage);
		if(tLINK_FUNC==null||tLINK_FUNC=='')
			{
				tLINK_FUNC = '����';
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
				tIF = '��';
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
				topt = '��ѡ�������';
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
				tLINK_INPUT = '������ֵ';
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
				tLINK_AND = '����';
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
			value :'��'
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
			value :'ɾ��'
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
			alert("ϵͳ����BOM��ȡ���ɹ���");
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
			alert("ϵͳ����BOM��ȡ���ɹ���");
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
	//���Ӻ���
	else if (Id == 'AddFunction') {
		var infArray = new Array();
		//getNodeInformation ���޸�
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
	//δ�޸����
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
			alert("ϵͳ�������������ȡ���ɹ���");
		}
	}
	return reSpanArray;

}
/*
 * 01�������������ţ� 02�������������ţ� 03����ɾ���ڵ㱾�� 04����BOMת����Input 05����Inputת����BOM 06�������������
 * 07������������ 08��������Input�ڵ� 09����BOM
 */
function prepareMenu_Right(id) {
	var Id = id;
	var reArray = new Array();
	var menuOption = null;

	var obNode = preEventNode;
	//alert('Id:'+Id);
	if (Id == 'BOM') {
		// ���'('
		menuOption = createMenuOption('01');
		reArray[reArray.length] = menuOption;
		// ɾ��BOM����BOMת����Input
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
			//ע�͵��˴�,���㷨�޸�Ϊʹ��bom�ķ�ʽ��ʼ��
			//menuOption = createMenuOption('10');
			//reArray[reArray.length] = menuOption;
			
			//tongmeng 2010-12-27 add
			//֧�ֳ�����¼��
			menuOption = createMenuOption('11');
			reArray[reArray.length] = menuOption;
			
		}
		//tongmeng 2011-04-14
			//֧�ֺ���
			menuOption = createMenuOption('12');
		  reArray[reArray.length] = menuOption;
	} else if (Id == 'BOMItem') {
		// ��ӡ�(��
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		// �������������������
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
		// ���'('
		//menuOption = createMenuOption('01');
		//reArray[reArray.length] = menuOption;
		// ɾ��BOM����BOMת����Input
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
			//ע�͵��˴�,���㷨�޸�Ϊʹ��bom�ķ�ʽ��ʼ��
			//menuOption = createMenuOption('10');
			//reArray[reArray.length] = menuOption;
			
			//tongmeng 2010-12-27 add
			//֧�ֳ�����¼��
			menuOption = createMenuOption('11');
			reArray[reArray.length] = menuOption;
			
			//tongmeng 2011-08-10
			//֧�ֺ���
			//menuOption = createMenuOption('12');
		  //reArray[reArray.length] = menuOption;
			
		}
		//tongmeng 2011-04-14
			//֧�ֺ���
			menuOption = createMenuOption('12');
		  reArray[reArray.length] = menuOption;
		  
		  //tongmeng 2011-08-16 add
		  //֧����չ��������
		  menuOption = createMenuOption('13');
		  reArray[reArray.length] = menuOption;
		  
		  
	} else if (Id == 'FunctionBOMItem') {
		// ��ӡ�(��
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		// �������������������
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
		// ��ӡ�(��
		menuOption = createMenuOption('10');
		reArray[reArray.length] = menuOption;
		// �������������������
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
		// ��ӡ�(��
		menuOption = createMenuOption('10');
		reArray[reArray.length] = menuOption;
		// �������������������
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
		//ɾ�����ڵ�
		menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;
		
		
	}else if (Id == 'DeleteOperator') {
		//tongmeng 2011-01-21 add
		//ɾ�����ڵ�
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
	//���Ӻ����Ķ��� 
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
 * 01�������������ţ� 02�������������ţ� 03����ɾ���ڵ㱾�� 04����BOMת����Input 05����Inputת����BOM 06�������������
 * 07������������ 08��������Input�ڵ� 09����BOM
 */
 
 //tongmeng 2010-12-17 Add
 //�����Ҽ�ѡ�����㷨
function createMenuOption(menuIndex) {
	var spanNode = document.createElement("span");
	spanNode.style.cursor = 'hand';
	spanNode.style.textDecoration = 'blink';

	if (menuIndex == '01') {
		var tAddLeft = getMapValueByKey('LINK_ADDLEFT',tLanguage);
		if(tAddLeft==null||tAddLeft=='')
		{
				tAddLeft = '����"("';
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
				tAddRight = '����")"';
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
				tLINK_DELETE = 'ɾ�����ڵ�';
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
					alert('�������������Ų���ɾ��!');
					return;
				}		
				fatherNode.removeChild(preEventNode);
			}
			 else if (preEventNode.id == 'Constant') {
				fatherNode.removeChild(preEventNode);
			}
			//tongmeng 2011-04-22 Add
			//���Ӻ����Ĵ���
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
				tLINK_INPUT = '����һ��ֵ';
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
				tLINK_OTHERBOM = '��������BOM';
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
				tLINK_ADDCAL = '����������';
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
				tLINK_ANDOR = '����"����"����"����"';
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
				tLINK_ADDINPUT = '���������';
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
				tLINK_ADDBOM = '����BOM';
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
				tLINK_ADDBOM = 'ѡ������������';
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
				tLINK_CONS = 'һ������';
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
	//���Ӻ���
	else if (menuIndex == '12') {
			var tLINK_FUNC = getMapValueByKey('LINK_FUNC',tLanguage);
		if(tLINK_FUNC==null||tLINK_FUNC=='')
		{
				tLINK_FUNC = '����';
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
	//������չ����
	else if (menuIndex == '13') {
			var tLINK_FUNC = getMapValueByKey('LINK_PARAMS',tLanguage);
		if(tLINK_FUNC==null||tLINK_FUNC=='')
		{
				tLINK_FUNC = '���Ӳ���';
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
	// ������Ĳ������к����ڲ��洢��������ʹ�ù��̶Բ������޸�
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
		reArray[4] = '0';//�����
	}
	
	//tongmeng 2011-04-14 Add
	//���ӶԺ����� ����
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
		reArray[4] = '1';//����
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
// ��ȡ��̨BOM����
function getBOMArray() {
	
	//alert('mIBRMSDefType:'+mIBRMSDefType);
	var BOMArray = new Array();
	var mySql1=new SqlClass();
	if(mIBRMSDefType=='0')
	{
	var sqlid1="makeLogicalNewSql1";
		
		mySql1.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tLanguage);//ָ������Ĳ���
	  
	}
	else
	{
		var sqlid1="makeLogicalNewSql6";
		
		mySql1.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tLanguage);//ָ������Ĳ���
		mySql1.addSubPara(mBusiness);//ָ������Ĳ���
	}
	  var strSq1=mySql1.getString();	
	//var sql = "select Name,getRuleMsg(Name,'"+tLanguage+"','BOM',CNName),Name from LRBOM where valid='1' order by BOMLevel,FBOM";
	// ����
	var tCatchFlag = false;
		for ( var i = 0; i < queryCache.length; i++) {
			if (queryCache[i][0] == strSq1) {
				tCatchFlag = true;
			}
		}
	BOMArray = getAndPrepareData(strSq1);
	//tongmeng 2012-06-13 add
	//ָ����
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
	// ����
	OtherRuleArray = getAndPrepareData(sql);

	return OtherRuleArray;
}

// �����Բ�ѯ�Ļ���
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
// ����BOM��ȡ��̨BOMItem
function getBOMItemArray(paraArray) {
	var BOMItemArray = new Array();
	var sql = ''
	var str = null;
	if (paraArray[0] == null || paraArray[0] == '') {
		alert("��ȡ��̨BOMItem����Ҫ�Ĳ���������֪��BOMItem������BOM");
	} else {
		if (paraArray[1] != null && paraArray[1] != '') {
			if (paraArray[1] == "INT") {
				paraArray[1] = "Number";
			}
			var sqlid2="makeLogicalNewSql2";
			var mySql2=new SqlClass();
			mySql2.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[1]);
	  	mySql2.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql2.addSubPara(paraArray[0]);
	  	
	  	sql=mySql2.getString();	
		//	sql = "select Name,getRuleMsg('LINK_OF','"+tLanguage+"','LINK',connector)||getRuleMsg('"+paraArray[0]+"_'||Name,'"+tLanguage+"','BOMItem',CNName),CommandType,Source,isHierarchical,'"+paraArray[0]+"'||'_'||Name from LRBOMItem where BOMName='"
		//			+ paraArray[0] + "' and CommandType='" + paraArray[1] + "'"
		//			+ " union "
		//			+ "select rulename,getRuleMsg('LINK_OF','"+tLanguage+"','LINK','��')||rulename,'Number','','0','BOMSubCal_'||rulename from lrtemplate where business='99' and '"+paraArray[0]+"'='BOMSubCal'"
			//		;
		} else {
			//sql = "select Name,getRuleMsg('LINK_OF','"+tLanguage+"','LINK',connector)||getRuleMsg('"+paraArray[0]+"_'||Name,'"+tLanguage+"','BOMItem',CNName),CommandType,Source,isHierarchical,'"+paraArray[0]+"'||'_'||Name from LRBOMItem where BOMName='"
			//		+ paraArray[0] + "'"
			//	  + " union "
			//		+ "select rulename,getRuleMsg('LINK_OF','"+tLanguage+"','LINK','��')||rulename,'Number','','0','BOMSubCal_'||rulename from lrtemplate where business='99' and '"+paraArray[0]+"'='BOMSubCal'"
			//		;

			//		;
			var sqlid3="makeLogicalNewSql3";
			var mySql3=new SqlClass();
			mySql3.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql3.addSubPara(paraArray[0]);
	  	sql=mySql3.getString();	

		}
		BOMItemArray = getAndPrepareData(sql);
		//if()
	}
	return BOMItemArray;
}

// ����BOMItem���ͻ�ȡ�����
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
		mySql4or7.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
		 //sql = "select Implenmation,getRuleMsg('CMD_'||name,'"+tLanguage+"','CMD',Display),CommandType,ResultType,ParaType,ParaNum,'CMD_'||name from LRCommand where commtype='0' and CommandType='"
		//		+ commandtype + "'";
		if (paraArray[0] == null || paraArray[0] == '') {
			alert("��ȡ��̨Operatror����Ҫ�Ĳ���������֪��Operator������CommandType");
			return null;
		} else {
			if (paraArray[3] == 1) {
				mySql4or7.setSqlId(sqlid4or7);//ָ��ʹ�õ�Sql��id
				mySql4or7.addSubPara(tLanguage);//ָ������Ĳ���
		 	 	mySql4or7.addSubPara(commandtype);
			} else {
				sqlid4or7="makeLogicalNewSql7";
				mySql4or7.setSqlId(sqlid4or7);//ָ��ʹ�õ�Sql��id
				mySql4or7.addSubPara(tLanguage);//ָ������Ĳ���
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
			mySql5.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
			mySql5.addSubPara(tLanguage);//ָ������Ĳ���
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
		alert("��������ҳ���");
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
// ����ɨ��ڵ�����ж����ݽ����ݴ�
var stack = new Array();
// ��������DT���е������д����ظ���������
var reCurArray = new Array();
// �������ɾ��߱��ͷ��Ϣʱ����Ϣ���л���
var disCol = new Array();
// ���ڼ�¼�����߼�������Input�ڵ㣬�Լ��پ��߱������붨���߼������ݵĽ�������

var RuleChArray = new Array();

function checkOutRule() {
	var ruleNodes = document.getElementById('RuleZone');
	if (!ruleNodes) {
		//alert("����û�ж��ƹ����붨���������ټ������Ƶ�������");
		return true;
	}
	var ruleNode = ruleNodes.firstChild;
	var endNode = ruleNodes.lastChild;
	if (!ruleNode || ruleNode == endNode) {
		//alert("����û�ж��ƹ����붨���������ټ������Ƶ�������");
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
			// ����������͵��ж�
			if (spanNode.id == "Operator"&&(spanNode.getAttribute('FunctionName')==null||spanNode.getAttribute('FunctionName')=='')) { // �߼���������ĸ���
				if (spanNode.getAttribute("ResultType") == 'Boolean') {
					BoolOpNum++;
				}
				//tongmeng 2011-02-09 modify
				var opParaNum = spanNode.getAttribute("IsNextOperator");
				// ��������������ҵĲ����Ƿ���ȷ
				// һԪ�����ֻ���ǣ�ǰһ���Ǵ�������һ���ǡ�ɾ����������
				// ��Ԫ�����ֻ���ǣ�ǰ���������������Ĵ�������������һ���Ǵ�����һ��Input��������ǰһ���Ǳ������������������һ����Input������BOM
				// ��̬�����ֻ���ǣ�ǰһ���������Ĵ�������һ���������
				if (opParaNum == 0) {
					// �ҵ���ǰ��һ������Spacer�Ľڵ�
					var BOMItemNode = null;
					var startNode = spanNode.previousSibling;
					while ((startNode.id == 'Spacer')||
						(startNode.id!='AddFunction'&&startNode.getAttribute('FunctionName')!=null&&startNode.getAttribute('FunctionName')!='')
						
						) {
						startNode = startNode.previousSibling;
					}
					BOMItemNode = startNode;
					// �����ң��ҵ���һ��id����Spacer�Ľڵ�
					startNode = spanNode.nextSibling;
					while ((startNode.id == 'Spacer')) {
						startNode = startNode.nextSibling;
					}
					var tlastNode = startNode;

					if (!(BOMItemNode.id == 'BOMItem' && (tlastNode.id == 'link'
							|| tlastNode.id == undefined || tlastNode.id == 'Parentheses')))
							{
								errorMessage += "�㷨:�� " + row + " ����������ӵĲ�������ȷ\n";
							}
				} else if (opParaNum == 1) {

				} else if (opParaNum == 2) {
					//alert("opParaNum:"+opParaNum);
					//tongmeng 2011-02-09 Add
					//��Ԫ�����, ����������͵�,ǰ��ı��붼Ϊ����
					if (spanNode.getAttribute("ResultType") == 'Number') {
						// �ҵ���ǰ��һ������Spacer�Ľڵ�
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
						// �����ң��ҵ���һ��id����Spacer�Ľڵ�
						startNode = spanNode.nextSibling;
						while ((startNode.id == 'Spacer' || startNode.id == 'BOM')) {
							startNode = startNode.nextSibling;
						}
						var tlastNode = startNode;
					//alert("BOMItemNode.id:"+BOMItemNode.id+":BOMItemNode.getAttribute("MatchType"):"+BOMItemNode.getAttribute("MatchType"));
						//ǰһ�����������ͱ�������ֵ�͵�
						if(!(((BOMItemNode.id == 'BOMItem' ||BOMItemNode.id=='AddFunction')&& BOMItemNode.getAttribute("MatchType")=='Number')
								&&(
										(tlastNode.id == 'BOMItem' && tlastNode.getAttribute("MatchType")=='Number')||
										(tlastNode.id == 'Input')||
										//"����" �� "����"
										(tlastNode.id == 'link')||
										//����
										(tlastNode.id == 'Constant')	
								)										
						))
						{
							//alert('BOMItemNode.id:'+BOMItemNode.id+":lastNode.id:"+lastNode.id);
							errorMessage += "����:�� " + row + " ����������ӵĲ�������ȷ\n";
						}
					}
				}
			}
			spanNode = spanNode.nextSibling;
			//alert('spanNode.id:'+spanNode.id+"FunctionName:"+spanNode.getAttribute('FunctionName'));

		}
		if (BoolOpNum != 1) {
			errorMessage += "����:�� " + row + " ����" + BoolOpNum + "��������Ϊ�߼��͵������!\n";
		}
		if (paren != 0) {
			errorMessage += "����:�� " + row + " �����Ų�ƥ�䣡\n";
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
		//alert("����û�ж��ƹ����붨���������ټ������Ƶ�������");
		return true;
	}
	var ruleNode = ruleCalNodes.firstChild;
	var endNode = ruleCalNodes.lastChild;
	if (!ruleNode || ruleNode == endNode) {
		//alert("����û�ж��ƹ����붨���������ټ������Ƶ�������");
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
			// ����������͵��ж�
			if (spanNode.id == "Operator") { // �߼���������ĸ���
				if (spanNode.getAttribute("ResultType") == 'Boolean') {
					BoolOpNum++;
				}
				//tongmeng 2011-02-09 modify
				var opParaNum = spanNode.getAttribute("IsNextOperator");
				// ��������������ҵĲ����Ƿ���ȷ
				// һԪ�����ֻ���ǣ�ǰһ���Ǵ�������һ���ǡ�ɾ����������
				// ��Ԫ�����ֻ���ǣ�ǰ���������������Ĵ�������������һ���Ǵ�����һ��Input��������ǰһ���Ǳ������������������һ����Input������BOM
				// ��̬�����ֻ���ǣ�ǰһ���������Ĵ�������һ���������
				if (opParaNum == 0) {
					// �ҵ���ǰ��һ������Spacer�Ľڵ�
					var BOMItemNode = null;
					var startNode = spanNode.previousSibling;
					while ((startNode.id == 'Spacer')) {
						startNode = startNode.previousSibling;
					}
					BOMItemNode = startNode;
					// �����ң��ҵ���һ��id����Spacer�Ľڵ�
					startNode = spanNode.nextSibling;
					while ((startNode.id == 'Spacer')) {
						startNode = startNode.nextSibling;
					}
					var tlastNode = startNode;

					if (!(BOMItemNode.id == 'BOMItem' && (tlastNode.id == 'link'
							|| tlastNode.id == undefined || tlastNode.id == 'Parentheses')))
							{
								errorMessage += "�㷨:�� " + row + " ����������ӵĲ�������ȷ\n";
							}
				} else if (opParaNum == 1) {

				} else if (opParaNum == 2) {
					//alert("opParaNum:"+opParaNum);
					//tongmeng 2011-02-09 Add
					//��Ԫ�����, ����������͵�,ǰ��ı��붼Ϊ����
					if (spanNode.getAttribute("ResultType") == 'Number') {
						// �ҵ���ǰ��һ������Spacer�Ľڵ�
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
						// �����ң��ҵ���һ��id����Spacer�Ľڵ�
						startNode = spanNode.nextSibling;
						while ((startNode.id == 'Spacer' || startNode.id == 'BOM')) {
							startNode = startNode.nextSibling;
						}
						var tlastNode = startNode;
					//alert("BOMItemNode.id:"+BOMItemNode.id+":BOMItemNode.getAttribute("MatchType"):"+BOMItemNode.getAttribute("MatchType"));
						//ǰһ�����������ͱ�������ֵ�͵�
						if(!(((BOMItemNode.id == 'BOMItem' ||BOMItemNode.id=='AddFunction')&& BOMItemNode.getAttribute("MatchType")=='Number')
								&&(
										(tlastNode.id == 'BOMItem' && tlastNode.getAttribute("MatchType")=='Number')||
										(tlastNode.id == 'Input')||
										//"����" �� "����"
										(tlastNode.id == 'link')||
										//����
										(tlastNode.id == 'Constant')	
								)										
						))
						{
							//alert('BOMItemNode.id:'+BOMItemNode.id+":lastNode.id:"+lastNode.id);
							errorMessage += "�㷨:�� " + row + " ����������ӵĲ�������ȷ\n";
						}
					}
				}
			}
			spanNode = spanNode.nextSibling;
		}
	//	if (BoolOpNum != 1) {
	//		errorMessage += "�㷨:�� " + row + " ����" + BoolOpNum + "��������Ϊ�߼��͵������!\n";
//		}
		if (paren != 0) {
			errorMessage += "�㷨:�� " + row + " �����Ų�ƥ�䣡\n";
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
 * �����������Ϊ�� ��������ֵ�������ķ���ֵ��һ�����飨reArray�� reArray[0]Я��ƴд�õ�SQLStatement
 * reArray[1]Я��ƴд�õ�createTable reArray[2]Я��ƴд�õľ��߱�ı�ͷ
 */
function composeSQL() {
	// ��ȡ��������Ľڵ�
	//alert('1');
	var xmlDoc = new ActiveXObject("Msxml2.DOMDocument.3.0");
	var ruleNodes = document.getElementById('RuleZone');
		var xmlRule = xmlDoc.createElement("Rule");
	xmlDoc.appendChild(xmlRule);
	SQLStatement = "";
	initParaBeforeComposeSQL();
	if (ruleNodes) {
	// ��ȡ����ĵ�һ������
	var ruleNode = ruleNodes.firstChild;
	// ��ȡ��������һ������
	var endNode = ruleNodes.lastChild;
	// ��ƴSQL��������Ҫʹ�õ��ı������г�ʼ��
	

	
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
		// ��ȡ�����ĵ�һ���ڵ�����һ���ڵ�
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;
		// ���ڼ�¼������һ��������sql�ַ���

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
			// ���Ż������
			if (spanNode.getAttribute("ChName")!="��ѡ�������")
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
			//������������Ĵ���
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
				if (spanNode.getAttribute("ChName")!="��ѡ�������")
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
	//���Ӽ������Ķ���
	// ��ȡ��������Ľڵ�
	var ruleCalNodes = document.getElementById('CalZone');
	// ��ȡ����ĵ�һ������
	var ruleCalNode = ruleCalNodes.firstChild;
	// ��ȡ��������һ������
	var endCalNode = ruleCalNodes.lastChild;
	// ��ƴSQL��������Ҫʹ�õ��ı������г�ʼ��
	
	var xmlCalCondition = xmlDoc.createElement("CalCondition");
	var MetaCalNode;
	var testrule = "";
	MetaCalNodeChNameArray.length = 0;
	
	var rightFlag = 0;
	
	while (ruleCalNode != endCalNode) {
		// ��ȡ�����ĵ�һ���ڵ�����һ���ڵ�
		var spanNode = ruleCalNode.firstChild;
		var lastNode = ruleCalNode.lastChild;
		// ���ڼ�¼������һ��������sql�ַ���

		xmlCalCondition = xmlDoc.createElement("CalCondition");
	
		
		//alert("spanNode.id:"+spanNode.id+":lastNode.id:"+lastNode.id);
		while (spanNode != lastNode) {
			if (spanNode.id == 'Spacer') {
				spanNode = spanNode.nextSibling;
				continue;
			}
			
			MetaCalNodeChNameArray[MetaCalNodeChNameArray.length] = spanNode;

			//���Ը���
			//tongmeng 2010-12-15 �޸�
			if (spanNode.getAttribute("ChName")!="��ѡ�������")
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
				if (spanCalNodeArray.length != 0&&spanNode.getAttribute("ChName")!="��ѡ�����") {
					//��Ҫ�޸�Ϊƴд�㷨��SQL
					//comACalSQL();
				}
				if(spanNode.getAttribute("ChName")!="��ѡ�����")
				{
					spanCalNodeArray.push(spanNode);
				}
			}
//--------------------------------------------//
			//tongmeng 2010-12-24 Add
			//������������Ĵ���
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
			} else if (spanNode.id == "Operator" &&spanNode.getAttribute("ChName")!="��ѡ�������") {
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
		//�����㷨����,������һ����bomitemʱ,Ҳ��Ҫѹһ��
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
		//��ʱ�Ȳ����ɼ����������Ķ�Ӧ,�Ժ���Ҫ�޸�lrtemplate��ر�
		CalRuleDesInCh = "";
		composeCalRuleDesInCh();

		ruleCalNode = ruleCalNode.nextSibling;
	}
	//------------------------------------------------------------------------------//
	ViewPara = xmlRule.xml;
	
	//tongmeng ��Ҫ���Ӷ�ָ�����У��
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

//���ʹ��ָ����ķ�ʽ,��ô,ָ���������Ҫ��һ��Ҫ�����ڹ����������
function checkRuleTable()
{
			var sqlid8="makeLogicalNewSql8";
			var mySql8=new SqlClass();
			mySql8.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
			mySql8.addSubPara(mRuleTableName);//ָ������Ĳ���
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
					alert('ʹ��ָ����ʽ,�㷨�������ָ�������������,Ŀǰȱ��:'+tPK);
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
	// ���ڼ�¼ɨ�赽�ڵ��Ӣ����
	var linkStr = '';
	var lparenStr = '';
	var rparenStr = '';
	var BOMStr = '';
	var BOMItemStr = '';
	var opStr = '';
	var opStr1 = '';
	// ���ڼ�¼ɨ�赽�ڵ��������
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
	
	// aler("��ջstack�ĳ����ǣ�"+stack.length);
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
 * ������������ֱ��ǣ� lparenStr�������ŵ�Ӣ���� outCol�� ��ʽΪBOM.BOMItem rparenStr�������ŵ�Ӣ���� opStr��
 * �������Ӣ���� flag�� �Ƿ���Ҫ���ɾ��߱��е�һ�еı�ʶ��Input��ʾҪ��BOM��ʾ��Ҫ dataType��
 * BOM.BOMItem��BOMItem���������ͣ�����falgΪInputʱ��Ҫ���������������� ��������ֵ�������ķ���ֵ��һ�����飨reArray��
 * reArray[0]Я��ƴд�õ�SQLStatement reArray[1]Я��ƴд�õ�createTable
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
//�˴���Ҫ����֧�ּ����͹����Ĵ���
function initParaBeforeComposeSQL() {
	// �洢�����SQL
	//tongmeng 2010-12-15 modify
	if(!mRuleTableFlag)
	{
		SQLStatement = "select #CalSQLStatement#,RuleID from #DTTable# where 1=1  ";
	}
	else
	{
		SQLStatement = "select #CalSQLStatement#,RuleID from "+mRuleTableName+" where 1=1  ";
	}
	// ����DT������
	CreateTable = "create table #DTTable# (";

	// �洢������ʾ����Ҫ�Ĳ���
	ViewPara = "";
	// �洢�������õ�BOM
	BOMSArray.length = 0;
	// �洢���򷴽����Ĳ���
	SQLParaArray.length = 0;
	// �洢�����������
	//tongmeng 2011-05-30 modify
	//LINK_CON
	//RuleDesInCh = "������";
	RuleDesInCh = "?LINK_CON?��";
	// �洢���߱���ͷ��Ҫ��ʾ��������
	ColumnHeadArray.length = 0;
	// �洢���߱��е���������
	ColumnDataTypeArray.length = 0;
	// �洢DT����������
	TableColumnNameArray.length = 0;
	// �洢���߱�����������Ƿ�Ϊ������֮һ��
	ColumnMultipleArray.length = 0;
	// �洢���߱�����Ƿ�Ϊ��������
	BaseColumnArray.length = 0;
	// �洢ɨ�赽�ڵ��������
	MetaNodeChNameArray.length = 0;
	// �洢ɨ��ڵ������
	spanNodeArray.length = 0;
	
	//�����ظ�����
	reCurr=0;
}

function completeParaAfterCompose() {
	// ����DT������
	//CreateTable += 'DTRate Number(10,4),RuleId varchar2(20))';
	CreateTable += 'DTRate varchar2(100),RuleId varchar2(20))';
	
	//alert(CreateTable);
	//tongmeng 2011-05-30 modify
	
	//RuleDesInCh += "�����㷨#";
	RuleDesInCh += "?LINK_CAL?#";
	
	//if(RuleDesInCh.indexOf("�ǿ�")>0){
		//alert("����һ��!");
	//}
	//alert(RuleDesInCh);

	ColumnHeadArray[ColumnHeadArray.length] = "���";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	TableColumnNameArray[TableColumnNameArray.length] = "DTRate";

  ColumnHeadArray[ColumnHeadArray.length] = "��ʾ��Ϣ";
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
		message = "���ƵĹ���̫������Թ�����о���";
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
		//tongmeng �˴���Ҫ������
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
		if(spanNode.id == "Operator" &&spanNode.getAttribute("ChName") =="��ѡ�������")
		{
			 continue;
		}
		if (spanNode.id != "Input" && spanNode.id != "InputNodes") {
			//tongmeng 2011-05-30 modify
			//�����Դ���
			//RuleDesInCh += spanNode.getAttribute("ChName");
			
			//alert('spanNode.getAttribute("KeyValue"):'+spanNode.getAttribute("KeyValue"));
			RuleDesInCh += "?"+spanNode.getAttribute("KeyValue")+"?";
		} else {
			RuleDesInCh += "#��";
		}
	}
	
	//alert('RuleDesInCh:'+RuleDesInCh);
}

//tongmeng 2010-12-14 modify
//���Ӽ����㷨�Ķ���
// ��˹����ƵĽӿ�
function comfirmLogic() {
	// ��˹����Ƿ�������
	//tongmeng 2010-12-29 add
	//fm.all('CreateDTFlag').value = '1';
	
	CalSQLStatement = "";
	//tongmeng 2011-02-09 add
	//У������㷨�����Ƿ���ȷ
//checkOutCalRule();
//tongmeng �㷨�߼�У�鴦����Ҫ��������,��ʱע�͵�.
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
	// ��ȡ����ĵ�һ������
	if(ruleNodes){
	var ruleNode = ruleNodes.firstChild;
	// ��ȡ��������һ������
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
	// ��ȡ����ĵ�һ������
	if(ruleNodes)
	{
	var ruleNode = ruleNodes.firstChild;
	// ��ȡ��������һ������
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
	// ���ڼ�¼�Ժ˽�����һ��
	//tongmeng 2010-12-15 modify
	//��¼���߱�ı�������
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
		alert('��ѡ�����������1����ѡ��һ�У�');
	} else if (dataArray.length == 0) {
		alert('����û��ѡ���У���ѡ��һ�У�');

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
								firstNode.innerHTML = "������һ��ֵ";
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
		alert("��ȡ��������Ҫ��ʾ�����ݲ���һһƥ�䣬���ҹ���Ա");
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
			InputNodes[i].innerHTML = "������һ��ֵ";
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
						firstNode.innerHTML = "������һ��ֵ";
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
					firstNode.innerHTML = "������һ��ֵ";
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
	// ��ȡ��������Ľڵ�
	var ruleNodes = document.getElementById('RuleZone');
	// ��ȡ����ĵ�һ������
	if(ruleNodes)
	{
	var ruleNode = ruleNodes.firstChild;
	// ��ȡ��������һ������
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
//���Ӷ��㷨���İ�ť�Ĵ���
	ruleNodes = document.getElementById('CalZone');
	// ��ȡ����ĵ�һ������
	if(ruleNodes)
	{
	var ruleNode = ruleNodes.firstChild;
	// ��ȡ��������һ������
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
		alert('�����������֧��xml�ļ���ȡ,���Ǳ�ҳ���ֹ���Ĳ���,�Ƽ�ʹ��IE5.0���Ͽ��Խ��������!');
		return;
	}
	if (xmlDoc == null) {
		alert('�����������֧��xml�ļ���ȡ,���Ǳ�ҳ���ֹ���Ĳ���,�Ƽ�ʹ��IE5.0���Ͽ��Խ��������!');
		return;
	}
	// ��������DIV����
	var RuleZone = document.createElement("div");
	RuleZone.setAttribute('id', 'RuleZone');
	var IfNode = document.getElementById("conditions");
	IfNode.parentNode.insertBefore(RuleZone, IfNode.nextSibling);
	// ����һ������
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
				
				//tongmeng ��ʼ��ǰ�����滻
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
			alert("�����ڵ�ʱ����"+e.message);
		}
		RuleZone.appendChild(ruleDiv);
		rowCount++;
	}
	
	
	
	spanNode = createAddLineButton("0");
	RuleZone.appendChild(spanNode);
//tongmeng 2010-12-17 modify
	//�����������
	//-----------------------------------------------------------//
		var CalZone = document.createElement("div");
	CalZone.setAttribute('id', 'CalZone');
	var CalIfNode = document.getElementById("calConditions");
	CalIfNode.parentNode.insertBefore(CalZone, CalIfNode.nextSibling);
	// ����һ������
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
				
				//tongmeng ��ʼ��ǰ�����滻
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
			alert("�����ڵ�ʱ����"+e.message);
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
	 	tRES_RESULT = "���";
	 }
	ColumnHeadArray[ColumnHeadArray.length] = tRES_RESULT;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	//TableColumnNameArray[TableColumnNameArray.length] = "DTRate";

 var tRES_MULTMSG = getMapValueByKey('RES_MULTMSG',tLanguage)
	 if(tRES_MULTMSG==null||tRES_MULTMSG=='')
	 {
	 	tRES_MULTMSG = "��������ʾ��Ϣ";
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
	ColumnHeadArray[ColumnHeadArray.length] = "�Ժ˲�ͨ��";
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";

	ColumnHeadArray[ColumnHeadArray.length] = "ת�˹��˱�����";
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
//�����͹������涨���޸�

function showOrHideMenuForCal() {
	//alert(spanCalNodeEnabled);
	if (spanCalNodeEnabled) {
		if (!isCalMenuUnfolded)// �˵�δ��
		{
			unCalFoldMenu(); // չ���˵�
			isCalMenuUnfolded = true;// ���ü�¼��ʶΪ��״̬
		} else {
			foldCalMenu(); // �۵��˵�
			isCalMenuUnfolded = false;// ���ü�¼��ʶΪ�۵�״̬
		}
	}
}

function unCalFoldMenu() {
	// ������DIV���ڵ㡢�����������ڵ㡢�������������ڵ�

	var nextNode = document.getElementById("calConditions").nextSibling;

	if (hasCalRule) {
		nextNode.style.display = '';
	} else {
		var divNode = document.createElement("div");
		divNode.setAttribute("id", "CalZone");
		var newLine = createNewAditionLine("1");
		var buttonNode = createAddLineButton("1");
		// ��ȡ��ǰ�ڵ�ĸ��ڵ��Լ���һ���ֵܽڵ�
		var pareNode = getSrcElemnt(getEvent()).parentNode;
		var nextNode = getSrcElemnt(getEvent()).nextSibling;
		// �����������ڵ��Լ��������������ڵ���뵽��form���ڵ�֮��

		divNode.appendChild(newLine);
		divNode.appendChild(buttonNode);
		// ��form�ڵ���뵽��ǰ�ڵ�֮��
		pareNode.insertBefore(divNode, nextNode);
		hasCalRule = true;
	}
}

// �۵��˵�����
function foldCalMenu() {
	// ��ȡ��ǰ�ڵ�ĸ��ڵ����һ���ֵܽڵ�
	var nextNode = getSrcElemnt(getEvent()).nextSibling;
	nextNode.style.display = 'none';
}

//tongmeng 2011-08-16 Add
//ʹ���µ�ƴ��SQL�㷨�ķ��� 
//calflag  0-���� 1-�㷨
function completeStack(calflag)
{
	//ѭ����ջ
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
		
	//	alert("i:"+i+":tNodeEnName��"+tNodeEnName+":tNodeType:"+tNodeType);
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


//ƴд�����㷨SQL
function comACalSQL() {
	
	var reStr = '';
	var str = '';
	var spanNode = null;
	var outCol = '';
	var op = '';
	var finished = false;
	// ���ڼ�¼ɨ�赽�ڵ��Ӣ����
	var linkStr = '';
	var lparenStr = '';
	var rparenStr = '';
	var BOMStr = '';
	var BOMItemStr = '';
	var opStr = '';
	var opStr1 = '';
	// ���ڼ�¼ɨ�赽�ڵ��������
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
	// aler("��ջstack�ĳ����ǣ�"+stack.length);
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
 * ������������ֱ��ǣ� lparenStr�������ŵ�Ӣ���� outCol�� ��ʽΪBOM.BOMItem rparenStr�������ŵ�Ӣ���� opStr��
 * �������Ӣ���� flag�� �Ƿ���Ҫ���ɾ��߱��е�һ�еı�ʶ��Input��ʾҪ��BOM��ʾ��Ҫ dataType��
 * BOM.BOMItem��BOMItem���������ͣ�����falgΪInputʱ��Ҫ���������������� ��������ֵ�������ķ���ֵ��һ�����飨reArray��
 * reArray[0]Я��ƴд�õ�SQLStatement reArray[1]Я��ƴд�õ�createTable
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
		if(spanNode.id == "Operator" &&spanNode.getAttribute("ChName") =="��ѡ�������")
		{
			 continue;
		}
		if (spanNode.id != "Input" && spanNode.id != "InputNodes") {
			//CalRuleDesInCh += spanNode.getAttribute("ChName");
			CalRuleDesInCh += "?"+spanNode.getAttribute("KeyValue")+"?";
		} else {
			CalRuleDesInCh += "#��";
		}
	}
}