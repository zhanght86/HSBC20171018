var turnPage = new turnPageClass();
var mAction ="";
/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 
function getInsuredDetail(parm1,parm2)
{	//alert("parm1: "+parm1+"   parm2: "+parm2);
    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;
//    alert("InsuredNo="+InsuredNo);
    var ContNo = fm.ContNo.value;
    emptyInsured();//���������¼���������¸�ֵ
    //��������ϸ��Ϣ
	
		var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
	    var strSQL=mySql1.getString();	
	
//    var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
	//������LDPerson����δ����ͻ������---����������---����Ϣ���ʴ�LCCustomerImpartParams���ͻ���֪��������ѯ
	 //��ѯ��������ͬ��,�ͻ����룬�ͻ�����<1--�ͻ�����Ϊ������>����֪����<02--������֪>����֪���<000>��
	 
	 	var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(ContNo);//ָ������Ĳ���
		mySql2.addSubPara(InsuredNo);//ָ������Ĳ���
	    var ssql=mySql2.getString();	
	 
//	 var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
//		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
//		  +" and tt.impartver='02' and tt.impartcode='000'";
	 var ssArr=easyExecSql(ssql);
	try
	{
	 arrResult[0][13]=ssArr[0][1];//���
	 arrResult[0][14]=ssArr[1][1];//����		
	}catch(ex){};
    if(arrResult!=null)
    {
        displayInsuredInfo();
    }
	
		 var sqlid3="DSHomeContSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(prtNo);//ָ������Ĳ���
		mySql3.addSubPara(InsuredNo);//ָ������Ĳ���
	   strSQL=mySql3.getString();	
	
//    strSQL ="select * from LCInsured where ContNo = '"+prtNo+"' and InsuredNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
    fm.InsuredAddressNo.value=tAddressNo;
    //��ʾ�����˵�ַ��Ϣ
    getdetailaddress2();
    getInsuredPolInfo();
    getImpartInfo();
    getAge2();
    showInsuredCodeName();//��ʾ������Ŀ�ĺ�����Ϣ
    //��¼�������в���Ҫ���пͻ��ϲ�����ע�͵���hl 20050505
    //����Ǹ���״̬��������ظ��ͻ�У��
    if(LoadFlag=="5" )
    {
      InsuredChkNew();
      return;
    }
}

function displayContInfo(arrResultCont){
	try{
		fm.ContNo.value             = arrResultCont[0][1];
		fm.InputNo.value            = arrResultCont[0][2];
		fm.contFillNo.value         = arrResultCont[0][3];
		fm.ProposalGrpContNo.value  = arrResultCont[0][4];
		fm.ContType.value           = arrResultCont[0][6];
		fm.FamilyID.value           = arrResultCont[0][7];
		fm.AgentCom.value     		= arrResultCont[0][13];
		fm.AgentCode.value          = arrResultCont[0][14];
		fm.SaleChnl.value           = arrResultCont[0][18];
		fm.PayIntv.value            = arrResultCont[0][21];
		fm.UWOperator.value         = arrResultCont[0][56];
		fm.UWDate.value             = arrResultCont[0][57];
		fm.PolApplyDate.value       = arrResultCont[0][60];
		fm.FirstTrialOperator.value = arrResultCont[0][70];
		fm.FirstTrialDate.value     = arrResultCont[0][71];
		fm.Handler.value         	= arrResultCont[0][19];
		fm.AgentComName.value 		= arrResultCont[0][89];
		fm.SignAgentName.value      = arrResultCont[0][90];
		fm.AgentSignDate.value      = arrResultCont[0][91];
		fm.ImpartRemark.value		= arrResultCont[0][95];
		fm.ReleaseFlag.value 		= arrResultCont[0][92];
		fm.Amnt.value        		= arrResultCont[0][42];
		fm.Prem.value        		= arrResultCont[0][41];
		fm.Password.value    		= arrResultCont[0][20];
		fm.SumPrem.value     		= arrResultCont[0][43];
		fm.PayIntv.value     		= arrResultCont[0][21];
		fm.OutPayFlag.value  		= arrResultCont[0][25];
		fm.PayMode.value     		= arrResultCont[0][22];
		fm.PayLocation.value 		= arrResultCont[0][23];
		fm.BankCode.value 			= arrResultCont[0][31];
		fm.AccName.value     		= arrResultCont[0][33];
		fm.BankAccNo.value   		= arrResultCont[0][32];
		fm.AutoPayFlag.value 		= arrResultCont[0][86];
		}catch(ex){
			alert("��ʾ��ͬ��Ϣʱ����");
		}
}
//��ʾ������Ϣ
function displayPol(arrResultPol){
	
	fm.GetYear.value      = arrResultPol[0][37];
	fm.LiveGetMode.value  = arrResultPol[0][82];
	fm.BonusGetMode.value = arrResultPol[0][84];
	fm.StandbyFlag3.value = arrResultPol[0][102];
	fm.GetLimit.value     = arrResultPol[0][118];
	
}
//������Ҫ��ʾ����Ϣ
function displayOther(){
	
	    var sqlid4="DSHomeContSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(prtNo);//ָ������Ĳ���
		mySql4.addSubPara(InsuredNo);//ָ������Ĳ���
	   var tSqlName=mySql4.getString();	
	
//	var tSqlName = "select AppSignName,InsSignName2,ManageCom from lbpocont where contno='"+prtNo+"' and inputno='"+InputNo+"'";
	var arrResultName;
	arrResultName=easyExecSql(tSqlName);
	if(arrResultName!=null){
		fm.AppSignName.value = arrResultName[0][0];
		if(arrResultName[0][1]!=null){
			fm.InsSignName2.value = arrResultName[0][1];
		}
		//fm.ManageCom.value = arrResultName[0][2];
		//fm.ScanCom.value   = arrResultName[0][2];
	}
	
}
//��ʾͶ������Ϣ
function displayAppnt(arrResultInsured){
	
		 var sqlid5="DSHomeContSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(prtNo);//ָ������Ĳ���
		mySql5.addSubPara(InputNo);//ָ������Ĳ���
	   var tsql=mySql5.getString();	
	
//	var tsql ="select appntno from lbpoappnt where contno='"+prtNo+"' and inputno='"+InputNo+"'";
	var arrResultAppntNo;//prompr("",tsql);
	arrResultAppntNo=easyExecSql(tsql);
	fm.AppntNo.value           = arrResultAppntNo[0][0];
	fm.RelationToInsured.value = arrResultInsured[0][10];
	fm.AppntName.value         = arrResultInsured[0][14];
	fm.AppntSex.value          = arrResultInsured[0][15];
	fm.AppntBirthday.value     = arrResultInsured[0][16];
	fm.AppntIDType.value       = arrResultInsured[0][17];
	fm.AppntIDNo.value         = arrResultInsured[0][18];
	fm.AppntNativePlace.value  = arrResultInsured[0][19];
	fm.AppntRgtAddress.value   = arrResultInsured[0][21];
	fm.AppntMarriage.value     = arrResultInsured[0][22];
	fm.AppntOccupationCode.value=arrResultInsured[0][37];
	fm.AppntWorkType.value     = arrResultInsured[0][38];
	fm.AppntPluralityType.value= arrResultInsured[0][39];
	fm.AppntPostalAddress.value= arrResultInsured[0][72];
	fm.AppntZipCode.value      = arrResultInsured[0][71];
	fm.AppntHomeAddress.value  = arrResultInsured[0][68];
	fm.AppntHomeZipCode.value  = arrResultInsured[0][67];
	fm.AppntCompanyPhone.value = arrResultInsured[0][62];
	fm.AppntMobile.value       = arrResultInsured[0][60];
	fm.AppntEMail.value        = arrResultInsured[0][59];
	fm.CompanyAddress.value      = arrResultInsured[0][64];
	fm.AppntFillNo.value       = arrResultInsured[0][3];
}
//��ʾ��������Ϣ
function displayInsured(arrResultInsured){
	fm.InputNo.value         = arrResultInsured[0][2];
	fm.InsuredNo.value       = arrResultInsured[0][4];
	fm.InsuredFillNo.value   = arrResultInsured[0][3];
	fm.RelationToAppnt.value = arrResultInsured[0][11];
	fm.InsuredAddressNo.value= arrResultInsured[0][12];
	fm.SequenceNo.value      = arrResultInsured[0][13];
	fm.Name.value            = arrResultInsured[0][14];
	fm.Sex.value             = arrResultInsured[0][15];
	fm.Birthday.value        = arrResultInsured[0][16];
	fm.IDType.value          = arrResultInsured[0][17];
	fm.IDNo.value            = arrResultInsured[0][18];
	fm.NativePlace.value     = arrResultInsured[0][19];
	fm.RgtAddress.value      = arrResultInsured[0][21];
	fm.Marriage.value        = arrResultInsured[0][22];
	fm.OccupationCode.value  = arrResultInsured[0][37];
	fm.WorkType.value        = arrResultInsured[0][38];
	fm.PluralityType.value   = arrResultInsured[0][39];
	fm.PostalAddress.value   = arrResultInsured[0][72];
	fm.ZipCode.value		 = arrResultInsured[0][71];
	fm.HomeAddress.value	 = arrResultInsured[0][68];
	fm.HomeZipCode.value	 = arrResultInsured[0][67];
	fm.CompanyPhone.value	 = arrResultInsured[0][62];
	fm.Mobile.value			 = arrResultInsured[0][60];
	fm.EMail.value			 = arrResultInsured[0][59];
	fm.CompanyAddress.value		 = arrResultInsured[0][64];
}
//Ͷ��������
function getAge()
{
	if(fm.PolApplyDate.value=="")
	{
		return;
	}
	if(fm.PolApplyDate.value.indexOf('-')==-1)
	{
		var Year =fm.PolApplyDate.value.substring(0,4);
		var Month =fm.PolApplyDate.value.substring(4,6);
		var Day =fm.PolApplyDate.value.substring(6,8);
		fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.PolApplyDate.value)<0)
	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.AppntAge.value="";
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolApplyDate.value);
  	return ;
}
//������������<����������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge2()
{	
	if(fm.Birthday.value=="")
	{
		return;
	}
	if(fm.Birthday.value.indexOf('-')==-1)
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(4,6);
		var Day =      fm.Birthday.value.substring(6,8);
		fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     fm.Birthday.value.substring(0,4);
	    var Month1 =    fm.Birthday.value.substring(5,7);
	    var Day1 =      fm.Birthday.value.substring(8,10);	
	    fm.Birthday.value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(fm.Birthday.value)<0)
  	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.InsuredAppAge.value="";
		return;
    }
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}
function displayBnf(){
	
} 
//��ͬ��Ͷ����������Ϣ������
function saveInsured(wFlag){
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	if(fm.SequenceNo.value=="1"){
		//fm.AppntNo.value           = fm.InsuredNo.value;
		fm.RelationToInsured.value = "00";
		fm.AppntName.value         = fm.Name.value;
		fm.AppntSex.value          = fm.Sex.value;
		fm.AppntBirthday.value     = fm.Birthday.value;
		fm.AppntIDType.value       = fm.IDType.value;
		fm.AppntIDNo.value         = fm.IDNo.value;
		fm.AppntNativePlace.value  = fm.NativePlace.value;
		fm.AppntRgtAddress.value   = fm.RgtAddress.value;
		fm.AppntMarriage.value     = fm.AppntMarriage.value;
		fm.AppntOccupationCode.value=fm.OccupationCode.value;
		fm.AppntWorkType.value     = fm.WorkType.value;
		fm.AppntPluralityType.value= fm.PluralityType.value;
		fm.AppntPostalAddress.value= fm.PostalAddress.value;
		fm.AppntZipCode.value      = fm.ZipCode.value;
		fm.AppntHomeAddress.value  = fm.HomeAddress.value;
		fm.AppntHomeZipCode.value  = fm.HomeZipCode.value;
		fm.AppntCompanyPhone.value = fm.CompanyPhone.value;
		fm.AppntMobile.value       = fm.Mobile.value;
		fm.AppntEMail.value        = fm.EMail.value;
		fm.AppntCompanyAddress.value      = fm.CompanyAddress.value;
		fm.AppntFillNo.value       = "1";
	}
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="INSERT";
	document.all( 'fmAction' ).value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.submit(); //�ύ
}
//��ͬ��Ͷ�������ˣ��޸�
function updateInsured(wFlag){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	
	if(fm.SequenceNo.value=="1"){
		//fm.AppntNo.value           = fm.InsuredNo.value;
		fm.RelationToInsured.value = "00";
		fm.AppntName.value         = fm.Name.value;
		fm.AppntSex.value          = fm.Sex.value;
		fm.AppntBirthday.value     = fm.Birthday.value;
		fm.AppntIDType.value       = fm.IDType.value;
		fm.AppntIDNo.value         = fm.IDNo.value;
		fm.AppntNativePlace.value  = fm.NativePlace.value;
		fm.AppntRgtAddress.value   = fm.RgtAddress.value;
		fm.AppntMarriage.value     = fm.Marriage.value;
		fm.AppntOccupationCode.value=fm.OccupationCode.value;
		fm.AppntWorkType.value     = fm.WorkType.value;
		fm.AppntPluralityType.value= fm.PluralityType.value;
		fm.AppntPostalAddress.value= fm.PostalAddress.value;
		fm.AppntZipCode.value      = fm.ZipCode.value;
		fm.AppntHomeAddress.value  = fm.HomeAddress.value;
		fm.AppntHomeZipCode.value  = fm.HomeZipCode.value;
		fm.AppntCompanyPhone.value = fm.CompanyPhone.value;
		fm.AppntMobile.value       = fm.Mobile.value;
		fm.AppntEMail.value        = fm.EMail.value;
		fm.AppntCompanyAddress.value      = fm.CompanyAddress.value;
	}
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	document.all( 'fmAction' ).value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	tAction = fm.action;
	fm.submit(); //�ύ
	fm.action = tAction;
}
//������Ϣ���޸�
function updateRisk(wFlag){
	saveRisk();
}
//������Ϣ������
function saveRisk(wFlag){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	document.all( 'fmAction' ).value = mAction;
	fm.action = "DSProposalSave.jsp";
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}
//���汣�ս���𡢺������޸�
function updateGet(wFlag){
	saveGet();
}
//���汣�ս���𡢺���������
function saveGet(wFlag){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	fm.action="DSGetSave.jsp";
	document.all( 'fmAction' ).value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	fm.submit(); //�ύ
}

function saveImpart(wFlag){
	//var tSql = "select * from lbpocustomerimpart where contno='"+prtNo+"' and inputno='"+InputNo+"'";
	//var arrResult;
	//arrResult=easyExecSql(tSql);
	//if(arrResult!=null){
	//	alert("�Ѿ��������֪��Ϣ��");
	//	return false;
	//}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
		//��ʼ����Ҫ�ĸ�֪�ͻ�����
		initType();
	}
	disabled(false);
	mAction="INSERT";
	fm.fmAction.value=mAction;
	fm.action="DSImpartSave.jsp";
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
	disabled(true);
}
function updateImpart(wFlag){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	fm.fmAction.value=mAction;
	fm.action="DSImpartSave.jsp";
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}
//��ʾ��֪��Ϣ
function displayImpart(){
	if(InputTime=="2"){
		
		var sqlid6="DSHomeContSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(prtNo);//ָ������Ĳ���
	   var ImpartSql=mySql6.getString();	
		
//		var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer<>'D04' and InputNo='3' order by impartver,ImpartCode";
		var arrResult;
		arrResult=easyExecSql(ImpartSql);
		if(arrResult!=null)
		{  
			turnPage.queryModal(ImpartSql, ImpartGrid,null,null,33);
		}else{
			//var ImpartSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = '101' order by ImpartCode";
			//turnPage.queryModal(ImpartSql2, ImpartGrid,null,null,27);
		}
		
		var sqlid7="DSHomeContSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(prtNo);//ָ������Ĳ���
	   var AgentSql=mySql7.getString();	
		
//		var AgentSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer='D04' and InputNo='3' order by impartver,impartcode";
		var arrResult2;
		arrResult2=easyExecSql(AgentSql);
		if(arrResult2!=null){
			turnPage.queryModal(AgentSql, AgentImpartGrid);
		}else{
			//var AgentSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = 'D04' order by ImpartCode";
			//turnPage.queryModal(AgentSql2,AgentImpartGrid);
		}
	}else{
		
		var sqlid8="DSHomeContSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(prtNo);//ָ������Ĳ���
		mySql8.addSubPara(InputNo);//ָ������Ĳ���
	   var ImpartSql=mySql8.getString();	
		
//		var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillNo from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer<>'D04' and InputNo='"+InputNo+"' order by impartver,impartcode";
		var arrResult;//prompt("",ImpartSql);
		arrResult=easyExecSql(ImpartSql);
		if(arrResult!=null)
		{  
			turnPage.queryModal(ImpartSql, ImpartGrid,null,null,33);
		}else{
			//var ImpartSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = '101' order by ImpartCode";
			//turnPage.queryModal(ImpartSql2, ImpartGrid,null,null,27);
		}
		
		var sqlid9="DSHomeContSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(prtNo);//ָ������Ĳ���
		mySql9.addSubPara(InputNo);//ָ������Ĳ���
	   var AgentSql=mySql9.getString();	
		
//		var AgentSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer='D04' and InputNo='"+InputNo+"' order by impartver,impartcode";
		var arrResult2;
		arrResult2=easyExecSql(AgentSql);
		if(arrResult2!=null){
			turnPage.queryModal(AgentSql, AgentImpartGrid);
		}else{
		
			//var AgentSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = 'D04' order by ImpartCode";
			//turnPage.queryModal(AgentSql2,AgentImpartGrid);
		}
	}
	//��Щ��֪������д�Ƿ� ��ʱ�����´���
	disabled(true);
}
//��Щ��֪������д�Ƿ� ��ʱ�����´���
function disabled(tFlag){
   try{	
	var tempOrder="document.all('ImpartGrid6')[0].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid7')[0].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(0, 6, "0");
	var tempOrder="document.all('ImpartGrid6')[29].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid7')[29].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(29, 6, "0");
	var tempOrder="document.all('ImpartGrid6')[30].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid7')[30].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(30, 6, "0");
	var tempOrder="document.all('ImpartGrid6')[31].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid7')[31].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(31, 6, "0");	
	var tempOrder="document.all('ImpartGrid6')[32].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid7')[32].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(32, 6, "0");
	  			}catch(ex){}
}

function initType(){
   try{	
	ImpartGrid.setRowColData(0, 6, "0");
	ImpartGrid.setRowColData(29, 6, "0");
	ImpartGrid.setRowColData(30, 6, "0");
	ImpartGrid.setRowColData(31, 6, "0");	
	ImpartGrid.setRowColData(32, 6, "0");
	  			}catch(ex){}
}
//��ʾ��ͬ��Ϣ
function displayCont(){
	fm.PrtNo.value = prtNo;
	fm.ProposalContNo.value = prtNo;
	fm.MissionID.value=tMissionID;
	fm.SubMissionID.value=tSubMissionID;
	fm.ContNo.value=prtNo
	fm.InputNo.value=InputNo;
	fm.InputTime.value=InputTime;//alert(InputTime);
	
		var sqlid10="DSHomeContSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(prtNo);//ָ������Ĳ���
	   var tSql=mySql10.getString();	
	
//	var tSql ="select managecom from es_doc_main where doccode ='"+prtNo+"'";
	var tManageCom = easyExecSql(tSql);
	fm.ManageCom.value=tManageCom[0][0];
	fm.ScanCom.value = tManageCom[0][0];
	fm.SequenceNo3.value  = "1";
	//���InputTime��ֵ��2����ʾ����¼��,�뽫��button֮���INPUT����Ϊdisabled
	if(InputTime=="2"){
		
		var sqlid11="DSHomeContSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(prtNo);//ָ������Ĳ���
	   var tContSql2=mySql11.getString();	
		
//  		var tContSql2="select * from lbpocont where contno='"+prtNo+"' and InputNo='3'";
  		var arrResultCont2;
		arrResultCont2=easyExecSql(tContSql2);
		displayContInfo(arrResultCont2);
		
			var sqlid12="DSHomeContSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(prtNo);//ָ������Ĳ���
	   var tAppntSql2=mySql12.getString();
		
//		var tAppntSql2 = "select * from lbpoappnt where contno='"+prtNo+"' and InputNo='3'";
		var arrResultAppnt2;
		arrResultAppnt2=easyExecSql(tAppntSql2);
		displayAppnt(arrResultAppnt2);
		
		var sqlid13="DSHomeContSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(prtNo);//ָ������Ĳ���
	   var tInsuredSql2=mySql13.getString();
		
//		var tInsuredSql2 = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='1' and InputNo='3'";
		var arrResultInsured2;
		arrResultInsured2=easyExecSql(tInsuredSql2);
		displayInsured(arrResultInsured2);
		
		var sqlid14="DSHomeContSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(prtNo);//ָ������Ĳ���
	   var tBnfSql2=mySql14.getString();
		
//		var tBnfSql2 = "select (select lbpoinsured.sequenceno from lbpoinsured where lbpoinsured.insuredno = b.insuredno and inputno='3'),1,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress,b.FillNo,b.insuredno,0 from lbpobnf b where b.contno='"+prtNo+"' and b.inputno='3' order by b.insuredno,b.fillno";
		var arrResultBnf2;//prompt("",tBnfSql2);
		arrResultBnf2=easyExecSql(tBnfSql2);
		turnPage.queryModal(tBnfSql2,BnfGrid);
		
		var sqlid15="DSHomeContSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(prtNo);//ָ������Ĳ���
	   var tRiskSql2=mySql15.getString();
		
//		var tRiskSql2="select b.risksequence,(select lbpoinsured.sequenceno from lbpoinsured where lbpoinsured.insuredno = b.insuredno and lbpoinsured.inputno='3'),b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.polno,b.fillno,b.insuredno,b.mainpolno from lbpopol b where b.contno='"+prtNo+"' and b.inputno='3' order by insuredno,risksequence"
		var arrResultRisk2;//prompt("",tRiskSql2);
		arrResultRisk2=easyExecSql(tRiskSql2);
		turnPage.queryModal(tRiskSql2,PolGrid);
		
		var sqlid16="DSHomeContSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(prtNo);//ָ������Ĳ���
	   var tPolSql2=mySql16.getString();
		
//		var tPolSql2="select * from lbpopol where contno='"+prtNo+"' and inputno='3' and fillno='1'";
		var arrResultPol2;
		arrResultPol2=easyExecSql(tPolSql2);
		displayPol(arrResultPol2);
		displayOther();
		//--------------------------------------------------------------------------------//
		//*************��multline�⣬�����е�element�û�************************//
		//�������е�input���������û�
		for(i=0;i<document.fm.elements.length;i++){
  			if(document.fm.elements[i].tagName=="INPUT" 
  			//&& (document.fm.elements[i].name.indexOf('Grid')==-1 || 
  			//document.fm.elements[i].name ==null ||document.fm.elements[i].name=='')
  			&&document.fm.elements[i].className.indexOf('muline')==-1
  			//&&document.fm.elements[i].name.indexOf('Grid')==-1 
  			){
  			//alert(document.fm.elements[i].name);
  			
  				document.fm.elements[i].disabled=true;
  				if(document.fm.elements[i].type=="button"){
  					document.fm.elements[i].disabled=false;
  				}
  			}
  		}
  		fm.InsuredSequencename.disabled=false;
  		//��ѯ������Ϣ����У�������ֶ��ÿղ�������޸�
  		//�����ͬ��Ϣ
		
		var sqlid17="DSHomeContSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(prtNo);//ָ������Ĳ���
	   var tErrorCont=mySql17.getString();
		
//  		var tErrorCont = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOCont' group by errortag";
  		var arrResultErrorCont;//prompt("",tErrorSql);
  		arrResultErrorCont=easyExecSql(tErrorCont);
  		if(arrResultErrorCont!=null){
  			for(var k=0;k<arrResultErrorCont.length;k++){
  				//alert(arrResultErrorCont[k][0]);
  				try{
	  				var ele="";
	  				ele=arrResultErrorCont[k][0];
	  				//�����������ȷ�ϵ��ֶ��óɿ����޸�
	  				document.all(""+ele+"").disabled=false;
	  				//ƴ�ַ������������ȷ�ϵ��ֶθ�����ʾ
	  				var warn="fm."+ele+".className=\"warn\";"
	  				//�������ȷ�ϵ��ֶ��ÿ�
	  				//document.all(""+ele+"").value="";
	  				eval(warn);//alert(warn);
	  				var tObj = document.all(""+ele+"");
	  				//����¼� �˴���Ϊ�˴ﵽ���ŵ�ĳ��У����ֶ���ʱ������ʾ��һ�κ͵ڶ��ε�¼����
						var tTitle = "";
						
		var sqlid18="DSHomeContSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(prtNo);//ָ������Ĳ���
		mySql18.addSubPara(arrResultErrorCont[k][0]);//ָ������Ĳ���
	   var tSQL=mySql18.getString();
						
//						var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorCont[k][0]+"' and otherno='1' and tablename='LBPOCont' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);
     				for(ti=0;ti<arrResultTitle.length;ti++)
     				{
     					tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     					if(ti==0)
     					{
     						tTitle = tTitle + "    ";
     					}
     				}
     				tObj.setAttribute('title',tTitle);
	  				if(fm.SequenceNo){
	  					fm.SequenceNo.disabled=false;
	  				}
	  				if(fm.SequenceNo3){
	  					fm.SequenceNo3.disabled=false;
	  				}
  				}catch(ex){
  					alert("��ʼ���������");
  				}
  				//����element���⴦��
  				if(arrResultErrorCont[k][0]=="AgentGroup"){
	  				if(fm.AgentGroup){
	  					fm.AgentGroup.disabled=false;
	  				}
  				}
  				if(arrResultErrorCont[k][0]=="NewPayMode"){
	  				if(fm.NewPayMode){
	  					fm.NewPayMode.disabled=false;
	  				}
  				}
  			}
  		}
  		//����������Ϣ
		
		var sqlid19="DSHomeContSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(prtNo);//ָ������Ĳ���
	   var tErrorInsured=mySql19.getString();
		
//  		var tErrorInsured = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOInsured' group by errortag";
  		var arrResultErrorInsured;//alert(556);//prompt("",tErrorSql);
  		arrResultErrorInsured=easyExecSql(tErrorInsured);
  		if(arrResultErrorInsured!=null){
  			for(k=0;k<arrResultErrorInsured.length;k++){
  				//alert(arrResultErrorInsured[k][0]);
  				try{
	  				var ele="";
	  				ele=arrResultErrorInsured[k][0];
	  				//�����������ȷ�ϵ��ֶ��óɿ����޸�
	  				document.all(""+ele+"").disabled=false;
	  				//ƴ�ַ������������ȷ�ϵ��ֶθ�����ʾ
	  				var warn="fm."+ele+".className=\"warn\";"
	  				//�������ȷ�ϵ��ֶ��ÿ�
	  				//document.all(""+ele+"").value="";
	  				eval(warn);//alert(warn);
	  				var tObj = document.all(""+ele+"");
	  				//����¼� �˴���Ϊ�˴ﵽ���ŵ�ĳ��У����ֶ���ʱ������ʾ��һ�κ͵ڶ��ε�¼����
					var tTitle = "";
					
		var sqlid20="DSHomeContSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(prtNo);//ָ������Ĳ���
		mySql20.addSubPara(arrResultErrorInsured[k][0]);//ָ������Ĳ���
	   var tSQL=mySql20.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorInsured[k][0]+"' and otherno='1' and tablename='LBPOInsured' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     				if(arrResultTitle!=null){
	     				for(ti=0;ti<arrResultTitle.length;ti++)
	     				{//alert(1);
	     					tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
	     					if(ti==0)
	     					{
	     						tTitle = tTitle + "    ";
	     					}
	     				}
	     				tObj.setAttribute('title',tTitle);
	  				}
  				}catch(ex){
  					alert("��ʼ���������");
  				}
  			}
  		}//����
		
		var sqlid21="DSHomeContSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(prtNo);//ָ������Ĳ���
	   var tErrorPol=mySql21.getString();
		
//  		var tErrorPol = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOPol' and otherno='1' group by errortag";
  		var arrResultErrorPol;//prompt("",tErrorSql);
  		arrResultErrorPol=easyExecSql(tErrorPol);
  		if(arrResultErrorPol!=null){
  			for(k=0;k<arrResultErrorPol.length;k++){
  				if(arrResultErrorPol[k][0]=="LiveGetMode"){
  					fm.LiveGetMode.className="warn";
  					fm.LiveGetMode.disabled=false;
  					var tObj = document.all("LiveGetMode");
  					var tTitle = "";
					
		var sqlid22="DSHomeContSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(prtNo);//ָ������Ĳ���
		mySql22.addSubPara(arrResultErrorPol[k][0]);//ָ������Ĳ���
	   var tSQL=mySql22.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="BonusGetMode"){
  					fm.BonusGetMode.className="warn";
  					fm.BonusGetMode.disabled=false;
  					var tObj = document.all("BonusGetMode");
  					var tTitle = "";
					
		var sqlid23="DSHomeContSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(prtNo);//ָ������Ĳ���
		mySql23.addSubPara(arrResultErrorPol[k][0]);//ָ������Ĳ���
	   var tSQL=mySql23.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="GetYear"){
  					fm.GetYear.className="warn";
  					fm.GetYear.disabled=false;
  					var tObj = document.all("GetYear");
  					var tTitle = "";
					
		var sqlid24="DSHomeContSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
		mySql24.addSubPara(prtNo);//ָ������Ĳ���
		mySql24.addSubPara(arrResultErrorPol[k][0]);//ָ������Ĳ���
	   var tSQL=mySql24.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="GetLimit"){
  					fm.GetLimit.className="warn";
  					fm.GetLimit.disabled=false;
  					var tObj = document.all("GetLimit");
  					var tTitle = "";
					
		var sqlid25="DSHomeContSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
		mySql25.addSubPara(prtNo);//ָ������Ĳ���
		mySql25.addSubPara(arrResultErrorPol[k][0]);//ָ������Ĳ���
	   var tSQL=mySql25.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="StandbyFlag3"){
  					fm.StandbyFlag3.className="warn";
  					fm.StandbyFlag3.disabled=false;
  					var tObj = document.all("StandbyFlag3");
  					var tTitle = "";
					
		var sqlid26="DSHomeContSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
		mySql26.addSubPara(prtNo);//ָ������Ĳ���
		mySql26.addSubPara(arrResultErrorPol[k][0]);//ָ������Ĳ���
	   var tSQL=mySql26.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  			}
  		}
  		/**
  		   MultLine��Ҫ���⴦��
  		*/
  		/**������*/
		
		var sqlid27="DSHomeContSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(prtNo);//ָ������Ĳ���
	   var tBnfGridSql=mySql27.getString();
		
//  		var tBnfGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOBnf' and bussno='"+prtNo+"' and bussnotype='TB'";
  		var arrResultCBnfGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCBnfGrid=easyExecSql(tBnfGridSql);
  		if(arrResultCBnfGrid!=null){
	  		for(a=0;a<arrResultCBnfGrid.length;a++){
	  			var tFillNo=arrResultCBnfGrid[a][0];
	  			var tFieldName=arrResultCBnfGrid[a][1];
	  			var tLineCount=0;
	  			tLineCount=getLineCount("BnfGrid",tFieldName);
	  			var tTitle = "";
				
		var sqlid28="DSHomeContSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
		mySql28.addSubPara(prtNo);//ָ������Ĳ���
		mySql28.addSubPara(tFieldName);//ָ������Ĳ���
		mySql28.addSubPara(tFillNo);//ָ������Ĳ���
	   var tSQL=mySql28.getString();
				
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOBnf' and otherno='"+tFillNo+"' order by errorcount ";
     			var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			BnfGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
	  			BnfGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
	  			
	  			var tempOrder="document.all('BnfGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
	  			var tempOrder2="document.all('BnfGrid"+tLineCount+"')["+(tFillNo-1)+"].verifyorder='2';";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder2);
	  			eval(tempOrder);
	  		}
  		}
  		/**����*/
		
		var sqlid29="DSHomeContSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
		mySql29.addSubPara(prtNo);//ָ������Ĳ���
	   var tPolGridSql=mySql29.getString();
		
//  		var tPolGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOPol' and bussno='"+prtNo+"'";
  		var arrResultCPolGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCPolGrid=easyExecSql(tPolGridSql);
  		if(arrResultCPolGrid!=null){
	  		for(a=0;a<arrResultCPolGrid.length;a++){
	  			var tFillNo=arrResultCPolGrid[a][0];
	  			var tFieldName=arrResultCPolGrid[a][1];
	  			if(tFieldName=="RiskCode"||tFieldName=="Amnt"||tFieldName=="Mult"||tFieldName=="InsuYear"
	  				||tFieldName=="PayYears"||tFieldName=="StandbyFlag2"||tFieldName=="StandbyFlag1"
	  				||tFieldName=="Remark"||tFieldName=="Prem"){
		  			var tLineCount=0;//alert(tFieldName);
		  			tLineCount=getLineCount("PolGrid",tFieldName);
		  			//alert("tFillNo: "+tFillNo);
		  			//alert("tLineCount: "+tLineCount);
		  			//if(tFieldName=="Name"){
		  			//	tLineCount=3;
		  			//}
		  			var tTitle = "";
					
		var sqlid30="DSHomeContSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(prtNo);//ָ������Ĳ���
		mySql30.addSubPara(tFieldName);//ָ������Ĳ���
		mySql30.addSubPara(tFillNo);//ָ������Ĳ���
	   var tSQL=mySql30.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOPol' and otherno='"+tFillNo+"' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
	     			for(ti=0;ti<arrResultTitle.length;ti++)
	     			{
	     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
	     				if(ti==0)
		     			{
		     				tTitle = tTitle + "    ";
		     			}
	     			}
	     			//alert(tTitle);
		  			//alert("tFillNo: "+tFillNo);
		  			//alert("tLineCount: "+tLineCount);
		  			//if(tFieldName=="Name"){
		  			//	tLineCount=3;
		  			//}
		  			PolGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
		  			PolGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
		  			var tempOrder="document.all('PolGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
		  			var tempOrder2="document.all('PolGrid"+tLineCount+"')["+(tFillNo-1)+"].verifyorder='2';";
	  				//alert("tempOrder"+tempOrder);
	  				eval(tempOrder2);
		  			//alert("tempOrder"+tempOrder);
		  			eval(tempOrder);
	  			}
	  		}
  		}
  		//������֪
		
		var sqlid31="DSHomeContSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(prtNo);//ָ������Ĳ���
	   var tCusImpartGridSql=mySql31.getString();
		
//  		var tCusImpartGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOCustomerImpart' and bussno='"+prtNo+"' and otherno>0 and otherno<34 ";
  		var arrResultCusImpartGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCusImpartGrid=easyExecSql(tCusImpartGridSql);
  		if(arrResultCusImpartGrid!=null){
	  		for(a=0;a<arrResultCusImpartGrid.length;a++){
	  			var tFillNo=arrResultCusImpartGrid[a][0];
	  			var tFieldName=arrResultCusImpartGrid[a][1];
	  			var tLineCount=0;//alert(tFieldName);
	  			tLineCount=getLineCount("ImpartGrid",tFieldName);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			var tTitle = "";
				
		var sqlid32="DSHomeContSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
		mySql32.addSubPara(prtNo);//ָ������Ĳ���
		mySql32.addSubPara(tFieldName);//ָ������Ĳ���
		mySql32.addSubPara(tFillNo);//ָ������Ĳ���
	   var tSQL=mySql32.getString();
				
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOCustomerImpart' and otherno='"+tFillNo+"' order by errorcount ";
     			var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
     			//alert(tTitle);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			ImpartGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
	  			ImpartGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
	  			var tempOrder="document.all('ImpartGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
	  			var tempOrder2="document.all('ImpartGrid"+tLineCount+"')["+(tFillNo-1)+"].verifyorder='4';";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder2);
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}//alert(803);
  		//ҵ��Ա��֪
		
				var sqlid33="DSHomeContSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(prtNo);//ָ������Ĳ���
	   var tAgeImpartGridSql=mySql33.getString();
		
//  		var tAgeImpartGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOCustomerImpart' and bussno='"+prtNo+"' and otherno>33 ";
  		var arrResultAgeImpartGrid;
  		//prompt("",tAgeImpartGridSql);
  		arrResultAgeImpartGrid=easyExecSql(tAgeImpartGridSql);
  		if(arrResultAgeImpartGrid!=null){//alert(arrResultAgeImpartGrid.length);
	  		for(a=0;a<arrResultAgeImpartGrid.length;a++){
	  			var tFillNo=arrResultAgeImpartGrid[a][0];
	  			var tFieldName=arrResultAgeImpartGrid[a][1];
	  			var tLineCount=0;//alert(tFieldName);
	  			tLineCount=getLineCount("ImpartGrid",tFieldName);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			var tTitle = "";
				
		var sqlid34="DSHomeContSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
		mySql34.addSubPara(prtNo);//ָ������Ĳ���
		mySql34.addSubPara(tFieldName);//ָ������Ĳ���
		mySql34.addSubPara(tFillNo);//ָ������Ĳ���
	   var tSQL=mySql34.getString();
				
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOCustomerImpart' and otherno='"+tFillNo+"' order by errorcount ";
     			var arrResultTitle = easyExecSql(tSQL);/**prompt("",tSQL);*/ //alert("arrResultTitle.length:"+arrResultTitle.length);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
     			//alert(tTitle);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			AgentImpartGrid.setRowColTitle((tFillNo-33-1),tLineCount,tTitle);
	  			AgentImpartGrid.setRowColClass((tFillNo-33-1),tLineCount,'warn');
	  			var tempOrder="document.all('AgentImpartGrid"+tLineCount+"')["+(tFillNo-33-1)+"].disabled=false;";
	  			var tempOrder2="document.all('AgentImpartGrid"+tLineCount+"')["+(tFillNo-33-1)+"].verifyorder='4';";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder2);
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}
  		//-------------------------------------------------------------------------------//
	}else{
		var tContSql="select * from lbpocont where contno='"+prtNo+"' and InputNo='"+InputNo+"'";
		var arrResultCont;
		arrResultCont=easyExecSql(tContSql);
		if(arrResultCont!=null){
			displayContInfo(arrResultCont);
		}
		
				var sqlid35="DSHomeContSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
		mySql35.addSubPara(prtNo);//ָ������Ĳ���
		mySql35.addSubPara(InputNo);//ָ������Ĳ���
	   var tInsuredSql=mySql35.getString();
		
//		var tInsuredSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='1' and InputNo='"+InputNo+"' and fillNo='1'";
		var arrResultInsured;
		arrResultInsured=easyExecSql(tInsuredSql);
		if(arrResultInsured!=null){
			displayInsured(arrResultInsured);
		}
		
						var sqlid36="DSHomeContSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
		mySql36.addSubPara(prtNo);//ָ������Ĳ���
		mySql36.addSubPara(InputNo);//ָ������Ĳ���
	   var tAppntsql=mySql36.getString();
		
//		var tAppntsql ="select appntno from lbpoappnt where contno='"+prtNo+"' and inputno='"+InputNo+"'";
		var arrResultAppntNo;
		arrResultAppntNo=easyExecSql(tAppntsql);
		fm.AppntNo.value           = arrResultAppntNo[0][0];
		//var tBnfSql = "select a.sequenceno,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress from lbpoinsured a,lbpobnf b where a.contno=b.contno and a.inputno=b.inputno and a.contno='"+prtNo+"' and a.inputno='"+InputNo+"'";
		
		var sqlid37="DSHomeContSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(InputNo);//ָ������Ĳ���
		mySql37.addSubPara(prtNo);//ָ������Ĳ���
		mySql37.addSubPara(InputNo);//ָ������Ĳ���
	   var tBnfSql=mySql37.getString();
		
//		var tBnfSql = "select (select lbpoinsured.sequenceno from lbpoinsured where lbpoinsured.insuredno = b.insuredno and inputno='"+InputNo+"'),1,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress,b.FillNo,b.insuredno,0 from lbpobnf b where b.contno='"+prtNo+"' and b.inputno='"+InputNo+"' order by b.insuredno,b.fillno";
		var arrResultBnf;
		arrResultBnf=easyExecSql(tBnfSql);
		if(arrResultBnf!=null){
			turnPage.queryModal(tBnfSql,BnfGrid);
		}
		
		var sqlid38="DSHomeContSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(InputNo);//ָ������Ĳ���
		mySql38.addSubPara(prtNo);//ָ������Ĳ���
		mySql38.addSubPara(InputNo);//ָ������Ĳ���
	   var tRiskSql=mySql38.getString();
		
//		var tRiskSql="select b.risksequence,(select lbpoinsured.sequenceno from lbpoinsured where lbpoinsured.insuredno = b.insuredno and inputno='"+InputNo+"'),b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.polno,b.fillno,b.insuredno,b.mainpolno from lbpopol b where b.contno='"+prtNo+"' and b.inputno='"+InputNo+"' order by insuredno"
		var arrResultRisk;
		arrResultRisk=easyExecSql(tRiskSql);
		if(arrResultRisk!=null){
			turnPage.queryModal(tRiskSql,PolGrid);
		}
		
				var sqlid39="DSHomeContSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
		mySql39.addSubPara(prtNo);//ָ������Ĳ���
		mySql39.addSubPara(InputNo);//ָ������Ĳ���
	   var tPolSql=mySql39.getString();
		
//		var tPolSql="select * from lbpopol where contno='"+prtNo+"' and inputno='"+InputNo+"' and fillno='1' order by insuredno,fillno ";
		var arrResultPol;
		arrResultPol=easyExecSql(tPolSql);
		if(arrResultPol!=null){
			displayPol(arrResultPol);
		}
		displayOther();
	}
}

//
function afterCodeSelect( cCodeName, Field ){
	//alert(cCodeName);
	if(cCodeName=="SequenceNo"){
		//���Ͷ������Ϣ
		fm.InsuredNo.value="";
		fm.RelationToMainInsured.value="";
		fm.Name.value="";
		fm.Sex.value="";
		fm.Birthday.value="";
		fm.IDType.value="";
		fm.IDNo.value="";
		fm.NativePlace.value="";
		fm.RgtAddress.value="";
		fm.Marriage.value="";
		fm.PostalAddress.value="";
		fm.ZipCode.value="";
		fm.HomeAddress.value="";
		fm.HomeZipCode.value="";
		fm.Mobile.value="";
		fm.CompanyPhone.value="";
		fm.EMail.value="";
		fm.CompanyAddress.value="";
		fm.WorkType.value="";
		fm.PluralityType.value="";
		fm.OccupationCode.value="";
		
		var sqlid40="DSHomeContSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
		mySql40.addSubPara(prtNo);//ָ������Ĳ���
		mySql40.addSubPara(Field.value);//ָ������Ĳ���
		mySql40.addSubPara(InputNo);//ָ������Ĳ���
	   var tSql=mySql40.getString();
		
//		var tSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='"+Field.value+"' and InputNo='"+InputNo+"'";
			var arrResult;
			arrResult=easyExecSql(tSql);
			if(arrResult!=null){
				displayInsured(arrResult);
			}
		if(InputTime=="2"){
			//initBnfGrid();
			fm.InsuredNo.disabled=true;
			fm.RelationToMainInsured.disabled=true;
			fm.Name.disabled=true;
			fm.Sex.disabled=true;
			fm.Birthday.disabled=true;
			fm.IDType.disabled=true;
			fm.IDNo.disabled=true;
			fm.NativePlace.disabled=true;
			fm.RgtAddress.disabled=true;
			fm.Marriage.disabled=true;
			fm.PostalAddress.disabled=true;
			fm.ZipCode.disabled=true;
			fm.HomeAddress.disabled=true;
			fm.HomeZipCode.disabled=true;
			fm.Mobile.disabled=true;
			fm.CompanyPhone.disabled=true;
			fm.EMail.disabled=true;
			fm.CompanyAddress.disabled=true;
			fm.WorkType.disabled=true;
			fm.PluralityType.disabled=true;
			fm.OccupationCode.disabled=true;
			fm.InsuredNo.className='common';
			fm.RelationToMainInsured.className='code';
			fm.Name.className='common';
			fm.Sex.className='code';
			fm.Birthday.className='common';
			fm.IDType.className='code';
			fm.IDNo.className='common3';
			fm.NativePlace.className='code';
			fm.RgtAddress.className='common';
			fm.Marriage.className='code';
			fm.PostalAddress.className='common3';
			fm.ZipCode.className='common';
			fm.HomeAddress.className='common3';
			fm.HomeZipCode.className='common';
			fm.Mobile.className='common';
			fm.CompanyPhone.className='common';
			fm.EMail.className='common';
			fm.CompanyAddress.className='common3';
			fm.WorkType.className='common';
			fm.PluralityType.className='common';
			fm.OccupationCode.className='code';
			
					var sqlid41="DSHomeContSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
		mySql41.addSubPara(prtNo);//ָ������Ĳ���
		mySql41.addSubPara(Field.value);//ָ������Ĳ���
		mySql41.addSubPara(InputNo);//ָ������Ĳ���
	   var tSql=mySql41.getString();
			
//			var tSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='"+Field.value+"' and InputNo='"+InputNo+"'";
			var arrResult;
			arrResult=easyExecSql(tSql);
			if(arrResult!=null){
				displayInsured(arrResult);
				//var tBnfSql = "select a.sequenceno,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress from lbpoinsured a,lbpobnf b where a.contno=b.contno and a.inputno=b.inputno and a.contno='"+prtNo+"' and a.inputno='"+InputNo+"' and a.SequenceNo='"+Field.value+"'";
				//var arrResultBnf;
				//arrResultBnf=easyExecSql(tBnfSql);
				//if(arrResultBnf!=null){
				//	turnPage.queryModal(tBnfSql,BnfGrid);
				//}
			}
			
		var sqlid42="DSHomeContSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
		mySql42.addSubPara(prtNo);//ָ������Ĳ���
		mySql42.addSubPara(Field.value);//ָ������Ĳ���
	   var tErrSql=mySql42.getString();
			
//			var tErrSql = "select errortag from lbpodatadetailerror where tablename='LBPOInsured' and bussno='"+prtNo+"' and otherno='"+Field.value+"'";
			var ErrarrResult;//prompt("",tErrSql);
			ErrarrResult=easyExecSql(tErrSql);
			if(ErrarrResult!=null){
				for(i=0;i<ErrarrResult.length;i++){
					var tOrder="fm."+ErrarrResult[i][0]+".disabled=false;";
					var tOrder2="fm."+ErrarrResult[i][0]+".className=\"warn\";";
					eval(tOrder);
					eval(tOrder2);
					var tObj = document.all(""+ErrarrResult[i][0]+"");
	  				//����¼� �˴���Ϊ�˴ﵽ���ŵ�ĳ��У����ֶ���ʱ������ʾ��һ�κ͵ڶ��ε�¼����
					var tTitle = "";
					
							var sqlid43="DSHomeContSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
		mySql43.addSubPara(prtNo);//ָ������Ĳ���
		mySql43.addSubPara(ErrarrResult[i][0]);//ָ������Ĳ���
		mySql43.addSubPara( Field.value);//ָ������Ĳ���
	   var tSQL=mySql43.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+ErrarrResult[i][0]+"' and otherno='"+Field.value+"' and tablename='LBPOInsured' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     				for(ti=0;ti<arrResultTitle.length;ti++)
     				{
     					tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     					if(ti==0)
     					{
     						tTitle = tTitle + "    ";
     					}
     				}
     				tObj.setAttribute('title',tTitle);
				}
			}
		}
	}
	
	if(cCodeName=="SequenceNo3"){
		fm.LiveGetMode.value="";
		fm.BonusGetMode.value="";
		fm.GetYear.value="";
		fm.GetLimit.value="";
		fm.StandbyFlag3.value="";
		if(InputTime=="2"){
			fm.LiveGetMode.disabled=true;
			fm.BonusGetMode.disabled=true;
			fm.GetYear.disabled=true;
			fm.GetLimit.disabled=true;
			fm.StandbyFlag3.disabled=true;
			fm.LiveGetMode.className='code';
			fm.BonusGetMode.className='code';
			fm.GetYear.className='common';
			fm.GetLimit.className='common';
			fm.StandbyFlag3.className='code';
			
										var sqlid44="DSHomeContSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
		mySql44.addSubPara(prtNo);//ָ������Ĳ���
		mySql44.addSubPara(InputNo);//ָ������Ĳ���
		mySql44.addSubPara( Field.value);//ָ������Ĳ���
	   var tGetSql=mySql44.getString();
			
//			var tGetSql="select LiveGetMode,BonusGetMode,GetYear,GetLimit,StandbyFlag3 from lbpopol b"
//						+" where contno='"+prtNo+"'"
//						+" and inputno='"+InputNo+"'"
//						+" and insuredno in (select insuredno from lbpoinsured where contno=b.contno and sequenceno='"+Field.value+"')";
			var tarrReltGet;//pro
			tarrReltGet=easyExecSql(tGetSql);
			if(tarrReltGet!=null){
				fm.LiveGetMode.value=tarrReltGet[0][0];
				fm.BonusGetMode.value=tarrReltGet[0][1];
				fm.GetYear.value=tarrReltGet[0][2];
				fm.GetLimit.value=tarrReltGet[0][3];
				fm.StandbyFlag3.value=tarrReltGet[0][4];
			}
			
		var sqlid45="DSHomeContSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
		mySql45.addSubPara(prtNo);//ָ������Ĳ���
		mySql45.addSubPara( Field.value);//ָ������Ĳ���
	   var tErrSql=mySql45.getString();
			
//			var tErrSql = "select errortag from lbpodatadetailerror where tablename='LBPOPol' and bussno='"+prtNo+"' and otherno='"+Field.value+"' and errorcode in(120013,120011,120010,120009,120012) group by errortag";
			var ErrarrResult;
			ErrarrResult=easyExecSql(tErrSql);//prompt("",tErrSql);alert("ErrarrResult.length:"+ErrarrResult.length);
			if(ErrarrResult!=null){
				for(i=0;i<ErrarrResult.length;i++){
					var tOrder="fm."+ErrarrResult[i][0]+".disabled=false;";
					var tOrder2="fm."+ErrarrResult[i][0]+".className=\"warn\";";
					eval(tOrder);
					eval(tOrder2);
					var tObj = document.all(""+ErrarrResult[i][0]+"");
	  				//����¼� �˴���Ϊ�˴ﵽ���ŵ�ĳ��У����ֶ���ʱ������ʾ��һ�κ͵ڶ��ε�¼����
					var tTitle = "";
					
		var sqlid46="DSHomeContSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
		mySql46.addSubPara(prtNo);//ָ������Ĳ���
		mySql46.addSubPara( ErrarrResult[i][0]);//ָ������Ĳ���
		mySql46.addSubPara( Field.value);//ָ������Ĳ���
	   var tSQL=mySql46.getString();
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+ErrarrResult[i][0]+"' and otherno='"+Field.value+"' and tablename='LBPOPol' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     				for(ti=0;ti<arrResultTitle.length;ti++)
     				{
     					tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     					if(ti==0)
     					{
     						tTitle = tTitle + "    ";
     					}
     				}
				}
			}
		}else{
			
		var sqlid47="DSHomeContSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
		mySql47.addSubPara(prtNo);//ָ������Ĳ���
		mySql47.addSubPara( InputNo);//ָ������Ĳ���
	    mySql47.addSubPara( Field.value);//ָ������Ĳ���
	   var tGetSql=mySql47.getString();
			
//			var tGetSql="select LiveGetMode,BonusGetMode,GetYear,GetLimit,StandbyFlag3 from lbpopol b"
//						+" where contno='"+prtNo+"'"
//						+" and inputno='"+InputNo+"'"
//						+" and insuredno in (select insuredno from lbpoinsured where contno=b.contno and sequenceno='"+Field.value+"')";
			var tarrReltGet;
			tarrReltGet=easyExecSql(tGetSql);
			if(tarrReltGet!=null){
				fm.LiveGetMode.value=tarrReltGet[0][0];
				fm.BonusGetMode.value=tarrReltGet[0][1];
				fm.GetYear.value=tarrReltGet[0][2];
				fm.GetLimit.value=tarrReltGet[0][3];
				fm.StandbyFlag3.value=tarrReltGet[0][4];
			}
		}
	}
	//if(cCodeName=="SequenceNo2"){
	//	initPolGrid();
	//	var tSql = "select "+Field.value+",b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.risksequence,b.polno  from lbpopol b where insuredno  in (select insuredno from lbpoinsured a where contno='"+prtNo+"' and  sequenceno='"+Field.value+"' and inputno='"+InputNo+"') and inputno='"+InputNo+"'";
	//	var arrResult;
	//	arrResult=easyExecSql(tSql);
	//	if(arrResult!=null){
	//		turnPage.queryModal(tSql,PolGrid);
	//	}
	//}
}

function afterSubmit4( FlagStr, content ){
	showInfo.close();
	if( FlagStr == "Fail" )
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
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//ˢ�½���
		//displayImpart();
		//displayCont();
	}
	mAction = "";	
}

function afterSubmit( FlagStr, content ){
	unlockScreen('lkscreen');  
	showInfo.close();
	if( FlagStr == "Fail" )
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
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//ˢ�½���
		//displayImpart();
		//displayCont();
		top.opener.easyQueryClickSelf();
		top.close();
		
	}
	mAction = "";
}
/**
* ����Ͷ���˱��������䡶Ͷ������������֮��=Ͷ���˱��������䡷
* ��������������yy��mm��dd��Ͷ������yy��mm��dd
* ����  ����
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("����"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("Ͷ������"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//��ǰ�´��ڳ�����
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//��ǰ��С�ڳ�����
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}

//����¼���У�麯��
function beforeSubmit(wFlag){
	//alert("wFlag:"+wFlag);
	//��ѯ������Ϣ����������ֶΡ������ֶ�����ѯ��
	
			var sqlid48="DSHomeContSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName("app.DSHomeContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
		mySql48.addSubPara(prtNo);//ָ������Ĳ���
//		mySql48.addSubPara( InputNo);//ָ������Ĳ���
//	    mySql48.addSubPara( Field.value);//ָ������Ĳ���
	   var errorMessage=mySql48.getString();
	
//	var errorMessage="select errortag,errorcontent from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename not in('LBPOAppnt','LBPOPol','LBPOCustomerImpart','LBPOBnf') ";
	var errorMessageResult;//prompt("",errorMessage);
	errorMessageResult=easyExecSql(errorMessage);
	if(errorMessageResult!=null){
	for(var k=0;k<errorMessageResult.length;k++){
			//���Ƿ��ѽ��й�������Ϣȷ�Ͻ���У�飬����������д�����Ϣ���Ѿ�У����������
  			if(errorMessageResult[k][0]!="ImpartContent"&&errorMessageResult[k][0]!="ImpartParamModle"&&errorMessageResult[k][0]!="AgentGroup"&&errorMessageResult[k][0]!="NewPayMode"&&errorMessageResult[k][0]!="LBPOCustomerImpart"&&errorMessageResult[k][0]!="Prem"&&errorMessageResult[k][0]!="Amnt"
  			   &&errorMessageResult[k][0]!="RnewFlag"){
  				//alert(arrResultError[k][0]);
  				try{
	  				var ele="";
	  				var eleName="";
	  				ele=errorMessageResult[k][0];
	  				eleName=errorMessageResult[k][1];
	  				//tongmeng 2009-03-19 modify
	  				//�������Ҫ¼����Ϣ,����ֱ��¼�ո񼴿�
	  				//alert(document.all(""+ele+"").name+""+":"+document.all(""+ele+"").verifyorder);
	  				if(wFlag!=0){
		  				if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)&&document.all(""+ele+"").verifyorder==wFlag){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  				
		  					alert(eleName+",��ȷ�ϣ�");
		  					return false;
		  				}
	  				}else{
	  					if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  					alert(eleName+",��ȷ�ϣ�");
		  					return false;
		  				}
	  				}
	  				
  				}catch(ex){
  					//alert("��ʼ���������");
  				}
  				//����element���⴦��
  				if(errorMessageResult[k][0]=="AgentGroup"){
	  				if(fm.AgentGroup){
	  					if(fm.AgentGroup.value==""){
	  						alert("���������δ����ȷ�ϣ�");
	  						return false;
	  					}
	  				}
  				}
  				if(errorMessageResult[k][0]=="NewPayMode"){
	  				if(fm.NewPayMode){
	  					if(fm.NewPayMode.value==""){
	  						alert("���ڽ�����ʽδ����ȷ�ϣ�");
	  						return false;
	  					}
	  				}
  				}
  				
  			}
  		}
  	}
  	//�����б�disabled��element���ó�disabled=false
  	for(i=0;i<document.fm.elements.length;i++){
	  	if(document.fm.elements[i].tagName=="INPUT" 
	  	//&& (document.fm.elements[i].name.indexOf('Grid')==-1 || 
	  	//document.fm.elements[i].name ==null ||document.fm.elements[i].name=='')
	  	&&document.fm.elements[i].className.indexOf('muline')==-1
	  	//&&document.fm.elements[i].name.indexOf('Grid')==-1
	  	&&document.fm.elements[i].verifyorder==wFlag
	  	)
	  	{
	  	//alert(document.fm.elements[i].name);
	  		document.fm.elements[i].disabled=false;
	  		if(document.fm.elements[i].type=="button"){
	  			document.fm.elements[i].disabled=false;
	  		}
	  	}
  	}
  	if(wFlag==2){
	  	for(i=0;i<document.fm.elements.length;i++){
	  		if(document.fm.elements[i].name.indexOf('PolGrid')!=-1
	  		||document.fm.elements[i].name.indexOf('BnfGrid')!=-1){
	  			document.fm.elements[i].disabled=false;
	  		}
	  	}
  	}
  	if(wFlag==4){
  		for(i=0;i<document.fm.elements.length;i++){
	  		if(document.fm.elements[i].name.indexOf('ImpartGrid')!=-1
	  		||document.fm.elements[i].name.indexOf('AgentImpartGrid')!=-1){
	  			document.fm.elements[i].disabled=false;
	  		}
	  	}
  	}
  	//��INPUTTIME��INPUTNO�ȹ��õķſ�
  	//fm.InputTime.disabled=false;
  	fm.fmAction.disabled=false;
  	fm.WorkFlowFlag.disabled=false;
  	fm.MissionID.disabled=false;
  	fm.SubMissionID.disabled=false;
  	fm.ActivityID.disabled=false;
  	fm.NoType.disabled=false;
  	fm.PrtNo2.disabled=false;
  	fm.FamilyType.disabled=false;
  	fm.FamilyID.disabled=false;
  	fm.PolType.disabled=false;
  	fm.CardFlag.disabled=false;
  	fm.SequenceNo.disabled=false;
  	fm.ContNo.disabled=false;
  	fm.ProposalContNo.disabled=false;
  	fm.ProposalGrpContNo.disabled=false;
  	fm.SelPolNo.disabled=false;
  	fm.InputNo.disabled=false;
  	fm.InputTime.disabled=false;
  	fm.GrpContNo.disabled=false;
  	fm.ManageCom.disabled=false;
  	fm.PrtNo.disabled=false;
  	fm.AppntFillNo.disabled=false;
  	fm.InsuredFillNo.disabled=false;
  	fm.AppntNo.disabled=false;
  	fm.InsuredNo.disabled=false;//alert(fm.InsuredNo.value);
  	fm.contFillNo.disabled=false;
  	fm.ContType.disabled=false;
  	return true;
}

//¼�����
function inputConfirm(wFlag){
	if(InputTime==2){
		if(!beforeSubmit(wFlag)){
			return false;	
		}
	}
	fm.InputTime.value = InputTime;
	if(InputTime==2){
		fm.WorkFlowFlag.value = ActivityID;
	}else{
		fm.WorkFlowFlag.value = ActivityID;
	}
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;			//¼�����
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "./DSInputConfirm.jsp";
	lockScreen('lkscreen');  
	fm.submit(); //�ύ
}

//���ظ��ֶ���MultLine��ĳ�е�λ��
function getLineCount(GridName,tFieldName){
	var tLineCount;
	//������
	if(GridName=="BnfGrid"){
		if(tFieldName=="IDType"){
			tLineCount=4;
		}
		if(tFieldName=="IDNo"){
			tLineCount=5;
		}
		if(tFieldName=="Name"){
			tLineCount=3;
		}
		if(tFieldName=="BnfType"){
			tLineCount=2;
		}
		if(tFieldName=="RelationToInsured"){
			tLineCount=6;
		}
		if(tFieldName=="BnfLot"){
			tLineCount=7;
		}
		if(tFieldName=="BnfGrade"){
			tLineCount=8;
		}
		if(tFieldName=="BnfAddress"){
			tLineCount=9;
		}
	}
	if(GridName=="ImpartGrid"){
		if(tFieldName=="ImpartContent"){
			tLineCount=3;
		}
		if(tFieldName=="ImpartParamModle"){
			tLineCount=4;
		}
		if(tFieldName=="Insured1"){
			tLineCount=6;
		}
		if(tFieldName=="Insured2"){
			tLineCount=7;
		}
		if(tFieldName=="insured3"){
			tLineCount=8;
		}
	}
	if(GridName=="PolGrid"){
		if(tFieldName=="RiskCode"){
			tLineCount=3;
		}
		if(tFieldName=="Amnt"){
			tLineCount=4;
		}
		if(tFieldName=="Mult"){
			tLineCount=5;
		}
		if(tFieldName=="StandbyFlag1"){
			tLineCount=6;
		}
		if(tFieldName=="InsuYear"){
			tLineCount=7;
		}
		if(tFieldName=="PayYears"){
			tLineCount=8;
		}
		if(tFieldName=="Prem"){
			tLineCount=9;
		}
		if(tFieldName=="StandbyFlag2"){
			tLineCount=10;
		}
		if(tFieldName=="Remark"){
			tLineCount=11;
		}
		if(tFieldName=="PolNo"){
			tLineCount=12;
		}
	}
	return tLineCount;
}


/*********************************************************************
 *  Ͷ�����뱻������ͬѡ����¼�
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function isSamePerson()
{
	//alert("1272");
    //��Ӧδѡͬһ�ˣ��ִ򹳵����
     //��Ӧδѡͬһ�ˣ��ִ򹳵����
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
    }
    
    if ( fm.SamePersonFlag.checked==true/*&&fm.RelationToAppnt.value!="00"*/)
    {
      //DivLCInsured.style.display = "none";
      //divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      //fm.RelationToAppnt.value="00"
      //alert(1289);
      displayissameperson();
    }
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    ///else if (fm.SamePersonFlag.checked == true)
    //{
    //  document.all('DivLCInsured').style.display = "none";
    //  divLCInsuredPerson.style.display = "none";
    //  divSalary.style.display = "none";

   //   displayissameperson();
    //}
    //��Ӧ��ѡͬһ�˵����
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "";
      //divSalary.style.display = "";
      if(InsuredGrid.mulLineCount==0)
	  {
		//fm.SequenceNo.value="1";//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
		//fm.RelationToMainInsured.value="00";//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
	  }
	  else
	  {
	   //try{document.all('SequenceNo').value=""; }catch(ex){};
       try{document.all('RelationToMainInsured').value=""; }catch(ex){};
	  }
      //try{document.all('InsuredNo').value=""; }catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= ""; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('InsuredPlace').value= ""; }catch(ex){};
      try{document.all('Marriage').value= "";}catch(ex){};
      try{document.all('MarriageDate').value= "";}catch(ex){};
      try{document.all('Health').value= "";}catch(ex){};
      try{document.all('Stature').value= "";}catch(ex){};
      try{document.all('Avoirdupois').value= "";}catch(ex){};
      try{document.all('Degree').value= "";}catch(ex){};
      try{document.all('CreditGrade').value= "";}catch(ex){};
      try{document.all('OthIDType').value= "";}catch(ex){};
      try{document.all('OthIDNo').value= "";}catch(ex){};
      try{document.all('ICNo').value="";}catch(ex){};
      try{document.all('GrpNo').value= "";}catch(ex){};
      try{document.all('JoinCompanyDate').value= "";}catch(ex){}
      try{document.all('StartWorkDate').value= "";}catch(ex){};
      try{document.all('Position').value= "";}catch(ex){};
      try{document.all('Salary').value= "";}catch(ex){};
      try{document.all('OccupationType').value= "";}catch(ex){};
      try{document.all('OccupationCode').value= "";}catch(ex){};
      try{document.all('WorkType').value= "";}catch(ex){};
      try{document.all('PluralityType').value= "";}catch(ex){};
      try{document.all('DeathDate').value= "";}catch(ex){};
      try{document.all('SmokeFlag').value= "";}catch(ex){};
      try{document.all('BlacklistFlag').value= "";}catch(ex){};
      try{document.all('Proterty').value= "";}catch(ex){};
      //try{document.all('Remark').value= "";}catch(ex){};
      try{document.all('State').value= "";}catch(ex){};
      try{document.all('Operator').value= "";}catch(ex){};
      try{document.all('MakeDate').value= "";}catch(ex){};
      try{document.all('MakeTime').value="";}catch(ex){};
      try{document.all('ModifyDate').value= "";}catch(ex){};
      try{document.all('ModifyTime').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('ZipCode').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('Mobile').value= "";}catch(ex){};
      try{document.all('EMail').value="";}catch(ex){};
      try{document.all('GrpName').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('GrpAddress').value="";}catch(ex){};
      try{document.all('GrpZipCode').value= "";}catch(ex){};
      //try{document.all('RelationToAppnt').value= "";}catch(ex){};
      try{document.all('Fax').value= "";}catch(ex){};
      try{document.all('HomePhone').value= "";}catch(ex){};
      try{document.all('HomeAddress').value=  "";}catch(ex){};
      try{document.all('HomeZipCode').value=  "";}catch(ex){};
      try{document.all('RgtAddress').value=  "";}catch(ex){};
      try{document.all('CompanyPhone').value=  "";}catch(ex){};
      try{document.all('CompanyAddress').value=  "";}catch(ex){};

    }
}

function displayissameperson()
{
  //try{fm.SequenceNo.value="1";}catch(ex){};//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
  try{fm.RelationToMainInsured.value="00";}catch(ex){};//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
//  try{fm.RelationToAppnt.value="00";}catch(ex){};
��//try{document.all('InsuredNo').value= document.all("AppntNo").value;                 }catch(ex){};
��try{document.all('Name').value= document.all("AppntName").value;                    }catch(ex){};
��try{document.all('Sex').value= document.all("AppntSex").value;                      }catch(ex){};
��try{document.all('Birthday').value= document.all("AppntBirthday").value;            }catch(ex){};
��try{document.all('IDType').value= document.all("AppntIDType").value;                }catch(ex){};
��try{document.all('IDNo').value= document.all("AppntIDNo").value;                    }catch(ex){};
��try{document.all('Password').value= document.all( "AppntPassword" ).value;          }catch(ex){};
��try{document.all('NativePlace').value= document.all("AppntNativePlace").value;      }catch(ex){};
��try{document.all('Nationality').value= document.all("AppntNationality").value;      }catch(ex){};
��try{document.all('InsuredAddressNo').value= document.all("AppntAddressNo").value;   }catch(ex){};
��try{document.all('InsuredPlace').value= document.all( "AppntPlace" ).value;      	  }catch(ex){};
��try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
��try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
��try{document.all('Health').value= document.all( "AppntHealth" ).value;              }catch(ex){};
��try{document.all('Stature').value= document.all( "AppntStature" ).value;            }catch(ex){};
��try{document.all('Avoirdupois').value= document.all( "AppntAvoirdupois" ).value;    }catch(ex){};
��try{document.all('Degree').value= document.all( "AppntDegree" ).value;              }catch(ex){};
��try{document.all('CreditGrade').value= document.all( "AppntDegreeCreditGrade" ).value;}catch(ex){};
��try{document.all('OthIDType').value= document.all( "AppntOthIDType" ).value;}catch(ex){};
��try{document.all('OthIDNo').value= document.all( "AppntOthIDNo" ).value;}catch(ex){};
��try{document.all('ICNo').value= document.all("AppntICNo").value;}catch(ex){};
��try{document.all('GrpNo').value= document.all("AppntGrpNo").value;}catch(ex){};
��try{document.all( 'JoinCompanyDate' ).value = document.all("JoinCompanyDate").value; if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
��try{document.all('StartWorkDate').value= document.all("AppntStartWorkDate").value;}catch(ex){};
��try{document.all('Position').value= document.all("AppntPosition").value;}catch(ex){};
��try{document.all( 'Position' ).value = document.all("Position").value;} catch(ex) { };
��try{document.all('Salary').value= document.all("AppntSalary").value;}catch(ex){};
��try{document.all('OccupationType').value= document.all("AppntOccupationType").value;}catch(ex){};
��try{document.all('OccupationCode').value= document.all("AppntOccupationCode").value;}catch(ex){};
��try{document.all('WorkType').value= document.all("AppntWorkType").value;}catch(ex){};
��try{document.all('PluralityType').value= document.all("AppntPluralityType").value;}catch(ex){};
��try{document.all('DeathDate').value= document.all("AppntDeathDate").value;}catch(ex){};
��try{document.all('SmokeFlag').value= document.all("AppntSmokeFlag").value;}catch(ex){};
��try{document.all('BlacklistFlag').value= document.all("AppntBlacklistFlag").value;}catch(ex){};
��try{document.all('Proterty').value= document.all("AppntProterty").value;}catch(ex){};
��//try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
��try{document.all('State').value= document.all("AppntState").value;}catch(ex){};
��try{document.all('Operator').value= document.all("AppntOperator").value;}catch(ex){};
��try{document.all('MakeDate').value= document.all("AppntMakeDate").value;}catch(ex){};
��try{document.all('MakeTime').value= document.all("AppntMakeTime").value;}catch(ex){};
��try{document.all('ModifyDate').value= document.all("AppntModifyDate").value;}catch(ex){};
��try{document.all('ModifyTime').value= document.all("AppntModifyTime").value;}catch(ex){};
��try{document.all('PostalAddress').value= document.all("AppntPostalAddress").value;}catch(ex){};
��try{document.all('ZipCode').value= document.all("AppntZipCode").value;}catch(ex){};
��try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
��try{document.all('Fax').value= document.all("AppntFax").value;}catch(ex){};
��try{document.all('Mobile').value= document.all("AppntMobile").value;}catch(ex){};
��try{document.all('EMail').value= document.all("AppntEMail").value;}catch(ex){};
��try{document.all('GrpName').value= document.all("AppntGrpName").value;}catch(ex){};
��try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
��try{document.all('CompanyAddress').value= document.all("AppntCompanyAddress").value;}catch(ex){};
��try{document.all('GrpZipCode').value= document.all("AppntGrpZipCode").value;}catch(ex){};
��try{document.all('GrpFax').value= document.all("AppntGrpFax").value;}catch(ex){};
��try{document.all('HomeAddress').value= document.all("AppntHomeAddress").value;}catch(ex){};
��try{document.all('HomePhone').value= document.all("AppntHomePhone").value;}catch(ex){};
��try{document.all('HomeZipCode').value= document.all("AppntHomeZipCode").value;}catch(ex){};
��try{document.all('HomeFax').value= document.all("AppntHomeFax").value;}catch(ex){};
��//try{document.all('RelationToAppnt').value="00";}catch(ex){};
��try{document.all('InsuredProvince').value= document.all("AppntProvince").value;}catch(ex){};
��try{document.all('InsuredCity').value= document.all("AppntCity").value;}catch(ex){};
��try{document.all('InsuredDistrict').value= document.all("AppntDistrict").value;}catch(ex){};
��try{document.all('LicenseType').value= document.all("AppntLicenseType").value;}catch(ex){};
  try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('RgtAddress').value= document.all("AppntRgtAddress").value;}catch(ex){};
  try{document.all('CompanyPhone').value= document.all("AppntCompanyPhone").value;}catch(ex){};
  try{document.all('Avoirdupois').value= document.all("AppntAvoirdupois").value;}catch(ex){};
  //showOneCodeName('incomeway','IncomeWay','IncomeWayName');

/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
  }
***************************/
}
