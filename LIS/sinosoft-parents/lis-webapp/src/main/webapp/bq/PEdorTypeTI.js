//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //��ʶ��ǰѡ�м�¼��GRID

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var sqlresourcename = "bq.PEdorTypeTIInputSql"; 
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage3 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//�ض����ȡ���㴦���¼�
//window.onfocus = initFocus;

//��ѯ��ť
function queryClick()
{
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
	

var strSql = "";
	var sqlid1="PEdorTypeTIInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.GrpContNo.value);
	strSql=mySql1.getString();
        
	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(strSql, LCGrpInsuAccGrid);
	showInfo.close();	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
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
		initLPInsuAccGrid();
	    	QueryBussiness();
	}
}

//��ѯ��ť
function queryPol()
{	
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


	var sqlid1="PEdorTypeTIInputSql2";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.PolNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.InsuredNo.value);
	mySql1.addSubPara(fm.InsuredName.value);
	mySql1.addSubPara(fm.IDType.value);
	mySql1.addSubPara(fm.IDNo.value);
	mySql1.addSubPara(fm.Sex.value);
	mySql1.addSubPara(fm.Birthday.value);
	var strSql=mySql1.getString();
          
	turnPage3.pageDivName = "divPage2";
	turnPage3.queryModal(strSql, LCInsuAccGrid);
	showInfo.close();	 
}

//����MultiLine�ĵ�ѡ��ťʱ����
function reportDetailClick(parm1, parm2)
{	
	fm.ContNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 2);
	//queryGEdorList();
	selGridFlag = 1;
}

//��������Ը����˻�ת��
function GEdorDetail()
{	
	if (LCInsuAccGrid.getSelNo()==0)
	{
	   alert("��ѡ����Ҫ�����˻�ת���ı���!");
	   return;
	}
	var tPolNo=LCInsuAccGrid.getRowColData(LCInsuAccGrid.getSelNo()-1,1);
	if(tPolNo==null||tPolNo=='')
	 {
		alert("δ�õ���������!");
		return;	
	 }
	window.open("./GrPEdorTypeTIInputMain.jsp?GrpContNo="+fm.GrpContNo.value+"&PolNo="+tPolNo+"&EdorType="+fm.EdorType.value);
}

//��������˻�ת��
function EdorDetail()
{
  	//����֮ǰ��Ҫѡ�����ת���ı���
	  if (LCInsuAccGrid.getSelNo()==0)
	  {
	    alert("��ѡ����Ҫ�����˻�ת���ı���!");
	    return;
	  }
	  var tPolNo=LCInsuAccGrid.getRowColData(LCInsuAccGrid.getSelNo()-1,1);
	  if(tPolNo==null||tPolNo=='')
	  {
		alert("δ�õ���������!");
		return;	
	  }
	window.open("./PEdorTypeTIDetailMain.jsp?ContNo="+fm.ContNo.value+"&PolNo="+tPolNo+"&EdorType="+fm.EdorType.value+"&EdorNo="+fm.EdorNo.value);
}

//�����ȡ�����¼�
function initFocus() 
{
  if (GEdorFlag)
  {   
    GEdorFlag = false;
    queryClick();
    queryGEdorList();
 	selGridFlag = 0;
  }
}

//��ѯ�������ĸ��˱�ȫ�б� �Ѿ�������ȫ��Ŀ�� 
function queryGEdorList() 
{

	var sqlid1="PEdorTypeTIInputSql3";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('GrpContNo').value);
	mySql1.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
	mySql1.addSubPara(document.all('EdorNo').value);
	var strSql=mySql1.getString();
          
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid); 	 
	if (selGridFlag == 2)
	{
		//fm.ContNo.value = "";
		fm.CustomerNo.value = "";
		selGridFlag = 0;
	}
}

//����MultiLine�ĵ�ѡ��ťʱ����
function reportDetail2Click(parm1, parm2)
{	
	fm.ContNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()-1, 2);
	selGridFlag = 2;
	//queryClick();
}

//���������¸��˱�ȫ
function cancelGEdor()
{
 if (LPInsuAccGrid.getSelNo()==0)
  {
    alert("��ѡ����Ҫ�����Ĺ�������!");
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
  document.all('Transact').value = "DELETE||PMAIN";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
	
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

/*��ѯ������Ϣ*/
function QueryBussiness()
{


	var sqlid1="PEdorTypeTIInputSql4";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	
	mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.ContNo.value);
	var strSql=mySql1.getString();
          
	turnPage3.pageDivName = "divPage3";
	turnPage3.queryModal(strSql, LPInsuAccGrid); 
}

function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
var sqlid1="PEdorTypeTIInputSql5";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdortype);
	var strSQL=mySql1.getString();
          
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}
