var showInfo;
var turnPage = new turnPageClass(); 

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initBonusGrid();
	
	// ��дSQL���
	var strSQL = "select bussno,otherno,content,operator,makedate "
						 + "from lsrundatainfo "
						 + "where busstype = 'FiscalLX' "
						 + getWherePart('standbyflag1','FiscalYear')
						 + getWherePart('bussno','GrpPolNo')
						 + getWherePart('otherno','PolNo')
						 + getWherePart('makedate','MakeDateB','>=','0')
						 + getWherePart('makedate','MakeDateE','<=','0')
						 + " order by bussno,otherno "
						 ;
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
	turnPage.queryModal(strSQL, BonusGrid);    
}


