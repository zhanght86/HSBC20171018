<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01
 * @date     : 2005-11-13, 2006-03-11
 * @direction: ��ȫ�����Ե����롢��ֹ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                EdorQuery();
                initInputBox();
                initPolGrid() ;
                queryPolInfo();
                queryAutoPayFlag();
            }
            catch (ex)
            {
                alert("�� PEdorTypeAPInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                //�ı���
                document.getElementsByName("EdorAcceptNo")[0].value = top.opener.document.getElementsByName("EdorAcceptNo")[0].value;
                document.getElementsByName("EdorNo")[0].value = top.opener.document.getElementsByName("EdorNo")[0].value;
                document.getElementsByName("EdorType")[0].value = top.opener.document.getElementsByName("EdorType")[0].value;
                document.getElementsByName("ContNo")[0].value = top.opener.document.getElementsByName("ContNo")[0].value;
                //document.getElementsByName("PolNo")[0].value = top.opener.document.getElementsByName("PolNo")[0].value;
                document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
                document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
            }
            catch (ex)
            {
                alert("�� PEdorTypeAPInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }
        
  //�����б�      
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
        iArray[1][0]="�����˿ͻ���";
        iArray[1][1]="60px";
        iArray[1][2]=60;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����������";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="Ͷ���˿ͻ���";
        iArray[3][1]="60px";
        iArray[3][2]=80;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="Ͷ��������";
        iArray[4][1]="60px";
        iArray[4][2]=80;
        iArray[4][3]=0;
        iArray[4][21]=3;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
        iArray[5][2]=80;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="���ѱ�׼";
        iArray[6][1]="60px";
        iArray[6][2]=80;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="60px";
        iArray[7][2]=80;
        iArray[7][3]=7;
        iArray[7][21]=3;
        iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="����";
        iArray[8][1]="50px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        iArray[8][21]=3;

        iArray[9]=new Array();
        iArray[9][0]="PolNo";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=0;
        iArray[9][21]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="����";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=2;
        iArray[10][4]="currency";

      PolGrid = new MulLineEnter("fm", "PolGrid");
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
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

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

