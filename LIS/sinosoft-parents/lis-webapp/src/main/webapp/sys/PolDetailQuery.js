
//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tDisplay;
var arrDataSet;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


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

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����SQL������������պ͸��գ�����ʾ����״̬�ͱ���ֹ��
    �� �� �ˣ������
    �޸����ڣ�2005.10.25
   =========================================================================
**/

function easyQueryClick()
{
	// ��дSQL���
	var strSQL = "";
	if(tIsCancelPolFlag=="0"){
	
var sqlid43="ProposalQuerySql43";
var mySql43=new SqlClass();
mySql43.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
mySql43.addSubPara(tContNo );//ָ������Ĳ���
strSQL=mySql43.getString();
		
//	 strSQL =   "(select a.grppolno,a.polno,a.prtno,a.appntname,a.insuredname,a.riskcode,b.riskshortname,a.signcom,"+
//	            "(case a.appflag when '1' then '�б�' when '4' then '��ֹ' end) as appflag,"+
//	            "a.amnt,a.cvalidate,a.enddate,a.payenddate,a.agentcode,a.uwflag, "+
//	            "(select codename from ldcode where codetype = 'payintv' and code = a.payintv),"+
//	            "a.paytodate,substr(a.polstate, 1, 4),a.payyears,a.prem,"+
//	            "(select nvl(sum(prem), 0) from lcprem where polno = a.polno and payplantype = '0' and payenddate = a.payenddate and length(dutycode)=6),"+
//	            "(select nvl(sum(prem), 0) from lcprem where polno = a.polno and payplantype in ('01', '03') and payenddate = a.payenddate ),"+ 
//	            "(select nvl(sum(prem), 0) from lcprem where polno = a.polno and payplantype in ('02', '04') and payenddate = a.payenddate ),"+
//	            "a.insuredappage "+
//	            "from lcpol a, lmrisk b where a.riskcode = b.riskcode  "+
//	            "and a.contno = '"+tContNo+"' and a.appflag in ('1','4','0'))"+//09-05-31  ����appflag=0������
//	            "order by a.appflag asc, a.cvalidate desc, a.riskcode asc ";
	}

	else
	 if(tIsCancelPolFlag=="1"){//����������ѯ
	 
	 var sqlid44="ProposalQuerySql44";
var mySql44=new SqlClass();
mySql44.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
mySql44.addSubPara(tContNo );//ָ������Ĳ���
strSQL=mySql44.getString();
	 
	   //strSQL = "select LBPol.grppolno,LBPol.PolNo,LBPol.PrtNo,LBPol.AppntName,LBPol.InsuredName,LBPol.RiskCode,LBPol.ManageCom,LBPol.Prem ,LBPol.Amnt ,LBPol.CValiDate,LBpol.AgentCode,uwflag,substr(LBPol.PolState,1,4) from LBPol where LBPol.MainPolNo='" + tPolNo + "' and appflag<>'9' order byLBPol.proposalno";
//	   alert("����������ѯ"+strSQL);
	}
	else
	{
	  alert("�������ʹ������!");
	  return ;
	}
	//��ѯSQL�����ؽ���ַ���
//	prompt("",strSQL);
	turnPage1.pageLineNum = 5;
	turnPage1.queryModal(strSQL, PolGrid);

   //��ѯ��������Ϣ����
   initInsuredGrid();
   var strSQL1 = "";
   var arr=new Array;
   
var sqlid45="ProposalQuerySql45";
var mySql45=new SqlClass();
mySql45.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
mySql45.addSubPara(tContNo );//ָ������Ĳ���
strSQL1=mySql45.getString();
   
//   strSQL1 = " select name,insuredno,(select codename from ldcode where codetype='sex' and code=sex),(select codename from ldcode where codetype='idtype' and code=idtype),idno,(select codename from ldcode where codetype='nativeplace' and code=nativeplace),(select codename from ldcode where codetype='occupationtype' and code=occupationtype),birthday from lcinsured  where 1=1	"
//	           +" and contno='"+ tContNo +"' "
//	           +" order by contno";


	 arr = easyExecSql(strSQL1);
    if (arr)
    {
        displayMultiline(arr,InsuredGrid);
    }
    else
    {
        initInsuredGrid();
    }
}


//��ѯ��ͬ��Ϣ��Ͷ������Ϣ
function initContAndAppnt()
{
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();

		try{
			
var sqlid46="ProposalQuerySql46";
var mySql46=new SqlClass();
mySql46.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
mySql46.addSubPara(tContNo );//ָ������Ĳ���
var strSQL=mySql46.getString();
			
			//var strSQL = "select a.grpcontno,a.contno ,a.prtno,a.managecom,a.salechnl,a.agentcode,a.agentgroup,a.agentcom,a.insuredno,a.paymode,a.poltype,a.signcom,a.customgetpoldate ,(select name from ldcom where comcode = a.managecom)||(select b.name from labranchgroup b where b.agentgroup = (select d.agentgroup from laagent d where d.agentcode = a.agentcode)) from lccont a where  a.contno= '"+tContNo+"'";
			arrReturn1 = easyExecSql(strSQL);
			if (arrReturn1 == null) {
			  alert("δ�鵽��ͬ��Ϣ");
			} else {
			   displayCont(arrReturn1[0]);
			}
			
var sqlid47="ProposalQuerySql47";
var mySql47=new SqlClass();
mySql47.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
mySql47.addSubPara(tContNo );//ָ������Ĳ���
var strSQL47=mySql47.getString();
			
			arrReturn2 =easyExecSql(strSQL47);
			//arrReturn2 =easyExecSql("select * from LCAppnt where ContNo= '" + tContNo + "'");
			if (arrReturn2 == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			  displayAppnt(arrReturn2[0]);
			}
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼��");
		}
}

 //��ͬ��ʾ
function displayCont(cArr)
  {
  	try { document.all('ContNo').value = cArr[1]; } catch(ex) { };
  	try { document.all('PrtNo').value = cArr[2]; } catch(ex) { };
  	try { document.all('ManageCom').value = cArr[3]; showOneCodeName('station','ManageCom','ManageComName'); } catch(ex) { };
  	try { document.all('SaleChnl').value = cArr[4]; showOneCodeName('salechnl','SaleChnl','SaleChnlName');} catch(ex) { };
  	try { document.all('AgentCode').value = cArr[5]; } catch(ex) { };
  	try { document.all('AgentGroup').value = cArr[6]; } catch(ex) { };
  	//try { document.all('AgentCom').value = cArr[7]; showOneCodeName('AgentCom','AgentCom','AgentComName');} catch(ex) { };
    try {
    	   document.all('AgentCom').value = cArr[7];
    	   if(document.all('AgentCom').value!=""&&document.all('AgentCom').value!=null)
    	   {
		   	
var sqlid48="ProposalQuerySql48";
var mySql48=new SqlClass();
mySql48.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
mySql48.addSubPara(document.all('AgentCom').value );//ָ������Ĳ���
var strSQL=mySql48.getString();
			
    	   //	var strSQL="select name from lacom where agentcom='"+document.all('AgentCom').value+"'";
    	   	var arrResult=easyExecSql(strSQL);
    	   	if(arrResult!=null)
    	   	{
    	   		document.all('AgentComName').value=arrResult[0];
    	   	}
    	   }
    	  } catch(ex) { };
  	try { document.all('InsuredNo').value = cArr[8]; } catch(ex) { };
  	try {
  		if(cArr[9] == 'A') document.all('PayMode').value = '��';
  	else
  		document.all('PayMode').value = '��';
  		/*
  		 switch(cArr[9]){
  		      case '1': document.all('PayMode').value = '�ֽ�' ;   break;
  		      case '2': document.all('PayMode').value = '�ֽ�֧Ʊ';break;
  		      case '3': document.all('PayMode').value = 'ת��֧Ʊ';break;
  		      case '4': document.all('PayMode').value = '����ת��';break;
  		      case '5': document.all('PayMode').value = '�ڲ�ת��';break;
  		      case '6': document.all('PayMode').value = '��������';break;
  		      case '7': document.all('PayMode').value = '����';    break;
  		      defeat: document.all('PayMode').value = '����';

  	   }*/
  	} catch(ex) { };
  	try { document.all('PolType').value = cArr[10]; } catch(ex) { };
    try { document.all('SignCom').value = cArr[11];showOneCodeName('comcode','SignCom','SignComName');} catch(ex) { };
  	try { document.all('CustomGetPolDate').value = cArr[12]; } catch(ex) { };
  	try { document.all('Name').value = cArr[13];}catch(ex){};
   }

//����Ͷ������Ϣ
function displayAppnt(cArr)
   {
    try { document.all('AppntNo').value = cArr[3]; } catch(ex) { };
  	try { document.all('AppntName').value = cArr[5]; } catch(ex) { };
  	try { document.all('AppntSex').value = cArr[6]; showOneCodeName('sex','AppntSex','AppntSexName');} catch(ex) { };
  	try { document.all('AppntBirthday').value = cArr[7]; } catch(ex) { };
  	try { document.all('AppntIDType').value = cArr[10]; showOneCodeName('IDType','AppntIDType','AppntIDTypeName');} catch(ex) { };
  	try { document.all('AppntIDNo').value = cArr[11]; } catch(ex) { };
  	try { document.all('WorkType').value = cArr[29]; showOneCodeName('occupationtype','WorkType','WorkTypeName');} catch(ex) { };
  	try { document.all('NativePlace').value = cArr[12]; showOneCodeName('NativePlace','NativePlace','NativePlaceName');} catch(ex) { };
//=================add=========liuxiaosong=============2006-11-21==================start========================
  //vip�ͻ���Ϣ
    try { 
    	var appntno=document.all('AppntNo').value;
    	//arrResult = easyExecSql("select vipvalue from ldperson where customerno='"+appntno+"'");
    	var tResourceName="sys.ProposalQuerySql";
		var tSQL = wrapSql(tResourceName,"querysqldes1",[appntno]);
		arrResult = easyExecSql(tSQL);
    	fm.VIPType.value=arrResult[0][0];
    	showOneCodeName('vipvalue','VIPType','VIPTypeName');
    }
    catch(ex) { };   
   }


// ������ϸ��ѯ �� ���ݷ��ظ�����
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
//	var tSel = 1;
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
		parent.VD.gVSwitch.deleteVar("PolNo");
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);

		if (cPolNo == "")
		    return;

		var GrpPolNo = PolGrid.getRowColData(tSel-1,1);
        var prtNo = PolGrid.getRowColData(tSel-1,3); 
        var ContNo = document.all('ContNo').value;
        //alert("dfdf");

  var strSql="";
  //strSql="select case lmriskapp.riskprop"
  //      +" when 'I' then"
  //      +" '1'"
  //      +" when 'G' then"
  //      +" '2'"
  //      +" when 'Y' then"
  //      +" '3'"
  //      + " when 'T' then"
  //      + " '5'"
  //      +" end"
  //      +" from lmriskapp"
  //      +" where riskcode in (select riskcode"
  //      +" from lcpol"
  //      +" where polno = mainpolno"
  //      +" and contno = '"+ContNo+"')"
  //arrResult = easyExecSql(strSql);
  //var BankFlag = arrResult[0][0];
  
  //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
    var BankFlag = "";
    var tSplitPrtNo = prtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
  //strSql="select salechnl ,cardflag from lccont where contno='"+ContNo+"'";
  //arrResult = easyExecSql(strSql);
  //var CardFlag = arrResult[0][1];
  //if(BankFlag==""||BankFlag==null||CardFlag=="3")
  //BankFlag="4";
  //alert("BankFlag="+BankFlag);//return false;
        if( tIsCancelPolFlag == "0"){
	    	if (GrpPolNo =="00000000000000000000") {
	    		//alert(BankFlag);
	    	 	window.open("./AllProQueryMain.jsp?LoadFlag=6&prtNo="+prtNo+"&ContNo="+ContNo+"&BankFlag="+BankFlag,"window1",sFeatures);
		    } else {
			window.open("./AllProQueryMain.jsp?LoadFlag=4","',sFeatures");
		    }
		} else {
		if( tIsCancelPolFlag == "1"){//����������ѯ
			if (GrpPolNo =="00000000000000000000")   {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1",sFeatures);
			} else {
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7","",sFeatures);
			}
	    } else {
	    	alert("�������ʹ������!");
	    	return ;
	    }
	 }
   }
}

// ���ݷ��ظ�����
function returnParent()
{
	//alert(tDisplay);
	if (tDisplay=="1")
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
	    		top.opener.afterQuery( arrReturn );
	    		top.close();
	    	}
	    	catch(ex)
	    	{
	    		alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
	    	}
	    }
	 }
	 else
	 {
	    top.close();
	 }
}

// ���Ѳ�Ѱ
function FeeQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);


		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  var cContNo = fm.ContNo.value;
		  window.open("../sys/RelFeeQueryMain.jsp?ContNo="+cContNo+"&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}


// �������Ѱ
function GetItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
      var cContNo = document.all('ContNo').value;

		if (cContNo== "" ||cPolNo == "")
		    return;
		  window.open("../sys/GetItemQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
	}
}

// �ݽ��Ѳ�ѯ
function TempFeeQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    //var cPolNo = PolGrid.getRowColData(tSel - 1,2);
	      var cContNo = document.all('ContNo').value;

		if (cContNo == "")
		    return;
		  window.open("../sys/PolTempFeeQueryMain.jsp?ContNo=" + cContNo  + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);
	}
}

// �������ѯ
function PremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        var cContNo = document.all('ContNo').value;

		if (cContNo == ""||cPolNo == "")
		    return;
		  window.open("../sys/PremQueryMain.jsp?ContNo=" + tContNo+ "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

// �ʻ���ѯ
function InsuredAccQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);


		if (cPolNo == "")
		    return;
		  window.open("../sys/InsureAccQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

// ������Ѱ
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelGetQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

//������ȡ��ѯ
function LiveGetQuery()
{
	//alert('���ڿ�������');
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
		var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		//<!-- XinYQ modifoed on 2006-02-22 : BGN -->
        var sOpenWinURL = "LiveGetQueryMain.jsp";
        var sParameters = "ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "��ȫ������ȡ��ѯ", "left");
		//<!-- XinYQ modifoed on 2006-02-22 : END -->
	}
}

//<!-- XinYQ added on 2006-03-01 : BGN -->
/*============================================================================*/

/*
 * ���մ�����ѯ
 */
function ProxyIncomePayQuery()
{
    var sOtherNo;
    try
    {
        sOtherNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex)
	{
		alert( "�Բ���, �޷���ȡ������ͬ������Ϣ��" );
		return;
	}
	if (sOtherNo != null && sOtherNo != "")
	{
        var sOpenWinURL = "ProxyIncomePayQueryMain.jsp";
        var sParameters = "OtherNo=" + sOtherNo;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "��ȫ���մ�����ѯ", "left");
	}
}

/*============================================================================*/

/*
 * ������ӡ��ѯ
 */
function ReissuePrintQuery()
{
    var sContNo;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex) { return; }
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("���棺�޷���ȡ������ͬ���롣��ѯ������ӡ��Ϣʧ�ܣ� ");
        return;
    }
    else
    {
        var sOpenWinURL = "ReissuePrintQueryMain.jsp";
        var sParameters = "EdorAcceptNo=null&ContNo=" + sContNo;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "������ӡ��ѯ", "left");
    }
}

/*============================================================================*/

/*
 * ��������Ϣ��ѯ
 */
function AgentQuery()
{
    OpenWindowNew("../bq/EdorAgentQueryMain.jsp", "��������Ϣ��ѯ", "left");
}

/*============================================================================*/
//<!-- XinYQ added on 2006-03-01 : END -->



//���Ĳ��˷Ѳ�ѯ
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName  + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
	}
}

function LCInsuAccQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		show( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
	    var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
        var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		OpenWindowNew("../sys/LCInsuAccQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"window017","left");
	}
}

// �潻/����Ѳ�ѯ
function LoLoanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;
           // alert("cPolNo"+cPolNo);
		  window.open("../sys/LoLoanQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

//��ȫ��ѯ
function PerEdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;

		  window.open("../sys/AllPBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);
	}
}


//��ȫ��ѯ
function BqEdorQueryClick()
{
    window.open("../sys/PolBqEdorMain.jsp?ContNo=" + tContNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
}


//���������ѯ
function BonusQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
		  window.open("../sys/BonusQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
	}
}

//�����ձ�������
function OmniQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
	    var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
        var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		window.open("../sys/OmniAccQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}

}

//===================add=============liuxiaosong=========2006-10-19============start===================
/**
 *���������޸Ĺ켣��ѯ
 */
function PwdChangeTrackQuery()
{
 	var ContNo = document.all('ContNo').value;
	window.open("../sys/PwdChangeTrackQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}
//===================add=============liuxiaosong=========2006-10-19============end=====================


//Ƿ�����Ѳ�ѯ
function PayPremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	  var cPolNo = PolGrid.getRowColData(tSel - 1,2);
    var cContNo = document.all('ContNo').value;

		if (cContNo == ""||cPolNo == "")
		    return;
		  window.open("../sys/PayPremQueryMain.jsp?ContNo=" + tContNo+ "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}


}


// ���ղ�ѯ
function MainRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);

		if (cPolNo==cMainPolNo)
			alert("��ѡ�����һ���������ݣ�����ѡ��һ�����������ݺ��ٵ�����ղ�ѯ��ť��")
		else
			{

				if (cMainPolNo == "")
				    return;

				  	initPolGrid();

						// ��дSQL���
						var mSQL = "";

						var sqlid49="ProposalQuerySql49";
						var mySql49=new SqlClass();
						mySql49.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
						mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
						mySql49.addSubPara(cMainPolNo );//ָ������Ĳ���
						mSQL=mySql49.getString();


//					    mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";

						//��ѯSQL�����ؽ���ַ���
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);

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
					  turnPage.strQuerySql     = mSQL;

					  //���ò�ѯ��ʼλ��
					  turnPage.pageIndex = 0;

					  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

					  //����MULTILINE������ʾ��ѯ���
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}
	}
}



//�����ղ�ѯ
function OddRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);

		if (cPolNo!=cMainPolNo)
			alert("��ѡ�����һ�����������ݣ�����ѡ��һ���������ݺ��ٵ�������ղ�ѯ��ť��")
		else
			{

				if (cPolNo == "")
				    return;

				  	initPolGrid();

						// ��дSQL���
						var mSQL = "";


						var sqlid50="ProposalQuerySql50";
						var mySql50=new SqlClass();
						mySql50.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
						mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
						mySql50.addSubPara(cMainPolNo );//ָ������Ĳ���
						mySql50.addSubPara(cPolNo );//ָ������Ĳ���
						mSQL=mySql50.getString();

//						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";

						//��ѯSQL�����ؽ���ַ���
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);

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
					  turnPage.strQuerySql     = mSQL;

					  //���ò�ѯ��ʼλ��
					  turnPage.pageIndex = 0;

					  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

					  //����MULTILINE������ʾ��ѯ���
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}
	}
}

// ������ϸ��ѯ
function PolClick1()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	 var cPrtNo = PolGrid.getRowColData(tSel - 1,3);
	 var cGrpContNo=PolGrid.getRowColData(tSel - 1,1);
   if (cPrtNo == "")
	 return;
   if(cGrpContNo=="00000000000000000000")
      window.open("../app/ContInputNoScanMain.jsp?prtNo="+ cPrtNo+"&LoadFlag=6","",sFeatures);
   else
	    window.open("../app/GroupPolApproveInfo.jsp?polNo="+ cGrpContNo+"&LoadFlag=16","",sFeatures);
	}
}


// ���������ѯ Modify by zhaorx 2005-03-06
//function ClaimGetQuery()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼��" );
//	else
//	{
//	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
//		if (cPolNo == "")
//		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);
//	}
//}


//��ע��Ϣ��ѯ
function ShowRemark()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel-1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/FrameRemarkMain.jsp?PolNo=" + cPolNo,"",sFeatures);
	}
}


//ɨ�����ѯ
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);


		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	}
}

//���屣��ɨ�����ѯ
function ScanQuery2()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);


		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	}
}

function GoBack(){

	//top.opener.easyQueryClick();
	top.close();

	}


function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
  }
}


function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
    if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������
	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}


function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������
	
	var sqlid51="ProposalQuerySql51";
	var mySql51=new SqlClass();
	mySql51.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
	mySql51.addSubPara(cAgentCode );//ָ������Ĳ���
	mySql51.addSubPara(document.all('ManageCom').value );//ָ������Ĳ���
	var strSql =mySql51.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
     alert("dddddddddd"+arrResult);
	}
}


//��������״̬-------------added by guanwei
function queryPolType()
{
	var cPolType = document.all('PolType').value;
//	var strSql = "select PolType from LACommisionDetail where AgentCode='" + document.all('AgentCode').value +"' and GrpContNo = '"+document.all('ContNo').value+"'";

	var sqlid52="ProposalQuerySql52";
	var mySql52=new SqlClass();
	mySql52.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
	mySql52.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
	mySql52.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
	var strSql =mySql52.getString();

//	var strSql = "    select 1 from lrascription where  contno='"+document.all('ContNo').value+"'    union    select 1 from lrascriptionb where  contno='"+document.all('ContNo').value+"'"
  var arrResult = easyExecSql(strSql);
    if (arrResult == "1")
    {
    document.all('PolType').value = "�¶�����";
    }
    else
    {
    document.all('PolType').value = "��������";
    }
}

//����ǩ������
function querySignDate()
{
	var tSignDate = document.all('SignDate').value;
	
		var sqlid53="ProposalQuerySql53";
	var mySql53=new SqlClass();
	mySql53.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
	mySql53.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
	var strSql =mySql53.getString();
	
//	var strSql = "select SignDate,LostTimes from LCCont where  ContNo = '"+document.all('ContNo').value+"'";
  var arrResult = easyExecSql(strSql);
  if(arrResult)
  {
	  document.all('SignDate').value = arrResult[0][0];
	  document.all('PrintTimes').value = arrResult[0][1]+"��";
  //alert ('querySignDate============='+arrResult);
  }
}

//������ӡ����
function queryMakeDate()
{
	var tMakeDate = document.all('MakeDate').value;
	
			var sqlid54="ProposalQuerySql54";
	var mySql54=new SqlClass();
	mySql54.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
	mySql54.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
	var strSql =mySql54.getString();
	
	//var strSql = "select max(MakeDate) from lccontprint where  ContNo = '"+document.all('ContNo').value+"'";
  var arrResult = easyExecSql(strSql);
  if(arrResult == ""||arrResult == null||arrResult =="null"  )
  {
   document.all('MakeDate').value = "δ��ñ�����ӡ����";
  }
  else
  {
  document.all('MakeDate').value = arrResult;
  }
}

//�������ѯ
function ShowQuest()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/QuestQueryMain.jsp?ContNo="+ContNo+"&Flag=5","",sFeatures);
}


//�˱���ѯ
function UWQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/UWQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}


//����������ѯ
function OperationQuery()
{
	var PrtNo = document.all('PrtNo').value;
	var ContNo = document.all('ContNo').value;
	
	var sqlid55="ProposalQuerySql55";
	var mySql55=new SqlClass();
	mySql55.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql55.setSqlId(sqlid55);//ָ��ʹ�õ�Sql��id
	mySql55.addSubPara(ContNo );//ָ������Ĳ���
	var strSql55 =mySql55.getString();
	
	var ContType=easyExecSql(strSql55,1,0,1)[0][0]
	//var ContType=easyExecSql("select conttype from lccont where contno='"+ContNo+"'",1,0,1)[0][0]

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../sys/RecordQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&ContType="+ContType,"",sFeatures);
}

//�������ѯ
function HealthQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/UWManuHealthQMain.jsp?ContNo="+ContNo+"&Flag=2","",sFeatures);
}


//���������ѯ
function MeetQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/RReportQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+ContNo+"&Flag=1","",sFeatures);
}

////�����˼���������ϲ�ѯ
//function InsuredHealthQuery()
//{
//	var ContNo = document.all('PrtNo').value;
//	var InsuredNo = document.all('InsuredNo').value;
//
//	if (ContNo == "")
//	{
//		    alert("û�еõ�Ͷ��������Ϣ��");
//		    return;
//	}
//	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+ContNo+"&CustomerNo="+InsuredNo+"&type=1");
//}

////Ͷ���˼���������֪��ѯ
//function ImpartQuery()
//{
//	var ContNo = document.all('PrtNo').value;
//	var AppntNo = document.all('AppntNo').value;
//
//	if (ContNo == "")
//	{
//		    alert("û�еõ�Ͷ��������Ϣ��");
//		    return;
//	}
//	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+AppntNo);
//}
//
////�����˼���������֪��ѯ
//function InsuredImpartQuery()
//{
//	var ContNo = document.all('PrtNo').value;
//	var InsuredNo = document.all('InsuredNo').value;
//
//	if (ContNo == "")
//	{
//		    alert("û�еõ�Ͷ��������Ϣ��");
//		    return;
//	}
//	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+InsuredNo);
//}

//Ͷ���˱����ۼƲ�ѯ
function��AmntAccumulateQuery()
{
	var ContNo = document.all('PrtNo').value;
	var AppntNo = document.all('AppntNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+AppntNo,"",sFeatures);
}
//�����˱����ۼƲ�ѯ
function InsuredAmntAccumulateQuery()
{
	var ContNo = document.all('PrtNo').value;
	var InsuredNo = document.all('InsuredNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);
}

//Ӱ�����ϲ�ѯ
function ImageQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}

//�Ժ���ʾ��Ϣ��ѯ
function  UWErrQuery()
{
	var ContNo = document.all('ContNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/UWErrMain.jsp?ContNo="+ContNo,"",sFeatures);
}


//�ٱ��ظ���ѯ
function UpReportQuery()
{
	var ContNo = document.all('ContNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/UWManuUpReportReply.jsp?ContNo="+ContNo+"&Flag=1","",sFeatures);
}


//�˱�֪ͨ���ѯ
function  UWNoticQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../sys/UWNoticQuery.jsp?ContNo="+ContNo,"",sFeatures);
}

//�ͻ��ϲ�֪ͨ���ѯ
function  KHHBNoticQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../sys/CustCombintQuery.jsp?ContNo="+ContNo,"",sFeatures);

}

//�������ѳб�������ѯ
function  InsuredqueryProposal()
{
var InsuredNo = InsuredGrid. getRowColData(0,2)

	if (InsuredNo == "")
	{
		    alert("û�еõ���������Ϣ��");
		    return;
	}
	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);
}

//������δ�б�������ѯ
function  InsuredqueryNotProposal()
{
	var InsuredNo = InsuredGrid. getRowColData(0,2)
	if (InsuredNo == "")
	{
		    alert("û�еõ���������Ϣ��");
		    return;
	}
	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);
}

//Ͷ�����ѳб�������ѯ
function  AppntqueryProposal()
{
	var AppntNo = document.all('AppntNo').value;
	if (AppntNo == "")
	{
		    alert("û�еõ�Ͷ������Ϣ��");
		    return;
	}
	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+AppntNo,"",sFeatures);
}

//Ͷ����δ�б�������ѯ
function  AppntqueryNotProposal()
{
	var AppntNo = document.all('AppntNo').value;
	//alert(AppntNo);
	if (AppntNo == "")
	{
		alert("û�еõ�Ͷ������Ϣ��");
		return;
	}
	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+AppntNo,"",sFeatures);
}


//<!-- XinYQ modified on 2006-02-20 : BGN -->
/*============================================================================*/

/**
 * ����״̬��ѯ
 */
function StateQuery()
{
    var sContNo;
    try
    {
        //sContNo = PolGrid.getRowColData(0,3);
        sContNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex) { return; }
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("���棺�޷���ȡ������ͬ���롣��ѯ����״̬ʧ�ܣ� ");
        return;
    }
    else
    {
        var sOpenWinURL = "LCContQueryMain.jsp";
        var sParameters = "ContNo=" + sContNo;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "����״̬��ѯ", "left");
    }
}

/*============================================================================*/
//<!-- XinYQ modified on 2006-02-20 : END -->


//�����ݽɷѲ�ѯ
function NewTempFeeQuery()
{
	var Prtno =document.all('PrtNo').value;
	var AppntNo = document.all('AppntNo').value;
	var AppntName = document.all('AppntName').value;
// alert("Prtno"+Prtno);
//  alert("AppntNo"+AppntNo);
//   alert("AppntName"+AppntName);
  var tSel = PolGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  	alert( "����ѡ��һ����¼��" );
  else
   window.open("../uw/UWTempFeeQry.jsp?Prtno=" + Prtno + " &AppntNo=" + AppntNo + "&AppntName= " +AppntName,"",sFeatures);
}


//�շ�Ա������Ϣ��ѯ
function ShowCollectorQuery()
{
	var AgentCode = document.all('AgentCode').value;

	if (AgentCode == "")
	{
	 alert("û�еõ��շ�Ա�����Ϣ!");
	 return;
	}
	 window.open("../sys/CollectorQueryMain.jsp?AgentCode="+AgentCode,"",sFeatures);
}

//��������켣��ѯ
function ShowTraceQuery()
{
	var ContNo = document.all('ContNo').value;
  //alert(ContNo);
	if (ContNo == "")
	{
	 alert("û�еõ��շ�Ա�����Ϣ!");
	 return;
	}
	 window.open("../sys/ShowTraceQuery.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

//�������ݲ�ѯ
function BaoPanShuju()
{
	window.open("../sys/BankDataQueryMain.jsp","",sFeatures);
	}

/*******************************************************************
 * ��ʼ
 * �����ǡ������ࡱ��ѯ������
 * ���ߣ� �����
 * ���ڣ� 2005-10-26
 ******************************************************************/


// ������ʷ��ѯ
function ShowWageHistoQuery(){
   var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("û�еõ������˱����Ϣ!");
		     return;
	}
	    window.open("../sys/LAwageQueryInput.jsp?ContNo="+tContNo,"",sFeatures);

	}


//��֯��ϵ��ѯ
function ShowOrganizationQuery(){
	var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("û�еõ������˱����Ϣ!");
		     return;
	}
	    window.open("../sys/LAAgentQuery.jsp?ContNo="+tContNo,"",sFeatures);

}

//ѪԵ��ϵ��ѯ
function ShowConsanguinityQuery(){
	var tContNo = document.all('ContNo').value;
	if( tContNo == ""){
		    alert("û���ҵ������˱����ص���Ϣ");
		    return;
		}
		window.open("../sys/LARearRelationQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}


// ������ʷ��ѯ
function ShowWelfareHistoQuery(){
	var tContNo = document.all('ContNo').value;
	if( tContNo == ""){
		    alert("û���ҵ������˱�ŵ������Ϣ");
		    return;
		}
		window.open("../sys/LAwageWelareQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}


//������ʷ��ѯ
function ShowCheckHistoQuery(){
	var tContNo = document.all('ContNo').value;
	if(tContNo == ""){
		   alert("û���ҵ������˵ı����Ϣ");
		   return;
		}
		window.open("../sys/LAAssessAccessoryQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}

//ҵ��Ա������Ϣ��ѯ
function ShowOperInfoQuery(){
	var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("û�еõ������˱����Ϣ!");
		     return;
	}
	    window.open("../sys/LAAgentQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}


//ҵ��Ա��ʷ��Ϣ��ѯ
function ShowOperHistoQuery(){
	var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("û�еõ������˱����Ϣ!");
		     return;
	}
	    window.open("../sys/LAAgentBQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}

/*******************************************************************
 * ����
 * ���ߣ� �����
 * ���ڣ� 2005-10-26
 ******************************************************************/

/*******************************************************************
 * ��ʼ
 * �����ǡ���ȫ�ࡱ��ѯ������
 * ���ߣ� �����
 * ���ڣ� 2005-10-31
 ******************************************************************/


 // ������Ϣ��ѯ
 function ShowRiskInfoQuery(){
 	    window.open("../sys/LmRiskAppQueryInput.jsp","",sFeatures);
 	}


// ����������Ϣ��ѯ
 function ShowBankRateQuery(){
 	    window.open("../sys/LdBankRateQueryInput.jsp","",sFeatures);
 	}

//�����˲�ѯ
function InsuredQuery(){
    PrtNo = document.all('PrtNo').value;
		if (PrtNo == "") return;
	  window.open("../app/ContInsuredQueryMain.jsp?prtNo="+PrtNo,"window1",sFeatures);
}


//��ѯ�ص����� added by guanwei 20060519
function QueryGetPolDate()
{
	
	var sqlid56="ProposalQuerySql56";
	var mySql56=new SqlClass();
	mySql56.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
	mySql56.addSubPara(tContNo );//ָ������Ĳ���
	var PolDateSQL =mySql56.getString();
	
//	var PolDateSQL = " select getpoldate,AccName,BankCode,BankAccNo "+
//									 "   from LCCont where ContNo = '"+tContNo+"'";
	var arrResultPolDate = easyExecSql(PolDateSQL);
	
	
	if(arrResultPolDate != null)
	{
	
	if(arrResultPolDate[0][0]!=""&&arrResultPolDate[0][0]!=null&&arrResultPolDate[0][0]!="null")
	{
		document.all('GetPolDate').value = arrResultPolDate[0][0];
		}
	else
		{
			document.all('GetPolDate').value = "";
			}
	if(arrResultPolDate[0][1]!=""&&arrResultPolDate[0][1]!=null&&arrResultPolDate[0][1]!="null")
	{
		document.all('RNAccName').value = arrResultPolDate[0][1];
		}
	else
		{
			document.all('RNAccName').value = "";
			}
	if(arrResultPolDate[0][2]!=""&&arrResultPolDate[0][2]!=null&&arrResultPolDate[0][2]!="null")
	{
		document.all('RNBankCode').value = arrResultPolDate[0][2];
		}
	else
		{
			document.all('RNBankCode').value = "";
			}
	if(arrResultPolDate[0][3]!=""&&arrResultPolDate[0][3]!=null&&arrResultPolDate[0][3]!="null")
	{
		document.all('RNAccNo').value = arrResultPolDate[0][3];
		}
	else
		{
			document.all('RNAccNo').value = "";
			}
		}
	}
