package com.fuad.fuadgithub.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fuad.fuadgithub.R
import com.fuad.fuadgithub.SectionsPagerAdapter
import com.fuad.fuadgithub.databinding.ActivityDetailUserBinding
import com.fuad.fuadgithub.response.ResponseDetailUser
import com.fuad.fuadgithub.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.app_name)

        val username = intent.getStringExtra(EXTRA_USER) as String
        detailViewModel.getUser(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        binding.viewPager.adapter = sectionsPagerAdapter
        supportActionBar?.elevation = 0f

        detailViewModel.userDetail.observe(this) { user ->
            setUserData(user)
        }

        detailViewModel.snackBarText.observe(this) {
            it.getContentIfnotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setUserData(user: ResponseDetailUser) {

        binding.detailName.text = user.name
        binding.detailUsername.text = user.login
        binding.companyIsi.text = user.company
        binding.locationIsi.text = user.location
        binding.repoIsi.text = resources.getString(R.string.repository, user.publicRepos)

        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.detailPhoto)
        val countFollow = arrayOf(user.followers, user.following)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position], countFollow[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}