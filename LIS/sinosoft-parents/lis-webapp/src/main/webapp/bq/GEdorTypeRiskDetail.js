// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0; // ��ʶ��ǰѡ�м�¼��GRID

// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage0 = new turnPageClass();
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// �ض����ȡ���㴦���¼�
window.onfocus = initFocus;

// ����Ĳ�ѯ��ť
function queryClick()
{
	// alert("This is GEdorTypeRiskDetail.js->queryClick!");
	if(checkGrpPol() == false)
	{
		return;
	}
	var tEdorNo = document.all('EdorNo').value;
	if(tEdorNo == "")
	{
		alert("��ȫ��Ϊ�գ�");
		return;
	}
	var tEdorType = document.all('EdorType').value;
	if(tEdorType == "")
	{
		alert("��ȫ����Ϊ�գ�");
		return;
	}
	var tGrpContNo = document.all('GrpContNo').value;
	if(tGrpContNo == "")
	{
		alert("���屣����Ϊ�գ�");
		return;
	}
	var tGrpPolNo = document.all('GrpPolNo').value;
	if(tGrpPolNo == "")
	{
		alert("�������ֱ�����Ϊ�գ�");
		return;
	}
	tRiskCode = document.all('RiskCode').value;
	if(tRiskCode == "")
	{
		return;
	}
//	var tStrSql = "select ContNo, InsuredNo, InsuredName, InsuredSex, InsuredBirthday, "
//			+ "(select IDType from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), "
//			+ "(select IDNo from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), PolNo "
//			+ "from LCPol a where " 
//				+" not exists (select * from LPEdorItem where EdorNo = '"+ tEdorNo+ "' and EdorType = '"+ tEdorType+ "' and PolNo = a.PolNo) "
//				+ " and not exists (select * from LPPol where EdorNo = '"+ tEdorNo+ "' and EdorType = '"+ tEdorType+ "' and GrpPolNo = '"+ tGrpPolNo+ "' and PolNo = a.PolNo)"
//				+" and not exists (select * from LPPol where EdorType = '"+ tEdorType+ "' and EdorNo = '"+ tEdorNo+ "' and GrpPolNo = '"+ tGrpPolNo+ "' and ContNo = a.ContNo) "
//				+ " and not exists (select 1 from LPEdorItem where edortype in ('GT','XT','ZT','AT','AX','WT','AZ') and ContNo = a.ContNo and edorstate = '0')"
//		   + " and GrpContNo = '"+ tGrpContNo+ "' and RiskCode = '"+ tRiskCode+ "' and GrpPolNo = '"+ tGrpPolNo+ "' "
//		   + getWherePart('ContNo', 'ContNo2')
//		   + getWherePart('InsuredNo', 'CustomerNo2')
//		   + getWherePart('InsuredName', 'Name2', 'like', '0');
	var tStrSql = "";
	var sqlid1="GEdorTypeRiskDetailSql4";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql1.addSubPara(tEdorType);//ָ������Ĳ���
	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql1.addSubPara(tEdorType);//ָ������Ĳ���
	mySql1.addSubPara(tGrpPolNo);//ָ������Ĳ���
	mySql1.addSubPara(tEdorType);//ָ������Ĳ���
	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql1.addSubPara(tGrpPolNo);//ָ������Ĳ���
	mySql1.addSubPara(tGrpContNo);//ָ������Ĳ���
	mySql1.addSubPara(tRiskCode);//ָ������Ĳ���
	mySql1.addSubPara(tGrpPolNo);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("ContNo2")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("CustomerNo2")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("Name2")[0].value);//ָ������Ĳ���
	tStrSql=mySql1.getString();	
	turnPage.queryModal(tStrSql, LCInsuredGrid);
//	mySql = new SqlClass();
//	mySql.setResourceName("bq.GEdorTypeRiskDetail");
//	mySql.setSqlId("GEdorTypeRiskDetailSql3");
//	mySql.addSubPara(tEdorNo);
//	mySql.addSubPara(tEdorType);
//	mySql.addSubPara(tGrpPolNo);
//	mySql.addSubPara(tGrpContNo);
//	mySql.addSubPara(tRiskCode);
//	mySql.addSubPara(fm.ContNo2.value);
//	mySql.addSubPara(fm.CustomerNo2.value);
//	mySql.addSubPara(fm.Name2.value);
//	turnPage.queryModal(mySql.getString(), LCInsuredGrid);
	if(selGridFlag == 1)
	{
		selGridFlag = 0;
	}
}

// ==================
function queryClick0()
{

	var tGrpContNo = document.all('GrpContNo').value;
	if(tGrpContNo == "")
	{
		alert("�������屣����Ϊ�գ�");
		return false;
	}
	mySql = new SqlClass();
	mySql.setResourceName("bq.GEdorTypeRiskDetail");
	mySql.setSqlId("GEdorTypeRiskDetailSql1");
	mySql.addSubPara(tGrpContNo);
	mySql.addSubPara(fm.EdorType.value);
	turnPage0.queryModal(mySql.getString(), LCInsured0Grid);
	if(selGridFlag == 1)
	{
		selGridFlag = 0;
	}
}

function QueryEdorInfo()
{
	mySql = new SqlClass();

	var tEdortype = document.all('EdorType').value
	if(tEdortype != null || tEdortype != '')
	{
		mySql.setResourceName("bq.GEdorTypeRiskDetail");
		mySql.setSqlId("GEdorTypeRiskDetailSql2");
		mySql.addSubPara(tEdortype);

		// var strSQL="select distinct EdorCode, EdorName from LMEdorItem where
		// EdorCode = '" + tEdortype + "'";
	}
	else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
		return;
	}

	var arrSelected = new Array();
	turnPage.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	// try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { };
	try
	{
		document.all('EdorTypeName').value = arrSelected[0][1];
	}
	catch (ex)
	{
	};
}

// ����Ĳ�ѯ��ť
function queryClick2()
{
	// alert("This is GEdorTypeRiskDetail.js->queryClick2");
	if(checkGrpPol() == false)
	{
		return;
	}
	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//	var strSql = "select ContNo, InsuredNo, InsuredName, InsuredSex, InsuredBirthday, "
//			+ "(select IDType from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), "
//			+ "(select IDNo from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), PolNo"
//			+ " from LCPol a where exists (select * from LPEdorItem where EdorType = '"
//			+ fm.EdorType.value
//			+ "' and EdorNo = '"
//			+ fm.EdorNo.value
//			+ "' and ContNo = a.ContNo and PolNo = a.PolNo) and GrpContNo = '"
//			+ fm.GrpContNo.value
//			+ "' and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "'"
//			+ getWherePart('ContNo', 'ContNo3')
//			+ getWherePart('InsuredNo', 'CustomerNo3')
//			+ getWherePart('InsuredName', 'Name3', 'like', '0');
	
	var strSql = "";
	var sqlid1="GEdorTypeRiskDetailSql5";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("ContNo3")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("CustomerNo3")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("Name3")[0].value);//ָ������Ĳ���
	strSql=mySql1.getString();	
	// document.write(strSql);
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid);
	if(selGridFlag == 2)
	{
		selGridFlag = 0;
	}
	showInfo.close();
}

// ========================
function checkGrpPol()
{

	// alert("This is checkGrpPol!");
	var tSelNo = LCInsured0Grid.getSelNo() - 1;
	var tRowNo = LCInsured0Grid.mulLineCount;
	if(tRowNo <= 0)
	{
		alert("��ѯ�ŵ�������Ϣ�б�ʧ�ܣ�");
		return false;
	}
	if(tSelNo < 0)
	{
		alert("��ѡ����Ҫ��ȫ���ŵ����ֱ���!");
		return false;
	}
}

// ����MultiLine�ĵ�ѡ��ťʱ����
function reportDetail0Click(parm1,parm2)
{
	// alert("This is reportDetail0Click!");
	fm.GrpPolNo.value = LCInsured0Grid.getRowColData(LCInsured0Grid.getSelNo()
					- 1, 1);
	fm.RiskCode.value = LCInsured0Grid.getRowColData(LCInsured0Grid.getSelNo()
					- 1, 2);
	// selGridFlag = 0;

	// queryClick0();
	// queryClick();//��ѯ�����ֶ�Ӧ�ı�����
	// queryGEdorList();
	// queryClick2();//��ѯ��ȫ���ı�����
}

// ����MultiLine�ĵ�ѡ��ťʱ����
function reportDetailClick(parm1,parm2)
{
	// alert("This is reportDetailClick!");
	fm.ContNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo() - 1,
			1);
	fm.PolNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo() - 1,
			8);
	fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()
					- 1, 2);
	queryGEdorList();
	selGridFlag = 1;
}

// ������˱�ȫ
function GEdorDetail()
{
	// alert("This is GEdorDetail!");
	if(selGridFlag == 0)
	{
		alert("����ѡ��һ�������ˣ�");
		return;
	}
	var showStr = "�������뼯���¸��˱�ȫ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(selGridFlag == 1)
	{
		document.all("Transact").value = "INSERT||EDOR";
	}
	fm.submit();
	document.all("Transact").value = "";
}

// �򿪸��˱�ȫ����ϸ����
function openGEdorDetail()
{
	// alert("This is openGEdorDetail!");
	fm.CustomerNoBak.value = fm.CustomerNo.value;
	fm.ContNoBak.value = fm.ContNo.value;
	window.open("./GEdorType" + document.all('EdorType').value + ".jsp");
	showInfo.close();

}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,content,Result)
{
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		GEdorFlag = true;
		if(fm.Transact.value == "I&EDORITEM")
		{
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+ content;
			//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
		else
		{
			openGEdorDetail();
		}
	}
	initLCInsuredGrid();
	initLCInsured2Grid();
}

// �����ȡ�����¼�
function initFocus()
{
	// alert("This is GEdorTypeRiskDetail.js->initFocus");
	// if (GEdorFlag)
	// {
	// GEdorFlag = false;
	// var tReturn = queryClick0();
	// if (tReturn == false){
	// return;
	// }
	// //queryClick();
	// //queryGEdorList();
	// selGridFlag = 0;
	// }
}

// ��ѯ�������ĸ��˱�ȫ�б� �Ѿ�������ȫ��Ŀ��
function queryGEdorList()
{
//	var strSql = "select ContNo, InsuredNo, InsuredName, InsuredSex, InsuredBirthday, "
//			+ "(select IDType from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo),"
//			+ "(select IDNo from LCInsured where ContNo = a.ContNo and InsuredNo = a.InsuredNo), PolNo"
//			+ " from LCPol a where GrpContNo = '"
//			+ fm.GrpContNo.value
//			+ "'"
//			+ " and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "'"
//			+ " and exists (select PolNo from LPPol where EdorNo = '"
//			+ fm.EdorNo.value
//			+ "' and EdorType = '"
//			+ fm.EdorType.value
//			+ "' and PolNo = a.PolNo and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "') and RiskCode = '"
//			+ fm.RiskCode.value
//			+ "'"
//			+ " and exists (select * from LPPol where EdorType = '"
//			+ fm.EdorType.value
//			+ "' and EdorNo = '"
//			+ fm.EdorNo.value
//			+ "' and GrpPolNo = '"
//			+ fm.GrpPolNo.value
//			+ "' and ContNo = a.ContNo)";
	// alert(strSql);
	var strSql = "";
	var sqlid1="GEdorTypeRiskDetailSql6";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
	strSql=mySql1.getString();	
	
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid);
	if(selGridFlag == 2)
	{
		selGridFlag = 0;
	}
}

// ����MultiLine�ĵ�ѡ��ťʱ����
function reportDetail2Click(parm1,parm2)
{
	fm.ContNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()
					- 1, 1);
	fm.PolNo.value = LCInsured2Grid.getRowColData(
			LCInsured2Grid.getSelNo() - 1, 8);
	fm.CustomerNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid
					.getSelNo()
					- 1, 2);
	fm.InsuredNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()
					- 1, 2);
	selGridFlag = 2;
	// queryClick();
}

// ���������¸��˱�ȫ
function cancelGEdor()
{
	// alert("This is cancelGEdor!");
	if(LCInsured2Grid.getSelNo() < 1)
	{
		alert("����ѡ��һ�������ˣ�");
		return;
	}
	document.all("Transact").value = "I&EDORITEM"
	var showStr = "���ڳ��������¸��˱�ȫ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//	var strSql = "select MakeDate, MakeTime, EdorState from LPEdorItem where EdorNo='"
//			+ fm.EdorNo.value + "' and EdorType = '" + fm.EdorType.value + "'";
	var strSql = "";
	var sqlid1="GEdorTypeRiskDetailSql7";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeRiskDetail"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	strSql=mySql1.getString();	
	
	var arrResult = easyExecSql(strSql); // ִ��SQL�����в�ѯ�����ض�ά����
	if(arrResult != null)
	{
		var tMakeDate = arrResult[0][0];
		var tMakeTime = arrResult[0][1];
		var tEdorState = arrResult[0][2];
		var tInusredNo = fm.CustomerNo.value;
		var tContNo = fm.ContNo.value;
		var tPolNo = fm.PolNo.value;
		var tEdorType = fm.EdorType.value;
		var tEdorAcceptNo = fm.EdorAcceptNo.value;
		var tEdorNo = fm.EdorNo.value;
		// alert(tEdorNo);
		// return;
		fm.action = "./PEdorAppCancelSubmit.jsp?EdorAcceptNo=" + tEdorAcceptNo
				+ "&ContNo=" + tContNo + "&EdorType=" + tEdorType
				+ "&DelFlag=EdorItem&InsuredNo=" + tInusredNo + "&MakeDate="
				+ tMakeDate + "&MakeTime=" + tMakeTime + "&EdorItemState="
				+ tEdorState + "&PolNo=" + tPolNo + "&EdorNo=" + tEdorNo;
		fm.submit();
		fm.action = "./GEdorTypeRiskDetailSubmit.jsp";
	}
}

/*******************************************************************************
 * ��ʾfrmSubmit��ܣ��������� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if(cDebug == "1")
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}

// ======================
function returnParent()
{
	top.opener.getEdorItemGrid();
	top.close();
}

// =====================
function saveGEdor()
{

	if(LCInsured2Grid.mulLineCount == 0)
	{
		alert("�����޸ĺ���ܱ������룡");
		return;
	}
	top.opener.updateClick();
	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=����ɹ�";
	//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}
