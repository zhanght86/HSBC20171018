var rowCount = 0; // �����߼����е�����
var isMenuUnfolded = false; // "���"�Ƿ��ڱ����֮��չ�������˵�
var preEventNode = null; // ���������������¼��Ľڵ�
var spanNodeEnabled = true;// �ڵ��ܷ���Ӧ������¼��ı�־
var InputNodeEnabled = false;
// ���ڱ�ʶ��Ӧ�ڵ���ҪЯ�������ݽṹ
var BOMIndex = new Array('id', 'EnName', 'ChName','KeyValue');
var BOMItemIndex = new Array('id', 'EnName', 'ChName', 'MatchType', 'Source',
		'isHierarchical','KeyValue');
var OperatorIndex = new Array('id', 'EnName', 'ChName', 'MatchType',
		'ResultType', 'ParaType', 'IsNextOperator','KeyValue');
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

var tResourceName_Logic = 'ibrms.makeLogicalSql';
function hightLight() {
	// �ڵ�����������ʱ����Ӧ����
	if (!spanNodeEnabled) {
		return;
	}
	// �ڵ�������б�壬��������ͣ�������˸��
	else {
		var ob = event.srcElement;
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
		var ob = event.srcElement;
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
		var newLine = createNewAditionLine();
		var buttonNode = createAddLineButton();
		// ��ȡ��ǰ�ڵ�ĸ��ڵ��Լ���һ���ֵܽڵ�
		var pareNode = event.srcElement.parentNode;
		var nextNode = event.srcElement.nextSibling;
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
	var nextNode = event.srcElement.nextSibling;
	nextNode.style.display = 'none';
}

// ��������������
function createNewAditionLine() {
	var divNode = document.createElement("div");// ����һ��"div"�ڵ�(һ����������һ����,��"div"��ʶ)
	// ���������ǵ�һ��������ʱ��������Ӵ�
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

	var buttonNode = createDeleteButtonNode(); // ����һ������ɾ���������İ�ť�ڵ�
	divNode.appendChild(buttonNode);
	rowCount++;
	return divNode;
}
// ����һ��ɾ���������İ�ť
function createDeleteButtonNode() {
	var buttonNode = document.createElement("input");
	// ���Button�ڵ�������Ӧ�¼�
	buttonNode.setAttribute("type", "button");
	buttonNode.setAttribute("id", "DelButton");
	buttonNode.onclick = function() {
		deleteLine();
	}
	buttonNode.style.background = 'url(./resources/img/delete.jpg)';
	buttonNode.style.width = '35';
	buttonNode.style.height = '32';
	return buttonNode;
}

function deleteLine() {
	if (!spanNodeEnabled) {
		return;
	}
	var fatherNode = event.srcElement.parentNode;

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
// ����һ���ܹ�����һ���������İ�ť
function createAddLineButton() {
	var buttonNode = document.createElement("input");
	// ���Button�ڵ�������Ӧ�¼�
	buttonNode.setAttribute("type", "button");
	buttonNode.setAttribute("id", "AddButton");
	buttonNode.onclick = function() {
		addNewLine();
	}
	buttonNode.style.background = 'url(./resources/img/add.jpg)';
	buttonNode.style.width = '35';
	buttonNode.style.height = '32';
	return buttonNode;
}

// ����һ��������
function addNewLine() {
	if (!spanNodeEnabled) {
		return;
	}
	var divNode = createNewAditionLine();
	var ob = event.srcElement;
	var fatherNode = ob.parentNode;
	fatherNode.insertBefore(divNode, ob);
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

	if (!isMenu) // �ж�
	{
		// �����
		if (nodeType != 'Input' && nodeType != 'AddInput'
				&& nodeType != "Delete") {
			spanNode.onmousedown = function() {
				popMenu();
			}
		} else if (nodeType == 'Input') {
			spanNode.onmousedown = function() {
				changeToInput();
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
	var srcNode = event.srcElement;
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
	// ��¼�������Դ�ڵ�
	preEventNode = event.srcElement;
	var spanArray = new Array();
	var clickedNode = event.srcElement;
	// �ж���걻��������Ҽ�
	var mouseButton = event.button;
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
		//if (mouseButton == 1)// IE6-8 ���¼�Ϊ1 IE9+ ���¼�Ϊ0,
		if (mouseButton == leftButton())// ������
		{
			if (Id == 'BOM') {
				spanArray = prepareMenu_Left('BOM');
			} else if (Id == 'BOMItem')
				spanArray = prepareMenu_Left('BOMItem');
			else if (Id == 'Operator')
				spanArray = prepareMenu_Left('Operator');
			else if (Id == 'Link')
				spanArray = prepareMenu_Left('Link');
		} else if (mouseButton == 2)// ����һ�
		{
			if (Id == 'BOM')
				spanArray = prepareMenu_Right('BOM');
			else if (Id == 'BOMItem')
				spanArray = prepareMenu_Right('BOMItem');
			else if (Id == 'Operator') {
				if (preEventNode.getAttribute('EnName') == "is null"
						|| preEventNode.getAttribute('EnName') == "is not null") {
					spanArray = prepareMenu_Right('Operator');
				}
			} else if (Id == 'Input')
				spanArray = prepareMenu_Right('Input');
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
		if (event.srcElement == preEventNode)
			return false;
	}
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
		var srcNode = event.srcElement;
		if (srcNode != preEventNode && srcNode != disNode) {
			destroyMenu();
		}
	}
}

function changeToInput() {
	var DISPLAYINPUT = this;

	preEventNode = event.srcElement;
	var clickedNode = event.srcElement;
	var mouseButton = event.button;
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
						&& currNode.getAttribute('ResultType') == "Boolean") {
					OperatorNode = currNode;
				}
				currNode = currNode.previousSibling;
			}

			if (!!BOMItemNode) {
				// �������ֵ�ǻ�������
				if (!!BOMItemNode.getAttribute('Source')) {
					var InputMenu = new Array();
					var strSQL = BOMItemNode.getAttribute('Source');
					InputMenu = getInputSource(strSQL);
					var spanArray = prepareBaseMenu(InputMenu);
					displayMenu(spanArray);
				}
				// �������ֵ���ǻ�������
				else {
					var dataType = OperatorNode.getAttribute('ParaType');
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

	if (!!obNode.getAttribute('EnName')) {
		inputNode.value = obNode.getAttribute('EnName');
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

	if (event.keyCode == 13) {
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
function convertInputsToSpans() {
	var spanNode;
	var type;
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
	debugger;
	var srcNode = event.srcElement;
	//IE11-Attribute
	var tSrcName = srcNode.EnName||srcNode.getAttribute("EnName");
	var tPreName = preEventNode.EnName||preEventNode.getAttribute("EnName");
	if (tSrcName != tPreName) {
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
	// ��Ҫ���ʵ�������������
	while (fromNode.nextSibling != lastNode
			&& fromNode.nextSibling.id != 'Link'
			&& fromNode.nextSibling.id != 'Left_Paren'
			&& fromNode.nextSibling.id != 'Right_Paren') {
		fatherNode.removeChild(fromNode.nextSibling);
	}
}

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
		while (preNode && preNode.id != 'Link') {
			//IE11-Attribute
			var tNodeResultType = preNode.ResultType||preNode.getAttribute("ResultType");
			if (preNode.id == 'Operator' && tNodeResultType == 'Boolean') {
				createNext = false;
				break;
			}
			preNode = preNode.previousSibling;
		}
		// ����һ��BOMItem�ڵ�
		if (createNext) {
			var spanNode = null;
			spanNode = createOriginSpanNode('Operator');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			spanNode = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		}
	} else if (Id == 'Operator') {
		var spanNode = null;
		var fatherNode = preEventNode.parentNode;
		//IE11-Attribute
		var tNextOperator = srNode.IsNextOperator||srNode.getAttribute("IsNextOperator");
		if (tNextOperator == 0) {
			return;
		}

		else if (tNextOperator == 1) {
			spanNode = createOriginSpanNode('Operator');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
			spanNode = createOriginSpanNode('Spacer');
			fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
		} else if (tNextOperator == 2) {
			//IE11-Attribute
			var tNodeResultType = srNode.ResultType||srNode.getAttribute("ResultType");
			if (tNodeResultType == 'Boolean') {
				//IE11-Attribute
				var tEnName = srNode.EnName||srNode.getAttribute("EnName");
				if (tEnName == "instrExt(@,#,$)>0"
						|| tEnName == "instrExt(@,#,$)=0") {
					var InputNodes = createInputNodes();
					fatherNode.insertBefore(InputNodes,
							preEventNode.nextSibling);
					spanNode = createOriginSpanNode('Spacer');
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				} else {
					spanNode = createOriginSpanNode('Input');
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
					spanNode = createOriginSpanNode('Spacer');
					fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				}
			} else {
				spanNode = createOriginSpanNode('BOM');
				fatherNode.insertBefore(spanNode, preEventNode.nextSibling);
				spanNode = createOriginSpanNode('Spacer');
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
	if (id == 'BOM') {
		paraArray[0] = {
			attribute :'id',
			value :'BOM'
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
	} else if (id == 'BOMItem') {
		paraArray[0] = {
			attribute :'id',
			value :'BOMItem'
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
			value :'&nbsp;&nbsp;;&nbsp;&nbsp;'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :';'
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

	if (Id == 'BOM') {
		var BOMArray = getBOMArray();
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
	} else if (Id == 'Operator') {
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
						if(LinkIndex[j + 1]=='ChName')
						{
								var tLink = getMapValueByKey(LinkArray[i][2],tLanguage);
							if(tLink==null||tLink=='')
							{
									tLink = LinkArray[i][j];
							}
			
								paraArray[j + 1] = {
											attribute :LinkIndex[j + 1],
											value :tLink
								};
						}
						else
						{
								paraArray[j + 1] = {
											attribute :LinkIndex[j + 1],
											value :LinkArray[i][j]
								};
						}

					}
					spanNode = createSpanNode(paraArray, true);
					reSpanArray[reSpanArray.length] = spanNode;
				}
			}
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

	if (Id == 'BOM') {
		// ���'('
		menuOption = createMenuOption('01');
		reArray[reArray.length] = menuOption;
		// ɾ��BOM����BOMת����Input
		var preNode = obNode.previousSibling;
		var isFirstBOM = true;
		while (preNode && preNode.id != 'Link') {
			if (preNode.id == 'BOM') {
				isFirstBOM = false;
				break;
			}
			preNode = preNode.previousSibling;
		}
		if (!isFirstBOM) {
			menuOption = createMenuOption('03');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOption('04');
			reArray[reArray.length] = menuOption;
		}
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
	} else if (Id == 'Operator') {
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('07');
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
	} else if (Id == 'InputRightScope') {
		menuOption = createMenuOption('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOption('07');
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
			if (preEventNode.id == 'BOM') {
				var nextNode = preEventNode.nextSibling;
				if (preEventNode.previousSibling.id == 'Operator'
						&& preEventNode.previousSibling.getAttribute('ResultType') != 'Boolean')
					fatherNode.removeChild(preEventNode.previousSibling);
				fatherNode.removeChild(nextNode);
				fatherNode.removeChild(preEventNode);
			} else if (preEventNode.id == 'BOMItem') {
				var preNode = preEventNode.previousSibling;
				if (preNode.previousSibling.id == 'Operator'
						&& preNode.previousSibling.getAttribute('ResultType') != 'Boolean')
					fatherNode.removeChild(preNode.previousSibling);
				fatherNode.removeChild(preNode);
				fatherNode.removeChild(preEventNode);
			} else if (preEventNode.id == 'Operator') {
				var nextNode = preEventNode.nextSibling;
				if (nextNode.nextSibling.id == 'BOMItem') {
					fatherNode.removeChild(nextNode.nextSibling);
				}
				fatherNode.removeChild(nextNode);
				fatherNode.removeChild(preEventNode);
			} else if (preEventNode.id == 'Input') {

			} else if (preEventNode.id == 'Left_Paren'
					|| preEventNode.id == 'Right_Paren') {
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
			var spanN = createOriginSpanNode('BOM');
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
			var spanN = createOriginSpanNode('BOM');
			var fatherNode = preEventNode.parentNode;
			fatherNode.insertBefore(spanN, preEventNode.nextSibling);
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
			if (!BOMFinished && preNode.id == 'BOM') {
				BOMName = preNode.getAttribute('EnName');
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {
				opParameter = preNode.getAttribute('ParaType');
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
			if (preNode.id != 'Spacer') {
				if (firstOperator) {
					if (preNode.id == 'Operator') {
						// CommandType = preNode.ParaType;
						CommandType = preNode.getAttribute('ResultType');
						if(CommandType=="Number"||CommandType=="String"||CommandType=="Date")
						{
							break;
						}
					}
				} else
					firstOperator = false;
			}
			if (!BOMItemFinished && preNode.id == 'BOMItem') {
				CommandType = preNode.getAttribute('MatchType');
				isHierarchical = preNode.getAttribute('isHierarchical');
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {

				opParameter = preNode.getAttribute('ParaType');
				OperatorFinished = true;
			}
			if (preNode.id == 'Operator' && preNode.getAttribute('ResultType') == 'Boolean') {
				hasBoolean = true;
			}
			preNode = preNode.previousSibling;
		}
		reArray[0] = CommandType;
		reArray[1] = opParameter;
		reArray[2] = hasBoolean;
		reArray[3] = isHierarchical;
	}
	return reArray;
}
// ��ȡ��̨BOM����
function getBOMArray() {
	var BOMArray = new Array();
	var mySql1=new SqlClass();
	if(mIBRMSDefType=='0')
	{
	var sqlid1="makeLogicalSql1";
		
		mySql1.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tLanguage);//ָ������Ĳ���
	  
	}
	else
	{
		var sqlid1="makeLogicalSql6";
		
		mySql1.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tLanguage);//ָ������Ĳ���
		mySql1.addSubPara(mBusiness);//ָ������Ĳ���
	}
	  var strSq1=mySql1.getString();	
	//var sql = "select Name,getRuleMsg(Name,'"+tLanguage+"','BOM',CNName),Name from LRBOM where valid='1' order by BOMLevel,FBOM";
	// ����
	BOMArray = getAndPrepareData(strSq1);

	return BOMArray;
}
// �����Բ�ѯ�Ļ���
function getAndPrepareData(sql) {
	var reArray = new Array();

	for ( var i = 0; i < queryCache.length; i++) {
		if (queryCache[i][0] == sql) {
			return queryCache[i][1];
		}
	}
	var str = easyQueryVer3(sql);
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
			var sqlid2="makeLogicalSql2";
			var mySql2=new SqlClass();
			mySql2.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(tLanguage);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[0]);
	  	mySql2.addSubPara(paraArray[1]);
	  	sql=mySql2.getString();	
			//sql = "select Name,getRuleMsg('LINK_OF','"+tLanguage+"','LINK',connector)||getRuleMsg('"+paraArray[0]+"_'||Name,'"+tLanguage+"','BOMItem',CNName),CommandType,Source,isHierarchical,'"+paraArray[0]+"'||'_'||Name from LRBOMItem where BOMName='"
			//		+ paraArray[0] + "' and CommandType='" + paraArray[1] + "'";
		} else {
			var sqlid3="makeLogicalSql3";
			var mySql3=new SqlClass();
			mySql3.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(tLanguage);
	  	mySql3.addSubPara(paraArray[0]);
	  	mySql3.addSubPara(paraArray[0]);
	  	sql=mySql3.getString();	
			//sql = "select Name,getRuleMsg('LINK_OF','"+tLanguage+"','LINK',connector)||getRuleMsg('"+paraArray[0]+"_'||Name,'"+tLanguage+"','BOMItem',CNName),CommandType,Source,isHierarchical,'"+paraArray[0]+"'||'_'||Name from LRBOMItem where BOMName='"
			//		+ paraArray[0] + "'";
		}
		BOMItemArray = getAndPrepareData(sql);
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
	var sql = "";
	/*var sqlid4="makeLogicalSql4";
			var mySql4=new SqlClass();
			mySql4.setResourceName(tResourceName_Logic); //ָ��ʹ�õ�properties�ļ���
			mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
			mySql4.addSubPara(tLanguage);//ָ������Ĳ���
	  	mySql4.addSubPara(commandtype);
	  
	  	
	
	
	 //sql = "select Implenmation,getRuleMsg('CMD_'||name,'"+tLanguage+"','CMD',Display),CommandType,ResultType,ParaType,ParaNum,'CMD_'||name from LRCommand where commtype='0' and CommandType='"
	//		+ commandtype + "'";
	if (paraArray[0] == null || paraArray[0] == '') {
		alert("��ȡ��̨Operatror����Ҫ�Ĳ���������֪��Operator������CommandType");

		return null;
	} else {
		if (paraArray[1] != null && paraArray[1] != '') {
		//	sql += " and ParaType='" + paraArray[1] + "'";
			var temp = "and ParaType='"+paraArray[1] + "'";
			mySql4.addSubPara(temp);
		}
		if (paraArray[2]) {
	//		sql += " and ResultType!='Boolean'";
			var temp = "and ResultType!='Boolean'";
			mySql4.addSubPara(temp);
		}
		else
		{
			mySql4.addSubPara('');
		}
		if (paraArray[3] == 1) {
			mySql4.addSubPara(" and (implenmation='instrExt(@,#,$)>0' or implenmation='instrExt(@,#,$)=0' or implenmation='strEquals(@,#,$)>0' or implenmation='strEquals(@,#,$)=0' or implenmation='is not null' or implenmation='is null')");
		} else {
			mySql4.addSubPara(" and (implenmation!='strEquals(@,#,$)>0' and implenmation!='strEquals(@,#,$)=0')");
		}
	}
	sql=mySql4.getString();	
	*/
	
			var sqlid4or7="makeLogicalSql4";
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
				sqlid4or7="makeLogicalSql7";
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
		alert("����û�ж��ƹ����붨���������ټ������Ƶ�������");
		return;
	}
	var ruleNode = ruleNodes.firstChild;
	var endNode = ruleNodes.lastChild;
	if (!ruleNode || ruleNode == endNode) {
		alert("����û�ж��ƹ����붨���������ټ������Ƶ�������");
		return;
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
			if (spanNode.id == "Left_Paren") {
				paren++;
			}
			if (spanNode.id == "Right_Paren") {
				paren--;
			}
			// ����������͵��ж�
			if (spanNode.id == "Operator") { // �߼���������ĸ���
				if (spanNode.getAttribute('ResultType') == 'Boolean') {
					BoolOpNum++;
				}
				var opParaNum = spanNode.ParaNum;
				// ��������������ҵĲ����Ƿ���ȷ
				// һԪ�����ֻ���ǣ�ǰһ���Ǵ�������һ���ǡ�ɾ����������
				// ��Ԫ�����ֻ���ǣ�ǰ���������������Ĵ�������������һ���Ǵ�����һ��Input��������ǰһ���Ǳ������������������һ����Input������BOM
				// ��̬�����ֻ���ǣ�ǰһ���������Ĵ�������һ���������
				if (opParaNum == 0) {
					// �ҵ���ǰ��һ������Spacer�Ľڵ�
					var BOMItemNode = null;
					var startNode = spanNode;
					while (startNode.id == 'Spacer') {
						startNode = startNode.previousSibling;
					}
					BOMItemNode = startNode;
					// �����ң��ҵ���һ��id����Spacer�Ľڵ�
					startNode = spanNode;
					while (startNode.id == 'Spacer') {
						startNode = startNode.previousSibling;
					}
					var lastNode = startNode;

					if (!(BOMItemNode.id == 'BOMItem' && (lastNode.id == 'link'
							|| lastNode.id == undefined || lastNode.id == 'Parentheses')))
						errorMessage += "�� " + row + " ����������ӵĲ�������ȷ\n";
				} else if (opParaNum == 1) {

				} else if (opParaNum == 2) {

				}
			}
			spanNode = spanNode.nextSibling;
		}
		if (BoolOpNum != 1) {
			errorMessage += "�� " + row + " ����" + BoolOpNum + "��������Ϊ�߼��͵������!\n";
		}
		if (paren != 0) {
			errorMessage += "�� " + row + " �����Ų�ƥ�䣡\n";
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
	var ruleNodes = document.getElementById('RuleZone');
	// ��ȡ����ĵ�һ������
	var ruleNode = ruleNodes.firstChild;
	// ��ȡ��������һ������
	var endNode = ruleNodes.lastChild;
	// ��ƴSQL��������Ҫʹ�õ��ı������г�ʼ��

	initParaBeforeComposeSQL();

	var xmlDoc = new ActiveXObject("Msxml2.DOMDocument.3.0");
	var xmlRule = xmlDoc.createElement("Rule");
	xmlDoc.appendChild(xmlRule);
	var xmlCondition = xmlDoc.createElement("Condition");
	var MetaNode;

	while (ruleNode != endNode) {
		// ��ȡ�����ĵ�һ���ڵ�����һ���ڵ�
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;
		// ���ڼ�¼������һ��������sql�ַ���

		xmlCondition = xmlDoc.createElement("Condition");

		MetaNodeChNameArray.length = 0;

		while (spanNode != lastNode) {
			if (spanNode.id == 'Spacer') {
				spanNode = spanNode.nextSibling;
				continue;
			}

			MetaNodeChNameArray[MetaNodeChNameArray.length] = spanNode;

			// ���Ż������
			MetaNode = composeXML(xmlDoc, spanNode);
			xmlCondition.appendChild(MetaNode);

			if (spanNode.id == "BOM") {
				if (spanNodeArray.length != 0) {
					comASQL();
				}
				spanNodeArray.push(spanNode);
			}

			else if (spanNode.id == "BOMItem") {
				spanNodeArray.push(spanNode);
			} else if (spanNode.id == "Operator") {
				spanNodeArray.push(spanNode);
				//IE11-Attribute
				var tSpanEnName = spanNode.EnName||spanNode.getAttribute("EnName");
				if(tSpanEnName == "is null"||tSpanEnName == "is not null"  )
				{
					comASQL();
				}
			} else if (spanNode.id == "Link") {
				if (spanNodeArray.length != 0) {
					comASQL();
				}
				spanNodeArray.push(spanNode);
				ColumnMetasChArray.length = 0;
			} else if (spanNode.id == "Left_Paren") {
				spanNodeArray.push(spanNode);
			} else if (spanNode.id == "Right_Paren") {
				spanNodeArray.push(spanNode);
			} else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
				spanNodeArray.push(spanNode);
				comASQL();
			}
			spanNode = spanNode.nextSibling;
		}
		if (spanNodeArray.length != 0) {
			comASQL();
		}
		xmlRule.appendChild(xmlCondition);
		composeRuleDesInCh();

		ruleNode = ruleNode.nextSibling;
	}
	ViewPara = xmlRule.xml;
	return completeParaAfterCompose();
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
	// aler("��ջstack�ĳ����ǣ�"+stack.length);
	for ( var i = 0, len = spanNodeArray.length; i < len; i++) {
		var spanNode = spanNodeArray[i];
		if (!(!!spanNode))
			continue;
		if (spanNode.id == "Link") {
			com(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			SQLStatement += ' ' + spanNode.getAttribute('EnName') + ' ';
		} else if (spanNode.id == "Left_Paren") {
			if (lparenStr != '') {
				SQLStatement += spanNode.getAttribute('EnName');
			}
			lparenStr = spanNode.getAttribute('EnName');
			pareNumber += 1;
		} else if (spanNode.id == "Right_Paren") {
			rparenStr = spanNode.getAttribute('EnName');
			pareNumber -= 1;
		} else if (spanNode.id == "BOM") {
			outCol += spanNode.getAttribute('EnName');
			BOMNode = spanNode.getAttribute('EnName');
			ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute('ChName');
		} else if (spanNode.id == "BOMItem") {
			outCol += '_' + spanNode.getAttribute('EnName');
			BOMItemNode = spanNode.getAttribute('EnName');
			BOMItemType = spanNode.getAttribute('MatchType');

			ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute('ChName');
		} else if (spanNode.id == "Operator") {
			if (opStr == '') {
				opStr = spanNode.getAttribute('EnName');
			} else {
				opStr1 = spanNode.getAttribute('EnName');
			}
			InputType = spanNode.getAttribute('ParaType');
			ColumnMetasChArray[ColumnMetasChArray.length] = spanNode.getAttribute('ChName');
		} else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
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
		BOMItemType) {
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
	//alert(outCol+":"+BOMItemType);
	if (outCol != '') {
		if (BOMItemType == 'Date') {
			temp = lparenStr + "to_date(?" + outCol.replace('_', '.')
					+ "?,'yyyy-mm-dd hh24:mi:ss')" + rparenStr;
		} 
		else if (BOMItemType == 'String')
		{
			temp = lparenStr + '\'?' + outCol.replace('_', '.') + '?\'' + rparenStr;
		}
		else {
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
	} else {
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
//	alert(SQLStatement);
	if (reCreateTable != "") {
		CreateTable += reCreateTable + ",";
	}
}

function initParaBeforeComposeSQL() {
	// �洢�����SQL
	SQLStatement = "select RuleId,UWLevel,Result from #DTTable# where ";
	// ����DT������
	CreateTable = "create table #DTTable# (";

	// �洢������ʾ����Ҫ�Ĳ���
	ViewPara = "";
	// �洢�������õ�BOM
	BOMSArray.length = 0;
	// �洢���򷴽����Ĳ���
	SQLParaArray.length = 0;
	// �洢�����������
//	RuleDesInCh = "�����";
	RuleDesInCh = "?LINK_IF?��";
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
	CreateTable += 'Result varchar2(4000),UWLevel varchar2(20),RuleId varchar2(20))';

//	RuleDesInCh += "��ô#";
	RuleDesInCh += "?LINK_THAN?#";
	
	//if(RuleDesInCh.indexOf("�ǿ�")>0){
		//alert("����һ��!");
	//}
	//alert(RuleDesInCh);
//

		var tRES_NOPASS = getMapValueByKey('RES_NOPASS',tLanguage)
	 if(tRES_NOPASS==null||tRES_NOPASS=='')
	 {
	 	tRES_NOPASS = "����";
	 }
	 
	ColumnHeadArray[ColumnHeadArray.length] = tRES_NOPASS;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	TableColumnNameArray[TableColumnNameArray.length] = "Result";
	

		var tRES_UWLEVEL = getMapValueByKey('RES_UWLEVEL',tLanguage)
	 if(tRES_UWLEVEL==null||tRES_UWLEVEL=='')
	 {
	 	tRES_UWLEVEL = "���򼶱�";
	 }
	ColumnHeadArray[ColumnHeadArray.length] = tRES_UWLEVEL;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";
	TableColumnNameArray[TableColumnNameArray.length] = "UWLevel";
	
	var tRES_MULTMSG = getMapValueByKey('RES_MULTMSG',tLanguage)
	 if(tRES_MULTMSG==null||tRES_MULTMSG=='')
	 {
	 	tRES_MULTMSG = "��������ʾ��Ϣ";
	 }


	ColumnHeadArray[ColumnHeadArray.length] = tRES_MULTMSG;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "Button";
	TableColumnNameArray[TableColumnNameArray.length] = "RuleId";


	return checkColumnLength();
}

function checkColumnLength() {
	var message = '';
	if (Length(SQLStatement) > 2000 || Length(BOMSArray.toString()) > 500
			|| Length(SQLParaArray.toString()) > 2000
			|| Length(RuleDesInCh) > 2000) {
		message = "���ƵĹ���̫������Թ�����о���";
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
	if (spanNode.id == 'BOM') {
		for ( var i = 0; i < BOMIndex.length; i++) {
			var att = spanNode.getAttribute(BOMIndex[i]);
			xmlNode.setAttribute(BOMIndex[i], att);
		}
	} else if (spanNode.id == 'BOMItem') {
		for ( var i = 0; i < BOMItemIndex.length; i++) {
			var att = spanNode.getAttribute(BOMItemIndex[i]);
			att = att.replace(new RegExp("'", "gm"), "''");
			xmlNode.setAttribute(BOMItemIndex[i], att);
		}
	} else if (spanNode.id == 'Operator') {
		for ( var i = 0; i < OperatorIndex.length; i++) {
			var att = spanNode.getAttribute(OperatorIndex[i]);
			xmlNode.setAttribute(OperatorIndex[i], att);
		}
	} else if (spanNode.id == 'Link') {
		for ( var i = 0; i < LinkIndex.length; i++) {
			var att = spanNode.getAttribute(LinkIndex[i]);
			xmlNode.setAttribute(LinkIndex[i], att);
		}
	} else if (spanNode.id == 'Left_Paren' || spanNode.id == 'Right_Paren') {
		for ( var i = 0; i < ParenIndex.length; i++) {
			var att = spanNode.getAttribute(ParenIndex[i]);
			xmlNode.setAttribute(ParenIndex[i], att);
		}
	} else if (spanNode.id == 'Input' || spanNode.id == 'InputNodes') {
		for ( var i = 0; i < InputIndex.length; i++) {
			var att = spanNode.getAttribute(InputIndex[i]);
			xmlNode.setAttribute(InputIndex[i], att);
		}
	}
	return xmlNode;
}
function composeRuleDesInCh() {
	/*
	var spanNode;
	for ( var i = 0, len = MetaNodeChNameArray.length; i < len; i++) {
		spanNode = MetaNodeChNameArray[i];
		if (spanNode.id != "Input" && spanNode.id != "InputNodes") {
			RuleDesInCh += spanNode.ChName;
		} else {
			RuleDesInCh += "#��";
		}
	}*/
		var spanNode;
	for ( var i = 0, len = MetaNodeChNameArray.length; i < len; i++) {
		spanNode = MetaNodeChNameArray[i];
		if (spanNode.id != "Input" && spanNode.id != "InputNodes") {
			//tongmeng 2011-05-30 modify
			//�����Դ���
			//RuleDesInCh += spanNode.ChName;
			
			//alert('spanNode.KeyValue:'+spanNode.KeyValue);
			RuleDesInCh += "?"+spanNode.getAttribute('KeyValue')+"?";
		} else {
			RuleDesInCh += "#��";
		}
	}
}

// ��˹����ƵĽӿ�
function comfirmLogic() {
	// ��˹����Ƿ�������
	if (checkOutRule()) {
		var fm = document.getElementById('fm');

		if (composeSQL()) {
			initDataArray();
			initRuleID();
			displayDicTable();

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
	var ruleNodes = document.getElementById('RuleZone');
	// ��ȡ����ĵ�һ������
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
	InputNodes[InputNodes.length] = document.getElementById('Result');
}

function getBaseBomItems() {
	getInputNodes();
	BaseColumnArray.length = 0;
	BaseBOMItemSourceArray.length = 0;
	ColumnMultipleArray.length = 0;
	var inputNode;
	var firstBomItem;
	for ( var i = 0; i < InputNodes.length - 1; i++) {
		inputNode = InputNodes[i];
		while (inputNode.id != 'BOMItem') {
			inputNode = inputNode.previousSibling;
		}
		firstBomItem = inputNode;
		if (!!firstBomItem.getAttribute('Source')) {
			BaseColumnArray[i] = true;

			inputNode = InputNodes[i];
			while (inputNode.id != 'Operator') {
				inputNode = inputNode.previousSibling;
			}
			if (inputNode.getAttribute('EnName') == "instrExt(@,#,$)>0"
					|| inputNode.getAttribute('EnName') == "instrExt(@,#,$)=0") {
				ColumnMultipleArray[i] = true;
			} else {
				ColumnMultipleArray[i] = false;
			}
		} else {
			BaseColumnArray[i] = false;
			ColumnMultipleArray[i] = false;
		}
		BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = firstBomItem.getAttribute('Source');
	}
	// ���ڼ�¼�Ժ˽�����һ��
	BaseColumnArray[BaseColumnArray.length] = false;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = "";

	
	
	//var sql = "select sysvarvalue from ldsysvar  where sysvar='ibrmsUWLevel'";
	//var reArray = getAndPrepareData(sql);
	//var UWSource = reArray[0][0];
	
		var UWSource = 'CodeType:uwpopedom '
	
	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = UWSource;
	
	BaseColumnArray[BaseColumnArray.length] = true;
	ColumnMultipleArray[ColumnMultipleArray.length] = false;
	BaseBOMItemSourceArray[BaseBOMItemSourceArray.length] = "";
	


}

function dataToTable() {
	getInputNodes();
	var dataD = new Array();
	if (InputNodes.length != 0) {
		for ( var i = 0; i < InputNodes.length - 1; i++) {
			if (InputNodes[i].id != "InputNodes") {
				if (!!InputNodes[i].getAttribute('EnName')) {
					if (ColumnDataTypeArray[i] == 'Date') {
						var dt = new Date(InputNodes[i].getAttribute('EnName'));
						dataD[i] = dt.format('Y-m-d H:i:s');
					} else {
						dataD[i] = InputNodes[i].getAttribute('EnName');
					}
				}
			} else {
				var firstNode = InputNodes[i].firstChild;
				var first = true;
				while (firstNode) {
					if (firstNode.id == "Input") {
						if (first) {
							dataD[i] = firstNode.getAttribute('EnName');
							first = false;
						} else {
							dataD[i] += ";" + firstNode.getAttribute('EnName');
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
		var col = e.getAttribute("column") - 2;
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
	var r = e.getAttribute("record");
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
			spanNode = createDeleteButtonNode();
			ruleDiv.appendChild(spanNode);
		} catch (e) {
			alert("�����ڵ�ʱ����");
		}
		RuleZone.appendChild(ruleDiv);
		rowCount++;
	}
	spanNode = createAddLineButton();
	RuleZone.appendChild(spanNode);

	//ColumnHeadArray[ColumnHeadArray.length] = "�Ժ˲�ͨ��";
	
	 var tNoPass = getMapValueByKey('RES_NOPASS',tLanguage)
	 if(tNoPass==null||tNoPass=='')
	 {
	 	tNoPass = "����";
	 }
	 ColumnHeadArray[ColumnHeadArray.length] = tNoPass;
	 
	 
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";


 var tUWLevel = getMapValueByKey('RES_UWLEVEL',tLanguage)
	 if(tUWLevel==null||tUWLevel=='')
	 {
	 	tUWLevel = "ת�˹��˱�����";
	 }
	ColumnHeadArray[ColumnHeadArray.length] = tUWLevel;
	
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "String";

	var tRES_MULTMSG = getMapValueByKey('RES_MULTMSG',tLanguage)
	 if(tRES_MULTMSG==null||tRES_MULTMSG=='')
	 {
	 	tRES_MULTMSG = "��������ʾ��Ϣ";
	 }


  ColumnHeadArray[ColumnHeadArray.length] = tRES_MULTMSG;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = "Button";
	//TableColumnNameArray[TableColumnNameArray.length] = "RuleId"
	//TableColumnNameArray[TableColumnNameArray.length] = "MultMsg";
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
