<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2007 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zengyg <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-05-24
 * @direction: ��ȫ�����ղ�����ȡ��ʼ������
 ******************************************************************************/
%>                          

<script language="JavaScript">  
function initForm()
{
  try
  {
     initInpBox();
     initPolGrid();
     getPolInfo();
     queryAccData();
     initRemark(); 
     Edorquery();

  }
  catch(re)
  {
    alert("PEdorTypeRCInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{ 
  try
  {   
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;

    var SQL = "select EdorName from lmedoritem where EdorCode = '"+document.all('EdorType').value +"'";
    var arr = easyExecSql(SQL);
    document.all('EdorTypeName').value=arr[0][0];
    //showOneCodeName('edorcode','EdorCode','EdorName');
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }
    var tEdorAcceptNo=document.all("EdorAcceptNo").value;
    try{
        var SQL = "select standbyflag3 from lpedoritem where EdorAcceptNo = '"+tEdorAcceptNo+"'";
        var arr = easyExecSql(SQL);
        if(arr){
            document.all('Remark').value = arr[0][0];  
        } 
    }catch(ex){
        alert("�� PEdorTypeOPInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }      
}
var PolGrid;
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
        iArray[1][0]="�������˺���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="���ִ���";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="150px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="���ѱ�׼";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="60px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��Ч����";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="���屣������";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      //PolGrid.canChk=1;
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