package com.mingo.baselibrary.net.procotol

class BaseResp<T>(val code: Int, val msg: String, val data: T)