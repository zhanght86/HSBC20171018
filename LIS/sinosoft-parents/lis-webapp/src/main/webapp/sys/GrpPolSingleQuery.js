var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var turnPageSingleInfoGrid = new turnPageClass();    //ȫ�ֱ���, ��ѯ�����ҳ, ������
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

function easyQueryClick()
{
	var sqlid826091225="DSHomeContSql826091225";
var mySql826091225=new SqlClass();
mySql826091225.setResourceName("sys.GrpPolSingleQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql826091225.setSqlId(sqlid826091225);//ָ��ʹ�õ�Sql��id
mySql826091225.addSubPara(tGrpContNo);//ָ������Ĳ���
mySql826091225.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql826091225.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql826091225.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
mySql826091225.addSubPara(fm.AppFlag.value);//ָ������Ĳ���
mySql826091225.addSubPara(fm.Sex.value);//ָ������Ĳ���
mySql826091225.addSubPara(fm.BirthDay.value);//ָ������Ĳ���
mySql826091225.addSubPara(fm.Name.value);//ָ������Ĳ���
var strSQL=mySql826091225.getString();
	
//	var strSQL = "select a.contno,a.InsuredNo,b.Name,a.RiskCode,a.CValidate,a.EndDate,b.BirthDay,decode(b.sex,'0','��','1','Ů','����'),a.Prem,a.Amnt,"
//						 + " decode(a.AppFlag,'0','Ͷ��δ��Ч','1','��Ч','2','����/��������δ��Ч','4',decode((select distinct '1' from LPEdorItem where edortype in ('GT','XT','ZT','CT','ES','AT','AX','WT','AZ') and ContNo = a.ContNo and edorstate = '0'),'1','�˱�ʧЧ','����ʧЧ'),'����') "
//						 //��ʾ��contno �ڲ���ѯ��polno
//						 //modify by jiaqiangli 2009-10-30
//						 + " ,a.polno " 
//						 + " from LCPol a,LCInsured b where 1=1 "
//    				 + " and a.GrpContNo='"+ tGrpContNo +"' and a.InsuredNo = b.InsuredNo and a.GrpContNo = b.GrpContNo and a.ContNo=b.ContNo "
//    				 + getWherePart('a.RiskCode', 'RiskCode')
//  				 	 + getWherePart('a.ContNo', 'ContNo')
//  				 	 + getWherePart('a.InsuredNo', 'InsuredNo')
//  				 	 + getWherePart('a.AppFlag', 'AppFlag')
//  				 	 + getWherePart('b.Sex', 'Sex')
//  				 	 + getWherePart('b.BirthDay', 'BirthDay')
//  					 + getWherePart('b.name', 'Name','like');
//
//  strSQL += " order by a.appflag,a.contno";
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = GrpPolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}
//Add By QianLy to show single people's Main Info on 2006-10-18
function querySingleInfo()
{
	var tSel = GrpPolGrid.getSelNo();
	var tContNo = GrpPolGrid.getRowColData(tSel-1,1);
	var insn = GrpPolGrid.getRowColData(tSel-1,2);
	
	var sqlid826091451="DSHomeContSql826091451";
var mySql826091451=new SqlClass();
mySql826091451.setResourceName("sys.GrpPolSingleQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql826091451.setSqlId(sqlid826091451);//ָ��ʹ�õ�Sql��id
mySql826091451.addSubPara(insn);//ָ������Ĳ���
mySql826091451.addSubPara(tContNo);//ָ������Ĳ���
var strSQL=mySql826091451.getString();
	
//	var strSQL = "select distinct insuredno,"
//	          + " name,"
//	          + " (select codename from ldcode where codetype = 'sex' and code = sex),"
//	          + " birthday,"
//	          + " (select codename from ldcode where codetype = 'idtype' and code = idtype),"
//	          + " idno,"
//            + " occupationcode,"
//            + " occupationtype,"
//            + " RgtAddress,"
//            + " (select codename from ldcode where codetype = 'marriage' and code = marriage)"
//	          + " from lcinsured"
//	          + " where 1 = 1"
//	          + " and insuredno = '"+insn+"'"
//	          + " and contno = '"+tContNo+"'";	 
    try
    {
    	  document.all("divSingleInfo").style.display= "";
        turnPageSingleInfoGrid.pageDivName = "divTurnPageSingleInfoGrid";
        turnPageSingleInfoGrid.queryModal(strSQL, SingleInfoGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ͻ���Ҫ��Ϣ�����쳣�� ");
    }
}
function getRisk(){
  var i = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	
	var sqlid826091551="DSHomeContSql826091551";
var mySql826091551=new SqlClass();
mySql826091551.setResourceName("sys.GrpPolSingleQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql826091551.setSqlId(sqlid826091551);//ָ��ʹ�õ�Sql��id
mySql826091551.addSubPara(tGrpContNo);//ָ������Ĳ���
strsql=mySql826091551.getString();
	
//	strsql = "select distinct a.RiskCode,b.riskname from LCPol a,lmrisk b where a.GrpContNo='"+ tGrpContNo +"'"
//           + " and a.riskcode=b.riskcode"
//           + " order by a.RiskCode";
	 
	//alert(strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			tCodeData = tCodeData + m+ "^"  + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];	
		}
	}
 
  return tCodeData;
}    

  function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "�����ڸ��˱�����Ϣ��ѡ��һ����¼." );
	else
	{
		//��ʾ��contno �ڲ���ѯ��polno
		//modify by jiaqiangli 2009-10-30
		var cPolNo = GrpPolGrid.getRowColData( tSel - 1, 12 );
		if(cPolNo!="")
		{   try
		    {
		    	 var strUrl="./GpsaAccQueryMain.jsp?PolNo="+ cPolNo;
			     window.open(strUrl,"",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
		  
		    }
		    catch(ex)
		    {
			    alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		    }

    }
   else{ alert( "���Ȳ�ѯ���˱�����Ϣ." ); return;}

	}
	
}  

function GoBack(){
	top.opener.window.focus();
	
	top.window.close();
}




function  LCInsureAccFeeQuery(){
	
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "�����ڸ��˱�����Ϣ��ѡ��һ����¼." );
	else
	{
		var cPolNo = GrpPolGrid.getRowColData( tSel - 1, 1 );
		if(cPolNo!="")
		{   try
		    {
			     window.open("./GpsLCInsureAccFeeQuery.jsp?PolNo="+ cPolNo,"",sFeatures);
		  
		    }
		    catch(ex)
		    {
			    alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		    }

    }
   else{ alert( "���Ȳ�ѯ���˱�����Ϣ." ); return;}

	}
	
	
	
	
}