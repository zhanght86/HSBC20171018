//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// ��ѯ��ť
function easyQueryClick() {
	initForm();
	if (fm.PolNo2.value == ""&&fm.ContNo.value == ""&&fm.AppntName.value == "")
	{
	   alert("�����ţ����ֺţ�Ͷ������������ͬʱΪ��");
       return false;
	} 
	// ��дSQL��䣬����ǵֽ����屣��Ҳ���������磺�������յ������
	var strSql = "select PolNo, riskcode, LeavingMoney, prem, amnt, SignDate, PayLocation "
	         + " from lcpol where appflag='1' and LeavingMoney<>0 "
	         + " and not exists (select PolNo from LCRnewStateHistory where contno=LCPol.contno and riskcode=LCPol.riskcode and state!='5') "
             ; 
    if(document.all('BankCode').value!="")
    {
       strSql= strSql+" and exists (select 1 from lccont where contno=lcpol.contno and bankcode='"+document.all('BankCode').value+"') "
    }
    strSql= strSql
      + getWherePart("ContNo", "ContNo")
      + getWherePart("PolNo", "PolNo2")
      + getWherePart("RiskCode", "RiskCode")
      + getWherePart("AppntName", "AppntName")
      +" and managecom like '"+comcode+"%'"
      ;
  if (fm.AppntName.value != "") 
  strSql = strSql + " and appntno in (select c.customerno from ldperson c where name='" + fm.AppntName.value + "')"; 
  strSql = strSql + " Order by SignDate";
				     
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��Ҫ�����ı�����");
    return "";
  }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = BankGrid;    
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;    
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);			     

  fm.PolNo.value = fm.PolNo2.value;
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(!checkData())	return false;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //�ύ 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
  }
} 

function checkData()
{
  if(document.all('PolNo').value==null||document.all('PolNo').value=='')
   {
   	alert("�������벻��Ϊ��");
    return false;
   }
  if(document.all('LeavingMoney').value==null||document.all('LeavingMoney').value=='')
   {
    alert("ʵ������Ϊ��");
    return false;
   }  
  // var tSql = "select ProPosalNo,leavingmoney,contno from LCPol where PolNo='"+document.all('PolNo').value+"'";
    var  sqlid1="NewPolFeeWithDrowSql0";
	var  mySql1=new SqlClass();
	mySql1.setResourceName("finfee.NewPolFeeWithDrowSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
	tSql=mySql1.getString();
   var tResult = easyExecSql(tSql, 1, 1, 1);
   if(!tResult)
   {
     alert("û�в鵽��Ӧ�ı�����");	
     return false;
   }
   if(tResult[0][1]==0)
   {
	alert("�ñ����޿������!");	
        return false;	
   }
   if(eval(document.all('LeavingMoney').value)<=0)
   {
     alert("������˷ѽ����������!");	
     return false;	
   }
   if(eval(document.all('LeavingMoney').value)>tResult[0][1])
   {
     alert("������˷ѽ����ڿ������!");	
     return false;	
   }
  document.all('ProPosalNo').value=tResult[0][0];
  document.all('ContNo').value=tResult[0][2];
  document.all('PolNo2').value=document.all('PolNo').value;
  return true;
}

function showOne(parm1, parm2) {
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpBankGridSel').value == '1' ) {
	  var index = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.PolNo.value = turnPage.arrDataCacheSet[index][0];
	  fm.LeavingMoney.value=turnPage.arrDataCacheSet[index][2];
  }
	//document.all('divPayMoney').style.display = "";
}
