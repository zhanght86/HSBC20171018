//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardPlanInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
  if (fm.ManageCom.value==null || fm.ManageCom.value=='')
  {
    alert("���������������");
    return false;
  }

  initCardPlanQueryGrid();
  initCardPlanDetailGrid();
  /*var strSQL = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 		+" where 1=1 "
 		+ getWherePart('a.appcom', 'ManageCom', 'like')
 		+ getWherePart('length(a.appcom)', 'Grade')
 		//+ getWherePart('a.PlanID', 'PlanID')
 		+ getWherePart('a.PlanType', 'PlanType')
 		+ getWherePart('a.PlanState', 'PlanState')
 		+ getWherePart('a.MakeDate', 'MakeDateB','>=')
 		+ getWherePart('a.MakeDate', 'MakeDateE','<=')
 		+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 		+ getWherePart('certifyclass', 'CertifyClass') 
 		+ getWherePart('CertifyClass2', 'CertifyClass2') + ")"  
 		+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
    var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ManageCom').value,document.all('Grade').value,document.all('PlanType').value
                                       ,document.all('PlanState').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                       ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardPlanQueryGrid);
   	//alert("��ѯ���ļ�¼������"+CardPlanQueryGrid.mulLineCount);
   	if(CardPlanQueryGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}

function showDetail(parm1, parm2)
{
  var tManageCom = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 1);
  var tPlanType = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 2);

  /*var strSql = "select a.planid, a.appcom, a.plantype,a.certifycode,"
      	+" (select certifyname from lmcertifydes where certifycode=a.certifycode), (select certifyprice from lmcertifydes where certifycode=a.certifycode) price,"
      	+" a.appcount, a.concount, a.retcount, assigncount, a.retcount - assigncount,(select certifyprice*decode( (a.retcount - assigncount),0,decode( (a.retcount),0,decode( (a.concount),0,a.appcount,a.concount),a.retcount),(a.retcount - assigncount))  from lmcertifydes  where certifycode = a.certifycode), "
      	+" (case a.planstate when 'A' then '����״̬'when 'C' then '�ύ״̬'when 'S' then '�����ύ״̬'when 'R' then '����״̬' end), "
      	+" a.MakeDate, a.UpdateDate "
    	+" from lzcardplan a where 1=1 and a.appcom = '" + tManageCom
    	+"' and a.plantype ='" + tPlanType + "'"   
    	+ getWherePart('a.appcom', 'ManageCom', 'like')
    	+ getWherePart('length(a.appcom)', 'Grade')
 		//+ getWherePart('a.PlanID', 'PlanID')
 		+ getWherePart('a.PlanType', 'PlanType')
 		+ getWherePart('a.PlanState', 'PlanState') 
 		+ getWherePart('a.MakeDate', 'MakeDateB','>=')
 		+ getWherePart('a.MakeDate', 'MakeDateE','<=') 	
 		+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 		+ getWherePart('certifyclass', 'CertifyClass') 
 		+ getWherePart('CertifyClass2', 'CertifyClass2') + ")"  
    	+" order by a.certifycode,a.planstate";*/
  var strSql = wrapSql(tResourceName,"querysqldes2",[tManageCom,tPlanType,document.all('ManageCom').value,document.all('Grade').value,document.all('PlanType').value
                                       ,document.all('PlanState').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                       ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
  //prompt("strSql",strSql);
  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage2.strQueryResult) {
 	 alert("δ��ѯ����֤�ƻ���ϸ��Ϣ��");
     return false;
    }else{
  	  turnPage2.pageDivName = "CardPlanDetailGrid";
  	  turnPage2.queryModal(strSql, CardPlanDetailGrid);
	} 
}

//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(CardPlanDetailGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage2.queryAllRecordCount>100000)
   	{
   		alert("һ�δ�ӡ�����ݳ���100000��,�뾫ȷ��ѯ������");
   		return false;
   	}
   	
	easyQueryPrint(2,'CardPlanDetailGrid','turnPage2');	
}

