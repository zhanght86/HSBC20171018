<%
//�������ƣ�ExtendInvoiceInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");

	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	if(session.getAttribute("AutoCheckon")!=null && !session.getAttribute("AutoCheckon").equals("null"))
	{
	}
	else
	{
	  session.setAttribute("AutoCheckon","");
	}
  String ContNo = request.getParameter("OtherNo");
  String TempFeeNo = request.getParameter("TempFeeNo");
	String strOperator = globalInput.Operator;
	String strManagecom = globalInput.ManageCom;

%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('ContNo').value = '<%=ContNo%>';
    document.all('GetNoticeNo').value = '<%=TempFeeNo%>';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('ChequNo').value = '<%=session.getAttribute("AutoCheckon")%>';
    if (document.all('GetNoticeNo').value == 'null')
    document.all('GetNoticeNo').value = '';
    if (document.all('ContNo').value == 'null')
    document.all('ContNo').value = '';
		document.all('PrintType').value = '01';
    document.all('PrintTypeName').value = '������Ʊ��ӡ';
  }
  catch(ex)
  {
    alert("��ExtendInvoiceInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initManageCom()
{
  try
  {
  	<!--	var QueryCode = "select code from ldcode where codetype = 'XQCheck' and trim(comcode) = '" + (<%=strManagecom%>+'').substring(1,6) + "'"; -->
  		var QueryCode = "";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("operfee.ExtendInvoiceInitSql");
		mySql1.setSqlId("ExtendInvoiceInitSql1");
		mySql1.addSubPara((<%=strManagecom%>+'').substring(1,6));
		QueryCode = mySql1.getString();
			var tResult = easyExecSql(QueryCode);
			if(tResult == null)
			{
				<!--	QueryCode = "select code from ldcode where codetype = 'XQCheck' and trim(comcode) = '" + (<%=strManagecom%>+'').substring(1,4) + "'"; -->
					var mySql2 = new SqlClass();
					mySql2.setResourceName("operfee.ExtendInvoiceInitSql");
					mySql2.setSqlId("ExtendInvoiceInitSql2");
					mySql2.addSubPara((<%=strManagecom%>+'').substring(1,4));
					strSql = mySql2.getString();
					QueryCode = easyExecSql(QueryCode);
					if(tResult == null)
					{
							alert("ϵͳ��û�иõ�½���������ڵ�֤���͡�");
							return false;
					}
			}

			document.all('CertifyCode').value = tResult[0][0];

	}
	catch(ex)
	{
  		alert("��ExtendInvoiceInit.jsp-->initManageCom�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
  try
  {
   //��ʼ����֤�Ĺ������
   	initManageCom();
    initContGrid();
    initInpBox();
  }
  catch(re)
  {
    alert("ExtendInvoiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initContGrid()
  {
    var iArray = new Array();

      try
      {
     iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="��ͬ��";         		//����
    iArray[1][1]="120px";            	//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


    iArray[2]=new Array();
    iArray[2][0]="����֪ͨ���";         		//����
    iArray[2][1]="120px";            	//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


    iArray[3]=new Array();
    iArray[3][0]="��������";         		//����
    iArray[3][1]="60px";            	//�п�
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ɷѽ��";         		//����
    iArray[4][1]="0px";            	//�п�
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  iArray[5]=new Array();
    iArray[5][0]="��ˮ��";         		//����
    iArray[5][1]="60px";            	//�п�
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
    iArray[6]=new Array();
    iArray[6][0]="payno";         		//����
    iArray[6][1]="0px";            	//�п�
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="payno";         		//����
    iArray[7][1]="0px";            	//�п�
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="managecom";         		//����
    iArray[8][1]="0px";            	//�п�
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ContGrid = new MulLineEnter( "fm" , "ContGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ContGrid.mulLineCount = 3;
      ContGrid.displayTitle = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 1;
      ContGrid.canChk = 0;
      ContGrid.loadMulLine(iArray);  
      //ContGrid.selBoxEventFuncName = "easyQueryAddClick";
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
