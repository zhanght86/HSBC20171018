package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LBPOAppntSchema;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOCustomerImpartSchema;
import com.sinosoft.lis.tb.DSContBL;
import com.sinosoft.lis.tb.DSContUI;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BQUWConfirmUI {
private static Logger logger = Logger.getLogger(BQUWConfirmUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public BQUWConfirmUI() {}

	// @Main
	public static void main(String[] args)
	{
          DSContUI tGroupPolUI = new DSContUI();
          LBPOContSchema mLCContSchema = new LBPOContSchema();
          LBPOAppntSchema mLCAppntSchema = new LBPOAppntSchema();
          //LDPersonSet mLDPersonSet = new LDPersonSet();
          //LDPersonSchema tLDPersonSchema = new LDPersonSchema();
         // LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
          
          mLCContSchema.setPrtNo("20041117000014");
          mLCContSchema.setAgentCode("8611000433");
          mLCContSchema.setAgentGroup("000000000448");
          mLCContSchema.setManageCom("8611");
          mLCContSchema.setSignCom("8611");
          mLCContSchema.setPolType("0");
          mLCContSchema.setContType("1");
          mLCContSchema.setPolApplyDate("0000-00-00");
          mLCAppntSchema.setAppntName("aaaaaaa");
          mLCAppntSchema.setAppntSex("0");
          mLCAppntSchema.setAppntBirthday("1979-01-01");
          mLCAppntSchema.setIDType("0");
          mLCAppntSchema.setIDNo("422103197901010870");
          mLCAppntSchema.setOccupationCode("1010101");
          mLCAppntSchema.setManageCom("8611");
          //mLDPersonSet.add(tLDPersonSchema);

//          LDPersonSchema tLDPersonSchema1=new LDPersonSchema();
//          tLDPersonSchema1.setName("BBBBa");
//          tLDPersonSchema1.setSex("1");
//          tLDPersonSchema1.setBirthday("1979-01-01");
//          tLDPersonSchema1.setIDType("0");
//          tLDPersonSchema1.setIDNo("422103197901010871");
//
//         mLDPersonSet.add(tLDPersonSchema1);
          LBPOCustomerImpartSet tLCCustomerImpartSet = new LBPOCustomerImpartSet();
          LBPOCustomerImpartSchema tLCCustomerImpartSchema =new LBPOCustomerImpartSchema();
          tLCCustomerImpartSchema.setImpartCode("010");
          tLCCustomerImpartSchema.setImpartVer("001");
          tLCCustomerImpartSchema.setCustomerNoType("A");
          tLCCustomerImpartSchema.setImpartContent("175,20");
          tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
          VData tInputData = new VData();
          GlobalInput tGI = new GlobalInput();
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("GrpNo", mLCContSchema.getPrtNo());
          tTransferData.setNameAndValue("GrpName", "hoho");
          tTransferData.setNameAndValue("mark", "12123");
          tGI.ManageCom = "8611";
          tGI.Operator = "000006";
          tInputData.add(tTransferData);
          //tInputData.add(tLDPersonSchema);
          tInputData.add(tLCCustomerImpartSet);
          tInputData.add(mLCContSchema);
          tInputData.add(mLCAppntSchema);
              //tInputData.add( mLCInsuredSchema);
            tInputData.add(tGI);
            tGroupPolUI.submitData(tInputData,"INSERT||CONT");
	}

	// @Method
	/**
	* 数据提交方法
	* @param: cInputData 传入的数据
	*		  cOperate 数据操作字符串
	* @return: boolean
	**/
	public boolean submitData( VData cInputData, String cOperate )
	{
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		BQUWConfirmBL tBQUWConfirmBL = new BQUWConfirmBL();

		logger.debug("---UI BEGIN---");
		if( tBQUWConfirmBL.submitData( cInputData, mOperate ) == false )
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tBQUWConfirmBL.mErrors );
			return false;
		}
		else
		{
			mResult = tBQUWConfirmBL.getResult();
		}
                logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult()
	{
		return mResult;
	}

}
