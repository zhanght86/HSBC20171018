var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var arrDataSet;
// ��ѯ��ť
function easyQueryClick()
{
	
	// ��ʼ������
	initErrLogBonusGrid();
	
	//if(tLogFlag=="C"){
	   //�ŵ���������
	//   var strSQL = "";
	//strSQL = "select standbyflag1,otherno,runtype,content,makedate from LSRunDataInfo where 1=1 and busstype='GRPBONUS' "
	//			 + getWherePart( 'otherno' ,'GrpPolNo')
	//			 + getWherePart( 'runtype','Type' )
	//			 + getWherePart( 'standbyflag1','FiscalYear' )
	//			 + getWherePart( 'MakeDate' );
	//}else{
	// ��дSQL���
	var strSQL = "";
	
	var sqlid831100643="DSHomeContSql831100643";
var mySql831100643=new SqlClass();
mySql831100643.setResourceName("uwgrp.ViewErrLogGrpBonusAssignSql");//ָ��ʹ�õ�properties�ļ���
mySql831100643.setSqlId(sqlid831100643);//ָ��ʹ�õ�Sql��id
mySql831100643.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
mySql831100643.addSubPara(fm.Type.value);//ָ������Ĳ���
mySql831100643.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
mySql831100643.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
strSQL=mySql831100643.getString();
	
//	strSQL = "select FiscalYear,GrpPolNo,Type,errMsg,makedate from LOBonusAssignGrpErrLog where 1=1  "
//				 + getWherePart( 'GrpPolNo' )
//				 + getWherePart( 'Type' )
//				 + getWherePart( 'FiscalYear' )
//				 + getWherePart( 'MakeDate' );
			
	//}	 
//	alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�в�ѯ����¼��");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = ErrLogBonusGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}




// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			//alert("����"+arrReturn);
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert(arrGrid);
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = PolGrid.getRowData(tRow-1);
	
	//tRow = 10 * turnPage.pageIndex + tRow; //10����multiline������
	//������Ҫ���ص�����
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}