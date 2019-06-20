package kr.smobile.personaAI.view.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import kr.smobile.personaAI.R


class SlideGuidePagerAdapter(private var context: Context) : PagerAdapter() {


    var titleArrayList = ArrayList<String>()

    init {
        titleArrayList.add("페르소나 AI는 간단한 말로 표정을 담아 만화로 소통하는 서비스 입니다.")
        titleArrayList.add("페르소나 AI는 간단한 말로 표정을 담아 만화로 소통하는 서비스 입니다.")
        titleArrayList.add("그리고 말로 표정을 담아 표현하는 주인공이 바로 당신의 페르소나 입니다.")
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val title = titleArrayList[position]
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.layout_guide, container, false)
        layout.findViewById<TextView>(R.id.title).text = title
        container.addView(layout);
        return layout
    }


    override fun getCount(): Int {
        return 3
    }


    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViewAt(position)
    }


}
