//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //��ʶ��ǰѡ�м�¼��GRID

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var sqlresourcename = "bq.PEdorTypeTIDetailInputSql"; 
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage3 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//�ض����ȡ���㴦���¼�
//window.onfocus = initFocus;

//��ѯ��ť
function queryClick()
{
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
    var sqlid1="PEdorTypeTIDetailInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tPolNo);
	mySql1.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
	strSql=mySql1.getString();
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{

	    var sqlid2="PEdorTypeTIDetailInputSql2";
		
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(tPolNo);
		mySql2.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
		var strSql2=mySql2.getString();
          
		arrResult=easyExecSql(strSql2);
	}
	//alert(strSql);
	tStartDate=arrResult[0][0];
	

   var sqlid3="PEdorTypeTIDetailInputSql3";
	
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tStartDate);
	mySql3.addSubPara(fm.PolNo.value);//ָ������Ĳ���
	var strSql3=mySql3.getString();
	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(strSql3, LCGrpInsuAccGrid);
	

    var sqlid4="PEdorTypeTIDetailInputSql4";
	
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	var strSql4=mySql4.getString();
          
	var arrResult=easyExecSql(strSql4);
	
	if(arrResult==null||arrResult=="")
	{
	  divLCInsuAcc.style.display="";
	}
	else
	{
		turnPage3.pageDivName = "divPage3";
		turnPage3.queryModal(strSql4, LPInsuAccGrid); 
		//divLCInsuAcc.style.display="none";
	}
	queryClick2();
}



//��ѯ��ť
function queryClick2()
{
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
    var sqlid5="PEdorTypeTIDetailInputSql5";
	
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tPolNo);
    mySql5.addSubPara(fm.EdorItemAppDate.value);
	var strSql=mySql5.getString();
          
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{

	    var sqlid6="PEdorTypeTIDetailInputSql6";
		
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tPolNo);
		mySql6.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
		var strSql6=mySql6.getString();
          
		arrResult=easyExecSql(strSql6);
	}
	
	tStartDate=arrResult[0][0];
	

   var sqlid7="PEdorTypeTIDetailInputSql7";
	
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	var strSql=mySql7.getString();
          
	turnPage3.pageDivName = "divPage3";
	turnPage3.queryModal(strSql, TempOutInsuAccGrid); 
	var arrResult=easyExecSql(strSql);
	if(arrResult==null||arrResult=="")
	{
		
	}
    else
	{
		showInsuAccIn("");
	}
}

function reportDetail2Click()
{
   var selno = TempOutInsuAccGrid.getSelNo()-1;
   if (selno <0)
   {
          alert("��ѡ��Ҫ�����ת����¼��");
          return;
   }
   var tCurrency = TempOutInsuAccGrid.getRowColData(selno,8);
   showInsuAccIn(tCurrency);
}

//�����˻�ת��
function DealAccIn()
{  
	
		//alert("111");
//   if (LCInsuAccGrid.getSelNo()==0)
//  {
    //alert("��ѡ����Ҫת����ʻ�!");
    //return;
  //}
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "INSERT||DEALIN";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}

//�������˱�ȫ
function cancelAccIn()
{
 if (divLCInsuAcc.style.display=="")
  {
    alert("û����Ҫ��������ʷת����Ϣ!");
    return;
  }

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.all('Transact').value = "DELETE||CANCELIN";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}


//

function cancelAccOut()
{
 if (TempOutInsuAccGrid.getSelNo()==0)
  {
    alert("��ѡ����Ҫ������ת����¼!");
    return;
  }
  fm.InsuAccNo.value=TempOutInsuAccGrid.getRowColData(TempOutInsuAccGrid.getSelNo()-1,3);
	//fm.PayPlanCode.value=TempOutInsuAccGrid.getRowColData(TempOutInsuAccGrid.getSelNo()-1,4);
	fm.PolNoDO.value=TempOutInsuAccGrid.getRowColData(TempOutInsuAccGrid.getSelNo()-1,2);

	//alert(fm.InsuAccNo.value+"@@@"+fm.PayPlanCode.value);
	//return;
	
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "DELETE||CANCELOUT";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}

//��ʾ
function showInsuAccIn(xCurrency)
{

	initLCInsuAccGrid();
	
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
var sqlid1="PEdorTypeTIDetailInputSql8";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tPolNo);
	mySql1.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
	var strSql=mySql1.getString();
          
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{
		var sqlid1="PEdorTypeTIDetailInputSql9";
	
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tPolNo);
	mySql2.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
	var strSql=mySql2.getString();
          
		arrResult=easyExecSql(strSql);
	}
	//alert(arrResult);
	tStartDate=arrResult[0][0];
	
	
	//alert("001");
var sqlid1="PEdorTypeTIDetailInputSql10";
	
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tStartDate);
	mySql3.addSubPara(fm.PolNo.value);//ָ������Ĳ���
	mySql3.addSubPara(xCurrency);
	var strSql=mySql3.getString();

	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsuAccGrid); 
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content, Result)
{
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//initLCGrpInsuAccGrid();
		//initLCInsuAccGrid();
    //initLPInsuAccGrid();
		//queryClick();
		
	}
initForm();
//alert("aaa");
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
	top.close();
	//top.opener.initLPInsuAccGrid();
	top.opener.QueryBussiness();
}
function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value
	if(tEdortype!=null || tEdortype !='')
	{
	    var sqlid1="PEdorTypeTIDetailInputSql11";
		
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tEdortype);
		var strSql=mySql1.getString();
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}












//�����˻�ת��
function DealAccOut()
{
	//alert("111");
   if (LCGrpInsuAccGrid.getSelNo()==0)
  {
    alert("��ѡ����Ҫת�����ʻ�!");
    return;
  }
  //ת����λ��
  try {
  	var OutUnit=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,9);
  	var OutBala=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,10);
  	
	if((OutUnit==null||OutUnit=='')&&(OutBala==null||OutBala==''))
	{
		alert("������ת����λ!");
		return;	
	}else if(OutUnit-0<=0)
	{
		alert("���������0�ĵ�λ��!");
	  	return;	
	}
	//alert(LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,8)+";;"+OutUnit);
	var aaa = OutUnit-LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6);
	if(aaa>0)
	{
		alert("ת���ĵ�λ�����ܶ��ڿ�ת����λ!");
	return;	
	}
  } catch(ex) { 
  	alert("��������ȷ��λ��!");
	return;	
  	}
    	fm.OutUnit.value = OutUnit;
  	//alert(OutUnit);
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.all('Transact').value = "INSERT||DEALOUT";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}
