//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();

var turnPage1 = new turnPageClass();


var turnPage2 = new turnPageClass();
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "acc.NBErrorModifyInputSql";

//�ύ�����水ť��Ӧ����
function submitFrom()
{

  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  lockScreen('submit');
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   document.all('Transact').value ="INSERT";
   document.getElementById("fm").submit(); //�ύ 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
//  alert(FlagStr);
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
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
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   

    //ִ����һ������
  }
  resetForm();
}


//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("toulian.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 


//�ύǰ��У�顢����  
function beforeSubmit()
{
    return true;
}           

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  } else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}


//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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
        
function afterQuery(arrQueryResult)
{
 

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  unlockScreen('submit');
  if (FlagStr == "Fail" )
  {             
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
  else
  { 
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

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
//    
  }
  initForm();
}

function easyQueryClick()
{
	// ��ʼ�����
    initPolGrid();
    initOldPlan();
    initNewPlan();

	// ��дSQL���
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("NBErrorModifyInputSql0");
	    mySql.addSubPara(fm.QureyPrtNo.value);
	    mySql.addSubPara(fm.QureyContNo.value);

	turnPage.queryModal(mySql.getString(), PolGrid);
	if(PolGrid.mulLineCount <= 0){
		alert("û�з������������ݣ�");
		return false;
	}
}


function getPolDetail(parm1,parm2)
{
	var selectRowNum = parm1.replace(/spanPolGrid/g,"");

	var PolNo = document.all('PolGrid1'+'r'+selectRowNum).value;
    var RiskCode = document.all('PolGrid2'+'r'+selectRowNum).value;
	
   // var PolNo=document.all(parm1).all('PolGrid1').value;
//    var RiskCode=document.all(parm1).all('PolGrid2').value;
    document.all('PolNo').value=PolNo;
    document.all('RiskCode').value=RiskCode;
    initOldPlan();
    initNewPlan();
    
    	// ��дSQL���
	 var mySql1=new SqlClass();
	    mySql1.setResourceName(sqlresourcename);
	    mySql1.setSqlId("NBErrorModifyInputSql1");
	    mySql1.addSubPara(PolNo);
	   

	turnPage1.queryModal(mySql1.getString(), OldPlanGrid);
	if(OldPlanGrid.mulLineCount <= 0){
		alert("û�з������������ݣ�");
		return false;
	}
	
	 var mySql2=new SqlClass();
	    mySql2.setResourceName(sqlresourcename);
	    mySql2.setSqlId("NBErrorModifyInputSql2");
	    mySql2.addSubPara(PolNo);
	   

	turnPage2.queryModal(mySql2.getString(), NewPlanGrid);
	
}