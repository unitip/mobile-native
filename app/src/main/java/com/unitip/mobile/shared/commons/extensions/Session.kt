package com.unitip.mobile.shared.commons.extensions

import com.unitip.mobile.shared.domain.models.Session

fun Session?.isCustomer(): Boolean {
    if (this == null) return false
    return role == "customer"
}

fun Session?.isDriver(): Boolean {
    if (this == null) return false
    return role == "driver"
}