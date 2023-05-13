package com.example.modulararchitecture

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.LoginActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest


class MainActivity : AppCompatActivity() {

    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest

    //same as module name required
    val DYNAMIC_FEATURE = "dynamicfeature"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)
        val buttonClick = findViewById<Button>(R.id.buttonClick)
        val buttonOpenNewsModule = findViewById<Button>(R.id.buttonOpenNewsModule)
        val buttonDeleteNewsModule = findViewById<Button>(R.id.buttonDeleteNewsModule)

        btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        initDynamicModules()

        buttonClick.setOnClickListener {

            if (!splitInstallManager.installedModules.contains(DYNAMIC_FEATURE)) {
                splitInstallManager.startInstall(request).addOnFailureListener {
                    Toast.makeText(this, "$it",Toast.LENGTH_LONG).show()
                }.addOnSuccessListener {
                        buttonOpenNewsModule.visibility = View.VISIBLE
                        buttonDeleteNewsModule.visibility = View.VISIBLE
                    }.addOnCompleteListener {}
            } else {
                buttonDeleteNewsModule.visibility = View.VISIBLE
                buttonOpenNewsModule.visibility = View.VISIBLE
            }
        }


        buttonOpenNewsModule.setOnClickListener {
            val intent =
                Intent().setClassName(this, "com.example.dynamicfeature.DynamicFeatureActivity")
            startActivity(intent)
        }

        buttonDeleteNewsModule.setOnClickListener {
            val list = ArrayList<String>()
            list.add(DYNAMIC_FEATURE)
            splitInstallManager.deferredUninstall(list).addOnSuccessListener {
                    buttonDeleteNewsModule.visibility = View.GONE
                    buttonOpenNewsModule.visibility = View.GONE
                }
        }

        /*//If we have multiple modules in the app, we can get the list of all the installed modules using
           SplitInstallManager.getInstalledModules()*/

    }

    private fun initDynamicModules() {

        //SplitInstallManager is responsible for downloading the module. The app has to be in Foreground to download the dynamic module.
        //SplitInstallRequest will contain the request information that will be used to request our dynamic feature module from Google Play.

        splitInstallManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest.newBuilder().addModule(DYNAMIC_FEATURE).build()
    }
}