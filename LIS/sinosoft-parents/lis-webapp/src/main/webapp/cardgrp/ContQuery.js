//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sqlresourcename = "cardgrp.ContQuerySql";
 var turnPage = new turnPageClass();   
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
	var tSel = ContGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{       
			var strreturn = getQueryResult();
			top.opener.afterQuery1( strreturn );
			
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
	var tRow = ContGrid.getSelNo();
	if( tRow == 0 || tRow == null || ContGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = ContGrid.getRowData(tRow-1);
	var streturn=arrSelected[0][1];
	return streturn;
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initContGrid();
	
	// ��дSQL���
	var strSQL = "";
	/*
	strSQL = "select ContNo,PrtNo,AgentCode,AppntName from LCCont where 1=1 "
				 + "and AppFlag='0' "
				 + " and (InputOperator ='' or InputOperator is null) " //�Ѿ�¼��ȷ�Ϻ�����ȷ��				 
				 + getWherePart( 'ContNo','GrpProposalNo')
				 + getWherePart( 'PrtNo' )
				 + getWherePart( 'ManageCom' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'AgentGroup' )
				 + "order by PrtNo";
*/

   			var mysql1= new SqlClass();
            var sqlid1 = "ContQuerySql1";
	        mysql1.setResourceName(sqlresourcename);
	        mySql1.setSqlId(sqlid1);
	        mysql1.addSubPara(fm.GrpProposalNo.value);
	        mysql1.addSubPara(fm.PrtNo.value);
	        mysql1.addSubPara(fm.ManageCom.value);
	        mysql1.addSubPara(fm.AgentCode.value);
	        mysql1.addSubPara(fm.AgentGroup.value);
	        
	turnPage.queryModal(mysql1.getString(), ContGrid);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initContGrid();
		//HZM �����޸�
		ContGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		ContGrid.loadMulLine(ContGrid.arraySave);		
		//HZM �����޸�
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				ContGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		ContGrid.delBlankLine();
	} // end of if
}
