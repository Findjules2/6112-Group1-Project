package com.example.a6112_final_project_kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a6112_final_project_kotlin.databinding.FragmentItemListBinding

class ItemListFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()

    private final val TAG: String = "ItemListFragment:"
    private lateinit var itemAdapter: ItemAdapter

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = ItemAdapter(emptyList()) { item ->
            Log.d(TAG, "Item clicked: ${item.name}")
            val action = ItemListFragmentDirections.actionItemListFragmentToEditItemFragment(item)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = itemAdapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            itemAdapter.updateItems(items)
        }

        binding.addItemBtn.setOnClickListener {
            val newItem = Item(
                id = 0, // Assign appropriate id
                name = "", // Provide default values or an empty form
                description = "",
                category = "",
                currQuantity = 0,
                lowStock = 0,
                required = 0,
                price = 0
            )
            val action = ItemListFragmentDirections.actionItemListFragmentToEditItemFragment(newItem)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Release the binding to avoid memory leaks
    }

    companion object {
        @JvmStatic
        fun newInstance() = ItemListFragment()
    }
}
