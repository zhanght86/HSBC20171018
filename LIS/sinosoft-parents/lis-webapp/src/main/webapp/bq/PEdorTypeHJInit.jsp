<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : �޻� <luohui@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-13, 2006-11-01, 2006-11-22
 * @direction: �ָ����ѳ�ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var CustomerGrid;    //ȫ�ֱ���, �����ͻ����

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            { 
                EdorQuery();
                //initHiddenArea();       
                initInputBox();
                initCustomerGrid();
                initPolGrid();
                getCustomerGrid(); 
                showRiskInfo();
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initForm �����з����쳣: ��ʼ���������");
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
                document.getElementsByName("PolNo")[0].value = top.opener.document.getElementsByName("PolNo")[0].value;
                document.getElementsByName("InsuredNo")[0].value = top.opener.document.getElementsByName("InsuredNo")[0].value;
                document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
                document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
                showOneCodeName("PEdorType", "EdorType", "EdorTypeName");
                showOneCodeName("HesitateFlag", "HesitateFlag", "HesitateFlagName");
            }
            catch (ex)
            {
                alert("�� PEdorTypeHJInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }
        function showRiskInfo()
        {
            
            var tContNo=document.all("ContNo").value;
            if(tContNo!=null&&tContNo!="")
            {        
                var strSQL = "select a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.prem,a.cvalidate,a.enddate from lcpol a,lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' order by a.PolNo asc "; 
                turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
                //�ж��Ƿ��ѯ�ɹ�
                if (!turnPage.strQueryResult) 
                {
                   return;
                }
                //��ѯ�ɹ������ַ��������ض�ά����
                turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
                //���ó�ʼ������MULTILINE����
                turnPage.pageDisplayGrid = PolGrid;    
                //����SQL���
                turnPage.strQuerySql = strSQL; 
                //���ò�ѯ��ʼλ��
                turnPage.pageIndex = 0;  
                //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
                arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
                //����MULTILINE������ʾ��ѯ���
                displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
            }

        }
        //�ͻ�������Ϣ�б�
        
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="90px";
        iArray[2][2]=50;
        iArray[2][3]=0;
        iArray[2][21]=2;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=2;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=2;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="150px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //��Щ���Ա�����loadMulLineǰ
        CustomerGrid.mulLineCount = 0;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.selBoxEventFuncName = "";
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorTypeFTInit.jsp --> initCustomerGrid �����з����쳣:��ʼ���������");
    }
    
}
function initPolGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="���ִ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";					//����1
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";         			//����2
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����8
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";     //����6
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ѱ�׼";         		//����8
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����5
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[8]=new Array();
      iArray[8][0]="����ֹ��";         		//����5
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ        
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel=0;
      PolGrid.hiddenPlus = 1; 
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName ="";
      PolGrid.loadMulLine(iArray);  
      PolGrid.detailInfo="������ʾ��ϸ��Ϣ";      
      PolGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
      
      }
}
    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

