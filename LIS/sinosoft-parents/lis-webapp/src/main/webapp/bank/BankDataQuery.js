//
//�������ƣ�BankDataQuery.js
//�����ܣ����������ݲ�ѯ
//�������ڣ�2004-10-20
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

//��ѯ��������
function easyQuery()
{
	if(document.all('PolNo').value=="")
	{
		if(document.all('StartDate').value=="")
		{
			alert("��ѡ��ʼʱ��");
			return false;
			}
		
		if(document.all('EndDate').value=="")
		{
			alert("��ѡ����ֹʱ��");
			return false;
			}
	}
	if(document.all('ManageCom').value=="")
	{
		alert("��ѡ��������");
		return false;
		}
	// ��дSQL���
	var strSQL = "";
	if(_DBT==_DBO){
//		strSQL = "select otherno,riskcode,accname,"
//			 + "(select trim(insuredname) from lcpol where contno = a.otherno and mainpolno=polno and rownum=1),startpaydate,"
//			 + "(select count(1) from lysendtobank where paycode = a.getnoticeno)+(select count(1) from lysendtobankb where paycode = a.getnoticeno),getnoticeno "
//			 + "from ljspay a "
//			 + "where othernotype = '2' and bankaccno is not null "
//			 + getWherePart('otherno','PolNo')
//			 + getWherePart('managecom','ManageCom','like')
//			 + getWherePart('startpaydate','StartDate','>=')
//			 + getWherePart('startpaydate','EndDate','<=')
//			 + " order by 1";
		
		var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value; 
		var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value; 
		var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value; 
		var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value; 
		 
		  var sqlid1="BankDataQuerySql1";
		  var mySql1=new SqlClass();
		  mySql1.setResourceName("bank.BankDataQuerySql");
		  mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
		  mySql1.addSubPara(PolNo);//ָ���������
		  mySql1.addSubPara(ManageCom);//ָ���������
		  mySql1.addSubPara(StartDate);//ָ���������
		  mySql1.addSubPara(EndDate);//ָ���������
		  var strSQL = mySql1.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select otherno,riskcode,accname,"
//			 + "(select trim(insuredname) from lcpol where contno = a.otherno and mainpolno=polno limit 0,1),startpaydate,"
//			 + "(select count(1) from lysendtobank where paycode = a.getnoticeno)+(select count(1) from lysendtobankb where paycode = a.getnoticeno),getnoticeno "
//			 + "from ljspay a "
//			 + "where othernotype = '2' and bankaccno is not null "
//			 + getWherePart('otherno','PolNo')
//			 + getWherePart('managecom','ManageCom','like')
//			 + getWherePart('startpaydate','StartDate','>=')
//			 + getWherePart('startpaydate','EndDate','<=')
//			 + " order by 1";
		
		var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value; 
		var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value; 
		var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value; 
		var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value; 
		 
		  var sqlid2="BankDataQuerySql2";
		  var mySql2=new SqlClass();
		  mySql2.setResourceName("bank.BankDataQuerySql");
		  mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
		  mySql2.addSubPara(PolNo);//ָ���������
		  mySql2.addSubPara(ManageCom);//ָ���������
		  mySql2.addSubPara(StartDate);//ָ���������
		  mySql2.addSubPara(EndDate);//ָ���������
		  var strSQL = mySql2.getString();
		
	}
	
  
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ���ȷ�ϣ�\n�ٸñ����ɷ��ѳɹ���\n�ڸñ���Ϊ������ת�˼���\n�۸ñ���δ�������ڣ�\n�ܸú˶Ա����ţ�");
    return false;
  }
  
  fm.bnteasyQuery.disabled = true;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CodeGrid;    
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           		 = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function easyPrint()
{
	fm.bntPrint.disabled=true;
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function QueryDetail()
{
	var tSel = CodeGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	else
	{
		fm.bntQueryDetail.disabled=true;
		var tGetNoticeNo = CodeGrid.getRowColData(tSel-1,7);
		var tPolNo = CodeGrid.getRowColData(tSel-1,1);
		window.open("./BankDataQueryDetailMain.jsp?GetNoticeNo=" + tGetNoticeNo + "&PolNo=" + tPolNo);
	}	
}

