//        ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "f1print.CountPrintSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
  var i = 0;
  var flag = 0;
	flag = 0;
        
	//for( i = 0; i < PolGrid.mulLineCount; i++ ) {
		if( PolGrid.getSelNo() ) {			
			flag = 1;
			//break;
		}
	//}
	
	if( flag == 0 ) {
		alert("����ѡ��һ����¼���ٴ�ӡ����");
		return false;
	}
	var tRow = PolGrid.getSelNo() - 1;
	var tPrtNo=PolGrid.getRowColData(tRow,1);
	if(tPrtNo==""||tPrtNo==null){
		alert("���Ȳ�ѯ��");
		return false;
	}
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  // showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  // showSubmitFrame(mDebug);     
	var vOrgTarget = fm.target;
	fm.target="f1print";
	document.getElementById("fm").submit();
	fm.target = vOrgTarget;
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");	
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  } else {
  	easyQueryClick();
  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��CountPrint.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}


// ��ѯ��ť
function easyQueryClick()
{
	if(!verifyInput()){
		return;
		}
	// ��ʼ�����
	initPolGrid();


	//var strManageComWhere = " AND ManageCom LIKE '" + manageCom + "%%' ";
	
	//if( fm.ManageCom.value != '' ) {
	//	strManageComWhere += " AND ManageCom LIKE '" + fm.ManageCom.value + "%%' ";
	//}
	if(fm.ManageCom.value=="")
	{
		alert("��ѡ����������");
		return false;
	}

	
	// ��дSQL���
	var strSQL = "";

	//strSQL =	"select 1 from(select count(distinct polno) r from lcpol where contno='"+document.all('ContNo').value+"' and appflag='1' ) where r>1";
	
		var sqlid1="CountPrintSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(document.all('ContNo').value);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString());  
	//�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
    return alert("�����п��ܺ��ж������֣����鱣����Ϣ");
	}
	var strContNoSQL="";
	     if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
    )
   {
   	   //or exists (select 'X' from lccontprinttrace r where r.contno = a.contno and r.makedate <= sysdate and r.makedate >= substr((sysdate - 30), 0, 10)))
   	   //Ϊ�������ܣ�ֻ������ʮ��ǩ���ı����������ʮ�첹��ı�����ʮ����Ե���
   	   strContNoSQL=" and (a.signdate <= sysdate and a.signdate >= substr((sysdate - 15), 0, 10)) ";
 	}
	/*
	strSQL = "SELECT PolNo,ContNo, PrtNo, RiskCode, AppntName, InsuredName,ManageCom,AgentCode FROM LCPol a where GrpPolNo like '%00000000000000000000'"
				 + " AND AppFlag = '1' "
				 //+ "and NVL ((select printcount from lccont where contno=a.contno), 0) = 0 "
				 //+ " and riskcode in (select riskcode from LMRiskApp where NotPrintPol = '1')"
				// + " AND NOT EXISTS ( SELECT PolNo FROM LCPol WHERE A.PrtNo = PrtNo AND AppFlag <> '1' ) "
				//Ӧ��ȫҪ���޸�Ϊprintcount in ('0','10')
				 + "and exists(select 1 from lccont where contno=a.contno and printcount in ('0','10')) "
				 + "and exists(select 1 from lmriskapp where riskcode=a.riskcode and notprintpol='1') "
				 + "and not exists(select 1 from lcpol where prtno=a.prtno and appflag='0') "
				 + " and ManageCom like '" + fm.ManageCom.value + "%'"
				 //+ strManageComWhere
			//	 + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+ComCode+"','Pl')=1 "
				 + getWherePart( 'ContNo' )
				 + getWherePart( 'PrtNo' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'RiskCode' )
				 + getWherePart( 'SaleChnl' )
				 +strContNoSQL;
*/
		var sqlid2="CountPrintSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.ManageCom.value);
		mySql2.addSubPara(fm.ContNo.value);
		mySql2.addSubPara(fm.PrtNo.value);
		mySql2.addSubPara(fm.AgentCode.value);
		mySql2.addSubPara(fm.RiskCode.value);
		mySql2.addSubPara(fm.SaleChnl.value);
		mySql2.addSubPara(strContNoSQL);


	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString());  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return alert("δ��ѯ������������������Ϣ");
	}
	
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html","",sFeatures);
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}

//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //��������	
		//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
    var sqlid3="CountPrintSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(cAgentCode);
	
    
    
    var arrResult = easyExecSql(mySql3.getString());
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
