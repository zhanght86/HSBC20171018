/** 
 * �������ƣ�LDUWUserInit.jsp
 * �����ܣ��������� ���ļ��а����ͻ�����Ҫ����ĺ������¼�
 * �������ڣ�2005-01-24 18:15:01
 * ������  ��ctrHTML
 * ������  ��
 */
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus() {
	if(showInfo!=null) {
	  try {
	    showInfo.focus();
	  }
	  catch(ex) {
	    showInfo=null;
	  }
	}
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
  showInfo.close();
  if (FlagStr == "Fail" ) {
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
  else {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //ִ����һ������
  }
}
// ��ѯ��ť
function easyQueryClick() {
	//�˴���дSQL���			     
/*	
	var strSql = "select a.GetNoticeNo, a.otherno, b.name, a.PayDate, a.SumDuePayMoney, a.bankcode, a.bankaccno, a.accname "
	           + " from LJSPay a, ldperson b where a.appntno=b.customerno and OtherNoType='2' "
          	 + getWherePart("a.GetNoticeNo", "GetNoticeNo2")
          	 + getWherePart("a.OtherNo", "OtherNo")
          	 + getWherePart("a.BankCode", "BankCode")
          	 + getWherePart("a.SumDuePayMoney", "SumDuePayMoney");
  
  if (fm.AppntName.value != "") strSql = strSql + " and a.appntno in (select c.customerno from ldperson c where name='" + fm.AppntName.value + "')";
  if (fm.PrtNo.value != "") strSql = strSql + " and a.otherno in (select polno from lcpol where prtno='" + fm.PrtNo.value + "')";
*/  			    
//   var strSql = "select UserCode,(select codename from ldcode where codetype='uwtype' and code=uwtype) end,UWPopedom from LDUWUser where 1=1 "
//    + getWherePart("UserCode", "UserCode")
//    + getWherePart("UWType", "UWType")
//    //2008-12-30 ln add --�����ѯ����
//    + getWherePart("UpUWPopedom", "UpUWPopedom")
//    + getWherePart("AddPoint", "AddPoint")
//    //end 2008-12-30   
//    + getWherePart("UWPopedom", "UWPopedom")
//    + getWherePart("Operator", "Operator")
//    + getWherePart("ManageCom", "ManageCom")
//    + getWherePart("MakeDate", "MakeDate")
//    + getWherePart("MakeTime", "MakeTime")
//    + getWherePart("ModifyDate", "ModifyDate")
//    + getWherePart("ModifyTime", "ModifyTime")
  ;
   
 	var  UserCode0 = window.document.getElementsByName(trim("UserCode"))[0].value;
  	var  UWType0 = window.document.getElementsByName(trim("UWType"))[0].value;
  	var  UpUWPopedom0 = window.document.getElementsByName(trim("UpUWPopedom"))[0].value;
  	var  AddPoint0 = window.document.getElementsByName(trim("AddPoint"))[0].value;
  	var  UWPopedom0 = window.document.getElementsByName(trim("UWPopedom"))[0].value;
  	var  Operator0 = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  MakeDate0 = window.document.getElementsByName(trim("MakeDate"))[0].value;
  	var  MakeTime0 = window.document.getElementsByName(trim("MakeTime"))[0].value;
  	var  ModifyDate0 = window.document.getElementsByName(trim("ModifyDate"))[0].value;
  	var  ModifyTime0 = window.document.getElementsByName(trim("ModifyTime"))[0].value;
	var sqlid0="LDIndUWUserQueryInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDIndUWUserQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(UserCode0);//ָ������Ĳ���
	mySql0.addSubPara(UWType0);//ָ������Ĳ���
	mySql0.addSubPara(UpUWPopedom0);//ָ������Ĳ���
	mySql0.addSubPara(AddPoint0);//ָ������Ĳ���
	mySql0.addSubPara(UWPopedom0);//ָ������Ĳ���
	mySql0.addSubPara(Operator0);//ָ������Ĳ���
	mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(MakeDate0);//ָ������Ĳ���
	mySql0.addSubPara(MakeTime0);//ָ������Ĳ���
	mySql0.addSubPara(ModifyDate0);//ָ������Ĳ���
	mySql0.addSubPara(ModifyTime0);//ָ������Ĳ���
	var strSql=mySql0.getString();
   
   
  //alert(strSql);
	turnPage.queryModal(strSql, LDUWUserGrid);
}
function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
  alert("�˴�ѡ��ĳһ�еĴ���");
//	if(document.all(parm1).all('InpBankGridSel').value == '1' ) {
//	  var index = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
//	  fm.GetNoticeNo.value = turnPage.arrDataCacheSet[index][0];
 // }
}
function returnParent()
{
        var arrReturn = new Array();
	var tSel = LDUWUserGrid.getSelNo();
	
	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				//alert(tSel);
				arrReturn = getQueryResult();
				//alert("aaa="+arrReturn[0][0]);
				top.opener.afterQuery( arrReturn );
				//alert("a");
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();
		
	}
}
function getQueryResult()
{
	var arrSelected = null;
	tRow = LDUWUserGrid.getSelNo();
	//alert("111" + tRow);
	//edit by guo xiang at 2004-9-13 17:54
	//if( tRow == 0 || tRow == null || arrDataSet == null )
	if( tRow == 0 || tRow == null )
	    return arrSelected;
	
	arrSelected = new Array();
	
	//������Ҫ���ص�����
	//edit by guo xiang at 2004-9-13 17:54
	arrSelected[0] = new Array();
	arrSelected[0] = LDUWUserGrid.getRowData(tRow-1);
	//arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

function afterQuery(arrQueryResult)
{
	 if( arrQueryResult != null )
	 {
//	 	var strSQL="select * from LDUWUser where UserCode='"+arrQueryResult[0][0]+"'";
	 	
		var sqlid1="LDIndUWUserQueryInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
		var strSQL=mySql1.getString();
	 	
	 	arrResult = easyExecSql(strSQL);
	 	document.all('UserCode').value= arrResult[0][0];
                document.all('UWType').value= arrResult[0][1];
                document.all('UpUserCode').value= arrResult[0][2];
                document.all('UpUWPopedom').value= arrResult[0][3];
                
               
        
                document.all('UWPopedom').value= arrResult[0][7];
                
     
                document.all('Remark').value= arrResult[0][15];
                document.all('Operator').value= arrResult[0][16];
                document.all('ManageCom').value= arrResult[0][17];
                document.all('MakeDate').value= arrResult[0][18];
                document.all('MakeTime').value= arrResult[0][19];
                document.all('ModifyDate').value= arrResult[0][20];
                document.all('ModifyTime').value= arrResult[0][21];
                
//                strSQL2="select UWState,UWStateName from LDUWGrade where UserCode='"+arrQueryResult[0][0]+"'";
                
        		var sqlid2="LDIndUWUserQueryInputSql2";
        		var mySql2=new SqlClass();
        		mySql2.setResourceName("config.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
        		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
        		mySql2.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        		strSQL2=mySql2.getString();
                
                turnPage.queryModal(strSQL2, UWResultGrid);
               
//                strSQL2="select UWKind,MaxAmnt from LDUWAmntGrade where UserCode='"+arrQueryResult[0][0]+"'";
                
        		var sqlid3="LDIndUWUserQueryInputSql3";
        		var mySql3=new SqlClass();
        		mySql3.setResourceName("config.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
        		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
        		mySql3.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        		strSQL2=mySql3.getString();
                
                turnPage.queryModal(strSQL2, UWMaxAmountGrid);
                showCodeName();
        }
}
function showCodeName()
{
	showCodeName('UWType','UWType','UWTypeName');
	showCodeName('uwpopedom','UWPopedom','UWPopedomName');
	showCodeName('edpopedom','edpopedom','edpopedomName');
	showCodeName('clPopedom','ClaimPopedom','claimpopedomName');
	showCodeName('upuwpopedom','UpUWPopedom','UpUserCodeName');
}
            