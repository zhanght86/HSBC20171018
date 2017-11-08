package com.sinosoft.lis.list;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class SinCardPassWordBL {
private static Logger logger = Logger.getLogger(SinCardPassWordBL.class);

	/**
	 * @对输入的自主卡单产生相应的密码
	 */
	private int cardNum;
	private String startNo;
	private String cOperate;
	private GlobalInput mGlobalInput = new GlobalInput();// 全局数据
	public CErrors mErrors = new CErrors();
	
	
	public boolean  submitData(VData cInputData, String cOperate)
	{
		try
		{
		if(!getInputData(cInputData))
		{
			CError.buildErr(this, "获取数据信息失败");
			return false;
		}
		if(!dealData())
		{
			CError.buildErr(this, "处理业务数据失败");
			return false;
		}
		}
		catch (Exception e) {
			CError.buildErr(this, e.toString());
		}
		return true;
	}
	private boolean getInputData(VData cInputData)
	{
		mGlobalInput= (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		startNo=(String)mTransferData.getValueByName("StartNo");
		cardNum=Integer.parseInt((String)mTransferData.getValueByName("cardNum"));
		if(mGlobalInput==null || "".equals(mGlobalInput))
		{
			CError.buildErr(this, "获取全局信息失败");
			return false;
		}
		if(startNo==null || "".equals(startNo))
		{
			CError.buildErr(this, "获取卡单起始号信息失败");
			return false;			
		}
		if(startNo.length()!=20)
		{
			CError.buildErr(this, "卡单号码长度不为20位，请核实");
			return false;
		}
		
		if( cardNum<=0)
		{
			CError.buildErr(this, "获取卡单数量信息失败");
			return false;	
		}
		if(cardNum>10000)
		{
			CError.buildErr(this, "卡单数量不能超过10000张");
			return false;
		}
		return true;
	}
	private boolean checkData()
	{
		return true;
	}
	
	
	
	private boolean dealData() throws Exception
	{
		String[][] tableHead = new String[1][2];
		String[][] cardData = new String[cardNum][2];
		tableHead[0][0]="卡    号";
		tableHead[0][1]="密    码";
		
		DESPlus tDESPlus = new DESPlus();
		int noLength = startNo.length();
		
		for(int i=1;i<=cardNum;i++)
		{
			int length = String.valueOf(cardNum).length();
			
			String Symbol=startNo.substring(0,(noLength-length));
			int startint=Integer.parseInt(startNo.substring((noLength-length-1)));
		
			String s = PubFun.LCh(String.valueOf(startint+i-1), "0", length);
			//获取当前卡单号码
			String tNo = Symbol + s;	
			logger.debug("卡号---------"+tNo);
			//获取卡号密码
			String newPassword=tDESPlus.getEncryptKey(tNo);
			cardData[i-1][0]=tNo;
			cardData[i-1][1]=newPassword;
			
		}
		

		// 获取生成文件的路径
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("CreatCardPWPath");
		if (!tLDSysVarDB.getInfo()) {
			CError.buildErr(this, "无法获得生成文件的路径！");
			return false;
		}
//		String tFileName = tLDSysVarDB.getSysVarValue()+ "List_" +certifycode+  mGlobalInput.Operator+tMakeTime+ ".xls.z";
		String tFileName= tLDSysVarDB.getSysVarValue()+ "List_" +startNo+ "_"+ mGlobalInput.Operator+ ".xls";
		HashReport tHashReport = new HashReport();
		if (!tHashReport.outputArrayToFile(tFileName, tableHead, cardData)) {
			CError.buildErr(this, tHashReport.mErrors.getFirstError());
			return false;
		}
		logger.debug("File : " + tFileName + " Created !");
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SinCardPassWordBL tCardPassWordBL = new SinCardPassWordBL();
		GlobalInput mG = new GlobalInput();
		mG.ComCode="86";
		mG.Operator="001";
		String mStartno="86728004110400000004";
		String cardnum="2";
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("StartNo", mStartno);
		mTransferData.setNameAndValue("cardNum", cardnum);
		VData mData = new VData();
		mData.add(mG);
		mData.add(mTransferData);
		if(!tCardPassWordBL.submitData(mData, ""))
		{
			logger.debug("fail--------");
		}
		logger.debug("end---------");
		
	}

}
