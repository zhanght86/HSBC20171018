
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
/*********************************************************************
 *  ����Ͷ���������ύ
 ****************************************************************** */
function approvePol1()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == null || tSel == 0 )
	{
		alert("��ѡ��һ��Ͷ�������ٽ��и��˲���");
	}
	else
	{
		var i = 0;
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		showSubmitFrame(mDebug);
		fm.submit(); //�ύ
	}
}

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
	//���ӡˢ�ŵ�����
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=�б�����&PolState=1003&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		return;
	}
	showApproveDetail();
  // ˢ�²�ѯ���
	easyQueryClick2();
	easyQueryClickSelf();		
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if (cShow=="true")
		cDiv.style.display="";
	else
		cDiv.style.display="none";  
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  ��ʾͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showPolDetail()
{
	var i = 0;
	var checkFlag = 0;
	for (i=0; i<PolGrid.mulLineCount; i++) 
	{
		if (PolGrid.getSelNo(i)) 
		{ 
		checkFlag = PolGrid.getSelNo();
		break;
		}
	}
	if (checkFlag) 
	{ 
	var cPolNo = PolGrid.getRowColData( checkFlag - 1, 1 );
	mSwitch.deleteVar( "PolNo" );
	mSwitch.addVar( "PolNo", "", cPolNo );
	window.open("./ProposalMain.jsp?LoadFlag=6","window1",sFeatures);
	}
	else 
	{
	alert("����ѡ��һ��������Ϣ��"); 
	}
}           

/*********************************************************************
 *  ����EasyQuery��ѯ����
 **************************************************************** */
//����  ѯ����ť��������������ѯ���������Ĺ����ض���
function easyQueryClick()
{
	bg.addParam("ContNo",fm.ProposalNo.value,'like');
	bg.addParam("ManageCom",fm.ManageCom.value,'like');
	bg.addParam("AgentCode",fm.AgentCode.value,'like');
	bg.addParam("MakeDate",fm.AgentCode.value,'like');
	if(document.all("SaleChnl").value!=""){
		bg.addParam(null," and exists(select 'x' from lccont where lccont.contno= a.missionprop1 and lccont.salechnl='"+document.all("SaleChnl").value+"')",null);	
	}
	bg.queryData();
}

/*********************************************************************
 *  ����EasyQuery��ѯ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function easyQueryClick2()
{
	// ��ʼ�����
	initPolGrid();
	var strOperate="like";
	// ��дSQL���
	var strSql = "";           
	
		var sqlid2="ProposalApproveSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ProposalNo.value);//ָ������Ĳ���
        mySql2.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
        mySql2.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
 
        mySql2.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
        mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
        mySql2.addSubPara(ComCode);//ָ������Ĳ���
	    strSql=mySql2.getString();	
	                
//	strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionprop6,lwmission.Missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1"
//		+ " and activityid = '0000001001'"
//		+ " and processid = '0000000003'"
//		+ getWherePart('lwmission.MissionProp1','ProposalNo',strOperate)
//		+ getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//		+ getWherePart('lwmission.MissionProp6','MakeDate',strOperate)
//		+ getWherePart('lwmission.MissionProp7','AgentCode',strOperate)
//		+ getWherePart('lwmission.MissionProp8','ManageCom',strOperate)
//		+ " and LWMission.MissionProp8 like '" + ComCode + "%%'"  //����Ȩ�޹�������
//		+ " and defaultoperator is null "
//		+ " order by modifydate asc,modifytime asc";	
	turnPage.strQueryResult = easyQueryVer3(strSql, 1, 1, 1);  	
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) 
	{
	//alert("û���������������ݣ�");
	return "";
	}
	turnPage.queryModal(strSql, PolGrid);
}


//����ѯ  �����˱��� ���С�
function easyQueryClickSelf()
{

	initSelfPolGrid();
	var strSql = "";                  
	
		var sqlid3="ProposalApproveSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(Operator);//ָ������Ĳ���

	    strSql=mySql3.getString();	
	           
//   	strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionprop6,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.MissionProp8,lwmission.MissionProp7 from lwmission where 1=1"
//   			 + " and activityid = '0000001001'"
//   			 + " and processid = '0000000003'"
//			 + " and defaultoperator ='" + Operator + "'"
//			 + " order by modifydate asc,modifytime asc";				   
	turnPage2.queryModal(strSql, SelfPolGrid);
}
//����  �롿��ť������������������ӹ��������뵽���˶���
function ApplyUW()
{

	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	fm.MissionID.value = PolGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 7);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 8);
	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
/*	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001001'"
					+ " and defaultoperator is not null";
*/					
	    var sqlid10="ProposalApproveSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(fm.MissionID.value);//ָ������Ĳ���
		mySql10.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
	    tOperatorSql=mySql10.getString();
	    
	var tOperator = easyExecSql(tOperatorSql);	
	if(tOperator){
		alert("�����ѱ�������Ա���뵽���˹����أ�");
		easyQueryClick2();
		easyQueryClickSelf();
		return false;
	}
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	fm.action = "ProposalApproveChk.jsp";
	fm.submit(); //�ύ
}

function showApproveDetail() 
{ 
	var selno = PolGrid.getSelNo() - 1;
	if (selno <0)
	{
	alert("��ѡ��Ҫ���˵�Ͷ������");
	return;
	}  
	var tMissionID = PolGrid.getRowColData(selno, 6);
	var tSubMissionID= PolGrid.getRowColData(selno, 7);
	var tActivityID = PolGrid.getRowColData(selno, 8); 
	var polNo = PolGrid.getRowColData(selno, 1);
	var prtNo = PolGrid.getRowColData(selno, 2);
	var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='�б�¼��' or CreatePos='�б�����') and (PolState='1002' or PolState='1003')";
	var arrResult = easyExecSql(strSql);
	if (arrResult!=null && arrResult[0][1]!=Operator) 
	{
	//alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
	//return;
	}
	//������ӡˢ��
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	//alert(mSwitch.getVar("ApprovePolNo"));
	var strSql1="";
	
		var sqlid9="ProposalApproveSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(prtNo);//ָ������Ĳ���

	    strSql1=mySql9.getString();	
	
//	strSql1="select case lmriskapp.riskprop"
//		+" when 'I' then '1'"
//		+" when 'G' then '2'"
//		+" when 'Y' then '3'"
//		+" when 'T' then '5'"
//		+" when 'C' then '1'"
//		+" when 'B' then '2'"
//		+" else '1' end"
//		+" from lmriskapp where riskcode in "
//		+"(select riskcode from lcpol where polno = mainpolno and contno = '"+prtNo+"')";
	var arrResult1 = easyExecSql(strSql1);
	if (arrResult1==null) 
	{
	alert("��ѯͶ����������Ϣʧ�ܣ�");
	return false;
	}
    var BankFlag = arrResult1[0][0];//�øñ�־�жϣ����벻ͬ��ҳ��
    
		var sqlid4="ProposalApproveSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(prtNo);//ָ������Ĳ���

	    var strSql2=mySql4.getString();	
	
	//var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	
	var  tSplitPrtNo = prtNo.substring(0,4);
    var BankFlag = "";
    var SubType = "";
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    	SubType = "03";
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
//        window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3) + "&ContNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 2) + "&BankFlag=" + BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

	EasyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=5&prtNo="+prtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&BankFlag="+BankFlag+"&SubType="+SubType+"&NoType=1", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);    
}  
//��ѡ���������˱������С���Ӧ���������븴��ҳ��
function InitshowApproveDetail() 
{ 
	var selno = SelfPolGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���˵�Ͷ������");
	      return;
	}  
	var tMissionID = SelfPolGrid.getRowColData(selno, 6);
	var tSubMissionID= SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var polNo = SelfPolGrid.getRowColData(selno, 1);
	var prtNo = SelfPolGrid.getRowColData(selno, 2);
	var NoType = "1";
	// var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='�б�¼��' or CreatePos='�б�����') and (PolState='1002' or PolState='1003')";
	// var arrResult = easyExecSql(strSql);
	// if (arrResult!=null && arrResult[0][1]!=Operator) {
	//   alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
	//   return;
	// }
	//������ӡˢ��
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=0;      //�������ڵĿ��; 
	var iHeight=0;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	//alert(mSwitch.getVar("ApprovePolNo"));
	var strSql1="";
	
			var sqlid5="ProposalApproveSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(polNo);//ָ������Ĳ���

	    strSql1=mySql5.getString();	
	
//	strSql1="select case lmriskapp.riskprop"
//		+" when 'I' then '1'"
//		+" when 'G' then '2'"
//		+" when 'Y' then '3'"
//		+" when 'T' then '5'"
//		+" when 'C' then '1'"
//		+" when 'B' then '2'"
//		+" else '1' end"
//		+" from lmriskapp where riskcode in "
//		+"(select riskcode from lcpol where polno = mainpolno and contno = '"+polNo+"')";
	//prompt("strSql1",strSql1);
	var arrResult1 = easyExecSql(strSql1);
	if (arrResult1==null) 
	{
	alert("��ѯͶ����������Ϣʧ�ܣ�");
	return false;
	}
    var BankFlag = arrResult1[0][0];//�øñ�־�жϣ����벻ͬ��ҳ��
  
  		var sqlid6="ProposalApproveSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(polNo);//ָ������Ĳ���

	    var strSql2=mySql6.getString();	
  
	//var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	
	var  tSplitPrtNo = polNo.substring(0,4);
    var BankFlag = "";
    var SubType = "";
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    	SubType = "03";
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
    
//    alert("BankFlag"+BankFlag);
//    alert("SubType"+SubType);
    
	easyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=5&prtNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType=TB10"+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
} 

/*********************************************************************
 *  ����Ͷ���������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function passApprovePol() 
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

    //�ύ
    var polNo = mSwitch.getVar("ApprovePolNo");
    //alert(polNo);
	window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo="+polNo+"&approveFlag=9";    
}

function refuseApprovePol() 
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		
	showSubmitFrame(mDebug);
    //�ύ
     var polNo = mSwitch.getVar("ApprovePolNo");
	window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo="+polNo+"&approveFlag=1";    
}

//************************
var cflag = "5";          //���������λ�� 5.����

function QuestInput()
{
	
	cProposalNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 1);  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
	//initInpBox();
    	//initPolBox();
	//initPolGrid();
	
}

function QuestReply()
{
	cProposalNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 1);  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestReplyMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
	//initInpBox();
  //initPolBox();
	//initPolGrid();
	
}

function QuestQuery()
{
	cProposalNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 1);  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
	//initInpBox();
  //initPolBox();
	//initPolGrid();
	
}


function returnparent()
  {   
  	var backstr=document.all("ContNo").value;    
  	//alert(backstr+"backstr");   
  	mSwitch.deleteVar("ContNo");               
	mSwitch.addVar("ContNo", "", backstr);     
	mSwitch.updateVar("ContNo", "", backstr); 
  	location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType; 
}        

function showNotePad()
{

	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = SelfPolGrid.getRowColData(selno, 6);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}
	var NoType = "1";
			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}



function queryAgent()
{
	if(document.all('AgentCode').value == "")	
	{
	//tongmeng 2007-09-10 modify ��Ϊ��������Ҳʹ�øò˵�������ȥ���Դ�����չҵ���͵�����
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	//var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	//var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
    else
	{
		var cAgentCode = fm.AgentCode.value;  //��������
		//tongmeng 2007-09-10 modify ��Ϊ��������Ҳʹ�øò˵�������ȥ���Դ�����չҵ���͵�����
		
		 var sqlid7="ProposalApproveSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ProposalApproveSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSQL=mySql7.getString();	
		
//		var strSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name from laagent a,latree b,labranchgroup c where 1=1 "
//		     + "and a.agentcode = b.agentcode " 
//		     + " and a.agentstate!='03' and a.agentstate!='04' and a.agentgroup = c.agentgroup and a.agentcode='"+cAgentCode+"'";
		var arrResult = easyExecSql(strSQL);
		if (arrResult != null) 
		{
			fm.AgentCode.value = arrResult[0][0];
		}
		else
		{
			
			alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
			fm.AgentCode.value="";
			return;
		}
	}
}

function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
  	//fm.AgentCode.value = arrResult[0][0];
  	//fm.BranchAttr.value = arrResult[0][93];
  	//fm.AgentGroup.value = arrResult[0][1];
  	//fm.AgentName.value = arrResult[0][5];
  	//fm.AgentManageCom.value = arrResult[0][2];
	fm.AgentCode.value = arrResult[0][0];
	}
}