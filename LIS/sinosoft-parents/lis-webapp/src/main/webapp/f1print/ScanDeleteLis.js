//�������ƣ�ScanDeleteLis.js
//�����ܣ��ۺϴ�ӡ���б������嵥--��֤ɾ���嵥
//�������ڣ�2010-06-02
//������  ��hanabin
//���¼�¼��  ������    ��������     ����ԭ��/����

var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();


function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

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
    	showDiv(inputButton,"false");
    }
}


//������ʼ���ڽ��в�ѯ��Ҫ�����ڷ�Χ�ڵ����κ���
function easyPrint()
{
var ManageCom = document.all('ManageCom').value;
	
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	
	if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("�������ӦΪ4λ��8λ��");
	//	return;
	//}  
	}else{
	   alert("��¼����������");
	   return false;
	}
	
	if(fm.PrtNo.value =="")
	{
		if(fm.StartDate.value==null||fm.StartDate.value==""||fm.EndDate.value==null||fm.EndDate.value=="")
		{
		   alert("��¼�롾ӡˢ�š���ɾ����ֹ���ڡ���");
		   return false;
		}
	}
		
    
    	//var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	
    	fm.action = './ScanDeleteLisSave.jsp';
    	fm.target="f1print";
    	document.getElementById("fm").submit(); //�ύ
    	
}

function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	
	if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("�������ӦΪ4λ��8λ��");
	//	return;
	//}  
	}else{
	   alert("��¼����������");
	   return false;
	}
	
	if(fm.PrtNo.value =="")
	{
		if(fm.StartDate.value==null||fm.StartDate.value==""||fm.EndDate.value==null||fm.EndDate.value=="")
		{
		   alert("��¼�롾ӡˢ�š���ɾ����ֹ���ڡ���");
		   return false;
		}
	}
		
	initCodeGrid();
	// ��дSQL���
//	var strSQL = " select a.managecom,a.prtno,a.makedate,a.maketime,a.oldoperator,a.remark from lcdeltrace a where 1=1"
//             + getWherePart('a.Makedate','StartDate','>=')
//             + getWherePart('a.Makedate','EndDate','<=')
//             + getWherePart('a.PrtNo','PrtNo')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comCode + "%%' "
//             + " order by a.MakeDate "
       
	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var  PrtNo0 = window.document.getElementsByName(trim("PrtNo"))[0].value;
	var sqlid0="ScanDeleteLisSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.ScanDeleteLisSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(StartDate0);//ָ������Ĳ���
	mySql0.addSubPara(EndDate0);//ָ������Ĳ���
	mySql0.addSubPara(PrtNo0);//ָ������Ĳ���
	mySql0.addSubPara(document.all("ManageCom").value);//ָ������Ĳ���
	mySql0.addSubPara(comCode);//ָ������Ĳ���
	var strSQL=mySql0.getString();
 			
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	
}


function afterQuery(arrResult)
{
  
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
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
//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
			parent.fraMain.rows = "0,0,0,0,*";
    }
    else
    {
  		parent.fraMain.rows = "0,0,0,0,*";
    }
}
function queryCom()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
	}
function afterQuery(arrResult)
{
  if(arrResult!=null)
  { 	
	    fm.AgentGroup.value = arrResult[0][3];
	    
	}
}


