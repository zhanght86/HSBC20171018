<%
//�������ƣ�AllPBqQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
     //����ҳ��ؼ��ĳ�ʼ����
%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
                              

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {   
	// ������ѯ����
    if (tflag=="0")
    {   
    	document.all('ContNo1').value = tContNo;
    	//alert(tContNo);
    }
    else 
    {
    	document.all('EdorAcceptNo').value = '';
    	document.all('ContNo1').value = tContNo;
    }
  }
  catch(ex)
  {
    alert("��PolBqEdorInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��AllPBqQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initBqEdorGrid();
	getBqEdorGrid();
		//if(tflag=="0") easyQueryClick();
  }
  catch(re)
  {
    alert("AllGBqQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    alert(re);
  }
}

function initBqEdorGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ������0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ������";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=8;
        
        iArray[5]=new Array();
        iArray[5][0]="��Ч����";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=8;
                
        iArray[6]=new Array();
        iArray[6][0]="����״̬";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="����Ա";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
      BqEdorGrid = new MulLineEnter( "document" , "BqEdorGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      BqEdorGrid.mulLineCount = 5;   
      BqEdorGrid.displayTitle = 1;
      BqEdorGrid.canSel =1;
      BqEdorGrid.selBoxEventFuncName ="getBqEdorDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      BqEdorGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      BqEdorGrid.hiddenSubtraction=1;
      BqEdorGrid.loadMulLine(iArray);        
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>