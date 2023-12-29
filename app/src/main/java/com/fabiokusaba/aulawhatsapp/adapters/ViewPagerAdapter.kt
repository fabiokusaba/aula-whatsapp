package com.fabiokusaba.aulawhatsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fabiokusaba.aulawhatsapp.fragments.ContatosFragment
import com.fabiokusaba.aulawhatsapp.fragments.ConversasFragment

class ViewPagerAdapter(
    private val abas: List<String>,
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int {
        return abas.size //listOf(0 -> "CONVERSAS", 1 -> "CONTATOS")
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            1 -> return ContatosFragment()
        }

        return ConversasFragment()
    }

}