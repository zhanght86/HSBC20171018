//�������ƣ�UWAuto.js
//�����ܣ������Զ��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;

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

  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content)
{
    
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        content = "�Ժ˲�ͨ����" + content;
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
        content = "�Ժ�ͨ����" + content;
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
	
  // ˢ�²�ѯ���
    jQuery("#publicSearch").click();
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}



// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initAllPolGrid();
	
	// ��дSQL���
	k++;
	var strSQL = "";
	//strSQL = "select ProposalNo,PrtNo,RiskCode,RiskVersion,AppntName,InsuredName from LCPol where "+k+" = "+k
	//			 + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+ComCode+"','Pd')=1 "
	//			 + " and AppFlag='0'"          //�б�
	//			 + " and UWFlag in ('0')"  //δ�˱�
	//			 + " and LCPol.ApproveFlag = '9' "
	//			 + " and grppolno = '00000000000000000000'"
	//			 + " and contno = '00000000000000000000'"
	//			 + getWherePart( 'ProposalNo' )
	//			 + getWherePart( 'ManageCom', 'ManageCom')
	//			 + getWherePart( 'AgentCode' )
	//			 + getWherePart( 'AgentGroup' )
	//			 + getWherePart( 'RiskCode' )
	//			 + getWherePart( 'PrtNo' )
	//			 + " and ManageCom like '" + ComCode + "%%'";
				 //+ getWherePart( 'RiskVersion' );
	  //��ѯSQL�����ؽ���ַ���
	
//	strSQL = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp3,lwmission.MissionProp4,lwmission.MissionProp5,LWMission.MissionID ,LWMission.SubMissionID,LWMission.ActivityID from lwmission where "+k+"="+k
//	        + " and LWMission.ProcessID = '0000000003' " 
//            + " and LWMission.ActivityID = '0000001003' " 
//            + " and defaultoperator is null "
//            + getWherePart('lwmission.MissionProp1','ContNo')
// 			+ getWherePart('lwmission.MissionProp2','PrtNo')
// 			+ getWherePart('lwmission.MissionProp3','AppntName')
// 			+ getWherePart('lwmission.MissionProp4','InsuredName')
// 			+ getWherePart('lwmission.MissionProp5','AgentName')
// 			+ getWherePart('lwmission.MissionProp6','ManageCom')
 	
		 var sqlid1="UWAutoSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWAutoSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
//		mySql1.addSubPara(k);//ָ������Ĳ���
//		mySql1.addSubPara(k);//ָ������Ĳ���
		
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AppntName.value);//ָ������Ĳ���
		
		mySql1.addSubPara(fm.InsuredName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AgentName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
						 
	turnPage.queryModal(strSQL, AllPolGrid);
	return true;
}

// ��ѯ��ť
function easyQueryClickSelf()
{
	// ��ʼ�����
	initPolGrid();
	// ��дSQL���
	k++;
	var strSQL = "";
  
  		 var sqlid2="UWAutoSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWAutoSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
//		mySql2.addSubPara(k);//ָ������Ĳ���
//		mySql2.addSubPara(k);//ָ������Ĳ���
		
		mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AppntName.value);//ָ������Ĳ���
		
		mySql2.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(ComCode);//ָ������Ĳ���
	    strSQL=mySql2.getString();	
  
//	strSQL = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp3,lwmission.MissionProp4,lwmission.MissionProp5,LWMission.MissionID ,LWMission.SubMissionID,LWMission.ActivityID,LCCont.FirstTrialDate,(select nvl(to_char(min(makedate)),'��ɨ���') from es_doc_main where subtype= 'UA001' and DocCode = LCCont.PrtNo),lwmission.makedate,nvl(to_char(to_date(lwmission.makedate) - LCCont.FirstTrialDate),' ') "
//			+ " from lwmission ,LCCont where "+k+"="+k
//	        + " and LWMission.ProcessID = '0000000003' " 
//            + " and LWMission.ActivityID = '0000001003' " 
//            + " and LWMission.MissionProp2 = LCCont.prtno "
//            + getWherePart('lwmission.MissionProp1','ContNo')
// 			+ getWherePart('lwmission.MissionProp2','PrtNo')
// 			+ getWherePart('lwmission.MissionProp3','AppntName')
// 			+ getWherePart('lwmission.MissionProp4','AgentCode')
// 			+ getWherePart('lwmission.MissionProp5','ManageCom','like' )
// 			+ " and MissionProp5 like '" + ComCode + "%%'";
 					 
	turnPage2.queryModal(strSQL, PolGrid);
	return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initPolGrid();
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

/*********************************************************************
 *  ִ���Զ��˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12 
 *********************************************************************
 */
function autochk()
{
  var i = 0;
  var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  //showSubmitFrame(mDebug);
  //lockScreen('lkscreen');  
  fm.action = "../uw/UWAutoChk.jsp";
  document.getElementById("fm").submit(); //�ύ
}


/*********************************************************************
 *  ִ���Զ��˱�����󷵻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12 
 *********************************************************************
 */
function afterAutoChk( FlagStr, content )
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        content = "�Ժ˲�ͨ����" + content;
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
        content = "�Ժ�ͨ����" + content;    
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
//    easyQueryClick();
   // easyQueryClickSelf();


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
 *  �����Զ��˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12 
 *********************************************************************
 */
function ApplyUW()
{

	var selno = AllPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}

	fm.MissionID.value = AllPolGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = AllPolGrid.getRowColData(selno, 7);
	fm.ActivityID.value = AllPolGrid.getRowColData(selno, 8);
	
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

	fm.action = "../uw/UWAutoApply.jsp";
	document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  �����Զ��˱�����󷵻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12
 *********************************************************************
 */
function afterApplyUW( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
//    easyQueryClick();
   // easyQueryClickSelf();
}

function queryAgent(gridName,row,col){
	if (gridName.indexOf("Public") != -1) {
		flag = "public";
	} else if (gridName.indexOf("Private") != -1) {
		flag = "private";
	}
	if (document.all('AgentCode').value == "") {
		var newWindow = window.open(
						"../sys/AgentCommonQueryMain.jsp?queryflag=" + flag
								+ "&ManageCom=" + document.all('ManageCom').value
								+ "&row="+row+"&col="+col  );
	}
}

	function afterQuery2(arrResult, queryFlag,row,col) {
		if (arrResult != null) {
			if (queryFlag == "public") {
				PublicWorkPoolQueryGrid.setRowColData(Number(row), Number(col), arrResult[0][0]);
			} else if (queryFlag == "private") {
				PrivateWorkPoolQueryGrid.setRowColData(Number(row), Number(col), arrResult[0][0]);
			}
		}
	}

