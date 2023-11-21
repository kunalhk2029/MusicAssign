package com.app.musicassign.business.domain.core

sealed class UIComponentState {
    object Visible:UIComponentState()
    object InVisible:UIComponentState()
    object Collapsed:UIComponentState()
    object Expanded:UIComponentState()
}