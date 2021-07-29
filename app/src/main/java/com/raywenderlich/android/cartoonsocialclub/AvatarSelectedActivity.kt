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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.cartoonsocialclub.databinding.ActivityAvatarSelectedBinding

/**
 * Screen shown when the user successfully completes their profile
 */
class AvatarSelectedActivity : AppCompatActivity() {

  private lateinit var selectedAvatarImageView: ImageView
  private lateinit var fullNameTextView: TextView
  private lateinit var initialTextView: TextView
  private lateinit var selectedAvatarDescriptionTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    val binding = ActivityAvatarSelectedBinding.inflate(layoutInflater)
    setContentView(binding.root)

    selectedAvatarImageView = binding.selectedAvatarImageView
    fullNameTextView = binding.fullNameTextView
    initialTextView = binding.initialTextView
    selectedAvatarDescriptionTextView = binding.selectedAvatarDescriptionTextView

    loadDataFromIntent()
  }

  private fun loadDataFromIntent() {
    val extras = intent.extras
    val fullName = extras?.getString(EXTRA_FULL_NAME)
    val cartoonEnumCase = extras?.getString(EXTRA_CARTOON)
    if (cartoonEnumCase == null) {
      finish()
      return
    }
    val cartoonAvatar = CartoonAvatar.valueOf(cartoonEnumCase)
    populateView(fullName ?: "", cartoonAvatar)
  }

  private fun populateView(fullName: String, cartoonAvatar: CartoonAvatar) {
    fullNameTextView.text = fullName
    selectedAvatarDescriptionTextView.text = cartoonAvatar.selectionDescription(this)
    when (cartoonAvatar) {
      CartoonAvatar.NONE -> {
        initialTextView.text = fullName.uppercasedInitial()
        selectedAvatarImageView.visibility = View.GONE
        initialTextView.visibility = View.VISIBLE
      } else -> {
        selectedAvatarImageView.setImageResource(cartoonAvatar.drawableRes)
      selectedAvatarImageView.visibility = View.VISIBLE
      initialTextView.visibility = View.GONE
      }
    }
  }

  companion object {
    private const val EXTRA_FULL_NAME = "com.raywenderlich.android.cartoonsocialclub.FULL_NAME"
    private const val EXTRA_CARTOON = "com.raywenderlich.android.cartoonsocialclub.CARTOON"

    fun newIntent(fullName: String, selectedAvatar: CartoonAvatar, context: Context): Intent {
      return Intent(context, AvatarSelectedActivity::class.java).apply {
        putExtra(EXTRA_FULL_NAME, fullName)
        putExtra(EXTRA_CARTOON, selectedAvatar.name)
      }
    }
  }
}