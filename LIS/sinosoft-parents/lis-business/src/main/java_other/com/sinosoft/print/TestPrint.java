package com.sinosoft.print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class TestPrint
{
private static Logger logger = Logger.getLogger(TestPrint.class);


	private VData mResult = new VData();

	public TestPrint()
	{

	}

	public boolean getPrintData(String cType)
	{
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		// xmlexport.createDocument("体检通知书", "NewPeNotice.vts", "0"); //
		// 最好紧接着就初始化xml文档
		if ("1".equals(cType))
		{
			xmlexport.createDocument("pdf打印", "NewPeNotice.vts", "0", "1");
		}
		else
		{
			xmlexport.createDocument("xsl测试", "NewPeNotice.vts", "0", "3");
		}

		// 测试用例开始
		xmlexport.createTitle("Company");
		xmlexport.addElement("Company", "ManageCom", "86000000");
		xmlexport.addElement("Company", "ManageName", "安邦人寿成都新都支公司营销服务一部");
		xmlexport.addElement("Company", "Address", "");

		xmlexport.createTitle("Medical");
		xmlexport.addElement("Medical", "CheckItem", "物理检查  尿常规  心电图    ");
		xmlexport.addElement("Medical", "CheckReason", "达标体检");
		xmlexport.addElement("Medical", "NeedLimosis", "");

		xmlexport.createTitle("ContINfo");
		xmlexport.addElement("ContINfo", "ContNo", "物理检查  尿常规  心电图    ");
		xmlexport.addElement("ContINfo", "ContNo2", "达标体检");
		xmlexport.addElement("ContINfo", "ContNo3", "");

		String[] StrCont;

		ListTable tListTable = new ListTable();
		String[] tTitle = new String[3];
		tTitle[0] = "Name";
		tTitle[1] = "Sex";
		tTitle[2] = "Phone";
		tListTable.setName("AppntInfo");

		for (int j = 1; j <= 2; j++)
		{
			StrCont = new String[3];
			StrCont[0] = "投保人姓名0" + j;
			StrCont[1] = "投保人性别1" + j;
			StrCont[2] = "投保人电话2" + j;
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "Appnt");

		tListTable = new ListTable();
		tTitle = new String[3];
		tTitle[0] = "Name";
		tTitle[1] = "Sex";
		tTitle[2] = "Phone";
		tListTable.setName("InsuredInfo");

		for (int j = 1; j <= 2; j++)
		{
			StrCont = new String[3];
			StrCont[0] = "被保人名称0" + j;
			StrCont[1] = "被保人性别1" + j;
			StrCont[2] = "被保人电话2" + j;
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "Insured");

		tListTable = new ListTable();
		tTitle = new String[4];
		tTitle[0] = "Code";
		tTitle[1] = "Param";
		tTitle[2] = "Name";
		tTitle[3] = "Type";
		tListTable.setName("BarCodeInfo");

		for (int j = 1; j <= 2; j++)
		{
			StrCont = new String[4];
			if (j == 1)
			{
				StrCont[0] = PrintManagerBL.CODE_PE;
				StrCont[2] = "CertifyCode";
				StrCont[3] = "BarCode";
			}
			else
			{
				StrCont[0] = "xxxxxxxxxxxxxxx";
				StrCont[2] = "ContNo";
				StrCont[3] = "BarCode";
			}
			StrCont[1] = "BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10";
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "BarCode");

		xmlexport.outputDocumentToFile("c:/", "PutFile" + cType, false); // 输出xml文档到文件

		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * 返回处理信息
	 * @return VData
	 */
	public VData getResult()
	{
		return mResult;
	}

	public static void main(String[] args)
	{
		TestPrint tTestPrint = new TestPrint();
		tTestPrint.getPrintData("1");
		tTestPrint.getPrintData("2");
	}

}
