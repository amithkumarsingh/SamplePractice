package com.vam.whitecoats.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vam.whitecoats.R
import com.vam.whitecoats.core.models.SearchedDrugItem
import com.vam.whitecoats.ui.interfaces.SearchDrugItemClick
import com.wang.avi.AVLoadingIndicatorView

class DrugSearchRecyclerAdapter(_listener: SearchDrugItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0
    var drugSearchItemList: ArrayList<SearchedDrugItem?> = ArrayList()
    var listener:SearchDrugItemClick

    init {
         listener = _listener;
    }

    override fun getItemCount(): Int {
        return if (drugSearchItemList != null) {
            drugSearchItemList.size
        } else {
            0
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder != null) {
            if (holder is DataObjectHolder) {
                val viewHolder = holder
                val dataModel: SearchedDrugItem? = drugSearchItemList.get(position)
                if (dataModel != null) {
                    viewHolder.drugName.text = dataModel.itemName
                    var itemtype:String?=""
                    var generic="Generic:"+dataModel.generic
                    if(dataModel.itemType.equals("D",true)){
                        itemtype="Drug"
                        generic=""
                    }else if(dataModel.itemType.equals("B",true)){
                        itemtype="by "+dataModel.manufacturer
                    }else if(dataModel.itemType.equals("O",true)){
                        itemtype="Combo Drug"
                        generic=""
                    }else if(dataModel.itemType.equals("V",true)){
                        itemtype="Vitamin"
                        generic=""
                    } else if(dataModel.itemType.equals("S",true)){
                        itemtype="Drug Subclass"
                        generic=""
                    } else if(dataModel.itemType.equals("C",true)){
                        itemtype="Drug Class"
                        generic=""
                    }
                    viewHolder.drugType.text=itemtype
                    if(generic.isEmpty()){
                        viewHolder.genricName.visibility=GONE
                    }else{
                        viewHolder.genricName.visibility= VISIBLE
                        viewHolder.genricName.text=generic
                    }
                    //viewHolder.genricName.text=generic
                    viewHolder.drugSearchItem.setOnClickListener {
                        listener.onSearchItemClick(dataModel)
                    }
                }


            } else if (holder is ExploreAdapter.ProgressViewHolder) {
                holder.progressBar.setPadding(0, 40, 0, 40)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder?
        if (viewType == VIEW_ITEM) {
            val view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drug_searched_item_row, parent, false)
            viewHolder = DataObjectHolder(view)
        } else {
            val view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false)
            viewHolder = ExploreAdapter.ProgressViewHolder(view)
        }
        return viewHolder!!
    }

    class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drugName: TextView
        val drugType: TextView
        val genricName: TextView
        val drugSearchItem: ConstraintLayout

        init {
            drugName = itemView.findViewById(R.id.tv_drug_name)
            drugType = itemView.findViewById(R.id.tv_drug_type)
            genricName = itemView.findViewById(R.id.tv_genric_name)
            drugSearchItem = itemView.findViewById(R.id.drug_search_item_layout)
        }
    }

    fun setDataList(data: ArrayList<SearchedDrugItem?>) {
        drugSearchItemList = data
        notifyDataSetChanged()
    }

    class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var progressBar: AVLoadingIndicatorView
        init {
            progressBar = v.findViewById<View>(R.id.avIndicator) as AVLoadingIndicatorView
        }
    }

    fun addDummyItemToList() {
        drugSearchItemList.add(null)
        notifyItemInserted(this.drugSearchItemList.size)
    }

    fun removeDummyItemFromList() {
        drugSearchItemList.remove(null)
        notifyItemRemoved(drugSearchItemList.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (drugSearchItemList.get(position) == null) VIEW_PROG else VIEW_ITEM
    }

}