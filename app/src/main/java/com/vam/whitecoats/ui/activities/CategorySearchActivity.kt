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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.vam.whitecoats.R
import com.vam.whitecoats.constants.RestApiConstants
import com.vam.whitecoats.core.models.CategoryFeedsSearchItem
import com.vam.whitecoats.core.realm.RealmManager
import com.vam.whitecoats.databinding.ActivityCategorySearchBinding
import com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter
import com.vam.whitecoats.ui.interfaces.CategoryFeedsItemClickListener
import com.vam.whitecoats.utils.AppUtil
import com.vam.whitecoats.utils.RestUtils
import com.vam.whitecoats.utils.SimpleDividerItemDecoration
import com.vam.whitecoats.viewmodel.CategorySearchViewModel
import com.vam.whitecoats.viewmodel.CategorySearchViewModelFactory
import io.realm.Realm
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class CategorySearchActivity : BaseActionBarActivity() {
    private var navigationFrom: String? = null
    private var category_name: String? = null
    private lateinit var searchString: String
    var page_no:Int=0
    private var isListExhausted = false
    private var loading = false
    private var categoryId: Int = 0
    private var category_type: String? = null
    private lateinit var categorySearchViewModel: CategorySearchViewModel
    private lateinit var adapter: FeedsCategoryDistributionAdapter
    private val feeds_list = ArrayList<CategoryFeedsSearchItem>()
    private var realm: Realm? = null
    private var realmManager: RealmManager? = null
    override fun setCurrentActivity() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_category_search)
        val binding: ActivityCategorySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_category_search)
        category_type = intent.getStringExtra("CategoryType")
        categoryId = intent.getIntExtra("CategoryId", 0)
        category_name=intent.getStringExtra("CategoryName")
        navigationFrom=intent.getStringExtra("NavigationFrom");

        realm = Realm.getDefaultInstance()
        realmManager = RealmManager(this)
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
        searchEdt.requestFocusFromTouch()
        searchEdt.requestFocus()
        searchEdt.setHint("Search"+" "+category_name)
        val im = getSystemService(BaseActionBarActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(searchEdt, 0)
        searchEdt.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.toString().trim().isEmpty()) {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(R.string.empty_search_keyword)
                    builder.setCancelable(true)
                    builder.setPositiveButton("ok") { dialog, which -> }.create().show()
                } else if (v.text.length < 3) {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(R.string.min_characters_search_keyword)
                    builder.setCancelable(true)
                    builder.setPositiveButton("ok") { dialog, which -> }.create().show()
                } else {
                    searchEdt.clearFocus()
                    im.hideSoftInputFromWindow(searchEdt.getWindowToken(), 0)
                    if (AppUtil.isConnectingToInternet(this)) {
                        searchString = v.text.toString()

                        val jsonObjectEvent = JSONObject()
                        try {
                            jsonObjectEvent.put("DocID", realmManager!!.getUserUUID(realm))
                            var eventName = ""
                          if (navigationFrom != null && navigationFrom.equals("SubCategoriesActivity", ignoreCase = true)) {
                              eventName =  "SubCategoryFeedSearchInitiated"
                              jsonObjectEvent.put("Sub-CategoryName", category_name)
                          } else if (navigationFrom != null && navigationFrom.equals("CategoryDistribution", ignoreCase = true)) {
                              eventName =   "CategoryDistributionSearchInitiated"
                            } else {
                              eventName =   "CategoryFeedSearchInitiated"
                              jsonObjectEvent.put("CategoryName", category_name)
                          }
                            jsonObjectEvent.put("SearchString", searchString)
                            AppUtil.logUserActionEvent(realmManager!!.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),this@CategorySearchActivity)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        categorySearchViewModel.displayLoader()
                        setRequestData(0,v.text.toString())
                        performSearch()
                    }

                }
                return@OnEditorActionListener true
            }
            false
        })
        val factory = CategorySearchViewModelFactory()
        categorySearchViewModel = ViewModelProviders.of(this, factory)[CategorySearchViewModel::class.java]
        categorySearchViewModel.setIsEmptyMsgVisibility(false)


        adapter = FeedsCategoryDistributionAdapter(CategoryFeedsItemClickListener { categoryFeeds ->
            if (categoryFeeds != null) {
                if (AppUtil.isConnectingToInternet(this)) {
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put(RestUtils.TAG_DOC_ID, realmManager!!.getDoc_id(realm))
                        jsonObject.put(RestUtils.CHANNEL_ID, categoryFeeds.channelId)
                        jsonObject.put(RestUtils.TAG_TYPE, categoryFeeds.feedTypeId)
                        jsonObject.put(RestUtils.FEED_ID, categoryFeeds.feedId)
                        val intent = Intent(this, EmptyActivity::class.java)
                        intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString())
                        startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })


        binding.setAdapter(adapter)
        binding.setViewModel(categorySearchViewModel)
        binding.setLifecycleOwner(this)
        var searchResultsList = binding.categorySearchList
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
                    if (isListExhausted || !AppUtil.isConnectingToInternet(this@CategorySearchActivity)) {
                        return
                    }
                    val visibleItemCount = recyclerView.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    if (!loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            adapter.addDummyItemToList()
                            loading = true
                            //Do pagination.. i.e. fetch new data
                            page_no += 1
                            setRequestData(page_no,searchString)
                            performSearch()
                        } else {
                            loading = false
                        }
                    }
                }
            }
        })
        categorySearchViewModel.isListExhausted().observe(this, androidx.lifecycle.Observer {
            isListExhausted=it
            if (isListExhausted) {
                //Toast.makeText(this, "No more feeds", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun performSearch() {
       categorySearchViewModel.getSearchedFeeds().observe(this, androidx.lifecycle.Observer {
           if (it != null) {
               adapter.removeDummyItemFromList()
               if (it.error == null) {
                   var feedsList = it.feedsList
                   loading = false
                   categorySearchViewModel.displayUIBasedOnCount(feedsList.size)
                   adapter?.setDataList(feedsList.toArrayList())
               } else {
                   if (AppUtil.isJSONValid(it.error)) {
                       try {
                           val jsonObject = JSONObject(it.error)
                           if (jsonObject.optString(RestUtils.TAG_ERROR_CODE) == "99") {
                               AppUtil.showSessionExpireAlert("Error", resources.getString(R.string.session_timedout), this)
                           } else if (jsonObject.optString(RestUtils.TAG_ERROR_CODE) == "603") {
                               AppUtil.AccessErrorPrompt(this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE))
                           }
                       } catch (e: JSONException) {
                           e.printStackTrace()
                       }
                   } else {
                       categorySearchViewModel.setIsLoaderVisible(false)
                       categorySearchViewModel.setIsEmptyMsgVisibility(true)
                       Toast.makeText(this, "Something went wrong,please try again.", Toast.LENGTH_SHORT).show()
                   }
                   loading = false
               }
           }

       })
    }

    private fun setRequestData(pageNumber: Int, searchString: String) {
        categorySearchViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORY_SEARCH, "USER_CATEGORY_LIST",  AppUtil.getRequestHeaders(this) as HashMap<String, String>, pageNumber,category_type, categoryId,searchString)
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