package org.android.go.sopt.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import org.android.go.sopt.R
import org.android.go.sopt.account.AccountFragment
import org.android.go.sopt.album.AlbumFragment
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.follower.FollowerFragment
import org.android.go.sopt.playlist.ListFragment
import org.android.go.sopt.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listFragment: ListFragment
    private lateinit var albumFragment: AlbumFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var followerFragment: FollowerFragment
    private lateinit var accountFragment: AccountFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시작 Fragment 설정
        setFirstFragment()

        // 바텀 네비게이션 뷰 구현
        changeFragmentByBnv()

        // 리스트 화면에서 바텀 네비게이션뷰 두번 클릭 시 맨 위 화면으로 이동
        binding.bnvMain.setOnItemReselectedListener {
            scrollToTopForReselectedItem(it)
        }
    }

    private fun setFirstFragment() {
        listFragment = ListFragment()
        supportFragmentManager.findFragmentById(R.id.fcv_main)
            ?: supportFragmentManager.beginTransaction().add(R.id.fcv_main, listFragment).commit()
    }

    private fun changeFragmentByBnv() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_playlist -> changeFragment(listFragment)
                R.id.menu_album -> {
                    if (!::albumFragment.isInitialized) {
                        albumFragment = AlbumFragment()
                    }
                    changeFragment(albumFragment)
                }
                R.id.menu_search -> {
                    if (!::searchFragment.isInitialized) {
                        searchFragment = SearchFragment()
                    }
                    changeFragment(SearchFragment())
                }
                R.id.menu_follower -> {
                    if (!::followerFragment.isInitialized) {
                        followerFragment = FollowerFragment()
                    }
                    changeFragment(FollowerFragment())
                }
                R.id.menu_account -> {
                    if (!::accountFragment.isInitialized) {
                        accountFragment = AccountFragment()
                    }
                    changeFragment(AccountFragment())
                }
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main, fragment)
        }
    }

    private fun scrollToTopForReselectedItem(item : MenuItem) {
        if (item.itemId == R.id.menu_playlist) {
            when (val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)) {
                is ListFragment -> {
                    currentFragment.scrollToTop()
                }
            }
        }
    }
}