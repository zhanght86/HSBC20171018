<%
//�������ƣ�PEdorTypeNSInit.jsp
//�����ܣ�
//�������ڣ�2005-07-20
//������  ��lizhuo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

function initForm()
{
  try
  {
    Edorquery();
    initInpBox();
    initPolGrid();
    initNewPolGrid();
    
    getPolInfo();
    getNewPolInfo();
    queryCustomerInfo();
    initImpartGrid();
    showimpart();
    showAddNewTypeInfo();
  }
  catch(re)
  {

    alert("PEdorTypeNSInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('MainPolNo').value = top.opener.document.all('PolNo').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    
    document.all('CureCvaliDate').value     = NullToEmpty("<%= CureCvaliDate %>");
    document.all('PreCvaliDate').value        = NullToEmpty("<%= PreCvaliDate %>");
    document.all('NextCvaliDate').value     = NullToEmpty("<%= NextCvaliDate %>");
    document.all('CurDate').value     = NullToEmpty("<%= tCurDate %>");  
    document.all('PayToDate').value=NullToEmpty("<%=tPaytodate%>"); 
    	   
    //������ձ�����
    //alert(document.all('MainPolNo').value );
    showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeNSInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initPolGrid()
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
        iArray[1][0]="�����˿ͻ���";
        iArray[1][1]="0px";
        iArray[1][2]=150;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="40px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="���ִ���";
        iArray[3][1]="60px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="158px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="���ѱ�׼";
        iArray[5][1]="60px";
        iArray[5][2]=150;
        iArray[5][3]=7;
        iArray[5][21]=0;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="60px";
        iArray[6][2]=150;
        iArray[6][3]=7;
        iArray[6][21]=0;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="��Ч����";
        iArray[7][1]="60px";
        iArray[7][2]=150;
        iArray[7][3]=8;
        iArray[7][21]=0;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="���屣������";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        
        iArray[10]=new Array();
        iArray[10][0]="�����ڼ�";
        iArray[10][1]="60px";
        iArray[10][2]=0;
        iArray[10][3]=0;
        

        iArray[11]=new Array();
        iArray[11][0]="��������";
        iArray[11][1]="60px";
        iArray[11][2]=0;
        iArray[11][3]=0;        
 
        
        iArray[12]=new Array();
        iArray[12][0]="��������";
        iArray[12][1]="60px";
        iArray[12][2]=0;
        iArray[12][3]=8;
        
        iArray[13]=new Array();
				iArray[13][0]="����";
				iArray[13][1]="60px";
				iArray[13][2]=100;
				iArray[13][3]=2;
				iArray[13][4]="currency";        

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 3;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=0;
        PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initNewPolGrid()
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
        iArray[1][0]="���ִ���";
        iArray[1][1]="60px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="160px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="���ѱ�׼";
        iArray[3][1]="60px";
        iArray[3][2]=150;
        iArray[3][3]=7;
        iArray[3][21]=3;
        iArray[3][23]="0";

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="60px";
        iArray[4][2]=150;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="��Ч����";
        iArray[5][1]="60px";
        iArray[5][2]=150;
        iArray[5][3]=8;


        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="0px";
        iArray[6][2]=0;
        iArray[6][3]=0;                 

        iArray[7]=new Array();
        iArray[7][0]="���屣������";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="���ֺ�";
        iArray[8][1]="100px";
        iArray[8][2]=0;
        iArray[8][3]=0;
        
        
        
        iArray[9]=new Array();
        iArray[9][0]="�����ڼ�";
        iArray[9][1]="60px";
        iArray[9][2]=0;
        iArray[9][3]=0;
        

        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="60px";
        iArray[10][2]=0;
        iArray[10][3]=0;        
 
        
        iArray[11]=new Array();
        iArray[11][0]="��������";
        iArray[11][1]="60px";
        iArray[11][2]=0;
        iArray[11][3]=8;   

        
 //       iArray[12]=new Array();      
 //       iArray[12][0]="��������";         		//����      
 //       iArray[12][1]="60px";            		//�п�      
 //       iArray[12][2]=60;            			//�����ֵ      
 //       iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������      
 //       iArray[12][4]="NewAddType";      
 //       iArray[12][5]="12|13";      
 //       iArray[12][6]="0|1";      
 //       iArray[12][9]="��������|len<=5";      
 //       iArray[12][15]="newaddtype";      
 //       iArray[12][17]="1";      
 //       iArray[12][18]=700;      
 //             
 //       iArray[13]=new Array();      
 //       iArray[13][0]="��������";         		//����      
 //       iArray[13][1]="60px";            		//�п�      
 //       iArray[13][2]=200;            			//�����ֵ      
 //       iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������      
        iArray[12]=new Array();
				iArray[12][0]="����";
				iArray[12][1]="60px";
				iArray[12][2]=100;
				iArray[12][3]=2;
				iArray[12][4]="currency";


        NewPolGrid = new MulLineEnter( "fm" , "NewPolGrid" );
        //��Щ���Ա�����loadMulLineǰ
        NewPolGrid.mulLineCount = 0;
        NewPolGrid.displayTitle = 1;
        NewPolGrid.canSel=1;
        NewPolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        NewPolGrid.hiddenSubtraction=1;
        NewPolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initImpartGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impartver";
      //add by jiaqiangli 2009-03-12 ��ȫֻȡ������֪������֪
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A05#,#A06#)";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      //iArray[2][16]="#02#";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 0;
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {

    }
}


</script>