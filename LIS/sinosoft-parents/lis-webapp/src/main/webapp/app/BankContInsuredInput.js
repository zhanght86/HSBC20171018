 //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mOperate = "";
var showInfo1;
var mDebug="0";
var turnPage = new turnPageClass(); 
var arrResult;

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

//�ύ�����水ť��Ӧ����
function submitForm()
{
 }


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  try { showInfo.close(); } catch(e) {}	             
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  if (FlagStr=="Succ" && mWFlag == 0)   
  {
  	if(confirm("�Ƿ����¼�������ͻ���"))
  	{
	       if(fm.InsuredSequencename.value=="��һ������������")
	       {
	       	     //emptyFormElements();
	       	     param="122";
	       	     fm.pagename.value="122"; 
	       	     fm.SequenceNo.value="2";
                            fm.InsuredSequencename.value="�ڶ�������������";	
                            if (scantype== "scan") 
                            {  
                            setFocus(); 
                            }	
                            noneedhome();                     	     
                            return false;
	       }
	       if(fm.InsuredSequencename.value=="�ڶ�������������")
	       {           
	       	     //emptyFormElements(); 
	       	     param="123";
	       	     fm.pagename.value="123"; 
	       	     fm.SequenceNo.value="3";
                            fm.InsuredSequencename.value="����������������";
                            if (scantype== "scan") 
                            {  
                            setFocus(); 
                            }	 
                            noneedhome();                                         
                            return false;		     
	       }
	       	if(fm.InsuredSequencename.value=="����������������")
	       {
	       	     //emptyFormElements();
	       	     param="124";
	       	     fm.pagename.value="124"; 
	       	     fm.SequenceNo.value="";
                            fm.InsuredSequencename.value="���ı�����������";
                            if (scantype== "scan") 
                            {  
                            setFocus(); 
                            }	                     
                            return false;		     
	       }  		
      }
  	
  }
  	
	
}




//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterQuery( FlagStr, content )
{
  
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
 
} 

//ȡ����ť��Ӧ����
function cancelForm()
{

}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
	
}           


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,50,82,*";
    }
 	else 
 	{
        parent.fraMain.rows = "0,0,0,82,*";
 	}
}

//�����¼��
function QuestInput()
{
    cContNo = fm.ContNo.value;  //��������	
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
        if(mSwitch.getVar( "ProposalGrpContNo" )=="")
        {
            alert("���޼����ͬͶ�����ţ����ȱ���!");	
        }
		else
		{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
        }
    }
    else
    {
        if(cContNo == "")
        {
            alert("���޺�ͬͶ�����ţ����ȱ���!");
        }
        else
        {	
            window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1");
        }
    }
}
//�������ѯ
function QuestQuery()
{
   cContNo = document.all("ContNo").value;  //��������
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
         if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
         {
          	alert("����ѡ��һ����������Ͷ����!");
          	return ;
         }
         else
         {
              window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
         }        
         }
    else
    {
        if(cContNo == "")
        {
	       alert("���޺�ͬͶ�����ţ����ȱ���!");
        }
	    else
	    {
            window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
        }
    }
} 
//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{

}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  
}           

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  
}

/*********************************************************************
 *  ����������Ϣ¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intoRiskInfo()
{
	if(fm.InsuredNo.value==""||fm.ContNo.value=="")
	{
		alert("������ӣ�ѡ�񱻱���");
		return false;
	}
	//mSwitch =parent.VD.gVSwitch;
	delInsuredVar();
	addInsuredVar();

  try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){}; //ѡ�����ֵ��������ֽ�������ѱ������Ϣ
  
	if ((LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("����ѡ�񱻱�����������Ϣ��");
		return;
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){};
	try{mSwitch.deleteVar('ContNo');}catch(ex){};
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){};
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){};
	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
}

/*********************************************************************
 *  ɾ�������б���������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delInsuredVar()
{
    try{mSwitch.deleteVar('ContNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredNo');}catch(ex){};
    try{mSwitch.deleteVar('PrtNo');}catch(ex){};
    try{mSwitch.deleteVar('GrpContNo');}catch(ex){};
 //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
 //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try{mSwitch.deleteVar('ExecuteCom');}catch(ex){};
    try{mSwitch.deleteVar('FamilyType');}catch(ex){};
    try{mSwitch.deleteVar('RelationToMainInsure');}catch(ex){};
    try{mSwitch.deleteVar('RelationToAppnt');}catch(ex){};
    try{mSwitch.deleteVar('AddressNo');}catch(ex){};
    try{mSwitch.deleteVar('SequenceNo');}catch(ex){};
    try{mSwitch.deleteVar('Name');}catch(ex){};
    try{mSwitch.deleteVar('Sex');}catch(ex){};
    try{mSwitch.deleteVar('Birthday');}catch(ex){};
    try{mSwitch.deleteVar('IDType');}catch(ex){};
    try{mSwitch.deleteVar('IDNo');}catch(ex){};
    try{mSwitch.deleteVar('RgtAddress');}catch(ex){};
    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try{mSwitch.deleteVar('Health');}catch(ex){};
    try{mSwitch.deleteVar('Stature');}catch(ex){};
    try{mSwitch.deleteVar('Avoirdupois');}catch(ex){};
    try{mSwitch.deleteVar('Degree');}catch(ex){};
    try{mSwitch.deleteVar('CreditGrade');}catch(ex){};
    try{mSwitch.deleteVar('BankCode');}catch(ex){};
    try{mSwitch.deleteVar('BankAccNo');}catch(ex){};
    try{mSwitch.deleteVar('AccName');}catch(ex){};
    try{mSwitch.deleteVar('JoinCompanyDate');}catch(ex){};
    try{mSwitch.deleteVar('StartWorkDate');}catch(ex){};
    try{mSwitch.deleteVar('Position');}catch(ex){};
    try{mSwitch.deleteVar('Salary');}catch(ex){};
    try{mSwitch.deleteVar('OccupationType');}catch(ex){};
    try{mSwitch.deleteVar('OccupationCode');}catch(ex){};
    try{mSwitch.deleteVar('WorkType');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){}; 
}

/*********************************************************************
 *  ������������Ϣ���뵽������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addInsuredVar()
{
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
    //alert("ContNo:"+fm.ContNo.value);
    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
 //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
 //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('ExecuteCom','',fm.ExecuteCom.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('RelationToMainInsure','',fm.RelationToMainInsure.value);}catch(ex){};
    try{mSwitch.addVar('RelationToAppnt','',fm.RelationToAppnt.value);}catch(ex){};
    try{mSwitch.addVar('AddressNo','',fm.AddressNo.value);}catch(ex){};
    try{mSwitch.addVar('SequenceNo','',fm.SequenceNo.value);}catch(ex){};
    try{mSwitch.addVar('Name','',fm.Name.value);}catch(ex){};
    try{mSwitch.addVar('Sex','',fm.Sex.value);}catch(ex){};
    try{mSwitch.addVar('Birthday','',fm.Birthday.value);}catch(ex){};
    try{mSwitch.addVar('IDType','',fm.IDType.value);}catch(ex){};
    try{mSwitch.addVar('IDNo','',fm.IDNo.value);}catch(ex){};
    try{mSwitch.addVar('RgtAddress','',fm.RgtAddress.value);}catch(ex){};
    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try{mSwitch.addVar('Health','',fm.Health.value);}catch(ex){};
    try{mSwitch.addVar('Stature','',fm.Stature.value);}catch(ex){};
    try{mSwitch.addVar('Avoirdupois','',fm.Avoirdupois.value);}catch(ex){};
    try{mSwitch.addVar('Degree','',fm.Degree.value);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',fm.CreditGrade.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',fm.JoinCompanyDate.value);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',fm.StartWorkDate.value);}catch(ex){};
    try{mSwitch.addVar('Position','',fm.Position.value);}catch(ex){};
    try{mSwitch.addVar('Salary','',fm.Salary.value);}catch(ex){};
    try{mSwitch.addVar('OccupationType','',fm.OccupationType.value);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',fm.OccupationCode.value);}catch(ex){};
    try{mSwitch.addVar('WorkType','',fm.WorkType.value);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
		try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};

}

/*********************************************************************
 *  ��ӱ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addRecord()
{    
	  var tPrtNo=document.all("PrtNo").value;
//	  var sqlStr = "select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";
	  
		var sqlid1="BankContInsuredInputSql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tPrtNo);//ָ������Ĳ���
		var strSql=mySql1.getString();
		  
      arrResult=easyExecSql(strSql,1,0);	
      if(arrResult!=null)
      {
      for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[sequencenocout][0])
      {	  	
	  	alert("�Ѿ����ڸÿͻ��ڲ���");
	  	fm.SequenceNo.focus();
	  	return false;	  	
       }
      }
      } 
    if(LoadFlag==1)
    {
    	if(fm.RelationToMainInsured.value=="")
    	{
    		
    		alert("����д�����������˹�ϵ��");
    		return false;
    	} 
    	if(fm.RelationToAppnt.value=="")
    	{
    		alert("����д��Ͷ���˹�ϵ����");
    		return false;
    	}        	   	
    }
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInput2() == false ) return false;
    }
    if(document.all('IDType').value=="0")
    {
       var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
       if (strChkIdNo != "")
       {  	
             alert(strChkIdNo);
	     return false;
       }  
    }  
    if(!checkself())
    return false;

    if(!checkrelation())
    return false;
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)return false;    
    if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;    
	ImpartGrid.delBlankLine();
	ImpartDetailGrid.delBlankLine();	
     if (fm.InsuredNo.value==''&&fm.AddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all( 'BQFlag' ).value = BQFlag;    
    document.all('fmAction').value="INSERT||CONTINSURED";
		fm.RelationToMainInsured.disabled = false	;
    fm.submit();
 }
 
/*********************************************************************
 *  �޸ı�ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */  
function modifyRecord()
{
/*
		    var tPrtNo=document.all("PrtNo").value;
      arrResult=easyExecSql("select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'",1,0);	
      for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[0][sequencenocout]){	  	
	  	alert("�Ѿ����ڸÿͻ��ڲ���");
	  	fm.SequenceNo.focus();
	  	return false;	  	
	  	}
	    }
*/	
	
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInput2() == false ) return false;
    }
    if(!checkself())
        return false;
    if (fm.Name.value=='')
    {
        alert("��ѡ����Ҫ�޸ĵĿͻ���")
        return false;
    }
    //alert("SelNo:"+InsuredGrid.getSelNo());
    if (InsuredGrid.mulLineCount==0)
    {
        alert("�ñ������˻�û�б��棬�޷������޸ģ�");
        return false;    
    }
    if (fm.InsuredNo.value==''&&fm.AddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;    
    }
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)return false;    
    if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;    
	ImpartGrid.delBlankLine();
	ImpartDetailGrid.delBlankLine();     
     
    document.all('ContType').value=ContType;
    document.all('fmAction').value="UPDATE||CONTINSURED";
    fm.submit();
}
/*********************************************************************
 *  ɾ����ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */  
 function deleteRecord()
{
    if (fm.InsuredNo.value=='')
    {
        alert("��ѡ����Ҫɾ���Ŀͻ���")
        return false;
    }
    if (InsuredGrid.mulLineCount==0)
    {
        alert("�ñ������˻�û�б��棬�޷������޸ģ�");
        return false;    
    }
     if (fm.InsuredNo.value==''&&fm.AddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.submit();
}
/*********************************************************************
 *  ������һҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */  
function returnparent()
{     
  	var backstr=document.all("ContNo").value;
  	
	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(document.all("PrtNo").value);
  	    location.href="../app/BankContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
        }
        if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(document.all("PrtNo").value);
  	    location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
        }
        
	if(LoadFlag=="2")
	{
  	    location.href="../app/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + document.all("GrpContNo").value+"&scantype="+scantype;
        }
        
	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+document.all("PrtNo").value;
	    return;
	}
	else if (LoadFlag=="7")
	{
	    location.href="../bq/GEdorTypeNI.jsp?BQFlag="+BQFlag;
	    return;
	}
	else if(LoadFlag=="4"||LoadFlag=="16"||LoadFlag=="13"||LoadFlag=="14"||LoadFlag=="23")
	{  
	    if(Auditing=="1")
	    {
	    	top.close();
	    }
	    else
	    {
	    mSwitch.deleteVar("PolNo");	
            parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype; 
	    }
	}
	else if (LoadFlag=="99")
	{
	    location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag+"&scantype="+scantype;
	    return;
	}		
/*    else
    {
        location.href="ContInput.jsp?LoadFlag="+ LoadFlag; 
	}  ���	��������Ĳ�ͬ�ݲ�֧��else��ʽ
*/
}
/*********************************************************************
 *  �������ּƻ�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */  
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../app/GroupRiskPlan.jsp");
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
    {
				if(cCodeName == "FamilyType"){
					if(Field.value == "0"){
						fm.RelationToMainInsured.value="00";
						fm.RelationToMainInsured.disabled = true;
						fm.SequenceNo.disabled = true;
					}else if(Field.value == "1"){
						fm.RelationToMainInsured.disabled = false;
						fm.SequenceNo.disabled = false;
					}
				}
        //�����������
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value!='0')
            {
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=true;
            }
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
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
//        var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";
       
    	var sqlid1="BankContInsuredInputSql1";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(ContNo);//ָ������Ĳ���
    	var strSQL=mySql1.getString();
        
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult) 
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = InsuredGrid;    
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
 *  ��ø�����ͬ�ı�������Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getProposalInsuredInfo()
{  
    var tContNo=document.all("ContNo").value;
//    var sqlstr =  "select * from LCInsured where ContNo='"+tContNo+"'";
    
	var sqlid1="BankContInsuredInputSql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	var strSql=mySql1.getString();
    
    arrResult=easyExecSql(strSql,1,0);
    if(arrResult==null)
    {
      //alert("δ�õ���Ͷ������Ϣ");
        return;
    }
    else
    {
        DisplayInsured();//�ú�ͬ�µı�Ͷ������Ϣ
        var tCustomerNo = arrResult[0][2];		// �õ�Ͷ���˿ͻ���
        var tAddressNo = arrResult[0][10]; 		// �õ�Ͷ���˵�ַ��
        
//        var sqlstr = "select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'"
        
    	var sqlid1="BankContInsuredInputSql3";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(tCustomerNo);//ָ������Ĳ���
    	var strSql=mySql1.getString();
        
        arrResult=easyExecSql(strSql,1,0);
        if(arrResult==null)
        {
            //alert("δ�õ��û���Ϣ");
            //return;
        }
        else
        {
            //displayAppnt();       //��ʾͶ������ϸ����
            emptyUndefined();
            fm.AddressNo.value=tAddressNo;
            getdetailaddress();//��ʾͶ���˵�ַ��ϸ����
        } 
    } 
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
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
//    var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    
	var sqlid1="BankContInsuredInputSql4";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
	var strSQL=mySql1.getString();
    
    arrResult=easyExecSql(strSQL);  
    if(arrResult!=null)
    {
        displayAppnt();
    }
//    strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
    
	var sqlid2="BankContInsuredInputSql5";
	var mySql2=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
	strSQL=mySql1.getString();
    
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ�� 
    fm.AddressNo.value=tAddressNo;
    getdetailaddress();  	
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
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
    try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};           
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};             
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};      
    try{document.all('AddressNo').value=arrResult[0][10];}catch(ex){};           
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
    try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};     
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};       
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};            
    try{document.all('Salary').value=arrResult[0][33];}catch(ex){};              
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
    try{document.all('AddressNo').value= mSwitch.getVar( "AddressNo" ); }catch(ex){};       
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
    try{document.all( 'JoinCompanyDate' ).value = mSwitch.getVar( "JoinCompanyDate" ); if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
    try{document.all('StartWorkDate').value= mSwitch.getVar( "AppntStartWorkDate" );}catch(ex){}; 
    try{document.all('Position').value= mSwitch.getVar( "AppntPosition" );}catch(ex){};      
    try{document.all( 'Position' ).value = mSwitch.getVar( "Position" ); if(document.all( 'Position' ).value=="false"){document.all( 'Position' ).value="";} } catch(ex) { }; 
    try{document.all('Salary').value= mSwitch.getVar( "AppntSalary" );}catch(ex){};        
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
    var ContNo=document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
//        var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ProposalContNo='"+ContNo+"' and CustomerNoType='I'";
        
    	var sqlid1="BankContInsuredInputSql6";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
    	mySql1.addSubPara(ContNo);//ָ������Ĳ���
    	var strSQL=mySql1.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
//        var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent,StartDate,EndDate,Prover,CurrCondition,IsProved from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
        
    	var sqlid1="BankContInsuredInputSql7";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
    	mySql1.addSubPara(ContNo);//ָ������Ĳ���
    	var strSQL=mySql1.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
function info(){
	alert("---------------");
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
//        var strSQL ="select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
        
    	var sqlid1="BankContInsuredInputSql8";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
    	mySql1.addSubPara(ContNo);//ָ������Ĳ���
    	var strSQL=mySql1.getString();
        
        //alert(strSQL);
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
        if (document.all('ContNo').value != "") 
        {
            alert("�ŵ��ĸ��������ж౻������");
            return false;
        }
        else 
            return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0") 
        {
//            var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
            
            var ContNo1 = document.all('ContNo').value;
        	var sqlid1="BankContInsuredInputSql9";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(ContNo1);//ָ������Ĳ���
        	var strSQL=mySql1.getString();
            
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("���������ж౻������");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00") 
        {
            alert("��ͥ��ֻ����һ������������");
            return false;
        }                 
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00") 
        {
//            var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
            
            var ContNo2 = document.all('ContNo').value;
        	var sqlid1="BankContInsuredInputSql10";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(ContNo2);//ָ������Ĳ���
        	var strSql=mySql1.getString();
            
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
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
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";   
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00" 
      displayissameperson();      
    }
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    else if (fm.SamePersonFlag.checked == true) 
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "none";  
      divSalary.style.display = "none";    
      displayissameperson();     
    }
    //��Ӧ��ѡͬһ�˵����
    else if (fm.SamePersonFlag.checked == false) 
    {
    document.all('DivLCInsured').style.display = "";
    divLCInsuredPerson.style.display = "none"; 
    divSalary.style.display = "none";     
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
      showAppnt1();                                                                                      
    } 
    else 
    {                                                                   
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
//    	var strSql="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'"
        
    	var InsuredNo1 = document.all("InsuredNo").value ;
    	var sqlid1="BankContInsuredInputSql11";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(InsuredNo1);//ָ������Ĳ���
    	var strSql=mySql1.getString();
    	
    	arrResult = easyExecSql(strSql, 1, 0);
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
		showInfo = window.open( "../sys/LDPersonQueryNew.html" );               
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
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){}; 
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){}; 
    try{document.all('Position').value= arrResult[0][23];}catch(ex){}; 
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){}; 
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
    //��ַ��ʾ���ֵı䶯
    try{document.all('AddressNo').value= "";}catch(ex){}; 
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
            
//            var strSql="select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'";
        	
        	var sqlid1="BankContInsuredInputSql12";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        	var strSql=mySql1.getString();
            
            arrResult = easyExecSql(strSql, 1, 0);
            
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
        	
//        	var strSql="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'";
        	
        	var sqlid1="BankContInsuredInputSql13";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        	var strSql=mySql1.getString();
        	
        	arrResult = easyExecSql(strSql, 1, 0);
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
//    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    
    var OccupationCode1 = fm.OccupationCode.value;
	var sqlid1="BankContInsuredInputSql14";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(OccupationCode1);//ָ������Ĳ���
	var strSql=mySql1.getString();
    
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) 
    {
        fm.OccupationType.value = arrResult[0][0];
    }
    else
    {  
        fm.OccupationType.value = '';
    }	
}  
/*��ø��˵���Ϣ��д��ҳ��ؼ�
function getProposalInsuredInfo(){
  var ContNo = fm.ContNo.value;
  //��������ϸ��Ϣ
  var strSQL ="select * from ldperson where CustomerNo in (select InsuredNo from LCInsured where ContNo='"+ContNo+"')";
  arrResult=easyExecSql(strSQL);  
  if(arrResult!=null){
  	DisplayCustomer();
  }
  
  strSQL ="select * from LCInsured where ContNo = '"+ContNo+"'";
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null){
    DisplayInsured();
  }else{
    return;
  }

  var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ�� 
  var InsuredNo=arrResult[0][2]; 
  var strSQL="select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+InsuredNo+"'";
  arrResult=easyExecSql(strSQL);
    if(arrResult!=null){
  	DisplayAddress();
    }
     	
    getInsuredPolInfo();
    
}*/


/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag)
{
	//alert("LoadFlag=="+LoadFlag);
	
	
    if (wFlag ==1 ) //¼�����ȷ��
    {
//        var tStr= "	select * from lwmission where 1=1 and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        
        var ContNo1 = fm.ContNo.value;
    	var sqlid1="BankContInsuredInputSql15";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(ContNo1);//ָ������Ĳ���
    	var tStr=mySql1.getString();
        
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) 
        {
		    alert("�ú�ͬ�Ѿ��������棡");
		    return;
		}
		fm.AppntNo.value = AppntNo;
		fm.AppntName.value = AppntName;
		fm.WorkFlowFlag.value = "7999999999";
    }
    else if (wFlag ==2)//�������ȷ��
    {
  	    if(document.all('ProposalContNo').value == "") 
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
            return;
        }
		fm.WorkFlowFlag.value = "0000001001";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag ==3)
    {
        if(document.all('ProposalContNo').value == "") 
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
            return;
        }
		fm.WorkFlowFlag.value = "0000001002";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;
		
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

    fm.action = "./InputConfirm.jsp";
    fm.submit(); //�ύ
} 
/*********************************************************************
 *  ��ѯ����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailaddress()
{

//    var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.homephone from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
   
    
    var AddressNo1 = fm.AddressNo.value��
    var InsuredNo5 = fm.InsuredNo.value��
	var sqlid1="BankContInsuredInputSql16";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(AddressNo1);//ָ������Ĳ���
	mySql1.addSubPara(InsuredNo5);//ָ������Ĳ���
	var strSQL=mySql1.getString();
    
    arrResult=easyExecSql(strSQL);
    try{document.all('AddressNo').value= arrResult[0][0];}catch(ex){}; 
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){}; 
    try{document.all('ZipCode').value= arrResult[0][2];}catch(ex){}; 
    try{document.all('Phone').value= arrResult[0][3];}catch(ex){}; 
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){}; 
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){}; 
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){}; 
    try{document.all('InsuredCompanyPhone').value= arrResult[0][6];}catch(ex){}; 
    try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){}; 
    try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){}; 
    try{document.all('InsuredHomePhone').value= arrResult[0][9];}catch(ex){}; 

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
//    strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
    
	var sqlid1="BankContInsuredInputSql17";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tProposalGrpContNo);//ָ������Ĳ���
	strsql=mySql1.getString();
    
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
    	 divContPlan.style.display="";   
    }
    else
    {
      //alert("���ռƻ�û�鵽");
        divContPlan.style.display="none";   
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
    	//alert("1");
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
//	strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	
	var sqlid1="BankContInsuredInputSql18";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tProposalGrpContNo);//ָ������Ĳ���
	strsql=mySql1.getString();
	
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		divExecuteCom.style.display="";
	}
	else
	{
	    divExecuteCom.style.display="none";
    }
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
}

function emptyInsured()
{
	
	try{document.all('InsuredNo').value= ""; }catch(ex){}; 
	try{document.all('ExecuteCom').value= ""; }catch(ex){}; 
	try{document.all('FamilyID').value= ""; }catch(ex){}; 
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){}; 
	try{document.all('RelationToAppnt').value= ""; }catch(ex){}; 
	try{document.all('AddressNo').value= ""; }catch(ex){}; 
	//try{document.all('SequenceNo').value= ""; }catch(ex){}; 
	try{document.all('Name').value= ""; }catch(ex){}; 
	try{document.all('Sex').value= ""; }catch(ex){}; 
	try{document.all('Birthday').value= ""; }catch(ex){}; 
	try{document.all('IDType').value= "0"; }catch(ex){}; 
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
	try{document.all('JoinCompanyDate').value= ""; }catch(ex){}; 
	try{document.all('StartWorkDate').value= ""; }catch(ex){}; 
	try{document.all('Position').value= ""; }catch(ex){}; 
	try{document.all('Salary').value= ""; }catch(ex){}; 
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
	ImpartDetailGrid.clearData(); 
	ImpartDetailGrid.addOne();	
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
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){}; 
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
	if(document.all('IDType').value=="0")
	{
		document.all('Birthday').value=getBirthdatByIdNo(iIdNo);
		document.all('Sex').value=getSexByIDNo(iIdNo);
	}	
} 
/*********************************************************************
 *  ��ͬ��Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{  
	mWFlag = 1;
	if (wFlag ==1 ) //¼�����ȷ��
	{
//	    var tStr= "	select * from lwmission where 1=1 "
//	    					+" and lwmission.processid = '0000000004'"
//	    					+" and lwmission.activityid = '0000002001'"
//	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    
	    var ProposalGrpContNo1 = fm.ProposalGrpContNo.value;
		var sqlid1="BankContInsuredInputSql19";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(ProposalGrpContNo1);//ָ������Ĳ���
		var tStr=mySql1.getString();
	    
	    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
	    if (turnPage.strQueryResult) 
	    {
	        alert("���ŵ���ͬ�Ѿ��������棡");
	        return;
	    }
		if(document.all('ProposalGrpContNo').value == "") 
	    {
	        alert("�ŵ���ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//¼�����
    }
    else if (wFlag ==2)//�������ȷ��
    {
        if(document.all('ProposalGrpContNo').value == "") 
	    {
	        alert("δ��ѯ���ŵ���ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000002002";					//�������
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(document.all('ProposalGrpContNo').value == "") 
	    {
	        alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		if(document.all('ProposalGrpContNo').value == "") 
	    {
	       alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
	       return;
	    }
	    fm.WorkFlowFlag.value = "0000001021";					//�����޸�
	    fm.MissionID.value = MissionID;
	    fm.SubMissionID.value = SubMissionID;		
	}
	else
		return;
		
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
	fm.action = "./GrpInputConfirm.jsp";
    fm.submit(); //�ύ
} 
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
//    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    
    var InsuredNo6 = fm.InsuredNo.value;
	var sqlid1="BankContInsuredInputSql20";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(InsuredNo6);//ָ������Ĳ���
	strsql=mySql1.getString();
    
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
    document.all("AddressNo").CodeData=tCodeData;
}                  

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;	
  window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer);
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
//	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//                +"  and Name='"+fm.Name.value
//                +"' and IDType='"+fm.IDType.value
//                +"' and IDNo='"+fm.IDNo.value   
//		+"' order by a.CustomerNo";
	    
	    
	    var Name = fm.Name.value;
	    var IDType = fm.IDType.value;
	    var IDNo = fm.IDNo.value;
		var sqlid1="BankContInsuredInputSql21";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(Name);//ָ������Ĳ���
		mySql1.addSubPara(IDType);//ָ������Ĳ���
		mySql1.addSubPara(IDNo);//ָ������Ĳ���
		strSQL=mySql1.getString();
	    
             turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
             if (turnPage.strQueryResult != "")
             { 	
 		  mOperate = 2;   
 		  window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
}
function DelRiskInfo()
{
	if(fm.InsuredNo.value=="")
	{
		alert("����ѡ�񱻱���");
		return false;
	}
	var tSel =PolGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert("�ÿͻ�û�����ֻ���������ѡ���ˣ�");	
		return false;
	}
	var tRow = PolGrid.getSelNo() - 1;	
	var tpolno=PolGrid.getRowColData(tRow,1)
	document.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	fm.submit(); //�ύ
	
}
function InsuredChk()
{
	var tSel =InsuredGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert("����ѡ�񱻱����ˣ�");	
		return false;
	}
	var tRow = InsuredGrid.getSelNo() - 1;	
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);	
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);		
//        var sqlstr="select *from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'";
        
		var sqlid1="BankContInsuredInputSql22";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tInsuredName);//ָ������Ĳ���
		mySql1.addSubPara(tInsuredSex);//ָ������Ĳ���
		mySql1.addSubPara(tBirthday);//ָ������Ĳ���
		mySql1.addSubPara(tInsuredNo);//ָ������Ĳ���
		var sqlstr=mySql1.getString();
        
        arrResult = easyExecSql(sqlstr,1,0);	
        if(arrResult==null)
        {
	   alert("��û����ñ����˱������ƵĿͻ�,����У��");
	   return false;
        }

	window.open("../uw/InsuredChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&InsuredNo="+tInsuredNo+"&Flag=I","window1");	
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
//       var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       
        var ContNo =  fm.ContNo.value;
		var sqlid1="BankContInsuredInputSql23";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(insuredno);//ָ������Ĳ���
		mySql1.addSubPara(ContNo);//ָ������Ĳ���
		mySql1.addSubPara(insuredno);//ָ������Ĳ���
		var strhomea=mySql1.getString();
       
       arrResult=easyExecSql(strhomea,1,0);  
  
       try{document.all('InsuredHomePhone').value= arrResult[0][2];}catch(ex){};  
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){}; 
       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){}; 

    }
}
function getdetail()
{
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";

var BankAccNo =  fm.BankAccNo.value;
var sqlid1="BankContInsuredInputSql24";
var mySql1=new SqlClass();
mySql1.setResourceName("app.BankContInsuredInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
mySql1.addSubPara(BankAccNo);//ָ������Ĳ���
var strSql=mySql1.getString();

arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }	
}      