//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();

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

// ��ѯ��ť
function easyQueryClick()
{
  //���ȼ���¼���
  if (!verifyInput()) return false; 
	
	var fiscalyear = document.all('FiscalYear').value ;
	//tongmeng 2007-11-21 modify
	//ȥ�����ʻ���ǰ���Ϊ0������Ϣ������
	// ��дSQL���
	var strSQL = "select a.GrpContNo,a.GrpName,a.managecom,a.cvalidate,a.prtno " 
						 + "from lcgrppol a " 
						 + "where appflag = '1' "
						 + "and exists (select 1 from lcinsureacc where grpcontno = a.grpcontno and state not in ('1','4') "
						 //+ " and insuaccbala > 0 "
						 + " and baladate >= '"+(fiscalyear-1)+"-12-31' and baladate < '"+fiscalyear+"-12-31') "
						 + getWherePart('a.GrpContNo','GrpContNo') 
						 + getWherePart('a.riskcode','RiskCode')
						 + getWherePart('a.cvalidate','BDate','>=','0')
						 + getWherePart('a.cvalidate','EDate','<=','0')
						 + " order by grpcontno";
	//alert(strSQL);
	turnPage.queryModal(strSQL, PolGrid);   
}

//�ύ�����水ť��Ӧ����
function appConfirm()
{
	//У���Ƿ��Ѿ����˴�����Ļ�����ĩ�������������
	var tFiscalEnd = document.all('FiscalYear').value;
	var nowDate = new Date();
	if(nowDate.getYear() > tFiscalEnd || (nowDate.getYear() == tFiscalEnd && nowDate.getMonth()+1 == "12" && nowDate.getDate() == "31"))
	{
	  var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	
		//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
		document.all('calculate').disabled = true;
		fm.action = "./GrpInterestSave.jsp";
		document.getElementById("fm").submit();
	}
	else
	{
		alert("δ���û�����ĩ�Ľ�Ϣʱ�䣬�����������");
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	document.all('calculate').disabled = false;
  if (FlagStr == "Fail" )
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //ִ����һ������
  }
}

function viewErrLog()
{
		window.open("./ViewErrLogFiscalLXMain.jsp");
}