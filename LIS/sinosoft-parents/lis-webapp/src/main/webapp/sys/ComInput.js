//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var mySql = new SqlClass();
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
	if(mOperate=="")
	{
		addClick();
	}
  if (!beforeSubmit())
    return false;
	
/*	if (!changeGroup())
		return false;
  if(mOperate=="INSERT||MAIN")
  {
  	if(!CheckBranchValid())
  		return false;
  }
*/  
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.fmtransact.value=mOperate;
//  if (fm.hideOperate.value=="")
//  {
//    alert("�����������ݶ�ʧ��");
//  }
  //showSubmitFrame(mDebug);
  //fm.fmtransact.value = "INSERT||COM";
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("��OLDCom.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  var ComCode;
  var ComGrade;
  //��Ӳ���
  if (!verifyInput()) return false;
  //alert(mOperate);
  if (trim(mOperate) == 'INSERT||COM')
  {
    //У����������Ƿ����
  /*  var strSQL = "";
    strSQL = "select Comcode from LDCom where 1=1 "
	    + getWherePart('ComCode')*/
		
	mySql = new SqlClass();
	mySql.setResourceName("sys.ComInputSql");
	mySql.setSqlId("ComSql1");
	mySql.addSubPara(fm.ComCode.value );     
    var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
    //alert(strSQL);
    if (strQueryResult) {  	
  		alert('�ù�����������Ѵ��ڣ�');
  		document.all('ComCode').value = '';
  		return false;
  	}
   //У���������ͼ����Ƿ�һ��
    ComCode = document.all('ComCode').value;
    ComGrade = document.all('ComGrade').value;
    if(ComGrade=='01') //�ܹ�˾
    {
   		if(ComCode.length!=2)
   		{
   			alert("�ü���Ļ������볤�Ȳ�����Ҫ��");
   			document.all('ComCode').value = '';
   			return false;
   		}
   	}
   	else if(ComGrade=='02') //�ֹ�˾
   	{	
   		if(ComCode.length!=4)
   		{
   			alert("�ü���Ļ������볤�Ȳ�����Ҫ��");
   			document.all('ComCode').value = '';
   			return false;
   		}
   			
   }
		else if(ComGrade=='03') //��֧
		{
   		if(ComCode.length!=6)
   		{
   			alert("�ü���Ļ������볤�Ȳ�����Ҫ��");
   			document.all('ComCode').value = '';
   			return false;
   		}
   						
		}
		else if (ComGrade=='04')//Ӫ������
		{
			if(ComCode.length!=8)
   		{
   			alert("�ü���Ļ������볤�Ȳ�����Ҫ��");
   			document.all('ComCode').value = '';
   			return false;
   		}
		}
  }
  
  return true;
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  mOperate="INSERT||COM";
  //showDiv(operateButton,"false");
  //showDiv(inputButton,"true");
  //fm.fmtransact.value = "INSERT||COM";
  submitForm();
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  
  mOperate = "UPDATE||COM";
  submitForm();
  }
  else
  {
    mOperate="";
    alert("��ȡ�����޸Ĳ�����");
  }
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./ComQuery.html","" ,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
  var i = 0;
  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  //showSubmitFrame(mDebug);
  fm.fmtransact.value = "DELETE||COM";

  document.getElementById("fm").submit(); //�ύ
  initForm();
  }
  else
  {
    //mOperate="";
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




/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all( 'ComCode' ).value = arrResult[0][0];
		document.all('OutComCode').value = arrResult[0][1];
		document.all('Name').value = arrResult[0][2];
		document.all('ShortName').value = arrResult[0][3];
		document.all('Address').value = arrResult[0][4];
		document.all('ZipCode').value = arrResult[0][5];
		document.all('Phone').value = arrResult[0][6];
		document.all('Fax').value = arrResult[0][7];
		document.all('EMail').value = arrResult[0][8];
		document.all('WebAddress').value = arrResult[0][9];
		document.all('SatrapName').value = arrResult[0][10];
		document.all('Sign').value = arrResult[0][11];
		//document.all('ComAreaType').value = arrResult[0][12];
		document.all('ComCitySize').value = arrResult[0][12];
	    document.all('UpComCode').value = arrResult[0][13];
	    document.all('ComGrade').value = arrResult[0][14];
	    document.all('IsDirUnder').value = arrResult[0][15];
	  
	    //showOneCodeName('ComAreaType','ComAreaType','ComAreaTypeName');
	    showOneCodeName('ComCitySize','ComCitySize','ComCitySizeName');
	    showOneCodeName('ComLevel','ComGrade','ComGradeName');
	    showOneCodeName('comdirectattr','IsDirUnder','IsDirUnderName');
	}
}

function SelectCom(){
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
	
  
}