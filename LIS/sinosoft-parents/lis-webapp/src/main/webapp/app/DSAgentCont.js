var turnPage = new turnPageClass();
var mAction ="";
var sqlresourcename = "app.DSAgentContSql";
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
   // var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
  
var sqlid1="DSAgentContSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(InsuredNo);
  
    arrResult=easyExecSql(mySql1.getString());
	//������LDPerson����δ����ͻ������---����������---����Ϣ���ʴ�LCCustomerImpartParams���ͻ���֪��������ѯ
	 //��ѯ��������ͬ��,�ͻ����룬�ͻ�����<1--�ͻ�����Ϊ������>����֪����<02--������֪>����֪���<000>��
	
	/* var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
		  +" and tt.impartver='02' and tt.impartcode='000'";
*/
var sqlid2="DSAgentContSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(ContNo);
mySql2.addSubPara(InsuredNo);
  

	 var ssArr=easyExecSql(mySql2.getString());
	try
	{
	 arrResult[0][13]=ssArr[0][1];//���
	 arrResult[0][14]=ssArr[1][1];//����		
	}catch(ex){};
    if(arrResult!=null)
    {
        displayInsuredInfo();
    }
  //  strSQL ="select * from LCInsured where ContNo = '"+prtNo+"' and InsuredNo='"+InsuredNo+"'";
  
var sqlid3="DSAgentContSql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(prtNo);
mySql3.addSubPara(InsuredNo);
    
  
    arrResult=easyExecSql(mySql3.getString());
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
		fm.ProposalGrpContNo.value  = arrResultCont[0][5];
		fm.ContType.value           = arrResultCont[0][6];
		fm.FamilyID.value           = arrResultCont[0][8];
		fm.AgentCom.value     		= arrResultCont[0][13];
		fm.AgentCode.value          = arrResultCont[0][14];
		fm.SaleChnl.value           = arrResultCont[0][18];
		fm.PayIntv.value            = arrResultCont[0][21];
		fm.PolApplyDate.value       = arrResultCont[0][60];
		fm.FirstTrialOperator.value = arrResultCont[0][70];
		fm.FirstTrialDate.value     = arrResultCont[0][71];
		fm.Handler.value          	= arrResultCont[0][19];
		fm.AgentComName.value 		= arrResultCont[0][89];
		fm.Password.value           = arrResultCont[0][20];
		fm.SumPrem.value            = arrResultCont[0][43];
		//fm.ApproveCode.value        = arrResultCont[0][52];
		//fm.ApproveTime.value   		= arrResultCont[0][54];
		fm.OutPayFlag.value  		= arrResultCont[0][25];
		fm.PayMode.value     		= arrResultCont[0][22];
		fm.PayLocation.value 		= arrResultCont[0][23];
		fm.BankCode.value 			= arrResultCont[0][31];
		fm.AccName.value     		= arrResultCont[0][33];
		fm.BankAccNo.value   		= arrResultCont[0][32];
		fm.AutoPayFlag.value 		= arrResultCont[0][86];
		fm.SignAgentName.value		= arrResultCont[0][90];
		fm.AgentSignDate.value		= arrResultCont[0][91];
		fm.UWOperator.value         = arrResultCont[0][56];
		fm.UWDate.value             = arrResultCont[0][57];
		fm.ImpartRemark.value       = arrResultCont[0][95];
	}catch(ex){
		alert("��ʾ��ͬ��Ϣʱ����");
	}
}
//��ʾ������Ϣ
function displayPol(arrResultPol){
	fm.GetYear.value      = arrResultPol[0][37];
	fm.LiveGetMode.value  = arrResultPol[0][82];
	//fm.BonusGetMode.value = arrResultPol[0][89];
	fm.StandbyFlag3.value = arrResultPol[0][102];
	fm.GetLimit.value     = arrResultPol[0][118];
	fm.BonusGetMode.value = arrResultPol[0][84];
}
//������Ҫ��ʾ����Ϣ
function displayOther(){
	//var tSqlName = "select AppSignName,InsSignName2 from lbpocont where contno='"+prtNo+"' and inputno='"+InputNo+"'";
	
var sqlid4="DSAgentContSql4";
var mySql4=new SqlClass();
mySql4.setResourceName(sqlresourcename);
mySql4.setSqlId(sqlid4);
mySql4.addSubPara(prtNo);
mySql4.addSubPara(InputNo);
	
	var arrResultName;
	arrResultName=easyExecSql(mySql4.getString());
	if(arrResultName!=null){
		fm.AppSignName.value = arrResultName[0][0];
		if(arrResultName[0][1]!=null){
			fm.InsSignName2.value = arrResultName[0][1];
		}
	}
	//var tSql ="select managecom from es_doc_main where doccode ='"+prtNo+"'";

var sqlid5="DSAgentContSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(prtNo);

	var tManageCom = easyExecSql(mySql5.getString());
	if(tManageCom==null||tManageCom=="null"){
	fm.ManageCom.value = "";
	fm.ScanCom.value   = "";
	}else{
	fm.ManageCom.value = tManageCom[0][0];
	fm.ScanCom.value   = tManageCom[0][0];
	}
}
//��ʾͶ������Ϣ
function displayAppnt(arrResultAppnt){
	fm.AppntNo.value           = arrResultAppnt[0][5];
	fm.AppntFillNo.value       = arrResultAppnt[0][3];
	fm.RelationToInsured.value = arrResultAppnt[0][45];
	fm.AppntName.value         = arrResultAppnt[0][7];
	fm.AppntSex.value          = arrResultAppnt[0][8];
	fm.AppntBirthday.value     = arrResultAppnt[0][9];
	fm.AppntIDType.value       = arrResultAppnt[0][12];
	fm.AppntIDNo.value         = arrResultAppnt[0][13];
	fm.AppntNativePlace.value  = arrResultAppnt[0][14];
	fm.AppntRgtAddress.value   = arrResultAppnt[0][16];
	fm.AppntMarriage.value     = arrResultAppnt[0][17];
	fm.AppntOccupationCode.value=arrResultAppnt[0][32];
	fm.AppntWorkType.value     = arrResultAppnt[0][33];
	fm.AppntPluralityType.value= arrResultAppnt[0][34];
	fm.AppntPostalAddress.value= arrResultAppnt[0][59];
	fm.AppntZipCode.value      = arrResultAppnt[0][58];
	fm.AppntHomeAddress.value  = arrResultAppnt[0][55];
	fm.AppntHomeZipCode.value  = arrResultAppnt[0][54];
	fm.AppntCompanyPhone.value     = arrResultAppnt[0][49];
	fm.AppntMobile.value       = arrResultAppnt[0][47];
	fm.AppntEMail.value        = arrResultAppnt[0][46];
	fm.AppntCompanyAddress.value      = arrResultAppnt[0][51];
	fm.AppntSocialInsuFlag.value = arrResultAppnt[0][60];
	fm.AppntIDExpDate.value = arrResultAppnt[0][61];
}
//��ʾ��������Ϣ
function displayInsured(arrResultInsured){
	fm.InputNo.value         = arrResultInsured[0][2];
	fm.InsuredNo.value       = arrResultInsured[0][4];
	fm.InsuredFillNo.value   = arrResultInsured[0][3]
	//fm.RelationToAppnt.value = arrResultInsured[0][11];
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
	fm.SocialInsuFlag.value		 = arrResultInsured[0][73];
	fm.IDExpDate.value		 = arrResultInsured[0][74];
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
	{}
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

//��ͬ��Ͷ����������Ϣ������
function saveInsured(wFlag){
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//���������¼�룬����У���Ƿ����д������ݶ��Ѿ�ȷ��
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="INSERT";
	document.all( 'fmAction' ).value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	document.getElementById("fm").submit(); //�ύ
}

//��ͬ��Ͷ�������ˣ��޸�
function updateInsured(wFlag){
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();		document.getElementById("fm").submit(); //�ύ
}
//������Ϣ���޸�
function updateRisk(wFlag){
	saveRisk(wFlag);
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();		document.getElementById("fm").submit(); //�ύ
}
//���汣�ս���𡢺������޸�
function updateGet(wFlag){
	saveGet(wFlag);
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
	fm.ContType.value="3";
	mAction="UPDATE";
	fm.action="DSGetSave.jsp";
	document.all( 'fmAction' ).value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();		
	document.getElementById("fm").submit(); //�ύ
}

function saveImpart(wFlag){
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();		
	document.getElementById("fm").submit(); //�ύ
	disabled(true);
}
function updateImpart(wFlag){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	fm.fmAction.value=mAction;
	fm.action="DSImpartSave.jsp";
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();		
	document.getElementById("fm").submit(); //�ύ
}
//��ʾ��֪��Ϣ
function displayImpart(){
    //alert("InputTime:"+InputTime);
	//tongmeng 2009-04-02 modify
	//���˺��н�����֪Ͷ���Ի�
	if(InputTime=="2"){//����¼��
	//var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,Insured1,PrtFlag,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer in('A05','A06') and InputNo='3' order by impartver,ImpartCode";


var sqlid6="DSAgentContSql6";
var mySql6=new SqlClass();
mySql6.setResourceName(sqlresourcename);
mySql6.setSqlId(sqlid6);
mySql6.addSubPara(prtNo);
 		var ImpartSql = mySql6.getString();

	
		//var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,Insured1,PrtFlag,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer<>'A03' and InputNo='3' order by impartver,ImpartCode";
		var arrResult;
		arrResult=easyExecSql(ImpartSql);
		
		var ImpartLength = 0;
		if(arrResult!=null)
		{
			ImpartLength = arrResult.length;
		}
		if(arrResult!=null)
		{  
			turnPage.queryModal(ImpartSql, ImpartGrid,null,null,ImpartLength);
		}else{
			alert('��ѯ��֪�޽��,��֪����¼�����!');
			return false;
			//var ImpartSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = '101' order by ImpartCode";
			//turnPage.queryModal(ImpartSql2, ImpartGrid,null,null,27);
		}
		//var AgentSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer='A03' and InputNo='3' order by impartver,impartcode";
	
var sqlid7="DSAgentContSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(prtNo);

	
		var arrResult2;
		arrResult2=easyExecSql(mySql7.getString());
		if(arrResult2!=null){
			turnPage.queryModal(mySql7.getString(), AgentImpartGrid);
		}else{
		//	var AgentSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = 'A03' order by ImpartCode";

var sqlid8="DSAgentContSql8";
var mySql8=new SqlClass();
mySql8.setResourceName(sqlresourcename);
mySql8.setSqlId(sqlid8);
mySql8.addSubPara("1");

			turnPage.queryModal(mySql8.getString(),AgentImpartGrid);
		}
	}else{
		//09-11-19�±��շ�Ͷ������ʽ�޸ģ������µĸ�֪����
//		var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,Insured1,PrtFlag,Insured2,insured3,fillNo from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer <>'A03' and InputNo='"+InputNo+"' order by impartver,impartcode";
	//	var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,Insured1,PrtFlag,Insured2,insured3,fillNo from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer in ('A05','A06') and InputNo='"+InputNo+"' order by impartver,impartcode";

var sqlid9="DSAgentContSql9";
var mySql9=new SqlClass();
mySql9.setResourceName(sqlresourcename);
mySql9.setSqlId(sqlid9);
mySql9.addSubPara(prtNo);
mySql9.addSubPara(InputNo);

		var arrResult;
		arrResult=easyExecSql(mySql9.getString());
		var ImpartLength = 0;
		if(arrResult!=null)
		{
			ImpartLength = arrResult.length;
		}
		if(arrResult!=null)
		{  
			turnPage.queryModal(mySql9.getString(), ImpartGrid,null,null,ImpartLength);
		}else{
			//var ImpartSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = '101' order by ImpartCode";
			//turnPage.queryModal(ImpartSql2, ImpartGrid,null,null,27);
		}
	//	var AgentSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer='A03' and InputNo='"+InputNo+"' order by impartver,impartcode";

var sqlid10="DSAgentContSql10";
var mySql10=new SqlClass();
mySql10.setResourceName(sqlresourcename);
mySql10.setSqlId(sqlid10);
mySql10.addSubPara(prtNo);
mySql10.addSubPara(InputNo);

		var arrResult2;
		arrResult2=easyExecSql(mySql10.getString());
		if(arrResult2!=null){
			turnPage.queryModal(mySql10.getString(), AgentImpartGrid);
		}else{
			//var AgentSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = 'A03' order by ImpartCode";
			//turnPage.queryModal(AgentSql2,AgentImpartGrid);
		}
	}
	//������Щ�ǡ���
	disabled(true);
}
//��Щ��֪������д�Ƿ� ��ʱ�����´���
function disabled(tFlag){
try{
	var tempOrder="document.all('ImpartGrid5')[0].disabled="+tFlag+";";
	  			eval(tempOrder);	
	var tempOrder="document.all('ImpartGrid6')[0].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(0, 5, "0");		
	var tempOrder="document.all('ImpartGrid5')[33].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid6')[33].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('AgentImpartGrid5')[4].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('AgentImpartGrid6')[4].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('AgentImpartGrid5')[5].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('AgentImpartGrid6')[5].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('AgentImpartGrid5')[6].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('AgentImpartGrid6')[6].disabled="+tFlag+";";
	  			eval(tempOrder);
	//ImpartGrid.setRowColData(31, 5, "0");
	  			}catch(ex){}
}

function initType(){
   try{	
	ImpartGrid.setRowColData(0, 6, "0");
	ImpartGrid.setRowColData(33, 6, "0");
	  			}catch(ex){}
}
//��ʾ��ͬ��Ϣ
function displayCont(){
	divSamePerson.style.display="";
	fm.PrtNo.value = prtNo;
	fm.ProposalContNo.value = prtNo;
	fm.MissionID.value=tMissionID;
	fm.SubMissionID.value=tSubMissionID;
	fm.ContNo.value=prtNo;
	fm.InputNo.value=InputNo;
	fm.InputTime.value=InputTime;//alert(InputTime);
	//���InputTime��ֵ��2����ʾ����¼��,�뽫��button֮���INPUT����Ϊdisabled
	if(InputTime=="2"){
  		//var tContSql2="select * from lbpocont where contno='"+prtNo+"' and InputNo='3'";

var sqlid11="DSAgentContSql11";
var mySql11=new SqlClass();
mySql11.setResourceName(sqlresourcename);
mySql11.setSqlId(sqlid11);
mySql11.addSubPara(prtNo);


  		var arrResultCont2;
		arrResultCont2=easyExecSql(mySql11.getString());
		displayContInfo(arrResultCont2);
	//	var tAppntSql2 = "select * from lbpoappnt where contno='"+prtNo+"' and InputNo='3'";

var sqlid12="DSAgentContSql12";
var mySql12=new SqlClass();
mySql12.setResourceName(sqlresourcename);
mySql12.setSqlId(sqlid12);
mySql12.addSubPara(prtNo);

		var arrResultAppnt2;
		arrResultAppnt2=easyExecSql(mySql12.getString());
		displayAppnt(arrResultAppnt2);
	//	var tInsuredSql2 = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='1' and InputNo='3'";

var sqlid13="DSAgentContSql13";
var mySql13=new SqlClass();
mySql13.setResourceName(sqlresourcename);
mySql13.setSqlId(sqlid13);
mySql13.addSubPara(prtNo);


		var arrResultInsured2;
		arrResultInsured2=easyExecSql(mySql13.getString());
		displayInsured(arrResultInsured2);
//		var tBnfSql2 = "select 1,b.BnfType,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress,b.FillNo,b.insuredno,0 from lbpobnf b where b.contno='"+prtNo+"' and b.inputno='3'"   

var sqlid14="DSAgentContSql14";
var mySql14=new SqlClass();
mySql14.setResourceName(sqlresourcename);
mySql14.setSqlId(sqlid14);
mySql14.addSubPara(prtNo);

		var arrResultBnf2;
		arrResultBnf2=easyExecSql(mySql14.getString());
		turnPage.queryModal(tBnfSql2,BnfGrid);
//		var tRiskSql2="select 1,1,b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.polno,b.fillno,b.insuredno,b.mainpolno from lbpopol b where b.contno='"+prtNo+"' and b.inputno='3' order by risksequence,fillno"

var sqlid15="DSAgentContSql15";
var mySql15=new SqlClass();
mySql15.setResourceName(sqlresourcename);
mySql15.setSqlId(sqlid15);
mySql15.addSubPara(prtNo);

		var arrResultRisk2;
		arrResultRisk2=easyExecSql(mySql15.getString());
		turnPage.queryModal(tRiskSql2,PolGrid);
	//	var tPolSql2="select * from lbpopol where contno='"+prtNo+"' and inputno='3' order by fillno";

var sqlid16="DSAgentContSql16";
var mySql16=new SqlClass();
mySql16.setResourceName(sqlresourcename);
mySql16.setSqlId(sqlid16);
mySql16.addSubPara(prtNo);

		var arrResultPol2;
		arrResultPol2=easyExecSql(mySql16.getString());
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
  		//var tErrorCont = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOCont' group by errortag";

var sqlid17="DSAgentContSql17";
var mySql17=new SqlClass();
mySql17.setResourceName(sqlresourcename);
mySql17.setSqlId(sqlid17);
mySql17.addSubPara(prtNo);

  		var arrResultErrorCont;//prompt("",tErrorSql);
  		arrResultErrorCont=easyExecSql(mySql17.getString());
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
					//	var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorCont[k][0]+"' and otherno='1' and tablename='LBPOCont' order by errorcount ";
     				
var sqlid18="DSAgentContSql18";
var mySql18=new SqlClass();
mySql18.setResourceName(sqlresourcename);
mySql18.setSqlId(sqlid18);
mySql18.addSubPara(prtNo);
mySql18.addSubPara(arrResultErrorCont[k][0]);
     				
     				var arrResultTitle = easyExecSql(mySql18.getString());
     				if(arrResultTitle!=null){
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
  				}catch(ex){
  					alert("��ʼ���������");
  				}
  			}
  		}
  		//����Ͷ������Ϣ
  	//	var tErrorAppnt = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOAppnt' group by errortag";
 
 var sqlid19="DSAgentContSql19";
var mySql19=new SqlClass();
mySql19.setResourceName(sqlresourcename);
mySql19.setSqlId(sqlid19);
mySql19.addSubPara(prtNo);
 
  		var arrResultErrorAppnt;//prompt("",tErrorSql);
  		arrResultErrorAppnt=easyExecSql(mySql19.getString());
  		if(arrResultErrorAppnt!=null){
  			for(k=0;k<arrResultErrorAppnt.length;k++){
  				//alert(arrResultErrorAppnt[k][0]);
  				try{
	  				var ele="";
	  				if(arrResultErrorAppnt[k][0]=="CompanyAddress"||arrResultErrorAppnt[k][0]=="CompanyPhone"
	  				   ||arrResultErrorAppnt[k][0]=="EMail"||arrResultErrorAppnt[k][0]=="HomeAddress"
	  				   ||arrResultErrorAppnt[k][0]=="HomeZipCode"||arrResultErrorAppnt[k][0]=="IDNo"
	  				   ||arrResultErrorAppnt[k][0]=="IDType"||arrResultErrorAppnt[k][0]=="Marriage"
	  				   ||arrResultErrorAppnt[k][0]=="Mobile"||arrResultErrorAppnt[k][0]=="Name"
	  				   ||arrResultErrorAppnt[k][0]=="NativePlace"||arrResultErrorAppnt[k][0]=="PluralityType"
	  				   ||arrResultErrorAppnt[k][0]=="RgtAddress"||arrResultErrorAppnt[k][0]=="Sex"
	  				   ||arrResultErrorAppnt[k][0]=="WorkType"||arrResultErrorAppnt[k][0]=="OccupationCode"
	  				   ||arrResultErrorAppnt[k][0]=="Birthday"||arrResultErrorAppnt[k][0]=="PostalAddress"
	  				   ||arrResultErrorAppnt[k][0]=="ZipCode"||arrResultErrorAppnt[k][0]=="SocialInsuFlag"
	  				   ||arrResultErrorAppnt[k][0]=="IDExpDate"){
	  				   	ele="Appnt"+arrResultErrorAppnt[k][0];
	  				   }else{
	  				   	ele=arrResultErrorAppnt[k][0];
	  				   }
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
		//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorAppnt[k][0]+"' and otherno='1' and tablename='LBPOAppnt' order by errorcount ";
     			
 var sqlid20="DSAgentContSql20";
var mySql20=new SqlClass();
mySql20.setResourceName(sqlresourcename);
mySql20.setSqlId(sqlid20);
mySql20.addSubPara(prtNo);	
mySql20.addSubPara(arrResultErrorAppnt[k][0]);	
     			
     				var arrResultTitle = easyExecSql(mySql20.getString());
     				if(arrResultTitle!=null){
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
  				}catch(ex){
  					alert("��ʼ���������");
  				}
  			}
  		}
  		//����������Ϣ
  //		var tErrorInsured = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOInsured' group by errortag";
 
var sqlid21="DSAgentContSql21";
var mySql21=new SqlClass();
mySql21.setResourceName(sqlresourcename);
mySql21.setSqlId(sqlid21);
mySql21.addSubPara(prtNo);	

 
 
  		var arrResultErrorInsured;//prompt("",tErrorSql);
  		arrResultErrorInsured=easyExecSql(mySql21.getString());
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
				//		var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorInsured[k][0]+"' and otherno='1' and tablename='LBPOInsured' order by errorcount ";
 
var sqlid22="DSAgentContSql22";
var mySql22=new SqlClass();
mySql22.setResourceName(sqlresourcename);
mySql22.setSqlId(sqlid22);
mySql22.addSubPara(prtNo);	
mySql22.addSubPara(arrResultErrorInsured[k][0]);	
 
     				var arrResultTitle = easyExecSql(mySql22.getString());
     				if(arrResultTitle!=null){
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
  				}catch(ex){
  					alert("��ʼ���������");
  				}
  			}
  		}
 // 		var tErrorPol = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOPol' and otherno='1' group by errortag";

var sqlid23="DSAgentContSql23";
var mySql23=new SqlClass();
mySql23.setResourceName(sqlresourcename);
mySql23.setSqlId(sqlid23);
mySql23.addSubPara(prtNo);	


  		var arrResultErrorPol;//prompt("",tErrorSql);
  		arrResultErrorPol=easyExecSql(mySql23.getString());
  		if(arrResultErrorPol!=null){
  			for(k=0;k<arrResultErrorPol.length;k++){
  				if(arrResultErrorPol[k][0]=="LiveGetMode"){
  					fm.LiveGetMode.className="warn";
  					fm.LiveGetMode.disabled=false;
  					var tObj = document.all("LiveGetMode");
  					var tTitle = "";
			//		var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";

var sqlid24="DSAgentContSql24";
var mySql24=new SqlClass();
mySql24.setResourceName(sqlresourcename);
mySql24.setSqlId(sqlid24);
mySql24.addSubPara(prtNo);	
mySql24.addSubPara(arrResultErrorPol[k][0]);	


	     			var arrResultTitle = easyExecSql(mySql24.getString());
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
	//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";

var sqlid25="DSAgentContSql25";
var mySql25=new SqlClass();
mySql25.setResourceName(sqlresourcename);
mySql25.setSqlId(sqlid25);
mySql25.addSubPara(prtNo);	
mySql25.addSubPara(arrResultErrorPol[k][0]);	

	     			var arrResultTitle = easyExecSql(mySql25.getString());
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
			//		var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";

var sqlid26="DSAgentContSql26";
var mySql26=new SqlClass();
mySql26.setResourceName(sqlresourcename);
mySql26.setSqlId(sqlid26);
mySql26.addSubPara(prtNo);	
mySql26.addSubPara(arrResultErrorPol[k][0]);

	     			var arrResultTitle = easyExecSql(mySql26.getString());
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
		//			var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	
var sqlid27="DSAgentContSql27";
var mySql27=new SqlClass();
mySql27.setResourceName(sqlresourcename);
mySql27.setSqlId(sqlid27);
mySql27.addSubPara(prtNo);	
mySql27.addSubPara(arrResultErrorPol[k][0]);
	
	     			var arrResultTitle = easyExecSql(mySql27.getString());
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
	//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";

var sqlid28="DSAgentContSql28";
var mySql28=new SqlClass();
mySql28.setResourceName(sqlresourcename);
mySql28.setSqlId(sqlid28);
mySql28.addSubPara(prtNo);	
mySql28.addSubPara(arrResultErrorPol[k][0]);

	     			var arrResultTitle = easyExecSql(mySql28.getString());
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
  //		var tBnfGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOBnf' and bussno='"+prtNo+"' and bussnotype='TB' ";
 
 var sqlid29="DSAgentContSql29";
var mySql29=new SqlClass();
mySql29.setResourceName(sqlresourcename);
mySql29.setSqlId(sqlid29);
mySql29.addSubPara(prtNo);	

 
  		var arrResultCBnfGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCBnfGrid=easyExecSql(mySql29.getString());
  		if(arrResultCBnfGrid!=null){
	  		for(a=0;a<arrResultCBnfGrid.length;a++){
	  			var tFillNo=arrResultCBnfGrid[a][0];
	  			var tFieldName=arrResultCBnfGrid[a][1];
	  			var tLineCount=0;
	  			tLineCount=getLineCount("BnfGrid",tFieldName);
	  			var tTitle = "";
		//		var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOBnf' and otherno='"+tFillNo+"' order by errorcount ";
   
 var sqlid30="DSAgentContSql30";
var mySql30=new SqlClass();
mySql30.setResourceName(sqlresourcename);
mySql30.setSqlId(sqlid30);
mySql30.addSubPara(prtNo);	
mySql30.addSubPara(tFieldName);
mySql30.addSubPara(tFillNo);
   
     			var arrResultTitle = easyExecSql(mySql30.getString());//prompt("",tSQL);
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
	  			//��ʾһ�����¼�����Ϣ
	  			BnfGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
	  			//������ʾ
	  			BnfGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
	  			//������disabled=false;
	  			var tempOrder="document.all('BnfGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}
  		/**����*/
  	//	var tPolGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOPol' and bussno='"+prtNo+"' ";
 
var sqlid31="DSAgentContSql31";
var mySql31=new SqlClass();
mySql31.setResourceName(sqlresourcename);
mySql31.setSqlId(sqlid31);
mySql31.addSubPara(prtNo);	

 
  		var arrResultCPolGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCPolGrid=easyExecSql(mySql31.getString());
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
		//			var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOPol' and otherno='"+tFillNo+"' order by errorcount ";

var sqlid32="DSAgentContSql32";
var mySql32=new SqlClass();
mySql32.setResourceName(sqlresourcename);
mySql32.setSqlId(sqlid32);
mySql32.addSubPara(prtNo);
mySql32.addSubPara(tFieldName);
mySql32.addSubPara(tFillNo);

	     			var arrResultTitle = easyExecSql(mySql32.getString());//prompt("",tSQL);
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
		  			//������disabled=false;
		  			var tempOrder="document.all('PolGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
		  			//alert("tempOrder"+tempOrder);
		  			eval(tempOrder);
	  			}
	  		}
  		}//alert(719);
  		//������֪
  	//	var tCusImpartGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOCustomerImpart' and bussno='"+prtNo+"' and otherno>0 and otherno<35 ";

var sqlid33="DSAgentContSql33";
var mySql33=new SqlClass();
mySql33.setResourceName(sqlresourcename);
mySql33.setSqlId(sqlid33);
mySql33.addSubPara(prtNo);


  		var arrResultCusImpartGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCusImpartGrid=easyExecSql(mySql33.getString());
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
	//			var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOCustomerImpart' and otherno='"+tFillNo+"' order by errorcount ";
 
 var sqlid34="DSAgentContSql34";
var mySql34=new SqlClass();
mySql34.setResourceName(sqlresourcename);
mySql34.setSqlId(sqlid34);
mySql34.addSubPara(prtNo);
mySql34.addSubPara(tFieldName);
mySql34.addSubPara(tFillNo);
 
     			var arrResultTitle = easyExecSql(mySql34.getString());//prompt("",tSQL);
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
	  			//������disabled=false;
	  			var tempOrder="document.all('ImpartGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}//alert(756);
  		//ҵ��Ա��֪
  		//var tAgeImpartGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOCustomerImpart' and bussno='"+prtNo+"' and otherno>34";
 
var sqlid35="DSAgentContSql35";
var mySql35=new SqlClass();
mySql35.setResourceName(sqlresourcename);
mySql35.setSqlId(sqlid35);
mySql35.addSubPara(prtNo);

 
  		var arrResultAgeImpartGrid;
  		//prompt("",tAgeImpartGridSql);
  		arrResultAgeImpartGrid=easyExecSql(mySql35.getString());
  		if(arrResultAgeImpartGrid!=null){//alert(arrResultAgeImpartGrid.length);
	  		for(a=0;a<arrResultAgeImpartGrid.length;a++){
	  			var tFillNo=arrResultAgeImpartGrid[a][0];
	  			var tFieldName=arrResultAgeImpartGrid[a][1];
	  			var tLineCount=0;//alert(tFieldName);
	  			tLineCount=getLineCount("ImpartGrid",tFieldName);
//	  			alert("tFillNo: "+tFillNo);
//	  			alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			var tTitle = "";
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOCustomerImpart' and otherno='"+tFillNo+"' order by errorcount ";
 
var sqlid36="DSAgentContSql36";
var mySql36=new SqlClass();
mySql36.setResourceName(sqlresourcename);
mySql36.setSqlId(sqlid36);
mySql36.addSubPara(prtNo);
mySql36.addSubPara(tFieldName);
mySql36.addSubPara(tFillNo);
 
     			var arrResultTitle = easyExecSql(mySql36.getString());/**prompt("",tSQL);*/ //alert("arrResultTitle.length:"+arrResultTitle.length);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "��"+arrResultTitle[ti][0]+"��¼����:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
//     			alert(tTitle);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			AgentImpartGrid.setRowColTitle((tFillNo-34-1),tLineCount,tTitle);
	  			AgentImpartGrid.setRowColClass((tFillNo-34-1),tLineCount,'warn');
	  			var tempOrder="document.all('AgentImpartGrid"+tLineCount+"')["+(tFillNo-34-1)+"].disabled=false;";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}//alert(793);
  		//-------------------------------------------------------------------------------//
	}else{
	//	var tContSql="select * from lbpocont where contno='"+prtNo+"' and InputNo='"+InputNo+"'";
	
var sqlid37="DSAgentContSql37";
var mySql37=new SqlClass();
mySql37.setResourceName(sqlresourcename);
mySql37.setSqlId(sqlid37);
mySql37.addSubPara(prtNo);
mySql37.addSubPara(InputNo);

	
		var arrResultCont;
		arrResultCont=easyExecSql(mySql37.getString());
		if(arrResultCont!=null){
			displayContInfo(arrResultCont);
		}
	//	var tAppntSql = "select * from lbpoappnt where contno='"+prtNo+"' and InputNo='"+InputNo+"'";

var sqlid38="DSAgentContSql38";
var mySql38=new SqlClass();
mySql38.setResourceName(sqlresourcename);
mySql38.setSqlId(sqlid38);
mySql38.addSubPara(prtNo);
mySql38.addSubPara(InputNo);

		var arrResultAppnt;
		arrResultAppnt=easyExecSql(mySql38.getString());
		if(arrResultAppnt!=null){
			displayAppnt(arrResultAppnt);
		}
		//var tInsuredSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='1' and InputNo='"+InputNo+"'";

var sqlid39="DSAgentContSql39";
var mySql39=new SqlClass();
mySql39.setResourceName(sqlresourcename);
mySql39.setSqlId(sqlid39);
mySql39.addSubPara(prtNo);
mySql39.addSubPara(InputNo);

		var arrResultInsured;
		arrResultInsured=easyExecSql(mySql39.getString());
		if(arrResultInsured!=null){
			displayInsured(arrResultInsured);
		}
		//var tBnfSql = "select a.sequenceno,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress from lbpoinsured a,lbpobnf b where a.contno=b.contno and a.inputno=b.inputno and a.contno='"+prtNo+"' and a.inputno='"+InputNo+"'";
	//	var tBnfSql = "select 1,b.BnfType,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress,b.FillNo,b.insuredno,0 from lbpobnf b where b.contno='"+prtNo+"' and b.inputno='"+InputNo+"'"   

var sqlid40="DSAgentContSql40";
var mySql40=new SqlClass();
mySql40.setResourceName(sqlresourcename);
mySql40.setSqlId(sqlid40);
mySql40.addSubPara(prtNo);
mySql40.addSubPara(InputNo);

		var arrResultBnf;//prompt("",tBnfSql);
		arrResultBnf=easyExecSql(mySql40.getString());
		if(arrResultBnf!=null){
			turnPage.queryModal(mySql40.getString(),BnfGrid);
		}
	//	var tRiskSql="select 1,1,b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.polno,b.fillno,b.insuredno,b.mainpolno from lbpopol b where b.contno='"+prtNo+"' and b.inputno='"+InputNo+"' order by risksequence,fillno"

var sqlid41="DSAgentContSql41";
var mySql41=new SqlClass();
mySql41.setResourceName(sqlresourcename);
mySql41.setSqlId(sqlid41);
mySql41.addSubPara(prtNo);
mySql41.addSubPara(InputNo);

		var arrResultRisk;//prompt("",tRiskSql);
		arrResultRisk=easyExecSql(mySql41.getString());
		if(arrResultRisk!=null){
			turnPage.queryModal(mySql41.getString(),PolGrid);
		}
//		var tPolSql="select * from lbpopol where contno='"+prtNo+"' and inputno='"+InputNo+"' order by fillno";

var sqlid42="DSAgentContSql42";
var mySql42=new SqlClass();
mySql42.setResourceName(sqlresourcename);
mySql42.setSqlId(sqlid42);
mySql42.addSubPara(prtNo);
mySql42.addSubPara(InputNo);

		var arrResultPol;
		arrResultPol=easyExecSql(mySql42.getString());
		if(arrResultPol!=null){
			displayPol(arrResultPol);
		}
		displayOther();
	}
	
	emptyUndefined();
}
//
function afterCodeSelect( cCodeName, Field ){
	
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
		initBnfGrid();
	//	var tSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='"+Field.value+"' and InputNo='"+InputNo+"'";

var sqlid43="DSAgentContSql43";
var mySql43=new SqlClass();
mySql43.setResourceName(sqlresourcename);
mySql43.setSqlId(sqlid43);
mySql43.addSubPara(prtNo);
mySql43.addSubPara(Field.value);
mySql43.addSubPara(InputNo);

		var arrResult;
		arrResult=easyExecSql(mySql43.getString());
		if(arrResult!=null){
			displayInsured(arrResult);
	//		var tBnfSql = "select a.sequenceno,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress from lbpoinsured a,lbpobnf b where a.contno=b.contno and a.inputno=b.inputno and a.contno='"+prtNo+"' and a.inputno='"+InputNo+"' and a.SequenceNo='"+Field.value+"'";

var sqlid44="DSAgentContSql44";
var mySql44=new SqlClass();
mySql44.setResourceName(sqlresourcename);
mySql44.setSqlId(sqlid44);
mySql44.addSubPara(prtNo);
mySql44.addSubPara(InputNo);
mySql44.addSubPara(Field.value);

			var arrResultBnf;
			arrResultBnf=easyExecSql(mySql44.getString());
			if(arrResultBnf!=null){
				turnPage.queryModal(tBnfSql,BnfGrid);
			}
		}
	}
	if(cCodeName=="SequenceNo2"){
		initPolGrid();
	//	var tSql = "select "+Field.value+",b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.risksequence,b.polno  from lbpopol b where insuredno  in (select insuredno from lbpoinsured a where contno='"+prtNo+"' and  sequenceno='"+Field.value+"' and inputno='"+InputNo+"') and inputno='"+InputNo+"'";

var sqlid45="DSAgentContSql45";
var mySql45=new SqlClass();
mySql45.setResourceName(sqlresourcename);
mySql45.setSqlId(sqlid45);
mySql45.addSubPara(Field.value);
mySql45.addSubPara(prtNo);
mySql45.addSubPara(Field.value);
mySql45.addSubPara(InputNo);
mySql45.addSubPara(InputNo);


		var arrResult;
		arrResult=easyExecSql(mySql45.getString());
		if(arrResult!=null){
			turnPage.queryModal(tSql,PolGrid);
		}
	}
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
	}
	mAction = "";	
}
//¼�����
function inputConfirm(wFlag){
	if(InputTime==2){
		if(!beforeSubmit(wFlag)){
			return false;	
		}
	}
	fm.InputTime.value = InputTime;//alert("InputTime:"+InputTime);
	if(InputTime==2){
		fm.WorkFlowFlag.value = ActivityID;//alert(fm.WorkFlowFlag.value);
	}else{
		fm.WorkFlowFlag.value = ActivityID;
	}
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;			//¼�����
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	fm.action = "./DSInputConfirm.jsp";
	lockScreen('lkscreen');  
	document.getElementById("fm").submit(); //�ύ
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
	//var errorMessage="select errortag,errorcontent from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename not in ('LBPOBnf','LBPOAppnt')";

var sqlid46="DSAgentContSql46";
var mySql46=new SqlClass();
mySql46.setResourceName(sqlresourcename);
mySql46.setSqlId(sqlid46);
mySql46.addSubPara(prtNo);


	var errorMessageResult;
	errorMessageResult=easyExecSql(mySql46.getString());
	if(errorMessageResult!=null){
	for(var k=0;k<errorMessageResult.length;k++){
			//���Ƿ��ѽ��й�������Ϣȷ�Ͻ���У�飬����������д�����Ϣ���Ѿ�У����������
  			if(errorMessageResult[k][0]!="ImpartContent"&&errorMessageResult[k][0]!="ImpartParamModle"&&errorMessageResult[k][0]!="AgentGroup"&&errorMessageResult[k][0]!="NewPayMode"&&errorMessageResult[k][0]!="LBPOCustomerImpart"&&errorMessageResult[k][0]!="Prem"&&errorMessageResult[k][0]!="Amnt"
  			   &&errorMessageResult[k][0]!="RnewFlag"&&errorMessageResult[k][0]!="RiskCode"&&errorMessageResult[k][0]!="Mult"){
  				//alert(arrResultError[k][0]);
  				try{
	  				var ele="";
	  				var eleName="";
	  				ele=errorMessageResult[k][0];
	  				eleName=errorMessageResult[k][1];
	  				//alert(document.all(""+ele+"").name+""+":"+document.all(""+ele+"").verifyorder);
	  				//tongmeng 2009-03-19 modify
	  				//�������Ҫ¼����Ϣ,����ֱ��¼�ո񼴿�
	  				if(wFlag!=0){
		  				if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)&&document.all(""+ele+"").verifyorder==wFlag){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  				
		  					//alert(eleName+",��ȷ�ϣ�");
		  					//return false;
		  				}
	  				}else{
	  					if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  					//alert(eleName+",��ȷ�ϣ�");
		  					//return false;
		  				}
	  				}
	  				
  				}catch(ex){
  					//alert("��ʼ���������");
  				}
  				//����element���⴦��
  				if(errorMessageResult[k][0]=="AgentGroup"){
	  				if(fm.AgentGroup){
	  					if(fm.AgentGroup.value==""){
	  						//alert("���������δ����ȷ�ϣ�");
	  						//return false;
	  					}
	  				}
  				}
  				if(errorMessageResult[k][0]=="NewPayMode"){
	  				if(fm.NewPayMode){
	  					if(fm.NewPayMode.value==""){
	  						//alert("���ڽ�����ʽδ����ȷ�ϣ�");
	  						//return false;
	  					}
	  				}
  				}
  				
  			}
  		}
  		}
  		//Ͷ�������⴦��
  	//	var tErrorAppnt = "select errortag,errorcontent from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOAppnt' ";

var sqlid47="DSAgentContSql47";
var mySql47=new SqlClass();
mySql47.setResourceName(sqlresourcename);
mySql47.setSqlId(sqlid47);
mySql47.addSubPara(prtNo);

  		var arrResultErrorAppnt;//prompt("",tErrorSql);
  		arrResultErrorAppnt=easyExecSql(mySql47.getString());
  		if(arrResultErrorAppnt!=null){
  			for(k=0;k<arrResultErrorAppnt.length;k++){
  				//alert(arrResultErrorAppnt[k][0]);
	  				var ele="";
	  				if(arrResultErrorAppnt[k][0]=="CompanyAddress"||arrResultErrorAppnt[k][0]=="CompanyPhone"
	  				   ||arrResultErrorAppnt[k][0]=="EMail"||arrResultErrorAppnt[k][0]=="HomeAddress"
	  				   ||arrResultErrorAppnt[k][0]=="HomeZipCode"||arrResultErrorAppnt[k][0]=="IDNo"
	  				   ||arrResultErrorAppnt[k][0]=="IDType"||arrResultErrorAppnt[k][0]=="Marriage"
	  				   ||arrResultErrorAppnt[k][0]=="Mobile"||arrResultErrorAppnt[k][0]=="Name"
	  				   ||arrResultErrorAppnt[k][0]=="NativePlace"||arrResultErrorAppnt[k][0]=="PluralityType"
	  				   ||arrResultErrorAppnt[k][0]=="RgtAddress"||arrResultErrorAppnt[k][0]=="Sex"
	  				   ||arrResultErrorAppnt[k][0]=="WorkType"||arrResultErrorAppnt[k][0]=="OccupationCode"
	  				   ||arrResultErrorAppnt[k][0]=="Birthday"||arrResultErrorAppnt[k][0]=="PostalAddress"
	  				   ||arrResultErrorAppnt[k][0]=="ZipCode"){
	  				   	ele="Appnt"+arrResultErrorAppnt[k][0];
	  				   }else{
	  				   	ele=arrResultErrorAppnt[k][0];
	  				   }
	  				   var eleName=arrResultErrorAppnt[k][1];
	  				   //tongmeng 2009-03-19 modify
	  				//�������Ҫ¼����Ϣ,����ֱ��¼�ո񼴿�
	  				   if(wFlag!=0){
		  				if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)&&document.all(""+ele+"").verifyorder==wFlag){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  				
		  					//alert(eleName+",��ȷ�ϣ�");
		  					//return false;
		  				}
	  					}else{
	  					if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  					//alert(eleName+",��ȷ�ϣ�");
		  					//return false;
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


function afterSubmit( FlagStr, content ){
	showInfo.close();
	unlockScreen('lkscreen');  
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
		top.close();
	}
	mAction = "";
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
		if(tFieldName=="PrtFlag"){
			tLineCount=5;
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
    //else if (fm.SamePersonFlag.checked == true)
    //{
     // document.all('DivLCInsured').style.display = "none";
    //  divLCInsuredPerson.style.display = "none";
    //  divSalary.style.display = "none";

    //  displayissameperson();
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
       //try{document.all('RelationToMainInsured').value=""; }catch(ex){};
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
  try{document.all('IDExpDate').value= document.all("AppntIDExpDate").value;                    }catch(ex){};
  try{document.all('SocialInsuFlag').value= document.all("AppntSocialInsuFlag").value;                    }catch(ex){};  
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
