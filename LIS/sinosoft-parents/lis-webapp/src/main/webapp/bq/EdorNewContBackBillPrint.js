//               ���ļ��а����ͻ�����Ҫ�����ĺ������¼�

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

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if (verifyInput())
	{
	  var i = 0;
	  var showStr="����׼����ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	  document.getElementById("fm").submit(); //�ύ
	}
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
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
	var iWidth=550;      //�������ڵĿ���; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //ִ����һ������
  }
}

//�����ս��ӡ�����շ��սᵥ
function printList()
{   
	
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    if(fm.StartDay.value == ""||fm.StartDay.value ==null)
		{
			alert( "����ѡ����ʼʱ�䣬�ٵ����ӡ��ť��" );
			return false;
		}
	else if(fm.EndDay.value == ""||fm.EndDay.value ==null)
		{
			alert( "����ѡ�����ʱ�䣬�ٵ����ӡ��ť��" );
			return false;
		}	
	else
	{
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");   
	fm.action="./EdorNewContBackBillPrintSave.jsp";	
	fm.target="f1print";
	fm.fmtransact.value="PRINT";
	submitForm();
	showInfo.close();
	
	}
}



//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}





//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("��ȡ����ɾ��������");
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
//����Լ����ͳ�ƺ���
function polcount()
{
	 if(fm.StartDay.value == ""||fm.StartDay.value ==null)
		{
			alert( "����ѡ����ʼʱ�䡣" );
			return false;
		}
	else if(fm.EndDay.value == ""||fm.EndDay.value ==null)
		{
			alert( "����ѡ�����ʱ�䡣" );
			return false;
		}	
	else
	{
		 var StartDay = document.all('StartDay').value;
		 var EndDay = document.all('EndDay').value;
		 var SaleChnl = document.all('SaleChnl').value;
		 var ManageCom = document.all('ManageCom').value;
		 var RiskCode = document.all('RiskCode').value;
		 
		 var sql="";
		 
		 var SaleChnl_sql="";
		 var ManageCom_sql="";
		 var RiskCode_sql="";
		 
		   	if(dateDiff(document.all('StartDay').value,document.all('EndDay').value,"D")>15)
        {
        	if(!confirm("ͳ��ʱ�������ϵͳ���ܻ�������Ƿ����"))
        	{
        		return;
        	}
        }
		 
		 
		 if( (SaleChnl !=null)&&(SaleChnl != "") )
		 {
		 	 SaleChnl_sql = " and SaleChnl='"+SaleChnl+"' ";
		 }
		 
		 if( (ManageCom !=null)&&(ManageCom != "") )
		 {
		 	 ManageCom_sql = " and managecom like '"+ManageCom+"%%' ";
		 }
		 
		 if( (RiskCode !=null)&&(RiskCode != "") )
		 {
		 	 RiskCode_sql = " and riskcode ='"+RiskCode+"' ";
		 }
		 
//		 sql= "select count(*) from lcpol c where appflag='1' and substr(grppolno,1,4)='0000' "
//       +" and SignDate>='"+StartDay+"' and SignDate<='"+EndDay+"' "
//       +SaleChnl_sql+ManageCom_sql+RiskCode_sql
//       +" and not exists (select 1 from Lcrnewstatehistory b where b.contno = c.contno ) ";
     
		    var sqlid1="EdorNewContBackBillPrintSql1";
		 	var mySql1=new SqlClass();
		 	mySql1.setResourceName("bq.EdorNewContBackBillPrintSql");
		 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
		 	mySql1.addSubPara(StartDay);//ָ���������
		 	mySql1.addSubPara(EndDay);
		 	mySql1.addSubPara(SaleChnl);
		 	mySql1.addSubPara(ManageCom);
		 	mySql1.addSubPara(RiskCode);
		 	sql = mySql1.getString();
		 
		 
     document.all('polamount').value =  easyExecSql(sql);
		 
	}
	
}