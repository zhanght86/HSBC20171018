//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
//�ύ�����水ť��Ӧ����
function signGrpPol()
{
	//ֻ������λ��վ����
	var tLine=comcode.length;
	if(tLine<4)
	{
		alert("ֻ������λ����Ļ�������ϵͳ������");
		return false;
	}
	
//	var tSel = GrpGrid.getSelNo();
//	var cPolNo = "";
//	if( tSel != null && tSel != 0 )
//		cPolNo = GrpGrid.getRowColData( tSel - 1, 1 );
    var isChk = false;
    var rowNum = GrpGrid.mulLineCount;
    for(var i=0; i<rowNum;i++)
    {
       if(GrpGrid.getChkNo(i))
       {
          isChk = true;
          break;
       }
    }
   if (isChk==false)
    {
      alert("��ѡ��һ�ż���Ͷ�������ٽ���ǩ������");
      return false;
    }
	else
	{
		var i = 0;
		var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
		lockScreen('lkscreen');  
		document.getElementById("fm").submit(); //�ύ
	}
//	if( cPolNo == null || cPolNo == "" )
//		alert("��ѡ��һ�ż���Ͷ�������ٽ���ǩ������");
}

/*********************************************************************
 *  ����Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
	if( FlagStr == "Fail" )
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	 	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    //verify 
    var sql="select dif from lcgrpcont where prtno='"+fm.tt.value+"'";
    var arrQueryResult = easyExecSql(sql, 1, 0);
    if(arrQueryResult!=null)
    {
    var money=arrQueryResult[0][0];
    if(money>0)
    {
    	alert("�������ı�������ʣ�࣬�뼰ʱ�˷ѣ�");
    }
  }
		// ˢ�²�ѯ���
		easyQueryClick();		
	}
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
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
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
 *  ����EasyQuery��ѯ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	// ��ʼ�����
	initGrpGrid();
	
	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	/*
	strSQL = "select grpcontno,PrtNo,AppntNo,GrpName,ManageCom,AgentCode from LCGrpcont where 1=1 "
				 + "and AppFlag='0' "
				 + "and ApproveFlag='9' "
				 + "and UWFlag in ('9','4') "			// �˱�ͨ��
				 + getWherePart( 'GrpContNo' )
				 + getWherePart( 'ManageCom' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'AgentGroup' )
				 + getWherePart( 'GrpNo' )
				 + getWherePart( 'GrpName' );
				 */
				 if(comcode=="%")
				 {
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop9,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid, "
	       + "case  when (select distinct 1 from  ljtempfee where otherno=lwmission.missionprop2) is  null then 'δ����δǩ��' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is not null and confdate is null and confflag='0' ) is not null then '�ѽɷѴ�ǩ��' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is null and confdate is null and confflag='0' ) is not null then 'δ����δǩ��' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confdate is not null and confflag='1' and tempfeeno in (select tempfeeno from ljagettempfee)) is not null then '����ɷѴ���δǩ��' else '״̬��ѯ����' end "
	       + "from lwmission where 1=1"			 
	       + " and lwmission.processid = '0000000011'"
	       + " and lwmission.activityid = '0000011006' "
				 + getWherePart('lwmission.missionprop1','GrpContNo',strOperate)
				 + getWherePart('lwmission.missionprop2','PrtNo',strOperate);
				 //+ getWherePart('lwmission.missionprop5','AgentCode',strOperate)
				 //+ getWherePart('lwmission.missionprop6','AgentGroup',strOperate)
				 //+ getWherePart('lwmission.missionprop9','GrpNo',strOperate)
				 //+ getWherePart('lwmission.missionprop7','GrpName',strOperate)
				 //+ " and lwmission.missionprop4 like '" + comcode + "%'";  //����Ȩ�޹�������	
				 //+ " order by lwmission.missionprop2"
				 //alert("ok");
//alert(strSQL);
	//easyQueryVer3(strSQL);
}else{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop9,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid ,"
	       + "case  when (select distinct 1 from  ljtempfee where otherno=lwmission.missionprop2) is  null then 'δ����δǩ��' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is not null and confdate is null and confflag='0' ) is not null then '�ѽɷѴ�ǩ��' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is null and confdate is null and confflag='0' ) is not null then 'δ����δǩ��' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confdate is not null and confflag='1' and tempfeeno in (select tempfeeno from ljagettempfee)) is not null then '����ɷѴ���δǩ��' else '״̬��ѯ����' end "
	       + "from lwmission where 1=1"			 
	       + " and lwmission.processid = '0000000011'"
	       + " and lwmission.activityid = '0000011006' "
				 + getWherePart('lwmission.missionprop1','GrpContNo',strOperate)
				 + getWherePart('lwmission.missionprop2','PrtNo',strOperate)
				 //+ getWherePart('lwmission.missionprop5','AgentCode',strOperate)
				 //+ getWherePart('lwmission.missionprop6','AgentGroup',strOperate)
				 //+ getWherePart('lwmission.missionprop9','GrpNo',strOperate)
				 //+ getWherePart('lwmission.missionprop7','GrpName',strOperate)
				 + " and lwmission.missionprop4 like '" +fm.ManageCom.value+ "%%'"; //����Ȩ�޹�������	
				 //+ " order by lwmission.missionprop2"
				 //alert("ok");
//alert(strSQL);
	//easyQueryVer3(strSQL);
}
	turnPage.queryModal(strSQL, GrpGrid);
}

/*********************************************************************
 *  ��ʾEasyQuery�Ĳ�ѯ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initGrpGrid();
		//HZM �����޸�
		GrpGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpGrid.loadMulLine(GrpGrid.arraySave);		
		//HZM �����޸�	
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//�����ڽ���֪ͨ��
function SendFirstNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var tSel = GrpGrid.getSelNo();
	var cProposalNo = "";
	if( tSel != null && tSel != 0 )
		cProposalNo = GrpGrid.getRowColData( tSel - 1, 1 );
 
  cOtherNoType="01"; //������������
  cCode="57";        //��������
  
  
  if (cProposalNo != "")
  {
  	//showModalDialog("../uwgrp/GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

