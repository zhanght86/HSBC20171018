<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2005-4-29 11:32����
//������  ��Lihs
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

var LoanGrid;

function initForm()
{
    try
    {
        initInputBox();
        //initQueryPolInfo();
        //getPolInfo(document.all('ContNo').value);
        initCustomerGrid();
        initLoanGrid();
        //initBankInfo();
        queryCustomerGrid();
    }
    catch (ex)
    {
        alert("�� PEdorTypeLNInit.jsp --> initForm �����з����쳣: ��ʼ���������");
    }
}

function initInputBox()
{
    try
    {
        Edorquery();
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        document.all('PolNo').value = top.opener.document.all('PolNo').value;
        document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
        //document.all('ContType').value ="1";
        showOneCodeName('PEdorType','EdorType','EdorTypeName');
    }
    catch (ex)
    {
        alert("�� PEdorTypeLNInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
    }
}

function initSelQuery()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.all('fmtransact').value = "QUERY||MAIN";
  fm.submit();
}
function initQueryPolInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    var arrSelected = new Array();
    turnPage.pageDisplayGrid = LoanGrid;
    turnPage.pageIndex = 0;
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
//       strSQL = "SELECT RISKCODE,INSUREDNO,INSUREDNAME,AMNT,PREM,'','' FROM LCPOL WHERE MAINPOLNO=POLNO AND "
//                             +"CONTNO='"+tContNo+"'";
      var sqlid1="PEdorTypeLNInputSql4";
      var mySql1=new SqlClass();
      mySql1.setResourceName("bq.PEdorTypeLNInputSql"); //ָ��ʹ�õ�properties�ļ���
      mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
      mySql1.addSubPara(tContNo);//ָ������Ĳ���
      strSQL=mySql1.getString();
    //alert("-----------"+strSQL+"------------");
    }
    else
    {
        alert('û����Ӧ�����ִ��룡');
        return;
    }
     turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
     if(!turnPage.strQueryResult)
     {
            alert("��ѯʧ�ܣ�");
     }
     else
     {
      arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
      displayMultiline(arrSelected, turnPage.pageDisplayGrid);
      }
}

function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="֤������";
        iArray[4][1]="90px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="֤������";
        iArray[5][1]="140px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�Ա�";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=2;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=8;
        iArray[7][21]=3;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //��Щ���Ա�����loadMulLineǰ
        CustomerGrid.mulLineCount = 3;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        //PolGrid.canChk=1;
        CustomerGrid.hiddenPlus=1;
        CustomerGrid.hiddenSubtraction=1;
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorTypeLNInit.jsp --> initCustomerGrid �����з����쳣: ��ʼ���������");
    }
}

// ��Ϣ�б�ĳ�ʼ��
function initLoanGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ͬ��";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�������";
        iArray[2][1]="60px";
        iArray[2][2]=300;
        iArray[2][3]=8;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=7;
        iArray[3][21]=3;
        iArray[3][23]="0";

        iArray[4]=new Array();
        iArray[4][0]="���ѱ�׼";
        iArray[4][1]="70px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="�ֽ��ֵ";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="�����޶�";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][21]=0;
        iArray[7][23]="0";
  
        iArray[8]=new Array();
        iArray[8][0]="��ʷ���Ϣ�ۼ�";
        iArray[8][1]="70px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][21]=0;
        iArray[8][23]="0";
        
        iArray[9]=new Array();
        iArray[9][0]="����";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=2;
        iArray[9][4]="currency";
        
        iArray[10]=new Array();
        iArray[10][0]="���ν��";
        iArray[10][1]="70px";
        iArray[10][2]=100;
        iArray[10][3]=7;
        iArray[10][21]=0;
        iArray[10][23]="0";


        LoanGrid = new MulLineEnter("fm", "LoanGrid");
        //��Щ���Ա�����loadMulLineǰ
        //LoanGrid.mulLineCount = 6;
        LoanGrid.canSel = 1;
        LoanGrid.displayTitle = 1;
        LoanGrid.hiddenPlus = 1;
        LoanGrid.hiddenSubtraction = 1;
		LoanGrid.selBoxEventFuncName = "ShowLoanInfo";
        LoanGrid.loadMulLine(iArray);

        //��̨��ѯ����ʼ������MulLine������
        fm.action="./PEdorTypeLNCount.jsp";
        fm.submit();
    }
    catch (ex)
    {
        alert("�� PEdorTypeLNInit.jsp --> initLoanGrid �����з����쳣: ��ʼ���������");
    }
}

function initBankInfo()
{
     //var i = 0;
     var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

   var strSql = "";
//    strSql = "select AppntNo,AppntName,Prem,Amnt,PayMode,BankCode,BankAccNo,AccName,PayLocation from LPCont where 1 =1" + " "
//               + getWherePart('ContNo')
//               + getWherePart('EdorNo')
//               + getWherePart('EdorType')
 //alert(strSql);
   var sqlid1="PEdorTypeLNInputSql5";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.PEdorTypeLNInputSql"); //ָ��ʹ�õ�properties�ļ���
   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
   mySql1.addSubPara(window.document.getElementsByName("EdorNo")[0].value);//ָ������Ĳ���
   mySql1.addSubPara(window.document.getElementsByName("EdorType")[0].value);//ָ������Ĳ���
   strSql=mySql1.getString();
   var arrResult = easyExecSql(strSql, 1, 0);
   if (arrResult == null)
   {
       //alert("û����Ӧ�ı�����Ϣ");
       showInfo.close();
           document.all('BankCode').value = "";
           document.all('BankAccNo').value = "";
           document.all('AccName').value = "";
           document.all('PayLocation').value = "";
   }
   else
   {
       displayBank(arrResult);
       showInfo.close();
   }

   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
     //document.all('fmtransact').value = "QUERY||PayLocation";
     //fm.submit();

}

</script>