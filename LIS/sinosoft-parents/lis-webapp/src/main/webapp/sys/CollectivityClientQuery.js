//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initCollectivityGrid();
	
	// ��дSQL���
	var strSQL = "";
	var strOperate = "like";
	
		var sqlid30="ContQuerySQL30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(fm.GrpNo.value);//ָ������Ĳ���
		mySql30.addSubPara(fm.GrpName.value);//ָ������Ĳ���
		mySql30.addSubPara(fm.GrpNature.value);//ָ������Ĳ���
	    strSQL=mySql30.getString();	
	
//	strSQL = "select customerno,GrpName,GrpNature from LDGrp  "
//	         +" where 1=1 "
//				 + getWherePart( 'customerno','GrpNo',strOperate )
//				 + getWherePart( 'GrpName','GrpName',strOperate )
//				 + getWherePart( 'GrpNature','GrpNature',strOperate );
//alert(strSQL);
	//execEasyQuery( strSQL );
	turnPage.queryModal(strSQL, CollectivityGrid); 
	//turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initCollectivityGrid();
		CollectivityGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		CollectivityGrid.loadMulLine(CollectivityGrid.arraySave);
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				CollectivityGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//CollectivityGrid.delBlankLine();
	} // end of if
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = CollectivityGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			
			top.opener.afterQuery4( arrReturn );
			top.close();
		}
		catch(ex)
		{
			//alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = CollectivityGrid.getSelNo();

	if( tRow == 0 || tRow == null)
	
		{
			//alert(1); 
			return arrSelected;
    }
  else
  	
	{
		 
	arrSelected = new Array();
	//������Ҫ���ص�����
  var strSQL = "";
  
  		var sqlid31="ContQuerySQL31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(CollectivityGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
	    strSQL=mySql31.getString();	
  
//	strSQL = "select CustomerNo,grpname,Asset,GrpNature,BusinessType,Peoples,Fax,vipvalue,blacklistflag from ldgrp where CustomerNo='"+
//	          CollectivityGrid.getRowColData(tRow-1,1)+"'";
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	return arrSelected; 
}
}
