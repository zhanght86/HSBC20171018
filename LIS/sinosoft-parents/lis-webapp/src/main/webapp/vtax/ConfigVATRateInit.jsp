<%
/*******************************************************************************
 * <p>Title       : ��ֵ˰˰��</p>
 * <p>Description : ˰������</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">
var turnPage = new turnPageClass(); 
var FeeTypeCode='<%=request.getParameter("FeeTypeCode")%>';
var ConfigID = '<%=request.getParameter("configID")%>';
strSql ="SELECT FeeTypeName FROM LDVATConfig WHERE id = '"+ConfigID+"' AND FeeTypeCode='"+FeeTypeCode+"'" ;
var FeeTypeName = easyExecSql(strSql );
function initForm()
{
  try
  {
    initInpBox();
    initTaxRateGrid(); 
  }
  catch(re)
  {
    alert("��ʼ���������!-initInpBox");
  }
  try {
		query(); 
	
		}catch(e) {
			alert("TaxRateGrid error");
		}
}

function query()
{

    mySql = new SqlClass();
    mySql.setResourceName("vrate.VatRate");
    mySql.setSqlId("VATRateSql2");
    mySql.addSubPara(FeeTypeCode);   
    mySql.addSubPara(ConfigID);
    initTaxRateGrid();
	turnPage.queryModal(mySql.getString(), TaxRateGrid);
}

function initInpBox()
{ 
  try
  {               
      document.all('TFeeTypeCode').value= FeeTypeCode;
      document.all('ConfigID').value = ConfigID;
      document.all('TFeeTypeName').value = FeeTypeName;
  }
  catch(ex)
  {
    alert("��ConfigVATRateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initTaxRateGrid()
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
      iArray[1][0]="��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������������";    	                //����
      iArray[2][1]="80px";            		        //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="������";    	                //����
      iArray[3][1]="80px";            		        //�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1]="80px";         			//�п�
      iArray[4][2]=10;          			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="˰��";    	                //����
      iArray[5][1]="80px";            		        //�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[6]=new Array();
      iArray[6][0]="����";    	                //����
      iArray[6][1]="80px";            		        //�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[7]=new Array();
      iArray[7][0]="ֹ��";    	                //����
      iArray[7][1]="80px";            		        //�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[8]=new Array();
      iArray[8][0]="��¼ID";    	                //����
      iArray[8][1]="60px";            		        //�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      
      TaxRateGrid = new MulLineEnter( "fm" , "TaxRateGrid" );

      //��Щ���Ա�����loadMulLineǰ
      TaxRateGrid.mulLineCount = 5;
      TaxRateGrid.displayTitle = 1;
      TaxRateGrid.canChk =0;
      TaxRateGrid.canSel =1;
      TaxRateGrid.selBoxEventFuncName ="showVATRateDetail";
      TaxRateGrid.locked =1;            //�Ƿ�������1Ϊ���� 0Ϊ������
      TaxRateGrid.hiddenPlus=1;        //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������ 
      TaxRateGrid.hiddenSubtraction=1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������  
      TaxRateGrid.recordNo=0;         //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
      TaxRateGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }



</script>