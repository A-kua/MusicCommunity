package fan.akua.exam.activities.main.model

import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.drake.brv.BindingAdapter
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.item.ItemBind
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import fan.akua.exam.R
import fan.akua.exam.data.MusicInfo
import fan.akua.exam.databinding.ItemLargecardBinding
import fan.akua.exam.databinding.ItemTypeLargecardBinding
import fan.akua.exam.utils.dp


class LargeCard(val data: List<MusicInfo>) : ItemBind {
    override fun onBind(vh: BindingAdapter.BindingViewHolder) {
        val binding = ItemTypeLargecardBinding.bind(vh.itemView)
        binding.title.text = vh.context.resources.getString(R.string.str_exclusive_song)
        val width = binding.root.context.resources.displayMetrics.widthPixels * 0.8
        binding.rv.linear(RecyclerView.HORIZONTAL).setup {
            addType<MusicInfo>(R.layout.item_largecard)
            onCreate {
                val itemLargeBinding = getBinding<ItemLargecardBinding>()
                val layoutParams = itemLargeBinding.parentCardView.layoutParams
                layoutParams.width = width.toInt()
                itemLargeBinding.parentCardView.layoutParams = layoutParams
            }
            onBind {
                val model = getModel<MusicInfo>()
                val itemLargeBinding = getBinding<ItemLargecardBinding>()
                itemLargeBinding.musicInfo = model
                Glide.with(itemLargeBinding.img)
                    .load(model.coverUrl)
                    .into(itemLargeBinding.img)
                itemLargeBinding.root.setOnClickListener {
                    Toast.makeText(context, model.musicName, Toast.LENGTH_SHORT).show()
                }
                itemLargeBinding.playButton.setOnClickListener {
                    Toast.makeText(
                        context,
                        "将${model.musicName}添加到音乐列表",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.models = data
        binding.rv.bindingAdapter.setAnimation(AnimationType.SLIDE_BOTTOM)
        val snapHelper: SnapHelper = LinearSnapHelper()
        binding.rv.setOnFlingListener(null)
        snapHelper.attachToRecyclerView(binding.rv)
    }
}