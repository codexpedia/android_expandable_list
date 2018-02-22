package com.example.expandablelist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import android.widget.Spinner
import android.widget.Toast

import java.util.ArrayList
import java.util.LinkedHashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val myDepartments = LinkedHashMap<String, HeaderInfo>()
    private val deptList = ArrayList<HeaderInfo>()

    private var listAdapter: MyListAdapter? = null
    private var myList: ExpandableListView? = null

    private val myListItemClicked = ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
        val headerInfo = deptList[groupPosition]
        val detailInfo = headerInfo.productList[childPosition]
        Toast.makeText(baseContext, "Clicked on Detail " + headerInfo.name
                + "/" + detailInfo.name, Toast.LENGTH_LONG).show()
        false
    }

    private val myListGroupClicked = ExpandableListView.OnGroupClickListener { parent, v, groupPosition, id ->
        //get the group header
        val headerInfo = deptList[groupPosition]
        //display it or do something with it
        Toast.makeText(baseContext, "Child on Header " + headerInfo.name!!,
                Toast.LENGTH_LONG).show()

        false
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById(R.id.department) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.dept_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        loadData()

        myList = findViewById(R.id.myList) as ExpandableListView
        listAdapter = MyListAdapter(this, deptList)
        myList!!.setAdapter(listAdapter)

        expandAll()

        val add = findViewById(R.id.add) as Button
        add.setOnClickListener(this)

        myList!!.setOnChildClickListener(myListItemClicked)
        myList!!.setOnGroupClickListener(myListGroupClicked)
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.add -> {
                val spinner = findViewById(R.id.department) as Spinner
                val department = spinner.selectedItem.toString()
                val editText = findViewById(R.id.product) as EditText
                val product = editText.text.toString()
                editText.setText("")

                val groupPosition = addProduct(department, product)
                listAdapter!!.notifyDataSetChanged()

                collapseAll()
                myList!!.expandGroup(groupPosition)
                myList!!.setSelectedGroup(groupPosition)
            }
        }
    }


    private fun expandAll() {
        val count = listAdapter!!.groupCount
        for (i in 0 until count) {
            myList!!.expandGroup(i)
        }
    }

    private fun collapseAll() {
        val count = listAdapter!!.groupCount
        for (i in 0 until count) {
            myList!!.collapseGroup(i)
        }
    }

    private fun loadData() {

        addProduct("Apparel", "Activewear")
        addProduct("Apparel", "Jackets")
        addProduct("Apparel", "Shorts")

        addProduct("Beauty", "Fragrances")
        addProduct("Beauty", "Makeup")

    }

    private fun addProduct(department: String, product: String): Int {

        var groupPosition = 0

        var headerInfo: HeaderInfo? = myDepartments[department]
        if (headerInfo == null) {
            headerInfo = HeaderInfo()
            headerInfo.name = department
            myDepartments.put(department, headerInfo)
            deptList.add(headerInfo)
        }

        val productList = headerInfo.productList
        var listSize = productList.size
        listSize++

        val detailInfo = DetailInfo()
        detailInfo.sequence = listSize.toString()
        detailInfo.name = product
        productList.add(detailInfo)
        headerInfo.productList = productList

        groupPosition = deptList.indexOf(headerInfo)
        return groupPosition
    }
}
