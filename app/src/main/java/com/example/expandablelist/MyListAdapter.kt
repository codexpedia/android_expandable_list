package com.example.expandablelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

import java.util.ArrayList

class MyListAdapter(private val context: Context, private val deptList: ArrayList<HeaderInfo>) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val productList = deptList[groupPosition].productList
        return productList[childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean,
                              view: View?, parent: ViewGroup): View {
        var view = view

        val detailInfo = getChild(groupPosition, childPosition) as DetailInfo
        if (view == null) {
            val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = infalInflater.inflate(R.layout.child_row, null)
        }

        val sequence = view!!.findViewById(R.id.sequence) as TextView
        sequence.setText(detailInfo.sequence.trim { it <= ' ' } + ") ")
        val childItem = view.findViewById(R.id.childItem) as TextView
        childItem.setText(detailInfo.name.trim { it <= ' ' })

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val productList = deptList[groupPosition].productList
        return productList.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return deptList[groupPosition]
    }

    override fun getGroupCount(): Int {
        return deptList.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isLastChild: Boolean, view: View?,
                              parent: ViewGroup): View {
        var view = view

        val headerInfo = getGroup(groupPosition) as HeaderInfo
        if (view == null) {
            val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inf.inflate(R.layout.group_heading, null)
        }

        val heading = view!!.findViewById(R.id.heading) as TextView
        heading.setText(headerInfo.name!!.trim { it <= ' ' })

        return view
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}