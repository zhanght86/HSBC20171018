//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var manageCom;

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
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
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,6);
		if( null == arrReturn ) {
			alert("��Ч������");
			return;
		}
		fmSave.target="f1print";
		fmSave.ContNo.value = tContNo ;
		fmSave.PrtSeq.value = tPrtSeq ;
		fmSave.noticetype.value = tNoticeType;
		
		fmSave.fmtransact.value = "CONFIRM";
		fmSave.submit();
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
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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
  }
  else
  { 
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
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
if(document.all('NoticeType').value == "")
  {
  	alert("��ѡ��֪ͨ�����ͣ�");
  	return;
  	}
	
    /*strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, LCCont.SaleChnl,LOPRTManager.ManageCom ,LOPRTManager.code FROM LOPRTManager,LCCont WHERE 1 = 1 " 
	   + "and LCCont.ProposalContNo = LOPRTManager.OtherNo "	 
	   + "and LOPRTManager.code in('08','78')"
	   + getWherePart('LOPRTManager.otherno', 'ContNo') 
	   + getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
	   + getWherePart('LOPRTManager.AgentCode','AgentCode')	  
	   + getWherePart('LCCont.SaleChnl','SaleChnl')
	   + " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'"//��½����Ȩ�޿���	  
	   +" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";*/
	if(fm.NoticeType.value == "01")
	{
//	   strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, LCCont.SaleChnl,LOPRTManager.ManageCom ,LOPRTManager.code FROM LOPRTManager,LCCont WHERE 1 = 1 " 
//	   + "and LCCont.ContNo = LOPRTManager.OtherNo "	 
//	   + "and LOPRTManager.code ='08'"
//	   + getWherePart('LOPRTManager.otherno', 'ContNo') 
//	   + getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
//	   + getWherePart('LOPRTManager.AgentCode','AgentCode')	  
//	   + getWherePart('LCCont.SaleChnl','SaleChnl')
//	   + " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'"//��½����Ȩ�޿���	  
//	   +" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";
		fmSave.noticetype.value = "01"
		
		var  ContNo0 = window.document.getElementsByName(trim("ContNo"))[0].value;
	  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	  	var  SaleChnl0 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		var sqlid0="NBCSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.NBCSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(ContNo0);//ָ������Ĳ���
		mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
		mySql0.addSubPara(AgentCode0);//ָ������Ĳ���
		mySql0.addSubPara(SaleChnl0);//ָ������Ĳ���
		mySql0.addSubPara(comcode);//ָ������Ĳ���
		strSQL=mySql0.getString();
	}
 else if(fm.NoticeType.value == "02")
 	{
// 		 strSQL = "SELECT LOPRTManager.PrtSeq, LOPRTManager.OtherNo,LOPRTManager.AgentCode, lcgrpcont.SaleChnl,LOPRTManager.ManageCom ,LOPRTManager.code FROM LOPRTManager,lcgrpcont WHERE 1 = 1 " 
//	   + "and lcgrpcont.proposalgrpcontno = LOPRTManager.OtherNo "	 
//	   + "and LOPRTManager.code ='78'"
//	   //+ getWherePart('LOPRTManager.otherno', 'ContNo') 
//	   + getWherePart('LOPRTManager.ManageCom', 'ManageCom', 'like')
//	   + getWherePart('LOPRTManager.AgentCode','AgentCode')	  
//	   + getWherePart('lcgrpcont.SaleChnl','SaleChnl')
//	   + " AND LOPRTManager.ManageCom LIKE '" + comcode + "%%'"//��½����Ȩ�޿���	  
//	   +" AND LOPRTManager.StateFlag = '0' AND LOPRTManager.PrtType = '0'";
 		fmSave.noticetype.value = "02";
 		
// 		if(fm.ContNo.value!=null&&fm.ContNo.value!=""){
// 		   //add by jiaqiangli 2009-11-18
// 		   strSQL =strSQL+" and LOPRTManager.otherno in (select prtno from lcgrppol where grpcontno ='"+fm.ContNo.value+"')";
// 		}
 		
	  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	  	var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	  	var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		var sqlid1="NBCSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.NBCSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(ManageCom1);//ָ������Ĳ���
		mySql1.addSubPara(AgentCode1);//ָ������Ĳ���
		mySql1.addSubPara(SaleChnl1);//ָ������Ĳ���
		mySql1.addSubPara(comcode);//ָ������Ĳ���
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		strSQL=mySql1.getString();
 	
 	}
		
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��Ҫ��ӡ������Լ�˷�֪ͨ�飡");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  
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

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
  
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}