//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPagePayGrid = new turnPageClass();
var showInfo;
var mDebug="0";
//var mSwitch = parent.VD.gVSwitch;
var tDisplay;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var turnPage = new turnPageClass();


function initQueryGo()
{
    //var strsql="select l.TempFeeNo,c.PayMoney,l.APPntName,c.InBankAccNo,c.InAccName,c.IDNo,l.PayDate from LJTempFee l,LJTempFeeClass c where l.TempFeeNo=c.TempFeeNo and l.APPntName =(select name from ldperson where CustomerNo='"+ customerNo+"')" ;
	//var strsql="select l.TempFeeNo,c.PayMoney,l.APPntName,c.InBankAccNo,c.InAccName,c.IDNo,l.PayDate from LJTempFee l,LJTempFeeClass c,ldperson p where l.TempFeeNo=c.TempFeeNo and l.APPntName= p.name and p.CustomerNo ='"+ customerNo+"'" ;
	/**
    if(fm.all('TempFeeType').value!=""||fm.all('PayMode').value!=""||fm.all('InBankAccNo').value!=""||fm.all('PayMoney').value!=""){
    	if(fm.all('TempFeeType').value!=""&&fm.all('TempFeeType').value!=null){
     		strsql=strsql+" and l.TempFeeType ='"+fm.all('TempFeeType').value+"'";
    	}
    	if(fm.all('PayMode').value!=""&&fm.all('PayMode').value!=null){
     		strsql=strsql+" and c.PayMode ='"+fm.all('PayMode').value+"'";
    	}
    	if(fm.all('InBankAccNo').value!=""&&fm.all('InBankAccNo').value!=null){
    		 strsql=strsql+" and c.InBankAccNo ='"+fm.all('InBankAccNo').value+"'";
    	}
    	if(fm.all('PayMoney').value!=""&&fm.all('PayMoney').value!=null){
     		strsql=strsql+" and c.PayMoney ='"+fm.all('PayMoney').value+"'";
    	}
    }
   */
    var mySql1=new SqlClass();
		mySql1.setResourceName("sys.PayQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("PayQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
		mySql1.addSubPara(fm.all('TempFeeType').value);//ָ������Ĳ���
		mySql1.addSubPara(fm.all('PayMode').value);//ָ������Ĳ���
		mySql1.addSubPara(fm.all('InBankAccNo').value);//ָ������Ĳ���
		mySql1.addSubPara(fm.all('PayMoney').value);//ָ������Ĳ���
	var strsql= mySql1.getString();
	
    try
    {
        turnPagePayGrid.queryModal(strsql, PayGrid);
    }
    catch (ex) {
    alert(ex);
    }
}
