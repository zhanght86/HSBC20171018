var showInfo;
var turnPage = new turnPageClass(); 

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initBonusGrid();
	
	// 书写SQL语句
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
	
	//查询SQL，返回结果字符串
	turnPage.queryModal(strSQL, BonusGrid);    
}


