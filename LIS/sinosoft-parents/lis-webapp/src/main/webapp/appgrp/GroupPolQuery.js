//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";


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
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
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
	//alert(arrSelected[0]);
	return arrSelected;
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initGrpPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate from LCGrpCont where 1=1 "
	       + " and appflag='0' and approveflag='0' and uwflag='0'"//δ���ˣ�δ�Զ��˱���δ�˹��˱���δǩ��
				 + " and (InputOperator ='' or InputOperator is null) " //�Ѿ�¼��ȷ�Ϻ�����ȷ��
				 + getWherePart( 'PrtNo' )
				 + getWherePart( 'ProposalGrpContNo','GrpProposalNo' )
				 + getWherePart( 'ManageCom' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'AgentGroup' )
				 + getWherePart( 'SaleChnl' )
				 + " and ManageCom like '" + comcode + "%%'";  //����Ȩ�޹�������	
				 + " order by PrtNo " ;
        //alert(strSQL);
	execEasyQuery( strSQL );
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
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
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		GrpPolGrid.delBlankLine();
	} // end of if
}
