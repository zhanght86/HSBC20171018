var showInfo;
var turnPage = new turnPageClass(); 


function polquery()
{

    if((fm.ContNo.value==null||fm.ContNo.value=="")&&(fm.PrtNo.value==null||fm.PrtNo.value=="")){
      alert("��¼�롮Ͷ�����š���ӡˢ�š����е�һ����ٽ��в�ѯ��");
      return false;
    }
	initPolGrid();
//	var sql = "select ContNo,PrtNo,"
//	             + " (select riskcode from lcpol where contno=a.contno and insuredno=a.insuredno and polno=mainpolno and (risksequence='1' or risksequence='-1')),"
//	             + " AppntName,InsuredName, Operator, MakeDate from LCCont a where AppFlag='0' "
//				 + "and GrpContNo='00000000000000000000' "
//				 + getWherePart( 'ContNo' )
//				 + getWherePart( 'ManageCom', 'ManageCom' )//��ѯ�����еļ���Ȩ�޹�������
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'RiskCode' )
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'MakeDate' )		 
//				 + " order by MakeDate desc,prtno";
	
  	var  ContNo0 = window.document.getElementsByName(trim("ContNo"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  RiskCode0 = window.document.getElementsByName(trim("RiskCode"))[0].value;
  	var  PrtNo0 = window.document.getElementsByName(trim("PrtNo"))[0].value;
  	var  MakeDate0 = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid0="delproposalSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.delproposalSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(ContNo0);//ָ������Ĳ���
	mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(AgentCode0);//ָ������Ĳ���
	mySql0.addSubPara(RiskCode0);//ָ������Ĳ���
	mySql0.addSubPara(PrtNo0);//ָ������Ĳ���
	mySql0.addSubPara(MakeDate0);//ָ������Ĳ���s
	var sql=mySql0.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	if (!turnPage.strQueryResult) 
	{
      alert("û��Ͷ������Ϣ!");
      return "";
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    turnPage.pageDisplayGrid = PolGrid;
    turnPage.strQuerySql     = sql;
    turnPage.pageIndex       = 0;
    var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    return true;
}

function delpol()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼" );
		return;
	}	
	var prtNo=PolGrid.getRowColData(tSel - 1,2);
		if (prtNo == "")
		{
			alert("δ��ѯ��Ҫ���滻��ӡˢ��!");
			return;
		}		
		document.all('PrtNoHide').value = prtNo;
  if (confirm("��ȷʵ��ɾ����ӡˢ���µ�����Ͷ������?"))
  {
  var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.all('fmAction').value = "DELETE||PRT";
  //document.all('PrtNo').value = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
  //document.all('PolNo').value = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
  document.getElementById("fm").submit(); 
  }
}

function afterSubmit( FlagStr, content )
{
  initForm();
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}