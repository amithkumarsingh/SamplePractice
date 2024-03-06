package com.vam.whitecoats.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.vam.whitecoats.App_Application
import com.vam.whitecoats.R
import com.vam.whitecoats.core.realm.RealmManager
import com.vam.whitecoats.databinding.ActivityAccountDeleteBinding
import com.vam.whitecoats.models.AccountDeleteAPIResponse
import com.vam.whitecoats.utils.AppUtil
import com.vam.whitecoats.utils.RestUtils
import com.vam.whitecoats.viewmodel.AccountDeleteViewModel
import com.vam.whitecoats.viewmodel.DrugClassViewModel
import io.realm.Realm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class AccountDeleteActivity : BaseActionBarActivity() {

    private var userId: Int = 0
    private lateinit var binding: ActivityAccountDeleteBinding
    private lateinit var accountDeleteViewModel: AccountDeleteViewModel
    private lateinit var realmManager: RealmManager
    override fun setCurrentActivity() {
        App_Application.setCurrentActivity(this@AccountDeleteActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        realmManager = RealmManager(this)
        userId = realmManager.getDoc_id(Realm.getDefaultInstance())
        accountDeleteViewModel = ViewModelProviders.of(this).get(AccountDeleteViewModel::class.java)
        mInflater = LayoutInflater.from(this)
        mCustomView = mInflater.inflate(R.layout.actionbar_preview, null)
        val mTitleTextView = mCustomView.findViewById<View>(R.id.title_edit) as TextView
        mTitleTextView.text = "Delete My Account"
        binding.reasonRadioGroup.clearCheck()
        binding.btnDelete.isEnabled = false
        binding.reasonOtherEt.isEnabled = false

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.reasonRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            //val radioButton = radioGroup.findViewById(i) as RadioButton
            when (i) {
                R.id.other_rb -> {
                    binding.reasonOtherEt.isEnabled = true
                }
                else -> {
                    binding.reasonOtherEt.isEnabled = false
                }
            }
            binding.btnDelete.isEnabled = true
        }

        binding.btnDelete.setOnClickListener {
            val selectedId: Int = binding.reasonRadioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                /*Toast.makeText(this@AccountDeleteActivity,
                        findViewById<RadioButton>(selectedId).text,
                        Toast.LENGTH_SHORT)
                        .show()*/

                displayConfirmationPopup()
            }
        }
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar.setDisplayShowHomeEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayUseLogoEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.customView = mCustomView
    }

    private fun displayConfirmationPopup() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .create()
        builder.setCancelable(false)
        val view = layoutInflater.inflate(R.layout.delete_account_popup, null)
        val deleteBtn = view.findViewById<Button>(R.id.delete_btn)
        val cancelBtn = view.findViewById<Button>(R.id.cancel_btn)
        var confirmLayout = view.findViewById<ConstraintLayout>(R.id.delete_confirm_cl)
        var deleteProgressLayout = view.findViewById<ConstraintLayout>(R.id.delete_progress_cl)
        builder.setView(view)
        cancelBtn.setOnClickListener {
            builder.dismiss()
        }
        deleteBtn.setOnClickListener {
            confirmLayout.visibility = View.GONE
            deleteProgressLayout.visibility = View.VISIBLE


            var reason = ""
            var id = binding.reasonRadioGroup.checkedRadioButtonId
            reason = when (id) {
                R.id.other_rb -> binding.reasonOtherEt.text.toString()
                else -> findViewById<RadioButton>(id).text.toString()
            }

            accountDeleteViewModel.deleteUserAccountRequest(AppUtil.getRequestHeaders(this@AccountDeleteActivity), userId, reason, binding.improvementEt.text.toString()).observe(this@AccountDeleteActivity, Observer {
                builder.dismiss()
                if (it != null) {
                    if (it.error == null) {
                        var intent1 = Intent(this@AccountDeleteActivity, AccountDeleteAckActivity::class.java)
                        startActivity(intent1)
                        finish()
                    } else {
                        if (AppUtil.isJSONValid(it.error)) {
                            try {
                                val jsonObject = JSONObject(it.error)
                                if (jsonObject.optString(RestUtils.TAG_ERROR_CODE) == "99") {
                                    AppUtil.showSessionExpireAlert("Error", resources.getString(R.string.session_timedout), this@AccountDeleteActivity)
                                } else {
                                    AppUtil.AccessErrorPrompt(this@AccountDeleteActivity, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE))
                                }
                            } catch (e: JSONException) {
                                Toast.makeText(this@AccountDeleteActivity, "Something went wrong,please try again.", Toast.LENGTH_SHORT).show()
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(this@AccountDeleteActivity, "Something went wrong,please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}