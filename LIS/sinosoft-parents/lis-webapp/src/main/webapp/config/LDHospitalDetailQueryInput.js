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
	var iHeight=350;     //�������ڵĸ߶�; 
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
	var iHeight=350;     //�������ڵĸ߶�; 
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

//  var strSql = "select hospitcode,a.hospitalname,a.hospitalgrade,mngcom,a.validitydate,a.address "
//         		 + " from  LDHospital a where 1=1 "
//             + getWherePart("hospitcode", "HospitalCode")
//             + getWherePart("hospitalname", "HospitalName",'like')
//             + getWherePart("hospitalgrade", "HospitalGrade")
//             + getWherePart("mngcom", "ManageCom",'like')
//             + getWherePart("validitydate", "ValidityDate",'<=')      
//             + " order by hospitcode ";
  
	var  HospitalCode0 = window.document.getElementsByName(trim("HospitalCode"))[0].value;
  	var  HospitalName0 = window.document.getElementsByName(trim("HospitalName"))[0].value;
  	var  HospitalGrade0 = window.document.getElementsByName(trim("HospitalGrade"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  ValidityDate0 = window.document.getElementsByName(trim("ValidityDate"))[0].value;
	var sqlid0="LDHospitalDetailQueryInitSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDHospitalDetailQueryInitSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(HospitalCode0);//ָ������Ĳ���
	mySql0.addSubPara(HospitalName0);//ָ������Ĳ���
	mySql0.addSubPara(HospitalGrade0);//ָ������Ĳ���
	mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(ValidityDate0);//ָ������Ĳ���
	var strSql=mySql0.getString(); 
  
  //alert(strSql);
	turnPage.queryModal(strSql, LDHospitalGrid);
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
	var tSel = LDHospitalGrid.getSelNo();
	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				//alert(tSel);
				arrReturn[0] = new Array();
			 arrReturn[0]  = LDHospitalGrid.getRowData(tSel-1);
			 //alert(arrReturn[0][0]);
			 if(arrReturn[0][0]=="")
			 {
			 	 alert("���Ȳ�ѯ,�ٷ���!");
			 	 return false;
			 }
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
	tRow = LDHospitalGrid.getSelNo();
	alert("111" + tRow);
	//edit by guo xiang at 2004-9-13 17:54
	//if( tRow == 0 || tRow == null || arrDataSet == null )
	if( tRow == 0 || tRow == null )
	    return arrSelected;
	
	arrSelected = new Array();
	
	//������Ҫ���ص�����
	//edit by guo xiang at 2004-9-13 17:54
	arrSelected[0] = new Array();
	arrSelected[0] = LDHospitalGrid.getRowData(tRow-1);

	return arrSelected;
}

function afterQuery(arrQueryResult)
{
	 if( arrQueryResult != null )
	 {
	 	alert(1)
//	 	var strSQL="select * from LDUWUser where UserCode='"+arrQueryResult[0][0]+"'";
	 	
		var sqlid1="LDHospitalDetailQueryInitSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.LDHospitalDetailQueryInitSql"); //ָ��ʹ�õ�properties�ļ���
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
                
        		var sqlid2="LDHospitalDetailQueryInitSql2";
        		var mySql2=new SqlClass();
        		mySql2.setResourceName("config.LDHospitalDetailQueryInitSql"); //ָ��ʹ�õ�properties�ļ���
        		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
        		mySql2.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        		strSQL2=mySql2.getString(); 
                
                turnPage.queryModal(strSQL2, UWResultGrid);
               
//                strSQL2="select UWKind,MaxAmnt from LDUWAmntGrade where UserCode='"+arrQueryResult[0][0]+"'";
                
        		var sqlid3="LDHospitalDetailQueryInitSql3";
        		var mySql3=new SqlClass();
        		mySql3.setResourceName("config.LDHospitalDetailQueryInitSql"); //ָ��ʹ�õ�properties�ļ���
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
            