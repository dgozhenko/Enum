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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.cartoonsocialclub.databinding.ListItemAvatarBinding

// TODO 1 : replace this dataclass with one using an Enum.
data class Item(
    val avatar: CartoonAvatar,
    var isSelected: Boolean
) {
    @DrawableRes
    var imageResourceBundle = avatar.drawableRes
}

class ViewHolder private constructor(val binding: ListItemAvatarBinding) :
    RecyclerView.ViewHolder(binding.root) {
  fun bind(item: Item) {
    binding.avatarThumbnailImageView.setImageResource(item.imageResourceBundle)
    val backgroundColor = if (item.isSelected) R.color.colorSelectionBackground else android.R
        .color.transparent
    binding.root.setBackgroundResource(backgroundColor)
  }

  companion object {
    fun from(parent: ViewGroup): ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = ListItemAvatarBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
    }
  }
}

/**
 * Adapter for populating the list of cartoon avatar choices in the recycler view
 * the onItemClicked is a lambda method passed for convenience in order to handle this externally
 */
class CartoonAvatarAdapter(
    var onItemClick: ((Item) -> Unit) = {}
) : ListAdapter<Item, ViewHolder>
(CartoonAvatarItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    holder.binding.root.setOnClickListener { onItemClick.invoke(item) }
    holder.bind(item)
  }
}

private class CartoonAvatarItemDiffCallback : DiffUtil.ItemCallback<Item>() {
  override fun areItemsTheSame(oldItem: Item, newItem: Item) =
      oldItem.imageResourceBundle == newItem.imageResourceBundle

  override fun areContentsTheSame(oldItem: Item, newItem: Item) =
      oldItem == newItem
}
