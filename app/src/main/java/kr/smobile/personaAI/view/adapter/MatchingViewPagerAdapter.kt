package kr.smobile.personaAI.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MatchingViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragmentList: ArrayList<Fragment>,
    private val titleList: ArrayList<String>
) : FragmentStatePagerAdapter(fragmentManager) {


    override fun getCount(): Int {
        return fragmentList.size
    }
    override fun getItem(p0: Int): Fragment {
        return fragmentList[p0]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}
