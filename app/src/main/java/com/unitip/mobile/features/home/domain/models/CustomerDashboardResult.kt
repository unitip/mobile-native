package com.unitip.mobile.features.home.domain.models

data class CustomerDashboardResult(
    val needAction: List<Order>,
    val ongoing: List<Order>
)