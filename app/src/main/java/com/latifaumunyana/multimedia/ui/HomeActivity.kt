package com.latifaumunyana.multimedia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.latifaumunyana.multimedia.R
import com.latifaumunyana.multimedia.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        binding.bnvHome.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                }

                R.id.games -> {
                    loadFragment(ProfileFragment())
                }

                R.id.profile -> {
                    loadFragment(GamesFragment())
                }

                R.id.settings -> {
                    val settingsFragment = SettingsFragment()
                    loadFragment(settingsFragment)
                }

                else->{
                    loadFragment(HomeFragment())
                }
            }
        }

    }

        fun loadFragment(fragment: Fragment): Boolean{
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fcvHome, fragment)
            fragmentTransaction.commit()
            return true
        }

    }
