//�������ƣ�WFEdorApp.js
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-27 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql=new SqlClass();
/*********************************************************************
 *  ��ѯ�����������
 *  ����:��ѯ�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickAll()
{
	// ��ʼ�����
	initAllGrid();

	// ��дSQL���
	var strSQL = "";
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.WFEdorApp");
    mySql.setSqlId("WFEdorAppSql2");
    mySql.addSubPara(curDay);
    mySql.addSubPara(fm.EdorAcceptNo.value);
    mySql.addSubPara(fm.ManageCom.value);
    mySql.addSubPara(fm.InputDate.value);
		mySql.addSubPara(manageCom);
		
	turnPage3.queryModal(mySql.getString(), AllGrid);
	
	HighlightAllRow();
	return true;
}

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSelf()
{
	// ��ʼ�����
	initSelfGrid();
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.WFEdorApp");
    mySql.setSqlId("WFEdorAppSql1");
    mySql.addSubPara(curDay);
    mySql.addSubPara(operator);
    	
	turnPage2.queryModal(mySql.getString(), SelfGrid);
	HighlightSelfRow();
	return true;
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function GoToBusiDeal()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	var tPrtNo = PrivateWorkPoolGrid.getRowColData(selno, 10);
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 13);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 14);		
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 16);
	var tDefaultOperator = PrivateWorkPoolGrid.getRowColData(selno, 11);
	var tLoadFlag = "scanApp";
	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
	}
	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorScanAppMain.jsp?" + varSrc,"��ȫɨ����������","left");

}
 /*
function GoToBusiDeal()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	var tPrtNo = SelfGrid.getRowColData(selno, 1);
	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
	var tMissionID = SelfGrid.getRowColData(selno, 5);
	var tSubMissionID = SelfGrid.getRowColData(selno, 6);		
	var tActivityID = SelfGrid.getRowColData(selno, 7);
		var tDefaultOperator = SelfGrid.getRowColData(selno, 9);
	var tLoadFlag = "scanApp";
	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
	}
	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorScanAppMain.jsp?" + varSrc,"��ȫɨ����������","left");

}
*/
/*********************************************************************
 *  Ӱ����
 *  ����: Ӱ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function GoToImageCopy()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	
//	var tPrtNo = SelfGrid.getRowColData(selno, 1);
//	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
//	var tMissionID = SelfGrid.getRowColData(selno, 5);
//	var tSubMissionID = SelfGrid.getRowColData(selno, 6);		
//	var tLoadFlag = "scanApp";
//	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	//var newWindow = OpenWindowNew("../bq/WFEdorAppImageCopy.jsp?" + varSrc,"����Ӱ��","left");

	var showStr="���ڸ���Ӱ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "../bq/WFEdorAppImageCopy.jsp?EdorAcceptNo=" + tEdorAcceptNo;
	document.getElementById("fm").submit(); //�ύ
}
 /*
function GoToImageCopy()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
	
//	var tPrtNo = SelfGrid.getRowColData(selno, 1);
//	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
//	var tMissionID = SelfGrid.getRowColData(selno, 5);
//	var tSubMissionID = SelfGrid.getRowColData(selno, 6);		
//	var tLoadFlag = "scanApp";
//	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	//var newWindow = OpenWindowNew("../bq/WFEdorAppImageCopy.jsp?" + varSrc,"����Ӱ��","left");

	var showStr="���ڸ���Ӱ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  

	fm.action = "../bq/WFEdorAppImageCopy.jsp?EdorAcceptNo=" + tEdorAcceptNo;
	document.getElementById("fm").submit(); //�ύ
}
*/
/*********************************************************************
 *  ��������
 *  ����: ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function applyMission()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 14);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 16);
	
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("ѡ������Ϊ�գ�");
	      return;
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
}
/* 
function applyMission()
{

	var selno = AllGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	fm.MissionID.value = AllGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = AllGrid.getRowColData(selno, 6);
	fm.ActivityID.value = AllGrid.getRowColData(selno, 7);
	
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("ѡ������Ϊ�գ�");
	      return;
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
}
*/
/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();

	if (FlagStr == "Succ" )	
	{
	  	//ˢ�²�ѯ���
		//easyQueryClickAll();
		//easyQueryClickSelf();	
		jQuery("#publicSearch").click();	
		jQuery("#privateSearch").click();	
	}
    else
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
    }
}

/*********************************************************************
 *  �򿪹��������±��鿴ҳ��
 *  ����: �򿪹��������±��鿴ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function showNotePad()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	//begin zbx 20110511 
	//var MissionID = SelfGrid.getRowColData(selno, 4);
	//var SubMissionID = SelfGrid.getRowColData(selno, 5);
	//var ActivityID = SelfGrid.getRowColData(selno, 6);
    var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 13);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 14);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 16);
	//end zbx 20110511 
	
	var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var OtherNoType = "1";
	
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	if(OtherNo==null || OtherNo== "")
	{
		mySql=new SqlClass();
    	mySql.setResourceName("bq.WFEdorApp");
    	mySql.setSqlId("WFEdorAppSql3");
    	mySql.addSubPara(MissionID);
    	OtherNo=easyExecSql(mySql.getString());
	}	
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	

}
 /*
function showNotePad()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	//begin zbx 20110511 
	//var MissionID = SelfGrid.getRowColData(selno, 4);
	//var SubMissionID = SelfGrid.getRowColData(selno, 5);
	//var ActivityID = SelfGrid.getRowColData(selno, 6);
    var MissionID = SelfGrid.getRowColData(selno, 5);
	var SubMissionID = SelfGrid.getRowColData(selno, 6);
	var ActivityID = SelfGrid.getRowColData(selno, 7);
	//end zbx 20110511 
	
	var OtherNo = SelfGrid.getRowColData(selno, 1);
	var OtherNoType = "1";
	
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	if(OtherNo==null || OtherNo== "")
	{
		mySql=new SqlClass();
    	mySql.setResourceName("bq.WFEdorApp");
    	mySql.setSqlId("WFEdorAppSql3");
    	mySql.addSubPara(MissionID);
    	OtherNo=easyExecSql(mySql.getString());
	}	
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	

}
*/
