<%
//GEdorTypexxxxInit.jsp
//�����ܣ�
//�������ڣ�2005-4-26 19:41
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
    //initBankInfo();
    queryCustomerInfo();
    initSelQuery();
    newGetType();
    //queryBankInfo();
    //�ĳ�DIV��ʾ add by jiaqiangli 2008-08-22
    initNewBnfGrid();
    //��ѯ��������Ϣ
    queryNewBnfGrid()
  }
  catch(re)
  {
    alert("GEdorTypeGCInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//����ʱ��ѯ
function initInpBox()
{
  try
  {
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('PEdorType','EdorType','EdorTypeName');
    try
    {
        document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
  }
  catch(ex)
  {
      alert("�� GEdorTypeGCInit.jsp --> InitInpBox �����з����쳣:��ʼ���������");
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
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="120px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 3;
        PolGrid.displayTitle = 1;
        PolGrid.canSel=0;
        PolGrid.canChk=0;
        PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
        PolGrid.selBoxEventFuncName ="" ;
      //��Щ����������loadMulLine����
    }
    catch(ex)
    {
      alert(ex);
    }
}

//add by jiaqiangli 2008-08-21 �������ȡ��ʽ���
/**
         * ����������Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initNewBnfGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

                iArray[1] = new Array();
                iArray[1][0] = "�����˺�";
                iArray[1][1] = "55px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;


                iArray[2] = new Array();
                iArray[2][0] = "����������";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "���������";
                iArray[3][1] = "0px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;


                iArray[4] = new Array();
                iArray[4][0] = "����������";
                iArray[4][1] = "50px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][9] = "����������|NotNull&Len<=60";

                iArray[5] = new Array();
                iArray[5][0] = "��";
                iArray[5][1] = "0px";
                iArray[5][2] = 0;
                iArray[5][3] = 0;
               
                iArray[6] = new Array();
                iArray[6][0] = "֤������";
                iArray[6][1] = "40px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "֤������";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][9] = "֤������|NotNull&Len<=20";

                iArray[8] = new Array();
                iArray[8][0] = "�뱻���˹�ϵ";
                iArray[8][1] = "50px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "����˳��";
                iArray[9][1] = "40px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "����ݶ�";
                iArray[10][1] = "40px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][9] = "����ݶ�|NotNull&Value>=0&Value<=1";

                iArray[11] = new Array();
                iArray[11][0] = "����";
                iArray[11][1] = "0px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][10] = "CustomerType";
                //iArray[11][11] = getCustomerType();
                iArray[11][19] = 1;
                
                iArray[12] = new Array();
                iArray[12][0] = "�Ա�";
                iArray[12][1] = "80px";
                iArray[12][2] = 100;
                iArray[12][3] = 0;
                
                iArray[13] = new Array();
                iArray[13][0] = "��������";
                iArray[13][1] = "80px";
                iArray[13][2] = 100;
                iArray[13][3] = 0;
                
                //����������
                iArray[14] = new Array();
                iArray[14][0] = "סַ";
                iArray[14][1] = "0px";
                iArray[14][2] = 100;
                iArray[14][3] = 1;
                                
                iArray[15] = new Array();
                iArray[15][0] = "�ʱ�";
                iArray[15][1] = "0px";
                iArray[15][2] = 100;
                iArray[15][3] = 1;
                                
                iArray[16] = new Array();
                iArray[16][0] = "��ע��Ϣ";
                iArray[16][1] = "0px";
                iArray[16][2] = 100;
                iArray[16][3] = 1;
                
                //add by jiaqiangli ���б��롢�����ʺš������ʻ���
                iArray[17] = new Array();
                iArray[17][0] = "���б���";
                iArray[17][1] = "80px";
                iArray[17][2] = 100;
                iArray[17][3] = 2;
                iArray[17][4] = "bank";
                iArray[17][9] = "���б���|NotNull&Code:bank";
                
                                
                iArray[18] = new Array();
                iArray[18][0] = "�����ʺ�";
                iArray[18][1] = "120px";
                iArray[18][2] = 100;
                iArray[18][3] = 1;
                iArray[18][22]="confirmSecondInput1"
                                
                iArray[19] = new Array();
                iArray[19][0] = "�����ʻ���";
                iArray[19][1] = "80px";
                iArray[19][2] = 100;
                iArray[19][3] = 1;
            }
            catch (ex)
            {
                alert("�� PEdorTypeGCInit.jsp --> initNewBnfGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                NewBnfGrid = new MulLineEnter("fm", "NewBnfGrid");
                NewBnfGrid.mulLineCount = 0;
                NewBnfGrid.displayTitle = 1;
                NewBnfGrid.locked = 1;
                NewBnfGrid.hiddenPlus = 1;
                NewBnfGrid.hiddenSubtraction = 1;
                NewBnfGrid.canChk = 0;
                NewBnfGrid.canSel = 0;
                NewBnfGrid.chkBoxEventFuncName = "";
                NewBnfGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                NewBnfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorTypeGCInit.jsp --> initNewBnfGrid �����з����쳣: ��ʼ���������");
            }
        }
        
</script>
