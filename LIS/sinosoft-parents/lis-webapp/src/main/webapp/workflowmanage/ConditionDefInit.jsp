<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--�û�У����-->

<script language="JavaScript">  

		function ondblclickQ(obj1,obj2)
		{
            if(document.all('BusiTypeQ').value == "")
            {
                alert("����ѡ��ҵ������!");
                return false;
            }
			return showCodeList('queryactivityid2',[obj1,obj2],[0,1],null,null,document.all('BusiTypeQ').value,1,'300');
		}
		function ondblclick2(obj1,obj2)
		{
            if(document.all('BusiType').value == "")
            {
                alert("����ѡ��ҵ������!");
                return false;
            }
			//return showCodeList('queryactivityid',[fm.StartActivityIDQ,fm.StartActivityNameQ],[0,1],null,null,'123',1,'300');
            return showCodeList('queryactivityid2',[obj1,obj2],[0,1],null,null,document.all('BusiType').value,1,'300');
		}


function initInpBox()
{

    try
    {
    }
    catch(ex)
    {
        alert("��InvAssOrderbuildDetailInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initTICollectGrid();
    }
    catch(re)
    {
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
} //�ɽ�����ܱ�
function initTICollectGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "ҵ������CODE";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "ҵ������";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "�������";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "�յ�";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "�յ�����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "ת����������CODE";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "ת����������";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "ת������";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "ת����������";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        TICollectGrid = new MulLineEnter("fm", "TICollectGrid");
        //��Щ���Ա�����loadMulLineǰ
        TICollectGrid.mulLineCount = 5;
        TICollectGrid.displayTitle = 1;
        TICollectGrid.hiddenPlus = 1;
        TICollectGrid.hiddenSubtraction = 1;
        TICollectGrid.canSel = 1;
        TICollectGrid.selBoxEventFuncName = "showData";
        TICollectGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
