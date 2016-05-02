package com.tanl.kitserver.util;

/**
 * Created by Administrator on 2016/5/2.
 */
public class ServiceResult<T> {

	public void test(T data){
		System.out.println(data.toString());
	}
	public boolean res(){
		return true;
	}
}
