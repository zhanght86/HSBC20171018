//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  initPersonGrid();
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	//initPersonGrid();

    //<!-- XinYQ added on 2005-12-09 : ��������һ�������Ž��в�ѯ : BGN -->
    var sCustomerNo = document.getElementsByName("CustomerNo")[0].value;
    var sName = document.getElementsByName("Name")[0].value;
    var sBirthday = document.getElementsByName("Birthday")[0].value;
    var sIDNo = document.getElementsByName("IDNo")[0].value;
    var sContNo = document.getElementsByName("ContNo")[0].value;
    if (trim(sCustomerNo) == "" && trim(sName) == "" && trim(sBirthday) == "" && trim(sIDNo) == "" && trim(sContNo) == "")
    {
        alert("���������������²�ѯ�����е�һ�����ͻ����롢�������������ڡ�֤�����롢������  ");
        return;
    }
    
//    if(trim(sCustomerNo) == null|| trim(sCustomerNo) == "")
//    {
//    	   alert("������ͻ�����");
//          return;
//    }
    //<!-- XinYQ added on 2005-12-09 : ��������һ�������Ž��в�ѯ : END -->

	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	
	var sqlid1="LDPersonQueryNewSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("sys.LDPersonQueryNewSql"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(fm.CustomerNo.value);//ָ������Ĳ���
    mySql1.addSubPara(fm.Name.value);//ָ������Ĳ���
    mySql1.addSubPara(fm.Sex.value);//ָ������Ĳ���
    mySql1.addSubPara(fm.Birthday.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.IDType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.IDNo.value);//ָ������Ĳ���
	var addStr1 = " and 1=1 ";
//	strSQL=mySql1.getString();
	
//	strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//				 + getWherePart( 'a.CustomerNo','CustomerNo',strOperate )
//				 + getWherePart( 'a.Name','Name',strOperate )
//				 + getWherePart( 'a.Sex','Sex',strOperate )
//				 + getWherePart( 'a.Birthday','Birthday','=' )
//				 + getWherePart( 'a.IDType','IDType',strOperate )
//				 + getWherePart( 'a.IDNo','IDNo',strOperate );

    if(sContNo != null && trim(sContNo) != "")
    {
    	addStr1 = " and a.customerno in ("
    	        + " select appntno from lcappnt where contno = '"+sContNo+"' "
    	        + " union "
    	        + " select insuredno from lcinsured where contno = '"+sContNo+"' "
    	        + " )";
    }
//	strSQL	+= "order by a.CustomerNo";
//alert(strSQL);

    mySql1.addSubPara(addStr1);//ָ������Ĳ���
    strSQL=mySql1.getString();
	turnPage.queryModal(strSQL, PersonGrid);        
}

// ���ݷ��ظ�����
function returnParent()
{
	//alert("aaa="+top.opener.location);
	var arrReturn = new Array();
	var tSel = PersonGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			if(queryFlag=="queryAppnt"){
			  top.opener.afterQuery( arrReturn );
		  }
		  else if(queryFlag=="queryInsured"){
			  top.opener.afterQuery21( arrReturn );
		  }
		  else{
			  top.opener.afterQuery( arrReturn );
		  }
			top.close();
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResult()
{
	//��ȡ��ȷ���к�
	tRow = PersonGrid.getSelNo() - 1;

	arrSelected = new Array();
	//������Ҫ���ص�����
	
		var sqlid2="LDPersonQueryNewSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("sys.LDPersonQueryNewSql"); //ָ��ʹ�õ�properties�ļ���
    mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    mySql2.addSubPara(PersonGrid.getRowColData(tRow, 1));//ָ������Ĳ���

	strSql=mySql2.getString();
	
//	strSql = "select * from LDPerson where 1=1"
//	       + " and CustomerNo = '" + PersonGrid.getRowColData(tRow, 1) + "'"
//	       + " and Name = '" + PersonGrid.getRowColData(tRow, 2) + "'"
//	       + " and Sex = '" + PersonGrid.getRowColData(tRow, 3) + "'"
//	       + " and Birthday = '" + PersonGrid.getRowColData(tRow, 4) + "'"
//	       + " and IDType = '" + PersonGrid.getRowColData(tRow, 5) + "'"
//	       + " and IDNo = '" + PersonGrid.getRowColData(tRow, 6) + "'"
	       ;
	
	//alert(strSql);
	var arrResult = easyExecSql(strSql);
	
	return arrResult;
}

