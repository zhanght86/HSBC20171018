// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
window.onfocus = myonfocus;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
// window.onfocus=focuswrap;
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
var cflag = "5";
var mWFlag = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
function myonfocus()
{
	if(showInfo != null)
	{
		try
		{
			showInfo.focus();
		}
		catch (ex)
		{
			showInfo = null;
		}
	}
}

/*******************************************************************************
 * ���� �� �ж���Ҫ¼���ҵ����Ա������ ���� �� �� ����ֵ�� boolean
 * ********************************************************************
 */
// =================20060309====changing start=======================
function IsMultiAgent()
{
	// ��������Ϊ�������ۼ�����Ϊ3��ʱ��Ҫ������¼��������ҵ����Ա��Ϣ������ʱ�ڴ��������ж�
	var tSaleChnl = ""; // ��������
	tSalChnl = document.all('AgentType').value;
	if(tSalChnl == "03")
	{

		// ��¼���ű�������ҵ��Ա�ĸ�����
		var tMulLineRowNum = "";
		tMulLineRowNum = MultiAgentGrid.mulLineCount;
		var tMulLineColNum = MultiAgentGrid.colCount;
		// alert("tMulLineRowNum = " + tMulLineRowNum);
		// alert("tMulLineColNum = " + tMulLineColNum);

		// ҵ��Ա�������������
		if(tMulLineRowNum - 1 < 0)
		{
			alert("����������Ϊ�������۵�����£�Ҫ������¼������ҵ����Ա��Ϣ����NoPCardContInput.js->IsMultiAgent()�����쳣��");
			// document.all("multiagentflag").value = true; //modify by liuqh
			// document.all("multiagentflag").checked = true;
			// DivMultiAgent.style.display="";
			return false;
		} // end if

		// ҵ��Ա���ڵ�������ʱ����������������Ϊ�գ�
		else
		{
			for(var i = 0; i < tMulLineRowNum; i++)
			{
				var tAgentCode = MultiAgentGrid.getRowColData(i, 1);
				var tManageCom = MultiAgentGrid.getRowColData(i, 3);
				// alert("tAgentCode = " + tAgentCode);
				// alert("tManageCom = " + tManageCom);
				if(!(tAgentCode == "" && tManageCom == ""))
				{
					if(!verifyNotNull("ҵ��Ա����", tAgentCode)
							&& verifyNotNull("��������sss", tManageCom))
					{
						alert("ҵ��Ա���벻��Ϊ�գ�");
						// document.all("multiagentflag").value = true; //modify by
						// liuqh
						// document.all("multiagentflag").checked = true;
						// DivMultiAgent.style.display="";
						return false;
					}
					if(!verifyNotNull("��������", tManageCom)
							&& verifyNotNull("ҵ��Ա����", tAgentCode))
					{
						alert("������������Ϊ�գ�");
						// document.all("multiagentflag").value = true; //modify by
						// liuqh
						// document.all("multiagentflag").checked = true;
						// DivMultiAgent.style.display="";
						return false;
					}
					break;
				} // end if
			} // end for
			// û����Ч�Ĵ�ҵ��Ա��Ϣ
			if(i == tMulLineRowNum)
			{
				alert("����������Ϊ�������۵�����£�Ҫ������¼������ҵ����Ա��Ϣ����NoPCardContInput.js�����쳣��");
				// document.all("multiagentflag").value = true; //modify by liuqh
				// document.all("multiagentflag").checked = true;
				// DivMultiAgent.style.display="";
				return false;
			} // end if
		} // end else
	} // end if
	return true;
}
// ===========changing end============================

function checkSaleChnl()
{
	var tAgentType = fm.AgentType.value;
	var tSecondAgentType = fm.SecondAgentType.value;
	if(tAgentType == "01")
	{
		if(tSecondAgentType == "")
		{
			alert("��ѡ�����������µĶ�������������");
			return false;
		}
	}
	return true;
}
/*******************************************************************************
 * ���漯��Ͷ�������ύ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function submitForm()
{
	if(verifyInput2() == false)
		return false;
	if(checkSaleChnl() == false)
		return false;
	var sysdate = "select to_char(sysdate,'YYYY-MM-DD') from dual";
	var arrQuery = easyExecSql(sysdate, 1, 0);
	if(fm.CValiDate.value < arrQuery[0][0]
			&& document.all('BackDateRemark').value == "")
	{
		alert("��������Ч���ڴ���׷����������ڱ���������׷�ݱ�ע��ע��׷���ڼ�������ر�Լ��");
		return false;
	}

	// �������� wangxw 20100925
	if(fm.OldGrpcontno.value != null && fm.OldGrpcontno.value != "")
	{
		if(!confirm("�Ƿ��������������"))
		{

			fm.OldGrpcontno1.value = document.all('ProposalGrpContNo').value;
			fm.ReNewTime.value = "1";
			return;
		}
		else
		{
			// ͳ����������
			var strSql = " select ReNewTimes from LCGrpContReNewTrace where grpcontno= '"+ fm.OldGrpcontno.value + "'";
			var ReNewTime = easyExecSql(strSql);
			if(ReNewTime != null && ReNewTime != "")
			{
				fm.ReNewTime.value = ReNewTime * 1 + 1;

			}
			else
			{
				alert("������ԭʼ�����Ų����ڣ���ȷ���Ƿ���ȷ��");
				return;
			}
		}
	}
	else
	{
		fm.OldGrpcontno1.value = document.all('ProposalGrpContNo').value;
		fm.ReNewTime.value = "1";
	}

	if(document.all('GrpNo').value != "" && document.all('GrpNo').value.length > 0)
	{
		var sqlstr = "select grpname from ldgrp where customerno='"+ document.all('GrpNo').value + "'";
		arrResult = easyExecSql(sqlstr, 1, 0);
		var i = arrResult.length;
		if(i != 1)
		{
			alert("Ͷ���˿ͻ�������!");
			return;
		}
		if(arrResult[0][0] != document.all('GrpName').value)
		{
			alert("��λ������������!");
			return;
		}

	}

	if(document.all('GetFlag').value == "4" && document.all('BankCode').value == "")
	{
		alert("����������ת�ʵ����к͸����е��ʺ�");
		document.all('BankCode').focus();
		return false;
	}
	// if(document.all('AgentType').value=="04" || document.all('AgentType').value=="06" ||
	// document.all('AgentType').value=="07")
	// {

	// if(fm.AgentCom.value==" " || fm.AgentCom.value=="")
	// {
	// alert("��ѡ���н�ṹ��");
	// return;
	// }
	// }

	// ����������Ϊ��������ʱ����Ҫ¼����������ҵ����Ա
	if(!IsMultiAgent())
	{
		return false;
	}
	// ��ϴǮ��Ϣ��¼��У�� //modify by liuqh 2008-11-27
	// if(document.all('ClientCare').value=="")
	// {
	// alert ("��¼��ͻ���ע���ܣ�");
	// return false;
	// }
	// if(document.all('ClientNeedJudge').value=="")
	// {
	// alert ("��¼��Ͷ��������Ĳ�Ʒ��ͻ����������Ƿ�һ�£�");
	// return false;
	// }
	// else{
	// if(document.all('ClientNeedJudge').value=="N")
	// {
	// if(document.all('FundReason').value=="")
	// {
	// alert ("�ͻ�����ı��������������Բ���ʱ����¼���Ƿ���������");
	// return false;
	// }
	// }
	// }
	// if(document.all('FundJudge').value=="")
	// {
	// alert ("��¼���ʽ��Ƿ���Դ��Ͷ����λ��");
	// return false;
	// }
	// if(document.all('GrpAddressNo').value=="")
	// {
	// alert("��¼��Ͷ����λ�ĵ�λ��ַ���룡");
	// return false;
	// }
	// if(document.all('DonateContflag').value=="1")
	// {
	// if(document.all('ExamAndAppNo').value==""){
	// alert ("���ŵ������ͱ�������¼���������룡");
	// return false;
	// }
	// }
	if(!checkZT())
		return false;

	if(fm.ContPlanFlag.value == "Y"
			&& (fm.FYCRate.value == null || fm.FYCRate.value == ""))
	{
		alert("�ñ���Ϊ��Ʒ��ϣ���¼��Ӷ�����ֵ��");
		return false;
	}

	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;

	// if((dateDiff(document.all('PolApplyDate').value, document.all('CValiDate').value,
	// "D")<=0) && (dateDiff(document.all('PolApplyDate').value,
	// document.all('CValiDate').value, "M")<=0)){
	// alert("Ͷ����������Ӧ�ڱ�����Ч����֮ǰ");
	// document.all('PolApplyDate').focus();
	// return false;
	// }
	if(mAction == "")
	{
		mAction = "INSERT";
		document.all('fmAction').value = mAction;
		document.all('LoadFlag').value = LoadFlag;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		lockScreen('lkscreen');
		tAction = fm.action;
		fm.action = "../appgrp/ContPolSave.jsp"
		document.getElementById("fm").submit();// �ύ
		// }
	}
}

function ifupdateSpec()
{
	var tSel = SpecInfoGrid.getSelNo();
	if(tSel > 0)
	{
		if(confirm("�Ƿ��޸���ѡ��Լ���ݣ�") == false)
		{
			fm.SpecFlag.value = "NO";
		}
		else
		{
			fm.SpecFlag.value = "YES";
		}
	}
	return true;
}

/*******************************************************************************
 * //�ӹ�˾¼�� wangxw 20100909 ***********************
 */
function InputAccountInfo()
{// ///edit by yaory
	if(fm.GrpNo.value == "")
	{
		alert("����¼��Ͷ����λ��Ϣ���ܽ����ӹ�˾¼����棡");
		return false;
	}
	checkManageCom();
	delGrpVar();
	addGrpVar();
	// alert( fm.GrpName.value );
	showInfo = window.open("../appgrp/AccountInfoMain.jsp?LoadFlag=" + LoadFlag+ "&GrpContNo=" + fm.GrpContNo.value + "&PrtNo=" + fm.PrtNo.value+ "&SupGrpName=" + fm.GrpName.value + "&SupGrpNo=" + fm.GrpNo.value+ "&ManageCom=" + ManageCom + "&ContType=2" + "&scantype="+ scantype + "&ProposalGrpContNo=" + fm.PrtNo.value + "&polNo="+ polNo);
}

/*******************************************************************************
 * //��ӱ����� ***********************
 */

function getintopersoninsured()
{// ///edit by yaory

	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��롲�������嵥����Ϣ���棡");
		return false;
	}
	checkManageCom();
	delGrpVar();
	addGrpVar();
	// parent.fraInterface.window.location =
	// "../appgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;

	// top.fraInterface.window.location =
	// "../appgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
	parent.fraInterface.window.location = "../appgrp/ContInsuredInput.jsp?LoadFlag="+ LoadFlag+ "&ContType=2"+ "&scantype="+ scantype+ "&ProposalGrpContNo="+ fm.PrtNo.value+ "&checktype=2&display=1&polNo="+ polNo+ "&MissionID="+ MissionID+ "&SubMissionID="+ SubMissionID+ "&SupCustomerNo="+ fm.GrpNo.value;
}
function ctrlclaimduty()
{// //edit by Sujie
	var tGrpContNo = document.all('PrtNo').value;
	if(tGrpContNo == null || tGrpContNo == "")
	{
		alert("�ŵ���ͬ��Ϣδ���棬������������֡���");
		return;
	}
	showInfo = window.open("../appgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+ tGrpContNo + "&GrpContNo=" + fm.GrpContNo.value+ "&LoadFlag=" + LoadFlag, "", sFeatures);
	// parent.fraInterface.window.location =
	// "../appgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
}
/*******************************************************************************
 * 
 * ***********************
 */
function grpfilllist()
{
	top.fraInterface.window.location = "../appgrp/ContInsuredInput.jsp?LoadFlag=18&ContType=2&prtNo="+ polNo+ "&scantype="+ scantype+ "&vContNo="+ vContNo+ "&ProposalGrpContNo="+ fm.ProposalGrpContNo.value+ "&checktype=2&display=1";
	// top.fraInterface.window.location =
	// "../appgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
	// parent.fraInterface.window.location =
	// "../appgrp/ContInsuredInput.jsp?LoadFlag=9&ContType=2&scantype="+
	// scantype+"&checktype=2"+"&ScanFlag="+ScanFlag+"&oldContNo="+oldContNo;
}
/*******************************************************************************
 * �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function afterSubmit(FlagStr,content)
{

	unlockScreen('lkscreen');
	showInfo.close();
	fm.StandbyFlag1.value = "";
	// alert(mSwitch.getVar('MissionID'));
	var tid = mSwitch.getVar('MissionID');
	// return;
	// ���Ӻ˱�������ʾ����������Լ���Ȩ������ʾ�û���
	var tsql = "select activityid,missionprop12 From lwmission where missionid='"+ tid + "'";

	/*
	 * var sqlid1="ContPolInputsql1"; var mySql1=new SqlClass();
	 * mySql1.setResourceName("appgrp.ContPolInputsql"); //ָ��ʹ�õ�properties�ļ���
	 * mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id mySql1.addSubPara(tid);//ָ������Ĳ���
	 * var tsql=mySql1.getString();
	 */
	var arrQueryResult = easyExecSql(tsql, 1, 0);
	if(arrQueryResult != null && arrQueryResult[0][0] == "0000002004")
	{
		/*
		 * var sqlid2="ContPolInputsql2"; var mySql2=new SqlClass();
		 * mySql2.setResourceName("appgrp.ContPolInputsql");
		 * //ָ��ʹ�õ�properties�ļ��� mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		 * mySql2.addSubPara(tuser);//ָ������Ĳ��� tsql=mySql2.getString();
		 */
		tsql = "select uwpopedom From lduwuser where usercode='" + tuser + "'";
		var tpope = easyExecSql(tsql, 1, 0);
		if(tpope[0][0] < arrQueryResult[0][1])
		{
			alert("�õ��ѳ������ĺ˱�Ȩ��!");
		}
	}
	// ///////////////////////////
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		showDiv(operateButton, "true");
		showDiv(inputButton, "false");

	}
	fillriskgrid();
	mAction = "";
	if(this.ScanFlag == "1")
	{
		fm.PrtNo.value = prtNo;
		initRiskGrid();
		fillriskgrid();
	}
	if(mWFlag == 1 && FlagStr != "Fail")
	{
		window.location.href("./ContPolInput.jsp");
	}
	querySpec();
	// location.reload();
	try
	{
		fm.RiskCode.value = "";
		fm.RiskCodeName.value = "";
		fm.PayIntv.value = "";
		fm.PayIntvName.value = "";
		fm.DistriFlag.value = "";
		fm.StandbyFlag1.value = "";
		fm.BonusRate.value = "0.0";
		fm.FixprofitRate.value = "0.0";
		fm.ChargeFeeRate.value = "-1";
		fm.CommRate.value = "-1";
		// ɾ����ͬ��Ϣ��ӡˢ�ź͹������Ҫ���ֲ���
		fm.PrtNo.value = prtNo;
		// alert("fm.ManageCom.value:"+fm.ManageCom.value);
		// fm.ManageCom.value = ManageCom;
		fm.GrpSpec.value = '';
		showCodeName();
	}
	catch (ex)
	{
	}
}

function afterSubmit1(FlagStr,content)
{
	unlockScreen('lkscreen');
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		showDiv(operateButton, "true");
		showDiv(inputButton, "false");
		top.close();
	}

	mAction = "";
	if(this.ScanFlag == "1")
	{
		fm.PrtNo.value = prtNo;
		initRiskGrid();
		fillriskgrid();
	}
	if(mWFlag == 1 && FlagStr != "Fail")
	{
		window.location.href("./ContPolInput.jsp");
	}
	// location.reload();
}

/*******************************************************************************
 * "����"��ť��Ӧ���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function resetForm()
{
	try
	{
		initForm();
		document.all('PrtNo').value = prtNo;
	}
	catch (re)
	{
		alert("��GroupPolInput.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

/*******************************************************************************
 * "ȡ��"��ť��Ӧ���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function cancelForm()
{
	showDiv(operateButton, "true");
	showDiv(inputButton, "false");
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

/*******************************************************************************
 * Click�¼������������ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function addClick()
{
	if(document.all('GetFlag').value == "4" && document.all('BankCode').value == "")
	{
		alert("����������ת�ʵ����к͸����е��ʺ�");
		document.all('BankCode').focus();
		return false;
	}
	// if(dateDiff(document.all('PolApplyDate').value, document.all('CValiDate').value,
	// "D")<0){
	// alert("Ͷ����������Ӧ�ڱ�����Ч����֮ǰ");
	// document.all('PolApplyDate').focus();
	// return false;
	// }
	// Ա��������=��ְ����+��������+������Ա����
	var intPeoples = parseInt(document.all('Peoples').value);
	var intAppntOnWorkPeoples = parseInt(document.all('AppntOnWorkPeoples').value);
	var intAppntOffWorkPeoples = parseInt(document.all('AppntOffWorkPeoples').value);
	var intAppntOtherPeoples = parseInt(document.all('AppntOtherPeoples').value);
	var intPeoples3 = parseInt(document.all('Peoples3').value);

	if(intPeoples != intAppntOnWorkPeoples + intAppntOffWorkPeoples
			+ intAppntOtherPeoples)
	{
		alert("Ա��������Ӧ��Ϊ"
				+ (intAppntOnWorkPeoples + intAppntOffWorkPeoples + intAppntOtherPeoples));
		document.all('Peoples').focus();
		return false;
	}

	// Ա��������Ӧ���ڵ��ڱ�������(��Ա)
	if(intPeoples < intPeoples3)
	{
		alert("Ա��������Ӧ���ڵ��ڱ�������");
		document.all('Peoples3').focus();
		return false;
	}

	// ����������Ӧ�Ĵ���
	// showDiv( operateButton, "false" );
	showDiv(operateButton, "true");
	showDiv(inputButton, "true");

	document.all('RiskCode').value = "";

	// ��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
	if(BQFlag == "2")
	{
		var strSql = "select grppolno, grpno from lcgrppol where prtno='"+ prtNo+ "' and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
		var arrResult = easyExecSql(strSql);
		// alert(arrResult);

		mOperate = 1;
		afterQuery(arrResult);

		// strSql = "select GrpNo,GrpName,GrpAddress,Satrap from LDGrp where
		// GrpNo='" + arrResult[0][1] + "'";
		// arrResult = easyExecSql(strSql);
		// mOperate = 2;
		// afterQuery(arrResult);

		document.all('RiskCode').value = BQRiskCode;
		document.all('RiskCode').className = "readonly";
		document.all('RiskCode').readOnly = true;
		document.all('RiskCode').ondblclick = "";
	}

	document.all('ContNo').value = "";
	document.all('ProposalGrpContNo').value = "";
}

/*******************************************************************************
 * Click�¼������������ѯ��ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function queryClick()
{
	if(this.ScanFlag == "1")
	{
		alert("��ɨ���¼�벻�����ѯ!");
		return false;
	}
	if(mOperate == 0)
	{
		mOperate = 1;
		// cContNo = document.all( 'ContNo' ).value;
		showInfo = window.open("./GroupPolQueryMain.jsp", "", sFeatures);
	}
}

/*******************************************************************************
 * Click�¼�����������޸ġ�ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function updateClick()
{
	if(!checkZT())
		return false;
	var sysdate = "select to_char(sysdate,'YYYY-MM-DD') from dual";
	var arrQuery = easyExecSql(sysdate, 1, 0);
	if(fm.CValiDate.value < arrQuery[0][0] && fm.BackDateRemark.value == "")
	{
		alert("��������Ч���ڴ���׷����������ڱ���������׷�ݱ�ע��ע��׷���ڼ�������ر�Լ��");
		return false;
	}

	// �������� wangxw 20100925
	if(fm.OldGrpcontno.value != null && fm.OldGrpcontno.value != "")
	{
		if(!confirm("�Ƿ��������������"))
		{
			fm.OldGrpcontno1.value = document.all('ProposalGrpContNo').value;
			fm.ReNewTime.value = "1";
			return;
		}
		else
		{
			// ͳ����������
			var strSql = " select ReNewTimes from LCGrpContReNewTrace where grpcontno= '"
					+ fm.OldGrpcontno.value + "'";
			var ReNewTime = easyExecSql(strSql);
			if(ReNewTime != null && ReNewTime != "")
			{
				fm.ReNewTime.value = ReNewTime * 1 + 1;
			}
			else
			{
				alert("������ԭʼ�����Ų����ڣ���ȷ���Ƿ���ȷ��");
				return;
			}
		}
	}
	else
	{
		fm.OldGrpcontno1.value = document.all('ProposalGrpContNo').value;
		fm.ReNewTime.value = "1";
	}

	if(document.all('GetFlag').value == "4" && document.all('BankCode').value == "")
	{
		alert("����������ת�ʵ����к͸����е��ʺ�");
		document.all('BankCode').focus();
		return false;
	}

	// if(dateDiff(document.all('PolApplyDate').value, document.all('CValiDate').value,
	// "D")<0){
	// alert("Ͷ����������Ӧ�ڱ�����Ч����֮ǰ");
	// document.all('PolApplyDate').focus();
	// return false;
	// }
	// ����������Ϊ��������ʱ����Ҫ¼����������ҵ����Ա
	if(!IsMultiAgent())
	{
		return false;
	}
	// ��ϴǮ��Ϣ��¼��У��
	// if(document.all('ClientCare').value=="")
	// {
	// alert ("��¼��ͻ���ע���ܣ�");
	// return false;
	// }
	// if(document.all('ClientNeedJudge').value=="")
	// {
	// alert ("��¼��Ͷ��������Ĳ�Ʒ��ͻ����������Ƿ�һ�£�");
	// return false;
	// }
	// else{
	// if(document.all('ClientNeedJudge').value=="N")
	// {
	// if(document.all('FundReason').value=="")
	// {
	// alert ("�ͻ�����ı��������������Բ���ʱ����¼���Ƿ���������");
	// return false;
	// }
	// }
	// }
	// if(document.all('FundJudge').value=="")
	// {
	// alert ("��¼���ʽ��Ƿ���Դ��Ͷ����λ��");
	// return false;
	// }
	// if(document.all('DonateContflag').value=="1")
	// {
	// if(document.all('ExamAndAppNo').value==""){
	// alert ("���ŵ������ͱ�������¼���������룡");
	// return false;
	// }
	// }
	// �ж��Ƿ��޸���ѡ��Լ����
	ifupdateSpec();
	var tProposalGrpContNo = "";
	tProposalGrpContNo = document.all('ProposalGrpContNo').value;
	if(verifyInput2() == false)
		return false;
	if(checkSaleChnl() == false)
		return false;
	// ImpartGrid.delBlankLine();
	if(tProposalGrpContNo == null || tProposalGrpContNo == "")
		if(this.ScanFlag == "1")
		{
			alert("��δ¼������,�������Ӻ�ͬ��Ϣ,�ٽ����޸�!");
		}
		else
		{
			alert("������Ͷ������ѯ�������ٽ����޸�!");
		}
	else
	{
		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ showStr;

		if(mAction == "")
		{
			// showSubmitFrame(mDebug);
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			lockScreen('lkscreen');
			mAction = "UPDATE";
			document.all('fmAction').value = mAction;
			fm.action = "../appgrp/ContPolSave.jsp"
			document.getElementById("fm").submit();// �ύ
		}
	}
}

/*******************************************************************************
 * Click�¼����������ɾ����ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function deleteClick()
{
	var tProposalGrpContNo = "";
	tProposalGrpContNo = document.all('ProposalGrpContNo').value;
	if(tProposalGrpContNo == null || tProposalGrpContNo == "")
		alert("������Ͷ������ѯ�������ٽ���ɾ��!");
	else
	{
		if(confirm("��ȷ��Ҫɾ�����ŵ���"))
		{
			var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
			if(confirm("�Ƿ�ɾ���������ͬ�µ�������Լ���ݣ�"))
			{
				fm.SpecFlag.value = "YES";
			}
			else
			{
				fm.SpecFlag.value = "NO";
			}
			if(mAction == "")
			{
				lockScreen('lkscreen');
				// showSubmitFrame(mDebug);
				var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
				mAction = "DELETE";
				document.all('fmAction').value = mAction;
				fm.action = "../appgrp/ContPolSave.jsp"
				document.getElementById("fm").submit();// �ύ
			}
		}
	}
}

/*******************************************************************************
 * ��ʾdiv ���� �� ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ ����ֵ�� ��
 * ********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if(cShow == "true")
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";
}

/*******************************************************************************
 * ����������������Ϣ����ťʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function intoPol()
{
	// ����������Ӧ�Ĵ���
	tProposalGrpContNo = fm.ProposalGrpContNo.value;
	if(tProposalGrpContNo == "")
	{
		alert("��������¼�뼯����Ϣ���ܽ��������Ϣ���֡�");
		return false
	}

	// �Ѽ�����Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch; // ���ݴ�
	putGrpPol();

	try
	{
		goToPic(2)
	}
	catch (e)
	{
	}

	try
	{
		parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag="
				+ LoadFlag + "&type=" + type;
	}
	catch (e)
	{
		parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=2&type="
				+ type;
	}
}

/*******************************************************************************
 * �Ѽ�����Ϣ�����ڴ� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function putGrpPol()
{
	delGrpPolVar();
	addIntoGrpPol();
}

/*******************************************************************************
 * �Ѽ�����Ϣ����ӵ������� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function addIntoGrpPol()
{
	try
	{
		mSwitch.addVar("intoPolFlag", "", "GROUPPOL");
	}
	catch (ex)
	{
	};
	// body��Ϣ
	try
	{
		mSwitch.addVar("BODY", "", window.document.body.innerHTML);
	}
	catch (ex)
	{
	};
	// ������Ϣ
	// ��"./AutoCreatLDGrpInit.jsp"�Զ�����
	try
	{
		mSwitch.addVar('GrpNo', '', document.all('GrpNo').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PrtNo', '', document.all('PrtNo').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Password', '', document.all('Password').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpName', '', document.all('GrpName').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpAddressCode', '', document.all('GrpAddressCode').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpAddress', '', document.all('GrpAddress').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpZipCode', '', document.all('GrpZipCode').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BusinessType', '', document.all('BusinessType').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpNature', '', document.all('GrpNature').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Peoples', '', document.all('Peoples').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('RgtMoney', '', document.all('RgtMoney').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Asset', '', document.all('Asset').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('NetProfitRate', '', document.all('NetProfitRate').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('MainBussiness', '', document.all('MainBussiness').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Corporation', '', document.all('Corporation').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ComAera', '', document.all('ComAera').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('LinkMan1', '', document.all('LinkMan1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Department1', '', document.all('Department1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('HeadShip1', '', document.all('HeadShip1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Phone1', '', document.all('Phone1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('E_Mail1', '', document.all('E_Mail1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Fax1', '', document.all('Fax1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('LinkMan2', '', document.all('LinkMan2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Department2', '', document.all('Department2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('HeadShip2', '', document.all('HeadShip2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Phone2', '', document.all('Phone2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('E_Mail2', '', document.all('E_Mail2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Fax2', '', document.all('Fax2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Fax', '', document.all('Fax').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Phone', '', document.all('Phone').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GetFlag', '', document.all('GetFlag').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Satrap', '', document.all('Satrap').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('EMail', '', document.all('EMail').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('FoundDate', '', document.all('FoundDate').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppntOnWorkPeoples', '',
				document.all('AppntOnWorkPeoples').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppntOffWorkPeoples', '',
				document.all('AppntOffWorkPeoples').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppntOtherPeoples', '',
				document.all('AppntOtherPeoples').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BankAccNo', '', document.all('BankAccNo').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BankCode', '', document.all('BankCode').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpGroupNo', '', document.all('GrpGroupNo').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('State', '', document.all('State').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BlacklistFlag', '', document.all('BlacklistFlag').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Currency', '', document.all('Currency').value);
	}
	catch (ex)
	{
	};

	try
	{
		mSwitch.addVar("ContNo", "", document.all('ContNo').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("ProposalGrpContNo", "",
				document.all('ProposalGrpContNo').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("ManageCom", "", document.all('ManageCom').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("SaleChnl", "", document.all('SaleChnl').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("AgentCom", "", document.all('AgentCom').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("AgentCode", "", document.all('AgentCode').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("AgentGroup", "", document.all('AgentGroup').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("AgentCode1", "", document.all('AgentCode1').value);
	}
	catch (ex)
	{
	};

	try
	{
		mSwitch.addVar("RiskCode", "", document.all('RiskCode').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("RiskVersion", "", document.all('RiskVersion').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("CValiDate", "", document.all('CValiDate').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar("PolApplyDate", "", document.all('PolApplyDate').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('StandbyFlag1', '', document.all('StandbyFlag1').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('StandbyFlag2', '', document.all('StandbyFlag2').value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('StandbyFlag3', '', document.all('StandbyFlag3').value);
	}
	catch (ex)
	{
	};
	// ------20060217---changing start----------------------------
	try
	{
		mSwitch.addVar('AgentType', '', document.all('AgentType').value);
	}
	catch (ex)
	{
	};
	// -------------changing end------------------------------------

}

/*******************************************************************************
 * �Ѽ�����Ϣ�ӱ�����ɾ�� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function delGrpPolVar()
{
	try
	{
		mSwitch.deleteVar("intoPolFlag");
	}
	catch (ex)
	{
	};
	// body��Ϣ
	try
	{
		mSwitch.deleteVar("BODY");
	}
	catch (ex)
	{
	};
	// ������Ϣ
	// ��"./AutoCreatLDGrpInit.jsp"�Զ�����
	try
	{
		mSwitch.deleteVar('GrpNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PrtNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Password');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpName');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpAddressCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpAddress');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpZipCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BusinessType');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpNature');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Peoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('RgtMoney');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Asset');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('NetProfitRate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('MainBussiness');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Corporation');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ComAera');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('LinkMan1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Department1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('HeadShip1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Phone1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('E_Mail1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Fax1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('LinkMan2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Department2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('HeadShip2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Phone2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('E_Mail2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Fax2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Fax');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Phone');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GetFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Satrap');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('EMail');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('FoundDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntOnWorkPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntOffWorkPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntOtherPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BankAccNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BankCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpGroupNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('State');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BlacklistFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Currency');
	}
	catch (ex)
	{
	};
	// -------20060217------changint start--------------------------
	try
	{
		mSwitch.deleteVar('AgentType');
	}
	catch (ex)
	{
	};
	// ------------changing end-------------------------------------

	mSwitch.deleteVar("ContNo");
	mSwitch.deleteVar("ProposalGrpContNo");
	mSwitch.deleteVar("ManageCom");
	mSwitch.deleteVar("SaleChnl");
	mSwitch.deleteVar("AgentCom");
	mSwitch.deleteVar("AgentCode");
	mSwitch.deleteVar("AgentCode1");

	mSwitch.deleteVar("RiskCode");
	mSwitch.deleteVar("RiskVersion");
	mSwitch.deleteVar("CValiDate");

}

/*******************************************************************************
 * Click�¼�����˫����Ͷ����λ�ͻ��š�¼���ʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showAppnt1()
{
	if(mOperate == 0)
	{
		mOperate = 2;
		showInfo = window.open("../sys/GroupMain.html", "", sFeatures);
	}
}
function showAppnt()
{
	if(document.all("GrpNo").value == "")
	{
		showAppnt1();
	}
	else
	{
		arrResult = easyExecSql(
				"select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='"
						+ document.all("GrpNo").value + "'", 1, 0);
		if(arrResult == null)
		{
			alert("δ�鵽Ͷ����λ��Ϣ");
		}
		else
		{
			displayAddress(arrResult[0]);
		}
	}
}

/*******************************************************************************
 * ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ ���� �� ��ѯ���صĶ�ά���� ����ֵ�� ��
 * ********************************************************************
 */
function afterQuery(arrQueryResult)
{
	// alert("here:" + arrQueryResult + "\n" + mOperate);
	// alert("1");
	// alert(arrQueryResult);
	if(arrQueryResult != null)
	{
		// alert("ok");
		arrResult = arrQueryResult;
		// alert(mOperate);
		if(mOperate == 1)
		{ // ��ѯ����Ͷ����
			// alert("ok");
			document.all('GrpContNo').value = arrQueryResult[0][0];
			arrResult = easyExecSql(
					"select * from LCGrpCont where GrpContNo = '"
							+ arrQueryResult[0][0] + "'", 1, 0);
			if(arrResult == null)
			{
				// alert("δ�鵽�ŵ���Ϣ");
			}
			else
			{
				displayLCGrpCont(arrResult[0]);
				// alert("2");
				var tgrpcontno = arrResult[0][0];
				fillriskgrid();
				// alert("3");
				arrResult = easyExecSql(
						"select a.* from LCGrpAddress a where a.AddressNo=(select AddressNo from LCGrpAppnt  where GrpContNo = '"
								+ tgrpcontno
								+ "') and a.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '"
								+ arrResult[0][0] + "')", 1, 0);
				if(arrResult == null)
				{
					alert("δ�鵽Ͷ����λ��ַ��Ϣ");

				}
				else
				{
					displayAddress1(arrResult[0]);
					// alert("3");
				}
				arrResult = easyExecSql(
						"select b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '"
								+ tgrpcontno + "')", 1, 0);
				if(arrResult == null)
				{
					alert("δ�鵽Ͷ����λ��Ϣ");

				}
				else
				{
					displayAddress2(arrResult[0]);
					// alert("4");
				}
				arrResult = easyExecSql(
						"select c.Name,c.PostalAddress,c.ZipCode,c.Phone from  LCGrpAppnt c where c.GrpContNo = '"
								+ tgrpcontno + "'", 1, 0);
				if(arrResult != null)
				{
					displayAddress3(arrResult[0]);
					// alert("5");
				}
				// ////////////////////////////////////////////////////////////
				// modifyed by wood ��֮ǰ��sql�����ܹ�˾��¼ʱ��ѯ�޷���ʾ����ҵ��Ա��Ϣ
				var ttt;
				var muliAgentSQL;
				if(ManageCom != "%" && ManageCom.length > 0)
				{
					ttt = ManageCom.substring(0, 2);
					muliAgentSQL = "select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "
							+ "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d "
							+ "where 1=1 "
							+ "and a.GrpContNo='"
							+ arrQueryResult[0][0]
							+ "' "
							+ "and b.agentgroup=c.agentgroup "
							+ "and c.agentcode!=a.agentcode "
							+ "and d.agentcode=c.agentcode "
							+ "and d.agentcode!=a.agentcode "
							+ "and c.grpcontno=a.GrpContNo ";
				}
				else
				{
					// ttt=ManageCom;
					muliAgentSQL = "select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "
							+ "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d "
							+ "where 1=1 "
							+ "and a.GrpContNo='"
							+ arrQueryResult[0][0]
							+ "' "
							+ "and b.agentgroup=c.agentgroup "
							+ "and c.agentcode!=a.agentcode "
							+ "and d.agentcode=c.agentcode "
							+ "and d.agentcode!=a.agentcode "
							+ "and c.grpcontno=a.GrpContNo ";
				}
				// alert(ttt);
				// ��ѯ���ж�ҵ��Ա��Ϣ

				;
				// alert("--muliAgentSQL="+muliAgentSQL);
				// fm.Asset.value=muliAgentSQL;
				turnPage.strQueryResult = easyQueryVer3(muliAgentSQL, 1, 0, 1);

				// alert("--turnPage.strQueryResult=="+turnPage.strQueryResult);
				// �ж��Ƿ��ѯ�ɹ�,�ɹ�����ʾ��֪��Ϣ
				if(turnPage.strQueryResult)
				{
					// ��ѯ�ɹ������ַ��������ض�ά����
					turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
					// ���ó�ʼ������MULTILINE����
					turnPage.pageDisplayGrid = MultiAgentGrid;
					// ����SQL���
					turnPage.strQuerySql = muliAgentSQL;
					// ���ò�ѯ��ʼλ��
					turnPage.pageIndex = 0;
					// �ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					arrDataSet = turnPage.getData(turnPage.arrDataCacheSet,
							turnPage.pageIndex, MAXSCREENLINES);
					// ����MULTILINE������ʾ��ѯ���
					displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
					// ��ʾ��ҵ��Ա
					// alert("1");
					displayMultiAgent();
					// alert("2");
				}
				else
				{
					// ��ʼ����ҵ��Ա�б�
					// alert("saafas");
					initMultiAgentGrid();
				}
				// alert("here!arrQueryResult[0][0]=="+arrQueryResult[0][0]);
				// ��ѯ��ҵ��Ա��Ϣ
				arrResult = easyExecSql(
						"select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "
								+ "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d "
								+ "where 1=1 "
								+ "and a.GrpContNo='"
								+ arrQueryResult[0][0]
								+ "' "
								+ "and b.agentgroup=c.agentgroup "
								+ "and c.agentcode=a.agentcode "
								+ "and d.agentcode=a.agentcode "
								+ "and c.grpcontno=a.GrpContNo", 1, 0);

				if(arrResult == null)
				{
					// alert("δ�õ���ҵ��Ա��Ϣ");
					// return;
				}
				else
				{
					displayMainAgent(); // ��ʾ��ҵ��Ա����ϸ����
				}

				// ///////////////////////////////////////////////////////////
			}
		}

		if(mOperate == 2)
		{ // Ͷ����λ��Ϣ
			arrResult = easyExecSql(
					"select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='"
							+ arrQueryResult[0][0] + "'", 1, 0);
			if(arrResult == null)
			{
				alert("δ�鵽Ͷ����λ��Ϣ");
			}
			else
			{
				displayAddress(arrResult[0]);
			}
		}
	}
	// turnPage.queryModal("select
	// ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart
	// where GrpContNo='" + arrQueryResult[0][0] + "'",ImpartGrid);
	// turnPage.queryModal("select
	// InsuYear,InsuYearFlag,InsuContent,Rate,EnsureContent,Peoples,RecompensePeoples,OccurMoney,RecompenseMoney,PendingMoney,SerialNo
	// from LCHistoryImpart where GrpContNo='"+ arrQueryResult[0][0] +
	// "'",HistoryImpartGrid);
	// turnPage.queryModal("select
	// OcurTime,DiseaseName,DiseasePepoles,CureMoney,Remark,SerialNo from
	// LCDiseaseImpart where GrpContNo='"+ arrQueryResult[0][0] +
	// "'",DiseaseGrid);
	// �ͻ��������
	// turnPage.queryModal("select ServKind,ServDetail,ServChoose,ServRemark
	// from LCGrpServInfo where GrpContNo='" + arrQueryResult[0][0] +
	// "'",ServInfoGrid);

	mOperate = 0; // �ָ���̬
}
/*******************************************************************************
 * �Ѳ�ѯ���صĿͻ���ַ���ݷ��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function displayAddress()
{
	try
	{
		document.all('GrpNo').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('CustomerNo').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddressNo').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddress').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpZipCode').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan1').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department1').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip1').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone1').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail1').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax1').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan2').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department2').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip2').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone2').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail2').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax2').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Operator').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeDate').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeTime').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyDate').value = "";
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyTime').value = "";
	}
	catch (ex)
	{
	};
	// ������ldgrp��
	try
	{
		document.all('GrpNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpName').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BusinessType').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpNature').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RgtMoney').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Asset').value = arrResult[0][6];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('NetProfitRate').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MainBussiness').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Corporation').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ComAera').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FoundDate').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntOnWorkPeoples').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntOffWorkPeoples').value = arrResult[0][15];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntOtherPeoples').value = arrResult[0][16];
	}
	catch (ex)
	{
	};
}
function displayAddress1()
{
	try
	{
		document.all('GrpNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('CustomerNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddressNo').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddress').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpZipCode').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan1').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department1').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip1').value = arrResult[0][6];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone1').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail1').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax1').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan2').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department2').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip2').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone2').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail2').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax2').value = arrResult[0][15];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Operator').value = arrResult[0][16];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeDate').value = arrResult[0][17];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeTime').value = arrResult[0][18];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyDate').value = arrResult[0][19];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyTime').value = arrResult[0][20];
	}
	catch (ex)
	{
	};

}

function displayAddress2()
{
	// ������ldgrp��
	try
	{
		document.all('GrpName').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	// wangxw 20100908 ԭ��ע�͵��ģ���֪ԭ���ȷſ�
	try
	{
		document.all('BusinessType').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpNature').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RgtMoney').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Asset').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('NetProfitRate').value = arrResult[0][6];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MainBussiness').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Corporation').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ComAera').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FoundDate').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntOnWorkPeoples').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntOffWorkPeoples').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntOtherPeoples').value = arrResult[0][15];
	}
	catch (ex)
	{
	};
	showCodeName();
}
function displayAddress3()
{
	try
	{
		document.all('GrpName').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddress').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpZipCode').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
}
/*******************************************************************************
 * �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function displayAppnt()
{
	// ��"./AutoCreatLDGrpInit.jsp"�Զ�����
	try
	{
		document.all('GrpNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Password').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpName').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddressCode').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddress').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpZipCode').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpNature').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RgtMoney').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Asset').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('NetProfitRate').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MainBussiness').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Corporation').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ComAera').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan1').value = arrResult[0][15];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department1').value = arrResult[0][16];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip1').value = arrResult[0][17];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone1').value = arrResult[0][18];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail1').value = arrResult[0][19];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax1').value = arrResult[0][20];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan2').value = arrResult[0][21];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department2').value = arrResult[0][22];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip2').value = arrResult[0][23];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone2').value = arrResult[0][24];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail2').value = arrResult[0][25];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax2').value = arrResult[0][26];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax').value = arrResult[0][27];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone').value = arrResult[0][28];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetFlag').value = arrResult[0][29];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Satrap').value = arrResult[0][30];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('EMail').value = arrResult[0][31];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FoundDate').value = arrResult[0][32];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BankAccNo').value = arrResult[0][33];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BankCode').value = arrResult[0][34];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpGroupNo').value = arrResult[0][35];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('State').value = arrResult[0][36];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Remark').value = arrResult[0][37];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BlacklistFlag').value = arrResult[0][38];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Operator').value = arrResult[0][39];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeDate').value = arrResult[0][40];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeTime').value = arrResult[0][41];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyDate').value = arrResult[0][42];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyTime').value = arrResult[0][43];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FIELDNUM').value = arrResult[0][44];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PK').value = arrResult[0][45];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('fDate').value = arrResult[0][46];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('mErrors').value = arrResult[0][47];
	}
	catch (ex)
	{
	};
}
function displayLCGrpCont()
{
	try
	{
		document.all('GrpContNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ProposalGrpContNo').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PrtNo').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SaleChnl').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SecondAgentType').value = arrResult[0][6];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ManageCom').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentManageCom').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	// alert(document.all('ManageCom').value);
	try
	{
		document.all('AgentCom').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentType').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	if(document.all('AgentType').value != null && document.all('AgentType').value == "12")// 2010-5-14
																				// ����������Ϊ12
	{
		DivContPlanFlag.style.display = "";
	}
	else
	{
		DivContPlanFlag.style.display = "none";
	}
	try
	{
		document.all('AgentCode').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentGroup').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentCode1').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Password').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Password2').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppntNo').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddressNo').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples2').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpName').value = arrResult[0][15];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BusinessType').value = arrResult[0][16];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpNature').value = arrResult[0][17];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RgtMoney').value = arrResult[0][18];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Asset').value = arrResult[0][19];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('NetProfitRate').value = arrResult[0][20];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MainBussiness').value = arrResult[0][21];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Corporation').value = arrResult[0][22];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ComAera').value = arrResult[0][23];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax').value = arrResult[0][24];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone').value = arrResult[0][25];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetFlag').value = arrResult[0][26];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Satrap').value = arrResult[0][27];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('EMail').value = arrResult[0][28];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FoundDate').value = arrResult[0][29];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpGroupNo').value = arrResult[0][30];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BankCode').value = arrResult[0][31];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BankAccNo').value = arrResult[0][32];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AccName').value = arrResult[0][33];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('DisputedFlag ').value = arrResult[0][34];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('OutPayFlag').value = arrResult[0][35];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetPolMode').value = arrResult[0][36];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Lang').value = arrResult[0][37];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Currency').value = arrResult[0][38];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LostTimes').value = arrResult[0][39];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PrintCount').value = arrResult[0][40];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RegetDate').value = arrResult[0][41];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LastEdorDate').value = arrResult[0][42];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LastGetDate').value = arrResult[0][43];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LastLoanDate').value = arrResult[0][44];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SpecFlag').value = arrResult[0][45];
	}
	catch (ex)
	{
	};
	// try {document.all('GrpSpec').value= arrResult[0][46];} catch(ex) { };
	try
	{
		document.all('PayMode').value = arrResult[0][47];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SignCom').value = arrResult[0][48];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SignDate').value = arrResult[0][49];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SignTime').value = arrResult[0][50];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('CValiDate').value = arrResult[0][51];
	}
	catch (ex)
	{
	};
	// try {document.all('PayIntv').value= arrResult[0][52];} catch(ex) { };
	try
	{
		document.all('ManageFeeRate').value = arrResult[0][53];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ExpPeoples').value = arrResult[0][54];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ExpPremium').value = arrResult[0][55];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ExpAmnt').value = arrResult[0][56];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples').value = arrResult[0][57];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Mult').value = arrResult[0][58];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Prem').value = arrResult[0][59];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Amnt').value = arrResult[0][60];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SumPrem').value = arrResult[0][61];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SumPay').value = arrResult[0][62];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Dif').value = arrResult[0][63];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Remark').value = arrResult[0][64];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('StandbyFlag1').value = arrResult[0][65];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('StandbyFlag2').value = arrResult[0][66];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('StandbyFlag3').value = arrResult[0][67];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('InputOperator').value = arrResult[0][68];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('InputDate').value = arrResult[0][69];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('InputTime').value = arrResult[0][70];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveFlag').value = arrResult[0][71];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveCode').value = arrResult[0][72];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveDate').value = arrResult[0][73];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveTime').value = arrResult[0][74];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('UWOperator').value = arrResult[0][75];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('UWFlag').value = arrResult[0][76];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('UWDate').value = arrResult[0][77];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('UWTime').value = arrResult[0][78];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppFlag').value = arrResult[0][79];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PolApplyDate').value = arrResult[0][80];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('CustomGetPolDate').value = arrResult[0][81];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetPolDate').value = arrResult[0][82];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetPolTime').value = arrResult[0][83];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('State').value = arrResult[0][84];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Operator').value = arrResult[0][85];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeDate').value = arrResult[0][86];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeTime').value = arrResult[0][87];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyDate').value = arrResult[0][88];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyTime').value = arrResult[0][89];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('EnterKind').value = arrResult[0][90];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AmntGrade').value = arrResult[0][91];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples3').value = arrResult[0][92];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('OnWorkPeoples').value = arrResult[0][93];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('OffWorkPeoples').value = arrResult[0][94];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('OtherPeoples').value = arrResult[0][95];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RelaPeoples').value = arrResult[0][96];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RelaMatePeoples').value = arrResult[0][97];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RelaYoungPeoples').value = arrResult[0][98];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RelaOtherPeoples').value = arrResult[0][99];
	}
	catch (ex)
	{
	};

	try
	{
		document.all('ReportNo').value = arrResult[0][113];
	}
	catch (ex)
	{
	};
	// try {alert(arrResult[0][114]) ; } catch(ex) { };
	try
	{
		document.all('ConferNo').value = arrResult[0][114];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SignReportNo').value = arrResult[0][115];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentConferNo').value = arrResult[0][116];
	}
	catch (ex)
	{
	};
	// try {document.all('ClientCare').value= arrResult[0][118]; } catch(ex) { };
	// try {document.all('ClientNeedJudge').value= arrResult[0][119]; } catch(ex) { };
	// try {document.all('FundJudge').value= arrResult[0][120]; } catch(ex) { };
	// try {document.all('FundReason').value= arrResult[0][121]; } catch(ex) { };

	if(arrResult[0][122] == null || arrResult[0][122] == "")
	{
		// try {document.all('EdorCalType').value= "N"; } catch(ex) { }; //Ĭ��ΪN
	}
	else
	{
		// try {document.all('EdorCalType').value= arrResult[0][122]; } catch(ex) { };
	}
	try
	{
		document.all('BackDateRemark').value = arrResult[0][121];
	}
	catch (ex)
	{
	};
	if(arrResult[0][124] == null || arrResult[0][124] == "")
	{
		// try {document.all('DonateContflag').value= "0"; } catch(ex) { }; //Ĭ��Ϊ0
	}
	else
	{
		// try {document.all('DonateContflag').value= arrResult[0][124]; } catch(ex) {
		// };
	}
	// try {document.all('ExamAndAppNo').value= arrResult[0][125]; } catch(ex) { };
	// ���� ����Ʒ���Ӷ�������־�� �� ��Ӷ�����ֵ�� --2010-03-26-- hanbin
	try
	{
		document.all('ContPlanFlag').value = arrResult[0][130];
	}
	catch (ex)
	{
	};

	// �жϡ�Ӷ�����ֵ�� ¼����Ƿ���ʾ ��ʾΪ�ٷֱ�
	if(document.all('ContPlanFlag').value != null
			&& document.all('ContPlanFlag').value != ""
			&& document.all('ContPlanFlag').value == "Y")
	{
		DivContPlan.style.display = '';
		try
		{
			document.all('FYCRate').value = arrResult[0][131] * 100;
		}
		catch (ex)
		{
		};
	}
	else
	{
		DivContPlan.style.display = 'none';
		try
		{
			document.all('FYCRate').value = "";
		}
		catch (ex)
		{
		};
	}
}
function displayLCGrpPol()
{
	// ��"./AutoCreatLCGrpPolInit.jsp"�Զ�����
	try
	{
		document.all('ContNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ProposalGrpContNo').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PrtNo').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('KindCode').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RiskCode').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RiskVersion').value = arrResult[0][6];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SignCom').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ManageCom').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	// alert(document.all('ManageCom').value );
	try
	{
		document.all('AgentCom').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentType').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SaleChnl').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Password').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpNo').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Password2').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpName').value = arrResult[0][15];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddressCode').value = arrResult[0][16];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddress').value = arrResult[0][17];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpZipCode').value = arrResult[0][18];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BusinessType').value = arrResult[0][19];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpNature').value = arrResult[0][20];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples2').value = arrResult[0][21];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RgtMoney').value = arrResult[0][22];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Asset').value = arrResult[0][23];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('NetProfitRate').value = arrResult[0][24];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MainBussiness').value = arrResult[0][25];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Corporation').value = arrResult[0][26];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ComAera').value = arrResult[0][27];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan1').value = arrResult[0][28];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department1').value = arrResult[0][29];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip1').value = arrResult[0][30];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone1').value = arrResult[0][31];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail1').value = arrResult[0][32];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax1').value = arrResult[0][33];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan2').value = arrResult[0][34];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department2').value = arrResult[0][35];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip2').value = arrResult[0][36];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone2').value = arrResult[0][37];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail2').value = arrResult[0][38];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax2').value = arrResult[0][39];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax').value = arrResult[0][40];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone').value = arrResult[0][41];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetFlag').value = arrResult[0][42];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Satrap').value = arrResult[0][43];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('EMail').value = arrResult[0][44];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FoundDate').value = arrResult[0][45];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BankAccNo').value = arrResult[0][46];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BankCode').value = arrResult[0][47];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpGroupNo').value = arrResult[0][48];
	}
	catch (ex)
	{
	};
	// try { document.all('PayIntv').value = arrResult[0][49]; } catch(ex) { };
	try
	{
		document.all('PayMode').value = arrResult[0][50];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('CValiDate').value = arrResult[0][51];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetPolDate').value = arrResult[0][52];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SignDate').value = arrResult[0][53];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FirstPayDate').value = arrResult[0][54];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PayEndDate').value = arrResult[0][55];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PaytoDate').value = arrResult[0][56];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('RegetDate').value = arrResult[0][57];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Peoples').value = arrResult[0][58];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Mult').value = arrResult[0][59];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Prem').value = arrResult[0][60];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Amnt').value = arrResult[0][61];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SumPrem').value = arrResult[0][62];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SumPay').value = arrResult[0][63];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Dif').value = arrResult[0][64];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('SSFlag').value = arrResult[0][65];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PeakLine').value = arrResult[0][66];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetLimit').value = arrResult[0][67];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetRate').value = arrResult[0][68];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MaxMedFee').value = arrResult[0][69];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ExpPeoples').value = arrResult[0][70];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ExpPremium').value = arrResult[0][71];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ExpAmnt').value = arrResult[0][72];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('DisputedFlag').value = arrResult[0][73];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('BonusRate').value = arrResult[0][74];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Lang').value = arrResult[0][75];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Currency').value = arrResult[0][76];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('State').value = arrResult[0][77];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LostTimes').value = arrResult[0][78];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AppFlag').value = arrResult[0][79];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveCode').value = arrResult[0][80];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveDate').value = arrResult[0][81];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('UWOperator').value = arrResult[0][82];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentCode').value = arrResult[0][83];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentGroup').value = arrResult[0][84];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('AgentCode1').value = arrResult[0][85];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Remark').value = arrResult[0][86];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('UWFlag').value = arrResult[0][87];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('OutPayFlag').value = arrResult[0][88];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ApproveFlag').value = arrResult[0][89];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('EmployeeRate').value = arrResult[0][90];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FamilyRate').value = arrResult[0][91];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Operator').value = arrResult[0][92];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeDate').value = arrResult[0][93];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('MakeTime').value = arrResult[0][94];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyDate').value = arrResult[0][95];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ModifyTime').value = arrResult[0][96];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('FIELDNUM').value = arrResult[0][97];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PK').value = arrResult[0][98];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('fDate').value = arrResult[0][99];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('ManageFeeRate').value = arrResult[0][100];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpSpec').value = arrResult[0][101];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GetPolMode').value = arrResult[0][102];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('PolApplyDate').value = arrResult[0][103];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('StandbyFlag1').value = arrResult[0][105];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('StandbyFlag2').value = arrResult[0][106];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('StandbyFlag3').value = arrResult[0][107];
	}
	catch (ex)
	{
	};

}

/*******************************************************************************
 * Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalGrpContNo.value;
	if(cPolNo == "")
	{
		alert("�������Ȳ�ѯͶ�������ܽ����ݽ�����Ϣ���֡�");
		return false
	}

	showInfo = window.open("./ProposalFee.jsp?PolNo=" + cPolNo
					+ "&polType=GROUP", "", sFeatures);
}

function queryCom()
{

	if(fm.AgentType.value != "01" && fm.AgentType.value != "07")
	{
		alert("��ѡ�����������������д�н������");
		return;
	}

	showInfo = window
			.open(
					"../appgrp/ComCommonQueryMain.jsp?branchtype=2",
					"AgentCommonQueryMain",
					'width='
							+ screen.availWidth
							+ ',height='
							+ screen.availHeight
							+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

function queryBank()
{
	// alert ("-------1--------");
	showInfo = window
			.open(
					"../appgrp/BankQueryMain.jsp",
					"BankQueryMain",
					'width='
							+ screen.availWidth
							+ ',height='
							+ screen.availHeight
							+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
function queryAgent()
{
	// alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value == "")
	{
		alert("����¼����������Ϣ��");
		return;
	}
	var tCom = (document.all('ManageCom').value).substring(0, 2);
	// alert(tCom);
	// return;
	if(LoadFlag!="4")
	{
		if(document.all('AgentCode').value == "")
		{
			// var newWindow =
			// window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			// ��������� ֱ������������ ����Ϊ ����ֱ�����������������
			if(fm.AgentType.value == "01")
			{
				// showInfo =
				// window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+ document.all('ManageCom').value+ "&branchtype=2","AgentCommonQueryMain",'width='+ screen.availWidth+ ',height='+ screen.availHeight+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			}
			else if(fm.AgentType.value == "03")
			{
				showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+ document.all('ManageCom').value+ "&branchtype=4","AgentCommonQueryMain",'width='+ screen.availWidth+ ',height='+ screen.availHeight+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			}
			else if(fm.AgentType.value == "12")// 2010-5-14 ����������Ϊ12
			{
				// �ۺ���չ branchtype='1'
				// 2010-4-30 ������Ҫ��1��4 �������⴦����branchtypeΪ50
				showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=" + tCom+ "&branchtype=50","AgentCommonQueryMain",'width='+ screen.availWidth+ ',height='+ screen.availHeight+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
			}
			else
			{
				showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+ document.all('ManageCom').value+ "&branchtype=2","AgentCommonQueryMain",'width='+ screen.availWidth+ ',height='+ screen.availHeight+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			}
		}
	}

	if(document.all('AgentCode').value != "")
	{
		var cAgentCode = fm.AgentCode.value; // ��������
		// alert("cAgentCode=="+cAgentCode);
		// ���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
		// alert("cAgentCode=="+cAgentCode);
		if(cAgentCode.length != 10)
		{
			return;
		}
		// var strSql = "select AgentCode,Name,AgentGroup from LAAgent where
		// AgentCode='" + cAgentCode +"' and ManageCom =
		// '"+document.all('ManageCom').value+"'";
		// var strSQL = "select
		// a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name
		// from LAAgent a,LATree b,LABranchGroup c where 1=1 "
		// + "and a.AgentCode = b.AgentCode and a.branchtype='2' and
		// a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup =
		// c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
		var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name,a.idtype,a.idno from LAAgent a,LATree b,LABranchGroup c where 1=1 "
				+ "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"
				+ cAgentCode + "'";
		if(document.all('AgentType').value == "12")// 2010-5-14 ����������Ϊ12
		{
			// strSQL = strSQL + " and a.branchtype='1'";
			// 2010-5-5 ���ز�1��4
			strSQL = strSQL + " and a.branchtype in ('1','4')";
		}
		var arrResult = easyExecSql(strSQL);
		// alert(arrResult);
		if(arrResult != null)
		{
			fm.AgentCode.value = arrResult[0][0];
			fm.AgentName.value = arrResult[0][3];
			fm.AgentManageCom.value = arrResult[0][2];
			fm.AgentGroup.value = arrResult[0][1];
			fm.BranchAttr.value = arrResult[0][10];
			queryZT();

			// alert("��ѯ���: �����˱���:["+arrResult[0][0]+"]
			// ����������Ϊ:["+arrResult[0][1]+"]");
		}
		else
		{
			fm.AgentGroup.value = "";
			alert("����Ϊ:[" + document.all('AgentCode').value + "]�Ĵ����˲����ڣ���ȷ��!");
		}
	}
}

// ��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{
	if(arrResult != null)
	{
		fm.AgentCode.value = arrResult[0][0];
		fm.AgentName.value = arrResult[0][3];
		fm.AgentManageCom.value = arrResult[0][2];
		fm.AgentGroup.value = arrResult[0][1];
		fm.BranchAttr.value = arrResult[0][10];
		showCodeName();
		queryZT();
	}
}

// ��ѯ����רԱ��������Ϣ
function queryZT()
{ // 2010-5-14 ����������Ϊ12
	if(document.all('AgentType').value == "12")
	{
		var strSQL = "select a.upagentcode,(select name from laagent where agentcode=a.upagentcode),a.agentcode,(select name from laagent where agentcode=a.agentcode) from laxagent a where a.managecom='"
				+ fm.AgentManageCom.value
				+ "'"
				+ " and exists (select 1 from laagent b where a.agentcode=b.agentcode and b.branchtype=("
				+ "select branchtype from laagent where agentcode = '"
				+ fm.AgentCode.value + "'))";
		var arrResult1 = easyExecSql(strSQL);
		if(arrResult1 != null)
		{
			document.all('AgentCodeOper').value = arrResult1[0][0];
			document.all('AgentNameOper').value = arrResult1[0][1];
			document.all('AgentCodeAssi').value = arrResult1[0][2];
			document.all('AgentNameAssi').value = arrResult1[0][3];
		}
		else
		{
			document.all('AgentCodeOper').value = '';
			document.all('AgentNameOper').value = '';
			document.all('AgentCodeAssi').value = '';
			document.all('AgentNameAssi').value = '';
		}
	}
}

// У����������Ϊ����ʱ���Ƿ�������רԱ��������Ϣ
function checkZT()
{ // 2010-5-14 ����������Ϊ12
	if(document.all('AgentType').value == "12")
	{
		if(document.all('AgentCodeOper').value == null
				|| (document.all('AgentCodeOper').value) == "")
		{
			alert("�ۺ���չרԱ��Ϣ����Ϊ�գ�");
			return false;
		}
		if(document.all('AgentCodeAssi').value == null
				|| (document.all('AgentCodeAssi').value) == "")
		{
			alert("�ۺ���չ������Ϣ����Ϊ�գ�");
			return false;
		}
		if(fm.ContPlanFlag.value == null || fm.ContPlanFlag.value == "")
		{
			alert("��������Ϊ�ۺϿ������Ʒ���Ӷ�������־����Ϊ�գ�");
			return false;
		}
		if(fm.ContPlanFlag.value == "Y"
				&& (fm.FYCRate.value == null || fm.FYCRate.value == ""))
		{
			alert("���Ӷ�������־ΪY��Ӷ�����ֵ����Ϊ�գ�");
			return false;
		}
	}
	return true;
}

// ���ز�ѯ�����������ҵ��Աmultline
function afterQuery3(arrResult)
{
	if(arrResult != null)
	{
		document.all(tField).all('MultiAgentGrid1').value = arrResult[0][0];
		document.all(tField).all('MultiAgentGrid2').value = arrResult[0][3];
		document.all(tField).all('MultiAgentGrid3').value = arrResult[0][2];
		document.all(tField).all('MultiAgentGrid4').value = arrResult[0][10];
		document.all(tField).all('MultiAgentGrid6').value = arrResult[0][1];
		document.all(tField).all('MultiAgentGrid7').value = arrResult[0][8];
	}

}

function afterQuery4(arrResult)
{
	if(arrResult != null)
	{
		fm.GrpNo.value = arrResult[0][0];
		fm.GrpName.value = arrResult[0][1];
		fm.Asset.value = arrResult[0][2];
		fm.GrpNature.value = arrResult[0][3];
		fm.BusinessType.value = arrResult[0][4];
		fm.Peoples.value = arrResult[0][5];
		fm.Fax.value = arrResult[0][6];
	}

}

function queryAgent2()
{
	if(document.all('ManageCom').value == "")
	{
		alert("����¼����������Ϣ��");
		return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length == 8)
	{
		var cAgentCode = fm.AgentCode.value; // ��������
		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='"
				+ cAgentCode
				+ "' and ManageCom = '"
				+ document.all('ManageCom').value + "'";
		var arrResult = easyExecSql(strSql);
		// alert(arrResult);
		if(arrResult != null)
		{
			fm.AgentGroup.value = arrResult[0][2];
			alert("��ѯ���:  �����˱���:[" + arrResult[0][0] + "] ����������Ϊ:["
					+ arrResult[0][1] + "]");
		}
		else
		{
			fm.AgentGroup.value = "";
			alert("����Ϊ:[" + document.all('AgentCode').value + "]�Ĵ����˲����ڣ���ȷ��!");
		}
	}
}

function afterCodeSelect(cCodeName,Field)
{

	if(cCodeName == "GetGrpAddressNo")
	{
		var strSQL = "select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"
				+ fm.GrpAddressNo.value
				+ "' and b.CustomerNo='"
				+ fm.GrpNo.value + "'";
		arrResult = easyExecSql(strSQL);
		try
		{
			document.all('GrpAddressNo').value = arrResult[0][0];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('GrpAddress').value = arrResult[0][1];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('GrpZipCode').value = arrResult[0][2];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('LinkMan1').value = arrResult[0][3];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('Department1').value = arrResult[0][4];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('HeadShip1').value = arrResult[0][5];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('Phone1').value = arrResult[0][6];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('E_Mail1').value = arrResult[0][7];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('Fax1').value = arrResult[0][8];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('LinkMan2').value = arrResult[0][9];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('Department2').value = arrResult[0][10];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('HeadShip2').value = arrResult[0][11];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('Phone2').value = arrResult[0][12];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('E_Mail2').value = arrResult[0][13];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('Fax2').value = arrResult[0][14];
		}
		catch (ex)
		{
		};
	}

	if(cCodeName == "AgentType")
	{
		var tAgentType = fm.AgentType.value;
		if(tAgentType == "01")
		{
			fm.SecondAgentType.disabled = false;
		}
		else
		{
			fm.SecondAgentType.value = "";
			fm.SecondAgentType.disabled = true;
		}// 2010-5-14 ����������Ϊ12
		if(tAgentType == "12")
		{
			DivAssiOper.style.display = '';
			DivContPlanFlag.style.display = '';
		}
		else
		{
			DivAssiOper.style.display = 'none';
			// �������������ء�DivContPlanFlag������ ContPlanFlag �ÿ�
			DivContPlanFlag.style.display = 'none';
			fm.ContPlanFlag.value = "";
			// �������������ء�"DivContPlan"������ FYCRate �ÿ�
			DivContPlan.style.display = 'none';
			fm.FYCRate.value = "";
		}
		try
		{
			document.all('AgentCode').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentName').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentManageCom').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('BranchAttr').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentCodeOper').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentNameOper').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentCodeAssi').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentNameAssi').value = "";
		}
		catch (ex)
		{
		};
		try
		{
			initMultiAgentGrid();
		}
		catch (ex)
		{
		};
	}

	if(cCodeName == "RiskGrp")
	{
		var strSql = "select bonusflag From lmriskapp  where riskcode ='"
				+ fm.RiskCode.value + "'";
		var arrResult = easyExecSql(strSql);
		var ttflag = "0";
		if(arrResult != null && arrResult[0][0] == "1")

		{
			divExplain.style.display = '';
		}
		else
		{
			divExplain.style.display = 'none';
		}
	}
	if(cCodeName == "RiskGrpWithOutEC")
	{
		var strSql = "select bonusflag From lmriskapp  where riskcode ='"
				+ fm.RiskCode.value + "'";
		var arrResult = easyExecSql(strSql);
		var ttflag = "0";
		if(arrResult != null && arrResult[0][0] == "1")

		{
			divExplain.style.display = '';
		}
		else
		{
			divExplain.style.display = 'none';
		}
		if(fm.RiskCode.value == "211901")
		{
			divKF.style.display = '';
		}
		else
		{
			divKF.style.display = 'none';
		}
	}

	// �����Ƿ���ʾ ��Ӷ�����ֵ�� ¼����
	if(cCodeName == "ContPlanFlag")
	{
		if(fm.ContPlanFlag.value == "Y")
		{
			DivContPlan.style.display = '';
			// alert("LoadFlag :" + LoadFlag)
			if(LoadFlag == "2")
			{
				fm.FYCRate.value = "18";
			}

		}
		else
		{
			DivContPlan.style.display = 'none';
		}
	}
}

function checkMainAppender(cRiskCode)
{
	if(isSubRisk(cRiskCode) == true)
	{ // ����
		var tPolNo = getMainRiskNo(cRiskCode); // ����¼�븽�յĴ���,�õ����ձ�������
		if(!checkRiskRelation(tPolNo, cRiskCode))
		{
			alert("�����հ�����ϵ������������պŲ��ܴ���������գ�");
			return false;
		}
	}
	return true;
}

function isSubRisk(cRiskCode)
{
	var arrQueryResult = easyExecSql(
			"select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode
					+ "'", 1, 0);

	if(arrQueryResult[0] == "S") // ��Ҫת�ɴ�д
		return true;
	if(arrQueryResult[0] == "M")
		return false;

	if(arrQueryResult[0].toUpperCase() == "A")
		if(confirm("�����ּȿ���������,�ֿ����Ǹ���!ѡ��ȷ����������¼��,ѡ��ȡ�����븽��¼��"))
			return false;
		else
			return true;

	return false;
}

function checkRiskRelation(tPolNo,cRiskCode)
{
	// �����¸���Ͷ����
	var strSql = "select RiskCode from LCGrpPol where GrpPolNo = '" + tPolNo
			+ "' and RiskCode in (select Code1 from LDCode1 where Code = '"
			+ cRiskCode + "' and codetype='grpchkappendrisk')";
	return easyQueryVer3(strSql);
}

function getMainRiskNo(cRiskCode)
{
	var urlStr = "../appgrp/MainRiskNoInput.jsp";
	var tPolNo = "";

	//tPolNo = window.showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=310;      //�������ڵĿ��; 
	var iHeight=100;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
	return tPolNo;
}

function grpRiskInfo()
{
	// alert('a');
	// var newWindow = window.open("../appgrp/GroupRisk.jsp","",sFeatures);
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��롲������Ϣ����");
	}
	delGrpVar();
	addGrpVar();
	showInfo = window.open("../appgrp/GroupRisk.jsp", "", sFeatures);
}

function grpInsuInfo()
{
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܡ���ӱ����ˡ���Ϣ��");
		return false;
	}
	// alert("1111"+fm.GrpContNo.value);
	fm.GrpContNo.value = fm.ProposalGrpContNo.value;
	delGrpVar();
	addGrpVar();
	parent.fraInterface.window.location = "../appgrp/ContInsuredInput.jsp?LoadFlag=2&ContType=2&scantype="
			+ scantype;
}

function grpInsuList()
{

	// alert("ok");add by yaory
	// var prtNo ="<%=request.getParameter("prtNo")%>";
	// var polNo ="<%=request.getParameter("polNo")%>";
	// var scantype ="<%=request.getParameter("scantype")%>";
	// var MissionID ="<%=request.getParameter("MissionID")%>";
	// var ManageCom ="<%=request.getParameter("ManageCom")%>";
	// var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
	// var ActivityID = "<%=request.getParameter("ActivityID")%>";
	// var NoType = "<%=request.getParameter("NoType")%>";
	// var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��롲�������嵥����Ϣ���棡");
		return false;
	}
	// ���뱻�����嵥ʱ�Ὣmanagecom�����ݱ��浽������,��֮�����lccont��lcpolʱ���õ�,
	// ����Ҫ��֤fm.managecom.value��lcgrpcont�еĹ��������ͬ
	checkManageCom();
	delGrpVar();
	addGrpVar();
	parent.fraInterface.window.location = "../appgrp/ContGrpInsuredInput.jsp?LoadFlag="
			+ LoadFlag
			+ "&scantype="
			+ scantype
			+ "&prtNo="
			+ polNo
			+ "&polNo="
			+ polNo
			+ "&MissionID="
			+ MissionID
			+ "&SubMissionID="
			+ SubMissionID;

}

function checkManageCom()
{
	var tNowMangeCom = fm.ManageCom.value;
	// alert("tMangeCom:"+tNowMangeCom);
	// alert("prtno:"+prtNo);
	var tSql = "select ManageCom from lcgrpcont where prtno = '" + prtNo + "'";
	var tOriManageCom = easyExecSql(tSql);
	if(tNowMangeCom != tOriManageCom)
	{
		// alert("��������뱣��ĺ�ͬ�Ĺ��������һ�£�");
		// fm.ManageCom.focus();
		fm.ManageCom.value = tOriManageCom;
		// return false;
	}
}

// ���ռƻ��ƶ�
function grpRiskPlanInfo()
{
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��С����ռƻ��ƶ�����");
		return false;
	}
	// //
	showInfo = window.open("../appgrp/ContPlan.jsp?AskpriceNo="
			+ fm.AskpriceNo.value + "&AskNo=" + fm.AskNo.value + "&GrpContNo="
			+ fm.GrpContNo.value + "&LoadFlag=" + LoadFlag + "&CValiDate="
			+ fm.CValiDate.value);
	// parent.fraInterface.window.location="../appgrp/ContPlan.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag+"";
}

/*******************************************************************************
 * ���ɨ�����ѯ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function ScanQuery2()
{
	var arrReturn = new Array();

	var prtNo = document.all("ProposalGrpContNo").value;

	if(prtNo == "" || prtNo == null)
	{
		alert("����ѡ��һ������Ͷ����!");
		return;
	}

	// var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' and
	// subtype!='3000' "; //�����ų���Э���ɨ������������Э������"����Ͷ����_�������Ͷ����"ɨ�����ô�������⡣
	// var crr = easyExecSql(tsql);
	// var tsubtype="";
	// //alert(crr);
	// if(crr!=null)
	// {
	// if(crr[0][0]=="1000")
	// {
	// tsubtype="TB1002";
	// }else{
	// tsubtype="TB1003";
	// }
	// }else{
	// tsubtype="TB1002";
	// }
	var strSql2 = "select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"
			+ prtNo + "'";
	var crrResult = easyExecSql(strSql2);
	var SubType = "";
	if(crrResult != null)
	{
		SubType = crrResult[0][0];
	}
	window
			.open(
					"../sys/ProposalEasyScan.jsp?BussType=TB&BussNoType=12&SubType="
							+ SubType + "&prtNo=" + prtNo, "",
					"status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
}

function focuswrap()
{
	myonfocus(showInfo);
}

function delGrpVar()
{
	// ɾ���������ڻ����еĸ��˺�ͬ��Ϣ
	try
	{
		mSwitch.deleteVar('ContNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ProposalContNo');
	}
	catch (ex)
	{
	};

	// �����ͬ��Ϣ
	try
	{
		mSwitch.deleteVar('GrpContNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ProposalGrpContNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PrtNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SaleChnl');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ManageCom');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AgentCom');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AgentType');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AgentCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AgentGroup');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AgentCode1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Password');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Password2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AddressNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Peoples2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpName');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BusinessType');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpNature');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('RgtMoney');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Asset');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('NetProfitRate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('MainBussiness');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Corporation');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ComAera');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Fax');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Phone');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GetFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Satrap');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('EMail');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('FoundDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntOnWorkPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntOffWorkPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntOtherPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpGroupNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BankCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('BankAccNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AccName');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('DisputedFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('OutPayFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GetPolMode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Lang');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Currency');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('LostTimes');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PrintCount');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('RegetDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('LastEdorDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('LastGetDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('LastLoanDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SpecFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpSpec');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PayMode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SignCom');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SignDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SignTime');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('CValiDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PayIntv');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('RiskCurrency');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ManageFeeRate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ExpPeoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ExpPremium');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ExpAmnt');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Peoples');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Mult');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Prem');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Amnt');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SumPrem');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('SumPay');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Dif');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Remark');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('StandbyFlag1');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('StandbyFlag2');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('StandbyFlag3');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('InputOperator');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('InputDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('InputTime');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ApproveFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ApproveCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ApproveDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('ApproveTime');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('UWOperator');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('UWFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('UWDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('UWTime');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppFlag');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PolApplyDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('CustomGetPolDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GetPolDate');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GetPolTime');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('State');
	}
	catch (ex)
	{
	};
	// ����Ͷ������Ϣ
	try
	{
		mSwitch.deleteVar('GrpNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AddressNo');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntGrade');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpName');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('PostalAddress');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('GrpZipCode');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Phone');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('Password');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('State');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('AppntType');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.deleteVar('RelationToInsured');
	}
	catch (ex)
	{
	};

}

function addGrpVar()
{
	// add activityid
	// alert(ScanFlag);
	try
	{
		mSwitch.addVar('ScanFlag', '', ScanFlag);
	}
	catch (ex)
	{
	};
	// end
	try
	{
		mSwitch.addVar('ContNo', '', '');
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ProposalContNo', '', '');
	}
	catch (ex)
	{
	};
	// �����ͬ��Ϣ
	try
	{
		mSwitch.addVar('GrpContNo', '', fm.GrpContNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ProposalGrpContNo', '', fm.ProposalGrpContNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PrtNo', '', fm.PrtNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SaleChnl', '', fm.SaleChnl.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ManageCom', '', fm.ManageCom.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AgentCom', '', fm.AgentCom.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AgentType', '', fm.AgentType.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AgentCode', '', fm.AgentCode.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AgentGroup', '', fm.AgentGroup.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AgentCode1', '', fm.AgentCode1.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Password', '', fm.Password.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Password2', '', fm.Password2.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppntNo', '', fm.AppntNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Addressno', '', fm.AddressNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Peoples2', '', fm.Peoples2.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpName', '', fm.GrpName.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BusinessType', '', fm.BusinessType.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpNature', '', fm.GrpNature.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('RgtMoney', '', fm.RgtMoney.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Asset', '', fm.Asset.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('NetProfitRate', '', fm.NetProfitRate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('MainBussiness', '', fm.MainBussiness.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Corporation', '', fm.Corporation.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ComAera', '', fm.ComAera.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Fax', '', fm.Fax.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Phone', '', fm.Phone.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GetFlag', '', fm.GetFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Satrap', '', fm.Satrap.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('EMail', '', fm.EMail.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('FoundDate', '', fm.FoundDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpGroupNo', '', fm.GrpGroupNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BankCode', '', fm.BankCode.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('BankAccNo', '', fm.BankAccNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AccName', '', fm.AccName.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('DisputedFlag', '', fm.DisputedFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('OutPayFlag', '', fm.OutPayFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GetPolMode', '', fm.GetPolMode.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Lang', '', fm.Lang.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Currency', '', fm.Currency.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('LostTimes', '', fm.LostTimes.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PrintCount', '', fm.PrintCount.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('RegetDate', '', fm.RegetDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('LastEdorDate', '', fm.LastEdorDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('LastGetDate', '', fm.LastGetDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('LastLoanDate', '', fm.LastLoanDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SpecFlag', '', fm.SpecFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpSpec', '', fm.GrpSpec.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PayMode', '', fm.PayMode.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SignCom', '', fm.SignCom.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SignDate', '', fm.SignDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SignTime', '', fm.SignTime.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('CValiDate', '', fm.CValiDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PayIntv', '', fm.PayIntv.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ManageFeeRate', '', fm.ManageFeeRate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ExpPeoples', '', fm.ExpPeoples.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ExpPremium', '', fm.ExpPremium.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ExpAmnt', '', fm.ExpAmnt.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Peoples', '', fm.Peoples.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Mult', '', fm.Mult.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Prem', '', fm.Prem.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Amnt', '', fm.Amnt.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SumPrem', '', fm.SumPrem.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('SumPay', '', fm.SumPay.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Dif', '', fm.Dif.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Remark', '', fm.Remark.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('StandbyFlag1', '', fm.StandbyFlag1.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('StandbyFlag2', '', fm.StandbyFlag2.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('StandbyFlag3', '', fm.StandbyFlag3.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('InputOperator', '', fm.InputOperator.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('InputDate', '', fm.InputDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('InputTime', '', fm.InputTime.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ApproveFlag', '', fm.ApproveFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ApproveCode', '', fm.ApproveCode.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ApproveDate', '', fm.ApproveDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ApproveTime', '', fm.ApproveTime.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('UWOperator', '', fm.UWOperator.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('UWFlag', '', fm.UWFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('UWDate', '', fm.UWDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('UWTime', '', fm.UWTime.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppFlag', '', fm.AppFlag.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PolApplyDate', '', fm.PolApplyDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('CustomGetPolDate', '', fm.CustomGetPolDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GetPolDate', '', fm.GetPolDate.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GetPolTime', '', fm.GetPolTime.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('State', '', fm.State.value);
	}
	catch (ex)
	{
	};
	// ����Ͷ������Ϣ

	try
	{
		mSwitch.addVar('GrpNo', '', fm.GrpNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PrtNo', '', fm.PrtNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AddressNo', '', fm.AddressNo.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppntGrade', '', fm.AppntGrade.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('GrpName', '', fm.Name.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('PostalAddress', '', fm.PostalAddress.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('ZipCode', '', fm.ZipCode.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Phone', '', fm.Phone.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('Password', '', fm.Password.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('State', '', fm.State.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('AppntType', '', fm.AppntType.value);
	}
	catch (ex)
	{
	};
	try
	{
		mSwitch.addVar('RelationToInsured', '', fm.RelationToInsured.value);
	}
	catch (ex)
	{
	};

}
function checkuseronly(comname)
{
	arrResult = easyExecSql("select * from LDGrp  where GrpName='" + comname
					+ "'", 1, 0);
	if(arrResult == null)
	{

	}
	else
	{
		arrResult = easyExecSql(
				"select b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='"
						+ arrResult[0][0] + "'", 1, 0);
		if(arrResult == null)
		{
			alert("δ�鵽Ͷ����λ��Ϣ");
		}
		else
		{
			try
			{
				document.all('GrpName').value = arrResult[0][0];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('BusinessType').value = arrResult[0][1];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('GrpNature').value = arrResult[0][2];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('Peoples').value = arrResult[0][3];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('RgtMoney').value = arrResult[0][4];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('Asset').value = arrResult[0][5];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('NetProfitRate').value = arrResult[0][6];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('MainBussiness').value = arrResult[0][7];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('Corporation').value = arrResult[0][8];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('ComAera').value = arrResult[0][9];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('Fax').value = arrResult[0][10];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('Phone').value = arrResult[0][11];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('FoundDate').value = arrResult[0][12];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('GrpNo').value = arrResult[0][13];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('AppntAppntOnWorkPeoples').value = arrResult[0][14];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('AppntOffWorkPeoples').value = arrResult[0][15];
			}
			catch (ex)
			{
			};
			try
			{
				document.all('AppntOtherPeoples').value = arrResult[0][16];
			}
			catch (ex)
			{
			};
		}
	}
}
// ��������ҳ��ĺ�����δ���õ�
function InputPolicy()
{
	var newWindow = window.open("../appgrp/NewProposal.jsp?RiskCode=111302",
			"", sFeatures);
}
function InputPolicyNoList()
{
	var newWindow = window.open(
			"../appgrp/NewProposal.jsp?NoListFlag=1&RiskCode=111302", "",
			sFeatures);
}

// ���һ�����ּ�¼
function addRecord()
{
	var tRiskCode = document.all('RiskCode').value;
	var tPayIntv = document.all('PayIntv').value;
	var tDistriFlag = document.all('DistriFlag').value;
	// var ExpPeoples = document.all('ExpPeoples').value ;
	var tGrpContNo = document.all('GrpContNo').value;
	var tRiskCurrency = document.all('RiskCurrency').value;
	document.all('LoadFlag').value = LoadFlag;

	// ��ѯ���ݿ��ж��Ƿ��Ѿ������ͬ��Ϣ
	var aSQL = "select * from lcgrpcont where grpcontno='" + tGrpContNo + "'";
	var aResult = easyExecSql(aSQL, 1, 0);

	if(aResult == null)
	{
		alert("�ŵ���ͬ��Ϣδ���棬������������֡���");
		return;
	}
	// add by 2011-09-28 HY ���ӱ��ִ���
	if(tRiskCode == null || tRiskCode == "" || tPayIntv == null
			|| tPayIntv == "" || tRiskCurrency == null || tRiskCurrency == "")
	{
		alert("������Ϣδ����¼�룡");
		return;
	}

	var accSql = "select bonusflag from lmriskapp where riskcode='" + tRiskCode
			+ "' ";
	var tBonusFlag = easyExecSql(accSql);
	if(tBonusFlag == "1")
	{
		// if(fm.StandbyFlag1.value=="")
		// {
		// alert("��ѡ��ֺ���!");
		// return false;
		// }
	}

	/***************************************************************************
	 * if(document.all('ExpPeoples').value != "" &&
	 * isNaN(parseFloat(document.all('ExpPeoples').value )) &&
	 * !isNumeric(document.all('ExpPeoples').value )) { alert("��Ϊ'Ԥ������'¼�����֣�"); return ; }
	 **************************************************************************/
	RiskGrid.delBlankLine();
	document.all('fmAction').value = "INSERT||GROUPRISK";

	var showStr = "��������ŵ�������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	lockScreen('lkscreen');
	fm.action = "../appgrp/GroupRiskSave.jsp"
	document.getElementById("fm").submit();
}

// ɾ��һ�����ּ�¼
function deleteRecord()
{

	RiskGrid.delBlankLine();
	var showStr = "����ɾ��������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all('fmAction').value = "DELETE||GROUPRISK";
	lockScreen('lkscreen');
	fm.action = "../appgrp/GroupRiskSave.jsp"
	document.getElementById("fm").submit();

}

/*******************************************************************************
 * �ŵ�������Ϣ�ĵ�¼�� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function grpFeeInput()
{
	var tGrpContNo = document.all('GrpContNo').value;
	if(tGrpContNo == null || tGrpContNo == "")
	{
		alert("�ŵ���ͬ��Ϣδ���棬������������֡���");
		return;
	}
	// showInfo =
	// window.open("../appgrp/GrpFeeInput.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag);
	parent.fraInterface.window.location = "../appgrp/GrpFeeInput.jsp?ProposalGrpContNo="
			+ tGrpContNo + "&LoadFlag=" + LoadFlag;
}

/*******************************************************************************
 * ��ʼ��������ʾ������ �����ͱ��ѵ�ͳ�� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function fillriskgrid()
{
	// alert("ok");
	// ���ӿ�����֧��
	if(fm.ProposalGrpContNo.value != "")
	{
		var tcontno = fm.PrtNo.value;
		var tsql = "select markettype from lcgrpcont where prtno='" + tcontno
				+ "'";
		var ttresult = easyExecSql(tsql);
		if(ttresult == null)
		{
			// ɾ����ͬ��Ϊnull
			return false;
		}
		// if(ttresult[0][0]==0)
		// {
		// var strSql = "Select a.riskcode ,b.riskname ,a.PayIntv,"
		// +"(select count(1) from lcpol where grppolno =a.grppolno),"
		// // +"(select count(1) from lcpol where grppolno =a.grppolno)+(select
		// count(1) from lcinsuredrelated where polno in (select polno from
		// lcpol where grppolno=a.grppolno)),"
		// +" prem,"
		// +"(select case when Sum(prem) is null then 0 else Sum(prem) end From
		// lcpol Where grppolno=a.Grppolno and appflag<>'4'),"
		// +"(select case when Sum(amnt) is null then 0 else Sum(amnt) end From
		// lcpol Where grppolno=a.Grppolno and appflag<>'4'),"
		// +"a.payenddate, "
		// +"a.StandbyFlag1, "
		// +"nvl(a.chargefeerate,0),"
		// +"nvl(a.commrate,0),"
		// +"a.bonusrate,"
		// +"nvl(a.fixprofitrate,0), "
		// +"decode(a.standbyflag4,'0','0-�µ�','1','1-������','-')"
		// +"From lcgrppol a,LMRiskApp b "
		// +"Where a.riskcode=b.riskcode and a.GrpContNo='" +
		// document.all('GrpContNo').value +"'";
		// turnPage.queryModal(strSql, RiskGrid);
		// }else{

		var strSql = "Select a.riskcode ,b.riskname ,a.PayIntv,"
				+ "nvl((select count(1) from lcpol where riskcode=a.riskcode and grpcontno=a.grpcontno and uwflag != '1'),0),"
				+ "nvl((select count(1) from lcpol where riskcode=a.riskcode and grpcontno=a.grpcontno and uwflag != '1'),0),"
				// +"a.Peoples2,"
				// +"a.Peoples2,"
				+ "nvl((select sum(prem) from lcpol where riskcode=a.riskcode and grpcontno=a.grpcontno and uwflag != '1'),0),"
				+ "nvl((select sum(amnt) from lcpol where riskcode=a.riskcode and grpcontno=a.grpcontno and uwflag != '1'),0),"
				// +"a.prem,"
				// +"a.amnt,"
				+ "a.payenddate, "
				+ "a.StandbyFlag1 ,"
				+ "nvl(a.chargefeerate,0),"
				+ "nvl(a.commrate,0),"
				+ "a.bonusrate,"
				+ "nvl(a.fixprofitrate,0), "
				+ "decode(a.standbyflag4,'0','0-�µ�','1','1-������','-')"
				+ ",( select currname from ldcurrency where 1 = 1  and currcode = a.currency )"
				+ "From lcgrppol a,LMRiskApp b "
				+ "Where  a.riskcode=b.riskcode and a.GrpContNo='"
				+ document.all('GrpContNo').value + "'";
		turnPage.queryModal(strSql, RiskGrid);
		// }

	}
	else
	{

		return false;
	}
}

function getapproveinfo()
{
	mOperate = 1;
	var approveinfo = new Array();
	approveinfo[0] = new Array();
	approveinfo[0][0] = polNo;
	afterQuery(approveinfo);
	// alert("2");
	queryAgent();
	var arrResult = easyExecSql("select agentcodeoper,agentcodeassi from LCGrpCont where GrpContNo = '"
			+ document.all('GrpContNo').value + "'");
	if(arrResult != null)
	{
		try
		{
			document.all('AgentCodeOper').value = arrResult[0][0];
		}
		catch (ex)
		{
		};
		try
		{
			document.all('AgentCodeAssi').value = arrResult[0][1];
		}
		catch (ex)
		{
		};
		document.all('AgentNameOper').value = "";
		document.all('AgentNameAssi').value = "";
		if(arrResult[0][0] != null && arrResult[0][0] != "")
		{
			var strSQL = "select name from laagent where agentcode='"
					+ arrResult[0][0] + "'";
			var arrResult1 = easyExecSql(strSQL);
			if(arrResult1 != null)
				fm.AgentNameOper.value = arrResult1[0][0];
		}
		if(arrResult[0][1] != null && arrResult[0][1] != "")
		{
			var strSQL = "select name from laagent where agentcode='"
					+ arrResult[0][1] + "'";
			var arrResult1 = easyExecSql(strSQL);
			if(arrResult1 != null)
				fm.AgentNameAssi.value = arrResult1[0][0];
		}// 2010-5-14 ����������Ϊ12
		if(fm.AgentType.value == "12")
			DivAssiOper.style.display = '';
	}
}
/*******************************************************************************
 * ѡ���ŵ��������¼�� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function GrpQuestInput()
{
	var cGrpProposalContNo = fm.ProposalGrpContNo.value; // ���屣������
	if(cGrpProposalContNo == "" || cGrpProposalContNo == null)
	{
		alert("����ѡ��һ������Ͷ����!");
		return;
	}
	showInfo = window.open("./GrpQuestInputMain.jsp?GrpContNo="
					+ cGrpProposalContNo + "&Flag=" + LoadFlag, "", sFeatures);

}
/*******************************************************************************
 * ѡ���ŵ�������Ĳ�ѯ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function GrpQuestQuery()
{
	var cGrpContNo = fm.GrpContNo.value; // �ŵ�Ͷ��������
	if(cGrpContNo == "" || cGrpContNo == null)
	{
		alert("����ѡ��һ����������Ͷ����!");
		return;
	}
	showInfo = window.open("./GrpQuestQueryMain.jsp?GrpContNo=" + cGrpContNo
					+ "&Flag=" + LoadFlag, "", sFeatures);
}
/*******************************************************************************
 * ����ͨ�����ŵ� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function gmanuchk()
{
	// �ŵ�Ͷ��������
	cProposalGrpContNo = fm.ProposalGrpContNo.value;
	cflag = "5";

	if(MissionID == "null" || SubMissionID == "null")
	{
		fm.MissionID.value = mSwitch.getVar('MissionID');
		fm.SubMissionID.value = mSwitch.getVar('SubMissionID');
	}
	else
	{
		mSwitch.deleteVar("MissionID");
		mSwitch.deleteVar("SubMissionID");
		mSwitch.addVar("MissionID", "", MissionID);
		mSwitch.addVar("SubMissionID", "", SubMissionID);
		mSwitch.updateVar("MissionID", "", MissionID);
		mSwitch.updateVar("SubMissionID", "", SubMissionID);
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	if(cProposalGrpContNo == null || cProposalGrpContNo == "")
		alert("��ѡ��������Ͷ�������ٽ��и��˲���");
	else
	{
		if(confirm("�ò���������ͨ���ñ������µ�����Ͷ����Ϣ,ȷ����"))
		{
			var i = 0;
			var showStr = "���ڸ��˼���Ͷ�����������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��,����Ͷ��������:"
					+ cProposalGrpContNo;
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+ showStr;
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			lockScreen('lkscreen');
			fm.action = "./GroupPolApproveSave.jsp?ProposalGrpContNo1="
					+ cProposalGrpContNo + "&Flag1=5";
			document.getElementById("fm").submit();
		}
		else
			return false;
	}

}
/*******************************************************************************
 * ������ذ�ť,�رյ�ǰҳ�� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function goback()
{

	if(LoadFlag == '16')
	{
		top.close();
	}
	if(LoadFlag == '14' || LoadFlag == '23')
	{
		top.opener.querygrp();
	}
	else
	{
		top.opener.easyQueryClick();
		top.opener.easyQueryClickSelf();
	}
	top.close();
}

/*******************************************************************************
 * �����޸ĸ��ŵ� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function approveupdate()
{

	cProposalGrpContNo = fm.ProposalGrpContNo.value; // �ŵ�Ͷ��������
	cflag = "5";
	if(cProposalGrpContNo == null || cProposalGrpContNo == "")
		alert("��ѡ��������Ͷ�������ٽ��и����޸�ȷ�ϲ���");
	else
	{
		if(confirm("�ò�����ʾ���е��޸������,ȷ����"))
		{
			var i = 0;
			var showStr = "���ڸ����޸ļ���Ͷ�����������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��,����Ͷ��������:"
					+ cProposalGrpContNo;
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+ showStr;
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			// window.open("./GroupPolApproveSave.jsp?ProposalGrpContNo="+cProposalGrpContNo+"&Flag1=5","windows1");
			fm.action = "./GrpApproveModifyMakeSure.jsp?ProposalGrpContNo="
					+ cProposalGrpContNo + "&Flag1=5";
			document.getElementById("fm").submit();
		}
		else
			return false;
		// window.close();
		// document.getElementById("fm").submit();//�ύ
	}

}

/*******************************************************************************
 * �ŵ��ֵ����� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function grpSubContInfo()
{
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��С��ֵ����ơ���");
		return;
	}
	delGrpVar();
	addGrpVar();
	cGrpContNo = document.all("GrpContNo").value;
	cPrtNo = document.all("PrtNo").value;
	showInfo = window.open(
			"../appgrp/SubContPolMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo="
					+ cPrtNo + "&LoadFlag=" + LoadFlag, "", sFeatures);
	// parent.fraInterface.window.location="../appgrp/SubContPolMain.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="
	// + cGrpContNo + "&PrtNo=" + cPrtNo+"&LoadFlag="+LoadFlag+"";
}

// ����������
function ascriptDefine()
{
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��С����������塳��");
		return;
	}

	cGrpContNo = document.all("GrpContNo").value;

	showInfo = window.open("../appgrp/AscriptionRuleMain.jsp?GrpContNo="
					+ cGrpContNo + "&LoadFlag=" + LoadFlag, "", sFeatures);

}
// ����Ѷ���
function feeDefine()
{
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��С�����Ѷ��塳��");
		return;
	}

	cGrpContNo = document.all("GrpContNo").value;

	showInfo = window.open("../appgrp/OutRiskFeeMain.jsp?GrpContNo="
					+ cGrpContNo + "&LoadFlag=" + LoadFlag, "", sFeatures);

}
// �ɷѹ�����
function payDefine()
{
	if(fm.ProposalGrpContNo.value == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��С�����Ѷ��塳��");
		return;
	}

	cGrpContNo = document.all("GrpContNo").value;

	showInfo = window.open("../appgrp/PayRuleMain.jsp?GrpContNo=" + cGrpContNo
					+ "&LoadFlag=" + LoadFlag, "", sFeatures);

}
function getdetailaddress()
{
	var strSQL = "select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"
			+ fm.GrpAddressNo.value
			+ "' and b.CustomerNo='"
			+ fm.GrpNo.value
			+ "'";
	arrResult = easyExecSql(strSQL);
	try
	{
		document.all('GrpAddressNo').value = arrResult[0][0];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpAddress').value = arrResult[0][1];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('GrpZipCode').value = arrResult[0][2];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan1').value = arrResult[0][3];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department1').value = arrResult[0][4];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip1').value = arrResult[0][5];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone1').value = arrResult[0][6];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail1').value = arrResult[0][7];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax1').value = arrResult[0][8];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('LinkMan2').value = arrResult[0][9];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Department2').value = arrResult[0][10];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('HeadShip2').value = arrResult[0][11];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Phone2').value = arrResult[0][12];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('E_Mail2').value = arrResult[0][13];
	}
	catch (ex)
	{
	};
	try
	{
		document.all('Fax2').value = arrResult[0][14];
	}
	catch (ex)
	{
	};
}

/*******************************************************************************
 * �����ͬ��Ϣ¼�����ȷ�� ���� �� wFlag--��״̬ʱ���ô˺������ߵķ�֧ ����ֵ�� ��
 * ********************************************************************
 */
function GrpInputConfirm(wFlag)
{
	// if(checkOtherQuest()==false)
	// return false;
	mWFlag = 1;
	if(wFlag == 1) // ¼�����ȷ��
	{
		// ����У���Ƿ�Ͷ��139���֣����Ͷ������¼�빫���ʻ�(139.151.162)
		var sql = "select * From lcgrppol where grpcontno='"
				+ fm.PrtNo.value
				+ "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('1','3'))";
		var rresult = easyExecSql(sql, 1, 0);
		if(rresult != null)
		{
			sql = "select * From lccont where poltype='2' and grpcontno='"
					+ fm.PrtNo.value + "'";
			var tt = easyExecSql(sql, 1, 0);
			if(tt == null)
			{
				alert("��Ͷ�������Ѿ�Ͷ����Ҫ���ӹ����˻������֣������ӹ����ʻ�!");
				return;
			}
			else
			{
				sql = "select count(*) from lcpol where poltypeflag='2' and grpcontno='"
						+ fm.PrtNo.value + "'";
				var sqll = "select count(*) From lcgrppol where grpcontno='"
						+ fm.PrtNo.value
						+ "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('1','3'))";
				var aa = easyExecSql(sql, 1, 0);
				var bb = easyExecSql(sqll, 1, 0);
				if(aa < bb)
				{
					alert("��Ͷ�������Ѿ�Ͷ����Ҫ���ӹ����˻������֣������ӹ����ʻ�!");
					return;
				}
			}
		}
		// У���Ƿ�ͬһ��������¼�����������������ϵ�������
		var tSQL = "select riskcode,count(1) from lcpol where prtno ='"
				+ fm.PrtNo.value + "' and poltypeflag ='1' group by riskcode";
		var arrResult = easyExecSql(tSQL, 1, 0);
		if(arrResult != null)
		{
			// alert(arrResult.length);
			for(i = 0; i < arrResult.length; i++)
			{
				var tRiskCode = arrResult[i][0];
				var tCount = arrResult[i][1];
				// alert(tRiskCode+"--"+tCount);
				if(tCount > 1)
				{
					alert("����" + tRiskCode + "�´�����������������¼��һ����������¼����ȷ�ı�����������");
					return false;
				}
			}
		}
		// (162,188,198)
		sql = "select * From lcgrppol where grpcontno='"
				+ fm.PrtNo.value
				+ "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('4','3'))";
		rresult = easyExecSql(sql, 1, 0);
		if(rresult != null)
		{
			sql = "select * From lccont where poltype='3' and grpcontno='"
					+ fm.PrtNo.value + "'";
			var tt = easyExecSql(sql, 1, 0);
			if(tt == null)
			{
				alert("��Ͷ�������Ѿ�Ͷ����Ҫ���ӷ����˻������֣������ӷ����ʻ�!");
				return;
			}
			else
			{
				sql = "select count(*) from lcpol where poltypeflag='3' and grpcontno='"
						+ fm.PrtNo.value + "'";
				var sqll = "select count(*) From lcgrppol where grpcontno='"
						+ fm.PrtNo.value
						+ "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('4','3'))";
				if(easyExecSql(sql, 1, 0) < easyExecSql(sqll, 1, 0))
				{
					alert("��Ͷ�������Ѿ�Ͷ����Ҫ���ӷ����˻������֣������ӷ����ʻ�!");
					return;
				}
			}
		}
		// 139�ͷֺ��գ�����¼�롰���������������˵�У��
		sql = "select count(*) from lcpol where poltypeflag='1' and grpcontno='"
				+ fm.PrtNo.value
				+ "' and riskcode in (select riskcode from lmriskapp where risktype6='1' or RiskPeriod='L')";
		var num1 = easyExecSql(sql, 1, 0);
		if(num1 > 0)
		{
			alert("��Ͷ�����´���¼�����������ĳ��ջ�139����!");
			return;
		}
		// ������ӷ����ʻ���������ӵķ����ʻ�ҪУ��
		sql = "select count(*) from lcpol where poltypeflag='3' and grpcontno='"
				+ fm.PrtNo.value
				+ "' and riskcode not in (select riskcode from lmriskapp where risktype6 in('4','3'))";
		var num2 = easyExecSql(sql, 1, 0);
		if(num2 > 0)
		{
			alert("��Ͷ�����´���¼�����ʻ�������!");
			return;
		}
		// ������ӹ����ʻ���������ӵĹ����ʻ�ҪУ��
		sql = "select count(*) from lcpol where poltypeflag='2' and grpcontno='"
				+ fm.PrtNo.value
				+ "' and riskcode not in (select riskcode from lmriskapp where risktype6 in('1','2','3'))";
		var num3 = easyExecSql(sql, 1, 0);
		if(num3 > 0)
		{
			alert("��Ͷ�����´���¼�����ʻ�������!");
			return;
		}
		// ��������ֻ����������,��¼ҵ��,ͬʱ������Ч���ڲ������ڲ������շѵĵ�������
		sql = "select count(*) From lcgrppol where grpcontno='"
				+ fm.PrtNo.value
				+ "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('4'))";
		var num4 = easyExecSql(sql, 1, 0);
		if(num4 > 0)
		{
			var wSql = "select enteraccdate from ljtempfee where otherno='"
					+ fm.PrtNo.value
					+ "' and tempfeetype='1' and confflag='0' ";
			var tenteraccdate = easyExecSql(wSql);
			if(tenteraccdate != null && tenteraccdate != "")
			{
				if(fm.CValiDate.value < tenteraccdate)
				{
					alert("������Ч�ղ����ڵ������� " + tenteraccdate + " ǰ,��ɾ��Ͷ��������¼��!");
					return;
				}
			}
			else
			{
				alert("��Ͷ�����²������ѵ��ʵĽ��ѣ����Ƚ���!");
				return;
			}
		}
		var tt = easyExecSql(sql, 1, 0);
		var tStr = "	select * from lwmission where 1=1 "
				+ " and lwmission.processid = '0000000004'"
				+ " and lwmission.activityid = '0000002001'"
				+ " and lwmission.missionprop1 = '" + fm.PrtNo.value + "'";
		turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
		if(turnPage.strQueryResult)
		{
			alert("���ŵ���ͬ�Ѿ��������棡");
			return;
		}
		if(document.all('ProposalGrpContNo').value == "")
		{
			alert("�ŵ���ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
			return;
		}
		var rc = mSwitch.getVar('ScanFlag');
		if(rc == "0")
		{
			fm.WorkFlowFlag.value = "0000002098";
		}
		else
		{
			fm.WorkFlowFlag.value = "0000002099";
		}
	}
	else if(wFlag == 2)// �������ȷ��
	{
		if(document.all('ProposalGrpContNo').value == "")
		{
			alert("δ��ѯ���ŵ���ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
			return;
		}
		fm.WorkFlowFlag.value = "0000002002"; // �������
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 3)
	{
		if(document.all('ProposalGrpContNo').value == "")
		{
			alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
			return;
		}
		fm.WorkFlowFlag.value = "0000001002"; // �����޸����
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		if(document.all('ProposalGrpContNo').value == "")
		{
			alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
			return;
		}
		fm.WorkFlowFlag.value = "0000001021"; // �����޸�
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else
		return;
	// ������ʾ����ѵĲ���(139,151,162,188,198)
	var sql = "select * from lcgrppol where grpcontno='"
			+ fm.ProposalGrpContNo.value
			+ "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('1','4','3'))";
	var Result = easyExecSql(sql, 1, 0);
	if(Result != null)
	{
		sql = "select * From lcgrpfee where grpcontno='"
				+ fm.ProposalGrpContNo.value + "'";
		Result = easyExecSql(sql, 1, 0);
		if(Result == null)
		{
			if(!confirm('���� ' + fm.ProposalGrpContNo.value
					+ ' �µĹ����û�ж��壬���Ƿ�Ҫȷ�ϲ�����'))
			{
				return false;
			}
		}
	}

	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockScreen('lkscreen');
	fm.action = "./GrpInputConfirm.jsp";
	document.getElementById("fm").submit();// �ύ
}

function checkOtherQuest()
{
	var tGrpContNo = document.all("GrpContNo").value;;
	var tcheckSQL = "select * from LCGrpIssuePol where grpcontno = '"
			+ tGrpContNo + "' and replyresult is null";
	var arrResult = easyExecSql(tcheckSQL);
	if(arrResult != null)
	{
		alert("��ͬ�»��������δ�ظ�����ظ����ڽ���[������޸�ȷ��]������");
		return false;
	}
	return true;
}

function showNotePad()
{

	// var selno = SelfGrpGrid.getSelNo()-1;
	// if (selno <0)
	// {
	// alert("��ѡ��һ������");
	// return;
	// }

	// var MissionID = SelfGrpGrid.getRowColData(selno, 4);
	var MissionID = document.all.MissionID.value;
	// alert(MissionID);

	// var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
	var SubMissionID = document.all.SubMissionID.value;
	// alert(SubMissionID);

	// var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var ActivityID = document.all.ActivityID.value;
	// alert("ActivityID="+ActivityID);

	// var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
	var PrtNo = document.all('prtNo').value;
	// alert("PrtNo="+ document.all('prtNo').value);

	var NoType = document.all.NoType.value;
	// alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}
	var varSrc = "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID
			+ "&ActivityID=" + ActivityID + "&PrtNo=" + PrtNo + "&NoType=2";// +
																			// NoType;
	var newWindow = OpenWindowNew(
			"../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp"
					+ varSrc, "���������±��鿴", "left");

}

/*******************************************************************************
 * ���뺺���ķ��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showCodeName()
{
	// alert("show1")
	showOneCodeName('comcode', 'ManageCom', 'ManageComName');
	showOneCodeName('comcode', 'AgentManageCom', 'AppntManageComName');
	// wangxw �����ҵ���͵���ʾ
	showOneCodeName('BusinessType', 'BusinessType', 'BusinessTypeName');
	// alert("codename1")
}

/*******************************************************************************
 * ��ʼ��������MissionID ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function initMissionID()
{
	if(MissionID == "null" || SubMissionID == "null")
	{
		MissionID = mSwitch.getVar('MissionID');
		SubMissionID = mSwitch.getVar('SubMissionID');
	}
	else
	{
		mSwitch.deleteVar("MissionID");
		mSwitch.deleteVar("SubMissionID");
		mSwitch.addVar("MissionID", "", MissionID);
		mSwitch.addVar("SubMissionID", "", SubMissionID);
		mSwitch.updateVar("MissionID", "", MissionID);
		mSwitch.updateVar("SubMissionID", "", SubMissionID);
	}
}
function getaddresscodedata()
{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select AddressNo,GrpAddress from LCGrpAddress where CustomerNo ='"
			+ fm.GrpNo.value + "'";
	// alert("strsql :" + strsql);
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
	if(turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for(i = 0; i < m; i++)
		{
			j = i + 1;
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|"
					+ turnPage.arrDataCacheSet[i][1];
		}
	}
	// alert ("tcodedata : " + tCodeData);
	// return tCodeData;
	document.all("GrpAddressNo").CodeData = tCodeData;
}

function grpPersonAge()
{
	// showInfo =
	// window.open("./GrpPersonAgeInput.jsp?GrpContNo="+fm.GrpContNo.value+");
	var tProposalGrpContNo = "";
	tProposalGrpContNo = document.all('ProposalGrpContNo').value;
	if(tProposalGrpContNo == null || tProposalGrpContNo == "")
	{
		alert("���뱣���ͬ��Ϣ���ܽ��롲������Ϣ����");
		return false
	}
	delGrpVar();
	addGrpVar();

	var cGrpContNo = document.all("GrpContNo").value;
	var cPrtNo = document.all("PrtNo").value;
	showInfo = window.open("GrpPersonAgeMain.jsp?GrpContNo=" + cGrpContNo
					+ "&PrtNo=" + cPrtNo, "", sFeatures);
}

// ��LJTempFee ���еõ�������Ч����
function QueryCValiDate()
{
	prtNo = document.all('PrtNo').value;
	// alert("prtNo============="+prtNo);
	// ������Ч��Ϊenteraccdate�ֶ�ֵ�ĵڶ���,����������SQL��ʹ��"+"�Ż�����⣬���� - (-1)���档
	var brrResult = easyExecSql("select to_date(max(enteraccdate)) - (-1) from ljtempfee where othernotype='4' and otherno='"
			+ prtNo + "' and operstate='0' ");
	var CValiDate = "";
	if(brrResult[0][0] == "")
	{
		alert("�޷��õ�������Ч��!");
		return;
	}
	else
	{
		try
		{
			document.all('CValiDate').value = brrResult[0][0];
		}
		catch (ex)
		{
			alert("������Ч����������!");
		};
	}
}

function QueryChargeDate()
{
	prtNo = document.all('PrtNo').value;
	var arrResult = easyExecSql("select to_date(max(enteraccdate)) from ljtempfee where othernotype='4' and otherno='"
			+ prtNo + "' and operstate='0' ");
	var ChargeDate = "";
	if(arrResult[0][0] == "")
	{
		// alert("�޷��õ������շ���!");
		// return;
	}
	else
	{
		try
		{
			document.all('PayDate').value = arrResult[0][0];
		}
		catch (ex)
		{
			alert("����������������!");
		};
	}

	// tongmeng 2009-04-20 add
	// ���ӱ�ȫ���˱���
	var tSQL = "select decode(EdorTransPercent,'-1','',EdorTransPercent) from lcgrpcont where grpcontno='"
			+ prtNo + "' ";
	var arrResult1 = easyExecSql(tSQL);
	// alert(arrResult1);
	{
		try
		{
			document.all('EdorTransPercent').value = arrResult1[0][0];
		}
		catch (ex)
		{
		};
	}

}

function initDetail()
{
	var arrResult = easyExecSql("select * from LCGrpCont where PrtNo = '"
					+ prtNo + "'", 1, 0);
	if(arrResult == null)
	{
		return;
	}
	else
	{
		try
		{
			document.all('GrpContNo').value = arrResult[0][0];
		}
		catch (ex)
		{
			alert(0);
		};
		try
		{
			document.all('ProposalGrpContNo').value = arrResult[0][1];
		}
		catch (ex)
		{
			alert(1);
		};
		try
		{
			document.all('PrtNo').value = arrResult[0][2];
		}
		catch (ex)
		{
			alert(2);
		};
		try
		{
			document.all('SaleChnl').value = arrResult[0][3];
		}
		catch (ex)
		{
			alert(3);
		};
		try
		{
			document.all('ManageCom').value = arrResult[0][4];
		}
		catch (ex)
		{
			alert(4);
		};
		// alert(document.all('ManageCom').value);
		try
		{
			document.all('AgentCom').value = arrResult[0][5];
		}
		catch (ex)
		{
			alert(5);
		};
		try
		{
			document.all('AgentType').value = arrResult[0][6];
		}
		catch (ex)
		{
			alert(6);
		};
		try
		{
			document.all('AgentCode').value = arrResult[0][7];
		}
		catch (ex)
		{
			alert(7);
		};
		try
		{
			document.all('AgentGroup').value = arrResult[0][8];
		}
		catch (ex)
		{
			alert(8);
		};
		try
		{
			document.all('AgentCode1').value = arrResult[0][9];
		}
		catch (ex)
		{
			alert(9);
		};
		try
		{
			document.all('GrpNo').value = arrResult[0][12];
		}
		catch (ex)
		{
			alert(12);
		}; // changed by tuqiang
		try
		{
			document.all('GrpAddressNo').value = arrResult[0][13];
		}
		catch (ex)
		{
			alert(13);
		};
		try
		{
			document.all('GrpName').value = arrResult[0][15];
		}
		catch (ex)
		{
			alert(15);
		};
		try
		{
			document.all('BusinessType').value = arrResult[0][16];
		}
		catch (ex)
		{
			alert(16);
		};
		try
		{
			document.all('GrpNature').value = arrResult[0][17];
		}
		catch (ex)
		{
			alert(17);
		};
		try
		{
			document.all('RgtMoney').value = arrResult[0][18];
		}
		catch (ex)
		{
			alert(18);
		};
		try
		{
			document.all('Asset').value = arrResult[0][19];
		}
		catch (ex)
		{
			alert(19);
		};
		try
		{
			document.all('NetProfitRate').value = arrResult[0][20];
		}
		catch (ex)
		{
			alert(20);
		};
		try
		{
			document.all('MainBussiness').value = arrResult[0][21];
		}
		catch (ex)
		{
			alert(21);
		};
		try
		{
			document.all('Corporation').value = arrResult[0][22];
		}
		catch (ex)
		{
			alert(22);
		};
		try
		{
			document.all('ComAera').value = arrResult[0][23];
		}
		catch (ex)
		{
			alert(23);
		};
		try
		{
			document.all('Fax').value = arrResult[0][24];
		}
		catch (ex)
		{
			alert(24);
		};
		try
		{
			document.all('Phone').value = arrResult[0][25];
		}
		catch (ex)
		{
			alert(25);
		};
		try
		{
			document.all('GetFlag').value = arrResult[0][26];
		}
		catch (ex)
		{
			alert(26);
		};
		try
		{
			document.all('FoundDate').value = arrResult[0][29];
		}
		catch (ex)
		{
			alert(29);
		};
		try
		{
			document.all('BankCode').value = arrResult[0][31];
		}
		catch (ex)
		{
			alert(31);
		};
		try
		{
			document.all('BankAccNo').value = arrResult[0][32];
		}
		catch (ex)
		{
			alert(32);
		};
		try
		{
			document.all('OutPayFlag').value = arrResult[0][35];
		}
		catch (ex)
		{
			alert(35);
		};
		try
		{
			document.all('Currency').value = arrResult[0][38];
		}
		catch (ex)
		{
			alert(38);
		};
		try
		{
			document.all('CValiDate').value = arrResult[0][51];
		}
		catch (ex)
		{
			alert(51);
		};
		try
		{
			document.all('ExpPeoples').value = arrResult[0][54];
		}
		catch (ex)
		{
			alert(2);
		};
		try
		{
			document.all('Remark').value = arrResult[0][64];
		}
		catch (ex)
		{
			alert(3);
		};
		try
		{
			alert(arrResult[0][126]);
			if(arrResult[0][126] == null || arrResult[0][126] == -1)
			{
				document.all('EdorTransPercent').value = "";
			}
			else
			{
				document.all('EdorTransPercent').value = arrResult[0][126];
			}
		}
		catch (ex)
		{
			alert(3);
		};
	}
}
function haveMultiAgent()
{
	// alert("aa����"+document.all("multiagentflag").checked);
	// if(document.all("multiagentflag").checked){ //modify by liuqh ��֧�ֶ�ҵ��Ա
	// DivMultiAgent.style.display="";
	// }
	// else{
	// DivMultiAgent.style.display="none";
	// }
}
// Muline ����Ӷ����� parm1
function queryAgentGrid(Field)
{
	// alert("Field=="+Field);
	tField = Field;
	if(document.all('ManageCom').value == "")
	{
		alert("����¼����������Ϣ��");
		return;
	}
	var tCom = (document.all('ManageCom').value).substring(0, 2);
	// if(document.all(Field).all('MultiAgentGrid1').value==""){
	// }
	if(fm.AgentType.value == "03")
	{
		// ��������
		// showInfo = window.open("./CertifyInfoQuery.jsp");
		showInfo = window
				.open(
						"./AgentCommonQueryMain.jsp?ManageCom=" + tCom
								+ "&queryflag=1&branchtype=3",
						"AgentCommonQueryMain",
						'width='
								+ screen.availWidth
								+ ',height='
								+ screen.availHeight
								+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	else if(fm.AgentType.value == "01")
	{
		// ֱ�� ����ֱ�� branchtype='2'
		showInfo = window
				.open(
						"../sys/AgentCommonQueryMain.jsp?ManageCom=" + tCom
								+ "&queryflag=1&branchtype=2",
						"AgentCommonQueryMain",
						'width='
								+ screen.availWidth
								+ ',height='
								+ screen.availHeight
								+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}
	else
	{
		showInfo = window
				.open(
						"../sys/AgentCommonQueryMain.jsp?ManageCom=" + tCom
								+ "&queryflag=1&branchtype=2",
						"AgentCommonQueryMain",
						'width='
								+ screen.availWidth
								+ ',height='
								+ screen.availHeight
								+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}

	// if(document.all( Field ).all('MultiAgentGrid1').value != "") {
	// var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value; //��������
	// var strSql = "select AgentCode from LAAgent where AgentCode='" +
	// cAgentCode +"'";
	// var arrResult = easyExecSql(strSql);
	// //alert(arrResult);
	// if (arrResult == null) {
	// alert("����Ϊ:["+document.all( Field
	// ).all('MultiAgentGrid1').value+"]�Ĵ����˲����ڣ���ȷ��!");
	// }
	// }
}

// ��ʼ����ҵ��Ա��Ϣ
function displayMainAgent()
{
	document.all("BranchAttr").value = arrResult[0][3];
	document.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent()
{
	// document.all('multiagentflag').checked="true"; //modify by liuqh
	// haveMultiAgent();
}
// У��Ͷ������
function checkapplydate()
{
	if(fm.PolApplyDate.value.length == 8)
	{
		if(fm.PolApplyDate.value.indexOf('-') == -1)
		{
			var Year = fm.PolApplyDate.value.substring(0, 4);
			var Month = fm.PolApplyDate.value.substring(4, 6);
			var Day = fm.PolApplyDate.value.substring(6, 8);
			fm.PolApplyDate.value = Year + "-" + Month + "-" + Day;
			if(Year == "0000" || Month == "00" || Day == "00")
			{
				alert("�������Ͷ����������!");
				fm.PolApplyDate.value = "";
				return;
			}
		}
	}

	else
	{
		var Year = fm.PolApplyDate.value.substring(0, 4);
		var Month = fm.PolApplyDate.value.substring(5, 7);
		var Day = fm.PolApplyDate.value.substring(8, 10);
		if(Year == "0000" || Month == "00" || Day == "00")
		{
			alert("�������Ͷ����������!");
			fm.PolApplyDate.value = "";
			return;
		}
	}

}

function checkCValidate()
{
	if(fm.CValiDate.value.length == 8)
	{
		if(fm.CValiDate.value.indexOf('-') == -1)
		{
			var Year = fm.CValiDate.value.substring(0, 4);
			var Month = fm.CValiDate.value.substring(4, 6);
			var Day = fm.CValiDate.value.substring(6, 8);
			fm.CValiDate.value = Year + "-" + Month + "-" + Day;
			if(Year == "0000" || Month == "00" || Day == "00")
			{
				alert("�������Ͷ����������!");
				fm.CValiDate.value = "";
				return;
			}
		}
	}

	else
	{
		var Year = fm.CValiDate.value.substring(0, 4);
		var Month = fm.CValiDate.value.substring(5, 7);
		var Day = fm.CValiDate.value.substring(8, 10);
		if(Year == "0000" || Month == "00" || Day == "00")
		{
			alert("�������Ͷ����������!");
			fm.CValiDate.value = "";
			return;
		}
	}
}

function afterQuery5(arrResult)
{
	var tt = ManageCom.substring(0, 2);
	var sql = "select agentcode,name,managecom,popedom from laperagent where agentcode='"
			+ arrResult
			+ "' and managecom like '"
			+ tt
			+ "' union select agentcode,name,managecom,agentgroup from laagent where agentcode='"
			+ arrResult + "'";
	var arrQueryResult = easyExecSql(sql, 1, 0);
	// alert("oo");
	if(arrQueryResult != null)
	{
		// alert("ok");
		document.all(tField).all('MultiAgentGrid1').value = arrQueryResult[0][0];
		document.all(tField).all('MultiAgentGrid2').value = arrQueryResult[0][1];
		document.all(tField).all('MultiAgentGrid3').value = arrQueryResult[0][2];
		document.all(tField).all('MultiAgentGrid6').value = arrQueryResult[0][3];
	}

}
function afterQuery6(arrResult)
{
	if(arrResult != null)
	{
		fm.BankCode.value = arrResult[0][0];
		fm.BankCodeName.value = arrResult[0][1];
	}
}

function ManageFeeCal()
{

	var strUrl = "../appgrp/CalMangeFeeMain.jsp?grpcontno=" + fm.PrtNo.value
	showInfo = window
			.open(
					strUrl,
					"",
					"width=600, height=250, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}

function AlladdNameClick()
{
	var strUrl = "../appgrp/GrpIndManagerDiskInput.html"
	showInfo = window.open(strUrl, "", sFeatures);
}

function showRealFee()
{
	window
			.open(
					"../uwgrp/RealFeeInp.jsp?GrpContNo=" + fm.PrtNo.value + "",
					"",
					"width=300, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}

function initRiskGrp(cObjCode,cObjName)
{
	document.all('PayIntv').value = "";
	document.all('PayIntvName').value = "";
	showCodeList('RiskGrp', [cObjCode, cObjName], [0, 1], null, null, null, 1,
			'400');
}

function getcodedata2()
{
	var sql = "select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"
			+ fm.sysdate.value
			+ "') and riskprop in ('G','D') "
			+ " union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
			+ " startdate<='"
			+ fm.sysdate.value
			+ "' and (enddate is null or enddate>'"
			+ fm.sysdate.value
			+ "') and ManageComGrp='"
			+ document.all('ManageCom').value
			+ "' "
			+ "  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('G','D') order by RiskCode";
	var tCodeData = "0|";
	turnPage.strQueryResult = easyQueryVer3(sql, 1, 0, 1);
	// prompt("��ѯ���ִ���:",sql);
	if(turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for(i = 0; i < m; i++)
		{
			j = i + 1;
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|"
					+ turnPage.arrDataCacheSet[i][1];
		}
	}
	// alert(tCodeData);
	document.all("RiskCode").CodeData = tCodeData;

}

function getSpecContent()
{
	var tSel = SpecInfoGrid.getSelNo() - 1;
	fm.GrpSpec.value = SpecInfoGrid.getRowColData(tSel, 2);
}
function querySpec()
{

	var tSQL = "select grpcontno,speccontent,operator,makedate,serialno,proposalgrpcontno from lccgrpspec where grpcontno ='"
			+ prtNo + "' order by makedate,maketime";
	if(LoadFlag == 16)
	{
		tSQL = "select grpcontno,speccontent,operator,makedate,serialno,proposalgrpcontno from lccgrpspec where grpcontno ='"
				+ GrpContNo + "' order by makedate,maketime";
	}
	turnPage.queryModal(tSQL, SpecInfoGrid);
}

function showSpecInfo()
{
	window.open("../uwgrp/SpecInpMain.jsp?GrpContNo=" + fm.PrtNo.value
					+ "&LoadFlag=11", "", sFeatures);
}

// �����˹��˱����水ť��������ʾ
function ctrlButtonDisabled(tContNo,LoadFlag)
{

	// �޸�Ϊ��Function��ֱ��ִ�к��������ؽ������(��ά����:��һ��Ϊ��ť���ƣ��ڶ���Ϊdisabled����)��
	try
	{
		var arrStr = easyExecSql("select ctrlGrpSigUWButton('" + tContNo
				+ "','" + LoadFlag + "') from dual");
		// prompt('',arrStr);
		if(arrStr != null)
		{
			for(var i = 0; i < arrStr.length; i++)
			{
				// alert(arrStr[i][0]+":"+arrStr[i][1]+":"+arrStr[i][2]);
				try
				{
					if(arrStr[i][1] == 0)
					{
						eval("document.all('" + arrStr[i][0] + "').disabled='true'");
					}
					else
					{
						eval("document.all('" + arrStr[i][0] + "').disabled=''");
					}
				}
				catch (e)
				{
				}

			}
		}
	}
	catch (ex)
	{

	}
}

// ���� wangxw 20100925
function fillodlgrpcont()
{
	// alert("111");
	var GrpContNo = fm.ProposalGrpContNo.value;
	var tsql = "select grpcontno,oldgrpcontno from lcgrpcontrenewtrace where prtno='"
			+ GrpContNo + "'";
	var result = easyExecSql(tsql);
	if(result)
	{
		if(result[0][0] != result[0][1])
		{
			// ����ͬ�Ų�����ԭʼ������ʱ������������������Ҫ��ʾԭʼ������
			fm.OldGrpcontno.value = result[0][1];
		}
	}
}