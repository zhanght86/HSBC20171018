var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

function submitForm()
{ 
	 if(!verifyInput()) 
	 {
	 	 return false;
	 }
	 var showStr="�����������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  //fm.action="./AdvancePremPrt.jsp";
//fm.target="fraSubmit";
//alert("cccccccc");
//showInfo.close();
//alert("why not");
	  document.getElementById("fm").submit(); //�ύ
	  //
 
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{   //alert("meiyou");
	
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   // showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
  }
}
 
 

function Polmake()
{
  var tManegeCom = document.all("ManageCom").value;
	var tSaleChnl = document.all("SaleChnl").value;
	var tPayIntv = document.all("PayIntv").value;
	var tPolType = document.all("PolType").value;
	var tStartDate =document.all("StartDate").value;
	var tEndDate =document.all("EndDate").value;
	fm.fmtransact.value = "Create";
	var tFileName="AppBirthdayBill" + "_" + tManegeCom+"_"+tSaleChnl+"_"+tPayIntv+"_"+tPolType+"_"+tStartDate+"_"+tEndDate+".xls";	
	fm.FileName.value=tFileName;
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'XSCreatListPath'";
	
	var sqlid1="AppBirthdayBillSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("agentprint.AppBirthdayBillSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSql=mySql1.getString();
	
	var filePath = easyExecSql(strSql); 
	fm.Url.value=filePath
	fm.action="./AppBirthdayBillPrt.jsp";
	fm.target="fraSubmit";
	submitForm();
}
function downAfterSubmit(cfilePath,fileName) { 
  //alert("adadad");
  showInfo.close();
  fileUrl.href = cfilePath + fileName; 
  //alert(fileUrl.href);
  //fileUrl.click();
  fm.action="./download.jsp?file="+fileUrl.href;
  //document.getElementById("fm").submit();
  document.getElementById("fm").submit(); //�ύ
} 

function Poldown()
{
	//alert("dgf1111111111111111111111111");
	var tManegeCom = document.all("ManageCom").value;
	var tSaleChnl = document.all("SaleChnl").value;
	var tPayIntv = document.all("PayIntv").value;
	var tPolType = document.all("PolType").value;
	var tStartDate =document.all("StartDate").value;
	var tEndDate =document.all("EndDate").value;
	
  var tFileName="AppBirthdayBill" + "_" + tManegeCom+"_"+tSaleChnl+"_"+tPayIntv+"_"+tPolType+"_"+tStartDate+"_"+tEndDate+".xls";	
  fm.FileName.value=tFileName;
    //var filePath="D:\\temp\\";
	//var fileUrl = filePath + tFileName;  
	//alert("tFileName"+tFileName);
    //fileUrl="D:\\temp\\"+tFileName;
	//alert("bbbbbbbbb");
  fm.fmtransact.value = "download";
//  var strSql = "select SysVarValue from LDSysVar where SysVar = 'XSCreatListPath'";
  
	var sqlid1="AppBirthdayBillSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("agentprint.AppBirthdayBillSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSql=mySql1.getString();
  
  var filePath = easyExecSql(strSql); 
  fm.Url.value=filePath
  fm.action="./AppBirthdayBillPrt.jsp";
  fm.target="fraSubmit";
  submitForm();
  
}
  
function format(strDate)
{
	var arrDate = strDate.split("-");
	 if (arrDate[1].length == 1)   arrDate[1] = "0" + arrDate[1];
      if (arrDate[2].length == 1)   arrDate[2] = "0" + arrDate[2];
      var result = arrDate[0]+"-"+arrDate[1]+"-"+arrDate[2];
      return result;
	}