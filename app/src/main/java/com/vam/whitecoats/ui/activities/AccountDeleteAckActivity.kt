package com.vam.whitecoats.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vam.whitecoats.core.realm.RealmManager
import com.vam.whitecoats.databinding.ActivityAccountDeleteAckBinding
import com.vam.whitecoats.tools.MySharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AccountDeleteAckActivity:AppCompatActivity() {


    private lateinit var mySharedPref: MySharedPref
    private lateinit var realmManager: RealmManager
    private lateinit var binding: ActivityAccountDeleteAckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAccountDeleteAckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        realmManager=RealmManager(this);
        mySharedPref = MySharedPref(this)
        lifecycleScope.launch {
            delay(4000)
            clearUserData()
            navigateUserToStartScreen()
        }
        binding.accountDelAckCloseBtn.setOnClickListener{
            clearUserData()
            navigateUserToStartScreen()
        }
    }

    private fun clearUserData() {
        mySharedPref.savePref(MySharedPref.STAY_LOGGED_IN, false)
        mySharedPref.savePref(MySharedPref.PREF_SESSION_TOKEN, "")
        mySharedPref.savePref(MySharedPref.PREF_INTROSLIDES, false)
        realmManager.deleteCompleteRealmData()
    }

    private fun navigateUserToStartScreen() {
        var intent= Intent(this@AccountDeleteAckActivity,IntroSlidesActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

}