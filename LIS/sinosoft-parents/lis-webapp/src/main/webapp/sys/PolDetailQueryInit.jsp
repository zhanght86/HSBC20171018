<%
//�������ƣ�PolQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
 <%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput globalInput = (GlobalInput)session.getValue("GI");
    if(globalInput == null) {
        out.println("session has expired");
        return;
    }
    String strOperator = globalInput.Operator;
%>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{

  try
  {
    // ������ѯ����
  //  document.all('CustomerNo').value = tCustomerNo;
  //  document.all('Name').value = tName;
      document.all('ContNo').value = tContNo;

  }
  catch(ex)
  {
    alert("��PolDetailQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
//    setOption("t_sex","0=��&1=Ů&2=����");
//    setOption("sex","0=��&1=Ů&2=����");
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");
  }
  catch(ex)
  {
    alert("��PolDetailQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    initInsuredGrid();
    easyQueryClick();
    //returnParent();
    initContAndAppnt();
    QueryGetPolDate();
  }
  catch(re)
  {
    alert("PolDetailQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

var PolGrid;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����������ֺ�";                //����
      iArray[1][1]="120px";                 //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="�����������ֺ���";              //����
      iArray[2][1]="120px";                 //�п�
      iArray[2][2]=100;                     //�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ��������";                //����
      iArray[3][1]="100px";                 //�п�
      iArray[3][2]=100;                     //�����ֵ
      iArray[3][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";                //����
      iArray[4][1]="80px";                  //�п�
      iArray[4][2]=100;                     //�����ֵ
      iArray[4][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����������";                //����
      iArray[5][1]="80px";                  //�п�
      iArray[5][2]=200;                     //�����ֵ
      iArray[5][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������


      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";              //����
      iArray[6][1]="80px";                  //�п�
      iArray[6][2]=200;                     //�����ֵ
      iArray[6][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������


      iArray[7]=new Array();
      iArray[7][0]="��������";              //����
      iArray[7][1]="80px";                  //�п�
      iArray[7][2]=200;                     //�����ֵ
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="��������";              //����
      iArray[8][1]="80px";                  //�п�
      iArray[8][2]=100;                     //�����ֵ
      iArray[8][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][3]=2;
      iArray[8][4]="station";                       //�Ƿ����ô���:null||""Ϊ������
      iArray[8][5]="6";                                 //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[8][9]="��������|code:station&NOTNULL";
      iArray[8][18]=250;
      iArray[8][19]= 0 ;

      iArray[9]=new Array();
      iArray[9][0]="����״̬";              //����
      iArray[9][1]="75px";                  //�п�
      iArray[9][2]=100;                     //�����ֵ
      iArray[9][3]=0;               //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="����";                 //����
      iArray[10][1]="75px";                 //�п�
      iArray[10][2]=100;                        //�����ֵ
      iArray[10][3]=7;              //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[10][23]="0";
			
      iArray[11]=new Array();
      iArray[11][0]="��Ч����";                 //����
      iArray[11][1]="90px";                 //�п�
      iArray[11][2]=100;                        //�����ֵ
      iArray[11][3]=8;                          //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="����ֹ��";                 //����
      iArray[12][1]="90px";                 //�п�
      iArray[12][2]=100;                        //�����ֵ
      iArray[12][3]=8;                          //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="�ɷ�ֹ��";                 //����
      iArray[13][1]="90px";                 //�п�
      iArray[13][2]=100;                        //�����ֵ
      iArray[13][3]=8;                          //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="�����˱���";               //����
      iArray[14][1]="80px";                 //�п�
      iArray[14][2]=100;                        //�����ֵ
      iArray[14][3]=3;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[14][4]="AgentCode";                        //�Ƿ����ô���:null||""Ϊ������
      iArray[14][5]="11";                               //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[14][9]="�����˱���|code:AgentCode&NOTNULL";
      iArray[14][18]=250;
      iArray[14][19]= 0 ;

      iArray[15]=new Array();
      iArray[15][0]="����״̬";                 //����
      iArray[15][1]="60px";                 //�п�
      iArray[15][2]=100;                        //�����ֵ
      iArray[15][3]=3;                          //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[15][4]="PolState";                         //�Ƿ����ô���:null||""Ϊ������
      iArray[15][5]="11";                               //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[15][9]="����״̬|code:PolState&NOTNULL";
      iArray[15][18]=150;
      iArray[15][19]= 0 ;

      //iArray[16]=new Array();
      //iArray[16][0]="����״̬����ԭ��";               //����
      //iArray[16][1]="0px";                    //�п�
      //iArray[16][2]=150;                      //�����ֵ
      //iArray[16][3]=3;                        //�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[16][4]="PolStateReason";                         //�Ƿ����ô���:null||""Ϊ������
      //iArray[16][5]="11";                                 //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      //iArray[16][9]="����״̬ԭ��|code:PolStateReason&NOTNULL";
      //iArray[16][18]=150;
      //iArray[16][19]= 0 ;

      //iArray[16]=new Array();
      //iArray[16][0]="���ѷ�ʽ";               //����
      //iArray[16][1]="60px";                   //�п�
      //iArray[16][2]=100;                      //�����ֵ
      //iArray[16][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[16]=new Array();
      iArray[16][0]="������ʽ";                 //����
      iArray[16][1]="60px";                 //�п�
      iArray[16][2]=100;                        //�����ֵ
      iArray[16][3]=0;                            //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[17]=new Array();
      iArray[17][0]="���Ѷ�Ӧ��";           //����
      iArray[17][1]="90px";                   //�п�
      iArray[17][2]=100;                          //�����ֵ
      iArray[17][3]=8;                            //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[18]=new Array();
      iArray[18][0]="����״̬";                 //����
      iArray[18][1]="60px";                   //�п�
      iArray[18][2]=100;                          //�����ֵ
      iArray[18][3]=3;                            //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[19]=new Array();
      iArray[19][0]="��������";                 //����
      iArray[19][1]="60px";                   //�п�
      iArray[19][2]=100;                          //�����ֵ
      iArray[19][3]=0;                            //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[20]=new Array();
      iArray[20][0]="�����ܶ�";                 //����
      iArray[20][1]="60px";                   //�п�
      iArray[20][2]=100;                          //�����ֵ
      iArray[20][3]=7;                            //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[20][23]="0";
			
      iArray[21]=new Array();
      iArray[21][0]="��������";                 //����
      iArray[21][1]="60px";                   //�п�
      iArray[21][2]=100;                          //�����ֵ
      iArray[21][3]=7;                            //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[21][23]="0";

      iArray[22]=new Array();
      iArray[22][0]="�����ӷ�";                 //����
      iArray[22][1]="60px";                   //�п�
      iArray[22][2]=100;                          //�����ֵ
      iArray[22][3]=7;                            //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[22][23]="0";
			
      iArray[23]=new Array();
      iArray[23][0]="ְҵ�ӷ�";                 //����
      iArray[23][1]="60px";                   //�п�
      iArray[23][2]=100;                          //�����ֵ
      iArray[23][3]=7;                          //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[23][23]="0";

      iArray[24]=new Array();
      iArray[24][0]="Ͷ������";                 //����
      iArray[24][1]="60px";                     //�п�
      iArray[24][2]=100;                        //�����ֵ
      iArray[24][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[25]=new Array();
			iArray[25][0]="����";
			iArray[25][1]="60px";
			iArray[25][2]=100;
			iArray[25][3]=2;
			iArray[25][4]="currency";


      PolGrid = new MulLineEnter( "document" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;

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


// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����������";
      iArray[1][1]="80px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="�ͻ���";
      iArray[2][1]="100px";
      iArray[2][2]=120;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="֤������";
      iArray[4][1]="120px";
      iArray[4][2]=150;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="֤������";
      iArray[5][1]="120px";
      iArray[5][2]=150;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="����";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="ְҵ�ȼ�";
      iArray[7][1]="80px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="��������";
      iArray[8][1]="80px";
      iArray[8][2]=100;
      iArray[8][3]=8;

      InsuredGrid = new MulLineEnter( "document" , "InsuredGrid" );
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 5;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.locked = 1;
      InsuredGrid.canSel = 0;

      InsuredGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid.hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>











