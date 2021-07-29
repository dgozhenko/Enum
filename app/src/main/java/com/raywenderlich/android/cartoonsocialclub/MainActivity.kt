/*
 * Copyright (c) 2021 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.cartoonsocialclub

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.cartoonsocialclub.databinding.ActivityMainBinding

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  private lateinit var fullNameEditText: EditText
  private lateinit var avatarsRecyclerView: RecyclerView
  private lateinit var continueButton: Button

  private lateinit var cartoonAvatarAdapter: CartoonAvatarAdapter

  private var selectedAvatar: CartoonAvatar? = null

  private var avatarChoices = CartoonAvatar.values().map {
    Item(it, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    fullNameEditText = binding.fullNameEditText
    avatarsRecyclerView = binding.avatarsRecyclerView
    continueButton = binding.continueButton
    setupRecyclerView()

    // Hide the "Continue" button while nothing is selected
    continueButton.visibility = View.GONE
    setupContinueButton()

  }

  private fun setupContinueButton() {
    fullNameEditText.doAfterTextChanged {
      if (it.toString().isNotBlank() && selectedAvatar != null) {
        continueButton.visibility = View.VISIBLE
      } else {
        continueButton.visibility = View.GONE
      }
    }

    continueButton.setOnClickListener {
      val selectedAvatar = selectedAvatar ?: return@setOnClickListener
      val firstName = fullNameEditText.text.toString()
      val intent = AvatarSelectedActivity.newIntent(firstName, selectedAvatar, this)
      startActivity(intent)
    }
  }

  private fun selectAvatar(avatar: Item) {
    selectedAvatar = avatar.avatar
    avatarChoices.forEach{
      it.isSelected = it.avatar == selectedAvatar
    }
    cartoonAvatarAdapter.notifyDataSetChanged()
    if (fullNameEditText.text.isNotBlank()) {
      continueButton.visibility = View.VISIBLE
    }
  }

  private fun setupRecyclerView() {
    cartoonAvatarAdapter = CartoonAvatarAdapter(onItemClick = { avatar ->
      selectAvatar(avatar)
    })
    cartoonAvatarAdapter.submitList(avatarChoices)
    avatarsRecyclerView.adapter = cartoonAvatarAdapter
  }
}
