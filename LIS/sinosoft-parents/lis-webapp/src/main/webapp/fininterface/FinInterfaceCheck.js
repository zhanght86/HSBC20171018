//�������ƣ�FinInterfaceCheck.js
//�����ܣ�ƾ֤�˶�
//�������ڣ�2007-10-23 
//������  ��m
//���¼�¼��  ������    ��������     ����ԭ��/����

var turnPage = new turnPageClass();//�ս������������
var turnPage2 = new turnPageClass();//�ս�ȷ���������
var mFlag = "0";
var showInfo;

/****************************************************
*����������ݲ���ӿڱ������ϸ���ڼ��ͳ��
*********************************************************/ 
function ToExcel()
{
	if(CheckQueryDataGrid.mulLineCount=="0"){
		alert("û�в�ѯ������");
		return false;
	} 	 	
	fm.action="./FinInterfaceCheckExcel.jsp";
	fm.target="fraSubmit";
	document.getElementById("fm").submit(); //�ύ
}
/************************
*PDF��ӡ����ʵ�� 
***************************/
function printFinInterface(){
	
	if(CheckQueryDataGrid.getSelNo()){	  	
		    var i = 0;
			var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		 	fm.action = "./TestInterfacePrintPDF.jsp?operator=printPDF";//����PDF��ӡ�������
		 	fm.target=".";	 
		 	document.all("serialNo").value = CheckQueryDataGrid.getRowColData(CheckQueryDataGrid.getSelNo()-1, 1);
		 	document.getElementById("fm").submit();
		 	showInfo.close();  
      }else{
        alert("��ѡ��һ����¼!");
      }

}
/********************
*��ҳ�����������
************************/
function beforeSubmit(){
	if(fm.checkType.value==''||fm.checkType.value==null){
		alert("��ѡ��˶�����");
		fm.checkType.focus();
		return false;
	}
	  	
   if(fm.checkType.value=="1"){
	  if(fm.accountCode.value==''||fm.accountCode.value==null){
	  			alert("��ѡ���Ŀ���ͼ���Ŀ");
	  			fm.accountCodeType.focus();
	  			return false;
	  	}	  	
	  if(fm.FinItemType.value==''||fm.FinItemType.value==null){
	  			alert("��ѡ������־");
	  			fm.FinItemType.focus();
	  			return false;
	  	}
	  if(fm.ManageCom.value==''||fm.ManageCom.value==null){
	  			alert("��ѡ��������");
	  			fm.ManageCom.focus();
	  			return false;
	  	}		  	  	
	  if(fm.StartDay.value==''||fm.StartDay.value==null){
	  			alert("��ѡ����ʼ����");
	  			fm.StartDay.focus();
	  			return false;
	  	}
	  if(fm.EndDay.value==''||fm.EndDay.value==null){
	  			alert("��ѡ����ֹ����");
	  			fm.EndDay.focus();
	  			return false;
	  	}
	  if(fm.EndDay.value<fm.StartDay.value){
					alert("��ʼʱ�������ֹʱ��,����������!");
					fm.StartDay.focus();
					return false;
	����}	  		  		  		  	
	 }
   else if(fm.checkType.value=="2"){
 	  if(fm.ContType.value==''||fm.ContType.value==null){
	  			alert("������ҵ��������ͣ�");
	  			fm.ContType.focus();
	  			return false;
	  	}    
	  if(fm.tNo.value==''||fm.tNo.value==null){
	  			alert("������ҵ����룡");
	  			fm.tNo.focus();
	  			return false;
	  	}
	 }	  
   else if(fm.checkType.value=="3"){
 	  if(fm.SClassType.value==''||fm.SClassType.value==null){
	  			alert("������ƾ֤���ͣ�");
	  			fm.SClassType.focus();
	  			return false;
	  	}    
	  if(fm.SDate.value==''||fm.SDate.value==null){
	  			alert("��������ʼ���ڣ�");
	  			fm.SDate.focus();
	  			return false;
	  	}
	  if(fm.EDate.value==''||fm.EDate.value==null){
	  			alert("������������ڣ�");
	  			fm.EDate.focus();
	  			return false;
	  	}
	 }	
	else if (fm.checkType.value=="4")
		{
				  if(fm.AccountID.value==''||fm.AccountID.value==null){
	  			alert("������ƾ֤���룡");
	  			fm.AccountID.focus();
	  			return false;
	  	}
		}
	return true;
}

/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *********************************************************************/
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //ִ����һ������
  }
}
// add 2006-9-30 11:49
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

function CheckQueryData()
{
  try
  {
	if(beforeSubmit()){
	
		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
	CheckQueryDataGrid.clearData("CheckQueryDataGrid");   
	var strSQL = "";
	/**
		strSQL="select CostId,CostName from FICostTypeDef order by CostId";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FinInterfaceCheckSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(1);//ָ������Ĳ���
		strSQL= mySql1.getString();
	
			var strQueryResultC = easyQueryVer3(strSQL,1,1,1);
			var	tArrayC = decodeEasyQueryResult(strQueryResultC);

    if(fm.checkType.value=="2"){//�˶�����^1|��Ŀ ^2|ҵ�����	
			
		var tNoType = "";
		if(fm.ContType.value=="1"||fm.ContType.value=="3")  //���ձ����ź͸���Ͷ������
			tNoType = "a.businessno03";
		else if(fm.ContType.value=="2"||fm.ContType.value=="4") //���ձ����ź�����Ͷ������
			tNoType = "a.grpcontno";
		else if(fm.ContType.value=="5") //�ݽ��Ѻ�
			tNoType = "a.BusinessNo";
		else if(fm.ContType.value=="6") //ʵ����
			tNoType = "a.BusinessNo06";
		else if(fm.ContType.value=="7") //ʵ�պ�
			tNoType = "a.BusinessNo05";
		else if(fm.ContType.value=="8") //���ձ�ȫ������
			tNoType = "a.BusinessNo07";
		else if(fm.ContType.value=="9") //���ձ�ȫ������
			tNoType = "a.endorsementno";
		else if(fm.ContType.value=="10") //�ⰸ��
			tNoType = "a.BusinessNo08";
		/**	
		strSQL = "select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b "
	       		 +" where a.aserialno = b.aserialno "
	       		 +" and b.summoney<>0"
             +" and a.Stringinfo01 like '" +manageCom + "%%'";
		*/
			var mySql2=new SqlClass();
				mySql2.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql2.setSqlId("FinInterfaceCheckSql2");//ָ��ʹ�õ�Sql��id
				mySql2.addSubPara(manageCom);//ָ������Ĳ���
				strSQL= mySql2.getString();
		if(fm.ContType.value=="8"){
		/**
			strSQL = strSQL + "and "+tNoType+" in trim((select edoracceptno from lpedormain where edorno = '" + trim(fm.tNo.value) + "'))";
			*/
			var mySql3=new SqlClass();
				mySql3.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql3.setSqlId("FinInterfaceCheckSql3");//ָ��ʹ�õ�Sql��id
				mySql3.addSubPara(manageCom);//ָ������Ĳ���
				mySql3.addSubPara(tNoType);//ָ������Ĳ���
				mySql3.addSubPara(fm.tNo.value);//ָ������Ĳ���
				strSQL= mySql3.getString();
			}
		else if (fm.ContType.value=="9"){
		/**
		  strSQL = strSQL + "and "+tNoType+" in trim((select edoracceptno from lpedormain where edorno = '" + trim(fm.tNo.value) + "'))";
		  	*/
		  	var mySql4=new SqlClass();
				mySql4.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql4.setSqlId("FinInterfaceCheckSql4");//ָ��ʹ�õ�Sql��id
				mySql4.addSubPara(manageCom);//ָ������Ĳ���
				mySql4.addSubPara(tNoType);//ָ������Ĳ���
				mySql4.addSubPara(fm.tNo.value);//ָ������Ĳ���
				strSQL= mySql4.getString();
		  }	
		else{
		/**
			strSQL = strSQL + " and "+ tNoType +" = '" + trim(fm.tNo.value) + "'";
			*/
			var mySql5=new SqlClass();
				mySql5.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql5.setSqlId("FinInterfaceCheckSql5");//ָ��ʹ�õ�Sql��id
				mySql5.addSubPara(manageCom);//ָ������Ĳ���
				mySql5.addSubPara(tNoType);//ָ������Ĳ���
				mySql5.addSubPara(fm.tNo.value);//ָ������Ĳ���
				strSQL= mySql5.getString();
		}
			/**
			strSQL = strSQL +" order by orzno,accountdate";
			*/
    }
    else if(fm.checkType.value=="1"){//�˶�����^1|��Ŀ ^2|ҵ�����

       	strSQL =  "select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b "
	       		 +" where a.aserialno = b.aserialno "
	       		 +" and b.accountcode like '"+fm.accountCode.value+"%%'"
	       		 +" and b.summoney<>0"
             +" and a.Stringinfo01 like '" +fm.ManageCom.value + "%%'"
       		   +" and a.accountdate >= '" +fm.StartDay.value + "'"
       		   +" and a.accountdate <= '" +fm.EndDay.value + "'";
			var mySql6=new SqlClass();
				mySql6.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql6.setSqlId("FinInterfaceCheckSql6");//ָ��ʹ�õ�Sql��id
				mySql6.addSubPara(fm.accountCode.value);//ָ������Ĳ���
				mySql6.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
				mySql6.addSubPara(fm.StartDay.value);//ָ������Ĳ���
				mySql6.addSubPara(fm.EndDay.value);//ָ������Ĳ���
				strSQL= mySql6.getString();
//      if (fm.accountCode.value == '1001/01/'||fm.accountCode.value == '1001/02/')
//      {
//      	strSQL = strSQL + " and substr(b.accountcode,1,8) = '" + fm.accountCode.value + "'";
//      	}
//    else
//    	{
//    		strSQL = strSQL + " and substr(b.accountcode,1,4) = '" + fm.accountCode.value + "'";
//    		} 		   
    		
      if (fm.FinItemType.value == null || fm.FinItemType.value == '') 	
      {
      /**
      		strSQL = strSQL + " order by accountdate,orzno";
      		*/
      		var mySql7=new SqlClass();
				mySql7.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql7.setSqlId("FinInterfaceCheckSql7");//ָ��ʹ�õ�Sql��id
				mySql7.addSubPara(fm.accountCode.value);//ָ������Ĳ���
				mySql7.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
				mySql7.addSubPara(fm.StartDay.value);//ָ������Ĳ���
				mySql7.addSubPara(fm.EndDay.value);//ָ������Ĳ���
				strSQL= mySql7.getString();
      }
      else
    	{	   
    	/**
	  	strSQL = strSQL 
	  	       + " and b.FinItemType = '"  +fm.FinItemType.value + "'"
       		   + " order by accountdate,orzno";
       		   */
       		var mySql8=new SqlClass();
				mySql8.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql8.setSqlId("FinInterfaceCheckSql8");//ָ��ʹ�õ�Sql��id
				mySql8.addSubPara(fm.accountCode.value);//ָ������Ĳ���
				mySql8.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
				mySql8.addSubPara(fm.StartDay.value);//ָ������Ĳ���
				mySql8.addSubPara(fm.EndDay.value);//ָ������Ĳ���
				mySql8.addSubPara(fm.FinItemType.value);//ָ������Ĳ���
				strSQL= mySql8.getString();	   
      }
    }
    else if(fm.checkType.value=="3"){

			//var cSQL = easyQueryVer3("select keyid from liclasstypekeydef where classtype = '"+document.all("SClassType").value+"'",1,1,1);
			//var	cArray = decodeEasyQueryResult(cSQL);
			/**
			strSQL ="select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b "
	       		 +" where a.aserialno = b.aserialno "
	       		 +" and b.summoney<>0"
       			 +" and b.certificateid in ( select Code From FiCodeTrans where Codetype = 'BigCertificateID' and codeAlias = '" +document.all("SClassType").value+"')"
       			 +" and a.Stringinfo01 like '" +fm.ManageCom1.value + "%%'"
       			 +" and b.accountdate >= '" +fm.SDate.value + "'"
       			 +" and b.accountdate <= '" +fm.EDate.value + "'"
       		   +" order by a.businessno,accountdate";
       		   */
       		var mySql9=new SqlClass();
				mySql9.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql9.setSqlId("FinInterfaceCheckSql10");//ָ��ʹ�õ�Sql��id
				mySql9.addSubPara(document.all("SClassType").value);//ָ������Ĳ���
				mySql9.addSubPara(fm.ManageCom1.value);//ָ������Ĳ���
				mySql9.addSubPara(fm.SDate.value);//ָ������Ĳ���
				mySql9.addSubPara(fm.EDate.value);//ָ������Ĳ���
				strSQL= mySql9.getString();
    	}
    else if(fm.checkType.value=="4"){
			/**
			strSQL ="select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b,FIDataTransGather c "
             +"where a.aserialno = b.aserialno "
             +"and a.batchno = b.batchno "
             +"and b.batchno = c.batchno "
             +"and b.managecom = c.managecom "
             +"and b.accountdate = c.accountdate "
             +"and b.certificateid in (select Code From FiCodeTrans where Codetype = 'BigCertificateID' and codeAlias = c.certificateid) "
             +"and a.Stringinfo01 like '" +manageCom + "%%' "
             +"and c.voucherno = '"+fm.AccountID.value+"' "
             +"and b.summoney<>0 "
             +"order by a.businessno,accountdate";
             */
            var mySql10=new SqlClass();
				mySql10.setResourceName("fininterface.FinInterfaceCheckSql"); //ָ��ʹ�õ�properties�ļ���
				mySql10.setSqlId("FinInterfaceCheckSql14");//ָ��ʹ�õ�Sql��id
				mySql10.addSubPara(manageCom);//ָ������Ĳ���
				mySql10.addSubPara(fm.AccountID.value);//ָ������Ĳ���
				strSQL= mySql10.getString();
    	}
	fm.ExportExcelSQL.value=strSQL;//����sql������excelʱ�õ�
	turnPage.queryModal(strSQL,CheckQueryDataGrid);

/*var strSQL = "select '1' as batchno,"
       +"'1' as managecom,"
       +"'1' as listflag,"
       +"'1' as accountcode,"
       +"'1' as bankdetail,"
       +"'1' as riskcode,"
       +"'1' as salechnl,"
       +"'1' as classid,"
       +"'1' as classname,"
       +"'1' as accountdate,"
       +"'1' as contno,"
       +"'1' as money"
  	   +" from lidatatransresult a"
       +" where 1=1";

    if(fm.checkType.value=="2"){//�˶�����^1|��Ŀ ^2|ҵ�����
       	if(fm.ContType.value=="1"){//����
       		if(fm.ContNo.value!="" && fm.ContNo.value!=null)
       		{
       			strSQL= strSQL +" and ContNo='" + fm.ContNo.value + "'";
       		}
       		else if(fm.PrtNo.value!="" && fm.PrtNo.value!=null)
       		{
       			strSQL= strSQL +" and ContNo=(select contno from lccont where ContType='1' and prtno='" + fm.PrtNo.value + "')";      		
       		}
       }
       	if(fm.ContType.value=="2"){//�ŵ�
       		if(fm.ContNo.value!="" && fm.ContNo.value!=null)
       		{
       			strSQL= strSQL +" and ContNo='" + fm.ContNo.value + "'";
       		}
       		else if(fm.PrtNo.value!="" && fm.PrtNo.value!=null)
       		{
				strSQL= strSQL +" and ContNo=(select distinct GrpContNo from lccont where ContType='2' and prtno='" + fm.PrtNo.value + "')";      		
       		}
      }
    }
    else if(fm.checkType.value=="1"){//�˶�����^1|��Ŀ ^2|ҵ�����
       	strSQL= strSQL + " and 2=2";
    }
    else{}
	fm.ExportExcelSQL.value=strSQL;//����sql������excelʱ�õ�
	fm.temptext.value=strSQL;
//	alert(fm.ExportExcelSQL.value);
	turnPage.queryModal(strSQL,CheckQueryDataGrid);
*/
	showInfo.close();
	
	if(CheckQueryDataGrid.mulLineCount=="0"){
		alert("û�в�ѯ������");
	}		 
	return true;		
	}
  }
  catch(ex)
  {
     alert(ex);
   }
}

function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "checkType") 
	{  
		if(fm.checkType.value=="1"){
			document.all("divSearch1").style.display = '';
			document.all("divSearch2").style.display = 'none';
			document.all("divSearch3").style.display = 'none';
			document.all("divSearch4").style.display = 'none';
		}else if(fm.checkType.value=="2"){
			document.all("divSearch2").style.display = '';
			document.all("divSearch1").style.display = 'none';	
			document.all("divSearch3").style.display = 'none';		
			document.all("divSearch4").style.display = 'none';
		}else if(fm.checkType.value=="3"){
			document.all("divSearch3").style.display = '';
			document.all("divSearch2").style.display = 'none';
			document.all("divSearch1").style.display = 'none';	
			document.all("divSearch4").style.display = 'none';		
		}else if(fm.checkType.value=="4"){
			document.all("divSearch4").style.display = '';
			document.all("divSearch2").style.display = 'none';
			document.all("divSearch1").style.display = 'none';	
			document.all("divSearch3").style.display = 'none';		
		}
		
	}
}

//��ʾ���ݵĺ�������easyQuery��MulLine һ��ʹ��
function showRecord(strRecord)
{

  //�����ѯ����ַ���
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
    
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray = new Array(0,9,4,1,7);
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //��ʼ���Ķ���
  	 turnPage.pageDisplayGrid = CheckQueryDataGrid;       
              
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}
