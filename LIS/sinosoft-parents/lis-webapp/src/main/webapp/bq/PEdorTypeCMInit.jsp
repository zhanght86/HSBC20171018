<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-12-3
 * @direction: �ͻ���Ҫ���ϱ��
 ******************************************************************************/
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

function initInpBox()
{

  try
  {
    Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('CustomerNo').value = top.opener.document.all('InsuredNo').value;    //XinYQ modified on 2006-03-29 : old : document.all('CustomerNo').value = top.opener.document.all('CustomerNoBak').value
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeCMInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeCMInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initInpCustomerInfo();
    initContNewGrid();
    queryRelationCont();
    initInpPCustomerInfo();
  }
  catch(re)
  {
    alert("PEdorTypeCMInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}



function initContNewGrid()
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
        iArray[1][0]="��������";
        iArray[1][1]="100px";
        iArray[1][2]=60;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�����˿ͻ���";
        iArray[2][1]="100px";
        iArray[2][2]=60;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��Ч����";
        iArray[3][1]="100px";
        iArray[3][2]=80;
        iArray[3][3]=8;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="���ѱ�׼";
        iArray[4][1]="100px";
        iArray[4][2]=80;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";
       

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";
        
        iArray[6]=new Array();
				iArray[6][0]="����";
				iArray[6][1]="60px";
				iArray[6][2]=100;
				iArray[6][3]=2;
				iArray[6][4]="currency";


      ContNewGrid = new MulLineEnter("fm", "ContNewGrid");
      ContNewGrid.mulLineCount = 1;
      ContNewGrid.displayTitle = 1;
      ContNewGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ContNewGrid.hiddenSubtraction=1;
      ContNewGrid.loadMulLine(iArray);
      ContNewGrid.selBoxEventFuncName ="" ;
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}





</script>