//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.GroupUWAutoDetailSql";

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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			window.open("./AppGrpPolQuery.jsp","",sFeatures);
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpPolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// ��ѯ��ť
var queryBug = 1;
function easyQueryClick()
{
	// ��ʼ�����
	initGrpPolGrid();
	if (contNo==null||contNo=='')
		contNo = "00000000000000000000";
	// ��дSQL���
	var strSQL = "";
	/*
	var strSQL = "select ProposalGrpContNo,PrtNo,GrpName ,AppntNo ,prem from LCGrpCont where " + queryBug + "=" + queryBug
    				 + " and AppFlag='0' "   
    				 + " and PrtNo='" + grpprtno + "'" 
    				 + " and approveflag = '9'"
    				 + " and uwflag = '0'"
    				 + " order by makedate,maketime"; 
    				 */           
   	var sqlid1="GroupUWAutoDetailSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(queryBug);
		mySql1.addSubPara(queryBug);
		mySql1.addSubPara(grpprtno);
    //alert(strSQL);
	execEasyQuery( mySql1.getString() );
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;
    
	if( arrResult == null || arrResult.length == 0 )
		alert( "���ŵ����Զ��˱����������!" );
	else
	{
		// ��ʼ�����
		initGrpPolGrid();
		//HZM �����޸�
		GrpPolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpPolGrid.loadMulLine(GrpPolGrid.arraySave);		
		//HZM �����޸�
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			GrpPolGrid.addOne();
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
			  GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//GrpPolGrid.delBlankLine();
	} // end of if
}


/*********************************************************************
 *  ���ص��Զ��˱���ѯ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function GoBack()
 {
 	//top.opener.easyQueryClick();
 	top.close();
 }

/*********************************************************************
 *   �Զ��˱���ѡͶ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpUWAutoMakeSure(){
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cGrpPrtNo="";
	  var cflag = "2";
	  
	  for (i=0; i<GrpPolGrid.mulLineCount; i++) {
	    if (GrpPolGrid.getSelNo(i)) { 
	      checkFlag = GrpPolGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpPolGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpPolGrid.getRowColData(checkFlag - 1, 2);
	  }
	  else {
	    alert("����ѡ��һ����Ч�ı�����Ϣ��"); 
	    return false;
	  }
	  if( cGrpPrtNo == null || trim(cGrpPrtNo)=='' || cGrpProposalNo == null || trim(cGrpProposalNo)== '' )
	  {
	    alert("����ѡ��һ����Ч�ı�����Ϣ��"); 
	    return false;
	  }
	 fm.GrpProposalNo.value = cGrpProposalNo ;
	 fm.GrpPrtNo.value = cGrpPrtNo ;
	 fm.submit();
	 
}
/*********************************************************************
 *  ����Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	//showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	if( FlagStr == "Fail" )
	{             
		
		//showInfo=showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}
	else
	{ 
		// ˢ�²�ѯ���
		//showInfo=showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
		easyQueryClick();
		top.opener.easyQueryClick();		
	}
}
