//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var k=0;


// ���ݷ��ظ�����
function returnLMChargeRate()
{		
	top.close();	
}

//��������
function saveLMChargeRate()
{	
	if(verifyInput2() == false) 
	  return false;   
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.all('mOperate').value = "INSERT||MAIN";
	fm.action = "./LMChargeRateSave.jsp";
	fm.submit();
}

//��������
function updateLMChargeRate()
{	
	CaseGrid.delBlank;
	if (CaseGrid.mulLineCount <= 0)
	{
	    alert("���Ȳ�ѯ�����ѣ��ٽ����޸ģ�");
	    return false;  
	}
	
	var tSelNO = CaseGrid.getSelNo();
	if (tSelNO <= 0)
	{
	    alert("��ѡ��һ����Ҫ�޸ĵ����������ݣ�");
	    return;
	}
	document.all('RiskCode').value=CaseGrid.getRowColData(tSelNO - 1,2);
	if(verifyInput2() == false) 
	  return false; 
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.all('mOperate').value = "UPDATE||MAIN";
	fm.action = "./LMChargeRateSave.jsp";
	fm.submit();
}

//ɾ������
function deleteLMChargeRate()
{	
	CaseGrid.delBlank;
	if (CaseGrid.mulLineCount <= 0)
	{
	    alert("���Ȳ�ѯ�����ѣ��ٽ���ɾ����");
	    return false;  
	}
	
	var tSelNO = CaseGrid.getSelNo();
	if (tSelNO <= 0)
	{
	    alert("��ѡ��һ����Ҫɾ�������������ݣ�");
	    return;
	}
	document.all('RiskCode').value=CaseGrid.getRowColData(tSelNO - 1,2);
    document.all('ChargeRate').value=CaseGrid.getRowColData(tSelNO -1,3);
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.all('mOperate').value = "DELETE||MAIN";
	fm.action = "./LMChargeRateSave.jsp";
	fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
    if (fm.mOperate.value == "DELETE||MAIN")
    {
        fm.RiskCode.value = "";
        fm.ChargeRate.value = "";
    }
    selectLMChargeRate();
  }
}



function selectLMChargeRate()
{
  k++;	  	
  initCaseGrid();
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

   //��дSQL��� 	       
// var strSql = "select p.GrpContNo,p.RiskCode,c.ChargeRate from  LCGrpPol p,LMGrpCharge c" +
//               " where c.GrpPolNo=p.GrpPolNo"; 
    var sqlid1="LMChargeRateSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.LMChargeRateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSql=mySql1.getString();
  	          
  if (document.all('GrpContNo').value != "")	//�����ͬ��
  	strSql = strSql + " and p.GrpContNo= '" + document.all('GrpContNo').value + "'";
  //alert(strSql);
      
  if (document.all('RiskCode').value != '')	//���ֱ���
  	strSql = strSql + " AND p.RiskCode= '" + document.all('RiskCode').value + "'";
  turnPage.queryModal(strSql,CaseGrid);
  showInfo.close();

}

function flagLMChargeRate(parm1,parm2) 
{
    if(document.all(parm1).all('InpCaseGridSel').value == '1')
    {
        document.all('RiskCode').value=document.all(parm1).all('CaseGrid2').value;
        document.all('ChargeRate').value=document.all(parm1).all('CaseGrid3').value;
    
    }
}

function afterCodeSelect( cCodeName, Field ) {
	parameter1=cCodeName;
	parameter2=Field;
	//alert(cCodeName);
	if(parameter1=="GrpRisk")
	{
        document.all('ChargeRate').value = "";
        selectLMChargeRate(); 
    	return;
    }
}
