package com.psw9999.firebase_dynamiclink

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.FirebaseApp
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.psw9999.firebase_dynamiclink.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFIX = "https://psw9999.page.link"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initButton()
        initDynamicLink()
    }


    override fun onResume() {
        super.onResume()
        observeData()
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    fun generateSharingLink(
        deepLink: Uri,
        getShareableLink: (String) -> Unit = {},
    ) {
        FirebaseDynamicLinks.getInstance().createDynamicLink().run {
            // What is this link parameter? You will get to know when we will actually use this function.
            link = deepLink

            // [domainUriPrefix] will be the domain name you added when setting up Dynamic Links at Firebase Console.
            // You can find it in the Dynamic Links dashboard.
            domainUriPrefix = PREFIX

            // Required
            androidParameters {
                build()
            }

            // Finally
            buildShortDynamicLink()
        }.also {
            it.addOnSuccessListener { dynamicLink ->
                getShareableLink(dynamicLink.shortLink.toString())
            }
            it.addOnFailureListener {
                Toast.makeText(this, "공유 링크 생성에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initButton() {
        binding.testButton.setOnClickListener {
            viewModel.onBtnClick()
        }
    }

    private fun shareInformation(link: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun initDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { dynamicLinkData ->
                dynamicLinkData?.let { uri ->
                    val deepLink = dynamicLinkData.link.toString()
                    val stockInfoNumber =
                        deepLink.substring(deepLink.lastIndexOf("/") + 1)
                    binding.tvTest.text = stockInfoNumber
                }
            }
            .addOnFailureListener {
            }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shareEvent.collectLatest {
                    generateSharingLink(
                        deepLink = "${PREFIX}/stockInfo/${50}".toUri(),
                    ) { generateLink ->
                        shareInformation(generateLink)
                    }
                }
            }
        }
    }
}