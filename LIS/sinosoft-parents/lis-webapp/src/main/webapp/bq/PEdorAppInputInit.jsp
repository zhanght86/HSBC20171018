<%
//�������ƣ�
//�����ܣ����˱�ȫ����
//�������ڣ�2005-04-26 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
    String CurrentDate = PubFun.getCurrentDate();
    Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
    String ValidDate = CurrentDate;
    String dayAfterCurrent = new FDate().getString(dt);
    String CurrentTime = PubFun.getCurrentTime();
%>


<script language="JavaScript">

    var CustomerContGrid, PolGrid, InusredGrid, RiskGrid, EdorItemGrid;

//ҳ���ʼ��
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initCustomerContGrid();
        initPolGrid();
        initInusredGrid();
        initRiskGrid();
        initEdorItemGrid();
        initInternalSwitchChnl();
        initDiv();
        //����ǿͻ���Ҫ���ϱ������Ŀ����д��������֮�٣�
        initCM();
        //ContStateQuery();
    }
    catch (ex)
    {
        alert("�� PEdorAppInputInit.jsp --> initForm �����з����쳣:��ʼ��������� ");
    }
}

//���մӹ�������ȫ����ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('EdorAcceptNo').value = NullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value    = NullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value = NullToEmpty("<%= tSubMissionID %>");
   document.all('LoadFlag').value     = NullToEmpty("<%= tLoadFlag %>");
   document.all('UserCode').value     = NullToEmpty("<%= tUserCode %>");
   document.all('OtherNo').value     = NullToEmpty("<%= tOtherNo %>");
   document.all('ActivityID').value     = NullToEmpty("<%= tActivityID %>");
}

function initInternalSwitchChnl() {
	var sCodeData = "";
	sCodeData = "0|^01|Ͷ��ת��|^02|����ת��|^03|�˱�ת��|^04|����Լת��|^OO|����";
	try { document.getElementsByName("InternalSwitchChnl")[0].CodeData = sCodeData; } catch (ex) {}
}

//��ʼ��ҳ��Ԫ��
function initInpBox()
{
    try
    {
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('EdorMakeDate').value = '<%=CurrentDate%>';
        document.all('EdorType').value = '';
        document.all('EdorTypeName').value = '';
        document.all('CValiDate').value = '';
        document.all('EdorItemAppDate').value = '<%=CurrentDate%>';
        //document.all('EdorValiDate').value = '<%=CurrentDate%>';
        document.all('Operator').value = "<%=tG.Operator%>";
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('ContType').value ='1';
        document.all('currentDay').value = '<%=CurrentDate%>';
        document.all('dayAfterCurrent').value = '<%=dayAfterCurrent%>';
    }
    catch (ex)
    {
        alert("�� PEdorInputInit.jsp --> initInpBox �����з����쳣:��ʼ��������� ");
    }
}

 function initApproveTrackGrid()
{                               
    			var iArray = new Array();
      
      		try
      		{
       			iArray[0]=new Array();
       			iArray[0][0]="���";         //����
       			iArray[0][1]="30px";         //����
       			iArray[0][2]=100;         //����
       			iArray[0][3]=0;         //����
       			
       			iArray[1]=new Array();
       			iArray[1][0]="��������";         //����
       			iArray[1][1]="60px";         //���
       			iArray[1][2]=100;         //��󳤶�
       			iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[2]=new Array();
       			iArray[2][0]="�޸�ԭ��";         //����
       			iArray[2][1]="60px";         //���
       			iArray[2][2]=100;         //��󳤶�
       			iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[3]=new Array();
       			iArray[3][0]="��������";         //����
       			iArray[3][1]="60px";         //���
       			iArray[3][2]=100;         //��󳤶�
       			iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[4]=new Array();
       			iArray[4][0]="����ʱ��";         //����
       			iArray[4][1]="60px";         //���
       			iArray[4][2]=100;         //��󳤶�
       			iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[5]=new Array();
       			iArray[5][0]="������";         //����
       			iArray[5][1]="50px";         //���
       			iArray[5][2]=100;         //��󳤶�
       			iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
 
       			iArray[6]=new Array();
       			iArray[6][0]="�Ƿ�Ϊ����";         //����
       			iArray[6][1]="60px";         //���
       			iArray[6][2]=100;         //��󳤶�
       			iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[7]=new Array();
       			iArray[7][0]="�������";         //����
       			iArray[7][1]="0px";         //���
       			iArray[7][2]=100;         //��󳤶�
       			iArray[7][3]=3;         //�Ƿ�����¼�룬0--���ܣ�1--����

     		
       		ApproveTrackGrid = new MulLineEnter( "fm" , "ApproveTrackGrid" ); 
       		
       		//��Щ���Ա�����loadMulLineǰ
       		//AgentGrid.mulLineCount = 1;   
       		ApproveTrackGrid.displayTitle = 1;
       		ApproveTrackGrid.canSel=1;
//     		ApproveTrackGrid.canChk=0;
       		ApproveTrackGrid.locked=1;
	     		ApproveTrackGrid.hiddenPlus=1;
	     		ApproveTrackGrid.hiddenSubtraction=1;
	     		ApproveTrackGrid.selBoxEventFuncName = "ShowApproveInfo"; //����RadioBoxʱ��Ӧ����
       		ApproveTrackGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��ApproveTrackGridʱ����"+ ex);
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
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="0px";
        iArray[1][2]=0;
        iArray[1][3]=3;

        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="0px";
        iArray[2][2]=0;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="120px";
        iArray[3][2]=200;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="����������ʾ����";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="�����ͬ����";
        iArray[5][1]="0px";
        iArray[5][2]=0;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="110px";
        iArray[6][2]=150;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="�ͻ�����";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="�������ֺ���";
        iArray[8][1]="120px";
        iArray[8][2]=150;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="������������";
        iArray[9][1]="70px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;
        iArray[9][23]=1;

        iArray[10]=new Array();
        iArray[10][0]="��Ч����";
        iArray[10][1]="70px";
        iArray[10][2]=100;
        iArray[10][3]=8;
        iArray[10][21]=3;
        iArray[10][23]=1;

        iArray[11]=new Array();
        iArray[11][0]="����ԭ��";
        iArray[11][1]="70px";
        iArray[11][2]=100;
        iArray[11][3]=0;

        iArray[12]=new Array();
        iArray[12][0]="����ԭ�����";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="���˷ѽ��";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;

        iArray[14]=new Array();
        iArray[14][0]="����ʱ��";
        iArray[14][1]="0px";
        iArray[14][2]=0;
        iArray[14][3]=3;

        iArray[15]=new Array();
        iArray[15][0]="���ɾ���ʱ��";
        iArray[15][1]="0px";
        iArray[15][2]=0;
        iArray[15][3]=3;

        iArray[16]=new Array();
        iArray[16][0]="��󱣴�ʱ��";
        iArray[16][1]="0px";
        iArray[16][2]=0;
        iArray[16][3]=3;

        iArray[17]=new Array();
        iArray[17][0]="�������";
        iArray[17][1]="0px";
        iArray[17][2]=0;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="����״̬";
        iArray[18][1]="70px";
        iArray[18][2]=100;
        iArray[18][3]=0;

        iArray[19]=new Array();
        iArray[19][0]="����״̬����";
        iArray[19][1]="0px";
        iArray[19][2]=0;
        iArray[19][3]=3;

        iArray[20]=new Array();
        iArray[20][0]="�������ͱ���";
        iArray[20][1]="0px";
        iArray[20][2]=0;
        iArray[20][3]=3;

        EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
        //��Щ���Ա�����loadMulLineǰ
        EdorItemGrid.mulLineCount = 5;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.canSel =1;
        EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //���RadioBoxʱ��Ӧ��JS����
        EdorItemGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorAppInputInit.jsp --> initEdorItemGrid �����з����쳣:��ʼ��������� ");
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
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�ͻ���ɫ";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ���";
        iArray[2][1]="90px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="90px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="140px";
        iArray[7][2]=200;
        iArray[7][3]=0;

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
        iArray[10][0]="�������";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        InsuredGrid = new MulLineEnter("fm", "InsuredGrid");
        //��Щ���Ա�����loadMulLineǰ
        InsuredGrid.mulLineCount = 5;
        InsuredGrid.displayTitle = 1;
        InsuredGrid.canChk=0;
        InsuredGrid.canSel =1;
        InsuredGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        InsuredGrid.hiddenSubtraction=1;
        InsuredGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
      }
      catch (ex)
      {
          alert("�� PEdorAppInputInit.jsp --> initInsuredGrid �����з����쳣:��ʼ��������� ");
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
        iArray[1][0]="���ִ���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ֺ���";
        iArray[2][1]="130px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�����˿ͻ���";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="����������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������/����";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="���ѱ�׼";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="��Ч����"
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        iArray[7][21]=3;

        iArray[8]=new Array();
        iArray[8][0]="���Ѷ�Ӧ��";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        iArray[8][21]=3;

        iArray[9]=new Array();
        iArray[9][0]="��������";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="���屣������";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 5;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=1;
        PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorAppInputInit.jsp --> initPolGrid �����з����쳣:��ʼ��������� ");
    }
}

function initCustomerContGrid()
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
        iArray[1][0]="������";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="Ͷ����";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="������";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="ҵ��Ա";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="��Ч����";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="ǩ������";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="�б�״̬";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        iArray[7][21]=2;

        iArray[8]=new Array();
        iArray[8][0]="�Ƿ������";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        CustomerContGrid = new MulLineEnter("fm", "CustomerContGrid");
        //��Щ���Ա�����loadMulLineǰ
        CustomerContGrid.mulLineCount = 3;
        CustomerContGrid.displayTitle = 1;
        CustomerContGrid.canChk=0;
        CustomerContGrid.canSel =1;
        CustomerContGrid.selBoxEventFuncName ="showContStateInfo" ;     //���RadioBoxʱ��Ӧ��JS����
        CustomerContGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        CustomerContGrid.hiddenSubtraction=1;
        CustomerContGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorAppInputInit.jsp --> initCustomerContGrid �����з����쳣:��ʼ��������� ");
    }
}

function initRiskGrid()
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
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="160px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]=1;

        iArray[5]=new Array();
        iArray[5][0]="����";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;
        

        iArray[6]=new Array();
        iArray[6][0]="���ѱ�׼";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]=1;
        
        iArray[7]=new Array();
        iArray[7][0]="����";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        iArray[7][4]="currency";
        
        iArray[8]=new Array();
        iArray[8][0]="�����ӷ�";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][21]=3;
        iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="ְҵ�ӷ�";
        iArray[9][1]="80px";
        iArray[9][2]=100;
        iArray[9][3]=7;
        iArray[9][21]=3;
        iArray[9][23]="0";

        iArray[10]=new Array();
        iArray[10][0]="���ֺ���";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=3;

        iArray[11]=new Array();
        iArray[11][0]="���Ѷ�Ӧ��";
        iArray[11][1]="80px";
        iArray[11][2]=100;
        iArray[11][3]=0;
        iArray[11][21]=3;

        RiskGrid = new MulLineEnter("fm", "RiskGrid");
        //��Щ���Ա�����loadMulLineǰ
        RiskGrid.mulLineCount = 5;
        RiskGrid.displayTitle = 1;
        RiskGrid.canChk=0;
        RiskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        RiskGrid.hiddenSubtraction=1;
        RiskGrid.loadMulLine(iArray);
      }
      catch (ex)
      {
          alert("�� PEdorAppInputInit.jsp --> initRiskGrid �����з����쳣:��ʼ��������� ");
      }
}

</script>
