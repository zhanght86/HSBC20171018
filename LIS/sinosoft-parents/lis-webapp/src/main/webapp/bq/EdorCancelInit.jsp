<%
//�������ƣ�EdorCancelInit.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 09:20:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

    var AppItemGrid;     //ȫ�ֱ���, ������Ŀ����

//ҳ���ʼ��
function initForm()
{
  try
  {
    initParam();
    initInputBox();
    initQuery();
    //<!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : BGN -->
    initAppItemGrid();
    queryAppItemGrid();
    //<!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : END -->
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
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

//���մӹ�������ȫ����ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('EdorAcceptNo').value     = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value        = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value     = nullToEmpty("<%= tSubMissionID %>");
   document.all('ActivityID').value     = nullToEmpty("<%= tActivityID %>");

}

//��ʼ��ҳ��Ԫ��
function initInputBox()
{
    try
    {
        document.getElementsByName("DelFlag")[0].value = "EdorApp";    //XinYQ added on 2006-02-09 : Ĭ�ϳ���ȫ����ȫ����
    }
    catch(ex)
    {
        alert("�� PEdorInputInit.jsp --> initInputBox �����з����쳣:��ʼ��������� ");
    }
}

// ��ȫ�����б�ĳ�ʼ��
//function initEdorMainGrid()
//{
//    var iArray = new Array();
//
//  try
//  {
//      iArray[0]=new Array();
//      iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
//      iArray[0][1]="30px";                    //�п�
//      iArray[0][2]=30;                        //�����ֵ
//      iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[1]=new Array();
//      iArray[1][0]="����������";              //����
//      iArray[1][1]="120px";                   //�п�
//      iArray[1][2]=100;                       //�����ֵ
//      iArray[1][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[2]=new Array();
//      iArray[2][0]="������";                  //����
//      iArray[2][1]="120px";                   //�п�
//      iArray[2][2]=100;                       //�����ֵ
//      iArray[2][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[3]=new Array();
//      iArray[3][0]="���ս��Ѷ�Ӧ��";          //����
//      iArray[3][1]="100px";                   //�п�
//      iArray[3][2]=100;                       //�����ֵ
//      iArray[3][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
//
//      iArray[4]=new Array();
//      iArray[4][0]="������������";                //����
//      iArray[4][1]="0px";                     //�п�
//      iArray[4][2]=100;                       //�����ֵ
//      iArray[4][3]=3;                         //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
//
//      iArray[5]=new Array();
//      iArray[5][0]="��Ч����";                //����
//      iArray[5][1]="0px";                     //�п�
//      iArray[5][2]=100;                       //�����ֵ
//      iArray[5][3]=3;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[6]=new Array();
//      iArray[6][0]="�䶯����";                //����
//      iArray[6][1]="0px";                     //�п�
//      iArray[6][2]=100;                       //�����ֵ
//      iArray[6][3]=3;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[7]=new Array();
//      iArray[7][0]="�䶯����";                //����
//      iArray[7][1]="0px";                     //�п�
//      iArray[7][2]=100;                       //�����ֵ
//      iArray[7][3]=3;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[8]=new Array();
//      iArray[8][0]="���˷ѽ��ϼ�";           //����
//      iArray[8][1]="100px";                   //�п�
//      iArray[8][2]=100;                       //�����ֵ
//      iArray[8][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[9]=new Array();
//      iArray[9][0]="���˷���Ϣ";              //����
//      iArray[9][1]="80px";                    //�п�
//      iArray[9][2]=100;                       //�����ֵ
//      iArray[9][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[10]=new Array();
//      iArray[10][0]="����״̬";
//      iArray[10][1]="80px";
//      iArray[10][2]=100;
//      iArray[10][3]=0;
//
//      iArray[11]=new Array();
//      iArray[11][0]="����״̬����";
//      iArray[11][1]="0px";
//      iArray[11][2]=100;
//      iArray[11][3]=3;
//
//      iArray[12]=new Array();
//      iArray[12][0]="�˱�״̬";               //����
//      iArray[12][1]="80px";                   //�п�
//      iArray[12][2]=80;                       //�����ֵ
//      iArray[12][3]=0;                        //�Ƿ���������,1��ʾ����0��ʾ������
//
//      iArray[13]=new Array();
//      iArray[13][0]="�˱�״̬����";           //����
//      iArray[13][1]="0px";                    //�п�
//      iArray[13][2]=80;                       //�����ֵ
//      iArray[13][3]=3;                        //�Ƿ���������,1��ʾ����0��ʾ������
//
//      EdorMainGrid = new MulLineEnter( "fm" , "EdorMainGrid" );
//      //��Щ���Ա�����loadMulLineǰ
//      EdorMainGrid.mulLineCount = 0;
//      EdorMainGrid.displayTitle = 1;
//      EdorMainGrid.locked = 1;
//      EdorMainGrid.canSel = 1;
//      EdorMainGrid.hiddenPlus = 1;
//      EdorMainGrid.hiddenSubtraction=1;
//      EdorMainGrid.loadMulLine(iArray);
//
//      EdorMainGrid. selBoxEventFuncName = "showEdorItemList";
//
//      //��Щ����������loadMulLine����
//      //EdorMainGrid.setRowColData(1,1,"asdf");
//  }
//  catch(ex)
//  {
//      alert(ex);
//  }
//}
//
//// ��ȫ��Ŀ�б�ĳ�ʼ��
//function initEdorItemGrid()
//{
//    var iArray = new Array();
//
//  try
//  {
//        iArray[0]=new Array();
//        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
//        iArray[0][1]="30px";                  //�п�
//        iArray[0][2]=30;                      //�����ֵ
//        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[1]=new Array();
//        iArray[1][0]="����������";            //����
//        iArray[1][1]="0px";                   //�п�
//        iArray[1][2]=100;                     //�����ֵ
//        iArray[1][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[2]=new Array();
//        iArray[2][0]="��������";              //����
//        iArray[2][1]="120px";                 //�п�
//        iArray[2][2]=100;                     //�����ֵ
//        iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[3]=new Array();
//        iArray[3][0]="������";                //����
//        iArray[3][1]="110px";                 //�п�
//        iArray[3][2]=100;                     //�����ֵ
//        iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[4]=new Array();
//        iArray[4][0]="�ͻ���";                //����
//        iArray[4][1]="80px";                  //�п�
//        iArray[4][2]=80;                      //�����ֵ
//        iArray[4][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[5]=new Array();
//        iArray[5][0]="���ֺ�";                //����
//        iArray[5][1]="120px";                 //�п�
//        iArray[5][2]=80;                      //�����ֵ
//        iArray[5][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[6]=new Array();
//        iArray[6][0]="������������";              //����
//        iArray[6][1]="0px";                   //�п�
//        iArray[6][2]=100;                     //�����ֵ
//        iArray[6][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
//
//        iArray[7]=new Array();
//        iArray[7][0]="��Ч����";              //����
//        iArray[7][1]="0px";                   //�п�
//        iArray[7][2]=100;                     //�����ֵ
//        iArray[7][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[8]=new Array();
//        iArray[8][0]="�䶯����";              //����
//        iArray[8][1]="0px";                   //�п�
//        iArray[8][2]=100;                     //�����ֵ
//        iArray[8][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[9]=new Array();
//        iArray[9][0]="�䶯����";              //����
//        iArray[9][1]="0px";                   //�п�
//        iArray[9][2]=100;                     //�����ֵ
//        iArray[9][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[10]=new Array();
//        iArray[10][0]="���˷ѽ��ϼ�";       //����
//        iArray[10][1]="80px";                 //�п�
//        iArray[10][2]=100;                    //�����ֵ
//        iArray[10][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[11]=new Array();
//        iArray[11][0]="���˷���Ϣ";           //����
//        iArray[11][1]="60px";                 //�п�
//        iArray[11][2]=100;                    //�����ֵ
//        iArray[11][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[12]=new Array();
//        iArray[12][0]="����״̬";
//        iArray[12][1]="60px";
//        iArray[12][2]=100;
//        iArray[12][3]=0;
//
//        iArray[13]=new Array();
//        iArray[13][0]="����״̬����";
//        iArray[13][1]="0px";
//        iArray[13][2]=100;
//        iArray[13][3]=3;
//
//        iArray[14]=new Array();
//        iArray[14][0]="�˱�״̬";             //����
//        iArray[14][1]="60px";                 //�п�
//        iArray[14][2]=80;                     //�����ֵ
//        iArray[14][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[15]=new Array();
//        iArray[15][0]="�˱�״̬����";         //����
//        iArray[15][1]="0px";                  //�п�
//        iArray[15][2]=80;                     //�����ֵ
//        iArray[15][3]=3;                      //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[16]=new Array();
//        iArray[16][0]="��������";
//        iArray[16][1]="0px";
//        iArray[16][2]=100;
//        iArray[16][3]=3;
//
//        iArray[17]=new Array();
//        iArray[17][0]="����ʱ��";
//        iArray[17][1]="0px";
//        iArray[17][2]=100;
//        iArray[17][3]=3;
//
//        iArray[18]=new Array();
//        iArray[18][0]="��ȫ��Ŀ����";
//        iArray[18][1]="0px";
//        iArray[18][2]=100;
//        iArray[18][3]=3;
//
//        EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" );
//        //��Щ���Ա�����loadMulLineǰ
//        EdorItemGrid.mulLineCount = 0;
//        EdorItemGrid.displayTitle = 1;
//        EdorItemGrid.locked = 1;
//        EdorItemGrid.canSel = 1;
//        EdorItemGrid.hiddenPlus = 1;
//        EdorItemGrid.hiddenSubtraction=1;
//        EdorItemGrid.loadMulLine(iArray);
//        EdorItemGrid.selBoxEventFuncName = "getEdorItemInfo";
//  }
//  catch(ex)
//  {
//      alert(ex);
//  }
//}

        //<!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : BGN -->

        //������Ŀ���в�ѯ MultiLine �ĳ�ʼ��
        function initAppItemGrid()
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
                iArray[1][0] = "������";
                iArray[1][1] = "90px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "95px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "�ͻ���";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "���ֺ�";
                iArray[4][1] = "105px";
                iArray[4][2] = 120;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��ȫ��Ŀ";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "������������";
                iArray[6][1] = "55px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "���˷ѽ��";
                iArray[7][1] = "55px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "����״̬";
                iArray[8][1] = "55px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "�˱�״̬";
                iArray[9][1] = "55px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
            }
            catch (ex)
            {
                alert("�� EdorCancelInit.jsp --> initAppItemGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                AppItemGrid = new MulLineEnter("document", "AppItemGrid");
                AppItemGrid.mulLineCount = 5;
                AppItemGrid.displayTitle = 1;
                AppItemGrid.locked = 1;
                AppItemGrid.hiddenPlus = 1;
                AppItemGrid.hiddenSubtraction = 1;
                AppItemGrid.canChk = 0;
                AppItemGrid.canSel = 1;
                AppItemGrid.chkBoxEventFuncName = ""
                AppItemGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                AppItemGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorCancelInit.jsp --> initAppItemGrid �����з����쳣: ��ʼ���������");
            }
        }

    //<!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : END -->

</script>
