<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT language="JavaScript1.2" src="./CVar.js"></SCRIPT>
<SCRIPT language="JavaScript1.2" >

//gVars=new CVar(1,new Array(),"adsf");
gVars =new CVar();
gVars1=new CVar();

gVCode  =new CVar();    //Code���е�������Ϣ
gVSwitch=new CVar();    //����ʾ����ʱ������������Ϣ

//��ʼ����ʾ���ݽ�������
gVSwitch.addVar("ShowCodeFrame","","");
gVSwitch.addVar("ShowCodeObj","","");
gVSwitch.addVar("ShowCodeOrder","","");
gVSwitch.addVar("ShowCodeX","",0);
gVSwitch.addVar("ShowCodeY","",0);
gVSwitch.addVar("ShowCodeError","","");
gVSwitch.addVar("ShowCodeErrorCode","","");

cpVar = new CVar();	//�洢���ҵľ���
dfVar = new CVar();	//�洢һЩĬ�ϱ���

try {
  top.achieveVD = true;   //�����ж�ҳ���ʼ�����
  //top.alert("achieveVD:" + achieveVD);
} catch(ex) {}
</SCRIPT>

</head>
</html>