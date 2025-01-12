package com.unitip.mobile.shared.commons.extensions

import com.unitip.mobile.shared.domain.models.Session

fun Session.isCustomer() = role == "customer"
fun Session.isDriver() = role == "driver"