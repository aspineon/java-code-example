package bonpay.osgi.launcher;

import bonpay.osgi.service.interf.IModuleExecutor;

/** ������, ������� ������ IModuleExecutor */
public interface IModuleExecutorAware {
	
	/** @return �������� IModuleExecutor */
	public IModuleExecutor getModuleExecutor();
}
