//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

function download()
{
	if (verifyInput() == false)
    return false;
  var i = 0;
  var showStr=""+"���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
	fm.target = "f1print";
	fm.all('op').value = 'download';
	fm.submit();
	showInfo.close();
}

function queryClick()
{
	// ��дSQL���
	
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRGetBsnsDataInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRGetBsnsDataInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(fm.all("StartDate").value);//ָ������Ĳ���
	    mySql100.addSubPara(fm.all("EndDate").value);//ָ������Ĳ���
	var sqlStr=mySql100.getString();
	
	/**
	var sqlStr="select (case when a.conttype='1' then a.contno else a.grpcontno end),"
	+ " b.dutycode,a.insuredno,a.cvalidate,(select max(IDNo) from lcinsured where insuredno=a.insuredno),InsuredAppAge,"
	+ " insuredsex,a.occupationtype,0,a.InsuredBirthday,a.riskcode,b.amnt,b.prem ,"
	+ " (case when conttype='1' then (select last_year_reser01 from ReportActu_temp  where i_info_cntr_no=a.contno and pol_code=a.riskcode) "
	+ " Else (select last_year_reser01 from ReportActu_temp  where ipsn_no=a.contno and pol_code=a.riskcode) end),"
	+ " (case when appflag='1' then '��Ч' else '��Ч' end),a.signdate from lcpol a,lcduty b "
	+ " where a.polno=b.polno and appflag in ('1','4') and a.signdate between '"+fm.all("StartDate").value+"' and '"+fm.all("EndDate").value+"' "
	//+ " and riskcode in ()" 
	+ " order by signdate "
	;
	//alert(sqlStr);
	*/
	turnPage.queryModal(sqlStr,BusynessGrid);
}

/**********************************/
/*�ս�ȷ���嵥�������ݵ���
/**********************************/
function exportExcel()
{
	fm.target= "fraSubmit";
	fm.action="./LRBsnzDataExportSave.jsp";
	fm.submit(); //�ύ
  
}

function afterCodeSelect( cCodeName, Field )
{
}
