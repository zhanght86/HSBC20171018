//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
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

function returnParent()
{
    //top.opener.initSelfGrid();
    //top.opener.easyQueryClick();
    top.close();
}


function easyQueryClick()
{
    // ��дSQL���
    var strSQL = "";
    if( tIsCancelPolFlag == "0"){

       /*  strSQL = "select a.InsuAccNo,a.GrpContNo,a.GrpPolNo,a.PrtNo,a.RiskCode,"
                +" a.AccType,a.InvestType,a.InsuredNo,a.AppntNo,a.Owner,"
                +" (select sum(sumactupaymoney) from ljapayperson p where p.polno = a.polno),"
                +" a.sumpaym,a.LastAccBala,"
                +" a.insuaccbala ,a.InsuAccGetMoney,a.ManageCom,a.Operator,(select nvl(q.fee,'') from lcinsureaccfeetrace q where  q.polno = a.polno and q.feecode like '%01')"
                +" from LCInsureAcc a where "
                +" a.PolNo='" + tPolNo + "'";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql1");
	mySql.addSubPara(tPolNo); 
    }
    else
    if(tIsCancelPolFlag=="1"){//����������ѯ
      //strSQL = "select LBPrem.PolNo,LBPrem.PayPlanCode,LBPrem.PayPlanType,LBPrem.PayTimes,LBPrem.Mult,LBPrem.Prem,LBPrem.SumPrem,LBPrem.Rate,LBPol.AppntName,LBPol.riskcode,LBPrem.PaytoDate,LBPrem.PayIntv ,,LCPrem.managefeerate from LBPol,LBPrem where LBPrem.PolNo = '" + tPolNo + "' and LBPol.PolNo = LBPrem.PolNo";
    mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql2");
	mySql.addSubPara(tPolNo); 
    }
    else {
      alert("�������ʹ������!");
      return;
      }

    //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    PolGrid.clearData();
    alert("���ݿ���û���������������ݣ�");
    //alert("��ѯʧ�ܣ�");
    return false;
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}


/*********************************************************************
 *  ��ʾ�����˻�������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsureAccClass()
{

    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    var cPolNo = "";
    if( tSel == 0 || tSel == null )
        alert("��ѡ��һ���˻���¼���ٲ鿴�����˻���ϸ��Ϣ");
    else
    {
        cInsuAccNo = PolGrid.getRowColData( tSel - 1, 1 );
        if (cInsuAccNo== null||cInsuAccNo == "")
            return;

        divLCInsureAccClass.style.display="";
        getAccClass(cInsuAccNo);
        divLCInsureAccTrace.style.display="";
        getAccTrace(cInsuAccNo);
         queryRate();
    }
}

function showAccDetail()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
    	alert("��ѡ��һ���˻���¼���ٲ鿴�����˻���ϸ��Ϣ");
    	}     
    else
    {
        var cInsuAccNo = PolGrid.getRowColData( tSel - 1, 1 );
        if (cInsuAccNo== null||cInsuAccNo == "")
            return;
        //divLCInsureAccClass.style.display="";
        
        getAccClass(cInsuAccNo);
        
        divLCInsureAccTrace.style.display="";
        getAccTrace(cInsuAccNo);
                        

        queryRate();
    }
}

function getAccClass(cInsuAccNo)
{
    // ��дSQL���
    
   // alert('cInsuAccNo'+cInsuAccNo);

   /* strSQL = " select a.GrpContNo,a.GrpPolNo,a.ManageCom,a.PayPlanCode,a.OtherNo,"
                +" a.AccAscription,a.AccType,a.AccComputeFlag,a.BalaDate,a.BalaTime,"
                +" a.sumpay,a.sumpaym,a.LastAccBala,a.LastUnitCount,a.LastUnitPrice,"
                +" a.InsuAccBala,a.UnitCount,a.InsuAccGetMoney,nvl(a.FrozenMoney,0),a.State,"
                +" a.Operator "
                +" from LCInsureAccClass a where "
                +" a.PolNo='" + tPolNo + "' and a.InsuAccNo='"+ cInsuAccNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql3");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
    var brr = easyExecSql(mySql.getString());
    if(!brr)
    {
        PolGrid2.clearData();
        alert("���ݿ���û���������������ݣ�");
        return false;
    }
   
    turnPage2.queryModal(mySql.getString(),PolGrid2);
}

function getAccTrace(cInsuAccNo)
{
    // ��дSQL���

 /*
   var strSQL_0 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
        + " Operator,MODIFYDATE,MODIFYTIME,"
        +		 " decode(moneytype,'BF',0,( select nvl(( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno),( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = substr(a.paydate,0,8)||'01' "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) ) from dual )) ,"
        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"   
        + " From LCInsureAccTrace a where a.moneytype in ('LX', 'BF','GL','JJ') and "
        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"' "; 
//   var strSQL_1 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
//        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
//        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
//        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
//        + " Operator,MODIFYDATE,MODIFYTIME," 
//        + " ,"
//        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"   
//        + " From LCInsureAccTrace a where a.moneytype in ('GL') and "
//        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"' ";
   var strSQL_2 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
        + " Operator,MODIFYDATE,MODIFYTIME,"
        +		 " ( select nvl(( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno),( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = substr(a.paydate,0,8)||'01' "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) ) from dual ) ,"
        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"   
        + " From LCInsureAccTrace a where a.moneytype in ('TB') and "
        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"'  ";
   var strSQL_3 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
        + " Operator,MODIFYDATE,MODIFYTIME,"
        +		 " ( select nvl(( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno),( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = substr(a.paydate,0,8)||'01' "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) ) from dual ) ,"
        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"        
        + " From LCInsureAccTrace a where a.moneytype in ('TBFY') and "
        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"'  and money<0 ";
   var strSQL=strSQL_0 + " union "+ strSQL_2 + " union " +strSQL_3 +" order by MODIFYDATE,MODIFYTIME";
   //alert(strSQL);
   */
   mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql4");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
    var brr = easyExecSql(mySql.getString());
    if(!brr)
    {
        PolGrid3.clearData();
        alert("���ݿ���û���������������ݣ�");
        return false;
    }
    turnPage3.queryModal(mySql.getString(),PolGrid3);

}

function queryRate()
{
  var i = 0;
  for(i;i<PolGrid3.mulLineCount;i++)
  {
    var tPolNo = PolGrid3.getRowColData(i,4);
    var tInsurAccNo = PolGrid3.getRowColData(i,6); 
    var tRiskCode = PolGrid3.getRowColData(i,7); 
    var tPayDate = PolGrid3.getRowColData(i,15);  
    var tMoneyType = PolGrid3.getRowColData(i,12).substring(0,2); 

    if(tPayDate.substring(8,tPayDate.length)=='01') 
    {
	   // strSQL = " select nvl((select rate from lminsuaccrate where insuaccno = '"+tInsurAccNo+"' and baladate = '"+tPayDate+"' and rateintv='Y' and riskcode= '"+tRiskCode+"'),'0.00') from dual "
	    mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql5");
	mySql.addSubPara(tInsurAccNo); 
	mySql.addSubPara(tPayDate); 
	mySql.addSubPara(tRiskCode);  
	    var brr = easyExecSql(mySql.getString(), 1, 0,"","",1);//ע��˴���6������Ӧ��Ϊ1����ʹ�÷�ҳ���ܣ�������ܻ��ȫ�ֱ���turnPage����
	    if(brr)
	    {
	    	  
	         PolGrid3.setRowColData(i, 24, brr[0][0]);
	    }
	    else
	    {
	    		PolGrid3.setRowColData(i, 24, '0.025');
	    }
	 }
	//������������ͳһ��Ϊ�գ����Ƿ�ҳ������⴦��ʧЧ
	if(tMoneyType=='BF')
	{
	   PolGrid3.setRowColData(i, 24, '');
	}
  }
}
//�ύ�����水ť��Ӧ����
function printNotice()
{
  //var i = 0;
  var showStr="���ڴ�ӡ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

    
	  fm.fmtransact.value = "PRINT";
		fm.target = "f1print";
		document.getElementById("fm").submit();
		showInfo.close();
}
