var turnPage = new turnPageClass();
var mSwitch = parent.VD.gVSwitch;
var prtNo=""
function haveMultiAgent(){
    if(document.all("multiagentflag").checked){
        DivMultiAgent.style.display="";	
    }
    else{
        DivMultiAgent.style.display="none";	
    }
}

//��ʼ����ѯ
function initQuery()
{
    QueryGrpCont();
    //QueryCValiDate();
	QueryChargeDate();
	QueryAgent();
	QueryGrpAppnt();
	QueryDetailGrpaddress();
	QueryRisk();
}

//��ѯ���屣����Ϣ
function QueryGrpCont()
{
    var tGrpContNo = document.all('GrpContNo').value;
    var strSQL = "select AgentCom,ReportNo,SaleChnl,PolApplyDate,CValiDate,ManageCom,Proposalgrpcontno from LCGrpCont "
               + "where GrpContNo='" + tGrpContNo + "'";
    var arrResult = easyExecSql(strSQL);
    if (arrResult == null)
        return;
    try{document.all('AgentCom').value = arrResult[0][0];}catch(ex){};
    try{document.all('ReportNo').value = arrResult[0][1];}catch(ex){};
    try{document.all('SaleChnl').value = arrResult[0][2];}catch(ex){};
    try{document.all('PolApplyDate').value = arrResult[0][3];}catch(ex){};
    try{document.all('CValiDate').value = arrResult[0][4];}catch(ex){};
    try{document.all('ManageCom').value = arrResult[0][5];}catch(ex){};
    try{document.all('ProposalGrpContNo').value = arrResult[0][5];}catch(ex){};
    showOneCodeName('comcode','ManageCom','ManageComName'); 
    showOneCodeName('SaleChnl','SaleChnl','SaleChnlName');
}

//��LJTempFee ���еõ�������Ч����
function QueryCValiDate()
{   
    prtNo = document.all('PrtNo').value;
    //������Ч��Ϊenteraccdate�ֶ�ֵ�ĵڶ���,����������SQL��ʹ��"+"�Ż�����⣬���� - (-1)���档
    var brrResult = easyExecSql("select to_date(max(enteraccdate)) - (-1) from ljtempfee where othernotype='4' and otherno='"+prtNo+"'");
    var CValiDate =""; 
    if(brrResult[0][0]=="")
    { 
      	var showStr="�޷����ݲ����շ������Զ����ɱ�����Ч���ڣ���ȷ���Ƿ��ѽ��ѻ����ֹ�¼��!";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px"); 
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=300;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    	//alert("�޷����ݲ����շ������Զ����ɱ�����Ч���ڣ���ȷ���Ƿ��ѽ��ѻ����ֹ�¼��!"); 
    	return;
    }
    else
    {   
        if(document.all('CValiDate').value==null||document.all('CValiDate').value=="")
        try {document.all('CValiDate').value = brrResult[0][0];} catch(ex) { alert("������Ч����������!");}; 
    } 
  	
}

function QueryChargeDate()
{
    prtNo=document.all('PrtNo').value;
    var arrResult = easyExecSql("select to_date(max(enteraccdate)) from ljtempfee where othernotype='4' and otherno='"+prtNo+"'");
    var ChargeDate =""; 
    if(arrResult[0][0]=="")
    { 
//        var showStr="�����շ����ڲ����ڣ���ȷ���Ƿ��ѽ���!";
//    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px"); 
        //alert("�����շ����ڲ����ڣ���ȷ���Ƿ��ѽ���!"); 
        return;
    }
    else
    { 
        try {document.all('PayDate').value = arrResult[0][0];} catch(ex) { alert("����������������!");}; 
    } 
}

//��ѯ����Ͷ���ͻ���Ϣ
function QueryGrpAppnt()
{
    var tPrtNo = trim(document.all('PrtNo').value);
    if ((tPrtNo == null) || (tPrtNo == "null") || (tPrtNo==""))
        return;
    var strSQL = "select a.GrpName,a.Asset,a.GrpNature,a.BusinessType,a.Peoples,a.Fax,b.AddressNo,b.CustomerNo,a.GetFlag,a.BankCode,a.BankAccNo,a.Remark "
         + "from LDGrp a,LCGrpAppnt b "
         + "where a.CustomerNo=b.CustomerNo and b.PrtNo = '" + tPrtNo +"'";
    var arrResult = easyExecSql(strSQL);
    if (arrResult == null)
        return;
    try{document.all('GrpName').value = arrResult[0][0];}catch(ex){}
    try{document.all('Asset').value = arrResult[0][1];}catch(ex){}
    try{document.all('GrpNature').value = arrResult[0][2];}catch(ex){}
    try{document.all('BusinessType').value = arrResult[0][3];}catch(ex){}
    try{document.all('Peoples').value = arrResult[0][4];}catch(ex){}
    try{document.all('Fax').value = arrResult[0][5];}catch(ex){}
    try{document.all('GrpAddressNo').value = arrResult[0][6];}catch(ex){}
    try{document.all('GrpNo').value = arrResult[0][7];}catch(ex){}
    try{document.all('GetFlag').value = arrResult[0][8];}catch(ex){}
    try{document.all('BankCode').value = arrResult[0][9];}catch(ex){}
    try{document.all('BankAccNo').value = arrResult[0][10];}catch(ex){}
    try{document.all('Remark').value = arrResult[0][11];}catch(ex){}
    showOneCodeName('GrpNature','GrpNature','GrpNatureName');
    showOneCodeName('BusinessType','BusinessType','BusinessTypeName');
    showOneCodeName('bank','BankCode','BankCodeName');
    showOneCodeName('PayMode','GetFlag','GetFlagName');
}

//��ѯ����Ͷ���ͻ���ַ��Ϣ
function QueryDetailGrpaddress(){
    if ((fm.GrpAddressNo.value == null) || (fm.GrpAddressNo.value == "null") || (fm.GrpAddressNo.value == ""))
        return;
    if ((fm.GrpNo.value == null) || (fm.GrpNo.value == "null") || (fm.GrpNo.value == ""))
        return;
    var strSQL = "select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b "
               + "where b.AddressNo='" + fm.GrpAddressNo.value + "' and b.CustomerNo='" + fm.GrpNo.value + "'";
    var arrResult = easyExecSql(strSQL);
    if (arrResult == null)
        return;
    try {document.all('GrpAddressNo').value = arrResult[0][0]; } catch(ex) { };
    try {document.all('GrpAddress').value = arrResult[0][1]; } catch(ex) { };
    try {document.all('GrpZipCode').value = arrResult[0][2]; } catch(ex) { };
    try {document.all('LinkMan1').value = arrResult[0][3]; } catch(ex) { };
    try {document.all('Department1').value = arrResult[0][4]; } catch(ex) { };
    //try {document.all('HeadShip1').value = arrResult[0][5]; } catch(ex) { };
    try {document.all('Phone1').value = arrResult[0][6]; } catch(ex) { };
    try {document.all('E_Mail1').value = arrResult[0][7]; } catch(ex) { };
    //try {document.all('Fax1').value = arrResult[0][8]; } catch(ex) { };
    try {document.all('LinkMan2').value = arrResult[0][9]; } catch(ex) { };
    try {document.all('Department2').value = arrResult[0][10]; } catch(ex) { };
    //try {document.all('HeadShip2').value = arrResult[0][11]; } catch(ex) { };
    try {document.all('Phone2').value = arrResult[0][12]; } catch(ex) { };
    try {document.all('E_Mail2').value = arrResult[0][13]; } catch(ex) { };
    //try {document.all('Fax2').value = arrResult[0][14]; } catch(ex) { };
} 

//��ѯҵ��Ա��Ϣ
function QueryAgent()
{
    //��ѯ��ҵ��Ա��Ϣ
    var strSQL = "";
    strSQL = "select a.agentcode,d.name,d.managecom,b.name,a.agentgroup,b.branchattr "
           + "from LCGrpCont a,labranchgroup b,laagent d " 
           + "where a.GrpContNo='" + fm.GrpContNo.value + "' " 
           + "and b.agentgroup=trim(a.agentgroup) "
           + "and d.agentcode=trim(a.agentcode) ";
   
    var arrResult=easyExecSql(strSQL,1,0);
    if(arrResult==null){
        return;
    }
    else{
        try{document.all('AgentCode').value = arrResult[0][0];}catch(ex){}
        try{document.all('AgentName').value = arrResult[0][1];}catch(ex){}
        try{document.all('AgentManageCom').value = arrResult[0][2];}catch(ex){}
        showOneCodeName('comcode','AgentManageCom','AppntManageComName');
        try{document.all('BranchAttr').value = arrResult[0][3];}catch(ex){}
        try{document.all('starAgent').value = arrResult[0][0];}catch(ex){}
    }
    
    //��ѯ��ҵ��Ա��Ϣ
    initMultiAgentGrid();
    strSQL = "select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "
           + "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d " 
           + "where a.GrpContNo='" + fm.GrpContNo.value + "' " 
           + "and b.agentgroup=c.agentgroup "
           + "and c.agentcode!=a.agentcode "
           + "and d.agentcode=c.agentcode "
           + "and d.agentcode!=a.agentcode "
           + "and c.grpcontno=a.GrpContNo ";
   turnPage.queryModal(strSQL, MultiAgentGrid);
   if (MultiAgentGrid.mulLineCount > 0)
   {
       document.all("multiagentflag").checked;
       DivMultiAgent.style.display="";	   
   }
}

//У��Ͷ������
function checkapplydate(){
    if(fm.PolApplyDate.value.length==8)
    {
        if(fm.PolApplyDate.value.indexOf('-')==-1){ 
            var Year = fm.PolApplyDate.value.substring(0,4);
            var Month = fm.PolApplyDate.value.substring(4,6);
            var Day = fm.PolApplyDate.value.substring(6,8);
            fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
            if(Year=="0000"||Month=="00"||Day=="00")
            {
               alert("�������Ͷ����������!");
               fm.PolApplyDate.value = ""; 
               return;
            }
        }
    }
} 

//��ѯ���屣��������Ϣ
function QueryRisk(){
    if((fm.GrpContNo.value == "") || (fm.GrpContNo.value == "null") || (fm.GrpContNo.value == null)) 
        return;
 
    var  strSql=" select a.riskcode, b.riskname,a.payintv,a.exppeoples,"
        +"((select count(c.riskcode) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode " 
        +" and c.poltypeflag='0') + "
        +"(select nvl(sum(i.insuredpeoples),0) from lcpol c,lcinsured i where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode " 
        +" and i.contno=c.contno and i.insuredno=c.insuredno and c.poltypeflag='1')) as peoples,"
        +"(select nvl(sum(c.prem), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode )"
        +"from lcgrppol a, lmriskapp b Where a.riskcode = b.riskcode and a.grpcontno = '"+document.all('GrpContNo').value+"'";

	turnPage.queryModal(strSql, RiskGrid);
} 

function grpFillList()
{
	delGrpVar();
	addGrpVar();
	top.fraInterface.window.location = "../app/GrpFillInsuredInput.jsp?LoadFlag=18&ContType=2&PrtNo=" + fm.PrtNo.value + "&scantype=" + fm.ScanType.value + "&ContNo=" + fm.ContNo.value + "&GrpContNo=" + fm.GrpContNo.value + "&checktype=2&display=1";
}

function delGrpVar(){
    //ɾ���������ڻ����еĸ��˺�ͬ��Ϣ
    try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
    
    //�����ͬ��Ϣ
    try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalGrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
    try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('Password2'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples2'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('BusinessType'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpNature'); } catch(ex) { };
    try { mSwitch.deleteVar('RgtMoney'); } catch(ex) { };
    try { mSwitch.deleteVar('Asset'); } catch(ex) { };
    try { mSwitch.deleteVar('NetProfitRate'); } catch(ex) { };
    try { mSwitch.deleteVar('MainBussiness'); } catch(ex) { };
    try { mSwitch.deleteVar('Corporation'); } catch(ex) { };
    try { mSwitch.deleteVar('ComAera'); } catch(ex) { };
    try { mSwitch.deleteVar('Fax'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('GetFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('Satrap'); } catch(ex) { };
    try { mSwitch.deleteVar('EMail'); } catch(ex) { };
    try { mSwitch.deleteVar('FoundDate'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOnWorkPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOffWorkPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOtherPeoples'); } catch(ex) { };            
    try { mSwitch.deleteVar('GrpGroupNo'); } catch(ex) { };
    try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
    try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AccName'); } catch(ex) { };
    try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
    try { mSwitch.deleteVar('Lang'); } catch(ex) { };
    try { mSwitch.deleteVar('Currency'); } catch(ex) { };
    try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
    try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
    try { mSwitch.deleteVar('RegetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastEdorDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastGetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastLoanDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SpecFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpSpec'); } catch(ex) { };
    try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
    try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
    try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
    try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
    try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageFeeRate'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPremium'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpAmnt'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
    try { mSwitch.deleteVar('Mult'); } catch(ex) { };
    try { mSwitch.deleteVar('Prem'); } catch(ex) { };
    try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPay'); } catch(ex) { };
    try { mSwitch.deleteVar('Dif'); } catch(ex) { };
    try { mSwitch.deleteVar('Remark'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag1'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag2'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag3'); } catch(ex) { };
    try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
    try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
    try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
    try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
    try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
    try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    //����Ͷ������Ϣ
    try { mSwitch.deleteVar('GrpNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntGrade'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('PostalAddress'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpZipCode'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntType'); } catch(ex) { };
    try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };
                                                      
}

function addGrpVar(){   
	//add activityid
	try { mSwitch.addVar('ScanFlag','',ScanFlag); } catch(ex) { }; 	
	//end      
    try { mSwitch.addVar('ContNo','',''); } catch(ex) { };   
    try { mSwitch.addVar('ProposalContNo','',''); } catch(ex) { }; 
    //�����ͬ��Ϣ
    try { mSwitch.addVar('GrpContNo','',fm.GrpContNo.value); } catch(ex) { };
    try { mSwitch.addVar('ProposalGrpContNo','',fm.ProposalGrpContNo.value); } catch(ex) { };
    try { mSwitch.addVar('PrtNo','',fm.PrtNo.value); } catch(ex) { };
    try { mSwitch.addVar('SaleChnl','',fm.SaleChnl.value); } catch(ex) { };
    try { mSwitch.addVar('ManageCom','',fm.ManageCom.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCom','',fm.AgentCom.value); } catch(ex) { };
    try { mSwitch.addVar('AgentType','',fm.AgentType.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCode','',fm.AgentCode.value); } catch(ex) { };
    try { mSwitch.addVar('AgentGroup','',fm.AgentGroup.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCode1','',fm.AgentCode1.value); } catch(ex) { };
    try { mSwitch.addVar('Password','',fm.Password.value); } catch(ex) { };
    try { mSwitch.addVar('Password2','',fm.Password2.value); } catch(ex) { };
    try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
    try { mSwitch.addVar('Addressno','',fm.AddressNo.value); } catch(ex) { };
    try { mSwitch.addVar('Peoples2','',fm.Peoples2.value); } catch(ex) { };
    try { mSwitch.addVar('GrpName','',fm.GrpName.value); } catch(ex) { };
    try { mSwitch.addVar('BusinessType','',fm.BusinessType.value); } catch(ex) { };
    try { mSwitch.addVar('GrpNature','',fm.GrpNature.value); } catch(ex) { };
    try { mSwitch.addVar('RgtMoney','',fm.RgtMoney.value); } catch(ex) { };
    try { mSwitch.addVar('Asset','',fm.Asset.value); } catch(ex) { };
    try { mSwitch.addVar('NetProfitRate','',fm.NetProfitRate.value); } catch(ex) { };
    try { mSwitch.addVar('MainBussiness','',fm.MainBussiness.value); } catch(ex) { };
    try { mSwitch.addVar('Corporation','',fm.Corporation.value); } catch(ex) { };
    try { mSwitch.addVar('ComAera','',fm.ComAera.value); } catch(ex) { };
    try { mSwitch.addVar('Fax','',fm.Fax.value); } catch(ex) { };
    try { mSwitch.addVar('Phone','',fm.Phone.value); } catch(ex) { };
    try { mSwitch.addVar('GetFlag','',fm.GetFlag.value); } catch(ex) { };
    try { mSwitch.addVar('Satrap','',fm.Satrap.value); } catch(ex) { };
    try { mSwitch.addVar('EMail','',fm.EMail.value); } catch(ex) { };
    try { mSwitch.addVar('FoundDate','',fm.FoundDate.value); } catch(ex) { };
    try { mSwitch.addVar('GrpGroupNo','',fm.GrpGroupNo.value); } catch(ex) { };
    try { mSwitch.addVar('BankCode','',fm.BankCode.value); } catch(ex) { };
    try { mSwitch.addVar('BankAccNo','',fm.BankAccNo.value); } catch(ex) { };
    try { mSwitch.addVar('AccName','',fm.AccName.value); } catch(ex) { };
    try { mSwitch.addVar('DisputedFlag','',fm.DisputedFlag.value); } catch(ex) { };
    try { mSwitch.addVar('OutPayFlag','',fm.OutPayFlag.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolMode','',fm.GetPolMode.value); } catch(ex) { };
    try { mSwitch.addVar('Lang','',fm.Lang.value); } catch(ex) { };
    try { mSwitch.addVar('Currency','',fm.Currency.value); } catch(ex) { };
    try { mSwitch.addVar('LostTimes','',fm.LostTimes.value); } catch(ex) { };
    try { mSwitch.addVar('PrintCount','',fm.PrintCount.value); } catch(ex) { };
    try { mSwitch.addVar('RegetDate','',fm.RegetDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastEdorDate','',fm.LastEdorDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastGetDate','',fm.LastGetDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastLoanDate','',fm.LastLoanDate.value); } catch(ex) { };
    try { mSwitch.addVar('SpecFlag','',fm.SpecFlag.value); } catch(ex) { };
    try { mSwitch.addVar('GrpSpec','',fm.GrpSpec.value); } catch(ex) { };
    try { mSwitch.addVar('PayMode','',fm.PayMode.value); } catch(ex) { };
    try { mSwitch.addVar('SignCom','',fm.SignCom.value); } catch(ex) { };
    try { mSwitch.addVar('SignDate','',fm.SignDate.value); } catch(ex) { };
    try { mSwitch.addVar('SignTime','',fm.SignTime.value); } catch(ex) { };
    try { mSwitch.addVar('CValiDate','',fm.CValiDate.value); } catch(ex) { };
    try { mSwitch.addVar('PayIntv','',fm.PayIntv.value); } catch(ex) { };
    try { mSwitch.addVar('ManageFeeRate','',fm.ManageFeeRate.value); } catch(ex) { };
    try { mSwitch.addVar('ExpPeoples','',fm.ExpPeoples.value); } catch(ex) { };
    try { mSwitch.addVar('ExpPremium','',fm.ExpPremium.value); } catch(ex) { };
    try { mSwitch.addVar('ExpAmnt','',fm.ExpAmnt.value); } catch(ex) { };
    try { mSwitch.addVar('Peoples','',fm.Peoples.value); } catch(ex) { };
    try { mSwitch.addVar('Mult','',fm.Mult.value); } catch(ex) { };
    try { mSwitch.addVar('Prem','',fm.Prem.value); } catch(ex) { };
    try { mSwitch.addVar('Amnt','',fm.Amnt.value); } catch(ex) { };
    try { mSwitch.addVar('SumPrem','',fm.SumPrem.value); } catch(ex) { };
    try { mSwitch.addVar('SumPay','',fm.SumPay.value); } catch(ex) { };
    try { mSwitch.addVar('Dif','',fm.Dif.value); } catch(ex) { };
    try { mSwitch.addVar('Remark','',fm.Remark.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag1','',fm.StandbyFlag1.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag2','',fm.StandbyFlag2.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag3','',fm.StandbyFlag3.value); } catch(ex) { };
    try { mSwitch.addVar('InputOperator','',fm.InputOperator.value); } catch(ex) { };
    try { mSwitch.addVar('InputDate','',fm.InputDate.value); } catch(ex) { };
    try { mSwitch.addVar('InputTime','',fm.InputTime.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveFlag','',fm.ApproveFlag.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveCode','',fm.ApproveCode.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveDate','',fm.ApproveDate.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveTime','',fm.ApproveTime.value); } catch(ex) { };
    try { mSwitch.addVar('UWOperator','',fm.UWOperator.value); } catch(ex) { };
    try { mSwitch.addVar('UWFlag','',fm.UWFlag.value); } catch(ex) { };
    try { mSwitch.addVar('UWDate','',fm.UWDate.value); } catch(ex) { };
    try { mSwitch.addVar('UWTime','',fm.UWTime.value); } catch(ex) { };
    try { mSwitch.addVar('AppFlag','',fm.AppFlag.value); } catch(ex) { };
    try { mSwitch.addVar('PolApplyDate','',fm.PolApplyDate.value); } catch(ex) { };
    try { mSwitch.addVar('CustomGetPolDate','',fm.CustomGetPolDate.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolDate','',fm.GetPolDate.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolTime','',fm.GetPolTime.value); } catch(ex) { };
    try { mSwitch.addVar('State','',fm.State.value); } catch(ex) { };
    //����Ͷ������Ϣ

    try { mSwitch.addVar('GrpNo','',fm.GrpNo.value); } catch(ex) { };
    try { mSwitch.addVar('PrtNo','',fm.PrtNo.value); } catch(ex) { };
    try { mSwitch.addVar('AddressNo','',fm.AddressNo.value); } catch(ex) { };
    try { mSwitch.addVar('AppntGrade','',fm.AppntGrade.value); } catch(ex) { };
    try { mSwitch.addVar('GrpName','',fm.Name.value); } catch(ex) { };
    try { mSwitch.addVar('PostalAddress','',fm.PostalAddress.value); } catch(ex) { };
    try { mSwitch.addVar('ZipCode','',fm.ZipCode.value); } catch(ex) { };
    try { mSwitch.addVar('Phone','',fm.Phone.value); } catch(ex) { };
    try { mSwitch.addVar('Password','',fm.Password.value); } catch(ex) { };
    try { mSwitch.addVar('State','',fm.State.value); } catch(ex) { };
    try { mSwitch.addVar('AppntType','',fm.AppntType.value); } catch(ex) { };
    try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };

} 

function grpRiskPlanInfo()
{
	if (fm.GrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܽ��С����ռƻ��ƶ�����");  
	    return false;  
	}
  showInfo = window.open("../app/ContPlan.jsp?GrpContNo="+fm.GrpContNo.value+"&LoadFlag=18");
}

function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    strsql = "select AddressNo,GrpAddress from LCGrpAddress where CustomerNo ='"+fm.GrpNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    document.all("GrpAddressNo").CodeData=tCodeData;
}


/*********************************************************************
 *  ѡ���ŵ�������Ĳ�ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */                        
function GrpQuestQuery()
{
	var cGrpContNo = fm.GrpContNo.value;  //�ŵ�Ͷ��������
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
    }
	showInfo=window.open("./GrpQuestQueryMain.jsp?GrpContNo="+cGrpContNo+"&Flag=18");
}     

/*********************************************************************
 *  ������ذ�ť,�رյ�ǰҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function goback()
{  
	top.opener.easyQueryClick();  
	top.close();
} 

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
    if(arrResult!=null)
    {
        fm.AgentCode.value = arrResult[0][0];
        fm.AgentName.value = arrResult[0][3];
        fm.AgentManageCom.value = arrResult[0][2];
        fm.AgentGroup.value = arrResult[0][1];
        fm.BranchAttr.value = arrResult[0][10];
        showCodeName();
    }
}

function afterCodeSelect( cCodeName, Field )
{
    if(cCodeName=="GetGrpAddressNo"){
         var strSQL="select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"+fm.GrpAddressNo.value+"' and b.CustomerNo='"+fm.GrpNo.value+"'";
         arrResult=easyExecSql(strSQL);
         try {document.all('GrpAddressNo').value= arrResult[0][0]; } catch(ex) { };
         try {document.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
         try {document.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
         try {document.all('LinkMan1').value= arrResult[0][3]; } catch(ex) { };
         try {document.all('Department1').value= arrResult[0][4]; } catch(ex) { };
         try {document.all('HeadShip1').value= arrResult[0][5]; } catch(ex) { };
         try {document.all('Phone1').value= arrResult[0][6]; } catch(ex) { };
         try {document.all('E_Mail1').value= arrResult[0][7]; } catch(ex) { };
         try {document.all('Fax1').value= arrResult[0][8]; } catch(ex) { };
         try {document.all('LinkMan2').value= arrResult[0][9]; } catch(ex) { };
         try {document.all('Department2').value= arrResult[0][10]; } catch(ex) { };
         try {document.all('HeadShip2').value= arrResult[0][11]; } catch(ex) { };
         try {document.all('Phone2').value= arrResult[0][12]; } catch(ex) { };
         try {document.all('E_Mail2').value= arrResult[0][13]; } catch(ex) { };
         try {document.all('Fax2').value= arrResult[0][14]; } catch(ex) { };
    }
}
    
function queryAgent()
{
    //alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
    if(document.all('ManageCom').value=="")
    {
         alert("����¼����������Ϣ��"); 
         return;
    }
    if(document.all('AgentCode').value == "")	
    {  
        //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
    }
    if(document.all('AgentCode').value != "")
    {
        var cAgentCode = fm.AgentCode.value;  //��������	
       
        //alert("cAgentCode=="+cAgentCode);
        if(cAgentCode.length!=8){
            return;
        }  
        //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
        var strSQL = "select a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
             + "and a.AgentCode = b.AgentCode and a.branchtype='2' and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'"; 
        
        var arrResult = easyExecSql(strSQL);
        //alert(arrResult);
        if (arrResult != null) {
            fm.AgentCode.value = arrResult[0][0];
            fm.BranchAttr.value = arrResult[0][92];
            fm.AgentGroup.value = arrResult[0][1];
            fm.AgentName.value = arrResult[0][5];
            fm.AgentManageCom.value = arrResult[0][2];
        }
        else
        {
            fm.AgentGroup.value="";
            alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
        }
    }	
}

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
}