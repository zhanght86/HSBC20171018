//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();



//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //initPolGrid();
  showSubmitFrame(mDebug);
  fmSave.submit(); //�ύ
}



//�ύ�����水ť��Ӧ����
function printPol()
{
 var i = 0;

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		arrReturn = getQueryResult();

		if( null == arrReturn ) {
			alert("��Ч������");
			return;
		}

		fmSave.PrtSeq.value = arrReturn[0][0];
		fmSave.PolNo.value = arrReturn[0][1];
		fmSave.fmtransact.value = "CONFIRM";
		//fmSave.submit();
		submitForm();
	  showInfo.close();

	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;

	arrSelected = new Array();
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0]=new Array();
	arrSelected[0][0]=PolGrid.getRowColData(tRow-1,1);
	arrSelected[0][1]=PolGrid.getRowColData(tRow-1,3);

	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();




  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

// ��ѯ��ť
function easyQueryClick()
{
	 //��ʼ�����
	initPolGrid();

		 //��дSQL���
	var strSQL = "";
	var len = tmanageCom.length;
	if(fm.all('Code').value=="null"||fm.all('Code').value=="")
	{
		alert("������֪ͨ�����ͣ�");
		return;
	}
	//alert(fm.all('Code').value);
	if(fm.all('Code').value=="15"||fm.all('Code').value=="16"){
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,(select distinct (case j.othernotype when '6' then '1' "+" when '7' then '3' "+" end ) from ljtempfee j where j.otherno = loprtmanager.otherno) FROM LOPRTManager WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and LOPRTManager.managecom like '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//	  + getWherePart('LOPRTManager.AgentCode','AgentCode')
//	  + getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.OtherNo','OtherNo','like')
//	  + getWherePart('LOPRTManager.Code','Code');
	
  	var  StartDay0 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay0 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq0 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo0 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code0 = window.document.getElementsByName(trim("Code"))[0].value;
	var sqlid0="RePrintSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql0.addSubPara(StartDay0);//ָ������Ĳ���
	mySql0.addSubPara(EndDay0);//ָ������Ĳ���
	mySql0.addSubPara(AgentCode0);//ָ������Ĳ���
	mySql0.addSubPara(PrtSeq0);//ָ������Ĳ���
	mySql0.addSubPara(OtherNo0);//ָ������Ĳ���
	mySql0.addSubPara(Code0);//ָ������Ĳ���
	strSQL=mySql0.getString();
	
	}
else if (fm.all('Code').value=="90")
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode FROM LOPRTManager WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and LOPRTManager.managecom like '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//	  + getWherePart('LOPRTManager.AgentCode','AgentCode')
//	  + getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.OtherNo','OtherNo','like')
//	  + getWherePart('LOPRTManager.Code','Code');
	
  	var  StartDay1 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay1 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq1 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo1 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code1 = window.document.getElementsByName(trim("Code"))[0].value;
	var sqlid1="RePrintSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql1.addSubPara(StartDay1);//ָ������Ĳ���
	mySql1.addSubPara(EndDay1);//ָ������Ĳ���
	mySql1.addSubPara(AgentCode1);//ָ������Ĳ���
	mySql1.addSubPara(PrtSeq1);//ָ������Ĳ���
	mySql1.addSubPara(OtherNo1);//ָ������Ĳ���
	mySql1.addSubPara(Code1);//ָ������Ĳ���
	strSQL=mySql1.getString();
	
	}
	else if (fm.all('Code').value=="54"||fm.all('Code').value=="52")//������ͳ���֪ͨ���otherno�����屣����---haopan
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,lcgrpcont.SaleChnl FROM LOPRTManager,lcgrpcont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  lcgrpcont.GrpContNo = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.OtherNo','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay2 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay2 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq2 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo2 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code2 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl2 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid2="RePrintSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql2.addSubPara(StartDay2);//ָ������Ĳ���
	mySql2.addSubPara(EndDay2);//ָ������Ĳ���
	mySql2.addSubPara(AgentCode2);//ָ������Ĳ���
	mySql2.addSubPara(PrtSeq2);//ָ������Ĳ���
	mySql2.addSubPara(OtherNo2);//ָ������Ĳ���
	mySql2.addSubPara(Code2);//ָ������Ĳ���
	mySql2.addSubPara(SaleChnl2);//ָ������Ĳ���
	strSQL=mySql2.getString();
	
	}
	else if (fm.all('Code').value=="G03")//��������������otherno���ŵ��µĸ�����----haopan
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,LCCont.SaleChnl FROM LOPRTManager,LCCont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  LCCont.ProposalContNo = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.standbyflag3','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay3 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay3 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode3 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq3 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo3 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code3 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl3 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid3="RePrintSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql3.addSubPara(StartDay3);//ָ������Ĳ���
	mySql3.addSubPara(EndDay3);//ָ������Ĳ���
	mySql3.addSubPara(OtherNo3);//ָ������Ĳ���
	mySql3.addSubPara(AgentCode3);//ָ������Ĳ���
	mySql3.addSubPara(PrtSeq3);//ָ������Ĳ���
	mySql3.addSubPara(Code3);//ָ������Ĳ���
	mySql3.addSubPara(SaleChnl3);//ָ������Ĳ���
	strSQL=mySql3.getString();
	
	}
	else if (fm.all('Code').value=="G04")//--haopan
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,LCCont.SaleChnl FROM LOPRTManager,LCCont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  LCCont.ProposalContNo = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.standbyflag2','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay4 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay4 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode4 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq4 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo4 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code4 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl4 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid4="RePrintSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql4.addSubPara(StartDay4);//ָ������Ĳ���
	mySql4.addSubPara(EndDay4);//ָ������Ĳ���
	mySql4.addSubPara(OtherNo4);//ָ������Ĳ���
	mySql4.addSubPara(AgentCode4);//ָ������Ĳ���
	mySql4.addSubPara(PrtSeq4);//ָ������Ĳ���
	mySql4.addSubPara(Code4);//ָ������Ĳ���
	mySql4.addSubPara(SaleChnl4);//ָ������Ĳ���
	strSQL=mySql4.getString();
	
	
	}
//	wujf add beg 2007-6-14 8:49 ����ţ�127221
	else if (fm.all('Code').value=="78")
	{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,lcgrpcont.SaleChnl FROM LOPRTManager,lcgrpcont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  lcgrpcont.proposalgrpcontno = LOPRTManager.otherno"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.standbyflag2','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('lcgrpcont.SaleChnl','SaleChnl');
	
  	var  StartDay5 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay5 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode5 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq5 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo5 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code5 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl5 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid5="RePrintSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql5.addSubPara(StartDay5);//ָ������Ĳ���
	mySql5.addSubPara(EndDay5);//ָ������Ĳ���
	mySql5.addSubPara(OtherNo5);//ָ������Ĳ���
	mySql5.addSubPara(AgentCode5);//ָ������Ĳ���
	mySql5.addSubPara(PrtSeq5);//ָ������Ĳ���
	mySql5.addSubPara(Code5);//ָ������Ĳ���
	mySql5.addSubPara(SaleChnl5);//ָ������Ĳ���
	strSQL=mySql5.getString();
	
	}
//#wujf add end 2007-6-14 8:50 ����ţ�127221

else{
//	strSQL = "SELECT LOPRTManager.PrtSeq,LOPRTManager.code, LOPRTManager.OtherNo, LOPRTManager.ManageCom, LOPRTManager.AgentCode,LCCont.SaleChnl FROM LOPRTManager,LCCont  WHERE LOPRTManager.StateFlag in( '1','3') AND LOPRTManager.PrtType = '0'  "
//	  + " and  LCCont.ProposalContNo = LOPRTManager.otherno"
//	  + " and code in ('16','18','15','54','09','52','08','78','90','BF00','JB00','G03','G04')"
//	  + " and LOPRTManager.managecom like  '"+tmanageCom+"%%'"
//		+ getWherePart('LOPRTManager.DoneDate','StartDay','>=')
//		+ getWherePart('LOPRTManager.DoneDate','EndDay','<=')
//		+ getWherePart('LOPRTManager.OtherNo','OtherNo','like') +
//	  getWherePart('LOPRTManager.AgentCode','AgentCode') +
//	  getWherePart('LOPRTManager.PrtSeq','PrtSeq')
//	  + getWherePart('LOPRTManager.Code','Code')
//	  + getWherePart('LCCont.SaleChnl','SaleChnl');
	
  	var  StartDay6 = window.document.getElementsByName(trim("StartDay"))[0].value;
  	var  EndDay6 = window.document.getElementsByName(trim("EndDay"))[0].value;
  	var  AgentCode6 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  PrtSeq6 = window.document.getElementsByName(trim("PrtSeq"))[0].value;
  	var  OtherNo6 = window.document.getElementsByName(trim("OtherNo"))[0].value;
  	var  Code6 = window.document.getElementsByName(trim("Code"))[0].value;
	var  SaleChnl6 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var sqlid6="RePrintSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("f1print.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(tmanageCom);//ָ������Ĳ���
	mySql6.addSubPara(StartDay6);//ָ������Ĳ���
	mySql6.addSubPara(EndDay6);//ָ������Ĳ���
	mySql6.addSubPara(AgentCode6);//ָ������Ĳ���
	mySql6.addSubPara(PrtSeq6);//ָ������Ĳ���
	mySql6.addSubPara(OtherNo6);//ָ������Ĳ���
	mySql6.addSubPara(Code6);//ָ������Ĳ���
	mySql6.addSubPara(SaleChnl6);//ָ������Ĳ���
	strSQL=mySql6.getString();
	
	}


	turnPage.strQueryResult  = easyQueryVer3(strSQL);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
   alert("û��Ҫ��ӡ����Ϣ��");
    return false;
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //tArr = chooseArray(arrDataSet,[0,1,3,4])
  //����MULTILINE������ʾ��ѯ���

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}
//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function IntegrateQueryClick()
{
	var newWindow = window.open("../sys/AllProposalQueryMain1.jsp","AllProposalQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  	newWindow.focus();
	}