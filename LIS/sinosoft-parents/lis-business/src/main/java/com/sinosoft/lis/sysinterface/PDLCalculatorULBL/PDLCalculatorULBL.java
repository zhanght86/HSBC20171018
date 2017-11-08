

package com.sinosoft.lis.sysinterface.PDLCalculatorULBL;

import java.io.File;

import com.enterprisedt.util.debug.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


public class PDLCalculatorULBL implements BusinessService {

    /**
     * 错误处理类
     */
    public CErrors mErrors = new CErrors();

    /**
     * 往前面传输数据的容器
     */
    private VData mResults = new VData();

    /**
     * 往后面传输数据的容器
     */
    private VData mInputData;

    /**
     * 内存文件暂存
     */
    private org.jdom.Document myDocument;

    private org.jdom.Element myElement;

    private GlobalInput mGlobalInput = new GlobalInput();

    private TransferData mTransferData = new TransferData();

    private String mFileName = "";// 文件名

    private String mImportPath = "";// 文件保存路径

    private boolean flag = false;
    public Logger logger=Logger.getLogger(PDLCalculatorULBL.class);



    /**
     * 数据操作字符串
     */
    private String mOperate;

    private MMap map = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();

    private String CurrentTime = PubFun.getCurrentTime();

    public boolean submitData(VData cInputData, String cOperate) {

        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;
        // 得到前台传入的数据
        if (!getInputData()) {
            return false;
        }
        // 进行业务处理
        if (!dealData()) {
            return false;
        }
        // 准备往后台的数据
        if (!prepareOutputData()) {
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();

        if (!tSubmit.submitData(mInputData, "")) {
            CError tError = new CError();
            tError.moduleName = "FundWorkCalendarDefUploadBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "数据提交失败。";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }


    // 获取前台传递的数据
    private boolean getInputData() {
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mFileName = (String) mTransferData.getValueByName("FileName");
       // logger.debug("获得的上传文件的名称是" + mFileName);
        this.logger.info("获得的上传文件的名称是" + mFileName);
        mImportPath = (String) mTransferData.getValueByName("ImportPath");
       // logger.debug("获得的上传文件的保存路径是" + mImportPath);
        this.logger.info("获得的上传文件的保存路径是" + mImportPath);
        return true;
    }


    // 业务处理
    private boolean dealData() {
    	Boolean result;
        //进行业务逻辑验证
        if (!this.checkData()) {
        	result = false;
            return false;
        }else{
        	PDLCalculatorULtBL tPDLCalculatorULtBL = new PDLCalculatorULtBL();
        	File file = new File(mImportPath + mFileName);
	        String temp = String.valueOf(System.nanoTime());
			String mNewFileName = mFileName.split("\\.")[0] + "_" + PubFun.getCurrentDate().replace("-", "") + PubFun.getCurrentTime().replace(":", "") + "_" + temp.substring(temp.length() - 5, temp.length()) + "." + mFileName.split("\\.")[1];
			GlobalInput tGlobalInput = new GlobalInput();
			tGlobalInput.Operator="AUTO";
			VData mInputData = new VData();			
			mInputData.add(tGlobalInput);
			if (file.renameTo(new File(mImportPath + mNewFileName))) {
				file.delete();
				result = tPDLCalculatorULtBL.submitData(mImportPath, mNewFileName,mInputData);
			}else {
				result = tPDLCalculatorULtBL.submitData(mImportPath, mFileName,mInputData);
			}
        	logger.debug("*****CRN****:"+result);
			this.logger.info("*****CRN****:"+result);
        	mResults.add(result);
        }
        if(result){
        	return true;
        }else{
        	return false;
        }
    }


    /**
     * 进行业务逻辑验证
     *
     * @return
     */
    private boolean checkData() {

        if (mTransferData == null) {
            CError.buildErr(this, "无导入文件信息，请重新导入!");
            return false;
        }

        if (mFileName == null || mFileName == "") {
            // @@错误处理
            CError.buildErr(this, "没有传入上传文件的名称!");
            return false;
        }

        if (mImportPath == null || mImportPath == "") {
            // @@错误处理
            CError.buildErr(this, "没有传入文件的保存的路径!");
            return false;
        }

        // 校验导入的物理源Xml或EXCEL文件已到指定目的地存在
        if (!this.checkImportFile()) {
            return false;
        }

        return true;
    }

    // 校验上传的文件是否已经保存到对应的路径中
    private boolean checkImportFile() {

        //logger.debug("上传文件的存储路径是" + (mImportPath + mFileName));
    	this.logger.info("上传文件的存储路径是" + (mImportPath + mFileName));
        File tFile = new File(mImportPath + mFileName);
        if (!tFile.exists()) {
            // @@错误处理
            CError.buildErr(this, "文件并没有存储到制定的路径中,上传失败");
            return false;
        }
        return true;
    }


    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "LDExRateImportNewBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }


    private boolean prepareOutputData() {
        try {

            mInputData.clear();
            mInputData.add(map);

        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDExRateImportNewBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    public VData getResult() {
        return mResults;
    }

    public CErrors getErrors() {
        // TODO Auto-generated method stub
        return mErrors;
    }


}

