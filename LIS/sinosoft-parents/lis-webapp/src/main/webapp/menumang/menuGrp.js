//���¼�¼�� showAllMenuInUnselect()��showMenuGrp()
//������:  ����  ����
//��������:  2005-5-4     2006-03-08  
//����ԭ��/���ݣ�����ҳ��Ȩ�޲˵�    ��дMulLine�Ĳ�ѯ��ʾ

var showInfo;
//���ƽ����ϵ�mulLine����ʾ����
var mulLineShowCount = 15;
var sqlcount = 0;
var selectArray = new Array();
var unselectArray = new Array();
var userArray = new Array();
var userArrayLen = 0;
var userCurPage = 0;
var removeArray = new Array() //���²˵����е�ɾ����¼
var showDiv = "false";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

document.onclick = function()
{
//	if(!document.all)
//		return;
	obj = event.srcElement ? event.srcElement : event.target;
	if (obj.type=="checkbox") {
		tArray = searchArray(obj.id);
       		tagInArray(tArray,obj.id);
	}
	if (obj.getAttribute("name")=="foldheader") {
		tArray = searchArray(obj.id);
		var nested = obj.nextElementSibling;
//		var srcIndex = obj.sourceIndex;
//		var nested = document.all[srcIndex+1];
		if (nested.style.display=="none") {
			nested.style.display='';
		} else {
			nested.style.display="none";
		}
//		nested = document.all[srcIndex+2];
//		if (nested.style.display=="none") {
//			nested.style.display='';
//		} else {
//			nested.style.display="none";
//		}
		tagShowlistInArray(tArray,obj.id);
	}
}

//document.onclick=change;

function initUserArray()
{
	var userArray = new Array();
	var userArrayLen = 0;
	var userCurPage = 0;
}

//function fillUserGrid()
//{
//	QueryGrpGrid.clearData("QueryGrpGrid");
//	for (var i = 0; i < mulLineShowCount; i++)
//	{
//		QueryGrpGrid.addOne("QueryGrpGrid");
//		var offset = i  + userCurPage*mulLineShowCount;
//		if (offset < userArrayLen)
//		{
//			QueryGrpGrid.setRowColData(i,1,userArray[offset][0]);
//			QueryGrpGrid.setRowColData(i,2,userArray[offset][1]);
//			QueryGrpGrid.setRowColData(i,3,userArray[offset][3]);
//			QueryGrpGrid.setRowColData(i,4,userArray[offset][2]);
//			QueryGrpGrid.setRowColData(i,5,userArray[offset][4]);
//		}
//		else
//		{
//			QueryGrpGrid.setRowColData(i,1,"");
//			QueryGrpGrid.setRowColData(i,2,"");
//			QueryGrpGrid.setRowColData(i,3,"");
//			QueryGrpGrid.setRowColData(i,4,"");
//			QueryGrpGrid.setRowColData(i,5,"");
//		}
//	}
//	//����Ĵ�����Ϊ��ʹ��ҳʱ�������ȷ��ʾ
//	for (var i = 0; i < mulLineShowCount; i++)
//	{
//		var offset = i  + userCurPage*mulLineShowCount;
//		document.all("QueryGrpGridNo")[i].value = offset + 1;
//	}
//}

//function userFirstPage()
//{
//	if (userArrayLen == 0) return;
//	userCurPage = 0;
//	fillUserGrid();
//}

//function userLastPage()
//{
//	if (userArrayLen == 0) return;
//	while ((userCurPage + 1)*mulLineShowCount < userArrayLen) userCurPage++;
//	fillUserGrid();
//}

//function userPageDown()
//{
//	if (userArrayLen == 0) return;
//	if (userArrayLen <= (userCurPage + 1) * mulLineShowCount)
//	{
//		alert("�Ѵ�βҳ");
//	}
//	else
//	{
//		userCurPage++;
//		fillUserGrid();
//	}
//}

//function userPageUp()
//{
//	if (userArrayLen == 0) return;
//	if (userCurPage == 0)
//	{
//		alert("�ѵ���ҳ");
//	}
//	else
//	{
//		userCurPage--;
//		fillUserGrid();
//	}
//}

function searchArray(id)
{
	for (var i = 0; i < selectArray.length; i++)
	{
		var aryId1 = "chk_" + selectArray[i][8];
		var aryId2 = "fld_" + selectArray[i][8];
		if (aryId1 == id || aryId2 == id)
			return selectArray;
	}
	for (var i = 0; i < unselectArray.length; i++)
	{
		var aryId1 = "chk_" + unselectArray[i][8];
		var aryId2 = "fld_" + unselectArray[i][8];
		if (aryId1 == id || aryId2 == id)
			return unselectArray;
	}
}

//�ύǰ���б�Ҫ�ļ��
function beforeSubmit()
{
    if (document.all('MenuGrpCode').value =="") {
        alert("�˵�����������д");
        return false;
    }
    return true;
}

function resetForm()
{
	document.all('MenuGrpCode').value = "";
	document.all('MenuGrpName').value = "";
	document.all('MenuGrpDescription').value = "";
	document.all('MenuSign').value = "";
}

function deleteClick()
{
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
        if (selMenuGrpNo == 0) {
	  alert("����û��ѡ����Ҫɾ���Ĳ˵���");
	  return;
	}

        var Operator = QueryGrpGrid.getRowColData(selMenuGrpNo - 1,5);
        var thisOperator = document.all("Operator").value;
        if (thisOperator != Operator) {
  	    alert("����Ȩɾ���˲˵���");
  	    return;
        }

	if (!confirm("��ȷʵҪɾ������˵�����"))
	  return;

	document.all("Action").value = "delete";

    submitForm();
}


function queryClick()
{
    QueryGrpGrid.clearData();
    userCurPage = 0;

    var MenuGrpName = document.all("MenuGrpName").value;
    var MenuGrpCode = document.all("MenuGrpCode").value;
    var MenuGrpDescription = document.all("MenuGrpDescription").value;
    var MenuSign = document.all("MenuSign").value;
    var Usercode = document.all("Usercode").value;
		
		// ��дSQL���
		
    //sqlcount++;
    //var sqlStr = "select MenuGrpName,MenuGrpCode,MenuSign,MenuGrpDescription,Operator from LDMenuGrp where " + sqlcount + "=" + sqlcount + " ";
  /*    mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql1");
	//mySql.addSubPara(fm.RptNo.value ); 
 
    if (MenuGrpName != "")
    //	sqlStr += "and MenuGrpName = '" + MenuGrpName + "'";
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql2");
	mySql.addSubPara(MenuGrpName); 
    if (MenuGrpCode != "")
    //	sqlStr += "and MenuGrpCode = '" + MenuGrpCode + "'";
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql3");
	mySql.addSubPara(MenuGrpName); 
	mySql.addSubPara(MenuGrpCode); 
    if (MenuGrpDescription != "")
    //	sqlStr += "and MenuGrpDescription = '" + MenuGrpDescription + "'";
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql4");
	mySql.addSubPara(MenuGrpName); 
	mySql.addSubPara(MenuGrpCode); 
	mySql.addSubPara(MenuGrpDescription); 
    if (MenuSign != "")
    //	sqlStr += "and MenuSign = '" + MenuSign + "'";
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql5");
	mySql.addSubPara(MenuGrpName); 
	mySql.addSubPara(MenuGrpCode); 
	mySql.addSubPara(MenuGrpDescription); 
	mySql.addSubPara(MenuSign); 
		if (Usercode != "")
    //	sqlStr += "and Operator = '" + Usercode + "'";
    */
  mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql6");
	mySql.addSubPara(MenuGrpName); 
	mySql.addSubPara(MenuGrpCode); 
	mySql.addSubPara(MenuGrpDescription); 
	mySql.addSubPara(MenuSign); 
	mySql.addSubPara(Usercode); 
    turnPage.queryModal(mySql.getString(), QueryGrpGrid);
    	
    //var strTemp =  easyQueryVer3(sqlStr, 1, 0, 1);
    //var tempArray = new Array;
    //userArray = turnPage.decodeEasyQueryResult(strTemp);
    //if (userArray == null) {
    //	alert("û���ҵ���ָ���Ĳ˵��顣");
    //	return;
    //}
    //userArrayLen = userArray.length;
    //fillUserGrid();
}

function insertClick()
{
	resetForm();
	//��ȥ��Ҫ��Ԫ��
	divQueryGrp.style.display = "none";
	divCmdButton.style.display = "none";
	trUsercode.style.display = "none";
	showAllMenuInUnselect();
	//��ʾ��Ҫ��Ԫ��
	divSubCmdButton.style.display = "";
	divmenuGrid2.style.display= "";
	document.all("Action").value = "insert";
}

function showAllMenuInUnselect(){
	//�ļ��˵�������һ��NodeSign�ֶΣ�1Ϊ3��Ҷ�ӣ�2Ϊ�ļ�Ҷ�ӣ�2005
	//var sqlStr = "select ParentNodeCode,ChildFlag,nodename,nodecode,NodeSign from LDMenu order by nodeorder";
	 mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql7"); 
	//var sqlStr = "select ParentNodeCode,ChildFlag,nodename,nodecode from LDMenu order by nodeorder";
	//ʹ��ģ������Դ������д�ڲ��֮ǰ
	turnPage.useSimulation   = 1;
	//ȡ��ǰ200���ڵ�
	mySql.addSubPara('1'); 
	var strTemp = easyQueryVer3(mySql.getString(), 1, 0, 1);
	var tempArray = new Array;
	
	tempArray = turnPage.decodeEasyQueryResult(strTemp);
	var times = 1;
	while (times * 200 < turnPage.queryAllRecordCount)
	times = times + 1;
	for ( var i = 2; i <= times; i++) {
		var strTemp1 = easyQueryVer3(mySql.getString(),1,0, (i-1)*200 + 1);
		//ȥ��ͷ����o|xxx^
		var sss = strTemp1.substring(6,strTemp1.length);
		strTemp = strTemp + '^' + sss;
	}
	tempArray = turnPage.decodeEasyQueryResult(strTemp);
	//alert("tempArray="+tempArray);//2005
	if (tempArray == null) return;
	//��ʼ��selectArray
	for (var i = 0; i < tempArray.length ; i++) {
		selectArray[i] = new Array();
		// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode
		// 5 hide 6 showlist 7 check 8 id
		selectArray[i][0] = 0;
		selectArray[i][1] = tempArray[i][0];
		selectArray[i][2] = 0;
		selectArray[i][3] = tempArray[i][2];
		selectArray[i][4] = tempArray[i][3];
		selectArray[i][5] = 1;
		selectArray[i][6] = 0;
		selectArray[i][7] = 0;
		selectArray[i][8] = "sel_" + tempArray[i][3];
		if (tempArray[i][1] > 0)
			selectArray[i][9] = 0; //����һ��˵�Ҷ��
		else
			selectArray[i][9] = 1;
		//�ж��Ƿ�ҳ��Ȩ�޽ڵ� 2005
		if (tempArray[i][4] == 1)
			selectArray[i][9] = 0; 
	}
    //��ʼ��unselectArray
    for (var i = 0; i < tempArray.length ; i++) {
    	unselectArray[i] = new Array();
    	unselectArray[i][0] = 0;
    	unselectArray[i][1] = tempArray[i][0];
    	unselectArray[i][2] = 0;

    	unselectArray[i][3] = tempArray[i][2];
    	unselectArray[i][4] = tempArray[i][3];
    	unselectArray[i][5] = 1;
    	unselectArray[i][6] = 0;
    	unselectArray[i][7] = 0;
    	unselectArray[i][8] = "unsel_" + tempArray[i][3];
		  if (tempArray[i][1] > 0)
			  unselectArray[i][9] = 0; //����һ��˵�Ҷ��
		  else
			  unselectArray[i][9] = 1;
		  //�ж��Ƿ�ҳ��Ȩ�޽ڵ� 2005
		  if (tempArray[i][4] == 1)
			  unselectArray[i][9] = 0; 
    }
    var Operator = document.all("Operator").value;
    //var sqlStr = "select nodecode from LDMenu " ;
    mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpSql");
	mySql.setSqlId("menuGrpSql8"); 
    if(Operator == "001")
    {
    	//sqlStr += " order by nodeorder" ;
    	mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		
		mySql.setSqlId("menuGrpSql9"); 
		mySql.addSubPara('1'); 
    }
	else
	{
       /* sqlStr += " where nodecode in (select nodecode from ldmenugrptomenu ";
        sqlStr += " where menuGrpCode in (select menuGrpCode from ldusertomenugrp ";
        sqlStr += " where usercode = '" + Operator + "' ) )";
        sqlStr += " order by nodeorder";*/
        mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		mySql.setSqlId("menuGrpSql10"); 
		mySql.addSubPara(Operator); 
    }
    strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
    tempArray = new Array;
    tempArray = turnPage.decodeEasyQueryResult(strTemp);
    times = 1;
    while (times * 200 < turnPage.queryAllRecordCount)
        times = times + 1;
    for ( var i = 2; i <= times; i++) {
    	var strTemp1 = easyQueryVer3(mySql.getString(),1,0, (i-1)*200 + 1);
	//ȥ��ͷ����o|xxx^
	var sss = strTemp1.substring(6,strTemp1.length);
       strTemp = strTemp + '^' + sss;
    }
    tempArray = turnPage.decodeEasyQueryResult(strTemp);
    if (tempArray != null) {
      for (var i = 0; i < tempArray.length; i++) {
	    	var nodecode = tempArray[i][0];
	    	for (var j = 0; j < unselectArray.length; j++) {
	    		if (unselectArray[j][4] == nodecode)
	    		    break;
	        }
	        addNode(unselectArray,j);
	    }
	}
	  //alert("menuGrp.js:showAllMenuInUnselect():selectArray(input for treeMenu.js:paintTree)="+selectArray);//2005�Ѿ����4���˵�ֵ
    paintTree(selectArray,"spanSelectMenuGrpTree");
    paintTree(unselectArray,"spanUnselectMenuGrpTree");
}

function updateClick()
{
	//alert("menuGrp.js:updateClick");//2005
  var selMenuGrpNo = QueryGrpGrid.getSelNo();
  if (selMenuGrpNo == 0) {
  	alert("����û��ѡ����Ҫ���µĲ˵���");
  	return;
  }
  var Operator = QueryGrpGrid.getRowColData(selMenuGrpNo - 1,5);
  var thisOperator = document.all("Operator").value;
  if (thisOperator != Operator) {
  	alert("����Ȩ�޸Ĵ˲˵���");
  	return;
  }
  //��ȥ��Ҫ��Ԫ��
  divQueryGrp.style.display = "none";
  divCmdButton.style.display = "none";
  trUsercode.style.display = "none";
  //��ѡ����û���Ϣ������������Ϣ�������
  var offset = selMenuGrpNo -1;
  document.all("MenuGrpName").value = QueryGrpGrid.getRowColData(offset,1);
  document.all("MenuGrpCode").value = QueryGrpGrid.getRowColData(offset,2);
  document.all("MenuSign").value = QueryGrpGrid.getRowColData(offset,3);
  document.all("MenuGrpDescription").value = QueryGrpGrid.getRowColData(offset,4);
  //ȡ��ѡ���û���Ӧ�Ĳ˵���
  showMenuGrp();
  //��ʾ��Ҫ��ʾ��Ԫ��
  divSubCmdButton.style.display = "";
  divmenuGrid2.style.display= "";
  document.all("Action").value = "update";
}

function showMenuGrp()
{
//	  	alert("menuGrp.js:showMenuGrp");//2005
    var selMenuGrpNo = QueryGrpGrid.getSelNo();
    if (selMenuGrpNo == 0)
        return;
    var menuGrpCode = QueryGrpGrid.getRowColData(selMenuGrpNo - 1,2);
   // var sqlStr = "select ParentNodeCode,ChildFlag,nodename,nodecode,NodeSign from LDMenu order by nodeorder";
   mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		mySql.setSqlId("menuGrpSql11"); 
		mySql.addSubPara('1'); 
    //ʹ��ģ������Դ������д�ڲ��֮ǰ
    turnPage.useSimulation   = 1;
    var tempArray = new Array;

    //ȡ��ǰ200���ڵ�
    var strTemp = easyQueryVer3(mySql.getString(), 1, 0, 1);
    tempArray = turnPage.decodeEasyQueryResult(strTemp);

    var times = 1;
    while (times * 200 < turnPage.queryAllRecordCount)
        times = times + 1;

    //alert(turnPage.queryAllRecordCount+':'+times);
    for ( var i = 2; i <= times; i++) {
    	var strTemp1 = easyQueryVer3(mySql.getString(),1,0, (i-1)*200 + 1);
	//ȥ��ͷ����o|xxx^
	var sss = strTemp1.substring(6,strTemp1.length);
       strTemp = strTemp + '^' + sss;
    }
    tempArray = turnPage.decodeEasyQueryResult(strTemp);
    //alert(tempArray.length);
    if (tempArray != null) {
	    //��ʼ��selectArray
	    for (var i = 0; i < tempArray.length ; i++) {

	    	selectArray[i] = new Array();

	    	selectArray[i][0] = 0;
	    	selectArray[i][1] = tempArray[i][0];
	    	selectArray[i][2] = 0;
	    	selectArray[i][3] = tempArray[i][2];
	    	selectArray[i][4] = tempArray[i][3];
	    	selectArray[i][5] = 1;
	    	selectArray[i][6] = 0;
	    	selectArray[i][7] = 0;
	    	selectArray[i][8] = "sel_" + tempArray[i][3];
//	    	if (tempArray[i][1] > 0)
//    	    	    selectArray[i][9] = 0; //����Ҷ��
//    		else
//    	    	    selectArray[i][9] = 1;
		if (tempArray[i][1] > 0)
			selectArray[i][9] = 0; //����һ��˵�Ҷ��
		else
			selectArray[i][9] = 1;
		//�ж��Ƿ�ҳ��Ȩ�޽ڵ� 2005
		if (tempArray[i][4] == 1)
			selectArray[i][9] = 0; 
    	    }
     }
    //��ʼ��unselectArray
    for (var i = 0; i < tempArray.length ; i++) {

    	unselectArray[i] = new Array();
    	unselectArray[i][0] = 0;
    	unselectArray[i][1] = tempArray[i][0];
    	unselectArray[i][2] = 0;

    	unselectArray[i][3] = tempArray[i][2];
    	unselectArray[i][4] = tempArray[i][3];
    	unselectArray[i][5] = 1;
    	unselectArray[i][6] = 0;
    	unselectArray[i][7] = 0; // check
    	unselectArray[i][8] = "unsel_" + tempArray[i][3];
//    	if (tempArray[i][1] > 0)
//    	    unselectArray[i][9] = 0; //����Ҷ��
//    	else
//    	    unselectArray[i][9] = 1;
    	if (tempArray[i][1] > 0)
			  unselectArray[i][9] = 0; //����һ��˵�Ҷ��
		  else
			  unselectArray[i][9] = 1;
		  //�жϸ���ҳ��Ȩ�޲˵��ڵ� 2005
		  if (tempArray[i][4] == 1)
			  unselectArray[i][9] = 0; //����ҳ��Ȩ�޲˵�
        }

    var Operator = document.all("Operator").value;
  //  var sqlStr = "select nodecode from LDMenu ";
    mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		mySql.setSqlId("menuGrpSql12"); 
		mySql.addSubPara('1'); 
    if(Operator == "001")
    {
    	//sqlStr += " order by nodeorder";
    	mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		mySql.setSqlId("menuGrpSql13"); 
		mySql.addSubPara('1'); 
    }
	else
	{
       /* sqlStr += " where nodecode in (select nodecode from ldmenugrptomenu ";
        sqlStr += " where menuGrpCode in (select menuGrpCode from ldusertomenugrp "
        sqlStr += " where usercode = '" + Operator + "' ) )";
        sqlStr += " order by nodeorder";*/
        mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		mySql.setSqlId("menuGrpSql14"); 
		mySql.addSubPara(Operator); 
    }

    strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
    tempArray = turnPage.decodeEasyQueryResult(strTemp);

    times = 1;
    while (times * 200 < turnPage.queryAllRecordCount)
        times = times + 1;

    for ( var i = 2; i <= times; i++) {
    	var strTemp1 = easyQueryVer3(mySql.getString(),1,0, (i-1)*200 + 1);
	//ȥ��ͷ����o|xxx^
	var sss = strTemp1.substring(6,strTemp1.length);
       strTemp = strTemp + '^' + sss;
    }
    tempArray = new Array;
    tempArray = turnPage.decodeEasyQueryResult(strTemp);
    if (tempArray != null) {
	    for (var i = 0; i < tempArray.length; i++) {
	    	var nodecode = tempArray[i][0];
	    	//if('1011'==nodecode)
	    	//{
	    	//	alert('1');
	    	//}
	    	for (var j = 0; j < unselectArray.length; j++) {
	    		if (unselectArray[j][4] == nodecode)
	    		{
	    		    break;
	    		}
	        }
	        addNode(unselectArray,j);
	    }
	}

   /* var sqlStr = "select nodecode from LDMenu where nodecode in "
    sqlStr +="(select nodecode from ldmenuGrpTomenu where menuGrpCode ='" + menuGrpCode + "') and " +  sqlcount + " = " + sqlcount;
    */sqlcount++;
	  mySql = new SqlClass();
		mySql.setResourceName("menumang.menuGrpSql");
		mySql.setSqlId("menuGrpSql15"); 
		mySql.addSubPara(menuGrpCode); 
    strTemp =  easyQueryVer3(mySql.getString(), 1, 0, 1);
    tempArray = turnPage.decodeEasyQueryResult(strTemp);

    times = 1;
    while (times * 200 < turnPage.queryAllRecordCount)
        times = times + 1;

    for ( var i = 2; i <= times; i++) {
    	var strTemp1 = easyQueryVer3(mySql.getString(),1,0, (i-1)*200 + 1);
	//ȥ��ͷ����o|xxx^
	var sss = strTemp1.substring(6,strTemp1.length);
       strTemp = strTemp + '^' + sss;
    }
    var tempArray = new Array;

    tempArray = turnPage.decodeEasyQueryResult(strTemp);


    if (tempArray != null) {
	    for (var i = 0; i < tempArray.length; i++) {
	    	var nodecode = tempArray[i][0];
	    	for (var j = 0; j < selectArray.length; j++) {
	    		if (selectArray[j][4] == nodecode)
	    		    break;
	        }
	        addNode(selectArray,j);
	        removeNode(unselectArray,j);
	    }
    }

    //����ԭʼѡ��˵���removeArray��
    removeArray = new Array();
    var index = 0;
    for (var i = 0; i < selectArray.length; i++) {
    	if (selectArray[i][5] == 1)
    	    continue;
    	removeArray[index] = selectArray[i][4];
    	index++;
    }

    paintTree(selectArray,"spanSelectMenuGrpTree");
    paintTree(unselectArray,"spanUnselectMenuGrpTree");
}

function update()
{
	//alert("menuGrp.js:update");//2005
	if (!beforeSubmit())
		return;
	//�������Ҫ����ɾ����removeArray,Ȼ�����ַ�������hideRemoveString��
	document.all("hideRemoveString").value = "";
	var hideRemoveStr = "";
	var count = 0;
	for (var i = 0; i < removeArray.length; i++)
	{
		var nodecode = removeArray[i];
		var j = 0;
		for (; j < selectArray.length; j++)
		{
			if (selectArray[j][5] == 1)
				continue;
			if (selectArray[j][4] == nodecode)
				break;
		}
		if (j == selectArray.length)
		{
			//�˽ڵ㱻ɾ����
			hideRemoveStr = hideRemoveStr + " |" + nodecode + "|^";
		}
	}
	document.all("hideRemoveString").value = hideRemoveStr;
	var menuGrpCode = document.all("MenuGrpCode").value;
	document.all("hideString").value = arrayToString(selectArray);
	HideMenuGrpGrid1.clearData("HideMenuGrpGrid1");
	submitForm();
}

function remove()
{
	//alert("menuGrp.js:remove");//2005
	submitForm();
}

function insert()
{
	//alert("menuGrp.js:insert");//2005
	if (!beforeSubmit())
		return;
	var menuGrpCode = document.all("MenuGrpCode").value;
	// ��SelectArray��������ݿ�����HideMenuGrpGrid��
	HideMenuGrpGrid1.clearData("HideMenuGrpGrid1");
	document.all("hideString").value = arrayToString(selectArray);
	submitForm();
}

function arrayToString(tArray)
{
	//alert("menuGrp.js:arrayToString");//2005
	var menuGrpCode = document.all("MenuGrpCode").value;
	var resultString = "";
	for (var i = 0; i < tArray.length; i++)
	{
		if (tArray[i][5]== 1)
			continue;
		resultString += menuGrpCode + "|";
		resultString += tArray[i][4] + "|^";
	}
	return resultString;
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //�ύ
}

function afterSubmit(FlagStr)
{
    showInfo.close();
    if (document.all('Action').value == "insert") {
        if (FlagStr == "success")
            alert("���Ӳ˵���ɹ���");
        else
            alert("���Ӳ˵���ʧ�ܣ����ܵ�ԭ���Ǵ˲˵����Ѵ���");
    }

    if (document.all('Action').value == "update") {
        if (FlagStr == "success")
            alert("���²˵���ɹ���");
        else
            alert("���²˵���ʧ�ܣ����ܵ�ԭ���Ǵ˲˵��鲻����");
    }

    if (document.all('Action').value == "delete") {
        if (FlagStr == "success")
            alert("ɾ���˵���ɹ���");
        else
            alert("ɾ���˵���ʧ��");
    }
    queryClick();
}

function addMenus()
{
	for (var i = 0; i < unselectArray.length; i++)
	{
		if (unselectArray[i][5] == 1)
			continue;
		if (unselectArray[i][7] == 1)
		{
			removeNode(unselectArray,i);
			addNode(selectArray,i);
		}
	}
	paintTree(selectArray,"spanSelectMenuGrpTree");
	paintTree(unselectArray,"spanUnselectMenuGrpTree");
}

function removeMenus()
{
	for (var i = 0; i < selectArray.length; i++)
	{
		if (selectArray[i][2] != 0)
			continue;
		if (selectArray[i][5] == 1)
			continue;
		if (selectArray[i][7] == 1)
		{
			removeNode(selectArray,i);
			addNode(unselectArray,i);
		}
	}
	paintTree(selectArray,"spanSelectMenuGrpTree");
	paintTree(unselectArray,"spanUnselectMenuGrpTree");
}

function initGrid()
{
	initmenuGrpGrid();
	initunSelectMenuGrpGrid();
}

function showDiv()
{
	divmenuGrid2.style.display="";
}

function okClick()
{
	if (document.all("Action").value == "insert")
	{
		insert()
	}
	if (document.all("Action").value == "update")
	{
		update();
	}
	if (document.all("Action").value == "delete")
	{
		remove();
	}
}

function cancelClick()
{
	divQueryGrp.style.display = '';
	divmenuGrid2.style.display="none";
	divCmdButton.style.display='';
	trUsercode.style.display = "";
	divSubCmdButton.style.display="none";
	initForm();
}
