package com.mertmsrl.kotlinproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    var handler : Handler? = null
    var runnable : Runnable = Runnable {  }
    var dataSource : DataSource? = null


    var bottomNav : BottomNavigationView? = null
    val fragmentManager : FragmentManager = supportFragmentManager
    var fragmentTransaction : FragmentTransaction? = null
    var firstFragment : FirstFragment? = null
    var secondFragment : SecondFragment? = null
    var thirdFragment : ThirdFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNav = bottom_nav

        



        var number = 0
        runnable = object : Runnable{
            override fun run() {
                number ++
                handler?.postDelayed(runnable, 1 * 1000)
            }

        }

        runnable = Runnable {
            number ++
            handler?.postDelayed(runnable, 1 * 1000)
        }





    }


    fun btnOpenFirstFragmentOnclick(view: View) {
        fragmentTransaction = fragmentManager.beginTransaction()
        firstFragment = FirstFragment()
        fragmentTransaction!!.replace(R.id.frame_layout, firstFragment!!)
        fragmentTransaction!!.commit()
    }
    fun btnOpenSecondFragmentOnClick(view: View) {
        fragmentTransaction = fragmentManager.beginTransaction()
        secondFragment = SecondFragment()
        if (fragmentTransaction != null && secondFragment != null){
            fragmentTransaction!!.replace(R.id.frame_layout, secondFragment!!)
            fragmentTransaction!!.commit()
        }
    }
    fun btnOpenThirdFragmentOnClick(view: View) {
        fragmentTransaction = fragmentManager.beginTransaction()
        thirdFragment = ThirdFragment()
        fragmentTransaction!!.replace(R.id.frame_layout, thirdFragment!!)
        fragmentTransaction!!.commit()

    }

    fun addBook(){

    }

}