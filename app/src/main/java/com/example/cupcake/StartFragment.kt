/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel


class StartFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.startFragment = this
    }

    fun orderCupcake(rusName: String) {
        sharedViewModel.setRusName(rusName)
        when (rusName) {
            "Блядокрут Яйцетрест" -> {
                sharedViewModel.setQuantity(1)
                Toast.makeText(
                    activity,
                    "Выбран Рус за ${sharedViewModel.pricePerRus * 1}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            "Праведнослав Кувалдович" -> {
                sharedViewModel.setQuantity(2)
                Toast.makeText(
                    activity,
                    "Выбран Рус за ${sharedViewModel.pricePerRus * 2}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            "Зубодробир Ящероплющевич" -> {
                sharedViewModel.setQuantity(4)
                Toast.makeText(
                    activity,
                    "Выбран Рус за ${sharedViewModel.pricePerRus * 4}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (sharedViewModel.hasNoSkillSet()) {
            sharedViewModel.setSkillAndPrice(getString(R.string.noskill), 0.0)
        }
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}