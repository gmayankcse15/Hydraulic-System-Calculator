package com.uniquespm.hydraulic.ui.symbol

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R

class SymbolFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mSymbolList: RecyclerView
    private val symbolResource = arrayOf(
        R.drawable.symbol_1,
        R.drawable.symbol_2,
        R.drawable.symbol_3,
        R.drawable.symbol_4,
        R.drawable.symbol_5,
        R.drawable.symbol_6,
        R.drawable.symbol_7,
        R.drawable.symbol_9,
        R.drawable.symbol_8,
        R.drawable.symbol_10,
        R.drawable.symbol_11,
        R.drawable.symbol_12,
        R.drawable.symbol_13
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_symbols, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSymbolList = view.findViewById(R.id.symbol_list)
        val  mSymbolAdapter = SymbolListAdapter(mContext, symbolResource)
        mSymbolList.layoutManager = LinearLayoutManager(mContext)
        mSymbolList.adapter = mSymbolAdapter
    }
}
