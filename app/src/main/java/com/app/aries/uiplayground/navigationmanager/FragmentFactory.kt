package com.app.aries.uiplayground.navigationmanager

import androidx.fragment.app.Fragment

interface FragmentFactory{
    fun newInstance(): Fragment
}