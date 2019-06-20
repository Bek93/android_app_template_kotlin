package kr.smobile.personaAI.view.main

import kr.smobile.personaAI.utils.Tab

interface MainNavigator {


    open fun onNavigationTabSelected(tab: Tab) {

    }

    open fun hideCurrentTab(tab: Tab) {

    }
}
