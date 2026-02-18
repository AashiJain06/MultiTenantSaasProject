package com.aashi.saas.context;

public class TenantContext {
	private static final ThreadLocal<Long> CURRENT_THREAD = new ThreadLocal<>();
	public static void setTenantId(Long tenantId)
	{
		CURRENT_THREAD.set(tenantId);
	}
   public static long getTenantId()
   {
	   return CURRENT_THREAD.get();
   }
   public static void clear()
   {
	   CURRENT_THREAD.remove();
   }
}
