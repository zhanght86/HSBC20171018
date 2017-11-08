<%
//�������ƣ�GEdorAppInputInit.jsp
//�����ܣ��ŵ���ȫ����
%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;
  String dayAfterCurrent = new FDate().getString(dt);
  String CurrentTime = PubFun.getCurrentTime();
%>
<script language="JavaScript">

//���մӹ�������ȫ����ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('EdorAcceptNo').value = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value    = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
   document.all('LoadFlag').value     = nullToEmpty("<%= tLoadFlag %>");
   document.all('UserCode').value     = nullToEmpty("<%= tUserCode %>");
}
//��null���ַ���תΪ��
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

//��ʼ��ҳ��Ԫ��
function initInpBox()
{
    try
    {
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('EdorType').value = '';
        document.all('EdorTypeName').value = '';
        document.all('EdorTypeCal').value = '';
        document.all('EdorTypeCalName').value = '';
        document.all('EdorItemAppDate').value = '<%=CurrentDate%>';
        document.all('EdorValiDate').value = '<%=CurrentDate%>';
        document.all('Operator').value = "<%=tG.Operator%>";
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('ContType').value ='2';
        document.all('currentDay').value = '<%=CurrentDate%>';
        document.all('dayAfterCurrent').value = '<%=dayAfterCurrent%>';
        //XinYQ modified on 2007-03-29
        document.getElementsByName("OtherNoType")[0].value = "4";
        document.getElementsByName("AppType")[0].value = "2";
        showOneCodeName("GEdorNoType", "OtherNoType", "OtherNoName");
        showOneCodeName("EdorAppType", "AppType", "AppTypeName");
        
        document.all('EdorType').disabled = false;
	      document.all('EdorTypeCal').disabled = false;
        //document.all('EdorValiDate').readOnly = false; 
        //document.all('EdorItemAppDate').readOnly = false;
    }
    catch(ex)
    {
      alert("��GEdorAppInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!"+ex);
    }
}
//��ʼ��ѡ���
function initSelBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("��GEdorAppInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
//ҳ���ʼ��
function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();
    initInusredGrid();
    initPolGrid();
    initEdorItemGrid();
    initDiv();
    ContStateQuery();
  }
  catch(re)
  {
    alert("GEdorAppInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initEdorItemGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="200px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�����㷨";
        iArray[4][1]="120px";
        iArray[4][2]=100;
        iArray[4][3]=0;      
        
        iArray[5]=new Array();
        iArray[5][0]="����������ʾ����";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;     
        
        iArray[6]=new Array();
        iArray[6][0]="���屣����";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="120px";
        iArray[7][2]=100;
        iArray[7][3]=3;
        
        iArray[8]=new Array();
        iArray[8][0]="�ͻ�����";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="�������ֺ���";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="������������";
        iArray[10][1]="120px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        
        iArray[11]=new Array();
        iArray[11][0]="��Ч����";
        iArray[11][1]="120px";
        iArray[11][2]=100;
        iArray[11][3]=0;
        
        iArray[12]=new Array();
        iArray[12][0]="����ԭ��";
        iArray[12][1]="120px";
        iArray[12][2]=100;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
        iArray[13][0]="����ԭ�����";
        iArray[13][1]="80px";
        iArray[13][2]=100;
        iArray[13][3]=3;
                 
        iArray[14]=new Array();
        iArray[14][0]="���˷ѽ��";
        iArray[14][1]="100px";
        iArray[14][2]=100;
        iArray[14][3]=3;
                 
        iArray[15]=new Array();
        iArray[15][0]="����ʱ��";
        iArray[15][1]="80px";
        iArray[15][2]=100;
        iArray[15][3]=3;
                 
        iArray[16]=new Array();
        iArray[16][0]="���ɾ���ʱ��";
        iArray[16][1]="0px";
        iArray[16][2]=100;
        iArray[16][3]=3;
                 
        iArray[17]=new Array();
        iArray[17][0]="��󱣴�ʱ��";
        iArray[17][1]="80px";
        iArray[17][2]=100;
        iArray[17][3]=3;
                 
        iArray[18]=new Array();
        iArray[18][0]="�������";
        iArray[18][1]="80px";
        iArray[18][2]=100;
        iArray[18][3]=3;
                 
        iArray[19]=new Array();
        iArray[19][0]="����״̬";
        iArray[19][1]="120px";
        iArray[19][2]=100;
        iArray[19][3]=0;
        
        iArray[20]=new Array();
        iArray[20][0]="����״̬����";
        iArray[20][1]="0px";
        iArray[20][2]=100;
        iArray[20][3]=3;

        iArray[21]=new Array();
        iArray[21][0]="�������ͱ���";
        iArray[21][1]="0px";
        iArray[21][2]=100;
        iArray[21][3]=3;
        
        iArray[22]=new Array();
        iArray[22][0]="����ԭ�����";
        iArray[22][1]="0px";
        iArray[22][2]=100;
        iArray[22][3]=3;
        
        iArray[23]=new Array();
        iArray[23][0]="����ԭ��";
        iArray[23][1]="0px";
        iArray[23][2]=100;
        iArray[23][3]=3;
        
        iArray[24]=new Array();
        iArray[24][0]="������������";
        iArray[24][1]="0px";
        iArray[24][2]=100;
        iArray[24][3]=3; 
        
        iArray[25]=new Array();
        iArray[25][0]="�����㷨����";
        iArray[25][1]="0px";
        iArray[25][2]=100;
        iArray[25][3]=3; 

      EdorItemGrid = new MulLineEnter( "document" , "EdorItemGrid" );
      //��Щ���Ա�����loadMulLineǰ
      EdorItemGrid.mulLineCount = 5;
      EdorItemGrid.displayTitle = 1;
      EdorItemGrid.canSel =1;
      EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      EdorItemGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      EdorItemGrid.hiddenSubtraction=1;
      EdorItemGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initInusredGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա�";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="֤������";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="290px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="���屣������";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        InsuredGrid = new MulLineEnter( "document" , "InsuredGrid" );
        //��Щ���Ա�����loadMulLineǰ
        InsuredGrid.mulLineCount = 5;
        InsuredGrid.displayTitle = 1;
        InsuredGrid.canChk=1;
        InsuredGrid.canSel =1;
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

function initPolGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ֺ���";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�����˿ͻ���";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="����������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="����/����";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�ڽ�����";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��Ч����"
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="��������";
        iArray[9][1]="80px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="���屣������";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=3;

        PolGrid = new MulLineEnter( "document" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
	  PolGrid.canSel =1;
      PolGrid.canChk=1;
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

</script>
