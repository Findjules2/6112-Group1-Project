package com.example.a6112_final_project_kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.a6112_final_project_kotlin.databinding.FragmentItemFormBinding

private const val ITEM = "item"

class EditItemFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()

    private var _binding: FragmentItemFormBinding? = null
    private val binding get() = _binding!!

    private val TAG = "EditItemFragment"

    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            item = it.getParcelable(ITEM)
        }
        Log.d(TAG, "onCreate: " + item?.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onCreateView: " + item?.name)

        with(binding) {
            val itemIdEditText = editTextItemId
            val itemNameEditText = editTextItemName
            val itemDescEditText = editTextItemDesc
            val itemCategoryEditText = editTextItemCategory

            val currQuantityEditText = editTextCurrQty
            val lowStockEditText = editTextLowStock
            val requiredEditText = editTextRequired
            val submitButton = buttonSubmit
            val cancelButton = buttonCancel

            itemIdEditText.setText(item?.id.toString())
            itemNameEditText.setText(item?.name)
            itemDescEditText.setText(item?.description)
            itemCategoryEditText.setText(item?.category)
            currQuantityEditText.setText(item?.currQuantity.toString())
            lowStockEditText.setText(item?.lowStock.toString())
            requiredEditText.setText(item?.required.toString())

            submitButton.setOnClickListener {
                item?.apply {
                    id = itemIdEditText.text.toString().toInt()
                    name = itemNameEditText.text.toString()
                    description = itemDescEditText.text.toString()
                    category = itemCategoryEditText.text.toString()
                    currQuantity = currQuantityEditText.text.toString().toInt()
                    lowStock = lowStockEditText.text.toString().toInt()
                    required = requiredEditText.text.toString().toInt()

                    viewModel.items.value?.let { itemList ->
                        val itemExists = itemList.any { it.id == this.id }
                        if (itemExists) {
                            viewModel.updateItem(this)
                        } else {
                            viewModel.addItem(this)
                        }
                    }
                }
                goBackToItemList()
            }

            cancelButton.setOnClickListener {
                goBackToItemList()
            }
        }
    }

    private fun goBackToItemList() {
        val action = EditItemFragmentDirections.actionEditItemFragmentToItemListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(item: Item) = EditItemFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM, item)
            }
        }
    }
}
