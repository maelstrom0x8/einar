package com.maelstrom.config;

public class TenantContext
{
	private static final ThreadLocal<Integer> currentTenant = new ThreadLocal<>();

	public static void setCurrentTenant(Integer tenant)
	{
		currentTenant.set(tenant);
	}

	public static Integer getCurrentTenant()
	{
		return currentTenant.get();
	}

	public static void clear()
	{
		currentTenant.remove();
	}
}
