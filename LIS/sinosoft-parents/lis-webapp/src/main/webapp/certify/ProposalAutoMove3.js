//ProposalAutoMove.js
var RiskType = "";
//var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
var mysql=new SqlClass();

mysql.setResourceName("certify.GrpSysCertTakeBackInputSql");
mysql.setSqlId("querysqldes9");
var strSql =mysql.getString();
var arrScanType = easyExecSql(strSql);
var mSwitch=parent.VD.gVSwitch;;
/**
 * Ϊÿ������¼��ؼ�������Ӧ�涯���¼�����ProposalInput.js�е��� 
 **/
function setFocus() {
  var tPrtNo ="";  
  var RiskType ="";  
  //alert("fm.ProposalContNo.value=="+fm.ProposalContNo.value);
  try
  {
    if (fm.ProposalContNo.value == null || fm.ProposalContNo.value == "")
    {
    	var tProposalContNo = mSwitch.getVar("ProposalContNo");
    	if(tProposalContNo==null||tProposalContNo ==""){
    		tPrtNo = fm.PrtNo.value;
    	}
    	else{
    		tPrtNo=tProposalContNo;
    	}
    }
    else
	  {
	  	tPrtNo = fm.ProposalContNo.value;//alert("tPrtNo=="+tPrtNo);
	  }
    
    RiskType = tPrtNo.substring(2,4);
  }catch(e){}
  
  if(RiskType == null || RiskType == "" )
  {
  	RiskType = fm.pagename.value;
  }
  //alert("RiskType: "+RiskType);
  var RowNo=0;
  var MultLineName = "";
  for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
  	//alert("LoadFlag"+LoadFlag);
    if (typeof(LoadFlag)!="undefined" && LoadFlag==99) {
      if (window.document.forms[0].elements[elementsNum].type == "text" 
          || window.document.forms[0].elements[elementsNum].type == "textarea" ) { 
        window.document.forms[0].elements[elementsNum].onclick = custom;
        window.document.forms[0].elements[elementsNum].onfocus = debugShow;  
      }
    
    }
    else {
    	if(RiskType=="15"||RiskType=="25"){
    		RiskType="35";
    	}
    	//����Ŀǰϵͳ�е���ɨ��¼��ӡˢ�Ų��淶���ݽ��������ӡˢ��ͳһ��Ϊ����Ͷ����  2008-12-26
    	if(RiskType!="35"&&RiskType!="61"&&RiskType!="51"&&RiskType!="16"&&RiskType!="21"){
    		RiskType="11";
    	}
    	var tFunction = "goToArea"+RiskType;
    	//alert(tFunction);
   // 	tFunction = tFunction + "('"+RowNo+"')";
    	  //if(window.document.forms[0].elements[elementsNum].name.indexOf("ImpartInsuredGrid")!=-1)
    	  //{
    	  //	alert(window.document.forms[0].elements[elementsNum].name);
    	  //}
    	  //alert("window.document.forms[0].elements[elementsNum]:"+window.document.forms[0].elements[elementsNum].name);
       if(window.document.forms[0].elements[elementsNum].name.indexOf("Grid")==-1)
        {
         //	eval(" window.document.forms[0].elements[elementsNum].onfocus = goToArea" +RiskType);  
         //
       //  var tObj = document.getElementById(window.document.forms[0].elements[elementsNum].id);
        // tObj.attachEvent("onfocus","goToArea" +RiskType);
         eval(" window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);   
       //  eval(" window.document.forms[0].elements[elementsNum].setAttribute('onfocus',goToArea" +RiskType+");");       		
        //  eval(" window.document.forms[0].elements[elementsNum].attachEvent('onfocus',goToArea" +RiskType+");");       		
       //  	eval(" window.document.forms[0].elements[elementsNum].setAttribute('onfocus','goToArea" +RiskType+"')" );  
      	}
     	else 
      	{
      		 //alert("MultLineName:"+MultLineName+":RowNo:"+RowNo);
      		 var tName = window.document.forms[0].elements[elementsNum].name.substring(0,window.document.forms[0].elements[elementsNum].name.indexOf('Grid'));
      		 if(MultLineName == null || MultLineName =="")
      		 {  
      		 	  MultLineName = tName;
      		 }
      		 else
      		 {
      		 	 if(MultLineName!=tName)
      		 	 {
      		 	 	  MultLineName = tName;
      		 	 	  RowNo = 0 ;
      		     }
      		 	 if(window.document.forms[0].elements[elementsNum].name.indexOf("No")!=-1)
      			 {
      			      //	alert(RowNo);
      				  RowNo = RowNo + 1;
      			 }
      		 }

  					eval("window.document.forms[0].elements[elementsNum].setAttribute('MultLineId','"+RowNo+"');");
    //  		alert(window.document.forms[0].elements[elementsNum].name);
    //  		eval(" window.document.forms[0].elements[elementsNum].setAttribute('onfocus','goToArea" +RiskType+"')" );  
      		//prompt('',"window.document.forms[0].elements[elementsNum].setAttribute('onfocus','goToArea" +RiskType+"')");
   //   	    eval(" window.document.forms[0].elements[elementsNum].onfocus = goToArea" +RiskType+"("+window.document.forms[0].elements[elementsNum].name+")");        		
   				//var btn1Obj = document.getElementById("btn1"); 
   			//	window.document.forms[0].elements[elementsNum].attachEvent("onfocus","goToArea" +RiskType);
   			//prompt(" window.document.forms[0].elements[elementsNum].onfocus = goToArea" +RiskType+"('"+RowNo+"')");
   			//	eval(" window.document.forms[0].elements[elementsNum].onfocus = goToArea" +RiskType+"('"+RowNo+"')");      
	//				 eval(" window.document.forms[0].elements[elementsNum].onClick = "+tFunction);  
						eval(" window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);   
      	}
      	
    }     
  } 
}

/**  * ��ʾ��򣬽��ؼ������� **/
function showPosition(l, t, w, h) 
{
  try{
	  //alert(l + " " + t + " " + w + " " + h);
	  top.fraPic.spanPosition.style.display = "";
	  top.fraPic.spanPosition.style.left = l;
	  top.fraPic.spanPosition.style.top = t;
	  top.fraPic.Rect.width = w;
	  top.fraPic.Rect.height = h;  
	  getvalue(w,h);
  }catch(e){
  
  }
}

/** 
 * ���غ��
 **/
function hiddenPosition() {
  top.fraPic.spanPosition.style.display = "none";
}

var hx = 0;
var hy = 0;
var goToLock = false;

var impart = false;

function goToImpart() {
  try {
    goToPic(1); top.fraPic.scrollTo(120, 810);
    impart = false;
  }
  catch(e) {}
}
function custom() {
  try {
    objName = this.name;
    //alert("aaa"+objName);
    
    try { hiddenPosition(); } catch(e) {}
    
    //top.fraPic.centerPic.innerHTML = "try { if (objName == \"\") { goToPic(1); top.fraPic.scrollTo(0, 0); showPosition(192+hx, 83+hy, 285, 115); } } catch(e) {} ";
    path = top.fraPic.centerPic.innerHTML;
    this.value = "try { if (objName == \"" + objName + path.substring(path.indexOf("=") + 4);
    //alert(path);  
  }
  catch(e) {}
}

function debugShow() {
  try {   
    objName = this.name;
    
    try { hiddenPosition(); } catch(e) {}
    
    eval(this.value);
  } catch(e) {}
}

function submitAutoMove(saveflag) {
  try {//alert(161);
     	if(saveflag=="11")//����Ͷ��ҳ��
     	{
         fm.autoMoveFlag.value ="goToArea11";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }   
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
     	}
     	if(saveflag=="121")//�ŵ���һ������ҳ��
     	{
         fm.autoMoveFlag.value ="goToArea121";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }    
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
     	}
     	if(saveflag=="122")//�ŵ��ڶ�������ҳ��
     	{
         fm.autoMoveFlag.value ="goToArea122";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }    
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
     	}
     	if(saveflag=="123")//�ŵ�����������ҳ��
     	{
         fm.autoMoveFlag.value ="goToArea123";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }    
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
     	}              
     	if(saveflag=="13")//��������ҳ��,��ͬ���ֶ�Ӧ��ͬҳ��
     	{
         fm.autoMoveFlag.value ="goToArea13"+RiskType;
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
               fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         
         
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
     	}     	     	
     	if(saveflag=="21")
     	{
         fm.autoMoveFlag.value = "goToArea21";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
        }
     	if(saveflag=="22")
     	{
         fm.autoMoveFlag.value = "goToArea22";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
        }
     	if(saveflag=="23")
     	{
         fm.autoMoveFlag.value = "goToArea23"+RiskType;
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("������ɣ�");
        }                
  } catch(e) {}
}  
/*** �����涯���ƺ���12**/

//function goToArea11() {
//  objName = this.name;
//  if (goToLock) {
//    objName = goToLock;
//    goToLock = false;
//  }
//
//  try { hiddenPosition(); } catch(e) {}
//
//  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 362); showPosition(284+hx, 431+hy, 291, 47); } } catch(e) {} 
//  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(280+hx, 468+hy, 297, 46); } } catch(e) {} 
//  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(295+hx, 517+hy, 266, 59); } } catch(e) {} 
//  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 362); showPosition(284+hx, 431+hy, 291, 47); } } catch(e) {} 
//  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(280+hx, 468+hy, 297, 46); } } catch(e) {} 
//  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(295+hx, 517+hy, 266, 59); } } catch(e) {} 
//  
//
//}
function goToArea11() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
//alert(objName);
  try { hiddenPosition(); } catch(e) {}

  //try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 81); showPosition(712+hx, 175+hy, 512, 167); } } catch(e) {} 
  try { if (objName == "PolAppntDate") { goToPic(1); top.fraPic.scrollTo(218, 1445); showPosition(950+hx, 1614+hy, 237, 50); } } catch(e) {} 
  //try { if (objName == "ManageCom") { goToPic(0); top.fraPic.scrollTo(245, 1528); showPosition(777+hx, 1623+hy, 405, 85); } } catch(e) {} 
  try { if (objName == "BankCode1") { goToPic(0); top.fraPic.scrollTo(9, 1528); showPosition(224+hx, 1586+hy, 289, 81); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(187, 200); showPosition(458+hx, 200+hy, 380, 50); } } catch(e) {} 
  try { if (objName == "SaleChnl") { goToPic(3); top.fraPic.scrollTo(9, 200); showPosition(75+hx, 250+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "CounterCode") { goToPic(0); top.fraPic.scrollTo(175, 1528); showPosition(661+hx, 1580+hy, 232, 82); } } catch(e) {} 
  try { if (objName == "AgentBankCode") { goToPic(0); top.fraPic.scrollTo(245, 1528); showPosition(984+hx, 1581+hy, 214, 80); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(36, 650); showPosition(126+hx, 745+hy, 296, 50); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(36, 650); showPosition(343+hx, 745+hy, 285, 50); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(229, 650); showPosition(566+hx, 745+hy, 383, 50); } } catch(e) {} 
  try { if (objName == "RelationToInsured") { goToPic(0); top.fraPic.scrollTo(229, 650); showPosition(900+hx, 745+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(36, 680); showPosition(137+hx, 785+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(36, 680); showPosition(540+hx, 785+hy, 644, 50); } } catch(e) {} 
  try { if (objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(36, 720); showPosition(110+hx, 825+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "AppntPlace") { goToPic(0); top.fraPic.scrollTo(36, 720); showPosition(460+hx, 825+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(229, 720); showPosition(740+hx, 825+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(79, 760); showPosition(137+hx, 865+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(229, 760); showPosition(850+hx, 865+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "AppntHomeAddress") { goToPic(0); top.fraPic.scrollTo(79, 800); showPosition(137+hx, 905+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "AppntHomeZipCode") { goToPic(0); top.fraPic.scrollTo(229, 800); showPosition(850+hx, 905+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(79, 840); showPosition(137+hx, 945+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "AppntPhone") { goToPic(0); top.fraPic.scrollTo(253, 840); showPosition(450+hx, 945+hy, 291, 50); } } catch(e) {} 
  try { if (objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(300, 840); showPosition(870+hx, 945+hy, 291, 50); } } catch(e) {} 
  try { if (objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(79, 880); showPosition(137+hx, 980+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "AppntWorkType") { goToPic(0); top.fraPic.scrollTo(79, 920); showPosition(137+hx, 1020+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "AppntPluralityType") { goToPic(0); top.fraPic.scrollTo(253, 920); showPosition(450+hx, 1020+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 960); showPosition(840+hx, 1020+hy, 310, 50); } } catch(e) {} 
  //try { if (objName == "AppntOccupationType") { goToPic(0); top.fraPic.scrollTo(253, 277); showPosition(919+hx, 388+hy, 300, 101); } } catch(e) {} 
  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(79, 277); showPosition(130+hx, 405+hy, 612, 120); } } catch(e) {} 
  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(79, 277); showPosition(138+hx, 402+hy, 595, 124); } } catch(e) {} 
  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(79, 277); showPosition(132+hx, 408+hy, 607, 115); } } catch(e) {} 
  
  try { if (objName == "PayMode") { goToPic(2); top.fraPic.scrollTo(79, 300); showPosition(112+hx, 468+hy, 540, 50); } } catch(e) {} 
  try { if (objName == "AppntBankCode") { goToPic(2); top.fraPic.scrollTo(79, 380); showPosition(660+hx, 510+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "AppntAccName") { goToPic(2); top.fraPic.scrollTo(79, 380); showPosition(112+hx, 550+hy, 302, 50); } } catch(e) {} 
  try { if (objName == "AppntBankAccNo") { goToPic(2); top.fraPic.scrollTo(263, 380); showPosition(412+hx, 550+hy, 750, 50); } } catch(e) {} 
  try { if (objName == "SecPayMode") { goToPic(2); top.fraPic.scrollTo(263, 300); showPosition(640+hx, 468+hy, 520, 50); } } catch(e) {} 
  try { if (objName == "AutoPayFlag") { goToPic(2); top.fraPic.scrollTo(79, 420); showPosition(112+hx, 590+hy, 1050, 50); } } catch(e) {} 
  try { if (objName == "OutPayFlag") { goToPic(2); top.fraPic.scrollTo(263, 260); showPosition(640+hx, 428+hy, 520, 50); } } catch(e) {} 
  
  try { if (objName == "Income0") { goToPic(1); top.fraPic.scrollTo(79, 480); showPosition(112+hx, 628+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "IncomeWay0") { goToPic(1); top.fraPic.scrollTo(263, 480); showPosition(490+hx, 628+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "AppntStature") { goToPic(1); top.fraPic.scrollTo(79, 520); showPosition(112+hx, 658+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntAvoirdupois") { goToPic(1); top.fraPic.scrollTo(263, 520); showPosition(490+hx, 658+hy, 500, 50); } } catch(e) {} 
  
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 1043); showPosition(122+hx, 1092+hy, 302, 50); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(34, 1043); showPosition(450+hx, 1092+hy, 282, 50); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(229, 1043); showPosition(767+hx, 1092+hy, 392, 50); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(136+hx, 1132+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(540+hx, 1132+hy, 633, 50); } } catch(e) {} 
  //try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(263, 247); showPosition(911+hx, 312+hy, 307, 130); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(110+hx, 1172+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "InsuredPlace") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(460+hx, 1172+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(229, 1123); showPosition(740+hx, 1172+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(79, 1163); showPosition(137+hx, 1212+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(229, 1163); showPosition(850+hx, 1212+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(79, 1203); showPosition(137+hx, 1252+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "Phone") { goToPic(0); top.fraPic.scrollTo(229, 1203); showPosition(450+hx, 1252+hy, 693, 50); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(79, 1243); showPosition(137+hx, 1287+hy, 1050, 50); } } catch(e) {} 
  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(79, 1283); showPosition(117+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "PluralityType") { goToPic(0); top.fraPic.scrollTo(229, 1283); showPosition(467+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(263, 1283); showPosition(930+hx, 1327+hy, 230, 50); } } catch(e) {} 
  //try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(263, 367); showPosition(916+hx, 481+hy, 302, 130); } } catch(e) {} 
  try { if (objName == "Income") { goToPic(1); top.fraPic.scrollTo(60, 467); showPosition(124+hx, 620+hy, 331, 50); } } catch(e) {} 
  try { if (objName == "IncomeWay") { goToPic(1); top.fraPic.scrollTo(229, 467); showPosition(460+hx, 620+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "Stature") { goToPic(2); top.fraPic.scrollTo(79, 767); showPosition(117+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "Avoirdupois") { goToPic(2); top.fraPic.scrollTo(79, 767); showPosition(117+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(124+hx, 520+hy, 631, 118); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(122+hx, 516+hy, 627, 121); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(131+hx, 514+hy, 644, 119); } } catch(e) {} 
  
  try { if (objName == "Name1") { goToPic(0); top.fraPic.scrollTo(0, 1043); showPosition(122+hx, 1092+hy, 302, 50); } } catch(e) {} 
  try { if (objName == "Sex1") { goToPic(0); top.fraPic.scrollTo(34, 1043); showPosition(450+hx, 1092+hy, 282, 50); } } catch(e) {} 
  try { if (objName == "Birthday1") { goToPic(0); top.fraPic.scrollTo(229, 1043); showPosition(767+hx, 1092+hy, 392, 50); } } catch(e) {} 
  try { if (objName == "IDType1") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(136+hx, 1132+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "IDNo1") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(540+hx, 1132+hy, 633, 50); } } catch(e) {} 
  //try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(263, 247); showPosition(911+hx, 312+hy, 307, 130); } } catch(e) {} 
  try { if (objName == "NativePlace1") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(110+hx, 1172+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "InsuredPlace1") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(460+hx, 1172+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "Marriage1") { goToPic(0); top.fraPic.scrollTo(229, 1123); showPosition(740+hx, 1172+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "PostalAddress1") { goToPic(0); top.fraPic.scrollTo(79, 1163); showPosition(137+hx, 1212+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "ZIPCODE1") { goToPic(0); top.fraPic.scrollTo(229, 1163); showPosition(850+hx, 1212+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "Mobile1") { goToPic(0); top.fraPic.scrollTo(79, 1203); showPosition(137+hx, 1252+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(229, 1203); showPosition(450+hx, 1252+hy, 693, 50); } } catch(e) {} 
  try { if (objName == "GrpName1") { goToPic(0); top.fraPic.scrollTo(79, 1243); showPosition(137+hx, 1287+hy, 1050, 50); } } catch(e) {} 
  try { if (objName == "WorkType1") { goToPic(0); top.fraPic.scrollTo(79, 1283); showPosition(117+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "PluralityType1") { goToPic(0); top.fraPic.scrollTo(229, 1283); showPosition(467+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "OccupationCode1") { goToPic(0); top.fraPic.scrollTo(263, 1283); showPosition(930+hx, 1327+hy, 230, 50); } } catch(e) {} 
  //try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(263, 367); showPosition(916+hx, 481+hy, 302, 130); } } catch(e) {} 
  try { if (objName == "Income1") { goToPic(1); top.fraPic.scrollTo(60, 467); showPosition(124+hx, 620+hy, 331, 50); } } catch(e) {} 
  try { if (objName == "IncomeWay1") { goToPic(1); top.fraPic.scrollTo(229, 467); showPosition(460+hx, 620+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "Stature1") { goToPic(2); top.fraPic.scrollTo(79, 767); showPosition(117+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "Avoirdupois1") { goToPic(2); top.fraPic.scrollTo(79, 767); showPosition(117+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "InsuredProvince1") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(124+hx, 520+hy, 631, 118); } } catch(e) {} 
  try { if (objName == "InsuredCity1") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(122+hx, 516+hy, 627, 121); } } catch(e) {} 
  try { if (objName == "InsuredDistrict1") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(131+hx, 514+hy, 644, 119); } } catch(e) {} 
  
  
  try { if (objName == "RiskCode") { goToPic(2); top.fraPic.scrollTo(94, 100); showPosition(128+hx, 100+hy, 400, 310); } } catch(e) {} 
  //try { if (objName == "insuhealthmpart") { goToPic(2); top.fraPic.scrollTo(97, 103); showPosition(188+hx, 182+hy, -12, 0); } } catch(e) {} 
  //try { if (objName == "insuworkimpart") { goToPic(3); top.fraPic.scrollTo(97, 118); showPosition(420+hx, 223+hy, -126, -8); } } catch(e) {} 
  try { if (objName == "insuworkimpart") { goToPic(1); top.fraPic.scrollTo(153, 291); showPosition(164+hx, 352+hy, 992, 400); } } catch(e) {} 
  try { if (objName == "insuhealthmpart") { goToPic(1); top.fraPic.scrollTo(153, 684); showPosition(164+hx, 725+hy, 993, 720); } } catch(e) {} 
}

/** 
 * �������涯���ƺ���
 **/
function goToArea35() {
//	alert(tObjInstance[0]);
//alert('2');
	//if(tObjName == null ||tObjName == "" )
	//{
  	objName = this.name; 
//	}
	//else
//	{
//		objName = tObjName.name;
//	}
 // var tObjInstance=this;
  //alert("objName:"+objName);
//  var sInstanceName = (tObjInstance.instanceName != null) ? tObjInstance.instanceName : tObjInstance.name;
 // alert(span$PageName$$SpanId$);
  //var mulCount=tObjInstance.mulLineCount;
  //alert("mulLineCount:"+mulCount);
  
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
  //alert("goToArea35: "+objName);
  try { hiddenPosition(); } catch(e) {}
  try { if (objName == "RiskCode" || objName == "RiskGrid1")  { goToPic(0); top.fraPic.scrollTo(20, 600); showPosition(100+hx, 720+hy, 450, 130); } } catch(e) {} 
//������Ϣ  
  try { if (objName == "ManageCom") { goToPic(1); top.fraPic.scrollTo(0, 1400); showPosition(80+hx, 1500+hy, 740, 50); } } catch(e) {}
  try { if (objName == "SaleChnl") { goToPic(1); top.fraPic.scrollTo(300, 1400); showPosition(815+hx, 1505+hy, 385, 50);} } catch(e) {} 
  try { if (objName == "Remark")  { goToPic(0); top.fraPic.scrollTo(50, 880); showPosition(70+hx, 1035+hy, 1101, 105); } } catch(e) {}

//Ͷ������Ϣ  
  try { if (objName == "AppntCustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(155+hx, 305+hy, 220, 45); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(155+hx, 305+hy, 200, 45); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(350+hx, 305+hy, 155, 45); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(200, 200); showPosition(555+hx, 305+hy, 366, 45); } } catch(e) {} 
  try { if (objName == "AppntRelationToInsured") { goToPic(0); top.fraPic.scrollTo(250, 200); showPosition(990+hx, 305+hy, 185, 45); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(175+hx, 350+hy, 500, 45); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(234, 200); showPosition(675+hx, 350+hy, 525, 45); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(165+hx, 375+hy, 550, 42); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(246, 200); showPosition(1030+hx, 388+hy, 180, 40); } } catch(e) {} 
  //try { if (objName == "AppntHomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 300); showPosition(165+hx, 420+hy, 800, 48); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(100, 200); showPosition(690+hx, 388+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "AppntWorkType") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(165+hx, 415+hy, 200, 42); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(246, 200); showPosition(985+hx, 425+hy, 215, 40); } } catch(e) {} 
  try { if (objName == "RelationToInsured") { goToPic(0); top.fraPic.scrollTo(246, 200); showPosition(985+hx, 315+hy, 215, 40); } } catch(e) {} 
  
  
//��������Ϣ
  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(170+hx, 482+hy, 190, 40); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(100, 320); showPosition(365+hx, 482+hy, 160, 40); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(150, 320); showPosition(540+hx, 482+hy, 350, 40); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(82, 420); showPosition(170+hx, 518+hy, 500, 42); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(300, 420); showPosition(640+hx, 518+hy, 560, 42); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(82, 400); showPosition(170+hx, 550+hy, 500, 40); } } catch(e) {} 
  try { if (objName == "HomeZipCode"||objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(246, 400); showPosition(1020+hx, 550+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(120, 400); showPosition(690+hx, 550+hy, 230, 40); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(246, 400); showPosition(990+hx, 580+hy, 210, 40); } } catch(e) {} 
  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(82, 400); showPosition(165+hx, 580+hy, 210, 40); } } catch(e) {} 

  try { if (objName == "Name1") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(170+hx, 482+hy, 190, 40); } } catch(e) {} 
  try { if (objName == "Sex1") { goToPic(0); top.fraPic.scrollTo(100, 320); showPosition(365+hx, 482+hy, 160, 40); } } catch(e) {} 
  try { if (objName == "Birthday1") { goToPic(0); top.fraPic.scrollTo(150, 320); showPosition(540+hx, 482+hy, 350, 40); } } catch(e) {} 
  try { if (objName == "IDType1") { goToPic(0); top.fraPic.scrollTo(82, 420); showPosition(170+hx, 518+hy, 500, 42); } } catch(e) {} 
  try { if (objName == "IDNo1") { goToPic(0); top.fraPic.scrollTo(300, 420); showPosition(640+hx, 518+hy, 560, 42); } } catch(e) {} 
  try { if (objName == "PostalAddress1") { goToPic(0); top.fraPic.scrollTo(82, 400); showPosition(170+hx, 550+hy, 500, 40); } } catch(e) {} 
  try { if (objName == "HomeZipCode1"||objName == "ZIPCODE1") { goToPic(0); top.fraPic.scrollTo(246, 400); showPosition(1020+hx, 550+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "Mobile1") { goToPic(0); top.fraPic.scrollTo(120, 400); showPosition(690+hx, 550+hy, 230, 40); } } catch(e) {} 
  try { if (objName == "OccupationCode1") { goToPic(0); top.fraPic.scrollTo(246, 400); showPosition(990+hx, 580+hy, 210, 40); } } catch(e) {} 
  try { if (objName == "WorkType1") { goToPic(0); top.fraPic.scrollTo(82, 400); showPosition(165+hx, 580+hy, 210, 40); } } catch(e) {} 
  
  
  try { if (objName == "ImpartInsuredGrid1")  { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(170+hx, 482+hy, 190, 40); } } catch(e) {} 
//��������Ϣ
  try { if (objName == "BnfGrid1")  { goToPic(0); top.fraPic.scrollTo(86, 450); showPosition(80+hx, 600+hy, 1100, 110); } } catch(e) {} 
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(86, 450);  } } catch(e) {} 
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(86, 450);  } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(86, 450);  } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(86, 450);   } } catch(e) {} 
  
//���ֵ�multLine
var tMultLineId = this.MultLineId-1; //alert(tMultLineId);
if (objName.indexOf('Grid')!=-1)
{
	//PolGrid.
//alert("&&&"+PolGrid.getRowColData(lastFocusColNo+":");
//alert(fm.all( span$PageName$$SpanId$ ).all('ObjGrid1').value );
//alert(this.MultLineId);
//alert('5');
}
if(tMultLineId<3){
  try { if (objName == "PolGrid3")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(115+hx,730+hy+(40*tMultLineId), 425, 50); } } catch(e) {} 
  try { if (objName == "PolGrid4")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(780+hx,730+hy+(40*tMultLineId), 260, 50); } } catch(e) {} 
  try { if (objName == "PolGrid5")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(780+hx,730+hy+(40*tMultLineId), 260, 50); } } catch(e) {} 
  try { if (objName == "PolGrid7")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(525+hx,730+hy+(40*tMultLineId), 140, 50); } } catch(e) {} 
  try { if (objName == "PolGrid8")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(650+hx,730+hy+(40*tMultLineId), 140, 50); } } catch(e) {} 
  try { if (objName == "PolGrid9")  { goToPic(0); top.fraPic.scrollTo(210, 650); showPosition(1025+hx,730+hy+(40*tMultLineId), 180, 50); } } catch(e) {} 
}
//��֪��multLine
  if((tMultLineId=="0"||tMultLineId=="1")&&objName.indexOf("ImpartGrid")!=-1){
  	try { if (objName == "ImpartGrid4") { goToPic(0); top.fraPic.scrollTo(86, 850); /*showPosition(800+hx,760+hy+(40*tMultLineId), 800, 45);*/ } } catch(e) {} 
  	try { if (objName == "ImpartGrid5") { goToPic(0); top.fraPic.scrollTo(86, 850);  } } catch(e) {} 
  }
  try { if (objName == "ImpartGrid")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(800+hx,760+hy+(40*tMultLineId), 800, 45); } } catch(e) {} 
  try { if (objName == "ImpartGrid")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(780+hx,760+hy+(40*tMultLineId), 260, 45); } } catch(e) {} 
  try { if (objName == "ImpartGrid")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(780+hx,760+hy+(40*tMultLineId), 260, 45); } } catch(e) {} 
  try { if (objName == "ImpartGrid")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(525+hx,760+hy+(40*tMultLineId), 140, 45); } } catch(e) {} 
  try { if (objName == "ImpartGrid")  { goToPic(0); top.fraPic.scrollTo(86, 650); showPosition(650+hx,760+hy+(40*tMultLineId), 140, 45); } } catch(e) {} 
  try { if (objName == "ImpartGrid")  { goToPic(0); top.fraPic.scrollTo(210, 650); showPosition(1025+hx,760+hy+(40*tMultLineId), 180, 45); } } catch(e) {} 


//������Ϣ  
  try { if (objName == "PayLocation") { goToPic(0); top.fraPic.scrollTo(120, 1280); } } catch(e) {} 
  try { if (objName == "AccName" || objName == "AppntAccName") { goToPic(0); top.fraPic.scrollTo(100, 1280); showPosition(205+hx, 1418+hy, 200, 42); } } catch(e) {} 
  try { if (objName == "Mult")  { goToPic(0); top.fraPic.scrollTo(400, 450); showPosition(275+hx, 725+hy, 180, 45); } } catch(e) {}
  try { if (objName == "Password") { goToPic(0); top.fraPic.scrollTo(86, 720); showPosition(75+hx, 850+hy, 850, 50); } } catch(e) {} 
  try { if (objName == "SumPrem")  { goToPic(0); top.fraPic.scrollTo(200, 720); showPosition(920+hx, 850+hy, 200, 50); } } catch(e) {} 
  try { if (objName == "PayIntv")  { goToPic(0); top.fraPic.scrollTo(86, 760); showPosition(120+hx, 880+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "StandbyFlag3")  { goToPic(0); top.fraPic.scrollTo(200, 760); showPosition(520+hx, 880+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "GetYear")  { goToPic(0); top.fraPic.scrollTo(200, 760); showPosition(980+hx, 880+hy, 200, 50); } } catch(e) {} 
  try { if (objName == "BankCode" || objName == "AppntBankCode")  { goToPic(0); top.fraPic.scrollTo(200, 1280); showPosition(510+hx, 1418+hy, 300, 42); } } catch(e) {}
  try { if (objName == "BankAccNo" || objName == "AppntBankAccNo")  { goToPic(0); top.fraPic.scrollTo(400, 1350); showPosition(810+hx, 1418+hy, 400, 42); } } catch(e) {}  
  try { if (objName == "AppSignName") { goToPic(0); top.fraPic.scrollTo(20, 1250); showPosition(75+hx, 1450+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "InsSignName2") { goToPic(0); top.fraPic.scrollTo(200, 1250); showPosition(400+hx, 1450+hy, 420, 60); } } catch(e) {} 
  try { if (objName == "CValiDate" || objName == "PolAppntDate" || objName == "PolApplyDate") { goToPic(0); top.fraPic.scrollTo(300, 1250); showPosition(845+hx, 1450+hy, 330, 60); } } catch(e) {} 
  try { if (objName == "AgentComName") { goToPic(0); top.fraPic.scrollTo(75, 1430); showPosition(75+hx, 1575+hy, 380, 45); } } catch(e) {} 
  try { if (objName == "AgentCom") { goToPic(0); top.fraPic.scrollTo(100, 1430); showPosition(460+hx, 1575+hy, 450, 55); } } catch(e) {} 
  try { if (objName == "Handler") { goToPic(1); top.fraPic.scrollTo(20, 1400); showPosition(120+hx, 1540+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(1); top.fraPic.scrollTo(0, 1400); showPosition(120+hx, 1540+hy, 500, 50); } } catch(e) {}
  try { if (objName == "FirstTrialOperator") { goToPic(1); top.fraPic.scrollTo(100, 1460); showPosition(75+hx, 1600+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "FirstTrialDate") { goToPic(1); top.fraPic.scrollTo(300, 1460); showPosition(75+hx, 1600+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "UWOperator") { goToPic(1); top.fraPic.scrollTo(100, 1500); showPosition(75+hx, 1640+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "UWDate") { goToPic(1); top.fraPic.scrollTo(300, 1500); showPosition(75+hx, 1640+hy, 1150, 50); } } catch(e) {} 
}

function goToArea13() {
  objName = this.name;
  //alert("objName:"+objName);
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "RiskCode") { goToPic(2); top.fraPic.scrollTo(94, 100); showPosition(128+hx, 100+hy, 400, 310); } } catch(e) {} 
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(94, 1347); showPosition(244+hx, 1447+hy, 155, 180); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(94, 1347); showPosition(380+hx, 1447+hy, 155, 180); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(94, 1347); showPosition(490+hx, 1447+hy, 254, 180); } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(94, 1347); showPosition(740+hx, 1447+hy, 155, 180); } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(233, 1347); showPosition(931+hx, 1447+hy, 155, 180); } } catch(e) {} 
  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(233, 1347); showPosition(820+hx, 1447+hy, 177, 180); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(245, 712); showPosition(797+hx, 764+hy, 343, 84); } } catch(e) {} 
  try { if (objName == "GetYear") { goToPic(0); top.fraPic.scrollTo(94, 712); showPosition(543+hx, 769+hy, 184, 82); } } catch(e) {} 
  try { if (objName == "GetDutyKind") { goToPic(0); top.fraPic.scrollTo(0, 712); showPosition(208+hx, 769+hy, 337, 75); } } catch(e) {} 
  try { if (objName == "Mult") { goToPic(0); top.fraPic.scrollTo(0, 527); showPosition(532+hx, 584+hy, 179, 192); } } catch(e) {} 
  try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(0, 527); showPosition(829+hx, 584+hy, 136, 190); } } catch(e) {} 
  //try { if (objName == "SpecGrid3") { goToPic(0); top.fraPic.scrollTo(0, 899); showPosition(196+hx, 945+hy, 1006, 88); } } catch(e) {} 
  try { if (objName == "Amnt") { goToPic(0); top.fraPic.scrollTo(0, 536); showPosition(541+hx, 585+hy, 167, 191); } } catch(e) {} 
  try { if (objName == "InsuYear") { goToPic(0); top.fraPic.scrollTo(0, 583); showPosition(329+hx, 593+hy, 254, 184); } } catch(e) {}   
}


//��ȡ�ؼ���ֵ������ʾ����
function getvalue(w,h)
{
	try
	{
		if(LoadFlag=="4"||LoadFlag=="5")
		{
		top.fraPic.RectCont.style.display ="";	
		top.fraPic.RectCont.width=w;
		top.fraPic.RectCont.height=h;  
		}
		top.fraPic.RectCont.value=eval("fm."+document.activeElement.name+"Name.value"); 
		top.fraPic.RectCont.value=document.activeElement.value+"��"+top.fraPic.RectCont.value; 
	}
	catch(ex)
	{	
		if(LoadFlag=="4"||LoadFlag=="5")
		{
		top.fraPic.RectCont.style.display ="";	
		top.fraPic.RectCont.width=w;
		top.fraPic.RectCont.height=h;  
		} 
		top.fraPic.RectCont.value=document.activeElement.value;
	} 
}

//������
function goToArea61() {
//	alert(tObjInstance[0]);
//alert('2');
	//if(tObjName == null ||tObjName == "" )
	//{
  	objName = this.name; 
//	}
	//else
//	{
//		objName = tObjName.name;
//	}
  //var tObjInstance=this;
 // alert("objName:"+objName);
  //var sInstanceName = (tObjInstance.instanceName != null) ? tObjInstance.instanceName : tObjInstance.name;
  //alert(span$PageName$$SpanId$);
  //var mulCount=tObjInstance.mulLineCount;
  //alert("mulLineCount:"+mulCount);
  
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
  //alert("goToArea61: "+objName);
  try { hiddenPosition(); } catch(e) {}
  try { if (objName == "RiskCode" || objName == "RiskGrid1")  { goToPic(0); top.fraPic.scrollTo(20, 600); showPosition(100+hx, 720+hy, 450, 130); } } catch(e) {} 
//������Ϣ  
  try { if (objName == "AppntAvoirdupois") { goToPic(1); top.fraPic.scrollTo(0, 1240); showPosition(80+hx, 1320+hy, 1101, 50); } } catch(e) {} 
  try { if (objName == "AppntStature")  { goToPic(1); top.fraPic.scrollTo(0, 1240); showPosition(80+hx, 1320+hy, 1101, 50); } } catch(e) {}
  try { if (objName == "Avoirdupois") { goToPic(1); top.fraPic.scrollTo(0, 1240); showPosition(80+hx, 1320+hy, 1101, 50); } } catch(e) {}
  try { if (objName == "Stature") { goToPic(1); top.fraPic.scrollTo(0, 1240); showPosition(80+hx, 1320+hy, 1101, 50); } } catch(e) {}
  try { if (objName == "Income0")  { goToPic(2); top.fraPic.scrollTo(50, 880); showPosition(70+hx, 990+hy, 1101, 50); } } catch(e) {}
  try { if (objName == "IncomeWay0")  { goToPic(2); top.fraPic.scrollTo(50, 880); showPosition(70+hx, 990+hy, 1101, 50); } } catch(e) {}
  try { if (objName == "Income") { goToPic(2); top.fraPic.scrollTo(50, 840); showPosition(70+hx, 950+hy, 1101, 50); } } catch(e) {}
  try { if (objName == "IncomeWay") { goToPic(2); top.fraPic.scrollTo(50, 840); showPosition(70+hx, 950+hy, 1101, 50);} } catch(e) {} 

//Ͷ������Ϣ  
  try { if (objName == "AppntCustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(155+hx, 305+hy, 220, 45); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 765); showPosition(155+hx, 765+hy, 200, 45); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 765); showPosition(350+hx, 765+hy, 210, 45); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(200, 765); showPosition(555+hx, 765+hy, 366, 45); } } catch(e) {} 
  try { if (objName == "RelationToInsured") { goToPic(0); top.fraPic.scrollTo(250, 765); showPosition(910+hx, 765+hy, 300, 45); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(0, 775); showPosition(75+hx, 799+hy, 500, 45); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(234, 775); showPosition(550+hx, 799+hy, 660, 45); } } catch(e) {} 
  try { if (objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(0, 785); showPosition(75+hx, 840+hy, 360, 45); } } catch(e) {} 
  try { if (objName == "AppntRgtAddress"||objName == "AppntPlace") { goToPic(0); top.fraPic.scrollTo(200, 785); showPosition(430+hx, 840+hy, 360, 45); } } catch(e) {} 
  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(300, 785); showPosition(780+hx, 840+hy, 420, 45); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 800); showPosition(75+hx, 880+hy, 780, 42); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(246, 800); showPosition(930+hx, 880+hy, 280, 40); } } catch(e) {} 
  try { if (objName == "AppntHomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 820); showPosition(75+hx, 915+hy, 780, 40); } } catch(e) {} 
  try { if (objName == "AppntHomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 820); showPosition(930+hx, 910+hy, 280, 40); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(0, 840); showPosition(75+hx, 950+hy, 380, 40); } } catch(e) {} 
  try { if (objName == "AppntCompanyPhone"||objName == "AppntPhone") { goToPic(0); top.fraPic.scrollTo(200, 840); showPosition(455+hx, 950+hy, 380, 40); } } catch(e) {} 
  try { if (objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(300, 840); showPosition(835+hx, 950+hy, 370, 40); } } catch(e) {} 
  try { if (objName == "AppntCompanyAddress"||objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(75, 860); showPosition(65+hx, 990+hy, 1150, 40); } } catch(e) {} 
  try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(100, 200); showPosition(690+hx, 388+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "AppntWorkType") { goToPic(0); top.fraPic.scrollTo(0, 880); showPosition(75+hx, 1020+hy, 385, 42); } } catch(e) {} 
  try { if (objName == "AppntPluralityType") { goToPic(0); top.fraPic.scrollTo(200, 880); showPosition(455+hx, 1020+hy, 335, 42); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 880); showPosition(785+hx, 1020+hy, 420, 42); } } catch(e) {} 
  //try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(246, 200); showPosition(985+hx, 425+hy, 215, 40); } } catch(e) {} 
  //try { if (objName == "RelationToInsured") { goToPic(0); top.fraPic.scrollTo(246, 200); showPosition(985+hx, 315+hy, 215, 40); } } catch(e) {} 
  
  
//��������Ϣ
  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 1010); showPosition(75+hx, 1100+hy, 330, 40); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(200, 1010); showPosition(390+hx, 1100+hy, 360, 40); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(300, 1010); showPosition(750+hx, 1100+hy, 450, 40); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 1030); showPosition(75+hx, 1140+hy, 430, 42); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(300, 1030); showPosition(500+hx, 1140+hy, 700, 42); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(0, 1050); showPosition(75+hx, 1175+hy, 365, 42); } } catch(e) {} 
  try { if (objName == "RgtAddress"||objName == "InsuredPlace") { goToPic(0); top.fraPic.scrollTo(200, 1050); showPosition(427+hx, 1175+hy, 355, 42); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(300, 1050); showPosition(775+hx, 1175+hy, 430, 42); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(82, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
  try { if (objName == "ZipCode"||objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
  try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
  try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1245+hy, 570, 40); } } catch(e) {} 
  try { if (objName == "CompanyPhone"||objName == "Phone") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(645+hx, 1245+hy, 570, 40); } } catch(e) {} 
  try { if (objName == "CompanyAddress"||objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(80, 1100); showPosition(75+hx, 1285+hy, 1150, 40); } } catch(e) {} 
  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(120, 400); showPosition(690+hx, 550+hy, 230, 40); } } catch(e) {} 
  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(0, 1120); showPosition(75+hx, 1320+hy, 385, 40); } } catch(e) {} 
  try { if (objName == "PluralityType") { goToPic(0); top.fraPic.scrollTo(200, 1120); showPosition(445+hx, 1320+hy, 355, 40); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 1120); showPosition(785+hx, 1320+hy, 420, 40); } } catch(e) {} 

  try { if (objName == "Name1") { goToPic(0); top.fraPic.scrollTo(0, 1010); showPosition(75+hx, 1100+hy, 330, 40); } } catch(e) {} 
  try { if (objName == "Sex1") { goToPic(0); top.fraPic.scrollTo(200, 1010); showPosition(390+hx, 1100+hy, 360, 40); } } catch(e) {} 
  try { if (objName == "Birthday1") { goToPic(0); top.fraPic.scrollTo(300, 1010); showPosition(750+hx, 1100+hy, 450, 40); } } catch(e) {} 
  try { if (objName == "IDType1") { goToPic(0); top.fraPic.scrollTo(0, 1030); showPosition(75+hx, 1140+hy, 430, 42); } } catch(e) {} 
  try { if (objName == "IDNo1") { goToPic(0); top.fraPic.scrollTo(300, 1030); showPosition(500+hx, 1140+hy, 700, 42); } } catch(e) {} 
  try { if (objName == "NativePlace1") { goToPic(0); top.fraPic.scrollTo(0, 1050); showPosition(75+hx, 1175+hy, 365, 42); } } catch(e) {} 
  try { if (objName == "RgtAddress1"||objName == "InsuredPlace1") { goToPic(0); top.fraPic.scrollTo(200, 1050); showPosition(427+hx, 1175+hy, 355, 42); } } catch(e) {} 
  try { if (objName == "Marriage1") { goToPic(0); top.fraPic.scrollTo(300, 1050); showPosition(775+hx, 1175+hy, 430, 42); } } catch(e) {} 
  try { if (objName == "PostalAddress1") { goToPic(0); top.fraPic.scrollTo(82, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
  try { if (objName == "ZipCode1"||objName == "ZIPCODE1") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
  try { if (objName == "HomeAddress1") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
  try { if (objName == "HomeZipCode1") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
  try { if (objName == "Mobile1") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1245+hy, 570, 40); } } catch(e) {} 
  try { if (objName == "CompanyPhone1"||objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(645+hx, 1245+hy, 570, 40); } } catch(e) {} 
  try { if (objName == "CompanyAddress1"||objName == "GrpName1") { goToPic(0); top.fraPic.scrollTo(80, 1100); showPosition(75+hx, 1285+hy, 1150, 40); } } catch(e) {} 
  try { if (objName == "GrpPhone1") { goToPic(0); top.fraPic.scrollTo(120, 400); showPosition(690+hx, 550+hy, 230, 40); } } catch(e) {} 
  try { if (objName == "WorkType1") { goToPic(0); top.fraPic.scrollTo(0, 1120); showPosition(75+hx, 1320+hy, 385, 40); } } catch(e) {} 
  try { if (objName == "PluralityType1") { goToPic(0); top.fraPic.scrollTo(200, 1120); showPosition(445+hx, 1320+hy, 355, 40); } } catch(e) {} 
  try { if (objName == "OccupationCode1") { goToPic(0); top.fraPic.scrollTo(300, 1120); showPosition(785+hx, 1320+hy, 420, 40); } } catch(e) {} 
  
  
//  try { if (objName == "ImpartInsuredGrid1")  { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(170+hx, 482+hy, 190, 40); } } catch(e) {} 
var tMultLineId = this.MultLineId-1; //alert(tMultLineId);
//��������Ϣ
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(0, 1350); showPosition(208+hx, 1450+hy+(52*tMultLineId), 125, 54); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(75, 1350); showPosition(333+hx, 1450+hy+(52*tMultLineId), 125, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(75, 1350); showPosition(450+hx, 1450+hy+(52*tMultLineId), 350, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(75, 1350); showPosition(790+hx, 1450+hy+(52*tMultLineId), 100, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(75, 1350); showPosition(890+hx, 1450+hy+(52*tMultLineId), 100, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(200, 1350); showPosition(995+hx, 1450+hy+(52*tMultLineId), 100, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid9") { goToPic(0); top.fraPic.scrollTo(200, 1350); showPosition(1100+hx, 1450+hy+(52*tMultLineId), 100, 53);  } } catch(e) {} 
  
//���ֵ�multLine
if (objName.indexOf('Grid')!=-1)
{
	//PolGrid.
//alert("&&&"+PolGrid.getRowColData(lastFocusColNo+":");
//alert(fm.all( span$PageName$$SpanId$ ).all('ObjGrid1').value );
//alert(this.MultLineId);
//alert('5');
}
if(tMultLineId<6){
  try { if (objName == "PolGrid3")  { goToPic(1); top.fraPic.scrollTo(86, 100+(10*tMultLineId)); showPosition(190+hx,150+hy+(36*tMultLineId), 330, 45); } } catch(e) {} 
  try { if (objName == "PolGrid4")  { goToPic(1); top.fraPic.scrollTo(86, 100+(10*tMultLineId)); showPosition(510+hx,150+hy+(36*tMultLineId), 150, 45); } } catch(e) {} 
  try { if (objName == "PolGrid5")  { goToPic(1); top.fraPic.scrollTo(86, 100+(10*tMultLineId)); showPosition(510+hx,150+hy+(36*tMultLineId), 150, 45); } } catch(e) {} 
  try { if (objName == "PolGrid6")  { goToPic(1); top.fraPic.scrollTo(86, 100+(10*tMultLineId)); showPosition(510+hx,150+hy+(36*tMultLineId), 150, 45); } } catch(e) {} 
  try { if (objName == "PolGrid7")  { goToPic(1); top.fraPic.scrollTo(86, 100+(10*tMultLineId)); showPosition(650+hx,150+hy+(36*tMultLineId), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid8")  { goToPic(1); top.fraPic.scrollTo(86, 100+(10*tMultLineId)); showPosition(760+hx,150+hy+(36*tMultLineId), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid9")  { goToPic(1); top.fraPic.scrollTo(210, 100+(10*tMultLineId)); showPosition(880+hx,150+hy+(36*tMultLineId), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid10")  { goToPic(1); top.fraPic.scrollTo(210, 100+(10*tMultLineId)); showPosition(990+hx,150+hy+(36*tMultLineId), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid11")  { goToPic(1); top.fraPic.scrollTo(210, 100+(10*tMultLineId)); showPosition(1100+hx,150+hy+(36*tMultLineId), 110, 45); } } catch(e) {} 
}
if(tMultLineId==8||tMultLineId==9){
  try { if (objName == "PolGrid3")  { goToPic(1); top.fraPic.scrollTo(86, 120+(10*(tMultLineId-2))); showPosition(190+hx,150+hy+(36*(tMultLineId-2)), 330, 45); } } catch(e) {} 
  try { if (objName == "PolGrid4")  { goToPic(1); top.fraPic.scrollTo(86, 120+(10*(tMultLineId-2))); showPosition(510+hx,150+hy+(36*(tMultLineId-2)), 150, 45); } } catch(e) {} 
  try { if (objName == "PolGrid5")  { goToPic(1); top.fraPic.scrollTo(86, 120+(10*(tMultLineId-2))); showPosition(510+hx,150+hy+(36*(tMultLineId-2)), 150, 45); } } catch(e) {} 
  try { if (objName == "PolGrid6")  { goToPic(1); top.fraPic.scrollTo(86, 120+(10*(tMultLineId-2))); showPosition(510+hx,150+hy+(36*(tMultLineId-2)), 150, 45); } } catch(e) {} 
  try { if (objName == "PolGrid7")  { goToPic(1); top.fraPic.scrollTo(86, 120+(10*(tMultLineId-2))); showPosition(650+hx,150+hy+(36*(tMultLineId-2)), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid8")  { goToPic(1); top.fraPic.scrollTo(86, 120+(10*(tMultLineId-2))); showPosition(760+hx,150+hy+(36*(tMultLineId-2)), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid9")  { goToPic(1); top.fraPic.scrollTo(210, 120+(10*(tMultLineId-2))); showPosition(880+hx,150+hy+(36*(tMultLineId-2)), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid10")  { goToPic(1); top.fraPic.scrollTo(210, 120+(10*(tMultLineId-2))); showPosition(990+hx,150+hy+(36*(tMultLineId-2)), 120, 45); } } catch(e) {} 
  try { if (objName == "PolGrid11")  { goToPic(1); top.fraPic.scrollTo(210, 120+(10*(tMultLineId-2))); showPosition(1100+hx,150+hy+(36*(tMultLineId-2)), 110, 45); } } catch(e) {} 
}

  try { if (objName == "Prem") { goToPic(1); top.fraPic.scrollTo(86, 100);} } catch(e) {} 
  try { if (objName == "Mult") { goToPic(1); top.fraPic.scrollTo(86, 100);} } catch(e) {} 

  try { if (objName == "Password") { goToPic(1); top.fraPic.scrollTo(86, 240); showPosition(75+hx, 440+hy, 850, 40); } } catch(e) {} 
  try { if (objName == "SumPrem")  { goToPic(1); top.fraPic.scrollTo(200, 240); showPosition(920+hx, 440+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "ApproveCode")  { goToPic(1); top.fraPic.scrollTo(86, 300); showPosition(75+hx, 475+hy, 850, 40); } } catch(e) {} 
  try { if (objName == "ApproveTime")  { goToPic(1); top.fraPic.scrollTo(200, 300); showPosition(920+hx, 475+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "PayIntv")  { goToPic(1); top.fraPic.scrollTo(86, 320); showPosition(75+hx, 510+hy, 700, 40); } } catch(e) {} 
  try { if (objName == "OutPayFlag")  { goToPic(1); top.fraPic.scrollTo(200, 320); showPosition(775+hx, 510+hy, 400, 40); } } catch(e) {} 
  try { if (objName == "PayMode")  { goToPic(1); top.fraPic.scrollTo(86, 340); showPosition(75+hx, 550+hy, 550, 40); } } catch(e) {} 
  try { if (objName == "PayLocation")  { goToPic(1); top.fraPic.scrollTo(200, 340); showPosition(635+hx, 550+hy, 550, 40); } } catch(e) {} 
  try { if (objName == "BankCode")  { goToPic(1); top.fraPic.scrollTo(200, 420); showPosition(650+hx, 590+hy, 400, 40); } } catch(e) {} 
  try { if (objName == "AccName")  { goToPic(1); top.fraPic.scrollTo(86, 420); showPosition(75+hx, 630+hy, 200, 40); } } catch(e) {} 
  try { if (objName == "BankAccNo")  { goToPic(1); top.fraPic.scrollTo(200, 420); showPosition(320+hx, 630+hy, 880, 40); } } catch(e) {} 
  try { if (objName == "AutoPayFlag")  { goToPic(1); top.fraPic.scrollTo(200, 480); showPosition(420+hx, 680+hy, 400, 40); } } catch(e) {} 
  try { if (objName == "LiveGetMode")  { goToPic(1); top.fraPic.scrollTo(86, 600); showPosition(75+hx, 760+hy, 1150, 40); } } catch(e) {} 
  try { if (objName == "GetYear")  { goToPic(1); top.fraPic.scrollTo(86, 640); showPosition(185+hx, 800+hy, 250, 50); } } catch(e) {} 
  try { if (objName == "GetLimit")  { goToPic(1); top.fraPic.scrollTo(200, 640); showPosition(430+hx, 800+hy, 200, 50); } } catch(e) {} 
  try { if (objName == "StandbyFlag3")  { goToPic(1); top.fraPic.scrollTo(300, 640); showPosition(640+hx, 800+hy, 550, 50); } } catch(e) {} 
  try { if (objName == "BonusGetMode1")  { goToPic(1); top.fraPic.scrollTo(86, 730); showPosition(75+hx, 880+hy, 1150, 40); } } catch(e) {} 
  try { if (objName == "BonusGetMode2")  { goToPic(1); top.fraPic.scrollTo(86, 770); showPosition(75+hx, 910+hy, 1150, 40); } } catch(e) {} 
  try { if (objName == "BonusGetMode3")  { goToPic(1); top.fraPic.scrollTo(86, 810); showPosition(75+hx, 950+hy, 1150, 40); } } catch(e) {} 

//��֪��multLine
  if((tMultLineId<10)){
  	try { if (objName == "ImpartGrid4") { goToPic(1); top.fraPic.scrollTo(86, 1140+(40*tMultLineId)); showPosition(75+hx,1320+hy+(40*tMultLineId), 920, 45); } } catch(e) {} 
  	try { if (objName == "ImpartGrid5") { goToPic(1); top.fraPic.scrollTo(200, 1140+(40*tMultLineId)); showPosition(995+hx,1320+hy+(40*tMultLineId), 100, 45); } } catch(e) {} 
  	try { if (objName == "ImpartGrid6") { goToPic(1); top.fraPic.scrollTo(300, 1140+(40*tMultLineId)); showPosition(1100+hx,1320+hy+(40*tMultLineId), 100, 45); } } catch(e) {} 
  }
  if((tMultLineId>9&&tMultLineId<27)){//alert(tMultLineId);
  	try { if (objName == "ImpartGrid4") { goToPic(2); top.fraPic.scrollTo(86, 90+(40*(tMultLineId-10))); showPosition(75+hx,110+hy+(40*(tMultLineId-10)), 920, 40); } } catch(e) {} 
  	try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 90+(40*(tMultLineId-10))); showPosition(995+hx,110+hy+(40*(tMultLineId-10)), 100, 40); } } catch(e) {} 
  	try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 90+(40*(tMultLineId-10))); showPosition(1100+hx,110+hy+(40*(tMultLineId-10)), 100, 40); } } catch(e) {} 
  }
  if((tMultLineId==27)){//alert(tMultLineId);
  	try { if (objName == "ImpartGrid4") { goToPic(2); top.fraPic.scrollTo(86, 750); showPosition(75+hx,810+hy, 920, 45); } } catch(e) {} 
  	try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 750); showPosition(995+hx,810+hy, 100, 45); } } catch(e) {} 
  	try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 750); showPosition(1100+hx,810+hy, 100, 45); } } catch(e) {} 
  }
  if((tMultLineId>27)){//alert(tMultLineId);
  	try { if (objName == "ImpartGrid4") { goToPic(2); top.fraPic.scrollTo(86, 790+(40*(tMultLineId-28))); showPosition(75+hx,850+hy+(40*(tMultLineId-28)), 920, 45); } } catch(e) {} 
  	try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 790+(40*(tMultLineId-28))); showPosition(995+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  	try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 790+(40*(tMultLineId-28))); showPosition(1100+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  }
  
  try { if (objName == "ImpartRemark")  { goToPic(2); top.fraPic.scrollTo(75, 1000); showPosition(75+hx,1050+hy, 1150, 170); } } catch(e) {} 
  try { if (objName == "AppSignName")  { goToPic(2); top.fraPic.scrollTo(300, 1200); showPosition(830+hx,1300+hy, 350, 120); } } catch(e) {} 
  try { if (objName == "InsSignName2")  { goToPic(2); top.fraPic.scrollTo(300, 1350); showPosition(830+hx,1410+hy, 350, 190); } } catch(e) {} 
  try { if (objName == "PolApplyDate"||objName == "PolAppntDate")  { goToPic(2); top.fraPic.scrollTo(300, 1450); showPosition(900+hx,1620+hy, 300, 45); } } catch(e) {} 
  try { if (objName == "FirstTrialOperator")  { goToPic(3); top.fraPic.scrollTo(75, 1000); showPosition(75+hx,1150+hy, 500, 45); } } catch(e) {} 
  try { if (objName == "FirstTrialDate")  { goToPic(3); top.fraPic.scrollTo(75, 1000); showPosition(580+hx,1150+hy, 200, 45); } } catch(e) {} 
  try { if (objName == "UWOperator")  { goToPic(3); top.fraPic.scrollTo(75, 1000); showPosition(75+hx,1200+hy, 500, 45); } } catch(e) {} 
  try { if (objName == "UWDate")  { goToPic(3); top.fraPic.scrollTo(75, 1000); showPosition(580+hx,1200+hy, 200, 45); } } catch(e) {} 


//������Ϣ  
  try { if (objName == "Handler" ) { goToPic(3); top.fraPic.scrollTo(75, 200); showPosition(75+hx, 220+hy, 330, 60); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(200, 200); showPosition(500+hx, 220+hy, 330, 60); } } catch(e) {} 
  try { if (objName == "SaleChnl" ) { goToPic(3); top.fraPic.scrollTo(75, 250); showPosition(75+hx, 260+hy, 1150, 90); } } catch(e) {} 
  //try { if (objName == "AgentComName")  { goToPic(3); top.fraPic.scrollTo(200, 1280); showPosition(510+hx, 1418+hy, 300, 42); } } catch(e) {}
  //try { if (objName == "AgentCom" )  { goToPic(3); top.fraPic.scrollTo(400, 1350); showPosition(810+hx, 1418+hy, 400, 42); } } catch(e) {}  
  if((tMultLineId<4)){//alert(tMultLineId);
  	try { if (objName == "AgentImpartGrid4") { goToPic(3); top.fraPic.scrollTo(75, 300+(40*tMultLineId)); showPosition(75+hx,440+hy+(50*tMultLineId), 920, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 790+(40*(tMultLineId-28))); showPosition(995+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 790+(40*(tMultLineId-28))); showPosition(1100+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  }
  if((tMultLineId>3)){//alert(tMultLineId);
  	try { if (objName == "AgentImpartGrid4") { goToPic(3); top.fraPic.scrollTo(75, 460+(40*(tMultLineId-3))); showPosition(75+hx,600+hy+(50*(tMultLineId-3)), 920, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 790+(40*(tMultLineId-28))); showPosition(995+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 790+(40*(tMultLineId-28))); showPosition(1100+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  }
  //try { if (objName == "Mult")  { goToPic(0); top.fraPic.scrollTo(400, 450); showPosition(275+hx, 725+hy, 180, 45); } } catch(e) {}
  //try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(120, 600); showPosition(765+hx, 680+hy, 220, 45); } } catch(e) {} 
  //try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(20, 600); showPosition(440+hx, 725+hy, 220, 45); } } catch(e) {} 
}

//Ԥ��   ��ͥ���涯
function goToArea51(){
	objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
  try { hiddenPosition(); } catch(e) {}
  var tMultLineId = this.MultLineId-1; //alert(tMultLineId);
  //alert("objName=="+objName);
  
//˫�ڡ�ɨ��¼��
if(fm.SequenceNo){
//��һ��������Ϣ
	if(fm.SequenceNo.value=="1"){//alert(780);
	  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "Name"||objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(75+hx, 260+hy, 330, 60); } } catch(e) {/*alert("error");*/} 
	  try { if (objName == "Sex"||objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(200, 200); showPosition(390+hx, 260+hy, 360, 60); } } catch(e) {} 
	  try { if (objName == "Birthday"||objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(300, 200); showPosition(750+hx, 260+hy, 450, 60); } } catch(e) {} 
	  try { if (objName == "IDType"||objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(0, 240); showPosition(75+hx, 300+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "IDNo"||objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(300, 240); showPosition(500+hx, 300+hy, 700, 60); } } catch(e) {} 
	  try { if (objName == "NativePlace"||objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(0, 280); showPosition(75+hx, 340+hy, 365, 60); } } catch(e) {} 
	  try { if (objName == "RgtAddress"||objName == "AppntPlace") { goToPic(0); top.fraPic.scrollTo(200, 280); showPosition(427+hx, 340+hy, 355, 60); } } catch(e) {} 
	  try { if (objName == "Marriage"||objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(300, 280); showPosition(775+hx, 340+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "PostalAddress"||objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(82, 320); showPosition(75+hx, 370+hy, 780, 60); } } catch(e) {} 
	  try { if (objName == "ZipCode"||objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(246, 320); showPosition(930+hx, 370+hy, 280, 60); } } catch(e) {} 
	  //try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
	  //try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
	  try { if (objName == "Mobile"||objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(0, 360); showPosition(75+hx, 410+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyPhone"||objName == "AppntPhone"||objName == "Phone") { goToPic(0); top.fraPic.scrollTo(246, 360); showPosition(470+hx, 410+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "EMail"||objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(246, 360); showPosition(850+hx, 410+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyAddress"||objName == "AppntGrpName"||objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(80, 400); showPosition(75+hx, 445+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "GrpPhone"||objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(120, 400); showPosition(690+hx, 550+hy, 230, 60); } } catch(e) {} 
	  try { if (objName == "WorkType"||objName == "AppntWorkType") { goToPic(0); top.fraPic.scrollTo(0, 400); showPosition(475+hx, 445+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "PluralityType"||objName == "AppntPluralityType") { goToPic(0); top.fraPic.scrollTo(200, 400); showPosition(645+hx, 445+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "OccupationCode"||objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 400); showPosition(800+hx, 445+hy, 420, 60); } } catch(e) {} 
	}  
	
	//�ڶ���������Ϣ
	if(fm.SequenceNo.value=="2"){
	  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 640); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 500); showPosition(75+hx, 515+hy, 300, 60); } } catch(e) {} 
	  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(200, 500); showPosition(360+hx, 515+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(300, 500); showPosition(560+hx, 515+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(300, 500); showPosition(850+hx, 515+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 540); showPosition(75+hx, 555+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(300, 540); showPosition(500+hx, 555+hy, 700, 60); } } catch(e) {} 
	  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(0, 580); showPosition(75+hx, 595+hy, 365, 60); } } catch(e) {} 
	  try { if (objName == "RgtAddress") { goToPic(0); top.fraPic.scrollTo(200, 580); showPosition(427+hx, 595+hy, 355, 60); } } catch(e) {} 
	  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(300, 580); showPosition(775+hx, 595+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(82, 520); showPosition(75+hx, 625+hy, 780, 60); } } catch(e) {} 
	  try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 520); showPosition(930+hx, 625+hy, 280, 60); } } catch(e) {} 
	  //try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
	  //try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
	  //try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(0, 560); showPosition(75+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "CompanyPhone") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(470+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(850+hx, 670+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyAddress") { goToPic(0); top.fraPic.scrollTo(80, 520); showPosition(75+hx, 660+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(120, 520); showPosition(690+hx, 810+hy, 230, 60); } } catch(e) {} 
	  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(0, 520); showPosition(475+hx, 660+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "PluralityType") { goToPic(0); top.fraPic.scrollTo(200, 520); showPosition(645+hx, 660+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 520); showPosition(800+hx, 660+hy, 420, 60); } } catch(e) {} 
		
	}
	
	//������������Ϣ
	if(fm.SequenceNo.value=="3"){
	  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 640); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 680); showPosition(75+hx, 720+hy, 300, 60); } } catch(e) {} 
	  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(200, 680); showPosition(360+hx, 720+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(300, 680); showPosition(560+hx, 720+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(300, 680); showPosition(850+hx, 720+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 720); showPosition(75+hx, 755+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(300, 720); showPosition(500+hx, 755+hy, 700, 60); } } catch(e) {} 
	  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(0, 760); showPosition(75+hx, 795+hy, 365, 60); } } catch(e) {} 
	  try { if (objName == "RgtAddress") { goToPic(0); top.fraPic.scrollTo(200, 760); showPosition(427+hx, 795+hy, 355, 60); } } catch(e) {} 
	  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(300, 760); showPosition(775+hx, 795+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(82, 700); showPosition(75+hx, 835+hy, 780, 60); } } catch(e) {} 
	  try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 700); showPosition(930+hx, 835+hy, 280, 60); } } catch(e) {} 
	  //try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
	  //try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
	  //try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(0, 560); showPosition(75+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "CompanyPhone") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(470+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(850+hx, 670+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyAddress") { goToPic(0); top.fraPic.scrollTo(80, 690); showPosition(75+hx, 870+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(120, 690); showPosition(690+hx, 910+hy, 230, 60); } } catch(e) {} 
	  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(0, 690); showPosition(475+hx, 870+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "PluralityType") { goToPic(0); top.fraPic.scrollTo(200, 690); showPosition(645+hx, 870+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 690); showPosition(800+hx, 870+hy, 420, 60); } } catch(e) {} 
			
	}
}//�쳣�������⴦��
else{
	  //��һ������
	  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "Name1"||objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 200); showPosition(75+hx, 260+hy, 330, 60); } } catch(e) {/*alert("error");*/} 
	  try { if (objName == "Sex1"||objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(200, 200); showPosition(390+hx, 260+hy, 360, 60); } } catch(e) {} 
	  try { if (objName == "Birthday1"||objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(300, 200); showPosition(750+hx, 260+hy, 450, 60); } } catch(e) {} 
	  try { if (objName == "IDType1"||objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(0, 240); showPosition(75+hx, 300+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "IDNo1"||objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(300, 240); showPosition(500+hx, 300+hy, 700, 60); } } catch(e) {} 
	  try { if (objName == "NativePlace1"||objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(0, 280); showPosition(75+hx, 340+hy, 365, 60); } } catch(e) {} 
	  try { if (objName == "RgtAddress1"||objName == "AppntPlace") { goToPic(0); top.fraPic.scrollTo(200, 280); showPosition(427+hx, 340+hy, 355, 60); } } catch(e) {} 
	  try { if (objName == "Marriage1"||objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(300, 280); showPosition(775+hx, 340+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "PostalAddress1"||objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(82, 320); showPosition(75+hx, 370+hy, 780, 60); } } catch(e) {} 
	  try { if (objName == "ZipCode1"||objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(246, 320); showPosition(930+hx, 370+hy, 280, 60); } } catch(e) {} 
	  //try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
	  //try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
	  try { if (objName == "Mobile1"||objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(0, 360); showPosition(75+hx, 410+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyPhone1"||objName == "AppntPhone"||objName == "Phone") { goToPic(0); top.fraPic.scrollTo(246, 360); showPosition(470+hx, 410+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "EMail1"||objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(246, 360); showPosition(850+hx, 410+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyAddress1"||objName == "AppntGrpName"||objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(80, 400); showPosition(75+hx, 445+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "GrpPhone1"||objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(120, 400); showPosition(690+hx, 550+hy, 230, 60); } } catch(e) {} 
	  try { if (objName == "WorkType1"||objName == "AppntWorkType") { goToPic(0); top.fraPic.scrollTo(0, 400); showPosition(475+hx, 445+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "PluralityType1"||objName == "AppntPluralityType") { goToPic(0); top.fraPic.scrollTo(200, 400); showPosition(645+hx, 445+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "OccupationCode1"||objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 400); showPosition(800+hx, 445+hy, 420, 60); } } catch(e) {} 
	
	  //�ڶ�������
	  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 640); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "Name2") { goToPic(0); top.fraPic.scrollTo(0, 500); showPosition(75+hx, 515+hy, 300, 60); } } catch(e) {} 
	  try { if (objName == "Sex2") { goToPic(0); top.fraPic.scrollTo(200, 500); showPosition(360+hx, 515+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "Birthday2") { goToPic(0); top.fraPic.scrollTo(300, 500); showPosition(560+hx, 515+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "RelationToAppnt2") { goToPic(0); top.fraPic.scrollTo(300, 500); showPosition(850+hx, 515+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "IDType2") { goToPic(0); top.fraPic.scrollTo(0, 540); showPosition(75+hx, 555+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "IDNo2") { goToPic(0); top.fraPic.scrollTo(300, 540); showPosition(500+hx, 555+hy, 700, 60); } } catch(e) {} 
	  try { if (objName == "NativePlace2") { goToPic(0); top.fraPic.scrollTo(0, 580); showPosition(75+hx, 595+hy, 365, 60); } } catch(e) {} 
	  try { if (objName == "RgtAddress2") { goToPic(0); top.fraPic.scrollTo(200, 580); showPosition(427+hx, 595+hy, 355, 60); } } catch(e) {} 
	  try { if (objName == "Mobile2") { goToPic(0); top.fraPic.scrollTo(300, 580); showPosition(775+hx, 595+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "HomeAddress2") { goToPic(0); top.fraPic.scrollTo(82, 520); showPosition(75+hx, 625+hy, 780, 60); } } catch(e) {} 
	  try { if (objName == "HomeZipCode2") { goToPic(0); top.fraPic.scrollTo(246, 520); showPosition(930+hx, 625+hy, 280, 60); } } catch(e) {} 
	  //try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
	  //try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
	  //try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(0, 560); showPosition(75+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "CompanyPhone") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(470+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(850+hx, 670+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyAddress2") { goToPic(0); top.fraPic.scrollTo(80, 520); showPosition(75+hx, 660+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "GrpPhone2") { goToPic(0); top.fraPic.scrollTo(120, 520); showPosition(690+hx, 810+hy, 230, 60); } } catch(e) {} 
	  try { if (objName == "WorkType2") { goToPic(0); top.fraPic.scrollTo(0, 520); showPosition(475+hx, 660+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "PluralityType2") { goToPic(0); top.fraPic.scrollTo(200, 520); showPosition(645+hx, 660+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "OccupationCode2") { goToPic(0); top.fraPic.scrollTo(300, 520); showPosition(800+hx, 660+hy, 420, 60); } } catch(e) {} 
	  
	  //����������
	  try { if (objName == "CustomerNo") { goToPic(0); top.fraPic.scrollTo(0, 640); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "SamePersonFlag") { goToPic(0); top.fraPic.scrollTo(80, 320); showPosition(165+hx, 482+hy, 180, 40); } } catch(e) {} 
	  try { if (objName == "Name3") { goToPic(0); top.fraPic.scrollTo(0, 680); showPosition(75+hx, 720+hy, 300, 60); } } catch(e) {} 
	  try { if (objName == "Sex3") { goToPic(0); top.fraPic.scrollTo(200, 680); showPosition(360+hx, 720+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "Birthday3") { goToPic(0); top.fraPic.scrollTo(300, 680); showPosition(560+hx, 720+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "RelationToAppnt3") { goToPic(0); top.fraPic.scrollTo(300, 680); showPosition(850+hx, 720+hy, 350, 60); } } catch(e) {} 
	  try { if (objName == "IDType3") { goToPic(0); top.fraPic.scrollTo(0, 720); showPosition(75+hx, 755+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "IDNo3") { goToPic(0); top.fraPic.scrollTo(300, 720); showPosition(500+hx, 755+hy, 700, 60); } } catch(e) {} 
	  try { if (objName == "NativePlace3") { goToPic(0); top.fraPic.scrollTo(0, 760); showPosition(75+hx, 795+hy, 365, 60); } } catch(e) {} 
	  try { if (objName == "RgtAddress3") { goToPic(0); top.fraPic.scrollTo(200, 760); showPosition(427+hx, 795+hy, 355, 60); } } catch(e) {} 
	  try { if (objName == "Mobile3") { goToPic(0); top.fraPic.scrollTo(300, 760); showPosition(775+hx, 795+hy, 430, 60); } } catch(e) {} 
	  try { if (objName == "HomeAddress3") { goToPic(0); top.fraPic.scrollTo(82, 700); showPosition(75+hx, 835+hy, 780, 60); } } catch(e) {} 
	  try { if (objName == "HomeZipCode3") { goToPic(0); top.fraPic.scrollTo(246, 700); showPosition(930+hx, 835+hy, 280, 60); } } catch(e) {} 
	  //try { if (objName == "HomeAddress") { goToPic(0); top.fraPic.scrollTo(0, 1080); showPosition(75+hx, 1215+hy, 780, 40); } } catch(e) {} 
	  //try { if (objName == "HomeZipCode") { goToPic(0); top.fraPic.scrollTo(246, 1080); showPosition(930+hx, 1215+hy, 280, 40); } } catch(e) {} 
	  //try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(0, 560); showPosition(75+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "CompanyPhone") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(470+hx, 670+hy, 400, 60); } } catch(e) {} 
	  //try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(246, 560); showPosition(850+hx, 670+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "CompanyAddress3") { goToPic(0); top.fraPic.scrollTo(80, 690); showPosition(75+hx, 870+hy, 400, 60); } } catch(e) {} 
	  try { if (objName == "GrpPhone3") { goToPic(0); top.fraPic.scrollTo(120, 690); showPosition(690+hx, 910+hy, 230, 60); } } catch(e) {} 
	  try { if (objName == "WorkType3") { goToPic(0); top.fraPic.scrollTo(0, 690); showPosition(475+hx, 870+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "PluralityType3") { goToPic(0); top.fraPic.scrollTo(200, 690); showPosition(645+hx, 870+hy, 200, 60); } } catch(e) {} 
	  try { if (objName == "OccupationCode3") { goToPic(0); top.fraPic.scrollTo(300, 690); showPosition(800+hx, 870+hy, 420, 60); } } catch(e) {} 
	  
}

  
  //������
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(0, 820+(40*tMultLineId)); showPosition(208+hx, 1000+hy+(37*tMultLineId), 125, 54); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(75, 820+(40*tMultLineId)); showPosition(333+hx, 1000+hy+(37*tMultLineId), 125, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(75, 820+(40*tMultLineId)); showPosition(450+hx, 1000+hy+(37*tMultLineId), 350, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(75, 820+(40*tMultLineId)); showPosition(790+hx, 1000+hy+(37*tMultLineId), 100, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(75, 820+(40*tMultLineId)); showPosition(890+hx, 1000+hy+(37*tMultLineId), 100, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(200, 820+(40*tMultLineId)); showPosition(995+hx, 1000+hy+(37*tMultLineId), 100, 53);  } } catch(e) {} 
  try { if (objName == "BnfGrid9") { goToPic(0); top.fraPic.scrollTo(200, 820+(40*tMultLineId)); showPosition(1100+hx, 1000+hy+(37*tMultLineId), 100, 53);  } } catch(e) {} 
  
  //����
  if((tMultLineId<8)){
	  try { if (objName == "PolGrid3")  { goToPic(0); top.fraPic.scrollTo(86, 1300+(25*tMultLineId)); showPosition(190+hx,1415+hy+(36*tMultLineId), 350, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid4")  { goToPic(0); top.fraPic.scrollTo(86, 1300+(25*tMultLineId)); showPosition(510+hx,1415+hy+(36*tMultLineId), 170, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid5")  { goToPic(0); top.fraPic.scrollTo(86, 1300+(25*tMultLineId)); showPosition(510+hx,1415+hy+(36*tMultLineId), 170, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid6")  { goToPic(0); top.fraPic.scrollTo(86, 1300+(25*tMultLineId)); showPosition(510+hx,1415+hy+(36*tMultLineId), 170, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid7")  { goToPic(0); top.fraPic.scrollTo(86, 1300+(25*tMultLineId)); showPosition(680+hx,1415+hy+(36*tMultLineId), 140, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid8")  { goToPic(0); top.fraPic.scrollTo(86, 1300+(25*tMultLineId)); showPosition(780+hx,1415+hy+(36*tMultLineId), 140, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid9")  { goToPic(0); top.fraPic.scrollTo(210, 1300+(25*tMultLineId)); showPosition(900+hx,1415+hy+(36*tMultLineId), 220, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid10")  { goToPic(0); top.fraPic.scrollTo(210, 1300+(25*tMultLineId)); showPosition(1080+hx,1415+hy+(36*tMultLineId), 120, 50); } } catch(e) {} 
	  //try { if (objName == "PolGrid11")  { goToPic(0); top.fraPic.scrollTo(210, 1300+(20*tMultLineId)); showPosition(1230+hx,1415+hy+(36*tMultLineId), 120, 45); } } catch(e) {} 
  }
  
  if((tMultLineId>7&&tMultLineId<11)){
	  try { if (objName == "PolGrid3")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId-8))); showPosition(190+hx,110+hy+(36*(tMultLineId-8)), 330, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid4")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId-8))); showPosition(510+hx,110+hy+(36*(tMultLineId-8)), 170, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid5")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId-8))); showPosition(510+hx,110+hy+(36*(tMultLineId-8)), 170, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid6")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId-8))); showPosition(510+hx,110+hy+(36*(tMultLineId-8)), 170, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid7")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId-8))); showPosition(680+hx,110+hy+(36*(tMultLineId-8)), 140, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid8")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId-8))); showPosition(780+hx,110+hy+(36*(tMultLineId-8)), 140, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid9")  { goToPic(1); top.fraPic.scrollTo(210, 10+(25*(tMultLineId-8))); showPosition(900+hx,110+hy+(36*(tMultLineId-8)), 220, 50); } } catch(e) {} 
	  try { if (objName == "PolGrid10")  { goToPic(1); top.fraPic.scrollTo(210, 10+(25*(tMultLineId-8))); showPosition(1080+hx,110+hy+(36*(tMultLineId-8)), 120, 50); } } catch(e) {} 
  }
  try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(86, 1300);} } catch(e) {} 
  try { if (objName == "Mult") { goToPic(0); top.fraPic.scrollTo(86, 1300);} } catch(e) {}   
  
  try { if (objName == "ReleaseFlag") { goToPic(1); top.fraPic.scrollTo(86, 95); showPosition(310+hx, 215+hy, 150, 50); } } catch(e) {} 
  try { if (objName == "Amnt") { goToPic(1); top.fraPic.scrollTo(210, 95); showPosition(460+hx, 215+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "Prem") { goToPic(1); top.fraPic.scrollTo(300, 95); showPosition(900+hx, 215+hy, 320, 50); } } catch(e) {} 
  try { if (objName == "Password") { goToPic(1); top.fraPic.scrollTo(86, 110); showPosition(75+hx, 250+hy, 900, 50); } } catch(e) {} 
  try { if (objName == "SumPrem")  { goToPic(1); top.fraPic.scrollTo(200, 110); showPosition(970+hx, 250+hy, 200, 50); } } catch(e) {} 
  try { if (objName == "PayIntv")  { goToPic(1); top.fraPic.scrollTo(86, 150); showPosition(75+hx, 290+hy, 700, 50); } } catch(e) {} 
  try { if (objName == "OutPayFlag")  { goToPic(1); top.fraPic.scrollTo(200, 150); showPosition(775+hx, 290+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "PayMode")  { goToPic(1); top.fraPic.scrollTo(86, 190); showPosition(75+hx, 330+hy, 550, 50); } } catch(e) {} 
  //try { if (objName == "PayLocation")  { goToPic(1); top.fraPic.scrollTo(200, 190); showPosition(635+hx, 340+hy, 550, 50); } } catch(e) {} 
  //try { if (objName == "ApproveCode")  { goToPic(1); top.fraPic.scrollTo(86, 150); showPosition(75+hx, 475+hy, 850, 50); } } catch(e) {} 
  //try { if (objName == "ApproveTime")  { goToPic(1); top.fraPic.scrollTo(200, 300); showPosition(920+hx, 475+hy, 200, 50); } } catch(e) {} 
  try { if (objName == "BankCode")  { goToPic(1); top.fraPic.scrollTo(200, 330); showPosition(750+hx, 370+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AccName")  { goToPic(1); top.fraPic.scrollTo(50, 330); showPosition(75+hx, 405+hy, 220, 50); } } catch(e) {} 
  try { if (objName == "BankAccNo")  { goToPic(1); top.fraPic.scrollTo(200, 330); showPosition(320+hx, 405+hy, 880, 50); } } catch(e) {} 
  try { if (objName == "AutoPayFlag")  { goToPic(1); top.fraPic.scrollTo(200, 380); showPosition(550+hx, 445+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "LiveGetMode")  { goToPic(1); top.fraPic.scrollTo(120, 560); showPosition(75+hx, 560+hy, 1150, 300); } } catch(e) {} 
  try { if (objName == "GetYear")  { goToPic(1); top.fraPic.scrollTo(120, 560); showPosition(75+hx, 560+hy, 1150, 300); } } catch(e) {} 
  try { if (objName == "GetLimit")  { goToPic(1); top.fraPic.scrollTo(120, 560); showPosition(75+hx, 560+hy, 1150, 300); } } catch(e) {} 
  try { if (objName == "StandbyFlag3")  { goToPic(1); top.fraPic.scrollTo(120, 560); showPosition(75+hx, 560+hy, 1150, 300); } } catch(e) {} 
  try { if (objName == "BonusGetMode")  { goToPic(1); top.fraPic.scrollTo(120, 560); showPosition(75+hx, 560+hy, 1150, 300); } } catch(e) {} 
  
  //try { if (objName == "ImpartGrid4") { goToPic(1); top.fraPic.scrollTo(86, 1140+(40*tMultLineId)); showPosition(75+hx,1320+hy+(40*tMultLineId), 920, 45); } } catch(e) {} 
  //try { if (objName == "ImpartGrid5") { goToPic(1); top.fraPic.scrollTo(200, 1140+(40*tMultLineId)); showPosition(995+hx,1320+hy+(40*tMultLineId), 100, 45); } } catch(e) {} 
  //try { if (objName == "ImpartGrid6") { goToPic(1); top.fraPic.scrollTo(300, 1140+(40*tMultLineId)); showPosition(1100+hx,1320+hy+(40*tMultLineId), 100, 45); } } catch(e) {} 
  
  
  try { if (objName == "ImpartRemark")  { goToPic(2); top.fraPic.scrollTo(75, 1000); showPosition(75+hx,1050+hy, 1150, 170); } } catch(e) {} 
  try { if (objName == "AppSignName")  { goToPic(2); top.fraPic.scrollTo(100, 1480); showPosition(300+hx,1590+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "InsSignName2")  { goToPic(2); top.fraPic.scrollTo(300, 1480); showPosition(750+hx,1590+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "PolApplyDate")  { goToPic(2); top.fraPic.scrollTo(300, 1465); showPosition(900+hx,1620+hy, 300, 50); } } catch(e) {} 
  
  try { if (objName == "Handler")  { goToPic(3); top.fraPic.scrollTo(75, 500); showPosition(75+hx,610+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "AgentCode")  { goToPic(3); top.fraPic.scrollTo(75, 500); showPosition(470+hx,610+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "SaleChnl" ) { goToPic(3); top.fraPic.scrollTo(75, 550); showPosition(75+hx, 660+hy, 1150, 90); } } catch(e) {} 
  try { if (objName == "AgentComName")  { goToPic(3); top.fraPic.scrollTo(75, 650); showPosition(75+hx,745+hy, 600, 50); } } catch(e) {} 
  try { if (objName == "AgentCom")  { goToPic(3); top.fraPic.scrollTo(250, 650); showPosition(675+hx,745+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "SignAgentName")  { goToPic(3); top.fraPic.scrollTo(75, 1180); showPosition(75+hx,1300+hy, 1150, 120); } } catch(e) {} 
  try { if (objName == "AgentSignDate")  { goToPic(3); top.fraPic.scrollTo(75, 1180); showPosition(75+hx,1300+hy, 1150, 120); } } catch(e) {} 
  try { if (objName == "UWOperator")  { goToPic(3); top.fraPic.scrollTo(75, 1380); showPosition(75+hx,1565+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "UWDate")  { goToPic(3); top.fraPic.scrollTo(75, 1380); showPosition(580+hx,1565+hy, 200, 50); } } catch(e) {} 
  try { if (objName == "FirstTrialOperator")  { goToPic(3); top.fraPic.scrollTo(75, 1380); showPosition(75+hx,1515+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "FirstTrialDate")  { goToPic(3); top.fraPic.scrollTo(75, 1380); showPosition(580+hx,1515+hy, 200, 50); } } catch(e) {} 
  
  try { if (objName == "AppntAvoirdupois") { goToPic(1); top.fraPic.scrollTo(0, 800); showPosition(80+hx, 930+hy, 1101, 80); } } catch(e) {} 
  try { if (objName == "AppntStature")  { goToPic(1); top.fraPic.scrollTo(0, 800); showPosition(80+hx, 930+hy, 1101, 80); } } catch(e) {}
  try { if (objName == "Avoirdupois") { goToPic(1); top.fraPic.scrollTo(0, 800); showPosition(80+hx, 930+hy, 1101, 80); } } catch(e) {}
  try { if (objName == "Stature") { goToPic(1); top.fraPic.scrollTo(0, 800); showPosition(80+hx, 930+hy, 1101, 80); } } catch(e) {}
  try { if (objName == "Income0")  { goToPic(2); top.fraPic.scrollTo(50, 700); showPosition(70+hx, 770+hy, 1101, 80); } } catch(e) {}
  try { if (objName == "IncomeWay0")  { goToPic(2); top.fraPic.scrollTo(50, 700); showPosition(70+hx, 770+hy, 1101, 80); } } catch(e) {}
  try { if (objName == "Income") { goToPic(2); top.fraPic.scrollTo(50, 700); showPosition(70+hx, 770+hy, 1101, 80); } } catch(e) {}
  try { if (objName == "IncomeWay") { goToPic(2); top.fraPic.scrollTo(50, 700); showPosition(70+hx, 770+hy, 1101, 80);} } catch(e) {} 
  
  //ҵ��Ա��֪
  if((tMultLineId<4)){
  	try { if (objName == "AgentImpartGrid4") { goToPic(3); top.fraPic.scrollTo(120, 750+(40*tMultLineId)); showPosition(75+hx,885+hy+(50*tMultLineId), 1150, 45); } } catch(e) {} 
  }
  if((tMultLineId>3)){
  	try { if (objName == "AgentImpartGrid4") { goToPic(3); top.fraPic.scrollTo(120, 900+(40*(tMultLineId-3))); showPosition(75+hx,1050+hy+(50*(tMultLineId-3)), 1150, 45); } } catch(e) {} 
  }
}

//  ����Ͷ�����涯
function goToArea16(){
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
//alert(objName);
  try { hiddenPosition(); } catch(e) {}
  //��������Ϣ
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 280); showPosition(155+hx, 290+hy, 200, 60); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 280); showPosition(350+hx, 290+hy, 210, 60); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(200, 280); showPosition(555+hx, 290+hy, 366, 60); } } catch(e) {} 
  try { if (objName == "RelationToInsured") { goToPic(0); top.fraPic.scrollTo(250, 280); showPosition(910+hx, 290+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(80+hx, 330+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(250, 320); showPosition(600+hx, 330+hy, 600, 60); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 360); showPosition(75+hx, 370+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(200, 360); showPosition(600+hx, 370+hy, 380, 60); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(300, 360); showPosition(900+hx, 370+hy, 300, 60); } } catch(e) {} 

  //��������Ϣ
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 400); showPosition(155+hx, 410+hy, 200, 60); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(0, 400); showPosition(350+hx, 410+hy, 210, 60); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(200, 400); showPosition(555+hx, 410+hy, 366, 60); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(250, 400); showPosition(910+hx, 410+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 440); showPosition(80+hx, 450+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(250, 440); showPosition(600+hx, 450+hy, 600, 60); } } catch(e) {} 
  try { if (objName == "CompanyAddress") { goToPic(0); top.fraPic.scrollTo(0, 480); showPosition(75+hx, 490+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(200, 480); showPosition(600+hx, 490+hy, 380, 60); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 480); showPosition(900+hx, 490+hy, 300, 60); } } catch(e) {} 
  
  try { if (objName == "Name1") { goToPic(0); top.fraPic.scrollTo(0, 400); showPosition(155+hx, 410+hy, 200, 60); } } catch(e) {} 
  try { if (objName == "Sex1") { goToPic(0); top.fraPic.scrollTo(0, 400); showPosition(350+hx, 410+hy, 210, 60); } } catch(e) {} 
  try { if (objName == "Birthday1") { goToPic(0); top.fraPic.scrollTo(200, 400); showPosition(555+hx, 410+hy, 366, 60); } } catch(e) {} 
  try { if (objName == "Mobile1") { goToPic(0); top.fraPic.scrollTo(250, 400); showPosition(910+hx, 410+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "IDType1") { goToPic(0); top.fraPic.scrollTo(0, 440); showPosition(80+hx, 450+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "IDNo1") { goToPic(0); top.fraPic.scrollTo(250, 440); showPosition(600+hx, 450+hy, 600, 60); } } catch(e) {} 
  try { if (objName == "CompanyAddress1") { goToPic(0); top.fraPic.scrollTo(0, 480); showPosition(75+hx, 490+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "WorkType1") { goToPic(0); top.fraPic.scrollTo(200, 480); showPosition(600+hx, 490+hy, 380, 60); } } catch(e) {} 
  try { if (objName == "OccupationCode1") { goToPic(0); top.fraPic.scrollTo(300, 480); showPosition(900+hx, 490+hy, 300, 60); } } catch(e) {} 
  
  
  
  //��������Ϣ	
  try { if (objName == "BnfGrid1") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid9") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid10") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 
  try { if (objName == "BnfGrid11") { goToPic(0); top.fraPic.scrollTo(110, 470); showPosition(90+hx, 520+hy, 1100, 130); } } catch(e) {} 

  //������Ϣ  
  try { if (objName == "PolGrid3") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(155+hx, 640+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "PolGrid5") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(600+hx, 640+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "PolGrid9") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(155+hx, 670+hy, 300, 60); } } catch(e) {} 
  //try { if (objName == "Amnt") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(155+hx, 670+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(155+hx, 670+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "Mult") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(600+hx, 640+hy, 300, 60); } } catch(e) {} 



  //Ͷ������
  try { if (objName == "Password") { goToPic(0); top.fraPic.scrollTo(300, 510); showPosition(600+hx, 670+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "SumPrem") { goToPic(0); top.fraPic.scrollTo(110, 510); showPosition(155+hx, 670+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "AutoPayFlag"||objName == "RnewFlag") { goToPic(0); top.fraPic.scrollTo(110, 550); showPosition(155+hx, 710+hy, 300, 60); } } catch(e) {} 

  var tMultLineId = this.MultLineId-1; //alert(tMultLineId);
  
  //��֪��MultiLine�涯
  try { if (objName == "ImpartGrid4") { goToPic(0); top.fraPic.scrollTo(86, 600+(35*tMultLineId)); showPosition(75+hx,760+hy+(30*tMultLineId), 920, 50); } } catch(e) {} 
  try { if (objName == "ImpartGrid6") { goToPic(0); top.fraPic.scrollTo(300, 600+(35*tMultLineId)); showPosition(1035+hx,760+hy+(30*tMultLineId), 110, 50); } } catch(e) {} 
  try { if (objName == "ImpartRemark") { goToPic(0); top.fraPic.scrollTo(110, 900); showPosition(90+hx, 1030+hy, 1100, 130); } } catch(e) {} 
  
  //Ͷ���ˡ���������������Ȩ
  try { if (objName == "AccName") { goToPic(0); top.fraPic.scrollTo(110, 1410); showPosition(200+hx, 1520+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "BankCode") { goToPic(0); top.fraPic.scrollTo(200, 1410); showPosition(800+hx, 1520+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "BankAccNo") { goToPic(0); top.fraPic.scrollTo(110, 1450); showPosition(160+hx, 1560+hy, 750, 60); } } catch(e) {} 
  try { if (objName == "AppSignName") { goToPic(0); top.fraPic.scrollTo(110, 1490); showPosition(190+hx, 1600+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "InsSignName2") { goToPic(0); top.fraPic.scrollTo(200, 1490); showPosition(500+hx, 1600+hy, 300, 60); } } catch(e) {} 
  try { if (objName == "PolApplyDate"||objName == "PolAppntDate") { goToPic(0); top.fraPic.scrollTo(300, 1490); showPosition(850+hx, 1600+hy, 320, 60); } } catch(e) {} 
  
  //ҵ��Ա��д��
  try { if (objName == "Handler") { goToPic(1); top.fraPic.scrollTo(110, 700); showPosition(110+hx, 820+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(1); top.fraPic.scrollTo(300, 700); showPosition(430+hx, 820+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "AgentComName") { goToPic(1); top.fraPic.scrollTo(110, 740); showPosition(110+hx, 860+hy, 400, 60); } } catch(e) {} 
  try { if (objName == "AgentCom") { goToPic(1); top.fraPic.scrollTo(300, 740); showPosition(550+hx, 860+hy, 500, 60); } } catch(e) {} 
  try { if (objName == "Lang") { goToPic(1); top.fraPic.scrollTo(110, 780); showPosition(110+hx, 900+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "Currency") { goToPic(1); top.fraPic.scrollTo(300, 780); showPosition(430+hx, 900+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "SignAgentName") { goToPic(1); top.fraPic.scrollTo(300, 900); showPosition(700+hx, 1020+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "AgentSignDate") { goToPic(1); top.fraPic.scrollTo(300, 900); showPosition(1000+hx, 1020+hy, 200, 60); } } catch(e) {} 
  try { if (objName == "SaleChnl") { goToPic(1); top.fraPic.scrollTo(110, 960); showPosition(90+hx, 1080+hy, 1100, 100); } } catch(e) {} 
  try { if (objName == "FirstTrialOperator") { goToPic(1); top.fraPic.scrollTo(110, 1100); showPosition(90+hx, 1190+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "FirstTrialDate") { goToPic(1); top.fraPic.scrollTo(300, 1100); showPosition(500+hx, 1190+hy, 320, 60); } } catch(e) {} 
  try { if (objName == "UWOperator") { goToPic(1); top.fraPic.scrollTo(110, 1100); showPosition(90+hx, 1230+hy, 400, 60); } } catch(e) {} 
  try { if (objName == "UWDate") { goToPic(1); top.fraPic.scrollTo(300, 1100); showPosition(500+hx, 1230+hy, 320, 60); } } catch(e) {} 
  
  //
  //if(fm.IFDS){
  //	try { if (objName == "RiskCode") { goToPic(2); top.fraPic.scrollTo(94, 100); showPosition(128+hx, 100+hy, 400, 310); } } catch(e) {} 
  //}
}

function goToArea21(){
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
//alert(objName);
  try { hiddenPosition(); } catch(e) {}

  //try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 81); showPosition(712+hx, 175+hy, 512, 167); } } catch(e) {} 
  try { if (objName == "PolAppntDate"||objName == "PolApplyDate") { goToPic(2); top.fraPic.scrollTo(218, 1445); showPosition(950+hx, 1614+hy, 237, 50); } } catch(e) {} 
  try { if (objName == "ManageCom") { goToPic(0); top.fraPic.scrollTo(245, 1528); showPosition(777+hx, 1623+hy, 405, 85); } } catch(e) {} 
  try { if (objName == "BankCode1") { goToPic(0); top.fraPic.scrollTo(9, 1528); showPosition(224+hx, 1586+hy, 289, 81); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(187, 120); showPosition(458+hx, 120+hy, 380, 50); } } catch(e) {} 
  try { if (objName == "SaleChnl") { goToPic(3); top.fraPic.scrollTo(9, 920); showPosition(75+hx, 1060+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "CounterCode") { goToPic(0); top.fraPic.scrollTo(175, 1528); showPosition(661+hx, 1580+hy, 232, 82); } } catch(e) {} 
  try { if (objName == "AgentBankCode") { goToPic(0); top.fraPic.scrollTo(245, 1528); showPosition(984+hx, 1581+hy, 214, 80); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(36, 650); showPosition(126+hx, 745+hy, 296, 50); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(36, 650); showPosition(343+hx, 745+hy, 285, 50); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(229, 650); showPosition(566+hx, 745+hy, 383, 50); } } catch(e) {} 
  try { if (objName == "RelationToInsured") { goToPic(0); top.fraPic.scrollTo(229, 650); showPosition(900+hx, 745+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(36, 680); showPosition(137+hx, 785+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(36, 680); showPosition(540+hx, 785+hy, 644, 50); } } catch(e) {} 
  try { if (objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(36, 720); showPosition(110+hx, 825+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "AppntPlace"||objName == "AppntRgtAddress") { goToPic(0); top.fraPic.scrollTo(36, 720); showPosition(460+hx, 825+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(229, 720); showPosition(740+hx, 825+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(79, 760); showPosition(137+hx, 865+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(229, 760); showPosition(850+hx, 865+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "AppntHomeAddress") { goToPic(0); top.fraPic.scrollTo(79, 800); showPosition(137+hx, 905+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "AppntHomeZipCode") { goToPic(0); top.fraPic.scrollTo(229, 800); showPosition(850+hx, 905+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(79, 840); showPosition(137+hx, 945+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "AppntPhone"||objName == "AppntCompanyPhone") { goToPic(0); top.fraPic.scrollTo(253, 840); showPosition(450+hx, 945+hy, 291, 50); } } catch(e) {} 
  try { if (objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(300, 840); showPosition(870+hx, 945+hy, 291, 50); } } catch(e) {} 
  try { if (objName == "AppntGrpName"||objName == "AppntCompanyAddress") { goToPic(0); top.fraPic.scrollTo(79, 880); showPosition(137+hx, 980+hy, 1150, 50); } } catch(e) {} 
  try { if (objName == "AppntWorkType") { goToPic(0); top.fraPic.scrollTo(79, 920); showPosition(137+hx, 1020+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "AppntPluralityType") { goToPic(0); top.fraPic.scrollTo(253, 920); showPosition(450+hx, 1020+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(300, 960); showPosition(840+hx, 1020+hy, 310, 50); } } catch(e) {} 
  //try { if (objName == "AppntOccupationType") { goToPic(0); top.fraPic.scrollTo(253, 277); showPosition(919+hx, 388+hy, 300, 101); } } catch(e) {} 
  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(79, 277); showPosition(130+hx, 405+hy, 612, 120); } } catch(e) {} 
  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(79, 277); showPosition(138+hx, 402+hy, 595, 124); } } catch(e) {} 
  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(79, 277); showPosition(132+hx, 408+hy, 607, 115); } } catch(e) {} 
  
  try { if (objName == "PayMode") { goToPic(1); top.fraPic.scrollTo(79, 300); showPosition(112+hx, 458+hy, 540, 55); } } catch(e) {} 
  try { if (objName == "PayLocation") { goToPic(1); top.fraPic.scrollTo(300, 300); showPosition(650+hx, 458+hy, 540, 55); } } catch(e) {} 
  try { if (objName == "BankCode") { goToPic(1); top.fraPic.scrollTo(300, 340); showPosition(650+hx, 498+hy, 540, 55); } } catch(e) {} 
  try { if (objName == "AccName") { goToPic(1); top.fraPic.scrollTo(75, 380); showPosition(112+hx, 538+hy, 300, 55); } } catch(e) {} 
  try { if (objName == "BankAccNo") { goToPic(1); top.fraPic.scrollTo(300, 380); showPosition(412+hx, 538+hy, 800, 55); } } catch(e) {} 
  try { if (objName == "AppntBankCode") { goToPic(1); top.fraPic.scrollTo(79, 380); showPosition(660+hx, 510+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "AppntAccName") { goToPic(1); top.fraPic.scrollTo(79, 380); showPosition(112+hx, 550+hy, 302, 50); } } catch(e) {} 
  try { if (objName == "AppntBankAccNo") { goToPic(1); top.fraPic.scrollTo(263, 380); showPosition(412+hx, 550+hy, 750, 50); } } catch(e) {} 
  try { if (objName == "SecPayMode") { goToPic(1); top.fraPic.scrollTo(263, 300); showPosition(640+hx, 468+hy, 520, 50); } } catch(e) {} 
  try { if (objName == "AutoPayFlag") { goToPic(1); top.fraPic.scrollTo(79, 420); showPosition(112+hx, 590+hy, 1050, 50); } } catch(e) {} 
  try { if (objName == "OutPayFlag") { goToPic(1); top.fraPic.scrollTo(263, 260); showPosition(640+hx, 418+hy, 520, 55); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(1); top.fraPic.scrollTo(75, 260); showPosition(112+hx, 418+hy, 600, 55); } } catch(e) {} 
  try { if (objName == "LiveGetMode") { goToPic(1); top.fraPic.scrollTo(75, 500); showPosition(112+hx, 658+hy, 1000, 55); } } catch(e) {} 
  try { if (objName == "BonusGetMode") { goToPic(1); top.fraPic.scrollTo(75, 600); showPosition(112+hx, 778+hy, 1000, 55); } } catch(e) {} 
  try { if (objName == "GetYear") { goToPic(1); top.fraPic.scrollTo(75, 530); showPosition(112+hx, 688+hy, 380, 55); } } catch(e) {} 
  try { if (objName == "GetLimit") { goToPic(1); top.fraPic.scrollTo(75, 530); showPosition(500+hx, 688+hy, 150, 55); } } catch(e) {} 
  try { if (objName == "StandbyFlag3") { goToPic(1); top.fraPic.scrollTo(300, 500); showPosition(650+hx, 688+hy, 600, 55); } } catch(e) {} 
  
  try { if (objName == "Income0") { goToPic(2); top.fraPic.scrollTo(79, 480); showPosition(112+hx, 668+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "IncomeWay0") { goToPic(2); top.fraPic.scrollTo(263, 480); showPosition(490+hx, 668+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "Income") { goToPic(2); top.fraPic.scrollTo(60, 467); showPosition(124+hx, 620+hy, 331, 50); } } catch(e) {} 
  try { if (objName == "IncomeWay") { goToPic(2); top.fraPic.scrollTo(229, 467); showPosition(460+hx, 620+hy, 500, 50); } } catch(e) {} 
  try { if (objName == "AppntStature") { goToPic(1); top.fraPic.scrollTo(79, 767); showPosition(112+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "AppntAvoirdupois") { goToPic(1); top.fraPic.scrollTo(263, 767); showPosition(490+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "Stature") { goToPic(1); top.fraPic.scrollTo(79, 767); showPosition(117+hx, 900+hy, 800, 50); } } catch(e) {} 
  try { if (objName == "Avoirdupois") { goToPic(1); top.fraPic.scrollTo(79, 767); showPosition(117+hx, 900+hy, 800, 50); } } catch(e) {} 
  
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 1043); showPosition(122+hx, 1092+hy, 302, 50); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(34, 1043); showPosition(450+hx, 1092+hy, 282, 50); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(229, 1043); showPosition(767+hx, 1092+hy, 392, 50); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(136+hx, 1132+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(540+hx, 1132+hy, 633, 50); } } catch(e) {} 
  //try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(263, 247); showPosition(911+hx, 312+hy, 307, 130); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(110+hx, 1172+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "InsuredPlace"||objName == "RgtAddress") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(460+hx, 1172+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(229, 1123); showPosition(740+hx, 1172+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(79, 1163); showPosition(137+hx, 1212+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "ZIPCODE"||objName == "ZipCode") { goToPic(0); top.fraPic.scrollTo(229, 1163); showPosition(850+hx, 1212+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(79, 1203); showPosition(137+hx, 1252+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "Phone"||objName == "CompanyPhone") { goToPic(0); top.fraPic.scrollTo(229, 1203); showPosition(450+hx, 1252+hy, 693, 50); } } catch(e) {} 
  try { if (objName == "GrpName"||objName == "CompanyAddress") { goToPic(0); top.fraPic.scrollTo(79, 1243); showPosition(137+hx, 1287+hy, 1050, 50); } } catch(e) {} 
  try { if (objName == "WorkType") { goToPic(0); top.fraPic.scrollTo(79, 1283); showPosition(117+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "PluralityType") { goToPic(0); top.fraPic.scrollTo(229, 1283); showPosition(467+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(263, 1283); showPosition(930+hx, 1327+hy, 230, 50); } } catch(e) {} 
  //try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(263, 367); showPosition(916+hx, 481+hy, 302, 130); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(124+hx, 520+hy, 631, 118); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(122+hx, 516+hy, 627, 121); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(131+hx, 514+hy, 644, 119); } } catch(e) {} 
  
  try { if (objName == "Name1") { goToPic(0); top.fraPic.scrollTo(0, 1043); showPosition(122+hx, 1092+hy, 302, 50); } } catch(e) {} 
  try { if (objName == "Sex1") { goToPic(0); top.fraPic.scrollTo(34, 1043); showPosition(450+hx, 1092+hy, 282, 50); } } catch(e) {} 
  try { if (objName == "Birthday1") { goToPic(0); top.fraPic.scrollTo(229, 1043); showPosition(767+hx, 1092+hy, 392, 50); } } catch(e) {} 
  try { if (objName == "IDType1") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(136+hx, 1132+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "IDNo1") { goToPic(0); top.fraPic.scrollTo(34, 1083); showPosition(540+hx, 1132+hy, 633, 50); } } catch(e) {} 
  //try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(263, 247); showPosition(911+hx, 312+hy, 307, 130); } } catch(e) {} 
  try { if (objName == "NativePlace1") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(110+hx, 1172+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "InsuredPlace1"||objName == "RgtAddress1") { goToPic(0); top.fraPic.scrollTo(36, 1123); showPosition(460+hx, 1172+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "Marriage1") { goToPic(0); top.fraPic.scrollTo(229, 1123); showPosition(740+hx, 1172+hy, 400, 50); } } catch(e) {} 
  try { if (objName == "PostalAddress1") { goToPic(0); top.fraPic.scrollTo(79, 1163); showPosition(137+hx, 1212+hy, 602, 50); } } catch(e) {} 
  try { if (objName == "ZIPCODE1"||objName == "ZipCode1") { goToPic(0); top.fraPic.scrollTo(229, 1163); showPosition(850+hx, 1212+hy, 313, 50); } } catch(e) {} 
  try { if (objName == "Mobile1") { goToPic(0); top.fraPic.scrollTo(79, 1203); showPosition(137+hx, 1252+hy, 300, 50); } } catch(e) {} 
  try { if (objName == "Phone1"||objName == "CompanyPhone1") { goToPic(0); top.fraPic.scrollTo(229, 1203); showPosition(450+hx, 1252+hy, 693, 50); } } catch(e) {} 
  try { if (objName == "GrpName1"||objName == "CompanyAddress1") { goToPic(0); top.fraPic.scrollTo(79, 1243); showPosition(137+hx, 1287+hy, 1050, 50); } } catch(e) {} 
  try { if (objName == "WorkType1") { goToPic(0); top.fraPic.scrollTo(79, 1283); showPosition(117+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "PluralityType1") { goToPic(0); top.fraPic.scrollTo(229, 1283); showPosition(467+hx, 1327+hy, 350, 50); } } catch(e) {} 
  try { if (objName == "OccupationCode1") { goToPic(0); top.fraPic.scrollTo(263, 1283); showPosition(930+hx, 1327+hy, 230, 50); } } catch(e) {} 
  //try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(263, 367); showPosition(916+hx, 481+hy, 302, 130); } } catch(e) {} 
  try { if (objName == "InsuredProvince1") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(124+hx, 520+hy, 631, 118); } } catch(e) {} 
  try { if (objName == "InsuredCity1") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(122+hx, 516+hy, 627, 121); } } catch(e) {} 
  try { if (objName == "InsuredDistrict1") { goToPic(0); top.fraPic.scrollTo(60, 367); showPosition(131+hx, 514+hy, 644, 119); } } catch(e) {} 
  
  
  if(fm.IFDS){
  try { if (objName == "RiskCode") { goToPic(2); top.fraPic.scrollTo(94, 100); showPosition(128+hx, 100+hy, 400, 310); } } catch(e) {} 
  }
  //try { if (objName == "insuhealthmpart") { goToPic(2); top.fraPic.scrollTo(97, 103); showPosition(188+hx, 182+hy, -12, 0); } } catch(e) {} 
  //try { if (objName == "insuworkimpart") { goToPic(3); top.fraPic.scrollTo(97, 118); showPosition(420+hx, 223+hy, -126, -8); } } catch(e) {} 
  try { if (objName == "insuworkimpart") { goToPic(1); top.fraPic.scrollTo(153, 291); showPosition(164+hx, 352+hy, 992, 400); } } catch(e) {} 
  try { if (objName == "insuhealthmpart") { goToPic(1); top.fraPic.scrollTo(153, 684); showPosition(164+hx, 725+hy, 993, 720); } } catch(e) {} 

  var tMultLineId = this.MultLineId-1; //alert(tMultLineId);
  try { if (objName == "BnfGrid1") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid9") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid10") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 
  try { if (objName == "BnfGrid11") { goToPic(0); top.fraPic.scrollTo(110, 1400+(30*tMultLineId)); showPosition(90+hx, 1455+(40*tMultLineId)+hy, 1100, 55); } } catch(e) {} 


  try { if (objName == "PolGrid3")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid4")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid5")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid6")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid7")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid8")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid9")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 
  try { if (objName == "PolGrid10")  { goToPic(1); top.fraPic.scrollTo(86, 10+(25*(tMultLineId))); showPosition(90+hx,135+hy+(40*(tMultLineId)), 1100, 55); } } catch(e) {} 

  try { if (objName == "Password") { goToPic(1); top.fraPic.scrollTo(86, 230); showPosition(90+hx, 375+hy, 800, 55); } } catch(e) {} 
  try { if (objName == "SumPrem") { goToPic(1); top.fraPic.scrollTo(300, 230); showPosition(900+hx, 375+hy, 300, 55); } } catch(e) {} 
  try { if (objName == "ImpartRemark") { goToPic(2); top.fraPic.scrollTo(110, 700); showPosition(90+hx, 740+hy, 1100, 300); } } catch(e) {} 
  
  try { if (objName == "AppSignName") { goToPic(2); top.fraPic.scrollTo(110, 1600); showPosition(90+hx, 1600+hy, 500, 55); } } catch(e) {} 
  try { if (objName == "InsSignName2") { goToPic(2); top.fraPic.scrollTo(110, 1600); showPosition(590+hx, 1600+hy, 500, 55); } } catch(e) {} 
  try { if (objName == "Handler") { goToPic(3); top.fraPic.scrollTo(110, 920); showPosition(90+hx, 1020+hy, 300, 55); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(200, 920); showPosition(390+hx, 1020+hy, 300, 55); } } catch(e) {} 
  try { if (objName == "AgentComName") { goToPic(3); top.fraPic.scrollTo(110, 50); showPosition(90+hx, 150+hy, 600, 55); } } catch(e) {} 
  try { if (objName == "AgentCom") { goToPic(3); top.fraPic.scrollTo(300, 50); showPosition(690+hx, 150+hy, 400, 55); } } catch(e) {} 
  
  try { if (objName == "SignAgentName") { goToPic(3); top.fraPic.scrollTo(110, 50); showPosition(90+hx, 110+hy, 350, 55); } } catch(e) {} 
  //try { if (objName == "AgentSignDate") { goToPic(3); top.fraPic.scrollTo(110, 50); showPosition(440+hx, 110+hy, 350, 55); } } catch(e) {} 
  try { if (objName == "FirstTrialOperator") { goToPic(3); top.fraPic.scrollTo(110, 1120); showPosition(90+hx, 1160+hy, 560, 55); } } catch(e) {} 
  try { if (objName == "FirstTrialDate") { goToPic(3); top.fraPic.scrollTo(300, 1120); showPosition(630+hx, 1160+hy, 300, 55); } } catch(e) {} 
  try { if (objName == "UWOperator") { goToPic(3); top.fraPic.scrollTo(110, 1160); showPosition(90+hx, 1220+hy, 560, 55); } } catch(e) {} 
  try { if (objName == "UWDate") { goToPic(3); top.fraPic.scrollTo(300, 1160); showPosition(630+hx, 1220+hy, 300, 55); } } catch(e) {} 

  if((tMultLineId<4)){//alert(tMultLineId);
  	try { if (objName == "AgentImpartGrid4") { goToPic(3); top.fraPic.scrollTo(75, 160+(40*tMultLineId)); showPosition(75+hx,300+hy+(50*tMultLineId), 920, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 790+(40*(tMultLineId-28))); showPosition(995+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 790+(40*(tMultLineId-28))); showPosition(1100+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  }
  if((tMultLineId>3)){//alert(tMultLineId);
  	try { if (objName == "AgentImpartGrid4") { goToPic(3); top.fraPic.scrollTo(75, 400+(40*(tMultLineId-3))); showPosition(75+hx,500+hy+(40*(tMultLineId-3)), 920, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid5") { goToPic(2); top.fraPic.scrollTo(200, 790+(40*(tMultLineId-28))); showPosition(995+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  	//try { if (objName == "ImpartGrid6") { goToPic(2); top.fraPic.scrollTo(300, 790+(40*(tMultLineId-28))); showPosition(1100+hx,850+hy+(40*(tMultLineId-28)), 100, 45); } } catch(e) {} 
  }
}
