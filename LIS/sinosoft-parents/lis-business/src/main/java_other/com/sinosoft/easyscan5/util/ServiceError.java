package com.sinosoft.easyscan5.util;

public class ServiceError {

	// 模块名称
		private String modalName;
		// service类名
		private String serviceClassName;
		// 方法名称
		private String methodName;
		//错误码
		private String errorCode;
		// 错误信息
		private String errorMessage;

		public String getModalName() {
			return modalName;
		}

		public void setModalName(String modalName) {
			this.modalName = modalName;
		}

		public String getServiceClassName() {
			return serviceClassName;
		}

		public void setServiceClassName(String serviceClassName) {
			this.serviceClassName = serviceClassName;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
}
