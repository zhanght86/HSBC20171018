
 //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mOperate = "";
var showInfo1;
var mDebug="0";
var turnPage = new turnPageClass();
var arrResult;
var mySql = new SqlClass();
//�����Ӷ���ִ�еĴ���
var addAction = 0;
//�ݽ����ܽ��
var sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
var tempFee = 0.0;
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
var tempClassFee = 0.0;
//����ȷ���󣬸ñ�����Ϊ�棬�������һ��ʱ�������ֵ�Ƿ�Ϊ�棬Ϊ�������Ȼ���ٽ��ñ����ü�
var confirmFlag=false;
//

var isBroker = false;

var arrCardRisk;
//window.onfocus=focuswrap;
var mSwitch = parent.VD.gVSwitch;
//������flag
var mWFlag = 0;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function focuswrap()
{
	myonfocus(showInfo1);
}

function addInsuredList()
{
	if(fm.Name.value==null||fm.Name.value==""){
		alert("��������Ϊ�գ�");
		return false;
	}
	
	if(fm.Sex.value==null||fm.Sex.value==""){
		alert("�Ա���Ϊ�գ�");
		return false;
	}
	
	if(fm.IDType.value==null||fm.IDType.value==""){
		alert("֤�����Ͳ���Ϊ�գ�");
		return false;
	}
	
	if(fm.IDNo.value==null||fm.IDNo.value==""){
		alert("֤�����벻��Ϊ�գ�");
		return false;
	}
	
	if(fm.Birthday.value==null||fm.Birthday.value==""){
		alert("�������ڲ���Ϊ�գ�");
		return false;
	}
	

	
	//У��������������	
	if(fm.RelationToMainInsured2.value==null||fm.RelationToMainInsured2.value==""){
		alert("�����������˹�ϵ����Ϊ�գ�");
		return false;
	}
	
	if(fm.RelationToMainInsured2.value=="00"){
		alert("�����������˹�ϵ����ѡ��Ϊ���ˣ�");
		return false;
	}
	
	//ְҵ����ְҵ���벻��Ϊ��
	
	if(fm.OccupationCode.value==null||fm.OccupationCode.value==""){
		alert("ְҵ���벻��Ϊ�գ�");
		return false;
	}
	
	if(fm.OccupationType.value==null||fm.OccupationType.value==""){
		alert("ְҵ�����Ϊ�գ�");
		return false;
	}

	
	var tSelNo = MainInsuredGrid.getSelNo();
	if(tSelNo==0)
	{
		alert("��ѡ��һ�����������˼�¼");
		return false;
	}else{
		fm.MainInsuredNo.value = MainInsuredGrid.getRowColData(tSelNo - 1, 1);
	}
	  if(isBroker)
    { //add by TianWK ȷ���Ǵ�LXContInfo���ѯ������
    	if(fm.CardNo.value==null||fm.CardNo.value=="")
    	{
    		//alert("���Բ�ѯ��ʽȷ�����ͻ���Ϣ");
    		//return false;
    	}
    }
    
    if (verifyInput2() == false)
        return;  
     
    if(document.all('IDType').value=="0")
    {
        var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
        if (strChkIdNo != "")
        {
            alert(strChkIdNo);
	        return false;
        }
    }    
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid) == false)
        return false;
    
    //ImpartGrid.delBlankLine();
    
    document.all('ContType').value=ContType;
    document.all('BQFlag').value = BQFlag;
    document.all('EdorType').value = EdorType;
    document.all('EdorTypeCal').value = EdorTypeCal;
    document.all('EdorValiDate').value = EdorValiDate;
    
    document.all('fmAction').value="INSERT||CONTINSURED";
    
    fm.PrtNo.value=prtNo;
    fm.GrpContNo.value=GrpContNo;
    fm.OldContNo.value=vContNo;
 
    var showStr="������ӱ����ˣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  var iWidth=550;      //�������ڵĿ��; 
	  var iHeight=250;     //�������ڵĸ߶�; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();


    
    fm.action="./InsuredRelaAddSave.jsp";
    fm.submit();	
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{ 
  try { showInfo.close(); } catch(e) {}
  if (FlagStr=="Succ" && mWFlag == 0)
  {
  	if(fm.CardNo.value!=""&&fm.ContNo.value!="")
  	{
  		fm.action="../broker/CollateContSave.jsp";
  		/*�޸���Ч���ڵ���Ϣ*/
  		fm.submit();
  		fm.CardNo.value="";
  	}
   else
  	{
  	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//     showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 	 
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  	var iWidth=550;      //�������ڵĿ��; 
  	var iHeight=350;     //�������ڵĸ߶�; 
  	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  	showInfo.focus();


  	 
  	 if(confirm("�Ƿ����¼�������ͻ���"))
  	  {
  	  	if(!checkISFILL())
  	  	{//�жϼ�����Ϣ
  	  		return false;
  	  	}
  		  emptyInsured();
  		  if (fm.ContType.value==2)
  		  {  
  			 fm.ContNo.value="";
  		   fm.ProposalContNo.value="";
  		  }
  	  }
  	else
  		{
  			parent.close();	
  		}
    }
  }
  if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();


	}
}

/*********************************************************************
 *  ��ѯְҵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */      
function throughwork()
{
  // var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
   
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql1");
	mySql.addSubPara(fm.OccupationCode.value ); 
   var arrResult = easyExecSql(mySql.getString());
   if (arrResult != null)
   {
       fm.OccupationType.value = arrResult[0][0];
   }
   else
   {
       fm.OccupationType.value = '1';
   }
}

/*********************************************************************
 *  ������һҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnparent()
{
	top.fraInterface.window.location="./InsuredRelaAdd.jsp?ContNo=" + vContNo + "&PrtNo=" + prtNo + "&LoadFlag=18&GrpContNo=" + GrpContNo;
}

/*********************************************************************
 *  ����ѡ��󴥷�ʱ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
    try
    {   //����Ǽ�ͥ���� 
        if(cCodeName == "FamilyType")
        {
            choosetype();
        }
        if(cCodeName == "OccupationCode")
        {
            //alert(fm.OccupationCode.value);
           // var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
            	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql2");
			mySql.addSubPara(fm.OccupationCode.value ); 
            var arrResult = easyExecSql(mySql.getString());
            if (arrResult != null)
            {
                fm.OccupationType.value = arrResult[0][0];
            }
            else
            {
                fm.OccupationType.value = '1';
            }
        }
    
    
        if(cCodeName=="GetAddressNo"){
            //alert("ok");
           // var strSQL="select postaladdress,zipcode from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
            //alert(strSQL);
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql3");
			mySql.addSubPara(fm.InsuredAddressNo.value ); 
			mySql.addSubPara(fm.InsuredNo.value ); 
            arrResult=easyExecSql(mySql.getString());
            try{document.all('PostalAddress').value= arrResult[0][0];}catch(ex){};
            try{document.all('ZipCode').value= arrResult[0][1];}catch(ex){};
            return;
        }

        //�����������
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value=="1")
            {   
            	  DivLCBasicInfo.style.display = "none"; 
               	  document.all('IDType').value="9";
            	  document.all('InsuredPeoples').value="10";
            	  document.all('InsuredAppAge').value="30";
            	  document.all('InsuredPeoples').readOnly=false;
                  document.all('InsuredAppAge').readOnly=false;
                  document.all('OccupationType').readOnly=false; 
              	  document.all('Name').value="������";
              	  document.all('OccupationCode').verify = "";
              	  document.all('Sex').value="0";
              	  document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  verify='��������ְҵ���|code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                  document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"�������˳�������|Date\" verifyorder='2' >";
            }
            else if(Field.value=="2")
            {
                DivLCBasicInfo.style.display = "none";
                document.all('IDType').value="9";
                document.all('InsuredPeoples').value="0";
                document.all('InsuredAppAge').value="30";
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=false; 
                document.all('OccupationCode').verify = "";
                document.all('OccupationType').readOnly=false;
                document.all('Name').value="�����ʻ�";
                document.all('Sex').value="0";
                document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  verify='��������ְҵ���|code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"�������˳�������|Date\" verifyorder='2' >";
            }
            else
            {  
                DivLCBasicInfo.style.display = "";   
                document.all('InsuredPeoples').value="";
                document.all('InsuredAppAge').value=""; 
                document.all('Name').value="";
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredPeoples').value="1";
                document.all('InsuredAppAge').readOnly=true;  
                document.all('OccupationCode').verify = "��������ְҵ����|NOTNUlL&CODE:OccupationCode";               
                document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  elementtype=nacessary verify='��������ְҵ���|NOTNULL&code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                document.all('OccupationType').insertAdjacentHTML("afterEnd","<font color=red>&nbsp;*</font>");
                document.all('OccupationType').readOnly=true;
                document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short elementtype=nacessary onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"�������˳�������|NOTNULL&DATE\" verifyorder='2' >";
                document.all('Birthday').insertAdjacentHTML("afterEnd","<font color=red>&nbsp;*</font>");
            }
            return;
        }
        if( cCodeName == "ImpartCode")
        {
        
        }
        if( cCodeName == "SequenceNo")
        {
            if(Field.value=="1"&&fm.SamePersonFlag.checked==false)
            {
                emptyInsured();
                param="121";
                fm.pagename.value="121";
                fm.InsuredSequencename.value="��һ������������";
                fm.RelationToMainInsured.value="00";
            }
            if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
            {
                if(InsuredGrid.mulLineCount==0)
                {
                    alert("������ӵ�һ������");
                    fm.SequenceNo.value="1";
                    return false;
                }
                emptyInsured();
                noneedhome();
                param="122";
                fm.pagename.value="122";
                fm.InsuredSequencename.value="�ڶ�������������";
            }
            if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
            {
                if(InsuredGrid.mulLineCount==0)
                {
                    alert("������ӵ�һ������");
                    Field.value="1";
                    return false;
                }
                if(InsuredGrid.mulLineCount==1)
                {
                    alert("������ӵڶ�������");
                    Field.value="1";
                    return false;
                }
                emptyInsured();
                noneedhome();
                param="123";
                fm.pagename.value="123";
                fm.InsuredSequencename.value="����������������";
            }
            if (scantype== "scan")
            {
                setFocus();
            }
        }
        if( cCodeName == "CheckPostalAddress")
        {
            if(fm.CheckPostalAddress.value=="1")
            {
                document.all('PostalAddress').value=document.all('GrpAddress').value;
                document.all('ZipCode').value=document.all('GrpZipCode').value;
                document.all('Phone').value= document.all('GrpPhone').value;
            }
            else if(fm.CheckPostalAddress.value=="2")
            {
                document.all('PostalAddress').value=document.all('HomeAddress').value;
                document.all('ZipCode').value=document.all('HomeZipCode').value;
                document.all('Phone').value= document.all('HomePhone').value;
            }
            else if(fm.CheckPostalAddress.value=="3")
            {
                document.all('PostalAddress').value="";
                document.all('ZipCode').value="";
                document.all('Phone').value= "";
            }
        }
        
    }
    catch(ex) {}

}

/*********************************************************************
 *  ��ʾ��ͥ���±������˵���Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredInfo()
{
	var tSelNo = ContGrid.getSelNo();
	var tPolNo = ContGrid.getRowColData(tSelNo - 1, 5);
	fm.ContNo.value = ContGrid.getRowColData(tSelNo - 1, 1);
	fm.RiskSortValue.value = ContGrid.getRowColData(tSelNo - 1, 6);
	fm.PolNo.value = tPolNo;
	//alert(fm.ContNo.value);
	//alert(fm.PolNo.value);
    var tGrpContNo=document.all("BPNo").value;
    if(tGrpContNo!=null&&tGrpContNo!="")
    {
//        var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from lcinsured a where "
//        		+ "a.grpcontno='"+tGrpContNo+"' and insuredno in" 
//        		+ " (select maincustomerno from lcinsuredrelated b where b.polno='"+tPolNo+"' )";
        
        /*var strSQL ="select InsuredNo,InsuredName,InsuredSex,InsuredBirthday,contno from lcpol a where "
    		+ "a.grpcontno='"+tGrpContNo+"' and polno='"+tPolNo+"'" ;*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql4");
			mySql.addSubPara(tGrpContNo); 
			mySql.addSubPara(tPolNo); 
        
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
        //prompt("",strSQL);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = MainInsuredGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }else{
    	alert("������屣����ʧ�ܣ�");
    }
    
}

/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;
    var ContNo = fm.ContNo.value;
    //��������ϸ��Ϣ
   // var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql5");
	mySql.addSubPara(InsuredNo); 
    arrResult=easyExecSql(mySql.getString());
    if(arrResult!=null)
    {
        displayAppnt();
    }
   // strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
    
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql6");
	mySql.addSubPara(ContNo); 
	mySql.addSubPara(InsuredNo); 
    arrResult=easyExecSql(mySql.getString());
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
    fm.InsuredAddressNo.value=tAddressNo;
    getdetailaddress();
    getInsuredPolInfo();
    getImpartInfo();
    //getImpartDetailInfo();
    InsuredChkNew();
    
    
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayCustomer()
{
	try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayAddress()
{
	try{document.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('GrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}

/*********************************************************************
 *  ��ʾ��������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayInsured()
{
    try{document.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{document.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{document.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{document.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{document.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{document.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    //try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{document.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{document.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{document.all('Name').value=arrResult[0][12];}catch(ex){};
    try{document.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{document.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{document.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{document.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{document.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{document.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{document.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{document.all('Health').value=arrResult[0][22];}catch(ex){};
    try{document.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{document.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{document.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{document.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{document.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{document.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{document.all('AccName').value=arrResult[0][29];}catch(ex){};
    //try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};
    //try{document.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{document.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{document.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{document.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{document.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{document.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{document.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{document.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{document.all('MakeDate').value=arrResult[0][41];}catch(ex){};
    try{document.all('MakeTime').value=arrResult[0][42];}catch(ex){};
    try{document.all('ModifyDate').value=arrResult[0][43];}catch(ex){};
    try{document.all('ModifyTime').value=arrResult[0][44];}catch(ex){};
    try{document.all('WorkNo').value=arrResult[0][55];}catch(ex){};
    //alert(arrResult[0][55]);
    getAge();   
    
}
function displayissameperson()
{
    try{document.all('InsuredNo').value= mSwitch.getVar( "AppntNo" ); }catch(ex){};
    try{document.all('Name').value= mSwitch.getVar( "AppntName" ); }catch(ex){};
    try{document.all('Sex').value= mSwitch.getVar( "AppntSex" ); }catch(ex){};
    try{document.all('Birthday').value= mSwitch.getVar( "AppntBirthday" ); }catch(ex){};
    try{document.all('IDType').value= mSwitch.getVar( "AppntIDType" ); }catch(ex){};
    try{document.all('IDNo').value= mSwitch.getVar( "AppntIDNo" ); }catch(ex){};
    try{document.all('Password').value= mSwitch.getVar( "AppntPassword" ); }catch(ex){};
    try{document.all('NativePlace').value= mSwitch.getVar( "AppntNativePlace" ); }catch(ex){};
    try{document.all('Nationality').value= mSwitch.getVar( "AppntNationality" ); }catch(ex){};
    try{document.all('InsuredAddressNo').value= mSwitch.getVar( "AppntAddressNo" ); }catch(ex){};
    try{document.all('RgtAddress').value= mSwitch.getVar( "AppntRgtAddress" ); }catch(ex){};
    try{document.all('Marriage').value= mSwitch.getVar( "AppntMarriage" );}catch(ex){};
    try{document.all('MarriageDate').value= mSwitch.getVar( "AppntMarriageDate" );}catch(ex){};
    try{document.all('Health').value= mSwitch.getVar( "AppntHealth" );}catch(ex){};
    try{document.all('Stature').value= mSwitch.getVar( "AppntStature" );}catch(ex){};
    try{document.all('Avoirdupois').value= mSwitch.getVar( "AppntAvoirdupois" );}catch(ex){};
    try{document.all('Degree').value= mSwitch.getVar( "AppntDegree" );}catch(ex){};
    try{document.all('CreditGrade').value= mSwitch.getVar( "AppntDegreeCreditGrade" );}catch(ex){};
    try{document.all('OthIDType').value= mSwitch.getVar( "AppntOthIDType" );}catch(ex){};
    try{document.all('OthIDNo').value= mSwitch.getVar( "AppntOthIDNo" );}catch(ex){};
    try{document.all('ICNo').value= mSwitch.getVar( "AppntICNo" );}catch(ex){};
    try{document.all('GrpNo').value= mSwitch.getVar( "AppntGrpNo" );}catch(ex){};
    //try{document.all( 'JoinCompanyDate' ).value = mSwitch.getVar( "JoinCompanyDate" ); if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
    try{document.all('StartWorkDate').value= mSwitch.getVar( "AppntStartWorkDate" );}catch(ex){};
    try{document.all('Position').value= mSwitch.getVar( "AppntPosition" );}catch(ex){};
    try{document.all( 'Position' ).value = mSwitch.getVar( "Position" ); if(document.all( 'Position' ).value=="false"){document.all( 'Position' ).value="";} } catch(ex) { };
    //try{document.all('Salary').value= mSwitch.getVar( "AppntSalary" );}catch(ex){};
    try{document.all( 'Salary' ).value = mSwitch.getVar( "Salary" ); if(document.all( 'Salary' ).value=="false"){document.all( 'Salary' ).value="";} } catch(ex) { };
    try{document.all('OccupationType').value= mSwitch.getVar( "AppntOccupationType" );}catch(ex){};
    try{document.all('OccupationCode').value= mSwitch.getVar( "AppntOccupationCode" );}catch(ex){};
    try{document.all('WorkType').value= mSwitch.getVar( "AppntWorkType" );}catch(ex){};
    try{document.all('PluralityType').value= mSwitch.getVar( "AppntPluralityType" );}catch(ex){};
    try{document.all('DeathDate').value= mSwitch.getVar( "AppntDeathDate" );}catch(ex){};
    try{document.all('SmokeFlag').value= mSwitch.getVar( "AppntSmokeFlag" );}catch(ex){};
    try{document.all('BlacklistFlag').value= mSwitch.getVar( "AppntBlacklistFlag" );}catch(ex){};
    try{document.all('Proterty').value= mSwitch.getVar( "AppntProterty" );}catch(ex){};
    try{document.all('Remark').value= mSwitch.getVar( "AppntRemark" );}catch(ex){};
    try{document.all('State').value= mSwitch.getVar( "AppntState" );}catch(ex){};
    try{document.all('Operator').value= mSwitch.getVar( "AppntOperator" );}catch(ex){};
    try{document.all('MakeDate').value= mSwitch.getVar( "AppntMakeDate" );}catch(ex){};
    try{document.all('MakeTime').value= mSwitch.getVar( "AppntMakeTime" );}catch(ex){};
    try{document.all('ModifyDate').value= mSwitch.getVar( "AppntModifyDate" );}catch(ex){};
    try{document.all('ModifyTime').value= mSwitch.getVar( "AppntModifyTime" );}catch(ex){};
    try{document.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{document.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{document.all('ZipCode').value= mSwitch.getVar( "AppntZipCode" );}catch(ex){};
    try{document.all('Phone').value= mSwitch.getVar( "AppntPhone" );}catch(ex){};
    try{document.all('Fax').value= mSwitch.getVar( "AppntFax" );}catch(ex){};
    try{document.all('Mobile').value= mSwitch.getVar( "AppntMobile" );}catch(ex){};
    try{document.all('EMail').value= mSwitch.getVar( "AppntEMail" );}catch(ex){};
    try{document.all('GrpName').value= mSwitch.getVar( "AppntGrpName" );}catch(ex){};
    try{document.all('GrpPhone').value= mSwitch.getVar( "AppntGrpPhone" );}catch(ex){};
    try{document.all('GrpAddress').value= mSwitch.getVar( "CompanyAddress" );}catch(ex){};
    try{document.all('GrpZipCode').value= mSwitch.getVar( "AppntGrpZipCode" );}catch(ex){};
    try{document.all('GrpFax').value= mSwitch.getVar( "AppntGrpFax" );}catch(ex){};
    try{document.all('HomeAddress').value= mSwitch.getVar( "AppntHomeAddress" );}catch(ex){};
    try{document.all('HomePhone').value= mSwitch.getVar( "AppntHomePhone" );}catch(ex){};
    try{document.all('HomeZipCode').value= mSwitch.getVar( "AppntHomeZipCode" );}catch(ex){};
    try{document.all('HomeFax').value= mSwitch.getVar( "AppntHomeFax" );}catch(ex){};
    getAge();
}
/*********************************************************************
 *  ��ѯ��֪��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid();
    var InsuredNo=document.all("InsuredNo").value;
    //alert("InsuredNo="+InsuredNo);
    var ContNo=document.all("ContNo").value; 
    //alert("ContNo="+ContNo); 
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
      //  var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where GrpContNo='"+ GrpContNo+"' and  CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' and PatchNo='0'";
        
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql7");
	mySql.addSubPara(GrpContNo); 
	mySql.addSubPara(InsuredNo); 
	mySql.addSubPara(ContNo); 
	
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        { 
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = ImpartGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  ��ѯ��֪��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
      //  var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent,StartDate,EndDate,Prover,CurrCondition,IsProved from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
            
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql8"); 
	mySql.addSubPara(InsuredNo); 
	mySql.addSubPara(ContNo); 
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = ImpartDetailGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  ��ñ�����������Ϣ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //������Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
       // var strSQL ="select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
        //alert(strSQL);
                
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql9"); 
	mySql.addSubPara(InsuredNo); 
	mySql.addSubPara(ContNo); 
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    //alert(fm.InsuredNo.value);
   /* strSql = "select * from ldperson where Name='"+fm.Name.value+"' and Sex='"+fm.Sex.value+"' and Birthday=to_date('"+fm.Birthday.value+"','YYYY-MM-DD') and CustomerNo<>'"+fm.InsuredNo.value+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDNo = '"+fm.IDNo.value+"' and CustomerNo<>'"+fm.InsuredNo.value+"'"; */
                
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql10"); 
	mySql.addSubPara(fm.Name.value); 
	mySql.addSubPara(fm.Sex.value); 
	mySql.addSubPara(fm.Birthday.value); 
	mySql.addSubPara(fm.InsuredNo.value); 
	mySql.addSubPara(fm.IDType.value); 
	mySql.addSubPara(fm.IDNo.value); 
	mySql.addSubPara(fm.InsuredNo.value); 
    var PayIntv = easyExecSql(mySql.getString());
    //alert(strSql);
    //fm.Name.value=strSql;
    //if(PayIntv==null)
    //{
    //	fm.InsuredChkButton2.disabled=true;
    //}
    //edit by yaory originwriter:yaory
}
/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolDetail(parm1,parm2)
{
    var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}
/*********************************************************************
 *  ���ݼ�ͥ�����ͣ����ؽ���ؼ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  У�鱻�����������������˹�ϵ
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function checkself()
{
	if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value=="")
	{
	    fm.RelationToMainInsured.value="00";
	    return true;
    }
	else if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value!="00")
	{
	    alert("���˵���'�����������˹�ϵ'ֻ����'����'");
	    fm.RelationToMainInsured.value="00";
	    return false;
    }
	else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value==""&&InsuredGrid.mulLineCount==0)
	{
	    fm.RelationToMainInsured.value="00";
	    return true;
    }
    else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value!="00"&&InsuredGrid.mulLineCount==0)
    {
	    alert("��ͥ���е�һλ�������˵�'�����������˹�ϵ'ֻ����'����'");
	    fm.RelationToMainInsured.value="00";
	    return false;
    }
    else
        return true;
}
/*********************************************************************
 *  У�鱣����
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        //if (document.all('ContNo').value != "")
        //{
        //    alert("�ŵ��ĸ��������ж౻������");
        //    return false;
        //}
        //else
            //return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
          //  var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
                        
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql11"); 
	mySql.addSubPara(document.all('ContNo').value);  
            turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("���������ж౻������");
                return false;
            }
            else
                return true;
        }
//        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
//        {
//            
//            alert("��ͥ��ֻ����һ������������");
//            return false;
//        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
           // var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
		                              
			mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql12"); 
			mySql.addSubPara(document.all('ContNo').value); 
            turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("Ͷ�����Ѿ��Ǹú�ͬ���µı�������");
                return false;
            }
		    else
		        return true;
        }
        else
            return true;
    }
	//select count(*) from ldinsured

}
/*********************************************************************
 *  Ͷ�����뱻������ͬѡ����¼�
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //��Ӧδѡͬһ�ˣ��ִ򹳵����
    if ( fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";
      displayissameperson();
    }
    //��Ӧ��ѡͬһ�˵����
    else if (fm.SamePersonFlag.checked == false)
    {
    document.all('DivLCInsured').style.display = "";
    divLCInsuredPerson.style.display = "";
    //divSalary.style.display = "";
    try{document.all('Name').value=""; }catch(ex){};
    try{document.all('Sex').value= ""; }catch(ex){};
    try{document.all('Birthday').value= ""; }catch(ex){};
    try{document.all('IDType').value= "0"; }catch(ex){};
    try{document.all('IDNo').value= ""; }catch(ex){};
    try{document.all('Password').value= ""; }catch(ex){};
    try{document.all('NativePlace').value= ""; }catch(ex){};
    try{document.all('Nationality').value=""; }catch(ex){};
    try{document.all('RgtAddress').value= ""; }catch(ex){};
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
    //try{document.all('JoinCompanyDate').value= "";}catch(ex){}
    try{document.all('StartWorkDate').value= "";}catch(ex){};
    try{document.all('Position').value= "";}catch(ex){};
    //try{document.all('Salary').value= "";}catch(ex){};
    try{document.all('OccupationType').value= "";}catch(ex){};
    try{document.all('OccupationCode').value= "";}catch(ex){};
    try{document.all('WorkType').value= "";}catch(ex){};
    try{document.all('PluralityType').value= "";}catch(ex){};
    try{document.all('DeathDate').value= "";}catch(ex){};
    try{document.all('SmokeFlag').value= "";}catch(ex){};
    try{document.all('BlacklistFlag').value= "";}catch(ex){};
    try{document.all('Proterty').value= "";}catch(ex){};
    try{document.all('Remark').value= "";}catch(ex){};
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
    try{document.all('GrpPhone').value= "";}catch(ex){};
    try{document.all('GrpAddress').value="";}catch(ex){};
    try{document.all('GrpZipCode').value= "";}catch(ex){};

    }
}
/*********************************************************************
 *  Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryInsuredNo()
{
    if (document.all("InsuredNo").value == "")
    {
    	if(!isBroker)
    	{
       showAppnt1();
      }
    else
    	{
    		showBrokerInsuredPeople();
    	}
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
       // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
                                 
			mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql33"); 
			mySql.addSubPara(document.all('InsuredNo').value); 
			arrResult = easyExecSql(mySql.getString(), 1, 0);
        if (arrResult == null)
        {
          alert("δ�鵽Ͷ������Ϣ");
          displayAppnt(new Array());
          emptyUndefined();
        }
        else
        {
          displayAppnt(arrResult[0]);
        }
    }
}
/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		//showInfo = window.open( "../sys/LDPersonQueryNew.html" );
		showInfo = window.open( "../sys/LDPersonQueryNew.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}  
/*********************************************************************
 *  ��ʾͶ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
    try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    //try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{document.all('Position').value= arrResult[0][23];}catch(ex){};
    //try{document.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){};
    try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{document.all('State').value= arrResult[0][34];}catch(ex){};
    try{document.all('Operator').value= arrResult[0][35];}catch(ex){};
    try{document.all('MakeDate').value= arrResult[0][36];}catch(ex){};
    try{document.all('MakeTime').value= arrResult[0][37];}catch(ex){};
    try{document.all('ModifyDate').value= arrResult[0][38];}catch(ex){};
    try{document.all('ModifyTime').value= arrResult[0][39];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][40];}catch(ex){};
    getAge();
    
    //��ַ��ʾ���ֵı䶯
    try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('GrpPhone').value=  "";}catch(ex){};
    try{document.all('GrpAddress').value=  "";}catch(ex){};
    try{document.all('GrpZipCode').value=  "";}catch(ex){};
}
/*********************************************************************
 *  ��ѯ���غ󴥷�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
  //alert("here:" + arrQueryResult + "\n" + mOperate);
    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// ��ѯͶ����
            document.all( 'ContNo' ).value = arrQueryResult[0][0];

            //arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);
	                     
			mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql14"); 
			mySql.addSubPara(arrQueryResult[0][0] ); 
			arrResult = easyExecSql(mySql.getString(),1,0);
            if (arrResult == null)
            {
                alert("δ�鵽Ͷ������Ϣ");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 2 )
        {		// Ͷ������Ϣ
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	             
			mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql15"); 
			mySql.addSubPara(arrQueryResult[0][0] ); 
			arrResult = easyExecSql(mySql.getString(),1,0);
        	if (arrResult == null)
        	{
        	    alert("δ�鵽Ͷ������Ϣ");
        	}
        	else
        	{
        	    displayAppnt(arrResult[0]);
        	}
        }
    }

	mOperate = 0;		// �ָ���̬
}
/*********************************************************************
 *  ��ѯְҵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailwork()
{
    //var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
                 
			mySql = new SqlClass();
			mySql.setResourceName("claimgrp.InsuredRelaInputSql");
			mySql.setSqlId("InsuredRelaSql16"); 
			mySql.addSubPara(fm.OccupationCode.value); 
			
    var arrResult = easyExecSql(mySql.getString());
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
    }
    else
    {
        fm.OccupationType.value = '';
    }
}

/*********************************************************************
 *  ��ѯ����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailaddress()
{
//alert("1");
   // var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
         
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql17"); 
	mySql.addSubPara(fm.InsuredAddressNo.value);
	mySql.addSubPara(fm.InsuredNo.value);
    arrResult=easyExecSql(mySql.getString());
    try{document.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
//alert("2");
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
//alert("3");
    try{document.all('ZipCode').value= arrResult[0][2];}catch(ex){};
//alert("4");
    try{document.all('HomePhone').value= arrResult[0][3];}catch(ex){};
//alert("5");
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){};
//alert("6");
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
//alert("7");
    try{document.all('GrpPhone').value= arrResult[0][6];}catch(ex){};
//alert("8")
   // try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    //try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
}
/*********************************************************************
 *  ��ѯ���ռƻ�
 *  ����  ��  �����ͬͶ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
   // strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
   	     
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql18"); 
	mySql.addSubPara(tProposalGrpContNo); 
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    	 divContPlan.style.display="";
    }
    else
    {
      //alert("���ռƻ�û�鵽");
        divContPlan.style.display="";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  ��ѯ�������
 *  ����  ��  �����ͬͶ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	//strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+GrpContNo+"' and a.ExecuteCom=b.ComCode";
	     
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql19"); 
	mySql.addSubPara(GrpContNo);
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
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

	return tCodeData;
}

function emptyInsured()
{

	try{document.all('InsuredNo').value= ""; }catch(ex){};
	//try{document.all('ExecuteCom').value= ""; }catch(ex){};
	try{document.all('FamilyID').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{document.all('RelationToAppnt').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('Name').value= ""; }catch(ex){};
	try{document.all('Sex').value= ""; }catch(ex){};
	try{document.all('Birthday').value= ""; }catch(ex){};
	try{document.all('IDType').value= "9"; }catch(ex){};
	try{document.all('IDNo').value= ""; }catch(ex){};
	try{document.all('NativePlace').value= ""; }catch(ex){};
	try{document.all('Nationality').value= ""; }catch(ex){};
	try{document.all('RgtAddress').value= ""; }catch(ex){};
	try{document.all('Marriage').value= ""; }catch(ex){};
	try{document.all('MarriageDate').value= ""; }catch(ex){};
	try{document.all('Health').value= ""; }catch(ex){};
	try{document.all('Stature').value= ""; }catch(ex){};
	try{document.all('Avoirdupois').value= ""; }catch(ex){};
	try{document.all('Degree').value= ""; }catch(ex){};
	try{document.all('CreditGrade').value= ""; }catch(ex){};
	try{document.all('BankCode').value= ""; }catch(ex){};
	try{document.all('BankAccNo').value= ""; }catch(ex){};
	try{document.all('AccName').value= ""; }catch(ex){};
	//try{document.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{document.all('StartWorkDate').value= ""; }catch(ex){};
	try{document.all('Position').value= ""; }catch(ex){};
	//try{document.all('Salary').value= ""; }catch(ex){};
	try{document.all('OccupationType').value= ""; }catch(ex){};
	try{document.all('OccupationCode').value= ""; }catch(ex){};
	try{document.all('WorkType').value= ""; }catch(ex){};
	try{document.all('PluralityType').value= ""; }catch(ex){};
	try{document.all('SmokeFlag').value= ""; }catch(ex){};
	try{document.all('ContPlanCode').value= ""; }catch(ex){};
    try{document.all('GrpName').value= ""; }catch(ex){};
    try{document.all('HomeAddress').value= ""; }catch(ex){};
    try{document.all('HomeZipCode').value= ""; }catch(ex){};
    try{document.all('HomePhone').value= ""; }catch(ex){};
    try{document.all('HomeFax').value= ""; }catch(ex){};
    try{document.all('GrpFax').value= ""; }catch(ex){};
    try{document.all('Fax').value= ""; }catch(ex){};
	emptyAddress();
	ImpartGrid.clearData();
	ImpartGrid.addOne();
}

/*********************************************************************
 *  ��տͻ���ַ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function emptyAddress()
{
	try{document.all('PostalAddress').value= "";  }catch(ex){};
	try{document.all('ZipCode').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('Mobile').value= "";  }catch(ex){};
	try{document.all('EMail').value= "";  }catch(ex){};
	try{document.all('GrpPhone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
}
/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('IDType').value=="0"&&document.all('IDNo').value!=""&&(document.all('IDNo').value.length==15||document.all('IDNo').value.length==18))
	{
		document.all('Birthday').value=getBirthdatByIdNo(iIdNo);
		document.all('Sex').value=getSexByIDNo(iIdNo);
		if(document.all('Sex').value=="0")
		 {
			 document.all('SexName').value="��";
		 }
		else
			 document.all('SexName').value="Ů";
	}
}

function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    
    //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    //alert("strsql :" + strsql);
         
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql20"); 
	mySql.addSubPara(fm.InsuredNo.value);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
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
    document.all("InsuredAddressNo").CodeData=tCodeData;
}                  

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;
  //window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer);
  window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
function checkidtype()
{
	if(fm.IDType.value=="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.IDNo.value="";
  }
}
function getallinfo()
{
 	if(fm.Name.value!=""&&fm.IDType.value!=""&&fm.IDNo.value!="")
 	{
	   /* strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
                +"  and Name='"+fm.Name.value
                +"' and IDType='"+fm.IDType.value
                +"' and IDNo='"+fm.IDNo.value
		+"' order by a.CustomerNo";*/
                  
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql21"); 
	mySql.addSubPara(fm.Name.value);
	mySql.addSubPara(fm.IDType.value);
	mySql.addSubPara(fm.IDNo.value);
             turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
             if (turnPage.strQueryResult != "")
             {
 		  mOperate = 2;
 		  //window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
}

function InsuredChk()
{
	//var tSel =InsuredGrid.getSelNo();
	//if( tSel == 0 || tSel == null )
	//{
	//	alert("����ѡ�񱻱����ˣ�");
	//	return false;
	//}
	//var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=fm.InsuredNo.value;
	var tInsuredName=fm.Name.value;
	var tInsuredSex=fm.Sex.value;
	var tBirthday=fm.Birthday.value;
	//alert(tInsuredNo);
	//alert(tInsuredName);
	//alert(tInsuredSex);
	//alert(tBirthday);
	
      /*  var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;*/
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql22"); 
	mySql.addSubPara(tInsuredName);
	mySql.addSubPara(tInsuredSex);
	mySql.addSubPara(tBirthday);
	mySql.addSubPara(tInsuredNo);
	mySql.addSubPara(fm.IDType.value);
	mySql.addSubPara(fm.IDNo.value);
	mySql.addSubPara(tInsuredNo);
        arrResult = easyExecSql(mySql.getString(),1,0);

        if(arrResult==null)
        {
	   alert("��û����ñ����˱������ƵĿͻ�,����У��");
	   return false;
        }
 //alert(GrpContNo);
	//window.open("../app/InsuredChkMain.jsp?ProposalNo1="+GrpContNo+"&InsuredNo="+tInsuredNo+"&Flag=I","window2");
	window.open("../app/InsuredChkMain.jsp?ProposalNo1="+GrpContNo+"&InsuredNo="+tInsuredNo+"&Flag=I","window2","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           document.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           document.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           document.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           document.all('BankAccNo').value="";
           document.all('BankCode').value="";
           document.all('AccName').value="";
	}

}
function AutoMoveForNext()
{
	if(fm.AutoMovePerson.value=="���Ƶڶ���������")
	{
		     //emptyFormElements();
		     param="122";
		     fm.pagename.value="122";
                     fm.AutoMovePerson.value="���Ƶ�����������";
                     return false;
	}
	if(fm.AutoMovePerson.value=="���Ƶ�����������")
	{
		     //emptyFormElements();
		     param="123";
		     fm.pagename.value="123";
                     fm.AutoMovePerson.value="���Ƶ�һ��������";
                     return false;
	}
		if(fm.AutoMovePerson.value=="���Ƶ�һ��������")
	{
		     //emptyFormElements();
		     param="121";
		     fm.pagename.value="121";
                     fm.AutoMovePerson.value="���Ƶڶ���������";
                     return false;
	}
}
function noneedhome()
{
    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);
        		break;
        	}
        }
     //  var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql23"); 
	mySql.addSubPara(insuredno);
	mySql.addSubPara(fm.ContNo.value);
	mySql.addSubPara(insuredno); 
       arrResult=easyExecSql(mySql.getString(),1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};
       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};
       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};
    }
}
function getdetail()
{
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";
 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql24");  
	mySql.addSubPara(fm.BankAccNo.value); 
arrResult = easyExecSql(mySql.getString());
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// �ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ
function InsuredChkNew(){

        var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
       /* var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
      */  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql25");  
	mySql.addSubPara(tInsuredName);
	mySql.addSubPara(tInsuredSex);
	mySql.addSubPara(tBirthday);
	mySql.addSubPara(tInsuredNo);
	mySql.addSubPara(fm.IDType.value);
	mySql.addSubPara(fm.IDNo.value);
	mySql.addSubPara(tInsuredNo);
        arrResult = easyExecSql(mySql.getString(),1,0);


        if(arrResult==null)
        {//disabled"Ͷ����Ч��"��ť

					fm.InsuredChkButton.disabled = true;
//				  return false;
        }else{
					//�������ͬ�������Ա����ղ�ͬ�ͻ��ŵ��û���ʾ"Ͷ����У��"��ť
				}
}

function checkappntbirthday(){
  if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ���˳�����������!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}
else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ���˳�����������!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}

function getAge(){
	
	//alert("fm.Birthday.value=="+fm.Birthday.value);
	
  if(fm.Birthday.value==""){
  	return;
  }
  //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	
  	if(calAge(fm.Birthday.value)<0)
  	{
      alert("��������ֻ��Ϊ��ǰ������ǰ");
      fm.InsuredAppAge.value="";
      return;
    }
  	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    return;
  }
 
   
  	  if(calAge(fm.Birthday.value)<0)
  	 {
      alert("��������ֻ��Ϊ��ǰ������ǰ");
      fm.InsuredAppAge.value="";
      return;
     }
  
  fm.InsuredAppAge.value=calAge(fm.Birthday.value);
  return; 

}



//У�鱻���˳�������
function checkinsuredbirthday(){
	if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("������ı����˳�����������!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}

else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("������ı����˳�����������!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}

/*********************************************************************
 *  �Ѳ�ѯ���صı����˿ͻ�������ʾ�������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayInsuredInfo()
{
    try {document.all('InsuredNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('Name').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        //document.all('JoinCompanyDate').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        //document.all('Salary').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    
    try {
        document.all('OccupationType').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = arrResult[0][26];
    } catch(ex) {
    }
    ;    
}
/**
 *Ͷ������ϸ������ʾ
 */
function displayLCCont() {
    //alert("aaa");
    try {
        document.all('GrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('ProposalContNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyType').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolType ').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('CardFlag').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('ManageCom').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        //document.all('ExecuteCom ').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCom').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentGroup').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode1 ').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentType').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('SaleChnl').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('Handler').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('Password').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday ').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredNo').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredName').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredSex').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredBirthday').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDType ').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDNo').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayIntv').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecPayMode').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayLocation').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('DisputedFlag').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('OutPayFlag').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolMode').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignCom').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignDate').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignTime').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('ConsignNo').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankCode').value = arrResult[0][41];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankAccNo').value = arrResult[0][42];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntAccName').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrintCount').value = arrResult[0][44];
    } catch(ex) {
    }
    ;
    try {
        document.all('LostTimes').value = arrResult[0][45];
    } catch(ex) {
    }
    ;
    try {
        document.all('Lang').value = arrResult[0][46];
    } catch(ex) {
    }
    ;
    try {
        document.all('Currency').value = arrResult[0][47];
    } catch(ex) {
    }
    ;
    try {
        document.all('Remark').value = arrResult[0][48];
    } catch(ex) {
    }
    ;
    try {
        document.all('Peoples ').value = arrResult[0][49];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mult').value = arrResult[0][50];
    } catch(ex) {
    }
    ;
    try {
        document.all('Prem').value = arrResult[0][51];
    } catch(ex) {
    }
    ;
    try {
        document.all('Amnt').value = arrResult[0][52];
    } catch(ex) {
    }
    ;
    try {
        document.all('SumPrem').value = arrResult[0][53];
    } catch(ex) {
    }
    ;
    try {
        document.all('Dif').value = arrResult[0][54];
    } catch(ex) {
    }
    ;
    try {
        document.all('PaytoDate').value = arrResult[0][55];
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstPayDate').value = arrResult[0][56];
    } catch(ex) {
    }
    ;
    try {
        document.all('CValiDate').value = arrResult[0][57];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputOperator ').value = arrResult[0][58];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputDate').value = arrResult[0][59];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputTime').value = arrResult[0][60];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveFlag').value = arrResult[0][61];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveCode').value = arrResult[0][62];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveDate').value = arrResult[0][63];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveTime').value = arrResult[0][64];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWFlag').value = arrResult[0][65];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWOperator').value = arrResult[0][66];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWDate').value = arrResult[0][67];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWTime').value = arrResult[0][68];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppFlag').value = arrResult[0][69];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolAppntDate').value = arrResult[0][70];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolDate').value = arrResult[0][71];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolTime').value = arrResult[0][72];
    } catch(ex) {
    }
    ;
    try {
        document.all('CustomGetPolDate').value = arrResult[0][73];
    } catch(ex) {
    }
    ;
    try {
        document.all('State').value = arrResult[0][74];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][75];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][76];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][77];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][78];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][79];
    } catch(ex) {
    }
    ;
    try {
        document.all('SellType').value = arrResult[0][87];
    } catch(ex) {
    }
    ;

    try {
        document.all('AppntBankCode').value = arrResult[0][90];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = arrResult[0][91];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][92];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayMode').value = arrResult[0][93];
    } catch(ex) {
    }
    ;

}
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
    if (showInfo != null)
    {
        try
        {
            showInfo.focus();
        }
       // ;
    catch
        (ex)
    {
        showInfo = null;
    }
    }
}

//ְҵ���˫��ʱΪ����ʱ����ʾְҵ������
function OccupationTypeDBLClick()
{
    //alert(fm.PolTypeFlag.value);
    if (fm.PolTypeFlag.value == "0")
    {
        return ;
    }
    showCodeList('OccupationType',[fm.OccupationType]);
}

//ְҵ�������ʱΪ����ʱ����ʾְҵ������
function OccupationTypeKeyUP()
{
    if (fm.PolTypeFlag.value == "0")
    {
        return ;
    }
    showCodeListKey('OccupationType',[fm.OccupationType]);
}
function checkGrpContType()
{
	 /*var sql="select * from lcgrpcont where prtno='"+fm.PrtNo.value
	      +"' and exists (select 'X' from lxbalance where trim(PrtNo)=balanceno)";
*/
  /*var sql=" select * from lcgrpcont where prtno='"+fm.PrtNo.value
       +"' and exists ( select 'x' from lxbalance  where prtno = "
       +" lxbalance.proposalno)" ;*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql26");  
	mySql.addSubPara(fm.PrtNo.value);
	 var aRe=easyExecSql(mySql.getString());
	 if(aRe==null)
	  {
	  	isBroker=false;
	  }
	else
		{
			isBroker=true ;
		}
}
function afterQuery2(arrQueryResult)
{
	  try {document.all('Name').value= arrQueryResult[0][0]; } catch(ex) { };
	  try {document.all('Sex').value= arrQueryResult[0][1]; } catch(ex) { };
	  try {document.all('Birthday').value= arrQueryResult[0][2]; } catch(ex) { };
	  try {document.all('IDType').value= arrQueryResult[0][3]; } catch(ex) { };
	  try {document.all('IDNo').value= arrQueryResult[0][4]; } catch(ex) { };
	  try {document.all('OccupationType').value= arrQueryResult[0][5]; } catch(ex) { };
	  try {document.all('OccupationCode').value= arrQueryResult[0][6]; } catch(ex) { };
	  //try {document.all('Salary').value= arrQueryResult[0][7]; } catch(ex) { };
	  //try {document.all('JoinCompanyDate').value= arrQueryResult[0][8]; } catch(ex) { };
	  try {document.all('CardNo').value= arrQueryResult[0][9]; } catch(ex) { };
}
function showBrokerInsuredPeople()
{
		//showInfo = window.open("../broker/BrokerInsuredPeoples.jsp?BalanceNo="+fm.PrtNo.value);
		showInfo = window.open("../broker/BrokerInsuredPeoples.jsp?BalanceNo="+fm.PrtNo.value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function checkISFILL()
{
	//  �����������Ƿ���Ҫ�������Ҫ������û�м�����Ϣ����������
	//var sql="select insuredstat from lcinsured where contno='"+vContNo+"' and name='������' and pluralitytype is null ";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql27");  
	mySql.addSubPara(vContNo);
	var aRe=easyExecSql(mySql.getString());
	if(aRe==null)
	{
//		alert("��ѯ��������Ϣʧ��");
//		return false;
  }
else
	{
		if(aRe[0][0]=='0')
		{//����Ҫ���ֱ�ӷ���
			return true;
		}
  		/**
  		 * zhangzheng 2009-02-12 
  		 * ϵͳû����Щlxϵ�еı�,�ȷ�ס,������������������
  		 */
//	  if(aRe[0][0]=='1')
//	  {
//	  	//��Ҫ����ж��Ƿ����м�����Ϣ�����޲���������
//	  //	var sql2="select count(*) from lxcontinfo where appflag='1' and balanceno='"+PrtNo+"'";
//	  	
//	  	var sql2= " select count(*) from lxcontinfo where appflag='1' and inuredflag='0' and exists (select 'x' from lxbalancesub "
//	  	+" where startno<=certifyno and endno>=certifyno and balanceno = (select balanceno from lxbalance "
//	  	+ " where proposalno='"+fm.PrtNo.value+"')) ";
//	  	
//	  	prompt("��Ҫ����ж��Ƿ����м�����Ϣ�����޲���������",sql2);
//	  	var tCount=easyExecSql(sql2);
//	  	if(tCount[0][0]==0)
//	  	{
//
//	  		alert("û�м�����Ϣ�������������");	
//	  		parent.close();	
//	  		return false;
//	  		//window.close();
//	  	}
//	  	else
//	  	{
//	  		return true;
//	  	}
//	  	
//	  }
	
	}
	
	
}

function queryPol()
{
	
	var tGrpContNo = fm.BPNo.value;
	
//	var sql = "select a.contno,a.riskcode,(select riskname from lmrisk where riskcode = a.riskcode),b.standbyflag2,a.polno	"
//			 + " from lcpol a, lcduty b where a.polno = b.polno	 and a.riskcode in"
//			 + " (select riskcode from lmrisksort where risksorttype = '23')"
//			 + " and grpcontno = '"+tGrpContNo+"'";
	
	//������������
    //1.1 ��RiskCode�������ִ���;
    //1.2 RiskSortType(���ַ�������)����Ϊ23;
    //1.3 RiskSortValue(���ַ���ֵ):
       //0 �����������˲��˲���Ҫ��������  ����241803;
       //1 �����������˲�����Ҫ�������� ������241801��241805��ʹ��lcduty.StandbyFlag2У����������������;
       //2 ��Ҫ�жϳб�ʱ¼����Ƿ��������������� ������241802��241806��ʹ��lcduty.StandbyFlag1�ж��Ƿ������������� 0-������������,1-������������

	
	/*var sql = "select k1,k2,k3,k4,k5,k6 from ("
		 + "select a.contno k1,a.riskcode k2,(select riskname from lmrisk where riskcode = a.riskcode) k3,b.standbyflag2 k4,a.polno k5,c.risksortvalue k6"
		 + " from lcpol a, lcduty b,lmrisksort c where a.polno = b.polno and a.riskcode=c.riskcode" 
		 + " and c.risksorttype = '23' and risksortvalue in('0','1')"
		 + " and grpcontno = '"+tGrpContNo+"'"
		 + " union "
		 + "  select a.contno k1,a.riskcode k2,(select riskname from lmrisk where riskcode = a.riskcode) k3,b.standbyflag2 k4,a.polno k5,c.risksortvalue k6"
		 + " from lcpol a, lcduty b,lmrisksort c where a.polno = b.polno and a.riskcode=c.riskcode" 
		 + " and c.risksorttype = '23' and risksortvalue in('2') and b.StandbyFlag1='1'"
		 + " and grpcontno = '"+tGrpContNo+"'"
		 + ") order by k1,k2";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql27");  
	mySql.addSubPara(tGrpContNo);
	mySql.addSubPara(tGrpContNo);
	//prompt("queryPol:",sql);
	turnPage.queryModal(mySql.getString(), ContGrid);
}

//��ѯ��������������Ϣ
function getInsuredRela(){
	var tSelNo = MainInsuredGrid.getSelNo();
	var tMainInsuredNo = MainInsuredGrid.getRowColData(tSelNo - 1, 1);
	var tPolNo = fm.PolNo.value;
    var tGrpContNo=document.all("BPNo").value;
   /* var strSQL ="select customerno,Name,Sex,Birthday,RelationToInsured,SequenceNo from lcinsuredrelated a where "
        		+ "a.polno='"+tPolNo+"' and maincustomerno ='"+tMainInsuredNo+"'" 
        		+ " and RelationToInsured <>'00'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaInputSql");
	mySql.setSqlId("InsuredRelaSql28");  
	mySql.addSubPara(tPolNo);
	mySql.addSubPara(tMainInsuredNo);
    turnPage.queryModal(mySql.getString(), InsuredRelaGrid);
}
//-----------------------------------------------End