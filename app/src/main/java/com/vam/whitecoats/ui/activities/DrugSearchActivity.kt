package com.vam.whitecoats.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.vam.whitecoats.App_Application
import com.vam.whitecoats.R
import com.vam.whitecoats.constants.RestApiConstants
import com.vam.whitecoats.core.models.SearchedDrugItem
import com.vam.whitecoats.core.realm.RealmManager
import com.vam.whitecoats.databinding.DrugSearchActivityBinding
import com.vam.whitecoats.ui.adapters.DrugSearchRecyclerAdapter
import com.vam.whitecoats.ui.interfaces.SearchDrugItemClick
import com.vam.whitecoats.utils.AppUtil
import com.vam.whitecoats.utils.RestUtils
import com.vam.whitecoats.utils.SimpleDividerItemDecoration
import com.vam.whitecoats.viewmodel.DrugSearchViewModel
import com.vam.whitecoats.viewmodel.DrugSearchViewModelFactory
import io.realm.Realm
import org.json.JSONException
import org.json.JSONObject

class DrugSearchActivity : BaseActionBarActivity(){
    private var navigationFrom: String? = null
    private lateinit var searchString: String
    private lateinit var drugSearchAdapter: DrugSearchRecyclerAdapter
    lateinit var drugSearchViewModel: DrugSearchViewModel
    var page_no:Int=0
    private var isListExhausted = false
    private var loading = false
    private var realm: Realm? = null
    private var realmManager: RealmManager? = null

    override fun setCurrentActivity() {
        App_Application.setCurrentActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drug_search_activity)

        mInflater = LayoutInflater.from(this)
        mCustomView = mInflater.inflate(R.layout.drug_search_edt_layout, null)
        var searchEdt = mCustomView.findViewById<View>(R.id.et_search_drug_action_bar) as EditText
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar.setDisplayShowHomeEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayUseLogoEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.customView = mCustomView

        navigationFrom=intent.getStringExtra("NavigationFrom")
        realm = Realm.getDefaultInstance()
        realmManager = RealmManager(this)
        //searchEdt.requestFocusFromTouch()
        //searchEdt.requestFocus()
        val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        searchEdt.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (AppUtil.isConnectingToInternet(this)) {
                    if (v.text.toString().trim().isEmpty()) {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage(R.string.empty_search_keyword)
                        builder.setCancelable(true)
                        builder.setPositiveButton("ok") { dialog, which -> }.create().show()
                    } else if (v.text.length < 2) {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Please enter minimum 2 characters for search")
                        builder.setCancelable(true)
                        builder.setPositiveButton("ok") { dialog, which -> }.create().show()
                    } else {
                        page_no = 0
                        searchEdt.clearFocus()
                        im.hideSoftInputFromWindow(searchEdt.getWindowToken(), 0)
                        if (AppUtil.isConnectingToInternet(this@DrugSearchActivity)) {
                            searchString = v.text.toString()
                            val jsonObjectEvent = JSONObject()
                            try {
                                jsonObjectEvent.put("DocID", realmManager!!.getUserUUID(realm))
                                var eventName = ""
                                if (navigationFrom != null && navigationFrom.equals("DrugClassActivity", ignoreCase = true)) {
                                    eventName =  "DrugClassSearchInitiated"
                                } else if (navigationFrom != null && navigationFrom.equals("DrugSubClassActivity", ignoreCase = true)) {
                                    eventName =   "DrugSubClassSearchInitiated"
                                } else if(navigationFrom != null && navigationFrom.equals("DrugsActivity", ignoreCase = true))  {
                                    eventName =   "DrugNameSearchInitiated"
                                }
                                jsonObjectEvent.put("SearchString", searchString)
                                AppUtil.logUserActionEvent(realmManager!!.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),this@DrugSearchActivity)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                            drugSearchViewModel.displayLoader()
                            setRequestData(v.text.toString(), page_no)
                            performSearch()
                        }

                    }
                }
                return@OnEditorActionListener true
            }
            false
        })

        val factory = DrugSearchViewModelFactory()
        drugSearchViewModel = ViewModelProviders.of(this, factory)[DrugSearchViewModel::class.java]
        drugSearchViewModel.setIsEmptyMsgVisibility(false)

        val searchDrugActivityBinding: DrugSearchActivityBinding = DataBindingUtil.setContentView(this@DrugSearchActivity, R.layout.drug_search_activity)
        drugSearchAdapter = DrugSearchRecyclerAdapter(object : SearchDrugItemClick {
            override fun onSearchItemClick(drugItem: SearchedDrugItem) {
                if (AppUtil.isConnectingToInternet(this@DrugSearchActivity)) {
                    if (drugItem.itemType.equals("s", true)) {
                        val intent = Intent(this@DrugSearchActivity, DrugsActivity::class.java)
                        intent.putExtra("drugSubClassId", drugItem.itemId)
                        startActivity(intent)
                    } else if (drugItem.itemType.equals("c", true)) {
                        val intent = Intent(this@DrugSearchActivity, DrugSubClassActivity::class.java)
                        intent.putExtra("drugClassId", drugItem.itemId)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@DrugSearchActivity, DrugDetailsActivity::class.java)
                        if (drugItem.itemType.equals("b", true)) {
                            intent.putExtra("isBrandInfo", true)
                        } else {
                            intent.putExtra("isBrandInfo", false)
                        }
                        intent.putExtra("drugId", drugItem.itemId)
                        intent.putExtra("drugName", drugItem.itemName)
                        intent.putExtra("genericName", drugItem.generic)
                        startActivity(intent)
                    }
                }
            }
        })

        searchDrugActivityBinding.setAdapter(drugSearchAdapter)
        searchDrugActivityBinding.setViewModel(drugSearchViewModel)
        searchDrugActivityBinding.setLifecycleOwner(this)
        var searchResultsList = searchDrugActivityBinding.rvDrugsList
        searchResultsList.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        //searchResultsList.layoutManager=linearLayoutManager
        searchResultsList.setLayoutManager(linearLayoutManager)
        searchResultsList.addItemDecoration(SimpleDividerItemDecoration(this))


        searchEdt.setupClearButtonWithAction()
        Handler().postDelayed({
            //searchEdt.requestFocusFromTouch()
            searchEdt.requestFocus()
            showSoftKeyboard(searchEdt)
        }, 500)


        searchResultsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) //check for scroll down
                {
                    if (isListExhausted || !AppUtil.isConnectingToInternet(this@DrugSearchActivity)) {
                        return
                    }
                    val visibleItemCount = recyclerView.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    if (!loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            drugSearchAdapter.addDummyItemToList()
                            loading = true
                            //Do pagination.. i.e. fetch new data
                            page_no += 1
                            setRequestData(searchString, page_no)
                            performSearch()
                        } else {
                            loading = false
                        }
                    }
                }
            }
        })

        drugSearchViewModel.isListExhausted().observe(this, Observer<Boolean> { aBoolean ->
            isListExhausted = aBoolean
        })

    }

    private fun performSearch() {
        drugSearchViewModel.getSearchedDrugs().observe(this, Observer {
            if (it != null) {
                if (it.error == null) {
                    var drugsList = it.drugsList
                    drugSearchViewModel.displayUIBasedOnCount(drugsList.size)
                    //var filteredItems = removeDuplicateItems(drugsList)
                    drugSearchAdapter.setDataList(removeDuplicateItems(drugsList))
                } else {
                    if (AppUtil.isJSONValid(it.error)) {
                        try {
                            val jsonObject = JSONObject(it.error)
                            if (jsonObject.optString(RestUtils.TAG_ERROR_CODE) == "99") {
                                AppUtil.showSessionExpireAlert("Error", resources.getString(R.string.session_timedout), this@DrugSearchActivity)
                            } else if (jsonObject.optString(RestUtils.TAG_ERROR_CODE) == "603") {
                                AppUtil.AccessErrorPrompt(this@DrugSearchActivity, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE))
                            }else{
                                drugSearchViewModel.setIsLoaderVisible(false)
                                drugSearchViewModel.setIsEmptyMsgVisibility(true)
                            }
                        } catch (e: JSONException) {
                            drugSearchViewModel.setIsLoaderVisible(false)
                            drugSearchViewModel.setIsEmptyMsgVisibility(true)
                            e.printStackTrace()
                        }
                    } else {
                        drugSearchViewModel.setIsLoaderVisible(false)
                        drugSearchViewModel.setIsEmptyMsgVisibility(true)
                        //Toast.makeText(this@DrugSearchActivity, "Something went wrong,please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun removeDuplicateItems(drugsList: List<SearchedDrugItem>):ArrayList<SearchedDrugItem?> {
        var filteredItems = ArrayList<SearchedDrugItem?>()
        var itemIds=HashSet<Int>()
        
        for (drugItem: SearchedDrugItem in drugsList){
            if(itemIds.contains(drugItem.itemId)){
                continue
            }else{
                itemIds.add(drugItem.itemId)
                filteredItems.add(drugItem)
            }
        }
        return filteredItems
    }


    private fun setRequestData(searchKeywork: String, pgNumber: Int) {
        drugSearchViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_DRUG_SEARCH, "GET_DRUG_SEARCH", AppUtil.getRequestHeaders(this) as HashMap<String, String>, pgNumber, searchKeywork)
    }

    fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun EditText.setupClearButtonWithAction() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val clearIcon = if (editable?.isNotEmpty() == true) R.drawable.ic_clear else 0
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_icon_new, 0, clearIcon, 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    this.setText("")
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
    }

    fun showSoftKeyboard(view: EditText) {
        if (view.requestFocus()) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}