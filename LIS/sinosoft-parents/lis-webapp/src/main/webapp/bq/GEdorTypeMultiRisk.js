//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var pEdorFlag = true;                        //����ʵʱˢ�´���������ݵ��б�

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();         //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
window.onfocus = initFocus;                  //�ض����ȡ���㴦���¼�
var arrList1 = new Array();                  //ѡ��ļ�¼�б�

/**
 * ��ѯ��ť
 */
function queryClick() {
	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
//	var strSql = "select GrpPolNo, RiskCode, Peoples2, Prem, Amnt from LCGrpPol where grpcontno = '"
//	           + fm.GrpContNo.value + "'"
//	           + getWherePart('GrpPolNo')
//	           + getWherePart('RiskCode')
//	           + " and RiskCode not in (select distinct riskcode from lcpol where polno in (select polno from lpedorItem where edorno='" + fm.EdorNo.value + "' and edortype='"+fm.EdorType.value+"'))";
	var strSql = "";
	var sqlid1="GEdorTypeMultiRiskSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeMultiRiskSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("GrpPolNo")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("RiskCode")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	strSql=mySql1.getString();	
	turnPage.queryModal(strSql, RiskGrid); 	 
	showInfo.close();	 
}

/**
 * ����MultiLine�ĸ�ѡ��ťʱ����
 */
function reportDetailClick(parm1, parm2) {	
  if (document.all(parm1).all('RiskGridChk').checked) {
    arrList1[document.all(parm1).all('RiskGridNo').value] = document.all(parm1).all('RiskGrid1').value;
  }
  else {
    arrList1[document.all(parm1).all('RiskGridNo').value] = null;
  }
  
  GrpBQ = false;
}

/**
 * �������˱�ȫ
 */
var arrP = 0;
function pEdorMultiDetail() 
{
    //У���Ƿ�ѡ��
    var i = 0;
    var chkFlag=false;
    for (i=0;i<RiskGrid.mulLineCount;i++ )
    {
        if (RiskGrid.getChkNo(i))
        {
          chkFlag=true;
          break;
        }
        
    }
    if (chkFlag)
    {
      PEdorDetail();
      
    }
    else
    {
        alert("��ѡ����Ҫ�����ļ�¼!");
    } 
 
}

/**
 * ������˱�ȫ
 */
function PEdorDetail() 
{    
    var showStr = "�������뼯���¸��˱�ȫ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all("Transact").value = "INSERT||EDORRISK";
    fm.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
  showInfo.close();

  if (FlagStr == "Fail") 
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else 
    { 
    pEdorFlag = true;
    
    if (fm.Transact.value=="DELETE||EDORRISK") 
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
     // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    }
    else 
    {
      openPEdorDetail();
      //PEdorDetail();
    }
  }
}

/**
 * �����ȡ�����¼�
 */
function initFocus() {
  if (pEdorFlag) {   
    pEdorFlag = false;
        
    queryPEdorList();
  }
}

var GrpBQ = false;
var GTArr = new Array();
/**
 * �򿪸��˱�ȫ����ϸ����
 */
function openPEdorDetail() 
{

}

/**
 * ��ѯ�������ĸ��˱�ȫ�б�
 */
function queryPEdorList() {
//  var strSql = "select GrpPolNo, RiskCode, Peoples2, Prem, Amnt from LCGrpPol where grppolno in "
//	           + "(select distinct grppolno from lcpol where polno in (select polno from lpedoritem where edorno='" + fm.EdorNo.value + "' and edortype='"+fm.EdorType.value+"'))";
  
  	var strSql = "";
	var sqlid1="GEdorTypeMultiRiskSql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeMultiRiskSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	strSql=mySql1.getString();	
  
  turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, Risk2Grid); 	 
	
	queryClick();
}

/**
 * ����MultiLine�ĵ�ѡ��ťʱ����
 */
function reportDetail2Click(parm1, parm2) {	
  if (document.all(parm1).all('Risk2GridChk').value=='on' || document.all(parm1).all('Risk2GridChk').value=='1') {
    fm.ContNo.value = document.all(parm1).all('Risk2Grid1').value;
  }
}

/**
 * ���������¸��˱�ȫ
 */
function cancelPEdor() {
  //У���Ƿ�ѡ��
    var i = 0;
    var chkFlag=false;
    for (i=0;i<Risk2Grid.mulLineCount;i++ )
    {
        if (Risk2Grid.getChkNo(i))
        {
          chkFlag=true;
          break;
        }
        
    }
    if (chkFlag)
    {
        document.all("Transact").value = "DELETE||EDORRISK"
    
        var showStr = "���ڳ�����ȫ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
        fm.submit();
    }
    else
    {
        alert("��ѡ����Ҫ�����ļ�¼!");
    }   
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug) {
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function returnParent() {
	top.opener.getEdorItemGrid();
	top.close();
}
