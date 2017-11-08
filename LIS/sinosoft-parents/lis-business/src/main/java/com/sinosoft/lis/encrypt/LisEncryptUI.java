/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.encrypt;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class LisEncryptUI  implements com.sinosoft.service.BusinessService {
private static Logger logger = Logger.getLogger(LisEncryptUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LisEncryptUI() {
	}
	
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		LisEncrypt tLisEncrypt = new LisEncrypt();

		if(cOperate.toLowerCase().equals("encrypt"))
		{
			String tOldPassWord = (String)cInputData.get(0);
			String tPassWord =  tLisEncrypt.encrypt(tOldPassWord);
			this.mInputData = new VData();
			this.mInputData.add(tPassWord);
		}
		
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mInputData;
	}

	public static void main(String[] args) {
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
