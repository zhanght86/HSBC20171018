//�������ƣ�LLDealUWsecond.js
//�����ܣ����˽���
//�������ڣ�2003-03-27 15:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
// δ��ɵĶ��κ˱���ͬ���б� 
function initQueryLLUnfinishedContGrid()
{
	var tCaseNo = fm.CaseNo.value;	
	/*var strSQL = "select t.caseno,t.batno,t.contno,t.appntno,t.appntname,"
			 + " case t.state when '0' then '������' when '1' then '���' end, "
			 + " case t.claimrelflag when '0' then '���' when '1' then '�޹�' end,"
			 + " t.healthimpartno1,t.healthimpartno2,t.noimpartdesc,t.remark1,t.passflag,t.uwidea,"
			 + " (select r.username from lduser r where r.usercode in (select w.defaultoperator from lwmission w where w.missionprop1=t.caseno and activityid = '0000005505')),"
			 + " t.makedate"
			 + " from llcuwbatch t where 1=1 and t.state = '0'" 
			 + getWherePart('t.caseno' ,'CaseNo')
			 //+ " and exists (select 1 from lwmission where 1 = 1 and activityid = '0000005035' and processid = '0000000005'"
			 //+ " and (DefaultOperator = '" + fm.Operator.value + "' or MissionProp19 = '" + fm.Operator.value + "') and missionprop8 = '1')"
	         + " order by t.batno,t.contno";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql1");
	mySql.addSubPara(fm.CaseNo.value ); 	 
	//turnPage1.pageLineNum=5;
	turnPage1.queryModal(mySql.getString(), LLUnfinishedContGrid);
}

// �Ѿ���ɵĶ��κ˱���ͬ���б� 
function initQueryLLContGrid()
{
	var tInsuredNo = fm.InsuredNo.value;
	var tCaseNo = fm.CaseNo.value;
	/*var strSQL = "select t.caseno,t.batno,t.contno,t.appntno,t.appntname"
		 + ",case t.state when '0' then '������' when '1' then '���' end "
		 + ",case t.claimrelflag when '0' then '���' when '1' then '�޹�' end"
		 + ",t.healthimpartno1,t.healthimpartno2,t.noimpartdesc,t.remark1,t.passflag,t.uwidea, "
		 + " (select r.username from lduser r where r.usercode=t.operator), "
		 + " t.makedate,case t.ineffectflag when '0' then 'δ����' when '1' then '��Ч' when '2' then '����Ч' when '3' then '����' end,InsuredNo,InsuredName "
		 + " from llcuwbatch t where 1=1 and t.state = '1' "  
         + getWherePart('t.caseno' ,'CaseNo')
         //+ " and exists (select 1 from lwmission where 1 = 1 and activityid = '0000005035' and processid = '0000000005'"
		 //+ " and (DefaultOperator = '" + fm.Operator.value + "' or MissionProp19 = '" + fm.Operator.value + "') and missionprop8 = '1')"
         + " order by t.batno,t.contno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql2");
	mySql.addSubPara(fm.CaseNo.value ); 
	//turnPage.pageLineNum=5;
	//turnPage.queryModal(strSQL, LLContGrid);
	turnPage2.queryModal(mySql.getString(), LLContGrid);
}

/**
 * 2009-01-19 zhangzheng
 * ��ʼ��ʱ��ѯ�������ڵ�,Ϊ���ź˱�֪ͨ��׼������
 */
function initLWMission()
{
	/*var tLWMissionSQL = " select MissionID,SubMissionID from lwmission "
        + " where 1 = 1 and missionprop1 = '"+document.all('CaseNo').value+"'";*/
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql3");
	mySql.addSubPara(document.all('CaseNo').value); 
	var tResult = easyExecSql(mySql.getString());
	if(tResult!=null)
	{
		document.all('MissionID').value = tResult[0][0];
		document.all('SubMissionID').value = tResult[0][1];
	}
}


function LLContGridClick()
{
	clearPage();
	//��ʾ ����ʱ����ĺ�ͬ��Ϣ
	var tSel = LLContGrid.getSelNo()-1;	
	fm.HealthImpartNo1.value = LLContGrid.getRowColData(tSel,8);	//Ͷ���齡����֪��ѯ�ʺ�
	fm.HealthImpartNo2.value = LLContGrid.getRowColData(tSel,9);	//��콡����֪��ѯ�ʺ�
	fm.NoImpartDesc.value    = LLContGrid.getRowColData(tSel,10);	//��Ӧδ��֪���
	fm.Remark1.value         = LLContGrid.getRowColData(tSel,11);	//������Ŀǰ����״������
	
	fm.CaseNo.value			 = LLContGrid.getRowColData(tSel,1);	//�ⰸ��
	fm.BatNo.value     		 = LLContGrid.getRowColData(tSel,2);	//���κ�
	fm.ContNo.value   		 = LLContGrid.getRowColData(tSel,3);	//��ͬ����
	
	var tPassFlag = LLContGrid.getRowColData(tSel,16);

	//  ��ѯ �ú�ͬ�����εĺ˱�����
	/*var strBatContSql="select t.caseno,t.batno,t.contno,t.uwno,t.passflag,t.uwidea,t.operator,t.modifydate from llcuwsub t where 1=1"
			+" and t.caseno='"+ fm.CaseNo.value +"' "
			+" and t.batno='"+ fm.BatNo.value +"'"
			+" and t.contno='"+ fm.ContNo.value +"'"
			+" order by t.uwno desc"*/
	        mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql4");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.BatNo.value);
	mySql.addSubPara(fm.ContNo.value);
//	prompt("��ѯ �ú�ͬ�����εĺ˱�����",strBatContSql);
	var arr=easyExecSql(mySql.getString());	
	if(arr){
		fm.PassFlag.value = arr[0][4];	//��ͬ�˱�����
		fm.UWIdea.value   = arr[0][5];	//��ͬ�˱����
		showOneCodeName('lluwsign','PassFlag','PassFlagname');   
	 	fm.UWOperator.value = arr[0][6];	//�˱���
		fm.UWDate.value     = arr[0][7];	//�˱�����
	}
	
	//��ѯ�ú�ͬ����� �˱���������ۡ������
	/*var strContSql="select passflag,uwidea,operator,modifydate from llcuwmaster where 1=1 "
			+" and caseno='"+ fm.CaseNo.value +"' "
			+" and contno='"+ fm.ContNo.value +"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql5");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.ContNo.value);
	
	var arrCont=easyExecSql(mySql.getString());	
    if(arrCont==null)
	{
	 	fm.uwContState.value="";	
		fm.uwContIdea.value="";	
	}
	else
	{
	 	fm.uwContState.value  = arrCont[0][0];	//�����ͬ�˱�����
	 	fm.uwContState1.value = arrCont[0][0];	//	 	
		fm.uwContIdea.value   = arrCont[0][1];	//	�����ͬ�˱����
		showOneCodeName('lluwsign','uwContState','uwContStatename');  
		fm.UWContOperator.value = arrCont[0][2];	//�˱���
		fm.UWContDate.value     = arrCont[0][3];	//�˱�����
    }
    
	//[��ѯ �ú�ͬ�µ����ֺ˱���Ϣ]
	/*var strSQL="select a.caseno,a.contno,a.proposalcontno,a.polno,"
	            +" c.riskcode,c.riskname,a.uwno,a.passflag,(select codename from "
	            +" ldcode where codetype='claimuwstate' and code=a.passflag),a.uwidea"
				+" from lluwmaster a,lcpol b,lmrisk c where 1=1 "
				+" and a.polno=b.polno and b.riskcode=c.riskcode"
				+" and a.caseno='"+fm.CaseNo.value+"' "
				+" and a.contno='"+fm.ContNo.value+"' "
				+" order by a.caseno,a.contno,a.polno";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql6");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.ContNo.value);
	var arr=easyExecSql(mySql.getString());	
    if(arr==null)
    {
    	LLPolGrid.clearData();
    }
	else
	{
		displayMultiline(arr, LLPolGrid);
	}
	
	//var tReasonSql = "select Remark2 from llcuwbatch where CaseNo = '"+fm.CaseNo.value+"' and batno='"+fm.BatNo.value+"'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql7");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.BatNo.value);
	var tReason = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(tReason){
		fm.UWRisk2.value = tReason[0][0];	//��������ԭ��
	}
}

function clearPage()
{
 	fm.uwContState.value="";
 	fm.uwContStatename.value="";	
	fm.uwContIdea.value="";	
	fm.UWRiskState.value="";
	fm.UWRiskStateName.value="";
	fm.UWRiskIdea.value="";
	fm.HealthImpartNo1.value="";
	fm.HealthImpartNo2.value="";
	fm.NoImpartDesc.value="";
	fm.Remark1.value="";
	fm.PassFlag.value="";
	fm.PassFlagname.value="";
	fm.UWIdea.value="";
	fm.UWOperator.value="";
	fm.UWDate.value="";
	fm.UWContOperator.value="";
	fm.UWContDate.value="";	
}

/**=========================================================================
    ������Ч��ť
    �޸��ˣ�����
    �޸����ڣ�2005.11.08
   =========================================================================
**/
function Cvalidate()
{
	lockScreen('Pol');
	var tSelNo = LLContGrid.getSelNo();
	if(tSelNo < 1)
	{
		alert("��ѡ��һ��������Ϣ");
		unlockScreen('Pol');
		return;
	}

	if(!confirm("��ȷ��Ҫ��[��Ч]������"))
	{
		unlockScreen('Pol');
		return;
	}
    
    //У���Ƿ񷢷�֪ͨ��
    if(!checkTakeBackAllNotice())
    {
    	unlockScreen('Pol');
    	return false;
    }
    
    fm.CaseNo.valueo     = LLContGrid.getRowColData(tSelNo-1,1);//�ⰸ��	
	fm.BatNo.value      = LLContGrid.getRowColData(tSelNo-1,2);//���κ�	
    fm.ContNo.value     = LLContGrid.getRowColData(tSelNo-1,3);//��ͬ����       
    fm.InEffectFlag.value = "1";//��Ч��־X�������ã�1��Ч��2����Ч��3����
    
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator=1";
	document.getElementById("fm").submit(); 
}

/**=========================================================================
    ���Ӳ���Ч��ť
    �޸��ˣ�����
    �޸����ڣ�2005.11.08
   =========================================================================
**/
function NotCvalidate()
{
	lockScreen('Pol');    	
	var tSelNo = LLContGrid.getSelNo();
    if(tSelNo < 1)
    {
		alert("��ѡ��һ��������Ϣ")
		return;
    }

	if(!confirm("��ȷ��Ҫ��[����Ч]������"))
	{
		unlockScreen('Pol');
		return;
	}
    
	//У���Ƿ񷢷�֪ͨ��
    if(!checkTakeBackAllNotice())
    {
    	unlockScreen('Pol');
    	return false;
    }
    
    fm.CaseNo.value = LLContGrid.getRowColData(tSelNo-1,1);//�ⰸ��	
	fm.BatNo.value  = LLContGrid.getRowColData(tSelNo-1,2);//���κ�	
	fm.ContNo.value = LLContGrid.getRowColData(tSelNo-1,3);//��ͬ���� 		
	fm.InEffectFlag.value = "2";//��Ч��־:X�������ã�1��Ч��2����Ч��3����
	
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	
	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator=1";
	document.getElementById("fm").submit();
}

//�������
function showSecondUWInput()
{
	var tSelNo = LLContGrid.getSelNo();
    if(tSelNo < 1)
    {
		alert("��ѡ��һ��������Ϣ")
		return;
    }
    fm.CaseNo.value      = LLContGrid.getRowColData(tSelNo-1,1);//�ⰸ��
    fm.InsuredNo.value   = LLContGrid.getRowColData(tSelNo-1,17);//�ⰸ��
    fm.InsuredName.value = LLContGrid.getRowColData(tSelNo-1,18);//�ⰸ��
	//if(!KillTwoWindows(fm.CaseNo.value,'30'))
	//{
	//	return false;
	//}	
	
	//����Ƿ���ڶ��˵ļӷ���Ϣ�����б������
    if (!checkLjspay(fm.CaseNo.value))
    {
        return;
    }

	var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.CaseNo.value+"&InsuredNo="+fm.InsuredNo.value+"&InsuredName="+fm.InsuredName.value+"&MissionID="+fm.MissionID.value+"&RptorName= ";    
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{	
	unlockScreen('Pol');
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initForm();
	}
}

//���ֺ˱����۾�����Ϣ----���ۡ��˱����
function LLPolGridClick()
{
	fm.UWRiskState.value="";
	fm.UWRiskStateName.value="";
	fm.UWRiskIdea.value="";
	var tSel = LLPolGrid.getSelNo()-1;	
	fm.UWRiskState.value=LLPolGrid.getRowColData(tSel,8);
	fm.UWRiskIdea.value=LLPolGrid.getRowColData(tSel,10);	//���ֺ˱����
	showOneCodeName('uwstate','UWRiskState','UWRiskStateName');   	
}

function turnback1()
{
	if(fm.Flag.value == "Y"){
		location.href="LLDealUWInput.jsp";
	}else{
		top.close();
	}
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����ӡ��ӷѳб���ѯ����ť
    �� �� �ˣ������
    �޸����ڣ�2005.11.02
   =========================================================================
**/
function LLUWAddFeeQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
     
	     var tClmNo     = LLUnfinishedContGrid.getRowColData(row-1,1);//�ⰸ��
	     var tBatNo     = LLUnfinishedContGrid.getRowColData(row-1,2);//���κ�
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//��ͬ����
	     var tInsuredNo = LLUnfinishedContGrid.getRowColData(row-1,4);//Ͷ���˺���
         var tQueryFlag = 1;//0�ǲ�ѯ��1��ѯ 	
         var tUrl = "../claimnb/LLUWAddFeeMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo1     = LLContGrid.getRowColData(row1-1,1);//�ⰸ��
	     var tBatNo1     = LLContGrid.getRowColData(row1-1,2);//���κ�
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//��ͬ����
	     var tInsuredNo1 = LLContGrid.getRowColData(row1-1,4);//Ͷ���˺���
         var tQueryFlag1 = 1;//0�ǲ�ѯ��1��ѯ 	
         var tUrl1 = "../claimnb/LLUWAddFeeMain.jsp?ClmNo="+tClmNo1+"&ContNo="+tContNo1+"&BatNo="+tBatNo1+"&InsuredNo="+tInsuredNo1+"&QueryFlag="+tQueryFlag1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("��ѡ��һ����");  
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����ӡ���Լ�б���ѯ����ť
    �� �� �ˣ������
    �޸����ڣ�2005.11.25
   =========================================================================
**/
function LLUWSpecQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
     
	     var tClmNo     = LLUnfinishedContGrid.getRowColData(row-1,1);//�ⰸ��
	     var tBatNo     = LLUnfinishedContGrid.getRowColData(row-1,2);//���κ�
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//��ͬ����
	     var tInsuredNo = LLUnfinishedContGrid.getRowColData(row-1,4);//Ͷ���˺���
         var tQueryFlag = 1;//0�ǲ�ѯ��1��ѯ 	
         var tUrl = "../claimnb/LLUWSpecMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo1     = LLContGrid.getRowColData(row1-1,1);//�ⰸ��
	     var tBatNo1     = LLContGrid.getRowColData(row1-1,2);//���κ�
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//��ͬ����
	     var tInsuredNo1 = LLContGrid.getRowColData(row1-1,4);//Ͷ���˺���
         var tQueryFlag1 = 1;//0�ǲ�ѯ��1��ѯ 	
         var tUrl1 = "../claimnb/LLUWSpecMain.jsp?ClmNo="+tClmNo1+"&ContNo="+tContNo1+"&BatNo="+tBatNo1+"&InsuredNo="+tInsuredNo1+"&QueryFlag="+tQueryFlag1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("��ѡ��һ����");  
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����ӡ��˱�֪ͨ���ѯ����ť
    �� �� �ˣ������
    �޸����ڣ�2005.12.05
   =========================================================================
**/
function LLUWNoticeQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
     
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//��ͬ����
	     var tUrl = "../claimnb/LLUWNoticeQueryMain.jsp?ContNo="+tContNo;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//��ͬ����
	     var tUrl1 = "../claimnb/LLUWNoticeQueryMain.jsp?ContNo="+tContNo1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("��ѡ��һ����");  
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����ӡ��ӷ�Ӧ����Ϣ��ѯ����ť
    �� �� �ˣ������
    �޸����ڣ�2006.01.05
   =========================================================================
**/
function LLUWLJSPayQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
         var tClmNo     = LLUnfinishedContGrid.getRowColData(row-1,1);//�ⰸ��
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//��ͬ����
	     var tUrl = "../claimnb/LLUWLJSPayQueryMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo1     = LLContGrid.getRowColData(row1-1,1);//�ⰸ��
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//��ͬ����
	     var tUrl1 = "../claimnb/LLUWLJSPayQueryMain.jsp?ClmNo="+tClmNo1+"&ContNo="+tContNo1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("��ѡ��һ����");  
}

/**=========================================================================
    �޸�ԭ�򣺰��������漰��Ӧ�ռ�¼�ĺ���
    �� �� �ˣ������
    �޸����ڣ�2006-01-05 17:42
   =========================================================================
**/
function CaseFeeCancel()
{
	 if(!confirm("�ͻ��������ݽ��Ѻ�ִ�д˲������Ѻ˱��ӷѵ�Ӧ����Ϣת��ʵ����Ϣ����ȷ��Ҫִ�д˲�����"))
     {
   	    return;
     }
	 var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo = LLContGrid.getRowColData(row1-1,1);//�ⰸ��
	     fm.action  = "./LLCaseBackFeeSave.jsp?ClmNo="+tClmNo;
         submitForm();
     }
     else
     {
     	 alert("��ѡ��һ����¼��");  
     }
}

/**=========================================================================
    �޸�ԭ����ӹ����ύ����
    �� �� �ˣ������
    �޸����ڣ�2006-01-05 17:42
   =========================================================================
**/
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
}

/**=========================================================================
    �޸�ԭ�򣺺˱��ӷѺ���
    �޸���  �������
    �޸����ڣ�2005.11.08
   =========================================================================
**/
function AddFeeCancel()
{	
	var tSelNo = LLContGrid.getSelNo();
	if(tSelNo < 1)
	{
		alert("��ѡ��һ��������Ϣ��")
		return;
	}	
    /* if(!confirm("�ͻ���������ѣ�ִ�в�����ֱ��ɾ���˱��ӷѵ���Ϣ���˲������ɻָ�����ȷ��Ҫִ�д˲�����"))
    {
   	    return;
    } */
    var tCaseNo     = LLContGrid.getRowColData(tSelNo-1,1);//�ⰸ��	
	var tBatNo      = LLContGrid.getRowColData(tSelNo-1,2);//���κ�	
    var tContNo     = LLContGrid.getRowColData(tSelNo-1,3);//��ͬ���� 
    fm.CaseNo.value = tCaseNo;
    fm.ContNo.value = tContNo;
    fm.BatNo.value  = tBatNo;
  
    //��Ч��־
    fm.InEffectFlag.value = "X";//X�������ã�1��Ч��2����Ч��3����
    var mOperator = "AddFeeCancel";
        
	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator="+mOperator;
    submitForm();
    
}

/**=========================================================================
    ���ӳ�����ť
    �޸��ˣ�����
    �޸����ڣ�20065.02.10
   =========================================================================
**/
function UWConclusionCancel()
{
   	  if(!confirm("��ȷ��Ҫ��[����]���������ѡ���˸ò������˱����۽�����Ч���ӷ����ݽ�������!!!"))
   	  {
   	    return;
      }
    
      //У���Ƿ񷢷�֪ͨ��
      if(!checkTakeBackAllNotice())
      {
    	return false;
      }
      
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  var tSelNo = LLContGrid.getSelNo();
	  if(tSelNo < 1)
	  {
			alert("��ѡ��һ��������Ϣ")
			return;
	  }	
	  var tCaseNo     = LLContGrid.getRowColData(tSelNo-1,1);//�ⰸ��	
	  var tBatNo      = LLContGrid.getRowColData(tSelNo-1,2);//���κ�	
	  var tContNo     = LLContGrid.getRowColData(tSelNo-1,3);//��ͬ���� 
	  fm.CaseNo.value = tCaseNo;
	  fm.ContNo.value = tContNo;
	  fm.BatNo.value  = tBatNo;
	  
	  //��Ч��־
	  fm.InEffectFlag.value = "3";//X�������ã�1��Ч��2����Ч��3����
	 
	  if (tCaseNo != "" && tContNo != "" )
	  {	 	
		  	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator=1";
		    document.getElementById("fm").submit();
	  }
}
/**
 * 2009-01-22 zhangzheng
 * У���Ƿ񷢷�֪ͨ��
 */
function checkSendAllNotice()
{
	/*var tcheckSQL = " select activitystatus from lwmission "
        + " where  missionprop1 = '"+document.all('CaseNo').value+"' and activityid='0000005505' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql8");
	mySql.addSubPara(document.all('CaseNo').value); 

	//prompt("У���Ƿ񷢷�֪ͨ��sql",tcheckSQL);
	var tResult = easyExecSql(mySql.getString());
	if(tResult!=null&&tResult[0]==3)
	{
		alert("��������û���յ�֪ͨ��,������ɶ��˴���!");
		return false
	}
	
	return true;
}

/**
 * 2009-01-15 zhangzheng 
 * ���ź˱�֪ͨ��
 */
function SendAllNotice(){
	var tSelNo = LLContGrid.getSelNo();
	if(tSelNo < 1)
	{
		alert("��ѡ��һ������ɺ˱��ı�����Ϣ")
		return;
	}	
	var tCaseNo     = LLContGrid.getRowColData(tSelNo-1,1);//�ⰸ��	
	var tBatNo      = LLContGrid.getRowColData(tSelNo-1,2);//���κ�	
	var tContNo     = LLContGrid.getRowColData(tSelNo-1,3);//��ͬ���� 
	//var tCheckSql = "select 1 from llcuwmaster where caseno='"+tCaseNo+"' and batno='"+tBatNo+"' and contno='"+tContNo+"' and passflag='1'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql9");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tBatNo); 
	mySql.addSubPara(tContNo); 
	var arrNUW=easyExecSql(mySql.getString());//prompt("",tSql);
	if(arrNUW){
		alert("���������˾ܱ����ۣ��޷����ͺ˱�֪ͨ��");
		return false;
	}
	//var tCheckSql2 = "select 1 from llcuwmaster where caseno='"+tCaseNo+"' and batno='"+tBatNo+"' and contno='"+tContNo+"' and passflag='5'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql10");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tBatNo); 
	mySql.addSubPara(tContNo); 
	var arrNUW2=easyExecSql(mySql.getString());//prompt("",tSql);
	if(arrNUW2){
		alert("������δ�±������˱����ۣ��޷����ͺ˱�֪ͨ��");
		return false;
	}
	//var tCheckSql3 = "select 1 from llcuwbatch where caseno='"+tCaseNo+"' and batno='"+tBatNo+"' and contno='"+tContNo+"' and ineffectflag!='0'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql11");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tBatNo); 
	mySql.addSubPara(tContNo); 
	var arrNUW3=easyExecSql(mySql.getString());
	//prompt("",tCheckSql3); 
	if(arrNUW3){
		alert("�������Ѿ�������Ч������Ч���������޷����ͺ˱�֪ͨ��");
		return false;
	}
	/*var tcheckSQL = " select activitystatus from lwmission "
        + " where  missionprop1 = '"+document.all('CaseNo').value+"' and activityid='0000005505' and missionprop2='"+tBatNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql12");
	mySql.addSubPara(document.all('CaseNo').value); 
	mySql.addSubPara(tBatNo); 
	
	//prompt("У���Ƿ񷢷�֪ͨ��sql",tcheckSQL);
	var tResult = easyExecSql(mySql.getString());
	if(tResult!=null&&tResult[0][0]==3)
	{
//		alert("֪ͨ���Ѿ����ͣ��޷��ٴη���!");
//		return false
	}
	//	alert(fm.ContNo.value);return false;
    //window.open("../claim/LLDealUWsecondSendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&EdorNo="+fm.EdorNo.value+"&EdorType="+tEdorType[0][0],"window1");
	//window.open("../claim/LLDealUWsecondSendAllNoticeMain.jsp?CaseNo="+document.all('CaseNo').value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value);
	  window.open("../uw/LLSendAllNoticeMain.jsp?MissionID="+fm.MissionID.value+"&ClmNo="+document.all('CaseNo').value+"&BatNo="+fm.BatNo.value+"&ContNo="+fm.ContNo.value,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");

}

/**
 * 05-12  ����У�� ͬһ�����µ����к�ͬ���붼û���ѷ��Ͳ�δ���յ�֪ͨ���������Ч������Ч����������
 * */
function beforeSubmit(){
	/*var tMissionSql = "select 1 from lwmission where missionprop1='"
		+ fm.CaseNo.value
		+"' and  activitystatus not in('1','2') and activityid='0000005505' and MissionProp2='"
		+ fm.BatNo.value
		+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql13");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.BatNo.value); 
	var arrMission=easyExecSql(mySql.getString());//prompt("",tSql);
	if(arrMission){
		alert("���κ˱����б�������֪ͨ��δ����״̬�����ܶԱ��κ˱���");
	}
}

/**
 * У���Ƿ����֪ͨ��
 */
function checkTakeBackAllNotice()
{
//	var tcheckSQL = " select 'y' from lwmission "
//        + " where  missionprop1 = '"+document.all('CaseNo').value+"' and activityid "
//        + "in('0000005531','0000005533','0000005551','0000005553','0000005571','0000005573','0000005541','0000005544','0000005543','0000005534') ";
	
	sql_id = "LLDealUWsecondSql14";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLDealUWsecondInputSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(document.all('CaseNo').value);//ָ������Ĳ���
	my_sql.addSubPara(document.all('BatNo').value);//ָ������Ĳ���
	my_sql.addSubPara(document.all('ContNo').value);
	str_sql = my_sql.getString();
	var tResult = easyExecSql(str_sql);
	if(tResult!=null&&tResult[0][0]=='y')
	{
		alert("��������û���յ�֪ͨ��,������ɶ��˴���!");
		return false
	}
	
	return true;
}