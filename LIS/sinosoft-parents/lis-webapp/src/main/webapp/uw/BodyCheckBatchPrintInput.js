//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var ContNo;

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

	showInfo.focus();  //showSubmitFrame(mDebug);
  initPolGrid();
  fm.submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
	/**
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tMissionID = PolGrid.getRowColData(tSel-1,6);
		tSubMissionID = PolGrid.getRowColData(tSel-1,7);
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.ContNo.value = tContNo ;
		fmSave.PrtNo.value = tPrtNo ;


		document.getElementById("fmSave").submit();
		showInfo.close();
	}
	
	*/
	
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	if(count==0){
		alert("����ѡ��һ����¼���ٵ����ӡ��ť��");	
		return;
	}
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckBatchPrintSave.jsp?ChkCount="+count;
	document.getElementById("fmSave").submit();
}

//�ύ�����水ť��Ӧ����
function printPol2()
{
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	if(count==0){
		alert("����ѡ��һ����¼���ٵ����ӡ��ť��");	
		return;
	}		
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckPrintSave2.jsp?ChkCount="+count;
	document.getElementById("fmSave").submit();
}

//�ύ�����水ť��Ӧ����
function printPol3()
{
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	count = 1;
		
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckPrintSave3.jsp?PrintType="+count;
	document.getElementById("fmSave").submit();
}

//�ύ�����水ť��Ӧ����
function printPol4()
{
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	count = 2;
		
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckPrintSave3.jsp?PrintType="+count;
	document.getElementById("fmSave").submit();
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
  //showInfo.close();
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
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

function returnParent()
{
    tContNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?ContNo="+tContNo;
}


// ��ѯ��ť
function easyQueryClick()
{

	initPolGrid();
	// ��дSQL���
	var strSQL = "";
//	var ssql = "SELECT processid FROM LWCORRESPONDING where busitype='1001'";
	
	 var sqlid1="BodyCheckBatchPrintInputSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.BodyCheckBatchPrintInputSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 var ssql = mySql1.getString();
	
	var tProcessID = easyExecSql(ssql);
	// ��дSQL���
//	strSQL = "SELECT a.MissionProp3, a.MissionProp2,a.MissionProp4, a.MissionProp7 ,a.MissionProp13,a.MissionID ,a.SubMissionID FROM LWMission a WHERE a.ActivityID  in (select activityid from lwactivity  where functionid ='10010025') "  //ActivityID = '0000001106' ��֤����Ϊ�б����
//	        + "and a.ProcessID ='"+tProcessID+"' " //�б�������	     
//	        + getWherePart('a.MissionProp2', 'ContNo')
//		    + getWherePart('a.MissionProp7', 'ManageCom', 'like')
//			+ getWherePart('a.MissionProp4','AgentCode');
//			    //+ getWherePart('LWMission.MissionProp13','SaleChnl')
//             if(fm.SaleChnl.value!=null&&fm.SaleChnl.value!=""){			   
//			    strSQL=strSQL+" and exists (select 1 from lcpol where prtno =a.missionprop1 and salechnl='"+fm.SaleChnl.value+"')";
//			    }
		
           
             var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
         	 var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
         	 var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
         	 var  SaleChnl2 = fm.SaleChnl.value;
         	 var sqlid2="BodyCheckBatchPrintInputSql2";
         	 var mySql2=new SqlClass();
         	 mySql2.setResourceName("uw.BodyCheckBatchPrintInputSql");
         	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
         	 mySql2.addSubPara(tProcessID);//ָ���������
         	 mySql2.addSubPara(ContNo2);//ָ���������
         	 mySql2.addSubPara(ManageCom2);//ָ���������
         	 mySql2.addSubPara(AgentCode2);//ָ���������
         	 mySql2.addSubPara(SaleChnl2);//ָ���������
         	 strSQL = mySql2.getString();

	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
   alert("û��Ҫ��ӡ�ĳб����֪ͨ�飡");
    return false;
    }
   turnPage.queryModal(strSQL, PolGrid);
   
////��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//alert("b");
//  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
//  turnPage.pageDisplayGrid = PolGrid;
//alert("c");
//  //����SQL���
//  turnPage.strQuerySql     = strSQL;
//alert("d");
//  //���ò�ѯ��ʼλ��
//  turnPage.pageIndex       = 0;
//alert("e");
//  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
// arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
// //tArr=chooseArray(arrDataSet,[0])
//  //����MULTILINE������ʾ��ѯ���
//
//  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  alert("ok");
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
  fmSave.printButton.disabled = false;
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