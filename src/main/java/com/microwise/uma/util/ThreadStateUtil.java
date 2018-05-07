package com.microwise.uma.util;

import java.lang.Thread.State;


/**
 * 处理线程状态的工具类
 * 
 * @author xu.baoji
 * @date 2013-9-18
 */
public class ThreadStateUtil {
	
	/***
	 * 获得 指定线程的 具体状态
	 * 
	 * @author xu.baoji
	 * @date 2013-9-18
	 *
	 * @param thread
	 * @return
	 */
	public  static String getThreadState(Thread thread){
		State state = thread.getState();
		if (state.equals(State.NEW)) {
			return state.name()+"：未启动线程";
		}else if (state.equals(State.BLOCKED)) {
			return state.name()+"：受阻塞线程";
		}else if (state.equals(State.RUNNABLE)) {
			return state.name()+"：可运行线程";
		}else if (state.equals(State.TERMINATED)) {
			return state.name()+"：已终止线程";
		}else if (state.equals(State.TIMED_WAITING)) {
			return state.name()+"：具有指定等待时间线程";
		}else if (state.equals(State.WAITING)) {
			return state.name()+"：某一等待线程";
		}else {
			return state.name()+"：未知状态的线程";
		}
	}
	
	/***
	 * 获得 指定线程的状态的 详细信息
	 * 
	 * @author xu.baoji
	 * @date 2013-9-18
	 *
	 * @param state
	 * @return
	 */
	public  static String getThreadState(State state){
		if (state.equals(State.NEW)) {
			return state.name()+"：未启动线程";
		}else if (state.equals(State.BLOCKED)) {
			return state.name()+"：受阻塞线程";
		}else if (state.equals(State.RUNNABLE)) {
			return state.name()+"：可运行线程";
		}else if (state.equals(State.TERMINATED)) {
			return state.name()+"：已终止线程";
		}else if (state.equals(State.TIMED_WAITING)) {
			return state.name()+"：具有指定等待时间线程";
		}else if (state.equals(State.WAITING)) {
			return state.name()+"：某一等待线程";
		}else {
			return state.name()+"：未知状态的线程";
		}
	}

}
