<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zengyg
 * @version  : 1.00
 * @date     : 2007-03-19
 * @direction: ��ǩ������ҳ���ʼ��JavaScript����
 * modified by pst on 2007 06 19
 ******************************************************************************/
 %>
<%
   String mCurrentDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">


function initForm()
{
	try{
  	//QueryEdorInfo();
    initEdorInfo();
	  initEdorGrid();
	  initQuery();
	  //initCheckInfo();
	}
  catch(ex)
  {
     alert("��PEdorTypePSInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initEdorInfo()
{
  try
  {
  	 //Edorquery();
     document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
     document.all('ContNo').value = top.opener.document.all('ContNo').value;
     
     document.all('EdorType').value = top.opener.document.all('EdorType').value;
     //document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
     document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
     document.all('PolNo').value = top.opener.document.all('PolNo').value;
     document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
     document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;

     showOneCodeName('EdorCode','EdorType','EdorTypeName');

     
     fm.chkS1.value="0";
     fm.chkS2.value="0";
     fm.chkS3.value="0";
     fm.chkS4.value="0";
     fm.chkS5.value="0";
     fm.chkS6.value="0";
     fm.chkS7.value="0";
     fm.chkS8.value="0";
     
     fm.chkO1.value="0";
     fm.chkO2.value="0";
     fm.chkO3.value="0";

  }
  catch(ex)
  {
     alert("��PEdorTypePSInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


/**
 * ���ݲ�������(¼����ѯ)����������ť�Ƿ���ʾ
 */
function EdorQuery()
{
	  //alert(22);
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

function initEdorGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";            		//�п�
        iArray[0][2]=10;            			//�����ֵ
        iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="80px";
        iArray[1][2]=50;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��ȫ������";
        iArray[2][1]="80px";
        iArray[2][2]=160;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="������Ŀ";
        iArray[3][1]="30px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="������Ŀ����";
        iArray[4][1]="80px";
        iArray[4][2]=60;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="������";
        iArray[5][1]="80px";
        iArray[5][2]=60;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="���Ĺ�����������";
        iArray[6][1]="60px";
        iArray[6][2]=70;
        iArray[6][3]=8;

        iArray[7]=new Array();
        iArray[7][0]="������Ч����";
        iArray[7][1]="60px";
        iArray[7][2]=70;
        iArray[7][3]=8;

        

      EdorGrid = new MulLineEnter("fm","EdorGrid" );
      //��Щ���Ա�����loadMulLineǰ
      EdorGrid.mulLineCount = 1;
      EdorGrid.displayTitle = 1;
      EdorGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      EdorGrid.canSel = 1;
      EdorGrid.canChk=0;
      EdorGrid.hiddenSubtraction=1;
      EdorGrid.loadMulLine(iArray);
      EdorGrid.selBoxEventFuncName ="" ;
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>