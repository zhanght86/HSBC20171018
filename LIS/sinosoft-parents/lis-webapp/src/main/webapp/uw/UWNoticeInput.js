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
  document.getElementById("fm").submit();//�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  //var testPol = PolGrid.getRowColData();
  //alert(tSel);
  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
		//arrReturn = getQueryResult();
		//ContNo=arrReturn[0][0];
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
	  tOldPrtSeq = PolGrid.getRowColData(tSel-1,10); 
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
	
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.NoticeType.value = tNoticeType;
		fmSave.fmtransact.value = "PRINT";
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.target = "../f1print";
	  if(tNoticeType==89)
	    {
	   	  	//alert(11111);
	   	  	fmSave.action="./MeetF1PSave.jsp";
	   	}
	   	else if(tNoticeType == 03)
	   	{
	   		 fmSave.action="./BodyCheckPrintSave.jsp";
	   	}
	   	else if(tNoticeType == 04)
	   	{
	   		fmSave.action="./MeetF1PSave.jsp";
	   	}
	  	  else
	  	{
	  	
	  	fmSave.action="./UWF1PSave.jsp";
	  	} 	
		fmSave.submit();
		showInfo.close();


	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");
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
	var iHeight=250;     //�������ڵĸ߶�; 
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
	var tManageCom = fm.ManageCom.value;
	if(tManageCom != null && tManageCom == "")
	{
		alert("��¼����������");
		return;
	}
	if(tManageCom.length < 4)
	{
		alert("��¼����λ�����ϵĹ��������");
		return;
	}
	initPolGrid();
	// ��дSQL���
	//tongmeng 2007-11-12 modify
	//ͳһ��ӡ�˱�֪ͨ��
	var strSQL = "";
	// ��дSQL���
//	var ssql = "select processid from lwcorresponding where busitype in ('1001')";
	
	var sqlid0="UWNoticeInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("uw.UWNoticeInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	var ssql=mySql0.getString();
	
	var tProcessID_TB = easyExecSql(ssql);
	
//	ssql ="select processid from lwcorresponding where busitype in ('1002')"
	
	var sqlid1="UWNoticeInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWNoticeInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	ssql=mySql1.getString();
		
	var tProcessID_BQ = easyExecSql(ssql);
	
	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,"
	        //+ " LWMission.MissionProp5 "
	        + "a.code "
	        + ",(select codename from ldcode where codetype='noticetype' and code=a.code) "
	        + ",LWMission.MissionProp7 "
	        + " ,c.salechnl,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 "
	        + " FROM LWMission,loprtmanager a,lccont c "
	        //+ " WHERE LWMission.ActivityID in ('0000001280','0000001017','0000001107','0000001302','0000001106','0000001108') "
	        //+ " and (LWMission.ProcessID = '0000000003' or LWMission.ProcessID = '0000000000')" //�б������� ||��0000001108����ʱ��Ҫ��ѯ��ȫ�Ĺ�����
	        + " WHERE LWMission.ActivityID  in(Select activityid from lwactivity where functionid in ('10010007','10010025','10010026','10010027','10010050','10010058','10010063','10010064','10010065','10010066','10010067','10010068')) "
	        + " and LWMission.processid in ('"+tProcessID_TB+"','"+tProcessID_BQ+"')"
	        + " and c.prtno=a.otherno "
	        + " and a.prtseq=LWMission.MissionProp3"
	        + " and LWMission.missionprop2 = c.contno "
	//tongmeng 2007-12-11 add
	if(document.all('NoticeType_Code').value!=null&&trim(document.all('NoticeType_Code').value)!='')   
	{
		strSQL = strSQL + " and exists (select '1' from loprtmanager where prtseq=LWMission.MissionProp3 and code='"+trim(document.all('NoticeType_Code').value)+"')"
	}     
	        
	        
	        strSQL = strSQL + getWherePart('LWMission.MissionProp1', 'PrtNo')
	        + getWherePart('LWMission.MissionProp2', 'ContNo')
			    + getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
			    + getWherePart('LWMission.MissionProp4','AgentCode')
			   // + getWherePart('LWMission.MissionProp5','NoticeType')
			    + getWherePart('c.salechnl','SaleChnl');
	//tongmeng 2007-10-30 add
	//���Ӻ˱��½��ۺ�ϵͳֱ�ӷ��ŵ�֪ͨ��Ĵ�ӡ
	var tSQL_b = " union "
	           + "select a.prtseq,a.otherno,a.agentcode,a.code,(select codename from ldcode where codetype='noticetype' and code=a.code),a.managecom,(select salechnl from lccont where contno=a.otherno) "
	           + ",'TBJB','1',a.prtseq from loprtmanager a " 
                   + " where 1=1 and a.standbyflag7='TBJB' and a.stateflag='0' "
	           + getWherePart('a.otherno', 'ContNo')
     	           + getWherePart('a.managecom', 'ManageCom', 'like')
	           + getWherePart('a.agentcode','AgentCode')
	           + getWherePart('a.code','NoticeType')
	if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
	{
	   tSQL_b = tSQL_b + " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
	}	
	
	//����ת�˲��ɹ�֪ͨ���ӡ hanbin 2010-05-07
	//����ʱ����������������̫�࣬��ʱ̫�� hanbin-2010-05-19
	var tSQL_c = " union "
        + " select '', a.prtno, a.agentcode, '21', (select codename from ldcode where codetype = 'noticetype' and code = '21'), a.managecom, a.salechnl,'TBBANK','','' from lccont a where a.conttype = '1' and a.appflag = '0' and a.uwflag not in ('a', '1', '2') "
        + " and exists(select polno from lyreturnfrombankb  where polno = prtno and banksuccflag != '0000' group by polno having(count(1)  = 3 ) ) " 
        + " and not exists(select 1 from loprtmanager where othernotype = '05' and otherno = a.prtno and code = '21') "
        + " and not exists(select 1 from ljtempfee where tempfeetype = '1' and otherno = a.prtno and enteraccdate is not null) "//�����Ѿ��������ٴ�ӡ֪ͨ��
        + " and not exists(select polno from lyreturnfrombankb  where polno = prtno and banksuccflag = '0000') "
        + " and a.makedate >= to_date((subStr(add_months(now(),-1),1,10)),'yyyy-mm-dd')  and a.makedate <= to_date(substr(now(),1,10),'yyyy-mm-dd')"
        + getWherePart('a.ContNo', 'ContNo')
	    + getWherePart('a.managecom', 'ManageCom', 'like')
        + getWherePart('a.agentcode','AgentCode')
        + getWherePart('a.salechnl','SaleChnl')
        if(document.all('NoticeType_Code').value!=null && trim(document.all('NoticeType_Code').value)!='')
		{
        	tSQL_c = tSQL_c + " and '21'='"+trim(document.all('NoticeType_Code').value)+"'"
		}
	//��ѯ����ת�˲��ɹ�֪ͨ�顿 ���� hanbin 2010-05-11
	var tSQL_c_rePrint = " union "
        + "select a.prtseq,a.otherno,a.agentcode,a.code,(select codename from ldcode where codetype='noticetype' and code=a.code),a.managecom,(select salechnl from lccont where contno=a.otherno) "
        + ",'TBBANK','1',a.prtseq from loprtmanager a " 
            + " where 1=1 and a.code='21' and a.stateflag='0' "
        + getWherePart('a.otherno', 'ContNo')
	    + getWherePart('a.managecom', 'ManageCom', 'like')
        + getWherePart('a.agentcode','AgentCode')
        + getWherePart('a.code','NoticeType')
		if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
		{
			tSQL_c_rePrint = tSQL_c_rePrint + " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
		}
	tSQL_c = tSQL_c + tSQL_c_rePrint;
         
	strSQL = strSQL + tSQL_b + tSQL_c;
	
	
	var  PrtNo2 = window.document.getElementsByName(trim("PrtNo"))[0].value;
	var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	var  SaleChnl2 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var  NoticeType2 = window.document.getElementsByName(trim("NoticeType"))[0].value;
	var sqlid2="UWNoticeInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWNoticeInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tProcessID_TB);//ָ������Ĳ���0
	mySql2.addSubPara(tProcessID_BQ);//ָ������Ĳ���1
	mySql2.addSubPara(trim(document.all('NoticeType_Code').value));//ָ������Ĳ���2
	
	mySql2.addSubPara(PrtNo2);//ָ������Ĳ���3
	mySql2.addSubPara(ContNo2);//ָ������Ĳ���4
	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���5
	mySql2.addSubPara(AgentCode2);//ָ������Ĳ���6
	mySql2.addSubPara(SaleChnl2);//ָ������Ĳ���7
	
	mySql2.addSubPara(ContNo2);//ָ������Ĳ���8
	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���9
	mySql2.addSubPara(AgentCode2);//ָ������Ĳ���10
	mySql2.addSubPara(NoticeType2);//ָ������Ĳ���11
	mySql2.addSubPara(trim(document.all('SaleChnl').value));//ָ������Ĳ���12
	
	mySql2.addSubPara(ContNo2);//ָ������Ĳ���13
	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���14
	mySql2.addSubPara(AgentCode2);//ָ������Ĳ���15
	mySql2.addSubPara(SaleChnl2);//ָ������Ĳ���16
	mySql2.addSubPara(trim(document.all('NoticeType_Code').value));//ָ������Ĳ���17
	mySql2.addSubPara(ContNo2);//ָ������Ĳ���18
	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���19
	mySql2.addSubPara(AgentCode2);//ָ������Ĳ���20
	mySql2.addSubPara(NoticeType2);//ָ������Ĳ���21
	mySql2.addSubPara(trim(document.all('SaleChnl').value));//ָ������Ĳ���22
	strSQL=mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);


  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޴���ӡ�ĺ˱�֪ͨ�飡");
    return false;
    }
    turnPage.queryModal(strSQL, PolGrid);
////��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//
//  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
//  turnPage.pageDisplayGrid = PolGrid;
//
//  //����SQL���
//  turnPage.strQuerySql     = strSQL;
//
//  //���ò�ѯ��ʼλ��
//  turnPage.pageIndex       = 0;
//
//  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
// arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
// //tArr=chooseArray(arrDataSet,[0])
//  //����MULTILINE������ʾ��ѯ���
//
//  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  //displayMultiline(tArr, turnPage.pageDisplayGrid);
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