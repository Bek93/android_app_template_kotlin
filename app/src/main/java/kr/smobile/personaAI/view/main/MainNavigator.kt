package kr.smobile.personaAI.view.main

import kr.smobile.personaAI.base.BaseNavigator
import kr.smobile.personaAI.utils.Tab

interface MainNavigator :BaseNavigator {


    fun onNavigationTabSelected(tab: Tab) {

    }

    fun hideCurrentTab(tab: Tab) {

    }
}
