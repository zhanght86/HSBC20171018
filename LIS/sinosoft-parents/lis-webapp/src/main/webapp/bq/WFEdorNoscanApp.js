//�������ƣ�WFEdorNoscanApp.js
//�����ܣ���ȫ������-��ȫ��ɨ������
//�������ڣ�2005-04-27 15:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPageSelfGrid = new turnPageClass();
var mySql=new SqlClass();

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
/* 
function easyQueryClickSelf()
{
	  mySql=new SqlClass();
		var strSQL = "";

 mySql.setResourceName("bq.WFEdorNoScanAppSql");
 mySql.setSqlId("WFEdorNoScanAppSql1");
 mySql.addSubPara(operator);
 mySql.addSubPara(comcode);
 mySql.addSubPara(fm.EdorAcceptNo_ser.value);
 mySql.addSubPara(fm.ManageCom_ser.value);
 mySql.addSubPara(fm.OtherNo.value);
 mySql.addSubPara(fm.OtherNoType.value);
 mySql.addSubPara(fm.EdorAppName.value);
 mySql.addSubPara(fm.AppType.value);
 mySql.addSubPara(fm.MakeDate.value);
 mySql.addSubPara(curDay);


	turnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
	turnPageSelfGrid.queryModal(mySql.getString(), SelfGrid);
	
	//alert(SelfGrid.mulLineCount);
	HighlightByRow();
	return true;
}

function HighlightByRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,16);  	
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
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var days = PrivateWorkPoolGrid.getRowColData(i,11); //��������    	
    	if(days != null && days != "")
    	{
    	   var ret = Arithmetic(days,'-','5','2');
	    	if(ret>=0)
	    	{
	    		PrivateWorkPoolGrid.setRowClass(i,'warn');
	    	}
    	}
    }  
}


/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 
 function goToBusiDeal()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var tDefaultOperator = operator;
  	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	//changed by pst on 2008-07-07 
	var tLoadFlag = "edorApp";
	
	var sqlid2="bq.WFEdorNoScanAppSql";
	var mySql2=new SqlClass();
 	mySql2.setResourceName(sqlid2);
 	mySql2.setSqlId("WFEdorNoScanAppSql3");
	mySql2.addSubPara(tMissionID);//ָ������Ĳ���
	strSQL=mySql2.getString();
  
  var brr = easyExecSql(strSQL);
  if(brr>0)
  {
  	tLoadFlag='approveModify';
  }

	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
	}
	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
		}
  //end
	var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID=" + tActivityID + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorAppInputFrame.jsp?Interface=../bq/PEdorAppInput.jsp" + varSrc,"","left");
} 
 /*
function goToBusiDeal()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 10);
	var tSubMissionID = SelfGrid.getRowColData(selno, 11);
	var tDefaultOperator = SelfGrid.getRowColData(selno, 13);
  var tActivityID = SelfGrid.getRowColData(selno, 12);
	//changed by pst on 2008-07-07 
	var tLoadFlag = "edorApp";
	
	var sqlid2="bq.WFEdorNoScanAppSql";
	var mySql2=new SqlClass();
 	mySql2.setResourceName(sqlid2);
 	mySql2.setSqlId("WFEdorNoScanAppSql3");
	mySql2.addSubPara(tMissionID);//ָ������Ĳ���
	strSQL=mySql2.getString();
  
  var brr = easyExecSql(strSQL);
  if(brr>0)
  {
  	tLoadFlag='approveModify';
  }

	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
	}
	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
		}
  //end
	var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID=" + tActivityID + " &MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorAppInputFrame.jsp?Interface=../bq/PEdorAppInput.jsp" + varSrc,"","left");
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
  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	fm.ICFlag.value="";
	fm.action = "../bq/WFEdorNoscanAppSave.jsp";
	document.getElementById("fm").submit(); //�ύ
}

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
	mySql=new SqlClass();
	if (FlagStr == "Succ" )
	{
		/*
		if(window.confirm("����Ҫ����������"))
		{
		*/
			//ֱ�ӽ��뱣ȫ�������
			var tEdorAcceptNo = fm.EdorAcceptNo.value;
			var tMissionID;
			var tSubMissionID;
			var tActivityID


			
 mySql.setResourceName("bq.WFEdorNoScanAppSql");
 mySql.setSqlId("WFEdorNoScanAppSql2");
 mySql.addSubPara(tEdorAcceptNo);


			var brr = easyExecSql(mySql.getString());

			if ( brr )
			{
				//alert("�Ѿ����뱣���");
				brr[0][0]==null||brr[0][0]=='null'?'0':tMissionID    = brr[0][0];
				brr[0][1]==null||brr[0][1]=='null'?'0':tSubMissionID = brr[0][1];
				brr[0][2]==null||brr[0][2]=='null'?'0':tActivityID = brr[0][2];
			}
			else
			{
				alert("�����������ѯʧ�ܣ�");
				return;
			}

			var tLoadFlag = "edorApp";
			
			var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID=" + tActivityID + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
			var newWindow = OpenWindowNew("../bq/PEdorAppInputFrame.jsp?Interface=../bq/PEdorAppInput.jsp" + varSrc,"","left");
/*
		}
		else
		{
	  		//ˢ���б�
			easyQueryClickSelf();
		}
*/
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
	
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 2);

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"","left");
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
	var MissionID = SelfGrid.getRowColData(selno, 10);
	var SubMissionID = SelfGrid.getRowColData(selno, 11);
	var ActivityID = SelfGrid.getRowColData(selno, 12);
	var OtherNo = SelfGrid.getRowColData(selno, 1);

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"","left");
}
*/
