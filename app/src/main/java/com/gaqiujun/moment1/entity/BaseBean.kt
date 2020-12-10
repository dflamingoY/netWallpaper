package com.gaqiujun.moment1.entity

import java.io.Serializable

class BaseBean : Serializable {
    var id: String? = null
    var title: String? = null
    var url: String? = null
    var count = 0
    var about: String? = null
}